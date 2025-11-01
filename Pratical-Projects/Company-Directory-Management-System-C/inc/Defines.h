/**
 * @file        Defines.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file for the defines.
 * @date        03/12/2023
**/
#ifndef INC_DEFINES_H_
#define INC_DEFINES_H_

//COMPANY MACROS:
#define COMPANY_PTR companyList->companyPtr
#define COMPANY_SIZE companyList->companyCtr
#define COMPANY_NUMBER companyList->companyPtr[index].number
#define COMPANY_NIF companyList->companyPtr[index].nif
#define COMPANY_NAME companyList->companyPtr[index].name
#define COMPANY_CATEGORY companyList->companyPtr[index].category
#define COMPANY_STREET companyList->companyPtr[index].address.street
#define COMPANY_LOCAL companyList->companyPtr[index].address.locality
#define COMPANY_POSTAL companyList->companyPtr[index].address.postalCode
#define COMPANY_STATUS companyList->companyPtr[index].status
#define COMPANY_ACTIVE companyList->companyPtr[index].active
#define COMPANY_RATING companyList->companyPtr[index].rating
#define COMPANY_ACTIVTY companyList->companyPtr[index].activity

//SEARCH MACROS:
#define COMPANY_SERCHS_TOTAL companyList->companyPtr[index].searchCtrTot
#define COMPANY_SERCHS_NIF companyList->companyPtr[index].searchCtrNif
#define COMPANY_SERCHS_NAME companyList->companyPtr[index].searchCtrName
#define COMPANY_SERCHS_LOCAL companyList->companyPtr[index].searchCtrLocal

//COMMENTS MACROS:
#define COMPANY_RATNG_TOTAL companyList->companyPtr[index].ratingTot
#define COMPANY_RATNG_COUNTER companyList->companyPtr[index].ratingCtr
#define COMPANY_COMMT_SIZE companyList->companyPtr[index].commentCtr
#define COMMENT_PTR companyList->companyPtr[index].commentPtr
#define COMPANY_COMMT_NUMBER companyList->companyPtr[index].commentPtr[id].commentId
#define COMPANY_COMMT_STATUS companyList->companyPtr[index].commentPtr[id].status
#define COMPANY_COMMT_TITLE companyList->companyPtr[index].commentPtr[id].title
#define COMPANY_COMMT_NAME companyList->companyPtr[index].commentPtr[id].name
#define COMPANY_COMMT_EMAIL companyList->companyPtr[index].commentPtr[id].email
#define COMPANY_COMMT_MSG companyList->companyPtr[index].commentPtr[id].message
#define COMPANY_COMMT_HIDDEN companyList->companyPtr[index].commentPtr[id].hidden

//BUSINESS MACROS:
#define BUSINESS_PTR businessList->businessPtr
#define BUSINESS_SIZE businessList->businessCtr
#define BUSINESS_NUMBER businessList->businessPtr[businessIndex].number
#define BUSINESS_NAME businessList->businessPtr[businessIndex].activity
#define BUSINESS_STATUS businessList->businessPtr[businessIndex].status
#define BUSINESS_ACTIVE businessList->businessPtr[businessIndex].active
#define BUSINESS_USED businessList->businessPtr[businessIndex].inUse
#define BUSINESS_CL_NAME companyList->businessPtr[businessIndex].activity
#define BUSINESS_CL_USED companyList->businessPtr[businessIndex].inUse
#define BUSINESS_CL_ACTIVE companyList->businessPtr[businessIndex].active
#define BUSINESS_CL_STATUS companyList->businessPtr[businessIndex].status

// USER MACROS:
#define FOR_USER 1
#define NOT_FOR_USER 0

// MENU MACROS:
#define BACK_MENU 1
#define NO_BACK_MENU 0
#define EXIT_MENU (-1)

//FILES MACROS:
#define COMPANY_DB_FILE "companies.bin"
#define COMPANY_DB_PTR dataBase->companyPtr
#define COMPANY_DB_SAVE "Saving list of companies into the file companies.bin ..."
#define BUSINESS_DB_FILE "business.bin"
#define BUSINESS_DB_PTR dataBase->businessPtr
#define BUSINESS_DB_SAVE "Saving list of businesses into the file business.bin ..."

#endif //INC_DEFINES_H_
