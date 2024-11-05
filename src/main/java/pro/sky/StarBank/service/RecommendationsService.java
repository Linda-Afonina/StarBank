package pro.sky.StarBank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.StarBank.model.Recommendations;
import pro.sky.StarBank.rule.RecommendationsRuleSet;

import java.util.*;

@Service
public class RecommendationsService {

    private final List<RecommendationsRuleSet> recommendationsRuleSetList;
    private final DynamicRulesService dynamicRulesService;
    private final QueryTypeService queryTypeService;
    Logger logger = LoggerFactory.getLogger(RecommendationsService.class);

    public RecommendationsService(List<RecommendationsRuleSet> recommendationsRuleSetList,
                                  DynamicRulesService dynamicRulesService, QueryTypeService queryTypeService) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
        this.dynamicRulesService = dynamicRulesService;
        this.queryTypeService = queryTypeService;
    }


    public List<Recommendations> getRecommendations(UUID users_id) {
        List<Recommendations> recommendationsList = new ArrayList<>();
        for (RecommendationsRuleSet recommendationsRuleSet : recommendationsRuleSetList) {
            Optional<Recommendations> recommendations = recommendationsRuleSet.getRecommendations(users_id);
            if (recommendations.isPresent()) {
                recommendationsList.add(recommendations.get());
            }
        }
        return recommendationsList;
    }

    public List<String> getListOfRecommendationsForUserWithCheckRules(UUID users_id) {
        List<Map<String, Object>> rules = dynamicRulesService.getAllRules();
        List<String> recommendations = new ArrayList<>();

        logger.info("Rules: " + rules);
        logger.info("User ID: " + users_id);

        for (Map<String, Object> rule : rules) {

            logger.info("Processing rule: " + rule);

            if (queryTypeService.checkRule(rule, users_id)) {
                String productName = (String) rule.get("productName");
                if (productName != null) {
                    recommendations.add(productName);
                } else {
                    logger.info("Product name is null for rule: " + rule);
                }
            } else {
                logger.info("Rule did not pass check: " + rule);
            }
        }

        return recommendations;
    }

}
