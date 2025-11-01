/**
 * @file        MangeBusiness.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Manage business for admin functions
 * @date        21/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/admin/ManageBusiness.h"
#include "../../inc/Output.h"
#include "../../inc/Input.h"
#include "../../inc/Defines.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../inc/MainMenu.h"

// ------------------------ Function Definitions -------------------------------
Storage *manageBusiness(Storage *dataBase) {
    int option = 0;

    do {
        manageBusinessDisplay();
        option = getValidatedIntWithMenu(0, 4, BACK_MENU);
        dataBase = manageBusinessFeature(dataBase, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);

    return dataBase;
}

Storage *manageBusinessFeature(Storage *businessList, int option) {
    switch (option) {
        case addBusiness:
            businessList = catalogBusinessInsert(businessList);
            break;
        case listBusiness:
            catalogBusinessList(businessList);
            break;
        case editBusinessStatus:
            businessList = editBusinessData(businessList);
            break;
        case removeBusiness:
            businessList = selectBusinessToRemove(businessList);
            break;
        case backMenu:
            puts(BACK);
            break;
        case exitProgram:
            puts(EXIT);
            saveData_FreeMemory(businessList);
            exit(0);
        default:
            break;
    }
    return businessList;
}

Storage *catalogBusinessInsert(Storage *businessList) {
    businessList = newBusinessList(businessList, (BUSINESS_SIZE + 1));
    businessList = newBusiness(businessList, BUSINESS_SIZE);
    BUSINESS_SIZE++;
    return businessList;
}

Storage *newBusinessList(Storage *businessList, int size) {
    if (BUSINESS_PTR == NULL) {

        BUSINESS_PTR = NULL;
        BUSINESS_PTR = calloc(1, sizeof(Business));
        BUSINESS_SIZE = 0;

    } else if (businessList->businessPtr != NULL && size != 1) {

        BUSINESS_PTR = realloc(BUSINESS_PTR, size * sizeof(Business));

        if (BUSINESS_PTR == NULL) {
            puts(NO_MEMORY);
            return NULL;
        }
    }
    return businessList;
}

Storage *newBusiness(Storage *businessList, int businessIndex) {
    puts(BAR_LONG);
    puts(TITLE_BUSINESS_ADD);

    BUSINESS_NUMBER = businessIndex;
    setCompanyBUSINESS(businessList, BUSINESS_NAME, BUSI_INSERT);
    strcpy(BUSINESS_STATUS, "Inactive");

    BUSINESS_ACTIVE = 0;
    BUSINESS_USED = 0;

    return businessList;
}

void catalogBusinessList(Storage *businessList) {
    if(businessListEmpty(businessList) == 0){
        businessListDisplay(businessList);
    }
}

int businessSELECT(Storage *businessList) {
    int businessChoice = 0;

    if(businessListEmpty(businessList) == 1) {
        return -1;
    }

    businessListDisplay(businessList);

    do {
        puts(BUSI_SELECT);
        puts(SELECT_INDEX);
        businessChoice = getValidatedIntWithMenu(-1, (BUSINESS_SIZE - 1), NO_BACK_MENU);

    } while (businessChoice < -1 || businessChoice > (BUSINESS_SIZE - 1));

    return businessChoice;
}

//---------------------------------- REMOVE ---------------------------------------------
Storage *selectBusinessToRemove(Storage *businessList) {
    int index = 0;

    if (businessListEmpty(businessList) == 1) {
        return 0;
    }

    puts(BAR_LONG);

    do {
        puts(BUSI_SELECT);
        puts(SELECT_INDEX);
        index = getValidatedIntWithMenu(-1, (BUSINESS_SIZE - 1), NO_BACK_MENU);

        if (index == -1) {
            puts(BACK);
            return businessList;
        }

    } while (index < 0 || index > (BUSINESS_SIZE - 1));

    removeBusinessData(businessList, index);

    return businessList;
}

Storage *removeBusinessData(Storage *businessList, int businessIndex) {
    int option = 0;

    puts(BAR_LONG);
    puts(TITLE_B_DEL);

    do {
        businessRemoveDisplay(businessList, businessIndex);
        option = getValidatedIntWithMenu(0, 1, BACK_MENU);
        businessList =
            deleteBusinessFeature(businessList, businessIndex, option);

        if (BUSINESS_ACTIVE == 0) {
            option = 0;
        }

        if (option == 9) {
            option = 0;
        }

    } while (option != 0);

    return businessList;
}

Storage *deleteBusinessFeature(Storage *businessList, int businessIndex, int option) {
    switch (option) {
        case 1:
            if (BUSINESS_ACTIVE == 0 && BUSINESS_USED == 0) {
                businessDELETE(businessList, businessIndex);
                break;
            } else {
                puts(BUSINESS_NO_DELETE);
                break;
            }
        case backMenu:
            puts(BACK);
            break;
        case exitProgram:
            puts(EXIT);
            saveData_FreeMemory(businessList);
            exit(0);
        default:
            break;
    }
    return businessList;
}

void businessDELETE(Storage *businessList, int businessIndex) {
    char *input = malloc(MAX_LEN * sizeof(char));
    int match = -1;
    int i = 0;

    readString(input, MAX_LEN, B_DEL_INSERT);
    reduceStringBUFFER(input);
    match = strcmp(input, BUSINESS_NAME);

    if (match == 0) {
        BUSINESS_NUMBER -= 1;
        strcpy(BUSINESS_NAME, "");
        strcpy(BUSINESS_STATUS, "");
        BUSINESS_ACTIVE = 0;

        for (i = businessIndex; i < BUSINESS_SIZE - 1; i++) {
            businessList->businessPtr[i] = businessList->businessPtr[i + 1];
            businessList->businessPtr[i].number -= 1;
        }

        puts(BUSINESS_DELETED);
        businessList->businessCtr--;

    } else {
        puts(INPUT_NO_MATCH);
    }
}

//-------------------------------------- EDIT ----------------------------------------------------------
Storage *editBusinessData(Storage *businessList) {
    int index = 0;
    int option = 0;

    puts(BAR_LONG);

    if (businessListEmpty(businessList) == 1) {
        return businessList;
    }

    businessListDisplay(businessList);

    do {
        puts(BUSI_SELECT);
        puts(SELECT_INDEX);
        index = getValidatedIntWithMenu(-1, (BUSINESS_SIZE - 1), NO_BACK_MENU);

        if (index == -1) {
            puts(BACK);
            return businessList;
        }

    } while (index >= 0 && index >= BUSINESS_SIZE);

    do {
        businessEditStatusDisplay(businessList, index);
        option = getValidatedIntWithMenu(0, 1, BACK_MENU);
        businessList = editFeatureBusiness(businessList, index, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);

    return businessList;
}

Storage *editFeatureBusiness(Storage *businessList, int index, int option) {
    switch (option) {
        case 1:
            setBusinessSTATUS(businessList, index);
            break;
        case backMenu:
            puts(BACK);
            break;
        case exitProgram:
            puts(EXIT);
            break;
        default:
            break;
    }
    return businessList;
}

Storage *catalogBusinessSave(Storage *businessList, char *filename) {
    int i;

    FILE *fp = fopen(filename, "wb");
    if (fp == NULL) {
        exit(EXIT_FAILURE);
    }

    fwrite(&BUSINESS_SIZE, sizeof(int), 1, fp);

    for (i = 0; i < BUSINESS_SIZE; i++) {
        fwrite(&BUSINESS_PTR[i], sizeof(Business), 1, fp);
    }

    fclose(fp);
    return businessList;
}

Storage *catalogBusinessLoad(Storage *businessList, char *filename) {
    FILE *fp = fopen(filename, "rb");
    if (fp == NULL) {
        return businessList;
    }

    fread(&BUSINESS_SIZE, sizeof(int), 1, fp);

    if (BUSINESS_SIZE > 0) {
        BUSINESS_PTR = (Business *) calloc(BUSINESS_SIZE, sizeof(Business));
        for (int i = 0; i < BUSINESS_SIZE; i++) {
            fread(&BUSINESS_PTR[i], sizeof(Business), 1, fp);
        }
    }

    fclose(fp);
    return businessList;
}
