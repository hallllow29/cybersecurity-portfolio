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

package pp_tp_er.core;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import org.json.simple.parser.ParseException;
import java.util.Objects;

/**
 * An implementation class that implements the provided {@link com.estg.core.AidBox AidBox}
 * interface.
 * Class responsible for the Aid Box behavior.
 *
 * @author 8230068
 * @author 8230069
 * @file AidBoxImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.core.AidBox
 * @since Java SDK 13
 */
public class AidBoxImpl implements AidBox {

    /**
     * {@link #code} code of a {@link pp_tp_er.core.AidBoxImpl AidBox}.
     */
    private String code;

    /**
     * {@link #zone zone} of a {@link pp_tp_er.core.AidBoxImpl AidBox}.
     */
    private String zone;

    /**
     * {@link pp_tp_er.core.ContainerManager ContainerManager} of a  {@link pp_tp_er.core.AidBoxImpl AidBox}.
     */
    private ContainerManager containerManager;

    /**
     * {@link pp_tp_er.core.DistancesAndDurations} manager for AidBox.
     */
    private DistancesAndDurations distanceManager;




    /**
     * Constructor of a {@link AidBoxImpl}.
     *
     * @param containers the {@link pp_tp_er.core.ContainerImpl containers}
     * @param code       the {@link #code code}
     * @param zone       the {@link #zone zone}
     *
     * @throws ContainerException the {@link pp_tp_er.core.ContainerImpl Container} exception
     */
    public AidBoxImpl(Container[] containers, String code, String zone) throws ContainerException {
        this.code = code;
        this.zone = zone;
        this.containerManager = new ContainerManager();
        for (Container container : containers) {
            this.containerManager.addContainer(container);
        }

        //This will try to get all distances and create an object called distance manager so each aid box have all distances from this
        //aid box to all other aid boxes
        try {
            this.distanceManager = new DistancesAndDurations();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Copy constructor of a {@link pp_tp_er.core.AidBoxImpl AidBox}.
     *
     * @param origAidBox the orig {@link pp_tp_er.core.AidBoxImpl AidBox}
     *
     * @throws ContainerException the {@link pp_tp_er.core.ContainerImpl Container} exception
     */
    public AidBoxImpl(AidBoxImpl origAidBox) throws ContainerException {
        this.code = origAidBox.code;
        this.zone = origAidBox.zone;
        this.containerManager = new ContainerManager();
        for (Container container : origAidBox.containerManager.getContainers()) {
            containerManager.addContainer(container);
        }

        try {
            this.distanceManager = new DistancesAndDurations();
        } catch (ParseException e) {
            System.err.println("Error initializing distance manager for AidBox " + this.code + ": " + e.getMessage());
        }

    }







    /**
     * Getter for the {@link #code code} of a {@link AidBoxImpl AidBox}.
     *
     * @return The {@link #code code} of a {@link AidBoxImpl AidBox}.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Getter for the {@link #zone} of a {@link AidBoxImpl AidBox}.
     *
     * @return The {@link #zone} of a {@link AidBoxImpl AidBox}
     */
    @Override
    public String getZone() {
        return this.zone;
    }

    /**
     * Getter for the distance between this {@link AidBoxImpl AidBox} to the given {@link AidBoxImpl AidBox}
     *
     * @param aidBox The given {@link AidBoxImpl AidBox} to get the distance to
     * @return The total distance
     * @throws AidBoxException <ul>
     *                         <li>If the given {@link AidBoxImpl AidBox} is null.</li>
     *                         <li>If the distance was not founded</li>
     *                         </ul>
     */
    @Override
    public double getDistance(AidBox aidBox) throws AidBoxException {
        if (aidBox == null) {
            throw new AidBoxException("AIDBOX PROVIDED IS NULL");
        }

        if (distanceManager == null) {
            throw new AidBoxException("DISTANCE MANAGER IS NULL");
        }
        return this.distanceManager.getDistance(this.getCode(), aidBox.getCode());
    }

    /**
     * Getter for the distance between this {@link AidBoxImpl AidBox} to the base
     *
     * @return The total distance to the base
     *
     * @throws AidBoxException If the distance was not founded
     * */
    public double getDistanceToBase() throws AidBoxException {
        if (distanceManager == null) {
            throw new AidBoxException("DISTANCE MANAGER IS NULL");

        }
        return this.distanceManager.getDistance(this.getCode(), "Base");
    }

    /**
     * The duration between this {@link AidBoxImpl AidBox} and the given {@link AidBoxImpl AidBox}
     *
     * @param aidBox The given {@link AidBoxImpl AidBox} to get the duration to
     * @return The total duration
     * @throws AidBoxException <ul>
     *                         <li>If the given {@link AidBoxImpl AidBox} is null.</li>
     *                         <li>If the distance was not founded</li>
     *                         </il>
     */
    @Override
    public double getDuration(AidBox aidBox) throws AidBoxException {
        if (aidBox == null) {
            throw new AidBoxException("AIDBOX PROVIDED IS NULL");
        }
        if (distanceManager == null) {
            throw new AidBoxException("DISTANCE MANAGER IS NULL");

        }
        return this.distanceManager.getDuration(this.getCode(), aidBox.getCode());
    }

    /**
     * Getter for the copy of {@link pp_tp_er.core.ContainerImpl Container} collection of a {@link AidBoxImpl AidBox}.
     *
     * @return The copy of {@link pp_tp_er.core.ContainerImpl Container} collection of a {@link AidBoxImpl AidBox}.
     */
    @Override
    public Container[] getContainers() {
        return containerManager.getContainers();
    }

    /**
     * Removes a given {@link pp_tp_er.core.ContainerImpl Container} from the collection of {@link pp_tp_er.core.ContainerImpl Container} of {@link AidBoxImpl AidBox}..
     *
     * @param container The given {@link pp_tp_er.core.ContainerImpl Container}..
     * @throws AidBoxException <ol>
     *                         <li>If the given {@link pp_tp_er.core.ContainerImpl Container} is null.</li>
     *                         <li>If the given {@link pp_tp_er.core.ContainerImpl Container} does not exist in the collection of {@link pp_tp_er.core.ContainerImpl Container}.</li>
     *                         <li>If there are no {@link pp_tp_er.core.ContainerImpl Container} left to remove.</li>
     *                         </ol>
     */
    @Override
    public void removeContainer(Container container) throws AidBoxException {
        try {
            containerManager.removeContainer(container);
        } catch (AidBoxException e) {
            throw new AidBoxException(e.getMessage());
        }
    }

    /**
     * Get the {@link pp_tp_er.core.ContainerImpl Container} based on a {@link pp_tp_er.core.ContainerTypeImpl ContainerType}
     *
     * @param containerType The given {@link pp_tp_er.core.ContainerTypeImpl ContainerType}
     * @return <ol>
     * <li>Container, if given {@link pp_tp_er.core.ContainerTypeImpl ContainerType} is on the {@link pp_tp_er.core.ContainerImpl Container} collection.</li>
     * <li>Null, if given {@link pp_tp_er.core.ContainerTypeImpl ContainerType} was not on the {@link pp_tp_er.core.ContainerImpl Container} collection</li>
     * </ol>
     */
    @Override
    public Container getContainer(ContainerType containerType) {
        return containerManager.getContainer(containerType);
    }

    /**
     * Adds a given {@link pp_tp_er.core.ContainerImpl Container} to the {@link pp_tp_er.core.ContainerImpl Container} collection of a {@link AidBoxImpl}.
     *
     * @param container The given {@link pp_tp_er.core.ContainerImpl Container}.
     * @return <ul>
     * <li>True, if the given {@link pp_tp_er.core.ContainerImpl Container} was added to {@link pp_tp_er.core.ContainerImpl Container} collection.</li>
     * <li>False, if the given {@link pp_tp_er.core.ContainerImpl Container} is equal with an existing {@link pp_tp_er.core.ContainerImpl Container}.</li>
     * </ul>
     * @throws ContainerException <ul>
     *                            <li>If the given {@link pp_tp_er.core.ContainerImpl Container} is null.</li>
     *                            <li>If the type of given {@link pp_tp_er.core.ContainerImpl Container} is equal with an existing {@link pp_tp_er.core.ContainerTypeImpl ContainerType} of a {@link AidBoxImpl}.</li>
     *                            </ul>
     */
    @Override
    public boolean addContainer(Container container) throws ContainerException {
        containerManager.addContainer(container);
        return true;
    }


    /**
     * Compares given {@link Object Object} with {@link Object this.Object}
     * of a {@link AidBoxImpl}
     *
     * @param obj The given {@link Object Object}
     * @return <ul>
     * <li><code>false</code>, if given {@link Object Object} is null.</li>
     * <li><code>true</code>, if given {@link Object Object} equals
     * {@link Object this.Object} of a {@link AidBoxImpl}.</li>
     * <li><code>False</code>, if class of given {@link Object Object} is not equal
     * to class of {@link Object this.Object} of a {@link AidBoxImpl}.</li>
     * <li><code>True</code>, if {@link #code code} of given {@link Object Object} equals
     * {@link Object this.Object} of a {@link AidBoxImpl}.</li>
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

        final AidBoxImpl other = (AidBoxImpl) obj;
        return Objects.equals(this.code, other.code);
    }


    /**
     * Outputs the content of a {@link AidBoxImpl} in a <code>String</code>.
     *
     * @return The content inlined.
     */
    @Override
    public String toString() {
        return "Code: " + this.code + " Zone: " + this.zone;
    }

    /**
     * Get the duration required to travel between this aid box to the base {@link pp_tp_er.core.InstitutionImpl Institution}.
     *
     * @return The duration required to travel between this {@link AidBoxImpl} to the base {@link pp_tp_er.core.InstitutionImpl (Institution)}.
     *
     * @throws AidBoxException If the DistanceManager is null or not initialized.
     */
    public double getDurationToBase() throws AidBoxException {
        if (distanceManager == null) {
            throw new AidBoxException("DISTANCE MANAGER IS NULL");
        }
        return this.distanceManager.getDuration(this.getCode(), "Base");
    }

}
