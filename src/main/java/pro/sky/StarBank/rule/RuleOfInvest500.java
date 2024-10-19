package pro.sky.StarBank.rule;

import org.springframework.stereotype.Component;
import pro.sky.StarBank.model.Recommendations;
import pro.sky.StarBank.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleOfInvest500 implements RecommendationsRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    public RuleOfInvest500(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    @Override
    public Optional<Recommendations> getRecommendations(UUID users_id) {
        if (recommendationsRepository.hasDebitProduct(users_id) &&
            !recommendationsRepository.hasInvestProduct(users_id) &&
            recommendationsRepository.getSavingAmount(users_id) > 1000) {
            return Optional.of(new Recommendations("Invest 500",
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                            "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет " +
                            "до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. " +
                            "Не упустите возможность разнообразить свой портфель, снизить риски и следить за " +
                            "актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой " +
                            "независимости!"));
        }
        return Optional.empty();
    }
}
