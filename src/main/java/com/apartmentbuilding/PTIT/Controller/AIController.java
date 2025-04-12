package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.Service.AI.IAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ai")
public class AIController {
    private final IAIService aiService;

    @GetMapping
    public String question(@RequestParam(value = "q") String question) {
        return aiService.answerQuestion(question);
    }
}
