package model;

public class Part{
	private int startIndex;
	private int count;
	private TopologyType tt;
	
	public Part(int startIndex, int count, TopologyType tt) {
		this.startIndex = startIndex;
		this.count = count;
		this.tt = tt;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getCount() {
		return count;
	}

	public TopologyType getTopology() {
		return tt;
	}
	
	
}
