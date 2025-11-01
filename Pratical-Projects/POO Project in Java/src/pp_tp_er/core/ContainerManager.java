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

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;

import java.util.Arrays;

/**
 * An class that implements the management of  {@link pp_tp_er.core.ContainerImpl Container}
 * as well of {@link pp_tp_er.core.ContainerTypeImpl ContainerType}.
 *
 * @author 8230068
 * @author 8230069
 * @version 1.6.9
 * @file ContainerManager.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @since Java SDK 13
 */
public class ContainerManager {
    /**
     * Initial size for the containers array
     */
    private static final int INIT_SIZE = 10;

    /**
     * Expand factor to expand containers array size
     */
    private static final int EXPAND_FACTOR = 1;

    private Container[] containers = new Container[INIT_SIZE];

    /**
     * Amount of containers of a {@link com.estg.core.AidBox AidBox}.
     */
    private int totalContainers;

	/**
	 * Instantiates a new Container manager.
	 */
	public ContainerManager() {
        this.containers = new Container[INIT_SIZE];
        this.totalContainers = 0;
    }

	/**
	 * Adds a given Container to the Container collection of a AidBox.
	 *
	 * @param container The given Container.
	 *
	 * @return <ul> <li> {@code True}, if the given Container was added to {@link pp_tp_er.core.ContainerImpl Container} collection.</li>
	 * <li>{@code False}, if the given {@link pp_tp_er.core.ContainerImpl Container} is equal with an existing {@link pp_tp_er.core.ContainerImpl Container}.</li> </ul>
	 *
	 * @throws ContainerException <ol>                            <li>If the given Container is null.</li>                            <li>If the type of given {@link pp_tp_er.core.ContainerImpl Container} is equal with an existing {@link pp_tp_er.core.ContainerTypeImpl type} of {@link pp_tp_er.core.ContainerImpl Container} of a AidBox.</li>                            </ol>
	 */
	public boolean addContainer(Container container) throws ContainerException {
        if (container == null) {
            throw new ContainerException("GIVEN CONTAINER IS NULL");
        }

        if (this.getContainer(container.getType()) != null) {
            throw new ContainerException("GIVEN CONTAINER TYPE EXISTS");
        }

        if (this.containsContainer(container)) {
            return false;
        }

        if (this.totalContainers == this.containers.length) {
            this.expandArraySize();
        }

        this.containers[this.totalContainers++] = container;
        return true;
    }

	/**
	 * Removes a given Container from the collection of containers of AidBox.
	 *
	 * @param container The given Container.
	 *
	 * @throws AidBoxException <ol>                         <li>If the given Container is null.</li>                         <li>If the given Container does not exist in the collection of containers.</li>                         <li>If there are no containers left to remove.</li>                         </ol>
	 */
	public void removeContainer(Container container) throws AidBoxException {
        if (container == null) {
            throw new AidBoxException("GIVEN CONTAINER IS NULL");
        }

        if (this.totalContainers == 0) {
            throw new AidBoxException("NO CONTAINER LEFT TO REMOVE");
        }

        if (!this.containsContainer(container)) {
            throw new AidBoxException("GIVEN CONTAINER DOES NOT EXIST");
        }

        int indexContainer = this.getIndexContainer(container);

        if (indexContainer != -1) {
            this.containers[indexContainer] = null;
            this.shiftContainers(indexContainer);
            this.totalContainers--;
        }
        this.containers = this.getContainers();
    }

	/**
	 * Get the {@link pp_tp_er.core.ContainerImpl Container} based on a {@link pp_tp_er.core.ContainerTypeImpl ContainerType}
	 *
	 * @param containerType The type of {@link pp_tp_er.core.ContainerImpl Container}
	 *
	 * @return <ol> <li>Container, if given ContainerType was on the Container collection.</li> <li>Null, if given ContainerType was not on the Container collection</li> </ol>
	 */
	public Container getContainer(ContainerType containerType) {
        if (containerType == null) {
            return null;
        }
        for (int counter = 0; counter < this.totalContainers; counter++) {
            if (this.containers[counter].getType().equals(containerType)) {
                return this.containers[counter];
            }
        }
        return null;
    }

    /**
     * Getter of the index for a given container.
     *
     * @param container The container to get the index of.
     * @return <ol>
     * <li>Index, if the container was found on the Container collection</li>
     * <li>-1, if the container was not found on the Container Collection</li>
     * </ol>
     */
    private int getIndexContainer(Container container) {
        for (int index = 0; index < this.totalContainers; index++) {
            if (this.containers[index].equals(container)) {
                return index;
            }
        }
        return -1;
    }

	/**
	 * Get containers container [ ].
	 *
	 * @return the container [ ]
	 */
	public Container[] getContainers() {
        return Arrays.copyOf(containers, totalContainers);
    }

    /**
     * Expands the array size.
     */
    private void expandArraySize() {
        this.containers = Arrays.copyOf(this.containers, this.containers.length * EXPAND_FACTOR);
    }

	/**
	 * Contains container boolean.
	 *
	 * @param container the container
	 *
	 * @return the boolean
	 */
	public boolean containsContainer(Container container) {
        return getIndexContainer(container) != -1;
    }

	/**
	 * Getter of the total number of containers.
	 *
	 * @return Int The total number of containers.
	 */
	public int getTotalContainers() {
        return totalContainers;
    }

    /**
     * Shifts the containers array to the left, starting from the specified index.
     *
     * @param index The index from which to start shifting containers to the left.
     */
    private void shiftContainers(int index) {
        for (int i = index; i < this.totalContainers - 1; i++) {
            this.containers[i] = this.containers[i + 1];
        }
        this.containers[this.totalContainers - 1] = null;
    }




}
