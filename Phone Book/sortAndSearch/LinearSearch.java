package phonebook.sortAndSearch;

import java.util.ArrayList;
import java.util.Map;

public class LinearSearch extends Directory {

    public int[] linearSearch() {
        int entriesCount = find.size();
        int entriesFound = 0;

        String currentEntry;
        for (int i = 0; i < entriesCount; i++) {
            currentEntry = find.pop();
            for (Map.Entry<String, Integer> integerStringEntry : phoneBookList) {
                if (currentEntry.equals(integerStringEntry.getKey())) {
                    entriesFound++;
                    break;
                }
            }
        }
        return new int[]{entriesCount, entriesFound};
    }
}
