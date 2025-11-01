import entities.*;
import entities.enums.BackPackSize;
import game.modes.AutomaticMode;
import entities.Mission;
import game.briefings.Report;
import game.modes.Simulation;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.graphs.CustomNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

	private Mission mission;
	private Player player;
	private Report report;
	private CustomNetwork<Room> battlefield;
	private Room room1, room2, room3, entryPoint, extractionPoint, extractionPoint2, targetRoom;
	private Simulation simulation;
	private Iterator<Room> entryPoints;
	private Iterator<Room> extractionPoints;

	@BeforeEach
	public void gameSetup() throws NotElementComparableException {
		battlefield = new CustomNetwork<>();
		mission = new Mission("Mission001", 1, battlefield);
		BackPack mochila = new BackPack(BackPackSize.LARGE.getCapacity());
		player = new Player("Tó Cruz", 100, mochila);
		report = new Report("Teste", player, mission);

		// Criar as salas
		room1 = new Room("Room 1");
		room2 = new Room("Room 2");
		room3 = new Room("Room 3");
		targetRoom = new Room("Target room");
		entryPoint = new Room ("Entry room");
		extractionPoint = new Room("Extraction Point");
		extractionPoint2 = new Room("Extraction room_2");

		// Adicionar salas ao mapa
		battlefield.addVertex(room1);
		battlefield.addVertex(room2);
		battlefield.addVertex(room3);
		battlefield.addVertex(targetRoom);

		// Adicionar conexoes ao mapa
		battlefield.addEdge(room1, room2, 1);
		battlefield.addEdge(room2, targetRoom, 1);
		battlefield.addEdge(targetRoom, extractionPoint, 1);

		// Definir o target e o entry point
		mission.setTarget(new Target(targetRoom, "Professor Ricardo"));
        mission.setEntryExitPoint(extractionPoint);
		// mission.setEntryExitPoint(entryPoint);
		// mission.setEntryExitPoint(extractionPoint);

		// O teste estava sempre a dar failed = null, porque eu nao segui uma boa
		// pratica, também nao daria sem entryPoins obtidos..
		// Nao se devia testar agora... devia-se de dar refactoring
		// e testar CADA vez que se altera algo.
		// Com o refactoring seguimos as boas praticas extras.

		// Podemos criar uma SimulationTestDemo.java...
		// Ou seguimos aqui o exemplo...

		// Por exemplo, o metodo testMove()...
		// Definir uma posicao ficiticia para o test testMove().
		player.setPosition(room1);

		// Podes por exemplo implementar um metodo de teste. Que indica se o player
		// moveu-se do entryPoint.
		// testPlayerMovedFromEntryPoint()     Há dezenas de tests que podemos efetuar.
		// Mas sem refactoring, nao é lá muito satisfatório.

		// Outro exemplo, testExtractionSelection()
		// tu queres chamar um metodo de simulation.
		// mas aqui simulation = null, pois nao está instanciado.
		// logo se chamas simulation... vais chamar a interface MAS
		/*simulation = new Simulation() {
			@Override
			public void movePlayer() throws ElementNotFoundException, EmptyCollectionException {

			}

			@Override
			public void game() throws ElementNotFoundException, EmptyCollectionException {

			}
		}*/

		// se chamares a subclass man...
		simulation = new AutomaticMode(mission, player, report);
		// Vai dar fail porque o metodo la dentro simulation.getBestExtractionPoint
		// Chama um Iterator de ExtractionPoints.
		// O que falta neste é exatamente dois unicos EntryExitPoint de Room para teste.
		// Só que eles também tem que se adicionados.
		mission.setEntryExitPoint(entryPoint);
		mission.setEntryExitPoint(extractionPoint);
		mission.setEntryExitPoint(extractionPoint2);

		battlefield.addVertex(entryPoint);
		battlefield.addVertex(extractionPoint);
		battlefield.addVertex(extractionPoint2);

		// Se executares nao vai dar fail, mas sim uma chamada "diferenca"
		// Nao dá fail pois nao ha nulls, MAS estas vertices adicionadas faltam algo...
		// O que será?
		battlefield.addEdge(room3, entryPoint, 4);
        battlefield.addEdge(entryPoint, room1, 5);
		battlefield.addEdge(extractionPoint2, room2,6);
		// Melhor maneira é pegar num papel desenhar o grafo visualmente
		// depois aqui adicionasse as vertices edges á battlefield.

	}

	@Test
	public void testSetupEntryPoints() throws EmptyCollectionException {
		assertFalse(mission.getEntryExitPoints().isEmpty(), "Os pontos de entrada/saída devem estar configurados!");
		Room firstEntry = this.mission.getEntryExitPoints().first();
		assertEquals("Entry room", firstEntry.getName());
	}

	@Test
	public void testMove() throws ElementNotFoundException {
		Room currentRoom = player.getPosition();
		Room targetRoom = mission.getTarget().getRoom();

		Iterator<Room> path = this.battlefield.iteratorShortestPath(currentRoom, targetRoom);

		assertTrue(path.hasNext(), "O caminho deveria de existir!");
		assertEquals(currentRoom, path.next(), "A primeira sala deve ser a posição atual do jogador");

		if (path.hasNext()) {
			Room nextRoom = path.next();
			player.setPosition(nextRoom);

			assertEquals(nextRoom, player.getPosition(), "O jogador deveria de estar na próxima sala");
			System.out.println("Jogador moveu-se para " + nextRoom.getName());
		} else {
			fail("Nenhuma sala próxima encontrada no caminho");
		}
	}

	@Test
	public void testReturnToExtraction() throws EmptyCollectionException, ElementNotFoundException {
		Room currentRoom = this.player.getPosition();
		Room extractionRoom = this.mission.getEntryExitPoints().first();

		Iterator<Room> path = this.battlefield.iteratorShortestPath(currentRoom, extractionRoom);

		assertTrue(path.hasNext(), "O caminho deveria de existir");
		assertEquals(currentRoom, path.next(), "A primeira sala deve ser a posição atual do jogador");

		if (path.hasNext()) {
			Room nextRoom = path.next();
			this.player.setPosition(nextRoom);

			assertEquals(nextRoom, this.player.getPosition(), "O jogador deveria de estar na proxima sala");
			System.out.println("Jogador moveu-se para " + nextRoom.getName());
		} else {
			fail("Nenuk caminho de volta para extraction");
		}
	}

	@Test
	public void testEnemiesMove() throws NotElementComparableException, ElementNotFoundException {
		Enemy enemy = new Enemy("Pedro", 10, room1);
		mission.setEnemy(enemy);

		Room currentRoom = enemy.getPosition();

		for (Room room : this.battlefield.getConnectedVertices(currentRoom)) {
			enemy.setPosition(room);
		}

		assertNotEquals("Room 1", enemy.getPosition().getName());

	}

	@Test
	public void testBackPack() throws EmptyCollectionException {
		BackPack mochila = new BackPack(BackPackSize.LARGE.getCapacity());

		MediKit kit1 = new MediKit("Small kit", null, 20);
		MediKit kit2 = new MediKit("Big kit", null, 50);
		mochila.addKit(kit1);
		mochila.addKit(kit2);

		assertEquals(2, mochila.numberOfKits());
		assertEquals(kit2, mochila.useKit());
		assertEquals(1, mochila.numberOfKits());

	}

	@Test
	public void testReport() {
		report.addRoom("Room 1");
		report.addRoom("Room 2");
		report.setMissionStatus("Accomplished");
		report.addEnemy("BadGuy");

		assertEquals("Accomplished", this.report.getMissionStatus());
		assertEquals(2, this.report.getTrajectoryToTarget().size());
		assertEquals(1, this.report.getEnemiesSurvived().size());

	}

	@Test
	public void testCalculatePathDamage() throws NotElementComparableException, ElementNotFoundException {
		Enemy pedro = new Enemy("Pedro", 10, room1);
		Enemy ruben = new Enemy("Ruben", 10, room2);
		mission.setEnemy(pedro);
		mission.setEnemy(ruben);
        room1.setEnemies(true);
        room2.setEnemies(true);
		double damage = simulation.calculatePathDamage(battlefield.iteratorShortestPath(room1, room2));
        System.out.println(damage);
		assertEquals(20, damage);

	}

	@Test
	public void testPlayerDie() {
		player.takesDamageFrom(100);
		assertFalse(player.isAlive());
	}

	@Test
	public void testExtractionPointSelection() throws ElementNotFoundException {
		Room bestExtractionPoint = simulation.bestExtractionPoint(targetRoom);
        System.out.println(bestExtractionPoint);
		assertEquals("Extraction Point", bestExtractionPoint.getName());

	}

}
