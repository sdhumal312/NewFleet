package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.document.DriverDocument;
import org.fleetopgroup.persistence.document.DriverPhoto;
import org.fleetopgroup.persistence.dto.DriverDocumentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentHistoryDto;
import org.fleetopgroup.persistence.model.DriverDocumentHistory;


public class DriverDocumentBL {
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	public DriverDocumentBL() {
	}

	/*// get logic in driver Information
	public DriverDocument prepareDocumentupload(DriverDocumentDto driverdocumentBean, MultipartFile file)
			throws IOException {

		DriverDocument driverDocument1 = new DriverDocument();

		driverDocument1.setDriver_id(driverdocumentBean.getDriver_id());
		driverDocument1.setDriver_documentid(driverdocumentBean.getDriver_documentid());

		driverDocument1.setDriver_documentname(driverdocumentBean.getDriver_documentname());

		byte[] bytes = file.getBytes();

		driverDocument1.setDriver_filename(file.getOriginalFilename());
		driverDocument1.setDriver_content(bytes);
		driverDocument1.setDriver_contentType(file.getContentType());

		// get current date format of
		String timestamp = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date());

		driverDocument1.setDriver_uploaddate(timestamp);

		return driverDocument1;
	}*/

	/* get information the driver Document Reviser */
	// get logic in driver Information
	/*public DriverDocument prepareDocumentRevise(DriverDocumentDto driverDocumentBean, MultipartFile file)
			throws IOException {

		DriverDocument driverDocument1 = new DriverDocument();

		driverDocument1.setDriver_documentid(driverDocumentBean.getDriver_documentid());
		driverDocument1.setDriver_id(driverDocumentBean.getDriver_id());
		driverDocument1.setDriver_documentname(driverDocumentBean.getDriver_documentname());
		driverDocument1.setDriver_uploaddate(driverDocumentBean.getDriver_uploaddate());

		byte[] bytes = file.getBytes();

		driverDocument1.setDriver_filename(file.getOriginalFilename());
		driverDocument1.setDriver_content(bytes);
		driverDocument1.setDriver_contentType(file.getContentType());

		// get current date format of
		String timestamp = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date());

		driverDocument1.setDriver_revdate(timestamp);

		return driverDocument1;
	}*/

	/* List of showing driverDocument details */
	public List<DriverDocumentDto> prepareListDriverDocument(List<DriverDocument> driverDocument) {
		List<DriverDocumentDto> beans = null;
		if (driverDocument != null && !driverDocument.isEmpty()) {
			beans = new ArrayList<DriverDocumentDto>();
			DriverDocumentDto driver = null;
			for (DriverDocument driverBean : driverDocument) {
				driver = new DriverDocumentDto();

				driver.setDriver_id(driverBean.getDriver_id());
				driver.setDriver_documentid(driverBean.getDriver_documentid());

				driver.setDriver_documentname(driverBean.getDriver_documentname());
				driver.setDriver_filename(driverBean.getDriver_filename());

				/*driver.setDriver_uploaddate(driverBean.getDriver_uploaddate());
				driver.setDriver_revdate(driverBean.getDriver_revdate());
				driver.setDriver_docFrom(driverBean.getDriver_docFrom());
				driver.setDriver_docTo(driverBean.getDriver_docTo());*/

				beans.add(driver);
			}
		}
		return beans;
	}

	/* get the information Driver Revise Document */
	public DriverDocumentDto GetDriverDocument(DriverDocument driverDocument) {

		DriverDocumentDto driverDocumentBean = new DriverDocumentDto();

		driverDocumentBean.setDriver_documentid(driverDocument.getDriver_documentid());
		driverDocumentBean.setDriver_documentname(driverDocument.getDriver_documentname());
		driverDocumentBean.setDriver_id(driverDocument.getDriver_id());
		if(driverDocument.getDriver_docFromDate() != null)
			driverDocumentBean.setDriver_docFrom(CreatedDateTime.format(driverDocument.getDriver_docFromDate()));
		if(driverDocument.getDriver_docToDate() != null)
			driverDocumentBean.setDriver_docTo(CreatedDateTime.format(driverDocument.getDriver_docToDate()));

		return driverDocumentBean;
	}

