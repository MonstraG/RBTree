package com.company;

import java.util.ArrayList;

/**
 * Класс дерева, реализующий все методы работы с деревом.
 */
class Tree {
    private static Node treeRoot = null;
    private static ArrayList<String> temp = new ArrayList<>();

    /**
     * Находит обьект Node по значению
     * @param value {char} - значение
     * @return {Node} - найденный нод
     */
    static Node find(char value){
        if (value == treeRoot.value) return treeRoot;
        Node compareTo = treeRoot;
        int compareRes = 1;
        while (compareRes != 0) {
            compareRes = Character.compare(compareTo.value, value);
            if (compareRes < 0) {
                if (compareTo.leftNode == null) return null;
                compareTo = compareTo.leftNode;
            }
            if (compareRes > 0) {
                if (compareTo.rightNode == null) return null;
                compareTo = compareTo.rightNode;
            }
        }
        return compareTo;
    }

    /**
     * Добавляет новый нод
     * @param value {char} - значение нового нода
     * @throws Exception - поднимает Exception если такой нод уже есть
     */
    static void addNode(char value) throws Exception {
        Node newNode = new Node();
        newNode.value = value;
        if (treeRoot == null) { // дерево пустое
            newNode.isRed = false;
            treeRoot = newNode;
        } else { // дерево не пустое
            if (find(value) != null) throw new Exception("Такой нод уже добавлен");

            Node compareTo = treeRoot;
            while (true) {
                int compareRes = Character.compare(compareTo.value, value);
                if (compareRes < 0) {
                    if (compareTo.leftNode == null) {
                        newNode.parent = compareTo;
                        compareTo.leftNode = newNode;
                        break;
                    }
                    compareTo = compareTo.leftNode;
                }
                if (compareRes > 0) {
                    if (compareTo.rightNode == null) {
                        newNode.parent = compareTo;
                        compareTo.rightNode = newNode;
                        break;
                    }
                    compareTo = compareTo.rightNode;
                }
            }// end while
            rebalance(newNode);
        }
    }

    /**
     * Удаляет нод по значению
     * @param value {char} - значение
     */
    static void removeNode(char value) {
        Node element = find(value);
        if (element == null) return; // такого нет
        if (element.rightNode != null && element.leftNode != null) { // есть оба сына
            //находим наименьший справа
            Node next = element.rightNode;
            while(next.leftNode != null) {
                next = next.leftNode;
            }

            if (next == element.rightNode) {
                element.rightNode = null;
            }
            else {
                next.parent.leftNode = null;
            }
            next.parent = null;
            element.value = next.value;
            rebalance(element);
            return;
        }
        if (element.leftNode != null) { // левый деть есть, правого нет
            if (element == treeRoot) {
                treeRoot = element.leftNode;
                treeRoot.parent = null;
            }
            if (element.parent.leftNode == element) {
                element.parent.leftNode = element.leftNode;
            }
            element.leftNode.parent = element.parent;
            return;
        }
        if (element.rightNode != null) { // правый есть, другого нет
            if (element == treeRoot) {
                treeRoot = element.rightNode;
                treeRoot.parent = null;
            }
            if (element.parent.rightNode == element) {
                element.parent.rightNode = element.rightNode;
            }
            element.rightNode.parent = element.parent;
            return;
        }
        // оба null и это корень
        if (element == treeRoot) {
            treeRoot = null;
            return;
        }

        //оба null и это не корнеь
        if (element.parent.rightNode == element)
            element.parent.rightNode = null;
        if (element.parent.leftNode == element)
            element.parent.leftNode = null;
    }

