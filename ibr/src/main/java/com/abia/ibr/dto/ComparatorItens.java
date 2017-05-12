package com.abia.ibr.dto;

public class ComparatorItens implements Comparable<ItemPorDataFilter> {
	 private int compareIntArrays (int[] a1, int[] a2) {
	        int minLength = Math.min (a1.length, a2.length);
	        for (int i = 0; i < minLength; ++i) {
	            if (a1[i] < a2[i])
	                return -1;
	            if (a1[i] > a2[i])
	                return +1;
	        }
	        if (a1.length < a2.length)
	            return -1;
	        if (a1.length > a2.length)
	            return +1;
	        return 0;
	    }

	

	@Override
	public int compareTo(ItemPorDataFilter o) {
		String[] str2 = o.getReferencia().split ("\\.");
		String[] str1 = o.getReferencia().split ("\\.");
		int[] int1 = new int[str1.length];
		int[] int2 = new int[str2.length];
		for (int i = 0; i < int1.length; ++i) { int1[i] = Integer.parseInt (str1[i]); }
		for (int i = 0; i < int2.length; ++i) { int2[i] = Integer.parseInt (str2[i]); }
		return compareIntArrays (int1, int2);
	}

}
