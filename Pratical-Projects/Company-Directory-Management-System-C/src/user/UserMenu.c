/**
 * @file        UserMenu.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       ...
 * @date
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/struct/CompanyStructure.h"
#include "../../inc/user/UserMenu.h"
#include "../../inc/user/SubmitSearch.h"
#include "../../inc/user/SubmitComment.h"
#include "../../inc/user/SubmitRating.h"
#include "../inc/Output.h"
#include "../inc/Input.h"
#include "../inc/Defines.h"
#include <stdio.h>
#include <stdlib.h>
#include "../../inc/MainMenu.h"
// ------------------------ Function Definitions -------------------------------
Storage *userMenu(Storage *dataBase) {
    int option;

    do {
        userMenuDisplay();
        option = getValidatedIntWithMenu(0, 3, BACK_MENU);
        dataBase = userMenuFeature(dataBase, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);
    return dataBase;
}

Storage *userMenuFeature(Storage *dataBase, int option) {
    switch (option) {
        case 1:
            submitSearch(dataBase);
            break;
        case 2:
            dataBase = submitRating(dataBase);
            break;
        case 3:
            dataBase = submitComment(dataBase);
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

