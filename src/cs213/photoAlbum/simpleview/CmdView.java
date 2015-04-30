package cs213.photoAlbum.simpleview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.IBackend;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;

/**
 * @author 1
 *
 */

public class CmdView {

	static ArrayList<String> validArgs = new ArrayList<String>() {
		{
			add("listusers");
			add("adduser");
			add("deleteuser");
			add("login");
		}
	};

	public static void main(String[] args) {

		IBackend backend = new Backend();
		backend = backend.readSessionData();
		IControl control = new Control(backend);
		
		if(args.length == 0)
		{
			System.out.println("Error: No command entered, exiting...");
			return;
		}
		
		if(!validArgs.contains(args[0]))
		{
			System.out.println("Error: Invalid command, exiting...");
			return;
		}
		
		switch (args[0])
		{
			case "listusers":
				System.out.println("Listing all existing users: ");
				if(backend.getUsers().size() > 0)
				{
					for(User u: backend.getUsers().values())
					{
						System.out.println(u.getUserId());
					}
				}
				else
				{
					System.out.println("Error: No users exist.");
				}
				break;
				
			case "adduser":
				if(args.length == 1)
				{
					System.out.println("Error: Please input the user id and name, try again...");
				}
				
				else if(args.length == 2)
				{
					System.out.println("Error: Please input the name, try again...");
				}
				
				else
				{
					if(control.addUser(args[1], args[2]))
					{
						 System.out.println("Created user "+ args[1] +" with name "+ args[2]);
					}
					else
					{
						System.out.println("Error: User "+ args[1] +" already exists with name "+ control.getUser(args[1]).getUserFullName());
					}
				}				
				break;
				
			case "deleteuser":
				if(args.length == 1)
				{
					System.out.println("Error: Please input the user id, try again...");
				}
				else
				{
					if(control.deleteUser(args[1]))
					{
						 System.out.println("Deleted user "+ args[1]);
					}
					else
					{
						System.out.println("Error: User "+ args[1] +" does not exist...");
					}
				}
				
				break;
				
			case "login":
				if(args.length == 1)
				{
					System.out.println("Error: Please input the user id, try again...");
				}
				else
				{
					if(control.login(args[1]))
					{
						 System.out.println("Logged in as "+ args[1]);
						 System.out.println("Entering interactive mode.");
						 interactiveMode(control, backend);
					}
					else
					{
						System.out.println("Error: User "+ args[1] +" does not exist...");
					}
				}
				
				break;
		}
		
		//After we finish doing stuff, write all changes we made to the disk
		try {
			backend.writeData(backend);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving session to disk!");
		}
	}
	
	static ArrayList<String> validArgsInteractiveMode = new ArrayList<String>() {
		{
			add("logout");
			add("createAlbum");
			add("listAlbums");
			add("listPhotos");
			add("deleteAlbum");
			add("addPhoto");
			add("movePhoto");
			add("removePhoto");
			add("addTag");
			add("deleteTag");
			add("listPhotoInfo");
			add("getPhotosByDate");
			add("getPhotosByTag");		
		}
	};
	
