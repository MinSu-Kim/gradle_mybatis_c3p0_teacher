package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.service.BoardUIService;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.BoardUI;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.ReplyList;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.ReplyList.Complete;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.utils.MediaUtils;

@SuppressWarnings("serial")
public class PanelBoard extends AbstractPanel<Board> implements ActionListener {
	private static String VIEW_REPLY_LIST = "댓글 보기";
	private static final File UPLOAD_DIR = new File(System.getProperty("user.dir") + "\\upload\\");

	static {
		if (!UPLOAD_DIR.exists())
			UPLOAD_DIR.mkdir();
	}

	private JTextField tfTitle;
	private JTextField tfWriter;
	private JTextArea taContent;
	private BoardUI boardUI;
	private Board board;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnList;
	private JButton btnWrite;
	private JFrame writeFrame;
	private JButton btnCancel;
	private JButton btnReplylist;
	private ReplyList pReply;
	private JFrame replyUI;

	private DefaultListModel<File> model;
	private JList<File> listFile;
	private ListTransferHandler listHandler;
	private JMenuItem mntmDel;
	private JMenuItem mntmDownload;

	private List<String> srcFilePath;

	public PanelBoard(String title) {
		super(title);
	}

	@Override
	protected void initComponents(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel pBoard = new JPanel();
		pBoard.setBorder(new EmptyBorder(0, 10, 0, 10));
		add(pBoard, BorderLayout.NORTH);
		pBoard.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblTitle = new JLabel("제목");
		pBoard.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);

		tfTitle = new JTextField();
		pBoard.add(tfTitle);
		tfTitle.setColumns(10);

		JPanel pContent = new JPanel();
		pContent.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(pContent, BorderLayout.CENTER);
		pContent.setLayout(new BorderLayout(0, 10));

		JLabel lblContent = new JLabel("내용");
		pContent.add(lblContent, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		pContent.add(scrollPane, BorderLayout.CENTER);

		taContent = new JTextArea();
		scrollPane.setViewportView(taContent);

		JPanel pWriterAttach = new JPanel();
		pContent.add(pWriterAttach, BorderLayout.SOUTH);
		pWriterAttach.setLayout(new BorderLayout(0, 10));

		JPanel pWriter = new JPanel();
		pWriterAttach.add(pWriter, BorderLayout.NORTH);
		pWriter.setLayout(new GridLayout(0, 1, 0, 10));

		JLabel lblWriter = new JLabel("작성자");
		pWriter.add(lblWriter);
		lblWriter.setHorizontalAlignment(SwingConstants.LEFT);

		tfWriter = new JTextField();
		pWriter.add(tfWriter);
		tfWriter.setColumns(10);

		JPanel pAttach = new JPanel();
		pWriterAttach.add(pAttach, BorderLayout.CENTER);
		pAttach.setLayout(new BorderLayout(0, 10));

		JLabel lblFileTitle = new JLabel("추가 할 파일을 드래그 하세요");
		lblFileTitle.setHorizontalAlignment(SwingConstants.LEFT);
		pAttach.add(lblFileTitle, BorderLayout.NORTH);

		model = new DefaultListModel<File>();
		listHandler = new ListTransferHandler();

		JPopupMenu popupMenu = new JPopupMenu();

		mntmDel = new JMenuItem("삭제");
		mntmDel.addActionListener(this);
		popupMenu.add(mntmDel);

		mntmDownload = new JMenuItem("다운로드");
		mntmDownload.addActionListener(this);
		popupMenu.add(mntmDownload);

		listFile = new JList<>(model);
		listFile.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listFile.setPreferredSize(new Dimension(0, 80));
		listFile.setDragEnabled(true);
		listFile.setTransferHandler(listHandler);
		listFile.setCellRenderer(new FileListCell());
		listFile.setDropMode(DropMode.INSERT);
		listFile.setComponentPopupMenu(popupMenu);

		JScrollPane scrollPane_1 = new JScrollPane();
		pAttach.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setViewportView(listFile);

		JPanel pSouth = new JPanel();
		pSouth.setBorder(new EmptyBorder(0, 10, 0, 19));
		add(pSouth, BorderLayout.SOUTH);
		pSouth.setLayout(new BorderLayout(0, 0));

		JPanel pBtns = new JPanel();
		pSouth.add(pBtns, BorderLayout.NORTH);

		btnReplylist = new JButton(VIEW_REPLY_LIST);
		pBtns.add(btnReplylist);

		btnWrite = new JButton("작성");
		pBtns.add(btnWrite);

		btnUpdate = new JButton("수정");
		pBtns.add(btnUpdate);

		btnCancel = new JButton("취소");
		pBtns.add(btnCancel);

		btnDelete = new JButton("삭제");
		pBtns.add(btnDelete);

		btnList = new JButton("목록");
		pBtns.add(btnList);

		btnList.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnUpdate.setVisible(false);
		btnWrite.addActionListener(this);
		btnReplylist.addActionListener(this);

		pReply = new ReplyList();
	}

