package com.kev.weeklynfl.games;

import java.util.List;

public class LeagueInfo {
    private List<Integer> weeks;
    private List<String> users;

    public LeagueInfo(List<Integer> weeks, List<String> users) {
        this.weeks = weeks;
        this.users = users;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
