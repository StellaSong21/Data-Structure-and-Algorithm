package com.fudan.sw.dsa.project2.entity;

import java.util.*;

/**
 * for subway graph
 *
 * @author zjiehang
 */
public class Graph {
    private int numOfVertices;
    private Map<String, Vertex> vertices;
    private Map<String, ArrayList<Vertex>> edgeMap;
    private Map<String, ArrayList<Double>> weightMap;
    private Map<String, ArrayList<Vertex>> lineMap;
    private boolean hasDir;

    public Graph(Boolean hasDir) {
        vertices = new HashMap<>();
        edgeMap = new HashMap<>();
        weightMap = new HashMap<>();
        lineMap = new HashMap<>();
        this.hasDir = hasDir;
    }

    /**
     * add vertex
     *
     * @param vertex: new vertex
     */
    public void addVertex(Vertex vertex) {
        String name = vertex.getName();
        if (!vertices.containsKey(name)) {
            vertices.put(name, vertex);
            edgeMap.put(name, new ArrayList<>());
            weightMap.put(name, new ArrayList<>());
            lineMap.put(name, new ArrayList<>());
            numOfVertices++;
        } else {
            vertices.get(name).addLine(vertex.getLine().get(0));
        }
    }

    /**
     * add edge, update edgeMap and weightMap
     *
     * @param vertex1:
     * @param vertex2:
     * @param time：weight of edge
     */
    public void addEdge(Vertex vertex1, Vertex vertex2, double time) {
        String name1 = vertex1.getName();
        String name2 = vertex2.getName();
        Vertex vertex11 = vertices.get(name1);
        Vertex vertex22 = vertices.get(name2);
        if (!edgeMap.get(name1).contains(vertex22) && !edgeMap.get(name2).contains(vertex11)) {
            edgeMap.get(name1).add(vertex22);
            weightMap.get(name1).add(time);
            if (!this.hasDir) {
                edgeMap.get(name2).add(vertex11);
                weightMap.get(name2).add(time);
            }
        }
    }

    public void addConnect(String name1, String name2) {
        if (!lineMap.get(name1).contains(vertices.get(name2)) && !lineMap.get(name2).contains(vertices.get(name1))) {
            lineMap.get(name1).add(vertices.get(name2));
            if (!this.hasDir)
                lineMap.get(name2).add(vertices.get(name1));
        }
    }

    public ArrayList<Vertex> getAdjacent(String name) {
        return edgeMap.get(name);
    }

    public ArrayList<Double> getWeight(String name) {
        return weightMap.get(name);
    }

    public double getWeightBtw(String name1,String name2){
        Vertex vertex2 = vertices.get(name2);

        ArrayList<Double> weight = getWeight(name1);
        ArrayList<Vertex> adjacent = getAdjacent(name1);
        int index = adjacent.indexOf(vertex2);
        return weight.get(index);
    }

    public ArrayList<Vertex> getConnect(String name) {
        return lineMap.get(name);
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }
}
