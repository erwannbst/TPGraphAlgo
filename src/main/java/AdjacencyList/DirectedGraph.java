package AdjacencyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;

public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

    private static int _DEBBUG = 0;

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public DirectedGraph() {
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
                    n.getSuccs().put(nn, 0);
                    nn.getPreds().put(n, 0);
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
        for (DirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : g.getNodes()) {
            DirectedNode nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn, 0);
                snn.getPreds().put(nn, 0);
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
        to.getPreds().remove(from);
    }

    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
        from.addSucc(to, 1);
        to.addPred(from, 1);
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     *
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
        for (DirectedNode n : g.getNodes()) {
            n.getSuccs().clear();
            n.getPreds().clear();
        }

        for (int i = 0; i < g.getNbNodes(); i++) {
            for (int j = 0; j < g.getNbNodes(); j++) {
                if (this.isArc(this.getNodes().get(i), this.getNodes().get(j))) {
                    g.addArc(g.getNodes().get(j), g.getNodes().get(i));
                }
            }
        }

        return g;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (DirectedNode n : nodes) {
            s.append("successors of ").append(n).append(" : ");
            for (DirectedNode sn : n.getSuccs().keySet()) {
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    //parcours en profondeur
    public void depthFirstSearch(DirectedNode n) {
        if (this.nodes.contains(n)) {
            ArrayList<DirectedNode> visited = new ArrayList<DirectedNode>();
            int cpt = 0;
            depthFirstSearchRecursive(n, visited, cpt);
        }
    }

    //parcours en profondeur récursif
    public int depthFirstSearchRecursive(DirectedNode n, ArrayList<DirectedNode> visited, int cpt) {
        cpt++;
        System.out.println(n + " " + cpt);
        visited.add(n);
        for (DirectedNode sn : n.getSuccs().keySet()) {
            if (!visited.contains(sn)) {
                cpt = this.depthFirstSearchRecursive(sn, visited, cpt);
            }
        }
        cpt++;
        System.out.println(n + " " + cpt);
        return cpt;
    }

    //parcours en largeur
    public void breadthFirstSearch(DirectedNode n) {
        if (this.nodes.contains(n)) {
            ArrayList<DirectedNode> visited = new ArrayList<>();
            visited.add(n);
            int cpt = 1;
            System.out.println(n + " " + cpt);
            breadthFirstSearchRecursive(n, visited, cpt);
        }
    }

    //parcours en largeur récursif
    public int breadthFirstSearchRecursive(DirectedNode n, ArrayList<DirectedNode> visited, int cpt) {

        ArrayList<DirectedNode> nodeToVisit = new ArrayList<>();

        for (DirectedNode sn : n.getSuccs().keySet()) {
            if (!visited.contains(sn)) {
                cpt++;
                System.out.println(sn + " " + cpt);
                nodeToVisit.add(sn);
                visited.add(sn);
            }
        }

        for (DirectedNode sn : nodeToVisit) {
            cpt = this.breadthFirstSearchRecursive(sn, visited, cpt);
        }
        return cpt;
    }

    //explore the graph in a depth-first manner. When we reach an end node, we push it in the stack
    public int[] explore(DirectedNode n) {
        int[] end = new int[this.order];
        int cpt = 0;
        ArrayList<DirectedNode> visited = new ArrayList<>();
        cpt = exploreRecursive(n, end, visited, cpt);

        for (DirectedNode nn : this.getNodes()) {
            if (!visited.contains(nn)) {
                cpt = exploreRecursive(nn, end, visited, cpt);
            }
        }

        return end;
    }

    //recursive function to explore the graph
    public int exploreRecursive(DirectedNode n, int[] end, ArrayList<DirectedNode> visited, int cpt) {
        visited.add(n);
        for (DirectedNode sn : n.getSuccs().keySet()) {
            if (!visited.contains(sn)) {
                cpt = exploreRecursive(sn, end, visited, cpt);
            }
        }
        end[cpt] = n.getLabel();
        cpt++;
        return cpt;
    }

    //compute the strongly connected components of the graph
    public ArrayList<ArrayList<DirectedNode>> computeSCC() {

        //get the end list
        int[] end = this.explore(this.getNodes().get(0));

        //reverse the graph
        DirectedGraph inverse = (DirectedGraph) this.computeInverse();

        //initialize the list of the strongly connected components
        ArrayList<ArrayList<DirectedNode>> scc = new ArrayList<>();

        //initialize the visited list
        ArrayList<DirectedNode> visited = new ArrayList<>();

        //for each node in the end list
        //if it is not visited
        //explore the graph in a depth-first manner.
        //when we finish we add the list of the nodes to the list of the strongly connected components
        for (int i = end.length - 1; i >= 0; i--) {
            DirectedNode n = inverse.getNodeOfList(new DirectedNode(end[i]));
            if (!visited.contains(n)) {
                visited.add(n);
                ArrayList<DirectedNode> sccTmp = new ArrayList<>();
                sccTmp.add(n);
                scc.add(sccTmp);
                exploreSCC(n, visited, sccTmp);
            }
        }
        return scc;
    }

    //recursive part of the computeSCC function
    public void exploreSCC(DirectedNode n, ArrayList<DirectedNode> visited, ArrayList<DirectedNode> scc) {

        //explore in a depth-first manner the node n
        for (DirectedNode sn : n.getSuccs().keySet()) {
            //if it's successor is not visited yet
            if (!visited.contains(sn)) {

                //add it to the list of the strongly connected components
                scc.add(sn);

                //mark it as visited
                visited.add(sn);

                //explore it
                exploreSCC(sn, visited, scc);

                //when we reach the end of the exploration we get a list of nodes fully connected to the node n
            }
        }
    }


    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10_000, 20_000, false, false, false, 100001);
//        GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
//        System.out.println(al);

//        //test de la méthode isArc
//        System.out.println("test de la méthode isArc");
//        System.out.println("graphe de base");
//        System.out.println(al);
//        System.out.println("confirmation de l'arc 1 vers 2");
//        System.out.println(al.isArc(al.getNodeOfList(new DirectedNode(1)), al.getNodeOfList(new DirectedNode(2))));
//
//        //test de la méthode removeArc
//        System.out.println("test de la méthode removeArc");
//        System.out.println("graphe de base");
//        System.out.println(al);
//        System.out.println("retrait de l'arc 1 vers 2");
//        al.removeArc(al.getNodeOfList(new DirectedNode(1)), al.getNodeOfList(new DirectedNode(2)));
//        System.out.println(al);
//
//        //test de la méthode addArc
//        System.out.println("test de la méthode addArc");
//        System.out.println("graphe de base");
//        System.out.println(al);
//        System.out.println("ajout de l'arc 1 vers 8");
//        al.addArc(al.getNodeOfList(new DirectedNode(1)), al.getNodeOfList(new DirectedNode(8)));
//        System.out.println(al);
//
//        //test de la methode compute inverse
//        System.out.println("test de la méthode compute inverse");
//        System.out.println("graphe de base");
//        System.out.println(al);
//        System.out.println("inversion du graph");
//        System.out.println(al.computeInverse());
//
//        //parcours en profondeur
//        System.out.println("graph de base");
//        System.out.println(al);
//        System.out.println("parcours en profondeur");
//        al.depthFirstSearch(al.getNodeOfList(new DirectedNode(1)));
//
//        //parcours en largeur
//        System.out.println("graph de base");
//        System.out.println(al);
//        System.out.println("parcours en largeur");
//        al.breadthFirstSearch(al.getNodeOfList(new DirectedNode(1)));

//        //exploration du graphe
//        System.out.println("graph de base");
//        System.out.println(al);
//        System.out.println("exploration du graphe");
//        int [] end = al.explore(al.getNodeOfList(new DirectedNode(1)));
//        for (int i = 0; i < end.length; i++) {
//            System.out.print(end[i] + " ");
//        }

        //test de la méthode computeSCC
//        System.out.println("\ntest de la méthode computeSCC");
//        System.out.println("graphe de base");
//        System.out.println(al);
//        System.out.println("calcul des composantes fortement connexes");

        //recording the time of the computation
        long startTime = System.currentTimeMillis();

        ArrayList<ArrayList<DirectedNode>> scc = al.computeSCC();

        //end of the recording
        long endTime = System.currentTimeMillis();
        System.out.println("temps d'execution : " + (endTime - startTime) + " ms");

//        for (int i = 0; i < scc.size(); i++) {
//            System.out.println("\ncomposante " + (i+1));
//            for (int j = 0; j < scc.get(i).size(); j++) {
//                System.out.print(scc.get(i).get(j).getLabel() + " ");
//            }
//        }

        //On remarque que le temps d'exécution est bien plus court que celui de la méthode computeSCC de AdjacencyMatrixDirectedGraph

       //temps d'execution : 1163 ms pour n = 10_000 et m = 20_000

    }
}
