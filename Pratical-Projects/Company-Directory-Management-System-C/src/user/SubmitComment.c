/**
 * @file        SubmitComment.c
 * @author      Pedro, Olga, Rubem
 * @copyright   ESTG IPP
 * @brief       ...
 * @date
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/struct/CompanyStructure.h"
#include "../../inc/user/SubmitComment.h"
#include "../../inc/user/SubmitSearch.h"
#include "../../inc/Input.h"
#include "../../inc/Output.h"
#include "../../inc/Defines.h"
#include "../../inc/MainMenu.h"
#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <string.h>
// ------------------------------ Libraries ------------------------------------
Storage *submitComment(Storage *dataBase) {
    int option = 0;

    if (companyListEmpty(dataBase) == 0) {
        return dataBase;
    }

    do {
        submitCommentDisplay();
        option = getValidatedIntWithMenu(0, 2, BACK_MENU);
        dataBase = submitCommentFeature(dataBase, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);

    return dataBase;
}

Storage *submitCommentFeature(Storage *dataBase, int option) {
    switch (option) {
        case searchCommentCompany:
            submitSearch(dataBase);
            break;
        case listCommentCompanies:
            userCompanyListDisplay(dataBase);
            break;
        case backMenu:
            puts(BACK);
            return dataBase;
        case exitProgram:
            puts(EXIT);
            saveData_FreeMemory(dataBase);
            exit(0);
        default:
            break;
    }
    dataBase = submitCommentCompany(dataBase);

    return dataBase;
}

Storage *submitCommentCompany(Storage *companyList) {
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
    puts(TITLE_COMPANY_COMMENT);
    getCompanyData(companyList, index);

    do {
        companyCommentDisplay(companyList, index);
        option = getValidatedIntWithMenu(0, 1, BACK_MENU);
        companyList = commentFeature(companyList, index, option);

        if (option == 9) {
            option = 0;
        }
    } while (option != 0);

    return companyList;
}

Storage *commentFeature(Storage *companyList, int index, int option) {
    switch (option) {
        case addComment:
            companyList = newCommentList(companyList, index, (COMPANY_COMMT_SIZE + 1));
            companyList = newCOMMENT(companyList, index, COMPANY_COMMT_SIZE);
            COMPANY_COMMT_SIZE++;
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

Storage *newCommentList(Storage *companyList, int index, int size) {
    if (COMMENT_PTR == NULL && size == 1) {

        COMMENT_PTR = NULL;
        COMMENT_PTR = malloc(1 * sizeof(Comment));

    } else if (COMMENT_PTR != NULL && size != 1) {

        COMMENT_PTR = realloc(COMMENT_PTR, size * sizeof(Comment));

        if (COMMENT_PTR == NULL) {
            puts(NO_MEMORY);
            return NULL;
        }
    }
    return companyList;
}

Storage *newCOMMENT(Storage *companyList, int index, int id) {

    COMPANY_COMMT_NUMBER = id;

    printf("ID: %d", COMPANY_COMMT_NUMBER);

    setCommentTITLE(companyList, index, id);
    setCommentNAME(companyList, index, id);
    setCommentEMAIL(companyList, index, id);
    setCommentMESSAGE(companyList, index, id);

    strcpy(COMPANY_COMMT_STATUS, "Active");

    reduceStringBUFFER(COMPANY_COMMT_STATUS);

    COMPANY_COMMT_HIDDEN = 0;

    return companyList;
}

