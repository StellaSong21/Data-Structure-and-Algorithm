package LAB05;

public class BinarySearchTree<T extends Comparable> {
    private Node root;
    private int size;

    public BinarySearchTree() {
        size = 0;
    }

    public BinarySearchTree(Node root) {
        this.root = root;
        size = 1;
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public boolean insert(T i) {
        Node y = null;
        Node x = this.root;
        Node z = new Node(i);
        while (x != null) {
            y = x;
            int cmp = z.getKey().compareTo(x.getKey());
            if (cmp < 0) {
                x = x.getLeftChild();
            } else {
                x = x.getRightChild();
            }
        }
        z.setParent(y);
        if (y == null) {
            this.root = z;
        } else if (z.getKey().compareTo(y.getKey()) < 0) {
            y.setLeftChild(z);
        } else {
            y.setRightChild(z);
        }
        size++;
        return true;
    }

    public Node delete(Node z) {
        Node y = null;
        Node x = null;
        if (z.getLeftChild() == null || z.getRightChild() == null) {
            y = z;
        } else {
            y = successor(z);
        }
        if (y.getLeftChild() != null) {
            x = y.getLeftChild();
        } else {
            x = y.getRightChild();
        }
        if (x != null) {
            x.setParent(y.getParent());
        }
        if (y.getParent() == null) {
            this.root = x;
        } else if (y == y.getParent().getLeftChild()) {
            y.getParent().setLeftChild(x);
        } else {
            y.getParent().setRightChild(x);
        }
        if (y != z) {
            z.setKey(y.getKey());
        }
        size--;
        return y;
    }

    public Node recursionSearch(Node x, T key) {
        if (x == null || key == x.getKey()) {
            return x;
        }
        if (key.compareTo(x.getKey()) < 0) {
            return recursionSearch(x.getLeftChild(), key);
        } else {
            return recursionSearch(x.getRightChild(), key);
        }
    }

    public Node iterativeSearch(Node x, T key) {
        while (x != null && key != x.getKey()) {
            if (key.compareTo(x.getKey()) < 0) {
                x = x.getLeftChild();
            } else {
                x = x.getRightChild();
            }
        }
        return x;
    }

    public Node successor(Node x) {
        if (x.getRightChild() != null) {
            return minimum(x.getRightChild());
        }
        Node y = x.getParent();
        while (y != null && x != y.getRightChild()) {
            x = y;
            y = x.getParent();
        }
        return y;
    }

    public Node predecessor(Node x) {
        if (x.getLeftChild() != null) {
            return maximum(x.getLeftChild());
        }
        Node y = x.getParent();
        while (y != null && x != y.getLeftChild()) {
            x = y;
            y = x.getParent();
        }
        return y;
    }

    private Node minimum(Node x) {
        while (x.getLeftChild() != null) {
            x = x.getLeftChild();
        }
        return x;
    }

    private Node maximum(Node x) {
        while (x.getRightChild() != null) {
            x = x.getRightChild();
        }
        return x;
    }

    public void inorderTreeWalk(Node root) {
        if (root != null) {
            inorderTreeWalk(root.getLeftChild());
            System.out.print(root.getKey() + "\t");
            inorderTreeWalk(root.getRightChild());
        }
    }

    public void preorderTreeWalk(Node root) {
        if (root != null) {
            System.out.print(root.getKey() + "\t");
            preorderTreeWalk(root.getLeftChild());
            preorderTreeWalk(root.getRightChild());
        }
    }

    public void postorderTreeWalk(Node root) {
        if (root != null) {
            postorderTreeWalk(root.getLeftChild());
            postorderTreeWalk(root.getRightChild());
            System.out.print(root.getKey() + "\t");
        }
    }
}
