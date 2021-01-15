package com.fudan.sw.dsa.project2.entity;

import java.util.ArrayList;

public class Heap {
    private int d = 2;
    private ArrayList<String> names;
    private ArrayList<Address> vertices;
    public int size;

    public Heap(ArrayList<Address> arrayList) {
        this.size = arrayList.size();
        this.names = new ArrayList<>();
        this.vertices = new ArrayList<>();
        for (Address vertex : arrayList) {
            this.vertices.add(vertex);
            this.names.add(vertex.getAddress());
        }
        buildMinHeap();
    }

    private void minHeapify(ArrayList<Address> vertices, ArrayList<String> names, int i, int length) {
        if (length <= 0) {
            return;
        }
        int least = i;
        Address leastValue = vertices.get(i);
        String leastStr = names.get(i);
        int left = i * d + 1;
        int right = (i + 1) * d;

        for (int j = left; j <= right; j++) {
            if (j <= length - 1 && vertices.get(j).getTime() < leastValue.getTime()) {
                least = j;
                leastValue = vertices.get(j);
                leastStr = names.get(j);
            }
        }

        if (least != i) {
            vertices.set(least, vertices.get(i));
            names.set(least, names.get(i));
            vertices.set(i, leastValue);
            names.set(i, leastStr);
            minHeapify(vertices, names, least, length);
        }
    }

    private void buildMinHeap() {
        for (int i = (int) Math.floor(this.size / 2.0) - 1; i >= 0; i--) {
            minHeapify(this.vertices, this.names, i, this.size);
        }
    }

    public Address extractMin() {
        if (this.size < 1) {
            System.out.println("没有元素");
            return null;
        }
        Address min = vertices.get(0);

        this.vertices.set(0, vertices.get(vertices.size() - 1));
        this.names.set(0, names.get(names.size() - 1));

        this.vertices.remove(vertices.size() - 1);
        this.names.remove(names.size() - 1);

        minHeapify(vertices, names, 0, vertices.size());
        this.size--;
        return min;
    }

    public void decreaseKey(Address vertex) {
        int index = this.names.indexOf(vertex.getAddress());
        this.vertices.get(index).setTime(vertex.getTime());
        int parent = (int) Math.floor((index - 1.0) / d);
        while (index > 0 && this.vertices.get(parent).getTime() > this.vertices.get(index).getTime()) {
            Address temp = this.vertices.get(index);
            String tempStr = this.names.get(index);
            this.vertices.set(index, this.vertices.get(parent));
            this.names.set(index, this.names.get(parent));
            this.vertices.set(parent, temp);
            this.names.set(parent, tempStr);
            index = parent;
            parent = (int) Math.floor((index - 1.0) / d);
        }
    }
}

