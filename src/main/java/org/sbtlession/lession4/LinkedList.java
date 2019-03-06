package org.sbtlession.lession4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> {

    Node<E> first;
    Node<E> last;
    Integer size;


    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Элемент связанного списка
     * Хранит ссылку на следующий и предыдущий элемент
     */
    protected class Node<E> {

        Node<E> next;
        Node<E> prev;
        E data;

        /**
         * Конструктор с 1 параметром.
         * Удобно использовать для создание первого элеменат
         *
         * @param data
         */
        protected Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        /**
         * Конструктор элемента с 2 парамметрами
         * Удобно использовать, если необходимо создать элемент для вставки в конец списка
         *
         * @param data Полезная информация
         * @param prev Ссылка на предыдущий элемент
         */
        protected Node(E data, Node<E> prev) {
            this.data = data;
            this.prev = prev;
            this.next = null;
        }

        /**
         * Конструктор элемента с 3 парамметрами.
         * Удобно использовать, если необходимо вставить элемент в центр списка
         *
         * @param data Полезная информация
         * @param prev Ссылка на предыдущий элемент от текущего
         * @param next Ссылка на следующий элемент от текущего
         */
        protected Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        /**
         * Устанавливает ссылку на следующий элемент. А также возвращает ее же.
         *
         * @param next Ссылка на следующий элемент от текущего
         * @return Возвращает ссылку на следующий элемент после успешной установки значения
         */
        protected Node<E> setNext(Node<E> next) {
            this.next = next;
            return next;
        }

        /**
         * Устанавливает ссылку на предыдущий элемент. А также возвращает ее же.
         *
         * @param prev Ссылка на предыдущий элемент от текущего
         * @return Возвращает ссылку на предыдущий элемент после успешной установки значения
         */
        protected Node<E> setPrev(Node<E> prev) {
            this.prev = prev;
            return prev;
        }

        /**
         * Метод возвразает ссылку на следующий элемент от текущего
         *
         * @return Возвращает ссылку на следующий элемент от текущего или null  в случае если следующего элемента нет
         */
        protected Node<E> getNext() {
            return next;
        }

        /**
         * Метод возвразает ссылку на пердыдущий элемент от текущего
         *
         * @return Возвращает ссылку на предыдущий элемент от текущего или null  в случае если предыдущего элемента нет
         */

        protected Node<E> getPrev() {
            return prev;
        }

        protected E getData() {
            return data;
        }

    }

    public void add(int index, E element) throws IndexOutOfBoundsException {
        if (index < size && index >= 0) {
            Node<E> newNode = new Node<E>(element);
            Node<E> currentNode = first;
            if (index == 0) {
                newNode.setNext(first);
                first.setPrev(newNode);
                first = newNode;
            } else {
                for (int i = 0; i < index; i++) {
                    currentNode = currentNode.getNext();
                }
                newNode.setPrev(currentNode.getPrev());
                newNode.setNext(currentNode);
                currentNode.setPrev(newNode);
                newNode.getPrev().setNext(newNode);
            }

            size = size + 1;

        } else
            throw new IndexOutOfBoundsException();

    }

    /**
     * Медот вставки в конец списка
     *
     * @param e Data
     * @return
     */
    public boolean add(E e) {
        if (last == null) {
            first = new Node<E>(e);
            last = first;
            size = 1;
            return true;
        } else {
            Node<E> node = new Node<E>(e, last);
            last.setNext(node);
            last = node;
            size = size + 1;
            return true;
        }
    }

    /**
     * Удаление элемента с номером Index из списка
     * Нумерация списка ведется с 0 до size
     *
     * @param index Номер удаляемого элемента
     * @return Возвращает удаленный элемент
     * @throws IndexOutOfBoundsException Выбасывает ошибку, если index вне допустимого диапазона
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < size && index >= 0) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            if (node.getPrev() != null)
                node.getPrev().setNext(node.getNext());// В поле next предыдущему элементу кладем ссылку на следующий от текущего
            else first = node.getNext();

            if (node.getNext() != null)
                node.getNext().setPrev(node.getPrev());// тоже самое, но наоборот для следующего элемента
            else last = node.getPrev();

            node.setPrev(null); //обнуляем у удаляемого элемента ссылки на следующий и предыдущий.
            node.setNext(null);
            size = size - 1;
            return node.getData();
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Метод получения Data элемента с номером index
     *
     * @param index Номер искомого элемента
     * @return Возвращает элемент с номером index
     * @throws IndexOutOfBoundsException Выбрасывает исключение, если index вне допустимого диапазона
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < size && index >= 0) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            return node.getData();
        }
        throw new IndexOutOfBoundsException();
    }

    class LinkIterator<E> implements Iterator<E> {
        Node<E> node;

        public LinkIterator(Node<E> node) {
            this.node = node;
        }

        public boolean hasNext() {
            if (node != null) return true;
            else return false;
        }

        public E next() throws NoSuchElementException {
            Node<E> tempNode = node;
            if (tempNode == null) throw new NoSuchElementException();
            node = tempNode.getNext();
            return tempNode.getData();
        }
    }

    public Iterator<E> iterator() {
        return new LinkIterator<E>(first);
    }

    /**
     * @return Возвращает количество элементов в списке
     */
    public Integer getSize() {
        return size;
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        System.out.println("size:" + list.getSize());
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
        System.out.println("Тест на вставку в середину \n");


        list.add(1, 10);
        System.out.println("list size:" + list.getSize());
        System.out.println("element position 1:" + list.get(1));
        iterator = list.iterator();
        System.out.println("Все элементы:");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
        System.out.println("\nТест на вставку в начало\n");

        list.add(0, 99);
        System.out.println("list size:" + list.getSize());
        System.out.println("element position 0:" + list.get(0));
        iterator = list.iterator();
        System.out.println("Все элементы:");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
        System.out.println("\nТест на вставку в конец\n");

        list.add(4, 88);
        System.out.println("list size:" + list.getSize());
        System.out.println("element position 4:" + list.get(4));
        iterator = list.iterator();
        System.out.println("Все элементы:");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
        System.out.println("\nТест на удаление первого элемента\n");

        list.remove(0);
        System.out.println("list size:" + list.getSize());
        System.out.println("element position 0:" + list.get(0));
        iterator = list.iterator();
        System.out.println("Все элементы:");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        System.out.println("\nТест на удаление центрального элемента\n");

        list.remove(2);
        System.out.println("list size:" + list.getSize());
        System.out.println("element position 2:" + list.get(2));
        iterator = list.iterator();
        System.out.println("Все элементы:");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        System.out.println("\nТест на удаление последнего элемента\n");

        list.remove(3);
        System.out.println("list size:" + list.getSize());
        System.out.println("element position 2:" + list.get(2));
        iterator = list.iterator();
        System.out.println("Все элементы:");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }


    }
}
