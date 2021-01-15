package LAB02;

import java.util.ArrayList;

class StackOfNumber<E> {
    private ArrayList<E> numbers;
    private int top = -1;

    //无参的构造方法，构造长度为LENGTH的String数组
    StackOfNumber() {
        numbers = new ArrayList<>();
    }

    //判断是否为空栈，为空时，返回true；
    private boolean isEmpty() {
        return top == -1;
    }

    void push(E number) {
        top++;
        numbers.add(number);

    }

    E pop() {
        if (isEmpty())
            return (E) new Object();
        else {
            E num = numbers.get(top);
            numbers.remove(top--);
            return num;
        }
    }

    private E peek() {
        if (isEmpty())
            return (E) new Object();
        else
            return numbers.get(top);
    }

    ArrayList<E> clear() {
        ArrayList<E> all = new ArrayList<>();
        while (!isEmpty()) {
            all.add(pop());
        }
        return all;
    }
}
