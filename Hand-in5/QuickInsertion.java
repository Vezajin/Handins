public class QuickInsertion {
	public static void sort(int[] a, int cutoff) {
		sort(a, cutoff, 0, a.length-1);
	}
	private static void sort(int[] a, int cutoff, int lo, int hi) {
		if (hi <= lo + cutoff) {
			insertionSort(a, lo, hi);
			return;
		}
		int j = partition(a, lo, hi);
		sort(a, cutoff, lo, j-1);
		sort(a, cutoff, j+1, hi);
	}
	
	private static int partition(int[] a, int lo, int hi) {
		int i = lo, j = hi+1;
		int v = a[lo];
		while (true) {
			while(a[++i] < v == true) if (i == hi) break;
				while(v < a[--j] == true) if (j == lo) break;
			if (i >= j) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}
	private static void insertionSort(int[] a, int lo, int hi) {
		int N = hi;
		for (int i = lo; i < N+1; i++) {
			for (int j = i; j > 0 && a[j] < a
			
			[j-1]; j--)
				exch(a, j, j-1);
		}
	}
	
	private static void exch(int[] a, int i, int j) {
		int tmpStorage = a[i]; a[i] = a[j]; a[j] = tmpStorage;
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		int cutoff = StdIn.readInt();
		int j = 0;
		while(j < 5) {
			double[] runTimes = new double[100];
			for(int runThrough = 0; runThrough < 100; runThrough++) {
				int[] a = new int[N];
				for(int i = 0; i<a.length; i++)
					a[i] = StdRandom.uniform(10*N);
				Stopwatch stopwatch = new Stopwatch();
				sort(a, cutoff);
				runTimes[runThrough] = stopwatch.elapsedTime();
			}
			StdOut.print("Mean: "); StdOut.printf("%.2e", StdStats.mean(runTimes));
			StdOut.println();
			StdOut.print("Dev: "); StdOut.printf("%.2e",StdStats.stddev(runTimes));
			StdOut.println();
			N = N*10;
			j++;
		}
	}
}