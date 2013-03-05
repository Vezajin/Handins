public class UFOne {
    private int[] id;    // id[i] = parent of i
    private int[] sz;    // sz[i] = number of objects in subtree rooted at i
    private int count;   // number of components
	//********************* Our code**********************************
	private int superCount;
	private double[] averageManyFriends, averageAllConnected, averageAllHaveFriends;
	private double manyFriendsRounds, allConnectedRounds, allHaveFriendsRounds;
	//***********************************************************************************

   /**
     * Create an empty union find data structure with N isolated sets.
     * @throws java.lang.IllegalArgumentException if N < 0
     */
    public UFOne(int N) {
        if (N < 0) throw new IllegalArgumentException();
        count = N;
		superCount = 0;
        id = new int[N];
        sz = new int[N];
//***********************Our code*********************************
		averageManyFriends = new double[100];
		averageAllConnected = new double[100];
		averageAllHaveFriends = new double[100];
		
		manyFriendsRounds = 0;
		allConnectedRounds = 0;
		allHaveFriendsRounds = 0;
//****************************************************************		
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

   /**
     * Return the id of component corresponding to object p.
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= p < N
     */
    public int find(int p) {
        if (p < 0 || p >= id.length) throw new IndexOutOfBoundsException();
        while (p != id[p])
            p = id[p];
        return p;
    }

   /**
     * Return the number of disjoint sets.
     */
    public int count() {
        return count;
    }

	public int size(int i) {
		return sz[i];
	}
  
   /**
     * Are objects p and q in the same set?
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

  
   /**
     * Replace sets containing p and q with their union.
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
     */
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
		// If the elements component size is 1, then it has no friends.
		if(sz[i] == 1) {
			superCount++; //<--- our code
		}
		if(sz[j] == 1) {
			superCount++; //<--- our code
		}
        if (i == j) return;

        // make smaller root point to larger one
        if   (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else                 { id[j] = i; sz[i] += sz[j]; }
        count--;
    }


    public static void main(String[] args) {
        int N = StdIn.readInt();
//*************** Our code *****************************************************
        UFOne ufTracker = new UFOne(100);
		double timeForGiant = 0;
		int runThrough = 0;
		while(runThrough<100) {
			
			UFOne uf = new UFOne(N);
			boolean allHaveFriends = false;
			boolean allConnected = false;
			boolean hasManyFriends = false;
			
			//StdRandom randomOne = new StdRandom();
			//StdRandom randomTwo = new StdRandom();
			int rounds = 0;
			Stopwatch stopwatch = new Stopwatch();
			
			while(allHaveFriends != true || allConnected != true || hasManyFriends != true) {
				
				//chooses two random IDs and unify them, to simulate a social network where people befriend each other.
				int p = StdRandom.uniform(N);
				int q = StdRandom.uniform(N);
				uf.union(p, q);
				
				if(uf.superCount >= N) {
					allHaveFriends = true;
					ufTracker.allHaveFriendsRounds = rounds;
				}
				//If all are connected, then there will only be one component.
				if(uf.count == 1) {
					allConnected = true;
					ufTracker.allConnectedRounds = rounds;
				}
				//Checks if there if a component that is "friends" with half of all the IDs.
				if(hasManyFriends == false) {
					if(uf.sz[uf.find(p)] >= N/2) {
						hasManyFriends = true;
						ufTracker.manyFriendsRounds = rounds;
						timeForGiant += stopwatch.elapsedTime();
					}
				}
				rounds++;
			}
			ufTracker.averageManyFriends[runThrough] = ufTracker.manyFriendsRounds;
			ufTracker.averageAllConnected[runThrough] = ufTracker.allConnectedRounds;
			ufTracker.averageAllHaveFriends[runThrough] = ufTracker.allHaveFriendsRounds;
			runThrough++;
		}
		double average = StdStats.mean(ufTracker.averageManyFriends);
		StdOut.println("Time it took until a giant component came to be in rounds: " + average);
		StdOut.println("StdDev: " + StdStats.stddev(ufTracker.averageManyFriends));
		StdOut.println("Time it took for a giant component: " + timeForGiant/100 + " seconds");
		
		average = StdStats.mean(ufTracker.averageAllConnected);
		StdOut.println("Time it took until everyone were connected came to be in rounds: " + average);
		StdOut.println("StdDev: " + StdStats.stddev(ufTracker.averageAllConnected));
		
		average = StdStats.mean(ufTracker.averageAllHaveFriends);
		StdOut.println("Time it took until all had friends came to be in rounds: " + average);
		StdOut.println("StdDev: " + StdStats.stddev(ufTracker.averageAllHaveFriends));
	}
}