package pl.aftermc.bans.util;

public final class StringUtil {

    public static String stringBuilder(String[] args, int liczOdArgumentu) {
        String msg = "";
        for (int i = liczOdArgumentu; i < args.length; i++) {
            msg = msg + args[i];
            if (i <= args.length - 2) {
                msg = msg + " ";
            }
        }
        return msg;
    }

}
