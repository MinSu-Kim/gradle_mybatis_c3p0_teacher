package kr.or.yi.gradle_mybatis_c3p0_teacher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.BoardUI;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.DepartmentFrameUI;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.EmployeeFrameUI;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.TitleFrameUI;

@SuppressWarnings("serial")
public class ErpApplication extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JButton btnTitle;
	private JButton btnDepartment;
	private JButton btnEmp;
	private JButton btnBoard;
	private Map<String, JFrame> map;
	
	private static final String TITLE_STR = "직책관리";
	private static final String DEPT_STR = "부서관리";
	private static final String EMP_STR = "사원관리";
	private static final String BOARD_STR = "게시판보기";
	
	public ErpApplication() {
		createFrame();
		initComponents();
	}

	private void createFrame() {
		map = new HashMap<>();
		map.put(TITLE_STR, new TitleFrameUI(TITLE_STR));
		map.put(DEPT_STR, new DepartmentFrameUI());
		map.put(EMP_STR, new EmployeeFrameUI(EMP_STR));
		map.put(BOARD_STR,new BoardUI());
	}

	private void initComponents() {
		setTitle("ERP 관리프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 85);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 10, 0));

		btnTitle = new JButton(TITLE_STR);
		btnTitle.addActionListener(this);
		contentPane.add(btnTitle);

		btnDepartment = new JButton(DEPT_STR);
		btnDepartment.addActionListener(this);
		contentPane.add(btnDepartment);

		btnEmp = new JButton(EMP_STR);
		btnEmp.addActionListener(this);
		contentPane.add(btnEmp);

		btnBoard = new JButton(BOARD_STR);
		btnBoard.addActionListener(this);
		contentPane.add(btnBoard);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBoard) {
			actionPerformedBtnBoard(e);
		}
		if (e.getSource() == btnEmp) {
			actionPerformedBtnEmp(e);
		}
		if (e.getSource() == btnDepartment) {
			actionPerformedBtnDepartment(e);
		}
		if (e.getSource() == btnTitle) {
			actionPerformedBtnTitle(e);
		}

	}

	protected void actionPerformedBtnTitle(ActionEvent e) {
		map.get(TITLE_STR).setVisible(true);
	}

	protected void actionPerformedBtnDepartment(ActionEvent e) {
		map.get(DEPT_STR).setVisible(true);
	}

	protected void actionPerformedBtnEmp(ActionEvent e) {
		map.get(EMP_STR).setVisible(true);
	}

	protected void actionPerformedBtnBoard(ActionEvent e) {
		((BoardUI)map.get(BOARD_STR)).changeListUI();
		map.get(BOARD_STR).setVisible(true);
	}
}
