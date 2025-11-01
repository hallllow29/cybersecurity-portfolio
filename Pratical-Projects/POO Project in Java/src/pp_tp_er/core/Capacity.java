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

import com.estg.core.ContainerType;

/**
 * The class responsible for the {@link pp_tp_er.core.Capacity Capacity}.
 *
 * @author 8230068
 * @author 8230069
 * @file Capacity.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @see pp_tp_er.pickingManagement.VehicleImpl
 * @since Java SDK 13
 */
public class Capacity {

	/**
	 * The {@link com.estg.core.ContainerType ContainerType} of a {@link pp_tp_er.core.Capacity Capacity}
	 */
	private ContainerType containerType;

	/**
	 * {@link #limit Limit} of a {@link  pp_tp_er.core.Capacity Capacity}
	 */
	private double limit;

	/**
	 * {@link #quantity Quantity} of a {@link pp_tp_er.core.Capacity Capacity}
	 */
	private double quantity;

	/**
	 * Constructor of a {@link pp_tp_er.core.Capacity Capacity}
	 * <p>
	 * Constructs with a {@link #containerType containerType} and
	 * a decimal {@link #quantity quantity} (in units), a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @param containerType The {@link com.estg.core.ContainerType ContainerType} of a {@link pp_tp_er.core.Capacity Capacity}
	 * @param quantity      The {@link #quantity quantity} (in units) of a {@link pp_tp_er.core.Capacity Capacity}
	 */
	public Capacity(ContainerType containerType, double quantity) {
		this.containerType = containerType;
		this.quantity = quantity;
		this.limit = quantity;
	}

	/**
	 * Copy constructor of a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @param capacity the capacity
	 */
	public Capacity(Capacity capacity) {
		this.containerType = capacity.containerType;
		this.quantity = capacity.quantity;
		this.limit = capacity.limit;
	}


	/**
	 * Getter for the {@link com.estg.core.ContainerType ContainerType} of a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @return the container type
	 */
	public ContainerType getContainerType() {
		return this.containerType;
	}

	/**
	 * Getter for the {@link #containerType name} of a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @return the {@link #containerType name} of a {@link pp_tp_er.core.ContainerImpl Container}
	 */
	public String getName() {
		return this.containerType.toString();
	}

	/**
	 * Getter for the {@link #limit limit} of a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @return The {@link #limit limit} of a {@link pp_tp_er.core.Capacity Capacity}
	 */
	public double getLimit() {
		return this.limit;
	}

	/**
	 * Getter for the {@link #quantity quantity} of a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @return The {@link #quantity quantity} of a {@link pp_tp_er.core.Capacity Capacity}
	 */
	public double getQuantity() {
		return this.quantity;
	}

	/**
	 * Sets a given {@link #quantity quantity} of a {@link pp_tp_er.core.Capacity Capacity}
	 *
	 * @param quantity The given {@link #quantity quantity} of a {@link pp_tp_er.core.Capacity Capacity}
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * Compares given {@link Object Object} with {@link Object this.Object}
	 * of a {@link pp_tp_er.core.Capacity Capacity}.
	 *
	 * @param obj The given {@link Object Object}
	 * @return <ul>
	 *     <li><code>false</code>, if given {@link Object Object} is null.</li>
	 *     <li><code>true</code>, if given {@link Object Object} equals
	 *     {@link Object this.Object} of a {@link pp_tp_er.core.Capacity Capacity}.</li>
	 *     <li><code>False</code>, if class of given {@link Object Object} is not equal
	 *     to class of {@link Object this.Object} of a {@link pp_tp_er.core.Capacity Capacity}.</li>
	 *     <li><code>True</code>, if {@link com.estg.core.ContainerType ContainerType} of given {@link Object Object} equals
	 *     {@link Object this.Object} of a {@link pp_tp_er.core.Capacity Capacity}.</li>
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

		final Capacity other = (Capacity) obj;
		return this.containerType.equals(other.containerType);
	}

	/**
	 * Outputs the content of a {@link pp_tp_er.core.Capacity Capacity} in a <code>String</code>.
	 *
	 * @return The content inlined.
	 */
	@Override
	public String toString() {
		return "\nType: " + this.containerType + " Capacity: " + this.quantity;
	}
}
