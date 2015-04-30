package cs213.photoAlbum.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public interface IBackend extends Serializable{	
	
	/**
	 * method readSessionData()  Loads users from memory.
	 * 
	 * @return Backend object
	 */
	public abstract Backend readSessionData();
	
	/**
	* Saves users into memory.
	* @param backend An instance of a class that uses the backend interface,
	 * @throws IOException 
	*/
	public abstract void writeData(IBackend backend) throws IOException;
	
	/** 
	 * returns the hashtable of users
	 */
	public Hashtable<String, User> getUsers();
	
	/**
	* Adds a new user to the Hashtable of users
	* @param id id of the user
	* @param fullName full name of the user
	* @return true if the user was successfully added, false if an existing user has the same id
	*/
	public boolean addUser(String id, String fullName);
		
	/**
	 * deletes user from the hashtable of users
	 * 
	 * @param id of the user to be deleted
	 * @return true if the user was successfully deleted, false if the user was not found
	 */			
	public boolean deleteUser(String id);
	
	

}