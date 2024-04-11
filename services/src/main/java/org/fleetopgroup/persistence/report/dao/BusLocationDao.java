/**
 * 
 */
package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.BusLocationDto;
import org.fleetopgroup.persistence.model.BusLocation;
import org.springframework.stereotype.Repository;

/**
 * @author SANJU
 *
 */
@Repository
public interface BusLocationDao {

	boolean checkVehicleStatus(BusLocation busLocation) throws Exception;

	public List<BusLocationDto> getBusLocationReportList(String Query, Integer companyId) throws Exception;

}
