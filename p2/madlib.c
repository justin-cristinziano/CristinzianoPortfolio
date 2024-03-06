/**
 * @file madlib.c
 * @author Justin Cristinziano (jmcrist2)
 * This is the main component of the madlib software
*/

#include <stdio.h>
#include <string.h>
#include "replace.h"
#include "input.h"

/**
 * This is the function that controls all of the other functions
 * to make the final madlib output
*/
int main() 
{
  char noun1[FIELD_MAX + 1];
  char noun2[FIELD_MAX + 1];
  char verb[FIELD_MAX + 1];
  char adjective[FIELD_MAX + 1];
  char adverb[FIELD_MAX + 1];
  char line[LINE_MAX + 1];

  // PLACEHOLDER_MAX + 3 to take in account for the <> and the null term
  char placeholderList[PLACEHOLDER_TYPE_COUNT][PLACEHOLDER_MAX + 3] = 
    {"<noun1>", "<noun2>", "<verb>", "<adjective>", "<adverb>"};


  readWord(noun1);
  readWord(noun2);
  readWord(verb);
  readWord(adjective);
  readWord(adverb);

  if(strlen(noun1) == 0 || strlen(noun2) == 0 || 
    strlen(verb) == 0 || strlen(adjective) == 0 || strlen(adverb) == 0) {
      exit(FIELD_MISSING_EXIT);
    }

  while(readLine(line)) {
    replaceWord(line, noun1, placeholderList[0]);
    replaceWord(line, noun2, placeholderList[1]);
    replaceWord(line, verb, placeholderList[2]);
    replaceWord(line, adjective, placeholderList[3]);
    replaceWord(line, adverb, placeholderList[4]);

    if(strlen(line) != 0) {
      printf("%s\n", line);
    }
  }
}