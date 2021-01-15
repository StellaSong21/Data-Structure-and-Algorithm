package LAB05;

public class Node< T extends Comparable> {
    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private T key;

    public Node() {
    }

    public Node(T key) {
        this.key = key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public T getKey() {
        return key;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }
}
