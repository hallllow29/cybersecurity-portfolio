/**
 * @file        MainMenu.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for the main menu.
 * @date        27/11/2023
**/
#ifndef MAINMENU_H_
#define MAINMENU_H_
// -------------------------- Function Declarations ----------------------------
/**
 * @brief Main menu.
 *
 * This function displays the main menu and let the user choose what menu
 * he wants to go.
 * @return Return the option the user choose.
**/
int mainMenu();

/**
 * @brief Function containing four submenus, one of them saves the dataBase and frees the prior allocated memory of the struct pointers.
 * @param option - Option the users choose based on what he wants to do on main menu feature.
 * @param dataBase - A point to a structure.
 * @return Returns the option the user choose.
**/
int mainMenuFeature(Storage *dataBase, int option);

/**
 * @brief This function initiates the storage structure, as well as the respective struct pointers and counters.
 * @param dataBase A struct pointer.
 * @return dataBase The data structure to work on.
**/
Storage *initLoadDataBase(Storage *dataBase);

/**
 * @brief Save data and free the memory.
 *
 * This function will allow to save the stack memory into a file and
 * frees the allocated memory for the struct pointer.
 *
 * @param dataBase The struct pointer.
**/
void saveData_FreeMemory(Storage *dataBase);

/**
 * @brief Frees memory from every comment list.
 *
 * This function will allow to free every comment list of each company.
 *
 * @param data The struct pointer.
**/
void freeCommentList(Storage *dataBase);

#endif //MAINMENU_H_
