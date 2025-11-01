/**
 * @file        SubmitComment.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the submi comment.
 * @date        29/11/2023
**/

#ifndef INC_USER_SUBMITCOMMENT_H_
#define INC_USER_SUBMITCOMMENT_H_


// -------------------------- Function Declarations ----------------------------
/**
 * @brief Submits a comment to the database.
 *
 * This function allows a user to submit a comment for a specific company.
 *
 * @param dataBase - A pointer to the storage structure where the database is stored.
 * @return Returns the updated database after adding the comment.
 **/
Storage *submitComment(Storage *dataBase);

/**
 * @brief Handles the comment submission feature for a company.
 *
 * This function provides options for the user to submit a comment for a company.
 *
 * @param dataBase - A pointer to a storage structure that as the database..
 * @param option -  The option the user choose to submission a comment.
 *
 * @return A pointer to the updated company database.
 */
Storage *submitCommentFeature(Storage *dataBase, int option);

/**
 * @brief Submits a comment for a specific company.
 *
 * This function allows a user to submit a comment for a specific company in the database.
 *
 * @param dataBase - A pointer to a storage structure that as the database.
 *
 * @return A pointer to the updated company database.
 */
Storage *submitCommentCompany(Storage *dataBase);

/**
 * @brief Handles the comment submission for a company.
 *
 * This function provides options for the user to submit a comment for a company.
 *
 * @param companyList A pointer to the company database.
 * @param index       The index of the company to submit a comment for.
 * @param option      The user's choice of comment submission option.
 *
 * @return A pointer to the updated company database.
 */
Storage *commentFeature(Storage *companyList, int index, int option);

/**
 * @brief Creates a new list of comments for a company.
 *
 * This function creates a new list of comments for a specific company.
 *
 * @param dataBase A pointer to the company database.
 * @param index    The index of the company to create a comment list for.
 * @param size     The size of the new comment list.
 *
 * @return A pointer to the updated company database.
 */
Storage *newCommentList(Storage *dataBase, int index, int size);

/**
 * @brief Adds a new comment to a company's comment list.
 *
 * This function adds a new comment to the comment list of a specific company.
 *
 * @param companyList A pointer to the company database.
 * @param index       The index of the company to add the comment to.
 * @param commentID   The ID of the new comment.
 *
 * @return A pointer to the updated company database.
 */
Storage *newCOMMENT(Storage *companyList, int index, int commentID);
#endif //INC_USER_SUBMITCOMMENT_H_
