package com.ai.tools.dotmatrix;

/**
 * @User: mayc
 * @Date: 2014年8月6日
 * @Time: 下午4:14:10
 * @version $Revision$ $Date$
 */
public class DotMatrix {

	static char mo[]={
					0x00,0x00, 0x10,0x90, 0x10,0x94, 0x13,0xFE, 
					0x7C,0x90, 0x11,0xFC, 0x19,0x04, 0x35,0xFC, 
					0x35,0x04, 0x51,0xFC, 0x50,0x40, 0x17,0xFE, 
					0x10,0x90, 0x11,0x08, 0x16,0x06, 0x00,0x00
			};
			 static char[] cmp_w ={128,64,32,16,8,4,2,1};
			public static void FontDisplay(int x, int y, byte[] FontModule)
			{ 
			for(int row=0;row<16;row++)
			{
			for(int c=0;c<8;c++)
			if((FontModule[row*2]&cmp_w[c])!=0){
				System.out.print("*");
			}else{
				System.out.print(" ");
			}
			System.out.println();
			//putpixel(c+x,row+y,15); 
			for(int c=0;c<8;c++)
			if((FontModule[row*2+1]&cmp_w[c])!=0){
				System.out.print("*");
			}else{
				System.out.print(" ");
			}
			System.out.println();
			//putpixel(c+8+x,row+y,15); 
			}
			}
			public static void main(String[] args) {
				DotMatrix.FontDisplay(100, 100, "A".getBytes());
			}
    }
