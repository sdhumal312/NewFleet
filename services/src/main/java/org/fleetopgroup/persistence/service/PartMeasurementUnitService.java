package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.PartMeasurementUnitRepository;
import org.fleetopgroup.persistence.dto.PartMeasurementUnitDto;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("IPartMeasurementUnitService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartMeasurementUnitService implements IPartMeasurementUnitService {
	
	@PersistenceContext EntityManager entityManager;

	@Autowired
	private PartMeasurementUnitRepository partMeasurementUnitDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartMeasurementUnit(PartMeasurementUnit status) throws Exception {
		partMeasurementUnitDao.save(status);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updatePartMeasurementUnit(PartMeasurementUnit status) throws Exception {
		partMeasurementUnitDao.updatePartMeasurementUnit(status.getPmuName(), status.getPmuSymbol(),
				status.getPmudescription(), status.getPmuid(), status.isNeedConversion(), status.getConvertTo(), status.getConversionRate());
	}

	@Transactional
	public List<PartMeasurementUnit> listPartMeasurementUnit() throws Exception {
		return partMeasurementUnitDao.findAll();
	}
	
	@Override
	public List<PartMeasurementUnitDto> listPartMeasurementUnitList() throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.pmuid, BA.pmuName, BA.pmuSymbol, BA.pmudescription, BA.needConversion,"
							+ " BA.convertTo, BA.conversionRate, MU.pmuName "
							+ " FROM PartMeasurementUnit AS BA "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = BA.convertTo"
							+ " where BA.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<PartMeasurementUnitDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					PartMeasurementUnitDto	measurementUnitDto = new PartMeasurementUnitDto();
					
					measurementUnitDto.setPmuid((Integer) result[0]);
					measurementUnitDto.setPmuName((String) result[1]);
					measurementUnitDto.setPmuSymbol((String) result[2]);
					measurementUnitDto.setPmudescription((String) result[3]);
					measurementUnitDto.setNeedConversion((boolean) result[4]);
					measurementUnitDto.setConvertTo((Integer) result[5]);
					measurementUnitDto.setConversionRate((Double) result[6]);
					measurementUnitDto.setConvertToStr((String) result[7]);
					
					if(measurementUnitDto.isNeedConversion()) {
						measurementUnitDto.setNeedConversionStr("Yes");
					}else {
						measurementUnitDto.setNeedConversionStr("No");
					}
					list.add(measurementUnitDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Transactional
	public PartMeasurementUnit getPartMeasurementUnit(int sid) throws Exception {
		return partMeasurementUnitDao.getPartMeasurementUnit(sid);
	}
	
	@Override
	public PartMeasurementUnitDto getPartMeasurementUnitDto(int sid) throws Exception {

		Query query  = entityManager
				.createQuery("SELECT BA.pmuid, BA.pmuName, BA.pmuSymbol, BA.pmudescription, BA.needConversion, "
						+ " BA.convertTo, BA.conversionRate, MU.pmuName "
						+ " FROM PartMeasurementUnit AS BA "
						+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = BA.convertTo"
						+ " where BA.pmuid = "+sid+" AND BA.markForDelete = 0 ");
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		PartMeasurementUnitDto	measurementUnitDto = null;
			if (result != null) {
				measurementUnitDto = new PartMeasurementUnitDto();
				
				measurementUnitDto.setPmuid((Integer) result[0]);
				measurementUnitDto.setPmuName((String) result[1]);
				measurementUnitDto.setPmuSymbol((String) result[2]);
				measurementUnitDto.setPmudescription((String) result[3]);
				measurementUnitDto.setNeedConversion((boolean) result[4]);
				measurementUnitDto.setConvertTo((Integer) result[5]);
				measurementUnitDto.setConversionRate((Double) result[6]);
				measurementUnitDto.setConvertToStr((String) result[7]);
				
				if(measurementUnitDto.isNeedConversion()) {
					measurementUnitDto.setNeedConversionStr("Yes");
				}else {
					measurementUnitDto.setNeedConversionStr("No");
				}
				
			}
		return measurementUnitDto;
	}

	@Transactional
	public void deletePartMeasurementUnit(Integer status) throws Exception {
		partMeasurementUnitDao.deletePartMeasurementUnit(status);
	}

	@Transactional
	public List<PartMeasurementUnit> listOnlyStatus() throws Exception {
		return partMeasurementUnitDao.findAll();
	}

	@Transactional
	public PartMeasurementUnit ValidatePartMeasurementUnit(String pmuName) throws Exception {
		return partMeasurementUnitDao.ValidatePartMeasurementUnit(pmuName);
	}
	
	@Override
	public Map<Integer, PartMeasurementUnit> getPartMeasurementUnitHM() throws Exception {
		List<PartMeasurementUnit> 	measermentList	= partMeasurementUnitDao.findAll();
		
		return	measermentList.stream().collect(Collectors.toMap(PartMeasurementUnit::getPmuid,
                Function.identity()));
		
	}
}