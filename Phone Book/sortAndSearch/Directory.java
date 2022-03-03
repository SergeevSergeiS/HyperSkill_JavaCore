package phonebook.sortAndSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Directory {
    static final String path = "D:\\";
    static final String phoneBookName = "directory.txt";
    static final String findName = "find.txt";

    List<Map.Entry<String, Integer>> phoneBookList;
    final Map<String, Integer> phoneBook;
    final Stack<String> find;

    public Directory() {
        File phoneBookFile = new File(path + File.separator + phoneBookName);
        File findFile = new File(path + File.separator + findName);

        String line;
        phoneBook = new HashMap<>();
        find = new Stack<>();

        try {
            FileReader fileReader = new FileReader(phoneBookFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int phoneNumber;
            String name;

            while ((line = bufferedReader.readLine()) != null) {
                phoneNumber = Integer.parseInt(line.substring(0, line.indexOf(" ")).trim());
                name = line.substring(line.indexOf(" ") + 1).trim();

                phoneBook.put(name, phoneNumber);
            }

            bufferedReader.close();
            fileReader.close();

            fileReader = new FileReader(findFile);
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                find.push(line);
            }

            bufferedReader.close();
            fileReader.close();

            phoneBookList = new ArrayList<>(phoneBook.entrySet());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
