package Project_Tree.TEST;

import Project_Tree.BTree.BTree;
import Project_Tree.BTree.Entry;
import TOOL.IO;

public class TestBTree {
    public static void main(String[] args) {
        BTree tree = new BTree();
        //tree.setDegree(2);
        Entry[] initial = IO.readBTxt("./src/1_initial.txt");
        for (int j = 0; j < initial.length; j++) {
            tree.BInsert(initial[j]);
        }
        tree.BPreorderTreeWalk(tree.getRoot());
        int time = 0;
        String[] keys = IO.readDel("./src/2_delete.txt");
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String s = tree.deleteTest(key);
            int x = tree.BDelete(key);
            if (x == 0) {
                time++;
                if (time > 100 && time <= 200) {
                    System.out.println(key + " 是删除的第 " + time + " 个元素");
                    System.out.println(s);
                }
            }
        }
    }
}
