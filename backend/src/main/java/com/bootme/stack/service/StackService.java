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
@Transactional
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;

    public void addStacks(List<StackRequest> requests) {
        for (StackRequest request : requests) {
            String name = request.getName();
            String type = request.getType();
            String icon = request.getIcon();
            validateDuplicate(name);

            stackRepository.save(new Stack(name, type, icon));
        }
    }

    public List<StackResponse> findAllStacks() {
        return stackRepository.findAll().stream()
                .map(StackResponse::of)
                .collect(Collectors.toList());
    }

    private void validateDuplicate(String name){
        boolean isExist = stackRepository.existsByName(name);
        if(isExist){
            throw new ConflictException(ALREADY_SAVED_STACK, name);
        }
    }

    public Stack getStackByName(String name) {
        return stackRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_STACK, name));
    }

}
