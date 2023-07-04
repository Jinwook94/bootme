package com.bootme.prompt.service;

import com.bootme.prompt.dto.PromptGenerationRequest;
import org.springframework.stereotype.Component;

@Component
public class ApiDesignUserInputProcessor implements UserInputProcessor {

    @Override
    public String process(PromptGenerationRequest request) {
        StringBuilder userMessage = new StringBuilder();

        userMessage.append("I need to design an API with the following description: \n```");
        userMessage.append(request.getApiDescription());
        userMessage.append("```\n\n");

        if (!request.getErd().isEmpty()) {
            userMessage.append("The ERD for this API design is: \n```");
            userMessage.append(request.getErd());
            userMessage.append("```\n\n");
        }

        if (!request.getEntity().isEmpty()) {
            userMessage.append("The entities involved are: \n```");
            userMessage.append(request.getEntity());
            userMessage.append("```\n\n");
        }

        return userMessage.toString();
    }

}
