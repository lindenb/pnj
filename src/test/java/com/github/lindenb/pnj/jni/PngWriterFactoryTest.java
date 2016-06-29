package com.github.lindenb.pnj.jni;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import com.github.lindenb.pnj.jni.PngWriter;
import com.github.lindenb.pnj.jni.PngWriterFactory;

public class PngWriterFactoryTest {
	
	
public static void main(String[] args) {
	try {
		System.loadLibrary("pnj");
		BufferedImage img= new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 50, 50);
		g.dispose();
		
		
		final PngWriter w = PngWriterFactory.newInstance().
				width(img.getWidth()).
				height(img.getHeight()).
				bitDepth(8).
				colorType(PngWriterFactory.ColorType.RGBA).
				openPngWriter("jeter.png");
		for(int y=0;y<img.getWidth();++y)
			{
			byte[] array=new byte[img.getWidth()*4*Integer.BYTES];
			w.writeRow(array);
			}
		
		
		w.close();
		
	} catch (Exception e) {
	e.printStackTrace();
	}
}
}
