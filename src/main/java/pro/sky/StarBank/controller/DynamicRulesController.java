package pro.sky.StarBank.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.StarBank.model.DynamicRules;
import pro.sky.StarBank.service.DynamicRulesService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rules")
public class DynamicRulesController {

    private final DynamicRulesService dynamicRulesService;

    public DynamicRulesController(DynamicRulesService dynamicRulesService) {
        this.dynamicRulesService = dynamicRulesService;
    }

    @PostMapping
    public void addRule(@RequestBody DynamicRules rule) {
        dynamicRulesService.addRule(rule);
    }

    @DeleteMapping("{id}")
    public void deleteRule(@PathVariable("id") Long id) {
        dynamicRulesService.deleteRule(id);
    }

    @GetMapping("all_rules")
    public List<Map<String, Object>> getAllRules() {
        return dynamicRulesService.getAllRules();
    }
}
