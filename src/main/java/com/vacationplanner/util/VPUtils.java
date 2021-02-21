package com.vacationplanner.util;

import com.vacationplanner.entity.VacationStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VPUtils {

    public static String getLoggedInUsername() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static Date getDateFromString(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return dateFormat.parse(date);
    }

    public static VacationStatus getVacationStatusFromString(String status) {
        if (status.equalsIgnoreCase("pending"))
            return VacationStatus.PENDING;
        else if (status.equalsIgnoreCase("approved"))
            return VacationStatus.APPROVED;
        else if (status.equalsIgnoreCase("rejected"))
            return VacationStatus.REJECTED;

        return VacationStatus.UNKNOWN;
    }

    public static SimpleMailMessage getMailMessage(String[] to, String subject, String content) {
        SimpleMailMessage retVal = new SimpleMailMessage();

        retVal.setTo(to);
        retVal.setSubject(subject);
        retVal.setText(content);

        return retVal;
    }

    public static Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
