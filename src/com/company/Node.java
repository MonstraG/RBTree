package com.company;

class Node {
    char value = 0;
    boolean isRed = true;
    Node leftNode = null;
    Node rightNode = null;
    Node parent = null;

    String serialize() {
        StringBuilder nodeInfo = new StringBuilder();
        nodeInfo.append(this.value);
        if (this.isRed) {
            nodeInfo.append(" is red, ");
        }
        nodeInfo.append(" with children: ");
        if (this.leftNode != null) {
            nodeInfo.append(" left: ").append(this.leftNode.value);
        }
        if (this.rightNode != null) {
            nodeInfo.append(" right: ").append(this.rightNode.value);
        }
        return(nodeInfo.toString());
    }
}


