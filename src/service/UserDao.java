package service;

import model.User;

public interface UserDao {
	
	/**
	 * Creates a new user in the User table
	 * 
	 * @param user
	 * @return the auto increment id of the new user
	 */
	public boolean createUser(User user);
	
	/**
	 * Updates a user in the User table
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);
	
	/**
	 * Finds the user with the email address matching the parameter
	 * 
	 * @param email
	 * @return the User object, or {@link User#NULL_USER} if a user cannot be found
	 */
	public User findUser(String email);
    
    /**
     * Checks that a user is in the system
     * @param email
     * @return true if exists
     */
    public boolean userExists(String email);
	
	/**
	 * Deletes a user with the specified email
	 * 
	 * @param email
	 * @return
	 */
	public boolean deleteUser(String email);
	
	/**
	 * Finds a user and logs them in if password is correct
	 * 
	 * @param email
	 * @return true if passwords match
	 */
	public boolean loginUser(String email, String password);
	
	public boolean passwordMatch(String pass1, String pass2);
	
}
