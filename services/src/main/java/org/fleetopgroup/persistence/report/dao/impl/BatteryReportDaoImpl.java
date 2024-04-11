package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.BatteryAmountDto;
import org.fleetopgroup.persistence.report.dao.IBatteryReportDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BatteryReportDaoImpl implements IBatteryReportDao {
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	DecimalFormat toFixedTwo =new DecimalFormat("#.##");
	
	@PersistenceContext	EntityManager entityManager;
	
	
	@Transactional
	public List<BatteryAmountDto> Battery_Purchase_Invoice_Report_List(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					" select BA.batteryAmountId, BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount,  "
							+ " BI.vendorId, V.vendorName, BA.batteryManufacturerId, BM.manufacturerName, BA.batteryTypeId, BT.batteryType, "
							+ " BA.batteryQuantity, BA.unitCost, BA.discount, BA.tax, BA.totalAmount, BA.discountTaxTypeId "
							+ " FROM BatteryAmount BA "
							+ " INNER JOIN BatteryInvoice BI on BI.batteryInvoiceId = BA.batteryInvoiceId "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId "
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
							+ " where BI.companyId = "+companyId+" AND BA.markForDelete = 0 AND BI.markForDelete = 0 " + Query + " ",
					Object[].class);
			//queryt.setParameter("comID", companyId);
			List<Object[]> results = queryt.getResultList();

			List<BatteryAmountDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<BatteryAmountDto>();
				BatteryAmountDto Dto = null;
				for (Object[] result : results) {
					Dto = new BatteryAmountDto();

					Dto.setBatteryAmountId((long) result[0]);
					Dto.setBatteryInvoiceId((long) result[1]);
					Dto.setBatteryInvoiceNumber((long) result[2]);
					if(result[3] != null)
					Dto.setInvoiceNumber((String) result[3]);
					Dto.setInvoiceDate(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[4], DateTimeUtility.DD_MM_YY));
					if(result[5] != null)
					Dto.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[5])));
					Dto.setVendorId((int) result[6]);
					Dto.setVendorName((String) result[7]);
					Dto.setBatteryManufacturerId((long) result[8]);
					Dto.setManufacturerName((String) result[9]);
					Dto.setBatteryTypeId((long) result[10]);
					Dto.setBatteryType((String) result[11]);
					if(result[12] != null)
					Dto.setBatteryQuantity((long) result[12]);
					if(result[13] != null)
					Dto.setUnitCost(Double.parseDouble(toFixedTwo.format(result[13])));
					if(result[14] != null)
					Dto.setDiscount(Double.parseDouble(toFixedTwo.format(result[14])) );
					if(result[15] != null)
					Dto.setTax((Double) result[15]);
					if(result[16] != null)
					Dto.setTotalAmount(Double.parseDouble(toFixedTwo.format(result[16])) );
					Dto.setDiscountTaxTypeId((short) result[17]);

					Dtos.add(Dto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
			queryt = null;
		}
	}
	
	
	
}