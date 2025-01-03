package com.softwaredroid.dictationmaster.llm;

public class PromptManager
{
    private static String IMPROVE_TEXT_1 = "Improve this text, fix errors and make it more natural. Also use fitting emojis, but at least 2 at the end. Only print out the new version and nothing else. The target language is %s: %s";

    private static String BOOK_IMPROVE = "Improve the following gap text (text enclosed by *) needs to be filled in and improved for my book. Make it vivid and sophisticated. The target language is %s: %s";

    public static String generateImprovedTextPrompt(String text,String targetLanguage)
    {
        // length limit
        if(text.length() > 300)
        {
            return null;
        }
        return String.format(IMPROVE_TEXT_1,targetLanguage, text);
    }

    public static String generateBoomImprovementPrompt(String text,String targetLanguage)
    {
        return String.format(BOOK_IMPROVE,targetLanguage, text);

    }

}
