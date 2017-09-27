package model;

import transforms.Col;
import transforms.Point3D;

public class Simplex extends Solid {
	public Simplex() {
		getVertices().add(new VertexBase(new Point3D(0.5,0.5,0.5)));	
		getVertices().add(new VertexBase(new Point3D(2,0.5,0.5)));	
		getVertices().add(new VertexBase(new Point3D(0.5,2,0.5)));	
		getVertices().add(new VertexBase(new Point3D(0.5,0.5,2)));
		
		getIndices().add(0);getIndices().add(1);getIndices().add(2);
		getIndices().add(1);getIndices().add(2);getIndices().add(3);
		getIndices().add(0);getIndices().add(2);getIndices().add(3);
		getIndices().add(0);getIndices().add(1);getIndices().add(3);
		
		getIndices().add(0); getIndices().add(1);
		getIndices().add(1); getIndices().add(2);
		getIndices().add(2); getIndices().add(0);
		getIndices().add(0); getIndices().add(3);
		getIndices().add(1); getIndices().add(3);
		getIndices().add(2); getIndices().add(3);
		
		getParts().add(new Part(0, 11, TopologyType.TRIANGLETOPOLOGY));
		getParts().add(new Part(12, 11, TopologyType.LINETOPOLOGY));
		
		getColors().add(new Col(0xececf8));
		getColors().add(new Col(0xc7c7ea));
		getColors().add(new Col(0x7d7dcf));
		getColors().add(new Col(0x1c1c4a));
	}
}
