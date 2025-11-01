/**
 * @file        Input.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the input.
 * @date        02/12/2023
**/

#include "../inc/struct/CompanyStructure.h"
#include "../inc/Output.h"

#ifndef INC_INPUT_H_
#define INC_INPUT_H_
// -------------------------- Function Declarations ----------------------------
typedef enum {
  manageCatalogCompanies = 1,

  addCompanyFeature = 1,
  deleteComment = 1,
  searchCompanyByNif = 1,
  editNIF = 1,
  topFiveCompanies = 1,
  searchCommentCompany = 1,
  searchRatingCompany = 1,
  addComment = 1,

  listCompaniesFeature = 2,
  hideAComment = 2,
  searchCompanyByName = 2,
  editName = 2,
  manageCatalogBusinesses = 2,
  mostSearchedCompanies = 2,
  listRatingCompanies = 2,
  listCommentCompanies = 2,

  manageCatalogReports = 3,
  searchCompanyFeature = 3,
  commentDisplay = 3,
  searchCompanyByCategory = 3,
  editCategory = 3,
  searchCompanyLocal = 3,


  removeCompanyFeature = 4,
  searchCompanyByBusiness = 4,
  editBusiness = 4,

  editCompanyFeature = 5,
  searchCompanyByStreet = 5,
  editStreet = 5,

  editCompanyCommentsFeature = 6,
  editLocal = 6,
  searchCompanyByLocal = 6,

  searchCompanyByPostal = 7,
  editPostalCode = 7,

  editStatus = 8,

  backMenu = 9,
  exitProgram = 0,

} option;

void cleanInputBuffer();

/**
 * @brief Searches for the index of a company.
 *
 * This function iterates through a Storage structure (assumed to be a collection of company data)
 * to find the index of a specific data type (like NIF, name, category, business type, street,
 * postal code, or local) that matches the input provided. The function compares the input with
 * each element of the company list and returns the index where a match is found.
 *
 * @param companyList - A pointer to a storage structure.
 * @param input - The specific data to be searched for.
 * @return Returns the index of the company list, returns -1 if there is no company associated.
 */
int getCompanyINDEX(Storage *companyList, char *input);

/**
 * @brief Short a input.
 *
 * This function is designed to modify a string by removing the newline character ('\n')
 * if it is present at the end of the string. It checks the length of the string and then
 * replaces the last character with a null terminator ('\0') if that character is a newline.
 * This is commonly used for strings that have been read from input streams where the newline
 * character is included at the end of the input.
 *
 * @param string - A pointer of a char array that will be modified.
 * @return Returns the same string passed as a parameter.
 */
char *reduceStringBUFFER(char *string);

/**
 * @brief Searches for a specific data on the company.
 *
 * This function will search for a specific data on the company based on what the user
 * wants to search.
 *
 * @param companyList - A pointer to a storage structure.
 * @param dest - A pointer of char array that will specified what type of data it will search for.
 * @param size - The size of the input buffer.
 * @param info - The message that will display so the user can search for.
 * @param forUser - An integer flag indicating wheter the search if for admin for user.
 * @return Returns the index of the company found. If not returns -1 if the data is not found in any company.
 */
int searchCompanyDATA(Storage *companyList, char *dest, char *info, int forUser);

/**
 * @brief Check if the inserted NIF is valid
 *
 * This function  validates the NIF entered by the user.
 *
 * @param nif - The NIF to be validated.
 * @return Returns check if the NIF is valid, otherwise return 0.
**/
int isValidNIF(int nif);

/**
 * @brief Check if the input is a valid category.
 *
 * This function will check if the input the user did is valid category based
 * on the available categories.
 *
 * @param input - A pointer of the input the user put.
 * @return Returns 1 if the category is valid and 0 if no valid.
 */
int isValidCATG(char *input);

/**
 * @brief Validates if the input is correct.
 *
 * This function checks the validity of the user input.
 *
 * @param input - The user input to be validated.
 * @return Return check if the input is valid, otherwise return 0.
**/
int isValidINPUT(char *input);

