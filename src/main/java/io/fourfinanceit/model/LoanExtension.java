package io.fourfinanceit.model;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private Date extensionDate;

    @Transient
    private BigDecimal amount;

    public LoanExtension() {
    }

    public LoanExtension(Date extensionDate, BigDecimal interestFactor) {
        this.extensionDate = extensionDate;
        this.interestFactor = interestFactor;
    }

    public void calculateAmount(BigDecimal loanInitialAmount) {
        BigDecimal interestInPercents = interestFactor.divide(new BigDecimal(100));
        amount = loanInitialAmount.multiply(interestInPercents).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getInterestFactor() {
        return interestFactor;
    }

    public Date getExtensionDate() {
        return extensionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanExtension)) return false;
        LoanExtension that = (LoanExtension) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(interestFactor, that.interestFactor) &&
                Objects.equal(extensionDate, that.extensionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, interestFactor, extensionDate);
    }

}
