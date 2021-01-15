package LAB02;

import java.util.ArrayList;
import java.util.HashMap;

class StackOfOperator {
    private String[] operators;
    private int size = 16;
    private int top = -1;
    private static final HashMap<String, Integer> map;

    static {
        map = new HashMap<>();
        map.put("(", 0);
        map.put("<<", -1);
        map.put(">>", -1);
        map.put(">>>", -1);
        map.put("+", 1);
        map.put("-", 1);
        map.put("*", 2);
        map.put("/", 2);
        map.put("%", 2);
    }

    //无参的构造方法，构造长度为LENGTH的String数组
    StackOfOperator() {
        this(16);
    }

    //构造方法，构造长度为length的String数组
    StackOfOperator(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("栈的初始大小不能小于0");
        }
        size = length;
        operators = new String[size];
    }

    String doPush(String operator) {
        if (operator.equals(")")) {
            StringBuilder s = new StringBuilder();
            while (!peek().equals("(")) {
                s.append(pop()).append(" "); }
            pop();
            return s.toString(); }
        if (operator.equals("(")) {
            push(operator);
            return ""; }
        if (isEmpty()) {
            push(operator);
            return "";
        } else {
            int a = map.get(operator);
            int b = map.get(peek());
            String nextop;
            if (b == 0) {
                push(operator);
                return ""; }
            if (a > b) {
                push(operator);
                nextop = "";
            } else {
                nextop = pop();
                push(operator); }
            return nextop; } }

    //判断是否为空栈，为空时，返回true；
    private boolean isEmpty() {
        return top == -1;
    }

    private void push(String operator) {
        if (top >= size) {
            size *= 2;
            String[] temp = new String[size];
            System.arraycopy(operators, 0, temp, 0, operators.length);
            operators = temp;
        }
        operators[++top] = operator;
    }

    private String pop() {
        if (isEmpty())
            return "";
        else {
            return operators[top--];
        }
    }

    private String peek() {
        if (isEmpty())
            return "";
        else
            return operators[top];
    }

    ArrayList<String> clear() {
        ArrayList<String> all = new ArrayList<>();
        while (!isEmpty()) {
            all.add(pop());
        }
        return all;
    }
}
