package com.github.lindenb.pnj.jni;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

class PngWriterImpl implements PngWriter {
	long png;
	long info;
	long stream;
	int width;
	int height;
	int bits_per_sample;
	PngWriterFactory.ColorType colorType;
	
	@Override
	public void close() {
	 _png_write_end(png);
	 _fclose(stream);
	 this.png=0L;
	 this.stream=0L;
	}

@Override
public void writeRow(byte[] row) throws java.io.IOException {
	_png_write_row(this.png,row);
	}

public void writeImageTile(final BufferedImage img)  throws java.io.IOException
	{
	//byte[] array=new byte[img.getWidth()*4*Integer.BYTES];
	WritableRaster raster = img.getRaster();
	DataBuffer buf = raster.getDataBuffer();
	switch(buf.getDataType()) 
		{
		case DataBuffer.TYPE_INT:
			int sample_per_pixel=3;
			DataBufferInt bufint = DataBufferInt.class.cast(buf);
			byte row[]=new byte[sample_per_pixel*img.getWidth()];
			for(int y=0;y<img.getHeight();++y)
				{
				for(int x=0;x<img.getWidth();++x)
					{
					int shift=0;
					int c = bufint.getElem(y*img.getWidth()+x);
					for(int z=sample_per_pixel-1;z>=0;--z)
						{
						row[x*sample_per_pixel+z] =  (byte) (c >> shift);
						shift+=8;
						}
					//row[x*4+3] =  (byte) (c );
					}
				this.writeRow(row);
				}
			return;
		default:
			System.err.println(buf.getDataType());
			break;
		}
	
	
	
	}



private static native void _png_write_end(long png);
private static native void _png_write_row(long png, byte[] row);
private static native void _fclose(long fp);

}
