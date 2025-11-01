/**
 * @file        Output.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Outputs functions.
 * @date        01/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/Output.h"
#include "../inc/Defines.h"
#include <stdio.h>
#include <string.h>
// ------------------------ Function Definitions -------------------------------
void mainMenuDisplay() {
    puts(BAR_LONG);
    puts(TITLE_MAIN);
    puts("[1] ADMIN");
    puts("[2] USER");
    puts("[3] COMPANY\n");
    puts("[0] EXIT");
}

void adminMenuDisplay() {
    puts(BAR_LONG);
    puts(TITLE_ADMIN);
    puts("[1] Manage Company Catalog");
    puts("[2] Manage Business Branches");
    puts("[3] Manage/View Reports");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void userMenuDisplay() {
    puts(BAR_LONG);
    puts(TITLE_USER);
    puts("[1] Search Company");
    puts("[2] Rate Company");
    puts("[3] Comment Company");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void companyMenuDisplay() {
    puts(BAR_LONG);
    puts(TITLE_COMPANY);
    puts("[1] Update Company Information");
    puts("[2] View Company Comments");
    puts("[3] Company Analytics");
    puts("[4] Company Search Analytics");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void manageCatalogDisplay() {
    puts(BAR_LONG);
    puts(TITLE_CATAL);
    puts("[1] Add Company");
    puts("[2] List Company");
    puts("[3] Search Company");
    puts("[4] Remove Company");
    puts("[5] Update Company Information");
    puts("[6] Update Company Comment");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void manageBusinessDisplay() {
    puts(BAR_LONG);
    puts(TITLE_BUSI);
    puts("[1] Add Business");
    puts("[2] List Business");
    puts("[3] Edit Business");
    puts("[4] Remove Business");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void manageReportDisplay() {
    puts(BAR_LONG);
    puts(TITLE_REPORT);
    puts("[1] Display Top 5 Companies by Rating");
    puts("[2] Display Most Searched Companies");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void submitSearchDisplay() {
    puts(BAR_LONG);
    puts(TITLE_COMPANY_SEARCH);
    puts("[1] Search by NIF");
    puts("[2] Search by NAME");
    puts("[3] Search by LOCAL");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void submitRatingDisplay() {
    puts(BAR_LONG);
    puts(TITLE_C_RATING);
    puts("[1] Search Company");
    puts("[2] List Company");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void submitCommentDisplay() {
    puts(BAR_LONG);
    puts(TITLE_COMPANY_COMMENT);
    puts("[1] Search Company");
    puts("[2] List Company");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void accessCommentDisplay() {
    puts("[1] Hide");
    puts("[0] EXIT");
}

void companyListDisplay(Storage *companyList) {
    int index = 0;
    puts(BAR_LONG);
    puts(TITLE_COMPANY_LIST);
    puts(HEADER_C_LIST);
    for (index = 0; index < COMPANY_SIZE; index++) {
        printf(
            "  %03d - %-9s - %-30s - %-11s - %-30s - %-30s, %-8s, %-30s - [%s]\n",
            COMPANY_NUMBER,
            COMPANY_NIF,
            COMPANY_NAME,
            COMPANY_CATEGORY,
            COMPANY_ACTIVTY,
            COMPANY_STREET,
            COMPANY_POSTAL,
            COMPANY_LOCAL,
            COMPANY_STATUS);
    }
}

int companyListEmpty(Storage *companyList) {
    puts(LIST_COMPANY_LOADING);
    if (COMPANY_SIZE == 0) {
        puts(LIST_COMPANY_EMPTY);
        return 0;
    } else {
        return 1;
    }
}

void companySearchDisplay() {
    puts(BAR_LONG);
    puts(TITLE_COMPANY_SEARCH);
    puts("[1] Search by NIF");
    puts("[2] Search by NAME");
    puts("[3] Search by CATEGORY");
    puts("[4] Search by BUSINESS");
    puts("[5] Search by STREET");
    puts("[6] Search by LOCAL");
    puts("[7] Search by POSTAL CODE");
    puts("[9] BACK\n");
    puts("[0] EXIT");
}

void companyRemoveDisplay(Storage *companyList, int index) {
    printf("You selected [%d] %s \n\n", index, COMPANY_NAME);
    puts("[1] REMOVE");
    puts("[9] BACK\n");
    puts("[0] EXIT");
    puts("Select an option:");
}

void companyEditDisplay(Storage *companyList, int index) {
    puts(BAR_LONG);
    puts(TITLE_COMPANY_EDIT);

    printf("You selected [%d] %s \n", index, COMPANY_NAME);
    printf("\n[1] NIF:\t\t%s\n", COMPANY_NIF);
    printf("[2] Name:\t\t%s\n", COMPANY_NAME);
    printf("[3] Category:\t\t%s\n", COMPANY_CATEGORY);
    printf("[4] Business:\t\t%s\n", COMPANY_ACTIVTY);
    printf("[5] Street:\t\t%s\n", COMPANY_STREET);
    printf("[6] Local:\t\t%s\n", COMPANY_LOCAL);
    printf("[7] Postal Code:\t%s\n", COMPANY_POSTAL);
    printf("[8] Status:\t\t[%s]\n\n", COMPANY_STATUS);
    puts("[9] BACK");
    puts("[0] Exit");
    puts("Select an option:");
}

void getCompanyData(Storage *companyList, int index) {
    puts(BAR_LONG);
    puts(TITLE_C_FOUND);
    puts(HEADER_C_LIST);
    printf("%03d - %-9s - %-30s - %-11s - %-30s- %-30s, %-8s, %-30s - [%s]\n",
           COMPANY_NUMBER,
           COMPANY_NIF,
           COMPANY_NAME,
           COMPANY_CATEGORY,
           COMPANY_ACTIVTY,
           COMPANY_STREET,
           COMPANY_POSTAL,
           COMPANY_LOCAL,
           COMPANY_STATUS);
    fflush(stdout);
}

// -------------------- BUSINESS: LIST, EMPTY, EDIT ----------------------------

void businessListDisplay(Storage *businessList) {
    int businessIndex;
    puts(BAR_LONG);
    puts(TITLE_BUSINESS_EDIT);
    puts(HEADER_B_LIST);
    for (businessIndex = 0; businessIndex < BUSINESS_SIZE; businessIndex++) {
        printf(" %03d  - %-30s - [%s]\n\n", BUSINESS_NUMBER, BUSINESS_NAME, BUSINESS_STATUS);
    }
}

int businessListEmpty(Storage *businessList) {
    puts(LIST_BUSINESS_LOADING);
    if (BUSINESS_SIZE == 0) {
        puts(LIST_BUSINESS_EMPTY);
        return 1;
    } else {
        // NAO FAZ DEVE AMOSTRAR, QUE SE NAO AMOSTRA DUAS VEZES MAN!!!!!

        // businessListDisplay(businessList);
        return 0;
    }
}

void businessRemoveDisplay(Storage *businessList, int index) {
    printf("You selected [%d] %s \n\n",
           index,
           businessList->businessPtr[index].activity);
    puts("[1] REMOVE");
    puts("[9] BACK\n");
    puts("[0] EXIT");
    puts("Select an option:");
}

void userCompanyListDisplay(Storage *companyList) {
    int valid = -1;
    int index = 0;
    int businessIndex = 0;

    puts(BAR_LONG);
    puts(TITLE_COMPANY_EDIT);
    puts("INDEX - NIF - NAME - CATEGORY - BUSINESS - ADDRESS - STATUS \n");
    for (index = 0; index < COMPANY_SIZE; index++) {
        businessIndex = getIndexOfActivity(companyList, index);
        if (BUSINESS_CL_USED  == 0) {
            continue;
        } else if (COMPANY_ACTIVE == 1) {
            printf("%03d - %s - %s - %s - %s - %s, %s, %s -  [%.10s]\n",
                   COMPANY_NUMBER,
                   COMPANY_NIF,
                   COMPANY_NAME,
                   COMPANY_CATEGORY,
                   COMPANY_ACTIVTY,
                   COMPANY_STREET,
                   COMPANY_LOCAL,
                   COMPANY_POSTAL,
                   COMPANY_STATUS);
            valid++;
        }

        if (valid == -1) {
            puts(NOT_FOUND);
        }
    }
}

int getIndexOfActivity(Storage *companyList, int index) {
    int businessIndex;

    for (businessIndex = 0; businessIndex < companyList->businessCtr; businessIndex++) {
        if (strcmp(COMPANY_NAME, BUSINESS_CL_NAME) == 0) {
            return businessIndex;
        } else {
            continue;
        }
    }

    return 0;
}

void getCompanyDataForUser(Storage *companyList, int index) {
    int i;
    int businessIndex = 0;

    puts(BAR_LONG);
    puts(TITLE_C_FOUND);
    puts(HEADER_C_LIST);

    businessIndex = getIndexOfActivity(companyList, index);

    if (BUSINESS_CL_ACTIVE == 1 && (COMPANY_ACTIVE == 1)) {
        printf(
            "%03d - %-9s - %-30s - %-11s - %-30s- %-30s, %-8s, %-30s - [%s]\n",
            COMPANY_NUMBER,
            COMPANY_NIF,
            COMPANY_NAME,
            COMPANY_CATEGORY,
            COMPANY_ACTIVTY,
            COMPANY_STREET,
            COMPANY_POSTAL,
            COMPANY_LOCAL,
            COMPANY_STATUS);
        fflush(stdout);
    } else {
        puts("Company not found");
    }
}

void companyRatingDisplay(Storage *companyList, int index) {
    printf("You selected [%d] %s \n\n", index, COMPANY_NAME);
    puts("[1] RATE");
    puts("[9] BACK\n");
    puts("[0] EXIT");
    puts("Select an option:");
}

void companyCommentDisplay(Storage *companyList, int index) {
    printf("You selected [%d] %s \n\n", index, COMPANY_NAME);
    puts("[1] COMMENT");
    puts("[9] BACK\n");
    puts("[0] EXIT");
    puts("Select an option:");
}

void commentListDisplay(Storage *companyList, int index) {
    int id;

    printf("%s COMMENT LIST:\n", COMPANY_NAME);

    for (id = 0; id < COMPANY_COMMT_SIZE; id++) {
        puts(BAR);
        printf("[NAME: %-20s              ]\n", COMPANY_COMMT_NAME);
        printf("[TITLE: %s                    ]\n", COMPANY_COMMT_TITLE);
        printf(" \" %s \" \n", COMPANY_COMMT_MSG);
        printf("COMMENT ID: %d \n", COMPANY_COMMT_NUMBER);
        printf("STATUS: %s\n", COMPANY_COMMT_STATUS);
        puts(BAR);
    }

}

void userCommentListDisplay(Storage *companyList, int index) {
    int id = 0;
    int businessIndex = 0;
    businessIndex = getIndexOfActivity(companyList, index);
    if (COMPANY_ACTIVE == 1 && BUSINESS_CL_USED  == 1) {
        puts(BAR_LONG);
        puts(TITLE_C_COMMENTS);
    }
    for (id = 0; id < COMPANY_COMMT_SIZE; id++) {
        if (COMPANY_COMMT_HIDDEN == 0 && COMPANY_ACTIVE == 1 && BUSINESS_CL_USED == 1) {
            puts(BAR);
            printf("[NAME: %s              ]\n", COMPANY_COMMT_NAME);
            printf(" \" %s \" \n", COMPANY_COMMT_MSG);
            printf("COMMENT ID: %d \n", COMPANY_COMMT_NUMBER);
            puts(BAR);
        }
    }
}

// ------------------------------------------------ BUSINESS --------------------------------------------------- //
void businessEditStatusDisplay(Storage *businessList, int businessIndex) {
    puts(BAR_LONG);
    puts(TITLE_BUSINESS_EDIT);
    printf("You selected [%d] %s \n",businessIndex, BUSINESS_NAME);
    printf("[1] Status:\t\t[%s]\n", BUSINESS_STATUS);
    puts("[9] Back\n");
    puts("[0] EXIT");
}

int commentListEmpty(Storage *companyList, int index) {
    puts(LIST_COMMENT_LOADING);
    if (COMPANY_COMMT_SIZE == 0) {
        printf(LIST_COMMT_EMPTY,COMPANY_NAME);
        return 1;
    } else {
        return 0;
    }
}

void companyCommentMenuDisplay() {
    puts(BAR_LONG);
    puts(TITLE_C_COMMENTS);
    puts("Choose what to you want to do: ");
    puts("[1] DELETE");
    puts("[2] HIDE/SHOW");
    puts("[3] LIST COMMENTS");
    puts("[9] BACK\n");
    puts("[0] EXIT");
    puts("Select an option.");
}

void hideAndShowCommentDisplay(){
    puts(BAR_LONG);
    puts((TITLE_C_COMMENTS));
    puts("[1] HIDE");
    puts("[2] SHOW");
    puts("[9] BACK\n");
    puts("[0] EXIT");
    puts("Select an option.");

}

void businessOnListDisplay() {
    puts("Is your company business on the list?");
    puts("[1] YES");
    puts("[0] NO\n");
}