package com.crio.codehackcontest.exchange;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * The ContestRequest class represents a request to create or update a contest.
 *
 * <p>This class is used to provide the necessary details for a contest, including its name
 * and an optional list of participants. It offers multiple constructors to initialize
 * the request with either the contest name, the list of participants, or both.</p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code String name} - The name of the contest.
 *       <ul>
 *           <li>Constraints:</li>
 *           <ul>
 *               <li>{@code @NotEmpty} - The contest name must not be empty.</li>
 *               <li>{@code @NonNull} - The contest name must not be null.</li>
 *           </ul>
 *       </ul>
 *   </li>
 *   <li>{@code List<String> participants} - The list of participants in the contest.
 *       <ul>
 *           <li>Default value: an empty list.</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, toString, equals, and hashCode methods.</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with arguments for all fields.</li>
 * </ul>
 *
 * <p><b>Constructors:</b></p>
 * <ul>
 *   <li>{@code ContestRequest()} - No-argument constructor.</li>
 *   <li>{@code ContestRequest(@NonNull String name)} - Constructor that initializes the request with the contest name.</li>
 *   <li>{@code ContestRequest(List<String> participants)} - Constructor that initializes the request with the list of participants.</li>
 *   <li>{@code ContestRequest(@NonNull String name, List<String> participants)} - Constructor that initializes the request with the contest name and participants.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This class should be used to create or update contest details by providing a non-empty contest name and an optional list of participants.</p>
 *
 * <pre>{@code
 * // Example usage with contest name only
 * ContestRequest contestWithName = new ContestRequest("Code Challenge");
 *
 * // Example usage with participants only
 * List<String> participants = Arrays.asList("Alice", "Bob", "Charlie");
 * ContestRequest contestWithParticipants = new ContestRequest(participants);
 *
 * // Example usage with both name and participants
 * ContestRequest fullContestRequest = new ContestRequest("Code Challenge", participants);
 * }</pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestRequest {
    @NotEmpty
    @NonNull
    private String name;
    private List<String> participants = new ArrayList<>();

    /**
     * Constructs a ContestRequest with the given contest name.
     *
     * @param name the name of the contest
     */
    public ContestRequest(@NonNull String name) {
        this.name = name;
    }

    /**
     * Constructs a ContestRequest with the given list of participants.
     *
     * @param participants the list of participants in the contest
     */
    public ContestRequest(List<String> participants) {
        this.participants = participants;
    }
}
