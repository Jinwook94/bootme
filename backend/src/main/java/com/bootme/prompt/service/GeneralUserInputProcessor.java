package com.bootme.prompt.service;

import com.bootme.prompt.dto.PromptGenerationRequest;
import org.springframework.stereotype.Component;

@Component
public class GeneralUserInputProcessor implements UserInputProcessor {

    @Override
    public String process(PromptGenerationRequest request) {
        StringBuilder userMessage = new StringBuilder();

        userMessage.append("I need you to generate a prompt where the task is: \n```");
        userMessage.append(request.getQuestion());
        userMessage.append("```\n\n");

        if (!request.getExample().isEmpty()) {
            userMessage.append("You can use the provided example as a starting point, Make sure to adjust the answer as per the specific application requirements: \n```");
            userMessage.append(request.getExample());
            userMessage.append("```\n\n");
        }

        if (!request.getTechStack().isEmpty()) {
            userMessage.append("The technology stacks involved are: \n```");
            userMessage.append(String.join(", ", request.getTechStack()));
            userMessage.append("```\n\n");
        }

        if (!request.getPrinciples().isEmpty()) {
            userMessage.append("The following principles should be taken into account: \n```");
            userMessage.append(String.join(", ", request.getPrinciples()));
            userMessage.append("```\n\n");
        }

        return userMessage.toString();
    }

}
