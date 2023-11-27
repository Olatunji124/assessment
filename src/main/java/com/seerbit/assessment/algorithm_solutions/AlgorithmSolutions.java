package com.seerbit.assessment.algorithm_solutions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AlgorithmSolutions {

    public static void determineTwoIntegerEqualsGivenValue(int[] array, int sum) {
        HashSet<Integer> integerSet = new HashSet<>();
        for (int integer : array) {
            int temp = sum - integer;

            // check if the current value is present in the set
            if (integerSet.contains(temp)) {
                System.out.println("Yes, there are any two integers in the\n" +
                        "array whose sum is equal to the given value.");
                return;
            }
            integerSet.add(integer);
        }
        System.out.println("No, there are no two integers in the array whose sum is equal to the given value.");
    }

    public static void findLowAndHighIndex(int[] array, int key) {
        //convert the array to list
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        //use built-in function to find the first and last index of the element
        int lowIndex = list.indexOf(key);
        int highIndex = list.lastIndexOf(key);
        System.out.println("Low index: " + lowIndex);
        System.out.println("High index: " + highIndex);
    }

    public static void mergeOverlappingIntervals(ArrayList<Pair> pairs) {
        if (pairs == null || pairs.size() == 0) {
            return;
        }
        ArrayList<Pair> result = new ArrayList<>();

        result.add(new Pair(pairs.get(0).start, pairs.get(0).end));

        for (int i = 1; i < pairs.size(); i++) {
            int x1 = pairs.get(i).start;
            int y1 = pairs.get(i).end;
            int y2 = result.get(result.size() - 1).end;

            if (y2 >= x1) {
                result.get(result.size() - 1).end = Math.max(y1, y2);
            } else {
                result.add(new Pair(x1, y1));
            }
        }
        for (Pair pair : result) {
            System.out.println("[" + pair.start + "," + pair.end + "]");
        }
    }

    static class Pair {
        public int start;
        public int end;

        public Pair(int x, int y) {
            this.start = x;
            this.end = y;
        }
    }

    public static void main(String[] args) {

    }
}
