package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelBoard;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelBoardList;

@SuppressWarnings("serial")
public class BoardUI extends JFrame implements ActionListener {
	private BoardDao dao;
	private JPanel contentPane;
	private JButton btnNewButton;
	private JPanel pContent;

	final static String BOARD_LIST = "목록보기";
	final static String BOARD_WRITE = "글쓰기";

	private CardLayout cardLayout;
	private PanelBoardList pList;
	private JPopupMenu popupMenu;
	private JMenuItem mntmRead;
	private PanelBoard pBoard;

	public BoardUI() {
		dao = new BoardDaoImpl();
		initComponents();
	}

	private void initComponents() {
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 665);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		pContent = new JPanel();
		contentPane.add(pContent, BorderLayout.CENTER);
		cardLayout = new CardLayout();
		pContent.setLayout(cardLayout);
		
		pList = new PanelBoardList();
		pList.setDao(dao);
		pList.setBoardUI(this);
		pContent.add(pList, BOARD_LIST);
		
		popupMenu = new JPopupMenu();

		mntmRead = new JMenuItem("보기");
		mntmRead.addActionListener(this);
		popupMenu.add(mntmRead);
		
		pList.setPopupMenu(popupMenu);
		
		pBoard = new PanelBoard(BOARD_WRITE);
		pBoard.setBoardUI(this);
		pBoard.setDao(dao);
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
		if (e.getSource() == mntmRead) {
			actionPerformedMntmRead(e);
		}
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
		pBoard.clearComponent();
	}
	
	public void changeUI() {
		cardLayout.show(pContent, BOARD_LIST);
	}
	
	public void setBtnNewButtonText() {
		btnNewButton.setText(BOARD_WRITE);
	}
	
	public void reloadList() {
		pList.reloadList();
	}
	
	protected void actionPerformedMntmRead(ActionEvent e) {
		Board board = pList.getSelectedBoard();
		cardLayout.show(pContent, BOARD_WRITE);
		pBoard.setItem(board);
		btnNewButton.setText(BOARD_LIST);
		pBoard.setEditable(false);
		pBoard.visibleBtnUpdate();
		pBoard.getBtnWrite().setText("수정");
	}
}
