package com.crio.codehackcontest.utils;

import com.crio.codehackcontest.entity.User;

import java.util.Comparator;

public class UserScoreComparator implements Comparator<User> {
    private final boolean ascending;

    // Default constructor sorts in ascending order
    public UserScoreComparator() {
        this.ascending = false;
    }

    // Constructor to specify sort order
    public UserScoreComparator(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(User u1, User u2) {
        if (ascending) {
            return Integer.compare(u1.getScore(), u2.getScore());
        } else {
            return Integer.compare(u2.getScore(), u1.getScore());
        }
    }
}
