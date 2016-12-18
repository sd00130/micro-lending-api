package io.fourfinanceit;

import io.fourfinanceit.exceptions.MaxApplicationsReachedException;
import io.fourfinanceit.exceptions.RiskyHoursApplicationException;
import io.fourfinanceit.model.Loan;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cons on 18/12/16.
 */
@Service
public class LoanValidationService {

    @Value("${riskyHoursEnd}")
    private int riskyHoursEnd;

    @Value("${max.attempts.from.single.ip}")
    private int maxAttemptsFromSingleIp;

    @Value("${max.amount}")
    private BigDecimal maxAmount;

    @Autowired
    LoanRepository loanRepository;

    public void validate(String ipAddress, BigDecimal amount) {
        List<Loan> todayFromThisIp = loanRepository.findByTermStartBetweenAndIpAddress(getDayStart(), getDayEnd(), ipAddress);
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
