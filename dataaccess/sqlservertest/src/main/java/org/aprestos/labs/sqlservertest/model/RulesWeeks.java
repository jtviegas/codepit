package org.aprestos.labs.sqlservertest.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "BS_RULES_WEEKS")
public class RulesWeeks {

    @Id
    @Column(name="WEEK_ID", unique = true, nullable = false)
    private BigDecimal id;
    @Column(name="WEEK_DESC")
    private String weekDescription;

    public RulesWeeks(){}


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getWeekDescription() {
        return weekDescription;
    }

    public void setWeekDescription(String weekDescription) {
        this.weekDescription = weekDescription;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RulesWeeks)) return false;
        RulesWeeks that = (RulesWeeks) o;
        return getId().equals(that.getId()) &&
                Objects.equals(getWeekDescription(), that.getWeekDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWeekDescription());
    }
}
