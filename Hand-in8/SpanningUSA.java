import java.util.HashMap;

public class SpanningUSA {
	private HashMap<String, Integer> cities = new HashMap<String, Integer>();
	private EdgeWeightedGraph graph;
	private PrimMST primMST;

	public SpanningUSA(String fileName) 
	{
		In reader = new In(fileName);
		String line;
		int cityCounter = 0, edgeWeight;
		String fNode, tNode;
		String tmpArray[];
		Stopwatch stopwatch = new Stopwatch();
		while(reader.hasNextLine()) 
		{
			line = reader.readLine();
			if (!line.matches(".*\\d.*")) {
				cities.put(line.trim(), cityCounter++);	
			}
			else {
				if(graph == null)
					graph = new EdgeWeightedGraph(cityCounter);
				tmpArray = line.split("\\--");
				fNode = tmpArray[0].trim();

				tmpArray = tmpArray[1].split("\\[");
				tNode = tmpArray[0].trim();
				edgeWeight = Integer.parseInt(tmpArray[1].substring(0, tmpArray[1].length()-1));

				graph.addEdge(new Edge(cities.get(fNode), cities.get(tNode), edgeWeight));
			}
		}
		
		primMST = new PrimMST(graph);
		System.out.println(stopwatch.elapsedTime());
		System.out.println(primMST.getTotalWeight());
	}

	public static void main(String[] args) 
	{
		new SpanningUSA("USA.txt");
	}

	public class EdgeWeightedGraph
	{
		private final int V; // number of vertices
		private int E; // number of edges
		private Bag<Edge>[] adj; // adjacency lists
		@SuppressWarnings("unchecked")
		public EdgeWeightedGraph(int V)
		{
			this.V = V;
			this.E = 0;
			adj = (Bag<Edge>[]) new Bag[V];
			for (int v = 0; v < V; v++)
				adj[v] = new Bag<Edge>();
		}
		public int V() { return V; }
		public int E() { return E; }

		public void addEdge(Edge e)
		{
			int v = e.either(), w = e.other(v);
			adj[v].add(e);
			adj[w].add(e);
			E++;
		}
		public Iterable<Edge> adj(int v)
		{ return adj[v]; }

		/*public Iterable<Edge> edges()
		{
		}*/
		// See page 609.
	}

	public class Edge implements Comparable<Edge>
	{
		private final int v; // one vertex
		private final int w; // the other vertex
		private final double weight; // edge weight

		public Edge(int v, int w, double weight)
		{
			this.v = v;
			this.w = w;
			this.weight = weight;
		}
		public double weight()
		{ return weight; }

		public int either()
		{ return v; }

		public int other(int vertex)
		{
			if (vertex == v) return w;
			else if (vertex == w) return v;
			else throw new RuntimeException("Inconsistent edge");
		}

		public int compareTo(Edge that)
		{
			if (this.weight() < that.weight()) return -1;
			else if (this.weight() > that.weight()) return +1;
			else return 0;
		}

		public String toString()
		{ return String.format("%d-%d %.2f", v, w, weight); }
	}

	public class PrimMST
	{
		private Edge[] edgeTo; // shortest edge from tree vertex
		private double[] distTo; // distTo[w] = edgeTo[w].weight()
		private boolean[] marked; // true if v on tree
		private IndexMinPQ<Double> pq; // eligible crossing edges
		private double totalWeight = 0;

		public PrimMST(EdgeWeightedGraph G)
		{
			edgeTo = new Edge[G.V()];
			distTo = new double[G.V()];
			marked = new boolean[G.V()];
			for (int v = 0; v < G.V(); v++)
				distTo[v] = Double.POSITIVE_INFINITY;
			pq = new IndexMinPQ<Double>(G.V());
			distTo[0] = 0.0;
			pq.insert(0, 0.0); // Initialize pq with 0, weight 0.
			while (!pq.isEmpty())
				visit(G, pq.delMin()); // Add closest vertex to tree.
			for(int i = 1; i<edgeTo.length; i++)
				totalWeight += edgeTo[i].weight();
		}

		private void visit(EdgeWeightedGraph G, int v)
		{ // Add v to tree; update data structures.
			marked[v] = true;
			for (Edge e : G.adj(v))
			{
				int w = e.other(v);
				if (marked[w]) continue; // v-w is ineligible.
				if (e.weight() < distTo[w])
				{ // Edge e is new best connection from tree to w.
					edgeTo[w] = e;
					distTo[w] = e.weight();
					if (pq.contains(w)) pq.changeKey(w, distTo[w]);
					else pq.insert(w, distTo[w]);
				}
			}
		}
		
		public double getTotalWeight() {
			return totalWeight;
		}

		/*public Iterable<Edge> edges()
		{
			// See Exercise 4.3.21.
		}*/
	}
}
