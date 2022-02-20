package metro;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class Util {

    public static JsonObject readJsonData(String path) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found");
            return null;
        } catch (IOException e) {
            return null;
        }

        try {
            return new JsonParser().parse(sb.toString()).getAsJsonObject();
        } catch (JsonSyntaxException | IllegalStateException e) {
            return null;
        }

    }

}
