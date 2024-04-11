package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.BatteryAmountRepository;
import org.fleetopgroup.persistence.dto.BatteryAmountDto;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryAmountService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BatteryAmountService implements IBatteryAmountService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired	private BatteryAmountRepository			batteryAmountRepository;
	@Autowired	private IBatteryInvoiceService			batteryInvoiceService;
	@Autowired	private IPendingVendorPaymentService	pendingVendorPaymentService;
	@Autowired	private ICompanyConfigurationService	companyConfigurationService;
	@Autowired	private IBankPaymentService				bankPaymentService;
	@Autowired	private IVendorService					vendorService;

	@Override
	public List<BatteryAmountDto> getBatteryAmountList(BatteryInvoiceDto battery) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.batteryAmountId, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryQuantity, BA.unitCost,"
							+ " BA.discount, BA.tax, BA.totalAmount, BA.wareHouseLocation, BA.batteryInvoiceId, BA.batteryAsignNumber,"
							+ " PL.partlocation_name, BM.manufacturerName, BT.batteryType, BA.batteryCapacityId, BC.batteryCapacity, BT.partNumber,"
							+ " BT.warrantyPeriod, BT.warrantyTypeId, BA.discountTaxTypeId "
							+ " FROM BatteryAmount AS BA "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocation "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId "
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId= BA.batteryCapacityId"
							+ " where BA.batteryInvoiceId = "+battery.getBatteryInvoiceId()+" AND BA.companyId = "+battery.getCompanyId()+" AND  BA.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryAmountDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryAmountDto	batteryInvoice = new BatteryAmountDto();
					
					batteryInvoice.setBatteryAmountId((Long) result[0]);
					batteryInvoice.setBatteryManufacturerId((Long) result[1]);
					batteryInvoice.setBatteryTypeId((Long) result[2]);
					batteryInvoice.setBatteryQuantity((Long) result[3]);
					batteryInvoice.setUnitCost((Double) result[4]);
					batteryInvoice.setDiscount((Double) result[5]);
					batteryInvoice.setTax((Double) result[6]);
					batteryInvoice.setTotalAmount((Double) result[7]);
					batteryInvoice.setWareHouseLocation((Integer) result[8]);
					batteryInvoice.setBatteryInvoiceId((Long) result[9]);
					batteryInvoice.setBatteryAsignNumber((Integer) result[10]);
					batteryInvoice.setLocationName((String) result[11]);
					batteryInvoice.setManufacturerName((String) result[12]);
					batteryInvoice.setBatteryType((String) result[13]);
					batteryInvoice.setBatteryCapacityId((Long) result[14]);
					batteryInvoice.setBatteryCapacity((String) result[15]);
					batteryInvoice.setPartNumber((String) result[16]);
					batteryInvoice.setWarrantyPeriod( (Integer) result[17]);
					batteryInvoice.setWarrantyTypeId((short) result[18]);
					batteryInvoice.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[18]));
					
					if(result[19] != null) {
						batteryInvoice.setDiscountTaxTypeId((short) result[19]);
					} else {
						batteryInvoice.setDiscountTaxTypeId((short) 1);
					}
					
					if(batteryInvoice.getPartNumber() != null) {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() + "-"+batteryInvoice.getPartNumber()+"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}else {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() +"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}
					
					
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	@Transactional
	public void updateBatteryAsignNumber(Integer asigned, Long amountId, Integer companyId) throws Exception {
		try {
			entityManager.createQuery("UPDATE BatteryAmount  SET batteryAsignNumber = batteryAsignNumber - "
					+ asigned + " where batteryAmountId=" + amountId+" AND companyId = "+companyId+"").executeUpdate();
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void AddBatteryAsignNumber(Integer asigned, Long amountId, Integer companyId) throws Exception {
		try {
			entityManager.createQuery("UPDATE BatteryAmount  SET batteryAsignNumber = batteryAsignNumber + "
					+ asigned + " where batteryAmountId=" + amountId+" AND companyId = "+companyId+"").executeUpdate();
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteBatteryAmountDetails(ValueObject valueObject) throws Exception {
		Optional<BatteryAmount>			batteryAmount		= null;
		CustomUserDetails				userDetails			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	= batteryAmountRepository.findById(valueObject.getLong("batteryAmountId"));
			
			updateInvoiceAmountToBatteryInvoice(batteryAmount.get().getTotalAmount(), batteryAmount.get().getBatteryInvoiceId(), userDetails.getCompany_id());
			BatteryInvoice updatedBatteryInvoice =batteryInvoiceService.getBatteryInvoiceByBatteryInvoiceId(batteryAmount.get().getBatteryInvoiceId(), userDetails.getCompany_id());
			
			batteryAmountRepository.deleteBatteryAmount(valueObject.getLong("batteryAmountId"));
			
			pendingVendorPaymentService.deletePendingVendorPaymentAmt(batteryAmount.get().getBatteryInvoiceId(), PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE, batteryAmount.get().getTotalAmount());
			
			HashMap<String,Object> companyConfiguration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
			if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
			ValueObject bankPaymentValueObject=new ValueObject();
			bankPaymentValueObject.put("oldPaymentTypeId",updatedBatteryInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("bankPaymentTypeId", updatedBatteryInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("currentPaymentTypeId", updatedBatteryInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("userId",userDetails.getId());
			bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
			bankPaymentValueObject.put("moduleId",batteryAmount.get().getBatteryInvoiceId());
			bankPaymentValueObject.put("moduleNo",updatedBatteryInvoice.getBatteryInvoiceNumber());
			bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.BATTRRY_INVENTORY);
			bankPaymentValueObject.put("amount",updatedBatteryInvoice.getInvoiceAmount());
			
			Vendor	vendor	=  vendorService.getVendor(updatedBatteryInvoice.getVendorId());
			bankPaymentValueObject.put("remark", " BI-"+updatedBatteryInvoice.getBatteryInvoiceNumber()+" vendor : "+vendor.getVendorName());
			
			bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
			}
			
			valueObject.put("deleted", true);
			valueObject.put("batteryInvoice", batteryAmount.get().getBatteryInvoiceId());
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			batteryAmount		= null;
			userDetails			= null;
		}
	}
	
	@Override
	@Transactional
	public void updateInvoiceAmountToBatteryInvoice(Double amount, Long invoiceId, Integer companyId) throws Exception {

		entityManager.createQuery("UPDATE BatteryInvoice  SET invoiceAmount = invoiceAmount - " + amount
				+ " where batteryInvoiceId=" + invoiceId+" AND companyId = "+companyId).executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateTotalAmountToBatteryAmount(Double amount, Long batteryAmountId, Integer companyId)
			throws Exception {

		entityManager.createQuery("UPDATE BatteryAmount  SET totalAmount = " + amount
				+ " where batteryAmountId=" + batteryAmountId+" AND companyId = "+companyId).executeUpdate();
	}
	
	@Override
	public void registerBatteryAmount(BatteryAmount batteryAmount) throws Exception {

		batteryAmountRepository.save(batteryAmount);
	}
}
