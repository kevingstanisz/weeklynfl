package com.kev.weeklynfl.games;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.tomcat.jni.Local;
import org.hibernate.metamodel.internal.JpaStaticMetaModelPopulationSetting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;


public class WeekNumber {

    private long weekNumber;
    private boolean picksLocked;
    private int weekNumberFromFrontend;

    @JsonCreator
    public WeekNumber(int weekNumberFromFrontend) {
        System.out.println("top constructor running");
        this.weekNumberFromFrontend = weekNumberFromFrontend;
    }

    public WeekNumber() {
        System.out.println("bottom constructor running");
        Calendar firstWeekCalendar = Calendar.getInstance();
        firstWeekCalendar.set(2020, Calendar.SEPTEMBER, 8, 4,0, 0);
        LocalDate firstWeekDate = LocalDateTime.ofInstant(firstWeekCalendar.toInstant(), firstWeekCalendar.getTimeZone().toZoneId()).toLocalDate();

        this.weekNumber  = ChronoUnit.WEEKS.between(firstWeekDate, LocalDate.now());

        Calendar firstWeekGameCalendar = Calendar.getInstance();
        firstWeekGameCalendar.set(2020, Calendar.SEPTEMBER, 10, 19,0, 0);
        LocalDate firstWeekGameDate = LocalDateTime.ofInstant(firstWeekCalendar.toInstant(), firstWeekCalendar.getTimeZone().toZoneId()).toLocalDate();

        if(this.weekNumber != ChronoUnit.WEEKS.between(firstWeekDate, LocalDate.now())) {
            picksLocked = true;
        }
    }

    public int getWeekNumberFromFrontend() {
        return weekNumberFromFrontend;
    }

    public void setWeekNumberFromFrontend(int weekNumberFromFrontend) {
        this.weekNumberFromFrontend = weekNumberFromFrontend;
    }

    // remove week 0 - start at week 1
    public long getWeekNumber() {
        return weekNumber + 1;
    }

    public boolean isPicksLocked() {
        return picksLocked;
    }

    @Override
    public String toString() {
        return "WeekNumber{" +
                "weekNumber=" + weekNumber +
                ", picksLocked=" + picksLocked +
                ", weekNumberFromFrontend=" + weekNumberFromFrontend +
                '}';
    }

    public static void main(String[] args) {
        WeekNumber weekNumber = new WeekNumber();
    }
}
