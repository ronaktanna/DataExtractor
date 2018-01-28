import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Trie {
	private class TrieNode{
		
		Map<Character, TrieNode> children;
		boolean isEndOfWord;
		
		public TrieNode(){
			children = new HashMap<>(); //(Character, TrieNode);
			isEndOfWord = false;
		}
	}
	
	private final TrieNode root;
	
	public Trie(){
		root = new TrieNode();
	}
	
	/** Function to insert a TrieNode **/ 
	public void insert(String word){
		TrieNode current = root;
		for(int i=0; i<word.length(); i++){
			char c = word.charAt(i);
			TrieNode node = current.children.get(c);
			if(node == null){
				node = new TrieNode();
				current.children.put(c,node);
			}
			current = node;
		}
		current.isEndOfWord = true;
	}
	
	/** Utility function 1 to insert a TrieNode. **/
	public void insertRecursive(String word){
		insertRecursive(root,word,0);
	}
	
	/** Utility function 2 to insert a TrieNode. **/
	private void insertRecursive(TrieNode current, String word, int index){
		if(index == word.length()){
			current.isEndOfWord = true;
			return;
		}
		TrieNode node = current.children.get(word.charAt(index));
		if(node == null){
			node = new TrieNode();
			current.children.put(word.charAt(index), node);
		}
		insertRecursive(node, word, index+1);
	}
	
	/** Function used to search if an entire string is present in the Tree. **/
	public boolean search(String word){
		TrieNode current = root;
		for(int i=0; i<word.length(); i++){
			TrieNode node = current.children.get(word.charAt(i));
			if(node == null)
				return false;
			current = node;
		}
		
		return current.isEndOfWord;
	}
	
	/** Utility function to search for the entire string in the Trie. **/
	public boolean searchRecursive(String word){
		return searchRecursive(root, word, 0);
	}
	
	/** Utility function to search for the entrie string in the Trie. **/
	private boolean searchRecursive(TrieNode current, String word, int index){
		if(index == word.length())
			return current.isEndOfWord;
		TrieNode node = current.children.get(word.charAt(index));
		if(node == null)
			return false;
		return searchRecursive(node, word, index+1);
	}
	
	/** Function to search for a prefix of a string alone in the Trie. **/
	public boolean searchForPrefix(String prefix){
		
		TrieNode current = root;
		TrieNode node;
		for(int i=0; i<prefix.length(); i++){
			node = current.children.get(prefix.charAt(i));
			if(node == null)
				return false;
			current = node;
		}
		
		return true;
	}
	
	/** Function to delete a String from the Tree. **/
	public void delete(String word){
		delete(root,word,0);
	}
	
	/** Utility function to delete a String from the Tree. **/
	public boolean delete(TrieNode current, String word, int index){
		if(index == word.length()){
			if(!current.isEndOfWord)
				return false;
			current.isEndOfWord = true;
			return current.children.size()==0;
		}
		TrieNode node = current.children.get(word.charAt(index));
		if(node == null)
			return false;
		boolean shouldDeleteCurrentNode = delete(node, word, index+1);
		if(shouldDeleteCurrentNode){
			current.children.remove(word.charAt(index));
			return current.children.size()==0;
		}
		return false;
	}
	
	public static void main(String[] args){
		Trie TrieObject = new Trie();
		
		/** Demo Data Structure Setup **/
		TrieObject.insert("148908433");
		TrieObject.insert("283133290");
		TrieObject.insert("1050450");
		TrieObject.insert("754243590");
		TrieObject.insert("098248459");
		TrieObject.insert("012948569");
		
		/** Search iterating over a bag of words **/
		/** List<String> bagOfWords = new ArrayList();
		 * bagOfWords => ["Hello", "the", "complaint", "is", "for", "LoanNo:", "1050450"]; */
		
		// String emailContent = "Hello, I want to enquire for the Loan Number 283133290 and my complaint is that something isn't working.";
		String emailContent = "Hello, I want to enquire for my Loan Number 1050450. My complaint is that something isn't working. Bla Bla Bla.";
		
		System.out.println("EmailText: " + emailContent);
		System.out.println("Extracting the bag of words...");
		String[] words = emailContent.replace(".", "").replace(",", "").replace("?", "").replace("!","").split(" ");
		System.out.print("Bag of Words: ");
		for(int i=0; i<words.length; i++)
			System.out.print(words[i] + " ");
		System.out.println();
		
		List<String> bagOfWords = Arrays.asList(words);
		
		boolean doesExist = false;
		
		for(String word: bagOfWords) {
			doesExist = TrieObject.search(word);
			if (doesExist) {
				System.out.println("The loan number is: " + word);
				break;
			}
		}
		
		if(!doesExist)
			System.out.println("No Loan Number found in the text!");
		
		/* TrieObject.insert("abc");
		TrieObject.insert("abgl");
		TrieObject.insert("cdf");
		TrieObject.insert("abcd");
		TrieObject.insert("lmn");
		System.out.println("Words inserted!");
		boolean doesExist = TrieObject.search("ab");
		System.out.println(doesExist);
		TrieObject.delete("abgl");
		System.out.println("Deleted abgl");
		doesExist = TrieObject.search("abcd");
		System.out.println(doesExist);
		TrieObject.delete("lmn");
		System.out.println("Deleted lmn");
		doesExist = TrieObject.searchForPrefix("l");
		System.out.println(doesExist); */
	}
}
