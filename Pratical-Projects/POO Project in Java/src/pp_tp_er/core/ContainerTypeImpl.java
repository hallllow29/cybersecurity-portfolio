/*
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 */

package pp_tp_er.core;

import com.estg.core.ContainerType;
import com.estg.io.HTTPProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * An implementation class that implements the provided {@link pp_tp_er.core.ContainerTypeImpl ContainerType}
 * interface.
 * Class responsible for the type of the {@link pp_tp_er.core.ContainerImpl Container}.
 *
 * @author 8230069, 8230068
 * @file ContainerTypeImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.core.ContainerType
 * @see com.estg.core.Container
 * @since Java SDK 13
 */
public class ContainerTypeImpl implements ContainerType {

	/**
	 * The {@link #name} of a {@link pp_tp_er.core.ContainerTypeImpl ContainerType}.
	 */
	private String name;

	/**
	 * {@link #types Collection} of {@link pp_tp_er.core.ContainerTypeImpl ContainerType}.
	 */
	private static ContainerType[] types;

	static {
		try {
			// Get the types from the API and store them in the array of types.
			updateTypesFromAPI();
		} catch (Exception e) {
			// If there's an error, print it and set the types to default values.
			System.err.println("Error initializing container types: " + e.getMessage());
			types = new ContainerType[]{
					new ContainerTypeImpl("clothing"),
					new ContainerTypeImpl("medicine"),
					new ContainerTypeImpl("perishable food"),
					new ContainerTypeImpl("non perishable food"),
					new ContainerTypeImpl("books")
			};
		}
	}

	/**
	 * Constructor of {@link ContainerTypeImpl ContainerType}
	 *
	 * @param name the {@link #name name} for a {@link ContainerTypeImpl ContainerType}
	 */
	public ContainerTypeImpl(String name) {
		this.name = name;
	}

	/**
	 * Gets all the container types.
	 *
	 * @return An collection of all {@link ContainerTypeImpl ContainerType}
	 */
	public static ContainerType[] getTypes() {
		return types.clone();
	}

	/**
	 * Compares given {@link Object Object} with {@link Object this.Object}
	 * of a {@link ContainerTypeImpl ContainerType}.
	 *
	 * @param obj The given {@link Object Object}
	 * @return <ul>
	 *     <li>{@code False}, if given {@link Object Object} is null.</li>
	 *     <li>{@code True}, if given {@link Object Object} equals
	 *     {@link Object this.Object} of a {@link pp_tp_er.core.ContainerImpl Container}.</li>
	 *     <li>{@code False}, if class of given {@link Object Object} is not equal
	 *     to class of {@link Object this.Object} of a {@link pp_tp_er.core.ContainerTypeImpl ContainerType}.</li>
	 *     <li>{@code True}, if {@link #name} of given {@link Object Object} equals
	 *     {@link Object this.Object} of a {@link pp_tp_er.core.ContainerTypeImpl ContainerType}.</li>
	 * </ul>
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html">Object</a>
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final ContainerTypeImpl other = (ContainerTypeImpl) obj;
		return this.name.equals(other.name);
	}

	/**
	 * Outputs the content of a {@link ContainerTypeImpl ContainerType} in a {@link String}.
	 *
	 * @return The content inlined.
	 */
	@Override
	public String toString() {
		return this.name;
	}


	/**
	 * Updates the container types from the API.
	 *
	 * @throws Exception if there's an issue with the API request.
	 */
	private static void updateTypesFromAPI() throws Exception {
		HTTPProvider httpProvider = new HTTPProvider();
		String containerTypeURL = "https://data.mongodb-api.com/app/data-docuz/endpoint/types";
		String containerTypeResponse = httpProvider.getFromURL(containerTypeURL);

		if (containerTypeResponse == null) {
			throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "GIVEN JSON IS NULL");
		}

		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = (JSONArray) jsonParser.parse(containerTypeResponse);

		if (jsonArray != null) {
			JSONObject containerType = (JSONObject) jsonArray.get(0);
			JSONArray containerTypeArray = (JSONArray) containerType.get("types");
			types = new ContainerType[containerTypeArray.size()];

			for (int counter = 0; counter < containerTypeArray.size(); counter++) {
				String typeName = (String) containerTypeArray.get(counter);
				types[counter] = new ContainerTypeImpl(typeName);
			}
		} else {
			throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION, "JSONARRAY IS NULL");
		}
	}
}
