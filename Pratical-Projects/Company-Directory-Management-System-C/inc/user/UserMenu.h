/**
 * @file        UserMenu.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the user menu.
 * @date        01/12/2023
**/

#ifndef INC_USER_USERMENU_H_
#define INC_USER_USERMENU_H_
// ----------------------- Function Declarations -------------------------------
/**
 * @brief Displays and manages the main user menu.
 * 
 * This function continuously displays the main user menu and processes the user's choices. 
 * It delegates the execution of specific features to the userMenuFeature function based on the user's selection. 
 * 
 * @param dataBase - A pointer to the storage structure where the database is stored.
 * @return Returns the updated database after processing the user's choices in the menu.
 **/
Storage *userMenu(Storage *dataBase);

/**
 * @brief Executes specific features based on the user's menu selection.
 * 
 * This function handles the execution submitting searches, ratings, and comments 
 * based on the user's choice in the main menu. 
 * 
 * @param dataBase - A pointer to the storage structure where the database is stored.
 * @param option - An integer representing the user's choice from the main menu.
 * @return Returns the updated database after executing the selected feature.
 **/
Storage *userMenuFeature(Storage *dataBase, int option);



#endif //INC_USER_USERMENU_H_
