import java.util.Arrays;
public class Congress {

	public Congress() {
		String s1 = "4062608 Alabama";
		String s2 = "551947 Alaska";
		String s3 = "29839250 California";
		String[] array = {s1, s2, s3};
		Arrays.sort(array);
		StdOut.println(array[0]);
		StdOut.println(array[1]);
		StdOut.println(array[2]);
	}
	
	public static void main(String[] args) {
		Congress congress = new Congress();
	}
}