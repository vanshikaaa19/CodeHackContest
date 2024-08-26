package com.crio.codehackcontest.service.implementation;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.entity.User;
import com.crio.codehackcontest.exchange.ContestRequest;
import com.crio.codehackcontest.exchange.UpdateUserRequest;
import com.crio.codehackcontest.exchange.UserRequest;
import com.crio.codehackcontest.model.Badges;
import com.crio.codehackcontest.repository.UserRepository;
import com.crio.codehackcontest.service.ContestService;
import com.crio.codehackcontest.service.UserService;
import com.crio.codehackcontest.utils.DetermineBadges;
import com.crio.codehackcontest.utils.GlobalDataConstants;
import com.crio.codehackcontest.utils.UserScoreComparator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ContestService contestService;

    public UserServiceImpl(UserRepository userRepository, ContestService contestService) {
        this.userRepository = userRepository;
        this.contestService = contestService;
    }

    /**
     * Creates a new user.
     *
     * @param user the UserRequest {@link com.crio.codehackcontest.exchange.UserRequest} containing user details
     * @return the created User {@link com.crio.codehackcontest.entity.User} object
     */
    @Override
    public User createUser(UserRequest user) {
        // Check if user ID already exists
        if (userRepository.findById(user.getUserid()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User ID already exists");
        }

        // Create a new user with initial score of 0 and an empty set of badges
        User savedUser = userRepository.save(new User(user.getUserid(), user.getUsername(), 0, new HashSet<>()));

        // Add the user to the contest if the contest supports single contest
        Contest contest = contestService.addUserToContest(GlobalDataConstants.contestId, new ContestRequest(List.of(savedUser.getUserid())));
        if (contest.getParticipantsId().contains(savedUser.getUserid())) {
            return savedUser;
        } else {
            throw new ResponseStatusException(HttpStatus.MULTI_STATUS, "User created but failed to participate in contest");
        }
    }

    /**
     * Updates an existing user's score and determines badges based on the new score.
     *
     * @param id   the ID of the user to update
     * @param user the UpdateUserRequest {@link com.crio.codehackcontest.exchange.UserRequest} containing updated user details
     * @return the updated User {@link com.crio.codehackcontest.entity.User} object
     */
    @Override
    public User updateUser(String id, UpdateUserRequest user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setScore(user.getScore());
            HashSet<Badges> badges = DetermineBadges.determineBadges(existingUser.getScore());
            existingUser.setBadges(badges);
            return userRepository.save(existingUser);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the found User {@link com.crio.codehackcontest.entity.User} object
     */
    @Override
    public User getUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    /**
     * Retrieves all users sorted by their scores.
     *
     * @return a list of all User {@link com.crio.codehackcontest.entity.User} objects sorted by score
     */
    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        users.sort(new UserScoreComparator());
        return users;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void delete(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            // Remove user from contest before deleting
            contestService.removeUserFromContest(GlobalDataConstants.contestId, new ContestRequest(List.of(optionalUser.get().getUserid())));
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

}
