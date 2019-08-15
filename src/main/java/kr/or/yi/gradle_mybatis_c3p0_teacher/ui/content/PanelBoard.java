package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.ibatis.exceptions.PersistenceException;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.BoardUI;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.ReplyList;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.ReplyList.Complete;

@SuppressWarnings("serial")
public class PanelBoard extends AbstractPanel<Board> implements ActionListener {
	private JTextField tfTitle;
	private JTextField tfWriter;
	private JTextArea taContent;
	private BoardDao dao;
	private BoardUI boardUI;
	private Board board;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnList;
	private JButton btnWrite;
	private JFrame writeFrame;
	private JButton btnCancel;
	private JButton btnReplylist;
	private ReplyList pReply;
	private JFrame replyUI;
	
	private boolean noReply;
	
	public PanelBoard(String title) {
		super(title);
	}

	@Override
	protected void initComponents(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel pBoard = new JPanel();
		pBoard.setBorder(new EmptyBorder(0, 10, 0, 10));
		add(pBoard, BorderLayout.NORTH);
		pBoard.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblTitle = new JLabel("제목");
		pBoard.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);

		tfTitle = new JTextField();
		pBoard.add(tfTitle);
		tfTitle.setColumns(10);

		JPanel pContent = new JPanel();
		pContent.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(pContent);
		pContent.setLayout(new BorderLayout(0, 0));

		JLabel lblContent = new JLabel("내용");
		pContent.add(lblContent, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		pContent.add(scrollPane, BorderLayout.CENTER);

		taContent = new JTextArea();
		scrollPane.setViewportView(taContent);

		JPanel panel = new JPanel();
		pContent.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblWriter = new JLabel("작성자");
		panel.add(lblWriter);
		lblWriter.setHorizontalAlignment(SwingConstants.LEFT);

		tfWriter = new JTextField();
		panel.add(tfWriter);
		tfWriter.setColumns(10);

		JPanel pSouth = new JPanel();
		pSouth.setBorder(new EmptyBorder(0, 10, 0, 19));
		add(pSouth, BorderLayout.SOUTH);
		pSouth.setLayout(new BorderLayout(0, 0));

		JPanel pBtns = new JPanel();
		pSouth.add(pBtns, BorderLayout.NORTH);

		btnReplylist = new JButton("댓글보기");
		pBtns.add(btnReplylist);

		btnWrite = new JButton("작성");
		pBtns.add(btnWrite);

		btnUpdate = new JButton("수정");
		pBtns.add(btnUpdate);

		btnCancel = new JButton("취소");
		pBtns.add(btnCancel);

		btnDelete = new JButton("삭제");
		pBtns.add(btnDelete);

		btnList = new JButton("목록");
		pBtns.add(btnList);

		btnList.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnUpdate.setVisible(false);
		btnWrite.addActionListener(this);
		btnReplylist.addActionListener(this);

		pReply = new ReplyList();
	}

	Complete returnComplete = new Complete() {
		@Override
		public void isComplete(boolean noReply) {
			PanelBoard.this.noReply = noReply;
			pReply.repaint();
			pReply.revalidate();
		}
	};


	public void setEditable(boolean isEditable) {
		tfTitle.setEditable(isEditable);
		taContent.setEditable(isEditable);
	}

	public void setItem(Board board) {
		this.board = board;
		pReply.setComplete(returnComplete);
		pReply.setBoard(board);

		tfTitle.setText(board.getTitle());
		tfWriter.setText(board.getWriter());
		taContent.setText(board.getContent());
		tfWriter.setEditable(false);
	}

	public Board getItem() {
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		String writer = tfWriter.getText().trim();

		if (title.equals("") || content.equals("") || writer.equals("")) {
			throw new RuntimeException("빈칸이 존재합니다. 확인하세요");
		}

		if (board == null) { // 추가
			return new Board(title, content, writer);
		} else { // 수정
			board.setTitle(title);
			board.setContent(content);
			board.setWriter(writer);
			return board;
		}
	}

	public void setDao(BoardDao dao) {
		this.dao = dao;
	}

	public void setBoardUI(BoardUI boardUI) {
		this.boardUI = boardUI;
	}

