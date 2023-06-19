package com.bootme.stack.service;

import com.bootme.common.exception.ConflictException;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.dto.StackRequest;
import com.bootme.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.bootme.common.exception.ErrorType.ALREADY_SAVED_STACK;

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

    private void validateDuplicate(String name){
        boolean isExist = stackRepository.existsByName(name);
        if(isExist){
            throw new ConflictException(ALREADY_SAVED_STACK, name);
        }
    }

}