	/* get the information Driver Document History */
	public DriverDocumentHistoryDto GetDriverDocHis(DriverDocumentHistory driverDocument) {

		DriverDocumentHistoryDto driverDocHisBean = new DriverDocumentHistoryDto();

		driverDocHisBean.setDriver_doHisid(driverDocument.getDriver_doHisid());
		driverDocHisBean.setDriver_docHisname(driverDocument.getDriver_docHisname());
		driverDocHisBean.setDriver_id(driverDocument.getDriver_id());

		return driverDocHisBean;
	}

	/* List of showing driverDocument History details */
	public List<DriverDocumentHistoryDto> prepareListDriverDocHis(List<DriverDocumentHistoryDto> driverDocumentHistory) {

		List<DriverDocumentHistoryDto> beans = null;
		if (driverDocumentHistory != null && !driverDocumentHistory.isEmpty()) {

			beans = new ArrayList<DriverDocumentHistoryDto>();
			DriverDocumentHistoryDto driver = null;
			for (DriverDocumentHistoryDto driverBean : driverDocumentHistory) {
				driver = new DriverDocumentHistoryDto();

				driver.setDriver_id(driverBean.getDriver_id());
				driver.setDriver_doHisid(driverBean.getDriver_doHisid());

				driver.setDriver_docHisname(driverBean.getDriver_docHisname());
				driver.setDriver_filename(driverBean.getDriver_filename());

				driver.setDriver_uploaddate(driverBean.getDriver_uploaddate());
				driver.setDriver_docHisFrom(driverBean.getDriver_docHisFrom());
				driver.setDriver_docHisTo(driverBean.getDriver_docHisTo());
				driver.setDriver_uploaddate(driverBean.getDriver_uploaddate());

				beans.add(driver);
			}
		}
		return beans;
	}

	/* get the information Driver Comment 
	public DriverCommentDto GetDriverComment(DriverComment driverComment) {

		DriverCommentDto driverCommentBean = new DriverCommentDto();

		driverCommentBean.setDriver_commentid(driverComment.getDriver_commentid());
		driverCommentBean.setDriver_title(driverComment.getDriver_title());
		driverCommentBean.setDriver_comment(driverComment.getDriver_comment());
		driverCommentBean.setDriver_id(driverComment.getDriver_id());
		driverCommentBean.setDriver_title(driverComment.getDriver_title());
		driverCommentBean.setCreated(driverComment.getCreated());
		driverCommentBean.setCreatedBy(driverComment.getCreatedBy());

		return driverCommentBean;
	}*/

	/* List of showing driver Photo details */
	public List<DriverPhoto> prepareListDriverPhoto(List<DriverPhoto> driverPhoto) {
		List<DriverPhoto> beans = null;
		if (driverPhoto != null && !driverPhoto.isEmpty()) {
			beans = new ArrayList<>();
			DriverPhoto driver = null;
			SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
			for (DriverPhoto driverBean : driverPhoto) {
				driver = new DriverPhoto();

				driver.setDriver_id(driverBean.getDriver_id());
				driver.set_id(driverBean.get_id());

				driver.setDriver_photoname(driverBean.getDriver_photoname());
				driver.setDriver_filename(driverBean.getDriver_filename());

				driver.setDriver_uploaddate(CreatedDateTime.format(driverBean.getUploaddate()));

				beans.add(driver);
			}
		}
		return beans;
	}

	/* get the information Driver Photo */
	public DriverPhoto GetDriverPhoto(DriverPhoto driverPhoto) {

		DriverPhoto driverPhotoBean = new DriverPhoto();

		driverPhotoBean.set_id(driverPhoto.get_id());
		driverPhotoBean.setDriver_photoname(driverPhoto.getDriver_photoname());
		driverPhotoBean.setDriver_id(driverPhoto.getDriver_id());
		driverPhotoBean.setDriver_uploaddate(driverPhoto.getDriver_uploaddate());

		return driverPhotoBean;
	}

}
