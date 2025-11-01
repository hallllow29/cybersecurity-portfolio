/**
 * @file        CompanyMenu.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the company menu.
 * @date        29/11/2023
**/

#ifndef INC_COMPANY_COMPANYMENU_H_
#define INC_COMPANY_COMPANYMENU_H_
// -------------------------- Function Declarations ----------------------------
/**
 * @brief Displays the company menu and handles user input for various operations.
 *
 * This function allows authorized users to interact with a company.
 * Users can perform operations such as adding, updating and deleting data.
 *
 * @param dataBase - A pointer to the storage system database.
 * @return Updated pointer to the storage system database.
**/
Storage *companyMenu(Storage *dataBase);

/**
 * @brief Executes a specific feature based on the user-selected option in the company menu.
 *
 * This function is called from the main company loop and performs actions based on the user's choice.

 * @param dataBase - A pointer to the storage system database.
 * @param index - The index of the authenticated company user in the database
 * @param option - The user-selected option from the company menu.
 * @return Updated pointer to the storage system database.
**/
Storage *companyMenuFeature(Storage *dataBase, int index, int option);



#endif //INC_COMPANY_COMPANYMENU_H_
