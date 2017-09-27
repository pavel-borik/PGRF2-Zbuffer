package model;

import transforms.Mat4;
import transforms.Point3D;

public interface Vertex {
	
	Point3D getPosition();
	Vertex mul(Mat4 m);
	Vertex mul(double i);
	Vertex add(VertexBase v);
}
