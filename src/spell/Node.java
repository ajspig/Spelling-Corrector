package spell;

public class Node implements INode{
    private int freq;
    private Node[] children;
    Node(){
        children = new Node[26];
    }
    @Override
    public int getValue() {
        return freq;
    }

    @Override
    public void incrementValue() {
        ++freq;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
