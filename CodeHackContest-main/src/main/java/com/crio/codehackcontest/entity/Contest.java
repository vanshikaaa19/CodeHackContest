package com.crio.codehackcontest.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The Contest class represents a contest entity stored in MongoDB.
 *
 * <p>It includes fields for the contest's ID, name, and participant IDs.</p>
 */
@Data
@Document(collection = "contest")
@NoArgsConstructor
@AllArgsConstructor
public class Contest {
    @Id
    private String id;

    @NonNull
    @NotEmpty
    private String name;

    private List<String> participantsId = new ArrayList<>();

    /**
     * Constructs a Contest with the specified name.
     *
     * @param name the name of the contest
     */
    public Contest(@NonNull @NotEmpty String name) {
        this.name = name;
    }

    /**
     * Constructs a Contest with the specified name and participants.
     *
     * @param name the name of the contest
     * @param participants the list of participant IDs
     */
    public Contest(@NonNull @NotEmpty String name, List<String> participants) {
        this.name = name;
        this.participantsId = participants != null ? new ArrayList<>(participants) : new ArrayList<>();
    }

    /**
     * Sets the list of participant IDs.
     *
     * @param participantsId the list of participant IDs to set
     */
    public void setParticipantsId(List<String> participantsId) {
        if (this.participantsId == null) {
            this.participantsId = new ArrayList<>(participantsId);
        }
        this.participantsId.addAll(participantsId);
    }

    /**
     * Removes the specified participant IDs from the contest.
     *
     * @param participantsId the list of participant IDs to remove
     */
    public void removeParticipantsId(List<String> participantsId) {
        if (this.participantsId != null) {
            this.participantsId.removeAll(participantsId);
        }
    }
}
