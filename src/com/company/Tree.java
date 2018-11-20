package com.company;

public class Tree {
    private static Node treeRoot = null;

    static int find(char value){
        Node compareTo = treeRoot;
        int compareCount = 0;
        int compareRes = 2;
        while (compareRes != 0) {
            if (compareTo.value == 0) {
                return -compareCount;
            }
            compareRes = Character.compare(compareTo.value, value);
            compareCount++;
            if (compareRes == -1) {
                compareTo = compareTo.leftNode;
            }
            if (compareRes == 1) {
                compareTo = compareTo.rightNode;
            }
        }
        return compareCount;
    }

    static int addNode(char value) throws Exception {
        Node newNode = new Node();
        newNode.value = value;
        int compareCount = 0;

        if (treeRoot == null) { // дерево пустое
            treeRoot = newNode;
        } else {
            if(find(value) > 0) {
                throw new Exception("Такой нод уже добавлен");
            }

            Node compareTo = treeRoot;
            while (true) {
                int compareRes = Character.compare(compareTo.value, value);
                compareCount++;
                if (compareTo.value == 0) { // если уже нашли куда вставлять
                    if (compareRes == -1) {
                        compareTo.leftNode = newNode;
                        break;
                    }
                    if (compareRes == 1) {
                        compareTo.rightNode = newNode;
                        break;
                    }
                }
                if (compareRes == -1) {
                    compareTo = compareTo.leftNode;
                }
                if (compareRes == 1) {
                    compareTo = compareTo.rightNode;
                }
            }
        }
        rebalance(newNode);
        return compareCount;
    }

    static int removeNode(char value){
        return 0;
    }

    static void rebalance(Node currentNode){
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
                    if (currentNode == currentNode.parent.rightNode) {
                        currentNode = currentNode.parent;
                        rotateLeft(currentNode);
                    }
                    // 3 случай:
                    currentNode.parent.isRed = false;
                    currentNode.parent.parent.isRed = true;
                    rotateRight(currentNode.parent.parent);
                }
            } //текущий элемент в правом поддереве
            else {
                //допишу завтра
            }
        }
    }

    static void addAlphabet() {}

    static void removeAll(){}

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
}