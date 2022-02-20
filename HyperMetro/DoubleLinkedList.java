package metro;

import java.util.NoSuchElementException;

class DoubleLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoubleLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    public Node<E> getHead() {
        return head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> curr = head;
        while (curr.getNext() != null) {
            sb.append(curr.getValue()).append(" ");
            curr = curr.getNext();
        }
        sb.append(curr.getValue());
        return sb.toString();
    }

    public void addFirst(E e) {
        Node<E> toAdd = new Node<>(head, null, e);

        if (head != null) {
            head.prev = toAdd;
        }

        head = toAdd;

        if (tail == null) {
            tail = toAdd;
        }

        size++;
    }

    public void addLast(E e) {
        if (head == null) {
            addFirst(e);
            return;
        } else {
            Node<E> toAdd = new Node<>(null, tail, e);
            tail.next = toAdd;
            tail = toAdd;
        }

        size++;
    }

    public void add(E e, Node<E> curr) {
        if (curr == null) {
            throw new NoSuchElementException();
        }

        Node<E> toAdd = new Node<>(curr, null, e);

        if (curr.prev == null) {
            addFirst(e);
            return;
        } else {
            toAdd.prev = curr.prev;
            curr.prev.next = toAdd;
            curr.prev = toAdd;
        }

        size++;
    }

    public void removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        size--;
    }

    public void removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
    }

    public void remove(Node<E> curr) {
        if (curr == null) {
            throw new NoSuchElementException();
        }

        if (curr.prev == null) {
            removeFirst();
            return;
        }

        if (curr.next == null) {
            removeLast();
            return;
        }

        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;
    }

    public Node<E> find(E e) {
        if (head == null) {
            return null;
        }

        Node<E> curr = head;

        if (head.getValue().equals(e)) {
            return head;
        }

        while (curr.getNext() != null) {
            curr = curr.getNext();
            if (curr.getValue().equals(e)) {
                return curr;
            }
        }

        return null;
    }

    static class Node<E> {

        private Node<E> next;
        private Node<E> prev;
        private final E value;

        public Node(Node<E> next, Node<E> prev, E value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public E getValue() {
            return value;
        }

    }

}
