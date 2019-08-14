package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelReply;

@SuppressWarnings("serial")
public class ReplyList extends JPanel {
	private List<Reply> replyList;
	
	private JPanel pReplies;
	private Complete returnComplete;
	
	public ReplyList() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout(0, 0));
		
		pReplies = new JPanel();
		pReplies.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(pReplies);
	}

	
	public void setComplete(Complete returnComplete) {
		this.returnComplete = returnComplete;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}


	public void loadReplies() {
		if (replyList == null) {
			JOptionPane.showMessageDialog(null, "replyList is NULL");
			return;
		}

		loadReplys.execute();
	}
	
	private SwingWorker<Void, PanelReply> loadReplys = new SwingWorker<Void, PanelReply>() {

		@Override
		protected Void doInBackground() throws Exception {
			for (Reply r : replyList) {
				PanelReply pr = new PanelReply();
				pr.setReply(r);
				publish(pr);
				pReplies.add(pr);
			}
			return null;
		}

		protected void done() {
			returnComplete.isComplete();
		};
		
	};
	
	public interface Complete {
		void isComplete();
	}
	
}
