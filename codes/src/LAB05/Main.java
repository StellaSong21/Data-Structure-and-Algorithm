package LAB05;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] ints = {5, 3, 8, 7, 2, 6, 4, 9, 10, 1};
        for (int anInt : ints) { bst.insert(anInt); }
        System.out.println("Inorder (sorted):");
        bst.inorderTreeWalk(bst.getRoot());
        System.out.println();
        System.out.println("Postorder:");
        bst.postorderTreeWalk(bst.getRoot());
        System.out.println();
        System.out.println("Preorder:");
        bst.preorderTreeWalk(bst.getRoot());
        System.out.println();
        System.out.println("The number of nodes is " + bst.getSize());
        System.out.println("Is 5 in the tree? ");
        Node m = bst.iterativeSearch(bst.getRoot(), 5);
        if (m != null) System.out.println(m.getKey() + " in the tree");
        else System.out.println("NOT in the tree");
        System.out.println("Inorder of left children:");
        bst.inorderTreeWalk(bst.getRoot().getLeftChild());
        System.out.println();
        System.out.println("Successor of 5: " + bst.successor(bst.getRoot()).getKey());
        System.out.println("Predecessor of 5: " + bst.predecessor(bst.getRoot()).getKey());
        System.out.println("After delete root, sort: ");
        bst.delete(bst.getRoot());
        bst.inorderTreeWalk(bst.getRoot());
        System.out.println();

        System.out.println("**********************************************************************************************");
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        String[] strings = {"song", "zhang", "hu", "qiu", "lv", "liu", "li", "wu", "wang"};
        for (String string : strings) { tree.insert(string); }
        System.out.println("Inorder (sorted):");
        tree.inorderTreeWalk(tree.getRoot());
        System.out.println();
        System.out.println("Postorder:");
        tree.postorderTreeWalk(tree.getRoot());
        System.out.println();
        System.out.println("Preorder:");
        tree.preorderTreeWalk(tree.getRoot());
        System.out.println();
        System.out.println("The number of nodes is " + tree.getSize());
        System.out.println("Is lu in the tree? ");
        Node n = tree.iterativeSearch(tree.getRoot(), "lu");
        if (n != null) System.out.println(n.getKey() + " in the tree");
        else System.out.println("NOT in the tree");
        System.out.println("Inorder of left children:");
        tree.inorderTreeWalk(tree.getRoot().getLeftChild());
        System.out.println();
        System.out.println("Successor of song: " + tree.successor(tree.getRoot()).getKey());
        System.out.println("Predecessor of song: " + tree.predecessor(tree.getRoot()).getKey());
        System.out.println("After delete root, sort: ");
        tree.delete(tree.getRoot());
        tree.inorderTreeWalk(tree.getRoot());
    }
}
