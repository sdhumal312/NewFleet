package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.TripRouteDto;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripRouteHistory;

public class TripRouteBL {
	public TripRouteBL() {
	}

	// save the TripRoute Model
	public TripRoute prepareModel(TripRouteDto TripRouteBean, boolean subRouteTypeNeeded) {
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		TripRoute status = new TripRoute();
		
		status.setRouteID(TripRouteBean.getRouteID());
		status.setRouteType(TripRouteBean.getRouteType());
		status.setRouteName(TripRouteBean.getRouteName());
		status.setRouteNo(TripRouteBean.getRouteNo());
		status.setRouteApproximateKM(TripRouteBean.getRouteApproximateKM());
		status.setRouteTimeFrom(TripRouteBean.getRouteTimeFrom());
		status.setRouteTimeTo(TripRouteBean.getRouteTimeTo());
		status.setRouteTotalHour(TripRouteBean.getRouteTotalHour());
		status.setRouteTotalLiter(TripRouteBean.getRouteTotalLiter());
		status.setRoutrAttendance(TripRouteBean.getRoutrAttendance());
		status.setRouteRemarks(TripRouteBean.getRouteRemarks());
		status.setVehicleGroupId(TripRouteBean.getVehicleGroupId());
		if(subRouteTypeNeeded) {
			if(TripRouteBean.getRouteType() == Integer.parseInt(TripRouteDto.ROUTE_TYPE_SUB_ROUTE+""))
				status.setMainRouteId(TripRouteBean.getMainRouteId());
		}
		status.setCreated(toDate);
		status.setLastupdated(toDate);

		return status;
	}

	// save the TripRoute Model
	public TripRoute prepareUpdate(TripRouteDto TripRouteBean, boolean subRouteTypeNeeded) {
		java.util.Date 	currentDateUpdate 	= new java.util.Date();
		Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());

		TripRoute status = new TripRoute();
		status.setRouteID(TripRouteBean.getRouteID());
		status.setRouteNumber(TripRouteBean.getRouteNumber());
		status.setRouteName(TripRouteBean.getRouteName());
		status.setRouteNo(TripRouteBean.getRouteNo());
		status.setRouteApproximateKM(TripRouteBean.getRouteApproximateKM());
		status.setRouteTimeFrom(TripRouteBean.getRouteTimeFrom());
		status.setRouteTimeTo(TripRouteBean.getRouteTimeTo());
		status.setRouteTotalHour(TripRouteBean.getRouteTotalHour());
		status.setRouteTotalLiter(TripRouteBean.getRouteTotalLiter());
		status.setRoutrAttendance(TripRouteBean.getRoutrAttendance());
		status.setRouteRemarks(TripRouteBean.getRouteRemarks());
		status.setVehicleGroupId(TripRouteBean.getVehicleGroupId());
		if(subRouteTypeNeeded) {
			if(TripRouteBean.getRouteType() == Integer.parseInt(TripRouteDto.ROUTE_TYPE_SUB_ROUTE+""))
				status.setMainRouteId(TripRouteBean.getMainRouteId());
		}
		status.setLastupdated(toDate);
		status.setRouteType(TripRouteBean.getRouteType());

		return status;
	}

	// show the List Of Vehicle Status
	public List<TripRoute> prepareListofBean(List<TripRoute> TripRoute) {
		List<TripRoute> beans = null;
		if (TripRoute != null && !TripRoute.isEmpty()) {
			beans = new ArrayList<TripRoute>();
			TripRoute bean = null;
			for (TripRoute Route : TripRoute) {
				bean = new TripRoute();
				bean.setRouteID(Route.getRouteID());
				
				bean.setRouteType(Route.getRouteType());
				bean.setRouteName(Route.getRouteName());
				
				bean.setRouteNo(Route.getRouteNo());
				bean.setRouteApproximateKM(Route.getRouteApproximateKM());
				bean.setRouteRemarks(Route.getRouteRemarks());
				bean.setCompanyId(Route.getCompanyId());

				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show TripRouteBean
	public TripRoute prepareTripRouteBean(TripRouteDto Route) {
		TripRoute bean = new TripRoute();
		bean.setRouteID(Route.getRouteID());
		bean.setRouteName(Route.getRouteName());
		bean.setRouteNo(Route.getRouteNo());
		bean.setRouteApproximateKM(Route.getRouteApproximateKM());

		bean.setRouteTimeFrom(Route.getRouteTimeFrom());
		bean.setRouteTimeTo(Route.getRouteTimeTo());
		bean.setRouteTotalHour(Route.getRouteTotalHour());
		bean.setRouteTotalLiter(Route.getRouteTotalLiter());
		bean.setRoutrAttendance(Route.getRoutrAttendance());

		bean.setRouteRemarks(Route.getRouteRemarks());

		// bean.setCreated(Route.getCreated());

		return bean;
	}

	// edit Show TripRouteBean
	public TripRoute prepareTripRouteBean(TripRoute Route) {
		TripRoute bean = new TripRoute();
		bean.setRouteID(Route.getRouteID());
		
		bean.setRouteType(Route.getRouteType());
		bean.setRouteNumber(Route.getRouteNumber());
		bean.setRouteName(Route.getRouteName());
		
		bean.setRouteNo(Route.getRouteNo());
		bean.setRouteApproximateKM(Route.getRouteApproximateKM());

		bean.setRouteTimeFrom(Route.getRouteTimeFrom());
		bean.setRouteTimeTo(Route.getRouteTimeTo());
		bean.setRouteTotalHour(Route.getRouteTotalHour());
		bean.setRouteTotalLiter(Route.getRouteTotalLiter());
		bean.setRoutrAttendance(Route.getRoutrAttendance());
		bean.setVehicleGroupId(Route.getVehicleGroupId());
		bean.setRouteRemarks(Route.getRouteRemarks());

		// bean.setCreated(Route.getCreated());

		return bean;
	}

	public TripRouteHistory prepareHistoryModel(TripRoute route) {
		TripRouteHistory	tripRouteHistory	= new TripRouteHistory();
		
		tripRouteHistory.setCompanyId(route.getCompanyId());
		tripRouteHistory.setLastModifiedById(route.getLastModifiedById());
		tripRouteHistory.setLastupdated(route.getLastupdated());
		tripRouteHistory.setMainRouteId(route.getMainRouteId());
		tripRouteHistory.setMarkForDelete(route.isMarkForDelete());
		tripRouteHistory.setRouteApproximateKM(route.getRouteApproximateKM());
		tripRouteHistory.setRouteID(route.getRouteID());
		tripRouteHistory.setRouteName(route.getRouteName());
		tripRouteHistory.setRouteNo(route.getRouteNo());
		tripRouteHistory.setRouteNumber(route.getRouteNumber());
		tripRouteHistory.setRouteRemarks(route.getRouteRemarks());
		tripRouteHistory.setRouteTimeFrom(route.getRouteTimeFrom());
		tripRouteHistory.setRouteTimeTo(route.getRouteTimeTo());
		tripRouteHistory.setRouteTotalHour(route.getRouteTotalHour());
		tripRouteHistory.setRouteTotalLiter(route.getRouteTotalLiter());
		tripRouteHistory.setRouteType(route.getRouteType());
		tripRouteHistory.setRoutrAttendance(route.getRoutrAttendance());
		tripRouteHistory.setTriproutefixedexpense(route.getTriproutefixedexpense());
		tripRouteHistory.setTriproutefixedincome(route.getTriproutefixedincome());
		tripRouteHistory.setVehicleGroupId(route.getVehicleGroupId());
		
		return tripRouteHistory;
	}
}
