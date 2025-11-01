/**
 * @file        ManageReport.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for report management functions in the application.
 * @date        29/11/2023
**/


#ifndef LP_PROJETO_MANAGEREPORT_H
#define LP_PROJETO_MANAGEREPORT_H
/**
 * @brief Access report to compare 2 companies
 *
 * This function will prompts the user to enter a name of a rival company
 * and it will show the status of is company and the rival company.
 *
 * @param dataBase - A storage structure containing the company list.
 * @param index - The index of the company on the list.
 */
void accessReport(Storage *dataBase, int index);

/**
 * @brief Displays the search report
 *
 * This function displays how many times the company got searched.
 *
 * @param storage - A storage structure containing the company list.
 * @param index - The index of the company on the list.
 */
void CompanySearchReport(Storage *dataBase, int index);

#endif //LP_PROJETO_MANAGEREPORT_H
