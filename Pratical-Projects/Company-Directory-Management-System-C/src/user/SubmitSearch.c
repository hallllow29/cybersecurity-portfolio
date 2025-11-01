/**
 * @file        SubmitSearch.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Submit search for user functions
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/struct/CompanyStructure.h"
#include "../../inc/user/SubmitSearch.h"
#include "../../inc/Input.h"
#include "../inc/Output.h"
#include "../inc/Defines.h"
#include <stdio.h>
#include <stdlib.h>
#include "../inc/MainMenu.h"
// ------------------------ Function Definitions -------------------------------
void submitSearch(Storage *dataBase) {
    int option = 0;

    if (companyListEmpty(dataBase) == 0) {
        return;
    }

    userCompanyListDisplay(dataBase);

    do {
        submitSearchDisplay();
        option = getValidatedIntWithMenu(0, 3, BACK_MENU);
        dataBase = submitSearchFeature(dataBase, option);

        if (option == 9) {
            puts(BACK);
            option = 0;
        }
    } while (option != 0);

}

Storage *submitSearchFeature(Storage *companyList, int option) {
    int index = 0;

    switch (option) {
        case searchCompanyByNif:
            searchCompanyDATA(companyList, COMPANY_NIF, NIF_INSERT, FOR_USER);
            break;
        case searchCompanyByName:
            searchCompanyDATA(companyList, COMPANY_NAME, NAME_INSERT, FOR_USER);
            break;
        case searchCompanyLocal:
            searchCompanyDATA(companyList, COMPANY_LOCAL, LOCAL_INSERT, FOR_USER);
            break;
        case backMenu:
            puts(BACK);
            break;
        case exitProgram:
            puts(EXIT);
            saveData_FreeMemory(companyList);
            exit(0);
        default:
            break;
    }
    return companyList;
}