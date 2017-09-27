package model;

import transforms.Col;
import transforms.Point3D;

public class Axis extends Solid {
	public Axis() {
		getVertices().add(new VertexBase(new Point3D(0, 0, 0)));
		getVertices().add(new VertexBase(new Point3D(3, 0, 0)));
		getVertices().add(new VertexBase(new Point3D(0, 3, 0)));
		getVertices().add(new VertexBase(new Point3D(0, 0, 3)));

		getIndices().add(0);
		getIndices().add(1);
		getIndices().add(0);
		getIndices().add(2);
		getIndices().add(0);
		getIndices().add(3);
		
		getParts().add(new Part(0, 5, TopologyType.AXIS));
		
		getColors().add(new Col(0x66ff66));
		getColors().add(new Col(0x0000cc));
		getColors().add(new Col(0xff33cc));


	}
}
