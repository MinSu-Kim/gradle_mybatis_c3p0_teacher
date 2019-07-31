package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.PageMaker;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.BoardUI;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.BoardList;

@SuppressWarnings("serial")
public class PanelBoardList extends JPanel implements ActionListener {
	private BoardList pList;
	private JPanel pPageBtns;
	private PageMaker pm;
	private Criteria cri;
	private JLabel lblPage;
	private BoardDao dao;
	private JButton btnSearch;
	private JComboBox<String> cmbCondition;
	private JTextField tfSearchKey;
	private BoardUI boardUI;

	public PanelBoardList() {
		setLayout(new BorderLayout(0, 0));
		JPanel pCenter = new JPanel();
		add(pCenter, BorderLayout.CENTER);
		pCenter.setLayout(new BorderLayout(0, 0));

		pList = new BoardList();
		pCenter.add(pList, BorderLayout.CENTER);

		JPanel pBottom = new JPanel();
		add(pBottom, BorderLayout.SOUTH);
		pBottom.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel pSearch = new JPanel();
		FlowLayout fl_pSearch = (FlowLayout) pSearch.getLayout();
		fl_pSearch.setAlignment(FlowLayout.LEFT);
		pBottom.add(pSearch);

		cmbCondition = new JComboBox<>();
		pSearch.add(cmbCondition);

		tfSearchKey = new JTextField();
		pSearch.add(tfSearchKey);
		tfSearchKey.setColumns(10);

		btnSearch = new JButton("검색");
		pSearch.add(btnSearch);

		lblPage = new JLabel();
		pBottom.add(lblPage);

		pPageBtns = new JPanel();
		pBottom.add(pPageBtns);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("<<")) {
			listToPage(1);
			initPaging();
			return;
		}
		if (e.getActionCommand().equals(">>")) {
			listToPage(pm.getTotalCount() / pm.getCri().getPerPageNum());
			initPaging();
			return;
		}
		if (e.getActionCommand().equals("<")) {
			listToPage(pm.getStartPage() - 10);
			initPaging();
			return;
		}
		if (e.getActionCommand().equals(">")) {
			listToPage(pm.getStartPage() + 10);
			initPaging();
			return;
		}

		listToPage(Integer.parseInt(e.getActionCommand()));
		selectedPageColor((JButton) e.getSource());
	}

	private void selectedPageColor(JButton curBtn) {
		curBtn.setForeground(Color.RED);
		for (Component c : pPageBtns.getComponents()) {
			if (!c.equals(curBtn))
				c.setForeground(Color.BLACK);
		}
	}

	private void listToPage(int startPage) {
		cri = new Criteria();
		cri.setPage(startPage); // 10page
		cri.setPerPageNum(20);// 1page당 20개

		int totalCnt = dao.countPaging();
		pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCnt);

		reloadList();

		lblPage.setText(pm.toString());
	}

	public void reloadList() {
		pList.setItemList(dao.getListCriteria(cri));
		pList.reloadData();
	}

	private void initPaging() {
		pPageBtns.removeAll();
		repaint();
		revalidate();

		if (pm.isPrev()) {
			JButton firstBtn = new JButton("<<");
			firstBtn.addActionListener(this);
			pPageBtns.add(firstBtn);
			JButton btn = new JButton("<");
			btn.addActionListener(this);
			pPageBtns.add(btn);
		}
		for (int i = pm.getStartPage(); i < pm.getEndPage() + 1; i++) {
			JButton btn = new JButton(i + "");
			btn.addActionListener(this);
			pPageBtns.add(btn);
		}
		if (pm.isNext()) {
			JButton btn = new JButton(">");
			btn.addActionListener(this);
			pPageBtns.add(btn);
			JButton lastBtn = new JButton(">>");
			lastBtn.addActionListener(this);
			pPageBtns.add(lastBtn);
		}
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public void setDao(BoardDao dao) {
		this.dao = dao;
		listToPage(1);
		initPaging();
	}

	public void setBoardUI(BoardUI boardUI) {
		this.boardUI = boardUI;
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		pList.setPopupMenu(popupMenu);
	}
	
	public Board getSelectedBoard() { 
		return pList.getSelectedItem();
	}
}
