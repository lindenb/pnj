package com.github.lindenb.pnj.jni;

public interface PngWriter extends AutoCloseable {
public void writeRow(byte[] array) throws java.io.IOException;
}
