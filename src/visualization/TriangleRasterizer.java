package visualization;

import rasterop.ZBuffer;
import transforms.Col;

public class TriangleRasterizer {
	
	private ZBuffer zbuffer;
	
	public TriangleRasterizer(ZBuffer zb) {
		this.zbuffer = zb;
		
	}

	public void draw(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3,	Col c1, Col c2, Col c3) {
		//y1 <= y2 <= y3
		if(y1 > y2) {
			double pomy = y1;
			y1 = y2;
			y2 = pomy;
			
			double pomx = x1;
			x1 = x2;
			x2 = pomx;
			
			double pomz = z1;
			z1 = z2;
			z2 = pomz;
			
			Col c = c1; c1 = c2; c2 = c;
	
		}
		if(y2 > y3) {
			double pomy = y2;
			y2 = y3;
			y3 = pomy;
			
			double pomx = x2;
			x2 = x3;
			x3 = pomx;
			
			double pomz = z2;
			z2 = z3;
			z3 = pomz;
			
			Col c = c2; c2 = c3; c3 = c;
		}
		if(y1 > y2) {
			double pomy = y1;
			y1 = y2;
			y2 = pomy;
			
			double pomx = x1;
			x1 = x2;
			x2 = pomx;
			
			double pomz = z1;
			z1 = z2;
			z2 = pomz;	
			
			Col c = c1; c1 = c2; c2 = c;
		}
		
		for (int y = Math.max((int) y1 + 1, 0); y <= Math.min(y2, zbuffer.getHeight()-1); y++) {
			double s1 = (y - y1) / (y2 - y1);
			double xa = x1 * (1 - s1) + x2 * s1;
			double za = z1 * (1 - s1) + z2 * s1;
	
			double s2 = (y - y1) / (y3 - y1);
			double xb = x1 * (1 - s2) + x3 * s2;
			double zb = z1 * (1 - s2) + z3 * s2;
			
			double r1 = ((c1.getR()*255)*(1-s1)+(c2.getR()*255)*s1);
			double r2 = ((c1.getR()*255)*(1-s2)+(c3.getR()*255)*s2);
			
			double g1 = ((c1.getG()*255)*(1-s1)+(c2.getG()*255)*s1);
			double g2 = ((c1.getG()*255)*(1-s2)+(c3.getG()*255)*s2);
			
			double b1 = ((c1.getB()*255)*(1-s1)+(c2.getB()*255)*s1);
			double b2 = ((c1.getB()*255)*(1-s2)+(c3.getB()*255)*s2);
			
			if(xa > xb) {
				double pomx = xa;
				xa = xb;
				xb = pomx;
				
				double pomz = za;
				za = zb;
				zb = pomz;
				
				double pomc = r1; r1 = r2; r2 = pomc;
				pomc = b1; b1 = b2; b2 = pomc;
				pomc = g1; g1 = g2; g2 = pomc;
			}
			
			for (int x = Math.max((int) xa + 1, 0); x <= Math.min(xb, zbuffer.getWidth()-1); x++) {
				double t = (x - xa) / (xb - xa);
				double z = (za * (1-t)+zb*(t));
				
				int r = (int) Math.max(0, Math.min(255, ((r1*(1-t)+r2*t))));
				int g = (int) Math.max(0, Math.min(255, ((g1*(1-t)+g2*t))));
				int b = (int) Math.max(0, Math.min(255, ((b1*(1-t)+b2*t))));
				
				zbuffer.zTest(x, y, z, new Col(r, g, b));
				
			}
		}
		
		for (int y = Math.max((int) y2 + 1, 0); y <= Math.min(y3, zbuffer.getHeight()-1); y++) {
			double s1 = (y - y2) / (y3 - y2);
			double xa = x2 * (1 - s1) + x3 * s1;
			double za = z2 * (1 - s1) + z3 * s1;
			
			double s2 = (y - y1) / (y3 - y1);
			double xb = x1 * (1 - s2) + x3 * s2;
			double zb = z1 * (1 - s2) + z3 * s2;
			
			double r1 = ((c1.getR()*255)*(1-s1)+(c2.getR()*255)*s1);
			double r2 = ((c1.getR()*255)*(1-s2)+(c3.getR()*255)*s2);
			
			double g1 = ((c1.getG()*255)*(1-s1)+(c2.getG()*255)*s1);
			double g2 = ((c1.getG()*255)*(1-s2)+(c3.getG()*255)*s2);
			
			double b1 = ((c1.getB()*255)*(1-s1)+(c2.getB()*255)*s1);
			double b2 = ((c1.getB()*255)*(1-s2)+(c3.getB()*255)*s2);

			if(xa > xb) {
				double pomx = xa;
				xa = xb;
				xb = pomx;
				
				double pomz = za;
				za = zb;
				zb = pomz;
				
				double pomc = r1; r1 = r2; r2 = pomc;
				pomc = b1; b1 = b2; b2 = pomc;
				pomc = g1; g1 = g2; g2 = pomc;
			}
			
			for (int x = Math.max((int) xa + 1, 0); x <=  Math.min(xb, zbuffer.getWidth()-1); x++) {
				double t = (x - xa) / (xb - xa);
				double z = (za * (1-t)+zb*(t));
				
				int r = (int) Math.max(0, Math.min(255, ((r1*(1-t)+r2*t))));
				int g = (int) Math.max(0, Math.min(255, ((g1*(1-t)+g2*t))));
				int b = (int) Math.max(0, Math.min(255, ((b1*(1-t)+b2*t))));
				
				zbuffer.zTest(x, y, z, new Col(r, g, b));
				
			}
		}
	}

}
