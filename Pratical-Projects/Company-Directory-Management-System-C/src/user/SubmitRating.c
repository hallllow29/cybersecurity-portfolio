/**
 * @file        SubmitRating.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Submit rating functions
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/user/SubmitRating.h"
#include "../../inc/user/SubmitSearch.h"
#include "../../inc/Input.h"
#include "../../inc/Output.h"
#include "../../inc/Defines.h"
#include "../../inc/MainMenu.h"
#include <stdio.h>
#include <stdlib.h>

// ------------------------ Function Definitions -------------------------------
Storage *submitRating(Storage *dataBase) {
    int option = 0;

    if (companyListEmpty(dataBase) == 0) {
        return dataBase;
    }

    do {
        submitRatingDisplay();
        option = getValidatedIntWithMenu(0, 2, BACK_MENU);
        dataBase = submitRatingFeature(dataBase, option);

        if (option == 9) {
            puts(BACK);
            option = 0;
        }
    } while (option != 0);

    return dataBase;
}

Storage *submitRatingFeature(Storage *dataBase, int option) {
    switch (option) {
        case searchRatingCompany:
            submitSearch(dataBase);
            dataBase = submitRatingCompany(dataBase);
            break;
        case listRatingCompanies:
            userCompanyListDisplay(dataBase);
            dataBase = submitRatingCompany(dataBase);
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

Storage *submitRatingCompany(Storage *companyList) {
    int index = 0;
    int option = 0;

    puts(BAR_LONG);

    do {
        puts(COMPA_SELECT);
        puts(SELECT_INDEX);
        index = getValidatedIntWithMenu(-1, (COMPANY_SIZE - 1), NO_BACK_MENU);

        if (index == -1) {
            puts(BACK);
            return companyList;
        }

    } while (index < 0 || index > (COMPANY_SIZE - 1));

    puts(BAR_LONG);
    puts(TITLE_C_RATING);
    getCompanyData(companyList, index);

    do {
        companyRatingDisplay(companyList, index);
        option = getValidatedIntWithMenu(0, 1, BACK_MENU);
        companyList = ratingFeature(companyList, index, option);

        if (option == 9) {
            option = 0;
        }

    } while (option != 0);

    return companyList;
}

Storage *ratingFeature(Storage *companyList, int index, int option) {
    switch (option) {
        case 1:
            setRATING(companyList, index);
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



