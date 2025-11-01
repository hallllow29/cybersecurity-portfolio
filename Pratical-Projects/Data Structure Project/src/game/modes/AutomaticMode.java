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
package game.modes;

import entities.*;
import game.io.Display;
import entities.Mission;
import game.briefings.Report;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * The AutomaticMode class represents a simulation mode where the game is
 * automatically executed without user interaction. The primary purpose of
 * this class is to handle automated decision-making, simulate player's movement,
 * and manage the flow of the game based on predefined rules and strategies.
 *
 * AutomaticMode extends the Simulation class and overrides core methods
 * to implement the specific logic needed for autonomous game operation.
 */
public class AutomaticMode extends Simulation {

    /**
     * Constructs a new AutomaticMode instance to simulate the gameplay autonomously.
     *
     * @param missionImpl the Mission object representing the mission details, including
     *                    its objectives, enemies, items, and the battlefield.
     * @param player      the Player object representing the player participating in the mission.
     * @param report      the Report object used to record events, player movements,
     *                    and outcomes during the simulation.
     */
    public AutomaticMode(Mission missionImpl, Player player, Report report) {
        super(missionImpl, player, report);
    }

    /**
     * Executes the main game logic for the automatic simulation mode.
     * This method initializes the simulation, displays the starting information,
     * updates the player's position, logs the entry point in the report,
     * and proceeds with the game flow.
     *
     * @throws ElementNotFoundException if a required simulation element, such as the entry point
     *                                  or target, cannot be located.
     * @throws EmptyCollectionException if the underlying collection used during game flow
     *                                  is found to be empty.
     */
    @Override
    public void game() throws ElementNotFoundException, EmptyCollectionException {

        renderAutomaticSimulation(this.getPlayer(), this.getMission().getTarget());

       String simulationStartInfo =  Display.playerStartsInEntryPoint(getPlayer().getName(), getMission().getCode(), getEntryPoint().getName());
       System.out.println(simulationStartInfo);


        this.getReport().addRoom(getPlayer().getPosition().getName());
        this.getReport().setEntryPoint(getPlayer().getPosition().getName());

        super.gameFlow();

    }

    /**
     * Moves the player to the next room in the simulation following the optimal path toward the current objective.
     * This method calculates the shortest path between the player's current position and the next objective,
     * updates the player's position, logs the movement, and checks the mission's state to determine
     * whether the game is over.
     *
     * @throws ElementNotFoundException if the necessary pathfinding elements, such as the player's position
     *                                  or the next objective room, are not found.
     */
    @Override
    public void movePlayer() throws ElementNotFoundException {
        StringBuilder movePlayerOutput = new StringBuilder();
        Room playerPosition = this.getPlayer().getPosition();
        Room nextObjective = this.getNextObjective();
        Iterator<Room> path;

        updateWeightsForEnemies();
        nextMissionStageInfo(isReturningToExit());
        this.displayPath(playerPosition, nextObjective);
        path = this.getBattleField().iteratorShortestPath(playerPosition, nextObjective);
        path.next();
        if (path.hasNext()) {
            Room nextPosition = path.next();

            movePlayerOutput.append("\n" + this.getPlayer().getName() + " plans to move from...").append
                (String.format("\n\t[%s] ---> [%s]\n", playerPosition.getName(), nextPosition.getName()));

            this.getPlayer().setPosition(nextPosition);
            super.addRoomToReport(nextPosition.getName());

        } else {
            if (isMissionAccomplished()) {
                this.setGameOver(true);
            }
        }
        System.out.print(movePlayerOutput);
    }
}
