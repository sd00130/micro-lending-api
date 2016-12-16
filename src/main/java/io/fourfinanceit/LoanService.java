package io.fourfinanceit;

import io.fourfinanceit.command.LoanApplyCommand;
import io.fourfinanceit.model.DateTimeRange;
import io.fourfinanceit.model.Loan;
import io.fourfinanceit.exceptions.LoanIsOverException;
import io.fourfinanceit.exceptions.MaxApplicationsReachedException;
import io.fourfinanceit.exceptions.RiskyHoursApplicationException;
import io.fourfinanceit.query.LoanDto;
import ma.glasnost.orika.MapperFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cons on 14/12/16.
 */
@Service
public class LoanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanService.class);

    @Value("${riskyHoursEnd}")
    private int riskyHoursEnd;

    @Value("${max.attempts.from.single.ip}")
    private int maxAttemptsFromSingleIp;

    @Value("${interest.factor}")
    private BigDecimal interestFactor;

    @Value("${max.amount}")
    private BigDecimal maxAmount;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    MapperFactory mapperFacade;

    public LoanDto applyToLoan(LoanApplyCommand applyCommand, String ipAddress) {
        BigDecimal amount = applyCommand.getAmount();
        String email = applyCommand.getCustomerEmail();
        Integer days = applyCommand.getDays();

        validateApplication(ipAddress, amount);

        Loan loan = new Loan(amount, new DateTimeRange(days), ipAddress, email);
        loan = loanRepository.save(loan);

        return mapperFacade.getMapperFacade().map(loan, LoanDto.class);
    }

    @Transactional
    public LoanDto extendLoan(Long id) {
        Loan loan = loanRepository.findOne(id);
        if (loan.isOver()) {
            throw new LoanIsOverException("Cannot extend loans that already ended");
        }
        loan.extend(interestFactor);
        loan = loanRepository.save(loan);

        return mapperFacade.getMapperFacade().map(loan, LoanDto.class);
    }

    public List<LoanDto> getLoans(String customerEmail) {
        List<Loan> loans = loanRepository.findByCustomerEmail(customerEmail);
        return mapperFacade.getMapperFacade().mapAsList(loans, LoanDto.class);
    }

    private void validateApplication(String ipAddress, BigDecimal amount) {
        List<Loan> todayFromThisIp = loanRepository.findByRangeStartBetweenAndIpAddress(getDayStart(), getDayEnd(), ipAddress);
        long count = todayFromThisIp.stream().count();

        if (count >= maxAttemptsFromSingleIp) {
            throw new MaxApplicationsReachedException("Max applications attempts reached from ip: " + ipAddress);
        }

        if (isAppliedInRiskyHours() && isWithMaximumAmount(amount)) {
            throw new RiskyHoursApplicationException("Application cannot be applied after " + DateTime.now().withTimeAtStartOfDay().toDate());
        }
    }

    private static Date getDayStart() {
        DateTime now = DateTime.now();
        return now.withTimeAtStartOfDay().toDate();
    }

    private static Date getDayEnd() {
        DateTime now = DateTime.now();
        return now.withTimeAtStartOfDay().plusDays(1).toDate();
    }

    private boolean isAppliedInRiskyHours() {
        return  DateTime.now().getHourOfDay() <= riskyHoursEnd;
    }

    private boolean isWithMaximumAmount(BigDecimal amount) {
        return maxAmount.compareTo(amount) <= 0;
    }
}
