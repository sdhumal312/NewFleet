package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleLaundryDetailsDto;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleLaundryDetailsDao {

	List<VehicleLaundryDetailsDto>  getVehicleLaundryDetailsList(Long invoiceId, Long clothTypeId) throws Exception;
}
