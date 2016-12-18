package io.fourfinanceit.model;

import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cons on 18/12/16.
 */
public class LoanTest {

    private static final BigDecimal INTEREST_FACTOR = new BigDecimal(1.5);

    @Test
    public void shouldGetFinalDate() {
        DateTime now = DateTime.now();
        Loan loan = new Loan(new BigDecimal(200.00),
                new Term(now.toDate(), 30),
                "192.168.0.3",
                "loantaker@fourfinance.it");
        loan.extend(INTEREST_FACTOR);

        Date expected = now.plusDays(30).plusWeeks(1).toDate();
        assertThat(expected.compareTo(loan.getFinalDate())).isEqualTo(0);
    }

    @Test
    public void shouldGetFinalAmount() {
        DateTime now = DateTime.now();
        Loan loan = new Loan(new BigDecimal(200.00),
                new Term(now.toDate(), 30),
                "192.168.0.3",
                "loantaker@fourfinance.it");
        loan.extend(INTEREST_FACTOR);
        loan.calculateExtensionFees();

        assertThat(loan.getFinalAmount().compareTo(new BigDecimal(203.000))).isEqualTo(0);
    }
}
