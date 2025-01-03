package com.softwaredroid.dictationmaster.llm;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.requests.ChatStreamRequest;
import com.cohere.api.resources.v2.requests.V2ChatStreamRequest;
import com.cohere.api.types.AssistantMessage;
import com.cohere.api.types.AssistantMessageContent;
import com.cohere.api.types.ChatMessageV2;
import com.cohere.api.types.ChatStreamRequestSafetyMode;
import com.cohere.api.types.NonStreamedChatResponse;
import com.cohere.api.types.StreamedChatResponseV2;
import com.cohere.api.types.UserMessage;
import com.cohere.api.types.UserMessageContent;

import java.util.List;


public class ChatPost
{
    public static String runPrompt(String message)
    {
        Cohere cohere = Cohere.builder().token(CohereAPIKey.KEY).clientName("snippet").build();
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message(message).build());

        return response.getText();
    }

}
