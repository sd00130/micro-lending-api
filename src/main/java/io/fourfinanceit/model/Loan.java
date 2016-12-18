package io.fourfinanceit.model;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    private Term term;

    private String customerEmail;

    private String ipAddress;

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private List<LoanExtension> extensions = newArrayList();

    public Loan() {
    }

    public Loan(BigDecimal amount, Term dateTimeRange, String inetAddress) {
        this(amount, dateTimeRange);
        this.ipAddress = inetAddress;
    }

    public Loan(BigDecimal amount, Term dateTimeRange, String inetAddress, String email) {
        this(amount, dateTimeRange, inetAddress);
        this.customerEmail = email;
    }


    public Loan(BigDecimal amount, Term dateTimeRange) {
        this.initialAmount = amount;
        this.term = dateTimeRange;
    }

    public BigDecimal getFinalAmount() {
        BigDecimal extensionsFee  = extensions.stream()
                .map(e -> e.getAmount())
                .reduce((a1, a2) -> a1.add(a2)).orElse(BigDecimal.ZERO);
        return initialAmount.add(extensionsFee);
    }

    public void extend(BigDecimal interestFactor) {
        LoanExtension extension = new LoanExtension(DateTime.now().toDate(), interestFactor);
        if (extensions == null) {
            extensions = newArrayList();
        }
        extensions.add(extension);
    }

    public void calculateExtensionFees() {
        extensions.stream().forEach(e -> e.calculateAmount(initialAmount));
    }

    public Date getFinalDate() {
        int weeks = (int) extensions.stream().count();
        return term.extendByWeeks(weeks);
    }


    public boolean isOver() {
        return !new DateTime(getFinalDate()).isAfterNow();
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

    public Term getTerm() {
        return term;
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
                Objects.equal(term, loan.term) &&
                Objects.equal(customerEmail, loan.customerEmail) &&
                Objects.equal(ipAddress, loan.ipAddress) &&
                Objects.equal(extensions, loan.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, initialAmount, term, customerEmail, ipAddress, extensions);
    }
}
