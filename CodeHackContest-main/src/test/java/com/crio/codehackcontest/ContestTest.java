package com.crio.codehackcontest;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.entity.User;
import com.crio.codehackcontest.exchange.ContestRequest;
import com.crio.codehackcontest.repository.ContestRepository;
import com.crio.codehackcontest.repository.UserRepository;
import com.crio.codehackcontest.service.implementation.ContestServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CodeHackContestApplication.class})
@AutoConfigureMockMvc
@DirtiesContext
class ContestTest {

    @Test
    public void test_create_contest_with_valid_data() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestRepository contestRepository = mock(ContestRepository.class);
        ContestServiceImpl contestService = new ContestServiceImpl(userRepository, contestRepository);

        List<String> participants = List.of("user1", "user2");
        ContestRequest contestRequest = new ContestRequest("New Contest", participants);

        when(userRepository.findById("user1")).thenReturn(Optional.of(new User()));
        when(userRepository.findById("user2")).thenReturn(Optional.of(new User()));
        when(contestRepository.findContestByName("New Contest")).thenReturn(Optional.empty());
        when(contestRepository.save(any(Contest.class))).thenReturn(new Contest("New Contest", participants));

        Contest createdContest = contestService.createContest(contestRequest);

        assertNotNull(createdContest);
        assertEquals("New Contest", createdContest.getName());
        assertEquals(participants, createdContest.getParticipantsId());
    }

    // create a contest with an existing name
    @Test
    public void test_create_contest_with_existing_name() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestRepository contestRepository = mock(ContestRepository.class);
        ContestServiceImpl contestService = new ContestServiceImpl(userRepository, contestRepository);

        List<String> participants = List.of("user1", "user2");
        ContestRequest contestRequest = new ContestRequest("Existing Contest", participants);

        when(userRepository.findById("user1")).thenReturn(Optional.of(new User()));
        when(userRepository.findById("user2")).thenReturn(Optional.of(new User()));
        when(contestRepository.findContestByName("Existing Contest")).thenReturn(Optional.of(new Contest()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> contestService.createContest(contestRequest));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Contest name already exists", exception.getReason());
    }

    // add a user to an existing contest
    @Test
    public void test_add_user_to_existing_contest() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestRepository contestRepository = mock(ContestRepository.class);
        ContestServiceImpl contestService = new ContestServiceImpl(userRepository, contestRepository);

        Contest existingContest = new Contest("Existing Contest", List.of("user1"));
        ContestRequest contestRequest = new ContestRequest("Existing Contest", List.of("user2"));

        when(contestRepository.findById("1")).thenReturn(Optional.of(existingContest));
        when(userRepository.findById("user2")).thenReturn(Optional.of(new User()));
        when(contestRepository.save(any(Contest.class))).thenReturn(existingContest);

        Contest updatedContest = contestService.addUserToContest("1", contestRequest);

        assertNotNull(updatedContest);
        assertTrue(updatedContest.getParticipantsId().contains("user2"));
    }

    // remove a user from an existing contest
    @Test
    public void test_remove_user_from_existing_contest() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestRepository contestRepository = mock(ContestRepository.class);
        ContestServiceImpl contestService = new ContestServiceImpl(userRepository, contestRepository);

        Contest existingContest = new Contest("Existing Contest", List.of("user1", "user2"));
        ContestRequest contestRequest = new ContestRequest("Existing Contest", List.of("user2"));

        when(contestRepository.findById("1")).thenReturn(Optional.of(existingContest));
        when(userRepository.findById("user2")).thenReturn(Optional.of(new User()));
        when(contestRepository.save(any(Contest.class))).thenReturn(existingContest);

        Contest updatedContest = contestService.removeUserFromContest("1", contestRequest);

        assertNotNull(updatedContest);
        assertFalse(updatedContest.getParticipantsId().contains("user2"));
    }

    // get a contest by its ID
    @Test
    public void test_get_contest_by_id() {
        UserRepository userRepository = mock(UserRepository.class);
        ContestRepository contestRepository = mock(ContestRepository.class);
        ContestServiceImpl contestService = new ContestServiceImpl(userRepository, contestRepository);

        Contest existingContest = new Contest("Existing Contest", List.of("user1", "user2"));

        when(contestRepository.findById("1")).thenReturn(Optional.of(existingContest));

        Contest retrievedContest = contestService.getContestById("1");

        assertNotNull(retrievedContest);
        assertEquals("Existing Contest", retrievedContest.getName());
    }

}
