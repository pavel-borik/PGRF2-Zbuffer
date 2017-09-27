package rasterop;

import java.awt.image.BufferedImage;

import transforms.Col;

public class ZBuffer {
	DepthBuffer df;
	ImageBuffer img;
	int width, height;
	Col bg = new Col(0x2f2f2f);
	
	public ZBuffer(int width, int height, BufferedImage bi) {
		df = new DepthBuffer(width, height);
		img = new ImageBuffer(width, height, bi);
		this.width = width;
		this.height = height;
		clear(width, height, bg);
	}
	
	public void zTest(int x, int y, double z, Col color) {
		if(z < 1 && z > 0 ) {
			if(z < df.getPixel(x, y)) {
			df.setPixel(x, y, z);			
			img.setPixel(x, y, color);
			}
		}
	}
	
	public void clear(int width, int height, Col bgColor) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				df.setPixel(i, j, 1.0);
				img.setPixel(i, j, bgColor);
			}
		}	
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
