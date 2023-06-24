package com.bootme.stack.service;

import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.dto.StackRequest;
import com.bootme.stack.dto.StackResponse;
import com.bootme.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.ALREADY_SAVED_STACK;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_STACK;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;

    private List<String> languages;
    private List<String> frameworks;
    private static final String LANGUAGE = "language";
    private static final String FRAMEWORK = "framework";

    @Transactional
    public void updateStacks() {
        languages = stackRepository.findByType(LANGUAGE)
                .stream()
                .map(Stack::getName)
                .collect(Collectors.toList());

        frameworks = stackRepository.findByType(FRAMEWORK)
                .stream()
                .map(Stack::getName)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addStacks(List<StackRequest> requests) {
        for (StackRequest request : requests) {
            String name = request.getName();
            String type = request.getType();
            String icon = request.getIcon();
            validateDuplicate(name);
            Stack stack = new Stack(name, type, icon);
            stackRepository.save(stack);
            if (LANGUAGE.equalsIgnoreCase(type)) {
                languages.add(name);
            } else if (FRAMEWORK.equalsIgnoreCase(type)) {
                frameworks.add(name);
            }
        }
    }

    @Transactional
    public void addLanguage(String language, String icon) {
        validateDuplicate(language);
        Stack stack = new Stack(language, LANGUAGE, icon);
        stackRepository.save(stack);
        languages.add(language);
    }

    @Transactional
    public void removeLanguage(String language) {
        Stack stack = getStackByNameAndType(language, LANGUAGE);
        stackRepository.delete(stack);
        languages.remove(language);
    }

    @Transactional
    public void addFramework(String framework, String icon) {
        validateDuplicate(framework);
        Stack stack = new Stack(framework, FRAMEWORK, icon);
        stackRepository.save(stack);
        frameworks.add(framework);
    }

    @Transactional
    public void removeFramework(String framework) {
        Stack stack = getStackByNameAndType(framework, FRAMEWORK);
        stackRepository.delete(stack);
        frameworks.remove(framework);
    }

    @Transactional(readOnly = true)
    public List<StackResponse> findAllStacks() {
        return stackRepository.findAll().stream()
                .map(StackResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Stack getStackByName(String name) {
        return stackRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_STACK, name));
    }

    private Stack getStackByNameAndType(String name, String type) {
        return stackRepository.findByNameAndType(name, type)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_STACK, name));
    }

    private void validateDuplicate(String name){
        boolean isExist = stackRepository.existsByName(name);
        if(isExist){
            throw new ConflictException(ALREADY_SAVED_STACK, name);
        }
    }

}
