package pro.sky.StarBank.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pro.sky.StarBank.model.DynamicRules;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Repository
public class DynamicRulesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DynamicRulesRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    public void addRule(DynamicRules rule) {
//        jdbcTemplate.update(
//                "INSERT INTO dynamic_rules (product_id, product_name, product_text, rule) VALUES (?, ?, ?, ?)",
//                rule.getProductId(), rule.getProductName(), rule.getProductText(),
//                rule.getRule());
//    }
    public void addRule(DynamicRules rule) {
        String sql = "INSERT INTO dynamic_rules (product_name, product_id, product_text, rule) VALUES (?, ?, ?, ?::jsonb)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            String ruleJson = objectMapper.writeValueAsString(rule.getRule());

            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, rule.getProductName());
                    ps.setString(2, rule.getProductId());
                    ps.setString(3, rule.getProductText());
                    ps.setString(4, ruleJson);
                    return ps;
                }
            }, keyHolder);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to JSON", e);
        }
    }

    public void deleteRule(Long id) {
        jdbcTemplate.update("DELETE FROM dynamic_rules WHERE id = ?", id);
    }

    public List<Map<String, Object>> getAllRules() {
        return jdbcTemplate.queryForList("SELECT * FROM dynamic_rules");
    }
}
