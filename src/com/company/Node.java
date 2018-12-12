package com.company;

/**
 * Класс элемента дерева. Содержит значение, цвет и связи с другими элементами.
 */
class Node {
    /**
     * Значение нода (буква латинского алфавита в нижнем регистре)
     */
    char value = 0;

    /**
     * Является ли красным этот нод
     */
    boolean isRed = true;

    /**
     * Ссылка на отца нода
     */
    Node parent = null;

    /**
     * Ссылка на левого ребенка нода
     */
    Node leftNode = null;

    /**
     * Ссылка на правого ребенка нода
     */
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