	Complete returnComplete = new Complete() {
		@Override
		public void isComplete() {
			btnReplylistChangeText();
			pReply.repaint();
			pReply.revalidate();
		}
	};

	private void btnReplylistChangeText() {
		Board newBoard = BoardUIService.getInstance().readBoard(board.getBno());
		VIEW_REPLY_LIST = String.format("%s [%d]", "댓글 보기", newBoard.getReplyCnt());
		btnReplylist.setText(VIEW_REPLY_LIST);
	}

	public void setEditable(boolean isEditable) {
		tfTitle.setEditable(isEditable);
		taContent.setEditable(isEditable);
	}

	public void setItem(Board board) {
		clearComponent();
		this.board = board;
		pReply.setComplete(returnComplete);
		pReply.setBoard(board);
		BoardUIService.getInstance().updateViewCnt((int) board.getBno());
		tfTitle.setText(board.getTitle());
		tfWriter.setText(board.getWriter());
		taContent.setText(board.getContent());
		tfWriter.setEditable(false);

		setUploadPaths(board.getFiles());

		VIEW_REPLY_LIST = String.format("%s [%d]", VIEW_REPLY_LIST, board.getReplyCnt());
		btnReplylist.setText(VIEW_REPLY_LIST);
	}

	public void setUploadPaths(List<String> uploadPaths) {
		for (String subPath : uploadPaths) {
			if (MediaUtils.checkImageType(subPath)) {
				model.addElement(new File(UPLOAD_DIR + subPath));
			} else {
				model.addElement(new File(UPLOAD_DIR + subPath));
			}
		}
	}

	public Board getItem() {
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		String writer = tfWriter.getText().trim();

		List<String> files = new ArrayList<String>();
		srcFilePath = new ArrayList<String>();

		for (int i = model.getSize() - 1; i >= 0; i--) {
			File file = model.get(i);
			System.out.println("model.get(i) ===> " + file.toPath());
			srcFilePath.add(file.getPath());

			UUID uid = UUID.randomUUID();
			String savedName = uid.toString() + "_" + file.getName();

			Calendar cal = Calendar.getInstance();
			String yearPath = File.separator + cal.get(Calendar.YEAR);
			String monthPath = yearPath + File.separator + String.format("%02d", cal.get(Calendar.MONTH) + 1);
			String datePath = monthPath + File.separator + String.format("%02d", cal.get(Calendar.DATE));

			String uploadPath = (datePath + File.separator + savedName).replace(File.separatorChar, '/');

			files.add(uploadPath);
		}

		if (title.equals("") || content.equals("") || writer.equals("")) {
			throw new RuntimeException("빈칸이 존재합니다. 확인하세요");
		}

		if (board == null) { // 추가
			return new Board(title, content, writer, files);
		} else { // 수정
			board.setTitle(title);
			board.setContent(content);
			board.setWriter(writer);
			board.setFiles(files);
			return board;
		}
	}

