package projecttu.Additional;

import java.util.ArrayList;
import java.util.Arrays;


public class Combinations {
	
	public Combinations(int n, int m) {
		if ( n > 0 && m > 0) {
			N = n; M = m;
			arr = new Integer[N];
			combinate();
		}
	}
	
	public ArrayList<Integer[]> getCompliteArr() {
		return set;
	}
	
	public void combinate() {
		int start = 0;
		int end = (M-N)+1;
		int index = 0;
		next(start, end, index);
	}
	
	private void next(int start, int end, int index) {
		if(end == M+1) {
			set.add(Arrays.copyOf(arr, arr.length));
			return;
		}
		for(int i=start; i<end; i++) {
			arr[index] = i;
			next(i+1, end+1, index+1);
		}
	}
	
	public static int max(Integer[] arr) {
		int maximum = 0;
		for(Integer i : arr)
			if(i>maximum)
				maximum = i;
		return maximum;
	}
	
	public static int min(Integer[] arr) {
		int minimum = max(arr);
		for(Integer i : arr)
			if(minimum > i)
				minimum = i;
		return minimum;
	}
	
	public static int count(Object[] arr, Object c) {
		int count = 0;
		for(Object i : arr)
			if(i == c)
				count++;
		return count;
		
	}
	
	public static int factorial(int i) {
		if(i == 1)
			return i;
		int result = i;
		result *= factorial(--i);
		return result;
	}
	
	private int N;
	private int M;
	private ArrayList<Integer[]> set = new ArrayList<Integer[]>();
	private Integer[] arr;
}
