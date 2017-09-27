package rasterop;

import java.awt.image.BufferedImage;

import model.Raster;
import transforms.Col;

public class ImageBuffer implements Raster<Col> {

	private BufferedImage img;

	public ImageBuffer(int width, int height, BufferedImage img) {
		this.img = img;
	}
	
	@Override
	public void setPixel(int x, int y, Col pixel) {
		img.setRGB(x, y, pixel.getRGB());
	}

	@Override
	public int getWidth() {
		return img.getWidth();
	}

	@Override
	public int getHeight() {
		return img.getHeight();
	}

	@Override
	public Col getPixel(int x, int y) {
		return getPixel(x, y);
	}


}
