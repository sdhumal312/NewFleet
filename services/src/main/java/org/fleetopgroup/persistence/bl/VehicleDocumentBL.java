package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.VehicleDocumentDto;

public class VehicleDocumentBL {

	public VehicleDocumentBL() {
	}

	/* get the information Driver Photo */
	public VehicleDocumentDto GetVehicleDocumentPhoto(VehicleDocumentDto vehicleDocument) {

		VehicleDocumentDto vehicleDocumentBean = new VehicleDocumentDto();

		vehicleDocumentBean.setId(vehicleDocument.getId());
		vehicleDocumentBean.setName(vehicleDocument.getName());
		vehicleDocumentBean.setVehid(vehicleDocument.getVehid());
		vehicleDocumentBean.setUploaddate(vehicleDocument.getUploaddate());
		vehicleDocumentBean.setFilename(vehicleDocument.getFilename());
		vehicleDocumentBean.setContent(vehicleDocument.getContent());
		vehicleDocumentBean.setContentType(vehicleDocument.getContentType());

		return vehicleDocumentBean;
	}

	public VehicleDocumentDto prepareVehicleDocumentBean(VehicleDocumentDto photo) {
		VehicleDocumentDto bean = new VehicleDocumentDto();
		bean.setId(photo.getId());
		bean.setVehid(photo.getVehid());
		bean.setName(photo.getName());
		bean.setUploaddate(photo.getUploaddate());
		bean.setDocTypeId(photo.getDocTypeId());

		return bean;
	}

}
