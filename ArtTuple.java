package code;

import java.util.Queue;

public class ArtTuple {
	private Node current;
	private int reach,depth;
	private ArtTuple parent;
	Queue<Node> children = null;
	
	public ArtTuple(Node n,int r,ArtTuple p,int d){
		this.setCurrent(n);
		this.setReach(r);
		this.setDepth(d);
		this.setParent(p);
		
	}

	public Node getCurrent() {
		return current;
	}

	public void setCurrent(Node current) {
		this.current = current;
	}

	public int getReach() {
		return reach;
	}

	public void setReach(int reach) {
		this.reach = reach;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public ArtTuple getParent() {
		return parent;
	}

	public void setParent(ArtTuple parent) {
		this.parent = parent;
	}

}
