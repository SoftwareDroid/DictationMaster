package com.softwaredroid.dictationmaster.llm;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.ChatMessage;
import com.cohere.api.types.Message;
import com.cohere.api.types.NonStreamedChatResponse;

import java.util.List;


public class ChatPost
{
    public static String test(String message)
    {
        Cohere cohere = Cohere.builder().token(CohereAPIKey.KEY).clientName("snippet").build();
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message(message).build());

        return response.getText();
    }
}
