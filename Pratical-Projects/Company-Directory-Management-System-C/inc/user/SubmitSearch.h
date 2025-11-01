/**
 * @file        SubmitSearch.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file o the submit search.
 * @date        01/12/2023
**/
#ifndef INC_USER_SUBMITSEARCH_H_
#define INC_USER_SUBMITSEARCH_H_
// -------------------------- Function Declarations ----------------------------

/**
 * @brief Manages the search process for companies in the database.
 * 
 * This function allows the user to search for companies based on specific criteria. It handles the search process, 
 * including displaying the search menu and processing user input for the search criteria.
 * 
 * @param dataBase - A pointer to the storage structure where the database of companies is stored.
 **/
void submitSearch(Storage *dataBase);

/**
 * @brief Performs a search based on a specific feature within the company list.
 * 
 * This function facilitates searching for companies based on selected criteria such as NIF, name, or location.
 * It updates the search count for each company when they are found and displays their data and comments.
 * 
 * @param companyList - A pointer to the storage structure where the list of companies is stored.
 * @param option - An integer representing the user's chosen search option.
 * @return Returns the updated list of companies after the search is performed.
 **/
Storage *submitSearchFeature(Storage *companyList, int option);

#endif //INC_USER_SUBMITSEARCH_H_
