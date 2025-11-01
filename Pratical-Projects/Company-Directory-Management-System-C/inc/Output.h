/**
 * @file        Output.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for the output.
 * @date        02/12/2023
**/
#include "../inc/struct/CompanyStructure.h"
#ifndef INC_OUTPUT_H_
#define INC_OUTPUT_H_

#define EMPTY 1
#define BAR "\n-----------------------------------------------------------------------------------------------------------------------"
#define BAR_LONG "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"

// TITLES:
#define TITLE_MAIN "\t\t\t\t\t\t\t\t\t\t\t\t     [MAIN MENU]"
#define TITLE_ADMIN "\t\t\t\t\t\t\t\t\t\t\t\t    [ADMIN MENU]"

#define TITLE_COMPANY_ADD "\t\t\t\t\t\t\t\t\t\t\t\t    [ADD] COMPANY"
#define TITLE_COMPANY_LIST "\t\t\t\t\t\t\t\t\t\t\t\t   [LIST] COMPANY"
#define TITLE_COMPANY_SEARCH "\t\t\t\t\t\t\t\t\t\t\t\t  [SEARCH] COMPANY"
#define TITLE_COMPANY_DEL "\t\t\t\t\t\t\t\t\t\t\t\t    [DEL] COMPANY"
#define TITLE_COMPANY_EDIT "\t\t\t\t\t\t\t\t\t\t\t\t  [EDIT] COMPANY"
#define TITLE_COMPANY_COMMENT "\t\t\t\t\t\t\t\t\t\t   [COMMENT] COMPANY"

#define TITLE_BUSINESS_ADD "\t\t\t\t\t\t\t\t\t\t\t\t   [ADD] BUSINESS"
#define TITLE_BUSINESS_EDIT "\t\t\t\t\t\t\t\t\t\t\t\t  [EDIT] BUSINESS"

#define TITLE_USER "\t\t\t\t\t\t\t\t\t\t\t\t     [USER MENU]"
#define TITLE_COMPANY "\t\t[COMPANY MENU]"
#define TITLE_CATAL "\t\t\t\t\t\t\t\t\t\t\t\t[MANAGE CATALOG MENU]"
#define TITLE_BUSI "\t\t[MANAGE BUSINESS MENU]"
#define TITLE_REPORT "\t\t[MANAGE REPORT MENU]"
#define TITLE_C_RATING "\t\t RATE COMPANY"
#define TITLE_B_DEL "\t\t\tREMOVE BUSINESS"
#define TITLE_C_FOUND "\t\t\t\t\tCOMPANY FOUND"
#define TITLE_C_COMMENTS "\t\t\t\tCOMPANY COMMENTS"
#define TITLE_COMPANY_ANALYTICS "\t\t\t\t\t\t\t\t\t\t\t\t [ANALYTICS] COMPANY"

// INPUT:
#define INPUT_NO_LENGTH "The INPUT should contain less than 30 characters."
#define INPUT_NO_LETTERS "The INPUT should contain only letters except & and .   ."
#define INPUT_NO_BLANK "The INPUT should not be empty."
#define INPUT_NO_SPECIALS "The INPUT should not contain\nany symbols except for the abreviation\n of R. and Av."
#define SELECT_INDEX "Select an index."
#define INPUT_NO_MATCH "INPUT DOES NOT MATCH THE REQUIRED INPUT.\n"
#define INPUT_INVALID "ONLY LETTERS OR NUMBERS"

// HEADER:
#define HEADER_C_LIST "INDEX -    NIF    -   \t\t  NAME  \t   -   CATEGORY  - \t      BUSINESS  \t  -  \t\t\t\t ADDRESS \t\t\t\t     - [STATUS]\n"
#define HEADER_B_LIST "INDEX -         NAME OF BUSINESS       - [STATUS] \n"

// NIF:
#define NIF_INSERT "\nInsert company NIF: "
#define NIF_NO_RANGE "The NIF should be between,\n100 000 000 and 999 999 999"
#define NIF_LENGTH 11
#define NIF_IN_USE "NIF already in use, please enter a valid one!"

// NAME:
#define NAME_INSERT "\nInsert company Name: "
#define NAME_ACCESS "\nTo access this feature, type the name of your company please: "
#define NAME_IN_USE "Name already in use, please enter a valid one"

// CATEGORY:
#define CATG_INSERT "\nInsert company Category: "
#define CATG_CHOOSE "PLEASE CHOOSE BETWEEN  \"SME\", \"Micro\" or \"Big\""

#define COMPANY_ADDED "\nCOMPANY %s WAS ADDED TO THE LIST WITH SUCCESS!\n"



// SELECT:
#define COMPA_SELECT "\nSelect an available company according to the index number.\nYou can go back by typing -1."
#define BUSI_SELECT "\nSelect an available business according to the index number.\nYou can go back by typing -1."
#define COMMT_SELECT "\nSelect an available comment according to the id number.\nYou can go back by typing -1."

