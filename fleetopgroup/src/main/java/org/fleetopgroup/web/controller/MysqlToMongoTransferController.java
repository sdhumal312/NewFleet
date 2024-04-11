package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dao.BranchDocumentRepository;
import org.fleetopgroup.persistence.dao.BranchPhotoRepository;
import org.fleetopgroup.persistence.dao.CashBookDocumentRepository;
import org.fleetopgroup.persistence.dao.CompanylogoRepository;
import org.fleetopgroup.persistence.dao.DriverDocumentRepository;
import org.fleetopgroup.persistence.dao.DriverPhotoRepository;
import org.fleetopgroup.persistence.dao.FuelDocumentRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRetreadDocumentRepository;
import org.fleetopgroup.persistence.dao.IssuesDocumentRepository;
import org.fleetopgroup.persistence.dao.IssuesPhotoRepository;
import org.fleetopgroup.persistence.dao.MailboxDocumentRepository;
import org.fleetopgroup.persistence.dao.MasterPartsPhotoRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersDocumentRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderAppDocumentRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderDocumentHistoryRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderDocumentRepository;
import org.fleetopgroup.persistence.dao.UserProfileDocumentRepository;
import org.fleetopgroup.persistence.dao.UserProfilePhotoRepository;
import org.fleetopgroup.persistence.dao.VehicleDocumentRepository;
import org.fleetopgroup.persistence.dao.VehiclePhotoRepository;
import org.fleetopgroup.persistence.dao.VendorDocumentRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersDocumentRepository;
import org.fleetopgroup.persistence.document.MailboxDocument;
import org.fleetopgroup.persistence.document.VehicleDocument;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IMailBoxService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MysqlToMongoTransferController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private IServiceEntriesDocumentService		documentService;
	
	@Autowired	private IServiceEntriesService ServiceEntriesService;
	
	@Autowired private DriverDocumentRepository		driverDocumentRepository;
	
	@Autowired private VehicleDocumentRepository	vehicleDocumentRepository;

	@Autowired private CashBookDocumentRepository	cashBookDocumentRepository;

	@Autowired private WorkOrdersDocumentRepository	 workOrdersDocumentRepository;

	@Autowired private MailboxDocumentRepository	 mailboxDocumentRepository;

	@Autowired private BranchDocumentRepository	 branchDocumentRepository;

	@Autowired private BranchPhotoRepository	branchPhotoRepository;

	@Autowired private FuelDocumentRepository	fuelDocumentRepository;

	@Autowired private InventoryTyreRetreadDocumentRepository	inventoryTyreRetreadDocumentRepository;

	@Autowired private IssuesDocumentRepository	issuesDocumentRepository;

	@Autowired private IssuesPhotoRepository	issuesPhotoRepository;

	@Autowired private PurchaseOrdersDocumentRepository	purchaseOrdersDocumentRepository;

	@Autowired private RenewalReminderAppDocumentRepository	renewalReminderAppDocumentRepository;

	@Autowired private RenewalReminderDocumentHistoryRepository	renewalReminderDocumentHistoryRepository;

	@Autowired private UserProfileDocumentRepository	userProfileDocumentRepository;

	@Autowired private UserProfilePhotoRepository	userProfilePhotoRepository;

	@Autowired private VendorDocumentRepository	vendorDocumentRepository;

	@Autowired private DriverPhotoRepository	driverPhotoRepository;

	@Autowired private MasterPartsPhotoRepository	masterPartsPhotoRepository;

	@Autowired private VehiclePhotoRepository	vehiclePhotoRepository;

	@Autowired private CompanylogoRepository	companyLogoRepository;
	
	@Autowired private IVehicleDocumentService		vehicleDocumentService;
	
	@Autowired private ICashBookService		cashBookService;

	@Autowired private IWorkOrdersService		workOrdersService;

	@Autowired private IMailBoxService		mailBoxService;

	@Autowired private IBranchService		branchService;

	@Autowired private IFuelService		fuelService;

	@Autowired private IInventoryTyreService		inventoryTyreService;

	@Autowired private IIssuesService		issuesService;

	@Autowired private IPurchaseOrdersService		purchaseOrdersService;

	@Autowired private RenewalReminderDocumentRepository	documentRepository;
	
	@Autowired IRenewalReminderDocumentService				 renewalReminderDocumentService;

	@Autowired IUserProfileService				 userProfileService;

	@Autowired IVendorService				 vendorService;

	@Autowired IDriverService				 driverService;

	@Autowired IMasterPartsService				masterPartsService;

	@Autowired IVehicleService				vehicleService;

	@Autowired ICompanyService				companyService;
	
	@Autowired private MongoTemplate		mongoTemplate;
	
	@RequestMapping(value = "/transferDataToMongoDb", method = RequestMethod.GET)
	public ModelAndView addVehicleFuelPermission(final VehicleFuelDto vehicleFuelDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

		} catch (Exception e) {
			LOGGER.error("Registering VehicleFuel account with information: {}", e);
		}
		return new ModelAndView("transferDataToMongoDb", model);
	}
	
	@RequestMapping(value = "/transferDataToMongoDbFromMysql", method = RequestMethod.GET)
	public ModelAndView transferDataToMongoDb(final VehicleFuelDto vehicleFuelDto, final HttpServletRequest request) {
	
		System.err.println("inside transferDataToMongoDbFromMysql....");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//transferServiceEntriesDocument();
			//transferDriverDocument();
			//transferVehicleDocument();
			transferRenewalReminderDocument();
			//documentService.transferDriverDocument(driverDocumentRepository.getDriverDocumentList(4600, 4700));
			//vehicleDocumentService.transferVehicleDocument(vehicleDocumentRepository.getVehicleDocumentList(6986, 7100));
			//renewalReminderDocumentService.transferRenewalReminderDocument(documentRepository.getRenewalDocumentList(42340, 42540));
			
			// 13/08/2018
			/*transferCashBookDocument();
			transferWorkOrderDocument();
			transferMailBoxDocument();
			transferBranchDocument();
			transferBranchPhoto();
			transferFuelDocument();
			transferInventoryTyreRetreadDocument();
			transferIssuesDocument();
			transferIssuesPhotos();
			transferPurchaseOrderDocument();
			transferRenewalReminderAppDocument();
			transferRenewalReminderDocumentHistory();
			transferUserProfileDocument();
			transferUserProfilePhoto();
			transferVendorDocument();
			transferDriverPhoto();
			transferMasterPartsPhoto();
			transferVehiclePhoto();
			transferCompanyLogo();*/
			
			
		} catch (Exception e) {
			LOGGER.error("Registering VehicleFuel account with information: {}", e);
		}
		return new ModelAndView("transferDataToMongoDb", model);
	}
	
	public void transferServiceEntriesDocument() throws Exception {
		
		long count = ServiceEntriesService.getServiceEntriesDocumentCount();
		long loopcount = count/200;
		long startLimit	= 0;
		long endLimit = 200;
		for (long i = 0; i <= loopcount; i++) {
			documentService.transferDataFromMySqlToMongoDb(ServiceEntriesService.getServiceEntriesDocumentList(startLimit, endLimit));
			startLimit = endLimit;
			endLimit = endLimit + 200;
			System.gc();
		}
		
		LOGGER.error("All Records Trnasferd Successfully !.");
	}
	
	public void transferDriverDocument() throws Exception {
		
		long count = driverDocumentRepository.getMaxId();
		System.err.println("count : "+count);
		Integer loopcount = (int) (count/200);
		Integer startLimit	= 0;
		Integer endLimit = 200;
		for (long i = 0; i <= loopcount; i++) {
			documentService.transferDriverDocument(driverDocumentRepository.getDriverDocumentList(startLimit, endLimit));
			startLimit = endLimit;
			endLimit = endLimit + 200;
			System.gc();
		}
		
		LOGGER.error("All Records Trnasferd Successfully !.");
	}
	
	public void transferVehicleDocument() throws Exception {
		
		long count = vehicleDocumentRepository.getVehicleDocumentMaxId();
		System.err.println("count : "+count);
		Integer loopcount = (int) (count/200);
		Integer startLimit	= 0;
		Integer endLimit = 200;
		for (long i = 0; i <= loopcount; i++) {
			vehicleDocumentService.transferVehicleDocument(vehicleDocumentRepository.getVehicleDocumentList(startLimit, endLimit));
			startLimit = endLimit;
			endLimit = endLimit + 200;
			System.gc();
		}
		
		LOGGER.error("All Records Trnasferd Successfully !.");
	}

	public void transferCashBookDocument() throws Exception {
		
		long count = cashBookService.getMaxCashBookId();
		if(count > 0) {
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				cashBookService.transferCahBookDocument(cashBookDocumentRepository.getCashBookDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			LOGGER.error("All Records Trnasferd Successfully !.");
		}else {
			LOGGER.error("No Records Fount For CashBookDocument");
		}
		
	}

	public void transferWorkOrderDocument() throws Exception {
		
		long count = workOrdersService.getWorkOrderDocumentMaxId();
		if(count > 0) {
			
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				workOrdersService.transferWorkOrderDocument(workOrdersDocumentRepository.getWorkOrderDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}else {
			LOGGER.error("No Records Fount For WorkOrderDocument");
		}
	}

	public void transferMailBoxDocument() throws Exception {
		
		long count = mailboxDocumentRepository.getMailBoxDocumentMaxId();
		System.err.println("count : "+count);
		Integer loopcount = (int) (count/200);
		Long startLimit	= (long) 0;
		Long endLimit = (long) 200;
		for (long i = 0; i <= loopcount; i++) {
			mailBoxService.transferMailBoxDocument(mailboxDocumentRepository.getMailBoxDocumentList(startLimit, endLimit));
			startLimit = endLimit;
			endLimit = endLimit + 200;
			System.gc();
		}
		
		LOGGER.error("All Records Trnasferd Successfully !.");
	}

	public void transferBranchDocument() throws Exception {
		
		long count = branchService.getBranchDocumentMaxId();
		if(count > 0) {
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				branchService.transferBranchDocument(branchDocumentRepository.getBranchDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}else {
			LOGGER.error("No Records Fount For BranchDocument");
		}
		
	}

	public void transferBranchPhoto() throws Exception {
		
		long count = branchService.getBranchPhotoMaxId();
		if(count > 0) {
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				branchService.transferBranchPhoto(branchPhotoRepository.getBranchPhotoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}else {
			LOGGER.error("No Records Fount For BranchPhoto");
		}
		
	}

	public void transferFuelDocument() throws Exception {
		
		long count = fuelService.getFuelDocumentMaxId();
		if(count > 0) {
			
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				fuelService.transferFuelDocument(fuelDocumentRepository.getFuelDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
	}

	public void transferInventoryTyreRetreadDocument() throws Exception {
		
		long count = inventoryTyreService.getInventoryTyreRetreadDocumentMaxId();
		if(count > 0) {
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				inventoryTyreService.transferInventoryTyreRetreadDocument(inventoryTyreRetreadDocumentRepository.getInventoryTyreRetreadDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferIssuesDocument() throws Exception {
		
		long count = issuesService.getIssuesDocumentMaxId();
		
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				issuesService.transferIssuesDocument(issuesDocumentRepository.getIssuesDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferIssuesPhotos() throws Exception {
		
		long count = issuesService.getIssuesPhotoMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				issuesService.transferIssuesPhotos(issuesPhotoRepository.getIssuesPhotoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferPurchaseOrderDocument() throws Exception {
		
		long count = purchaseOrdersService.getPurchaseOrdersDocumentMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				purchaseOrdersService.transferPurchaseOrderDocument(purchaseOrdersDocumentRepository.getPurchaseOrdersDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferRenewalReminderAppDocument() throws Exception {
		
		long count = renewalReminderDocumentService.getRenewalReminderAppDocumentMaxId();
		System.err.println("count : "+count);
		
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				renewalReminderDocumentService.transferRenewalReminderAppDocument(renewalReminderAppDocumentRepository.getRenewalReminderAppDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferRenewalReminderDocumentHistory() throws Exception {
		
		long count = renewalReminderDocumentService.getRenewalReminderDocumentHistoryMaxId();
		if(count > 0) {
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				renewalReminderDocumentService.transferRenewalReminderDocumentHistory(renewalReminderDocumentHistoryRepository.getRenewalReminderDocumentHistoryList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferUserProfileDocument() throws Exception {
		
		long count = userProfileService.getUserProfileDocumentMaxId();
		if(count > 0) {
			System.err.println("count : "+count);
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				userProfileService.transferUserProfileDocument(userProfileDocumentRepository.getUserProfileDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferUserProfilePhoto() throws Exception {
		
		long count = userProfileService.getUserProfilePhotoMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			long startLimit	= 0;
			long endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				userProfileService.transferUserProfilePhoto(userProfilePhotoRepository.getUserProfilePhotoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferVendorDocument() throws Exception {
		
		long count = vendorService.getVendorDocumentMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				vendorService.transferVendorDocument(vendorDocumentRepository.getVendorDocumentList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferDriverPhoto() throws Exception {
		
		long count = driverService.getDriverPhotoMaxId();
		System.err.println("count : "+count);
		
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				driverService.transferDriverPhoto(driverPhotoRepository.getDriverPhotoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferMasterPartsPhoto() throws Exception {
		
		long count = masterPartsService.getMasterPartsPhotoMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Long startLimit	= (long) 0;
			Long endLimit = (long) 200;
			for (long i = 0; i <= loopcount; i++) {
				masterPartsService.transferMasterPartsPhoto(masterPartsPhotoRepository.getMasterPartsPhotoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferVehiclePhoto() throws Exception {
		
		long count = vehicleService.getVehiclePhotoMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				vehicleService.transferVehiclePhoto(vehiclePhotoRepository.getVehiclePhotoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}

	public void transferCompanyLogo() throws Exception {
		
		long count = companyService.getCompanylogoMaxId();
		System.err.println("count : "+count);
		if(count > 0) {
			Integer loopcount = (int) (count/200);
			Integer startLimit	= 0;
			Integer endLimit = 200;
			for (long i = 0; i <= loopcount; i++) {
				companyService.transferCompanyLogo(companyLogoRepository.getCompanylogoList(startLimit, endLimit));
				startLimit = endLimit;
				endLimit = endLimit + 200;
				System.gc();
			}
			
			LOGGER.error("All Records Trnasferd Successfully !.");
		}
		
	}
	
	public void transferRenewalReminderDocument() throws Exception {
		
		long count = documentRepository.getBigRenewalDocumentList();
		System.err.println("count : "+count);
		long loopcount = (count/200);
		long startLimit	= 0;
		long endLimit = 200;
		for (long i = 0; i <= loopcount; i++) {
			renewalReminderDocumentService.transferRenewalReminderDocument(documentRepository.getRenewalDocumentList(startLimit, endLimit));
			startLimit = endLimit;
			endLimit = endLimit + 200;
			System.gc();
		}
		
		LOGGER.error("All Records Trnasferd Successfully !.");
	}
	
	@RequestMapping(value = "/UpdateCompanyId", method = RequestMethod.GET)
	public ModelAndView UpdateCompanyId(final VehicleFuelDto vehicleFuelDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			renewalReminderDocumentService.updateCompanyId();
			LOGGER.error("All Records Updated Successfully !.");
		} catch (Exception e) {
			LOGGER.error("Registering VehicleFuel account with information: {}", e);
		}
		return new ModelAndView("transferDataToMongoDb", model);
	}
	
	//@RequestMapping(value = "/transferDataToMongoDbFromMysql", method = RequestMethod.POST)
	public ModelAndView updateVehicleDoc(@ModelAttribute("command") FuelDto fuelDto,
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result, RedirectAttributes redir) throws IOException {
		try {
			
			System.err.println("input-file-preview "+file);
			
			if (!file.isEmpty()) {
				
				Query select = Query.query(Criteria.where("_id").is(2));
				Update update = new Update();
				update.set("content", file.getBytes());
				update.set("contentType", file.getContentType());
				update.set("filename", file.getOriginalFilename());
			    mongoTemplate.findAndModify(select, update, VehicleDocument.class);
			}
		} catch (Exception e) {
			System.err.println("Exception "+e);
		}
		
		return new ModelAndView("transferDataToMongoDb", new HashMap<String, Object>());
	}
	
	public void insertSequnceCounter() throws Exception {
		
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "_id"));
		query.limit(1);
		MailboxDocument maxObject = mongoTemplate.findOne(query, MailboxDocument.class);
		
		System.err.println("maxObject : "+maxObject.get_id());
	}

}
