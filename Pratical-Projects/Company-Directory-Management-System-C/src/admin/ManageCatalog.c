/**
 * @file        ManageCatalog.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Manage the catalog for admin functions
 * @date        29/11/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/admin/ManageCatalog.h"
#include "../../inc/admin/ManageBusiness.h"
#include "../../inc/Output.h"
#include "../../inc/Input.h"
#include "../../inc/Defines.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../../inc/MainMenu.h"

// ------------------------ Function Definitions -------------------------------
Storage *manageCatalog(Storage *dataBase) {
    int option = 0;
    do {
        manageCatalogDisplay();
        option = getValidatedIntWithMenu(0, 8, BACK_MENU);
        dataBase = manageCatalogFeature(dataBase, option);

        if (option == 9) {
            option = 0;
        }

    } while (option != 0);
    return dataBase;
}

Storage *manageCatalogFeature(Storage *companyList, int option) {
    switch (option) {
        case addCompanyFeature:
            companyList = catalogCompanyInsert(companyList);
            break;
        case listCompaniesFeature:
            catalogCompanyList(companyList);
            break;
        case searchCompanyFeature:
            catalogCompanySearch(companyList);
            break;
        case removeCompanyFeature:
            companyList = selectCompanyRemove(companyList);
            break;
        case editCompanyFeature:
            companyList = selectCompanyToEdit(companyList);
            break;
        case editCompanyCommentsFeature:
            companyList = selectCompanyEditComments(companyList);
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

// ----------------------------- ADD ------------------------------------------
Storage *catalogCompanyInsert(Storage *companyList) {
    companyList = newCompanyList(companyList, (COMPANY_SIZE + 1));
    companyList = newCompany(companyList, COMPANY_SIZE);
    COMPANY_SIZE++;
    return companyList;
}

Storage *newCompanyList(Storage *companyList, int size) {
    if (COMPANY_PTR == NULL) {

        COMPANY_PTR = calloc(1, sizeof(Company));
        COMPANY_SIZE = 0;

    } else if (COMPANY_PTR != NULL && size != 1) {

        COMPANY_PTR = realloc(COMPANY_PTR, size * sizeof(Company));

        if (COMPANY_PTR == NULL) {
            puts(NO_MEMORY);
            return NULL;
        }
    }
    return companyList;
}

Storage *newCompany(Storage *companyList, int index) {
    int businessIndex = 0;
    puts(BAR_LONG);
    puts(TITLE_COMPANY_ADD);

    COMPANY_NUMBER = index;
    setCompanyNIF(companyList, COMPANY_NIF, NIF_INSERT);
    setCompanyNAME(companyList, COMPANY_NAME, NAME_INSERT);
    setCompanyCATG(companyList, COMPANY_CATEGORY, CATG_INSERT);
    setCompanySTREET(companyList, COMPANY_STREET, STREET_INSERT);
    setCompanyPOSTAL(companyList, COMPANY_POSTAL, POSTAL_INSERT);
    setCompanyLOCAL(companyList, COMPANY_LOCAL, LOCAL_INSERT);

    if(businessListEmpty(companyList) == 1){
        companyList = catalogBusinessInsert(companyList);
        businessIndex = businessSELECT(companyList);
        companyList = addBusinessToCompany(companyList, businessIndex, index);
    } else {
        companyList = businessOnList(companyList, index);
    }

    COMPANY_COMMT_SIZE = 0;

    initializeSearchCount(companyList, index);

    return companyList;
}

Storage *businessOnList(Storage *companyList, int index) {
    int option = 0;
    int businessIndex = 0;

    do {
        businessListDisplay(companyList);
        businessOnListDisplay();
        option = getValidatedIntWithMenu(0, 1, EXIT_MENU);

        if (option == 1) {

            businessIndex = businessSELECT(companyList);
            companyList = addBusinessToCompany(companyList, businessIndex, index);

            if (businessIndex == -1 || option == 1) {
                puts(BACK);
                return companyList;
            }
        }
    } while (option != 0);

    companyList = catalogBusinessInsert(companyList);
    businessListDisplay(companyList);
    businessIndex = businessSELECT(companyList);
    companyList = addBusinessToCompany(companyList, businessIndex, index);

    return companyList;
}

Storage *addBusinessToCompany(Storage *companyList, int businessIndex, int index) {
    if (businessIndex != -1) {

        strcpy(COMPANY_ACTIVTY, BUSINESS_CL_NAME);
        BUSINESS_CL_ACTIVE = 1;

        strcpy(BUSINESS_CL_STATUS, "Active");
        BUSINESS_CL_USED = 1;

        strcpy(COMPANY_STATUS, "Active");
        COMPANY_ACTIVE = 1;
        printf(COMPANY_ADDED, COMPANY_NAME);

    } else if (businessIndex == -1) {
        BUSINESS_CL_USED = 0;
        COMPANY_ACTIVE = 0;
        printf(BUSINESS_MISSING, COMPANY_NAME);
    }
    return companyList;
}

Storage *initializeSearchCount(Storage *companyList, int index) {
    COMPANY_SERCHS_TOTAL = 0;
    COMPANY_SERCHS_NIF = 0;
    COMPANY_SERCHS_NAME = 0;
    COMPANY_SERCHS_LOCAL = 0;
    COMMENT_PTR = NULL;
    COMPANY_COMMT_SIZE = 0;
    return companyList;
}

// ---------------------------- LIST -------------------------------------------
void catalogCompanyList(Storage *companyList) {
    if (companyListEmpty(companyList) == 1) {
        companyListDisplay(companyList);
    }

}

// --------------------------- SEARCH ------------------------------------------

void catalogCompanySearch(Storage *companyList) {
    catalogCompanyList(companyList);
    companySearchFeature(companyList);
}

void companySearchFeature(Storage *companyList) {
    int option = 0;

    do {
        companySearchDisplay();
        option = getValidatedIntWithMenu(0, 7, BACK_MENU);
        option = searchFeatureCompany(companyList, option);

        if (option == 9) {
            option = 0;
        }

    } while (option != 0);
}

int searchFeatureCompany(Storage *companyList, int option) {
    int index = 0;

    switch (option) {
        case searchCompanyByNif:
            searchCompanyDATA(companyList, COMPANY_NIF, NIF_INSERT, NOT_FOR_USER);
            break;
        case searchCompanyByName:
            searchCompanyDATA(companyList, COMPANY_NAME, NAME_INSERT, NOT_FOR_USER);
            break;
        case searchCompanyByCategory:
            searchCompanyDATA(companyList, COMPANY_CATEGORY, CATG_INSERT, NOT_FOR_USER);
            break;
        case searchCompanyByBusiness:
            searchCompanyDATA(companyList, COMPANY_ACTIVTY, BUSI_INSERT, NOT_FOR_USER);
            break;
        case searchCompanyByStreet:
            searchCompanyDATA(companyList, COMPANY_STREET, STREET_INSERT, NOT_FOR_USER);
            break;
        case searchCompanyByLocal:
            searchCompanyDATA(companyList, COMPANY_LOCAL,  LOCAL_INSERT, NOT_FOR_USER);
            break;
        case searchCompanyByPostal:
            searchCompanyDATA(companyList, COMPANY_POSTAL, POSTAL_INSERT, NOT_FOR_USER);
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
    return option;
}

// --------------------------- REMOVE ------------------------------------------

Storage *selectCompanyRemove(Storage *companyList) {
    int index = 0;

    if (companyListEmpty(companyList) == 1) {
        companyListDisplay(companyList);
    } else {
        return companyList;
    }

    puts(BAR_LONG);

    do {
        puts(COMPA_SELECT);
        puts(SELECT_INDEX);

        index = getValidatedIntWithMenu(-1, (COMPANY_SIZE - 1), NO_BACK_MENU);

        if (index == -1) {
            puts(BACK);
            return companyList;
        }

    } while (index < 0 || index > COMPANY_SIZE - 1);

    removeCompanyData(companyList, index);

    return companyList;
}

Storage *removeCompanyData(Storage *companyList, int index) {
    int option = 0;

    puts(BAR_LONG);
    puts(TITLE_COMPANY_DEL);
    do {
        companyRemoveDisplay(companyList, index);
        option = getValidatedIntWithMenu(0, 1, BACK_MENU);
        companyList = deleteFeature(companyList, index, option);

        if (COMPANY_ACTIVE == 1 || option == 9) {
            option = 0;
        }

    } while (option != 0);

    return companyList;
}

Storage *deleteFeature(Storage *companyList, int index, int option) {
    int valid = 0;
    switch (option) {
        case 1:
            if (COMPANY_COMMT_SIZE == 0) {
                valid = companyDELETE(companyList, index);
                if (valid == 1) {
                    COMPANY_SIZE--;
                }
                break;
            } else {
                puts(NO_DEL_COMPANY);
                break;
            }
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

// ---------------------------- EDIT -------------------------------------------

Storage *selectCompanyToEdit(Storage *companyList) {
    int index = 0;

    if (companyListEmpty(companyList) == 1) {
        companyListDisplay(companyList);
    } else {
        return companyList;
    }

    puts(BAR_LONG);

    do {
        puts(COMPA_SELECT);
        puts(SELECT_INDEX);
        index = getValidatedIntWithMenu(-1, (COMPANY_SIZE - 1), NO_BACK_MENU);

        if (index == -1) {
            puts(BACK);
            return companyList;
        }

    } while (index < 0 || index > COMPANY_SIZE - 1);

    selectFeatureToEdit(companyList, index);

    return companyList;
}

Storage *selectFeatureToEdit(Storage *companyList, int index) {
    int option = 0;

    do {
        companyEditDisplay(companyList, index);
        option = getValidatedIntWithMenu(0, 8, BACK_MENU);
        companyList = editFeatureCompany(companyList, index, option);

        if (option == 9) {
            option = 0;
        }

    } while (option != 0);

    return companyList;
}

Storage *editFeatureCompany(Storage *companyList, int index, int option) {
    int businessIndex;
    switch (option) {
        case editNIF:
            setCompanyNIF(companyList, COMPANY_NIF, NIF_INSERT);
            break;
        case editName:
            setCompanyNAME(companyList, COMPANY_NAME, NAME_INSERT);
            break;
        case editCategory:
            setCompanyCATG(companyList, COMPANY_CATEGORY, CATG_INSERT);
            break;
        case editBusiness:
            companyList = businessOnList(companyList,index);
            break;
        case editStreet:
            setCompanySTREET(companyList, COMPANY_STREET, STREET_INSERT);
            break;
        case editLocal:
            setCompanyLOCAL(companyList, COMPANY_LOCAL, LOCAL_INSERT);
            break;
        case editPostalCode:
            setCompanyPOSTAL(companyList, COMPANY_POSTAL, POSTAL_INSERT);
            break;
        case editStatus:
            setCompanySTATUS(companyList, index);
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

//------------------------- EDIT COMPANY COMMENTS ------------------------------
Storage *selectCompanyEditComments(Storage *companyList) {
    int index = 0;
    int commentList = 0;

    if (companyListEmpty(companyList) == 1) {
        companyListDisplay(companyList);
    } else {
        return companyList;
    }


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

    commentList = commentListEmpty(companyList, index);

    if (commentList == EMPTY) {
        return companyList;
    } else {
        selectCommentOptionEdit(companyList, index);
    }

    return companyList;
}

Storage *selectCommentOptionEdit(Storage *companyList, int index) {
    int option;

    do {
        companyCommentMenuDisplay();
        option = getValidatedIntWithMenu(0, 3, BACK_MENU);
        companyList = commentMenuEdit(companyList, index, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);

    return companyList;
}

Storage *commentMenuEdit(Storage *companyList, int index, int option) {
    switch (option) {
        case deleteComment:
            companyList = selectCommentToDelete(companyList, index);
            break;
        case hideAComment:
            companyList = hideAndShowComment(companyList, index);
            break;
        case commentDisplay:
            if (commentListEmpty(companyList, index) == 0) {
                commentListDisplay(companyList, index);
            }
            break;
        case backMenu:
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

Storage *selectCommentToDelete(Storage *companyList, int index) {
    int id;
    int list;

    list = commentListEmpty(companyList, index);

    if (list == EMPTY) {
        return companyList;
    }

    commentListDisplay(companyList, index);

    puts(COMMT_SELECT);
    puts(SELECT_INDEX);

    id = getValidatedIntWithMenu(-1, (COMPANY_COMMT_SIZE - 1), NO_BACK_MENU);

    if (index == -1) {
        puts(BACK);
        return companyList;
    }

    companyList = commentDELETE(companyList, index, id);

    return companyList;

}

Storage *commentDELETE(Storage *companyList, int index, int id) {
    int i;

    if (COMPANY_COMMT_NUMBER == id) {
        COMPANY_COMMT_NUMBER -= 1;
        strcpy(COMPANY_COMMT_EMAIL, "");
        strcpy(COMPANY_COMMT_NAME, "");
        strcpy(COMPANY_COMMT_TITLE, "");
        strcpy(COMPANY_COMMT_MSG, "");

        for (i = id; i < COMPANY_COMMT_SIZE - 1; i++) {
            companyList->companyPtr->commentPtr[i] =
                companyList->companyPtr->commentPtr[i + 1];
            companyList->companyPtr->commentPtr[i].commentId -= 1;
        }
        puts(COMMT_DELETED);

        COMPANY_COMMT_SIZE--;

        if (COMPANY_COMMT_SIZE == 0) {
            free(COMMENT_PTR);
            COMMENT_PTR = NULL;
        }
    } else {
        puts(COMMT_NOT_FOUND);
    }

    return companyList;
}

Storage *hideAndShowComment(Storage *companyList, int index) {
    int id = 0;
    int list = 0;
    int option = 0;

    list = commentListEmpty(companyList, index);

    if (list == EMPTY) {
        return companyList;
    }

    hideAndShowCommentDisplay();

    option = getValidatedIntWithMenu(0, 2, BACK_MENU);

    if(option == 9 || option == 0){
        return companyList;
    }

    commentListDisplay(companyList, index);

    puts(COMMT_SELECT);
    puts(SELECT_INDEX);

    id = getValidatedIntWithMenu(-1, (COMPANY_COMMT_SIZE - 1), NO_BACK_MENU);

    if (id == -1) {
        puts(BACK);
        return companyList;
    }

    commentSEARCH(companyList, index, id, option);

    return companyList;
}

//--------------------------------------------------- SAVE TO FILES ---------------------------------------------------

Storage *catalogCompanySave(Storage *companyList, char *filename) {
    int i;

    FILE *fp = fopen(filename, "wb");
    if (fp == NULL) {
        exit(EXIT_FAILURE);
    }

    fwrite(&COMPANY_SIZE, sizeof(int), 1, fp);

    for (i = 0; i < COMPANY_SIZE; i++) {
        fwrite(&COMPANY_PTR[i], sizeof(Company), 1, fp);
        fwrite(COMPANY_PTR[i].commentPtr,
               sizeof(Comment),
               companyList->companyPtr[i].commentCtr,
               fp);
    }

    fclose(fp);
    return companyList;
}

Storage *catalogCompanyLoad(Storage *companyList, char *filename) {
    FILE *fp = fopen(filename, "rb");
    if (fp == NULL) {
        puts(FILE_NO_DATA);
        return companyList; // ou retornar NULL ou tratar o erro de outra forma
    }

    fread(&COMPANY_SIZE, sizeof(int), 1, fp);
    if (COMPANY_SIZE > 0) {
        COMPANY_PTR = (Company *) calloc(COMPANY_SIZE, sizeof(Company));
        for (int i = 0; i < COMPANY_SIZE; i++) {
            fread(&COMPANY_PTR[i], sizeof(Company), 1, fp);
            if (COMPANY_PTR[i].commentCtr != 0) {
                COMPANY_PTR[i].commentPtr =
                    alloc_COMMT_array(COMPANY_PTR[i].commentCtr);
                fread(COMPANY_PTR[i].commentPtr,
                      sizeof(Comment),
                      COMPANY_PTR[i].commentCtr,
                      fp);
            }
        }
    }

    fclose(fp);
    return companyList;
}

Comment *alloc_COMMT_array(int size) {
    return (Comment *) calloc(size, sizeof(Comment));
}
