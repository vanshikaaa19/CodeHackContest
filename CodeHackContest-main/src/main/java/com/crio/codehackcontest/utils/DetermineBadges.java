package com.crio.codehackcontest.utils;

import com.crio.codehackcontest.model.Badges;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Component
public class DetermineBadges {
    // Define sets of badges based on score ranges
    private static final HashSet<Badges> scoreBelow30 = new HashSet<>();
    private static final HashSet<Badges> scoreBelow60 = new HashSet<>();
    private static final HashSet<Badges> scoreUpto100 = new HashSet<>();

    // Initialize badge sets in the constructor
    public DetermineBadges() {
        scoreBelow30.add(Badges.CODENINJA);
        scoreBelow60.addAll(List.of(Badges.CODENINJA, Badges.CODECHAMP));
        scoreUpto100.addAll(List.of(Badges.CODENINJA, Badges.CODECHAMP, Badges.CODEMASTER));
    }

    /**
     * Determines the badges based on the user's score.
     *
     * @param score the user's score
     * @return a set of badges earned based on the score
     */
    public static HashSet<Badges> determineBadges(int score) {
        // Validate score range
        if (score < 0 || score > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User score must be between 0 and 100");
        }

        // Determine badges based on score ranges
        if (score < 30) {
            return scoreBelow30;
        } else if (score < 60) {
            return scoreBelow60;
        } else {
            return scoreUpto100;
        }
    }
}
