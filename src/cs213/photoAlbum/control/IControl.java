/**
 * @author 1
 */

package cs213.photoAlbum.control;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;

public interface IControl {

	/*
	 * The control (or controller) does all manipulation/processing of data
	 * within the program including the creation (but not storage) and deletion
	 * of new objects, sorting/filtering data on various dimensions, and
	 * checking data validity that goes beyond syntactic correctness of input
	 * commands. (For instance, checking dates for validity.)
	 * 
	 * As mentioned earlier, the view can directly interact with the model only
	 * for direct access of data that is not processed in any way. Note that the
	 * user specification in the model includes storing albums in efficient data
	 * structures.
	 * 
	 * The control can know about a single user at a time. Then all operations
	 * on the control apply to that user.
	 * 
	 * The control should not be tied to any one particular model
	 * implementation, so it should interact with the model interface(s).
	 */
	
	/**
	* @return Returns the active user 
	*/
	User getActiveUser();
	
	/**
	* @return Returns a list of albums associated with the active user 
	*/
	public abstract ArrayList<Album> listAlbums();
	
	/**
	* Adds a new user to the Hashtable of users
	* @param id id of the user
	* @param fullName full name of the user
	* @return true if the user was successfully added, false if an existing user has the same id
	*/
	public abstract boolean addUser(String userId, String name);
	
	/**
	* Removes a user from the Hashtable of users
	* @param id id of the user
	* @return true if the user was successfully deleted, false if the user does not exist
	*/
	public abstract boolean deleteUser(String userId);

	/**
	* @return the user with the given userId
	*/
	public abstract User getUser(String userId);

	
	/**
	 * @param name The name of the album being created.
	 * @return <code>true</code> if the album was successfully created; <code>false</code> if the album already exists.
	 */
	public abstract boolean createAlbum(String name);
	
	/**
	 * @param name The name of the album being deleted.
	 * @return <code>true</code> if the album was successfully deleted; <code>false</code> if the album does not exist.
	 */
	public abstract boolean deleteAlbum(String name);
	
	/**
	 * @return <code>true</code> if the active user has any albums; <code>false</code> if that user has no albums.
	 */
	public abstract boolean albumExists();
	
	/**
	 * @param fileName The name of the photo.
	 * @return <code>true</code> if the photo exists; <code>false</code> if the photo does not exist.
	 */
	public abstract boolean photoExists(String fileName);
	
	/**
	 * @param fileName The name of the photo being added.
	 * @param caption The caption associated with the photo being added.
	 * @param albumName The name of the album the photo is being added to.
	 * @return <code>true</code> if the photo was successfully added; <code>false</code> if the photo already exists.
	 * @throws NullPointerException if the album the photo is being added to does not exist.
	 * @throws FileNotFoundException if the photo does not exist.
	 */
	public abstract boolean addPhoto(String fileName, String caption, String albumName) throws NullPointerException, FileNotFoundException;
	
	/**
	 * @param fileName The name of the photo being moved.
	 * @param oldAlbumName The name of the album the photo is being moved from.
	 * @param newAlbumName The name of the album the photo is being moved to.
	 * @return <code>true</code> if the photo was successfully moved; 
	 * <code>false</code> if the photo does not exist in the old album.
	 * @throws NullPointerException if the old album or new album does not exist.
	 * @throws IllegalArgumentException if the new album already contains the photo being moved.
	 */
	public abstract boolean movePhoto(String fileName, String oldAlbumName, String newAlbumName) throws NullPointerException, IllegalArgumentException;
	
	/**
	 * @param fileName The name of the photo being removed.
	 * @param albumName The name of the album the photo is being removed from.
	 * @return <code>true</code> if the photo was successfully removed; 
	 * <code>false</code> if the photo does not exist in the album.
	 * @throws NullPointerException if the album does not exist.
	 */
	public abstract boolean removePhoto(String fileName, String albumName) throws NullPointerException;
	
	/**
	 * @param fileName The name of the photo tags are being added to.
	 * @param tagType The type of the new tag.
	 * @param tagValue The value of the new tag.
	 * @return <code>true</code> if the tag was successfully added; 
	 * <code>false</code> if the tag already exists.
	 * @throws NullPointerException if the file does not exist.
	 */
	public abstract boolean addTag(String fileName, String tagType, String tagValue) throws NullPointerException;
	
	/**
	 * @param fileName The name of the photo tags are being removed from.
	 * @param tagType The type of the tag being deleted.
	 * @param tagValue The value of the tag being deleted.
	 * @return <code>true</code> if the tag was successfully removed; 
	 * <code>false</code> if the tag does not exist for the photo.
	 * @throws NullPointerException if the file does not exist.
	 */
	public abstract boolean deleteTag(String fileName, String tagType, String tagValue) throws NullPointerException;
	
	/**
	 * @param fileName The name of the photo to search for.
	 * @return The photo if it exists; <code>null</code> otherwise.
	 */
	public abstract Photo getPhoto(String fileName);
	
	/**
	 * @param startDate The starting date of the range of time to search in.
	 * @param endDate The ending date of the range of time to search in.
	 * @return The list of photos taken in the time period specified by the starting and ending dates.
	 */
	public abstract ArrayList<Photo> getPhotosByDate(Date startDate, Date endDate);
	
	/**
	 * @param startDate The starting date of the range of time to search in "MM/dd/yyyy-HH:m:s" format.
	 * @param endDate The ending date of the range of time to search in "MM/dd/yyyy-HH:m:s" format.
	 * @return The list of photos taken in the time period specified by the starting and ending dates.
	 */
	public abstract ArrayList<Photo> getPhotosByDate(String startDate, String endDate) throws java.text.ParseException;
	
	/**
	 * @param tagValue A tag value to search for.
	 * @return The list of photos matching the specified tag value.
	 */
	public abstract ArrayList<Photo> getPhotosByTag(String tagValue);
	
	/**
	 * @param t A list of tags to search for.
	 * @return The list of photos with all the specified tags.
	 */
	public abstract ArrayList<Photo> getPhotosByTag(Tag... t);
	
	/**
	 * @return The list of photos belonging to the active user.
	 */
	public abstract ArrayList<Photo> getPhotos();
	
	/**
	 * @return The list of photos belonging to the album specified
	 */
	public abstract ArrayList<Photo> getPhotosByAlbum(String name);
	
	/**
	 * @param user Sets the active User to user
	 */
	public abstract void login(User user);
	
	/**
	 * @param id Sets the active User to the user matching the id
	 * @return true if the id is valid, false if the id is not found
	 */
	public abstract boolean login(String id);
	
	/**
	 * Clears the active user from memory and saves the session to disk.
	 */
	public abstract void logout();
	
	/**
	 * @return The album matching the specified name, null if no album is found
	 */
	public abstract Album getAlbum(String name);
}
