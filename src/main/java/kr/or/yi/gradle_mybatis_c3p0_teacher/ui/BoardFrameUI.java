package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.PageMaker;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.BoardList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class BoardFrameUI extends JFrame implements ActionListener {
	private BoardDao dao;
	private Criteria cri;
	private JLabel lblPage;
	private JPanel pBottom;
	private JPanel pPageBtns;
	private BoardList pList;
	private PageMaker pm;
	private JPanel pSearch;
	private JButton btnNew;
	private JComboBox<String> comboBox;
	private JTextField textField;
	private JPanel pCenter;
	private JPanel pNew;
	private JButton btnSearch;

	public BoardFrameUI(String title) {
		super(title);
		initDao();
		initComponents();
		listToPage(1);
		initPaging();
	}

	private void initPaging() {
		pPageBtns.removeAll();
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

	private void initComponents() {
		setBounds(10, 19, 400, 608);
		
		pCenter = new JPanel();
		getContentPane().add(pCenter, BorderLayout.CENTER);
		pCenter.setLayout(new BorderLayout(0, 0));
				
				pNew = new JPanel();
				FlowLayout fl_pNew = (FlowLayout) pNew.getLayout();
				fl_pNew.setAlignment(FlowLayout.RIGHT);
				pCenter.add(pNew, BorderLayout.NORTH);
				
				btnNew = new JButton("글쓰기");
				btnNew.addActionListener(this);
				pNew.add(btnNew);
		
				pList = new BoardList();
				pCenter.add(pList, BorderLayout.CENTER);

		pBottom = new JPanel();
		getContentPane().add(pBottom, BorderLayout.SOUTH);
		pBottom.setLayout(new GridLayout(0, 1, 0, 0));
		
		pSearch = new JPanel();
		FlowLayout fl_pSearch = (FlowLayout) pSearch.getLayout();
		fl_pSearch.setAlignment(FlowLayout.LEFT);
		pBottom.add(pSearch);
		
		comboBox = new JComboBox<>();
		pSearch.add(comboBox);
		
		textField = new JTextField();
		pSearch.add(textField);
		textField.setColumns(10);
		
		btnSearch = new JButton("검색");
		pSearch.add(btnSearch);

		lblPage = new JLabel();
		pBottom.add(lblPage);

		pPageBtns = new JPanel();
		pBottom.add(pPageBtns);
	}

	protected void initDao() {
		dao = new BoardDaoImpl();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNew) {
			actionPerformedBtnNew(e);
			return;
		}
		if (e.getActionCommand().equals("<<")) {
			listToPage(1);
			initPaging();
		}else if (e.getActionCommand().equals(">>")) {
			listToPage(pm.getTotalCount()/pm.getCri().getPerPageNum());
			initPaging();
		}else if (e.getActionCommand().equals("<")) {
			listToPage(pm.getStartPage()-10);
			initPaging();
		}else if (e.getActionCommand().equals(">")) {
			listToPage(pm.getStartPage()+10);
			initPaging();
		}else {
			listToPage(Integer.parseInt(e.getActionCommand()));
			selectedPageColor((JButton) e.getSource());
		}
	}

	private void selectedPageColor(JButton curBtn) {
		curBtn.setForeground(Color.RED);
		for(Component c : pPageBtns.getComponents()) {
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

		pList.setItemList(dao.getListCriteria(cri));
		pList.reloadData();
		
		lblPage.setText(pm.toString());
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

					BoardFrameUI frame = new BoardFrameUI("게시판");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	protected void actionPerformedBtnNew(ActionEvent e) {
		BoardInputUI frame = new BoardInputUI();
		frame.setVisible(true);
	}
}
