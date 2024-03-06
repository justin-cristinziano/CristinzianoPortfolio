/**
 * @file input.h
 * @author Justin Cristinziano (jmcrist2)
 * The header file for the input.c file that takes in the replacement
 * string and the line of text for the strings to be implemented into
 */

#include "replace.h"
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

/** The length of the longest possible placeholder
    (adjective) */
#define PLACEHOLDER_MAX 9

/** Number of types of placeholders (ex: noun1, noun2... etc) */
#define PLACEHOLDER_TYPE_COUNT 5

/**
 * This function reads in the line (a replacement word)
 * @param word is the string that is being read in from
 * standard input
*/
void readWord( char word[ FIELD_MAX + 1 ]);

/**
 * This function reads in the lines after the replacement words
 * (the line with the temp words)
 * @param line is the single line of text with the temp words
 * @return true if the line is read in without an EOF and false
 * if it hits an error
*/
bool readLine( char line[ LINE_MAX + 1 ]);
