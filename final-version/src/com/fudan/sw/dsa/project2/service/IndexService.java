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
    private final double SPEED = 5000;//(m/h)

    //以下为最少换乘所需的全局变量
    private ArrayList<Address> path;
    private int minTransferTimes;//全局变量：最少换乘次数
    private double costTime;//花费时间
    private ArrayList<Address> temp;

    /**
     * create the graph use file
     */
    public void createGraphFromFile() {
        //如果图未初始化
        if (this.graph == null) {
            FileGetter fileGetter = new FileGetter();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileGetter.readFileFromClasspath()))) {
                String tempStr;
                //以地铁线为单位，存储地图信息
                ArrayList<ArrayList<String[]>> lines = new ArrayList<>();
                ArrayList<String[]> newLine = null;
                while ((tempStr = bufferedReader.readLine()) != null) {
                    String[] info = tempStr.split("\\s+");
                    //对第一行的处理
                    if ("Line".equals(info[0])) {
                        if (newLine != null) {
                            lines.add(newLine);
                        }
                        newLine = new ArrayList<>();
                        newLine.add(info);
                    }
                    //对地铁站信息的处理
                    else {
                        if (newLine != null)
                            newLine.add(info);
                    }
                }
                lines.add(newLine);

                //create the graph from file
                this.graph = new Graph(false);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                for (int i = 0; i < lines.size(); i++) {
                    newLine = lines.get(i);
                    String[] lineInfo = newLine.get(0);
                    String line = "Line " + lineInfo[1];
                    String isLoop = lineInfo[2];
                    int dir = Integer.parseInt(lineInfo[3]);
                    Address start = null;//存储起点站，为环线
                    Address vertex0 = null;//存储上一个站点的信息
                    Date time0 = null;//存储上一个站点的date时间
                    for (int j = 0; j < dir; j++) {
                        for (int k = 1; k < newLine.size(); k++) {
                            String[] vertexInfo = newLine.get(k);
                            String timeStr = vertexInfo[j + 3];
                            if (!"--".equals(timeStr)) {
                                Date time = sdf.parse(timeStr);
                                Address vertex = new Address(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
                                if ("isLoop".equals(isLoop) && k == 1) {
                                    start = vertex;//如果是环线，需要把起点存起来
                                }
                                graph.addVertex(vertex);
                                if (vertex0 != null && time0 != null) {
                                    int dirTime = (int) (time.getTime() - time0.getTime()) / (1000 * 60);
                                    graph.addEdge(new Edge(line, vertex0.getAddress(), vertex.getAddress(), dirTime < 0 ? (60 * 24) + dirTime : dirTime));
                                }
                                if ("isLoop".equals(isLoop) && k == newLine.size() - 1 && start != null) {
                                    graph.addEdge(new Edge(line, vertex.getAddress(), start.getAddress(), 3));
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
                    String line = "Line " + newLine.get(0)[1];
                    for (int n = 1; n < newLine.size(); n++) {
                        String[] vInfo = newLine.get(n);
                        for (int r = n + 1; r < newLine.size(); r++) {
                            String[] uInfo = newLine.get(r);
                            graph.addConnect(new Edge(line, vInfo[0], uInfo[0], 1));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //找到附近的站点
    private ArrayList<Address>[] getAround(Address startPoint, Address endPoint, int size) {
        ArrayList<Address>[] arrayLists = new ArrayList[2];
        arrayLists[0] = new ArrayList<Address>();
        arrayLists[1] = new ArrayList<Address>();
        ArrayList<Address> vertices = new ArrayList<>(graph.getVertices().values());
        for (int i = 0; i < vertices.size(); i++) {
            Address v = vertices.get(i);
            double lengths = DistanceUtil.getDistance(startPoint, v);
            if (arrayLists[0].size() == 0)
                arrayLists[0].add(v);
            else
                for (int j = 0; j < arrayLists[0].size(); j++) {
                    if (DistanceUtil.getDistance(startPoint, arrayLists[0].get(j)) > lengths) {
                        arrayLists[0].add(j, v);
                        if (arrayLists[0].size() > size)
                            arrayLists[0].remove(size);
                    }
                }

            double lengthe = DistanceUtil.getDistance(endPoint, v);
            if (arrayLists[1].size() == 0)
                arrayLists[1].add(v);
            else
                for (int j = 0; j < arrayLists[1].size(); j++) {
                    if (DistanceUtil.getDistance(endPoint, arrayLists[1].get(j)) > lengthe) {
                        arrayLists[1].add(j, v);
                        if (arrayLists[1].size() > size)
                            arrayLists[1].remove(size);
                    }
                }
        }
        return arrayLists;
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
        double length = 0;
        double[] minAndLen;
        long time1 = 0;
        long time2 = 0;
        List<Address> addresses = new ArrayList<Address>();
        switch (choose) {
            case "1":
                //步行最少
                time1 = System.nanoTime();
                minAndLen = leastWalking(addresses, startPoint, endPoint);
                time2 = System.nanoTime();
                System.out.println("查询从 " + startPoint.getAddress() + " 到 " + endPoint.getAddress() + " 的步行最少路径花费的时间为 " + (time2 - time1) + " ns");
                minutes = minAndLen[0];
                length = minAndLen[1];
                break;
            case "2":
                //换乘最少
                time1 = System.nanoTime();
                leastTimes(startPoint, endPoint);
                time2 = System.nanoTime();
                System.out.println("查询从 " + startPoint.getAddress() + " 到 " + endPoint.getAddress() + " 的最少换乘路径花费的时间为 " + (time2 - time1) + " ns");
                addresses.addAll(path);
                minutes = costTime;
                length = DistanceUtil.getDistance(startPoint, addresses.get(0)) + DistanceUtil.getDistance(endPoint, addresses.get(addresses.size() - 1));
                break;
            case "3":
                //时间最短
                time1 = System.nanoTime();
                minAndLen = leastTime(addresses, startPoint, endPoint);
                time2 = System.nanoTime();
                System.out.println("查询从 " + startPoint.getAddress() + " 到 " + endPoint.getAddress() + " 的时间最短路径花费的时间为 " + (time2 - time1) + " ns");
                minutes = minAndLen[0];
                length = minAndLen[1];
                break;
            default:
                break;
        }

        ReturnValue returnValue = new ReturnValue();
        returnValue.setStartPoint(startPoint);
        returnValue.setEndPoint(endPoint);
        returnValue.setSubwayList(addresses);
        returnValue.setMinutes(Double.parseDouble(String.format("%.2f", minutes)));
        returnValue.setLengths(Double.parseDouble(String.format("%.2f", length)));
        returnValue.setQueryTime(time2 - time1);
        return returnValue;
    }

    private double[] leastWalking(List<Address> addresses, Address startPoint, Address endPoint) {
        ArrayList<Address>[] matches = getAround(startPoint, endPoint, 1);
        Address start = matches[0].get(0);
        Address end = matches[1].get(0);
        double length1 = DistanceUtil.getDistance(startPoint, start);
        double length2 = DistanceUtil.getDistance(end, endPoint);

        double distanceDir = DistanceUtil.getDistance(startPoint, endPoint);//起点到终点的距离

        double minutes = 0;
        double length = 0;

        if (distanceDir < length1 + length2) {
            minutes = (distanceDir / SPEED) * 60;
            length = distanceDir;
        } else {
            double time1 = (length1 / SPEED) * 60;
            double time2 = (length2 / SPEED) * 60;
            addresses.clear();
            minutes = getMinTime(this.graph, addresses, start, end) + time1 + time2;
            length = length1 + length2;
        }
        return new double[]{minutes, length};
    }

    private double[] leastTime(List<Address> addresses, Address startPoint, Address endPoint) {
        double length = DistanceUtil.getDistance(startPoint, endPoint);
        Address start = new Address(startPoint.getAddress()+"0",startPoint.getLongitude()+"",startPoint.getLatitude()+"");
        Address end = new Address(endPoint.getAddress()+"0",endPoint.getLongitude()+"",endPoint.getLatitude()+"");
        this.graph.addTwo(start, end, SPEED);
        double minutes = getMinTime(this.graph, addresses, start, end);

        addresses.remove(start);
        addresses.remove(end);

        if (addresses.size() > 0) {
            double length1 = DistanceUtil.getDistance(startPoint, addresses.get(0));
            double length2 = DistanceUtil.getDistance(endPoint, addresses.get(addresses.size() - 1));
            length = length1 + length2;
        }

        this.graph.deleteTwo(start, end);
        return new double[]{minutes, length};
    }

    private List<Address> leastTimes(Address startPoint, Address endPoint) {
        path = new ArrayList<>();
        temp = new ArrayList<>();
        ArrayList<Address>[] matches = getAround(startPoint, endPoint, 3);
        ArrayList<Address> starts = matches[0];
        ArrayList<Address> ends = matches[1];
        double length1;//步行到起点地铁站的距离
        double length2;//步行到终点地铁站的距离
        double time1;
        double time2;
        minTransferTimes = Integer.MAX_VALUE;
        costTime = Double.MAX_VALUE;
        for (int i = 0; i < starts.size(); i++) {
            for (int j = 0; j < ends.size(); j++) {
                Address start = starts.get(i);
                Address end = ends.get(j);
                length1 = DistanceUtil.getDistance(startPoint, start);
                time1 = (length1 / SPEED) * 60;
                length2 = DistanceUtil.getDistance(end, endPoint);
                time2 = (length2 / SPEED) * 60;
                temp.add(start);
                start.reached = true;
                depthFirstSearch(graph, start, end, "Line", 0, time1 + time2);
                temp.remove(start);
                start.reached = false;
            }
        }
        return path;
    }

    //深度优先搜索
    private void depthFirstSearch(Graph graph, Address currentVertice, Address endVertice, String lastline, int transferTimes, double costTime) {
        if (transferTimes > minTransferTimes || transferTimes > 5) return;//换乘一般不超过五次
        if (currentVertice == endVertice) {//当前点是终点
            //todo test
            if (transferTimes < minTransferTimes || (transferTimes == this.minTransferTimes && this.costTime > costTime)) {//换乘次数变小 || 换乘次数相等且花销时间变小
                //更新
                path = new ArrayList<>();
                path.addAll(temp);
                minTransferTimes = transferTimes;
                this.costTime = costTime;
            }
            return;
        }
        //当前点不是终点：走到和当前点相邻的点
        ArrayList<Edge> edges = graph.getAdjacent(currentVertice.getAddress());
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);//将当前点的边取出
            Address nextVertice = graph.getVertices().get(edge.getEnd());//看这条边的相邻点
            if (!nextVertice.reached) {//如果这个相邻点没有被走过
                int newTransfer = 0;
                if (edge.getLine().equals(lastline)) {
                    //同一条地铁线路
                    newTransfer = transferTimes;
                } else newTransfer = transferTimes + 1;//不同地铁线路，换乘次数加一
                temp.add(nextVertice);//将下一个点加入这个路径里面
                currentVertice.reached = true;
                depthFirstSearch(graph, nextVertice, endVertice, edge.getLine(), newTransfer, costTime + edge.getWeight());
                nextVertice.reached = false;
                temp.remove(nextVertice);
            }
        }

    }

    //由Dijkstra算法得到具体路径和所需时间
    private double getMinTime(Graph graph, List<Address> addresses, Address startPoint, Address endPoint) {
        getShortestPath(graph, startPoint, endPoint);
        Address endVertex = graph.getVertices().get(endPoint.getAddress());
        double minutes = endVertex.getTime();
        while (endVertex != null) {
            addresses.add(0, endVertex);
            endVertex = endVertex.getPi();
        }
        return minutes;
    }

    //Dijkstra
    private void getShortestPath(Graph graph, Address start, Address end) {
        for (Address vertex : graph.getVertices().values()) {
            vertex.setTime(Integer.MAX_VALUE);
            vertex.setPi(null);
        }
        Address startVertex = graph.getVertices().get(start.getAddress());
        startVertex.setTime(0);
        ArrayList<Address> arrayList = new ArrayList<>(graph.getVertices().values());
        Heap heap = new Heap(arrayList);
        while (heap.size != 0) {
            Address u = heap.extractMin();
            if (u.getAddress().equals(end.getAddress())) {
                return;
            }
            ArrayList<Edge> adjacent = graph.getAdjacent(u.getAddress());
            for (int i = 0; i < adjacent.size(); i++) {
                Edge e = adjacent.get(i);
                Address v = graph.getVertices().get(e.getEnd());
                if (v.getTime() > u.getTime() + e.getWeight()) {
                    v.setTime(u.getTime() + e.getWeight());
                    heap.decreaseKey(v);
                    v.setPi(u);
                }
            }
        }
    }


    /*
     * Dijkstra2的最少换乘
     */
//    private double[] leastTimes(List<Address> addresses, Address startPoint, Address endPoint) {
//        ArrayList<Address>[] matches = getAround(startPoint, endPoint, 1);
//        ArrayList<Address> starts = matches[0];
//        ArrayList<Address> ends = matches[1];
//        double length1 = Integer.MAX_VALUE;//步行到起点地铁站的距离
//        double length2 = Integer.MAX_VALUE;//步行到终点地铁站的距离
//        double time1 = 0;
//        double time2 = 0;
//        double length = DistanceUtil.getDistance(startPoint, endPoint);
//        double minutes = Double.MAX_VALUE;
//        int times = Integer.MAX_VALUE;
//        for (int i = 0; i < starts.size(); i++) {
//            for (int j = 0; j < ends.size(); j++) {
//                Address start = starts.get(i);
//                Address end = ends.get(j);
//                length1 = DistanceUtil.getDistance(startPoint, start);
//                time1 = (length1 / SPEED) * 60;
//                length2 = DistanceUtil.getDistance(end, endPoint);
//                time2 = (length2 / SPEED) * 60;
//
//                Address endVertex = Dijkstra2(this.graph, start, end);
////                Address endVertex = graph.getVertices().get(endPoint.getAddress());
//                int newtimes = endVertex.getTimes();
//                double time = endVertex.getTime();
//                if (newtimes < times || (times == newtimes && time - times * 10 + time1 + time2 < minutes)) {
//                    times = newtimes;
//                    length = length1 + length2;
//                    minutes = time - times * 10 + time1 + time2;
//                    addresses.clear();
//                    while (endVertex != null) {
//                        addresses.add(0, endVertex);
//                        endVertex = endVertex.getPi();
//                    }
//                }
//            }
//        }
//        return new double[]{minutes, length};
//    }


    /*
     * test的最少换乘
     */
//    private double[] leastTimes(List<Address> addresses, Address startPoint, Address endPoint) {
//        ArrayList<Address>[] matches = getAround(startPoint, endPoint, 1);
//        ArrayList<Address> starts = matches[0];
//        ArrayList<Address> ends = matches[1];
//        double length1 = Integer.MAX_VALUE;//步行到起点地铁站的距离
//        double length2 = Integer.MAX_VALUE;//步行到终点地铁站的距离
//        double time1 = 0;
//        double time2 = 0;
//        double length = DistanceUtil.getDistance(startPoint, endPoint);
//        double minutes = Double.MAX_VALUE;
//        int times = Integer.MAX_VALUE;
//        for (int i = 0; i < starts.size(); i++) {
//            for (int j = 0; j < ends.size(); j++) {
//                Address start = starts.get(i);
//                Address end = ends.get(j);
//                length1 = DistanceUtil.getDistance(startPoint, start);
//                length2 = DistanceUtil.getDistance(end, endPoint);
//                time1 = (length1 / SPEED) * 60;
//                time2 = (length2 / SPEED) * 60;
//                test test = new test(addresses, graph);
//                test.getPath(start, null, start, end);
//            }
//        }
//
//        return new double[]{minutes, length};
//    }


    /*
     * 根据路径得到所需时间和换乘次数
     */
//    private double[] getTimesAndTime(Address[] addresses) {
//        double time = 0;
//        int times = 0;
//        Address address = addresses[0];
//        address.setTime(0);
//        address.setTimes(0);
//
//        ArrayList<ArrayList<String>> lines = new ArrayList<>();
//        for (int i = 1; i < addresses.length; i++) {
//            Edge edge = this.graph.lineBtw(addresses[i - 1], addresses[i]).get(0);
//            double timebtw = addresses[i - 1].getTime() + edge.getWeight();
//            time += timebtw;
//            addresses[i].setTime(timebtw);
//            if (this.graph.onLine(address, addresses[i])) {
//            } else {
//                address = addresses[i - 1];
//                times++;
//            }
//        }
//
//
//        return new double[]{times, time};
//    }


    /*
     * 深搜
     */
//    private void DFS(Graph graph, Address start, Address end) {
//        String startStr = start.getAddress();
//        String endStr = end.getAddress();
//        start.color = -1;
//        end.color = 0;
//        for (Address vertex : graph.getVertices().values()) {
//            if (!startStr.equals(vertex.getAddress()) && !endStr.equals(vertex.getAddress())) {
//                vertex.color = 1;
//            }
//            vertex.setPi(null);
//        }
//        ArrayList<Edge> edges = graph.getAdjacent(start.getAddress());
//        ArrayList<ArrayList<Address>> paths = new ArrayList<>();
//        ArrayList<Address> path = new ArrayList<>();
//        for (Edge edge : edges) {
//            Address u = graph.getVertices().get(edge.getEnd());
//            if (u.color == 1) {
////                DFSVISIT(graph, u);
//            }
//        }
//    }


    /*
     * 深搜
     */
//    private void DFSVISIT(Graph graph, Address u, ArrayList<ArrayList<Address>> paths, ArrayList<Address> path) {
//        path.add(u);
//        u.color = 2;
//        ArrayList<Edge> edges = graph.getAdjacent(u.getAddress());
//        for (int i = 0; i < edges.size(); i++) {
//            Address v = graph.getVertices().get(edges.get(i).getEnd());
//            if (v.color == 1) {
//                path.add(v);
//                DFSVISIT(graph, v, paths, path);
//            } else if (v.color == 0) {
//                u.color = 1;
//                paths.add(path);
//            }
//        }
//        u.color = -1;
//    }


    /*
     * Dijkstra2
     */
//    private Address Dijkstra2(Graph graph, Address start, Address end) {
//        for (Address vertex : graph.getVertices().values()) {
//            vertex.setTime(Integer.MAX_VALUE);
//            vertex.setPi(null);
//            vertex.setTimes(Integer.MAX_VALUE);
//        }
//        start = graph.getVertices().get(start.getAddress());
//        start.setTime(0);
//        start.setTimes(0);
//
//        ArrayList<Address> arrayList = new ArrayList<>(graph.getVertices().values());
//        TimesHeap heap = new TimesHeap(arrayList);
//        ArrayList<Address> hasVisited = new ArrayList<>();
//        while (heap.size != 0) {
//            Address u = heap.extractMin();
//            //todo test u
//            hasVisited.add(u);
//            if (this.graph.getVertices().get("宋园路").getTimes() == 2) {
//                int d = 0;
//            }
//            if (this.graph.getVertices().get("宋园路").getTimes() == 3) {
//                int d = 0;
//            }
//            System.out.println("u: " + u.getAddress() + u.getTimes());
//            if (u.getAddress().equals(end.getAddress())) {
//                return u;
//            }
//            ArrayList<Edge> adjacent = graph.getAdjacent(u.getAddress());
//            for (int i = 0; i < adjacent.size(); i++) {
//                Edge e = adjacent.get(i);
//                Address v = graph.getVertices().get(e.getEnd());
//                //todo test v
//                System.out.print("v: " + v.getAddress());
//                if (hasVisited.contains(v)) {
//                    continue;
//                }
//                if (u.getPi() != null) {
//                    if (graph.onLine(u.getPi(), v)) {//不换乘
//                        if (v.getTimes() > u.getTimes()) {
//                            v.setTimes(u.getTimes());
//                            v.setTime(u.getTime() + e.getWeight());
//                            heap.decreaseKey(v);
//                            v.setPi(u);
//                            System.out.println("\tcase1");
//                        } else if (v.getTimes() == u.getTimes()) {
//                            if (v.getTime() > u.getTime() + e.getWeight()) {
//                                v.setTime(u.getTime() + e.getWeight());
////                                heap.decreaseKey(v);
//                                v.setPi(u);
//                                System.out.println("\tcase2");
//                            }
//                        } else
//                            System.out.println("\tcase3");
//                    } else {
//                        if (v.getTimes() > u.getTimes() + 1) {
//                            v.setTimes(u.getTimes() + 1);
//                            v.setTime(u.getTime() + 10 + e.getWeight());
//                            heap.decreaseKey(v);
//                            v.setPi(u);
//                            System.out.println("\tcase4");
//                        } else if (v.getTimes() == u.getTimes() + 1) {
//                            if (v.getTime() > u.getTime() + 10 + e.getWeight()) {
//                                v.setTime(u.getTime() + 10 + e.getWeight());
////                                heap.decreaseKey(v);
//                                v.setPi(u);
//                                System.out.println("\tcase5");
//                            }
//                        } else
//                            System.out.println("\tcase6");
//                    }
//                } else {
//                    v.setTimes(u.getTimes());
//                    v.setTime(u.getTime() + e.getWeight());
//                    heap.decreaseKey(v);
//                    v.setPi(u);
//                    System.out.println();
//                }
//            }
//        }
//        return null;
//    }
}
