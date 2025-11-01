/**
 * @file        SubmitRating.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the submit rating.
 * @date        29/11/2023
**/

#ifndef INC_USER_SUBMITRATING_H_
#define INC_USER_SUBMITRATING_H_
/**
 * @brief Manages the submission of ratings for companies.
 * 
 * This function guides the user through the process of submitting ratings for companies stored in the database.
 * It includes checking if the company list is empty and displaying relevant menus for rating submission.
 * 
 * @param dataBase - A pointer to the storage structure where the database of companies is stored.
 * @return Returns the updated database after the rating submission process is complete.
 **/
Storage *submitRating(Storage *dataBase);

/**
 * @brief Handles the selection of features for company rating.
 * 
 * This function allows users to choose specific features or actions (like searching or listing companies) 
 * for rating companies. It redirects to appropriate functions based on the user's choice.
 * 
 * @param dataBase - A pointer to the storage structure where the database of companies is stored.
 * @param option - An integer representing the user's chosen option for the rating feature.
 * @return Returns the updated database after processing the selected rating feature.
 **/
Storage *submitRatingFeature(Storage *dataBase, int option);


/**
 * @brief Submit rating for a specific company.
 * 
 * This function enables the user to select a company by index and submit ratings for it. 
 * It includes displaying company data and managing the rating submission process.
 * 
 * @param companyList - A pointer to the storage structure where the list of companies is stored.
 * @return Returns the updated list of companies after ratings have been submitted for a specific company.
 **/
Storage *submitRatingCompany(Storage *companyList);


/**
 * @brief Manages the rating options for a selected company.
 * 
 * This function processes the user's choice of rating options for a specific company.
 * It includes setting the rating and handling other user interactions.
 * 
 * @param companyList - A pointer to the storage structure where the list of companies is stored.
 * @param index - The index of the company being rated.
 * @param option - The user's chosen rating option.
 * @return Returns //TODO: return
 **/
Storage *ratingFeature(Storage *companyList, int index, int option);


#endif //INC_USER_SUBMITRATING_H_
