package com.fudan.sw.dsa.project2.entity;

import java.util.*;

public class test {
    private Stack<Address> stack = new Stack<>();
    private List<Address> addresses;
    private Graph graph;
    private Address flag;
    private double time;
    private int times;


    public test(List<Address> sers, Graph graph) {
        this.graph = graph;
        this.addresses = sers;
        this.time = Integer.MAX_VALUE;
        this.times = Integer.MAX_VALUE;
        initial();
    }

    public boolean isVertexInStack(Address vertex) {
        Iterator<Address> it = stack.iterator();
        while (it.hasNext()) {
            Address address0 = (Address) it.next();
            if (address0.getAddress().equals(vertex.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public void showAndSavePath() {
        Address[] o = (Address[]) stack.toArray(new Address[stack.size()]);
        if (o[o.length - 1].getTimes() < times) {
            times = o[o.length - 1].getTimes();
            time = o[o.length - 1].getTime();
            addresses.clear();
            addresses.addAll(Arrays.asList(o));
        } else if (o[o.length - 1].getTimes() == times) {
            if (o[o.length - 1].getTime() < time) {
                time = o[o.length - 1].getTime();
                addresses.clear();
                addresses.addAll(Arrays.asList(o));
            }
        }
//        initial();
    }

    /**
     * 寻找路径的方法
     * cNode: 当前的起始节点currentNode
     * pNode: 当前起始节点的上一节点previousNode
     * sNode: 最初的起始节点startNode
     * eNode: 终点endNode
     */
//    public boolean getPaths(Graph graph, Address cNode, Address pNode, Address sNode, Address eNode) {
//        Address nNode = null;
//        if (cNode != null && pNode != null && cNode == pNode)
//            return false;
//        if (cNode != null) {
//            int i = 0;
//            stack.push(cNode);
//            if (cNode == eNode) {
//                showAndSavePath();
//                return true;
//            } else {
//                nNode = graph.getRelationAddress(cNode).get(i);
//                while (nNode != null) {
//                    if (pNode != null && (nNode == sNode || nNode == pNode || isVertexInStack(nNode))) {
//                        i++;
//                        if (i >= graph.getRelationAddress(cNode).size())
//                            nNode = null;
//                        else
//                            nNode = graph.getRelationAddress(cNode).get(i);
//                        continue;
//                    }
//
//                    /* 以nNode为新的起始节点，当前起始节点cNode为上一节点，递归调用寻路方法 */
//                    if (getPaths(graph, nNode, cNode, sNode, eNode))/* 递归调用 */ {
//                        /* 如果找到一条路径，则弹出栈顶节点 */
//                        stack.pop();
//                    }
//                    /* 继续在与cNode有连接关系的节点集中测试nNode */
//                    i++;
//                    if (i >= graph.getRelationAddress(cNode).size())
//                        nNode = null;
//                    else
//                        nNode = graph.getRelationAddress(cNode).get(i);
//                }
//                stack.pop();
//                return false;
//            }
//        } else
//            return false;
//    }
    private void initial() {
        for (Address address : graph.getVertices().values()) {
            address.setTime(Integer.MAX_VALUE);
            address.setTimes(Integer.MAX_VALUE);
        }
    }

    public boolean getPath(Address currentAddr, Address previousAddr, Address startAddr, Address endAddr) {
        Address address = null;
        if (currentAddr != null && previousAddr != null && currentAddr == previousAddr) {
            return false;
        }
        if (currentAddr != null) {
            int i = 0;
            if (previousAddr == null) {
                flag = currentAddr;
                currentAddr.setTime(0);
                currentAddr.setTimes(0);
            } else {
                if (!graph.onLine(flag, currentAddr)) {
                    currentAddr.setTimes(flag.getTimes() + 1);
                    flag = previousAddr;
                    if (currentAddr.getTimes() > times || currentAddr.getTimes() > 15) {
                        return false;
                    }
                } else {
                    currentAddr.setTimes(flag.getTimes());
                }
                currentAddr.setTime(previousAddr.getTime() + graph.lineBtw(previousAddr, currentAddr).get(0).getWeight());
            }

            stack.push(currentAddr);
            //todo current
//            System.out.println("stack: " + stack.size());
//            System.out.println("current: " + currentAddr.getAddress());
            if (currentAddr == endAddr) {
                showAndSavePath();
                return true;
            } else {
                address = graph.getRelationAddress(currentAddr).get(i);
                while (address != null) {
                    if (previousAddr != null && (address == startAddr || previousAddr == address || isVertexInStack(address))) {
                        i++;
                        if (i >= graph.getRelationAddress(currentAddr).size()) {
                            address = null;
                        } else {
                            address = graph.getRelationAddress(currentAddr).get(i);
                        }
                        continue;
                    }
                    if (getPath(address, currentAddr, startAddr, endAddr)) {
                        Address address0 = stack.pop();
                        address0.setTimes(Integer.MAX_VALUE);
                        address0.setTime(Integer.MAX_VALUE);
                    }

                    i++;
                    if (i < graph.getRelationAddress(currentAddr).size()) {
                        address = graph.getRelationAddress(currentAddr).get(i);
                    } else {
                        address = null;
                    }
                }
                Address address0 = stack.pop();
                //todo test pop
//                System.out.println("pop: " + address0.getAddress());
                address0.setTimes(Integer.MAX_VALUE);
                address0.setTime(Integer.MAX_VALUE);
                return false;
            }
        } else {
            return false;
        }
    }
}
