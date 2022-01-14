# CS240
## what I have done so far
- I think I moved the files over properly
- unfortunately I am not so sure about the jars folder, there are two options and I dont know which one to pick
- Also not sure why passoff is refusing to be called anything but passoff.passoff
  - ditto for spell
## Brief overview of the project
- validate single words
- Dictionary generated from large text file
- Every time program runs it creates a dictionary from the text file
- Dictionary contains every word and frequency of word
- Want match to be case-insensitive
- use a Trie
  - Each Trie-node stores a count and a sequence of nodes
  - the word "kick" will be stored as follows:
  root.nodes['k].nodes['i].nodes['c].nodes['k]
  - we want to keep track of new nodes and words
  - you **must** use toString() hasCode() and equals(Object)
    - toString(): for each word, in alphabetical order: <word>\n
    - (must be recursive)
  - **use of other data structures is not allowed in trie or node classes**
  - Load all the words from provided file into your Trie with Trie.add(String)
  - compare the input against the trie with Trie.find(String)
- INode
- Four Edit distances
- 
## Task list for Spelling Corrector
- Create classes that implement ITrie, INOde and IspellCorrector interfaces
  - constructor takes no arguments
  - interfaces are on course website
  - need to implement all methods defined in the Interfaces
- Main class is also provided
  - need to create an instance of ISpellCorrector implementing class inside the 'main' method
- The provided code is in a 'spell' package **Your code should also be in a 'spell' package**
- Follow steps for setting up Spelling Corrector unit tests
- 
## objectives
- learn how to write java class
  - constructors
  - properties, getters/settings
  - throwing exceptions
  - toString(), equals(), hasCode()
  - Comparable, compareTo()
- learn how to implement Java interfaces
- learn basic Java I/O