/**
 * @brief Validates if the street is correct.
 *
 * This function validates if the street entered by user is correct.
 *
 * @param input - The street input to be validated.
 * @return Return check if the street is valid, otherwise return 0.
**/
int isValidSTREET(char *input);

/**
 * @brief Validates if the local is correct.
 *
 * This function validates if the local entered by the user is correct.
 *
 * @param input - The local input to be validated.
 * @return Return check if the local is valid, otherwise return 0.
**/
int isValidLOCAL(char *input);

/**
 * @brief Set the status of the company in the specified index.
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param index - The index of the company list.
 */
void setCompanySTATUS(Storage *companyList, int index);

/**
 * @brief Validates if the postal code is correct.
 *
 * This function validates if the postal code entered by the user is correct.
 *
 * @param input - The postal code input to be validated.
 * @return Return check if the postal code is valid, otherwise return 0.
**/
int isValidPOSTAL(char *input);

/**
 * @brief Delete a company from a specified index in the company list.
 *
 * This function removes a company from the company list at the specified index.
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param index - The index of the company to be deleted.
**/
int companyDELETE(Storage *companyList, int index);

/**
 * @brief Sets the rating of a company at a specified index.
 *
 * This function allows you to set a rating for a company in the given company list.
 *
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param index - The index of the company to be rated.
**/
void setRATING(Storage *companyList, int index);

/**
 * @brief Sets the title for a comment at the specified index and comment ID.
 *
 * This function allows you to set the title of a comment for a specific company in the given company list.
 *
 * @param index - The index of the company in the list.
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param id - The ID of the comment for which the title is being set.
**/
void setCommentTITLE(Storage *companyList, int index, int id);

/**
 * @brief Sets the username of a commenter for a comment at the specified index and comment ID.
 *
 * This function allows you to set the username of a commenter for a specific comment on the company list in the given company list.
 *
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param id - The ID of the comment for which the username is being set.
 * @param  index - The index of the company in the list.
**/
void setCommentNAME(Storage *companyList, int index, int id);

/**
 * @brief Sets the email of a commenter for a comment at the specified index and comment ID.
 *
 * This function allows you to set the email of a commenter for a specific comment on the company list in the given company list.
 *
 * @param index - The index of the company in the list.
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param id - The ID of the comment for which the email is being set.
**/
void setCommentEMAIL(Storage *companyList, int index, int id);

/**
 * @brief Sets the message content of a comment at the specified index and comment ID.
 *
 * This function allows you to set the message content of a specific comment on the company.
 *
 * @param id - The ID of the comment for which the text is being set.
 * @param companyList - A pointer to the storage structure containing the company list.
 * @param index - The index of the company in the list.
**/
void setCommentMESSAGE(Storage *companyList, int index, int id);

/**
 * @brief Company menu sign in
 *
 * This function will ask the user to input the name of his
 * company so he can sign in into the company menu.
 *
 * @param companyList - A pointer to a storage structure that contains the company list.
 * @return Returns the index of the company to sign in.
**/
int companySignIn(Storage *companyList);

/**
 * @brief Search for a comment ID.
 *
 * This function iterates through the comments of a specific company.
 * If the comment id is found, the function mark it as hidden and changes
 * the status to "Inactive".
 *
 * @param companyList - A pointer to a Storage structure.
 * @param index - The index of the company.
 * @param targetID - The id of the comment to be searched.
 * @param option - An integer representing the action to be performed if it is 1 is for hiding comment and 0 for show comment.
 * @return Returns 1 if the comment is found and updated it, returns 0 if the comment is not found.
 */
int commentSEARCH(Storage *companyList, int index, int targetID, int option);

/**
 * @brief Set the business status.
 *
 * This function will let the user choose if he wants to active or inactive a business.
 *
 * @param businessList - A pointer to a storage structure that contains the business list.
 * @param businessIndex - The index of the business on the list.
 */
void setBusinessSTATUS(Storage *businessList, int businessIndex);

/**
 * @brief Read a string from the input.
 * @param string - A pointer to her the string will be stored.
 * @param size - The maximum size of the string.
 * @param msg - Message to be displayed when the user type the string.
 */
void readString(char *string, unsigned int size, char *msg);

