package com.bootme.config;

import com.bootme.stack.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final StackService stackService;

    @Override
    public void run(ApplicationArguments args) {
        stackService.updateStacks();
    }

}
