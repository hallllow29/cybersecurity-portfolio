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

import com.estg.core.Measurement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A implementation class that implements the provided {@link Measurement Measurement}
 * interface.
 * The class responsible for a {@link pp_tp_er.core.MeasurementImpl Measurement}.
 *
 * @author 8230068
 * @author 8230069
 * @file MeasurementImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see com.estg.core.Measurement
 * @since Java SDK 13
 */
public class MeasurementImpl implements Measurement {

    /**
     * {@link #date Date} of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     */
    private final LocalDateTime date;

    /**
     * {@code Value} (in Kg) of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     */
    private final double value;
    /**
     * {@link #containerCode ContainerCode} of a Container of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     */
    private String containerCode;


    /**
     * Constructor responsible for the creation of a {@link  pp_tp_er.core.MeasurementImpl MeasurementImpl}
     *
     * @param containerCode The given {@code containerCode} of a {@link #MeasurementImpl(String, String, double) Measurement}
     * @param isoString     The given date {@code date} of a {@link #MeasurementImpl(String, String, double) Measurement}
     * @param value         The given {@code value} of a {@link #MeasurementImpl(String, String, double) Measurement}
     */
    public MeasurementImpl(String containerCode, String isoString, double value) {
        this.containerCode = containerCode;
        this.date = parseDate(isoString);
        this.value = value;
    }

    /**
     * Copy constructor of a {@link MeasurementImpl Measurement}
     * <p>
     *
     * @param origMeasurement The given {@link MeasurementImpl Measurement}
     *
     * @see MeasurementImpl
     */
    public MeasurementImpl(MeasurementImpl origMeasurement) {
        this.containerCode = origMeasurement.containerCode;
        this.date = origMeasurement.date;
        this.value = origMeasurement.value;

    }

    /**
     * Getter for the {@link #date date} of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     *
     * @return The {@link #date date} of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     * @see LocalDateTime
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Getter for the {@link #containerCode containerCode} of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     *
     * @return The {@link #containerCode containerCode} of a {@link pp_tp_er.core.MeasurementImpl Measurement}
     */
    public String getContainerCode() {
        return this.containerCode;
    }

    /**
     * Getter for the {@link #value value} (in Kg) of a {@link #MeasurementImpl(String, String, double) Measurement}
     *
     * @return The {@link #value value} (in Kg) of a {@link #MeasurementImpl(String, String, double) Measurement}
     * @see com.estg.core.Measurement
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Compares given {@link Object Object} with {@link Object this.Object}
     * of a {@link #MeasurementImpl(String, String, double) Measurement}
     *
     * @param obj The given <code>Object</code>
     * @return <ul>
     * <li><code>false</code>, if given <code>Object</code> is null.</li>
     * <li><code>true</code>, if given <code>Object</code> equals
     * <code>this Object</code> of a {@link #MeasurementImpl(String, String, double) Measurement}.</li>
     * <li><code>False</code>, if class of given <code>Object</code> is not equal
     * to class of <code>this Object</code> of a {@link #MeasurementImpl(String, String, double) Measurement}.</li>
     * <li><code>True</code>, if date of given <code>Object</code> equals
     * <code>this Object</code> of a {@link #MeasurementImpl(String, String, double) Measurement}.</li>
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

        final MeasurementImpl other = (MeasurementImpl) obj;
        return this.date.equals(other.date);

    }


    /**
     * Outputs the content of a {@link MeasurementImpl Measurement} in a {@link String}.
     *
     * @return The content inlined.
     */
    @Override
    public String toString() {
        return "Code:" + this.containerCode + "\t\tDate: " + this.date + " \t\tValue: " + this.value;
    }

    /**
     * Converts given ISO-8601 {@link String} to {@link java.time.LocalDateTime LocalDateTime}.
     * <p>
     *
     *
     * @param isoString The given ISO-8601 {@link String}.
     * @return The {@link java.time.LocalDateTime LocalDateTime}.
     * @see LocalDateTime
     */
    private LocalDateTime parseDate(String isoString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(isoString, formatter);
    }

}
