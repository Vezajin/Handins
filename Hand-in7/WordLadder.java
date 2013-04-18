import java.util.ArrayList;
import java.util.HashMap;


public class WordLadder {
	String line;
	In reader;
	HashMap<String, Integer> wordsHashMap;
	ArrayList<String> words;
	Digraph graph;
	
	public WordLadder(String fileName) {
		In reader = new In(fileName);
		wordsHashMap = new HashMap<String, Integer>();
		words = new ArrayList<String>();
		
		while(reader.hasNextLine()) {
			line = reader.readLine();
		}
		
		reader = new In(fileName); 
		String line;
		while (reader.hasNextLine()) {
			line = reader.readLine();
			words.add(line);
			wordsHashMap.put(line, words.size()-1);
		}
		
		reader.close();
		graph = new Digraph(words.size());
		for(int i = 0; i < words.size(); i++){
            compare(words.get(i), i);
        }
	}
	
	private void compare(String word, int val) {
		char[] arrayWord = word.substring(1, word.length()).toCharArray();
		
		for(int i = 0; i<words.size(); i++) {
			if(i != val) {
				String wordToCompareTo = words.get(i);
				int count = 0;
				
				for(int j = 0; j<arrayWord.length; j++) {		
					if(wordToCompareTo.contains(Character.toString(arrayWord[j]))) {
						wordToCompareTo = wordToCompareTo.replaceFirst(Character.toString(arrayWord[j]), "");
		                count++;
		            }
		            else
		                break;
					
					if(count == 4)
						graph.addEdge(val, i);
				}
			}
		}
	}
	
	public Digraph getGraph() {
		return graph;
	}
	
	public HashMap<String, Integer> getWordsHashMap() {
		return wordsHashMap;
	}
}
