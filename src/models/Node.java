package models;

public class Node {
    private int parentNode;
    public int location;
    private int costFromStart;

    public Node(int location) {
        int parentNode = -1;
        costFromStart = 0;
        this.location = location;
    }

    public Node(int location, int parentNode, int costFromStart) {
        this.parentNode = parentNode;
        this.location = location;
        this.costFromStart = costFromStart;
    }

    public int getLocation() {
        return location;
    }

    public int getParentNode() {
        return parentNode;
    }

    public boolean equals(Node node) {
        if (location == node.getLocation())
            return true;
        return false;
    }

    public int getCostFromStart() {
        return costFromStart;
    }

    public void changeParentNode(int parentNode, int parentCost) {
        costFromStart = parentCost + 1;
        this.parentNode = parentNode;
    }

}