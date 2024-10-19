package pro.sky.StarBank.rule;

import pro.sky.StarBank.model.Recommendations;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationsRuleSet {

    Optional<Recommendations> getRecommendations(UUID users_id);
}
