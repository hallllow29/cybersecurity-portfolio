/*
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 */

package pp_tp_er.pickingManagement;

import com.estg.core.Institution;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import pp_tp_er.interfaces.RouteStrategy;

/**
 * An implementation class that implements the provided {@link com.estg.pickingManagement.RouteGenerator RouteGenerator}
 * interface.
 * Class responsible to generate a route for the vehicles of an institution to pick containers.
 *
 * @author 8230068
 * @author 8230069
 * @file RouteGeneratorImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.pickingManagement.RouteGenerator
 * @since Java SDK 13
 */
public class RouteGeneratorImpl implements RouteGenerator {
    private RouteStrategy strategy;

	/**
	 * Instantiates a new Route generator.
	 *
	 * @param strategy the strategy
	 */
	public RouteGeneratorImpl(RouteStrategy strategy) {
        this.strategy = strategy;
    }

	/**
	 * Sets strategy.
	 *
	 * @param strategy the strategy
	 */
	public void setStrategy(RouteStrategy strategy) {
        this.strategy = strategy;
    }

	/**
	 * Generate routes.
	 * @param institution The institution to generate the route.
	 * @return The generated routes.
	 */
    @Override
    public Route[] generateRoutes(Institution institution) {
        return strategy.generateRoutes(institution);
    }
}
