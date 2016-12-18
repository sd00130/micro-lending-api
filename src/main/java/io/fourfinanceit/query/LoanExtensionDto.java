package io.fourfinanceit.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cons on 15/12/16.
 */
public class LoanExtensionDto {

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date extensionDate;

    private BigDecimal interestFactor;

    private BigDecimal amount;

    public LoanExtensionDto() {
    }

    public Date getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(Date extensionDate) {
        this.extensionDate = extensionDate;
    }

    public BigDecimal getInterestFactor() {
        return interestFactor;
    }

    public void setInterestFactor(BigDecimal interestFactor) {
        this.interestFactor = interestFactor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
