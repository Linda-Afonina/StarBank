package pro.sky.StarBank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.StarBank.model.DynamicRules;
import pro.sky.StarBank.repository.DynamicRulesRepository;

import java.util.List;
import java.util.Map;


@Service
public class DynamicRulesService {

    private final DynamicRulesRepository dynamicRulesRepository;

    Logger logger = LoggerFactory.getLogger(DynamicRulesService.class);

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
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> list = dynamicRulesRepository.getAllRules();
        long endTime = System.currentTimeMillis();
        logger.info("Start time: " + startTime + ", end time: " + endTime + ", operation time: "
                + (endTime - startTime) + " ms.");
        return list;
    }
}
