package pro.sky.StarBank.service;

import org.springframework.stereotype.Service;
import pro.sky.StarBank.model.Recommendations;
import pro.sky.StarBank.rule.RecommendationsRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationsService {

    private final List<RecommendationsRuleSet> recommendationsRuleSetList;

    public RecommendationsService(List<RecommendationsRuleSet> recommendationsRuleSetList) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
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

}
