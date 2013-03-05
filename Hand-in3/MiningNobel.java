import java.util.Arrays;

public class MiningNobel {
	public static int count(double[] a) {
		Arrays.sort(a);
		int N = a.length;
		int cnt = 0;
		for(int i = 0; i<N;i++)
			for(int j = 0; j<N;j++)
				for(int k = 0; k<N;k++) {
					if(Arrays.binarySearch(a, -a[i]-a[j]-a[k]) > 0)
						cnt++;
					}
		return cnt;
	}
	
	public static double[] readNumbers(String fileName){
		In input = new In(fileName);
		int lines = 0;
		while(input.readLine() != null) {
			lines++;
		}
		double[] numbers = new double[lines];
		int i = 0;
		input = new In(fileName);
		while(input.hasNextLine()) {
			numbers[i++] = Double.parseDouble(input.readLine().trim().split("\\,+")[1]);
		}
		input.close();
		return numbers;
	}
	
	public static void main(String[] args) {
		double[] numbers = readNumbers("constants.txt");
		Stopwatch timer = new Stopwatch();
		int output = count(numbers);
		double time = timer.elapsedTime();
		StdOut.println(output + " time: " + time);
	}
}