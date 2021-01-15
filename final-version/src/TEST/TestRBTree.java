package TEST;

import RBTree.RBTree;
import TOOL.IO;
import RBTree.Node;
import java.util.ArrayList;

public class TestRBTree {
    public static void main(String[] args) {
        RBTree tree = new RBTree();
        Node[] initial = IO.readRBTxt("./src/1_initial.txt");
        for (int j = 0; j < initial.length; j++) {
            tree.RBInsert(initial[j]);
        }

        Node s = tree.RBSearch(tree.getRoot().getLeftChild(), "mollify");
        System.out.println("key = " + s.getKey() + ", value = " + s.getValue() + ", " + s.getColor());

        ArrayList<Node> arrayList = tree.RBSearch(tree.getRoot(), "fustian", "mollify");
        if (arrayList != null)
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println("key = " + arrayList.get(i).getKey() + " value = " + arrayList.get(i).getValue());
            }

        //tree.preorderTreeWalk(tree.getRoot());
        String[] keys = IO.readDel("./src/2_delete.txt");
        for (int j = 0; j < keys.length; j++) {
            tree.RBDelete(keys[j]);
        }

        Node[] insert = IO.readRBTxt("./src/3_insert.txt");
        for (int j = 0; j < insert.length; j++) {
            tree.RBInsert(insert[j]);
        }
        tree.RBPreorderTreeWalk(tree.getRoot());


        //  tree.preorderTreeWalk(tree.getRoot());

        //tree.preorderTreeWalk(tree.getRoot());
//        Node a = new Node("a", "a");
//        Node b = new Node("b", "a");
//        Node c = new Node("c", "a");
//        Node d = new Node("d", "a");
//        Node e = new Node("e", "a");
//        Node f = new Node("f", "a");
//        Node g = new Node("g", "a");
//        Node h = new Node("h", "a");
//        Node i = new Node("i", "a");
//        Node j = new Node("j", "a");
//        Node k = new Node("k", "a");
//        Node l = new Node("l", "a");
//        Node m = new Node("m", "a");
//        Node n = new Node("n", "a");
//        Node o = new Node("o", "a");
//        Node p = new Node("p", "a");
//        Node q = new Node("q", "a");
//        Node r = new Node("r", "a");
//        Node s = new Node("s", "a");
//
//        Node[] nodes = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s};
//        for (int num = 0; num < nodes.length; num++) {
//            tree.RBInsert(nodes[num]);
//        }
//
//        for (int num = 4; num < 10; num++) {
//            tree.RBDelete(nodes[num].getKey());
//        }
//        tree.preorderTreeWalk(tree.getRoot());
    }
}