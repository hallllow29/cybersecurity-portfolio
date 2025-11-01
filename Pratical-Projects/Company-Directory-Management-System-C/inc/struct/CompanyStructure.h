/**
 * @file        CompanyStructure.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the company struct.
 * @date        30/11/2023
**/

#ifndef COMPANYSTRUCTURE_H_
#define COMPANYSTRUCTURE_H_
#include "CommentStructure.h"
#include "BusinessStruct.h"

#define MAX_LEN 20
#define MAX_LEN_NIF 11
#define MAX_LEN_STATUS 10
#define MAX_LEN_CATG 7
#define MAX_LEN_STREET 50
#define SIZE_LOCAL 40


// ------------------------- Function Declarations -----------------------------
/**
 * @brief The structure of the address.
**/
typedef struct Address {
  char street[MAX_LEN_STREET];
  char locality[MAX_LEN];
  char postalCode[9];
} Address;

/**
 * @brief The structure of a company.
 *
**/
typedef struct Company {
  int number;
  char nif[MAX_LEN_NIF];
  char name[MAX_LEN_NAME];
  char category[MAX_LEN_CATG];
  char activity[MAX_LEN];
  Address address;
  char status[MAX_LEN_STATUS];
  int active;

  // RATING
  float rating;
  float ratingTot;
  float ratingCtr;

  // COMMENTS
  Comment *commentPtr;
  int commentCtr;

  // SEARCH COUNT
  int searchCtrTot;
  int searchCtrNif;
  int searchCtrName;
  int searchCtrLocal;

} Company;

/**
 * @brief The structure of the storage
 *
 * This structure contains the list of all companies and
 * business so is more easy to us to manipulate data using
 * this struct will point to each company and business struct.
 *
 */
typedef struct Storage {
  int companyCtr;
  Company *companyPtr;

  int businessCtr;
  Business *businessPtr;

} Storage;

#endif //COMPANYSTRUCTURE_H_
