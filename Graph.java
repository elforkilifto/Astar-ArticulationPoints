package code;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This represents the data structure storing all the roads, nodes, and
 * segments, as well as some information on which nodes and segments should be
 * highlighted.
 * 
 * @author tony
 */
public class Graph {
	// map node IDs to Nodes.
	Map<Integer, Node> nodes = new HashMap<>();
	// map road IDs to Roads.
	Map<Integer, Road> roads;
	// just some collection of Segments.
	Collection<Segment> segments;

	Collection<Node> artNode;

	Node highlightedNode;
	Collection<Road> highlightedRoads = new HashSet<>();
	List<Node> aStarHighlight = new ArrayList<Node>();
	List<Segment> segHighlight = new ArrayList<Segment>();

	public Graph(File nodes, File roads, File segments, File polygons) {
		this.nodes = Parser.parseNodes(nodes, this);
		this.roads = Parser.parseRoads(roads, this);
		this.segments = Parser.parseSegments(segments, this);
	}

	public void draw(Graphics g, Dimension screen, Location origin, double scale) {
		// a compatibility wart on swing is that it has to give out Graphics
		// objects, but Graphics2D objects are nicer to work with. Luckily
		// they're a subclass, and swing always gives them out anyway, so we can
		// just do this.
		Graphics2D g2 = (Graphics2D) g;

		// draw all the segments.
		g2.setColor(Mapper.SEGMENT_COLOUR);
		for (Segment s : segments)
			s.draw(g2, origin, scale);

		// draw the segments of all highlighted roads.
		g2.setColor(Mapper.HIGHLIGHT_COLOUR);
		g2.setStroke(new BasicStroke(3));
		for (Road road : highlightedRoads) {
			for (Segment seg : road.components) {
				seg.draw(g2, origin, scale);
			}
		}

		// draw all the nodes.
		g2.setColor(Mapper.NODE_COLOUR);
		for (Node n : nodes.values())
			if (n.isArtPoint()) {
				g2.setColor(Color.PINK);
				n.draw(g2, screen, origin, scale);

			} else {
				g2.setColor(Mapper.NODE_COLOUR);
				n.draw(g2, screen, origin, scale);
			}

		// draw the highlighted node, if it exists.
		if (highlightedNode != null) {
			g2.setColor(Mapper.HIGHLIGHT_COLOUR);
			highlightedNode.draw(g2, screen, origin, scale);
		}

		if (aStarHighlight != null) {
			for (Node x : this.aStarHighlight) {
				g2.setColor(Mapper.HIGHLIGHT_COLOUR);
				x.draw(g2, screen, origin, scale);
			}
			if (segHighlight != null) {
				double totlength = 0;
				 Set<String> names = new HashSet<String>();
				for (Segment z : this.segHighlight) {
					names.add(z.road.name);
					totlength += z.length;
					g2.setColor(Mapper.HIGHLIGHT_COLOUR);
					z.draw(g2, origin, scale);	
				}
				if(totlength>0){
					System.out.println("\n");
					System.out.println("Route is  : ");
					for (String s : names) {
						System.out.println(s);
					}
					System.out.println("\n");
					System.out.println("Total Length is " + totlength);
					totlength = 0;
				}
			}
		}

	}

	public void aStarHighlight(List<Node> l) {
		this.aStarHighlight = l;

	}

	public void setSegHighlights(List<Segment> h) {
		this.segHighlight = h;
	}

	public void setHighlight(Node node) {
		this.highlightedNode = node;
	}

	public void setHighlight(Collection<Road> roads) {
		this.highlightedRoads = roads;
	}
}

// code for COMP261 assignments