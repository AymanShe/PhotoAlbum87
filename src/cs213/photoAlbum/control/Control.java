package cs213.photoAlbum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.IBackend;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;

public class Control implements IControl{
	
	IBackend backend;
	User user;
	
	public Control(IBackend backend)
	{
		this.backend = backend;
	}
	
	/**
	 * Returns whether the control has an active user
	 */
	public boolean isLoggedIn()
	{
		return this.user != null;
	}

	@Override
	public boolean createAlbum(String name) {
		if(user.getAlbum(name) != null)
		{
			return false;
		}
		user.addAlbum(new Album(name));
		return true;
	}

	@Override
	public boolean deleteAlbum(String name) {
		if(user.getAlbum(name) != null)
		{
			user.deleteAlbum(name);
			return true;
		}
		return false;
	}

	@Override
	public boolean albumExists() {
		return user.getAlbums().size() > 0;
	}

	@Override
	public boolean photoExists(String fileName) {
		return this.getPhoto(fileName) != null;
	}

	@Override
	public boolean addPhoto(String fileName, String caption, String albumName) throws NullPointerException, FileNotFoundException {
		
		if(user.getAlbum(albumName) == null)
		{
			throw new NullPointerException();
		}
		
		File photoFile = new File(fileName);
		if(!photoFile.exists()){
			throw new FileNotFoundException();
		}
		Date lastModified = new Date(photoFile.lastModified());
		
		if(getPhoto(fileName) != null)
		{
			
		}
		
		//if the album doesn't have an existing copy of the photo in it
		if(user.getAlbum(albumName).getPhoto(fileName) == null)
		{
			//if an instance of the photo already exists, add that
			if(getPhoto(fileName) != null)
			{
				user.getAlbum(albumName).addPhoto(getPhoto(fileName));
			}
			//otherwise, create a new photo
			else{
				user.getAlbum(albumName).addPhoto(fileName, caption, lastModified);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean movePhoto(String fileName, String oldAlbumName,
			String newAlbumName) throws NullPointerException, IllegalArgumentException{
		
		if(user.getAlbum(oldAlbumName) == null || user.getAlbum(newAlbumName) == null)
		{
			throw new NullPointerException();
		}
		
		//if the new album already has a copy of the photo, don't overwrite it
		if(user.getAlbum(newAlbumName).getPhoto(fileName) != null)
		{
			throw new IllegalArgumentException();
		}
		
		if(user.getAlbum(oldAlbumName).getPhoto(fileName) != null)
		{
			user.getAlbum(newAlbumName).addPhoto(user.getAlbum(oldAlbumName).getPhoto(fileName));
			user.getAlbum(oldAlbumName).deletePhoto(fileName);
			return true;
		}
		return false;
	}

	@Override
	public boolean removePhoto(String fileName, String albumName) throws NullPointerException {
		
		if(user.getAlbum(albumName) == null)
		{
			throw new NullPointerException();
		}
		
		if(user.getAlbum(albumName).getPhoto(fileName) != null)
		{
			user.getAlbum(albumName).deletePhoto(fileName);
			return true;
		}
		return false;
	}

	@Override
	public boolean addTag(String fileName, String tagType, String tagValue) throws NullPointerException{
		for(Photo p: user.getPhotos())
		{
			if(p.getFilePath().equals(fileName))
			{
				return p.addTag(tagType, tagValue);
			}
		}
		
		throw new NullPointerException();
	}

	@Override
	public boolean deleteTag(String fileName, String tagType, String tagValue) throws NullPointerException {
		for(Photo p: user.getPhotos())
		{
			if(p.getFilePath().equals(fileName))
			{
				return p.removeTag(tagType, tagValue);
			}
		}
		throw new NullPointerException();
	}

	@Override
	public Photo getPhoto(String fileName) {
		for(Album a: user.getAlbums())
		{
			if(a.getPhoto(fileName) != null)
			{
				return a.getPhoto(fileName);
			}
		}
		return null;
	}

	@Override
	public ArrayList<Photo> getPhotosByDate(Date startDate, Date endDate) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		if(!albumExists())
		{
			return null;
		}
		
		for (Photo photo : user.getPhotos()) 
		{
			if(photo.getDate().after(startDate) && photo.getDate().before(endDate))
			{
				photos.add(photo);
			}
			else if(photo.getDate().equals(startDate))
			{
				photos.add(photo);
			}
			else if(photo.getDate().equals(endDate))
			{
				photos.add(photo);
			}
		}

		Collections.sort(photos);
		
		return photos;
	}

	@Override
	public ArrayList<Photo> getPhotosByDate(String startDate, String endDate) throws java.text.ParseException {
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		//DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date start = (Date) formatter.parse(startDate);
		Date end = (Date) formatter.parse(endDate);
		
		return getPhotosByDate(start, end);
	}
	
	@Override
	public ArrayList<Photo> getPhotosByAlbum(String name) {
		
		if(user.getAlbum(name) == null)
		{
			return null;
		}
		
		return user.getAlbum(name).getPhotos();
	}

	@Override
	public ArrayList<Photo> getPhotosByTag(String tagValue) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		if(!albumExists())
		{
			return null;
		}
		
		for (Photo photo : user.getPhotos()) 
		{
			for(Tag t: photo.getTags())
			{
				if(t != null && t.getValue().equals(tagValue))
				{
					photos.add(photo);
				}
			}
		}

		Collections.sort(photos);
		
		return photos;
	}

	@Override
	public ArrayList<Photo> getPhotosByTag(Tag... t) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		if(!albumExists())
		{
			return null;
		}
		
		for (Photo photo : user.getPhotos()) 
		{
			boolean matches = true;
			
			for(Tag tag: t)
			{
				//if the tag type wasn't provided, search the photo's tags for the tag value
				if(tag.getType() == null)
				{
					boolean found = false;
					for(Tag tag2: photo.getTags())
					{
						if(tag2 != null && tag2.getValue().equals(tag.getValue()))
						{
							found = true;
							break;
						}
					}
					//if the tag value was not found, it isn't a match
					matches = found;
				}
				else
				{
					if(!photo.getTags().contains(tag))
					{
						matches = false;
						break;
					}
				}
			}
			if(matches)
			{
				photos.add(photo);
			}
		}

		Collections.sort(photos);
		
		return photos;
	}

	@Override
	public void logout() {
		try {
			backend.writeData(backend);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving session to disk!");
		}
		
		this.user = null;
	}

	@Override
	public void login(User user) {
		this.user = user;		
	}

	@Override
	public boolean login(String id) {
		if(!this.backend.getUsers().containsKey(id)) return false;
			
		this.user = this.backend.getUsers().get(id);	
		return true;
	}

	@Override
	public boolean addUser(String userId, String name) {
		return backend.addUser(userId, name);
	}
	
	@Override
	public User getUser(String userId)
	{
		return backend.getUsers().get(userId);
	}
	
	@Override
	public User getActiveUser()
	{
		return this.user;
	}

	@Override
	public boolean deleteUser(String userId) {
		return backend.deleteUser(userId);
	}

	@Override
	public ArrayList<Album> listAlbums() {
		return this.user.getAlbums();
	}
	
	@Override
	public Album getAlbum(String name) {
		for(Album a: this.user.getAlbums())
		{
			if(a.getAlbumName().equals(name))
			{
				return a;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Photo> getPhotos() {
		return user.getPhotos();
	}

}
