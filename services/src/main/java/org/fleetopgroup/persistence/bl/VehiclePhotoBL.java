package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.document.VehiclePhoto;

public class VehiclePhotoBL {

	public VehiclePhotoBL() {
	}

	/* get the information Driver Photo */
	public VehiclePhoto GetVehiclePhotoProfile(VehiclePhoto vehiclePhoto) {

		VehiclePhoto vehicleDocumentBean = new VehiclePhoto();

		vehicleDocumentBean.set_id(vehiclePhoto.get_id());
		vehicleDocumentBean.setVehid(vehiclePhoto.getVehid());
		vehicleDocumentBean.setUploaddateOn(vehiclePhoto.getUploaddateOn());
		vehicleDocumentBean.setFilename(vehiclePhoto.getFilename());
		vehicleDocumentBean.setContent(vehiclePhoto.getContent());
		vehicleDocumentBean.setContentType(vehiclePhoto.getContentType());	

		return vehicleDocumentBean;
	}

	/*public VehiclePhoto prepareVehiclePhotoBean(VehiclePhoto photo) {
		VehiclePhoto bean = new VehiclePhoto();
		bean.setId(photo.getId());
		bean.setVehid(photo.getVehid());
		bean.setName(photo.getName());
		bean.setUploaddate(photo.getUploaddate());

		return bean;
	}*/

}
