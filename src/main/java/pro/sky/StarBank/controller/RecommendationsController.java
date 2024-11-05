package pro.sky.StarBank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.StarBank.repository.RecommendationsRepository;
import pro.sky.StarBank.service.DynamicRulesService;
import pro.sky.StarBank.service.QueryTypeService;
import pro.sky.StarBank.service.RecommendationsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("recommendation")
public class RecommendationsController {

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationsService recommendationsService;
    private final DynamicRulesService dynamicRulesService;
    private final QueryTypeService queryTypeService;

    public RecommendationsController(RecommendationsRepository recommendationsRepository,
                                     RecommendationsService recommendationsService,
                                     DynamicRulesService dynamicRulesService,
                                     QueryTypeService queryTypeService) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsService = recommendationsService;
        this.dynamicRulesService = dynamicRulesService;
        this.queryTypeService = queryTypeService;
    }

    @GetMapping("transaction_amount")
    public Integer getRandomTransactionAmountForCurrentUser(UUID userID) {
        return recommendationsRepository.getRandomTransactionAmount(userID);
    }

    @GetMapping("{users_id}")
    public String getListOfRecommendationsForUser(@PathVariable("users_id") UUID users_id) {
        return "user_id: " + users_id + ", \nrecommendations: " +
                recommendationsService.getRecommendations(users_id);
    }

    @GetMapping("get/{users_id}")
    public List<String> getListOfRecommendationsForUserWithCheckRules(@PathVariable("users_id") UUID users_id) {
        return recommendationsService.getListOfRecommendationsForUserWithCheckRules(users_id);
    }
}
