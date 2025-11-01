/**
 * @file        AdminMenu.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the admin menu.
 * @date        27/11/2023
**/

#ifndef INC_ADMIN_ADMINMENU_H_
#define INC_ADMIN_ADMINMENU_H_
// -------------------------- Function Declarations ----------------------------
/**
 * @brief Admin Menu handler.
 *
 * This function displays the admin menu and processes user input
 * to navigate trough various admin features.
 *
 * @param dataBase - A pointer to a Storage structure.
 *
 * @return Returns the updated database containing the company list and business list.
**/
Storage *adminMenu(Storage *dataBase);

/**
 * @brief Executes the feature corresponding to the selected admin menu option.
 *
 * This function handles the execution of various admin tasks based on the option selected
 * by the user. It directs to different functions like managing companies, business and reports.
 *
 * @param dataBase - A pointer to the storage structure containing the company list and business list information.
 * @param option - An integer representing the user option on the admin menu.
 * @return Returns the updated database containing the company list and business list.
**/
Storage *adminMenuFeature(Storage *dataBase, int option);
#endif //INC_ADMIN_ADMINMENU_H_
