package io.fourfinanceit.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by cons on 15/12/16.
 */
public class LoanExtensionDto {

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date whenExtended;

    public LoanExtensionDto() {

    }

    public Date getWhenExtended() {
        return whenExtended;
    }

    public void setWhenExtended(Date whenExtended) {
        this.whenExtended = whenExtended;
    }
}
