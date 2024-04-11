package org.fleetopgroup.controller;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VehicleGPSMobileRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private IVehicleGPSDetailsService			vehicleGPSDetailsService;
	
	@Autowired VehicleRepository	vehicleRepository;
	
	
	@RequestMapping(value="/getVehicleGPSDataAtTimeOnMob", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleGPSDataAtTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		
		try {
			System.out.println("innn");
			object 				= new ValueObject(allRequestParams);
			return vehicleGPSDetailsService.getVehicleGPSDataAtSpecifiedTime(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			System.err.println("Exception "+e);
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/vehicleListforTataGRP",produces="application/json", method = RequestMethod.POST)
	public HashMap<Object,Object> vehicleListforTataGRP(@RequestParam HashMap<Object,Object> allRequestParams) throws Exception {
		
		HashMap<Object,Object> map = new HashMap<>();
		List<Vehicle> vehicleList = null;
		
			try {
				 Integer gpsVendorId = Integer.parseInt(allRequestParams.get("gpsVendorId")+"");
				 Integer companyId = Integer.parseInt(allRequestParams.get("companyId")+"");
				
				vehicleList = vehicleRepository.getVehicleListByGpsVendor(gpsVendorId,companyId);
				
				map.put("vehicleList", vehicleList);
	
			} catch (Exception e) {
				// TODO: handle exception
			}
	    return map;
	}

}
