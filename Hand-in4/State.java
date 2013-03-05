public class State {
		String name;
		int pop;
		int seats;
		double priority;
		
		public State (String s, int pop, int seats) {
			name = s;
			this.pop = pop;
			this.seats = seats;
			huntingtonHill();
		}
		
		public void setPriority(double i) {
			priority = i;
		}
		
		public double getPriority() {
			return priority;
		}
		
		public void addSeat() {
			seats++;
		}
		
		public int getPop() {
			return pop;
		}
		
		public int getSeats() {
			return seats;
		}
		
		public String getName() {
			return name;
		}
		
		public void huntingtonHill() {
			int p = getPop();
			int n = getSeats();
			double hh = p/Math.sqrt(n*(n+1));
			setPriority(hh);
			
		}
		
		public double compareTo(State s) {
			return priority - s.getPriority();
		}
	}