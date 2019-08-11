package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.BoardUI;

@SuppressWarnings("serial")
public class PanelBoard extends AbstractPanel<Board> implements ActionListener {
	private JTextField tfTitle;
	private JTextField tfWriter;
	private JTextArea taContent;
	private JButton btnWrite;
	private BoardDao dao;
	private BoardUI boardUI;
	private Board board;
	private JButton btnUpdate;
	
	public PanelBoard(String title) {
		super(title);
	}

	@Override
	protected void initComponents(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel pBoard = new JPanel();
		add(pBoard, BorderLayout.NORTH);
		pBoard.setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblTitle = new JLabel("  제목");
		pBoard.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);

		tfTitle = new JTextField();
		pBoard.add(tfTitle);
		tfTitle.setColumns(10);

		JLabel lblWriter = new JLabel("  작성자");
		pBoard.add(lblWriter);
		lblWriter.setHorizontalAlignment(SwingConstants.RIGHT);

		tfWriter = new JTextField();
		pBoard.add(tfWriter);
		tfWriter.setColumns(10);

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

		JPanel pBtns = new JPanel();
		add(pBtns, BorderLayout.SOUTH);

		btnWrite = new JButton("작성");
		btnWrite.addActionListener(this);
		pBtns.add(btnWrite);
		
		btnUpdate = new JButton("수정하기");
		btnUpdate.addActionListener(this);
		btnUpdate.setVisible(false);
		pBtns.add(btnUpdate);

		JButton btnCancel = new JButton("취소");
		pBtns.add(btnCancel);
	}

	public void visibleBtnUpdate() {
		btnWrite.setVisible(false);
		btnUpdate.setVisible(true);
	}
	
	public void setItem(Board board) {
		this.board = board;
		tfTitle.setText(board.getTitle());
		tfWriter.setText(board.getWriter());
		taContent.setText(board.getContent());
		tfWriter.setEditable(false);
	}

	public Board getItem() {
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		String writer = tfWriter.getText().trim();
		
		if (board==null) {	//추가
			return new Board(title, content, writer);
		}else {				//수정
			board.setTitle(title);
			board.setContent(content);
			board.setWriter(writer);
			return board;
		}
	}

	public void clearComponent() {
		tfTitle.setText("");
		tfWriter.setText("");
		taContent.setText("");
	}

	public void setEditable(boolean isEditable) {
		tfTitle.setEditable(isEditable);
		taContent.setEditable(isEditable);
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
		if (e.getSource() == btnUpdate) {
			setEditable(true);
			btnWrite.setVisible(true);
			btnUpdate.setVisible(false);
		}
		if (e.getSource() == btnWrite) {
			if (e.getActionCommand().equals("작성")) {
				actionPerformedBtnWrite(e);
			}else {
				actionPerformedBtnUpdate(e);
			}
		}
	}

	private void actionPerformedBtnUpdate(ActionEvent e) {
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		String writer = tfWriter.getText().trim();
		
		board.setTitle(title);
		board.setContent(content);
		board.setWriter(writer);
		
		int res = dao.updateBoard(board);
		if (res == 1) {
			JOptionPane.showMessageDialog(null, "수정하였습니다");
			clearComponent();
		}
		boardUI.reloadList();
		boardUI.changeUI();		
		boardUI.setBtnNewButtonText();
	}

	public JButton getBtnWrite() {
		return btnWrite;
	}

	protected void actionPerformedBtnWrite(ActionEvent e) {
		if (dao == null) {
			JOptionPane.showMessageDialog(null, "dao null");
		}
		int res = dao.insertBoard(getItem());
		if (res == 1) {
			JOptionPane.showMessageDialog(null, "추가하였습니다");
			clearComponent();
		}
		boardUI.reloadList();
		boardUI.changeUI();
	}
}
