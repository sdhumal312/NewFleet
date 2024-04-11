package com.fleetop.gpsservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fleetop.gpsservice.model.VehicleGpsData;

public interface VehicleGpsDataRepository  extends JpaRepository<VehicleGpsData, Long>  {
	
	@Query(nativeQuery=true,value="SELECT * FROM VehicleGpsData WHERE vid =?1 AND company_Id =?2 AND mark_For_Delete = 0 AND event_date_time <= ?3 AND event_date_time >= ?4 order by event_date_time ASC")
	public List<VehicleGpsData> getInBetweenGpsData(int vid,int companyId,Date startDate,Date endDate);

}
