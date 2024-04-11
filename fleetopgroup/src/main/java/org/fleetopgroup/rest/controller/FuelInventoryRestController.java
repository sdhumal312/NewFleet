
package org.fleetopgroup.rest.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.FuelInvoiceHistoryRepository;
import org.fleetopgroup.persistence.document.FuelInvoiceDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelInvoiceDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.FuelInvoiceHistory;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class FuelInventoryRestController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired	private	ICompanyConfigurationService	companyConfigurationService;
	@Autowired	private	IFuelInvoiceService				fuelInvoiceService;
	@Autowired	private	IVendorService					vendorService;
	@Autowired	private	IFuelService					fuelService;
	@Autowired private	FuelInvoiceHistoryRepository	fuelInvoiceHistoryRepository;
	
	@GetMapping(path = "/FuelInventory")
	public ModelAndView fuelInventory(final HttpServletResponse result, HttpServletRequest	request) throws Exception {
		ModelAndView map = new ModelAndView("FuelInventory");
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_INVOICE_CONFIG);
			
			List <Vendor> petrolPumpList= vendorService.getOwnPetrolPumpList(userDetails.getCompany_id());
				
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("EDIT_FUEL_INVENTORY"))) {
				map.addObject("editInvoicePermission", true);
			}
			map.addObject("permissions", permission);
			map.addObject("configuration", configuration);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("petrolPumpList", petrolPumpList);
			map.addObject("accessToken", Utility.getUniqueToken(request));
			
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/saveFuelInventoryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveFuelInventoryDetails(@RequestParam("fuelInvoiceData") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject();
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			
			object.put("file", uploadfile);
			
			return fuelInvoiceService.saveFuelInventoryDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getPageWiseFuelInvoiceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPageWiseFuelInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return fuelInvoiceService.getPageWiseFuelInvoiceDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getFuelStockDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_INVOICE_CONFIG);
			object.put("companyId", userDetails.getCompany_id());
			object.put("showBalanceStockInvoiceList", configuration.getOrDefault("showBalanceStockInvoiceList", false));
			if((boolean) configuration.getOrDefault("showBalanceStockInvoiceList", false)) {
				object.put("FuelInvoice",fuelInvoiceService.getFuelInvoiceDtoList(object));
			}else {
				object.put("stockDetails", fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(object.getInt("petrolPumpId"), object.getInt("companyId")));
			}
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	
	@PostMapping(path = "/getFuelStockDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelStockDetail(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelInvoiceService.getFuelStockDetail(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@PostMapping(path = "/getFuelStockDetailsEdit", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelStockDetailsForEdit(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelInvoiceService.getFuelStockDetail(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/download/FuelInvoiceDocument/{ureaDocumentId}")
	public String  downloadUreaInvoiceDocument(@PathVariable("ureaDocumentId") Long ureaDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			if (ureaDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				FuelInvoiceDocument document = fuelInvoiceService.getFuelInvoiceDocumentDetails(ureaDocumentId, userDetails.getCompany_id());
				if (document != null && document.getContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getFilename() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getContentType());
						response.getOutputStream().write(document.getContent());
						out.flush();
						out.close();
				}
			}

		}catch (Exception e) {
			throw e;
		}
		return null;
	}

	@GetMapping(path = "/showFuelInvoice")
	public ModelAndView showClothInvoice(@RequestParam("Id") final Long invoiceId,final HttpServletRequest request) {
		ModelAndView 					map 			= new ModelAndView("ShowFuelInventory");//Jsp
		CustomUserDetails				userDetails		= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission		= userDetails.getGrantedAuthoritiesList();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_INVOICE_CONFIG);
			
			map.addObject("invoiceId",invoiceId);
			map.addObject("permission",permission);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			
			
		}catch (Exception e) {
			logger.error("Exception ", e);
		}
		return map;
	}
	
	@PostMapping(path = "/getFuelInvoiceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return fuelInvoiceService.getFuelInvoiceDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteFuelInvoice", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteFuelInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelInvoiceService.deleteFuelInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/updateShortExcessQuantity", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateShortExcessQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelInvoiceService.updateShortExcessQuantity(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getFuelInvoiceHistoryByInvoiceId", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelInvoiceHistoryByInvoiceId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelInvoiceService.getFuelInvoiceHistoryByInvoiceId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(value = "/editFuelInvoice")
	public ModelAndView editFuelInvoice(HttpServletRequest	request,@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ModelAndView map = new ModelAndView("editFuelInvoice");
		CustomUserDetails			userDetails				= null;
		HashMap<String, Object> 	configuration			= null;
		ValueObject 				object 					= null;
		Fuel						fuel					= null;
		FuelInvoiceHistory			fuelInvoiceHistoryDetail= null;
		try { 
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_INVOICE_CONFIG);
			object 				= new ValueObject(allRequestParams);
			
			fuel 				= fuelService.getfueldetailsbyfuelInvoiceId(object.getLong("Id"), userDetails.getCompany_id());
			fuelInvoiceHistoryDetail = fuelInvoiceHistoryRepository.getStatusWiseInvoiceHistory(object.getLong("Id"), userDetails.getCompany_id());
			if(fuel != null || fuelInvoiceHistoryDetail != null) {
				return new ModelAndView("redirect:/FuelInventory.in");
			}
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("EDIT_FUEL_INVENTORY"))) {
				map.addObject("editInvoicePermission", true);
			}
			map.addObject("fuelInvoiceId", object.getLong("Id"));
			map.addObject("permission", permission);
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			map.addObject("accessToken", Utility.getUniqueToken(request));
			map.addObject("companyId", userDetails.getCompany_id());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/updateFuelInventoryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateFuelInventoryDetails(@RequestParam("fuelInvoiceData") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject();
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			object.put("file", uploadfile);
			
			return fuelInvoiceService.updateFuelInventoryDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@GetMapping(value="createFuelTransfer")
	public ModelAndView createFuelTransfer(final HttpServletRequest req , final HttpServletResponse res) throws Exception {
		ModelAndView map = new ModelAndView("createFuelTransfer");
		
		CustomUserDetails userDetails = null;
		try {
			
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List <Vendor> petrolPumpList= vendorService.getOwnPetrolPumpList(userDetails.getCompany_id());
			map.addObject("TransferCreatedBy", userDetails.getFirstName());
			map.addObject("petrolPumpList", petrolPumpList);
			return map;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@RequestMapping(value = "/SearchFuelInvoiceByPetrolPump", method = RequestMethod.POST)
	public void SearchFuelInvoiceByPetrolPump(Map<String, Object> map, @RequestParam("term") Integer  term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<FuelInvoiceDto> fuelInvoiceList = fuelInvoiceService.getFuelInvoiceListByPetrolPump(userDetails.getCompany_id(), term);
		List<FuelInvoiceDto> fuelInvoice = new ArrayList<>();
		if (fuelInvoiceList != null && !fuelInvoiceList.isEmpty()) {
			for (FuelInvoiceDto add : fuelInvoiceList) {
				FuelInvoiceDto wadd = new FuelInvoiceDto();
				wadd.setFuelInvoiceId(add.getFuelInvoiceId());
				wadd.setFuelInvoiceNumber(add.getFuelInvoiceNumber());
				wadd.setBalanceStock(add.getBalanceStock());

				fuelInvoice.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(fuelInvoice));
	}
	
	@PostMapping(value="/saveTransferFuel", produces="application/json")
	public HashMap<Object, Object>  saveTransferFuel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject = fuelInvoiceService.saveTransferFuelDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PostMapping(path = "/getFuelBalanceStockDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelBalanceStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			if(object.getBoolean("getStockFromInventoryConfig",false)) {
				object.put("stockDetails",fuelInvoiceService.getBalanceStockFromInvoice(object.getInt("petrolPumpId"), object.getInt("companyId")));
			}else {
				object.put("stockDetails", fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(object.getInt("petrolPumpId"), object.getInt("companyId")));
			}
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
