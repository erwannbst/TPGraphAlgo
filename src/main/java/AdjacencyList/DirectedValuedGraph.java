package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class DirectedValuedGraph extends DirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.order = matrixVal.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrixVal[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,matrixVal[n.getLabel()][j]);
                    nn.getPreds().put(n,matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        if (from.getSuccs().containsKey(to)) {
            return;
        }
        from.getSuccs().put(to, cost);
        to.getPreds().put(from, cost);
        this.m++;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
            	s.append("(").append(sn).append(",").append(n.getSuccs().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
        System.out.println(al);
        //test of the addArc method
        DirectedNode n1 = al.getNodes().get(0);
        DirectedNode n2 = al.getNodes().get(1);
        DirectedNode n3 = al.getNodes().get(2);
        DirectedNode n4 = al.getNodes().get(3);
        DirectedNode n5 = al.getNodes().get(4);
        DirectedNode n6 = al.getNodes().get(5);
        DirectedNode n7 = al.getNodes().get(6);
        DirectedNode n8 = al.getNodes().get(7);
        DirectedNode n9 = al.getNodes().get(8);
        DirectedNode n10 = al.getNodes().get(9);
        al.addArc(n1, n2, 1);
        al.addArc(n1, n3, 2);
        al.addArc(n1, n4, 3);
        al.addArc(n2, n5, 4);
        al.addArc(n2, n6, 5);
        al.addArc(n3, n7, 6);
        al.addArc(n3, n8, 7);
        al.addArc(n4, n9, 8);
        al.addArc(n4, n10, 9);
        System.out.println(al);
    }
	
}
