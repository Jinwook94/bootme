package com.bootme.prompt.service;

import com.bootme.prompt.dto.PromptGenerationRequest;
import org.springframework.stereotype.Component;

@Component
public class FeatureUserInputProcessor implements UserInputProcessor {

    @Override
    public String process(PromptGenerationRequest request) {
        StringBuilder userMessage = new StringBuilder();

        userMessage.append("I need a feature with the following description: \n```");
        userMessage.append(request.getFeatureDescription());
        userMessage.append("```\n\n");

        if (!request.getExampleCode().isEmpty()) {
            userMessage.append("You can use the provided code snippet as a starting point: \n```");
            userMessage.append(request.getExampleCode());
            userMessage.append("```\n\n");
        }

        if (!request.getTechStack().isEmpty()) {
            userMessage.append("The technology stacks for this feature are: \n```");
            userMessage.append(String.join(", ", request.getTechStack()));
            userMessage.append("```\n\n");
        }

        if (!request.getPrinciples().isEmpty()) {
            userMessage.append("The following principles should be taken into account for this feature: \n```");
            userMessage.append(String.join(", ", request.getPrinciples()));
            userMessage.append("```\n\n");
        }

        return userMessage.toString();
    }

}
