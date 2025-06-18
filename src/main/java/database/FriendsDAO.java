package database;

import java.util.*;

public class FriendsDAO {

    private Map<String, List<String>> friends = new HashMap<>();

    public void addFriends(String a, String b) {
        if(!friends.containsKey(a)) {
            friends.put(a, new ArrayList<>());
        }

        if(!friends.containsKey(b)) {
            friends.put(b, new ArrayList<>());
        }

        friends.get(a).add(b);
        friends.get(b).add(a);
    }

    public void removeFriends(String a, String b) {
        if(friends.containsKey(a)) {
            friends.get(a).remove(b);
        }
        if(friends.containsKey(b)) {
            friends.get(b).remove(a);
        }
    }

    public List<String> getFriends(String a) {
        if(!friends.containsKey(a)) return new ArrayList<>();

        return friends.get(a);
    }
}
