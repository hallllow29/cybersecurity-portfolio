/**
 * @file        CommentStructure.c
 * @author      Pedro, Olga, Ruben
 * @copyright   ESTG IPP
 * @brief       Header file of the comment struct.
 * @date        13/12/2023
**/
#ifndef COMMENTSTRUCTURE_H_
#define COMMENTSTRUCTURE_H_

#define MAX_LEN_MESSAGE 100
#define MAX_LEN 20
#define MAX_LEN_STATUS 10
#define MAX_LEN_TITLE 20
#define MAX_LEN_EMAIL 20
#define MAX_LEN_NAME 30
// -------------------------------- MACROS -------------------------------------
// ------------------------- Function Declarations -----------------------------
/**
 * @struct Comment
 * @brief A structure that represents the comment.
**/

typedef struct Comment {
    int commentId;
    char title[MAX_LEN_TITLE];
    char name[MAX_LEN_NAME];
    char email[MAX_LEN_EMAIL];
    char message[MAX_LEN_MESSAGE];
    int hidden;
    char status[MAX_LEN_STATUS];

} Comment;
#endif //COMMENTSTRUCTURE_H_