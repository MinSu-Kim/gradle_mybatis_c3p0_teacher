package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelTitle;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.TitleList;

@SuppressWarnings("serial")
public class TitleFrameUI extends JFrame {
	
	public TitleFrameUI() {
		initComponents();
	}

	private void initComponents() {
		setTitle("직책관리");
		setBounds(200, 100, 450, 450);
		JPanel pMain = new JPanel();
		getContentPane().add(pMain, BorderLayout.CENTER);
		pMain.setLayout(new BorderLayout(0, 0));

		PanelTitle pContent = new PanelTitle();
		pMain.add(pContent, BorderLayout.CENTER);

		JPanel pBtns = new JPanel();
		pMain.add(pBtns, BorderLayout.SOUTH);

		JButton btnAdd = new JButton("추가");
		pBtns.add(btnAdd);

		JButton btnCancel = new JButton("취소");
		pBtns.add(btnCancel);

		TitleList pList = new TitleList("직책 목록");
		
		pList.setItemList(new ArrayList<Title>());
		pList.reloadData();
		
		getContentPane().add(pList, BorderLayout.SOUTH);
	}

}
