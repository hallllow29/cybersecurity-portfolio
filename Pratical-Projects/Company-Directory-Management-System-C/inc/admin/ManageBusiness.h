/**
 * @file        ManageBusiness.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for manage business.
 * @date        27/11/2023
**/

#ifndef INC_ADMIN_MANAGEBUSINESS_H_
#define INC_ADMIN_MANAGEBUSINESS_H_

enum businessMenu {
    addBusiness = 1,
    listBusiness = 2,
    editBusinessStatus = 3,
    removeBusiness = 4,

};
// -------------------------- Function Declarations ----------------------------
/**
 * @brief Manages business operations on a database.
 *
 * This function serves as an interface for managing a business database.
 * It provides a menu system allowing the user to select various operations.
 *
 * @param dataBase - A pointer to a storage structure containing the business list.
 *
 * @return Returns the updated Storage pointer after performing operations.
**/
Storage *manageBusiness(Storage *dataBase);

/**
 * @brief Handles the business management.
 *
 * This function is responsible for executing various business management based
 * on the user input option.
 *
 * @param option - An integer representing the user selected option
 * @param companyList - A pointer to a Storage structure.
 *
 * @return Returns the updated business list.
**/
Storage *manageBusinessFeature(Storage *companyList, int option);

/**
 * @brief Insert a new business.
 *
 * This function adds a new business into the business list. It first expands
 * the list to accommodate a new business.
 *
 * @param businessList - A pointer to a Storage structure that contains that list
 * of business.
 *
 * @return Returns a pointer to the updated Storage structure containing the list
 * of business.
**/
Storage *catalogBusinessInsert(Storage *dataBase);

/**
 * @brief List the business list.
 *
 * This function allows to list the available list of business. If it is
 * empty it will prompt that the list is empty.
 *
 * @param businessList - A pointer to a storage structure that contains the list
 * of business.
**/
void catalogBusinessList(Storage *businessList);

/**
 * @brief Allocates or reallocates memory for a list of companies.
 *
 *  This function either initializes a new company list if the provided list
 *  is NULL, or reallocates memory to expand an existing list. It handles
 *  memory allocation for both the list of companies and the individual
 *  company structures within it.
 *
 * @param companyList - A pointer to a Storage structure, which may be NULL
 * if the list is being created for the first time or an existing list that
 * needs to be expanded.
 *
 * @param size - The new size for the company list, which is used to determine
 * the amount of memory to allocate.
 *
 * @return Returns a pointer to the allocated Storage structure.
**/
Storage *newBusinessList(Storage *businessList, int size);

/**
 * @brief Adds a new business to the business list on a specific index.
 *
 * This function initializes a new business on the existing business list.
 * It ask for the name of the business and automatically set the business as
 * inactive and not in use.
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 * @param index - Ths index in the business list where the new business will be add.
 *
 * @return Returns the updated business list.
**/
Storage *newBusiness(Storage *businessList, int index);

/**
 * @brief Menu to remove business data.
 *
 * This function will prompt the user what is the index of the business he wants to remove
 * and will check if the index is OK.
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 *
 * @return Returns the updated business list.
**/
Storage *selectBusinessToRemove(Storage *businessList);

/**
 * @brief Manage the deletion of a business based on specific conditions.
 *
 * This function processes the deletion of a business from the business list
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 * @param index - The index of the business in the list that will be removed.
 * @param option - Option the user choose (remove or exit).
 *
 * @return Returns the updated business list.
**/
Storage *deleteBusinessFeature(Storage *businessList, int index, int option);

/**
 * @brief Delete a business.
 *
 * This function deletes a business from the business list if the input name matches the
 * mame of the business at the specified index.
 *
 * @param companyList - A pointer to a Storage structure that contains the business list.
 * @param index - The index of the business in the list to be removed.
**/
void businessDELETE(Storage *businessList, int index);

/**
 * @brief Choose business.
 *
 * This function allows the user to choose a business to is newly created company
 * according to the index of the business.
 *
 * @param companyList - A pointer to the Storage structure that contains the company list.
**/
int businessSELECT(Storage *companyList);

/**
 * @brief Edit feature business.
 *
 * This function will check what feature the user wants to edit.
 *
 * @param businessList - A pointer to a Storage structure containing the business list.
 * @param index - The index where the business is on the list.
 * @param option - The option the user wants to do (edit or exit).
 * @return Returns the index of the business that the user choose.
 */

Storage *editFeatureBusiness(Storage *businessList, int index, int option);

/**
 * @brief Edit business data.
 *
 * This function will show what's  the business the list have and then ask
 * what business he wants to edit based on the indexed business.
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 * @Return Returns teh business list updated.
**/
Storage *editBusinessData(Storage *businessList);

/**
 * @brief Saves the business list to a file.
 * 
 * This function writes the business list to a binary file specified by the filename. 
 * It iterates through each business in the list and writes its data to the file.
 * 
 * @param businessList - A pointer to the storage structure where the business list is stored.
 * @param filename - The name of the file where the business list will be saved.
 * @return Returns the business list after saving it to the file.
 **/
Storage *catalogBusinessSave(Storage *businessList, char *filename);


/**
 * @brief Loads the business list from a file.
 * 
 * This function reads the business list from a binary file by the filename. 
 * It updates the business list in memory with the data read from the file.
 * 
 * @param businessList - A pointer to the storage structure where the business list is stored.
 * @param filename - The name of the file from which the business list will be loaded.
 * @return Returns the updated business list after loading it from the file.
 **/
Storage *catalogBusinessLoad(Storage *businessList, char *filename);


/**
 * @brief Removes data from the business list.
 * 
 * This function facilitates the removal of a business's data based on a specified index. 
 * It allows the user to select different features or options related to the removal process 
 * and applies these changes to the business list. The process continues until the user 
 * decides to exit the removal menu or until the business is marked as inactive.
 * 
 * @param businessList - A pointer to the storage structure where the business list is stored.
 * @param businessIndex - The index of the business to be removed.
 * @return Returns the updated business list after applying the removal changes.
**/
Storage *removeBusinessData(Storage *businessList, int businessIndex);


#endif //INC_ADMIN_MANAGEBUSINESS_H_
