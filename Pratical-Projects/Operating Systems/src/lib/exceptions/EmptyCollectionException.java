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
 * The EmptyCollectionException is a custom exception class that is thrown to indicate
 * that a specific operation could not be performed because the custom used collection is empty.
 */
public class EmptyCollectionException extends Exception
{
	/**
	 * Constructs a new EmptyCollectionException with the specified detail message.
	 *
	 * @param message the detailed exception message indicating the nature or source of the empty collection error
	 */
	public EmptyCollectionException(String message) {
		super(message);
	}

}
