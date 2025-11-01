/**
 * @file        MainMenu.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Main menu
 * @date        27/11/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/struct/CompanyStructure.h"
#include "../inc/MainMenu.h"
#include "../inc/admin/AdminMenu.h"
#include "../inc/user/UserMenu.h"
#include "../inc/company/CompanyMenu.h"
#include "../inc/admin/ManageCatalog.h"
#include "../inc/admin/ManageBusiness.h"
#include "../inc/Output.h"
#include "../inc/Input.h"
#include "../inc/Defines.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// ------------------------ Function Definitions -------------------------------
int mainMenu() {

    int option = 0;

    Storage *dataBase = malloc(1 * sizeof(Storage));
    dataBase = initLoadDataBase(dataBase);

    do {
        mainMenuDisplay();
        option = getValidatedIntWithMenu(0, 3, EXIT_MENU);
        option = mainMenuFeature(dataBase, option);
    } while (option != 0);

    return option;
}

int mainMenuFeature(Storage *dataBase, int option) {
    switch (option) {
        case 1:
            adminMenu(dataBase);
            break;
        case 2:
            userMenu(dataBase);
            break;
        case 3:
            companyMenu(dataBase);
            break;
        case 0:
            puts(EXIT);
            saveData_FreeMemory(dataBase);
            break;
        default:
            break;
    }
    return option;
}

Storage *initLoadDataBase(Storage *dataBase) {

    COMPANY_DB_PTR=NULL;
    BUSINESS_DB_PTR=NULL;
    dataBase->companyCtr = 0;
    dataBase->businessCtr = 0;
    dataBase = catalogCompanyLoad(dataBase, COMPANY_DB_FILE);
    dataBase = catalogBusinessLoad(dataBase, BUSINESS_DB_FILE);
    return dataBase;
}

void saveData_FreeMemory(Storage *dataBase) {
    puts(COMPANY_DB_SAVE);
    puts(BUSINESS_DB_SAVE);
    sleep(1);
    dataBase = catalogCompanySave(dataBase, COMPANY_DB_FILE);
    dataBase = catalogBusinessSave(dataBase, BUSINESS_DB_FILE);
    freeCommentList(dataBase);
    free(COMPANY_DB_PTR);
    free(BUSINESS_DB_PTR);
    free(dataBase);


}

void freeCommentList(Storage *companyList) {
    int index = 0;
    for (index = 0; index < COMPANY_SIZE; index++) {
        if (COMPANY_COMMT_SIZE > 0) {
            free(COMMENT_PTR);
        }
    }
}