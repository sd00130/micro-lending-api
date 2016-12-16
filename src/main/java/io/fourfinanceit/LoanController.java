package io.fourfinanceit;

import io.fourfinanceit.command.LoanApplyCommand;
import io.fourfinanceit.query.LoanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by cons on 14/12/16.
 */
@Validated
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public LoanDto apply(@RequestBody @Valid LoanApplyCommand loanApplyCommand, HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        return loanService.applyToLoan(loanApplyCommand, ipAddress);
    }

    @RequestMapping(value = "/{loanId}/extend", method = RequestMethod.GET)
    public LoanDto extend(@PathVariable("loanId") String loanId) {
        Long id = Long.valueOf(loanId);
        return loanService.extendLoan(id);
    }
 }
