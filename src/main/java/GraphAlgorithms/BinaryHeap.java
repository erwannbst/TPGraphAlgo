package GraphAlgorithms;


public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public void insert(int element) {
        boolean inserted = false;
        int index = this.nodes.length;
    	for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == Integer.MAX_VALUE && !inserted) {
                nodes[i] = element;
                inserted = true;
                index = i;
            }
        }
        if (!inserted) {
            resize();
            nodes[index] = element;
        }
        pos++;


        int parent = getParent(index);
        while(this.nodes[index] < this.nodes[parent] && index != 0) {
            swap(index, parent);
            int tmp = parent;
            parent = getParent(tmp);
            index = tmp;
        }

    }

    public int remove() {
        int value = nodes[0];

        int lastLeaf =0;

        for(int i = 0; i<this.nodes.length; i++){
            if(this.nodes[i] == Integer.MAX_VALUE){
                lastLeaf = i -1;
                break;
            }
        }
        swap(0, lastLeaf);
        nodes[lastLeaf] = Integer.MAX_VALUE;

        int bestChild = getBestChildPos(0);
        int parent = 0;
        while(!isLeaf(parent) && this.nodes[parent] > this.nodes[bestChild]) {
            swap(parent, bestChild);
            int tmp = bestChild;
            bestChild = getBestChildPos(bestChild);
            parent = tmp;
        }

    	return value;
    }

    private int getParent(int i) {
    	return (i-1)/2;
    }

    private int getLeftChild(int index) {
        return 2 * index + 1;
    }

    private int getRightChild(int index) {
        return 2 * index + 2;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            int leftChild = this.nodes[getLeftChild(src)];
            int rightChild = this.nodes[getRightChild(src)];


            if(leftChild <= rightChild) {
                return getLeftChild(src);
            }else {
                return getRightChild(src);
            }
        }
    }


    /**
	 * Test if the node is a leaf in the binary heap
	 *
	 * @returns true if it's a leaf or false else
	 *
	 */
    private boolean isLeaf(int src) {
        return getLeftChild(src) >= this.nodes.length || this.nodes[getLeftChild(src)] == Integer.MAX_VALUE;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
	 * Recursive test to check the validity of the binary heap
	 *
	 * @returns a boolean equal to True if the binary tree is compact from left to right
	 *
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }





    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand);
            jarjarBin.insert(rand);
            k--;
        }
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());

        // test of remove
        for(int i=0; i < 10; i++){
            System.out.println("remove: " + jarjarBin.remove());
        }

    }

}
