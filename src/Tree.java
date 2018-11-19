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

    static int removeNode(char value){
        return 0;
    }

    static void rebalance(){}

    static void addAlphabet() {}

    static void removeAll(){}
}
