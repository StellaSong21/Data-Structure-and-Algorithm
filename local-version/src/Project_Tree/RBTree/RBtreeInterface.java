package Project_Tree.RBTree;

import java.util.ArrayList;

public interface RBtreeInterface {
    int RBInsert(Node node);

    int RBDelete(String key);

    Node RBSearch(Node x, String key);

    ArrayList<Node> RBSearch(Node root, String start, String end);

    void RBPreorderTreeWalk(Node root);
}
