package BTree;

import java.util.ArrayList;

/**
 * @author SongYijing 17302010079
 * @date 2018/11/8 11:51
 */

public class Node {
    /**
     * parent：节点的父节点
     * previous：叶节点的前节点
     * next：叶节点的后节点
     * entries：节点的关键字列表或者entry列表
     * children：子节点列表
     * isLeaf：是否为叶节点a
     * isRoot：是否为根节点
     */
    private Node parent;
    private Node previous;
    private Node next;
    private ArrayList entries;
    private ArrayList<Node> children;
    private boolean isLeaf;
    private boolean isRoot;

    public Node() {
    }

    public Node(boolean isLeaf) {
        this.isLeaf = isLeaf;
        if (!isLeaf)
            entries = new ArrayList<String>();
        else
            entries = new ArrayList<Entry>();

        if (!isLeaf)
            children = new ArrayList<Node>();
    }

    public Node(boolean isLeaf, boolean isRoot) {
        this(isLeaf);
        this.isRoot = isRoot;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public ArrayList getEntries() {
        return entries;
    }

    public void setEntries(ArrayList entries) {
        this.entries = entries;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    int add(Entry entry) {
        if (entry == null) {
            return -1;
        }
        ArrayList<Entry> arrayList = entries;
        int size = arrayList.size();
        int i;
        for (i = 0; i < size; i++) {
            if (arrayList.get(i).getKey().toLowerCase().equals(entry.getKey().toLowerCase())) {
                arrayList.get(i).setValue(entry.getValue());
                return 1;
            }
            if (arrayList.get(i).getKey().toLowerCase().compareTo(entry.getKey().toLowerCase()) > 0) {
                arrayList.add(i, entry);
                return 0;
            }
        }
        entries.add(entry);
        return 0;
    }

    int add(String string) {
        if (string == null) {
            return -1;
        }
        ArrayList<String> arrayList = entries;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).toLowerCase().equals(string.toLowerCase())) {
                return 1;
            }
            if (arrayList.get(i).toLowerCase().compareTo(string.toLowerCase()) > 0) {
                entries.add(i, string);
                return 0;
            }
        }
        entries.add(string);
        return 0;
    }

    int search(String key) {
        int index = entries.size();
        if (this.isLeaf) {
            ArrayList<Entry> arrayList = entries;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getKey().toLowerCase().equals(key.toLowerCase())) {
                    index = i;
                    break;
                }
                if (arrayList.get(i).getKey().toLowerCase().compareTo(key.toLowerCase()) > 0) {
                    index += i;
                    break;
                }
            }
        } else {
            ArrayList<String> arrayList = entries;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).toLowerCase().equals(key.toLowerCase())) {
                    index = i;
                    break;
                }
                if (arrayList.get(i).toLowerCase().compareTo(key.toLowerCase()) > 0) {
                    index += i;
                    break;
                }
            }
        }
        return index;
    }
}
