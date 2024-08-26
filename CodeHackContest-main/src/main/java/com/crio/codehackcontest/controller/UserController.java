package com.crio.codehackcontest.controller;

import com.crio.codehackcontest.entity.User;
import com.crio.codehackcontest.exchange.GenericResponse;
import com.crio.codehackcontest.exchange.UpdateUserRequest;
import com.crio.codehackcontest.exchange.UserRequest;
import com.crio.codehackcontest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The UserController class handles HTTP requests for managing users.
 *
 * <p>This controller provides endpoints for creating, retrieving, updating, and deleting users.</p>
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructs a UserController with the given UserService.
     *
     * @param userService the service to manage user operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ----------------------------- USER -----------------------------

    /**
     * Creates a new user.
     *
     * @param user {@link com.crio.codehackcontest.exchange.UserRequest} the request containing user details
     * @return a ResponseEntity containing the created user
     */
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest user) {
        User savedUser = userService.createUser(user);
        GenericResponse<User> data = new GenericResponse<>(savedUser);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        GenericResponse<User> data = new GenericResponse<>(user);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Retrieves all users.
     *
     * @return a ResponseEntity containing a list of all users
     */
    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getUsers();
        GenericResponse<List<User>> data = new GenericResponse<>(users);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Updates a user by its ID.
     *
     * @param id the ID of the user to update
     * @param user {@link com.crio.codehackcontest.exchange.UpdateUserRequest} the request containing updated user details
     * @return a ResponseEntity containing the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest user) {
        User savedUser = userService.updateUser(id, user);
        GenericResponse<User> data = new GenericResponse<>(savedUser);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity containing a confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.delete(id);
        GenericResponse<String> data = new GenericResponse<>("User Deleted Successfully");
        return ResponseEntity.ok().body(data);
    }

}
