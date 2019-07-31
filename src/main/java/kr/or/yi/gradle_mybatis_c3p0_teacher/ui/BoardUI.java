package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelBoard;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.EventQueue;

import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelBoardList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class BoardUI extends JFrame implements ActionListener {
	private BoardDao dao;
	private JPanel contentPane;
	private JButton btnNewButton;
	private JPanel pContent;

	final static String BOARD_LIST = "목록보기";
	final static String BOARD_WRITE = "글쓰기";

	private CardLayout cardLayout;

	public BoardUI() {
		dao = new BoardDaoImpl();
		initComponents();
	}

	private void initComponents() {
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		pContent = new JPanel();
		contentPane.add(pContent, BorderLayout.CENTER);
		cardLayout = new CardLayout();
		pContent.setLayout(cardLayout);
		
		PanelBoardList pList = new PanelBoardList();
		pList.setDao(dao);
		pContent.add(pList, BOARD_LIST);

		PanelBoard pBoard = new PanelBoard(BOARD_WRITE);
		pContent.add(pBoard, BOARD_WRITE);

		JPanel pBtn = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pBtn.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(pBtn, BorderLayout.NORTH);

		btnNewButton = new JButton(BOARD_WRITE);
		btnNewButton.addActionListener(this);
		pBtn.add(btnNewButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			actionPerformedBtnNewButton(e);
		}
	}

	protected void actionPerformedBtnNewButton(ActionEvent e) {
		cardLayout.show(pContent, e.getActionCommand());
		if (btnNewButton.getText().equals(BOARD_LIST)){
			btnNewButton.setText(BOARD_WRITE);
		}else {
			btnNewButton.setText(BOARD_LIST);
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

					BoardUI frame = new BoardUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
