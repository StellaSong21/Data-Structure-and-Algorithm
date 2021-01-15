package Project_Tree.RBTree;

import java.util.ArrayList;

/**
 * @author SongYijing 17302010079
 * @date 2018/11/6 10:22
 */

public class RBTree implements RBtreeInterface {
    private final Node NIL = new Node(1, null, null);
    private Node root;
    private int size;

    public RBTree() {
        root = NIL;
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int RBInsert(Node node) {
        return insert(node);
    }

    @Override
    public int RBDelete(String key) {
        return delete(key);
    }

    @Override
    public Node RBSearch(Node x, String key) {
        return search(x, key);
    }

    @Override
    public ArrayList<Node> RBSearch(Node root, String start, String end) {
        return search(root, start, end);
    }

    @Override
    public void RBPreorderTreeWalk(Node root) {
        preorderTreeWalk(root);
    }

    private int insert(Node z) {
        Node y = NIL;
        Node x = root;
        while (x != NIL) {
            y = x;
            if (z.getKey().toLowerCase().compareTo(x.getKey().toLowerCase()) < 0)
                x = x.getLeftChild();
            else if (z.getKey().toLowerCase().compareTo(x.getKey().toLowerCase()) > 0)
                x = x.getRightChild();
            else {
                x.setValue(z.getValue());
                return 1;
            }
        }
        z.setParent(y);
        if (y == NIL)
            root = z;
        else if (z.getKey().toLowerCase().compareTo(y.getKey().toLowerCase()) < 0)
            y.setLeftChild(z);
        else
            y.setRightChild(z);
        z.setLeftChild(NIL);
        z.setRightChild(NIL);
        z.setColor(0);
        insertFixup(z);
        size++;
        return 0;
    }

    private void insertFixup(Node z) {
        while (z.getParent().getColor() == 0) {
            if (z.getParent() == z.getParent().getParent().getLeftChild()) {
                Node y = z.getParent().getParent().getRightChild();
                if (y.getColor() == 0) {
                    z.getParent().setColor(1);
                    y.setColor(1);
                    z.getParent().getParent().setColor(0);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRightChild()) {
                        z = z.getParent();
                        leftRotation(z);
                    }
                    z.getParent().setColor(1);
                    z.getParent().getParent().setColor(0);
                    rightRotation(z.getParent().getParent());
                }
            } else {
                Node y = z.getParent().getParent().getLeftChild();
                if (y.getColor() == 0) {
                    z.getParent().setColor(1);
                    y.setColor(1);
                    z.getParent().getParent().setColor(0);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeftChild()) {
                        z = z.getParent();
                        rightRotation(z);
                    }
                    z.getParent().setColor(1);
                    z.getParent().getParent().setColor(0);
                    leftRotation(z.getParent().getParent());
                }
            }
        }
        this.root.setColor(1);
    }

    private void leftRotation(Node x) {
        Node y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if (y.getLeftChild() != NIL)
            y.getLeftChild().setParent(x);
        y.setParent(x.getParent());
        if (x.getParent() == NIL)
            root = y;
        else if (x == x.getParent().getLeftChild())
            x.getParent().setLeftChild(y);
        else
            x.getParent().setRightChild(y);
        y.setLeftChild(x);
        x.setParent(y);
    }

    private void rightRotation(Node x) {
        Node y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());
        if (y.getRightChild() != NIL)
            y.getRightChild().setParent(x);
        y.setParent(x.getParent());
        if (x == x.getParent().getRightChild())
            x.getParent().setRightChild(y);
        else if (x.getParent() == NIL)
            root = y;
        else
            x.getParent().setLeftChild(y);
        y.setRightChild(x);
        x.setParent(y);
    }

    private void transplant(Node u, Node v) {
        if (u.getParent() == NIL)
            root = v;
        else if (u == u.getParent().getLeftChild())
            u.getParent().setLeftChild(v);
        else
            u.getParent().setRightChild(v);
        if (v != NIL)
            v.setParent(u.getParent());
    }

    private int delete(String key) {
        Node z = RBSearch(root, key);
        if (z == NIL) {
            return -1;
        }
        Node y;
        Node x = NIL;
        if (z.getLeftChild() == NIL || z.getRightChild() == NIL)
            y = z;
        else {
            if (predecessor(z).getColor() == 0)
                y = predecessor(z);
            else
                y = successor(z);
        }

        if (y.getLeftChild() != NIL)
            x = y.getLeftChild();
        else
            x = y.getRightChild();
        x.setParent(y.getParent());
        if (y.getParent() == NIL)
            root = x;
        else {
            if (y == y.getParent().getLeftChild())
                y.getParent().setLeftChild(x);
            else y.getParent().setRightChild(x);
        }
        if (y != z)
            z.setKey(y.getKey());
        if (y.getColor() == 1)
            deleteFixup(x);
        size--;
        return 0;
    }

    private void deleteFixup(Node x) {
        while (x != root && x.getColor() == 1) {
            if (x == x.getParent().getLeftChild()) {
                Node w = x.getParent().getRightChild();
                if (w.getColor() == 0) {
                    w.setColor(1);
                    x.getParent().setColor(0);
                    leftRotation(x.getParent());
                    w = x.getParent().getRightChild();
                }
                if (w.getLeftChild().getColor() == 1 && w.getRightChild().getColor() == 1) {
                    w.setColor(0);
                    x = x.getParent();
                } else {
                    if (w.getRightChild().getColor() == 1) {
                        w.getLeftChild().setColor(1);
                        w.setColor(0);
                        rightRotation(w);
                        w = x.getParent().getRightChild();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(1);
                    w.getRightChild().setColor(1);
                    leftRotation(x.getParent());
                    x = root;
                }

            } else {
                Node w = x.getParent().getLeftChild();
                if (w.getColor() == 0) {
                    w.setColor(1);
                    x.getParent().setColor(0);
                    rightRotation(x.getParent());
                    w = x.getParent().getLeftChild();
                }
                if (w.getRightChild().getColor() == 1 && w.getLeftChild().getColor() == 1) {
                    w.setColor(0);
                    x = x.getParent();
                } else {
                    if (w.getLeftChild().getColor() == 1) {
                        w.getRightChild().setColor(1);
                        w.setColor(0);
                        leftRotation(w);
                        w = x.getParent().getLeftChild();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(1);
                    w.getLeftChild().setColor(1);
                    rightRotation(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(1);
    }

    private Node search(Node x, String key) {
        if (x == NIL || key.toLowerCase().equals(x.getKey().toLowerCase()))
            return x;
        if (key.toLowerCase().compareTo(x.getKey().toLowerCase()) < 0)
            return RBSearch(x.getLeftChild(), key);
        return RBSearch(x.getRightChild(), key);
    }

    private ArrayList<Node> search(Node root, String start, String end) {
        if (this.root == NIL) {
            return null;
        }
        if (start == null || "".equals(start)) {
            start = minimum(this.root).getKey();
        }
        if (end == null || "".equals(end)) {
            end = maximum(this.root).getKey();
        }
        ArrayList<Node> arrayList = new ArrayList<Node>();
        if (start.toLowerCase().compareTo(end.toLowerCase()) < 0)
            inorderTreeWalk(root, start, end, arrayList);
        else if (start.equals(end)) {
            arrayList.add(RBSearch(root, start));
        } else
            inorderTreeWalk(root, end, start, arrayList);
        return arrayList;
    }

    private void inorderTreeWalk(Node x, String start, String end, ArrayList<Node> arrayList) {
        if (x != NIL) {
            inorderTreeWalk(x.getLeftChild(), start, end, arrayList);
            if (x.getKey().toLowerCase().compareTo(start.toLowerCase()) > 0 && x.getKey().toLowerCase().compareTo(end.toLowerCase()) < 0) {
                arrayList.add(x);
            }
            inorderTreeWalk(x.getRightChild(), start, end, arrayList);
        }
    }

    private void preorderTreeWalk(Node root) {
        if (root != NIL) {
            String[] s = info(root);
            System.out.println("level = " + s[0] + ", child = " + s[1] + ", " + s[2] + ":" + s[3] + s[4]);
            if (root.getLeftChild() == NIL)
                System.out.println("level = " + (Integer.parseInt(s[0]) + 1) + ", child = " + 0 + ", null(BLACK)");
            preorderTreeWalk(root.getLeftChild());
            if (root.getRightChild() == NIL)
                System.out.println("level = " + (Integer.parseInt(s[0]) + 1) + ", child = " + 1 + ", null(BLACK)");
            preorderTreeWalk(root.getRightChild());
        }
    }

    private String[] info(Node root) {
        int level = 0;
        int child = 0;
        if (root != this.root) {
            Node y = root.getParent();
            if (root == y.getRightChild()) {
                child = 1;
            }
            while (y != NIL) {
                level++;
                y = y.getParent();
            }
        }
        String key = root.getKey();
        String value = root.getValue();
        int color = root.getColor();
        String[] s = new String[5];
        s[0] = level + "";
        s[1] = child + "";
        s[2] = key;
        s[3] = value;
        s[4] = color == 0 ? "(RED)" : "(BLACK)";
        return s;
    }

    private Node minimum(Node x) {
        while (x.getLeftChild() != NIL)
            x = x.getLeftChild();
        return x;
    }

    private Node maximum(Node x) {
        while (x.getRightChild() != NIL)
            x = x.getRightChild();
        return x;
    }

    private Node successor(Node z) {
        if (z.getRightChild() != NIL) {
            return minimum(z.getRightChild());
        }
        Node y = z.getParent();
        while (y != NIL && y.getRightChild() == z) {
            z = y;
            y = y.getParent();
        }
        return y;
    }

    private Node predecessor(Node z) {
        if (z.getLeftChild() != NIL) {
            return maximum(z.getLeftChild());
        }
        Node y = z.getParent();
        while (y != NIL && y.getLeftChild() == z) {
            z = y;
            y = y.getParent();
        }
        return y;
    }
}
