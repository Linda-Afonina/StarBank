package pro.sky.StarBank.service;

import org.springframework.stereotype.Service;
import pro.sky.StarBank.model.DynamicRules;
import pro.sky.StarBank.repository.DynamicRulesRepository;

import java.util.List;
import java.util.Map;

@Service
public class DynamicRulesService {

    private final DynamicRulesRepository dynamicRulesRepository;

    public DynamicRulesService(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    public void addRule(DynamicRules rule) {
        dynamicRulesRepository.addRule(rule);
    }

    public void deleteRule(Long id) {
        dynamicRulesRepository.deleteRule(id);
    }

    public List<Map<String, Object>> getAllRules() {
        return dynamicRulesRepository.getAllRules();
    }
}
