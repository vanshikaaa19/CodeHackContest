package com.crio.codehackcontest.entity;

import com.crio.codehackcontest.model.Badges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;

/**
 * The User class represents a user entity stored in MongoDB.
 *
 * <p>It includes fields for the user's ID, username, score, and badges.</p>
 */
@Data
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userid;

    @NonNull
    private String username;

    @Min(0)
    @Max(100)
    private int score = 0;

    private HashSet<Badges> badges;
}
