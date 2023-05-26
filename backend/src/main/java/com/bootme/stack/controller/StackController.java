package com.bootme.stack.controller;

import com.bootme.stack.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stacks")
@RequiredArgsConstructor
public class StackController {

    private final StackService stackService;

    @PostMapping
    public ResponseEntity<Void> addStacks(@RequestBody List<Map<String, String>> stacks){
        stackService.addStacks(stacks);
        return ResponseEntity.ok().build();
    }

}
