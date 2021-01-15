package LAB06;

import java.util.ArrayList;

public class Vertex {
    private int weight;
    ArrayList<Vertex> predecessor;
    int length;
    int color;//颜色：0表示白色，1表示灰色，2表示黑色
    Vertex pi;

    public Vertex() {
    }

    public Vertex(int weight) {
        this.weight = weight;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
