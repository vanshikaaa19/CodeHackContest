package com.crio.codehackcontest.exchange;

import com.crio.codehackcontest.model.Badges;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;


/**
 * The UpdateUserRequest class represents a request to update a user's score and badges.
 *
 * <p>Users can update their score through this request. The system will automatically
 * determine and update the user's badges based on the new score.</p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code int score} - The score of the user.
 *       <ul>
 *           <li>Constraints:</li>
 *           <ul>
 *               <li>Minimum value: 0 (inclusive)</li>
 *               <li>Maximum value: 100 (inclusive)</li>
 *           </ul>
 *           <li>Error Messages:</li>
 *           <ul>
 *               <li>"Score can't be less than 0" if the value is below 0.</li>
 *               <li>"Score can't be more than 100" if the value is above 100.</li>
 *           </ul>
 *       </ul>
 *   </li>
 *   <li>{@code HashSet<Badges> badges} - A set of badges awarded to the user. This set is automatically updated based on the user's score.</li>
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
 * <p>Users can specify a new score within the range of 0 to 100. The badges field does not need to be manually updated by the user as the system will handle badge updates based on the new score.</p>
 *
 * <pre>{@code
 * UpdateUserRequest updateUserRequest = new UpdateUserRequest();
 * updateUserRequest.setScore(85);
 * // No need to set badges manually; it will be updated automatically based on the score
 * }</pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @Min(value = 0, message = "Score can't be less than 0")
    @Max(value = 100, message = "Score can't be more than 100")
    private int score;
    private HashSet<Badges> badges;
}

