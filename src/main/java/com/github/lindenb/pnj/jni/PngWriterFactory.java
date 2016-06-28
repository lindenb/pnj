package com.github.lindenb.pnj.jni;

import java.io.File;
import com.github.lindenb.pnj.jni.PngWriter;
import com.github.lindenb.pnj.jni.PngWriterImpl;

public class PngWriterFactory {
	private static final int COLOR_MASK_PALETTE = 1;
	private static final int COLOR_MASK_COLOR = 2;
	private static final int COLOR_MASK_ALPHA = 4;
	public static final int COLOR_TYPE_GRAY=0;
	public static final int COLOR_TYPE_RGB = COLOR_MASK_COLOR ;
	public static final int INTERLACE_NONE=0;
	int _width = -1;
	int _height = -1;
	int _bitDepth = -1;
	int _colorType = -1;
	int _interlaceType = INTERLACE_NONE;
	int _compressionType = -1;
	int _filterType = -1;
	
	private PngWriterFactory()	{
	}
	
	public static PngWriterFactory newInstance() {
	return new PngWriterFactory();
	}

	public PngWriterFactory width(int w) {
	this._width = w;
	return this;
	}
	
	public PngWriterFactory height(int h) {
	this._height = h;
	return this;
	}

	public PngWriterFactory bitDepth(int d) {
	this._bitDepth = d;
	return this;
	}
	
	public PngWriterFactory colorType(int v) {
	this._colorType = v;
	return this;
	}
	
	public PngWriterFactory interlaceType(int v) {
	this._interlaceType = v;
	return this;
	}

	public PngWriterFactory compressionType(int v) {
	this._compressionType = v;
	return this;
	}
	
	public PngWriterFactory filterType(int v) {
	this._filterType = v;
	return this;
	}


public PngWriter PngWriter(final String filename) {
	final PngWriterImpl w = new PngWriterImpl();
	w.png = _png_create_write_struct();
	w.info =  _png_create_info_struct(w.png);
	w.stream = _fopen(filename);
	
	_png_init_io(w.png,w.stream);
	
	return w;
	}

private static native long _fopen(final String path);
private static native long _png_create_write_struct();
private static native long _png_create_info_struct(long png);
private static native void _png_init_io(long png,long fp);
}
