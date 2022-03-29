package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	private  int[][] matrixCosts;	// The graph with Costs

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super();
		this.order = mat.length;
		this.matrix = new int[this.order][this.order];
		this.matrixCosts = new int[this.order][this.order];
		for(int i =0;i<this.order;i++){
			for(int j=i;j<this.order;j++){
				int val = mat[i][j];
				int cost = matrixVal[i][j]; 
				this.matrix[i][j] = val;
				this.matrix[j][i] = val;
				this.matrixCosts[i][j] = cost;
				this.matrixCosts[j][i] = cost; 
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
     * removes the edge (x,y) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
	@Override
	public void removeEdge(UndirectedNode x, UndirectedNode y) {
		super.removeEdge(x, y);
		if(!isEdge(x, y)){
			this.matrixCosts[x.getLabel()][y.getLabel()] = 0;
			this.matrixCosts[y.getLabel()][x.getLabel()] = 0;
		}
	}

	/**
     * adds the edge (x,y,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
	public void addEdge(UndirectedNode x, UndirectedNode y, int cost ) {
		super.addEdge(x,y);
		if(this.matrixCosts[x.getLabel()][y.getLabel()] == 0) {
			this.matrixCosts[x.getLabel()][y.getLabel()] = cost;
			this.matrixCosts[y.getLabel()][x.getLabel()] = cost;
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
		int[][] matrix = GraphTools.generateGraphData(10, 20, true, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		GraphTools.afficherMatrix(matrix);
		AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);
		// A completer

		System.out.println("• Add arc between 4 and 2 of cost 8 if no weight defined");
		UndirectedNode node1 = new UndirectedNode(4),
				node2 = new UndirectedNode(2);
		System.out.println("\tisArc ? " + am.isEdge(node1, node2) + " of cost " + am.getMatrixCosts()[4][2]);
		System.out.println("\tAdding arc...");
		am.addEdge(node1, node2, 8);
		System.out.println("\tisArc ? " + am.isEdge(node1, node2) + " of cost " + am.getMatrixCosts()[4][2] + "\n");


		System.out.println("• Remove arc between 0 and 7");
		node1 = new UndirectedNode(0);
		node2 = new UndirectedNode(7);
		System.out.println("\tisArc ? " + am.isEdge(node1, node2) + " of cost " + am.getMatrixCosts()[0][7]);
		System.out.println("\tRemoving arc...");
		am.removeEdge(node1, node2);
		System.out.println("\tisArc ? " + am.isEdge(node1, node2) + " of cost " + am.getMatrixCosts()[0][7] + "\n");
	}

}
