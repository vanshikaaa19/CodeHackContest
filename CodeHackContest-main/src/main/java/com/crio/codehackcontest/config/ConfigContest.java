package com.crio.codehackcontest.config;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.repository.ContestRepository;
import com.crio.codehackcontest.utils.GlobalDataConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The ConfigContest class initializes the contest configuration on application startup.
 *
 * <p>This component ensures that the application is configured with the necessary contest data
 * based on the configuration constants provided in {@link com.crio.codehackcontest.utils.GlobalDataConstants}.</p>
 */
@Component
public class ConfigContest {
    private final ContestRepository contestRepository;

    /**
     * Constructs a ConfigContest with the given ContestRepository.
     *
     * @param contestRepository the repository for contest data access
     */
    @Autowired
    public ConfigContest(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    /**
     * Initializes the contest configuration on application startup.
     *
     * <p>This method checks if a contest with the specified name exists in the database.
     * If not, it creates a new contest. If more than one contest exists, it deletes all contests
     * except the specified single contest. If exactly one contest exists, it retrieves its ID
     * and stores it in {@link com.crio.codehackcontest.utils.GlobalDataConstants#contestId}.</p>
     */
    @PostConstruct
    public void initializeContest() {
        if (GlobalDataConstants.singleContestSupport) {
            Optional<Contest> contest = Optional.empty();
            Contest createContestData = new Contest(GlobalDataConstants.contestName, new ArrayList<>());
            long count = contestRepository.count();
            if (count == 0) {
                contest = Optional.of(contestRepository.save(createContestData));
            } else if (count > 1) {
                contestRepository.deleteAllExceptSingleContest(GlobalDataConstants.contestName);
            } else {
                contest = contestRepository.findContestByName(GlobalDataConstants.contestName);
            }
            contest.ifPresent(value -> GlobalDataConstants.contestId = value.getId());
        }
    }
}
