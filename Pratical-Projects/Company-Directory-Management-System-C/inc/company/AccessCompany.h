/**
 * @file        AccessCompany.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of access company.
 * @date        29/11/2023
**/
#ifndef INC_COMPANY_ACCESSCOMPANY_H_
#define INC_COMPANY_ACCESSCOMPANY_H_
// -------------------------- Function Declarations ----------------------------
/**
 * @brief Access the company menu.
 *
 * This function outputs to the user what he wants to do in the
 * company menu.
 *
 * @param dataBase - A pointer to a Storage structure containing the company list.
 * @param index - The index of the company on the list.
 * @return Returns the updated company list.
 */
Storage *accessCompany(Storage *dataBase, int index);


#endif //INC_COMPANY_ACCESSCOMPANY_H_
