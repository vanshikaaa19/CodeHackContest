package com.crio.codehackcontest.service;

import com.crio.codehackcontest.entity.User;
import com.crio.codehackcontest.exchange.UpdateUserRequest;
import com.crio.codehackcontest.exchange.UserRequest;

import java.util.List;

/**
 * The UserService interface defines methods for managing user-related operations.
 */
public interface UserService {

    /**
     * Creates a new user based on the provided user request.
     *
     * @param user {@link com.crio.codehackcontest.exchange.UserRequest} the user request containing user details
     * @return the created User {@link com.crio.codehackcontest.entity.User} object
     */
    User createUser(UserRequest user);

    /**
     * Updates an existing user identified by the given ID with the provided update request.
     *
     * @param id   the ID of the user to update
     * @param user {@link com.crio.codehackcontest.exchange.UserRequest} the update request containing new user details
     * @return the updated User {@link com.crio.codehackcontest.entity.User} object
     */
    User updateUser(String id, UpdateUserRequest user);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the found User {@link com.crio.codehackcontest.entity.User} object
     */
    User getUserById(String id);

    /**
     * Retrieves all users.
     *
     * @return a list of all User List<{@link com.crio.codehackcontest.entity.User}> objects
     */
    List<User> getUsers();

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    void delete(String id);
}
