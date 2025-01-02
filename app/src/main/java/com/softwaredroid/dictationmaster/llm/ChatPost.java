package com.softwaredroid.dictationmaster.llm;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.ChatMessage;
import com.cohere.api.types.Message;
import com.cohere.api.types.NonStreamedChatResponse;

import java.util.List;


public class ChatPost {
    public static void main(String[] args) {
        Cohere cohere = Cohere.builder().token(CohereAPIKey.KEY).clientName("snippet").build();

        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("What year was he born?")
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Who discovered gravity?").build()),
                                        Message.chatbot(ChatMessage.builder().message("The man who is widely credited with discovering gravity is Sir Isaac Newton").build()))).build());

        System.out.println(response);
    }
}
