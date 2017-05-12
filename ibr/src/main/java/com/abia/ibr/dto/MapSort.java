package com.abia.ibr.dto;

import java.util.Comparator;

public class MapSort implements Comparator<String> {

    @Override
    public int compare(String v1, String v2) {
        String[] a1 = v1.split("\\.");
        String[] a2 = v2.split("\\.");
        int min = 0;
        int max = 0;
        if (a1.length <= a2.length) {
            min = a1.length;
            max = a2.length;
        } else {
            min = a2.length;
            max = a1.length;
        }
        int[] first = new int[max];
        int[] second = new int[max];
        for (int i = 0; i < a1.length; i++) {
            first[i] = Integer.parseInt(a1[i]);
        }
        for (int i = 0; i < a2.length; i++) {
            second[i] = Integer.parseInt(a2[i]);
        }

        for (int i = 0; i < min; i++) {
            if (first[i] != second[i]) {
                return first[i] > second[i] ? 1 : -1;
            }
        }
        return 1;
    }
    }
