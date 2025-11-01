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
package lib.exceptions;

/**
 * This exception is thrown to indicate that a specific element
 * could not be found during the execution of a custom collection.
 */
public class ElementNotFoundException extends Exception {

	/**
	 * Constructs a new ElementNotFoundException with a specified detail message.
	 *
	 * @param message the detail message providing additional context about the exception
	 */
	public ElementNotFoundException(String message) {
		super(message);
	}

}
