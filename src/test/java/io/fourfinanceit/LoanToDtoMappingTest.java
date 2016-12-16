package io.fourfinanceit;

import io.fourfinanceit.configuration.mapping.MapperFacadeProvider;
import io.fourfinanceit.model.DateTimeRange;
import io.fourfinanceit.model.Loan;
import io.fourfinanceit.query.LoanDto;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cons on 15/12/16.
 */
public class LoanToDtoMappingTest {

    private static final BigDecimal INTEREST_FACTOR = new BigDecimal(1.5);

    private MapperFacadeProvider mapperFacadeProvider;

    @Before
    public void setUp() {
        mapperFacadeProvider = new MapperFacadeProvider();
    }

    @Test
    public void mappingTest() {
        DateTime now = DateTime.now();
        Loan loan = new Loan(new BigDecimal(200.00),
                            new DateTimeRange(now.toDate(), 30),
                            "192.168.0.3",
                            "loantaker@fourfinance.it");
        loan.extend(INTEREST_FACTOR);
        loan.extend(INTEREST_FACTOR);
        loan.extend(INTEREST_FACTOR);
        loan.extend(INTEREST_FACTOR);

        LoanDto loanDto = mapperFacadeProvider.mapperFacade().map(loan, LoanDto.class);

        assertThat(loanDto.getCustomer()).isEqualTo("loantaker@fourfinance.it");
        assertThat(loanDto.getInitialAmount()).isEqualTo(new BigDecimal(200.00));
        assertThat(loanDto.getExtensions()).hasSize(4);
        assertThat(loanDto.getStart()).isEqualTo(now.toDate());
        assertThat(loanDto.getEnd()).isEqualTo(now.plusDays(30).withTimeAtStartOfDay().plusDays(1).toDate());
        assertThat(loanDto.getFinalAmount().compareTo(BigDecimal.valueOf(212.000))).isEqualTo(0);
    }
}