    /**
     * Основной метод перебалансировки дерева для этого нода
     * @param currentNode {Node} - нод, из которого запускать перебалансировку
     */
    private static void rebalance(Node currentNode) {
        while (currentNode.parent != null && currentNode.parent.isRed) {
            //когда текущий элемент находится в левом поддереве
            if (currentNode.parent == currentNode.parent.parent.leftNode) {
                Node uncle = currentNode.parent.parent.rightNode;
                if (uncle != null && uncle.isRed) {
                    // 1 случай: элемент красный, родитель и дядя - красные
                    currentNode.parent.isRed = false;
                    uncle.isRed = false;
                    currentNode.parent.parent.isRed = true;
                    currentNode = currentNode.parent.parent;
                }
                else {
                    /* 2 случай: элемент красный, в левом поддереве справа, родитель красный, дядя черный
                        переходим к 3 случаю путем поворота поддерева, в котором находится элемент */
                    if (currentNode == currentNode.parent.rightNode) {
                        currentNode = currentNode.parent;
                        rotateLeft(currentNode);
                    }
                    // 3 случай: эелемент красный, в левом поддереве слева, родитель красный, дядя черный
                    currentNode.parent.isRed = false;
                    if (currentNode.parent.parent != null) {
                        currentNode.parent.parent.isRed = true;
                        rotateRight(currentNode.parent.parent);
                    }
                }
            } //текущий элемент в правом поддереве
            else {
                Node uncle = currentNode.parent.parent.leftNode;
                if (uncle != null && uncle.isRed) {
                    //4 случай: элемент в правом поддереве, родитель и дядя - красные
                    currentNode.parent.isRed = false;
                    uncle.isRed = false;
                    currentNode.parent.parent.isRed = true;
                    currentNode = currentNode.parent.parent;
                }
                else {
                    //5 случай: элемент в правом поддереве справа, родитель красный, дядя черный

                    if (currentNode == currentNode.parent.rightNode) {
                        currentNode = currentNode.parent;
                        rotateLeft(currentNode);
                    }
                    //6 случай: элемент красный, в правом поддереве слева, родитель красный, дядя черный
                    currentNode.parent.isRed = false;
                    if (currentNode.parent.parent != null) {
                        currentNode.parent.parent.isRed = true;
                        rotateRight(currentNode.parent.parent);
                    }
                }
            }
        }
        treeRoot.isRed = false;
    }

    /**
     * Добавляет сразу весь алфавит a-z.
     * @throws Exception - поднимает Exception если какой-то элемент алфавита уже добавлен
     */
    static void addAlphabet() throws Exception {
        for(char i = 'a'; i <= 'z'; i++) addNode(i);
    }

    /**
     * Отчищает дерево
     */
    static void removeAll() { treeRoot = null; }

    /**
     * Вспомогательный метод перебалансировки, производящий левый поворот из данного нода
     * @param x {Node} - нод из которого производить поворот
     */
    private static void rotateLeft(Node x) {
        Node y = x.rightNode;
        if (treeRoot == x) {
            treeRoot = y;
        }
        if (x.parent != null) {
            if (x.parent.leftNode == x) {
                x.parent.leftNode = y;
            }
            if (x.parent.rightNode == x) {
                x.parent.rightNode = y;
            }
        }
        y.parent = x.parent;
        x.parent = y;
        x.rightNode = y.leftNode;
        if (x.rightNode != null) x.rightNode.parent = x;
        y.leftNode = x;

    }

    /**
     * Вспомогательный метод перебалансировки, производящий правый поворот из данного нода
     * @param x {Node} - нод из которого производить поворот
     */
    private static void rotateRight(Node x){
        Node y = x.leftNode;
        if (treeRoot == x) {
            treeRoot = y;
        }
        if (x.parent != null) {
            if (x.parent.leftNode == x) {
                x.parent.leftNode = y;
            }
            if (x.parent.rightNode == x) {
                x.parent.rightNode = y;
            }
        }
        y.parent = x.parent;
        x.parent = y;
        x.leftNode = y.rightNode;
        if (x.leftNode != null) x.leftNode.parent = x;
        y.rightNode = x;
    }

    /**
     * Сериализует все дерево.
     * @return {String} - дерево в виде строки
     */
    static String serialize() {
        if (treeRoot == null) return "Элементов нет";
        StringBuilder result = new StringBuilder();
        walkTree(treeRoot);
        for (String item : temp)
            result.append(item).append(" \n");
        temp.clear();
        return result.toString();
    }

    /**
     * Рекурсивный вспомогательный метод, спускающийся из нода до листьев дерева, неоходимый для сериализации
     * @param start {Node} - нод из которого начать спуск.
     */
    private static void walkTree(Node start) {
        if (start.leftNode != null) {
            walkTree(start.leftNode);
        }
        if (start.rightNode != null) {
            walkTree(start.rightNode);
        }
        temp.add(start.toString());
    }
}
