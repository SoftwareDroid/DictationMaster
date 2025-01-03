package com.softwaredroid.dictationmaster;

import android.content.Context;

public class TextManipulator
{
    private boolean enabled = true;
    public String TURN_ON_OFF_COMMAND = "Nana";
    public String DELETE_WORD_COMMAND = "lösche wort";
    public String DELETE_TWO_WORDS_COMMAND = "lösche zwei wörter";
    public String DELETE_THREE_WORDS_COMMAND = "lösche drei wörter";
    public String DELETE_SENTENCE_COMMAND = "lösche satz";
    private IDictationService service;
    private Context context;
    private boolean autoStartKeyboardIfAwake = true;

    public class CommandResult
    {
        public CommandResult(String text, boolean continueMicInput)
        {
            this.newText = text;
            this.continueMicInput = continueMicInput;
        }

        public String newText;
        public boolean continueMicInput = true;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean startMicInputAtKeyboardOpening()
    {
        return enabled && autoStartKeyboardIfAwake;
    }

    TextManipulator(IDictationService service, Context context)
    {
        // delete the entered text plus one more
        this.service = service;
        this.context = context;
    }

    public CommandResult searchAndApplyCommands(String text)
    {
        String lowerCaseText = text.toLowerCase();
        // GBoard add sometimes a space for the text word
        for (String turnOffCommand : new String[]{TURN_ON_OFF_COMMAND, TURN_ON_OFF_COMMAND + " "})
        {
            // compare with real text to respect case sentive
            if (text.endsWith(turnOffCommand))
            {
                setActivationState(!enabled);
                service.showNotification(enabled ? context.getString(R.string.sakura_awake) : context.getString(R.string.sakura_sleeps_now));
                // remove entered commands
                return new CommandResult(text.substring(0, text.length() - turnOffCommand.length()), enabled);
            }
        }

        if (enabled)
        {
            if (lowerCaseText.endsWith(DELETE_WORD_COMMAND))
            {
                return new CommandResult(deleteLastWords(text.substring(0, text.length() - DELETE_WORD_COMMAND.length()), 1), true);
            }
            if (lowerCaseText.endsWith(DELETE_TWO_WORDS_COMMAND))
            {
                return new CommandResult(deleteLastWords(text.substring(0, text.length() - DELETE_WORD_COMMAND.length()), 2), true);
            }
            if (lowerCaseText.endsWith(DELETE_THREE_WORDS_COMMAND))
            {
                return new CommandResult(deleteLastWords(text.substring(0, text.length() - DELETE_WORD_COMMAND.length()), 3), true);
            }

            if (lowerCaseText.endsWith(DELETE_SENTENCE_COMMAND))
            {
                return new CommandResult(deleteLastSentence(text), true);
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
