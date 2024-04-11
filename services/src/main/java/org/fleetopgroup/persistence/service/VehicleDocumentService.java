package org.fleetopgroup.persistence.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDocumentDto;
import org.fleetopgroup.persistence.model.VehicleDocType;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VehicleDocumentService implements IVehicleDocumentService {

	@Autowired private MongoTemplate									mongoTemplate;
	@Autowired private ISequenceCounterService							sequenceCounterService;
	@Autowired private IRenewalReminderService 							RenewalReminderService;
	@Autowired private IVehicleDocumentService							vehicleDocumentService;
	@Autowired private IVehicleDocTypeService 							vehicleDocTypeService;
	@Autowired private IVehicleService 									vehicleService;
	@Autowired private MongoOperations									mongoOperations;
	
	RenewalReminderBL RRBL = new RenewalReminderBL();
	VehicleBL 		  VBL  = new VehicleBL();
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	
	@Override
	public void transferVehicleDocument(List<VehicleDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.VehicleDocument		vehicleDocument		= null;
		List<org.fleetopgroup.persistence.document.VehicleDocument> vehicleDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				vehicleDocumentList	= new ArrayList<>();
				for(VehicleDocument	document : list) {
					vehicleDocument	= new org.fleetopgroup.persistence.document.VehicleDocument();
					vehicleDocument.setFilename(document.getFilename());
					vehicleDocument.setContent(document.getContent());
					vehicleDocument.setContentType(document.getContentType());
					vehicleDocument.setVehid(document.getVehid());
					vehicleDocument.setDocTypeId(document.getDocTypeId());
					vehicleDocument.setCompanyId(document.getCompanyId());
					vehicleDocument.setCreated(document.getCreated());
					vehicleDocument.setCreatedById(document.getCreatedById());
					vehicleDocument.setId(document.getId());
					
					vehicleDocumentList.add(vehicleDocument);
				}
				System.err.println("Saving driverDocument ....");
				mongoTemplate.insert(vehicleDocumentList, org.fleetopgroup.persistence.document.VehicleDocument.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public void saveVehicleDoc(org.fleetopgroup.persistence.document.VehicleDocument vehicleDocument) {
		try {
			vehicleDocument.setId((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_VEHICLE_DOCUMENT));
			mongoTemplate.insert(vehicleDocument);
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
		
	}
	
	@Override
	public List<org.fleetopgroup.persistence.document.VehicleDocument> listVehicleDocument(Integer vehicleDocument, Integer companyId) throws Exception {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("vehid").is(vehicleDocument).and("companyId").is(companyId).and("markForDelete").is(false));
			return mongoTemplate.find(query, org.fleetopgroup.persistence.document.VehicleDocument.class);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public org.fleetopgroup.persistence.document.VehicleDocument getVehicleDocument(Integer vehicleDocumentId, Integer companyId)
			throws Exception {
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(vehicleDocumentId).and("companyId").is(companyId).and("markForDelete").is(false));
			return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.VehicleDocument.class);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public org.fleetopgroup.persistence.document.VehicleDocument getVehicleDocument(int vehicle_id) throws Exception {
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(vehicle_id).and("markForDelete").is(false));
			return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.VehicleDocument.class);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void deleteVehicleDocument(int vehicle_id, Integer companyId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(vehicle_id).and("companyId").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
       mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.VehicleDocument.class);
	}
	
	@Override
	public long getVehicleDocumentCount(int vehicle_id, Integer companyId) throws Exception {
		 Query query = new Query();
			query.addCriteria(Criteria.where("vehid").is(vehicle_id).and("companyId").is(companyId).and("markForDelete").is(false));
			return mongoTemplate.count(query, org.fleetopgroup.persistence.document.VehicleDocument.class);

	}
	
	@Override
	public long getVehiclePhotoCount(int vehicle_id, Integer companyId) throws Exception {
		 Query query = new Query();
			query.addCriteria(Criteria.where("vehid").is(vehicle_id).and("companyId").is(companyId).and("markForDelete").is(false));
			return mongoTemplate.count(query, org.fleetopgroup.persistence.document.VehiclePhoto.class);

	}
	
	@Override
	@Transactional
	public ValueObject saveVehicleDocumentDetails(ValueObject valueObject, MultipartFile file) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			org.fleetopgroup.persistence.document.VehicleDocument photo = new org.fleetopgroup.persistence.document.VehicleDocument();
			
			if (!file.isEmpty()) {
				
				byte[] bytes = file.getBytes();
				photo.setDocTypeId(valueObject.getLong("docTypeId",0));
				photo.setVehid(valueObject.getInt("vid",0));
				photo.setFilename(file.getOriginalFilename());
				photo.setContent(bytes);
				photo.setContentType(file.getContentType());
				photo.setCompanyId(userDetails.getCompany_id());
				photo.setCreated(new Date());
				photo.setCreatedById(userDetails.getId());
				
				if(valueObject.get("id") == null) {
					vehicleDocumentService.saveVehicleDoc(photo);
				}else {
					photo.setId(valueObject.getInt("id"));
					mongoOperations.save(photo);
				}
				
				valueObject.put("documentUpdated", true);
			}
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getDocumentList(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date 		currentDate = new java.util.Date();
			DateFormat 	   		ft 		    = new SimpleDateFormat("YYYY-MM-dd");
			
			valueObject.put("renewal", RRBL.Only_Show_ListofRenewalDto(RenewalReminderService.listVehicleRenewalReminder(
					valueObject.getInt("vid"), ft.format(currentDate)+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id())));
			
			valueObject.put("vehicledocumentList", getVehicleDocumentList(vehicleDocumentService.listVehicleDocument(valueObject.getInt("vid"), userDetails.getCompany_id())));
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<VehicleDocumentDto> getVehicleDocumentList(List<org.fleetopgroup.persistence.document.VehicleDocument> list){
		CustomUserDetails	userDetails		= null;
		try {
			/** Show Only Select Column Value in Vehicle Tables **/
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleDocumentDto> Dtos = null;
			if(list != null) {
				VehicleDocType	docType	= null;
				Dtos = new ArrayList<>();
				for(org.fleetopgroup.persistence.document.VehicleDocument document : list) {
					VehicleDocumentDto Documentto = null;
					Documentto = new VehicleDocumentDto();
					Documentto.setId(document.getId());
					if(document.getDocTypeId() != null)
						docType	= vehicleDocTypeService.getVehicleDocTypeByID(document.getDocTypeId(), userDetails.getCompany_id());
					
					if(docType != null)
						Documentto.setName(vehicleDocTypeService.getVehicleDocTypeByID(document.getDocTypeId(), userDetails.getCompany_id()).getvDocType());
					if(document.getCreated() != null)
						Documentto.setUploaddate(CreatedDateTime.format(document.getCreated()));
					Documentto.setFilename(document.getFilename());
					Documentto.setVehid(document.getVehid());

					Dtos.add(Documentto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails = null;
		}
	}
	
	@Override
	public ValueObject getVehicleDocumentById(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails  userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDocumentDto vehi = prepareVehicleDocumentBean(vehicleDocumentService.getVehicleDocument(valueObject.getInt("documentId"), userDetails.getCompany_id()));
			
			valueObject.put("vehicledocument", vehi);
			valueObject.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehi.getVehid())));
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public VehicleDocumentDto prepareVehicleDocumentBean(org.fleetopgroup.persistence.document.VehicleDocument photo) {
		VehicleDocumentDto bean = new VehicleDocumentDto();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		bean.setId(photo.getId());
		bean.setVehid(photo.getVehid());
		VehicleDocType vehicleDocType = vehicleDocTypeService.getVehicleDocTypeByID(photo.getDocTypeId(), userDetails.getCompany_id());
		if(vehicleDocType != null) {
			bean.setName(vehicleDocType.getvDocType());
		}
	//	bean.setName(vehicleDocTypeService.getVehicleDocTypeByID(photo.getDocTypeId(), userDetails.getCompany_id()).getvDocType());
		if(bean.getCreated() != null)
			bean.setUploaddate(CreatedDateTime.format(photo.getCreated()));
		bean.setDocTypeId(photo.getDocTypeId());
		
		return bean;
	}
	
	@Override
	public ValueObject deleteVehicleDocumentById(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails  userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleDocumentService.deleteVehicleDocument(valueObject.getInt("documentId"), userDetails.getCompany_id());
			valueObject.put("deleteVehicleDocument", true);
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
