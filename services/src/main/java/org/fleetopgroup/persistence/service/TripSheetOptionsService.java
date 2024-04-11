package org.fleetopgroup.persistence.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.TripSheetOptionsRepository;
import org.fleetopgroup.persistence.model.TripSheetOptions;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripSheetOptionsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public  class TripSheetOptionsService implements ITripSheetOptionsService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private TripSheetOptionsRepository tripSheetOptionsRepository;

	


	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	
	
	@Override
	public List<TripSheetOptions> findAllByCompanyId(Integer companyId) throws Exception {
		try {
			return  tripSheetOptionsRepository.findAllByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
/*	@Transactional
	public List<TripSheetOptions> findAll() {

		return TripSheetRepository.findAll();
	}*/
	
	
	@Transactional
	@Override
	public TripSheetOptions findBytripsheetextraname(String tripsheetextraname, Integer companyId) throws Exception {

		return tripSheetOptionsRepository.findBytripsheetextraname(tripsheetextraname, companyId);
	}
	
	
	
	@Transactional
	public TripSheetOptions registerNewTripSheetOpionstype(TripSheetOptions accountDto) throws Exception 
	{
		return tripSheetOptionsRepository.save(accountDto);
		
	}	
	
	@Transactional
	public TripSheetOptions get_TripSheet_Options_Id(Long tripsheetoptionsId, Integer companyId) {

		return tripSheetOptionsRepository.get_TripSheet_Options_Id(tripsheetoptionsId, companyId);
	}
	
	@Transactional
	public void update_TripSheet_Options_Name(String tripsheetextraname, String tripsheetextradescription,  Long lastModifiedById, Date lastupdated, Long tripsheetoptionsId, Integer companyId) throws Exception {

		tripSheetOptionsRepository.update_TripSheet_Options_Name(tripsheetextraname, tripsheetextradescription, lastModifiedById, lastupdated, tripsheetoptionsId, companyId);
	}
	
	@Transactional
	public void delete_Tripsheet_Options(Long tripsheetoptionsId, Integer companyId) {
		
		tripSheetOptionsRepository.delete_Vehicle_TyreSize(tripsheetoptionsId, companyId);
	}
	
	@Override
	public List<TripSheetOptions> listTripExtraOptions(Integer companyId) throws Exception {
		try {
			return tripSheetOptionsRepository.listTripExtraOptions(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}	