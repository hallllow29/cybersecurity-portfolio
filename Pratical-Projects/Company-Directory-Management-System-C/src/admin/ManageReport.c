/**
 * @file        ManageReport.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Manage reports for admin functions.
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/admin/ManageReport.h"
#include "../inc/Output.h"
#include "../inc/Input.h"
#include "../inc/Defines.h"
#include "../inc/MainMenu.h"
#include <stdio.h>
#include <stdlib.h>

// ------------------------ Function Definitions -------------------------------
Storage *manageReport(Storage *dataBase) {
    int option;
    do {
        manageReportDisplay();
        option = getValidatedIntWithMenu(0, 2, BACK_MENU);
        dataBase = manageReportFeature(dataBase, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);

    return dataBase;
}

Storage *manageReportFeature(Storage *dataBase, int option) {
    switch (option) {
        case topFiveCompanies:
            displayTopFiveCompanies(dataBase);
            break;
        case mostSearchedCompanies:
            displayMostSearchedCompanies(dataBase);
            break;
        case backMenu:
            puts(BACK);
            break;
        case exitProgram:
            puts(EXIT);
            saveData_FreeMemory(dataBase);
            exit(1);
        default:
            break;
    }
    return dataBase;
}

// ----------------------------- Top Five Companies ------------------------------------------

int compareCompaniesRating(const void *a, const void *b) {
    const Company *companyA = (const Company *) a;
    const Company *companyB = (const Company *) b;

    float avgRatingA = companyA->rating;
    float avgRatingB = companyB->rating;

    if (avgRatingA < avgRatingB) {
        return 1;
    } else if (avgRatingA > avgRatingB) {
        return -1;
    }
    return 0;
}

void displayTopFiveCompanies(Storage *companyList) {
    int businessIndex = 0;
    int index = 0;

    if (companyList == NULL || COMPANY_SIZE == 0) {
        puts(LIST_COMPANY_EMPTY);
        return;
    }

    qsort(COMPANY_PTR, COMPANY_SIZE, sizeof(Company), compareCompaniesRating);

    puts("Top 5 Companies by Rating:\n");

    int count = COMPANY_SIZE < 5 ? COMPANY_SIZE : 5;

    for (index = 0; index < count; ++index) {
        businessIndex = getIndexOfActivity(companyList, index);
        if (BUSINESS_CL_USED == 0) {
            continue;
        } else if (COMPANY_ACTIVE == 1) {
            printf("%d. %-20s - Rating: %.2f\n", index + 1, COMPANY_NAME, COMPANY_RATING);
        }
    }
}

// ----------------------------- Most Searched Companies ------------------------------------------

void displayMostSearchedCompanies(Storage *companyList) {
    int index = 0;
    int businessIndex = 0;

    if(companyListEmpty(companyList) == 0) {
        return;
    }

    puts("Most Searched Companies");
    // TODO: prompt
    puts("\t\tNAME\t\t - SEARCH COUNT");

    for (index = 0; index < COMPANY_SIZE; index++) {
        businessIndex = getIndexOfActivity(companyList, index);
        if (BUSINESS_CL_USED == 0) {
            continue;
        } else if (COMPANY_ACTIVE == 1) {
            printf("%-20s - %03d\n", COMPANY_NAME, COMPANY_SERCHS_TOTAL);
        }
    }
}

