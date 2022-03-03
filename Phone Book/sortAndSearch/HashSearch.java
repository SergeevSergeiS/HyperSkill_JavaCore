package phonebook.sortAndSearch;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HashSearch extends Directory {

    private List<List<Map.Entry<String, Integer>>> hashTable;
    private int bucketsCount = 0;

    public void generateHashes() {
        bucketsCount = (int) Math.ceil(phoneBookList.size() / 0.75);
        hashTable = new ArrayList<>(Collections.nCopies(bucketsCount, null));

        int hash;
        for (Map.Entry<String, Integer> entry : phoneBookList) {
            hash = getHash(entry.getKey());
            if (hashTable.get(hash) == null) {
                hashTable.set(hash, new ArrayList<>());
            }
            hashTable.get(hash).add(entry);
        }
    }

    public int[] search() {
        int entriesCount = find.size();
        int entriesFound = 0;

        int hash;
        List<Map.Entry<String, Integer>> bucket;
        for (String name : find) {
            hash = getHash(name);
            bucket = hashTable.get(hash);

            for (Map.Entry<String, Integer> entry : bucket) {
                if (name.compareTo(entry.getKey()) == 0) {
                    entriesFound++;
                    break;
                }
            }
        }
        return new int[]{entriesCount, entriesFound};
    }

    private int getHash(String string) {
        byte[] values = string.getBytes(StandardCharsets.UTF_8);

        int hash = 0;

        for (byte value : values) {
            hash = (hash * 5 + value) % bucketsCount;
        }

        return hash;
    }

}
