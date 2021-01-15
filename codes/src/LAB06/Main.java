package LAB06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String fileName = "./src/input(1).txt";
        try {
            File file = new File(fileName);
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(file));
            String num = reader.readLine();
            int numOfVertices = Integer.parseInt(num.split("\\s+")[0]);
            int numOfEdges = Integer.parseInt(num.split("\\s+")[1]);
            ArrayList<Vertex> vertices = new ArrayList<>();
            ArrayList<Integer>[] matrix = new ArrayList[numOfVertices];
            for (int j = 0; j < matrix.length; j++) {
                matrix[j] = new ArrayList<>();
            }
            for (int i = 0; i < numOfEdges; i++) {
                String edge = reader.readLine();
                String[] arcs = edge.split("\\s+");
                matrix[Integer.parseInt(arcs[0]) - 1].add(Integer.parseInt(arcs[1]) - 1);
            }
            String verticesc = reader.readLine();
            String[] strings = verticesc.split("\\s+");
            for (String string : strings) {
                vertices.add(new Vertex(Integer.parseInt(string)));
            }
            reader.close();
            Graph graph = new Graph(numOfVertices, matrix, vertices);
            long start = System.currentTimeMillis();
            Vertex longest = graph.dagLongestPath();
            long end = System.currentTimeMillis();
            System.out.println("总工期为 " + longest.length);
            System.out.println("工作顺序如下：");
            ArrayList<Vertex> lengthest = new ArrayList<>();
            while (longest != null) {
                lengthest.add(longest);
                longest = longest.pi;
            }
            for (int m = lengthest.size() - 1; m >= 0; m--) {
                System.out.print((vertices.indexOf(lengthest.get(m)) + 1) + "\t");
            }
            System.out.println();
            System.out.println("这些工作对应的权重如下：");
            for (int m = lengthest.size() - 1; m >= 0; m--) {
                System.out.print(lengthest.get(m).getWeight() + "\t");
            }
            System.out.println();
            System.out.println("找到最长路径所需时间为 " + (end - start) + " ms");
        } catch (IOException e) {
            System.err.println("读取文件异常");
        } catch (NumberFormatException e) {
            System.err.println("输入文件格式错误");
        }
    }
}
