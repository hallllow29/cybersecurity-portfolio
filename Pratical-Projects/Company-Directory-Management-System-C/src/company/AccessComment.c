/**
 * @file        AccessComments.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Access comments for the company menu functions.
 * @date        12/12/2023
**/
// ------------------------------ Libraries ------------------------------------
#include "../../inc/struct/CompanyStructure.h"
#include "../../inc/company/AccessComment.h"
#include "../inc/admin/ManageCatalog.h"
#include "../../inc/Output.h"
#include "../../inc/Input.h"
#include "../../inc/Defines.h"
#include <stdio.h>
// ------------------------ Function Definitions -------------------------------
Storage *accessComment(Storage *companyList, int index) {
    int option = 0;

    int list = commentListEmpty(companyList, index);

    if (list == EMPTY) {
        return companyList;
    }

    commentListDisplay(companyList, index);
    accessCommentDisplay();
    option = getValidatedIntWithMenu(0, 1, EXIT_MENU);

    if (option == 1) {
        commentMenuEdit(companyList, index, 2);
    } else if (option == 0) {
        puts(BACK);
    }

    return companyList;
}