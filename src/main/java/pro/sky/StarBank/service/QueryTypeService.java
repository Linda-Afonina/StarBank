package pro.sky.StarBank.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.StarBank.model.QueryType;
import pro.sky.StarBank.repository.QueryTypeRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class QueryTypeService {

    private final QueryTypeRepository queryTypeRepository;

    private final ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(QueryTypeService.class);

    public QueryTypeService(QueryTypeRepository queryTypeRepository, ObjectMapper objectMapper) {
        this.queryTypeRepository = queryTypeRepository;
        this.objectMapper = objectMapper;
    }

    private boolean checkQuery(Map<String, Object> queryMap, UUID userId) {
        if (queryMap == null || userId == null) {
            throw new IllegalArgumentException("QueryMap or userId cannot be null");
        }

        try {
            Object queryObj = queryMap.get("query");
            if (queryObj == null) {
                throw new IllegalArgumentException("Query cannot be null");
            }

            QueryType queryType = QueryType.valueOf((String) queryObj);

            Object argumentsObj = queryMap.get("arguments");
            if (argumentsObj == null) {
                throw new IllegalArgumentException("Arguments cannot be null");
            }

            List<String> arguments = objectMapper.convertValue(argumentsObj,
                    new TypeReference<List<String>>() {
                    });

            logger.info("Query Type: " + queryType);
            logger.info("Arguments: " + arguments);

            switch (queryType) {
                case USER_OF:
                    return queryTypeRepository.checkUserOfQuery(arguments, userId);
                case ACTIVE_USER_OF:
                    return queryTypeRepository.checkActiveUserOfQuery(arguments, userId);
                case TRANSACTION_SUM_COMPARE:
                    return queryTypeRepository.checkTransactionSumCompareQuery(arguments, userId);
                case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW:
                    return queryTypeRepository.checkTransactionSumCompareDepositWithdrawQuery(arguments, userId);
                default:
                    throw new IllegalArgumentException("Unknown query type: " + queryType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to check query", e);
        }
    }

    public boolean checkRule(Map<String, Object> rule, UUID user_Id) {
        if (rule == null || user_Id == null) {
            throw new IllegalArgumentException("Rule or user_Id cannot be null");
        }

        try {
            logger.info("Rule: " + rule);
            logger.info("User ID: " + user_Id);

            if (rule.get("rule") instanceof List) {
                List<Map<String, Object>> ruleQueries = objectMapper.convertValue(rule.get("rule"),
                        new TypeReference<List<Map<String, Object>>>() {
                        });

                logger.info("Rule Queries: " + ruleQueries);

                for (Map<String, Object> queryMap : ruleQueries) {
                    boolean result = checkQuery(queryMap, user_Id);
                    if (!result) {
                        return false;
                    }
                }
                return true;
            } else {
                Map<String, Object> ruleQuery = objectMapper.convertValue(rule.get("rule"),
                        new TypeReference<Map<String, Object>>() {
                        });

                logger.info("Rule Query: " + ruleQuery);

                boolean result = checkQuery(ruleQuery, user_Id);
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to check rule", e);
        }
    }
}
