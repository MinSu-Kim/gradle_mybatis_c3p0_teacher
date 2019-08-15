package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
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
	private JButton btnNew;
	private JPanel pContent;

	final static String BOARD_LIST = "목록보기";
	final static String BOARD_WRITE = "글쓰기";

	private CardLayout cardLayout;
	private PanelBoardList pList;
	private JPopupMenu popupMenu;
	private JMenuItem mntmRead;
	private PanelBoard pBoard;
	private JFrame writeFrame;

	public BoardUI() {
		dao = new BoardDaoImpl();
		initComponents();
	}

	private void initComponents() {
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 625);
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
		pContent.add(pList, BOARD_LIST);
		
		popupMenu = new JPopupMenu();

		mntmRead = new JMenuItem("보기");
		mntmRead.addActionListener(this);
		popupMenu.add(mntmRead);
		
		pList.setPopupMenu(popupMenu);
		
		pBoard = new PanelBoard(BOARD_WRITE);
		pContent.add(pBoard, BOARD_WRITE);
		
		pBoard.setBoardUI(this);
		pBoard.setDao(dao);
	
		JPanel pBtn = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pBtn.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(pBtn, BorderLayout.NORTH);

		btnNew = new JButton(BOARD_WRITE);
		btnNew.addActionListener(this);
		pBtn.add(btnNew);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmRead) {
			actionPerformedMntmRead(e);
		}
		if (e.getSource() == btnNew) {
			actionPerformedBtnNewButton(e);
		}
		
	}

	protected void actionPerformedBtnNewButton(ActionEvent e) {
		if (writeFrame==null) {
			writeFrame = new JFrame("글쓰기");
			writeFrame.setBounds(500, 100, 400, 600);
			PanelBoard newBoardPane = new PanelBoard("글쓰기");
			newBoardPane.setFrame(writeFrame);
			newBoardPane.setBoardUI(this);
			newBoardPane.setDao(dao);
			newBoardPane.setWriteMode();
			writeFrame.getContentPane().add(newBoardPane, BorderLayout.CENTER);
		}
		writeFrame.setVisible(true);
	}
	
	public void changeListUI() {
		cardLayout.show(pContent, BOARD_LIST);
		btnNew.setVisible(true);
	}
	
	public void setBtnNewButtonText() {
		btnNew.setText(BOARD_WRITE);
	}
	
	public void reloadList() {
		pList.reloadList();
	}
	
	protected void actionPerformedMntmRead(ActionEvent e) {
		Board board = pList.getSelectedBoard();
		cardLayout.show(pContent, BOARD_WRITE);
		pBoard.setItem(board);
		pBoard.setReadMode();
		pBoard.setEditable(false);
		btnNew.setVisible(false);
	}
	
}
