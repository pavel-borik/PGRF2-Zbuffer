package model;

import java.util.List;

public interface Topology {
	
	List<Integer> getIndices();
	List<Part> getParts();
	Part getPart(int partIndex);
}
