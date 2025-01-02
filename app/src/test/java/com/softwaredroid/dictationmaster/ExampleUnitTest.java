package com.softwaredroid.dictationmaster;

import org.junit.Test;

import static org.junit.Assert.*;

import com.softwaredroid.dictationmaster.llm.ChatPost;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void addition_isCorrect()
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCohere()
    {
        String test = ChatPost.test("Translate to English and only print out the translation nothing else: Sie nutzte eines der flauschigen Handtücher vermutlich auch für Elfen Haut gedacht, um das Gefühl der Reinigung zu perfektionieren. Um nicht in ihre alten und dreckigen Klamotten zurückzuschlüpfen nutzte sie einer der ebenfalls flauschigen bademäntel. Sie stellte sicher dass er zu war und ihre Intimbereiche vollständig bedeckt. Langsam ging sie zum Ausgang, wo Jasper die ganze Zeit gewartet hatte. Er blickte erst verdutzt als er sie in ihrem neuen Kleidungsstück sah doch dann führte er sie schulterzuckend zu ihrem neuen persönlichen Reich zurück. Zufrieden und immer noch mit einem prickelnden Gefühl auf der Haut schlüpfte Raven unter ihre Bettdecke und viel in einen erholsamen Schlaf.");
        int i = 1 + test.length();
    }

}

