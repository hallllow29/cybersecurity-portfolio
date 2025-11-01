/**
 * @file        ManageReport.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for report management functions in the application.
 * @date        29/11/2023
**/

#ifndef INC_ADMIN_MANAGEREPORTS_H_
#define INC_ADMIN_MANAGEREPORTS_H_

// ----------------------- Function Declarations -------------------------------
/**
 * @brief Manges the companies reports
 *
 * This function prompts the user to select which report he wants to see.
 *
 * @param dataBase - Pointer to a Storage structure containing the company data.
 * @return Returns the updated data base.
**/
Storage *manageReport(Storage *dataBase);

/**
 * @brief Switch case report feature.
 *
 * This function is the switch case depending on the user input it will go
 * to the menu that the user choose.
 *
 * @param dataBase - A pointer to a Storage structure containing the company data.
 * @param option - The option that the user choose to do.
 * @return Returns the updated data base.
**/
Storage *manageReportFeature(Storage *dataBase, int option);

/**
 * @brief Displays the top five companies based on user ratings.
 *        This function sorts and presents the companies with the highest ratings
 *        given by users, showcasing the top five among them.
 * @param dataBase Pointer to the Storage structure containing company data.
**/
void displayTopFiveCompanies(Storage *dataBase);

/**
 * @brief Displays a list of companies ordered by the number of times they have been searched, 
 *        specifically based on search criteria like NIF, Name, and Locality.
 *        This function ranks companies according to their search frequency in these categories 
 *        and presents them in a sorted list.
 * @param dataBase Pointer to the Storage structure containing company data.
**/
void displayMostSearchedCompanies(Storage *dataBase);

/**
 * @brief Compares the ratings of two companies.
 *
 * This function compares the average of two companies and returns an integer
 * indicating the order which they should be displayed.
 *
 * @param companyA - A pointer to the firsts company.
 * @param companyB - A pointer to the second company.
 *
 * @return Returns an integer depending on what is the company that have more average.
**/
int compareCompaniesRating(const void *companyA, const void* companyB);

/**
 * @brief Generates a search analytics report.
 *
 * This function prints outs the search analytics for a specific company
 * at a given index.The report includes the name of the company and the number
 * of searches performed for various attributes like NIF, name, and locality. If the
 * provided index is out of bounds (either negative or beyond the number of companies
 * in the storage), it prints an error message.
 *
 * @param storage - A pointer to a storage structure.
 * @param index - The index of the company to be displayed.
 */
void CompanySearchReport(Storage *storage, int index);


#endif // INC_ADMIN_MANAGEREPORTS_H_
