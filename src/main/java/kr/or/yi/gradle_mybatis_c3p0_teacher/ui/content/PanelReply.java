package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;

@SuppressWarnings("serial")
public class PanelReply extends JPanel implements ActionListener {
	
	public static int PANEL_REPLY_WIDTH = 400;
	public static int PANEL_REPLY_HEHIGHT = 100;
	
	private JTextField tfReplyText;
	private Reply reply;
	private JLabel lblNoReplyer;
	private JLabel lblRegDate;
	private JButton btnModify;

	public PanelReply() {

		initComponents();
	}

	private void initComponents() {
		setPreferredSize(new Dimension(PANEL_REPLY_WIDTH, PANEL_REPLY_HEHIGHT));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 10, 0, 10));
		add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel pReply = new JPanel();
		panel.add(pReply);
		pReply.setLayout(new GridLayout(1, 0, 0, 0));

		lblNoReplyer = new JLabel("New label");
		lblNoReplyer.setVerticalAlignment(SwingConstants.TOP);
		pReply.add(lblNoReplyer);

		lblRegDate = new JLabel("New label");
		lblRegDate.setVerticalAlignment(SwingConstants.TOP);
		lblRegDate.setHorizontalAlignment(SwingConstants.RIGHT);
		pReply.add(lblRegDate);

		tfReplyText = new JTextField();
		panel.add(tfReplyText);
		tfReplyText.setColumns(10);

		JPanel pBtn = new JPanel();
		panel.add(pBtn);
		pBtn.setLayout(new BoxLayout(pBtn, BoxLayout.X_AXIS));

		btnModify = new JButton("수정");
		btnModify.addActionListener(this);
		pBtn.add(btnModify);

		JPanel pImg = new JPanel();
		pImg.setBorder(null);
		add(pImg, BorderLayout.WEST);
		pImg.setLayout(new BoxLayout(pImg, BoxLayout.Y_AXIS));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(createImageIcon("img/reply-icon.png"));
		pImg.add(lblIcon);
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
		lblNoReplyer.setText(String.format("%d - %s", reply.getRno(), reply.getReplyer()));
		lblRegDate.setText(String.format("%tF", reply.getRegDate()));
		tfReplyText.setText(reply.getReplyText());
		tfReplyText.setEditable(false);
	}

	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		ImageIcon icon = new ImageIcon(imgURL);
		if (imgURL != null) {
			return new ImageIcon(getScaledImage(icon.getImage(), 30, 30));
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnModify) {
			actionPerformedBtnModify(e);
		}
	}
	protected void actionPerformedBtnModify(ActionEvent e) {
		JOptionPane.showMessageDialog(null, reply);
	}
}
