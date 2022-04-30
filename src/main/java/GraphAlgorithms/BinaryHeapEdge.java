package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Collection.Triple;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class BinaryHeapEdge {

    /**
     * A list structure for a faster management of the heap by indexing
     */
    private List<Triple<UndirectedNode, UndirectedNode, Integer>> binh;

    public BinaryHeapEdge() {
        this.binh = new ArrayList<>();
    }

    public boolean isEmpty() {
        return binh.isEmpty();
    }

    /**
     * Insert a new edge in the binary heap
     *
     * @param from one node of the edge
     * @param to   one node of the edge
     * @param val  the edge weight
     */
    public void insert(UndirectedNode from, UndirectedNode to, int val) {
        Triple<UndirectedNode, UndirectedNode, Integer> edge = new Triple<>(from, to, val);
        binh.add(edge);
        int index = binh.size() - 1;

        while (index > 0 && binh.get(index).getThird() < binh.get(getFather(index)).getThird()) {
            swap(index, getFather(index));
            index = getFather(index);
        }

    }


    /**
     * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
     *
     * @return the edge with the minimal value (root of the binary heap)
     */
    public Triple<UndirectedNode, UndirectedNode, Integer> remove() {
        Triple<UndirectedNode, UndirectedNode, Integer> res = new Triple<>(binh.get(0).getFirst(), binh.get(0).getSecond(), binh.get(0).getThird());
        swap(0, binh.size() - 1);
        binh.remove(binh.size() - 1);

        int index = 0;
        int bestChild = getBestChildPos(index);
        while (!isLeaf(index) && binh.get(index).getThird() > binh.get(bestChild).getThird()) {
            swap(index, bestChild);
            index = bestChild;
            bestChild = getBestChildPos(bestChild);
        }
        return res;


    }

    public int getFather(int index) {
        return (index - 1) / 2;
    }

    public int getLeftChild(int index) {
        return 2 * index + 1;
    }

    public int getRightChild(int index) {
        return 2 * index + 2;
    }


    /**
     * From an edge indexed by src, find the child having the least weight and return it
     *
     * @param src an index of the list edges
     * @return the index of the child edge with the least weight
     */
    private int getBestChildPos(int src) {
        int lastIndex = binh.size() - 1;
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            int leftChild = getLeftChild(src);
            int rightChild = getRightChild(src);
            if(rightChild > lastIndex) {
                return leftChild;
            }else {
                Triple<UndirectedNode, UndirectedNode, Integer> left = binh.get(leftChild);
                Triple<UndirectedNode, UndirectedNode, Integer> right = binh.get(rightChild);
                if (left.getThird() < right.getThird()) {
                    return leftChild;
                } else {
                    return rightChild;
                }
            }
        }
    }

    private boolean isLeaf(int src) {
        return (getLeftChild(src) >= binh.size());
    }


    /**
     * Swap two edges in the binary heap
     *
     * @param father an index of the list edges
     * @param child  an index of the list edges
     */
    private void swap(int father, int child) {
        Triple<UndirectedNode, UndirectedNode, Integer> temp = new Triple<>(binh.get(father).getFirst(), binh.get(father).getSecond(), binh.get(father).getThird());
        binh.get(father).setTriple(binh.get(child));
        binh.get(child).setTriple(temp);
    }







    /**
     * Create the string of the visualisation of a binary heap
     *
     * @return the string of the binary heap
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Triple<UndirectedNode, UndirectedNode, Integer> no : binh) {
            s.append(no).append(", ");
        }
        return s.toString();
    }


    private String space(int x) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < x; i++) {
            res.append(" ");
        }
        return res.toString();
    }

    /**
     * Print a nice visualisation of the binary heap as a hierarchy tree
     */
    public void lovelyPrinting() {
        int nodeWidth = this.binh.get(0).toString().length();
        int depth = 1 + (int) (Math.log(this.binh.size()) / Math.log(2));
        int index = 0;

        for (int h = 1; h <= depth; h++) {
            int left = ((int) (Math.pow(2, depth - h - 1))) * nodeWidth - nodeWidth / 2;
            int between = ((int) (Math.pow(2, depth - h)) - 1) * nodeWidth;
            int i = 0;
            System.out.print(space(left));
            while (i < Math.pow(2, h - 1) && index < binh.size()) {
                System.out.print(binh.get(index) + space(between));
                index++;
                i++;
            }
            System.out.println("");
        }
        System.out.println("");
    }

    // ------------------------------------
    // 					TEST
    // ------------------------------------

    /**
     * Recursive test to check the validity of the binary heap
     *
     * @return a boolean equal to True if the binary tree is compact from left to right
     */
    private boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        int lastIndex = binh.size() - 1;
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= lastIndex) {
                return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left);
            } else {
                return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left)
                        && binh.get(right).getThird() >= binh.get(root).getThird() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println(jarjarBin.isEmpty() + "\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k + 30), rand);
            k--;
        }

        System.out.println(jarjarBin.test());

        jarjarBin.lovelyPrinting();

        //test of remove
        for(int i = 0; i < m; i++) {
            System.out.println("element removed : " + jarjarBin.remove().getThird());
        }


    }

}

