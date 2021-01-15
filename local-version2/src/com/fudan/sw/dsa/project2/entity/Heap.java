package com.fudan.sw.dsa.project2.entity;

import java.util.ArrayList;

public class Heap {
    private int d = 2;
    private ArrayList<String> names;
    private ArrayList<Vertex> vertices;
    public int size;
    private String property;

    public Heap(ArrayList<Vertex> arrayList, String property) {
        this.size = arrayList.size();
        this.vertices = new ArrayList<>();
        this.names = new ArrayList<>();
        for (Vertex vertex : arrayList) {
            this.vertices.add(vertex);
            this.names.add(vertex.getName());
        }
        this.property = property;
        buildMinHeap();
    }

    ArrayList<Vertex> minHeapify(ArrayList<Vertex> vertices, ArrayList<String> names, int i, int length) {
        int least = i;
        Vertex leastValue = vertices.get(i);
        String leastStr = names.get(i);
        int left = i * d + 1;
        int right = (i + 1) * d;
        if ("time".equals(this.property)) {
            for (int j = left; j <= right; j++) {
                if (j <= length - 1 && vertices.get(j).time < leastValue.time) {
                    least = j;
                    leastValue = vertices.get(j);
                    leastStr = names.get(j);
                }
            }
        }
        if ("times".equals(this.property)) {
            for (int j = left; j <= right; j++) {
                if (j <= length - 1 && vertices.get(j).times < leastValue.times) {
                    least = j;
                    leastValue = vertices.get(j);
                    leastStr = names.get(j);
                }
            }
        }
        if (least != i) {
            vertices.set(least, vertices.get(i));
            names.set(least, names.get(i));

            vertices.set(i, leastValue);
            names.set(i, leastStr);

            minHeapify(vertices, names, least, length);
        }
        return vertices;
    }

    private void buildMinHeap() {
        for (int i = (int) Math.floor(this.vertices.size() / 2) - 1; i >= 0; i--) {
            minHeapify(this.vertices, this.names, i, this.vertices.size());
        }
    }

    public Vertex extractMin() {
        if (this.size < 1) {
            System.out.println("没有元素");
            return null;
        }
        Vertex max = this.vertices.get(0);

        this.vertices.set(0, this.vertices.get(this.vertices.size() - 1));
        this.names.set(0, this.names.get(this.names.size() - 1));

        this.vertices.remove(this.vertices.size() - 1);
        this.names.remove(this.names.size() - 1);

        minHeapify(this.vertices, this.names, 0, this.vertices.size());
        this.size--;
        return max;
    }

    public void decreaseKey(Vertex vertex) {
        if ("time".equals(this.property)) {
            int index = this.names.indexOf(vertex.getName());
            this.vertices.get(index).time = vertex.time;
            int parent = (int) Math.floor((index - 1) / d);
            while (index > 0 && this.vertices.get(parent).time > this.vertices.get(index).time) {
                Vertex temp = this.vertices.get(index);
                String tempStr = this.names.get(index);

                this.vertices.set(index, this.vertices.get(parent));
                this.names.set(index, this.names.get(parent));

                this.vertices.set(parent, temp);
                this.names.set(parent, tempStr);

                index = parent;
                parent = (int) Math.floor((index - 1) / d);
            }
        }
        if ("times".equals(this.property)) {
            int index = this.names.indexOf(vertex.getName());
            this.vertices.get(index).times = vertex.times;
            int parent = (int) Math.floor((index - 1) / d);
            while (index > 0 && this.vertices.get(parent).times > this.vertices.get(index).times) {
                String tempStr = this.names.get(index);
                Vertex temp = this.vertices.get(index);

                this.names.set(index, this.names.get(parent));
                this.vertices.set(index, this.vertices.get(parent));

                this.names.set(parent, tempStr);
                this.vertices.set(parent, temp);

                index = parent;
                parent = (int) Math.floor((index - 1) / d);
            }
        }
    }

}
