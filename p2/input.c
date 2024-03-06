/**
 * @file input.c
 * @author Justin Cristinziano (jmcrist2)
 * This reads the inputted replacement strings and the 
 * string with the temporary strings
*/

#include "input.h"

/** ASCII Dec value of space */
#define SPACE 32

/** ASCII Dec value of apostrophe */
#define APOSTROPHE 39

/** ASCII Dec value of hyphen */
#define HYPHEN 45

/** ASCII Dec value of zero */
#define ZERO 48
/** ASCII Dec value of nine */

#define NINE 57

/** ASCII Dec value of capital A */
#define UPPER_A 65

/** ASCII Dec value of capital Z */
#define UPPER_Z 90

/** ASCII Dec value of lowercase a */
#define LOWER_A 97

/** ASCII Dec value of lowercase z*/
#define LOWER_Z 122

/**
 * This function makes sure that the placeholder found in 
 * readLine is valid
 * @param placeholder is the placeholder being checked (whatever is put
 * inside of the <>)
 * @return true if the placeholder is valid
*/
bool isValidPlaceholder( char placeholder[] ) {
    char validPlaceholders[PLACEHOLDER_TYPE_COUNT][PLACEHOLDER_MAX + 1] = 
      {"noun1", "noun2", "verb", "adverb", "adjective"};

    for(int i = 0; i < PLACEHOLDER_TYPE_COUNT; i++) {
        if(strcmp(validPlaceholders[i], placeholder) == 0) {
            return true;
        }
    }

    return false;
}

/**
 * This function reads in the line (a replacement word)
 * @param word is the string that is being read in from
 * standard input
*/
void readWord( char word[ FIELD_MAX + 1 ]) 
{
  int index = 0;
  char c;
  while((scanf("%c", &c) == 1) && c != '\n') {
    word[index] = c;
    index++;
  }
  word[index] = '\0';


  if(strlen(word) > FIELD_MAX) {
    exit(FIELD_MAX_EXIT);
  }

  if(strlen(word) == 0) {
    exit(FIELD_MISSING_EXIT);
  }

  for(int i = 0; word[i]; i++) {
    if(word[i] != 32 && word[i] != 39 && word[i] != 45 && 
    !(word[i] >= 48 && word[i] <= 57) && 
    !(word[i] >= 65 && word[i] <= 90) &&
    !(word[i] >= 97 && word[i] <= 122)) {
      exit(FIELD_MAX_EXIT);
    }
  }
}



/**
 * This function reads in the lines after the replacement words
 * (the line with the temp words)
 * @param line is the single line of text with the temp words
 * @return true if the line is read in without an EOF and false
 * if it hits an error
*/
bool readLine( char line[ LINE_MAX + 1 ])
{
  char c;
  int indexOfLine = 0;
  while(scanf("%c", &c) == 1 && c != '\n') {
    line[indexOfLine] = c;
    indexOfLine++;
  }

  if(strlen(line) == 0) {
    return false;
  }

  line[indexOfLine] = '\0';

  //This becomes true once we hit a '<' in the line
  bool inBrackets = false;

  //Exit if the line is longer than LINE_MAX
  if(strlen(line) > LINE_MAX) {
    exit(LINE_TOO_LONG_EXIT);
  }
  
  char placeholder[11];
  int placeholderIndex = 0;
  //Read the line and make sure each placeholder is valid
  for(int i = 0; line[i]; i++) {
    if(line[i] == '<') {
      inBrackets = true;
    } else if(line[i] == '>') {
      inBrackets = false;
      placeholder[placeholderIndex] = '\0';
      if(!isValidPlaceholder(placeholder)) {
        exit(INVALID_PLACEHOLDER_EXIT);
      }
    } else {
        if(inBrackets) {
          placeholder[placeholderIndex] = line[i];
          placeholderIndex++;
        } else {
          for(int i = strlen(placeholder) - 1; i >= 0; i--) {
            placeholder[i] = '\0';
          }
          placeholderIndex = 0;
        }
    }
  }
  return true;
}