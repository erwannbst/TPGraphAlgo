package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;

public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	private  int[][] matrixCosts;	// The graph with Costs

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super();
		this.order = mat.length;
		this.matrix = new int[this.order][this.order];
		this.matrixCosts = new int[this.order][this.order];
		for(int i =0;i<this.order;i++){
			for(int j=0;j<this.order;j++){
				int val = mat[i][j];
				int cost = matrixVal[i][j]; 
				this.matrix[i][j] = val;				
				this.matrixCosts[i][j] = cost; 
				this.m += val;					
			}
		}
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------
	
	/**
	 * @return the matrix with costs of the graph
 	 */
	public int[][] getMatrixCosts() {
		return matrixCosts;
	}

	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	/**
     * removes the arc (from,to) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		super.removeArc(from, to);
		if(!isArc(from, to)){
			this.matrixCosts[from.getLabel()][to.getLabel()] = 0;
		}
	}

	/**
     * adds the arc (from,to,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
	public void addArc(DirectedNode from, DirectedNode to, int cost ) {
		super.addArc(from,to);
		if(this.matrixCosts[from.getLabel()][to.getLabel()] == 0) {
			this.matrixCosts[from.getLabel()][to.getLabel()] = cost;
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder(super.toString() + "\n Matrix of Costs: \n");
		for (int[] matrixCost : this.matrixCosts) {
			for (int i : matrixCost) {
				s.append(i).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix = GraphTools.generateGraphData(10, 30, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);
		// A completer

		System.out.println("??? Add arc between 4 and 2 of cost 8 if no weight defined");
		DirectedNode node1 = new DirectedNode(4),
				node2 = new DirectedNode(2);
		System.out.println("\tisArc ? " + am.isArc(node1, node2) + " of cost " + am.getMatrixCosts()[4][2]);
		System.out.println("\tAdding arc...");
		am.addArc(node1, node2, 8);
		System.out.println("\tisArc ? " + am.isArc(node1, node2) + " of cost " + am.getMatrixCosts()[4][2] + "\n");


		System.out.println("??? Delete arc between 0 and 3");
		node1 = new DirectedNode(0);
		node2 = new DirectedNode(3);
		System.out.println("\tisArc ? " + am.isArc(node1, node2) + " of cost " + am.getMatrixCosts()[0][3]);
		System.out.println("\tDeleting arc...");
		am.removeArc(node1, node2);
		System.out.println("\tisArc ? " + am.isArc(node1, node2) + " of cost " + am.getMatrixCosts()[0][3] + "\n");

	}
}
