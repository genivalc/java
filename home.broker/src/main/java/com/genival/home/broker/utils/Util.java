package com.genival.home.broker.utils;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Active;
import com.genival.home.broker.entities.Client;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class Util {
    public static Object doubleToString;
    static NumberFormat formatValue = new DecimalFormat("R$ #,##0.00");
    private static Client legacyClient = null;
    private static Active legacyActive = null;
    private static Account legacyAccount = null;
    private static LocalDateTime dateTime = LocalDateTime.now();

    public static void registerClient(Client client) {
        legacyClient = client;
    }

    public static Client getLoggedClient() {
        return legacyClient;
    }

    public static LocalDateTime incrementDays(long numberOfDays) {
        dateTime = dateTime.plusDays(numberOfDays);
        return dateTime;
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static LocalDateTime getCurrentDate() {
        return dateTime;
    }

    public static void registerAsset(Active asset) {
        legacyActive = asset;
    }

    public static Active getLoggedAsset() {
        return legacyActive;
    }

    public static Account getLoggedAccount() {
        return legacyAccount;
    }

    public static void registerAccount(Account account) {
        legacyAccount = account;
    }

    public String formatDate(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public String formatOnlyDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public LocalDate stringToDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String validateEmpty(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }

    public boolean validateDate(String date, int st) {
        if (date.isEmpty() && st != 0) {
            return true;
        }

        if (date.length() != 10) {
            return false;
        }

        int year = Integer.parseInt(date.substring(6, 10));
        int month = Integer.parseInt(date.substring(3, 5));
        int day = Integer.parseInt(date.substring(0, 2));
        if (year > 2000 && year < 2050) {
            int leapYearTest = year % 4;
            boolean isLeapYear;
            isLeapYear = leapYearTest == 0;
            if (month >= 1 && month <= 12) {
                if (month == 2 && isLeapYear) {
                    return day >= 1 && day <= 29;
                } else {
                    if (month == 2 && !isLeapYear) {
                        return day >= 1 && day <= 28;
                    } else {
                        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                            return day >= 1 && day <= 31;
                        } else {
                            if (month == 4 || month == 6 || month == 9 || month == 11) {
                                return day >= 1 && day <= 30;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String columnText(String text, int size) {
        if (text.length() > size) {
            return text.substring(0, size);
        } else if (text.length() < size) {
            return String.format("%-" + size + "s", text);
        } else {
            return text;
        }
    }

    public String largeText(String text, int size) {
        if (text.length() <= size) {
            return String.format("%-" + size + "s", text);
        } else {
            StringBuilder formattedText = new StringBuilder();
            int breakLength = 0;
            for (int i = 0; i < text.length(); i++) {
                if (breakLength == size) {
                    formattedText.append(" \n ");
                    breakLength = 0;
                }
                formattedText.append(text.charAt(i));
                breakLength++;
            }
            return formattedText.toString();
        }
    }


    public String formatValue(BigDecimal value) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(value);
    }
}


