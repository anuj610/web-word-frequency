package wordfrequency;

import java.util.*;

public class FrequencyCalculator {

    private static final int TOP_LIMIT = 10;

    public void parseAndCalculate(String url) {
        String content;

        // Parse the web page and the links inside it and return as a concatenated string
        try {
            content = (new WebParser(url)).deepParse();
        }
        catch (Exception e) {
            System.out.println("Invalid url passed as argument. Message: " + e.getMessage());
            return;
        }

        this.calculateFrequenciesAndPrint(content);
    }

    /**
     * Calculates frequencies of word occurrences and prints the top most occurred words
     * Words are only considered to be [a-zA-Z]
     * Words are considered case insensitive while calculating frequencies
     * @param content Content from the parsed web pages
     */
    private void calculateFrequenciesAndPrint(String content) {
        Map<String, Integer> wordMap = new HashMap<>();
        Map<String, Integer> wordPairMap = new HashMap<>();
        StringBuilder wordBuffer = new StringBuilder();

        // convert the string to a char array to loop through each character
        // we want to loop through each character once so as to parse the string only once and in a linear manner
        char[] charArray = content.toCharArray();

        String prevWordStr = "";
        boolean invalidWord = false;
        for(int i=0; i<charArray.length; i++) {
            char currChar = charArray[i];

            // if the character is neither a letter nor a whitespace, it means it is an invalid word for our use case
            // we would be ignoring such words
            if (!Character.isLetter(currChar) && !Character.isWhitespace(currChar)) {
                invalidWord = true;
            }

            // if we encounter a whitespace char, we reset the value of invalidWord
            if(Character.isWhitespace(currChar) && invalidWord) {
                wordBuffer = new StringBuilder();
                invalidWord = false;
                prevWordStr = "";
            }

            // if char is a letter, add to current word buffer
            if(Character.isLetter(currChar) && !invalidWord) {
                wordBuffer.append(Character.toLowerCase(currChar));
                // unless we reach the end of the string we continue
                if(i != charArray.length-1) {
                    continue;
                }
            }

            // if current character is not a letter and current word is a valid one, we add it to the word count map and update the frequency
            // the current word buffer is also reset in this step
            if(wordBuffer.length() > 0 && !invalidWord) {
                String wordStr = wordBuffer.toString();
                wordMap.put(wordStr, wordMap.getOrDefault(wordStr, 0) + 1);

                if(!prevWordStr.equals("")) {
                    String wordPairStr = (prevWordStr + wordStr).trim();
                    wordPairMap.put(wordPairStr, wordPairMap.getOrDefault(wordPairStr, 0) + 1);
                }

                prevWordStr = wordStr + " ";

                wordBuffer = new StringBuilder();
            }
        }

        printMostFrequentWords(wordMap);
        printMostFrequentWords(wordPairMap);
    }

    /**
     * Sort the words based on their frequencies and print
     * @param wordMap Map of words and their frequency of occurrence
     */
    private void printMostFrequentWords(Map<String, Integer> wordMap) {
        List<String> wordList = new ArrayList(wordMap.keySet());

        // Sort the word list based on the frequency values
        Collections.sort(wordList,
                (a, b) -> wordMap.get(a).equals(wordMap.get(b)) ? a.compareTo(b) : wordMap.get(b) - wordMap.get(a));

        // Print the word list along with the frequency
        for(int i=0; i < wordList.size() && i < TOP_LIMIT; i++) {
            String word = wordList.get(i);
            System.out.println(wordList.get(i) + " " + wordMap.get(word));
        }
    }
}
