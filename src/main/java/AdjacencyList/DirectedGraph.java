package AdjacencyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;

public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

	private static int _DEBBUG =0;
		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<DirectedNode>();
	}

    public DirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,0);
                    nn.getPreds().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public DirectedGraph(DirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : g.getNodes()) {
        	DirectedNode nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn,0);
                snn.getPreds().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public boolean isArc(DirectedNode from, DirectedNode to) {
    	return from.getSuccs().containsKey(to);
    }

    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
    	from.getSuccs().remove(to);
    }

    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
    	from.addSucc(to, 1);
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public DirectedNode makeNode(int label) {
        return new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
                int IndSucc = j.getLabel();
                matrix[i][IndSucc] = 1;
            }
        }
        return matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {
        DirectedGraph g = new DirectedGraph(this);
        // A completer
        // --- Succ <--> pred
        Map<DirectedNode, Integer> tmpPred = new HashMap<DirectedNode, Integer>();
        Map<DirectedNode, Integer> tmpSuccs = new HashMap<DirectedNode, Integer>();

        for (DirectedNode n : g.getNodes()) {
            System.out.println(getNodeOfList(n).getSuccs());
            System.out.println(getNodeOfList(n).getPreds());

            tmpPred.putAll(new HashMap<DirectedNode, Integer>(getNodeOfList(n).getPreds()));
            tmpSuccs.putAll(new HashMap<DirectedNode, Integer>(getNodeOfList(n).getSuccs()));


            for (Map.Entry<DirectedNode, Integer> nP : tmpPred.entrySet()) {
                g.removeArc(new DirectedNode(n.getLabel()), new DirectedNode(nP.getKey().getLabel()));
                g.addArc(n, nP.getKey());
            }
            for (Map.Entry<DirectedNode, Integer> nP : tmpSuccs.entrySet()) {
                g.removeArc(new DirectedNode(nP.getKey().getLabel()), new DirectedNode(n.getLabel()));
                //g.addArc(n,nP.getKey());
            }

            getNodeOfList(n).getPreds().clear();
            getNodeOfList(n).getPreds().putAll(getNodeOfList(n).getSuccs());

            getNodeOfList(n).getSuccs().clear();

            getNodeOfList(n).getSuccs().putAll(tmpPred);

            System.out.println("----");
            System.out.println(getNodeOfList(n).getSuccs());
            System.out.println(getNodeOfList(n).getPreds());
            System.out.println("******");
            tmpPred.clear();
            //tmpSuccs.clear();

        }

        return g;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);
        // A completer

        //test de la méthode isArc
        System.out.println("test de la méthode isArc");
        System.out.println("graphe de base");
        System.out.println(al);
        System.out.println("confirmation de l'arc 1 vers 2");
        System.out.println(al.isArc(al.getNodeOfList(new DirectedNode(1)), al.getNodeOfList(new DirectedNode(2))));

        //test de la méthode removeArc
        System.out.println("test de la méthode removeArc");
        System.out.println("graphe de base");
        System.out.println(al);
        System.out.println("retrait de l'arc 1 vers 2");
        al.removeArc(al.getNodeOfList(new DirectedNode(1)), al.getNodeOfList(new DirectedNode(2)));
        System.out.println(al);

        //test de la méthode addArc
        System.out.println("test de la méthode addArc");
        System.out.println("graphe de base");
        System.out.println(al);
        System.out.println("ajout de l'arc 1 vers 8");
        al.addArc(al.getNodeOfList(new DirectedNode(1)), al.getNodeOfList(new DirectedNode(8)));
        System.out.println(al);


    }
}
