package model;

import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid {
	
	public Cube() {
		getVertices().add(new VertexBase(new Point3D(0,0,0)));	
		getVertices().add(new VertexBase(new Point3D(1,0,0)));	
		getVertices().add(new VertexBase(new Point3D(1,1,0)));	
		getVertices().add(new VertexBase(new Point3D(0,1,0)));	
		getVertices().add(new VertexBase(new Point3D(0,0,1)));	
		getVertices().add(new VertexBase(new Point3D(1,0,1)));	
		getVertices().add(new VertexBase(new Point3D(1,1,1)));	
		getVertices().add(new VertexBase(new Point3D(0,1,1)));	
		
		getIndices().add(0);getIndices().add(1);getIndices().add(2);
		getIndices().add(0);getIndices().add(2);getIndices().add(3);
		getIndices().add(0);getIndices().add(1);getIndices().add(5);
		getIndices().add(0);getIndices().add(4);getIndices().add(5);
		getIndices().add(1);getIndices().add(2);getIndices().add(6);
		getIndices().add(1);getIndices().add(5);getIndices().add(6);
		getIndices().add(2);getIndices().add(3);getIndices().add(7);
		getIndices().add(2);getIndices().add(6);getIndices().add(7);
		getIndices().add(3);getIndices().add(0);getIndices().add(4);
		getIndices().add(3);getIndices().add(4);getIndices().add(7);
		getIndices().add(4);getIndices().add(5);getIndices().add(6);
		getIndices().add(4);getIndices().add(6);getIndices().add(7);
		
		getIndices().add(0); getIndices().add(1);
		getIndices().add(0); getIndices().add(3);
		getIndices().add(0); getIndices().add(4);
		getIndices().add(1); getIndices().add(2);
		getIndices().add(1); getIndices().add(5);
		getIndices().add(2); getIndices().add(3);
		getIndices().add(2); getIndices().add(6);
		getIndices().add(3); getIndices().add(7);
		getIndices().add(4); getIndices().add(7);
		getIndices().add(4); getIndices().add(5);
		getIndices().add(5); getIndices().add(6);
		getIndices().add(6); getIndices().add(7);
		
		getParts().add(new Part(0, 35, TopologyType.TRIANGLETOPOLOGY));
		getParts().add(new Part(36, 23, TopologyType.LINETOPOLOGY));
		
		getColors().add(new Col(0xffcccc));
		getColors().add(new Col(0xffcccc));
		getColors().add(new Col(0xff8080));
		getColors().add(new Col(0xff8080));
		getColors().add(new Col(0xcc5200));
		getColors().add(new Col(0xcc5200));
		getColors().add(new Col(0x662900));
		getColors().add(new Col(0x662900));
		getColors().add(new Col(0x800000));
		getColors().add(new Col(0x800000));
		getColors().add(new Col(0xff0066));
		getColors().add(new Col(0xff0066));
	}
}
