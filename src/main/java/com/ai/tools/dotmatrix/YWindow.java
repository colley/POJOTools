package com.ai.tools.dotmatrix;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class YWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 827880273309807558L;
	//窗口的长宽
	private static final int WIDTH=670;
	private static final int HEIGHT=210;
	
	public YWindow(String ywname){
		super(ywname);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();  
		setBounds((dimension.width-WIDTH)>>1, (dimension.height-HEIGHT)>>1, WIDTH, HEIGHT);//设置窗口居中
		add(new DotTest());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new YWindow("点阵钟表01");
	}
	
}
