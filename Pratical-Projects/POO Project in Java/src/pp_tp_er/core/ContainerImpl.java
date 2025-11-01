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
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;

/**
 * An implementation class that implements the provided {@link pp_tp_er.core.ContainerImpl Container}
 * interface.
 *
 * @author 8230068
 * @author 8230069
 * @file ContainerImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @since Java SDK 13
 */
public class ContainerImpl implements Container {

    /**
     * {@link #INITIAL_MEASUREMENT Initial measurement} of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    private static final int INITIAL_MEASUREMENT = 1;

    /**
     * {@link #totalMeasurements Amount} of {@link #measurements measurements} of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    private int totalMeasurements;

    /**
     * {@link #capacity Capacity} (in Kg) of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    private double capacity;

    /**
     * {@link #code Code} of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    private final String code;

    /**
     * {@link pp_tp_er.core.ContainerTypeImpl ContainerType} of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    ContainerType type;

    /**
     * {@link pp_tp_er.core.MeasurementImpl Measurement} collection of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    Measurement[] measurements;

    /**
     * Constructor of {@link pp_tp_er.core.ContainerImpl Container}.
     * <p>
     * Constructs with {@link #code code}, a {@link #capacity capacity} and a {@link pp_tp_er.core.ContainerTypeImpl ContainerType}, a {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @param code     The {@link #code code} of a {@link pp_tp_er.core.ContainerImpl Container}
     * @param capacity The {@link #capacity capacity} of a {@link pp_tp_er.core.ContainerImpl Container}
     * @param type     The {@link pp_tp_er.core.ContainerTypeImpl ContainerType} of a {@link pp_tp_er.core.ContainerImpl Container}
     */
    public ContainerImpl(String code, double capacity, ContainerType type) {
        this.capacity = capacity;
        this.code = code;
        this.type = type;
        this.measurements = new Measurement[INITIAL_MEASUREMENT];
        this.totalMeasurements = 0;
    }

    /**
     * Copy constructor of a {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @param origContainer the orig {@link pp_tp_er.core.ContainerImpl Container}
     */
    public ContainerImpl(ContainerImpl origContainer) {
        this.capacity = origContainer.capacity;
        this.code = origContainer.code;
        this.type = origContainer.type;
        this.measurements = Arrays.copyOf(origContainer.measurements, origContainer.measurements.length);
        this.totalMeasurements = origContainer.totalMeasurements;
    }

    /**
     * Getter for the {@link #type type} {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @return The {@link #type type}
     * of a {@link pp_tp_er.core.ContainerImpl Container}.
     */
    @Override
    public ContainerType getType() {
        return this.type;
    }

