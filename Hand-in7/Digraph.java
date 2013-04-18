public class Digraph {
	private final int vertices;
	private int edges;
	private Bag<Integer>[] adj;
	
	@SuppressWarnings("unchecked")
	public Digraph(int V)
	{
		vertices = V;
		edges = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}
	public int vertices() { return vertices; }
	
	public int edges() { return edges; }
	
	public void addEdge(int v, int w) {
		adj[v].add(w);
		edges++;
	}
	
	public Iterable<Integer> adj(int v)
	{ return adj[v]; }
	
	public Digraph reverse() {
		Digraph R = new Digraph(vertices);
		for (int v = 0; v < vertices; v++)
			for (int w : adj(v))
				R.addEdge(w, v);
		return R;
	}
}