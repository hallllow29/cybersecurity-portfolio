/**
 * @file        CompanyMenu.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Company menu functions.
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/company/CompanyMenu.h"
#include "../../inc/company/AccessCompany.h"
#include "../../inc/company/AccessComment.h"
#include "../../inc/Input.h"
#include "../../inc/Defines.h"
#include "../../inc/MainMenu.h"
#include "../../inc/company/AccessReport.h"
#include <stdio.h>
#include <stdlib.h>
// ------------------------ Function Definitions -------------------------------
Storage *companyMenu(Storage *dataBase) {
    int index = 0;
    int option = 0;

    if (companyListEmpty(dataBase) == 0) {
        return dataBase;
    }

    index = companySignIn(dataBase);

    if (index == -1) {
        puts(NO_ACCESS_DATA);
        return dataBase;
    } else {
        do {
            companyMenuDisplay();
            option = getValidatedIntWithMenu(0, 4, BACK_MENU);

            dataBase = companyMenuFeature(dataBase, index, option);
            if (option == 9) {
                option = 0;
            }
        } while (option != 0);
    }
    return dataBase;
}

Storage *companyMenuFeature(Storage *dataBase, int index, int option) {
    switch (option) {
        case 1:
            dataBase = accessCompany(dataBase, index);
            break;
        case 2:
            dataBase = accessComment(dataBase, index);
            break;
        case 3:
            accessReport(dataBase, index);
            break;
        case 4:
            CompanySearchReport(dataBase, index);
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









