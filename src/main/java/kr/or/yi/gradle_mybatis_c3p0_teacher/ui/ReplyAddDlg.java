package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;
import kr.or.yi.gradle_mybatis_c3p0_teacher.service.ReplyUIService;

@SuppressWarnings("serial")
public class ReplyAddDlg extends JDialog implements ActionListener {

	private final JPanel contentPanel;

	public final static String REPLY_ADD = "댓글 추가";
	public final static String REPLY_UPDATE = "댓글 수정";

	private JPanel pBtns;

	private JPanel pCenter;
	private JLabel lblReplyer;
	private JTextField tfReplyer;
	private JLabel lblNewLabel_1;
	private JTextField tfText;
	private JButton cancelButton;
	private JButton okButton;

	private Reply reply;
	private ReplyUIService replyService;

	private int bno;
	private ReplyResponse replyListener;

	public ReplyAddDlg() {
		setBounds(100, 100, 450, 209);
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);

		pCenter = new JPanel();
		contentPanel.add(pCenter, BorderLayout.CENTER);
		pCenter.setLayout(new GridLayout(0, 2, 10, 0));

		lblReplyer = new JLabel("Replyer");
		pCenter.add(lblReplyer);
		lblReplyer.setHorizontalAlignment(SwingConstants.RIGHT);

		tfReplyer = new JTextField();
		pCenter.add(tfReplyer);
		tfReplyer.setColumns(10);

		lblNewLabel_1 = new JLabel("Reply Text");
		pCenter.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		tfText = new JTextField();
		pCenter.add(tfText);
		tfText.setColumns(10);

		pBtns = new JPanel();
		pBtns.setLayout(new FlowLayout(FlowLayout.RIGHT));
		contentPanel.add(pBtns, BorderLayout.SOUTH);

		okButton = new JButton(REPLY_ADD);
		okButton.addActionListener(this);
		pBtns.add(okButton);

		cancelButton = new JButton("취소");
		cancelButton.addActionListener(this);
		pBtns.add(cancelButton);
	}

	public void setReplyService(ReplyUIService replyService) {
		this.replyService = replyService;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public void setReplyListener(ReplyResponse replyListener) {
		this.replyListener = replyListener;
	}

	public void setpUpdate(Reply reply) {
		this.reply = reply;
		tfText.setText(reply.getReplyText());
		tfReplyer.setText(reply.getReplyer());
		tfReplyer.setEditable(false);
	}

	public void showDlg(String mode) {
		okButton.setText(mode);
		setTitle(mode);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			if (e.getActionCommand().equals(REPLY_ADD)) {
				actionPerformedOkButton(e);
			} else {
				actionPerformedUpdateButton(e);
			}
		}
		if (e.getSource() == cancelButton) {
			actionPerformedCancelButton(e);
		}
	}

	protected void actionPerformedCancelButton(ActionEvent e) {
		dispose();
	}

	protected void actionPerformedUpdateButton(ActionEvent e) {
		reply.setReplyer(tfReplyer.getText().trim());
		reply.setReplyText(tfText.getText().trim());
		replyService.updateReply(reply);
		JOptionPane.showMessageDialog(null, "수정 되었습니다");
		replyListener.replyComplete();
		dispose();
	}

	protected void actionPerformedOkButton(ActionEvent e) {
		Reply reply = new Reply(bno, tfText.getText().trim(), tfReplyer.getText().trim());
		replyService.addReply(reply);
		replyListener.replyComplete();
		dispose();
	}

	public interface ReplyResponse {
		void replyComplete();
	}
}
