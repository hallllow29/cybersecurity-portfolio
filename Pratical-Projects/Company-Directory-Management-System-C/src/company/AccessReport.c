/**
 * @file        ManageReport.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Access the reports for companies functions.
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/company/AccessReport.h"
#include "../inc/Output.h"
#include "../inc/Input.h"
#include "../inc/Defines.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
// ------------------------ Function Definitions -------------------------------

void accessReport(Storage *companyList, int index) {
    int rival = 0;
    char input[MAX_LEN];

    readString(input, MAX_LEN_NAME, RIVAL_INSERT);

    rival = searchCompanyNAME(companyList,input, FOR_USER);

    if (rival != -1) {
        puts(TITLE_COMPANY_ANALYTICS);
        printf("\t\t%-20s  versus \t\t%-20s \n",COMPANY_NAME, companyList->companyPtr[rival].name);
        printf("NIF      \t %03d\t\t        -  \t\t %03d\n", COMPANY_SERCHS_NIF, companyList->companyPtr[rival].searchCtrNif);
        printf("Name     \t %03d\t\t        -  \t\t %03d\n",COMPANY_SERCHS_NAME, companyList->companyPtr[rival].searchCtrName);
        printf("Locality \t %03d\t\t        -  \t\t %03d\n", COMPANY_SERCHS_LOCAL, companyList->companyPtr[rival].searchCtrLocal);
    }
}

void CompanySearchReport(Storage *companyList, int index) {
    if (index < 0 || index >= COMPANY_SIZE) {
        puts("Invalid company index.");
        return;
    }

    printf("%s Search Analytics\n", COMPANY_NAME);
    printf("NIF - %d\n", COMPANY_SERCHS_NIF);
    printf("Name - %d\n", COMPANY_SERCHS_NAME);
    printf("Locality - %d\n", COMPANY_SERCHS_LOCAL);
}
