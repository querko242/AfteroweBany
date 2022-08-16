package pl.aftermc.bans.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimeUtil {

    public static long getTimeWithString(String s) {
        Pattern pattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[, \\s]*)?(?:([0-9]+)\\s*mo[a-z]*[, \\s]*)?(?:([0-9]+)\\s*d[a-z]*[, \\s]*)?(?:([0-9]+)\\s*h[a-z]*[, \\s]*)?(?:([0-9]+)\\s*m[a-z]*[, \\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
        Matcher matcher = pattern.matcher(s);
        long czas = 0L;
        boolean found = false;
        while (matcher.find()) {
            if ((matcher.group() != null) && (!matcher.group().isEmpty())) {
                for (int i = 0; i < matcher.groupCount(); i++) {
                    if ((matcher.group(i) != null) && (!matcher.group(i).isEmpty())) {
                        found = true;
                        break;
                    }
                }
                if ((matcher.group(1) != null) && (!matcher.group(1).isEmpty())) {
                    czas += 31536000 * Integer.valueOf(matcher.group(1)).intValue();
                }
                if ((matcher.group(2) != null) && (!matcher.group(2).isEmpty())) {
                    czas += 2592000 * Integer.valueOf(matcher.group(2)).intValue();
                }
                if ((matcher.group(3) != null) && (!matcher.group(3).isEmpty())) {
                    czas += 86400 * Integer.valueOf(matcher.group(3)).intValue();
                }
                if ((matcher.group(4) != null) && (!matcher.group(4).isEmpty())) {
                    czas += 3600 * Integer.valueOf(matcher.group(4)).intValue();
                }
                if ((matcher.group(5) != null) && (!matcher.group(5).isEmpty())) {
                    czas += 60 * Integer.valueOf(matcher.group(5)).intValue();
                }
                if ((matcher.group(6) != null) && (!matcher.group(6).isEmpty())) {
                    czas += Integer.valueOf(matcher.group(6)).intValue();
                }
            }
        }
        if (!found) {
            return -1L;
        }
        return czas * 1000L;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

    public static String getDate(long czas) {
        return sdf.format(new Date(czas));
    }

}
