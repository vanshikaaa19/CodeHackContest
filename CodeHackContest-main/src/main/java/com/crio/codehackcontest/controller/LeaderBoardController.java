package com.crio.codehackcontest.controller;

import com.crio.codehackcontest.exchange.GenericResponse;
import com.crio.codehackcontest.model.LeaderBoard;
import com.crio.codehackcontest.service.ContestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The LeaderBoardController class handles HTTP requests for retrieving leaderboards.
 *
 * <p>This controller provides an endpoint for fetching the leaderboard for a specific contest.</p>
 */
@RestController
@RequestMapping("/leaderboard")
public class LeaderBoardController {
    private final ContestService contestService;

    /**
     * Constructs a LeaderBoardController with the given ContestService.
     *
     * @param contestService the service to manage contest operations
     */
    public LeaderBoardController(ContestService contestService) {
        this.contestService = contestService;
    }

    /**
     * Retrieves the leaderboard for a specific contest by its ID.
     *
     * @param id the ID of the contest
     * @return a ResponseEntity containing the leaderboard
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLeaderBoardByContestId(@PathVariable String id) {
        LeaderBoard leaderBoard = contestService.checkLeaderBoard(id);
        GenericResponse<LeaderBoard> data = new GenericResponse<>(leaderBoard);
        return ResponseEntity.ok().body(data);
    }
}
