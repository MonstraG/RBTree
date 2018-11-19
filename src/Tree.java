public class Tree {
    private Node treeRoot = null;

    int find(char value){

        return 0;
    }

    int addNode(char value) throws Exception {
        if(find(value) > 0) {
            throw new Exception("Такой нод уже добавлен");
        }

        Node newNode = new Node();
        newNode.value = value;
        int compareCount = 0;

        if (treeRoot == null) { // дерево пустое
            treeRoot = newNode;
        } else { // дерево не пустое
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
        rebalance();
        return compareCount;
    }

    int removeNode(char value){
        return 0;
    }

    void rebalance(){}
}
