package phonebook;

import phonebook.sortAndSearch.HashSearch;
import phonebook.sortAndSearch.JumpingBubble;
import phonebook.sortAndSearch.LinearSearch;
import phonebook.sortAndSearch.QuickBinary;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start searching (linear search)...");
        long linearSearchTime = linearSearch();

        System.out.println("\nStart searching (bubble sort + jump search)...");
        jumpingBubble(linearSearchTime * 10);

        System.out.println("\nStart searching (quick sort + binary search)...");
        quickBinary();

        System.out.println("\nStart searching (hash table)...");
        hashSearch();
    }

    private static long linearSearch() {

        LinearSearch linearSearch = new LinearSearch();

        long startTime = System.currentTimeMillis();

        int[] result = linearSearch.linearSearch();

        long endTime = System.currentTimeMillis();

        long ms = endTime - startTime;

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                result[1],
                result[0],
                getTime(ms));

        return endTime - startTime;
    }

    private static void jumpingBubble(long timeLimit) {
        JumpingBubble jumpingBubble = new JumpingBubble();
        boolean wasStopped = false;
        int[] result;

        long startFullTime = System.currentTimeMillis();

        long startBubbleTime = System.currentTimeMillis();
        wasStopped = !jumpingBubble.bubbleSort(timeLimit);
        long endBubbleTime = System.currentTimeMillis();

        long startSearchTime = System.currentTimeMillis();
        if (wasStopped) {
            LinearSearch linearSearch = new LinearSearch();
            result = linearSearch.linearSearch();
        } else {
            result = jumpingBubble.jumpSearch();
        }
        long endSearchTime = System.currentTimeMillis();

        long endFullTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                result[1],
                result[0],
                getTime(endFullTime - startFullTime));

        System.out.printf("Sorting time: %s\n",
                getTime(endBubbleTime - startBubbleTime));

        System.out.printf("Searching time: %s\n",
                getTime(endSearchTime - startSearchTime));
    }

    private static void quickBinary() {
        QuickBinary quickBinary = new QuickBinary();
        int[] result;

        long startFullTime = System.currentTimeMillis();

        long startSortTime = System.currentTimeMillis();
        quickBinary.startQuickSort();
        long endBubbleTime = System.currentTimeMillis();

        long startSearchTime = System.currentTimeMillis();
        result = quickBinary.binarySearch();
        long endSearchTime = System.currentTimeMillis();

        long endFullTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                result[1],
                result[0],
                getTime(endFullTime - startFullTime));

        System.out.printf("Sorting time: %s\n",
                getTime(endBubbleTime - startSortTime));

        System.out.printf("Searching time: %s\n",
                getTime(endSearchTime - startSearchTime));
    }

    private static void hashSearch() {
        HashSearch hashSearch = new HashSearch();
        int[] result;

        long startFullTime = System.currentTimeMillis();

        long startSortTime = System.currentTimeMillis();
        hashSearch.generateHashes();
        long endBubbleTime = System.currentTimeMillis();

        long startSearchTime = System.currentTimeMillis();
        result = hashSearch.search();
        long endSearchTime = System.currentTimeMillis();

        long endFullTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                result[1],
                result[0],
                getTime(endFullTime - startFullTime));

        System.out.printf("Creating time: %s\n",
                getTime(endBubbleTime - startSortTime));

        System.out.printf("Searching time: %s\n",
                getTime(endSearchTime - startSearchTime));
    }


    private static String getTime(long ms) {

        //System.out.println(ms);

        long sec = ms / 1000;
        ms = ms % 1000;
        long min = sec / 60;
        sec = sec % 60;

        return String.format("%d min. %d sec. %d ms.",
                min,
                sec,
                ms);
    }
}
