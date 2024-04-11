package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverCommentDao {

public List<DriverCommentDto> getDriverCommentListByDriverId(DriverCommentDto	commentDto) throws Exception;
	
	public List<DriverCommentDto> getDriverCommentListByGid(DriverCommentDto	commentDto) throws Exception;
	
	public List<DriverCommentDto> getDriverCommentListByCompanyId(DriverCommentDto	commentDto) throws Exception;
}
