package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.AbstractPanel;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.AbstractList;

@SuppressWarnings("serial")
public abstract class AbstractFrameUI<T> extends JFrame implements ActionListener {
	private JButton btnAdd;
	protected AbstractPanel<T> pContent;
	protected List<T> itemList;
	protected AbstractList<T> pList;
	private JButton btnCancel;

	private JPopupMenu popupMenu;
	private JMenuItem mntmUpdate;
	private JMenuItem mntmDelete;

	public AbstractFrameUI(String title) {
		initDao();
		initComponents(title);
	}

	protected abstract void initDao();

	private void initComponents(String title) {
		setTitle(title);
		setBounds(200, 100, 450, 450);
		JPanel pMain = new JPanel();
		getContentPane().add(pMain, BorderLayout.CENTER);
		pMain.setLayout(new BorderLayout(0, 0));

		pContent = createContentPanel();

		pMain.add(pContent, BorderLayout.CENTER);

		JPanel pBtns = new JPanel();
		pMain.add(pBtns, BorderLayout.SOUTH);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);

		pList = createListPanel();
		getContentPane().add(pList, BorderLayout.SOUTH);

		popupMenu = new JPopupMenu();

		mntmUpdate = new JMenuItem("수정");
		mntmUpdate.addActionListener(this);
		popupMenu.add(mntmUpdate);

		mntmDelete = new JMenuItem("삭제");
		mntmDelete.addActionListener(this);
		popupMenu.add(mntmDelete);

		pList.setPopupMenu(popupMenu);

		reloadList();
		clearContent();
	}

	protected abstract AbstractList<T> createListPanel();

	protected abstract AbstractPanel<T> createContentPanel();

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmDelete) {
			actionPerformedMntmDelete(e);
		}
		if (e.getSource() == mntmUpdate) {
			actionPerformedMntmUpdate(e);
		}
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		if (e.getSource() == btnAdd) {
			if (e.getActionCommand().equals("추가")) {
				actionPerformedBtnAdd(e);
			}
			if (e.getActionCommand().equals("수정")) {
				actionPerformedBtnUpdate(e);
			}
		}
	}

	protected abstract void clearContent();

	protected void reloadList() {
		itemList = getListAll();
		pList.setItemList(itemList);
		pList.reloadData();
	}

	protected abstract List<T> getListAll();

	private void refreshUI(T item, int res) {
		String message = res == 1 ? "성공" : "실패";
		JOptionPane.showMessageDialog(null, item + message);
		reloadList();
		clearContent();
	}

	private void actionPerformedBtnUpdate(ActionEvent e) {
		T updateDept = pContent.getItem();
//		int res = dao.updateDepartment(updateDept);
		int res =updateItem(updateDept);
		refreshUI(updateDept, res);
		btnAdd.setText("추가");
	}
	
	protected abstract int updateItem(T item);
	
	protected void actionPerformedBtnAdd(ActionEvent e) {
		T insertDepartment = pContent.getItem();
		int res = insertItem(insertDepartment);
		refreshUI(insertDepartment, res);
	}
	protected abstract int insertItem(T item);
	
	protected void actionPerformedBtnCancel(ActionEvent e) {
		clearContent();
	}

	private void actionPerformedMntmUpdate(ActionEvent e) {
		T updateDept = pList.getSelectedItem();
		pContent.setItem(updateDept);
		btnAdd.setText("수정");
	}

	private void actionPerformedMntmDelete(ActionEvent e) {
		T delDept = pList.getSelectedItem();
		int res = deleteItem(delDept);
		refreshUI(delDept, res);
	}
	
	protected abstract int deleteItem(T item);

}
