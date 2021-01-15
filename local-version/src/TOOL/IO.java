package TOOL;

import Project_Tree.BTree.Entry;
import Project_Tree.RBTree.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IO {
    private static ArrayList<String> read(String filePath) {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static Node[] readRBTxt(String filePath) {
        ArrayList<String> arrayList = read(filePath);
        int length = arrayList.size();
        Node[] array = new Node[length / 2];
        for (int i = 0; i < length - 1; i += 2) {
            array[i / 2] = new Node(arrayList.get(i + 1), arrayList.get(i + 2));
        }
        return array;
    }

    public static String[] readDel(String filePath) {
        ArrayList<String> arrayList = read(filePath);
        int length = arrayList.size() - 1;
        String[] keys = new String[length];
        for (int i = 0; i < length; i++) {
            keys[i] = arrayList.get(i + 1);
        }
        return keys;
    }

    public static Entry[] readBTxt(String filePath){
        ArrayList<String> arrayList = read(filePath);
        int length = arrayList.size();
        Entry[] array = new Entry[length / 2];
        for (int i = 0; i < length - 1; i += 2) {
            array[i / 2] = new Entry(arrayList.get(i + 1), arrayList.get(i + 2));
        }
        return array;
    }
}
