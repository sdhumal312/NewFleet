package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCommentDao {

	public List<VehicleCommentDto> getVehicleCommentListByVid(VehicleCommentDto	commentDto) throws Exception;
	
	public List<VehicleCommentDto> getVehicleCommentListByGid(VehicleCommentDto	commentDto) throws Exception;
	
	public List<VehicleCommentDto> getVehicleCommentListByCompanyId(VehicleCommentDto	commentDto) throws Exception;
}
