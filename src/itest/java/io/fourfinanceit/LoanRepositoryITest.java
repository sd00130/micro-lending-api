package io.fourfinanceit;

import io.fourfinanceit.model.DateTimeRange;
import io.fourfinanceit.model.Loan;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cons on 14/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class LoanRepositoryITest {

    @Autowired
    LoanRepository loanRepository;

    @Before
    public void setUp() {
        Loan loan = new Loan(new BigDecimal(10.00), new DateTimeRange(new Date(), 10), "192.168.0.2", "loantaker@fourfinance.it");
        Loan anotherLoan = new Loan(new BigDecimal(10.00), new DateTimeRange(new Date(), 10), "192.168.0.3", "loangiver@fourfinance.it");
        loanRepository.save(Arrays.asList(loan, anotherLoan));
    }

    @Test
    public void shouldGetLoansForCustomer() {
        List<Loan> loans = loanRepository.findByCustomerEmail("loantaker@fourfinance.it");
        assertThat(loans).hasSize(1);
        assertThat(loans.get(0).getCustomerEmail()).isEqualTo("loantaker@fourfinance.it");
    }

    @Test
    public void shouldGetByDateAndIPTest() {
        DateTime now = DateTime.now();
        Loan loanBefore = new Loan(new BigDecimal(20.00), new DateTimeRange(now.minusDays(3).toDate(), 30), "192.168.0.3", "loantaker@fourfinance.it");
        Loan loanAfter = new Loan(new BigDecimal(30.00), new DateTimeRange(now.plusDays(3).toDate(), 50), "192.168.0.1", "loantaker@fourfinance.it");
        loanRepository.save(Arrays.asList(loanBefore, loanAfter));
        List<Loan> loans = loanRepository.findByRangeStartBetweenAndIpAddress(now.withTimeAtStartOfDay().toDate(), now.plusDays(1).toDate(), "192.168.0.3");
        assertThat(loans).hasSize(1);
    }

}
