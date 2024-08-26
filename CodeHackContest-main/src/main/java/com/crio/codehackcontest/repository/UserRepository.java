package com.crio.codehackcontest.repository;

import com.crio.codehackcontest.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The UserRepository interface provides data access methods for the User entity.
 *
 * <p>It extends MongoRepository to inherit basic CRUD operations for User objects.</p>
 */
public interface UserRepository extends MongoRepository<User, String> {
}