/**
 * @brief Check NIF in use
 *
 * This function will check if the NIF is already in use on
 * a company
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - The NIF that the user input.
 * @return Returns 1 if the NIF is not in use and 0 if the NIF already exist.
 */
int isNIFinUse(Storage *companyList, char *input);

/**
 * @biref Check name in use
 *
 * This function will check if the name of a company is already
 * in use or not.
 *
 * @param company - A pointer so a Storage structure containing the company list.
 * @param input - The name of the company the user input.
 * @return Returns 1 if the name is not in use and returns 0 if the name is in use.
 */
int isNAMEinUse(Storage *company, char *input);

/**
 * @brief Reads an int from the input.
 *
 * This function prompts the user to enter an integer and then it will validates
 * depending on the min value and max value.
 *
 * @param minValue - The minimum value of the input.
 * @param maxValue - THe maximum value of the input.
 * @param withMenu - The flag indicate what menu is.
 * @return The validated integer.
 */
int getValidatedIntWithMenu(int minValue, int maxValue, int withMenu);

/**
 * @brief Reads a double value.
 *
 * This functions prompts the user to enter a double value and then checks if the value is
 * valid depending on the min value and max value.
 *
 * @param minValue - The minimum acceptable value.
 * @param maxValue - The maximum acceptable value.
 * @return Return the validated double.
 */
double getDouble(double minValue, double maxValue);

/**
 * @brief Clean company data.
 *
 * This function will wipe every data of a specific company.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param index - The index of the company to be wiped.
 */
void companyCleanDATA(Storage *companyList, int index);

/**
 * @breif Set the NIF.
 *
 * This function will set the NIF for a company on the list, and
 * will check if the NIF is valid or not.
 *
 * @param companyList - A pointer to a Storage structure.
 * @param dest - A char pointer where the valid NIF will be stored.
 * @param msg - A message prompt to be displayed.
 */
void setCompanyNIF(Storage *companyList, char *dest, char *msg);

/**
 * @brief Set the name of a company.
 *
 * This function will set the name for a company on the list , and will
 * check if the name is valid or already in use.
 *
 * @param companyList - A pointer to a Storage structure
 * @param dest - A char pointer where the valid name will be stored.
 * @param msg - A message prompt to be displayed.
 */
void setCompanyNAME(Storage *companyList, char *dest, char *msg);

/**
 * @brief Sets the street for a company.
 *
 * This function prompts the user to set a street to a company, and then will
 * validate if the input is valid.
 *
 * @param companyList - A pointer to a Storage structure.
 * @param dest - A pointer to the destination string.
 * @param msg - A pointer to a message that will be displayed.
 */
void setCompanySTREET(Storage *companyList, char *dest, char *msg);

/**
 * @brief Sets the category to a company.
 *
 * This functions prompts the user to set a category based on 3 categories available,
 * if the user dont choose the right one it will warn him to choose between the 3 categories.
 *
 * @param companyList - A pointer to a Storage structure.
 * @param dest - A pointer to the destination string.
 * @param msg - A pointer to a message that will be displayed.
 */
void setCompanyCATG(Storage *companyList, char *dest, char *msg);

/**
 * @brief Sets the postal code to a company.
 *
 * This function prompts the user to a enter a postal code for the company, and
 * then will check if the input is valid.
 *
 * @param companyList - A pointer to a Storage structure.
 * @param dest - A pointer to the destination string.
 * @param msg - A pointer to a message that will be displayed.
 */
void setCompanyPOSTAL(Storage *companyList, char *dest, char *msg);

/**
 * @brief Sets the  local to a company.
 *
 * This function prompts the user to enter a local for the company, and then
 * checks if the input the user entered is valid.
 *
 * @param companyList - A pointer to a Storage structure.
 * @param dest - A pointer to the destination string.
 * @param msg - A pointer to a message that will be displayed.
 */
void setCompanyLOCAL(Storage *companyList, char *dest, char *msg);

/**
 * @brief Sets a business name to the list.
 *
 * This functions prompts the user to enter a business. If it is already
 * in the list it tells the user that the business already created.
 *
 * @param businessList - A pointer to a Storage structure.
 * @param dest - A pointer to the destination string.
 * @param msg - A pointer to a message that will be displayed.
 */
