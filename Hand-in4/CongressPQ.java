import java.util.Scanner;
public class CongressPQ {

	private State[] pq;
	private int states = 0;
	private int seatsRemaining = 0;
	
	public CongressPQ(int maxN) {
		pq = new State[maxN+1];
	}
	
	public void setRemainingSeats(int i) {
		seatsRemaining = i;
	}
	
	public int getRemainingSeats() {
		return seatsRemaining;
	}
	
	public void decreaseRemainingSeats() {
		seatsRemaining--;
	}
	
	public void insert(State state) {
		pq[++states] = state;
		swim(states);
	}
	
	public State delMax() {
		State max = pq[1];
		exch(1, states--);
		sink(1);
		pq[states+1] = null;
		return max;
	}
	
	boolean isEmpty() {
		return states == 0;
	}
	
	public State max() {
		State max = pq[1];
		return max;
	}
	
	public int size() {
		return states;
	}
	
	private void swim(int k) {
		while (k > 1 && less(k/2, k) == true) {
			exch(k, k/2);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2*k <= states) {
			int j = 2*k;
			if (j < states && less(j, j+1) == true) 
				j++;
			if (less(k, j) == false) 
				break;
			exch(k, j);
			 k = j;
		}
	}
	
	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}
		
	private void exch(int i, int j) { 
		State state = pq[i]; pq[i] = pq[j]; pq[j] = state; 
	}
	
	public static void main(String[] args) {
		In reader = new In("bads-labs/congress/data/1990-in.txt");
		Stopwatch stopwatch = new Stopwatch();
		
		int states = Integer.parseInt(reader.readLine());
		CongressPQ congressPQ = new CongressPQ(states);
		int seats = Integer.parseInt(reader.readLine());
		String stateName = "";
		int statePop = 0;
		//Creates all the states.
		while(reader.hasNextLine()) {
			stateName = reader.readLine();
			if(!reader.hasNextLine())
				break;
			statePop = Integer.parseInt(reader.readLine());
			State state = new State(stateName, statePop, 1);
			congressPQ.insert(state);
		}
		reader.close();
		
		//Adjusts the number of remaining seats by subtracking the number of states.
		congressPQ.setRemainingSeats(seats-congressPQ.size());
		//Fixes the PQ.
		congressPQ.sink(1);
		
		while(congressPQ.getRemainingSeats() > 0) {
			State state = congressPQ.delMax();
			state.addSeat(); congressPQ.decreaseRemainingSeats();
			//StdOut.println(state.getSeats());
			state.huntingtonHill();
			//StdOut.println(state.getPop());
			congressPQ.insert(state);
			congressPQ.sink(1);
			
		}
		Out writer = new Out("Output");
		while(congressPQ.size() > 0) {
			State state = congressPQ.delMax();
			writer.println(state.getName() + " " + state.getSeats());
		}
		writer.close();
		StdOut.println(stopwatch.elapsedTime());
	}
}