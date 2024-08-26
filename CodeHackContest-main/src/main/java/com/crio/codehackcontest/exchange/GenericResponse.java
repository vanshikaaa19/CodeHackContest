package com.crio.codehackcontest.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The GenericResponse class is a generic container for API responses.
 *
 * <p>This class is used to provide a standardized structure for responses, containing
 * the main data and an optional message. It is a best practice to return data inside
 * a response object, as it allows for easy extension with additional fields if required.</p>
 *
 * <p><b>Type Parameters:</b></p>
 * <ul>
 *   <li>{@code <T>} - The type of the data being returned in the response.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code T data} - The main data of the response.</li>
 *   <li>{@code String msg} - An optional message accompanying the response.</li>
 * </ul>
 *
 * <p><b>Constructors:</b></p>
 * <ul>
 *   <li>{@code GenericResponse()} - No-argument constructor.</li>
 *   <li>{@code GenericResponse(T data)} - Constructor that initializes the response with the given data.</li>
 *   <li>{@code GenericResponse(T data, String msg)} - Constructor that initializes the response with the given data and message.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This class should be used to return data from an API endpoint, encapsulated in a response object.
 * If additional keys need to be added to the response in the future, they can be easily incorporated into this class.</p>
 *
 * <pre>{@code
 * // Example usage with data only
 * GenericResponse<User> response = new GenericResponse<>(new User("John Doe"));
 *
 * // Example usage with data and message
 * GenericResponse<User> responseWithMsg = new GenericResponse<>(new User("John Doe"), "User retrieved successfully");
 *
 *  // Example usage with List of User data
 *  GenericResponse<List<User>> response= new GenericResponse<>(
 *     [
 *         {
 *             "id": "66892aa280fd437a5f365bc7",
 *             "username": "Test User2",
 *             "userid": "testuser2",
 *             "score": 10,
 *             "badges": [
 *                 "Code Ninja"
 *             ]
 *         },
 *         {
 *             "id": "66892a9580fd437a5f365bc6",
 *             "username": "Test User1",
 *             "userid": "testuser1",
 *             "score": 0,
 *             "badges": []
 *         }
 *     ]);
 *
 * }</pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private T data;
    private String msg;

    /**
     * Constructs a GenericResponse with the given data.
     *
     * @param data the data to be included in the response
     */
    public GenericResponse(T data) {
        this.data = data;
    }
}
