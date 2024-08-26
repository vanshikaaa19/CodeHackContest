package com.crio.codehackcontest.exchange;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserRequest class represents a request to create a new user.
 *
 * <p>This class is used exclusively for creating new users. It contains
 * the necessary information such as the user's ID and username.</p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code String userid} - The unique identifier for the user.
 *       <ul>
 *           <li>Constraints:</li>
 *           <ul>
 *               <li>{@code @NotEmpty} - The user ID must not be empty.</li>
 *               <li>{@code @ID} - The user ID must be unique.</li>
 *           </ul>
 *       </ul>
 *   </li>
 *   <li>{@code String username} - The name of the user.
 *       <ul>
 *           <li>Constraints:</li>
 *           <ul>
 *               <li>{@code @NotEmpty} - The username must not be empty.</li>
 *           </ul>
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
 * <p><b>Usage:</b></p>
 * <p>This class should be used to create new users by providing a non-empty user ID and username.</p>
 *
 * <pre>{@code
 * UserRequest userRequest = new UserRequest("user123", "John Doe");
 * }</pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotEmpty
    private String userid;
    @NotEmpty
    private String username;
}
