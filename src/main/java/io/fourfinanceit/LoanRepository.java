package io.fourfinanceit;

import io.fourfinanceit.model.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by cons on 14/12/16.
 */
public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByCustomerEmail(String email);

    List<Loan> findByRangeStartBetweenAndIpAddress(Date startOfTheDay, Date endOfTheDay, String ipAddress);

}