    /**
     * Getter for the {@link #capacity capacity} (in Kg) of a {@link #capacity capacity}.
     *
     * @return The {@link #capacity capacity} of a {@link #capacity capacity}.
     */
    @Override
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Getter for the {@link #code code} of a {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @return The {@link #code code} of a {@link pp_tp_er.core.ContainerImpl Container}.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Getter for the deep copy of {@link pp_tp_er.core.MeasurementImpl Measurements}
     * collection of a {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @return The deep copy of {@link pp_tp_er.core.MeasurementImpl Measurements}
     * collection of a {@link pp_tp_er.core.ContainerImpl Container}.
     */
    @Override
    public Measurement[] getMeasurements() {
        Measurement[] copyMeasurements = new Measurement[this.totalMeasurements];
        for (int counter = 0; counter < this.totalMeasurements; counter++) {
            copyMeasurements[counter] = new MeasurementImpl((MeasurementImpl) this.measurements[counter]);
        }
        return copyMeasurements;
    }

    /**
     * Getter for the deep copy of {@link pp_tp_er.core.MeasurementImpl Measurements} collection of a {@link pp_tp_er.core.ContainerImpl Container} for a given {@link LocalDate}.
     *
     * @param localDate The given {@link LocalDate}
     * @return The deep copy of {@link pp_tp_er.core.MeasurementImpl Measurements} collection of a {@link pp_tp_er.core.ContainerImpl Container} for a given {@link LocalDate}
     */
    @Override
    public Measurement[] getMeasurements(LocalDate localDate) {
        int totalDateMeasurements = getTotalDateMeasurements(localDate);
        return getDateMeasurements(new Measurement[totalDateMeasurements], localDate);
    }

    /**
     * Adds a given {@link pp_tp_er.core.MeasurementImpl Measurements} to the {@link pp_tp_er.core.MeasurementImpl Measurements} collection of {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @param measurement The given {@link pp_tp_er.core.MeasurementImpl Measurements}.
     * @return <ul>
     * <li>True, if the given {@link pp_tp_er.core.MeasurementImpl Measurements} was added in {@link pp_tp_er.core.MeasurementImpl Measurements} collection.</li>
     * <li>False, if the {@link LocalDate date} of given {@link pp_tp_er.core.MeasurementImpl Measurements} is equal with an existing {@link pp_tp_er.core.MeasurementImpl Measurements}, but the value of both {@link pp_tp_er.core.MeasurementImpl Measurements} is equal.</li>
     * </ul>
     * @throws MeasurementException <ol>
     *                              <li>If the given {@link pp_tp_er.core.MeasurementImpl Measurements} is null.</li>
     *                              <li>If the value of given {@link pp_tp_er.core.MeasurementImpl Measurements} is less than 0.</li>
     *                              <li>If the {@link LocalDate date} of given {@link pp_tp_er.core.MeasurementImpl Measurements} is before the last existing {@link LocalDate date} of {@link pp_tp_er.core.MeasurementImpl Measurements}</li>
     *                              <li>If the date of given {@link pp_tp_er.core.MeasurementImpl Measurements} is equal with an existing {@link pp_tp_er.core.MeasurementImpl Measurements}, but the value of both {@link pp_tp_er.core.MeasurementImpl Measurements} is different.</li>
     *                              </ol>
     */
    @Override
    public boolean addMeasurement(Measurement measurement) throws MeasurementException {
        this.validateMeasurement(measurement);

        if (this.hasMeasurement(measurement)) {
            return false;
        }

        if (this.totalMeasurements == this.measurements.length) {
            this.measurements = Arrays.copyOf(this.measurements, 2 * this.measurements.length);
        }

        this.measurements[this.totalMeasurements++] = measurement;
        return true;
    }

    /**
     * Compares given {@link Object Object} with {@link Object this.Object}
     * of a {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @param obj The given {@link Object Object}
     * @return <ul>
     * <li><code>false</code>, if given {@link Object Object} is null.</li>
     * <li><code>true</code>, if given {@link Object Object} equals
     * {@link Object this.Object} of a {@link pp_tp_er.core.ContainerImpl Container}.</li>
     * <li><code>False</code>, if class of given {@link Object Object} is not equal
     * to class of {@link Object this.Object} of a {@link pp_tp_er.core.ContainerImpl Container}.</li>
     * <li><code>True</code>, if {@link #code code} of given {@link Object Object} equals
     * {@link Object this.Object} of a {@link pp_tp_er.core.ContainerImpl Container}.</li>
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

        final ContainerImpl other = (ContainerImpl) obj;
        return this.code.equals(other.code);
    }

    /**
     * Outputs the content of a {@link pp_tp_er.core.ContainerImpl Container} in {@code String}.
     *
     * @return The content inlined.
     */
    @Override
    public String toString() {
        return "Code: " + this.code + " Capacity: " + this.capacity + " Type: " + this.type;
    }


    /**
     * Gets the amount of {@link pp_tp_er.core.MeasurementImpl Measurement} of a
     * {@link pp_tp_er.core.ContainerImpl Container} with
     * a given {@link java.time.LocalDate LocalDate}
     *
     * @param localDate The given {@link java.time.LocalDate LocalDate}.
     * @return The amount of {@link pp_tp_er.core.MeasurementImpl Measurement} of that {@link java.time.LocalDate LocalDate}.
     */
    private int getTotalDateMeasurements(LocalDate localDate) {
        if (this.totalMeasurements == 0) {
            return 0;
        }
        int totalDateMeasurements = 0;
        Measurement[] copyMeasurements = getMeasurements();
        for (Measurement copyMeasurement : copyMeasurements) {
            if (localDate.isEqual(ChronoLocalDate.from(copyMeasurement.getDate()))) {
                totalDateMeasurements++;
            }
        }
        return totalDateMeasurements;
    }

    /**
     * Gets a collection of {@link pp_tp_er.core.MeasurementImpl Measurement} of a
     * {@link pp_tp_er.core.ContainerImpl Container} with a given {@link LocalDate LocalDate} and
     * a given collection of all {@link pp_tp_er.core.MeasurementImpl Measurement}.
     *
     * @param dateMeasurements The given collection of {@link pp_tp_er.core.MeasurementImpl Measurement}
     * @param localDate        The given {@link LocalDate LocalDate}
     * @return The collection of {@link pp_tp_er.core.MeasurementImpl Measurement} of that {@link LocalDate LocalDate}.
     * @see  LocalDate
     */
    private Measurement[] getDateMeasurements(Measurement[] dateMeasurements, LocalDate localDate) {
        Measurement[] copyMeasurements = getMeasurements();
        int index = 0;
        for (Measurement copyMeasurement : copyMeasurements) {
            if (localDate.isEqual(copyMeasurement.getDate().toLocalDate())) {
                dateMeasurements[index] = copyMeasurement;
                index++;
            }
        }
        return dateMeasurements;
    }

    /**
     * Verifies with given{@link pp_tp_er.core.MeasurementImpl Measurement} if it exists in a {@link pp_tp_er.core.ContainerImpl Container}.
     *
     * @param measurement The given {@link pp_tp_er.core.MeasurementImpl Measurement}
     * @return <ul>
     * <li>False, if given {@link pp_tp_er.core.MeasurementImpl Measurement} does not exist.</li>
     * <li>True, if given {@link pp_tp_er.core.MeasurementImpl Measurement} does exist.</li>
     * </ul>
     * @throws MeasurementException If, given {@link pp_tp_er.core.MeasurementImpl Measurement} exists but the value is different.
     */
    private boolean hasMeasurement(Measurement measurement) throws MeasurementException {
        if (this.totalMeasurements == 0) {
            return false;
        }
        Measurement[] copyMeasurements = this.measurements;
        for (Measurement copyMeasurement : copyMeasurements) {
            if (copyMeasurement.equals(measurement)) {
                if (copyMeasurement.getValue() != measurement.getValue()) {
                    throw new MeasurementException("GIVEN MEASUREMENT DATE EXISTS WITH DIFFERENT VALUES");
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if the given {@link pp_tp_er.core.MeasurementImpl Measurement} is valid or not.
     * @param measurement {@link pp_tp_er.core.MeasurementImpl Measurement} to validate
     * @throws MeasurementException
     * <ol>
     *    <li>If {@link pp_tp_er.core.MeasurementImpl Measurement} is null.</li>
     *    <li>If the {@link pp_tp_er.core.MeasurementImpl Measurement} is below zero.</li>
     *    <li>If the {@link pp_tp_er.core.MeasurementImpl Measurement} date is before the last existing {@link pp_tp_er.core.MeasurementImpl Measurement} date.</li>
     *     </ol>
     */
    private void validateMeasurement(Measurement measurement) throws MeasurementException {
        if (measurement == null) {
            throw new MeasurementException("GIVEN MEASUREMENT IS NULL");
        }
        if (measurement.getValue() < 0) {
            throw new MeasurementException("GIVEN MEASUREMENT VALUE IS BELOW ZERO");
        }
        if (this.totalMeasurements > 0 && measurement.getDate().isBefore(this.measurements[this.totalMeasurements - 1].getDate())) {
            throw new MeasurementException("GIVEN MEASUREMENT DATE IS BEFORE THE LAST EXISTING MEASUREMENT DATE");
        }
    }
}
