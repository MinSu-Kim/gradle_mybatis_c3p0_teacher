package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;

@SuppressWarnings("serial")
public class PanelTitle extends JPanel {
	private JTextField tfTitleNo;
	private JTextField tfTitleName;

	public PanelTitle() {
		initComponents();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "직책 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblTitleNo = new JLabel("직책 번호");
		lblTitleNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitleNo);

		tfTitleNo = new JTextField();
		add(tfTitleNo);
		tfTitleNo.setColumns(10);

		JLabel lblTitleName = new JLabel("직책명");
		lblTitleName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitleName);

		tfTitleName = new JTextField();
		tfTitleName.setColumns(10);
		add(tfTitleName);
	}

	public void setItem(Title Title) {
		tfTitleNo.setText(String.format("T%03d", Title.getTitleNo()));
		tfTitleName.setText(Title.getTitleName());
	}

	public Title getItem() {
		int titleNo = Integer.parseInt(tfTitleNo.getText().trim().substring(1));
		String titleName = tfTitleName.getText().trim();
		return new Title(titleNo, titleName);
	}

	public void clearComponent(int nextNo) {
		tfTitleNo.setText(String.format("T%03d", nextNo));
		tfTitleName.setText("");
		tfTitleNo.setEditable(false);
	}

	public JTextField getTfNo() {
		return tfTitleNo;
	}

	public void setComponentAllEditable(boolean isEditable) {
		tfTitleNo.setEditable(isEditable);
		tfTitleName.setEditable(isEditable);
	}

}
