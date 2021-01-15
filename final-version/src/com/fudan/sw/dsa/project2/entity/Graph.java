package com.fudan.sw.dsa.project2.entity;

import com.fudan.sw.dsa.project2.util.DistanceUtil;

import java.util.*;

/**
 * for subway graph
 *
 * @author zjiehang
 */
public class Graph {
    private boolean hasDir;
    private Map<String, Address> vertices;
    private Map<String, ArrayList<Edge>> edgeMap;
    private Map<String, ArrayList<Address>> lineMap;

    public Graph(Boolean hasDir) {
        this.hasDir = hasDir;
        vertices = new HashMap<>();
        edgeMap = new HashMap<>();
        lineMap = new HashMap<>();
    }

    public void addVertex(Address vertex) {
        String name = vertex.getAddress();
        if (!vertices.containsKey(name)) {
            vertices.put(name, vertex);
            edgeMap.put(name, new ArrayList<>());
            lineMap.put(name, new ArrayList<>());
        }
    }

    public void addEdge(Edge edge) {
        String start = edge.getStart();
        edgeMap.get(start).add(edge);
        if (!hasDir) {
            String end = edge.getEnd();
            edgeMap.get(end).add(new Edge(edge.getLine(), edge.getEnd(), edge.getStart(), edge.getWeight()));
        }
    }

    public void addConnect(Edge edge) {
        String start = edge.getStart();
        String end = edge.getEnd();
        if (!hasDir) {
            lineMap.get(end).add(vertices.get(start));
        }
        lineMap.get(start).add(vertices.get(end));

    }

    public ArrayList<Edge> getAdjacent(String name) {
        return edgeMap.get(name);
    }

    public Map<String, Address> getVertices() {
        return vertices;
    }

    public void addTwo(Address start, Address end, double speed) {
        addVertex(start);
        addVertex(end);
        ArrayList<Edge> edgess = edgeMap.get(start.getAddress());
        for (Address vertex : vertices.values()) {
            double lengths = DistanceUtil.getDistance(start, vertex);
            double lengthe = DistanceUtil.getDistance(vertex, end);
            if (!start.getAddress().equals(vertex.getAddress()) && !end.getAddress().equals(vertex.getAddress())) {
                Edge edges = new Edge("Line", start.getAddress(), vertex.getAddress(), (lengths / speed) * 60);
                edgess.add(edges);
                ArrayList<Edge> edgesv = edgeMap.get(vertex.getAddress());
                Edge edgee = new Edge("Line", vertex.getAddress(), end.getAddress(), (lengthe / speed) * 60);
                edgesv.add(edgee);
            }
        }
        Edge edgese = new Edge("Line", start.getAddress(), end.getAddress(), (DistanceUtil.getDistance(start, end) / speed) * 60);
        edgess.add(edgese);
    }

    public void deleteTwo(Address start, Address end) {
        vertices.remove(start.getAddress());
        vertices.remove(end.getAddress());
        edgeMap.remove(start.getAddress());
        String endName = end.getAddress();
        for (Address vertex : vertices.values()) {
            ArrayList<Edge> edges = edgeMap.get(vertex.getAddress());
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                if (endName.equals(edge.getEnd())) {
                    edges.remove(edge);
                }
            }
//            edges.remove(edges.size() - 1);
        }
    }

    public ArrayList<Address> getRelationAddress(Address v) {
        ArrayList<Address> addresses = new ArrayList<>();
        ArrayList<Edge> edges = edgeMap.get(v.getAddress());
        for (int i = 0; i < edges.size(); i++) {
            String uStr = edges.get(i).getEnd();
            Address u = vertices.get(uStr);
            if (!addresses.contains(u))
                addresses.add(u);
        }
        return addresses;
    }

    public ArrayList<Edge> lineBtw(Address address1, Address address2) {
        ArrayList<Edge> lines = new ArrayList<>();
        ArrayList<Edge> edges = edgeMap.get(address1.getAddress());
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (edge.getEnd().equals(address2.getAddress()) && !lines.contains(edge)) {
                lines.add(edge);
            }
        }
        return lines;
    }

    public boolean onLine(Address address1, Address address2) {
        if (lineMap.get(address1.getAddress()).contains(vertices.get(address2.getAddress())))
            return true;
        return false;
    }
}
