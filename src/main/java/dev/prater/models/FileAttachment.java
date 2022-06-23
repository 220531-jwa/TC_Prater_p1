package dev.prater.models;

import java.time.LocalDateTime;

public class FileAttachment {
	int id;
	int reqID;
	String name;
	String extension; 
	String path;
	LocalDateTime uploadDate;
	
	public int getId() {return id;}
	public int getReqID() {return reqID;}
	public String getName() {return name;}
	public String getExtension() {return extension;}
	public String getPath() {return path;}
	public LocalDateTime getUploadDate() {return uploadDate;}
	
	public void setId(int id) {this.id = id;}
	public void setReqID (int reqID) {this.reqID = reqID;}
	public void setName(String name) {this.name = name;}
	public void setExtension(String extension) {this.extension = extension;}
	public void setPath(String path) {this.path = path;}
	public void setUploadDate(LocalDateTime uploadDate) {this.uploadDate = uploadDate;}
	
	@Override
	public String toString() 
	{
		return "FileAttachment [id=" + id + ", reqID=" + reqID + ", name=" + name + ", extension=" + extension + ", path=" + path
				+ ", uploadDate=" + uploadDate + "]";
	}
}
