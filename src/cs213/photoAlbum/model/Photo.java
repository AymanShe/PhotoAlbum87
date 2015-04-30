package cs213.photoAlbum.model;



import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 2
 *
 *creates a photo object
 *
 */
public class Photo implements Comparable, Serializable{

	/**
	 * The Photo class is used to create a Photo object that 
	 * stores the fileName of the photo, a caption for a description 
	 * of the photo, a date for the date the file was saved, and 
	 * functionality to add and delete photos to and from albums, also 
	 * to re-caption photos.
	 * 
	 * @param filePath name of file
	 * @param caption caption for the photo
	 * @param date the date of the photo
	 * 
	 * @param tag <location> location of photo
	 * @param tag <people> people in the photo
	 *  
	 * 
	 */
	
	String filePath;
	String caption;
	
	//not sure if date would be okay
	SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy'-'HH:mm:ss");
	Date date;
	String dateString;
	
	//added calendar for the getInstance()
	Calendar calendar; 
	
	ArrayList<Album> albums = new ArrayList<Album>();
	ArrayList<Tag> tagList = new ArrayList<Tag>();
	
	
	/** default constructor 
	 * @param lastModified TODO*/
	public Photo(String f, String c, Date lastModified){
		this.filePath = f;
		this.caption = c;
		
		this.date = lastModified;
		this.dateString = sdf.format(date);
		
		this.calendar = calendar.getInstance();
		calendar.set(Calendar.MILLISECOND,0);
	}
	
	/** 
	 *  @return the albums this photo belongs to   
	*/
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	/** method getFileName()
	 * 
	 *  @return the file name
	 *     
	*/
	public String getFilePath(){
		return filePath;
	}
	
	/**
	 * @param n string that holds new file name
	 * 
	 */	
	public void setFilePath(String n){
		this.filePath = n;
	}
	
	
	/** method getCaption() returns the caption*/
	public String getCaption(){
		return caption;
	}
	
	
	/**
	 * @param s string that holds new caption
	 * 
	 */	
	public void setCaption(String s){
		this.caption = s;
	}
	
	
	/** method getDate() returns date */
	public Date getDate(){
		return date;
	}
	
	/**
	 * @param d date that holds new date
	 *
	 */	
	public void setDate(Date d){
		this.date = d;
		
	}
	
	/** 
	 *  returns the list of tags associated with this photo
	*/
	public ArrayList<Tag> getTags(){
		return tagList;
	}
	
	public boolean addTag(String tagType, String tagValue)
	{
		Tag newTag = new Tag(tagType, tagValue);
		
		/*for(Tag t: tagList)
		{
			if(t.getType().equals(tagType) && t.getValue().equals(tagValue))
			{
				return false;
			}
		}*/
		
		if(tagList.contains(newTag)) return false;
		
		tagList.add(newTag);
		return true;
	}
	
	public boolean removeTag(String tagType, String tagValue)
	{
		Tag newTag = new Tag(tagType, tagValue);
		
		/*for(Tag t: tagList)
		{
			if(t.getType().equals(tagType) && t.getValue().equals(tagValue))
			{
				return false;
			}
		}*/
		
		if(!tagList.contains(newTag)) return false;
		
		tagList.remove(newTag);
		return true;
	}
	
	public String toString(){
		return "fileName: "+filePath+ " caption: " +caption + " date is: " + date
				+ " dateString is: "+ dateString+  " tag1: "+ tagList.toString()
				+ " Calendar time is: "+ sdf.format(calendar.getTime());
		
	}

	@Override
	public int compareTo(Object object) {
		if (object != null && object instanceof Photo) {
            Photo p = (Photo) object;
            return this.date.compareTo(p.getDate());
        }

        return -1;
	}

	
}
