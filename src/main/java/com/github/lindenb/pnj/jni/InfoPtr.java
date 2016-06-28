package com.github.lindenb.pnj.jni;

public class InfoPtr {
	final PngPtr pngPtr;
	long info_ptr=0L;
	
	InfoPtr() {
		this(new PngPtr());
	}
	
	InfoPtr(final PngPtr pngPtr) {
		this.pngPtr = pngPtr;
		this.info_ptr = _png_create_info_struct(pngPtr.png_ptr);
	}
	
	
	
	@Override
	protected void finalize()
		{
		}
	
	private static native long _png_create_info_struct(long png);
}
