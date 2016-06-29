package com.github.lindenb.pnj.jni;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import com.github.lindenb.pnj.jni.PngWriter;
import com.github.lindenb.pnj.jni.PngWriterFactory;

public class PngWriterFactoryTest {
	
	
public static void main(String[] args) {
	try {
		System.loadLibrary("pnj");
		BufferedImage img= new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g=img.createGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 50, 50);
		g.dispose();
		
		
		final PngWriter w = PngWriterFactory.newInstance().
				width(img.getWidth()).
				height(img.getHeight()).
				bitDepth(8).
				colorType(PngWriterFactory.ColorType.RGB).
				openPngWriter("jeter2.png");
		
		w.writeImageTile(img);
		w.close();
		
	} catch (Exception e) {
	e.printStackTrace();
	}
}
}
