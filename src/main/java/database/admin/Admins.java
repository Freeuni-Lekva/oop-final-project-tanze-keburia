package database.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Admins {
    private static HashSet<String> admins = new HashSet<>();
    public static void addAdmin(String username) {
        admins.add(username);
    }
    public static void removeAdmin(String username) {
        admins.remove(username);
    }

    public static boolean isAdmin(String username) {
        return admins.contains(username);
    }

    public static List<String> getAdmins() {
        return  new ArrayList<String>(admins);
    }
}
