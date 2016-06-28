package com.github.lindenb.pnj.jni;

class PngWriterImpl implements PngWriter {
	long png;
	long info;
	long stream;
	
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


private static native void _png_write_end(long png);
private static native void _png_write_row(long png, byte[] row);
private static native void _fclose(long fp);

}
