package Project_Tree.BTree;

import java.util.ArrayList;

public interface BTreeInterface {
    int BInsert(Entry entry);

    int BDelete(String key);

    Entry BSearch(Node x, String key);

    ArrayList<Entry> BSearch(Node root, String start, String end);

    void BPreorderTreeWalk(Node root);
}
