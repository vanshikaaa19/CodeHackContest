package com.crio.codehackcontest.service;

import com.crio.codehackcontest.entity.Contest;
import com.crio.codehackcontest.exchange.ContestRequest;
import com.crio.codehackcontest.model.LeaderBoard;

import java.util.List;

public interface ContestService {
    Contest createContest(ContestRequest contestRequest);

    Contest addUserToContest(String id, ContestRequest contestRequest);

    Contest removeUserFromContest(String id, ContestRequest contestRequest);

    Contest getContestById(String id);

    List<Contest> getContests();

    void deleteContest(String id);

    LeaderBoard checkLeaderBoard(String id);

}
