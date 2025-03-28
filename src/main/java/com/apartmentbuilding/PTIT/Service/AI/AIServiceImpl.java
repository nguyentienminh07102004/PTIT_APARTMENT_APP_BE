package com.apartmentbuilding.PTIT.Service.AI;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements IAIService {
    private final OpenAiChatModel openAiChatModel;

    @Override
    public String answerQuestion(String question) {
        return openAiChatModel.call(question);
    }
}
