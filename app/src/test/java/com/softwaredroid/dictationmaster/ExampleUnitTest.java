package com.softwaredroid.dictationmaster;

import org.junit.Test;

import static org.junit.Assert.*;

import com.softwaredroid.dictationmaster.llm.ChatPost;
import com.softwaredroid.dictationmaster.llm.ChatPostStreaming;
import com.softwaredroid.dictationmaster.llm.PromptManager;

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
    public void testSimpleCoherePrompt()
    {
        // 3.2 seconds
        String text = "können wir uns nacher treffer oder hast du keine Zeit? alternativ geht auch morgen?";
        // 2.9 seconds
        text = "Wenn alle da sind und Katze ist es wahrscheinlich sehr stressig. Vielleicht könnten wir noch heute Filmeabend/spazieren. Ich war schon eine Weile nicht mehr da";

        String prompt = PromptManager.generateImprovedTextPrompt(text,"german");
        prompt = null;
        if(prompt != null)
        {
            long startTime = System.currentTimeMillis();
            String test = ChatPost.runPrompt(prompt);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Prompt execution time: " + duration + " milliseconds");
            int i = 1 + test.length();
        }
    }



    @Test
    public void testCoherePromptForBook()
    {
        String text = "können wir uns nacher treffer oder hast du keine Zeit? alternativ geht auch morgen?";
        // 1900 chars 11s
        String bookText1900 = "Raven fühlte sich wieder privilegiert als sie den verbotenen Bereich betrat. Der Boden war angenehm warm und gefliest wie alles aus schwarz poliertem glatten *Platzhalter*. Sie glaubte dass der Waschraum hauptsächlich von anderen Schatten Wesen wie *Platzhalter* benutzt wurden als sie die Einrichtung näher betrachtete. Es gab an der Decke Öffnungen aus denen Wasser regnete. Ein Mechanismus beruht auf Steinen angereichert mit Feuermagie regulierte die Wärme des Wassers. Mehrere Schalter ermöglichten es unterschiedliche Anzahl an Steinen zu verwenden, um die gewünschte Temperatur zu erzeugen. In einem kleinen Schrank aus glänzenden Hartholz waren verschiedene Seifen gelagert. Neugierig ergriff Raven ein Stück aus dem Bereich für Dunkelelfen. Es war ein Block der nach kurzem riechen einen Geruch von *etwas was Dunkelelfen mögen* in ihre Nase Aufstiegen ließ. Nachdem sie sich entkleidet hatte stellte sie sich unter die große Öffnung und spielte mit den Hebeln, um die perfekte Temperatur zu finden. *Welche Temperatur könnte das Mädchen mögen*. In ihrer Heimat im nördlichen Wald gab es kein warmes Wasser und sie musste immer sich mit kaltem abfinden. Doch wenn Sie die Gelegenheit hatte dann wollte sie diesen Luxus schon auskosten. Das Wasser war himmlisch sie schloss die Augen und fühlte wie der Dreck von Wochen von ihr abgespült wurde. Das Stück Seife hatte einen überraschenden Effekt als sie sich damit einrieb. Es war leicht mit Schatten Energie angereichert. *Schreibe über den Seifeneffekt*. Ihre Konzentration stieg sowie auch ihre Gefühlslage. Als sie fertig war blickte sie bevor sie sich abtrocknete im großen Spiegel auf der anderen Seite des Raumes an. ";
        // 8 seconds
        String bookText1000 = "Raven fühlte sich wieder privilegiert als sie den verbotenen Bereich betrat. Der Boden war angenehm warm und gefliest wie alles aus schwarz poliertem glatten *Platzhalter*. Sie glaubte dass der Waschraum hauptsächlich von anderen Schatten Wesen wie *Platzhalter* benutzt wurden als sie die Einrichtung näher betrachtete. Es gab an der Decke Öffnungen aus denen Wasser regnete. Ein Mechanismus beruht auf Steinen angereichert mit Feuermagie regulierte die Wärme des Wassers. Mehrere Schalter ermöglichten es unterschiedliche Anzahl an Steinen zu verwenden, um die gewünschte Temperatur zu erzeugen. In einem kleinen Schrank aus glänzenden Hartholz waren verschiedene Seifen gelagert. Neugierig ergriff Raven ein Stück aus dem Bereich für Dunkelelfen. Es war ein Block der nach kurzem riechen einen Geruch von *etwas was Dunkelelfen mögen* in ihre Nase Aufstiegen ließ. Nachdem sie sich entkleidet hatte stellte sie sich unter die große Öffnung und spielte mit den Hebeln, um die perfekte Temperatur zu finden. *Welche Temperatur könnte das Mädchen mögen*.";
        text = "können wir uns nacher treffer oder hast du keine Zeit? alternativ geht auch morgen?";
        String prompt = PromptManager.generateBoomImprovementPrompt(bookText1000,"german");
        prompt = null;
        if(prompt != null)
        {
            long startTime = System.currentTimeMillis();
            String test = ChatPost.runPrompt(prompt);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Prompt execution time: " + duration + " milliseconds");
            int i = 1 + test.length();
        }
    }

    @Test
    public void testStreaming()
    {
        String bookText1000 = "Raven fühlte sich wieder privilegiert als sie den verbotenen Bereich betrat. Der Boden war angenehm warm und gefliest wie alles aus schwarz poliertem glatten *Platzhalter*. Sie glaubte dass der Waschraum hauptsächlich von anderen Schatten Wesen wie *Platzhalter* benutzt wurden als sie die Einrichtung näher betrachtete. Es gab an der Decke Öffnungen aus denen Wasser regnete. Ein Mechanismus beruht auf Steinen angereichert mit Feuermagie regulierte die Wärme des Wassers. Mehrere Schalter ermöglichten es unterschiedliche Anzahl an Steinen zu verwenden, um die gewünschte Temperatur zu erzeugen. In einem kleinen Schrank aus glänzenden Hartholz waren verschiedene Seifen gelagert. Neugierig ergriff Raven ein Stück aus dem Bereich für Dunkelelfen. Es war ein Block der nach kurzem riechen einen Geruch von *etwas was Dunkelelfen mögen* in ihre Nase Aufstiegen ließ. Nachdem sie sich entkleidet hatte stellte sie sich unter die große Öffnung und spielte mit den Hebeln, um die perfekte Temperatur zu finden. *Welche Temperatur könnte das Mädchen mögen*.";
        String text = "können wir uns nacher treffer oder hast du keine Zeit? alternativ geht auch morgen?";

        String prompt = PromptManager.generateBoomImprovementPrompt(bookText1000,"german");
        if(prompt != null)
        {
            long startTime = System.currentTimeMillis();
            ChatPostStreaming.main(prompt);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Prompt execution time: " + duration + " milliseconds");
        }
    }
}

