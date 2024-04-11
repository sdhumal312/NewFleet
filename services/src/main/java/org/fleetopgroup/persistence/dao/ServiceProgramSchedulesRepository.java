package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceProgramSchedulesRepository extends JpaRepository<ServiceProgramSchedules, Long> {

	@Modifying
	@Query("UPDATE ServiceProgramSchedules SET markForDelete = 1 where serviceScheduleId = ?1")
	public void deleteServiceSchedule(Long id) throws Exception;
	
	@Query("From ServiceProgramSchedules where vehicleServiceProgramId = ?1 and markForDelete = 0")
	public List<ServiceProgramSchedules>  getServiceProgramSchedulesListByProgramId(Long vehicleServiceProgramId) throws Exception;
	
	@Modifying
	@Query("UPDATE ServiceProgramSchedules SET markForDelete = 1 where vehicleServiceProgramId = ?1")
	public void deleteServiceScheduleByProgramId(Long id) throws Exception;
	
	@Query("From ServiceProgramSchedules where vehicleServiceProgramId = ?1 AND jobSubTypeId = ?2 AND markForDelete = 0")
	public List<ServiceProgramSchedules>  validateServiceSchedule(Long vehicleServiceProgramId, Integer jobSubTypeId) throws Exception;
	
	
	
}