	public void setBoardUI(BoardUI boardUI) {
		this.boardUI = boardUI;
	}

	@Override
	public void clearComponent(int nextNo) {
		throw new UnsupportedOperationException();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReplylist) {
			actionPerformedBtnReplylist(e);
		}
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel();
		}
		if (e.getSource() == btnList) {
			actionPerformedBtnList();
		}

		if (e.getSource() == btnWrite) {
			actionPerformedBtnWrite(e);
		}

		if (e.getSource() == btnDelete) {
			actionPerformedBtnDelete(e);
		}

		if (e.getSource() == btnUpdate) {
			if (e.getActionCommand().equals("수정")) {
				actionPerformedChangeUpdateMode();
			} else { // 저장 (수정)
				actionPerformedUpdate();
			}
		}
		if (e.getSource() == mntmDel) {
			actionPerformedMntmDel(e);
		}
		if (e.getSource() == mntmDownload) {
			actionPerformedMntmDownload(e);
		}
	}

	private void actionPerformedMntmDownload(ActionEvent e) {
		int[] selIndexes = listFile.getSelectedIndices();
		for (int i = 0; i < selIndexes.length; i++) {
			JFileChooser f = new JFileChooser(System.getProperty("user.dir"));
			if ( f.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
				File file = f.getSelectedFile();
				File downFile = model.get(selIndexes[i]);
				makeFile(downFile, file);
			}
		}
	}

	private void actionPerformedMntmDel(ActionEvent e) {
		int[] selIndexes = listFile.getSelectedIndices();
		System.out.println(Arrays.toString(selIndexes));
		System.out.println(model);
		
		for (int i = selIndexes.length - 1; i >= 0; i--) {
			File deleteFile = model.get(selIndexes[i]);
			deleteFile.delete();
			String start = deleteFile.getPath().substring(0, deleteFile.getPath().lastIndexOf("\\")+1);
			String end = deleteFile.getPath().substring(deleteFile.getPath().lastIndexOf("\\")+1);
			String thumbnailName = start + "s_" + end;
			File newFile = new File(thumbnailName);
			newFile.delete();
			model.removeElementAt(selIndexes[i]);
		}
	}

	private void actionPerformedUpdate() {
		//수정후 저장
		String title = tfTitle.getText().trim();
		String content = taContent.getText();
		
		board.setTitle(title);
		board.setContent(content);
		
		
		System.out.println("model.getSize() : " + model.getSize());
		
		if (model.getSize() >0) {
			List<String> files = new ArrayList<String>();
			for(int i=model.getSize()-1; i>-1; i--){
				String f = model.getElementAt(i).getPath();
				System.out.println(f);
				System.out.println(f.indexOf("upload"));
				String sub = f.substring(f.indexOf("upload")+6).replace("\\", "/");
				System.out.println("sub ========== " + sub);
				files.add(sub);
			}
			
			board.setFiles(files);
			System.out.println("update files " + files);
		}else {
			board.setFiles(null);
		}
		
		JOptionPane.showMessageDialog(null, "board" + board);

		BoardUIService.getInstance().modify(board);
		JOptionPane.showMessageDialog(null, "수정하였습니다");
		clearComponent();
		boardUI.reloadList();
		boardUI.changeListUI();
		boardUI.setBtnNewButtonText();
		board = null;
		btnUpdate.setText("수정");
		clearComponent();

		if (replyUI!=null && replyUI.isVisible()) {
			viewReplyUI(false);
		}
	}

	protected void actionPerformedBtnWrite(ActionEvent e) {

		try {
			Board board = getItem();
			new UploadWorker(board).execute();
		} catch (RuntimeException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}

	}

	private void insertBoard(Board board) {
		try {
			BoardUIService.getInstance().register(board);
			boardUI.reloadList();
			writeFrame.dispose();
			clearComponent();
			JOptionPane.showMessageDialog(null, "Upload 완료");
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, "Upload 실패");
		}
	}

	private class UploadWorker extends SwingWorker<Void, String> {
		private List<String> uploadPathList;
		private Board board;

		public UploadWorker(Board board) {
			this.board = board;
			this.uploadPathList = board.getFiles();
		}

		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i < uploadPathList.size(); i++) {
				String filePath = board.getFiles().get(i);
				String yearPath = filePath.substring(1, 5);
				String monthPath = yearPath + File.separator + filePath.substring(6, 8);
				String datePath = monthPath + File.separator + filePath.substring(9, 11);

				makeDir(UPLOAD_DIR, yearPath, monthPath, datePath);

				File target = new File(UPLOAD_DIR + filePath);
				makeFile(new File(srcFilePath.get(i)), target);// 원본->목적

				if (MediaUtils.checkImageType(filePath.substring(filePath.lastIndexOf("_")))) {
					makeThumbnale(srcFilePath.get(i), filePath);
				}
			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			System.out.println("chunks" + chunks);
		}

		@Override
		protected void done() {
			insertBoard(board);
			model.clear();
		}

	}

	private void makeThumbnale(String srcPath, String targetPath) {
		try {
			File srcFile = new File(srcPath);
			String thumbnailName = UPLOAD_DIR + targetPath.substring(0, 12) + "s_" + targetPath.substring(12);
			File newFile = new File(thumbnailName);
			String formatName = thumbnailName.substring(thumbnailName.lastIndexOf(".") + 1);
			MediaUtils.createThumbnale(srcFile, newFile, formatName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void makeFile(File srcFile, File destFile) /* throws IOException, FileNotFoundException */ {

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));) {
			byte[] readBuffer = new byte[1024];
			while (bis.read(readBuffer, 0, readBuffer.length) != -1) {
				bos.write(readBuffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void makeDir(File uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists())
			return;
		for (String path : paths) {
			File dirPath = new File(uploadPath, path);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}

	protected void actionPerformedBtnDelete(ActionEvent e) {
		
		
		try {
		
			BoardUIService.getInstance().remove((int)board.getBno());
			
			for (int i = model.size() - 1; i >= 0; i--) {
				File deleteFile = model.get(i);
				deleteFile.delete();
				String start = deleteFile.getPath().substring(0, deleteFile.getPath().lastIndexOf("\\")+1);
				String end = deleteFile.getPath().substring(deleteFile.getPath().lastIndexOf("\\")+1);
				String thumbnailName = start + "s_" + end;
				File newFile = new File(thumbnailName);
				newFile.delete();
				model.removeElementAt(i);
			}
			
			JOptionPane.showMessageDialog(null, "삭제하였습니다");
			clearComponent();
			boardUI.reloadList();
			boardUI.changeListUI();
			clearComponent();
		} catch (RuntimeException e1) {
			JOptionPane.showMessageDialog(null, "댓글이 존재하여 삭제가 되지않습니다.");
		}
	}

	
	public void setWriteMode() {
		btnWrite.setVisible(true);
		btnList.setVisible(true);
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(false);

		listFile.setEnabled(true);
		listFile.setDragEnabled(true);

		mntmDel.setEnabled(true);
	}

	public void setReadMode() {
		btnWrite.setVisible(false);
		btnList.setVisible(true);
		btnUpdate.setVisible(true);
		btnUpdate.setText("수정");
		btnDelete.setVisible(true);
		btnCancel.setVisible(false);

		listFile.setDragEnabled(false);
		mntmDel.setEnabled(false);

		if (replyUI == null || !replyUI.isVisible()) {
			btnReplylist.setText(VIEW_REPLY_LIST);
		}
	}

	public void setUpdateMode() {
		setEditable(true);
		btnUpdate.setText("저장");
		btnWrite.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(true);
		btnList.setVisible(false);

		listFile.setEnabled(true);
		listFile.setDragEnabled(true);
		mntmDel.setEnabled(true);
	}

	private void actionPerformedChangeUpdateMode() {
		setEditable(true);
		btnUpdate.setText("저장");
		btnWrite.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(true);
		btnList.setVisible(false);
		listFile.setDragEnabled(true);
		mntmDel.setEnabled(true);
	}

	public void setFrame(JFrame writeFrame) {
		this.writeFrame = writeFrame;
	}

	public void clearComponent() {
		tfTitle.setText("");
		tfWriter.setText("");
		taContent.setText("");
		model.clear();
		board = null;
		VIEW_REPLY_LIST = "댓글 보기";
	}

	protected void actionPerformedBtnCancel() {
		boardUI.changeListUI();
		clearComponent();
		if (writeFrame != null) {
			writeFrame.dispose();
		}

		if (replyUI != null && replyUI.isVisible()) {
			viewReplyUI(false);
		}
	}

	private void actionPerformedBtnList() {
		actionPerformedBtnCancel();
		if (writeFrame != null) {
			writeFrame.dispose();
			setWriteMode();
		}
		// 게시판 목록 새로 읽기 추가
		boardUI.reloadList();

		viewReplyUI(false);
	}

	private void viewReplyUI(boolean isVisible) {
		if (replyUI == null) {
			replyUI = new JFrame();
			Rectangle rv = new Rectangle();
			boardUI.getBounds(rv);
			boardUI.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					replyUI.dispose();
					btnReplylist.setText(VIEW_REPLY_LIST);
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					if (replyUI.isDisplayable()) {
						replyUI.setVisible(true);
					}
				}
			});
			boardUI.addWindowStateListener(new WindowStateListener() {
				@Override
				public void windowStateChanged(WindowEvent e) {
					replyUI.setState(e.getNewState());
				}
			});
			boardUI.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals("windowMoved")) {
						Rectangle rv = new Rectangle();
						boardUI.getBounds(rv);
						int x = (int) (rv.getX() + rv.getWidth());
						replyUI.setBounds(x, (int) rv.getY(), 400, (int) rv.getHeight());
					}
				}
			});
			int x = (int) (rv.getX() + rv.getWidth());
			replyUI.setBounds(x, (int) rv.getY(), 320, (int) rv.getHeight());
			replyUI.setUndecorated(true);
			replyUI.getContentPane().add(pReply);
		}
		replyUI.setVisible(isVisible);
	}

	protected void actionPerformedBtnReplylist(ActionEvent e) {
		if (btnReplylist.getText().equals(VIEW_REPLY_LIST)) {
			btnReplylist.setText("댓글 닫기");
			viewReplyUI(true);
		} else {
			btnReplylist.setText(VIEW_REPLY_LIST);
			viewReplyUI(false);
		}
	}

	private class ListTransferHandler extends TransferHandler {

		@Override
		public boolean canImport(TransferHandler.TransferSupport info) {
			if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				return false;
			}
			if (!listFile.getDragEnabled()) {
				return false;
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean importData(TransferHandler.TransferSupport info) {
			if (!info.isDrop()) {
				return false;
			}

			if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				displayDropLocation("List doesn't accept a drop of this type.");
				return false;
			}

			Transferable t = info.getTransferable();
			List<File> data;
			try {
				data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
			} catch (Exception e) {
				return false;
			}
			JList.DropLocation dropLocation = (JList.DropLocation) info.getDropLocation();
			int dropIndex = dropLocation.getIndex();

			for (Object file : data) {
				model.add(dropIndex++, (File) file);
			}
			return true;
		}

		private void displayDropLocation(String string) {
			System.out.println(string);
		}
	}

	private class FileListCell extends DefaultListCellRenderer {

		@SuppressWarnings("rawtypes")
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (c instanceof JLabel && value instanceof File) {
				JLabel l = (JLabel) c;
				File file = (File) value;

				try {
					if (MediaUtils.checkImageType(file.getName())) {
						l.setIcon(MediaUtils.createImageIcon(file));
					} else {
						l.setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
					}
					l.setText(file.getName());
					Path source = Paths.get(file.getPath());
					l.setToolTipText(file.getAbsolutePath() + " type(" + Files.probeContentType(source) + ")");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return c;
		}

	}

}
