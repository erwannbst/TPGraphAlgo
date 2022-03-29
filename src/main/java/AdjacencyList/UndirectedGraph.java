package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

public class UndirectedGraph extends AbstractListGraph<UndirectedNode> implements IUndirectedGraph {

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<UndirectedNode> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
    }

    public UndirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(this.makeNode(i));
        }
        for (UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrix[n.getLabel()].length; j++) {
            	UndirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn,0);
                    nn.getNeighbours().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public UndirectedGraph(UndirectedGraph g) {
        super();
        this.order = g.getNbNodes();
        this.m = g.getNbEdges();
        this.nodes = new ArrayList<>();
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (UndirectedNode n : g.getNodes()) {
        	UndirectedNode nn = this.getNodes().get(n.getLabel());
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
            	UndirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getNeighbours().put(snn,0);
                snn.getNeighbours().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbEdges() {
        return this.m;
    }

    @Override
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {  
    	return getNodeOfList(x).getNeighbours().containsKey(getNodeOfList(y));
    }

    @Override
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
    	if(isEdge(x,y)){
    		x.getNeighbours().remove(y);
    		y.getNeighbours().remove(x);
    	}
    }

    @Override
    public void addEdge(UndirectedNode x, UndirectedNode y) {
    	if(!isEdge(x,y)){
    		x.addNeigh(y, 1);
    		y.addNeigh(x, 1);
    	}
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public UndirectedNode makeNode(int label) {
        return new UndirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }
    
    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (UndirectedNode j : nodes.get(i).getNeighbours().keySet()) {
                int IndNeigh = j.getLabel();
                matrix[i][IndNeigh] = 1;
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    //parcours en profondeur
    public void depthFirstSearch(UndirectedNode n) {
        if (this.nodes.contains(n)) {
            ArrayList<UndirectedNode> visited = new ArrayList<UndirectedNode>();
            int cpt = 0;
            depthFirstSearchRecursive(n, visited, cpt);
        }
    }

    //parcours en profondeur récursif
    public int depthFirstSearchRecursive(UndirectedNode n, ArrayList<UndirectedNode> visited, int cpt) {
        cpt++;
        System.out.println(n + " " + cpt);
        visited.add(n);
        for (UndirectedNode sn : n.getNeighbours().keySet()) {
            if (!visited.contains(sn)) {
                cpt = this.depthFirstSearchRecursive(sn, visited, cpt);
            }
        }
        cpt++;
        System.out.println(n + " " + cpt);
        return cpt;
    }


    //parcours en largeur
    public void breadthFirstSearch(UndirectedNode n) {
        if (this.nodes.contains(n)) {
            ArrayList<UndirectedNode> visited = new ArrayList<>();
            visited.add(n);
            int cpt = 1;
            System.out.println(n + " " + cpt);
            breadthFirstSearchRecursive(n, visited, cpt);
        }
    }

    //parcours en largeur récursif
    public int breadthFirstSearchRecursive(UndirectedNode n, ArrayList<UndirectedNode> visited, int cpt) {

        ArrayList<UndirectedNode> nodeToVisit = new ArrayList<>();

        for(UndirectedNode sn : n.getNeighbours().keySet()) {
            if(!visited.contains(sn)) {
                cpt++;
                System.out.println(sn + " " + cpt);
                nodeToVisit.add(sn);
                visited.add(sn);
            }
        }

        for (UndirectedNode sn : nodeToVisit) {
            cpt = this.breadthFirstSearchRecursive(sn, visited, cpt);
        }
        return cpt;
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println(al);
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        // A completer

        //test de remove edge
        System.out.println("test de remove edge");
        System.out.println("affichage du graphe");
        System.out.println(al);
        System.out.println("retrait de l'arete entre 2 et 1");
        al.removeEdge(al.getNodeOfList(new UndirectedNode(1)), al.getNodeOfList(new UndirectedNode(2)));
        System.out.println(al);

        //test de add edge
        System.out.println("\ntest de add edge");
        System.out.println("test de add edge");
        System.out.println("affichage du graphe");
        System.out.println(al);
        System.out.println("Ajout de l'arete entre 2 et 7");
        al.addEdge(al.getNodeOfList(new UndirectedNode(2)),al.getNodeOfList(new UndirectedNode(7)));
        System.out.println(al);

        //test de depthFirstSearch
        System.out.println("test de depthFirstSearch");
        System.out.println("affichage du graphe");
        System.out.println(al);
        al.depthFirstSearch(al.getNodeOfList(new UndirectedNode(1)));

        //test de breadthFirstSearch
        System.out.println("test de breadthFirstSearch");
        System.out.println("affichage du graphe");
        System.out.println(al);
        al.breadthFirstSearch(al.getNodeOfList(new UndirectedNode(1)));
    }

}
