/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleTyreModelSubTypeDto;
import org.fleetopgroup.persistence.dto.VehicleTyreModelTypeDto;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubTypeHistory;
import org.fleetopgroup.persistence.model.VehicleTyreModelType;
import org.fleetopgroup.persistence.model.VehicleTyreModelTypeHistory;

/**
 * @author fleetop
 *
 */
public class VehicleTyreModelTypeBL {

	// Save the Job settings types
	public VehicleTyreModelType prepare_VehicleTyreModelType(VehicleTyreModelTypeDto VehicleTyreModelTypeDto) {

		Date     	currentDateUpdate 	= new Date();
		Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		// create obj on driverDoctype
		VehicleTyreModelType model = new VehicleTyreModelType();

		model.setTYRE_MT_ID(VehicleTyreModelTypeDto.getTYRE_MT_ID());
		model.setTYRE_MODEL(VehicleTyreModelTypeDto.getTYRE_MODEL());
		model.setTYRE_MODEL_DESCRITION(VehicleTyreModelTypeDto.getTYRE_MODEL_DESCRITION());
		model.setCREATED_DATE(toDate);
		model.setLASTMODIFIED_DATE(toDate);
		model.setMarkForDelete(false);

		return model;
	}

	// would show the driver List of Document Driver
	public List<VehicleTyreModelTypeDto> Model_Tyre_ListofDto(List<VehicleTyreModelType> VehicleTyreModelType) {

		List<VehicleTyreModelTypeDto> Dtos = null;
		if (VehicleTyreModelType != null && !VehicleTyreModelType.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreModelTypeDto>();

			VehicleTyreModelTypeDto Dto = null;
			for (VehicleTyreModelType DriverDocType : VehicleTyreModelType) {
				Dto = new VehicleTyreModelTypeDto();

				Dto.setTYRE_MT_ID(DriverDocType.getTYRE_MT_ID());
				Dto.setTYRE_MODEL(DriverDocType.getTYRE_MODEL());
				Dto.setTYRE_MODEL_DESCRITION(DriverDocType.getTYRE_MODEL_DESCRITION());
				
				

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public VehicleTyreModelTypeDto prepareJobTypeDto(VehicleTyreModelType TyreType) {

		VehicleTyreModelTypeDto Dto = new VehicleTyreModelTypeDto();

		Dto.setTYRE_MT_ID(TyreType.getTYRE_MT_ID());
		Dto.setTYRE_MODEL(TyreType.getTYRE_MODEL());
		Dto.setTYRE_MODEL_DESCRITION(TyreType.getTYRE_MODEL_DESCRITION());

		return Dto;
	}

	/***************************************************************************************************************	
	*/
	// Save the JobSub settings types
	public VehicleTyreModelSubType prepare_TyreModelSub_Model(VehicleTyreModelSubTypeDto VehicleTyreModelSubTypeDto) {

		Date 		currentDateUpdate 	= new Date();
		Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		// create obj on driverDoctype
		VehicleTyreModelSubType model = new VehicleTyreModelSubType();

		model.setTYRE_MST_ID(VehicleTyreModelSubTypeDto.getTYRE_MST_ID());
		model.setTYRE_MODEL(VehicleTyreModelSubTypeDto.getTYRE_MODEL());
		model.setTYRE_MODEL_SUBTYPE(VehicleTyreModelSubTypeDto.getTYRE_MODEL_SUBTYPE());
		model.setTYRE_MODEL_DESCRITION(VehicleTyreModelSubTypeDto.getTYRE_MODEL_DESCRITION());
		model.setTYRE_MT_ID(VehicleTyreModelSubTypeDto.getTYRE_MT_ID());
		model.setCREATED_DATE(toDate);
		model.setLASTMODIFIED_DATE(toDate);		
		model.setMarkForDelete(false);
	
		model.setWarrantyPeriod(VehicleTyreModelSubTypeDto.getWarrantyPeriod());//devy
		model.setWarrantyTypeId(VehicleTyreModelSubTypeDto.getWarrantyTypeId());//devy
		model.setWarrentyterm(VehicleTyreModelSubTypeDto.getWarrentyterm());//devy
		model.setTyreModelTypeId(VehicleTyreModelSubTypeDto.getTyreModelTypeId());
		model.setGauageMeasurementLine(VehicleTyreModelSubTypeDto.getGauageMeasurementLine());
		model.setTyreGauge(VehicleTyreModelSubTypeDto.getTyreGauge());
		model.setTyreTubeTypeId(VehicleTyreModelSubTypeDto.getTyreTubeTypeId());
		model.setPly(VehicleTyreModelSubTypeDto.getPly());
		model.setPsi(VehicleTyreModelSubTypeDto.getPsi());
		model.setTyreModelSizeId(VehicleTyreModelSubTypeDto.getTyreModelSizeId());
		return model;
		
		
	}

	// would show the Job Sub List of Document Driver
	public List<VehicleTyreModelSubType> TyreModelSub_ListofDto(List<VehicleTyreModelSubType> VehicleTyreModelType) {

		List<VehicleTyreModelSubType> Dtos = null;
		if (VehicleTyreModelType != null && !VehicleTyreModelType.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreModelSubType>();

			VehicleTyreModelSubType DocType = null;

			for (VehicleTyreModelSubType ModelSubTypeDto : VehicleTyreModelType) {

				DocType = new VehicleTyreModelSubType();

				DocType.setTYRE_MST_ID(ModelSubTypeDto.getTYRE_MST_ID());
				DocType.setTYRE_MODEL(ModelSubTypeDto.getTYRE_MODEL());
				DocType.setTYRE_MODEL_SUBTYPE(ModelSubTypeDto.getTYRE_MODEL_SUBTYPE());
				DocType.setTYRE_MODEL_DESCRITION(ModelSubTypeDto.getTYRE_MODEL_DESCRITION());
				

				Dtos.add(DocType);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public VehicleTyreModelSubType prepare_TyreModelSubTypeDto(VehicleTyreModelSubTypeDto ModelSubTypeDto) {
		Date 		currentDateUpdate 	= new Date();
		Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		VehicleTyreModelSubType DocType = new VehicleTyreModelSubType();

		DocType.setTYRE_MST_ID(ModelSubTypeDto.getTYRE_MST_ID());
		DocType.setTYRE_MT_ID(ModelSubTypeDto.getTYRE_MT_ID());
		DocType.setTYRE_MODEL(ModelSubTypeDto.getTYRE_MODEL());
		DocType.setTYRE_MODEL_SUBTYPE(ModelSubTypeDto.getTYRE_MODEL_SUBTYPE());
		DocType.setTYRE_MODEL_DESCRITION(ModelSubTypeDto.getTYRE_MODEL_DESCRITION());
		DocType.setWarrantyPeriod(ModelSubTypeDto.getWarrantyPeriod());
		DocType.setWarrantyTypeId(ModelSubTypeDto.getWarrantyTypeId());
		DocType.setWarrentyterm(ModelSubTypeDto.getWarrentyterm());
		DocType.setTyreModelTypeId(ModelSubTypeDto.getTyreModelTypeId());
		DocType.setTyreModelSizeId(ModelSubTypeDto.getTyreModelSizeId());
		DocType.setGauageMeasurementLine(ModelSubTypeDto.getGauageMeasurementLine());
		DocType.setTyreGauge(ModelSubTypeDto.getTyreGauge());
		DocType.setTyreTubeTypeId(ModelSubTypeDto.getTyreTubeTypeId());
		DocType.setPly(ModelSubTypeDto.getPly());
		DocType.setPsi(ModelSubTypeDto.getPsi());
		DocType.setCREATED_DATE(toDate);
		DocType.setLASTMODIFIED_DATE(toDate);		
		DocType.setMarkForDelete(false);
		return DocType;
	
	}

	public VehicleTyreModelSubTypeHistory prepareHistoryModel(VehicleTyreModelSubType vehicleTyreModelSubType) {
		VehicleTyreModelSubTypeHistory  modelSubTypeHistory		= new VehicleTyreModelSubTypeHistory();
		
		modelSubTypeHistory.setCOMPANY_ID(vehicleTyreModelSubType.getCOMPANY_ID());
		modelSubTypeHistory.setLASTMODIFIED_DATE(vehicleTyreModelSubType.getLASTMODIFIED_DATE());
		modelSubTypeHistory.setLASTMODIFIEDBYID(vehicleTyreModelSubType.getLASTMODIFIEDBYID());
		modelSubTypeHistory.setMarkForDelete(vehicleTyreModelSubType.isMarkForDelete());
		modelSubTypeHistory.setTYRE_MODEL(vehicleTyreModelSubType.getTYRE_MODEL());
		modelSubTypeHistory.setTYRE_MODEL_DESCRITION(vehicleTyreModelSubType.getTYRE_MODEL_DESCRITION());
		modelSubTypeHistory.setTYRE_MODEL_SUBTYPE(vehicleTyreModelSubType.getTYRE_MODEL_SUBTYPE());
		modelSubTypeHistory.setTYRE_MST_ID(vehicleTyreModelSubType.getTYRE_MST_ID());
		modelSubTypeHistory.setTYRE_MT_ID(vehicleTyreModelSubType.getTYRE_MT_ID());
		
		return modelSubTypeHistory;
	}

	public VehicleTyreModelTypeHistory prepareVehicleTyreModelTypeHistoryModel(VehicleTyreModelType modelType) {
		VehicleTyreModelTypeHistory		tyreModelTypeHistory	= new VehicleTyreModelTypeHistory();
		
		tyreModelTypeHistory.setCOMPANY_ID(modelType.getCOMPANY_ID());
		tyreModelTypeHistory.setLASTMODIFIED_DATE(modelType.getLASTMODIFIED_DATE());
		tyreModelTypeHistory.setLASTMODIFIEDBYID(modelType.getLASTMODIFIEDBYID());
		tyreModelTypeHistory.setMarkForDelete(modelType.isMarkForDelete());
		tyreModelTypeHistory.setTYRE_MODEL(modelType.getTYRE_MODEL());
		tyreModelTypeHistory.setTYRE_MODEL_DESCRITION(modelType.getTYRE_MODEL_DESCRITION());
		tyreModelTypeHistory.setTYRE_MT_ID(modelType.getTYRE_MT_ID());
		
		return tyreModelTypeHistory;
	}

}
