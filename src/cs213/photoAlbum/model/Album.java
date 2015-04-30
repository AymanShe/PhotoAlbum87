package cs213.photoAlbum.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @author 2
 *
 */



public class Album implements Serializable{
	/**
	 * The Album is used to store zero or more photos, also
	 * it has the functionality to add and delete photos
	 * to and from albums, lastly the Album class is able to 
	 * re-caption photos
	 * 
	 * 
	 * @param albumName the name of the album
	 * @param photoList a JList of photo objects
	 * 
	 */

	String albumName;
	
	ArrayList<Photo> photoList = new ArrayList<Photo>();
	
	/** default constructor */
	public Album(String name){
		this.albumName = name;
		
	}
	
	/*Album(String name, Photo photo){
		this.albumName = name;
		photoList.add(photo);
	}*/
	
	/** returns album name */
	public String getAlbumName(){
		return albumName;
	}
	
	/**
	 *method setAlbumName() sets the album name
	 * @param a string that holds new album name
	 * 
	 *  
	 */
	public void setAlbumName(String a){
		this.albumName = a;
	}
	
	/**
	 *method addPhoto() adds a photo to the album
	 *	@param Object photo the photo to be added 
	 *  
	 * 
	 */
	public void addPhoto(Photo photo){
		photo.albums.add(this);
		photoList.add(photo);
	}
	
	/**
	 *	@param fileName The file name of the photo.
	 * @param caption The photo caption.
	 *  Adds a new photo with the specified file name and caption to the album
	 * @param lastModified TODO
	 */
	public void addPhoto(String fileName, String caption, Date lastModified){
		Photo p = new Photo(fileName, caption, lastModified);
		p.albums.add(this);
		
		photoList.add(p);
	}
	
	/**
	 * method deletePhoto() deletes a selected photo
	 * @param name the name of photo to be deleted 
	 *   
	 * @return true if the photo was removed from the album, false if the photo was not found
	 */		
	public boolean deletePhoto(String name){
		
		for(Photo p: photoList)
		{
			if(p.getFilePath().equals(name))
			{
				for(int i = 0; i < p.albums.size(); i++)
				{
					if(p.albums.get(i).getAlbumName().equals(this.getAlbumName()))
					{
						photoList.remove(p);
						p.albums.remove(i);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * method getPhoto() returns the selected photo
	 * 
	 * @param photoName the name of the photo
	 *    
	 * @return Photo if <true> if the photo found 
	 *         <null> <false> if photo is not found
	 * 
	 */	
	public Photo getPhoto(String photoName ){
		
		
		for(Photo p: photoList)
		{
			if(p.getFilePath().equals(photoName))
			{
				return p;
			}
		}
		return null;
		
	}
	
	/**
	 * @return the earliest date a photo was taken in the album
	 */
	public Date getStartDate()
	{
		Date earliest = null;
		for(Photo p: photoList)
		{
			if(earliest == null)
			{
				earliest = p.getDate();
			}
			else
			{
				if(p.getDate().compareTo(earliest) < 0)
				{
					earliest = p.getDate();
				}
			}
		}
		return earliest;
	}
	
	/**
	 * @return the latest date a photo was taken in the album
	 */
	public Date getEndDate()
	{
		Date latest = null;
		for(Photo p: photoList)
		{
			if(latest == null)
			{
				latest = p.getDate();
			}
			else
			{
				if(p.getDate().compareTo(latest) > 0)
				{
					latest = p.getDate();
				}
			}
		}
		return latest;
	}
	
	/**
	 * returns the list of photos in the album
	 */	
	public ArrayList<Photo> getPhotos(){
		return photoList;
	}
		
	
	
	/**
	 * method lookForPhotoName() looks for the photo in the list
	 * @pparam name the name of the photo to be looked for
	 * 
	 * @return <true> if found
	 *         <false> if not found  
	 * 
	 * 
	 */
	public boolean lookForPhotoName(String name){
		
		Iterator<Photo> itr = photoList.iterator();
		
		
		while(itr.hasNext() ){
			String tempName = itr.next().filePath;
			
			if(tempName == name)
			{
				
				return true;
				
			}
						
		}
		return false;
		
	}
	
	/**
	 * method reCaptionPhoto() changes the caption for the photo
	 * @param name the name of photo to be re-captioned   
	 * @param c is the new caption
	 * 
	 * @return <true> if success
	 *         <false> if failed
	 * 
	 */	
	public boolean reCaptionPhoto(String name, String c){
		
		Iterator<Photo> itr = photoList.iterator();
		int count = 0;
		
		while(itr.hasNext() ){
			String tempName = itr.next().filePath;
			
			if(tempName == name)
			{
				//Photo temp = photoList.get(count);
				photoList.get(count).setCaption(c);
				return true;
				
			}
			else if(itr.hasNext()){
				count++;
			}
			
		}
		return false;
		
		
	}
	
	
	/** toString() to pprint out the album class */
	public String toString(){
		return "album name is: " +albumName + " photoList is: " + photoList.toString();
	}
}
