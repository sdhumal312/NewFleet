package org.fleetopgroup.rest.controller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.persistence.bl.CompanyBL;
import org.fleetopgroup.persistence.dao.FuelStockDetailsRepository;
import org.fleetopgroup.persistence.dao.MarqueeMasterRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelStockDetailsDto;
import org.fleetopgroup.persistence.dto.MarqueeMasterDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IMarqueeMasterService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/MarqueeMasterRestController")
public class MarqueeMasterRestController {

	@Autowired 	IMarqueeMasterService   	MarqueeMasterService;
	@Autowired 	ICompanyService   			companyService;
	@Autowired	MarqueeMasterRepository		MarqueeMasterRepository;
	@Autowired 	FuelStockDetailsRepository	fuelStockDetailsRepository;
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	@Autowired	IUreaInvoiceToDetailsService	ureaInvoiceToDetailsService;
	@Autowired  IFuelInvoiceService fuelInvoiceService;
	
				CompanyBL	 				companyBL 					= new CompanyBL();
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());				

	@RequestMapping(value = "/getCompanyInformationDetails", method = RequestMethod.GET)
	public void getCompanyInformationDetails(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Company> 		companyList 		= new ArrayList<>();
		ArrayList<Company> 	CompanyDetailsList	= null;
		try {
			CompanyDetailsList 	= (ArrayList<Company>) companyService.findAll();

			if(CompanyDetailsList != null && !CompanyDetailsList.isEmpty()) {	
				for (Company CompanyDetails : CompanyDetailsList) {
					Company 	companyDto 	= new Company();
					companyDto.setCompany_id(CompanyDetails.getCompany_id());
					companyDto.setCompany_name(CompanyDetails.getCompany_name());

					companyList.add(companyDto);
				}
			}

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(companyList));
		}catch(Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
		}
	}

	@RequestMapping(value = "/getAllMarqueeMasterDetails", method = RequestMethod.GET)//for display marquee   
	public void getAllMarqueeMasterDetails(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<MarqueeMasterDto> 			marqueeMasterList 			= null;
		ArrayList<MarqueeMasterDto> 	marqueeMasterDetailsList 	= null;
		MarqueeMasterDto 				marqueeMasterDto			= null;
		
		try {
			marqueeMasterDetailsList 	= (ArrayList<MarqueeMasterDto>) MarqueeMasterService.getAllMarqueeMessagesList();
			marqueeMasterList 			= new ArrayList<>();
				if(marqueeMasterDetailsList != null && !marqueeMasterDetailsList.isEmpty()) {	
					for (MarqueeMasterDto dto : marqueeMasterDetailsList) {

						marqueeMasterDto = new MarqueeMasterDto();
						marqueeMasterDto.setMarquee_id(dto.getMarquee_id());
						marqueeMasterDto.setCompanyId(dto.getCompanyId());
						marqueeMasterDto.setCompanyName(dto.getCompanyName());
						marqueeMasterDto.setMarquee_message(dto.getMarquee_message().split("`")[0]);
	
						marqueeMasterList.add(marqueeMasterDto);
					}
				}

					Gson gson = new Gson();
					response.getWriter().write(gson.toJson(marqueeMasterList));
			}catch(Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
		}
	}

	@RequestMapping(value = "/addMarqueeMaster", method = RequestMethod.GET)
	public ModelAndView addMarqueeMaster( final HttpServletRequest request) {
		ModelAndView 					map 			= new ModelAndView("addMarqueeMaster");
		CustomUserDetails				userDetails		= null;
		Collection<GrantedAuthority>	permission 		= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			permission				= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);

		}  catch (Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
			throw e;
		}
		return map;
	}


	@RequestMapping(value = "/saveMarqueeMaster", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addMarqueeMessage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 	= null;
		try {
			object 		= new ValueObject(allRequestParams);

			return MarqueeMasterService.saveMarqueeMessage(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object = null;      
		}
	}

	@RequestMapping(value = "/getMessageByCompanyId", method = RequestMethod.POST, produces="application/json")//for master
	public HashMap<Object, Object>  getMessageByCompanyId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);

			return MarqueeMasterService.getMessageByCompanyId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 			= null;      
		}
	}


	@RequestMapping(value = "/removeMessage", method = RequestMethod.POST, produces="application/json")//for master
	public HashMap<Object, Object>  removeMessage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		long 			marqueeId		= 0;		

		try {
			object 				= new ValueObject(allRequestParams);
			
			marqueeId=	object.getLong("marqueeId");
			MarqueeMasterRepository.deleteMarqueeMessage(marqueeId);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 			= null;      
		}
	}

	@RequestMapping(value = "/updateMarqueeMessage", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateMarqueeMessage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 			object 			= null;
		long					marqueeId		= 0;
		String					message			= null;
		String					finalMessage	= null;

		try {
			object 				= new ValueObject(allRequestParams);
			marqueeId			= object.getLong("marqueeId");
			message				= object.getString("message");
			finalMessage		= message+"`"+object.getString("colorId");

			MarqueeMasterRepository.updateMarqueeMessage(finalMessage,marqueeId);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 			= null;      
		}
	}

	@RequestMapping(value = "/getCompayWiseAlertMarqueeMessage", method = RequestMethod.GET)
	public void getCompayWiseAlertMarqueeMessage(Map<String, Object> map, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<MarqueeMasterDto>	marqueeMasterMessage 	= null;
		Gson 					gson					= null;	
		try {
			marqueeMasterMessage  	= MarqueeMasterService.getCompayWiseMarqueeMessage();
			gson 					= new Gson();
			response.getWriter().write(gson.toJson(marqueeMasterMessage));
		}catch(Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value="/getStockWiseMarqueeAlert" ,method=RequestMethod.GET)
	public void getStockWiseMarqueeAlert(final HttpServletRequest request ,HttpServletResponse response) throws Exception {
		MarqueeMasterDto	marqueeMasterMessage = null;
		ValueObject valueOutObject= null;
		Gson 					gson					= null;
		CustomUserDetails	userDetails	= null;
		HashMap<String, Object> configuration =null;
		HashMap<String, Object> configurationUrea =null;
		List<UreaInvoiceToDetailsDto> minStockUreaList= null;
	
		try {
			valueOutObject = new ValueObject();
			userDetails =(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_INVOICE_CONFIG);
			configurationUrea 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			
			List <FuelStockDetailsDto> fuelStock =fuelInvoiceService.getMinimumfuelStock(userDetails.getCompany_id(),Integer.parseInt(configuration.getOrDefault("fuelThreasholValue", "500")+""));
			if(fuelStock != null && !fuelStock.isEmpty()) {
				valueOutObject.put("fuelStock", fuelStock);
				valueOutObject.put("FuelThreashold", configuration.getOrDefault("fuelThreasholValue", "700"));
			}
			String query =" group by CD.wareHouseLocation HAVING SUM(CD.stockQuantity) < "+ configurationUrea.getOrDefault("ureaNotificationThreashold", 700) +" ";
			minStockUreaList = ureaInvoiceToDetailsService.getAllLocationUreaStockDetails(userDetails.getCompany_id(),query);
			if(minStockUreaList!= null && !minStockUreaList.isEmpty()) {
				valueOutObject.put("UreaStock", minStockUreaList);
				valueOutObject.put("UreaThreashold", configurationUrea.getOrDefault("ureaNotificationThreashold", 700));
			}
			gson 					= new Gson();

			response.getWriter().write(gson.toJson(valueOutObject.getHtData()));
		}catch(Exception e) {
			throw e;
		}
		
	}
	

}