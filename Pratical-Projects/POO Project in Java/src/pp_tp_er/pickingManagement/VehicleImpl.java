/*
 * Nome: Ruben Xavier Ferreira Nunes
 * Número: 8230069
 * Turma: LSIRCT2
 *
 * Nome: Pedro Daniel Gonçalves Antunes
 * Número: 8230068
 * Turma: LSIRCT2
 *
 */
package pp_tp_er.pickingManagement;

import com.estg.core.ContainerType;
import com.estg.pickingManagement.Vehicle;
import pp_tp_er.core.Capacity;

/**
 * An implementation class that implements the provided {@link com.estg.pickingManagement.Vehicle Vehicle}
 * interface.
 * Class that implements the {@link com.estg.pickingManagement.Vehicle Vehicle} interface.
 *
 * @author 8230069
 * @author 8230068
 * @version 1.6.9
 * @file VehicleImpl.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @see com.estg.pickingManagement.Vehicle
 * @since Java SDK 13
 */
public class VehicleImpl implements Vehicle {

    /**
     * {@link #totalCapacities Amount} of {@link pp_tp_er.core.Capacity Capacities} of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     */
    private int totalCapacities;

    /**
     * {@link #code Code} that identifies the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     */
    private String code;

    /**
     * If the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} is {@link #onDuty on duty} or not.
     */
    private boolean onDuty;

    /**
     * {@link #capacities Collection} of {@link pp_tp_er.core.Capacity Capacities} of a {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}
     */
    Capacity[] capacities;

    /**
     * Constructor that receives the {@link #code code} and the {@link #capacities capacities} of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     *
     * @param code       The {@link #code code} that identifies the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     * @param capacities The capacities of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     */
    public VehicleImpl(String code, Capacity[] capacities) {
        this.code = code;
        this.capacities = new Capacity[capacities.length];
        System.arraycopy(capacities, 0, this.capacities, 0, capacities.length);
        this.totalCapacities = this.capacities.length;
        this.onDuty = true;
    }

    /**
     * Copy constructor for the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     *
     * @param vehicle The vehicle to be copied.
     */
    public VehicleImpl(VehicleImpl vehicle) {
        this.code = vehicle.code;
        this.capacities = new Capacity[vehicle.capacities.length];
        for (int i = 0; i < vehicle.capacities.length; i++) {
            this.capacities[i] = new Capacity(vehicle.capacities[i]);
        }
        this.totalCapacities = vehicle.totalCapacities;
        this.onDuty = vehicle.onDuty;
    }

    /**
     * Get the capacities of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} based on the container type.
     * @param containerType The container type to check the capacity for.
     * @return Double The capacity of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} for the specified type.
     */
    @Override
    public double getCapacity(ContainerType containerType) {
        //Get all the capacities of the vehicle and iterate for each one to check if the capacity have the same name of the container type
        //and if equals, return the total quantity for that specific capacity.
        for (Capacity capacity : capacities) {
            if (capacity.getName().equals(containerType.toString())) {
                return capacity.getQuantity();
            }
        }
        return 0;
    }

    /**
     * Get all capacities of the vehicle.
     *
     * @return Capacity[] Array of capacities of the vehicle.
     */
    public Capacity[] getCapacities() {
        Capacity[] copyCapacities = new Capacity[this.totalCapacities];
        for (int counter = 0; counter < this.totalCapacities; counter++) {
            copyCapacities[counter] = new Capacity(this.capacities[counter]);
        }
        return copyCapacities;
    }

    /**
     * Get the code of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     * @return String The code of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Get the stat of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     *
     * @return True, if the vehicle is on duty.         False, if the vehicle is not on duty.
     */
    public boolean getOnDuty() {
        return this.onDuty;
    }

    /**
     * Set the stat of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle}.
     *
     * @param onDuty True, if the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} is on duty.               Flase, if the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} is not on duty.
     */
    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    /**
     * Compare the code of the vehicle with the code of the vehicle passed as parameter.
     * @param obj The object to compare with.
     * @return True, if the code of the vehicle is equal to the code of the vehicle passed as parameter.
     *         False, if the code of the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} is not equal to the code of the vehicle passed as parameter.
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

        final VehicleImpl other = (VehicleImpl) obj;
        return this.code.equals(other.code);
    }


    /**
     * String representation of the vehicle.
     * @return String The string representation of the vehicle.
     */
    @Override
    public String toString() {
        return "Code: " + this.code + capacityToString() + "\n-----------------------";
    }


    /**
     * Check if the vehicle has the specified container type.
     *
     * @param containerType The container type to check.
     *
     * @return True, if the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} has the specified container type.         False, if the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} does not have the specified container type.
     */
    protected boolean hasContainerType(ContainerType containerType) {
        if (this.totalCapacities == 0) {
            return false;
        }
        for (Capacity capacity : capacities) {
            if (capacity.getName().equals(containerType.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the vehicle has the specified container type and if the quantity is greater than 0.
     *
     * @param containerType The container type to check.
     *
     * @return True, if the {@link pp_tp_er.pickingManagement.VehicleImpl Vehicle} has the specified container type and the quantity is greater than 0.         False, if the vehicle does not have the specified container type or the quantity is equal to 0.
     */
    protected boolean hasContainerTypeRoom(ContainerType containerType) {
        String nameContainerType = containerType.toString();
        for (Capacity capacity : capacities) {
            if (capacity.getName().equals(nameContainerType)) {
                if (capacity.getQuantity() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * String representation of the vehicle capacities.
     * @return String The string representation of the vehicle capacities.
     */
    private String capacityToString() {
        String s = "";

        if (this.totalCapacities <= 0) {
            return "Vehicle " + this.code + " does not have any Capacity";
        }
        for (int counter = 0; counter < this.totalCapacities; counter++) {
            s += this.capacities[counter].toString();
        }
        return s;
    }
}
