package com.crio.codehackcontest.service.implementation;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.entity.User;
import com.crio.codehackcontest.exchange.ContestRequest;
import com.crio.codehackcontest.model.LeaderBoard;
import com.crio.codehackcontest.repository.ContestRepository;
import com.crio.codehackcontest.repository.UserRepository;
import com.crio.codehackcontest.service.ContestService;
import com.crio.codehackcontest.utils.UserScoreComparator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContestServiceImpl implements ContestService {
    private final UserRepository userRepository;
    private final ContestRepository contestRepository;

    public ContestServiceImpl(UserRepository userRepository, ContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }

    /**
     * Creates a new contest with specified participants.
     *
     * @param contestRequest the ContestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} containing contest details
     * @return the created Contest {@link com.crio.codehackcontest.entity.Contest} object
     */
    @Override
    public Contest createContest(ContestRequest contestRequest) {
        List<String> participantsId = findAllValidExistingUser(contestRequest);
        Optional<Contest> contest = contestRepository.findContestByName(contestRequest.getName());
        if (contest.isEmpty()) {
            return contestRepository.save(new Contest(contestRequest.getName(), participantsId));
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Contest name already exists");
        }
    }

    /**
     * Adds users to a contest by contest ID.
     *
     * @param id             the ID of the contest to modify
     * @param contestRequest the ContestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} containing user IDs to add
     * @return the updated Contest {@link com.crio.codehackcontest.entity.Contest} object
     */
    @Override
    public Contest addUserToContest(String id, ContestRequest contestRequest) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isPresent()) {
            Contest contest = optionalContest.get();
            List<String> participantsId = findAllValidExistingUser(contestRequest);
            List<String> userNotExistsInContest = findAllUsersExistInContest(false, contest.getParticipantsId(), participantsId);
            contest.setParticipantsId(userNotExistsInContest);
            return contestRepository.save(contest);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found");
        }
    }

    /**
     * Removes users from a contest by contest ID.
     *
     * @param id             the ID of the contest to modify
     * @param contestRequest the ContestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} containing user IDs to remove
     * @return the updated Contest {@link com.crio.codehackcontest.entity.Contest} object
     */
    @Override
    public Contest removeUserFromContest(String id, ContestRequest contestRequest) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isPresent()) {
            Contest contest = optionalContest.get();
            List<String> participantsId = findAllValidExistingUser(contestRequest);
            List<String> userNotExistsInContest = findAllUsersExistInContest(true, contest.getParticipantsId(), participantsId);
            contest.removeParticipantsId(userNotExistsInContest);
            return contestRepository.save(contest);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found");
        }
    }

    /**
     * Retrieves a contest by its ID.
     *
     * @param id the ID of the contest to retrieve
     * @return the found Contest {@link com.crio.codehackcontest.entity.Contest} object
     */
    @Override
    public Contest getContestById(String id) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isPresent()) {
            return optionalContest.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found");
        }
    }

    /**
     * Retrieves all contests.
     *
     * @return a list of all List<{@link com.crio.codehackcontest.entity.Contest}> objects
     */
    @Override
    public List<Contest> getContests() {
        return contestRepository.findAll();
    }

    /**
     * Deletes a contest by its ID.
     *
     * @param id the ID of the contest to delete
     */
    @Override
    public void deleteContest(String id) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isPresent()) {
            contestRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found");
        }
    }

    /**
     * Retrieves the leaderboard for a contest by contest ID.
     *
     * @param id the ID of the contest
     * @return the LeaderBoard {@link com.crio.codehackcontest.model.LeaderBoard} object representing the contest leaderboard
     */
    @Override
    public LeaderBoard checkLeaderBoard(String id) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isPresent()) {
            List<User> users = userRepository.findAllById(optionalContest.get().getParticipantsId());
            users.sort(new UserScoreComparator());
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.setId("lb_for_contest_id_" + optionalContest.get().getId());
            leaderBoard.setName("LeaderBoard: " + optionalContest.get().getName());
            leaderBoard.setUser(users);
            return leaderBoard;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found");
        }
    }

    /**
     * Helper method to find valid existing users from contest request.
     *
     * @param contestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} the ContestRequest containing user IDs
     * @return a list of valid user IDs
     */
    private List<String> findAllValidExistingUser(ContestRequest contestRequest) {
        List<String> participantsId = new ArrayList<>();
        for (String id : contestRequest.getParticipants()) {
            if (userRepository.findById(id).isPresent()) {
                participantsId.add(id);
            }
        }
        return participantsId;
    }

    /**
     * Helper method to find users existing or not existing in the contest.
     *
     * @param wantToDelete   true if users to be deleted else false
     * @param existingUsers  existing users in the contest
     * @param participantsId all the participants user ids
     * @return list of users present
     */
    private List<String> findAllUsersExistInContest(boolean wantToDelete, List<String> existingUsers, List<String> participantsId) {
        if (wantToDelete) {
            participantsId.retainAll(existingUsers);
            return existingUsers;
        } else {
            participantsId.removeAll(existingUsers);
            return participantsId;
        }
    }

}
