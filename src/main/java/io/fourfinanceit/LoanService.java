package io.fourfinanceit;

import io.fourfinanceit.command.LoanApplyCommand;
import io.fourfinanceit.model.Term;
import io.fourfinanceit.model.Loan;
import io.fourfinanceit.exceptions.LoanIsOverException;
import io.fourfinanceit.query.LoanDto;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cons on 14/12/16.
 */
@Service
public class LoanService {

    @Value("${interest.factor}")
    private BigDecimal interestFactor;

    @Autowired
    LoanValidationService validationService;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    MapperFactory mapperFacade;

    public LoanDto applyToLoan(LoanApplyCommand applyCommand, String ipAddress) {
        BigDecimal amount = applyCommand.getAmount();
        String email = applyCommand.getCustomerEmail();
        Integer days = applyCommand.getDays();

        validationService.validate(ipAddress, amount);

        Loan loan = new Loan(amount, new Term(days), ipAddress, email);
        loan = loanRepository.save(loan);
        loan.calculateExtensionFees();

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
        loan.calculateExtensionFees();

        return mapperFacade.getMapperFacade().map(loan, LoanDto.class);
    }

    public List<LoanDto> getLoans(String customerEmail) {
        List<Loan> loans = loanRepository.findByCustomerEmail(customerEmail);
        loans.stream().forEach(l -> l.calculateExtensionFees());
        return mapperFacade.getMapperFacade().mapAsList(loans, LoanDto.class);
    }
}
