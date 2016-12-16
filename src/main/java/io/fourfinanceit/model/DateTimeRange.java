package io.fourfinanceit.model;

import javax.persistence.Embeddable;
import java.util.Date;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

/**
 * Created by cons on 14/12/16.
 */
@Embeddable
public class DateTimeRange {

    private Date start;

    private Date end;

    public DateTimeRange() {
    }

    public DateTimeRange(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public DateTimeRange(Date start, int days) {
        this.start = start;
        this.end = new DateTime(start).plusDays(days).withTimeAtStartOfDay().plusDays(1).toDate();
    }

    public DateTimeRange(int days) {
        this(new Date(), days);
    }

    public boolean isOver() {
        return new Date().after(end);
    }

    public Date getFinalDate(int weeksCount) {
        return new DateTime(end).plusWeeks(weeksCount).toDate();
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateTimeRange that = (DateTimeRange) o;
        return Objects.equal(start, that.start) &&
                Objects.equal(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(start, end);
    }
}
