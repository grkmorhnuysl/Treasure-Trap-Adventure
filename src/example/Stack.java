package example;

import java.util.LinkedList;

public class Stack<T> {
    private LinkedList<T> stack;

    public Stack() {
        stack = new LinkedList<>();
    }

    public void push(T item) {
        stack.addFirst(item);
    }

    public T pop() {
        return stack.removeFirst();
    }

    public T peek() {
        return stack.getFirst();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void display() {
        System.out.println("Stack: " + stack);
    }
}
