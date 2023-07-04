package com.bootme.prompt.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromptGenerationRequest {

    @NotBlank
    private String promptType;

    // fields from GeneralPromptRequest
    private String question;
    private String example;

    // fields from FeaturePromptRequest
    private String featureDescription;
    private String exampleCode;

    // fields from ApiDesignPromptRequest
    private String apiDescription;
    private String erd;
    private String entity;

    // common fields
    private List<String> techStack;
    private List<String> principles;
    private String responseFormat;
    private String promptLanguage;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("promptType", promptType);

        if (question != null && !question.isEmpty()) {
            node.put("question", question);
        }
        if (example != null && !example.isEmpty()) {
            node.put("example", example);
        }
        if (featureDescription != null && !featureDescription.isEmpty()) {
            node.put("featureDescription", featureDescription);
        }
        if (exampleCode != null && !exampleCode.isEmpty()) {
            node.put("exampleCode", exampleCode);
        }
        if (apiDescription != null && !apiDescription.isEmpty()) {
            node.put("apiDescription", apiDescription);
        }
        if (erd != null && !erd.isEmpty()) {
            node.put("erd", erd);
        }
        if (entity != null && !entity.isEmpty()) {
            node.put("entity", entity);
        }
        if (techStack != null && !techStack.isEmpty()) {
            node.putPOJO("techStack", techStack);
        }
        if (principles != null && !principles.isEmpty()) {
            node.putPOJO("principles", principles);
        }
        if (responseFormat != null && !responseFormat.isEmpty()) {
            node.put("responseFormat", responseFormat);
        }
        if (promptLanguage != null && !promptLanguage.isEmpty()) {
            node.put("promptLanguage", promptLanguage);
        }
        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
