package com.bootme.stack.controller;

import com.bootme.stack.dto.StackRequest;
import com.bootme.stack.dto.StackResponse;
import com.bootme.stack.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stacks")
@RequiredArgsConstructor
public class StackController {

    private final StackService stackService;

    @GetMapping
    public ResponseEntity<List<StackResponse>> findAllStacks() {
        List<StackResponse> stacks = stackService.findAllStacks();
        return ResponseEntity.ok(stacks);
    }

    @PostMapping
    public ResponseEntity<Void> addStacks(@RequestBody List<StackRequest> requests) {
        stackService.addStacks(requests);
        return ResponseEntity.ok().build();
    }

}