void setCompanyBUSINESS(Storage *businessList, char *dest, char *msg);

/**
 * @brief Checks if a given string contains only allowed characters.
 *
 * This function iterates over each character of the input string and checks for special characters.
 * Allowed characters include alphabets, spaces, hyphens, and an ampersand. Additionally,
 * a period is allowed only if it is the last character following an alphabet character.
 *
 * @param input A constant character pointer representing the string to be checked.
 * @param length An integer representing the length of the string.
 *
 * @return int Returns 1 if the string contains only allowed characters, 0 otherwise.
 */
int checkSpecialChars(const char *input, int length);

/**
 * @brief Checks if a business with the given activity name already exists in the business list.
 *
 * Iterates over the business list and compares each business's activity name with the provided input.
 * If a match is found, it indicates that the business already exists.
 *
 * @param businessList -  A pointer to a Storage Structure.
 * @param input - A string representing the activity name to search for.
 * @return int Returns 0 if the business already exists  otherwise, returns 1.
 */
int businessInUse(Storage *businessList, char *input);

/**
 * @brief Searches for a company on the list based on the input NIF.
 *
 * Tis functions iterates over the company list to find a company NIF
 * with the match input. If found retrieves the company data for the user or
 * for the admin.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - A string the user input to compare for.
 * @param forUser - An integer flag determining the type of data to be retrieved 1 for user
 * type and 0 for admin type.
 * @return Return the index of the company based on the NIF.
 */
int searchCompanyNIF(Storage *companyList, char *input, int forUser);

/**
 * @brief Searches for companies in the list based on street name and retrieves their data.
 *
 * This function iterates over the company list, comparing each company's street name with the provided input.
 * If a match is found, it retrieves data for that company.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - A string representing the street name to be matched with companies in the list.
 * @return Return the index of the company based on the street.
 **/
int searchCompanySTREET(Storage *companyList, char *input);

/**
 * @brief Searches for companies on the list based on the postal code input.
 *
 * This functions iterates over the company list to find companies that matches the given postal code.
 * If match is found, retrieves data for that company.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - A string representing the postal code to be matched with a company on the list.
 * @return Return the index of the company based on the postal code.
 */
int searchCompanyPOSTAL(Storage *companyList, char *input);

/**
 * @brief Searches for companies on the list based on the input name.
 *
 * This function iterates over the company list to find a company that matches the given name.
 * Depending on the 'forUser' flag, it retrieves either general company data or user-specific data.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - A string representing the name to be matched with a company on the list.
 * @param forUser - An integer flag determining the type of data to be retrieved:
 * 0 for general company data, 1 for user-specific company data.
 * @return Return the index of the company based on the name.
 */
int searchCompanyNAME(Storage *companyList, char *input, int forUser);

/**
 * @brief Searches for companies on the list based on the business input.
 *
 * This function iterates over the company list to find companies business with
 * the provided input. If match is found, it retrieves company's data.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - A string representing the business to be matched with a company in the list.
 * @return Return the index of the company based on the business.
 */
int searchCompanyBUSINESS(Storage *companyList, char *input);

/**
 * @brief Searches for companies on the list based on the category input.
 *
 * This function iterates over the company list to find companies category with
 * the provided input. If match is found, it retrieves data for for that company.
 *
 * @param companyList - A pointer to a Storage structure that containing the company list.
 * @param input - A string representing the category to be matched with a company in the list.
 * @return Return the index of the company based on the category.
 */
int searchCompanyCATG(Storage *companyList, char *input);

/**
 * @brief Searches for companies on the list based on the local input.
 *
 * This function iterates over the company list to find companies that match the given local identifier (input).
 * It then retrieves company data differently based on the 'forUser' flag.
 *
 * @param companyList - A pointer to a Storage structure containing the company list.
 * @param input - A string representing the local to be matched.
 * @param forUser - An integer flag determining the type of data to be retrieved:
 * 0 for general company data, 1 for user-specific company data.
 * @return Return the index of the company based on the local.
 */
int searchCompanyLOCAL(Storage *companyList, char *input, int forUser);
#endif //INC_INPUT_H_
