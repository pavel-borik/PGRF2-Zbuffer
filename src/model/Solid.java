package model;

import java.util.ArrayList;
import java.util.List;

import transforms.Col;

public class Solid implements Geometry, Topology {
	protected List<Vertex> vertices = new ArrayList<>();
	protected List<Integer> indices = new ArrayList<>();
	protected List<Part> parts = new ArrayList<>();
	protected List<Col> colors = new ArrayList<>();
	
	@Override
	public List<Integer> getIndices() {
		return indices;
	}

	@Override
	public List<Part> getParts() {
		return parts;
	}
	
	@Override
	public Part getPart(int partIndex) {
		return parts.get(partIndex);
	}
	
	@Override
	public List<Vertex> getVertices() {
		return vertices;
	}

	public List<Col> getColors() {
		return colors;
	}
	
}
