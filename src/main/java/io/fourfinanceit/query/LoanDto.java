package io.fourfinanceit.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


/**
 * Created by cons on 14/12/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanDto {

    private Long id;

    private BigDecimal initialAmount;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date start;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date end;

    private String customer;

    private BigDecimal finalAmount;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date finalDate;

    private List<LoanExtensionDto> extensions = newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public List<LoanExtensionDto> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<LoanExtensionDto> extensions) {
        this.extensions = extensions;
    }
}
