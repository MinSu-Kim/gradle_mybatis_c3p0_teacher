package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;

@SuppressWarnings("serial")
public class PanelBoard extends AbstractPanel<Board> {
	private JTextField tfNo;
	private JTextField tfTitle;
	private JTextField tfWriter;
	private JTextArea taContent;

	public PanelBoard(String title) {
		super(title);
	}

	@Override
	protected void initComponents(String title) {
		setBorder(new TitledBorder(null, title + " 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel pBoard = new JPanel();
		add(pBoard, BorderLayout.NORTH);
		pBoard.setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblNo = new JLabel("  번호");
		pBoard.add(lblNo);
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);

		tfNo = new JTextField();
		pBoard.add(tfNo);
		tfNo.setColumns(10);

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
	}

	public void setItem(Board board) {
		tfNo.setText(board.getBno()+"");
		tfTitle.setText(board.getTitle());
		taContent.setText(board.getContent());
		tfNo.setEditable(false);
	}

	public Board getItem() {
		int bno = Integer.parseInt(tfNo.getText().trim());
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		String writer = tfWriter.getText().trim();
		Date regdate = new Date();
		Date updatedate = new Date();
		return new Board(bno, title, content, writer, regdate, updatedate);
	}

	public void clearComponent() {
		tfNo.setText("");
		tfTitle.setText("");
		taContent.setText("");
		tfNo.setEditable(false);
	}

	@Override
	public void clearComponent(int nextNo) {
		throw new UnsupportedOperationException();
	}

}
