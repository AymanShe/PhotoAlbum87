package cs213.photoAlbum.model;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author 2
 *
 */
public class User implements Serializable{
	
	/**
	 * The User is used to add, delete and rename albums.
	 * 
	 * @param userId user's unique id
	 * @param userFullName the user's full name
	 * @param albumList a list of albums for the user
	 * 
	 * 
	 */
	
	String userId;
	String userFullName;
	
	ArrayList<Album> albumList = new ArrayList<Album>();
	
	
	/** default constructor */
	/**
	 * @param userid
	 * @param userfullname
	 */
	public User(String userid, String userfullname){
		this.userId= userid;
		this.userFullName = userfullname;		
				
	}
	
	/** returns userId */
	public String getUserId(){
		return userId;
	}
	
	/**
	 * method setUserId() sets the userId to a new value
	 * @param z holds the new userId
	 * 
	 *  
	 */
	public void setUserId(String z){
		userId = z;
	}
	
	/** returns userfullName */
	public String getUserFullName(){
		return userFullName;
	}
	
	/**
	 * method setUserFullName() set the user full name
	 * @param b holds the new full name 
	 * 
	 *
	 */	
	public void setUserFullName(String b){
		userFullName = b;
	}
	
	/**
	 * method lookForAlbumName() looks for album in the list
	 * @param name holds the name of the album to be search for
	 * 
	 * @return <true> if found
	 *         <false> if not found
	 * 
	 * 
	 */
	public boolean lookForAlbumName(String name){
		Iterator<Album> itr = albumList.iterator();
		
		
		while(itr.hasNext() ){
			String tempName = itr.next().albumName;
			
			if(tempName == name)
			{
				
				return true;
				
			}
						
		}
		return false;
		
	}
	
	/**
	 *Method addAlbum() adds a new album
	 * @param name holds the new album name 
	 * 
	 */
	public void addAlbum(Album album){
		albumList.add(album);
		
		
	}
	
	/**
	 * Method deleteAlbum() deletes an existing album
	 * @param name the name of the album to be deleted
	 * 
	 * 
	 * 
	 */	
	public boolean deleteAlbum(String name){
		
		for(Album a: albumList)
		{
			if(a.getAlbumName().equals(name))
			{
				albumList.remove(a);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method getAlbum() gets an album from the lis of albums
	 * @param name the name of the album to be retrieved
	 * 
	 * @return <true> return Album album 
	 *         <false> return null
	 * 
	 * 
	 */	
	public Album getAlbum(String name){
		
		for(Album a: albumList)
		{
			if(a.getAlbumName().equals(name))
			{
				return a;
			}
		}
		
		return null;
		
	}
	
	/**
	 * returns the list of albums belonging to the user
	 */	
	public ArrayList<Album> getAlbums(){
		return albumList;
	}
	
	/**
	 * returns the list of photos belonging to the user
	 */	
	public ArrayList<Photo> getPhotos(){
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		for(Album a: albumList)
		{
			photos.addAll(a.getPhotos());
		}
		
		return photos;
	}
	
	/**
	 * method renameAlbum() renames album
	 * @param object album the album to be renamed
	 * @param name the name of the new album
	 * 
	 * @return <true> if success
	 *         <false> if failed
	 * 
	 * 
	 */
	public boolean renameAlbum(String albumname, String newname){
		
		for(Album a: this.albumList)
		{
			if(a.getAlbumName().equals(albumname))
			{
				a.setAlbumName(newname);
				return true;
			}
		}
		return false;
	}
	
	/** toString() to print the user class */
	public String toString(){
		return "user ID is: " +userId + " user full name is: " + userFullName+ " albumList is: "+ albumList.toString();
	}
	

}
