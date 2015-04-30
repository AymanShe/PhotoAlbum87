package cs213.photoAlbum.model;

import java.io.Serializable;

/*
 * @Author 1
 */

public class Tag implements Serializable{

	String tagType;
	String tagValue;
	
	/**
	 * Creates a new Tag object. Tags are a pair of tag type and tag value strings.
	 * @param tagType The type of the tag.
	 * @param tagValue The value of the tag.
	 */
	public Tag (String tagType, String tagValue) {
        this.tagType = tagType;
        this.tagValue = tagValue;
    }

	/**
	 * @param setType The new type of the tag.
	 */
	public void setType(String newType)
	{
		tagType = newType;
	}
	
	/**
	 * Returns the type of the tag.
	 */
	public String getType()
	{
		return tagType;
	}
	
	/**
	 * @param newValue The new value of the tag.
	 */
	public void setValue(String newValue)
	{
		tagValue = newValue;
	}
	
	/**
	 * Returns the value of the tag.
	 */
	public String getValue()
	{
		return tagValue;
	}
	
	/**
	 * Returns true if the other object is a Tag with the same tag type and value.
	 */
    @Override
    public boolean equals(Object object) {

        if (object != null && object instanceof Tag) {
            Tag t = (Tag) object;
            return tagType.equals(t.tagType) && tagValue.equals(t.tagValue);
        }

        return false;
    }
}
