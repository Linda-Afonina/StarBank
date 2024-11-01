package pro.sky.StarBank.model;

import java.util.List;
import java.util.Objects;

public class DynamicRules {

    private Long id;
    private String productName;
    private String productId;
    private String productText;
    private List<RulesQuery> rule;

    public DynamicRules() {
    }

    public DynamicRules(Long id, String productName, String productId, String productText, List<RulesQuery> rule) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rule = rule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RulesQuery> getRule() {
        return rule;
    }

    public void setRule(List<RulesQuery> rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRules that = (DynamicRules) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText) && Objects.equals(rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rule);
    }

    @Override
    public String toString() {
        return "DynamicRules{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", productText='" + productText + '\'' +
                ", rule=" + rule +
                '}';
    }
}
