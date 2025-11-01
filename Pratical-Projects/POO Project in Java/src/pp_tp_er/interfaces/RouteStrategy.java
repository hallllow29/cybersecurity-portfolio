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
package pp_tp_er.interfaces;

import com.estg.core.Institution;
import com.estg.pickingManagement.Route;

/**
 * A interface that establishes a contract to {@link pp_tp_er.pickingManagement.AbstractRouteStrategy AbstractRouteStrategy}
 * {@link pp_tp_er.pickingManagement.PerishableRouteStrategy PerishableRouteStrategy} and
 * {@link pp_tp_er.pickingManagement.StandardRouteStrategy StandardRouteStrategy}
 *
 * @author 8230068
 * @author 8230069
 * @version 1.6.9
 * @file RouteStrategy.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @see pp_tp_er.pickingManagement.RouteGeneratorImpl
 * @since Java SDK 13
 */
public interface RouteStrategy {
    /**
     * Generates {@link pp_tp_er.pickingManagement.RouteImpl Routes} with give {@link pp_tp_er.core.InstitutionImpl Institution}.
     *
     * @param institution the given {@link pp_tp_er.core.InstitutionImpl Institution}
     *
     * @return Collection of generated {@link pp_tp_er.pickingManagement.RouteImpl Routes}
     */
    Route[] generateRoutes(Institution institution);
}
