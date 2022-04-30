package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import AdjacencyList.DirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixDirectedGraph extends AbstractMatrixGraph<DirectedNode> implements IDirectedGraph {

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyMatrixDirectedGraph() {
        super();
    }

    public AdjacencyMatrixDirectedGraph(int[][] M) {
        this.order = M.length;
        this.matrix = new int[this.order][this.order];
        for (int i = 0; i < this.order; i++) {
            for (int j = 0; j < this.order; j++) {
                this.matrix[i][j] = M[i][j];
                this.m += M[i][j];
            }
        }
    }

    public AdjacencyMatrixDirectedGraph(IDirectedGraph g) {
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        this.matrix = g.toAdjacencyMatrix();
    }

    //--------------------------------------------------
    // 					Accessors
    //--------------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    public List<Integer> getSuccessors(DirectedNode x) {
        List<Integer> v = new ArrayList<Integer>();
        for (int i = 0; i < this.matrix[x.getLabel()].length; i++) {
            if (this.matrix[x.getLabel()][i] > 0) {
                v.add(i);
            }
        }
        return v;
    }

    public List<Integer> getPredecessors(DirectedNode x) {
        List<Integer> v = new ArrayList<Integer>();
        for (int i = 0; i < this.matrix.length; i++) {
            if (this.matrix[i][x.getLabel()] > 0) {
                v.add(i);
            }
        }
        return v;
    }


    // ------------------------------------------------
    // 					Methods
    // ------------------------------------------------

    @Override
    public boolean isArc(DirectedNode from, DirectedNode to) {
        return this.matrix[from.getLabel()][to.getLabel()] >= 1;
    }

    /**
     * removes the arc (from,to) if there exists at least one between these nodes in the graph.
     */
    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
        if (isArc(from, to)) {
            this.matrix[from.getLabel()][to.getLabel()] -= 1;
        }
    }

    /**
     * Adds the arc (from,to). we allow multiple graph.
     */
    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
        this.matrix[from.getLabel()][to.getLabel()] += 1;
    }


    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    public int[][] toAdjacencyMatrix() {
        return this.matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {
        AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(this.matrix);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                am.matrix[i][j] = this.matrix[j][i];
            }
        }
        return am;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                s.append(anInt).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    //explore the graph in a depth-first manner. When we reach an end node, we push it in the stack
    public int[] explore(DirectedNode n) {
        int[] end = new int[this.order];
        int cpt = 0;
        ArrayList<Integer> visited = new ArrayList<>();
        cpt = exploreRecursive(n.getLabel(), end, visited, cpt);

        for (int i = 0; i < this.order; i++) {
            if (!visited.contains(i)) {
                cpt = exploreRecursive(i, end, visited, cpt);
            }
        }
        return end;
    }

    //recursive function to explore the graph
    private int exploreRecursive(int n, int[] end, ArrayList<Integer> visited, int cpt) {
        visited.add(n);
        for (int i = 0; i < this.order; i++) {
            if (this.matrix[n][i] == 1 && !visited.contains(i)) {
                cpt = exploreRecursive(i, end, visited, cpt);
            }
        }
        end[cpt] = n;
        cpt++;
        return cpt;
    }

    //compute the strongly connected components of the graph
    public ArrayList<ArrayList<DirectedNode>> computeSCC() {

        //get the end list
        int[] end = this.explore(new DirectedNode(0));

        //reverse the graph
        AdjacencyMatrixDirectedGraph inverse = (AdjacencyMatrixDirectedGraph) this.computeInverse();

        //initialize the list of the strongly connected components
        ArrayList<ArrayList<DirectedNode>> scc = new ArrayList<>();

        //initialize the visited list
        ArrayList<Integer> visited = new ArrayList<>();

        //for each node in the end list
        //if it is not visited
        //explore the graph in a depth-first manner.
        //when we finish we add the list of the nodes to the list of the strongly connected components
        for (int i = end.length - 1; i >= 0; i--) {
            int n = end[i];
            if (!visited.contains(n)) {
                visited.add(n);
                ArrayList<DirectedNode> sccTmp = new ArrayList<>();
                sccTmp.add(new DirectedNode(n));
                scc.add(sccTmp);
                exploreSCC(new DirectedNode(n), visited, sccTmp, inverse);
            }
        }
        return scc;
    }

    //recursive part of the computeSCC function
    public void exploreSCC(DirectedNode n, ArrayList<Integer> visited, ArrayList<DirectedNode> scc, AdjacencyMatrixDirectedGraph inverse) {

        //explore in a depth-first manner the node n
        for (int i = 0; i < this.order; i++) {
            //if it's successor is not visited yet
            if (!visited.contains(i) && inverse.matrix[n.getLabel()][i] == 1) {

                //add it to the list of the strongly connected components
                scc.add(new DirectedNode(i));

                //mark it as visited
                visited.add(i);

                //explore it
                exploreSCC(new DirectedNode(i), visited, scc, inverse);

                //when we reach the end of the exploration we get a list of nodes fully connected to the node n
            }
        }
    }


    public static void main(String[] args) {
        int[][] matrix2 = GraphTools.generateGraphData(10_000, 20_000, false, false, false, 100001);
        AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
//		System.out.println(am);
//		List<Integer> t = am.getSuccessors(new DirectedNode(1));
//		for (Integer integer : t) {
//			System.out.print(integer + ", ");
//		}
//		System.out.println();
//		List<Integer> t2 = am.getPredecessors(new DirectedNode(2));
//		for (Integer integer : t2) {
//			System.out.print(integer + ", ");
//		}

        //test de compute inverse
//		System.out.println("test de compute inverse");
//		System.out.println("matrice de base");
//		System.out.println(am);
//		System.out.println("matrice inverse");
//		System.out.println(am.computeInverse());
//
        //exploration du graphe
//		System.out.println("graph de base");
//		System.out.println(am);
//		System.out.println("exploration du graphe");
//		int [] end = am.explore(new DirectedNode(1));
//		for (int i = 0; i < end.length; i++) {
//			System.out.print(end[i] + " ");
//		}

        //test de la méthode computeSCC
        System.out.println("\ntest de la méthode computeSCC");
        System.out.println("graphe de base");
//		System.out.println(am);
        System.out.println("calcul des composantes fortement connexes");

        //record the time of the computation
        long startTime = System.currentTimeMillis();

        ArrayList<ArrayList<DirectedNode>> scc = am.computeSCC();

        //end of the record
        long endTime = System.currentTimeMillis();

        //print the time of the computation
        System.out.println("temps de calcul : " + (endTime - startTime) + " ms");

//		for (int i = 0; i < scc.size(); i++) {
//			System.out.println("\ncomposante " + (i+1));
//			for (int j = 0; j < scc.get(i).size(); j++) {
//				System.out.print(scc.get(i).get(j).getLabel() + " ");
//			}
//		}

        //On remarque que le temps de calcul est bien plus long que le temps de calcul de la méthode computeSCC de DirectedGraph.

        //temps de calcul : 617224 ms pour n = 10_000 et m = 20_000
    }
}
