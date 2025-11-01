/**
 * @file        AdminMenu.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Admin menu functions
 * @date        27/11/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/struct/CompanyStructure.h"
#include "../inc/MainMenu.h"
#include "../../inc/admin/AdminMenu.h"
#include "../../inc/admin/ManageCatalog.h"
#include "../../inc/admin/ManageBusiness.h"
#include "../../inc/admin/ManageReport.h"
#include "../inc/Output.h"
#include "../inc/Input.h"
#include "../inc/Defines.h"
#include <stdlib.h>
#include <stdio.h>
// ------------------------ Function Definitions -------------------------------
Storage *adminMenu(Storage *dataBase) {
    int option = 0;
    do {
        adminMenuDisplay();
        option = getValidatedIntWithMenu(0, 3, BACK_MENU);
        dataBase = adminMenuFeature(dataBase, option);

        if (option == 9) {
            option = 0;
        }

    } while (option != 0);
    return dataBase;
}

Storage *adminMenuFeature(Storage *dataBase, int option) {
    switch (option) {
        case manageCatalogCompanies:
            dataBase = manageCatalog(dataBase);
            break;
        case manageCatalogBusinesses:
            dataBase = manageBusiness(dataBase);
            break;
        case manageCatalogReports:
            dataBase = manageReport(dataBase);
            break;
        case backMenu:
            puts(BACK);
            break;
        case exitProgram:
            puts(EXIT);
            saveData_FreeMemory(dataBase);
            exit(0);
        default:
            break;
    }
    return dataBase;
}