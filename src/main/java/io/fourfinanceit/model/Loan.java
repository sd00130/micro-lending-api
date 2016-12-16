package io.fourfinanceit.model;

import com.google.common.base.*;
import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;


/**
 * Created by cons on 14/12/16.
 */
@Entity
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal initialAmount;

    private DateTimeRange range;

    private String customerEmail;

    private String ipAddress;

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private List<LoanExtension> extensions = newArrayList();

    public Loan() {
    }

    public Loan(BigDecimal amount, DateTimeRange dateTimeRange, String inetAddress) {
        this(amount, dateTimeRange);
        this.ipAddress = inetAddress;
    }

    public Loan(BigDecimal amount, DateTimeRange dateTimeRange, String inetAddress, String email) {
        this(amount, dateTimeRange, inetAddress);
        this.customerEmail = email;
    }


    public Loan(BigDecimal amount, DateTimeRange dateTimeRange) {
        this.initialAmount = amount;
        this.range = dateTimeRange;
    }

    public BigDecimal getFinalAmount() {
        BigDecimal extensionFee = extensions.stream()
                .map(e -> e.getInterestFactor().divide(new BigDecimal(100)))
                .map(f -> f.multiply(initialAmount))
                .reduce((f1, f2) -> f1.add(f2)).orElse(BigDecimal.ZERO);
        return initialAmount.add(extensionFee);
    }

    public Date getFinalDate() {
        int weeks = (int) extensions.stream().count();
        return range.getFinalDate(weeks);
    }

    public void extend(BigDecimal interestFactor) {
        LoanExtension extension = new LoanExtension(DateTime.now().toDate(), interestFactor);
        if (extensions == null) {
            extensions = newArrayList();
        }
        extensions.add(extension);
    }

    public boolean isOver() {
        return range.isOver();
    }

    public Long getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public DateTimeRange getRange() {
        return range;
    }

    public List<LoanExtension> getExtensions() {
        return extensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return com.google.common.base.Objects.equal(id, loan.id) &&
                Objects.equal(initialAmount, loan.initialAmount) &&
                Objects.equal(range, loan.range) &&
                Objects.equal(customerEmail, loan.customerEmail) &&
                Objects.equal(ipAddress, loan.ipAddress) &&
                Objects.equal(extensions, loan.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, initialAmount, range, customerEmail, ipAddress, extensions);
    }
}
