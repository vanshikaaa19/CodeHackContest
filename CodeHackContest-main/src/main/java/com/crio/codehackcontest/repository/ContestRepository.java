package com.crio.codehackcontest.repository;

import com.crio.codehackcontest.entity.Contest;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The ContestRepository interface provides data access methods for the Contest entity.
 *
 * <p>It extends MongoRepository to inherit basic CRUD operations for Contest objects.</p>
 */
public interface ContestRepository extends MongoRepository<Contest, String> {

    /**
     * Deletes all contests except the one with the specified name.
     *
     * @param name the name of the contest to retain
     */
    @Transactional
    @Query(value = "{ 'name': { $ne: ?0 } }", delete = true)
    void deleteAllExceptSingleContest(String name);

    /**
     * Finds a contest by its name.
     *
     * @param name the name of the contest to find
     * @return an Optional containing the found contest, or empty if not found
     */
    Optional<Contest> findContestByName(@NonNull String name);

}
