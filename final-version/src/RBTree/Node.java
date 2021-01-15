package RBTree;

/**
 * @author SongYijing 17302010079
 * @date 2018/11/6 10:22
 */

public class Node {
    /**
     * color：红色：0，黑色：1;为便于插入，默认值为0；
     * key：英文
     * value：解释，中文
     * parent：父节点
     * leftChild：左边子节点
     * rightChild：右边子节点
     */
    private int color = 0;
    private String key;
    private String value;
    private Node parent;
    private Node leftChild;
    private Node rightChild;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }

    Node(int color, String key, String value) {
        this.color = color;
        this.key = key;
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    void setColor(int color) {
        this.color = color;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

    Node getParent() {
        return parent;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    Node getRightChild() {
        return rightChild;
    }

    void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}
