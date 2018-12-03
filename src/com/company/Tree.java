
package com.company;

import java.util.ArrayList;

public class Tree {
    private static Node treeRoot = null;
    private static ArrayList<Node> nodeIndex = new ArrayList<>();

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

    static void  addNode(char value) throws Exception {
        Node newNode = new Node();
        newNode.value = value;
        if (treeRoot == null) { // дерево пустое
            treeRoot = newNode;
            nodeIndex.add(newNode);
        } else { // дерево не пустое
            if(find(value) != null) throw new Exception("Такой нод уже добавлен");
            Node compareTo = treeRoot;
            while (true) {
                int compareRes = Character.compare(compareTo.value, value);
                if (compareRes < 0) {
                    if (compareTo.leftNode == null) {
                        newNode.parent = compareTo;
                        compareTo.leftNode = newNode;
                        nodeIndex.add(newNode);
                        break;
                    }
                    compareTo = compareTo.leftNode;
                }
                if (compareRes > 0) {
                    if (compareTo.rightNode == null) {
                        newNode.parent = compareTo;
                        compareTo.rightNode = newNode;
                        nodeIndex.add(newNode);
                        break;
                    }
                    compareTo = compareTo.rightNode;
                }
            }// end while
            rebalance(newNode);
        }
    }

    static void removeNode(char value) {
        Node element = find(value);
        if (element == null) return; // такого нет
        if (element.rightNode != null && element.leftNode != null) { // есть оба сына
            //находим наименьший справа
            Node next = element.rightNode;
            while(next.leftNode != null)
                next = next.leftNode;

            //переназначаем все
            next.parent.leftNode = null;
            next.parent = element.parent;
            next.rightNode = element.rightNode;
            next.leftNode = element.leftNode;
            if (element != treeRoot) {
                if (element.parent.leftNode == element)
                    element.parent.leftNode = next;
                if (element.parent.rightNode == element)
                    element.parent.rightNode = next;
            } else {
                treeRoot = next;
            }
            rebalance(next);
            return;
        }


        // TODO: fix other removals below this line.
        if (element.leftNode != null) { // левый деть есть, правого нет
            Node child = element.leftNode;
            child.isRed = element.isRed;
            element = child;
            element.leftNode = null;
            rebalance(element);
            return;
        }
        if (element.rightNode != null) { // правый есть, другого нет
            Node child = element.rightNode;
            child.isRed = element.isRed;
            element = child;
            element.rightNode = null;
            rebalance(element); 
            return;
        }
        // оба null
        if (element.parent.rightNode == element)
            element.parent.rightNode = null;
        if (element.parent.leftNode == element)
            element.parent.leftNode = null;
    }

    private static void rebalance(Node currentNode) {
        while (currentNode.parent.isRed) {
            //когда текущий элемент находится в левом поддереве
            if (currentNode.parent == currentNode.parent.parent.leftNode) {
                Node uncle = currentNode.parent.parent.rightNode;
                if (uncle.isRed) {
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
                    currentNode.parent.parent.isRed = true;
                    rotateRight(currentNode.parent.parent);
                }
            } //текущий элемент в правом поддереве
            else {
                Node uncle = currentNode.parent.parent.leftNode;
                if (uncle.isRed) {
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
                    currentNode.parent.parent.isRed = true;
                    rotateRight(currentNode.parent.parent);
                }
            }
        }
        treeRoot.isRed = false;
    }

    static void addAlphabet() throws Exception {
        for(char i = 'a'; i <= 'z'; i++)
            addNode(i);
    }

    static void removeAll(){
        treeRoot = null;
        nodeIndex = new ArrayList<>();
    }

    private static void rotateLeft(Node x) {
        Node y = x.rightNode;
        x.rightNode = y.leftNode;
        if (y.leftNode != null)
            y.leftNode.parent = x;
        y.parent = x.parent;
        if (x == x.parent.leftNode) {
            x.parent.leftNode = y;
        }
        else {
            x.parent.rightNode = y;
        }
        y.leftNode = x;
        x.parent = y;
    }
    private static void rotateRight(Node x){
        Node y = x.leftNode;
        x.leftNode = y.rightNode;
        if (y.rightNode != null)
            y.rightNode.parent = x;
        y.parent = x.parent;
        if (x == x.parent.leftNode) {
            x.parent.leftNode = y;
        }
        else {
            x.parent.rightNode = y;
        }
        y.rightNode = x;
        x.parent = y;
    }

    static String serialize() {
        StringBuilder result = new StringBuilder();
        for (Node node : nodeIndex){
            result.append(node.value);
                    if (node.leftNode != null)
                        result.append(" ").append(node.leftNode.value);
                    if (node.rightNode != null)
                        result.append(" ").append(node.rightNode.value);
                    result.append("\n");
        }
        if (result.toString().equals("")) return "Элементов нет";
        return result.toString();
    }

}
