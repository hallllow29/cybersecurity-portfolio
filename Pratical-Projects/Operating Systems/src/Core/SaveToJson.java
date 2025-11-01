package Core;

import com.orsoncharts.util.json.JSONArray;
import com.orsoncharts.util.json.JSONObject;
import lib.lists.ArrayUnorderedList;

import java.io.FileWriter;

/**
 * The SaveToJson class provides functionality for serializing a collection
 * of Task objects to a JSON file.
 */
public class SaveToJson {

    /**
     * The name of the file used for saving and loading task data in JSON format.
     * This constant represents the filename which is used in file I/O operations
     * for storing serialized task information.
     */
    private static final String FILENAME = "data.json";

    /**
     * Serializes a list of Task objects into a JSON format and saves it to a file.
     *
     * @param tasks an ArrayUnorderedList of Task objects to be serialized and saved to a JSON file
     */
    public static void saveSystem(ArrayUnorderedList<Task> tasks) {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            JSONObject  taskObject = new JSONObject();
            taskObject.put("name", task.getName());
            taskObject.put("priority", task.getPriority().toString());
            taskObject.put("duration", task.getDuration());
            taskObject.put("memorySize", task.getMemorySize());
            jsonArray.add(taskObject);
        }

        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
