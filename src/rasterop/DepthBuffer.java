package rasterop;

import model.Raster;

public class DepthBuffer implements Raster<Double> {
	private double[][] depth;
	private int width;
	private int height;
	
	public DepthBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		depth = new double[width][height];
	}
	
	@Override
	public void setPixel(int x, int y, Double pixel) {
		depth[x][y] = pixel;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Double getPixel(int x, int y) {
		return depth[x][y];
	}
	
}
