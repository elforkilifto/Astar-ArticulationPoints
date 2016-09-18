package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class astar  {

	private Node start, temp, end;
	private PriorityQueue<aStarTuple> que;
	private Set<Node> nodes;
	tupleComparator c = new tupleComparator();
	List<Node> pathfrom = new ArrayList<Node>();
	private List<Segment>highlightSeg = new ArrayList<Segment>();


	public astar(Node start, Node end, Set<Node> s) {   // information passed in once user clicks a*and 2 nodes in GUI.
		que = new PriorityQueue<aStarTuple>(c);		
		this.start = start;
		this.end = end;
		nodes = s;

	}

	public void setNodes(Set<Node> s) {            //set all the nodes to not visited and that they dont have a previous node.
		for (Node n : s) {
			n.setVisited(false);
			n.setPreviousNode(null);
		}
	}

	public List<Node> runAStar() {
		aStarTuple tup = new aStarTuple(start,null,0,start.location.distance(end.location));   //create first tuple of start Node
		que.add(tup);                                                                          
		while (!que.isEmpty()){
			aStarTuple next= que.poll();
			if(!next.getCurrent().isVisited()){   		
				if(next.getCurrent()==(end)){                            // closing condition check if this current is the end if it is
					List<Node> pathfound = Path(next.getCurrent());      // it calls the path method which returns a list of its parent path.
					return pathfound;   			
				}
				next.getCurrent().setVisited(true);

				segloop: for(Segment s: next.getCurrent().segments){      // checks which direction the  the path is heading.
					if (next.getCurrent()==s.start)
						temp = s.end;
					else if(s.end == next.getCurrent())temp=s.start;
					if(temp.isVisited()) continue segloop;
					
					double costToNeighbour = next.getRunningCost()+s.length;	                               // sets costs of current stage in the tuple 	
					temp.setPreviousNode(next.getCurrent());                                                   // sets each nodes previous node.
					double estimate = temp.location.distance(end.location);
					aStarTuple Neighbour = new aStarTuple(temp,next.getCurrent(),costToNeighbour,estimate);   // make tuple for neighbours             				
					Neighbour.totalCost = costToNeighbour+estimate;                                           // keeps running costs for each neighbour in tuple
					                                                                                          // key to find the shortest path.
					que.add(Neighbour);
				}


			}
		
		}
		
		return null;
	}

	
public List<Node> Path(Node current){
	
	
		pathfrom = new ArrayList<Node>();                                      //method returns a list of nodes of the paths
		highlightSeg = new ArrayList<Segment>();                               // parents nodes/roads also adds any segments that need 
		while(current.getPreviousNode()!=null){                                //to be highlighted.
			for (Segment s:current.segments){                                  
				if(current.getPreviousNode().segments.contains(s)){
					this.highlightSeg.add(s);
					
				}
			}
			pathfrom.add(current);
			current = current.getPreviousNode();
		}
		pathfrom.add(current);
		
		return pathfrom;
	
}
public List<Segment> getHighlight(){
	return highlightSeg;
}


public class tupleComparator implements Comparator<aStarTuple>{

	

		@Override
		public int compare(aStarTuple o1, aStarTuple o2) {
			if(o1.totalCost<o2.totalCost)return -1;
			else if (o1.totalCost>o2.totalCost)return 1;
			return 0;
		}
}



	
}



