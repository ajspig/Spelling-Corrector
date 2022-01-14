package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector{
    private Trie trie;
    ArrayList<String> edit_One_Distances;
    public SpellCorrector(){
        trie = new Trie();
        edit_One_Distances = new ArrayList<>();
    }
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //reads in the file and then populates the trie
        //that way suggestSimilarWord can access it
        File newFile = new File(dictionaryFileName);
        Scanner sc = new Scanner(newFile);
        //sc.useDelimiter("(\\s+)+");
        //do I need a try catch?
        while(sc.hasNext()) {
            String word = sc.next(); //read in the file one word at a time
            //pass each word into our addTrie function
            trie.add(word);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        if(trie.find(inputWord) != null){ //this means the word is in our tree so return the input word as the suggestion
            return inputWord;
        }
        //calculating edit 1 distances
//        ArrayList<String> edit_One_Distances = new ArrayList<>();
        edit_One_Distances.addAll(DeletionDistance(inputWord));
        edit_One_Distances.addAll(TransposeDistance(inputWord));
        edit_One_Distances.addAll(AlterationDistance(inputWord));
        edit_One_Distances.addAll(IterationDistance(inputWord));

        //check if dictionary/trie contains any of the words
        //and check if edit distance is the closest
        int highestFreq = 0;
        String associatedString = null;
        for(int i =0; i < edit_One_Distances.size(); i++){
            String currWord = edit_One_Distances.get(i);
            if(trie.find(currWord) != null){
                int currFreq = trie.find(currWord).getValue();
                if(currFreq >highestFreq){
                    //if this word occurs more often update highestFreq and associateString
                    //2. highest frequency
                    highestFreq = currFreq;
                    associatedString = currWord;
                }else if( highestFreq !=0 && currFreq == highestFreq){
                    //if they are the same frequency
                    if(associatedString.compareTo(currWord) >0){
                        //3. first alphabetically
                        associatedString = currWord;
                    }
                }
            }
        }
        //if our associatedString is still null
        //this menas we need to call editDistancetwo
        if(associatedString == null){
            associatedString = editTwo();
        }
        //reset
        edit_One_Distances.clear();
        return associatedString;

    }

    public String editTwo(){
        //create an arrayList of second edit Distances
        ArrayList<String> edit_Two_Distances = new ArrayList<>();
        //loop through edit_two and run the for distance programs on each
        for(int i =0; i < edit_One_Distances.size(); ++i){
            String currString = edit_One_Distances.get(i);
            edit_Two_Distances.addAll(DeletionDistance(currString));
            edit_Two_Distances.addAll(TransposeDistance(currString));
            edit_Two_Distances.addAll(AlterationDistance(currString));
            edit_Two_Distances.addAll(IterationDistance(currString));
        }
        //now our edit_two_distances should be packed
        //check if dictionary/trie contains any of the words
        //and check if edit distance is the closest
        int highestFreq = 0;
        String associatedString = null;
        for(int i =0; i < edit_Two_Distances.size(); i++){
            String currWord = edit_Two_Distances.get(i);
            if(trie.find(currWord) != null){
                int currFreq = trie.find(currWord).getValue();
                if(currFreq >highestFreq){
                    //if this word occurs more often update highestFreq and associateString
                    //2. highest frequency
                    highestFreq = currFreq;
                    associatedString = currWord;
                }else if( highestFreq !=0 && currFreq == highestFreq){
                    //if they are the same frequency
                    if(associatedString.compareTo(currWord) >0){
                        //3. first alphabetically
                        associatedString = currWord;
                    }
                }
            }
        }
        //if our associatedString is still null
        //this means the word cannot be found, so return null
        return associatedString;
    }


    public Set<String> DeletionDistance(String inputWord) {
        //loop through each of the characters in the word
        //for each character remove it from the word
        //add it to the set
        Set<String> setToReturn = new HashSet<>();
        //wait should this be equal to new HashSet(); or what I have or null?
        for (int i = 0; i < inputWord.length(); ++i) {
            StringBuilder sb = new StringBuilder(inputWord);
            sb.deleteCharAt(i);
            setToReturn.add(sb.toString());
        }
        return setToReturn;
    }
    public Set<String> TransposeDistance(String inputWord){
        Set<String> setToReturn = new HashSet<>();
        //do a for loop for each character in the hash
        //house, ohuse, huose, hosue, houes,
        //starting with teh second index
        //but it before the index before it
        for (int i = 0; i < inputWord.length()-1; ++i) {
            char currChar = inputWord.charAt(i);
            StringBuilder sb = new StringBuilder(inputWord);
            sb.deleteCharAt(i);
            //delete the character at its current position
            int index = i+1;
            //then add it in at the i-1 index
            setToReturn.add(insertChar(sb.toString(), currChar, index));
        }


            return setToReturn;
    }
    public Set<String> AlterationDistance(String inputWord){
        Set<String> setToReturn = new HashSet<>();
        // for each character in inputWord,
        // replace that character with every other character in the alphabet
        for (int i = 0; i < inputWord.length(); ++i) {
            char currChar = inputWord.charAt(i);
            StringBuilder sb = new StringBuilder(inputWord);
            sb.deleteCharAt(i);
            //works great, now I need to just loop through every letter in the alphabet
            for(char alphabet = 'a'; alphabet <='z'; alphabet++ ){
                if(alphabet != currChar) {
                    setToReturn.add(insertChar(sb.toString(), alphabet, i));
                }
            }
        }
            return setToReturn;
    }
    public Set<String> IterationDistance(String inputWord){
        Set<String> setToReturn = new HashSet<>();
        for (int i = 0; i < inputWord.length()+1; ++i) {
            //char currChar = inputWord.charAt(i-1);
            for(char alphabet = 'a'; alphabet <='z'; alphabet++ ) {

                setToReturn.add(addChar(inputWord, alphabet, i));
            }

        }

        return setToReturn;
    }

    public String insertChar (String str, char ch, int index){
        return str.substring(0, index) + ch + str.substring(index);
    }
    public String addChar (String str, char ch, int index){
        return str.substring(0, index) + ch + str.substring(index);
    }
}
