/**
 * @file replace.c
 * @author Justin Cristinziano (jmcrist2)
 * This piece replaces the placeholders
 * with the replacement strings read in by input.c
 */

#include "replace.h"

/**
 * This function computes the resulting length of the story line 
 * if the specified placeholder is replaced by the replacement string
 * @param line is the line of the madlib with the placeholders
 * @param word is the word is the replacement word
 * @param placeholder is the placeholder word (what's surrounded by <>)
 * @return true if the line isn't too long with the addition
 * and false if not
*/
bool computeLen( char line[ LINE_MAX + 1], char word[ FIELD_MAX + 1], char placeholder[])
{
  int lineLen = strlen(line);
  int wordLen = strlen(word);
  int placeholderLen = strlen(placeholder);

  if((lineLen - placeholderLen + wordLen) > 100) {
    return false;
  } else {
    return true;
  }
}

/**
 * This function finds the index where the first occurance
 * of the placeholder is
 * @param 
 * @return index of the first < (where the first temp word is found)
 * or 0 if the temp word wasn't found
*/
int findIndex( char line[LINE_MAX + 1], char placeholder[]) {
  int lineLength = strlen(line);
  int placeholderLength = strlen(placeholder);

  // For loop that runs through every 
  for(int i = 0; i < lineLength - placeholderLength; i++) {
    bool found = true;
    // From this position in the line, check 
    // the next (plaaceholderLength)
    for(int j = 0; j < placeholderLength; j++) {
        if(line[i + j] != placeholder[j]) {
            found = false;
            break;
        }
    }
    // If it was found, return the index of the < ... return -1 if not
    if(found) {
        return i;
    }
  }
  return -1;
}

/**
 * This function replaces a given placeholder 
 * in the line with the given replacement string
 * @param line is the line being read in
 * @param word is the word that is going to replace the placeholder
 * @param placeholder is the placeholder word (what's surrounded by <>)
*/
void replaceWord( char line[ LINE_MAX + 1], char word[ FIELD_MAX + 1], char placeholder[] )
{
  int replaceIndex = findIndex(line, placeholder);

  while(replaceIndex != -1) {
    if(computeLen(line, word, placeholder)) {
      char tempLine[LINE_MAX + 1];
      // line + replaceIndex = start of the replacement word (<>)
      // + strlen(placeholder) takes you to the end of the replacement
      // word
      strcpy(tempLine, line + replaceIndex + strlen(placeholder)); 
      line[replaceIndex] = '\0';
      strcat(line, word);
      strcat(line, tempLine);
    } else {
      exit(LINE_TOO_LONG_EXIT);
    }
    replaceIndex = findIndex(line,placeholder);
  }
}