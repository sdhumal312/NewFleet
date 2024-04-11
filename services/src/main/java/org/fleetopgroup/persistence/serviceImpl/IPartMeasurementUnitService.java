package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.PartMeasurementUnitDto;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;


public interface IPartMeasurementUnitService {

	public void addPartMeasurementUnit(PartMeasurementUnit status) throws Exception;

	public void updatePartMeasurementUnit(PartMeasurementUnit status) throws Exception;

	public List<PartMeasurementUnit> listPartMeasurementUnit() throws Exception;
	
	public List<PartMeasurementUnitDto> listPartMeasurementUnitList() throws Exception;

	public PartMeasurementUnit getPartMeasurementUnit(int sid) throws Exception;
	
	public PartMeasurementUnitDto getPartMeasurementUnitDto(int sid) throws Exception;

	public void deletePartMeasurementUnit(Integer status) throws Exception;

	public List<PartMeasurementUnit> listOnlyStatus() throws Exception;
	
	public PartMeasurementUnit ValidatePartMeasurementUnit(String sid) throws Exception;
	
	public Map<Integer, PartMeasurementUnit>  getPartMeasurementUnitHM() throws Exception;
}