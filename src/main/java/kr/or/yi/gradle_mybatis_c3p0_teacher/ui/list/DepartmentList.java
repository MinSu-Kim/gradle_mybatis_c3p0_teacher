package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;

@SuppressWarnings("serial")
public class DepartmentList extends JPanel {
	private JTable table;
	protected List<Department> itemList;
	private JScrollPane scrollPane;

	public DepartmentList(String title) {
		initComponents(title);
	}

	private void initComponents(String title) {
		setBorder(new TitledBorder(null, title + "목록", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400, 200));
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		table.setComponentPopupMenu(popupMenu);
		scrollPane.setComponentPopupMenu(popupMenu);
	}

	public void setItemList(List<Department> itemList) {
		this.itemList = itemList;
	}

	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));
		tableAlignmentAndWidth();
	}

	private Object[][] getRows() {
		Object[][] rows = new Object[itemList.size()][];
		for (int i = 0; i < itemList.size(); i++) {
			rows[i] = toArray(i);
		}
		return rows;
	}

	protected void tableAlignmentAndWidth() {
		// 직책번호, 직책명은 가운데 정렬
		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2);
		// 직책번호, 직책명의 폭을 (100, 200)으로 가능하면 설정
		tableSetWidth(100, 200, 100);
	}

	protected Object[] toArray(int idx) {
		Department department = itemList.get(idx);
		return department.toArray();
	}

	protected String[] getColumnNames() {
		return new String[] { "부서번호", "부서명", "위치" };
	}

	// 테이블 셀 내용의 정렬
	protected void tableCellAlignment(int align, int... idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < idx.length; i++) {
			model.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}

	// 테이블 셀의 폭 설정
	protected void tableSetWidth(int... width) {
		TableColumnModel cModel = table.getColumnModel();

		for (int i = 0; i < width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}

	public Department getSelectedItem() {
		int i = table.getSelectedRow();
		Department item = null;
		if (table.getModel().getRowCount() == 0) {
			return item;
		}
		if (i < 0 || i > table.getModel().getRowCount() - 1) {
			JOptionPane.showMessageDialog(null, "선택된 부서가 없습니다.");
			return item;
		}

		item = itemList.get(i);
		return item;
	}
}
