package com.graphs.graphicengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.graphs.engine.data.Edge;
import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.Vertex;

public class GraphDrawer {
	
	private static int getMaxHeight(GraphContener graphContener){
		int maxH = 0;
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getY() > maxH){
				maxH = (int)v.getY();
			}
		}
		System.out.println("max y = " + maxH);
		return maxH;
	}
	
	private static int getMaxWidth(GraphContener graphContener){
		int maxW = 0;
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getX() > maxW){
				maxW = (int)v.getX();
			}
		}
		System.out.println("max x = " + maxW);
		return maxW;
	}
	
	private static int getMinHeight(GraphContener graphContener){
		int minH = Integer.MAX_VALUE;
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getY() < minH){
				minH = (int)v.getY();
			}
		}
		System.out.println("min y = " + minH);
		return minH;
	}
	
	private static int getMinWidth(GraphContener graphContener){
		int minW = Integer.MAX_VALUE;
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getX() < minW){
				minW = (int)v.getX();
			}
		}
		System.out.println("min x = " + minW);
		return minW;
	}
	
	public static BufferedImage generateImage(GraphContener graphContener){
		int minWidth = getMinWidth(graphContener);
		int minHeight = getMinHeight(graphContener);
		int maxWidth = getMaxWidth(graphContener);
		int maxHeight = getMaxHeight(graphContener);
/*
		if(minWidth > 0){
			minWidth = 0;
		}
		if(minHeight > 0){
			minHeight = 0;
		}
		*/
		System.out.println("Preparing to draw image of size : " + 
				(maxWidth + (-minWidth)+ 2 * Vertex.VERTEX_SIZE) + ", " + 
				(maxHeight + (-minHeight) + 2 * Vertex.VERTEX_SIZE));
		BufferedImage buff = new BufferedImage(maxWidth + (-minWidth)+ 2 * Vertex.VERTEX_SIZE,
				maxHeight + (-minHeight) + 2 * Vertex.VERTEX_SIZE, 
				BufferedImage.TYPE_INT_RGB);
		buff.getGraphics().setColor(Color.white);
		buff.getGraphics().fillRect(0, 0, maxWidth + (-minWidth)+ 2 * Vertex.VERTEX_SIZE,
				maxHeight + (-minHeight) + 2 * Vertex.VERTEX_SIZE);
		drawGraph((Graphics2D)buff.getGraphics(), graphContener, minWidth, minHeight, true);
		return buff;
	}
	

	public static void drawGraph(Graphics2D g, GraphContener graphContener, int transX, int transY, boolean translate){
		// save old ransformation
		AffineTransform saveAT = g.getTransform();
		
		if(!translate){
			g.translate(graphContener.getTranslationX(), graphContener.getTranslationY());
		}
		else{
			System.out.println("Translating image to draw : " + transX + ", " + transY);
			g.translate(-transX + Vertex.VERTEX_SIZE, -transY + Vertex.VERTEX_SIZE);
		}
		for(Iterator<Edge> iter = graphContener.getAllEdges().iterator();iter.hasNext();){
			Edge v = (Edge)iter.next();
			g.setColor(Color.black);
			g.drawLine((int)v.getA().getX(), (int)v.getA().getY(), 
					(int)v.getB().getX(), (int)v.getB().getY());
		}

		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v == graphContener.getSelectedVertex())
				g.setColor(Color.red);
			else
				g.setColor(Color.green);
			g.fillOval((int)(v.getX() - Vertex.VERTEX_SIZE/2), (int)(v.getY() - Vertex.VERTEX_SIZE/2), Vertex.VERTEX_SIZE, Vertex.VERTEX_SIZE);
			g.setColor(Color.black);
			g.drawOval((int)(v.getX() - Vertex.VERTEX_SIZE/2), (int)(v.getY() - Vertex.VERTEX_SIZE/2), Vertex.VERTEX_SIZE, Vertex.VERTEX_SIZE);
			if(v.getId().length() == 2)
				g.drawString(v.getId(), (int)(v.getX()-7), (int)(v.getY() + Vertex.VERTEX_SIZE/2-6));
			else if (v.getId().length() == 1)
				g.drawString(v.getId(), (int)(v.getX()-3), (int)(v.getY() + Vertex.VERTEX_SIZE/2-6));
			else
				g.drawString(v.getId().substring(0,1) + "..", (int)(v.getX()-7), (int)(v.getY() + Vertex.VERTEX_SIZE/2-6));
		}

		//restore transformation
		
		g.setTransform(saveAT);
	}
}
