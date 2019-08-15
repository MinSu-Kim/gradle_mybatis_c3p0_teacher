package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.ReplyList.Complete;

@SuppressWarnings("serial")
public class TestReplyList extends JFrame {

	private JPanel contentPane;

	private ReplyList pReplyList;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestReplyList frame = new TestReplyList();
//					frame.setUndecorated(true);
					frame.setVisible(true);
					frame.reload();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestReplyList() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pReplyList = new ReplyList();
		pReplyList.setBoard(new Board(8196));
		pReplyList.setComplete(returnComplete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(pReplyList);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	Complete returnComplete = new Complete() {
		@Override
		public void isComplete(boolean noReply) {
			if (noReply) {
				JOptionPane.showMessageDialog(null, "댓글이 존재하지 않음");
			}
			reload();
		}
	}; 
	
	public void reload() {
		JOptionPane.showMessageDialog(null, "aaaaaa");
		repaint();
		revalidate();
	}


}
