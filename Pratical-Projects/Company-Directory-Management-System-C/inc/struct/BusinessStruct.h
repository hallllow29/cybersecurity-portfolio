/**
 * @file        BusinessStruct.h
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the business struct.
 * @date        29/11/2023
**/
#ifndef INC_STRUCT_BUSINESSSTRUCT_H_
#define INC_STRUCT_BUSINESSSTRUCT_H_

#define MAX_LEN 20
#define MAX_LEN_STATUS 10

/**
 * @struct Business
 * @brief A struct that represents a business.
 *
 */
typedef struct Business {
    int number;
    char activity[MAX_LEN];
    char status[MAX_LEN_STATUS];
    int active;
    int inUse;

} Business;

#endif //INC_STRUCT_BUSINESSSTRUCT_H_
