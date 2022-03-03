package phonebook.sortAndSearch;

import java.util.ArrayList;
import java.util.Map;

public class JumpingBubble extends Directory {
    public int[] jumpSearch() {
        int entriesCount = find.size();
        int entriesFound = 0;

        String currentEntry;
        int inc = (int) Math.sqrt(phoneBookList.size());
        int cyclesCount = phoneBookList.size() / inc;
        int compare;

        for (int i = 0; i < entriesCount; i++) {
            currentEntry = find.pop();
            for (int j = inc; j < phoneBookList.size(); j = j + inc) {
                compare = currentEntry.compareTo(phoneBookList.get(j).getKey());

                if (compare == 0) {
                    entriesFound++;
                    break;
                } else if (compare < 0) {
                    for (int k = 1; k < inc; k++) {
                        compare = currentEntry.compareTo(phoneBookList.get(j - k).getKey());
                        if (compare == 0) {
                            entriesFound++;
                            break;
                        }
                    }
                    break;
                }
            }
            for (int k = 1; k < phoneBookList.size() % inc; k++) {
                compare = currentEntry.compareTo(phoneBookList.get(phoneBookList.size() - k).getKey());
                if (compare == 0) {
                    entriesFound++;
                    break;
                }
            }
        }
        return new int[]{entriesCount, entriesFound};
    }

    public boolean bubbleSort(long maxSearchTime) {

        long startTime = System.currentTimeMillis();
        long checkTime;

        boolean sorted = false;
        int length = phoneBookList.size();
        int compare;
        Map.Entry<String, Integer> swap;

        while (!sorted) {
            sorted = true;

            for (int i = 0; i < length - 1; i++) {
                compare = phoneBookList.get(i).getKey().compareTo(phoneBookList.get(i + 1).getKey());

                if (compare > 0) {
                    swap = phoneBookList.get(i);
                    phoneBookList.set(i, phoneBookList.get(i + 1));
                    phoneBookList.set(i + 1, swap);
                    sorted = false;
                }
            }
            length--;

            checkTime = System.currentTimeMillis();

            if (checkTime - startTime > maxSearchTime) {
                return false;
            }
        }
        return true;
    }
}
