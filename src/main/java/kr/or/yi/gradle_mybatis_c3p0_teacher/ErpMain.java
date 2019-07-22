package kr.or.yi.gradle_mybatis_c3p0_teacher;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class ErpMain {

	public static void main(String[] args) {
		try {
			// JTattoo.jar : JTattoo Look and Feel
//			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			// SeaGlassLookAndFeel
//			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			// 2. quaqua.jar : Quaqua Look and Feel
//			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
			// 3. liquidlnf.jar : Liquid Look and Feel
//			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// 4. idw-gpl.jar : InfoNode Look and Feel
//			UIManager.setLookAndFeel("net.infonode.gui.laf.InfoNodeLookAndFeel");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErpApplication frame = new ErpApplication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
