package projecttu.Gamelogic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import projecttu.Additional.Combinations;


public class PokerHands {
	
	public static ArrayList<String[]> bestHand(String[] hand) {
		Combinations comb = new Combinations(5, 7);
		ArrayList<Integer[]> indexes = comb.getCompliteArr();
		ArrayList<String[]> hands = new ArrayList<String[]>();
		String[] handArr = new String[5];
		
		for(Integer[] itr : indexes) {
			int j = 0;
			for(Integer i : itr)
				handArr[j++] = hand[i];
			hands.add(handArr.clone());
		}
		
		ArrayList<String[]> result = max(hands);
		System.out.println("\n\nResult hands");
		for(String[] str: result) {
			System.out.println();
			for(String s: str)
				System.out.print(s+" ");
		}
		
		return result;
	}
	
	public static ArrayList<Integer> handRank(String[] hand) {
		Integer[] ranks = cardRanks(hand);
		ArrayList<Integer> arr =
				new ArrayList<Integer>();
		
		if(straight(ranks) && flush(hand)) {
			arr.add(8);
			arr.add(Combinations.max(ranks));
	        return arr;
		}
	    else if(kind(4, ranks) != null) {
	    	arr.add(7);
	    	arr.add(kind(4, ranks));
	    	arr.add(kind(1, ranks));
	        return arr;
	    }
        else if(kind(3, ranks) != null && kind(2, ranks) != null) {
        	arr.add(6);
        	arr.add(kind(3, ranks));
        	arr.add(kind(2, ranks));
	        return arr;
        }
        else if (flush(hand)) {
        	arr.add(5);
        	arr.add(Combinations.max(ranks));
	        return arr;
        }
	    else if( straight(ranks)) {
	    	arr.add(4);
	    	arr.add(Combinations.max(ranks));
	        return arr;
	    }
	    else if(kind(3, ranks) != null) {
	    	arr.add(3);
	    	arr.add(kind(3, ranks));
	        return arr;
	    }
        else if(twoPair(ranks) != null) {
        	arr.add(2);
        	Integer[] twp = twoPair(ranks);
        	arr.add(twp[0]);
        	arr.add(twp[1]);
        	arr.add(kind(1, ranks));
	        return arr;
        }
        else if(kind(2, ranks) != null) {
        	arr.add(1);
        	arr.add(kind(2, ranks));
        	arr.add(Combinations.max(ranks));
	        return arr;
        }
	    else {
	    	arr.add(0);
	    	arr.add(Combinations.max(ranks));
	        return arr;
	    }
		
	}
	
	public static Integer[] cardRanks(String[] hand) {
		Integer[] ranks = new Integer[hand.length];
		int index = 0;
		for(String s : hand)
			ranks[index++] = "--23456789TJQKA".indexOf(
					Character.toString(s.charAt(0)));
		Arrays.sort(ranks, Collections.reverseOrder());
		if(ranks.equals(new Integer[]{14, 5, 4, 3, 2}))
			return new Integer[]{5, 4, 3, 2, 1};
		else
			return ranks;
	}
	
	public static boolean flush(String[] hand) {
		HashSet<Character> set = 
				new HashSet<Character>();
		for(String s : hand)
			set.add(s.charAt(1));
		if(set.size() == 1)
			return true;
		return false;
	}
	
	public static boolean straight(Integer[] ranks) {
		HashSet<Integer> set = 
				new HashSet<Integer>();
		for(Integer i : ranks)
			set.add(i);
		if(Combinations.max(ranks)-Combinations.min(ranks) == 4 && set.size() == 5)
			return true;
		return false;
	}
	
	public static Integer kind(int numCards, Integer[] ranks) {
		for(Integer i : ranks)
			if(Combinations.count(ranks, i) == numCards)
				return i;
		return null;
		
	}
	
	public static Integer[] twoPair(Integer[] ranks) {
		Integer pair = kind(2, ranks);
		Integer[] inverseRanks = ranks.clone();
		Arrays.sort(inverseRanks);
		Integer lowPair = kind(2, inverseRanks);
		Integer[] arr = new Integer[2];
		if(pair != null && lowPair !=
				null && lowPair != pair) {
			arr[0] = pair;
			arr[1] = lowPair;
			return arr;
		}
		return null;
		
	}
	
	public static ArrayList<String[]> allmax(HashMap<String, String[]> deal) {
		ArrayList<String[]> result = 
				new ArrayList<String[]>();
		int maxval = 0;
        ArrayList<Integer> max = 
        		new ArrayList<Integer>();
	    for(String name : deal.keySet()) {
//	        Integer xval = handRank(deal.get(name));
	        
	        ArrayList<Integer> arr = handRank(deal.get(name));

	        Integer xval = arr.get(0);
	        if(result.isEmpty() || xval > maxval) {
	        	result.clear();
	            result.add(deal.get(name));
	            max = (ArrayList<Integer>) arr.clone();
	            maxval = xval;
	        }
	        else if(maxval == xval) {
//	        	System.out.println("!!!!!!!!!!!LENGTH ARR "+arr.size());
//	        	System.out.println("!!!!!!!!!!!LENGTH MAX "+max.size());
	        	for(int i=1; i<arr.size(); i++) {
	        		if(arr.get(i) > max.get(i)) {
	        			result.clear();
	        			result.add(deal.get(name));
	        			max = (ArrayList<Integer>) arr.clone();
	        			break;
	        		}
	        		else if(arr.get(i) == max.get(i)) {
	        			if(i == arr.size()-1)
	        				result.add(deal.get(name));
	        			continue;
	        		}
	        		else break;
	        	}
	        }
	    }
	    return result;
	}
	
	public static ArrayList<String[]> max(ArrayList<String[]> deal) {
		ArrayList<String[]> result = 
				new ArrayList<String[]>();
		int maxval = 0;
        ArrayList<Integer> max = 
        		new ArrayList<Integer>();
	    for(String[] hand : deal) {        
	        ArrayList<Integer> arr = handRank(hand);
	        Integer xval = arr.get(0);
	        if(result.isEmpty() || xval > maxval) {
	        	result.clear();
	            result.add(hand);
	            max = (ArrayList<Integer>) arr.clone();
	            maxval = xval;
	        }
	        else if(maxval == xval) {
	        	for(int i=1; i<arr.size(); i++) {
	        		if(arr.get(i) > max.get(i)) {
	        			result.clear();
	        			result.add(hand);
	        			max = (ArrayList<Integer>) arr.clone();
	        			break;
	        		}
	        		else if(arr.get(i) == max.get(i)) {
	        			if(i == arr.size()-1)
	        				result.add(hand);
	        			continue;
	        		}
	        		else break;
	        	}
	        }
	    }
	    return result;
	}
}
