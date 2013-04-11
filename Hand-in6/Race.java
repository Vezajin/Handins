
public class Race {

	int[] profile;
	String name;
	public Race(String name, int[] p) {
		profile = p;
		this.name = name;
	}
	
	public int[] getProfile() {
		return profile;
	}
	
	public String getName() {
		return name;
	}
}
