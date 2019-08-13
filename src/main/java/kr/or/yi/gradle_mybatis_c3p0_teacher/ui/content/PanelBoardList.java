package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.PageMaker;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.SearchCriteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.BoardList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class PanelBoardList extends JPanel implements ActionListener, ItemListener {
	private BoardList pList;
	private JPanel pPageBtns;
	private PageMaker pm;
	private SearchCriteria cri;
	private JLabel lblPage;
	private BoardDao dao;
	private JButton btnSearch;
	private JComboBox<String> cmbCondition;
	private JTextField tfSearchKey;
	private Map<String, String> searchMap;
	private String[] keys;
	private int totalCnt;
	
	public PanelBoardList() {
		createSearchMap();
		initComponents();
	}

	private void createSearchMap() {
		keys = new String[] {"---", "Title", "Content", "Writer", "Title OR Content", "Content OR Writer", "Title OR Content OR Writer"};
		String[] values = {"n", "t", "c", "w", "tc", "cw", "tcw"};
		
		searchMap = new HashMap<String, String>();
		for(int i=0; i<keys.length;i++) {
			searchMap.put(keys[i], values[i]);
		}
	}

	private void initComponents() {
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
		cmbCondition.addItemListener(this);
		cmbCondition.setModel(getCmbModel());
		pSearch.add(cmbCondition);

		tfSearchKey = new JTextField();
		pSearch.add(tfSearchKey);
		tfSearchKey.setColumns(20);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		pSearch.add(btnSearch);

		lblPage = new JLabel();
		pBottom.add(lblPage);

		pPageBtns = new JPanel();
		pBottom.add(pPageBtns);
	}

	private ComboBoxModel<String> getCmbModel() {
		ComboBoxModel<String> model = new DefaultComboBoxModel<String>(keys);
		return model;
	}

	private void reloadPaging(int startNum) {
		listToPage(startNum);
		initPaging();
	}
	
	public void reloadList() {
		pList.setItemList(dao.getListCriteria(cri));
		pList.reloadData();
	}

	private void listToPage(int startPage) {
		if (cri==null) {
			cri = new SearchCriteria();
		}
		
		cri.setPage(startPage); // 10page
		cri.setPerPageNum(20);// 1page당 20개
		
		totalCnt = dao.listSearchCount(cri);
		
		if (pm==null) {
			pm = new PageMaker();
		}
		pm.setCri(cri);
		pm.setTotalCount(totalCnt);

		reloadList();

		lblPage.setText(pm.toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			actionPerformedBtnSearch(e);
			return;
		}
		if (e.getActionCommand().equals("<<")) {
			reloadPaging(1);
			return;
		}
		if (e.getActionCommand().equals(">>")) {
			reloadPaging(pm.getTotalCount() / pm.getCri().getPerPageNum());
			return;
		}
		if (e.getActionCommand().equals("<")) {
			reloadPaging(pm.getStartPage() - 10);
			return;
		}
		if (e.getActionCommand().equals(">")) {
			reloadPaging(pm.getStartPage() + 10);
			return;
		}

		listToPage(Integer.parseInt(e.getActionCommand()));
		selectedPageColor((JButton) e.getSource());
	}

	protected void actionPerformedBtnSearch(ActionEvent e) {
		String searchType = (String) cmbCondition.getSelectedItem();
		String keyword = "%" + tfSearchKey.getText().trim() + "%";
		cri.setSearchType(searchMap.get(searchType));
		cri.setKeyword(keyword);
		reloadPaging(1);
	}
	
	private void selectedPageColor(JButton curBtn) {
		curBtn.setForeground(Color.RED);
		for (Component c : pPageBtns.getComponents()) {
			if (!c.equals(curBtn))
				c.setForeground(Color.BLACK);
		}
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

	public void setDao(BoardDao dao) {
		this.dao = dao;
		reloadPaging(1);
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		pList.setPopupMenu(popupMenu);
	}
	
	public Board getSelectedBoard() { 
		return pList.getSelectedItem();
	}
	
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == cmbCondition) {
			itemStateChangedCmbCondition(e);
		}
	}
	
	protected void itemStateChangedCmbCondition(ItemEvent e) {
		if (e.getStateChange()==ItemEvent.SELECTED) {
			if (e.getItem().equals("---")) {
				tfSearchKey.setText("");
			}
		}
	}
}
