package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.PostDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.PostDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.PostList;

@SuppressWarnings("serial")
public class PostFrameUI extends JFrame {
	private PostDao dao;
	
	public PostFrameUI(String title) {
		super(title);
		initDao();
		initComponents();
	}
	
	private void initComponents() {
		setBounds(10, 19, 400, 700);
		PostList pList = new PostList();
		pList.setItemList(dao.selectPostByAll());
		pList.reloadData();
		getContentPane().add(pList, BorderLayout.CENTER);
		
		JPanel pPage = new JPanel();
		getContentPane().add(pPage, BorderLayout.SOUTH);
	}
	
	protected  void initDao() {
		dao = new PostDaoImpl();
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
