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


package api;

import com.estg.core.*;
import com.estg.core.exceptions.*;
import com.estg.pickingManagement.*;
import org.json.simple.parser.ParseException;
import pp_tp_er.core.*;
import pp_tp_er.pickingManagement.PerishableRouteStrategy;
import pp_tp_er.pickingManagement.RouteGeneratorImpl;
import pp_tp_er.pickingManagement.StandardRouteStrategy;
import util.JsonUtil;
import util.SaveToJsonFile;
import pp_tp_er.pickingManagement.VehicleImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * An implementation class that implements an api containing all the needed methods for
 * a user interaction with a CLI/CMD (Terminal)
 *
 * @author 8230068
 * @author 8230069
 * @file NewMain.java
 * @copyright ESTG IPP
 * @brief PP, Trabalho Prático (Recurso)
 * @date 2024/23/06
 * @version 1.6.9
 * @since Java SDK 13
 */
public class NewMain {

    private static final Scanner input = new Scanner(System.in);
    private static Institution institution;
    private static RouteGenerator routeGenerator = new RouteGeneratorImpl(new StandardRouteStrategy());

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        try {
            institution = generateInstitution(scanner);
            while (!exit) {
                displayMenu();
                int option = getUserOption();
                exit = selectMenu(option);
            }

        } catch (ParseException | InstitutionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getUserOption() {
        return validateUserOption();
    }

    private static InstitutionImpl generateInstitution(Scanner scanner) throws InstitutionException, IOException {
        System.out.println("Enter the name of the institution:");
        String institutionName = scanner.nextLine();

        while (institutionName.isEmpty()) {
            System.out.println("The institution name cannot be empty. Try again.");
            institutionName = scanner.nextLine();
        }

        return new InstitutionImpl(institutionName);
    }

    // ------------------------- ROUTE MANAGEMENT ------------------------ //
    private static void routeManagementMenu() {
        boolean exit = false;
        while (!exit) {
            displayRouteManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    generateRoutesFeature();
                    break;
                case 2:
                    viewPickingMapsFeature();
                    break;
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void generateRoutesFeature() {
        System.out.println("==== GENERATOR ROUTES ====");
        System.out.println("[1] PRIORITY: STANDARD ROUTES");
        System.out.println("[2] PRIORITY: PERISHABLE ROUTES");
        System.out.println("Option: ");
        int option = getUserOption();

        switch (option) {
            case 1:
                ((RouteGeneratorImpl) routeGenerator).setStrategy(new StandardRouteStrategy());
                break;
            case 2:
                ((RouteGeneratorImpl) routeGenerator).setStrategy(new PerishableRouteStrategy());
                break;
            default:
                System.out.println("Option not found.");
                return;
        }

        Route[] routes = routeGenerator.generateRoutes(institution);
        for (Route route : routes) {
            if (route != null) {
                System.out.println(route);
            }
        }
    }

    private static void viewPickingMapsFeature() {
        PickingMap[] pickingMaps = institution.getPickingMaps();
        int counter = 0;
        for (PickingMap pickingMap : pickingMaps) {
            if (pickingMap != null) {
                counter++;
                System.out.println(pickingMap);
            }
        }
        if (counter == 0) {
            System.out.println("No picking maps found.");
        }
    }

    // ------------------------- CONTAINER MANAGEMENT ------------------------ //
    private static void containerManagementMenu() {
        boolean exit = false;
        while (!exit) {
            displayContainerManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    listContainersFeature();
                    break;
                case 2:
                    listMeasurementFeature();
                    break;
                case 3:
                    listEmptyContainersFeature();
                    break;
                case 4:
                    listDefectiveContainersFeature();
                    break;
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void listContainersFeature() {
        System.out.println("==== LIST CONTAINERS ====");
        for (Container container : getAllContainers(institution)) {
            System.out.println(container);
        }
    }

    private static void listEmptyContainersFeature() {
        System.out.println("=== LIST EMPTY CONTAINERS ===");
        for (Container container : ((InstitutionImpl) institution).getEmptyContainers()) {
            System.out.println(container);
        }
    }

    private static void listDefectiveContainersFeature() {
        System.out.println("=== LIST DEFECTIVE CONTAINERS ===");
        for (Container container : ((InstitutionImpl) institution).getDefectiveContainers()) {
            System.out.println(container);
        }
    }

    private static void listMeasurementFeature() {
        System.out.println("==== LIST MEASUREMENTS ====");
        System.out.println("[1] List Measurements");
        System.out.println("Option:");
        int option = validateUserOption();

        if (option == 1) {
            listMeasurements();
        } else {
            System.out.println("Option not found.");
        }
    }

    private static void listMeasurements() {
        System.out.println("==== LIST MEASUREMENTS ====");
        if (institution.getAidBoxes() == null || ((InstitutionImpl) institution).getTotalAidBoxes() == 0) {
            System.out.println("List of Aid Boxes is empty.");
            return;
        }

        System.out.println("Enter Aid Box Code: ");
        String code = input.nextLine();

        for (AidBox aidBox : institution.getAidBoxes()) {
            if (aidBox.getCode().equals(code)) {
                for (Container container : aidBox.getContainers()) {
                    for (Measurement measurement : container.getMeasurements()) {
                        System.out.println(measurement);
                    }
                }
            }
        }
    }

    // ------------------------- AID BOX MANAGEMENT ------------------------ //
    private static void aidBoxManagementMenu() {
        boolean exit = false;
        while (!exit) {
            displayAidBoxManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    listAidBoxesFeature();
                    break;
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void listAidBoxesFeature() {
        System.out.println("==== List Aid Boxes ====");
        if (institution.getAidBoxes() == null || ((InstitutionImpl) institution).getTotalAidBoxes() == 0) {
            System.out.println("List of Aid Boxes is empty.");
            return;
        }

        int index = 0;
        for (AidBox aidBox : institution.getAidBoxes()) {
            System.out.println("(" + (index + 1) + ")" + aidBox.toString());
        }
    }

    // ------------------------- VEHICLE MANAGEMENT ------------------------ //
    private static void vehicleManagementMenu() {
        boolean exit = false;
        while (!exit) {
            displayVehicleManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    listVehicleFeature();
                    break;
                case 2:
                    addVehicleFeature();
                    break;
                case 3:
                    removeVehicleFeature();
                    break;
                case 4:
                    changeStatusFeature();
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void addVehicleFeature() {
        try {
            Vehicle vehicle = generateVehicle();
        } catch (VehicleException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listVehicleFeature() {
        System.out.println("==== List Vehicles ====");
        if (institution.getVehicles() == null || ((InstitutionImpl) institution).getTotalVehicles() == 0) {
            System.out.println("List of Vehicles is empty.");
            return;
        }

        for (Vehicle vehicle : institution.getVehicles()) {
            System.out.println(vehicle.toString());
        }
    }

    private static Vehicle generateVehicle() throws VehicleException {
        System.out.println("==== Generate Vehicle ====");
        System.out.println("Enter Vehicle Code: ");
        String vehicleCode = input.nextLine();
        System.out.println("Enter Number of Capacities: ");
        int vehicleTotalCapacities = validateUserOption();

        Capacity[] vehicleCapacities = new Capacity[vehicleTotalCapacities];

        for (int counter = 0; counter < vehicleCapacities.length; counter++) {
            System.out.println("Enter Capacity " + (counter + 1) + ":");
            System.out.println("Container Type: ");
            String containerType = input.nextLine();
            System.out.println("Limit: ");
            int capacityLimit = validateUserOption();
            vehicleCapacities[counter] = new Capacity(new ContainerTypeImpl(containerType), capacityLimit);
        }

        Vehicle generatedVehicle = new VehicleImpl(vehicleCode, vehicleCapacities);
        institution.addVehicle(generatedVehicle);

        return generatedVehicle;
    }

    private static void removeVehicleFeature() {
        try {
            removeVehicle();
        } catch (VehicleException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void changeStatusFeature() {
        System.out.println("=== Change Status Vehicle ====");
        Vehicle[] listVehicles = institution.getVehicles();

        if (listVehicles == null || listVehicles.length == 0) {
            System.out.println("List of Vehicles is empty.");
            return;
        }

        int counter = 0;
        for (Vehicle vehicle : listVehicles) {
            if (vehicle != null) {
                System.out.println("(" + (counter + 1) + ") " + listVehicles[counter].toString());
                ++counter;
            }
        }

        System.out.println("Enter Vehicle Index: ");
        int index = input.nextInt();
        input.nextLine();  // Consume newline

        if (index < 1 || index > listVehicles.length || listVehicles[index - 1] == null) {
            System.out.println("Invalid vehicle index.");
            return;
        }

        Vehicle selectedVehicle = listVehicles[index - 1];
        boolean currentStatus = ((VehicleImpl) selectedVehicle).getOnDuty();

        System.out.println("Current status of vehicle " + selectedVehicle.getCode() + " is " + (currentStatus ? "on duty" : "off duty"));
        System.out.println("Do you want to change the status? (yes/no): ");
        String response = input.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            try {
                if (currentStatus) {
                    institution.disableVehicle(selectedVehicle);
                } else {
                    institution.enableVehicle(selectedVehicle);
                }
                System.out.println("Status changed successfully.");
            } catch (VehicleException e) {
                System.out.println("Error changing status: " + e.getMessage());
            }
        } else {
            System.out.println("No changes made.");
        }

    }

    private static void removeVehicle() throws VehicleException {
        System.out.println("==== Remove Vehicle ====");
        Vehicle[] listVehicles = institution.getVehicles();

        if (listVehicles == null || listVehicles.length == 0) {
            System.out.println("List of Vehicles is empty.");
            return;
        }

        int counter = 0;
        for (Vehicle vehicle : listVehicles) {
            if (vehicle != null) {
                System.out.println("(" + (counter + 1) + ") " + listVehicles[counter].toString());
                ++counter;
            }
        }

        System.out.println("Enter Vehicle Index: ");
        int index = input.nextInt();
        Vehicle removeVehicle = ((InstitutionImpl) institution).retrieveVehicle(index - 1);

        ((InstitutionImpl) institution).removeVehicle(removeVehicle);
        System.out.println("Vehicle removed successfully.");
    }


    // ------------------------- DATA MANAGEMENT ------------------------ //


    private static void saveDataFeature() {
        try {
            saveData();
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveData() throws IOException {
        System.out.println("==== SAVE DATA ====");
        SaveToJsonFile.saveJsonFile(institution);
    }

    private static void readingsManagementMenu() throws ParseException {
        boolean exit = false;
        while (!exit) {
            displayReadingsManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    listReadingsFeature();
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void listReadingsFeature() throws ParseException {
        System.out.println("==== List Readings ====");
        Measurement[] measurements = JsonUtil.getJsonToMeasurement();
        for (Measurement measurement : measurements) {
            System.out.println(measurement.toString());
        }
    }

    private static void typesManagementMenu() throws ParseException {
        boolean exit = false;
        while (!exit) {
            displayTypesManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    listTypesFeature();
                    break;
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void listTypesFeature() throws ParseException {
        System.out.println("==== List Types ====");
        ContainerType[] types = JsonUtil.getJsonToContainerType();
        for (ContainerType type : types) {
            System.out.println(type.toString());
        }
    }

    // ------------------------- REPORT MANAGEMENT ------------------------ //
    private static void reportManagementMenu() {
        boolean exit = false;
        while (!exit) {
            displayReportManagementMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    listReportsFeature();
                    break;
                case 9:
                    return;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option not found.");
            }
        }
    }

    private static void listReportsFeature() {
        PickingMap[] pickingMaps = institution.getPickingMaps();
        int counter = 0;

        for (PickingMap pickingMap : pickingMaps) {
            for (Route routes : pickingMap.getRoutes()) {
                Report report = routes.getReport();
                if (report != null) {
                    System.out.println("(" + (counter + 1) + ") " + report);
                    ++counter;
                }
            }
        }
    }

    private static Container[] getAllContainers(Institution institution) {
        AidBox[] aidBoxes = institution.getAidBoxes();
        Container[] allContainers = new Container[1000];
        int containersCounter = 0;

        for (AidBox box : aidBoxes) {
            for (Container container : box.getContainers()) {
                allContainers[containersCounter++] = container;
            }
        }

        return Arrays.copyOf(allContainers, containersCounter);
    }

    // ------------------------- MENU UTILITIES ------------------------ //
    private static boolean selectMenu(int option) throws ParseException {
        switch (option) {
            case 1:
                routeManagementMenu();
                break;
            case 2:
                aidBoxManagementMenu();
                break;
            case 3:
                containerManagementMenu();
                break;
            case 4:
                vehicleManagementMenu();
                break;
            case 5:
                saveDataFeature();
                break;
            case 6:
                reportManagementMenu();
                break;
            case 7:
                readingsManagementMenu();
            case 8:
                typesManagementMenu();
            case 9:
                return confirmExit();
            default:
                System.out.println("Invalid option.");
                break;
        }
        return false;
    }

    private static int validateUserOption() {
        int option = -1;
        try {
            option = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("SYMBOLS OR LETTERS ARE NOT VALID OPTIONS.");
            input.nextLine();
        }
        return option;
    }

    private static boolean confirmExit() {
        System.out.println("Are you sure you want to exit? (YES or NO)");
        String choice = input.nextLine().toLowerCase();

        if (choice.equals("yes")) {
            System.out.println("Exiting....");
            return true;
        } else if (choice.equals("no")) {
            System.out.println("Returning to menu....");
            return false;
        }

        System.out.println("Invalid option.");
        System.out.println("Returning to menu....");
        return false;
    }

    private static void displayMenu() {
        System.out.println("==== Institution " + institution.getName() + " menu ====");
        System.out.println("[1] Route Management");
        System.out.println("[2] Aid Box Management");
        System.out.println("[3] Container Management");
        System.out.println("[4] Vehicle Management");
        System.out.println("[5] Save Current Data");
        System.out.println("[6] Report Management");
        System.out.println("[7] Get the readings for today");
        System.out.println("[8] List of available types");
        System.out.println("[9] Exit");
        System.out.println("Option: ");
        System.out.println("=====================");
    }

    private static void displayRouteManagementMenu() {
        System.out.println("==== Route Management ====");
        System.out.println("[1] Generate Routes");
        System.out.println("[2] View Picking Maps");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("=====================");
    }

    private static void displayContainerManagementMenu() {
        System.out.println("==== Container Management ====");
        System.out.println("[1] List Containers");
        System.out.println("[2] List Measurements");
        System.out.println("[3] List Empty Containers");
        System.out.println("[4] List Defective Containers");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("=====================");
    }

    private static void displayAidBoxManagementMenu() {
        System.out.println("==== Aid Box Management ====");
        System.out.println("[1] List Aid Boxes");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("=====================");
    }

    private static void displayVehicleManagementMenu() {
        System.out.println("==== Vehicle Management ====");
        System.out.println("[1] List Vehicles");
        System.out.println("[2] Add Vehicle");
        System.out.println("[3] Remove Vehicle");
        System.out.println("[4] Change Vehicle Status");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("=====================");
    }

    private static void displayReportManagementMenu() {
        System.out.println("==== Report Management ====");
        System.out.println("[1] List Report");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("===========================");
    }

    private static void displayReadingsManagementMenu() {
        System.out.println("==== Readings Management ====");
        System.out.println("[1] List Readings");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("===========================");
    }

    private static void displayTypesManagementMenu() {
        System.out.println("==== Types Management ====");
        System.out.println("[1] List Types");
        System.out.println("[9] Back");
        System.out.println("Option: ");
        System.out.println("===========================");
    }
}
