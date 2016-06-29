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
		BufferedImage img= new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB);
		Graphics2D g=img.createGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 100, 200);
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, 80, 180);
		g.dispose();
		
		 //javax.imageio.ImageIO.write(img, "png", new java.io.File("jeter0.png"));
		final PngWriter w = PngWriterFactory.newInstance().
				width(img.getWidth()).
				height(img.getHeight()*10).
				bitDepth(8).
				colorType(PngWriterFactory.ColorType.RGB).
				openPngWriter("jeter2.png");
		for(int x=0;x<10;++x)
		   w.writeImageTile(img);
		w.close();
		
	} catch (Exception e) {
	e.printStackTrace();
	}
}
}
