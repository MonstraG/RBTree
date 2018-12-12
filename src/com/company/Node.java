package com.company;

/**
 * Класс элемента дерева. Содержит значение, цвет и связи с другими элементами.
 */
class Node {
    char value = 0;
    boolean isRed = true;
    Node parent = null;
    Node leftNode = null;
    Node rightNode = null;


    /**
     * Метод-сериализатор для класса Node
     * @return {String} сериализированная строка представляющая нод.
     */
    public String toString() {
        StringBuilder nodeInfo = new StringBuilder();
        nodeInfo.append("Node { value: ").append(this.value).append(", isRed: ").append(this.isRed);
        if (this.parent != null) {
            nodeInfo.append(", parent: ").append(this.parent.value);
        }
        if (this.leftNode != null) {
            nodeInfo.append(", leftNode: ").append(this.leftNode.value);
        }
        if (this.rightNode != null) {
            nodeInfo.append(", rightNode: ").append(this.rightNode.value);
        }
        nodeInfo.append(" }");
        return(nodeInfo.toString());
    }
}


