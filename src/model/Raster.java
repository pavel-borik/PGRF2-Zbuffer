package model;

public interface Raster<P> {
	
	void setPixel(int x, int y, P pixel);
	int getWidth();
	int getHeight();
	P getPixel(int x, int y);
}
