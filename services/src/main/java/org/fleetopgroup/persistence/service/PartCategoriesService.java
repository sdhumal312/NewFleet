package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dao.PartCategoriesRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksToPartsDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.PartCategories;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("IPartCategoriesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartCategoriesService implements IPartCategoriesService {

	@Autowired
	private IWorkOrdersService WorkOrdersService;
	WorkOrdersBL WOBL = new WorkOrdersBL();

	@Autowired
	private IServiceEntriesService ServiceEntriesService;
	ServiceEntriesBL SEBL = new ServiceEntriesBL();

	@Autowired
	private PartCategoriesRepository PartCategoriesDao;
	
	@PersistenceContext
	EntityManager entityManager ;

	@Autowired ICompanyConfigurationService		companyConfigurationService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartCategories(PartCategories status) throws Exception {
		HashMap<String, Object>    configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(status.getCompanyId(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.COMMON_PART_CATEGORY)) {
				status.setOwnPartCategory(true);
			}
			PartCategoriesDao.save(status);
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updatePartCategories(PartCategories status) throws Exception {
		PartCategoriesDao.updatePartCategories(status.getPcName(), status.getPcdescription(), status.getLastModifiedById(), status.getLastModifiedOn(),status.getPcid(), status.getCompanyId(), status.isIncPartIssueCateory());
	}
	@Transactional
	public List<PartCategories> listPartCategories() throws Exception {
		return PartCategoriesDao.findAll();
	}

	@Transactional
	public PartCategories getPartCategories(int sid, Integer companyid) throws Exception {
		HashMap<String, Object>    configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyid, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_PART_CATEGORY)) {
				return PartCategoriesDao.getPartCategories(sid);
			}else {
				return PartCategoriesDao.getPartCategories(sid, companyid);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}

	}

	@Transactional
	public void deletePartCategories(Integer partid, Integer companyId) throws Exception {
		PartCategoriesDao.deletePartCategories(partid, companyId);
	}

	@Transactional
	public List<PartCategories> listOnlyStatus() throws Exception {
		return PartCategoriesDao.findAll();
	}

	@Transactional
	public List<PartCategories> ValidateCategoriesName(String Categories, Integer companyId) throws Exception {

		HashMap<String, Object>    configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_PART_CATEGORY)) {
				return PartCategoriesDao.ValidateCategoriesName(Categories);
			}else {
				return PartCategoriesDao.ValidateCategoriesName(Categories, companyId);
			}

		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}


	}

	@Override
	public PartCategories ValidatePCName(String Categories, Integer companyId) throws Exception {
		HashMap<String, Object>    configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_PART_CATEGORY)) {
				return PartCategoriesDao.ValidatePCName(Categories);
			}else {
				return PartCategoriesDao.ValidatePCName(Categories, companyId);
			}

		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}

	}

	@Override
	public List<PartCategories> listPartCategoriesByCompayId(Integer companyId) throws Exception {
		HashMap<String, Object>    configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_PART_CATEGORY)) {
				System.err.println("inside ALL ");
				return PartCategoriesDao.findAll();
			}
			else {
				System.err.println("inside else");
				return PartCategoriesDao.findAllByCompanyId(companyId);
			}

		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}
	
	@Override
	public List<PartCategories> listPartCategoriesByCompayIdAncIncIssue(Integer companyId) throws Exception {
		return PartCategoriesDao.findCategoryByIncInIssue(companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getMostPartConsumedReport(ValueObject valueObject) throws Exception {
		List<WorkOrdersTasksToPartsDto>					workOrdersTasksDtoList		= null;
		List<WorkOrdersTasksToPartsDto>					finalTasksDtoList			= null;
		List<ServiceEntriesTasksToPartsDto>				serviceEntriesTasksDtoList	= null;
		CustomUserDetails								userDetails					= null;
		String											month						= null;
		Long											GroupID						= null;
		Integer											LocationID					= 0;
		ValueObject										valueOutObject				= null;
		HashMap<Long, WorkOrdersTasksToPartsDto>		partConsumeReportHM			= null;
		WorkOrdersTasksToPartsDto 						preWorkorder				= null;
		try {

			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueOutObject	= new ValueObject();

			month	= valueObject.getString("dateRange", null);
			GroupID = valueObject.getLong("vehicleGroupId");
			LocationID=valueObject.getInt("location");
			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {
				From_TO_DateRange = month.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			}
			catch (Exception e) {

			}
			workOrdersTasksDtoList			=	 (List<WorkOrdersTasksToPartsDto>) WorkOrdersService.getWorkOrdersTasksTo_Most_Parts_Count_Report(GroupID,dateRangeFrom, dateRangeTo , userDetails.getCompany_id(),LocationID);
			serviceEntriesTasksDtoList		= 	ServiceEntriesService.getServiceEntriesTasksTo_Most_Parts_Count_Report(GroupID,dateRangeFrom, dateRangeTo , userDetails.getCompany_id(), LocationID);
			finalTasksDtoList				= new ArrayList<WorkOrdersTasksToPartsDto>();

			if(workOrdersTasksDtoList != null && workOrdersTasksDtoList.size() > 0) {
				for(WorkOrdersTasksToPartsDto workorder : workOrdersTasksDtoList) {
					if(partConsumeReportHM == null) {
						partConsumeReportHM	= new HashMap<Long, WorkOrdersTasksToPartsDto>();
						partConsumeReportHM.put(workorder.getPartid(), workorder);
					} else {

						if(partConsumeReportHM.containsKey(workorder.getPartid())) {
							preWorkorder	= partConsumeReportHM.get(workorder.getPartid());
							preWorkorder.setQuantity(preWorkorder.getQuantity() + workorder.getQuantity());
							preWorkorder.setTotalValuePartConsumed(preWorkorder.getTotalValuePartConsumed() + workorder.getTotalValuePartConsumed());
						} else {
							partConsumeReportHM.put(workorder.getPartid(), workorder);
						}
					}
				}
			}
			if(serviceEntriesTasksDtoList != null && serviceEntriesTasksDtoList.size() > 0) {
				for(ServiceEntriesTasksToPartsDto serviceEntries : serviceEntriesTasksDtoList) {
					WorkOrdersTasksToPartsDto 	workorder = new WorkOrdersTasksToPartsDto();
					workorder.setPartid(serviceEntries.getPartid());
					workorder.setPartCount(serviceEntries.getPartCount());
					workorder.setPartname(serviceEntries.getPartname());
					workorder.setPartnumber(serviceEntries.getPartnumber());
					workorder.setTotalValuePartConsumed(serviceEntries.getTotalValuePartConsumed());
					workorder.setQuantity(serviceEntries.getQuantity());

					if(partConsumeReportHM == null) {
						partConsumeReportHM	= new HashMap<Long, WorkOrdersTasksToPartsDto>();
						partConsumeReportHM.put(workorder.getPartid(), workorder);
					}else if(partConsumeReportHM.containsKey(workorder.getPartid())) {
						preWorkorder	= partConsumeReportHM.get(workorder.getPartid());
						preWorkorder.setPartCount(preWorkorder.getPartCount() + workorder.getPartCount());
						workorder.setQuantity(preWorkorder.getQuantity() + workorder.getQuantity());
						preWorkorder.setTotalValuePartConsumed(preWorkorder.getTotalValuePartConsumed() + workorder.getTotalValuePartConsumed());
					} else {
						partConsumeReportHM.put(workorder.getPartid(),workorder);
					}
				}
			}

			if(partConsumeReportHM != null) {

				finalTasksDtoList.addAll(partConsumeReportHM.values());
				finalTasksDtoList.sort(Comparator.comparing(WorkOrdersTasksToPartsDto::getPartCount).reversed());

			}
			if(finalTasksDtoList.size()>20) {
				valueOutObject.put("finalTasksDtoList", finalTasksDtoList.subList(0, 20));
			}
			else {
				valueOutObject.put("finalTasksDtoList", finalTasksDtoList);
			}


			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			workOrdersTasksDtoList		= null; 
			finalTasksDtoList			= null; 
			serviceEntriesTasksDtoList	= null; 
			userDetails					= null; 
			month						= null; 
			GroupID						= null; 
			LocationID					= 0;    
			valueOutObject				= null; 
			partConsumeReportHM			= null; 
			preWorkorder				= null; 
		}
	}

	@Override
	public List<PartCategories> searchPartCategories(String term) throws Exception {
		CustomUserDetails userDetials = null;
		
		HashMap<String, Object> 	configuration	= null;
		List<Object[]> results = null;
		List<PartCategories> finalList = null;
		try {
			
			
			userDetials = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration=companyConfigurationService.getCompanyConfiguration(userDetials.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			TypedQuery<Object[]> query = null;
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			if((boolean) configuration.getOrDefault(MastersConfigurationConstants.COMMON_PART_CATEGORY, false)) {
				 query = entityManager.createQuery(" SELECT pc.pcid ,pc.pcName FROM PartCategories as pc"
							+ " where (lower(pc.pcName) Like (:term) ) AND  pc.isOwnPartCategory = 0 and pc.markForDelete = 0  ",Object[].class);	
			}else {
				 query = entityManager.createQuery(" SELECT pc.pcid ,pc.pcName FROM PartCategories as pc"
							+ " where (lower(pc.pcName) Like (:term) ) AND  pc.companyId = "+userDetials.getCompany_id()+" AND pc.isOwnPartCategory = 1 and pc.markForDelete = 0 ",Object[].class);	
			}
			query.setParameter("term", "%"+term+"%");
			results=query.getResultList();
			
			if(results != null && !results.isEmpty()) {
				finalList = new ArrayList<>();
				PartCategories  partCategories = null;
				for(Object[] result : results) {
					partCategories = new PartCategories();
					partCategories.setPcid((Integer) result[0]);
					partCategories.setPcName((String) result[1]);
					finalList.add(partCategories);
				}
			}
			}
			return finalList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<PartCategories> getPartCategoryList(Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT P.pcid, P.pcName "
				+ " FROM PartCategories P "
				+ " where P.companyId = "+companyId+ "  AND  P.incPartIssueCateory = 1 ",Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<PartCategories> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PartCategories>();
			PartCategories list = null;
			for (Object[] result : results) {
				list = new PartCategories();
				list.setPcid((Integer) result[0]);
				list.setPcName((String) result[1]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}

}
