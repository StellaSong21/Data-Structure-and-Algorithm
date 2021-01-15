package TEST;

import BTree.BTree;
import BTree.Entry;
import TOOL.IO;

import java.util.ArrayList;

public class TestBTree {
    public static void main(String[] args) {
        BTree tree = new BTree();
        tree.setDegree(2);
        Entry[] initial = IO.readBTxt("./src/1_initial.txt");
        for (int j = 0; j < initial.length; j++) {
            tree.BInsert(initial[j]);
        }
//        ArrayList<Entry> arrayList = tree.BSearch(tree.getRoot(), "civility", "ballerina");
//        for (int i = 0; i < arrayList.size(); i++) {
//            System.out.println("key = " + arrayList.get(i).getKey() + ", value = " + arrayList.get(i).getValue());
//        }
//        String[] keys = IO.readDel("./src/2_delete.txt");
//        for (int m = 20; m >= 0; m--) {
//            tree.BDelete(initial[m].getKey());
//        }
        for (int j = 0; j < initial.length; j++) {
            tree.BDelete(initial[j].getKey());
        }
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println();
//        tree.BDelete(initial[0].getKey());
//        tree.BDelete(initial[1].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println();
//        tree.BDelete(initial[2].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("3:");
//        tree.BDelete(initial[3].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("4:");
//        tree.BDelete(initial[4].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("5:");
//        tree.BDelete(initial[5].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("6:");
//        tree.BDelete(initial[6].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("7:");
//        tree.BDelete(initial[7].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("8:");
//        tree.BDelete(initial[8].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("9:");
//        tree.BDelete(initial[9].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("10:");
//        tree.BDelete(initial[10].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("11:");
//        tree.BDelete(initial[11].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("12:");
//        tree.BDelete(initial[12].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("13:");
//        tree.BDelete(initial[13].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("14:");
//        tree.BDelete(initial[14].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("15:");
//        tree.BDelete(initial[15].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("16:");
//        tree.BDelete(initial[16].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("17:");
//        tree.BDelete(initial[17].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("18:");
//        tree.BDelete(initial[18].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("19:");
//        tree.BDelete(initial[19].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("20:");
//        tree.BDelete(initial[20].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("21:");
//        tree.BDelete(initial[21].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("22:");
//        tree.BDelete(initial[22].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("23:");
//        tree.BDelete(initial[23].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("24:");
//        tree.BDelete(initial[24].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("25:");
//        tree.BDelete(initial[25].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("26:");
//        tree.BDelete(initial[26].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("27:");
//        tree.BDelete(initial[27].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("28:");
//        tree.BDelete(initial[28].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("29:");
//        tree.BDelete(initial[29].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("30:");
//        tree.BDelete(initial[30].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("31:");
//
//        tree.BDelete(initial[31].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("32:");
//        tree.BDelete(initial[32].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("33:");
//        tree.BDelete(initial[33].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("34:");
//        tree.BDelete(initial[34].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("35:");
//        tree.BDelete(initial[35].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("36:");
//        tree.BDelete(initial[36].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("37:");
//        tree.BDelete(initial[37].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("38:");
//        tree.BDelete(initial[38].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("39:");
//        tree.BDelete(initial[39].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println("40:");
//        tree.BDelete(initial[40].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println();
//        tree.BDelete(initial[41].getKey());
//        tree.BPreorderTreeWalk(tree.getRoot());
//        System.out.println();
//        tree.BDelete(initial[42].getKey());
//        Entry[] insert = IO.readBTxt("./src/3_insert.txt");
//        for (int j = 0; j < insert.length; j++) {
//            tree.BInsert(insert[j]);
//        }
        tree.BPreorderTreeWalk(tree.getRoot());
    }
}
