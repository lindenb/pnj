package com.github.lindenb.pnj.jni;

public class PngPtr {
	long png_ptr=0L;
	
	PngPtr() {
		this.png_ptr = _png_create_write_struct();
	}
	
	
	
	@Override
	protected void finalize()
		{
		}
	
	private static native long _png_create_write_struct();
}
