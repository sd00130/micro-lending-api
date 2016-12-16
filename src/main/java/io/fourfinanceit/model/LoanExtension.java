package io.fourfinanceit.model;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cons on 14/12/16.
 */
@Entity
public class LoanExtension {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal interestFactor;

    private Date whenExtended;

    public LoanExtension() {
    }

    public LoanExtension(Date whenExtended, BigDecimal interestFactor) {
        this.whenExtended = whenExtended;
        this.interestFactor = interestFactor;
    }

    public BigDecimal getInterestFactor() {
        return interestFactor;
    }

    public Date getWhenExtended() {
        return whenExtended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanExtension)) return false;
        LoanExtension that = (LoanExtension) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(interestFactor, that.interestFactor) &&
                Objects.equal(whenExtended, that.whenExtended);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, interestFactor, whenExtended);
    }
}
