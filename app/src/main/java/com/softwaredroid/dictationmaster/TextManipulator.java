package com.softwaredroid.dictationmaster;

import java.util.Map;

public class TextManipulator
{
    public static String DELETE_WORD_COMMAND = "lösche wort";
    public static String DELETE_SENTENCE_COMMAND = "lösche satz";

    public static String searchAndApplyCommands(String text)
    {
        String lowerCaseText = text.toLowerCase();

        if (lowerCaseText.contains(DELETE_WORD_COMMAND))
        {
            return deleteLastWord(text);
        }

        if (lowerCaseText.contains(DELETE_SENTENCE_COMMAND))
        {
            return deleteLastSentence(text);
        }

        return null;
    }

    private static String deleteLastWord(String text)
    {
        // Split the text into words
        String[] words = text.split(" ");
        if (words.length > 3)
        {
            // Remove the last word
            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < words.length - 3; i++)
            {
                newText.append(words[i]).append(" ");
            }
            return newText.toString().trim(); // Return the modified text
        }
        return text; // Return original text if no words to delete
    }

    private static String deleteLastSentence(String text)
    {
        // Split the text into sentences
        String[] sentences = text.split("(?<=[.!?])\\s*");
        if (sentences.length > 0)
        {
            // Remove the last sentence
            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < sentences.length - 1; i++)
            {
                newText.append(sentences[i]).append(" ");
            }
            return newText.toString().trim(); // Return the modified text
        }
        return text; // Return original text if no sentences to delete
    }
}
