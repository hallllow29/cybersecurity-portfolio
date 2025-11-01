/**
 * @file        AccessCompany.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Access the company menu functions.
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/company/AccessCompany.h"
#include "../../inc/admin/ManageCatalog.h"
#include "../../inc/Output.h"
#include "../../inc/Input.h"
#include "../../inc/Defines.h"
// ------------------------ Function Definitions -------------------------------
Storage *accessCompany(Storage *companyList, int index) {
    int option;

    do {
        companyEditDisplay(companyList, index);
        option = getValidatedIntWithMenu(0, 8, BACK_MENU);
        companyList = editFeatureCompany(companyList, index, option);

        if(option == 9){
            option = 0;
        }

    } while (option != 0);

    return companyList;
}