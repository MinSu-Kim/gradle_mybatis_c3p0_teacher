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

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.PostDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.PostDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.PageMaker;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.PostList;

@SuppressWarnings("serial")
public class PostFrameUI extends JFrame implements ActionListener {
	private PostDao dao;
	private Criteria cri;
	private JLabel lblPage;
	private JPanel pPage;
	private JPanel pBtns;
	private PostList pList;
	private PageMaker pm;

	public PostFrameUI(String title) {
		super(title);
		initDao();
		initComponents();
		listToPage(1);
		initPaging();
	}

	private void initPaging() {
		pBtns.removeAll();
		revalidate();
		
		if (pm.isPrev()) {
			JButton firstBtn = new JButton("<<");
			firstBtn.addActionListener(this);
			pBtns.add(firstBtn);
			JButton btn = new JButton("<");
			btn.addActionListener(this);
			pBtns.add(btn);
		}
		for (int i = pm.getStartPage(); i < pm.getEndPage() + 1; i++) {
			JButton btn = new JButton(i + "");
			btn.addActionListener(this);
			pBtns.add(btn);
		}
		if (pm.isNext()) {
			JButton btn = new JButton(">");
			btn.addActionListener(this);
			pBtns.add(btn);
			JButton lastBtn = new JButton(">>");
			lastBtn.addActionListener(this);
			pBtns.add(lastBtn);
		}
	}

	private void initComponents() {
		setBounds(10, 19, 400, 700);

		pList = new PostList();
		getContentPane().add(pList, BorderLayout.CENTER);

		pPage = new JPanel();
		getContentPane().add(pPage, BorderLayout.SOUTH);
		pPage.setLayout(new GridLayout(0, 1, 0, 0));

		lblPage = new JLabel();
		pPage.add(lblPage);

		pBtns = new JPanel();
		pPage.add(pBtns);
	}

	protected void initDao() {
		dao = new PostDaoImpl();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
		for(Component c : pBtns.getComponents()) {
			if (!c.equals(curBtn))
				c.setForeground(Color.BLACK);
		}
	}

	private void listToPage(int startPage) {
		cri = new Criteria();
		cri.setPage(startPage); // 10page
		cri.setPerPageNum(20);// 1page당 20개

		int totalCnt = dao.countPaging(cri);
		pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCnt);

		pList.setItemList(dao.listCriteria(cri));
		pList.reloadData();
		
		lblPage.setText(pm.toString());
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

					PostFrameUI frame = new PostFrameUI("페이징 테스트");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
