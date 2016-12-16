package io.fourfinanceit;

import io.fourfinanceit.query.LoanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cons on 15/12/16.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    LoanService loanService;

    @RequestMapping(value = "/{customerEmail}/loans", method = RequestMethod.GET)
    public List<LoanDto> getLoans(@PathVariable("customerEmail") String customerEmail) {
        return loanService.getLoans(customerEmail);
    }
}