	public static void interactiveMode(IControl control, IBackend backend)
	{
		String command;
		Scanner in = new Scanner(System.in);

		ArrayList<String> tokens = new ArrayList<String>();
		
		while(true)
		{
			//loop until a valid command is entered
			do{
				System.out.println("Enter a command: ");
				command = in.nextLine();
				
				if(command.isEmpty())
				{
					System.out.println("Error: No command entered, try again...");
				}
				
				else
				{
					String regex;
					
					if(command.contains("\"")&& !command.contains(":")){
						regex = "\"";
					}
					else{
						regex = " ";
					}
					
					String[] parts = command.split(regex);		
					tokens = new ArrayList<String>();

					for(String s: parts)
					{
						if(s.trim().length() > 0)
						{
							tokens.add(s.trim());
						}
					}
					
					command = tokens.get(0);

					if(!validArgsInteractiveMode.contains(command))
					{
						System.out.println("Error: Invalid command, try again...");
					}
				}
			}
			while(command.isEmpty() || !validArgsInteractiveMode.contains(command));
			
			//if the logout command was entered, break out of the infinite loop and save any changes that were made
			if(command.equals("logout"))
			{
				System.out.println("Logged out...");
				break;
			}
			
			//a valid command was entered, do stuff
			switch(command)
			{
				//working
				case "listAlbums":
				{
					if(control.listAlbums().size() == 0)
					{
						System.out.println("No albums exist for user " + control.getActiveUser().getUserId() + ":");
						break;
					}
					
					for(Album a: control.listAlbums())
					{
						//print <name> number of photos: <numberOfPhotos>, <start date> - <end date> 
						System.out.println(a.getAlbumName()+ " number of photos: " + a.getPhotos().size() + ", " 
						+ a.getStartDate() + " - " + a.getEndDate());
					}
					break;
				}	
				
				//this works
				case "createAlbum":
				{
					if(tokens.size() < 2)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String albumName = tokens.get(1);
					
					if(control.createAlbum(albumName))
					{
						System.out.println("Created album for user " + control.getActiveUser().getUserId() + ":");
						System.out.println(albumName);
					}
					
					else{
						System.out.println("Album exists for user "+control.getActiveUser().getUserId() + ":");
						System.out.println(albumName);
					}
					break;
				}
				
				//working
				case "deleteAlbum":
				{
					if(tokens.size() < 2)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String albumName = tokens.get(1);
					
					if(control.deleteAlbum(albumName))
					{
						System.out.println("Deleted album from user " + control.getActiveUser().getUserId() + ":");
						System.out.println(albumName);
					}
					
					else{
						System.out.println("Album does not exist for user "+control.getActiveUser().getUserId() + ":");
						System.out.println(albumName);
					}
					break;
				}
				
				//this works
				case "listPhotos":
				{
					if(tokens.size() < 2)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String albumName = tokens.get(1);
					
					if(control.getPhotosByAlbum(albumName) == null)
					{
						System.out.println("Error: Album " + albumName + " does not exist.");
					}
					
					else{
						if(control.getPhotosByAlbum(albumName).size() == 0)
						{
							System.out.println("No photos found in " + albumName);
						}
						else{
							System.out.println("Photos for album "+ albumName + ":"); 
							
							for(Photo p: control.getPhotosByAlbum(albumName))
							{
								System.out.println(p.getFilePath() + " " + p.getDate());
							}
						}
					}
					
					break;
				}
				
				
				case "addPhoto":
				{
					/*
					 * addPhoto "<fileName>" "<caption>" "<albumName>"
Output:
Added photo <fileName>:
<caption> - Album: <albumName> working
Or:
Photo <fileName> already exists in album <albumName> working
Or:
Album <albumName> does not exist working
Or:
File <fileName> does not exist <- need to implement this
					 */
					if(tokens.size() < 4)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String fileName = tokens.get(1);
					String caption = tokens.get(2);
					String albumName = tokens.get(3);
					
					try{
						if(control.addPhoto(fileName, caption, albumName))
						{
							System.out.println("Added photo " + fileName);
							System.out.println(control.getPhoto(fileName).getCaption() + " - Album: " + albumName);
						}
						else
						{
							System.out.println("Photo " + fileName + " already exists in album " + albumName);
						}
					}
					catch (NullPointerException e)
					{
						System.out.println("Album " + albumName + " does not exist");
					} 
					catch (FileNotFoundException e) {
						System.out.println("File " + fileName + " does not exist.");
					}
					
					break;
				}
				
				//working
				case "movePhoto":
				{
					/*
					 * movePhoto "<fileName>" "<oldAlbumName>" "<newAlbumName>"
Output:
Moved photo <fileName>:
<fileName> - From album <oldAlbumName> to album <newAlbumName>
Or:
Album <albumName> does not exist
Or:
Photo <fileName> does not exist in <oldAlbumName>
					 */
					if(tokens.size() < 4)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					try{
						if(control.movePhoto(tokens.get(1), tokens.get(2), tokens.get(3))){
							System.out.println("Moved photo " + tokens.get(1) + ":");
							System.out.println(tokens.get(1) + " - " + "From album " + tokens.get(2) + " to album " + tokens.get(3));
						}
						else
						{
							System.out.println("Photo " + tokens.get(1) + " does not exist in " + tokens.get(2));
						}
					}
					catch(NullPointerException e)
					{
						if(control.getActiveUser().getAlbum(tokens.get(2)) == null)
						{
							System.out.println("Album " + tokens.get(2) + " does not exist");
						}
						else if(control.getActiveUser().getAlbum(tokens.get(3)) == null)
						{
							System.out.println("Album " + tokens.get(3) + " does not exist");
						}
					}
					catch(IllegalArgumentException e)
					{
						//the photo already exists in the new album
					}
					break;
				}
				
				//working
				case "removePhoto":
				{
					/*
					 * To remove a photo from an album:
removePhoto "<fileName>" "<albumName>"
Output:
Removed photo:
<fileName> - From album <albumName>
Or:
Album <albumName> does not exist
Or:
Photo <fileName> is not in album <albumName>
					 */
					if(tokens.size() < 3)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String fileName = tokens.get(1);
					String albumName = tokens.get(2);
					
					try
					{
						if(control.removePhoto(fileName, albumName))
						{
							System.out.println("Removed photo:");
							System.out.println(fileName + " - From album " + albumName);
						}
						else
						{
							System.out.println("Photo " + fileName + " does not exist in " + albumName);
						}
					}
					catch(NullPointerException e)
					{
						System.out.println("Album " + albumName + " does not exist");
					}
					
					break;
				}
				
				//working
				case "addTag":
				{
					/*
					 * addTag "<fileName>" <tagType>:"<tagValue>"
Output:
Added tag:
<fileName> <tagType>:<tagValue>
Or:
Photo <fileName> does not exist
Or:
Tag already exists for <fileName> <tagType>:<tagValue>
					 */
					if(tokens.size() < 3)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String fileName3 = tokens.get(1).replace("\"", "").replace("<", "").replace(">", "");
					String[] tag = tokens.get(2).replace("\"", "").replace("<", "").replace(">", "").split(":");
					
					if(tag.length < 2)
					{
						System.out.println("Error: Improperly formatted tag...");
						break;
					}
					
					try
					{
						String tagType = tag[0];
						String tagValue = tag[1];
						
						if(control.addTag(fileName3, tagType, tagValue))
						{
							System.out.println("Added tag:");
							System.out.println(fileName3 + " " + tagType + ":" + tagValue);
						}
						else
						{
							System.out.println("Tag already exists for " + fileName3 + " " + tagType + ":" + tagValue);
						}	
					}
					catch(NullPointerException e)
					{
						System.out.println("Photo " + fileName3 + " does not exist");
					}
					
					break;
				}
					
				//working
				case "deleteTag":
				{
					/*
					 * deleteTag "<fileName>" <tagType>:"<tagValue>"
Output:
Deleted tag:
<fileName> <tagType>:<tagValue>
Or:
Photo <fileName> does not exist
Or:
Tag does not exist for <fileName> <tagType>:<tagValue>
					 */
					if(tokens.size() < 3)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String fileName = tokens.get(1).replace("\"", "").replace("<", "").replace(">", "");
					String[] tag = tokens.get(2).replace("\"", "").replace("<", "").replace(">", "").split(":");
					
					if(tag.length < 2)
					{
						System.out.println("Error: Improperly formatted tag...");
						break;
					}
					
					try
					{
						String tagType = tag[0];
						String tagValue = tag[1];
						
						if(control.deleteTag(fileName, tagType, tagValue))
						{
							System.out.println("Deleted tag:");
							System.out.println(fileName + " " + tagType + ":" + tagValue);
						}
						else
						{
							System.out.println("Tag does not exist for " + fileName + " " + tagType + ":" + tagValue);
						}	
					}
					catch(NullPointerException e)
					{
						System.out.println("Photo " + fileName + " does not exist");
					}
					break;
				}
				
				//working
				case "listPhotoInfo":
				{
					if(tokens.size() < 2)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					String fileName = tokens.get(1);
					Photo p = control.getPhoto(fileName);
					
					if(p == null)
					{
						System.out.println("Photo " + fileName +  " does not exist");
						break;
					}
					
					System.out.println("Photo file name: " + fileName);
					
					System.out.print("Album: ");
					for(Album a: p.getAlbums())
					{
						System.out.print(a.getAlbumName() + ", ");
					}
					System.out.println();
					
					System.out.println("Date: " + p.getDate());
					System.out.println("Caption: " + p.getCaption());
					System.out.println("Tags:");

					for(Tag t: p.getTags())
					{
						if(t != null){
							System.out.println(t.getType() + ":" + t.getValue());
						}
					}
					
					break;
				}
					
				//working
				case "getPhotosByDate":
				{
					if(tokens.size() < 3)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					
					try
					{
						//Photos for user <user id> in range <start date> to <end date>:
						//<caption> - Album: <albumName>[,<albumName>]... - Date: <date> 
						
						String startDate = tokens.get(1);
						String endDate = tokens.get(2);
						
						System.out.println("Photos for user " + control.getActiveUser().getUserId() + 
								" in range " + startDate + " to " + endDate + ":");
						
						if(control.getPhotosByDate(startDate, endDate).isEmpty()){
							System.out.println("No photos found in range.");
						}
						
						else{
							for(Photo photo: control.getPhotosByDate(startDate, endDate))
							{
								System.out.print(photo.getCaption() + " ");
								System.out.print("Album: ");
								for(Album a: photo.getAlbums())
								{
								System.out.print(a.getAlbumName() + ", ");
								}
								System.out.println("Date: " + photo.getDate());		
							}	
						}
					}
					catch(Exception e)
					{
						System.out.println("Error parsing dates...");
						System.out.println("Enter dates in: \"MM/dd/yyyy-HH:mm:ss\" format.");
					}
					
					break;
				}
					
				//working
				case "getPhotosByTag":
				{
					if(tokens.size() < 2)
					{
						System.out.println("Error: Invalid amount of arguments.");
						break;
					}
					/*
					 * getPhotosByTag [<tagType>:]"<tagValue>" [,[<tagType>:]"<tagValue>"]...
			Output:
Photos for user <user id> with tags <search string>:
<caption> - Album: <albumName>[,<albumName>]... - Date: <date> 
					 */
					
					ArrayList<Photo> photos = new ArrayList<Photo>();
					ArrayList<Tag> tags = new ArrayList<Tag>();
					
					//parse all tagType:tagValue pairs and add them to the arrayList of tags
					for(int i = 1; i < tokens.size(); i++)
					{
						String[] tag = tokens.get(i).replace("\"", "").replace("<", "").replace(">", "").split(":");
							
						String tagValue = null;
						String tagType = null;
							
						if(tag.length == 1)
						{
							tagValue = tag[0];
						}
						else if(tag.length == 2)
						{
							tagType = tag[0];
							tagValue = tag[1];
						}
						tags.add(new Tag(tagType, tagValue));
					}
					
					try{
						Tag[] t = new Tag[tags.size()];
						tags.toArray(t);
						photos.addAll(control.getPhotosByTag(t));
					}
					catch(NullPointerException e)
					{
						System.out.println("Error: No albums exist.");
					}
						
					System.out.print("Photos for user " + control.getActiveUser().getUserId() + " with tags ");
					for(int i = 1; i < tokens.size(); i++)
					{
						System.out.print(tokens.get(i) + " ");
					}
					System.out.println(":");
						
					if(photos.size() == 0)
					{
						System.out.println("No photos matching the tags above...");
					}
						
					else
					{
						for(Photo p: photos)
						{
							System.out.print(p.getCaption() + " - " + "Album: ");
							for(Album a: p.getAlbums())
							{
								System.out.print(a.getAlbumName() + ", ");
							}
							System.out.println("Date: " + p.getDate());	
						}
					}
					break;
				}
				
				default:
					System.out.println("Error: Invalid command entered...");
					break;
			}
		}
		
		//After we finish doing stuff, write all changes we made to the disk		
		try {
			backend.writeData(backend);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving session to disk!");
		}
	}
}