package tools;

public class Tools {

	static public boolean isBetween(int val, int min, int max) {
		return min <= val && val <= max;
	}
	
	static public void swap (int arr[], int i1, int i2) {
		int tmp = arr[i2];
		arr[i2] = arr[i1];
		arr[i1] = tmp;
	}
	
	public static void printTrace() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		System.out.println("Stack length : " + String.valueOf(trace.length - 2));
		for(int i = 2; i < trace.length; ++i) {
			System.out.println(trace[i]);
		}
		System.out.println("");
   }
}
