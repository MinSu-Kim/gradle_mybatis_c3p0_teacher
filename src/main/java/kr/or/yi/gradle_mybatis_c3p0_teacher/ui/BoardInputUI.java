package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelBoard;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class BoardInputUI extends JFrame {

	private JPanel contentPane;

	public BoardInputUI() {
		initComponents();
	}
	private void initComponents() {
		setTitle("게시글 작성");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		PanelBoard pCenter = new PanelBoard("게시글 쓰기");
		contentPane.add(pCenter, BorderLayout.CENTER);
		
		JPanel pBottom = new JPanel();
		contentPane.add(pBottom, BorderLayout.SOUTH);
		
		JButton btnNew = new JButton("글쓰기");
		pBottom.add(btnNew);
		
		JButton btnCancel = new JButton("취소");
		pBottom.add(btnCancel);
	}

}
