package BTree;

import java.util.ArrayList;

public class BTree implements BTreeInterface {
    /**
     * degree：阶数
     * root：根节点
     * head：叶节点链表的表头
     */

    private int degree = 2;//[degree-1，2degree-1]  2 <= degree <= 37
    private Node root;
    private int size;

    public void setDegree(int degree) {
        if (degree >= 2)
            this.degree = degree;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public int BInsert(Entry entry) {
        return insert(entry);
    }

    @Override
    public int BDelete(String key) {
        return delete(key);
    }

    @Override
    public Entry BSearch(Node x, String key) {
        return search(x, key);
    }

    @Override
    public ArrayList<Entry> BSearch(Node root, String start, String end) {
        return search(root, start, end);
    }

    @Override
    public void BPreorderTreeWalk(Node root) {
        preorderTreeWalk(root);
    }

    //插入：0；修改：1
    private int insert(Entry entry) {
        if (root == null) {
            this.root = new Node(true, true);
        }
        if (root.isLeaf() && root.getEntries().size() < 2 * degree - 1) {
            int x = root.add(entry);
            if (x == 0)
                size++;
            return x;
        }
        Node node = searchNode(this.root, entry.getKey());
        int result = node.add(entry);
        if (result == 1)
            return 1;
        while (node.getEntries().size() >= 2 * degree) {
            Node[] splitNodes = splitNode(node);
            if (splitNodes[1].isLeaf()) {
                splitNodes[2].setNext(node.getNext());
                if (node.getNext() != null)
                    node.getNext().setPrevious(splitNodes[2]);

                splitNodes[1].setPrevious(node.getPrevious());
                if (node.getPrevious() != null)
                    node.getPrevious().setNext(splitNodes[1]);

                splitNodes[1].setNext(splitNodes[2]);
                splitNodes[2].setPrevious(splitNodes[1]);
            }
            if (!node.isLeaf()) {
                ArrayList<Node> children = node.getChildren();
                for (int i = 0; i <= degree; i++) {
                    splitNodes[1].getChildren().add(children.get(i));
                    children.get(i).setParent(splitNodes[1]);
                }
                for (int i = degree + 1; i < children.size(); i++) {
                    splitNodes[2].getChildren().add(children.get(i));
                    children.get(i).setParent(splitNodes[2]);
                }
            }
            if (node.isRoot()) {
                splitNodes[2].setParent(splitNodes[0]);
                splitNodes[1].setParent(splitNodes[0]);
                ArrayList<Node> arrayList = splitNodes[0].getChildren();
                arrayList.add(splitNodes[1]);
                arrayList.add(splitNodes[2]);
                this.root = splitNodes[0];
                break;
            } else {
                splitNodes[2].setParent(node.getParent());
                splitNodes[1].setParent(node.getParent());
                ArrayList<Node> children = node.getParent().getChildren();
                int i = children.indexOf(node);
                children.remove(node);
                children.add(i, splitNodes[1]);
                children.add(i + 1, splitNodes[2]);
                node = node.getParent();
                node.add((String) splitNodes[0].getEntries().get(0));
            }
        }
        size++;
        return 0;
    }

    private Node[] splitNode(Node node) {
        Node[] nodes;
        if (node.isLeaf() && node.isRoot())
            nodes = new Node[]{new Node(false, true), new Node(true, false), new Node(true, false)};
        else if (node.isLeaf() && !node.isRoot())
            nodes = new Node[]{new Node(false, false), new Node(true, false), new Node(true, false)};
        else if (!node.isLeaf() && node.isRoot())
            nodes = new Node[]{new Node(false, true), new Node(false, false), new Node(false, false)};
        else
            nodes = new Node[]{new Node(false, false), new Node(false, false), new Node(false, false)};
        if (node.isLeaf()) {
            ArrayList<Entry> entries = node.getEntries();
            ArrayList<String> parent = new ArrayList<String>();
            parent.add(entries.get(degree).getKey());
            nodes[0].setEntries(parent);
            ArrayList<Entry> entries1 = new ArrayList<Entry>();
            for (int i = 0; i < degree; i++)
                entries1.add(entries.get(i));
            nodes[1].setEntries(entries1);
            ArrayList<Entry> entries2 = new ArrayList<Entry>();
            for (int i = degree; i < degree * 2 && i < entries.size(); i++)
                entries2.add(entries.get(i));
            nodes[2].setEntries(entries2);
        } else {
            ArrayList<String> entries = node.getEntries();
            ArrayList<String> parent = new ArrayList<String>();
            parent.add(entries.get(degree));
            nodes[0].setEntries(parent);
            ArrayList<String> entries1 = new ArrayList<String>();
            for (int i = 0; i < degree; i++)
                entries1.add(entries.get(i));
            nodes[1].setEntries(entries1);
            ArrayList<String> entries2 = new ArrayList<String>();
            for (int i = degree + 1; i < degree * 2 && i < entries.size(); i++)
                entries2.add(entries.get(i));
            nodes[2].setEntries(entries2);
        }

        return nodes;
    }

    private int delete(String key) {
        Entry entry = search(root, key);
        if (entry == null)
            return -1;
        Node nodeLeaf = searchNode(root, key);

        ArrayList<Node> nodeParent = new ArrayList<Node>();
        nodeParent.add(nodeLeaf.getParent());
        int sizeParent = 0;
        ArrayList<Node> currentNode = new ArrayList<Node>();
        currentNode.add(nodeLeaf);
        int sizeCurrent = 0;
        ArrayList<Node> nodeBrother = new ArrayList<Node>();
        nodeBrother.add(brother(nodeLeaf));
        int sizeBrother = 0;

        Node brother = brother(nodeLeaf);
        Node current = nodeLeaf;
        Node parent;

        while (current.getEntries().size() < degree
                && !current.isRoot()
                && brother.getEntries().size() < degree) {
            parent = current.getParent();
            brother = brother(parent);
            current = parent;
            nodeBrother.add(brother);
            sizeBrother++;
            currentNode.add(current);
            sizeCurrent++;
            nodeParent.add(current.getParent());
            sizeParent++;
        }

        current = currentNode.get(sizeCurrent);
        parent = nodeParent.get(sizeParent);
        brother = nodeBrother.get(sizeBrother);
        if (sizeCurrent >= 1) {
            if (!current.isRoot() && current.getEntries().size() < degree && brother.getEntries().size() >= degree) {
                if (parent.getChildren().indexOf(brother) > parent.getChildren().indexOf(current)) {
                    ArrayList<String> arrayList = brother.getEntries();
                    current.add((String) parent.getEntries().get(0));
                    parent.getEntries().remove(0);
                    parent.add(arrayList.get(0));
                    brother.getEntries().remove(0);
                    current.getChildren().add(brother.getChildren().get(0));
                    brother.getChildren().get(0).setParent(current);
                    brother.getChildren().remove(0);
                } else {
                    String m = (String) brother.getEntries().get(brother.getEntries().size() - 1);
                    current.add((String) parent.getEntries().get(parent.getChildren().indexOf(current) - 1));
                    parent.getEntries().remove(parent.getChildren().indexOf(current) - 1);
                    parent.add(m);
                    brother.getEntries().remove(m);

                    current.getChildren().add(0, brother.getChildren().get(brother.getChildren().size() - 1));
                    brother.getChildren().get(brother.getChildren().size() - 1).setParent(current);
                    brother.getChildren().remove(brother.getChildren().size() - 1);
                }
            }
        }
        sizeCurrent--;
        while (sizeCurrent >= 1) {//处理内部节点
            current = currentNode.get(sizeCurrent--);
            parent = current.getParent();
            brother = brother(current);
            int index = parent.getChildren().indexOf(current);
            Node node;
            Node[] nodes;
            if (index == 0) {
                nodes = mergeNode(current, brother);
                node = nodes[1];
                parent = nodes[0];
            } else {
                nodes = mergeNode(brother, current);
                node = nodes[1];
                parent = nodes[0];
            }
            if (parent.getEntries().size() == 0) {
                this.root = node;
                node.setRoot(true);
                node.setParent(null);
            }
        }

        int index = nodeLeaf.search(key);
        nodeLeaf.getEntries().remove(index);

        if (nodeLeaf.isRoot()) {
//            nodeLeaf.getEntries().remove(search(this.root, key));
            if (nodeLeaf.getEntries().size() == 0)
                this.root = null;
        } else {
            Node nodeChild = searchString(this.root, key);
            if (nodeLeaf.getEntries().size() < degree - 1) {
                brother = brother(nodeLeaf);
                if (brother.getEntries().size() < degree) {
                    parent = nodeLeaf.getParent();
                    Node node;
                    Node[] nodes;
                    if (parent.getChildren().indexOf(brother) > parent.getChildren().indexOf(nodeLeaf)) {
                        nodes = mergeNode(nodeLeaf, brother);
                        node = nodes[1];
                        fixUp(node, nodeLeaf, brother);
                        parent = nodes[0];
                    } else {
                        nodes = mergeNode(brother, nodeLeaf);
                        node = nodes[1];
                        fixUp(node, brother, nodeLeaf);
                        parent = nodes[0];
                    }
                    if (parent.getEntries().size() == 0) {
                        this.root = node;
                        node.setRoot(true);
                        node.setParent(null);
                    }
                    nodeChild = searchString(this.root, key);
                    if (nodeChild != null) {
                        nodeChild.getEntries().remove(key);
                        ArrayList<Entry> arrayList = node.getEntries();
                        nodeChild.add(arrayList.get(0).getKey());
                    }
                } else {
                    if (nodeLeaf.getParent().getChildren().indexOf(brother) > nodeLeaf.getParent().getChildren().indexOf(nodeLeaf)) {
                        ArrayList<Entry> arrayList = brother.getEntries();
                        Entry m = arrayList.get(0);
                        nodeLeaf.getParent().getEntries().remove(0);
                        nodeLeaf.getParent().add(arrayList.get(1).getKey());
                        brother.getEntries().remove(0);
                        nodeLeaf.add(m);
                        nodeChild = searchString(this.root, key);
                        if (nodeChild != null) {
                            nodeChild.getEntries().remove(key);
                            nodeChild.add(m.getKey());
                        }
                    } else {
                        Entry m = (Entry) brother.getEntries().get(brother.getEntries().size() - 1);
                        nodeLeaf.getParent().getEntries().remove(nodeLeaf.getParent().getChildren().indexOf(nodeLeaf) - 1);
                        nodeLeaf.getParent().add(m.getKey());
                        brother.getEntries().remove(m);
                        nodeLeaf.add(m);
                        nodeChild = searchString(this.root, key);
                        if (nodeChild != null) {
                            nodeChild.getEntries().remove(key);
                            nodeChild.add(m.getKey());
                        }
                    }
                }
            } else {
                nodeChild = searchString(this.root, key);
                if (nodeChild != null) {
                    nodeChild.getEntries().remove(key);
                    ArrayList<Entry> arrayList = nodeLeaf.getEntries();
                    nodeChild.add(arrayList.get(index).getKey());
                }
            }
        }
        size--;
        return 0;
    }

    private void fixUp(Node node, Node left, Node right) {
        if (node.isLeaf()) {
            node.setNext(right.getNext());
            if (right.getNext() != null) {
                right.getNext().setPrevious(node);
            }
            node.setPrevious(left.getPrevious());
            if (left.getPrevious() != null) {
                left.getPrevious().setNext(node);
            }

        }
    }

    private Node[] mergeNode(Node left, Node right) {
        Node[] nodes = new Node[2];//本身、父、子
        if (left.isLeaf())
            nodes[1] = new Node(true);
        else
            nodes[1] = new Node(false);
        int index = left.getParent().getChildren().indexOf(left);
        ArrayList arrayList1 = left.getEntries();
        ArrayList arrayList2 = right.getEntries();
        ArrayList<Node> arrayList3 = left.getChildren();
        ArrayList<Node> arrayList4 = right.getChildren();
        Node parent = left.getParent();
        if (nodes[1].isLeaf()) {
            for (Object anArrayList1 : arrayList1) {
                nodes[1].add((Entry) anArrayList1);
            }
            for (Object anArrayList2 : arrayList2) {
                nodes[1].add((Entry) anArrayList2);
            }
        } else {
            for (Object anArrayList1 : arrayList1) {
                nodes[1].add((String) anArrayList1);
            }
            for (Object anArrayList2 : arrayList2) {
                nodes[1].add((String) anArrayList2);
            }
            nodes[1].add((String) parent.getEntries().get(index));
        }
        if (arrayList3 != null) {
            for (Node anArrayList3 : arrayList3) {
                nodes[1].getChildren().add(anArrayList3);
                anArrayList3.setParent(nodes[1]);
            }
        }
        if (arrayList4 != null) {
            for (Node anArrayList4 : arrayList4) {
                nodes[1].getChildren().add(anArrayList4);
                anArrayList4.setParent(nodes[1]);
            }
        }

        parent.getEntries().remove(index);
        parent.getChildren().remove(left);
        parent.getChildren().remove(right);
        nodes[1].setParent(parent);
        parent.getChildren().add(index, nodes[1]);
        nodes[0] = parent;
        return nodes;
    }

    //得到前面的兄弟节点，如果是第一个，得到的是后面的
    private Node brother(Node x) {
        Node brother = null;
        if (!x.isRoot()) {
            ArrayList<Node> arrayList = x.getParent().getChildren();
            int index = arrayList.indexOf(x);
            if (index == 0) {
                brother = arrayList.get(1);
            } else {
                brother = arrayList.get(index - 1);
            }
        }
        return brother;
    }

    //在x为root的树上查找,返回Entry对象
    private Entry search(Node x, String key) {
        Node node = searchNode(x, key);
        if (node != null) {
            ArrayList<Entry> arrayList = node.getEntries();
            int index = node.search(key);
            if (index >= arrayList.size())
                return null;
            else
                return arrayList.get(index);
        } else
            return null;
    }

    private ArrayList<Entry> search(Node root, String start, String end) {
        if (start == null || "".equals(start)) {
            start = minimum(root).getKey();
        }
        if (end == null || "".equals(end)) {
            end = maximum(root).getKey();
        }
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        if (start.toLowerCase().compareTo(end.toLowerCase()) < 0) {
            Node startNode = searchNode(root, start);
            Node endNode = searchNode(root, end);
            int indexStart = startNode.search(start);
            int indexEnd = endNode.search(end);
            if (indexStart >= startNode.getEntries().size())
                indexStart = indexStart - startNode.getEntries().size();
            if (indexEnd >= endNode.getEntries().size())
                indexEnd = indexEnd - endNode.getEntries().size() - 1;
            if (startNode == endNode) {
                for (int i = indexStart; i <= indexEnd; i++) {
                    arrayList.add((Entry) startNode.getEntries().get(i));
                }
            } else {
                for (int i = indexStart; i < startNode.getEntries().size(); i++) {
                    arrayList.add((Entry) startNode.getEntries().get(i));
                }
                startNode = startNode.getNext();
                while (startNode != null && startNode != endNode) {
                    for (int j = 0; j < startNode.getEntries().size(); j++) {
                        arrayList.add((Entry) startNode.getEntries().get(j));
                    }
                    startNode = startNode.getNext();
                }
                for (int i = 0; i <= indexEnd; i++) {
                    arrayList.add((Entry) endNode.getEntries().get(i));
                }
            }
        } else if (start.toLowerCase().equals(end.toLowerCase())) {
            if (search(root, start) != null) {
                arrayList.add(search(root, start));
            }
        } else {
            arrayList = search(root, end, start);
        }
        return arrayList;
    }

    //搜索所在的Node(叶节点中)，返回存在的Node，或者如果不存在的话，返回可以插入的对应的Node
    private Node searchNode(Node x, String key) {
        if (x == null) {
            return x;
        }
        while (!x.isLeaf()) {
            ArrayList<String> arrayList = x.getEntries();
            int i = 0;
            for (; i < arrayList.size(); i++) {
                if (key.toLowerCase().compareTo(arrayList.get(i).toLowerCase()) < 0) {
                    x = x.getChildren().get(i);
                    break;
                } else if (key.toLowerCase().equals(arrayList.get(i).toLowerCase())) {
                    x = x.getChildren().get(i + 1);
                    break;
                }
            }
            if (i == arrayList.size())
                x = x.getChildren().get(i);
        }
        return x;
    }

    //在x为root的树上查找包含key的节点并返回
    private Node searchString(Node x, String key) {
        while (!x.isLeaf()) {
            ArrayList<String> arrayList = x.getEntries();
            int i = 0;

            for (; i < arrayList.size(); i++) {
                if (key.toLowerCase().compareTo(arrayList.get(i).toLowerCase()) < 0) {
                    x = x.getChildren().get(i);
                    break;
                }
                if (key.toLowerCase().equals(arrayList.get(i).toLowerCase())) {
                    return x;
                }
            }
            if (i == arrayList.size())
                x = x.getChildren().get(i);
        }
        if (x.isLeaf())
            return null;
        return x;
    }

    private Entry minimum(Node x) {
        while (!x.isLeaf()) {
            x = x.getChildren().get(0);
        }
        return (Entry) x.getEntries().get(0);
    }

    private Entry maximum(Node x) {
        while (!x.isLeaf()) {
            x = x.getChildren().get(x.getChildren().size() - 1);
        }
        return (Entry) x.getEntries().get(x.getEntries().size() - 1);
    }

    private void preorderTreeWalk(Node root) {
        if (this.root != null) {
            String[] s = info(root);
            System.out.println("level = " + s[0] + ", child = " + s[1] + ", " + s[2]);
            if (!root.isLeaf()) {
                for (int i = 0; i < root.getChildren().size(); i++) {
                    preorderTreeWalk(root.getChildren().get(i));
                }
            }
        }
    }

    private String[] info(Node root) {
        String[] s = new String[3];
        s[2] = " ";
        if (root.isLeaf()) {
            ArrayList<Entry> entries = root.getEntries();
            for (Entry entry : entries) {
                s[2] += "/" + entry.getKey() + ":" + entry.getValue();
            }
            s[2] += "/";
        } else {
            ArrayList<String> entries = root.getEntries();
            for (String entry : entries) {
                s[2] += "/" + entry;
            }
            s[2] += "/";
        }
        int level = 0;
        int child = 0;
        if (root != this.root) {
            Node y = root.getParent();
            child = y.getChildren().indexOf(root);
            while (y != null) {
                level++;
                y = y.getParent();
            }
        }
        s[0] = level + "";
        s[1] = child + "";
        return s;
    }

    public int getSize() {
        return size;
    }
}
