package com.github.lindenb.pnj.jni;

import java.awt.image.BufferedImage;

public interface PngWriter extends AutoCloseable {
public void writeRow(byte[] array) throws java.io.IOException;
public void writeImageTile(BufferedImage img)  throws java.io.IOException;
}
