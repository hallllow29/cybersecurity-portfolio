/**
 * @file        ManageCatalog.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for the manage catalog (Manage companies)
 * @date        29/11/2023
**/
#ifndef INC_ADMIN_MANAGECATALOG_H_
#define INC_ADMIN_MANAGECATALOG_H_

/**
 *
 */



// ----------------------- Function Declarations -------------------------------
/**
 * @brief Manages the main catalog.
 *
 * This function presents the catalog management menu and handles user input
 * for various operations.
 *
 * @param dataBase - A pointer to the storage structure holding the current catalog data.
 * @return A pointer to `Storage`, representing the updated catalog after processing user choices.
 */
Storage *manageCatalog(Storage *dataBase);

/**
 * @brief Processes specific catalog management features.
 *
 * Based on the user's selection, this function can add, list, search, edit, or remove companies from the catalog.
 *
 * @param companyList - The current catalog of companies.
 * @param option - The user-selected feature to be executed.
 *
 * @return A pointer to `Storage` with the updated catalog after executing the chosen feature.
 */
Storage *manageCatalogFeature(Storage *companyList, int option);

/**
 * @brief Inserts a new company into the catalog.
 *
 * This function adds a new company to the current catalog of companies.
 *
 * @param companyList - The current catalog of companies.
 *
 * @return A pointer to `Storage` with the updated catalog including the newly added company.
 */
Storage *catalogCompanyInsert(Storage *companyList);

/**
 * @brief Allocates or reallocates the company list to a new size.
 *
 * This function adjusts the size of the company list, either by allocating or reallocating memory.
 *
 * @param companyList - The current catalog of companies.
 * @param size - The new size for the company list.
 *
 * @return A pointer to `Storage` with the resized company list.
 */
Storage *newCompanyList(Storage *companyList, int size);

/**
 * @brief Adds a new company.
 *
 * This function inserts a new company into the company list at a given index.
 *
 * @param companyList - The current catalog of companies.
 * @param index - The index where the new company will be added.
 *
 * @return A pointer to `Storage` with the updated catalog including the new company.
 */
Storage *newCompany(Storage *companyList, int size);

/**
 * @brief Displays the list of companies in the catalog.
 *
 * This function outputs the current list of companies in the catalog.
 *
 * @param companyList - The current catalog of companies.
 */
void catalogCompanyList(Storage *companyList);

/**
 * @brief Catalog company search.
 *
 * This function will check if there is companies on the list and if so
 * let the user enter the search menu.
 *
 * @param companyList - A pointer to a structure that contains the company list.
**/
void catalogCompanySearch(Storage *companyList);

/**
 * @brief Display the menu search.
 *
 * This function displays the menu search function and prompts the user to choose for what
 * he wants to search.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
**/
void companySearchFeature(Storage *companyList);

/**
 * @brief Switch-case search feature.
 *
 * This function is a switch-case that depending on the user option
 * will search for.
 *
 * @param companyList - A pointer to a storage structure where the company is stored.
 * @param option - The option that the user choose.
 * @return Returns the option the user input.
**/
int searchFeatureCompany(Storage *companyList, int option);

/**
 * @brief Select a company to remove.
 *
 * This function will ask the user what company he wants to remove
 * based on the index of the company.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @return Returns the updated company list.
**/
Storage *selectCompanyRemove(Storage *companyList);

/**
 * @brief Delete a company feature
 *
 * This function will delete a company feature if the company does not have
 * any comments.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @param option - The option the user choose.
 * @param index - The index of the company on the list.
 * @return Returns the updated company list.
**/
Storage *deleteFeature(Storage *companyList, int index, int option);

/**
 * @brief Saves the current state of the company catalog to a file.
 *
 * This function writes the data of all companies in the catalog, including their comments, 
 * to a binary file specified by the filename. The function iterates through each company 
 * and writes its data and associated comments to the file.
 *
 * @param companyList - A pointer to the current list of companies in the catalog.
 * @param filename - A string representing the name of the file to which the data will be saved.
 *
 * @return A pointer to `Storage`, representing the catalog after saving the data.
 */
Storage *catalogCompanySave(Storage *companyList, char *filename);

/**
 * @brief Loads the company catalog data from a file.
 *
 * This function reads the data of companies, including their comments, from a binary file 
 * specified by the filename. It allocates memory for the companies and their comments and 
 * populates the catalog with this data. If the file is not found or empty, the function 
 * returns the original company list without changes.
 *
 * @param companyList - A pointer to the current list of companies in the catalog.
 * @param filename - A string representing the name of the file from which the data will be loaded.
 *
 * @return A pointer to `Storage`, representing the catalog after loading the data. Returns the 
 *         original company list if the file is empty or not found.
 */
Storage *catalogCompanyLoad(Storage *companyList, char *filename);

