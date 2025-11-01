/**
 * @file        AccessComments.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file to access comments.
 * @date        30/11/2023
**/
#ifndef INC_COMPANY_ACCESSCOMMENTS_H_
#define INC_COMPANY_ACCESSCOMMENTS_H_
// -------------------------- Function Declarations ----------------------------

/**
 * @breif Access comment menu
 *
 * This function outputs the comments menu and let the user choose
 * what he wants to do with the comment he selected.
 *
 * @param dataBase - A pointer to a Storage structure containing the company list.
 * @param index - The index of the company on the list.
 * @return Returns the updated company list.
 */
Storage *accessComment(Storage *dataBase, int index);
#endif //INC_COMPANY_ACCESSCOMMENTS_H_
