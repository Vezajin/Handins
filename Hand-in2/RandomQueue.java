import java.util.Iterator;

public class RandomQueue<Item> implements Iterable<Item> {

	private Item[] array;
	private int N;
	
	public static void  main(String[] args) {
		// Build a queue containing the Integers 1,2,...,6:
    RandomQueue<Integer> Q= new RandomQueue<Integer>();
    for (int i = 1; i < 7; ++i) Q.enqueue(i); // autoboxing! cool!
 
    // Print 30 die rolls to standard output
    StdOut.print("Some die rolls: ");
    for (int i = 1; i < 30; ++i) StdOut.print(Q.sample() +" ");
    StdOut.println();

    // Let's be more serious: do they really behave like die rolls?
    int[] rolls= new int [10000];
    for (int i = 0; i < 10000; ++i)
      rolls[i] = Q.sample(); // autounboxing! Also cool!
    StdOut.printf("Mean (should be around 3.5): %5.4f\n", StdStats.mean(rolls));
    StdOut.printf("Standard deviation (should be around 1.7): %5.4f\n",
		  StdStats.stddev(rolls));
    
    // Let's look at the iterator. First, we make a queue of colours:
    
    RandomQueue<String> C= new RandomQueue<String>();
    C.enqueue("red"); C.enqueue("blue"); C.enqueue("green"); C.enqueue("yellow"); 

    Iterator I= C.iterator();
    Iterator J= C.iterator();

    StdOut.print("Two colours from first shuffle: ");
    StdOut.print(I.next()+" ");
    StdOut.print(I.next()+" ");
    
    StdOut.print("\nEntire second shuffle: ");
    while (J.hasNext()) StdOut.print(J.next()+" ");

    StdOut.print("\nRemaining two colours from first shuffle: ");
    StdOut.print(I.next()+" ");
    StdOut.println(I.next());
	}
	
	@SuppressWarnings("unchecked")
	public RandomQueue() { // create an empty random queue
		array = (Item[]) new Object[10];
		N = 0; // number of items on the queue
	}
	
	public boolean isEmpty() { return N == 0; }
	
	public int size() { return N; }
	
	@SuppressWarnings("unchecked")
	public void enqueue(Item item) { // Add item to the end of the queue.
		boolean alreadyExists = false;
		for(int i = 0; i<array.length; i++) {
			if(array[i] == null) // then the end of the used array has been reached.
				break;
			if(item == array[i])
				alreadyExists = true;
		}
		
		if(alreadyExists == false) {
			if(N < array.length) {
				array[N] = item;
				N++;
			}
			else {
				Item[] tmp = (Item[]) new Object[array.length*2];
				for(int i = 0; i<array.length; i++) {
					tmp[i] = array[i];
				}
				array = tmp;
				array[N] = item;
				N++;
			}
		}
	}
	@SuppressWarnings("unchecked")
	public Item dequeue() { // Remove a random item from the queue.
		int p = StdRandom.uniform(N);
		Item item = array[p];
		if(array[p+1] != null) { //Checks if we're at the end of the used array already.
			for(int i = p; i<array.length; i++) {
				if(array[i+1] != null)
					array[i] = array[i+1];
				else //Then we've reached the end of the stored items
					array[i] = null;
					break;
			}
		}
		else {
			array[p] = null;
		}
		N--;
		if(N == array.length/4) { //resizes the array
			Item[] tmp = (Item[]) new Object[array.length/2];
			for(int i = 0; i<array.length; i++) {
				tmp[i] = array[i];
			}
			array = tmp;
		}
		return item;
	}
	
	public Item sample() {// return (but do not remove) a random item
		int p = StdRandom.uniform(N);
		Item item = array[p];
		return item;
	}
	
	public Iterator<Item> iterator() { return new QueueIterator(); }
		private class QueueIterator implements Iterator<Item> {
			int i = 0;
			boolean iterated[];
			
			private QueueIterator() {
				iterated = new boolean[N];
				for(int j = 0; j<iterated.length; j++) { iterated[j] = false; }
			}
			
			public boolean hasNext() {
			return(i<N);
			}
			
			public Item next() {
				int j = StdRandom.uniform(N);
				i++;
				boolean found = false;
				while(found == false) {
					if(iterated[j] == false) {
						found = true;
						iterated[j] = true;
					}
					
					else
						j = StdRandom.uniform(N);
				}
				return array[j];
			}
			
			public void remove() {}
		}
}