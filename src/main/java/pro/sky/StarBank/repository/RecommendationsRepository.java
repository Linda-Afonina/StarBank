package pro.sky.StarBank.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public boolean hasDebitProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'", Integer.class, user_id);

        return result > 0 ? true : false;
    }

    public boolean hasInvestProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'INVEST'", Integer.class, user_id);

        return result > 0 ? true : false;
    }

    public boolean hasCreditProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'CREDIT'", Integer.class, user_id);

        return result > 0 ? true : false;
    }

    public Long getSavingAmount(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'SAVING' AND t.TYPE = 'DEPOSIT'",
                Long.class, user_id);

        return result != null ? result : 0;
    }

    public Long getDebitAmount(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT' AND t.TYPE = 'DEPOSIT'",
                Long.class, user_id);

        return result != null ? result : 0;
    }

    public Long getDebitExpenses(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT' AND p.TYPE = 'WITHDRAW'",
                Long.class, user_id);

        return result != null ? result : 0;
    }
}
