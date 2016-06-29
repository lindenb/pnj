package com.github.lindenb.pnj.jni;

import java.io.File;
import java.io.IOException;

import com.github.lindenb.pnj.jni.PngWriter;
import com.github.lindenb.pnj.jni.PngWriterImpl;

public class PngWriterFactory {
	private static final int COLOR_MASK_PALETTE = 1;
	private static final int COLOR_MASK_COLOR = 2;
	private static final int COLOR_MASK_ALPHA = 4;
	
	
	public enum ColorType
		{
		GRAY(0),
		GRAY_ALPHA(COLOR_MASK_ALPHA),
		PALETTE(COLOR_MASK_COLOR | COLOR_MASK_PALETTE),
		RGB(COLOR_MASK_COLOR),
		RGBA(COLOR_MASK_COLOR| COLOR_MASK_ALPHA),
		;
		private final int value;
		ColorType(int v) {
			this.value=v;
		}
		}
	
	public enum Interlace
		{
		None,ADAM7
		}
	
	
	int _width = -1;
	int _height = -1;
	int bits_per_sample = -1;
	ColorType _colorType = null;
	Interlace _interlaceType = Interlace.None;
	
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
	this.bits_per_sample = d;
	return this;
	}
	
	public PngWriterFactory colorType(final ColorType v) {
	this._colorType = v;
	return this;
	}
	
	public PngWriterFactory interlaceType(final Interlace v) {
	this._interlaceType = v;
	return this;
	}


public PngWriter openPngWriter(final File out) throws IOException {
	return openPngWriter(out.getPath());
	}
public PngWriter openPngWriter(final String filename) throws IOException {
	if(this._width<0) throw new IllegalArgumentException("width<0");
	if(this._height<0) throw new IllegalArgumentException("height<0");
	if(!( this.bits_per_sample==1 ||  this.bits_per_sample==2||  this.bits_per_sample==4||  this.bits_per_sample==8||  this.bits_per_sample==16)) throw new IllegalArgumentException("_bitDepth<0");
	if(this._colorType==null) throw new IllegalArgumentException("Color type");
	final PngWriterImpl w = new PngWriterImpl();
	w.png = _png_create_write_struct();
	w.info =  _png_create_info_struct(w.png);
	w.stream = _fopen(filename);
	w.width = this._width;
	w.height = this._height;
	w.bits_per_sample = this.bits_per_sample;
	w.colorType = this._colorType;
	
	
	_png_init_io(w.png,w.stream);
	
	
	_png_set_IHDR(w.png, w.info,
			 this._width,
			 this._height,
             this.bits_per_sample,
             this._colorType.value,
             (this._interlaceType==null || this._interlaceType.equals(Interlace.None)?0:1)
             );

	_png_write_info(w.png, w.info);
	
	return w;
	}
private static native void _png_set_IHDR(long png_ptr, long info_ptr, int width, int height, int bit_depth, int color_type, int interlace_type);
private static native long _fopen(final String path);
private static native long _png_create_write_struct();
private static native long _png_create_info_struct(long png);
private static native void _png_init_io(long png,long fp);
private static native void _png_write_info(long png,long fp);
}
