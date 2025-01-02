package com.softwaredroid.dictationmaster;

import android.content.Context;

public class TextManipulator
{
    private boolean enabled = true;
    public String TURN_ON_OFF_COMMAND = "sakura";
    public String DELETE_WORD_COMMAND = "lösche wort";
    public String DELETE_SENTENCE_COMMAND = "lösche satz";
    private int DELETE_SENTENCE_COMMAND_NUMBER_TO_DELETE = 0;
    private IDictationService service;
    private Context context;



    TextManipulator(IDictationService service, Context context)
    {
        // delete the entered text plus one more
        DELETE_SENTENCE_COMMAND_NUMBER_TO_DELETE = DELETE_WORD_COMMAND.split(" ").length + 1;
        this.service = service;
        this.context = context;
    }

    public String searchAndApplyCommands(String text)
    {
        String lowerCaseText = text.toLowerCase();
        if (lowerCaseText.contains(TURN_ON_OFF_COMMAND))
        {
            setActivationState(!enabled);
            service.showNotification(enabled ? context.getString(R.string.sakura_awake) : context.getString(R.string.sakura_sleeps_now));
            // remove entered commands
            return deleteLastWords(text, 1);
        }

        if (enabled)
        {
            if (lowerCaseText.contains(DELETE_WORD_COMMAND))
            {
                return deleteLastWords(text, DELETE_SENTENCE_COMMAND_NUMBER_TO_DELETE);
            }

            if (lowerCaseText.contains(DELETE_SENTENCE_COMMAND))
            {
                return deleteLastSentence(text);
            }
        }
        return null;
    }

    private void setActivationState(boolean newState)
    {
        enabled = newState;
    }

    private String deleteLastWords(String text, int numberToDelete)
    {
        // Split the text into words
        String[] words = text.split(" ");
        if (words.length > numberToDelete - 1)
        {
            // Remove the last word
            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < words.length - numberToDelete; i++)
            {
                newText.append(words[i]).append(" ");
            }
            return newText.toString().trim(); // Return the modified text
        }
        return text; // Return original text if no words to delete
    }

    private String deleteLastSentence(String text)
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
