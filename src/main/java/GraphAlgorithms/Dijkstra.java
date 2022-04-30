package GraphAlgorithms;

import AdjacencyMatrix.AdjacencyMatrixDirectedValuedGraph;

import java.util.*;

//
//// Main class DPQ
//public class GFG {

public class Dijkstra {

    int[][] matrix = {
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 1, 1, 0}};

    int[][] matrixValued = {
            {0, 85, 217, 0, 173, 0, 0, 0, 0, 0},
            {85, 0, 0, 0, 0, 80, 0, 0, 0, 0},
            {217, 0, 0, 0, 0, 0, 186, 103, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 183, 0, 0},
            {173, 0, 0, 0, 0, 0, 0, 0, 0, 502},
            {0, 80, 0, 0, 0, 0, 0, 0, 250, 0},
            {0, 0, 186, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 103, 183, 0, 0, 0, 0, 0, 167},
            {0, 0, 0, 0, 0, 250, 0, 0, 0, 84},
            {0, 0, 0, 0, 502, 0, 0, 167, 84, 0}};

    int V[] = {0, 9999, 9999, 9999, 9999, 9999, 9999, 9999, 9999, 9999};
    int P[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    public Dijkstra(){

    }

    public static void iteration(int x, int[][] matrix, int[] V, int[] P, int[] F) {
        // On étudie les chemins depuis le sommet x = b
        for (int y = 0; y < matrix[x].length; y++) {
            if(matrix[x][y] == 1){ // x et y sont voisins b et f
                int newLength = calculLongueur(P, V, x, y);
                if(newLength < V[y]){
                    V[y] = newLength;
                    P[y] = x;
                }
            }
        }
        F[x] = 1;
    }

    // Calcule la longueur du chemin connu
    public static int calculLongueur(int[] P, int[] V, int x, int y){
        int newLength = y;
        while(P[x] != -1) {
            newLength += V[x];
            x = P[x];
        }
        return newLength;
    }

    public static void main(String[] args) {
        // int[][] matrix = GraphTools.generateGraphData(10, 11, false, false, false, 101);
        // int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);

        // Init
        Dijkstra dij = new Dijkstra();
        dij.compute();

//        AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrix, matrixValued);
//        System.out.println(am);
    }

    public int compute(){

        //Init
        List<Integer> Q = new ArrayList<Integer>();
        int sdeb = 0;

        for (int i = 0; i < 10; i++){
            this.V[i] = Integer.MAX_VALUE;
            Q.add(i);
        }
        this.V[0] = 0;

        while(!Q.isEmpty()){
            int s1 = trouve_min(Q);
            System.out.println(s1);
            System.out.println(Q);
            Q.remove(Q.indexOf(s1));
            for(int s2 : voisinsDe(s1)){
                maj_distances(s1, s2);
            }
        }

        // Plus court chemin
        List<Integer> A = new ArrayList<Integer>();
        int s = 10;
        while(s != sdeb){
            A.add(0, s);
            s = P[s];
        }
        A.add(0, sdeb);

        System.out.println(A);

        return 1;
    }

    public List<Integer> voisinsDe(int noeud){
        List<Integer> voisins = new ArrayList<Integer>();
        for (int v: matrix[noeud]) {
            if(v == 1);
            voisins.add(v);
        }
        return  voisins;
    }

    public Integer trouve_min(List<Integer> Q){
        int mini = Integer.MAX_VALUE;
        int sommet = -1;
        for (int i = 0; i < Q.size(); i++){ // Pour chaque sommet s de Q
            int s = Q.get(i);
            if(this.V[s] < mini){
                mini = this.V[s];
                sommet = s;
            }
        }
        return sommet;
    }

    public void maj_distances(int s1, int s2){
        if(V[s2] > V[s1] + matrixValued[s1][s2]){
            V[s2] = V[s1] + matrixValued[s1][s2];
            P[s2] = s1;
        }
    }
}