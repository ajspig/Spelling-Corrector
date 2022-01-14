package spell;

public class Trie implements ITrie{
    private Node root;
    private int wordCount; // everytime we change the freq of a word count from 0 to >0 we should also increment this
    private int nodeCount; // everytime you add a node, increase this

    public Trie(){
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }

    @Override
    public void add(String word) {
        word.toLowerCase();

        INode currNode = root;
        for(int i =0; i < word.length(); ++i) {//loop through all the characters in the word
            char currChar = word.charAt(i);
            int index = currChar - 'a';
            if(currNode.getChildren()[index] == null){
                Node newNode = new Node();
                currNode.getChildren()[index]= newNode;
                currNode = newNode;
                // if its null we want to create a new node at that place, and then update currNode with that new node
                // we also want to increase nodeCount by one
                ++nodeCount;

            }else{
                currNode = currNode.getChildren()[index];
                // if there is something in that array
            }

        }
        if(currNode.getValue() == 0){
            ++wordCount;
        }
        currNode.incrementValue(); //increase frequency count for currNode
    }

    @Override
    public INode find(String word) {
        word.toLowerCase();
        INode currNode = root;
        for(int i =0; i < word.length(); ++i) {//loop through all the characters in the word
            char currChar = word.charAt(i);
            int index = currChar - 'a';
            if(currNode.getChildren()[index] == null) {
                return null;
                // that means we didnt find the word, so return null
            }else{
                currNode = currNode.getChildren()[index];
                // keep going
            }
        }
        // to make sure it's not part of word
        // we need to check if the currNode frequency is greater than 0, if it is, then we can return currNode
        // else its return null
        if(currNode.getValue() > 0){
            return currNode;
        }
        return null;
    }

    @Override
    public int getWordCount() {
        //just count the number of nodes that have a non-zero counter
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
    @Override
    public String toString(){
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        //traverse the tree
        //every node that has a non-zero counter add to output
        toString_Helper(root, curWord, output);
        return output.toString();
    }
    private void toString_Helper(INode n, StringBuilder curWord, StringBuilder output){
        //first check if the node has a non-zero counter
        //if it does append to output
        if(n.getValue() >0){
            output.append(curWord.toString());
            output.append("\n");
            //append nodes word to output
        }
        //recurs on nodes children
        for(int i =0; i < n.getChildren().length; ++i){
            //why INode?
            INode child = n.getChildren()[i];
            if(child != null){
                char childLetter = (char)('a'+i);
                curWord.append(childLetter);
                toString_Helper(child, curWord, output);
                //my toString_Helper wants a Node and i give it an INode

                //we need to remove the letter
                curWord.deleteCharAt(curWord.length()-1);
            }
        }

    }
    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()){
            //is o null?
            //do this and o have the same class?
            return false;
        }
        Trie t = (Trie) o;
        // before we send root and t into our equals_Helper function,
        // check if they have the same node and word count
        if((this.nodeCount != t.nodeCount) || (this.wordCount != t.wordCount)){
            return false;
        }
        //failing equal tree, but objects are unequal
        //do this and d have the same wordCount and nodeCount?
        return equals_Helper(this.root, t.root);
    }
    private boolean equals_Helper(Node n1, Node n2 ){
        if(n1.getValue() != n2.getValue()) {
            return false;
        }
        //checking if frequency counts for each node are the same
        //now check if the nodes both have non-null children in exactly the same indexes
        for(int i = 0; i < n1.getChildren().length; ++i) {
            INode child1 = n1.getChildren()[i];
            //running into a problem if both of the nodes are null
            if((child1 != null && n2.getChildren()[i] == null )|| (child1 == null && n2.getChildren()[i] != null)){
                return false;
            }
            if (child1 != null && n2.getChildren()[i] != null) {
                if (!equals_Helper((Node)child1, (Node)n2.getChildren()[i])) {
                    //if both n1 and n2 have a non-null child at the same spot
                    //recursivly call the equals helper on those nodes
                    //how do we fix the fact that it returns a bool
                    //can we just say returns true? walk through the functionality
                    return false;
                }
            }
        }
        return true;
        //recurse on the children and compare the child subtrees
    }

    @Override
    public int hashCode(){
        //by the node count
        // and word count of the Trie.
        int sumOfIndex = 1;
        for(int i = 0; i < root.getChildren().length; ++i){
            if(root.getChildren()[i] != null){
                sumOfIndex *=i;
            }
        }
        return sumOfIndex*wordCount*nodeCount;


        //take teh value of this trie and calculate an integer based off the value of teh tree
        //deterministic= same value each time
        //combine teh following values
        //1. word
        //2. node
        //3. index of each of the root node's non-null children

    }
}
