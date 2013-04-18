import java.util.ArrayList;

public class BreadthFirstPaths
{
	private boolean[] marked; // Is a shortest path to this vertex known?
	private int[] edgeTo; // last vertex on known path to this vertex
	private final int s; // source
	public BreadthFirstPaths(Digraph G, int s) {
		marked = new boolean[G.vertices()];
		edgeTo = new int[G.vertices()];
		this.s = s;
		bfs(G, s);
	}
	private void bfs(Digraph G, int s) {
		Queue<Integer> queue = new Queue<Integer>();
		marked[s] = true; // Mark the source
		queue.enqueue(s); // and put it on the queue.
		while (!queue.isEmpty()) {
			int v = queue.dequeue(); // Remove next vertex from the queue.
			for (int w : G.adj(v))
				if (!marked[w]) // For every unmarked adjacent vertex,
				{
					edgeTo[w] = v; // save last edge on a shortest path,
					marked[w] = true; // mark it because path is known,
					queue.enqueue(w); // and add it to the queue.
				}
		}
	}
	public boolean hasPathTo(int v)
	{ return marked[v]; }

	public Stack<Integer> pathTo(int v) {
		if (!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x != s; x = edgeTo[x])
			path.push(x);
		path.push(s);
		return path;
	}
	
	public static ArrayList<Integer> testMethod(String inputTestFile, WordLadder wl) {
		In reader = new In(inputTestFile);
        ArrayList<Integer> results = new ArrayList<Integer>();
        
        while(reader.hasNextLine())
        {
            String[] line = reader.readLine().split("\\s+");
            String word1 = line[0].trim();
            String word2 = line[1].trim();  
            
            BreadthFirstPaths bfp = new BreadthFirstPaths(wl.getGraph(), wl.getWordsHashMap().get(word1));
            Stack<Integer> path;
            if((path = bfp.pathTo(wl.getWordsHashMap().get(word2))) != null)
                results.add(path.size()-1);
            else
                results.add(-1);
        }
        return results;
	}
	
	public static void main (String[] args) {
		WordLadder wordLadder = new WordLadder("words-5757-dat.txt");
		for(int r : testMethod("words-5757-test-in.txt", wordLadder)) {
			System.out.println(r);
		}
	}
}