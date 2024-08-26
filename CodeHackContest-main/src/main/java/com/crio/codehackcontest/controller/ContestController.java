package com.crio.codehackcontest.controller;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.exchange.ContestRequest;
import com.crio.codehackcontest.exchange.GenericResponse;
import com.crio.codehackcontest.service.ContestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The ContestController class handles HTTP requests for managing contests.
 *
 * <p>This controller provides endpoints for creating, retrieving, updating, and deleting contests.</p>
 */
@RestController
@RequestMapping("/contests")
public class ContestController {
    private final ContestService contestService;

    /**
     * Constructs a ContestController with the given ContestService.
     *
     * @param contestService the service to manage contest operations
     */
    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    // ----------------------------- CONTEST -----------------------------

    /**
     * Creates a new contest.
     *
     * @param contestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} the request containing contest details
     * @return a ResponseEntity containing the created contest
     */
    @PostMapping("")
    public ResponseEntity<?> createContest(@Valid @RequestBody ContestRequest contestRequest) {
        Contest savedContest = contestService.createContest(contestRequest);
        GenericResponse<Contest> data = new GenericResponse<>(savedContest, "Contest: single-contest\n Other than this contest, other will get deleted on refresh of server");
        return ResponseEntity.ok().body(data);
    }

    /**
     * Retrieves all contests.
     *
     * @return a ResponseEntity containing a list of all contests
     */
    @GetMapping("")
    public ResponseEntity<?> getAllContest() {
        List<Contest> contests = contestService.getContests();
        GenericResponse<List<Contest>> data = new GenericResponse<>(contests);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Retrieves a contest by its ID.
     *
     * @param id the ID of the contest to retrieve
     * @return a ResponseEntity containing the contest
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getContestById(@PathVariable String id) {
        Contest contest = contestService.getContestById(id);
        GenericResponse<Contest> data = new GenericResponse<>(contest);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Adds a user to a contest.
     *
     * @param id the ID of the contest
     * @param contestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} the request containing the user details to add
     * @return a ResponseEntity containing the updated contest
     */
    @PutMapping("/add/{id}")
    public ResponseEntity<?> addUserToContest(@PathVariable String id, @RequestBody ContestRequest contestRequest) {
        Contest contest = contestService.addUserToContest(id, contestRequest);
        GenericResponse<Contest> data = new GenericResponse<>(contest);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Removes a user from a contest.
     *
     * @param id the ID of the contest
     * @param contestRequest {@link com.crio.codehackcontest.exchange.ContestRequest} the request containing the user details to remove
     * @return a ResponseEntity containing the updated contest
     */
    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeUserFromContest(@PathVariable String id, @RequestBody ContestRequest contestRequest) {
        Contest contest = contestService.removeUserFromContest(id, contestRequest);
        GenericResponse<Contest> data = new GenericResponse<>(contest);
        return ResponseEntity.ok().body(data);
    }

    /**
     * Deletes a contest by its ID.
     *
     * @param id the ID of the contest to delete
     * @return a ResponseEntity containing a confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContest(@PathVariable String id) {
        contestService.deleteContest(id);
        GenericResponse<String> data = new GenericResponse<>("Contest Deleted Successfully");
        return ResponseEntity.ok().body(data);
    }
}