// BUSINESS:
#define BUSI_INSERT "\nInsert company Business: "
#define BUSINESS_MISSING "\nDO NOT FORGET TO UPDATE COMPANY INFORMATION.\nIN REGARDS TO BUSINESS OF %s\n"
#define BUSINESS_NO_DELETE "You cant remove a business while is status are active or business is in use"
#define BUSINESS_DELETED "Business removed with success"

// STREET:
#define STREET_INSERT "\nInsert company Street: "


// LOCAL:
#define LOCAL_INSERT "\nInsert company Locality: "

// POSTAL:
#define POSTAL_INSERT "\nInsert company Postal: "
#define POSTAL_NO_RANGE "The Postal Code should be between, 1000-001 and 9001-801"
#define POSTAL_LENGTH 10

// DELETE:
#define C_DEL_INSERT "To delete this company, please retype\nthe name of the company and press enter: "
#define B_DEL_INSERT "To delete this business, please retype\nthe name of the company and press enter: "
#define C_DEL_SUCCESS "Company deleted with success"
#define C_SIGN_IN "\t \t COMPANY SIGN IN"

// RATING:
#define RATING_INSERT "Give a rating between 0.0 and 5.0"
#define RATING_NO_RANGE "The rating given should be between 0.0 and 5.0"
#define NEW_AVRG_RATE "New average rating: %f\n"

// STATUS:
#define CURRENT_STATUS "Current company status %s\n"
#define NEW_STATUS "Enter the new status\n[1] Inactive\n[2] Active"
#define INVALID_STATUS "Invalid option please enter 0-Inactive or 1-Active!"

// COMMENT:
#define C_COMMT_TITLE_INSERT "\nTITLE: "
#define C_COMMT_NAME_INSERT "\nNAME: "
#define C_COMMT_EMAIL_INSERT "\nEMAIL: "
#define C_COMMT_MSG_INSERT "\nCOMMENT: "
#define NO_DEL_COMPANY "DELETING A COMPANY WITH COMMENTS IS NOT POSSIBLE!"
#define COMMT_DELETED "Comment deleted with success."
#define COMMT_NOT_FOUND "Comment not found!"
#define COMMT_ID "COMMENT WITH THE ID: %d"

// RIVAL:
#define RIVAL_INSERT "Name your competition: "

//OTHER DEFINES:
#define NO_OPTION "OPTION NOT FOUND."
#define EXIT_SUGGEST "TO EXIT PRESS [0]."
#define BACK_SUGGEST "TO GO BACK PRESS [9]."
#define BACK "GOING BACK A MENU..."
#define EXIT "YOU ARE EXITING THE PROGRAM..."
#define LIST_COMPANY_EMPTY "LIST HAS NO COMPANIES YET!"
#define LIST_BUSINESS_EMPTY "LIST HAS NO BUSINESSES YET!"
#define LIST_COMMT_EMPTY "The company %s has no comments yet!\n"
#define OUT_OF_BOUND "INDEX OUT OF BOUNDS!\nSELECT AN AVAILABLE INDEX."
#define LIST_COMPANY_LOADING "\nRetrieving list of companies..."
#define LIST_BUSINESS_LOADING "\nRetrieving list of businesses..."
#define LIST_COMMENT_LOADING "Retrieving list of comments..."
#define NO_ACCESS_DATA "YOU HAVE NO PERMISSION TO ACCESS THIS DATA."
#define FILE_NO_DATA "FILE HAS NO DATA\n"
#define NOT_FOUND "DATA NOT FOUND"
#define NO_MEMORY "NOT ENOUGH MEMORY. ALLOCATE MEMORY AND REPEAT PROCESS."

// ------------------------ Function Declarations ------------------------------
/**
 * @brief Function to display the main menu.
 *
**/
void mainMenuDisplay();

// ------------------------------ LAYER 1 --------------------------------------
/**
 * @brief Function to display the admin menu.
**/
void adminMenuDisplay();

/**
 * @brief Function to display the user menu.
**/
void userMenuDisplay();

/**
 * @brief Function to display the company menu.
**/
void companyMenuDisplay();
// ------------------------------ LAYER 2 --------------------------------------

// ----------------- ADMIN: CATALOG, BUSINESS, REPORTS -------------------------
/**
 * @brief Function to display the manage catalog company.
**/
void manageCatalogDisplay();

/**
 * @brief Function to display the manage catalog business.
**/
void manageBusinessDisplay();

/**
 * @brief Function to display the reports menu.
**/
void manageReportDisplay();

// --------------------- USER: SEARCH, RATE, COMMENT ---------------------------
/**
 * @brief Function to display the menu search by(NIF, NAME, LOCAL) menu.
**/
void submitSearchDisplay();

/**
 * @brief Function to display the menu of what the user wants to search(all companies at once or search by).
**/
void submitRatingDisplay();
/**
 * @brief Function to display the submit comment menu.
**/
void submitCommentDisplay();

// -------------------- COMPANY: INFO, REVIEW, REPORT --------------------------



// ------------------------------ LAYER 3 --------------------------------------

