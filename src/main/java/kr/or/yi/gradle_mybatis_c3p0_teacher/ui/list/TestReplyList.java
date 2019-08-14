package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.ReplyDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.ReplyDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.ReplyList.Complete;

public class TestReplyList extends JFrame {

	private JPanel contentPane;

	private ReplyList pReplyList;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestReplyList frame = new TestReplyList();
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
		ReplyDao dao = new ReplyDaoImpl();
		List<Reply> list = dao.listReply(4121);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pReplyList = new ReplyList();
		pReplyList.setComplete(returnComplete);
		pReplyList.setReplyList(list);
		pReplyList.loadReplies();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(pReplyList);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	Complete returnComplete = new Complete() {
		@Override
		public void isComplete() {
			reload();
		}
	}; 
	
	public void reload() {
		repaint();
		revalidate();
	}


}
