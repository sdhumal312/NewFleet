package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PartManufacturer;


public interface IPartManufacturerService {

	public void addPartManufacturer(PartManufacturer status) throws Exception;

	public void updatePartManufacturer(PartManufacturer status) throws Exception;

	public List<PartManufacturer> listPartManufacturer() throws Exception;

	public PartManufacturer getPartManufacturer(int sid, Integer companyId) throws Exception;

	public void deletePartManufacturer(Integer status, Integer companyId) throws Exception;

	public List<PartManufacturer> listOnlyStatus() throws Exception;
	
	public List<PartManufacturer> ValidatePartManufacturerName(String Manufacturer , Integer companyId) throws Exception;
	
	public PartManufacturer ValidateManufacturerName(String Manufacturer , Integer companyId) throws Exception;
	
	public List<PartManufacturer> listPartManufacturerByCompanyId(Integer companyId) throws Exception;
}