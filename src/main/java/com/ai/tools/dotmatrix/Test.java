package com.ai.tools.dotmatrix;

public class Test {
	public static void main(String[] args) {
		int[] rom=ROM.rom[0];
		int[][] map=new int[16][8];//单个字节的字模
		for (int i = 0; i < map.length; i++) {
			int[] b=new int[8];
			for (int j = 0; j < rom.length; j++) {
				
				ROM.intToBit(map[j],0,rom[j]);
				System.out.println(b);
			}
			
		}
	}
	
	static void testIntToBit(){
		int rom=0x28;
		StringBuffer sb=new StringBuffer();
		for (int j = 0; j < 8; j++) {//一个字节8个位
			sb.insert(0, rom&0x01);//低字节先出来
			
			if(j==3)sb.insert(0, ' ');
			rom>>=1;
		}
	}
}
