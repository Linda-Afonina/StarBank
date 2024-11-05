package pro.sky.StarBank.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class QueryTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public QueryTypeRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkUserOfQuery(List<String> query, UUID user_Id) {
        String productType = query.get(0);
        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ?",
                Integer.class, user_Id, productType);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean checkActiveUserOfQuery(List<String> query, UUID user_Id) {
        String productType = query.get(0);
        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ?",
                Integer.class, user_Id, productType);
        if (count >= 5) {
            return true;
        }
        return false;
    }

    public boolean checkTransactionSumCompareQuery(List<String> query, UUID user_Id) {
        String productType = query.get(0);
        String transactionType = query.get(1);
        String operator = query.get(2);
        int value = Integer.parseInt(query.get(3));

        Integer sum = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = ?",
                Integer.class, user_Id, productType, transactionType);

        if (sum == null) {
            sum = 0;
        }

        switch (operator) {
            case ">":
                return sum > value;
            case "<":
                return sum < value;
            case "=":
                return sum == value;
            case ">=":
                return sum >= value;
            case "<=":
                return sum <= value;
            default:
                throw new IllegalArgumentException("Wrong operator: " + operator);
        }
    }

    public boolean checkTransactionSumCompareDepositWithdrawQuery(List<String> query, UUID user_Id) {
        String productType = query.get(0);
        String operator = query.get(1);

        Integer sumDeposit = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'DEPOSIT'",
                Integer.class, user_Id, productType);

        Integer sumWithdraw = jdbcTemplate.queryForObject(
                "SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'",
                Integer.class, user_Id, productType);

        if (sumDeposit == null) {
            sumDeposit = 0;
        }
        if (sumWithdraw == null) {
            sumWithdraw = 0;
        }

        switch (operator) {
            case ">":
                return sumDeposit > sumWithdraw;
            case "<":
                return sumDeposit < sumWithdraw;
            case "=":
                return sumDeposit == sumWithdraw;
            case ">=":
                return sumDeposit >= sumWithdraw;
            case "<=":
                return sumDeposit <= sumWithdraw;
            default:
                throw new IllegalArgumentException("Wrong operator: " + operator);
        }
    }
}