// -------------- CATALOG: LIST, EMPTY, SEARCH, DEL, EDIT ----------------------
/**
 * @brief Display the data of the company.
 *
 * This function will display the information relative of a company.
 *
 * @param companyList - A pointer to a Storage structure that contains the list of companies.
**/
void companyListDisplay(Storage *companyList);

/**
 * @brief Check if company list empty.
 *
 * This function will check if the company list is empty or not.
 *
 * @param companyList - A pointer to a Storage structure that contains the list of companies.
 * @return Returns 1 if list is empty if not returns 0.
**/
int companyListEmpty(Storage *companyList);

/**
 * @brief Displays the company list for the user.
 * @param companyList
 */

void userCompanyListDisplay(Storage *companyList);

int accessCompanyListEmpty(Storage *companyList);

/**
 * @brief Display the menu search.
 *
 * This function will display what the user wants to search.
**/
void companySearchDisplay();

/**
 * @brief Get company data.
 *
 * This function will get the company data based on the index provided.
 *
 * @param companyList - A pointer to a Storage structure that contains the company list.
 * @param index - The index where the company is on the list.
**/
void getCompanyData(Storage *companyList, int index);

/**
 * @brief Displays the company that will be removed.
 *
 * This function will display the name of the company that the user will remove and ask if he really wants remove
 * or exit.
 *
 * @param index - The index of where the company is on the list.
 * @param companyList - A pointer to a Storage structure that contains the company list.
**/
void companyRemoveDisplay(Storage *companyList, int index);

/**
 * @brief Menu edit display.
 *
 * This function will display all available data that the user can edit on a company.
 *
 * @param companyList - A pointer to a Storage structure that contains the company list.
 * @param index - The index of the company on the list.
**/
void companyEditDisplay(Storage *companyList, int index);

/**
 * @brief Menu rating display.
 *
 * This function will display the rating menu.
 *
 * @param index - The index of the company on the list.
 * @param companyList - A pointer to a Storage structure that contains the company list.
**/
void companyRatingDisplay(Storage *companyList, int index);

/**
 * @brief Menu comment display.
 *
 * This function will display comment menu.
 *
 * @param companyList - A pointer to a Storage structure that contains the company list.
 * @param index - The index of the company on the list.
**/
void companyCommentDisplay(Storage *companyList, int index);

/**
 * @brief Menu company comments display.
 *
 * This function displays comments of a certain company based on the index.
 *
 * @param index - The index of the company on the list.
 * @param companyList - A pointer to a Storage structure that contains the company list.
**/
void commentListDisplay(Storage *companyList, int index);

int commentListEmpty(Storage *companyList, int index);

//---------------------------------------------------- BUSINESS --------------------------------------------------------

/**
 * @brief Check if the business list is empty.
 *
 * This function will check if the business list is empty or not.
 *
 * @param businessList - A pointer to a Storage structure that contains the list of business.
 * @return Returns 1 if the list is empty and 0 if not.
 */
int businessListEmpty(Storage *businessList);

/**
 * @brief Display the business list.
 *
 * This function will display all business available on the list and the status of each one.
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 */
void businessListDisplay(Storage *businessList);

/**
 * @breif Displays the menu to remove business.
 *
 * This function will display the menu to remove a business based on the index of the business on the list.
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 * @param index - The index of the business on the list.
 */
void businessRemoveDisplay(Storage *businessList, int index);

/**
 * @brief Displays the business edit menu.
 *
 * This function will display the business menu based on the index of the business on the list.
 *
 * @param businessList - A pointer to a Storage structure that contains the business list.
 * @param index - The index of the business on the list.
 */
void businessEditStatusDisplay(Storage *businessList, int index);
/**
 * @brief Displays the comments for the user
 * @param companyList - A pointer to a Storage structure.
 * @param index - The index of the company on the list which will display the comments.
 */
void userCommentListDisplay(Storage *companyList, int index);

/**
 * @brief Displays the comments menu.
 *
 */
void companyCommentMenuDisplay();

/**
 * @brief Check the company list is empty for the user.
 * @param companyList - A pointer to a Storage structure.
 * @return Returns 1 if the list is empty and 0 if not.
 */
int companyListUserEmpty(Storage *companyList);

/**
 * @brief Displays the company data for the user
 *
 * @param companyList - A pointer to a Storage structure.
 * @param index The index of the company to display.
 */
void getCompanyDataForUser(Storage *companyList, int index);

/**
 * @breif Get the index of activity of a company.
 *
 * This function will check what is the index of the business on the business list
 * based on the business the company have
 *
 * @param companyList - A pointer to the Storage structure.
 * @param index - The index of the company on the list.
 * @return Returns the index of the business.
 */
int getIndexOfActivity(Storage *companyList, int index);

/**
 * @brief Display to the user if the business he wants to associated to is
 * company is on the list.
 */
void businessOnListDisplay();

/**
 * @brief Displays the comment menu for the company menu.
 */
void accessCommentDisplay();

/**
 * @brief Displays the menu of hide and show comments for the admin.
 */
void  hideAndShowCommentDisplay();

#endif //INC_OUTPUT_H_