	@Override
	public void clearComponent(int nextNo) {
		throw new UnsupportedOperationException();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReplylist) {
			actionPerformedBtnReplylist(e);
		}
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel();
		}
		if (e.getSource() == btnList) {
			actionPerformedBtnList();
		}

		if (e.getSource() == btnWrite) {
			actionPerformedBtnWrite(e);
		}

		if (e.getSource() == btnDelete) {
			actionPerformedBtnDelete(e);
		}

		if (e.getSource() == btnUpdate) {
			if (e.getActionCommand().equals("수정")) {
				actionPerformedChangeUpdateMode();
			} else { // 저장 (수정)
				actionPerformedUpdate();
			}
		}
	}

	private void actionPerformedUpdate() {
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		board.setTitle(title);
		board.setContent(content);
		JOptionPane.showMessageDialog(null, "board" + board);

		int res = dao.updateBoard(board);
		if (res == 1) {
			JOptionPane.showMessageDialog(null, "수정하였습니다");
			clearComponent();
		}
		boardUI.reloadList();
		boardUI.changeListUI();
		boardUI.setBtnNewButtonText();
		board = null;
		btnUpdate.setText("수정");
		clearComponent();
		
		if (replyUI.isVisible()) {
			viewReplyUI(false);
		}
	}

	protected void actionPerformedBtnWrite(ActionEvent e) {

		try {
			if (dao == null)
				throw new RuntimeException("dao null");
			int res = dao.insertBoard(getItem());
			if (res == 1) {
				JOptionPane.showMessageDialog(null, "추가하였습니다");
				clearComponent();
				boardUI.reloadList();
				writeFrame.dispose();
				clearComponent();
			}
		} catch (RuntimeException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}

	}

	protected void actionPerformedBtnDelete(ActionEvent e) {
		try {
			int res = dao.deleteBoard(board.getBno());
			if (res == 1) {
				JOptionPane.showMessageDialog(null, "삭제하였습니다");
				clearComponent();
			}
			boardUI.reloadList();
			boardUI.changeListUI();
			clearComponent();
		}catch(PersistenceException e1) {
			JOptionPane.showMessageDialog(null, "댓글이 존재하여 삭제가 되지않습니다.");
		}
	}

	public void setWriteMode() {
		btnWrite.setVisible(true);
		btnList.setVisible(true);
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(false);
	}

	public void setReadMode() {
		btnWrite.setVisible(false);
		btnList.setVisible(true);
		btnUpdate.setVisible(true);
		btnUpdate.setText("수정");
		btnDelete.setVisible(true);
		btnCancel.setVisible(false);
		
		if (replyUI==null || !replyUI.isVisible()) {
			btnReplylist.setText("댓글보기");
		}
	}

	private void actionPerformedChangeUpdateMode() {
		setEditable(true);
		btnUpdate.setText("저장");
		btnWrite.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(true);
		btnList.setVisible(false);
	}

	public void setUpdateMode() {
		setEditable(true);
		btnUpdate.setText("저장");
		btnWrite.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(true);
		btnList.setVisible(false);
	}

	public void setFrame(JFrame writeFrame) {
		this.writeFrame = writeFrame;
	}

	public void clearComponent() {
		tfTitle.setText("");
		tfWriter.setText("");
		taContent.setText("");
		board = null;
	}

	protected void actionPerformedBtnCancel() {
		boardUI.changeListUI();
		clearComponent();
		if (writeFrame != null) {
			writeFrame.dispose();
		}
		
		if (replyUI != null && replyUI.isVisible()) {
			viewReplyUI(false);
		}
	}

	private void actionPerformedBtnList() {
		actionPerformedBtnCancel();
		if (writeFrame != null) {
			writeFrame.dispose();
			setWriteMode();
		}
		
		viewReplyUI(false);
	}

	private void viewReplyUI(boolean isVisible) {
		if (replyUI == null) {
			replyUI = new JFrame();
			Rectangle rv = new Rectangle();
			boardUI.getBounds(rv);
			boardUI.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					replyUI.dispose();
					btnReplylist.setText("댓글보기");
				}
			});
			boardUI.addWindowStateListener(new WindowStateListener() {
				@Override
				public void windowStateChanged(WindowEvent e) {
					replyUI.setState(e.getNewState());
				}
			});
			boardUI.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals("windowMoved")) {
						Rectangle rv = new Rectangle();
						boardUI.getBounds(rv);
						int x = (int) (rv.getX() + rv.getWidth());
						replyUI.setBounds(x, (int) rv.getY(), 400, (int) rv.getHeight());
					}
				}
			});
			int x = (int) (rv.getX() + rv.getWidth());
			replyUI.setBounds(x, (int) rv.getY(), 400, (int) rv.getHeight());
			replyUI.setUndecorated(true);
			replyUI.getContentPane().add(pReply);
		}
		replyUI.setVisible(isVisible);
	}

	protected void actionPerformedBtnReplylist(ActionEvent e) {
		System.out.println("noReply " + noReply);
		if (noReply) {
			JOptionPane.showMessageDialog(null, "댓글이 존재하지 않습니다");
			btnReplylist.setText("댓글보기");
			return;
		}
		
		if (btnReplylist.getText().equals("댓글보기")) {
			btnReplylist.setText("댓글 닫기");
			viewReplyUI(true);
		} else {
			btnReplylist.setText("댓글보기");
			viewReplyUI(false);
		}
	}

}
