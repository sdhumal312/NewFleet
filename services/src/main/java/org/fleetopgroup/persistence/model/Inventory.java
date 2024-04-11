package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id")
	private Long inventory_id;
	
	@Column(name = "inventory_Number")
	private Long inventory_Number;

	@Column(name = "partid", nullable = false)
	private Long partid;

	@Column(name = "make", length = 150)
	private String make;

	@Column(name = "locationId")
	private Integer locationId;

	@Column(name = "quantity", length = 10)
	private Double quantity;
	
	@Column(name = "mainQuantity", length = 10)
	private Double mainQuantity;
	
	@Column(name = "inTransitQuantity")
	private Double inTransitQuantity;

	@Column(name = "history_quantity", length = 10)
	private Double history_quantity;

	@Column(name = "unitprice", length = 10)
	private Double unitprice;
	
	@Column(name = "mainUnitprice", length = 10)
	private Double mainUnitprice;

	@Column(name = "discount", length = 10)
	private Double discount;

	@Column(name = "tax", length = 10)
	private Double tax;

	@Column(name = "total", length = 10)
	private Double total;
	
	@Column(name = "purchaseorder_id")
	private Long purchaseorder_id;
	
	@Column(name = "purchaseorderto_partid")
	private Long purchaseorderto_partid;

	@Column(name = "invoice_number", length = 25)
	private String invoice_number;

	@Column(name = "invoice_date")
	private Date invoice_date;

	@Column(name = "invoice_amount", length = 30)
	private String invoice_amount;

	@Column(name = "vendor_id", length = 10)
	private Integer vendor_id;

	@Column(name = "description", length = 150)
	private String description;

	@Column(name = "inventory_location_id")
	private Long inventory_location_id;

	@Column(name = "inventory_all_id")
	private Long inventory_all_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "partInvoiceId")
	private Long  partInvoiceId;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "subLocationId")
	private Integer	subLocationId;
	
	@Column(name = "discountTaxTypeId")
	private short discountTaxTypeId;
	
	@Column(name = "serialNoAddedForParts", nullable = false, columnDefinition = "int default 0")
	private int	serialNoAddedForParts;
	
	@Column(name = "repairStockId")
	private Long repairStockId;

	public Inventory() {
		super();
	}

	public Inventory(Long inventory_id,Long inventory_Number, Long partid, String make, Integer location, 
			Double quantity, Double history_quantity, Double unitprice,
			Double discount, Double tax, Double total,  String invoice_number, Date invoice_date,
			String invoice_amount,Integer vendor_id,
			String description, Long inventory_location_id, Long inventory_all_id, Long createdBy,
			Long lastModifiedBy, boolean MarkForDelete, Date created, Date lastupdated, Integer companyId, Long partInvoiceId) {
		super();
		this.inventory_id = inventory_id;
		this.inventory_Number	= inventory_Number;
		this.partid = partid;
		this.make = make;
		this.locationId = location;
		this.quantity = quantity;
		this.history_quantity = history_quantity;
		this.unitprice = unitprice;
		this.discount = discount;
		this.tax = tax;
		this.total = total;
		this.invoice_number = invoice_number;
		this.invoice_date = invoice_date;
		this.invoice_amount = invoice_amount;
		//this.unittype = unittype;
		this.vendor_id = vendor_id;
		//this.vendor_name = vendor_name;
		//this.vendor_location = vendor_location;
		this.description = description;
		this.inventory_location_id = inventory_location_id;
		this.inventory_all_id = inventory_all_id;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.partInvoiceId = partInvoiceId;
	}
	
	
	

	public Long getPartInvoiceId() {
		return partInvoiceId;
	}

	public void setPartInvoiceId(Long partInvoiceId) {
		this.partInvoiceId = partInvoiceId;
	}

	public Long getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(Long inventory_id) {
		this.inventory_id = inventory_id;
	}

	public Long getInventory_Number() {
		return inventory_Number;
	}

	public void setInventory_Number(Long inventory_Number) {
		this.inventory_Number = inventory_Number;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}



	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}



	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getHistory_quantity() {
		return history_quantity;
	}

	public void setHistory_quantity(Double history_quantity) {
		this.history_quantity = history_quantity;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public Date getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}


	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	/*public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getVendor_location() {
		return vendor_location;
	}

	public void setVendor_location(String vendor_location) {
		this.vendor_location = vendor_location;
	}
*/
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getInventory_location_id() {
		return inventory_location_id;
	}

	public void setInventory_location_id(Long inventory_location_id) {
		this.inventory_location_id = inventory_location_id;
	}

	public Long getInventory_all_id() {
		return inventory_all_id;
	}

	public void setInventory_all_id(Long inventory_all_id) {
		this.inventory_all_id = inventory_all_id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}*/

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	public int getSerialNoAddedForParts() {
		return serialNoAddedForParts;
	}

	public void setSerialNoAddedForParts(int serialNoAddedForParts) {
		this.serialNoAddedForParts = serialNoAddedForParts;
	}

	public Double getMainQuantity() {
		return mainQuantity;
	}

	public void setMainQuantity(Double mainQuantity) {
		this.mainQuantity = mainQuantity;
	}

	public Double getMainUnitprice() {
		return mainUnitprice;
	}

	public void setMainUnitprice(Double mainUnitprice) {
		this.mainUnitprice = mainUnitprice;
	}
	
	public Long getRepairStockId() {
		return repairStockId;
	}

	public Double getInTransitQuantity() {
		return inTransitQuantity;
	}

	public void setInTransitQuantity(Double inTransitQuantity) {
		this.inTransitQuantity = inTransitQuantity;
	}

	public void setRepairStockId(Long repairStockId) {
		this.repairStockId = repairStockId;
	}
	
	

	public Long getPurchaseorderto_partid() {
		return purchaseorderto_partid;
	}

	public void setPurchaseorderto_partid(Long purchaseorderto_partid) {
		this.purchaseorderto_partid = purchaseorderto_partid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventory_all_id == null) ? 0 : inventory_all_id.hashCode());
		result = prime * result + ((inventory_id == null) ? 0 : inventory_id.hashCode());
		result = prime * result + ((invoice_date == null) ? 0 : invoice_date.hashCode());
		result = prime * result + ((invoice_number == null) ? 0 : invoice_number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (inventory_all_id == null) {
			if (other.inventory_all_id != null)
				return false;
		} else if (!inventory_all_id.equals(other.inventory_all_id))
			return false;
		if (inventory_id == null) {
			if (other.inventory_id != null)
				return false;
		} else if (!inventory_id.equals(other.inventory_id))
			return false;
		if (invoice_date == null) {
			if (other.invoice_date != null)
				return false;
		} else if (!invoice_date.equals(other.invoice_date))
			return false;
		if (invoice_number == null) {
			if (other.invoice_number != null)
				return false;
		} else if (!invoice_number.equals(other.invoice_number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Inventory [inventory_id=");
		builder.append(inventory_id);
		builder.append(", inventory_Number=");
		builder.append(inventory_Number);
		builder.append(", partid=");
		builder.append(partid);
		/*builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", part_photoid=");
		builder.append(part_photoid);
		builder.append(", parttype=");
		builder.append(parttype);
		builder.append(", partTypeId=");
		builder.append(partTypeId);
		builder.append(", category=");
		builder.append(category);*/
		builder.append(", make=");
		builder.append(make);
		/*builder.append(", location=");
		builder.append(location);*/
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", history_quantity=");
		builder.append(history_quantity);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", total=");
		builder.append(total);
		builder.append(", invoice_number=");
		builder.append(invoice_number);
		builder.append(", invoice_date=");
		builder.append(invoice_date);
		builder.append(", invoice_amount=");
		builder.append(invoice_amount);
		/*builder.append(", unittype=");
		builder.append(unittype);*/
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		/*builder.append(", vendor_name=");
		builder.append(vendor_name);
		builder.append(", vendor_location=");
		builder.append(vendor_location);*/
		builder.append(", description=");
		builder.append(description);
		builder.append(", inventory_location_id=");
		builder.append(inventory_location_id);
		builder.append(", inventory_all_id=");
		builder.append(inventory_all_id);
		builder.append(", companyId=");
		builder.append(companyId);
		/*builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);*/
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", partInvoiceId=");
		builder.append(partInvoiceId);
		builder.append(", subLocationId=");
		builder.append(subLocationId);
		builder.append("]");
		return builder.toString();
	}

}