package LAB06;

import java.util.ArrayList;
import java.util.Stack;

public class Graph {
    private int numOfVertices;
    private ArrayList<Integer>[] matrix;//每一个节点的指向链表
    private ArrayList<Vertex> vertices;

    public Graph() {
    }

    public Graph(int numOfVertices, ArrayList<Integer>[] matrix, ArrayList<Vertex> vertices) {
        this.numOfVertices = numOfVertices;
        this.matrix = matrix;
        this.vertices = vertices;
    }

    private Stack<Vertex> DFS() {
        Stack<Vertex> dfs = new Stack<>();
        for (int j = 0; j < this.numOfVertices; j++) {
            vertices.get(j).predecessor = new ArrayList<>();
            vertices.get(j).color = 0;
        }
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).color == 0) {
                DFSVISIT(vertices.get(i), dfs);
            }
        }
        return dfs;
    }

    private void DFSVISIT(Vertex u, Stack<Vertex> stack) {
        int i = vertices.indexOf(u);
        u.color = 1;
        for (int j = 0; j < matrix[i].size(); j++) {
            Vertex v = vertices.get(matrix[i].get(j));
            if (v.color == 0) {
                v.predecessor.add(u);
                DFSVISIT(v, stack);
            }
        }
        u.color = 2;
        stack.push(u);
    }

    public Vertex dagLongestPath() {
        for (int j = 0; j < this.numOfVertices; j++) {
            vertices.get(j).pi = null;
            vertices.get(j).length = Integer.MIN_VALUE;
        }
        Stack<Vertex> stack = DFS();
        while (!stack.empty()) {
            Vertex u = stack.pop();
            if (u.length == Integer.MIN_VALUE) {
                u.length = u.getWeight();
            }
            int i = vertices.indexOf(u);
            for (int j = matrix[i].size() - 1; j >= 0; j--) {
                Vertex v = vertices.get(matrix[i].get(j));
                if (v.length < u.length + v.getWeight()) {
                    v.length = u.length + v.getWeight();
                    v.pi = u;
                }
            }
        }
        Vertex longest = vertices.get(0);
        for (int m = 0; m < this.numOfVertices; m++) {
            if (vertices.get(m).length > longest.length)
                longest = vertices.get(m);
        }
        return longest;
    }
}
