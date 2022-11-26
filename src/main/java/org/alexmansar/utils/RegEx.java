package org.alexmansar.utils;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Slf4j
public class RegEx {
    static String REGEX_NAME_UKR = "^[а-щА-ЩЬьЮюЯяЇїІіЄєҐ\\s?]{4,20}";
    static String REGEX_NAME_ENG = "^[\\w\\s?]{4,20}";
    static String REGEX_PHONE = "^(\\+380)\\d{9}";
    static String REGEX_MAIL = "[\\w+([\\.-]?\\w+)]{5,}@\\w{2,}\\.\\w{2,4}";
    static String REGEX_ADDRESS = "^\\w{2,20}";
    static String REGEX_SALARY = "^\\d{2,20}";
    static String REGEX_DATE = "\\d{4}-\\d{2}-\\d{2}";
    static String REGEX_DATE_UPDATE = "\\d{4}-\\d{2}-\\d{2}T00:00";
    static Font FONT = new Font("Dialog", Font.ITALIC, 9);

    public static boolean checkName(JTextField field, JLabel label) {
        if (!((pattern(field.getText(), REGEX_NAME_UKR)) || (pattern(field.getText(), REGEX_NAME_ENG)))) {
            return createCheckLabel(field, label, "not correct email {}", "Use only ukrainian or english letters, and be more than 3 letters");
        } else {
            return changeLabelVisible(label);
        }
    }

    public static boolean checkName(JTable table, int col, int row) {
        return (pattern(table.getValueAt(row, col).toString(), REGEX_NAME_UKR)) || (pattern(table.getValueAt(row, col).toString(), REGEX_NAME_ENG));
    }

    public static boolean checkPhone(JTextField field, JLabel label) {
        if (!pattern(field.getText(), REGEX_PHONE)) {
            return createCheckLabel(field, label, "not correct number {}", "Use next format : +380xxxxxxxxx");
        } else {
            return changeLabelVisible(label);
        }
    }

    public static boolean checkPhone(JTable table, int col, int row) {
        return pattern(table.getValueAt(row, col).toString(), REGEX_PHONE);
    }

    public static boolean checkEmail(JTextField field, JLabel label) {
        if (!pattern(field.getText(), REGEX_MAIL)) {
            return createCheckLabel(field, label, "not correct email {}", "Not valid email");
        } else {
            return changeLabelVisible(label);
        }
    }

    public static boolean checkEmail(JTable table, int col, int row) {
        return pattern(table.getValueAt(row, col).toString(), REGEX_MAIL);
    }

    public static boolean checkAddress(JTextField field, JLabel label) {
        if (!pattern(field.getText(), REGEX_ADDRESS)) {
            return createCheckLabel(field, label, "not correct name {}", "Characteristic might be 2 to 20 letters");
        } else {
            return changeLabelVisible(label);
        }
    }

    public static boolean checkAddress(JTable table, int col, int row) {
        return pattern(table.getValueAt(row, col).toString(), REGEX_ADDRESS);
    }

    public static boolean checkSalary(JTextField field, JLabel label) {
        if (!pattern(field.getText(), REGEX_SALARY)) {
            return createCheckLabel(field, label, "not correct salary {}", "Characteristic might be 2 to 20 letters");
        } else {
            return changeLabelVisible(label);
        }
    }

    public static boolean checkSalary(JTable table, int col, int row) {
        return pattern(table.getValueAt(row, col).toString(), REGEX_SALARY);
    }

    public static boolean checkBirthday(JTable table, int col, int row) {
        return (pattern(table.getValueAt(row, col).toString(), REGEX_DATE_UPDATE));
    }

    public static boolean checkBirthday(JTextField field, JLabel label) {
        int min = 18;
        int max = 65;
        if (!(pattern(field.getText(), REGEX_DATE))) {
            return createCheckLabel(field, label, "not correct date", "use next format yyyy-mm-dd");
        }
        LocalDateTime userDate = getLocalDateTime(field);
        LocalDateTime minAge = LocalDateTime.now().minusYears(min);
        LocalDateTime maxAge = LocalDateTime.now().minusYears(max);
        if (userDate.isAfter(LocalDateTime.now())) {
            return createCheckLabel(field, label, "not correct date", "from future");
        } else if (userDate.isAfter(minAge)) {
            return createCheckLabel(field, label, "not correct date", "too yong");
        } else if (userDate.isBefore(maxAge)) {
            return createCheckLabel(field, label, "not correct date", "too old");
        }
        return changeLabelVisible(label);
    }

    public static boolean checkHiringDate(JTable table, int col, int row) {
        return (pattern(table.getValueAt(row, col).toString(), REGEX_DATE_UPDATE));
    }

    public static boolean checkHiringDate(JTextField field, JLabel label, JTextField birthday) {
        if (!(pattern(field.getText(), REGEX_DATE))) {
            return createCheckLabel(field, label, "not correct date", "use next format yyyy-mm-dd");
        }
        LocalDateTime userDate = getLocalDateTime(field);
        LocalDateTime birthdayDate = getLocalDateTime(birthday);
        LocalDateTime minDateToHiring = birthdayDate.plusYears(18);
        if (userDate.isAfter(LocalDateTime.now())) {
            return createCheckLabel(field, label, "not correct date", "from future");
        } else if (userDate.isBefore(birthdayDate)) {
            return createCheckLabel(field, label, "hiring date cant be before than birthday", "hiring date cant be before than birthday");
        } else if (userDate.isBefore(minDateToHiring)) {
            return createCheckLabel(field, label, "hiring date cant be before than birthday", "<html>hiring date can't be less then <br>" + minDateToHiring + "</html>");
        }
        return changeLabelVisible(label);
    }

    public static LocalDateTime getLocalDateTime(JTextField field) {
        String s = field.getText();
        int year = Integer.parseInt(s.substring(0, 4));
        int month = Integer.parseInt(s.substring(5, 7));
        int day = Integer.parseInt(s.substring(8, 10));
        return LocalDateTime.of(year, month, day, 0, 0, 0, 0);
    }

    private static boolean changeLabelVisible(JLabel label) {
        label.setVisible(false);
        return true;
    }

    private static boolean createCheckLabel(JTextField field, JLabel label, String logMessage, String labelText) {
        label.setForeground(Color.RED);
        label.setFont(FONT);
        label.setVisible(true);
        log.info(logMessage, field.getText());
        label.setText(labelText);
        return false;
    }

    private static boolean pattern(String message, String regEx) {
        try {
            return Pattern.compile(regEx).matcher(message).matches();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
