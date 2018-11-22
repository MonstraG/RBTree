package com.company;

public class Tree {
    private static Node treeRoot = null;

    static Node find(char value){
        Node compareTo = treeRoot;
        int compareRes = 2;
        while (compareRes != 0) {
            if (compareTo.value == 0) {
                return null;
            }
            compareRes = Character.compare(compareTo.value, value);
            if (compareRes < 0) {
                if (compareTo.leftNode == null) {
                    return null;
                }
                compareTo = compareTo.leftNode;
            }
            if (compareRes > 0) {
                if (compareTo.rightNode == null) {
                    return null;
                }
                compareTo = compareTo.rightNode;
            }
        }
        if (compareTo == treeRoot) {
            return null;
        }
        return compareTo;
    }

    static void  addNode(char value) throws Exception {
        Node newNode = new Node();
        newNode.value = value;
        if (treeRoot == null) { // дерево пустое
            treeRoot = newNode;
        } else {
            if(find(value) != null) {
                throw new Exception("Такой нод уже добавлен");
            }

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

    static void removeNode(char value){
        Node element = find(value);
        if (element.rightNode == null && element.leftNode == null) {
            if (element.parent.rightNode == element)
                element.parent.rightNode = null;
        }
        if (element.leftNode!=null && element.rightNode == null) {
            Node child = element.leftNode;
            child.isRed = element.isRed;
            element = child;
            element.leftNode = null;
        }
        if (element.leftNode==null && element.rightNode != null) {
            Node child = element.rightNode;
            child.isRed = element.isRed;
            element = child;
            element.rightNode = null;
        }
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
                    /* 2 случай: элемент красный, в левом поддереве справа, родитель красный, дядя черный
                        переходим к 3 случаю путем поворота поддерева, в котором находится элемент
                     */
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
        {
            addNode(i);
        }
    }

    static void removeAll(){

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
}
