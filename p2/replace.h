/**
 * @file replace.h
 * @author Justin Cristinziano (jmcrist2)
 * The header file for the replace.c file that replaces the placeholders
 * with the replacement strings read in by input.c
 */
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/** Maximum length for replacement string. */
#define FIELD_MAX 24

/** Maximum length for line of text. */
#define LINE_MAX 100

/** Exit status for a missing replacement word */
#define FIELD_MISSING_EXIT 101

/** Exit status for replacement word being greater
 *  than FIELD_MAX */
#define FIELD_MAX_EXIT 102

/** Exit status for a line extending past LINE_MAX */
#define LINE_TOO_LONG_EXIT 103

/** Exit status for a line extending past LINE_MAX */
#define INVALID_PLACEHOLDER_EXIT 104

/**
 * This function replaces a given placeholder 
 * in the line with the given replacement string
 * @param line is the line being read in
 * @param word is the word that is going to replace the placeholder
 * @param placeholder is the placeholder word (what's surrounded by <>)
*/
void replaceWord( char line[ LINE_MAX + 1], char word[ FIELD_MAX + 1], char placeholder[] );