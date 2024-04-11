package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IVehicleDocumentService {

	public void transferVehicleDocument(List<VehicleDocument> list) throws Exception;
	
	public void saveVehicleDoc(org.fleetopgroup.persistence.document.VehicleDocument vehicleDocument);
	
	public List<org.fleetopgroup.persistence.document.VehicleDocument> listVehicleDocument(Integer vehicleDocument, Integer companyId) throws Exception;
	
	public org.fleetopgroup.persistence.document.VehicleDocument getVehicleDocument(Integer vehicleDocumentId, Integer companyId) throws Exception;
	
	public org.fleetopgroup.persistence.document.VehicleDocument getVehicleDocument(int vehicle_id) throws Exception;
	
	public void deleteVehicleDocument(int vehicle_id, Integer companyId) throws Exception;

	public long getVehicleDocumentCount(int vehicle_id, Integer companyId) throws Exception;
	
	public long getVehiclePhotoCount(int vehicle_id, Integer companyId) throws Exception;
	
	public ValueObject saveVehicleDocumentDetails(ValueObject valueObject, MultipartFile file) throws Exception;
	
	public ValueObject getDocumentList(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleDocumentById(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteVehicleDocumentById(ValueObject valueObject) throws Exception;
}
