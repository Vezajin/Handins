public class Gorilla {
	int d;
	public static void main(String[] args) {
		
		int k = 20, d = 10000, counter = 0;
		String line, species = "", dna = "";
		int[] p = new int[d];
		Race[] r = new Race[12];
		In reader = new In("dataFile.txt");
		Gorilla gorilla = new Gorilla();
		gorilla.d = d;
		
		while(reader.hasNextLine()) {
			line = reader.readLine();
			//Then it is a race
			if(line.contains(">")) {
				if(!dna.equals("")) {
					p = gorilla.calcHash(dna, k);
					Race race = new Race(species, p);
					r[counter++] = race;
					dna = "";
				}
				species = line;				
			}
			
			//It is dna
			else 
				dna += line;
		}
		p = gorilla.calcHash(dna, k);
		Race race = new Race(species, p);
		r[counter] = race;
		gorilla.similiarityToHumans(r);
		gorilla.similiarityToEachOther(r);
	}
	
	public Gorilla() {
		
	}
	
	public void similiarityToHumans(Race[] r) {
		for (int i = 1; i < r.length; i++) {
			int[] humans = r[0].getProfile();
			StdOut.println("Humans and "+r[i].getName()+" similiarty: "+
					dotProduct(humans, r[i].getProfile())/(length(humans)*length(r[i].getProfile())));
		}
	}
	
	public void similiarityToEachOther(Race[] r) {
		double mostAlike = 0, leastAlike = 1;
		String mostNames = "", leastNames = "";
		for (int j = 0; j < r.length; j++)
			for (int i = 1; i < r.length; i++) {
				if(i!=j){
					double result = dotProduct(r[j].getProfile(), r[i].getProfile())/(length(r[j].getProfile())*length(r[i].getProfile()));
					if(mostAlike < result) {
						mostAlike = result; mostNames = r[j].getName() +" and "+r[i].getName();
					}
					if (leastAlike > result) {
						leastAlike = result; leastNames = r[j].getName() +" and "+r[i].getName();
					}
				}
			}
		StdOut.println("The two most alike are: " +mostNames+ ": "+mostAlike);
		StdOut.println("The two least alike are: " +leastNames+ ": "+leastAlike);
	}
	
	public int[] calcHash(String dna, int k) {
		int[] array = new int[d];
		for(int index = 0; index < dna.length()-k+1; index++) 
				array[hash(dna.substring(index, index+k))]++;
		return array;
	}
	
	
	public int hash(String x) {
		return (x.hashCode() & 0x7fffffff) % d;
	}
	
	public static double dotProduct(int[] profileA, int[] profileB) {
		double dotProduct = 0;
		for(int i = 0; i<profileA.length; i++) {
			dotProduct += profileA[i]*profileB[i];
		}
		return dotProduct;
	}
	
	public static double length(int[] profile) {
		double length = 0;
		for(int i = 0; i<profile.length; i++) {
			length += (profile[i] * profile[i]);
		}
		return Math.sqrt(length);
	}
	
	public static void computeAngle() {
		int[] a = {2,1};
		int[] b =  {8,4};
		int[] c = {5,9};
		int[] d = {13,2};
		double d1 = dotProduct(a, b)/(length(a)*length(b));
		double d2 = dotProduct(c, b)/(length(c)*length(b));
		double d3 = dotProduct(c, d)/(length(c)*length(d));
		StdOut.println(d1); //Tested with calcualtor, results match
		StdOut.println(d2); //Tested with calcualtor, results match
		StdOut.println(d3); //Tested with calcualtor, results match
	}
}