/**
 * @brief Allocate a comment in a array.
 * 
 * This function allocates memory for an array of Comment structures, with the number of elements
 * specified by the 'size' parameter. It uses calloc to initialize all elements to zero.
 * 
 * @param size - The number of Comment structures to be allocated in the array.
 * @return Returns a pointer to the allocated array of Comment structures. If the allocation fails, returns NULL.
 */
Comment *alloc_COMMT_array(int size);

/**
 * @brief Select a feature to edit
 *
 * This function let the user select what feature he wants to edit.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @param index - The index of the company list on the list.
 * @return Returns the company list updated.
 */
Storage *selectFeatureToEdit(Storage *companyList, int index);

/**
 * @brief Select option to edit comment
 *
 * This function will prompt a menu and the user will choose if he wants to
 * delete, hide or list comments.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @param index - The index of the company on the list.
 * @return Returns the updated company list.
 */

Storage *selectCommentOptionEdit(Storage *companyList, int index);

/**
 * @brief Clean the comment data.
 *
 * This function cleans all the data comments putting all blanc and null;
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @param index - The index of the company on the list.
 * @param id - The id of the comment data to be wiped.
 * @return Returns the updated company list.
 */
Storage *commentDELETE(Storage *companyList, int index, int id);

/**
 * @brief Select a company to edit.
 *
 * This function will prompts the user to select the company he wants to edit
 * data based on the index of the company.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @return Returns the updated company list.
**/
Storage* selectCompanyToEdit (Storage *companyList);

/**
 * @brief company edit switch case.
 *
 * This function is a switch-case menu that will select the option the user choose.
 *
 * @param companyList - A pointer to a structure where the company list is stored.
 * @param option - The option the user choose to edit.
 * @param index - The index of the company on the list.
 * @return Returns the updated company list.
**/
Storage *editFeatureCompany(Storage *companyList, int index, int option);

/**
 * @brief Select a company to edit comments.
 *
 * This function prompts the user to select a company that he wants to edit comments
 * and checks if the company has comments.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @return Returns the company list updated.
**/
Storage *selectCompanyEditComments(Storage *companyList);

/**
 * @brief Select a comment to delete.
 *
 * This function let the user select the comment he wants to edit based on the index of
 * the comment.
 *
 * @param companyList - A pointer to a storage a structure where the company list is stored.
 * @param index - The index of the company on the list.
 * @return Returns the updated company list.
 */
Storage *selectCommentToDelete(Storage *companyList, int index);

/**
 * @brief Switch case comment menu.
 *
 * This function will act as a switch-case depending on the user input.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @param index - The index of the company on the list.
 * @param option - The option that the user choose.
 * @return Returns the updated company list.
 */
Storage *commentMenuEdit(Storage *companyList, int index, int option);

/**
 * @brief Hide a comment.
 *
 * This function will let the user to hide a specific comment for a specific company.
 *
 * @param companyList - A pointer to a storage structure where the company list is stored.
 * @param index - The index of the company on the list.
 * @return Returns the company list updated.
 */
Storage *hideAndShowComment(Storage *companyList, int index);

/**
 * @brief Add a business to the company.
 *
 * This function will add a business to a company and associated the business to that company.
 *
 * @param companyList - A pointer to a storage structure where the company list os located.
 * @param businessIndex - The index of the business the user selected on the list.
 * @param index - The index of the company in the list.
 * @return Returns the company list updated.
 */
Storage *addBusinessToCompany(Storage *companyList, int businessIndex, int index);

/**
 * @brief Initializes the search count
 *
 * This function initializes the search count for a company so we can increment
 * how many times the company as been searched.
 *
 * @param companyList - A pointer to a storage structure where the company list is located.
 * @param index - The index number of the company in the list.
 * @return Returns the updated company list.
 */
Storage *initializeSearchCount(Storage *companyList, int index);

/**
 * @brief Confirms to delete company
 *
 * This function will confirm if the user really want's to remove the company asking the
 * user re-type the name of the company he wants to delete.
 *
 * @param companyList - A pointer to the storage structure where the company list is located.
 * @param index - The index number of the company in the list.
 * @return Return the updated company list.
 */
Storage *removeCompanyData(Storage *companyList, int index);

/**
 * @brief Checks and handles the presence of a business in the business list.
 * 
 * This function displays the business list and prompts the user to check if a business is on the list. 
 * If the user selects an option indicating the business is not on the list, it initiates the process 
 * to insert a new business into the list.
 * 
 * @param companyList - A pointer to the storage structure where the business list is stored.
 * @return Returns the updated business list, potentially with a new business added.
 **/
Storage *businessOnList(Storage *companyList, int index);


#endif //INC_ADMIN_MANAGECATALOG_H_

