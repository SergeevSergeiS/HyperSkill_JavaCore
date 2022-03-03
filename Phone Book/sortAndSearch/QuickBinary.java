package phonebook.sortAndSearch;

import java.util.ArrayList;
import java.util.Map;

public class QuickBinary extends Directory {

    public int[] binarySearch() {
        int entriesCount = find.size();
        int entriesFound = 0;

        int currentStart;
        int currentEnd;
        int currentPointer;
        int pointerBackup;

        int compare;
        String currentEntry;
        for (int i = 0; i < entriesCount; i++) {
            currentEntry = find.pop();

            currentStart = 0;
            currentEnd = phoneBookList.size() - 1;
            currentPointer = (currentEnd + currentStart) / 2;
            pointerBackup = -1;

            while(true) {
                compare = currentEntry.compareTo(phoneBookList.get(currentPointer).getKey());

                if (currentPointer == pointerBackup) {
                    break;
                }

                pointerBackup = currentPointer;

                if (compare == 0) {
                    entriesFound++;
                    break;
                } else if (compare < 0) {
                    compare = currentEntry.compareTo(phoneBookList.get(currentStart).getKey());
                    if (compare == 0) {
                        entriesFound++;
                        break;
                    }
                    currentEnd = currentPointer - 1;
                    currentPointer = (currentStart + currentEnd) / 2;

                } else {
                    compare = currentEntry.compareTo(phoneBookList.get(currentEnd).getKey());
                    if (compare == 0) {
                        entriesFound++;
                        break;
                    }
                    currentStart = currentPointer + 1 ;
                    currentPointer = (currentStart + currentEnd) / 2;
                }
            }
        }

        return new int[]{entriesCount, entriesFound};
    }

    public void startQuickSort() {
        subQuickSort(0, 0, phoneBookList.size() - 1);
        //System.out.println(phoneBookList);
    }

    private void subQuickSort(int pivot, int start, int end) {
        if (start >= end) {
            return;
        }

        Map.Entry<String, Integer> tempElement = phoneBookList.get(pivot);
        phoneBookList.set(pivot, phoneBookList.get(start));
        phoneBookList.set(start, tempElement);

        int i = start + 1;
        int j = end;

        while (i <= j) {
            while (phoneBookList.get(i).getKey().compareTo(phoneBookList.get(start).getKey()) <= 0) {
                i++;
                if(i > j) {
                    break;
                }
            }
            while (phoneBookList.get(j).getKey().compareTo(phoneBookList.get(start).getKey()) >= 0) {
                j--;
                if(i > j) {
                    break;
                }
            }

            if (i > j) {
                break;
            }

            tempElement = phoneBookList.get(i);
            phoneBookList.set(i, phoneBookList.get(j));
            phoneBookList.set(j, tempElement);
        }

        tempElement = phoneBookList.get(start);
        phoneBookList.set(start, phoneBookList.get(j));
        phoneBookList.set(j, tempElement);

        subQuickSort(start, start, j - 1);
        subQuickSort(j + 1, j + 1, end);
    }

}
