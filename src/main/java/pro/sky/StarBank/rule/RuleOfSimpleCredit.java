package pro.sky.StarBank.rule;

import org.springframework.stereotype.Component;
import pro.sky.StarBank.model.Recommendations;
import pro.sky.StarBank.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleOfSimpleCredit implements RecommendationsRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    public RuleOfSimpleCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<Recommendations> getRecommendations(UUID users_id) {
        if (!recommendationsRepository.hasCreditProduct(users_id) &&
                recommendationsRepository.getDebitAmount(users_id) > recommendationsRepository.getDebitExpenses(users_id) &&
                recommendationsRepository.getDebitExpenses(users_id) > 100_000
        ) {
            return Optional.of(new Recommendations("Простой кредит",
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    "Откройте мир выгодных кредитов с нами!\n" +
                            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит" +
                            " — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и" +
                            " индивидуальный подход к каждому клиенту.\n" +
                            "Почему выбирают нас:\n" +
                            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки " +
                            "занимает всего несколько часов.\n" +
                            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном" +
                            " приложении.\n" +
                            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку " +
                            "недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"));
        }
        return Optional.empty();
    }
}
