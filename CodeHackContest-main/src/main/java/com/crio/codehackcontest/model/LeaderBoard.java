package com.crio.codehackcontest.model;

import com.crio.codehackcontest.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderBoard {
    private String id;
    private String name;
    private List<User> user;
}
