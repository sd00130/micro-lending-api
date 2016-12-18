package io.fourfinanceit.model;

import javax.persistence.Embeddable;
import java.util.Date;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

/**
 * Created by cons on 14/12/16.
 */
@Embeddable
public class Term {

    private Date start;

    private int days;

    public Term() {
    }

    public Term(Date start, int days) {
        this.start = start;
        this.days = days;
    }

    public Term(int days) {
        this(new Date(), days);
    }

    public Date extendByWeeks(int weeksCount) {
        return new DateTime(getEnd())
                .plusWeeks(weeksCount)
                .toDate();
    }

    public Date getEnd() {
        return new DateTime(start).
                plusDays(days)
                .toDate();
    }

    public Date getStart() {
        return start;
    }

    public int getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term that = (Term) o;
        return Objects.equal(start, that.start) &&
                Objects.equal(days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(start, days);
    }
}
