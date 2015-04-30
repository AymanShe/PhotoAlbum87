package cs213.photoAlbum.model;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/**
 * @author 1
 * @modify by 2
 * as per 1's instructions
 * added IBackend, writeData(Iback, back)
 */
public class Backend implements IBackend, Serializable{
	/**
	 * Backend functionally will allow for storage and retrieval of users,albums, and
	 * photo file info from the data directory. Also, the storage and retrieval photo
	 *  files that in other areas.
	 *  
	 *  @param userList JList of users
	 * @param user a User object
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	Hashtable<String, User> users = new Hashtable<String, User>();
	User user;
	private static final String dirName = "data";
	private static final String fileName = "users.dat";
	
	/** default constructor */
	public Backend(){
	
	}
	
	/**
	* Loads users from memory.
	*/
	//readSessionData is called from the view, don't call this in the constructor!
	public Backend readSessionData()
    {
        	try{
            	File dir = new File(dirName);
            	dir.mkdir();
            	
                ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream(dirName + File.separator + fileName));
                return (Backend)ois.readObject();
            }
            catch(Exception e){
                Backend data = new Backend();
                return data;
            }
    }
	
	

	/**
	* Saves users into memory.
	* @param backend An instance of a class that uses the backend interface,
	*/
	//Change to (IBackend backend) once you have created the backend interface
	public void writeData(IBackend backend) 
            throws IOException {
		ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(dirName + File.separator + fileName));
        oos.writeObject(backend);
    }
	
	/** 
	 * returns the hashtable of users
	 */
	public Hashtable<String, User> getUsers()
	{
		return users;
	}
	
	/**
	* Adds a new user to the Hashtable of users
	* @param id id of the user
	* @param fullName full name of the user
	* @return true if the user was successfully added, false if an existing user has the same id
	*/
	public boolean addUser(String id, String fullName)
	{
		if(this.users.containsKey(id)) return false;
		
		this.users.put(id, new User(id, fullName));
		return true;
	}
	
	/**
	 * deletes user from the hashtable of users
	 * 
	 * @param id of the user to be deleted
	 * @return true if the user was successfully deleted, false if the user was not found
	 */			
	public boolean deleteUser(String id){
		if(!this.users.containsKey(id)) return false;
		
		this.users.remove(id);
		return true;
	}
}