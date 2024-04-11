package org.fleetopgroup.persistence.model;

import java.util.Arrays;

/**
 * @author fleetop
 *
 *
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "file_upload")
public class UploadFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
	private long id;

	@Column(name = "photoname")
	private String photoname;

	@Column(name = "photodate")
	private String photodate;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Lob
	@Column(name = "srs_data", nullable = false, columnDefinition = "mediumblob")
	private byte[] data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getPhotoname() {
		return photoname;
	}

	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public String getPhotodate() {
		return photodate;
	}

	public void setPhotodate(String photodate) {
		this.photodate = photodate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("UploadFile [photoname=");
		builder.append(photoname);
		builder.append(", photodate=");
		builder.append(photodate);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", data=");
		builder.append(data != null ? Arrays.toString(Arrays.copyOf(data, Math.min(data.length, maxLen))) : null);
		builder.append("]");
		return builder.toString();
	}

	
}
