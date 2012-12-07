package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import projecttu.Additional.Combinations;

public class TestCombinations {
	public TestCombinations() {
		testCombinationsPositiveNum();
	}
	
	@Test(expected= NegativeArraySizeException.class)
	public void testCombinationsPositiveNum() { 
		Combinations n1 = new Combinations(-1, 1);
		Combinations n2 = new Combinations(1, -1);
		Combinations n3 = new Combinations(0, 0);
		Combinations n4 = new Combinations(1, 1);
		
		fail();
//		
//		ArrayList<Integer[]> a1 = n1.getCompliteArr();
//		ArrayList<Integer[]> a2 = n2.getCompliteArr();
//		ArrayList<Integer[]> a3 = n3.getCompliteArr();
//		ArrayList<Integer[]> a4 = n4.getCompliteArr();
//		
//		Integer[] i1 = new Integer[1];
//		for(Integer[] i: a1)
//			assertArrayEquals(i, i1);
		
		
		
	}
	
}
