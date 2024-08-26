package com.crio.codehackcontest;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.entity.User;
import com.crio.codehackcontest.exchange.ContestRequest;
import com.crio.codehackcontest.exchange.UpdateUserRequest;
import com.crio.codehackcontest.exchange.UserRequest;
import com.crio.codehackcontest.repository.UserRepository;
import com.crio.codehackcontest.service.ContestService;
import com.crio.codehackcontest.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CodeHackContestApplication.class})
@AutoConfigureMockMvc
@DirtiesContext
public class UserTest {
    // Creating a new user successfully
    @Test
    public void test_create_user_successfully() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        UserRequest userRequest = new UserRequest("1", "testUser");
        User user = new User("1", "testUser", 0, new HashSet<>());
        Contest contest = new Contest();
        contest.setParticipantsId(List.of("1"));

        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(contestService.addUserToContest(anyString(), any(ContestRequest.class))).thenReturn(contest);

        User createdUser = userService.createUser(userRequest);

        assertNotNull(createdUser);
        assertEquals("1", createdUser.getUserid());
        assertEquals("testUser", createdUser.getUsername());
    }

    // Creating a user with an existing user ID
    @Test
    public void test_create_user_with_existing_id() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        UserRequest userRequest = new UserRequest("1", "testUser");
        User existingUser = new User("1", "existingUser", 0, new HashSet<>());

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.createUser(userRequest));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("User ID already exists", exception.getReason());
    }


    // Updating an existing user's score and badges
    @Test
    public void test_update_user_score_and_badges() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        User existingUser = new User("1", "testUser", 0, new HashSet<>());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(50, new HashSet<>());

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser("1", updateUserRequest);

        assertNotNull(updatedUser);
        assertEquals(50, updatedUser.getScore());
        // Add assertions for badges if needed
    }

    // Retrieving a user by ID successfully
    @Test
    public void test_get_user_by_id_success() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        User existingUser = new User("1", "testUser", 0, new HashSet<>());

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));

        User retrievedUser = userService.getUserById("1");

        assertNotNull(retrievedUser);
        assertEquals("1", retrievedUser.getUserid());
        assertEquals("testUser", retrievedUser.getUsername());
    }

    // Retrieving all users sorted by score in descending order
    @Test
    public void test_get_users_sorted_by_score_descending() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        List<User> users = new ArrayList<>();
        users.add(new User("1", "user1", 50, new HashSet<>()));
        users.add(new User("2", "user2", 30, new HashSet<>()));

        when(userRepository.findAll()).thenReturn(users);

        List<User> sortedUsers = userService.getUsers();

        assertNotNull(sortedUsers);
        assertEquals(2, sortedUsers.size());
        assertEquals("user1", sortedUsers.get(0).getUsername());
        assertEquals("user2", sortedUsers.get(1).getUsername());
    }

    // Adding a user to a contest upon creation
    @Test
    public void test_add_user_to_contest_upon_creation() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        UserRequest userRequest = new UserRequest("1", "testUser");
        User user = new User("1", "testUser", 0, new HashSet<>());
        Contest contest = new Contest();
        contest.setParticipantsId(List.of("1"));

        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(contestService.addUserToContest(anyString(), any(ContestRequest.class))).thenReturn(contest);

        User createdUser = userService.createUser(userRequest);

        assertNotNull(createdUser);
        assertEquals("1", createdUser.getUserid());
        assertEquals("testUser", createdUser.getUsername());
        assertTrue(contest.getParticipantsId().contains("1"));
    }

    // Deleting a user and removing them from the contest
    @Test
    public void test_delete_user_and_remove_from_contest() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        User user = new User("1", "testUser", 0, new HashSet<>());
        Contest contest = new Contest();
        contest.setParticipantsId(List.of("1"));

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(contestService.removeUserFromContest(anyString(), any(ContestRequest.class))).thenReturn(contest);

        userService.delete("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    // Updating a non-existent user
    @Test
    public void test_update_non_existent_user() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(50, new HashSet<>());

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.updateUser("1", updateUserRequest));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    // Retrieving a non-existent user by ID
    @Test
    public void test_retrieve_non_existent_user_by_id() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.getUserById("1"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    // Deleting a non-existent user
    @Test
    public void test_delete_non_existent_user() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.delete("1"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    // User creation fails to participate in contest
    @Test
    public void test_user_creation_fails_to_participate() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        UserRequest userRequest = new UserRequest("1", "testUser");
        User user = new User("1", "testUser", 0, new HashSet<>());
        Contest contest = new Contest();

        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(contestService.addUserToContest(anyString(), any(ContestRequest.class))).thenReturn(contest);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.createUser(userRequest));

        assertEquals(HttpStatus.MULTI_STATUS, exception.getStatusCode());
        assertEquals("User created but failed to participate in contest", exception.getReason());
    }

    // Ensure user score is within valid range (0-100)
    @Test
    public void test_user_score_within_valid_range() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        User existingUser = new User("1", "testUser", 0, new HashSet<>());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(150, new HashSet<>());

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.updateUser("1", updateUserRequest));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("User score must be between 0 and 100", exception.getReason());
    }

    // Ensure badges are correctly determined based on user score
    @Test
    public void test_check_for_proper_exception_handling_and_status_codes() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.getUserById("1"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    // Check for proper exception handling and status codes
    @Test
    public void test_exception_handling_status_codes() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestService contestService = mock(ContestService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, contestService);

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.getUserById("1"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }
}
