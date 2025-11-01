/**
 * @file        Input.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Input functions.
 * @date        02/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../inc/struct/CompanyStructure.h"
#include "../inc/MainMenu.h"
#include "../inc/Input.h"
#include "../inc/Output.h"
#include "../inc/Defines.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
// ------------------------ Function Definitions -------------------------------

void cleanInputBuffer() {
    char ch;
    while ((ch = getchar()) != '\n' && ch != EOF);
}

void setCompanyNAME(Storage *companyList, char *dest, char *msg) {
    int valid = 0;
    char *input = calloc(MAX_LEN_NAME, sizeof(char));

    do {
        readString(input, MAX_LEN_NAME, msg);
        valid = isValidINPUT(input) && isNAMEinUse(companyList, input);
    } while (valid == 0);

    input = reduceStringBUFFER(input);
    strcpy(dest, input);
    reduceStringBUFFER(dest);
    free(input);
}

void setCompanyNIF(Storage *companyList, char *dest, char *msg) {
    int valid = 0;
    int nif = 0;
    char *input = malloc(NIF_LENGTH * sizeof(char));

    do {
        readString(input, NIF_LENGTH, msg);
        nif = atoi(input);
        valid = isValidNIF(nif) && isNIFinUse(companyList, input);

    } while (valid == 0);

    strcpy(dest, input);
    reduceStringBUFFER(dest);
    free(input);
}

void setCompanySTREET(Storage *companyList, char *dest, char *msg) {
    int valid = 0;
    char *input = calloc(MAX_LEN_STREET, sizeof(char));

    do {
        readString(input, MAX_LEN_STREET, msg);
        valid = isValidSTREET(input);
    } while (valid == 0);

    strcpy(dest, input);
    free(input);
}

void setCompanyCATG(Storage *companyList, char *dest, char *msg) {
    int valid = 0;
    char *input = calloc(MAX_LEN_CATG, sizeof(char));

    do {
        readString(input, MAX_LEN_CATG, msg);
        valid = isValidCATG(input);
    } while (valid == 0);

    strcpy(dest, input);
    reduceStringBUFFER(input);
    free(input);
}

void setCompanyPOSTAL(Storage *companyList, char *dest, char *msg) {
    int valid = 0;
    char *input = calloc(POSTAL_LENGTH, sizeof(char));

    do {
        readString(input, POSTAL_LENGTH, msg);
        valid = isValidPOSTAL(input);
    } while (valid == 0);

    strcpy(dest, input);
    reduceStringBUFFER(input);
    free(input);
}

void setCompanyLOCAL(Storage *companyList, char *dest, char *msg) {
    int valid = 0;
    char *input = calloc(SIZE_LOCAL, sizeof(char));

    do {
        readString(input, SIZE_LOCAL, msg);
        valid = isValidLOCAL(input);
    } while (valid == 0);

    strcpy(dest, input);
    reduceStringBUFFER(input);
    free(input);
}

int businessInUse(Storage *businessList, char *input) {
    int businessIndex = 0;

    for (businessIndex = 0; businessIndex < BUSINESS_SIZE; businessIndex++) {
        if (strcmp(input, BUSINESS_NAME) == 0) {
            // TODO: puts
            puts("Business already created!");
            return 0;
        }
    }

    return 1;
}

void setCompanyBUSINESS(Storage *businessList, char *dest, char *msg) {
    int valid = 0;
    char *input = calloc(MAX_LEN, sizeof(char));

    do {
        readString(input, MAX_LEN, msg);
        valid = isValidINPUT(input) && businessInUse(businessList, input);
    } while (valid == 0);

    strcpy(dest, input);
    reduceStringBUFFER(input);
    free(input);
}

int isValidNIF(int nif) {
    int check = 0;

    if (nif < 100000000 || nif > 999999999) {
        puts(NIF_NO_RANGE);
        return 0;
    } else {
        check = 1;
    }

    return check;
}

int searchCompanyNIF(Storage *companyList, char *input, int forUser) {
    int index = 0;

    if (forUser == 0) {
        for (index = 0; index < COMPANY_SIZE; index++) {
            if (strcmp(COMPANY_NIF, input) == 0) {
                getCompanyData(companyList, index);
                return index;
            }
        }
    } else if (forUser == 1) {
        for (index = 0; index < COMPANY_SIZE; index++) {
            if (strcmp(COMPANY_NIF, input) == 0) {
                getCompanyDataForUser(companyList, index);
                userCommentListDisplay(companyList, index);
                COMPANY_SERCHS_TOTAL++;
                COMPANY_SERCHS_NIF++;
                return index;
            }
        }
    }
    return -1;
}

int searchCompanyNAME(Storage *companyList, char *input, int forUser) {
    int index = 0;

    if (forUser == 0) {
        for (index = 0; index < COMPANY_SIZE; index++) {
            if (strcmp(COMPANY_NAME, input) == 0) {
                getCompanyData(companyList, index);
                return index;
            }
        }

    } else if (forUser == 1) {
        for (index = 0; index < COMPANY_SIZE; index++) {
            if (strcmp(COMPANY_NAME, input) == 0) {
                getCompanyDataForUser(companyList, index);
                userCommentListDisplay(companyList, index);
                COMPANY_SERCHS_TOTAL++;
                COMPANY_SERCHS_NAME++;
                return index;
            }
        }
    }
    return -1;
}

int searchCompanyCATG(Storage *companyList, char *input) {
    int index = 0;
    int multiple = 0;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_CATEGORY, input) == 0) {
            getCompanyData(companyList, index);
            multiple++;
        }
    }
    if (multiple < 1) {
        return -1;
    }
}

int searchCompanyLOCAL(Storage *companyList, char *input, int forUser) {
    int index = 0;
    int multiple = 0;

    if (forUser == 0) {
        for (index = 0; index < COMPANY_SIZE; index++) {
            if (strcmp(COMPANY_LOCAL, input) == 0) {
                getCompanyData(companyList, index);
                multiple++;
            }
        }

    } else if (forUser == 1) {
        for (index = 0; index < COMPANY_SIZE; index++) {
            if (strcmp(COMPANY_LOCAL, input) == 0) {
                getCompanyDataForUser(companyList, index);
                userCommentListDisplay(companyList, index);
                COMPANY_SERCHS_TOTAL++;
                COMPANY_SERCHS_LOCAL++;
                multiple++;
            }
        }
    }
    if (multiple < 1) {
        return -1;
    }
}

int searchCompanyBUSINESS(Storage *companyList, char *input) {
    int index = 0;
    int multiple = 0;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_ACTIVTY, input) == 0) {
            getCompanyData(companyList, index);
            multiple++;
        }
    }
    if (multiple < 1) {
        return -1;
    }

}

int searchCompanyPOSTAL(Storage *companyList, char *input) {
    int index = 0;
    int multiple = 0;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_POSTAL, input) == 0) {
            getCompanyData(companyList, index);
            multiple++;
        }
    }
    if (multiple < 1) {
        return -1;
    }
}

int searchCompanySTREET(Storage *companyList, char *input) {
    int index = 0;
    int multiple = 0;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_STREET, input) == 0) {
            getCompanyData(companyList, index);
            multiple++;
        }
    }
    if (multiple < 1) {
        return -1;
    }
}

int searchCompanyDATA(Storage *companyList, char *dest, char *info, int forUser) {
    int index = 0;
    int nif = 0;
    char input[MAX_LEN];

    readString(input, MAX_LEN, info);

    if (dest == COMPANY_NIF) {
        nif = atoi(input);
        isValidNIF(nif);
        index = searchCompanyNIF(companyList, input, forUser);
    } else if (dest == COMPANY_NAME) {
        isValidINPUT(input);
        index = searchCompanyNAME(companyList, input, forUser);
    } else if (dest == COMPANY_CATEGORY) {
        isValidCATG(input);
        index = searchCompanyCATG(companyList, input);
    } else if (dest == COMPANY_ACTIVTY) {
        isValidINPUT(input);
        index = searchCompanyBUSINESS(companyList, input);
    } else if (dest == COMPANY_STREET) {
        isValidSTREET(input);
        index = searchCompanySTREET(companyList, input);
    } else if (dest == COMPANY_POSTAL) {
        isValidPOSTAL(input);
        index = searchCompanyPOSTAL(companyList, input);
    } else if (dest == COMPANY_LOCAL) {
        isValidLOCAL(input);
        index = searchCompanyLOCAL(companyList, input, forUser);
    }

    if (index == -1) {
        puts(NOT_FOUND);
        return -1;
    }

    return index;

}

int getCompanyINDEX(Storage *companyList, char *input) {
    int index = 0;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_NAME, input) == 0) {
            return index;
        }
    }
    return -1;

}

int isNIFinUse(Storage *companyList, char *input) {
    int index = 0;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_NIF, input) == 0) {
            puts(NIF_IN_USE);
            return 0;
        }
    }
    return 1;
}

int isNAMEinUse(Storage *companyList, char *input) {
    int index;

    for (index = 0; index < COMPANY_SIZE; index++) {
        if (strcmp(COMPANY_NAME, input) == 0) {
            puts(NAME_IN_USE);
            return 0;
        }
    }
    return 1;
}

int isValidINPUT(char *input) {
    int check = 0;
    int i;
    int length = strlen(input);

    if (length > MAX_LEN) {
        puts(INPUT_NO_LENGTH);
        return 0;

    } else if (isalnum(input[0]) == 0) {
        puts(INPUT_NO_LENGTH);
        puts(INPUT_NO_LETTERS);
        return 0;

    } else if (length == 1) {
        puts(INPUT_NO_LENGTH);
        puts(INPUT_NO_BLANK);
        return 0;
    } else {
        check = checkSpecialChars(input, length);
    }

    return check;
}

int checkSpecialChars(const char *input, int length) {
    int i;
    for (i = 0; i < length; i++) {
        if (isalpha(input[i]) == 0) {
            if ((input[i] == '&') || (input[i] == ' ')
                || (isalpha(input[i - 1]) && input[i] == '.'
                    && input[i + 1] == '\0') || input[i] == '-') {
                continue;
            }
            puts(INPUT_NO_LETTERS);
            return 0;
        }
    }
    return 1;
}

int isValidCATG(char *input) {
    int check = 0;

    if (strcmp("SME", input) == 0 || strcmp("Micro", input) == 0
        || strcmp("Big", input) == 0) {
        check = 1;
    } else {
        puts(CATG_CHOOSE);
    }
    return check;
}

int isValidSTREET(char *input) {
    int check = 0;
    if (strlen(input) > MAX_LEN) {

        puts(INPUT_NO_LENGTH);
        return 0;

    } else if (input[0] == ' ' || input[0] == '.') {

        puts(INPUT_NO_LENGTH);
        puts(INPUT_NO_SPECIALS);
        return 0;

    } else if (strlen(input) == 1) {

        puts(INPUT_NO_LENGTH);
        puts(INPUT_NO_BLANK);
        return 0;

    } else {
        check = 1;
    }

    for (int i = 0; i < strlen(input); i++) {
        if (!isalnum(input[i])) {
            if (input[i] == ' ') {
                continue;
            }
            puts(INPUT_INVALID);
            return 0;
        }
    }

    return check;
}

int isValidPOSTAL(char *input) {
    char postal1[5] = {input[0], input[1], input[2], input[3], '\0'};
    char postal2[4] = {input[5], input[6], input[7], '\0'};
    int check = 0;
    int i = 0;
    int postalLeft = atoi(postal1);
    int postalRight = atoi(postal2);

    if (strlen(input) != 8) {
        puts(POSTAL_NO_RANGE);
        return 0;
    }

    for (i = 0; i < strlen(input); i++) {
        if (i == 4) {
            if (input[i] == '-') {
                check = 1;
                break;
            } else {
                puts(POSTAL_NO_RANGE);
                return 0;
            }
        }
    }

    if ((postalLeft < 1000 || postalLeft > 9001)
        || (postalRight < 001 || postalRight > 999)) {
        puts(POSTAL_NO_RANGE);
        check = 0;
    } else if (postalLeft == 9001 && postalRight > 801) {
        puts(POSTAL_NO_RANGE);
        check = 0;
    } else {
        check = 1;
    }

    return check;
}

char *reduceStringBUFFER(char *string) {
    if ((strlen(string) > 0) && string[strlen(string) - 1] == '\n') {
        string[strlen(string) - 1] = '\0';
    }
    return string;
}

void setCompanySTATUS(Storage *companyList, int index) {
    int option = 0;
    printf(CURRENT_STATUS, COMPANY_STATUS);
    puts(NEW_STATUS);

    option = getValidatedIntWithMenu(0, 2, BACK_MENU);

    if (option == 1) {
        strcpy(COMPANY_STATUS, "Inactive");
        COMPANY_ACTIVE = 0;
    } else if (option == 2) {
        strcpy(COMPANY_STATUS, "Active");
        COMPANY_ACTIVE = 1;
    } else if (option == 9) {
        puts(BACK);
    } else if (option == 0) {
        puts(EXIT);
        saveData_FreeMemory(companyList);
        exit(0);
    }

}

int isValidLOCAL(char *input) {
    int check = 0;
    if (strlen(input) > MAX_LEN) {

        puts(INPUT_NO_LENGTH);
        return 0;

    } else if (input[0] == ' ' || input[0] == '.') {

        puts(INPUT_NO_LENGTH);
        puts(INPUT_NO_LETTERS);
        return 0;

    } else if (strlen(input) == 1) {
        puts(INPUT_NO_LENGTH);
        puts(INPUT_NO_BLANK);
        return 0;

    } else {
        check = 1;
    }

    for (int i = 0; i < strlen(input); i++) {
        if (!isalpha(input[i])) {
            if (isalpha(input[i - 1]) && input[i] == ' '
                && isalpha(input[i + 1])) {
                continue;
            }
            puts(INPUT_INVALID);
            return 0;
        }
    }
    return check;
}

int companyDELETE(Storage *companyList, int index) {
    char *input = calloc(MAX_LEN, sizeof(char));
    int match = 0;

    readString(input, MAX_LEN, C_DEL_INSERT);

    reduceStringBUFFER(input);

    match = strcmp(input, COMPANY_NAME);

    if (match == 0) {
        companyCleanDATA(companyList, index);
        free(input);
        return 1;
    } else {
        printf(INPUT_NO_MATCH);
        free(input);
        return 0;
    }
}

void companyCleanDATA(Storage *companyList, int index) {
    COMPANY_NUMBER -= 1;
    strcpy(COMPANY_NIF, "");
    strcpy(COMPANY_NAME, "");
    strcpy(COMPANY_CATEGORY, "");
    strcpy(COMPANY_STREET, "");
    strcpy(COMPANY_LOCAL, "");
    strcpy(COMPANY_POSTAL, "");
    strcpy(COMPANY_STATUS, "");

    for (int i = index; i < COMPANY_SIZE - 1; i++) {
        companyList->companyPtr[i] = companyList->companyPtr[i + 1];
        companyList->companyPtr[i].number -= 1;
    }

    puts(C_DEL_SUCCESS);
}

void setRATING(Storage *companyList, int index) {
    double rating = 0.0;

    puts(RATING_INSERT);

    rating = getDouble(0.0, 5.0);

    if (rating >= 0.0 && rating <= 5.0) {

        COMPANY_RATNG_TOTAL += rating;
        COMPANY_RATNG_COUNTER++;
        COMPANY_RATING = COMPANY_RATNG_TOTAL / (float) COMPANY_RATNG_COUNTER;

        printf(NEW_AVRG_RATE, COMPANY_RATING);
    }
}

void setCommentTITLE(Storage *companyList, int index, int id) {
    readString(COMPANY_COMMT_TITLE, MAX_LEN_TITLE, C_COMMT_TITLE_INSERT);
}

void setCommentNAME(Storage *companyList, int index, int id) {
    readString(COMPANY_COMMT_NAME, MAX_LEN_NAME, C_COMMT_NAME_INSERT);
}

void setCommentEMAIL(Storage *companyList, int index, int id) {
    readString(COMPANY_COMMT_EMAIL, MAX_LEN_EMAIL, C_COMMT_EMAIL_INSERT);
}

void setCommentMESSAGE(Storage *companyList, int index, int id) {
    readString(COMPANY_COMMT_MSG, MAX_LEN_MESSAGE, C_COMMT_MSG_INSERT);
}

int companySignIn(Storage *companyList) {
    int valid = 0;
    int index;
    char *input;

    input = malloc(MAX_LEN * sizeof(char));
    puts(C_SIGN_IN);

    do {
        readString(input, MAX_LEN, NAME_ACCESS);

        valid = isValidINPUT(input);

    } while (valid == 0);

    index = getCompanyINDEX(companyList, input);

    free(input);
    return index;
}

int commentSEARCH(Storage *companyList, int index, int targetID, int option) {
    int id;

    for (id = 0; id < companyList->companyPtr[index].commentCtr; id++) {
        if (COMPANY_COMMT_NUMBER == targetID) {
            id = targetID;
            if (option == 1) {
                COMPANY_COMMT_HIDDEN = 1;
                strcpy(COMPANY_COMMT_STATUS, "Inactive");
                printf(COMMT_ID, targetID);
                return 1;
            } else if (option == 2) {
                COMPANY_COMMT_HIDDEN = 0;
                strcpy(COMPANY_COMMT_STATUS, "Active");
                printf(COMMT_ID, targetID);
                return 1;
            }
        }
    }

    puts(COMMT_NOT_FOUND);
    puts("TO LEAVE TYPE IN -1");
    return 0;
}

void setBusinessSTATUS(Storage *businessList, int businessIndex) {
    int option = 0;
    puts(NEW_STATUS);
    option = getValidatedIntWithMenu(0, 2, BACK_MENU);

    if (option == 1) {
        strcpy(BUSINESS_STATUS, "Inactive");
        BUSINESS_ACTIVE = 0;
        BUSINESS_USED = 0;
    } else if (option == 2) {
        strcpy(BUSINESS_STATUS, "Active");
        BUSINESS_ACTIVE = 1;
    } else if (option == 9) {
        puts(BACK);
    } else if (option == 0) {
        puts(EXIT);
        saveData_FreeMemory(businessList);
        exit(0);
    }

}

void readString(char *string, unsigned int size, char *msg) {
    printf(msg);
    if (fgets(string, size, stdin) != NULL) {
        unsigned int len = strlen(string) - 1;
        if (string[len] == '\n') {
            string[len] = '\0';
        } else {
            cleanInputBuffer();
        }
    }
}

int getValidatedIntWithMenu(int minValue, int maxValue, int withMenu) {
    int value;

    if (withMenu == -1) {
        while (printf("\nOPTION: "), scanf("%d", &value) != 1 || value < minValue
            || value > maxValue) {
            cleanInputBuffer();
            puts(NO_OPTION);
            puts(EXIT_SUGGEST);
        }
    } else if (withMenu == 1) {
        while (printf("\nOPTION: "), scanf("%d", &value) != 1
            || value < minValue || value > maxValue) {
            if (value == 9) {
                return value;
            }
            cleanInputBuffer();
            puts(NO_OPTION);
            puts(BACK_SUGGEST);
            puts(EXIT_SUGGEST);
        }
    } else if (withMenu == 0) {
        while (printf("\nINDEX: "), scanf("%d", &value) != 1 || value < minValue
            || value > maxValue) {
            cleanInputBuffer();
            puts(OUT_OF_BOUND);
        }
    }
    cleanInputBuffer();
    return value;
}

double getDouble(double minValue, double maxValue) {
    double value;
    while (printf("\nRATING: "), scanf("%lf", &value) != 1 || value < minValue
        || value > maxValue) {
        cleanInputBuffer();
        puts(RATING_NO_RANGE);
    }
    cleanInputBuffer();
    return value;
}

