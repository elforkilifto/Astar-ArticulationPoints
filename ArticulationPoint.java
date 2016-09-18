package code;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class ArticulationPoint {

	public void initArtPoint(Collection<Node> allNodes){

		for (Node n : allNodes){                   // set all nodes  that they arnt articulation points or visited
			n.setDepth(Integer.MAX_VALUE);         // set the maximum depth of all of them infinite.
			n.setArtPoint(false);
			n.setVisited(false);
		}
		for (Node root:allNodes){  //  Initiate the algorithm using the set of all the nodes passed in the mapper class
			int numSubtree = 0;
			if(!root.isVisited()){
				root.setDepth(0);
				root.setVisited(true);
				
				for(Node neighbour:root.neighbours()){
					if(!neighbour.isVisited()){
						artAlgor(neighbour,root);            //run the main algorithm with the root and all of its adjacent neighbours,
						numSubtree++;                       //if node has a subtree  then it is an articulation point
					}				
				}

			}
			if(numSubtree>1)root.setArtPoint(true);
		}

	}



	private void artAlgor(Node neighbour, Node root) {
        
		neighbour.setVisited(true);
		root.setVisited(true);
		
		Stack<ArtTuple> fringe = new Stack<ArtTuple>();                           // create new Stack of tuples
		fringe.push(new ArtTuple(neighbour,0,new ArtTuple(root,0,null,0),1));     // push our neighnour onto the stack with the root 
		                                                                          // as its parent.
		while (!fringe.isEmpty()){
			ArtTuple elem = fringe.peek();
			Node temp=elem.getCurrent();
			if(elem.children == null){                    // first case  Tuples queue children is empty
				temp.setDepth(elem.getDepth());
				elem.setReach(elem.getDepth());
				elem.children = new ArrayDeque<Node>();
				for(Node n:temp.neighbours()){			
						if(n!=elem.getParent().getCurrent()){
							elem.children.add(n);	
					}
				}
			}else if(!elem.children.isEmpty()){            // second case the Tuples queue is not empty 
				Node child = elem.children.poll();
				if (child.getDepth() < Integer.MAX_VALUE){
					elem.setReach(Math.min(elem.getReach(),child.getDepth() ));
				}else{
					child.setVisited(true);
					fringe.push(new ArtTuple(child,elem.getReach(),elem,(elem.getCurrent().getDepth()+1)));
				}			
			}else{                                        // 3rd case everything on the queue has already been prcessed check it the
				if(temp!=neighbour){                      // parent is an articulation point.
					if(elem.getReach()>=elem.getParent().getDepth()){
						elem.getParent().getCurrent().setArtPoint(true);
					}
					elem.getParent().setReach(Math.min(elem.getParent().getReach(), elem.getReach()));
				}
				fringe.pop();
			}
		}

	}



}
