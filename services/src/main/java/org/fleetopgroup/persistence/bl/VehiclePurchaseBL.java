package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.VehiclePurchaseDto;

public class VehiclePurchaseBL {

	public VehiclePurchaseBL() {
	}

	/* get the information Driver Photo */
	public VehiclePurchaseDto GetVehiclePurchase(VehiclePurchaseDto vehiclePurchase) {

		VehiclePurchaseDto vehiclePurchaseBean = new VehiclePurchaseDto();

		vehiclePurchaseBean.setId(vehiclePurchase.getId());
		vehiclePurchaseBean.setName(vehiclePurchase.getName());
		vehiclePurchaseBean.setVehid(vehiclePurchase.getVehid());
		vehiclePurchaseBean.setUploaddate(vehiclePurchase.getUploaddate());
		vehiclePurchaseBean.setFilename(vehiclePurchase.getFilename());
		vehiclePurchaseBean.setContent(vehiclePurchase.getContent());
		vehiclePurchaseBean.setContentType(vehiclePurchase.getContentType());

		return vehiclePurchaseBean;
	}

	public VehiclePurchaseDto prepareVehiclePurchaseBean(VehiclePurchaseDto photo) {
		VehiclePurchaseDto bean = new VehiclePurchaseDto();
		bean.setId(photo.getId());
		bean.setVehid(photo.getVehid());
		bean.setName(photo.getName());
		bean.setUploaddate(photo.getUploaddate());
		bean.setvPurchaseTypeId(photo.getvPurchaseTypeId());

		return bean;
	}

}
