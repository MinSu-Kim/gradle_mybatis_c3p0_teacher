package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.PageMaker;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;
import kr.or.yi.gradle_mybatis_c3p0_teacher.service.ReplyUIService;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.ReplyAddDlg;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.ReplyAddDlg.ReplyResponse;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelReply;

@SuppressWarnings("serial")
public class ReplyList extends JPanel implements ActionListener{
	private List<Reply> replyList;
	private JPanel pPageBtns;
	private JPanel pReplies;
	private Complete returnComplete;
	private ReplyUIService replyService;
	private Criteria cri;
	private Board board;
	private int totalCnt;
	private PageMaker pm;
	
	private JLabel lblPage;
	private JPanel pBottom;
	private JPanel panel;
	private JButton btnReplyAdd;
	
	
	public interface Complete {
		void isComplete();
	}
	
	public ReplyList() {
		setBorder(new TitledBorder(null, "댓글 목록", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		replyService = ReplyUIService.getInstance();
		
		initComponents();
	}

	private void initComponents() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pReplies = new JPanel();
		add(pReplies);
		pReplies.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblPage = new JLabel("");
		lblPage.setVisible(false);
		add(lblPage);
		
		pBottom = new JPanel();
		add(pBottom);
		pBottom.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pBottom.add(panel);
		
		btnReplyAdd = new JButton("댓글 등록");
		btnReplyAdd.addActionListener(this);
		panel.add(btnReplyAdd);
		
		pPageBtns = new JPanel();
		pBottom.add(pPageBtns);
	}

	public void setBoard(Board board) {
		this.board = board;
		reloadPaging(1);
	}

	public void setComplete(Complete returnComplete) {
		this.returnComplete = returnComplete;
	}
	
	private void reloadPaging(int startNum) {
		listToPage(startNum);
		initPaging();//페이지 버튼 생성, 리스너 추가
	}
	
	private void listToPage(int startPage) {
		if (cri==null) {
			cri = new Criteria();
		}
		
		cri.setPage(startPage); // 10page
		cri.setPerPageNum(5);// 1page당 20개

		totalCnt = replyService.count((int)board.getBno());
		
		if (pm==null) {
			pm = new PageMaker();
		}
		pm.setCri(cri);
		pm.setTotalCount(totalCnt);

		lblPage.setText(pm.toString());
		
		reloadList();
	}

	private void reloadList() {
		replyList = replyService.listPage((int)board.getBno(), cri);
		new LoadReplyList().execute();
	}
	
	private class LoadReplyList extends SwingWorker<Void, PanelReply>{

		@Override
		protected Void doInBackground() throws Exception {
			pReplies.removeAll();
			for (Reply r : replyList) {
				PanelReply pr = new PanelReply();
				pr.setReply(r);
				pr.setReplyService(replyService);
				pr.setParent(ReplyList.this);
				pReplies.add(pr);
			}
			return null;
		}
		
		protected void done() {
			returnComplete.isComplete();
		};
		
	};
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReplyAdd) {
			actionPerformedBtnReplyAdd(e);
			return;
		}

		if (e.getActionCommand().equals("<<")) {
			reloadPaging(1);
			return;
		}
		if (e.getActionCommand().equals(">>")) {
			reloadPaging(pm.getTotalCount() / pm.getCri().getPerPageNum());
			return;
		}
		if (e.getActionCommand().equals("<")) {
			reloadPaging(pm.getStartPage() - 10);
			return;
		}
		if (e.getActionCommand().equals(">")) {
			reloadPaging(pm.getStartPage() + 10);
			return;
		}

		listToPage(Integer.parseInt(e.getActionCommand()));
		selectedPageColor((JButton) e.getSource());
	}
	
	private void selectedPageColor(JButton curBtn) {
		curBtn.setForeground(Color.RED);
		for (Component c : pPageBtns.getComponents()) {
			if (!c.equals(curBtn))
				c.setForeground(Color.BLACK);
		}
	}
	
	private void initPaging() {
		pPageBtns.removeAll();
		repaint();
		revalidate();

		if (pm.isPrev()) {
			JButton firstBtn = new JButton("<<");
			firstBtn.addActionListener(this);
			pPageBtns.add(firstBtn);
			JButton btn = new JButton("<");
			btn.addActionListener(this);
			pPageBtns.add(btn);
		}
		for (int i = pm.getStartPage(); i < pm.getEndPage() + 1; i++) {
			JButton btn = new JButton(i + "");
			btn.addActionListener(this);
			pPageBtns.add(btn);
		}
		if (pm.isNext()) {
			JButton btn = new JButton(">");
			btn.addActionListener(this);
			pPageBtns.add(btn);
			JButton lastBtn = new JButton(">>");
			lastBtn.addActionListener(this);
			pPageBtns.add(lastBtn);
		}
	}

	protected void actionPerformedBtnReplyAdd(ActionEvent e) {
		ReplyAddDlg dlg = new ReplyAddDlg();
		dlg.showDlg(ReplyAddDlg.REPLY_ADD);
		dlg.setReplyService(replyService);
		dlg.setBno((int)board.getBno());
		dlg.setReplyListener(replyListener);
		dlg.setVisible(true);
	}
	
	ReplyAddDlg.ReplyResponse replyListener = new ReplyResponse() {
		@Override
		public void replyComplete() {
			reloadPaging(1);	
			returnComplete.isComplete();
		}
	};

	public ReplyAddDlg.ReplyResponse getReplyListener() {
		return replyListener;
	}
	
	
}
