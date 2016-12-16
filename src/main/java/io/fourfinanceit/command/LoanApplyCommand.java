package io.fourfinanceit.command;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * Created by cons on 14/12/16.
 */
public class LoanApplyCommand {

    @DecimalMin("50.00")
    @DecimalMax("425.00")
    private BigDecimal amount;

    @Min(10)
    private Integer days;

    @NotBlank
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String customerEmail;

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getDays() {
        return days;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
