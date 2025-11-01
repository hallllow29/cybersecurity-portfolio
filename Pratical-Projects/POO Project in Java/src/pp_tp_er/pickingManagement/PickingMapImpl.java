/*
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 */
package pp_tp_er.pickingManagement;

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Route;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * An implementation class that implements the provided {@link com.estg.pickingManagement.PickingMap PickingMap}
 * interface.
 *
 * @author 8230068
 * @author 8230069
 * @file PickingMapImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.pickingManagement.PickingMap
 * @since Java SDK 13
 */
public class PickingMapImpl implements PickingMap {

	/**
	 * Amount of routes of PickingMap.
	 */
	private int totalRoutes;
	/**
	 * Date of a PickingMap
	 */
	private LocalDateTime date;

	/**
	 * Collection of Route of a PickingMap
	 */
	private Route[] routes;

	/**
	 * The constructor of a PickingMap object.
	 *
	 * @param date   The date of a PickingMap.
	 * @param routes The collections of Route of a PickingMap.
	 */
	public PickingMapImpl(LocalDateTime date, Route[] routes) {
		this.date = date;
		this.routes = Arrays.copyOf(routes, routes.length);
		this.totalRoutes = routes.length;
	}

	/**
	 * Copy construct of a PickingMap
	 *
	 * @param pickingMap The original PickingMap
	 */
	public PickingMapImpl(PickingMapImpl pickingMap) {
		this.date = pickingMap.date;
		this.routes = pickingMap.routes;
		this.totalRoutes = pickingMap.totalRoutes;
	}


	/**
	 * Getter for the LocalDateTime of a PickingMap
	 *
	 * @return The LocalDateTime.
	 */
	@Override
	public LocalDateTime getDate() {

		return this.date;
	}

	/**
	 * Getter for the collection of Route of a PickingMap.
	 *
	 * @return The collection of Route.
	 */
	@Override
	public Route[] getRoutes() {
		return this.routes;
	}

	/**
	 * String representation of a PickingMap.
	 * @return String representation of the picking map in the format "Picking map date [date] Available routes: [routes]"
	 */
	public String toString() {
		return "Picking map date: " + this.date + "\nAid boxes on the route\n" + this.routeToString();
	}

	/**
	 * Converts the list of routes to a string representation.
	 * @return A string representation of the routes. If there are no routes, returns "No routes".
	 */
	private String routeToString() {
		StringBuilder sb = new StringBuilder();

		if (this.totalRoutes == 0) {
			sb.append("No routes");
        } else {
            for (int i = 0; i < this.totalRoutes; i++) {
                sb.append("*").append(this.routes[i].toString());
            }
		}

		return sb.toString();
	}

}