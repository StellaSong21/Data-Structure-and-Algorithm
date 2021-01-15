package com.fudan.sw.dsa.project2.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fudan.sw.dsa.project2.entity.*;
import com.fudan.sw.dsa.project2.util.DistanceUtil;
import org.springframework.stereotype.Service;

import com.fudan.sw.dsa.project2.constant.FileGetter;

/**
 * this class is what you need to complete
 *
 * @author zjiehang
 */
@Service
public class IndexService {
    //the subway graph
    private Graph graph = null;
    private final double SPEED = 5000;

    /**
     * create the graph use file
     */
    public void createGraphFromFile() {
        //如果图未初始化
        if (this.graph == null) {
            FileGetter fileGetter = new FileGetter();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileGetter.readFileFromClasspath()))) {
                String tempStr;
                ArrayList<ArrayList<String[]>> lines = new ArrayList<>();
                ArrayList<String[]> newLine = null;
                while ((tempStr = bufferedReader.readLine()) != null) {
                    String[] info = tempStr.split("\\s+");
                    if ("Line".equals(info[0])) {
                        if (newLine != null) {
                            lines.add(newLine);
                        }
                        newLine = new ArrayList<>();
                        newLine.add(info);
                    } else {
                        if (newLine != null)
                            newLine.add(info);
                    }
                }
                //create the graph from file
                this.graph = new Graph(false);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                for (int i = 0; i < lines.size(); i++) {
                    newLine = lines.get(i);
                    String[] lineInfo = newLine.get(0);
                    String line = "Line " + lineInfo[1];
                    String isLoop = lineInfo[2];
                    int dir = Integer.parseInt(lineInfo[3]);
                    Vertex start = null;
                    Vertex vertex0 = null;
                    Date time0 = null;
                    for (int j = 0; j < dir; j++) {
                        for (int k = 1; k < newLine.size(); k++) {
                            String[] vertexInfo = newLine.get(k);
                            String timestr = vertexInfo[j + 3];
                            if (!"--".equals(timestr)) {
                                Date time = sdf.parse(timestr);
                                Vertex vertex = new Vertex(vertexInfo[0],
                                        Double.parseDouble(vertexInfo[1]),
                                        Double.parseDouble(vertexInfo[2]),
                                        line);
                                if ("isLoop".equals(isLoop) && k == 1) {
                                    start = vertex;
                                }
                                graph.addVertex(vertex);
                                if (vertex0 != null && time0 != null) {
                                    int dirTime = (int) (time.getTime() - time0.getTime()) / (1000 * 60);
                                    graph.addEdge(vertex0, vertex, dirTime < 0 ? (60 * 24) + dirTime : dirTime);
                                }
                                if ("isLoop".equals(isLoop) && k == newLine.size() - 1) {
                                    graph.addEdge(vertex, start, 3);
                                }
                                vertex0 = vertex;
                                time0 = time;
                            }
                        }
                        vertex0 = null;
                        time0 = null;
                    }
                }
                for (int m = 0; m < lines.size(); m++) {
                    newLine = lines.get(m);
                    for (int n = 1; n < newLine.size(); n++) {
                        String[] vInfo = newLine.get(n);
                        for (int r = n + 1; r < newLine.size(); r++) {
                            String[] uInfo = newLine.get(r);
                            graph.addConnect(vInfo[0], uInfo[0]);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ReturnValue travelRoute(Map<String, Object> params) {
        String startAddress = params.get("startAddress").toString();
        String startLongitude = params.get("startLongitude").toString();
        String startLatitude = params.get("startLatitude").toString();
        String endAddress = params.get("endAddress").toString();
        String endLongitude = params.get("endLongitude").toString();
        String endLatitude = params.get("endLatitude").toString();
        String choose = params.get("choose").toString();

        System.out.println(startAddress);
        System.out.println(startLongitude);
        System.out.println(startLatitude);
        System.out.println(endAddress);
        System.out.println(endLongitude);
        System.out.println(endLatitude);
        System.out.println(choose);

        Address startPoint = new Address(startAddress, startLongitude, startLatitude);
        Address endPoint = new Address(endAddress, endLongitude, endLatitude);
        double minutes = 0;
        List<Address> addresses = new ArrayList<Address>();
        switch (choose) {
            case "1":
                //步行最少
                minutes = minimunWalking(addresses, startPoint, endPoint);
                break;
            case "2":
                //换乘最少
                minutes = minimunTimes(addresses, startPoint, endPoint);
                break;
            case "3":
                //时间最短
                minutes = minimunTime(addresses, startPoint, endPoint);
                break;
            default:
                break;
        }

        ReturnValue returnValue = new ReturnValue();
        returnValue.setStartPoint(startPoint);
        returnValue.setEndPoint(endPoint);
        returnValue.setSubwayList(addresses);
        returnValue.setMinutes(Double.parseDouble(String.format("%.2f", minutes)));
        return returnValue;
    }

    private double minimunWalking(List<Address> addresses, Address startPoint, Address endPoint) {
        double length1 = Integer.MAX_VALUE;//步行到起点地铁站的距离
        double length2 = Integer.MAX_VALUE;//步行到终点地铁站的距离
        Address start = null;//起点地铁站
        Address end = null;//终点地铁站
        for (Vertex vertex : graph.getVertices().values()) {
            Address address = new Address(vertex.getName(), vertex.getLongitude() + "", vertex.getLatitude() + "");
            double startLength = DistanceUtil.getDistance(address, startPoint);
            if (startLength < length1) {
                length1 = startLength;
                start = address;
            }
            double endLength = DistanceUtil.getDistance(address, endPoint);
            if (endLength < length2) {
                length2 = endLength;
                end = address;
            }
        }

        double distanceDir = DistanceUtil.getDistance(startPoint, endPoint);//起点到终点的距离

        double minutes = 0;

        if (distanceDir < length1 + length2) {
            minutes = (distanceDir / SPEED) * 60;
        } else {
            double time1 = (length1 / SPEED) * 60;
            double time2 = (length2 / SPEED) * 60;
            if (start != null && end != null) {
                addresses.clear();
                minutes = getMinTime(this.graph, addresses, start, end, "time", null) + time1 + time2;
            }
        }
        return minutes;
    }

    private double minimunTime(List<Address> addresses, Address startPoint, Address endPoint) {
        double minutes = (DistanceUtil.getDistance(startPoint, endPoint) / SPEED) * 60;
        ArrayList<Vertex[]> matches = getAround(startPoint, endPoint);

        for (int k = 0; k < matches.size(); k++) {
            Vertex[] match = matches.get(k);
            Vertex v = match[0];
            Address vAddr = new Address(v.getName(), v.getLongitude() + "", v.getLatitude() + "");
            Vertex u = match[1];
            if (u.getName().equals(v.getName())) {
                continue;
            }
            Address uAddr = new Address(u.getName(), u.getLongitude() + "", u.getLatitude() + "");
            double time1 = (DistanceUtil.getDistance(startPoint, vAddr) / SPEED) * 60;
            double time2 = (DistanceUtil.getDistance(endPoint, uAddr) / SPEED) * 60;

            List<Address> addresses0 = new ArrayList<>();
            double time3 = getMinTime(this.graph, addresses0, vAddr, uAddr, "time", null);

            if (minutes > time1 + time2 + time3) {
                minutes = time1 + time2 + time3;
                addresses.clear();
                addresses.addAll(addresses0);
            }
        }
        return minutes;
    }

    private double minimunTimes(List<Address> addresses, Address startPoint, Address endPoint) {
        double minutes = Double.MAX_VALUE;
        ArrayList<Vertex[]> matches = getAround(startPoint, endPoint);

        for (int k = 0; k < matches.size(); k++) {
            Vertex[] match = matches.get(k);
            Vertex u = match[1];
            Address uAddr = new Address(u.getName(), u.getLongitude() + "", u.getLatitude() + "");
            Vertex v = match[0];
            Address vAddr = new Address(v.getName(), v.getLongitude() + "", v.getLatitude() + "");
            if (u.getName().equals(v.getName())) {
                continue;
            }
            double time1 = (DistanceUtil.getDistance(startPoint, vAddr) / SPEED) * 60;
            double time2 = (DistanceUtil.getDistance(endPoint, uAddr) / SPEED) * 60;
            Vertex end = getShortestPath(this.graph, vAddr, uAddr, "times", null);
            //todo test
//            System.out.println("vAddr: " + vAddr.getAddress());
//            System.out.println("uAddr: " + uAddr.getAddress());

            //todo test end
//            System.out.println("end: " + end.getName());
//            while (end.pre.size() != 0) {
//                ArrayList<Vertex> vertices = end.pre;
//
//                for (int i = 0; i < vertices.size(); i++) {
//                    System.out.println(vertices.get(i).getName() + " : " + vertices.get(i).pre.size());
//                }
//                end = end.pre.get(0);
//            }
            Graph graph = new Graph(true);
            graph = getGraph(graph, end);

            //todo test
//            for (Vertex vertex : graph.getVertices().values()) {
//                System.out.println("graph: " + vertex.getName());
//                ArrayList<Vertex> adjacent = graph.getAdjacent(vertex.getName());
//                for (int i = 0; i < adjacent.size(); i++) {
//                    System.out.println("adaj: " + adjacent.get(i).getName());
//                }
//            }


            Vertex min = getShortestPath(graph, vAddr, uAddr, "time", null);
            //todo test min
//            System.out.println("min: " + min.getName());
            if (min != null && min.time + time1 + time2 < minutes) {
                minutes = min.time + time1 + time2;
                addresses.clear();
                while (min != null) {
                    addresses.add(0, new Address(min.getName(), min.getLongitude() + "", min.getLatitude() + ""));
                    min = min.pi;
                }
            }
        }
        return minutes;
    }

    private Graph getGraph(Graph graph, Vertex vertex) {
        graph.addVertex(vertex);
        //todo test
//        System.out.println(vertex.getName() + " : " + vertex.pre.size());
        ArrayList<Vertex> pres = vertex.pre;
        for (int i = 0; i < pres.size(); i++) {
            Vertex start = pres.get(i);
            graph.addVertex(vertex);
            ArrayList<Address> addresses = new ArrayList<>();
            double minutes = getMinutesBtw(this.graph, addresses,
                    new Address(start.getName(),
                            start.getLongitude() + "",
                            start.getLatitude() + ""),
                    new Address(vertex.getName(),
                            vertex.getLongitude() + "",
                            vertex.getLatitude() + ""));
//            ///todo test
//            for (int k = 0; k < addresses.size(); k++) {
//                System.out.println("\t\t" + addresses.get(k).getAddress());
//            }
            Vertex vertex0;
            for (int j = addresses.size() - 2; j >= 0; j--) {
                vertex0 = this.graph.getVertices().get(addresses.get(j).getAddress());
                // new Vertex(vertex0.getName(),vertex0.getLongitude(),vertex0.getLatitude(),vertex0.getLine(),vertex0.pre)
                graph.addVertex(vertex0);
                graph.addEdge(vertex0,
                        vertex,
                        this.graph.getWeightBtw(vertex.getName(), vertex0.getName()));
                vertex = vertex0;
            }
            //todo test
//            System.out.println("start: " + start.pre.size());
            graph = getGraph(graph, start);
        }
        return graph;
    }

    private Vertex getShortestPath(Graph graph, Address start, Address end, String property, String line) {
        if ("time".equals(property)) {
            for (Vertex vertex : graph.getVertices().values()) {
                vertex.time = Double.MAX_VALUE / 2;
                vertex.pi = null;
            }
        }
        if ("times".equals(property)) {
            for (Vertex vertex : graph.getVertices().values()) {
                vertex.times = Integer.MAX_VALUE / 2;
                vertex.pre = new ArrayList<>();
            }
        }

        Vertex startVertex = graph.getVertices().get(start.getAddress());
        startVertex.time = 0;
        startVertex.times = 0;

        Vertex endVertex = graph.getVertices().get(end.getAddress());

        ArrayList<Vertex> arrayList = new ArrayList<>(graph.getVertices().values());

        if ("time".equals(property)) {
            Heap heap = new Heap(arrayList, "time");
            while (heap.size != 0) {
                Vertex u = heap.extractMin();
                ArrayList<Vertex> adjacent = graph.getAdjacent(u.getName());
                ArrayList<Double> weight = graph.getWeight(u.getName());
                for (int i = 0; i < adjacent.size(); i++) {
                    Vertex v = adjacent.get(i);
                    //todo test
                   // System.out.println(line);

                    if (line != null && !v.getLine().contains(line)) {
                        continue;
                    }
                    if (v.time > u.time + weight.get(i)) {
                        v.time = u.time + weight.get(i);
                        heap.decreaseKey(v);
                        v.pi = u;
                        if (v.getName().equals(end.getAddress())) {
                            return v;
                        }
                    }
                }
            }
        }
        if ("times".equals(property)) {
            Heap heap = new Heap(arrayList, "times");
            while (heap.size != 0) {
                Vertex u = heap.extractMin();
                //todo test
//                System.out.println("u: " + u.getName());
                ArrayList<Vertex> connect = graph.getConnect(u.getName());
                for (int i = 0; i < connect.size(); i++) {
                    Vertex v = connect.get(i);
                    //todo test
//                    System.out.println("v: " + v.getName());
                    if (v.times > u.times + 1) {
                        v.times = u.times + 1;
                        heap.decreaseKey(v);
                        v.pre.clear();
                        v.pre.add(u);
                    } else if (v.times == u.times + 1) {
                        v.pre.add(u);
                    }
                    if (v.getName().equals(end.getAddress())) {
                        return v;
                    }
                }
            }
        }
        return null;
    }

    private double getMinTime(Graph graph, List<Address> addresses, Address startPoint, Address endPoint, String property, String line) {
        getShortestPath(graph, startPoint, endPoint, property, line);
        Vertex endVertex = graph.getVertices().get(endPoint.getAddress());
        double minutes = endVertex.time;
        while (endVertex != null) {
            addresses.add(0, new Address(endVertex.getName(), endVertex.getLongitude() + "", endVertex.getLatitude() + ""));
            endVertex = endVertex.pi;
        }
        return minutes;
    }

    private double getMinutesBtw(Graph graph, List<Address> bwtn, Address startPoint, Address endPoint) {
        String startn = startPoint.getAddress();
        String endn = endPoint.getAddress();
        Vertex startv = graph.getVertices().get(startn);
        Vertex endv = graph.getVertices().get(endn);
        ArrayList<String> startLine = startv.getLine();
        ArrayList<String> endLine = endv.getLine();

        ArrayList<String> sameLine = new ArrayList<>();
        for (int i = 0; i < startLine.size(); i++) {
            String startl = startLine.get(i);
            for (int j = 0; j < endLine.size(); j++) {
                if (startl.equals(endLine.get(j))) {
                    sameLine.add(startl);
                }
            }
        }

        double minutes = Integer.MAX_VALUE;
        for (int k = 0; k < sameLine.size(); k++) {
            List<Address> addresses = new ArrayList<>();
            double time = getMinTime(graph, addresses, startPoint, endPoint, "time", sameLine.get(k));
            if (time < minutes) {
                minutes = time;
                bwtn.clear();
                bwtn.addAll(addresses);
            }
        }
        return minutes;
    }

    private ArrayList<Vertex[]> getAround(Address startPoint, Address endPoint) {
        double distance = DistanceUtil.getDistance(startPoint, endPoint);
        ArrayList<Vertex> vertices = new ArrayList<>(graph.getVertices().values());
        ArrayList<Vertex[]> matches = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);
            for (int j = 0; j < vertices.size(); j++) {
                Vertex u = vertices.get(j);
                double length1 = DistanceUtil.getDistance(startPoint, new Address(v.getName(), v.getLongitude() + "", v.getLatitude() + ""));
                double length2 = DistanceUtil.getDistance(endPoint, new Address(u.getName(), u.getLongitude() + "", u.getLatitude() + ""));
                if (length1 + length2 < distance) {
                    Vertex[] match = {v, u};
                    matches.add(match);
                }
            }
        }
        return matches;
    }
}
