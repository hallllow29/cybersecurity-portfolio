package Main;

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

import game.io.MainMenu;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {

			MainMenu.mainMenu();

		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro inesperado: " + e.getMessage());
		} catch (NotElementComparableException e) {
			throw new RuntimeException(e);
		}
	}

}

