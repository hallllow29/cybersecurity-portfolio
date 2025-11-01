/*
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 */

package pp_tp_er.core;

import com.estg.core.exceptions.AidBoxException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import util.JsonUtil;

/**
 * The class responsible for the Distances and Durations for the Aid Boxes.
 *
 * @author 8230068
 * @author 8230069
 * @version 1.0.5
 * @file DistancesAndDurations.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @since Java SDK 13
 */
public class DistancesAndDurations {


    /**
     * {@code Collection} of all stored all {@code distances} between the aid boxes and base.
     */
    private double[] distances;

    /**
     * {@code Collection} of all stored all {@code durations} between the aid boxes and base.
     */
    private double[] durations;

    /**
     * {@code Collection} of all stored all {@code "from" codes} of the aid boxes.
     */
    private String[] fromCodes;

    /**
     * {@code Collection} of all stored all {@code "to" codes} of the aid boxes.
     */
    private String[] toCodes;
    /**
     * Instantiates a new Distances and Durations.
     *
     * @throws ParseException If, for any reason, the {@code JSON data} is not well extracted or is corrupted.
     * @see <a href=https://www.json.org/json-en.html>JSON</a>
     */
    public DistancesAndDurations() throws ParseException {
        this.loadDistances();
    }

    /**
     * Gets {@code distance} between two aid boxes identified by their {@code codes}.
     *
     * @param from {@code code} to the starting location.
     * @param to   {@code code} of the destination location.
     *
     * @return {@code distance} between the two locations.
     *
     * @throws AidBoxException If no {@code distance} was found between the two locations.
     */
    public double getDistance(String from, String to) throws AidBoxException {
        for (int i = 0; i < this.fromCodes.length; i++) {
            if (this.fromCodes[i].equals(from) && this.toCodes[i].equals(to)) {
                return this.distances[i];
            }
        }
        throw new AidBoxException("DISTANCE NOT FOUND BETWEEN " + from + " AND " + to);
    }

    /**
     * Gets duration between two aid boxes identified by their codes.
     *
     * @param from {@code code} to the starting location.
     * @param to   {@code code} to the destination location.
     *
     * @return The {@code duration} between the two locations.
     *
     * @throws AidBoxException If no {@code duration} was found between the two locations.
     */
    public double getDuration(String from, String to) throws AidBoxException {
        for (int i = 0; i < this.fromCodes.length; i++) {
            if (fromCodes[i].equals(from) && toCodes[i].equals(to)) {
                return this.durations[i];
            }
        }
        throw new AidBoxException("DURATION NOT FOUND BETWEEN " + from + " AND " + to);
    }

    @Override
    /**
     * Prints all {@code distances} and {@code durations} between the aid boxes and base.
     * @return String The {@code distances} and {@code durations} between the aid boxes and base.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.fromCodes.length; i++) {
            s.append("From: ").append(this.fromCodes[i]).append(" To: ").append(this.toCodes[i]).append(" Distance: ").append(this.distances[i]).append(" Duration: ").append(this.durations[i]).append("\n");
        }
        return s.toString();
    }

    /**
     * Load {@code distances} and {@code durations} between the aid boxes and base.
     *
     * @throws ParseException If, for any reason, the {@code JSON data} is not well extracted or is corrupted.
     * @see <a href=https://www.json.org/json-en.html>JSON</a>
     */
    private void loadDistances() throws ParseException {
        JSONObject[] jsonDistances = JsonUtil.getJsonToDistance();
        int totalEntries = 0;

        // Count total entries
        for (JSONObject selected : jsonDistances) {
            JSONArray toArray = (JSONArray) selected.get("to");
            totalEntries += toArray.size();
        }

        // Initialize arrays
        fromCodes = new String[totalEntries];
        toCodes = new String[totalEntries];
        distances = new double[totalEntries];
        durations = new double[totalEntries];

        int index = 0;
        // Load data into arrays
        for (JSONObject selected : jsonDistances) {
            String from = (String) selected.get("from");
            JSONArray toArray = (JSONArray) selected.get("to");
            for (Object toObj : toArray) {
                JSONObject toJson = (JSONObject) toObj;
                fromCodes[index] = from;
                toCodes[index] = (String) toJson.get("name");
                distances[index] = ((Number) toJson.get("distance")).doubleValue();
                durations[index] = ((Number) toJson.get("duration")).doubleValue();
                index++;
            }
        }
    }

}
