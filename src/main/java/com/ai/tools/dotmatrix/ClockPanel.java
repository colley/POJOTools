package com.ai.tools.dotmatrix;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

public class ClockPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5540245003364721001L;
	/**
	 * 点阵的行数
	 */
	public static final int ROWS=16;
	/**
	 * 点阵的列数
	 */
	public static final int COLUMNS=64;
	
	private static final int Z_WIDTH=640;//像素的绘制区域
	private static final int Z_HEIGHT=160;
	
	private static final int WIDTH=Z_WIDTH/COLUMNS;//点阵像素的直径
	private static final int HEIGHT=Z_HEIGHT/ROWS;
	
	private static final int Z_X=10;//区域的X偏移量
	private static final int Z_Y=10;//区域的Y偏移量
	
	private static final int GRAY=0x80;//灰度
	private static final Color FRONT=Color.green;//前景像素颜色
	private static final Color BACK=new Color(GRAY,GRAY,GRAY);//背景像素颜色
	public static final Color BACKGROUND=Color.black;//全局背景色
	private int[][] map=new int[ROWS][COLUMNS];
	
	public ClockPanel() {
		this.setBackground(ClockPanel.BACKGROUND);
		clearMap();
		//新起一个线程
		new Thread(){
			@Override
			public void run() {
				while (true) {
					writeMap();//写数据
					repaint();//刷新到界面
					try {
						Thread.sleep(1000);//延时调用
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}.start();
	}
	private void clearMap(){
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				map[i][j]=0;
			}
		}
	}
	private void writeMap(){
		GregorianCalendar g=new GregorianCalendar();
		int hour=g.get(Calendar.HOUR_OF_DAY);
		int min=g.get(Calendar.MINUTE);
		int sec=g.get(Calendar.SECOND);
		ROM.romToDisplay(map, ROM.rom[hour/10], 0);
		ROM.romToDisplay(map, ROM.rom[hour%10], 1);
		
		ROM.romToDisplay(map, ROM.rom[10], 2);//:
		
		ROM.romToDisplay(map, ROM.rom[min/10], 3);
		ROM.romToDisplay(map, ROM.rom[min%10], 4);
		
		ROM.romToDisplay(map, ROM.rom[10], 5);//:
		
		ROM.romToDisplay(map, ROM.rom[sec/10], 6);
		ROM.romToDisplay(map, ROM.rom[sec%10], 7);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color c=g.getColor();
//		g.drawRect(Z_X, Z_X, Z_WIDTH, Z_HEIGHT);//绘制像素区域
		g.setColor(BACK);
		g.fillOval(Z_X, Z_Y, WIDTH, HEIGHT);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (map[i][j]==1) {
					g.setColor(FRONT);//显示
					System.out.print("*");
				} else {
					System.out.print(" ");
					g.setColor(BACK);//擦除
				}
				g.fillOval(Z_X+j*WIDTH, Z_Y+i*HEIGHT, WIDTH, HEIGHT);
			}
			
		}
		g.setColor(c);
	}
}
