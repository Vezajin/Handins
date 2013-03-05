public class QuickFindUFMark {
    private int[] id;
    private int count;
	private boolean allConnected, nonIsolated, giant;
	int isIsolated;
	int numberOfElements;
	private double[] averageManyFriends, averageAllConnected, averageAllHaveFriends;
	private double manyFriendsRounds, allConnectedRounds, allHaveFriendsRounds;

    // instantiate N isolated components 0 through N-1
    public QuickFindUFMark(int N) {
        numberOfElements = N;
		count = N;
		isIsolated = N;
		allConnected = false;
		nonIsolated = false;
		giant = false;
		averageManyFriends = new double[1];
		averageAllConnected = new double[1];
		averageAllHaveFriends = new double[1];
		
		manyFriendsRounds = 0;
		allConnectedRounds = 0;
		allHaveFriendsRounds = 0;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    // return number of connected components
    public int count() {
        return count;
    }

    // Return component identifier for component containing p
    public int find(int p) {
        return id[p];
    }

    // are elements p and q in the same component?
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }
		
	public void union(int p, int q) { // Give p and q the same root.
		int pID = find(p);			
		int qID = find(q);	
		// Nothing to do if p and q are already in the same component.
		if (pID == qID) return;
		// Rename p’s component to q’s name.
		int tempCount = 0;
		int tempIsolatedCount = 0;
		for (int i = 0; i < id.length; i++) {
			if (id[i] == pID) {
				id[i] = qID;
				tempIsolatedCount++;
			}
			if(id[i] ==  qID)
				tempCount++;
		}
		count--;
		
		if(tempCount >= numberOfElements/2) {
		giant = true;
		}
		if(tempIsolatedCount == 1) {
			isIsolated--;
		} 
		if(tempCount - tempIsolatedCount == 1) {
			isIsolated--;
		}
		if(isIsolated == 0) {
			nonIsolated = true;
		}
		
		if(count == 1) {
			allConnected = true;
		}
	}

    public static void main(String[] args) {
         int N = StdIn.readInt();
		 boolean allHaveFriends = false;
		 boolean allConnected = false;
		 boolean hasManyFriends = false;
        QuickFindUFMark ufTracker = new QuickFindUFMark(100);
		double timeForGiant = 0;
		double timeNonIsolated = 0;
		double timeAllConnected = 0;
			
			QuickFindUFMark uf = new QuickFindUFMark(N);
			
			//StdRandom randomOne = new StdRandom();
			//StdRandom randomTwo = new StdRandom();
			int rounds = 0;
			Stopwatch stopwatch = new Stopwatch();
			
			while(allHaveFriends != true || allConnected != true || hasManyFriends != true) {
				
				//chooses two random IDs and unify them, to simulate a social network where people befriend each other.
				int p = StdRandom.uniform(N);
				int q = StdRandom.uniform(N);
				//if(uf.connected(p, q)) continue;
				uf.union(p, q);
				
				if(allHaveFriends == false && uf.nonIsolated == true) {
						allHaveFriends = true;
						ufTracker.allHaveFriendsRounds = rounds;
						timeNonIsolated += stopwatch.elapsedTime();
				}
				//If all are connected, then there will only be one component.
				if(allConnected == false && uf.allConnected == true) {
						allConnected = true;
						ufTracker.allConnectedRounds = rounds;
						timeAllConnected += stopwatch.elapsedTime();
				}
				//Checks if there if a component that is "friends" with half of all the IDs.
				if(hasManyFriends == false && uf.giant == true) {
						hasManyFriends = true;
						ufTracker.manyFriendsRounds = rounds;
						timeForGiant += stopwatch.elapsedTime();
				}
				rounds++;
			}
			ufTracker.averageManyFriends[0] = ufTracker.manyFriendsRounds;
			ufTracker.averageAllConnected[0] = ufTracker.allConnectedRounds;
			ufTracker.averageAllHaveFriends[0] = ufTracker.allHaveFriendsRounds;

		double average = StdStats.mean(ufTracker.averageManyFriends);
		StdOut.println("Time it took until a giant component came to be in rounds: " + average);
		StdOut.println("StdDev: " + StdStats.stddev(ufTracker.averageManyFriends));
		StdOut.println("Time it took for a giant component: " + timeForGiant + " seconds");
		
		average = StdStats.mean(ufTracker.averageAllConnected);
		StdOut.println("Time it took until everyone were connected came to be in rounds: " + average);
		StdOut.println("StdDev: " + StdStats.stddev(ufTracker.averageAllConnected));
		StdOut.println("Time it took for all connected: " + timeAllConnected + " seconds");
		
		average = StdStats.mean(ufTracker.averageAllHaveFriends);
		StdOut.println("Time it took until all had friends came to be in rounds: " + average);
		StdOut.println("StdDev: " + StdStats.stddev(ufTracker.averageAllHaveFriends));
		StdOut.println("Time it took for non isolated: " + timeNonIsolated + " seconds");
    }

}