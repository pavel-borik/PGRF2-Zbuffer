package model;

import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;

public class VertexBase implements Vertex {
	
	private Point3D position;
	private Col color;
	private double one;
	
	public VertexBase(Point3D position) {
		this.position = position;
	}
	
	public VertexBase(Point3D position, Col color) {
		this.position = position;
		this.color = color;
		this.one = 1.0d;
	}

	@Override
	public Point3D getPosition() {
		return position;
	}
	@Override
	public VertexBase mul (Mat4 m){	
		return new VertexBase(this.position.mul(m));
	}
	@Override
	public VertexBase mul(double i){
		VertexBase pomV = new VertexBase(position.mul(i));
		pomV.one = this.one * i;
		return pomV;
	}
	
	@Override
	public VertexBase add(VertexBase v){
		VertexBase pomV = new VertexBase(position.add(v.position));
		pomV.one = this.one + v.one;
		return pomV;
	}
	
	public VertexBase dehomog(){
		double w =  this.position.getW();
		VertexBase v = new VertexBase(this.position.mul(w));
		v.one = this.one/w;
		return v;
	}
	
	public Col getColor() {
		return color;
	}

	public void setColor(Col color) {
		this.color = color;
	}

	public double getW() {
		return position.getW();
	}
	
}
