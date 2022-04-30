package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Collection.Triple;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;
import GraphAlgorithms.BinaryHeapEdge;

public class UndirectedValuedGraph extends UndirectedGraph {

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public UndirectedValuedGraph(int[][] matrixVal) {
        super();
        this.order = matrixVal.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrixVal[n.getLabel()].length; j++) {
                UndirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn, matrixVal[n.getLabel()][j]);
                    nn.getNeighbours().put(n, matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    //PRIM algorithm using BinaryHeapEdge

    //Q5.How do you use the binary heap structure to implement the Prim's algorithm?
    //We use the binary heap to get the lowest edge in the graph.

    //Which operation is needed to be implemented in the binary heap?
    //The operation of containing the cost and the 2 nodes of the edge.

    //What is the complexity gains in comparison to a classical implementation?
    //The complexity gains is made when we research the lowest edge in the graph.
    //The classical implementation need to compare all the edges in the graph. O(m)
    //With the binary heap, the remove operation is made in O(log(m)) time.


    public UndirectedValuedGraph prim(UndirectedNode start) {

        // the returned graph
        UndirectedValuedGraph res = new UndirectedValuedGraph(new int[this.order][this.order]);

        // the binary heap of the edges and their costs
        BinaryHeapEdge heap = new BinaryHeapEdge();

        // the list of the nodes already visited
        List<UndirectedNode> visited = new ArrayList<>();

        // the current node
        UndirectedNode current = start;

        // when all the nodes are visited, the algorithm is finished
        while (visited.size() < res.order-1) {

            // add the current node to the list of visited nodes
            visited.add(current);

            //add the edges in the binary heap
            for (UndirectedNode n : current.getNeighbours().keySet()) {
                // if the node is not visited yet
                if (!visited.contains(n)) {
                    heap.insert(current, n, current.getNeighbours().get(n));
                }
            }

            // get the min cost edge
            Triple<UndirectedNode, UndirectedNode, Integer> min = heap.remove();

            // while the edge doesn't link 2 nodes already visited
            while (visited.contains(min.getFirst()) && visited.contains(min.getSecond())) {
                min = heap.remove();
            }

            // add the edge to the graph
            res.addEdge(res.getNodeOfList(min.getFirst()), res.getNodeOfList(min.getSecond()), min.getThird());

            // set the current node to the other node of the edge
            current = min.getSecond();

        }
        return res;
    }


    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        if (x.getNeighbours().containsKey(y)) {
            return;
        }
        x.getNeighbours().put(y, cost);
        y.getNeighbours().put(x, cost);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append("(").append(sn).append(",").append(n.getNeighbours().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 15, false, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        UndirectedValuedGraph al = new UndirectedValuedGraph(matrixValued);
        System.out.println(al);
        // test of addEdge
        al.addEdge(al.getNodes().get(0), al.getNodes().get(1), 1);
        al.addEdge(al.getNodes().get(0), al.getNodes().get(2), 2);
        al.addEdge(al.getNodes().get(0), al.getNodes().get(3), 3);
        System.out.println(al);

        // test of prim
        UndirectedValuedGraph res = al.prim(al.getNodes().get(0));
        System.out.println(res);


    }
}
