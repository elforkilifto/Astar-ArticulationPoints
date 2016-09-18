package code;
public class aStarTuple {

	private Node current;
	private Node from;
	private double runningCost;
	private double heuristicCost;
	public double totalCost = runningCost+heuristicCost;
	
	
	public aStarTuple(Node c, Node f, double r,double h){
		
		this.setCurrent(c);
		this.setFrom(f);
		this.setRunningCost(r);
		this.setHeuristicCost(h);
		
		
	}


	public Node getCurrent() {
		return current;
	}


	public void setCurrent(Node current) {
		this.current = current;
	}


	public Node getFrom() {
		return from;
	}


	public void setFrom(Node from) {
		this.from = from;
	}


	public double getRunningCost() {
		return runningCost;
	}


	public void setRunningCost(double runningCost) {
		this.runningCost = runningCost;
	}


	public double getHeuristicCost() {
		return heuristicCost;
	}


	public void setHeuristicCost(double heuristicCost) {
		this.heuristicCost = heuristicCost;
	}
}
