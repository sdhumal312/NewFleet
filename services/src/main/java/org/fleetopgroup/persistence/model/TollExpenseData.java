package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TollExpenseData")
public class TollExpenseData {

	@Id
	@Column(name = "tollExpenseDataId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tollExpenseDataId;
	
	@Nullable
	@Column(name = "push_id")
	private String push_id;
	
	@Nullable
	@Column(name = "customer_id")
	private String customer_id;
	
	@Nullable
	@Column(name = "customer_name")
	private String customer_name;
	
	@Nullable
	@Column(name = "customer_first_name")
	private String customer_first_name;

	@Nullable
	@Column(name = "customer_last_name")
	private String customer_last_name;
	
	@Nullable
	@Column(name = "mobile_number")
	private String mobile_number;
	
	@Nullable
	@Column(name = "email_id")
	private String  email_id;
	
	@Nullable
	@Column(name = "tag_id")
	private String tag_id;
	
	@Nullable
	@Column(name = "vehicle_id")
	private String vehicle_id;
	
	@Nullable
	@Column(name = "vehicle_no")
	private String vehicle_no;
	
	@Column(name = "transaction_datetime")
	private Date transaction_datetime;
	
	@Nullable
	@Column(name = "reader_datetime")
	private Date reader_datetime;
	
	@Column(name = "transaction_amount")
	private Double transaction_amount;
	
	@Nullable
	@Column(name = "balance_amount")
	private Double balance_amount;
	
	@Nullable
	@Column(name = "transaction_reference_number")
	private String transaction_reference_number;
	
	@Nullable
	@Column(name = "transaction_id")
	private String transaction_id;
	
	@Nullable
	@Column(name = "transaction_type")
	private String transaction_type;
	
	@Nullable
	@Column(name = "transaction_status")
	private String transaction_status;
	
	@Nullable
	@Column(name = "lane_code")
	private String lane_code;
	
	@Nullable
	@Column(name = "plaza_code")
	private String plaza_code;
	
	@Nullable
	@Column(name = "plaza_name")
	private String plaza_name;
	
	@Nullable
	@Column(name = "plazaName")
	private String plazaName;
	
	@Nullable
	@Column(name = "direction")
	private String direction;
	
	@Nullable
	@Column(name = "lat")
	private String lat;
	
	@Nullable
	@Column(name = "longitude")
	private String longitude;
	
	@Nullable
	@Column(name = "rrn")
	private String rrn;
	
	@Nullable
	@Column(name = "remark")
	private String remark;
	
	@Nullable
	@Column(name = "comments")
	private String comments;
	
	@Nullable
	@Column(name = "currency")
	private String currency;

	public String getPush_id() {
		return push_id;
	}

	public void setPush_id(String push_id) {
		this.push_id = push_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_first_name() {
		return customer_first_name;
	}

	public void setCustomer_first_name(String customer_first_name) {
		this.customer_first_name = customer_first_name;
	}

	public String getCustomer_last_name() {
		return customer_last_name;
	}

	public void setCustomer_last_name(String customer_last_name) {
		this.customer_last_name = customer_last_name;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public String getVehicle_no() {
		return vehicle_no;
	}

	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}


	public Long getTollExpenseDataId() {
		return tollExpenseDataId;
	}

	public void setTollExpenseDataId(Long tollExpenseDataId) {
		this.tollExpenseDataId = tollExpenseDataId;
	}

	public Date getTransaction_datetime() {
		return transaction_datetime;
	}

	public void setTransaction_datetime(Date transaction_datetime) {
		this.transaction_datetime = transaction_datetime;
	}

	public Date getReader_datetime() {
		return reader_datetime;
	}

	public void setReader_datetime(Date reader_datetime) {
		this.reader_datetime = reader_datetime;
	}

	public Double getTransaction_amount() {
		return transaction_amount;
	}

	public void setTransaction_amount(Double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}

	public Double getBalance_amount() {
		return balance_amount;
	}

	public void setBalance_amount(Double balance_amount) {
		this.balance_amount = balance_amount;
	}

	public String getTransaction_reference_number() {
		return transaction_reference_number;
	}

	public void setTransaction_reference_number(String transaction_reference_number) {
		this.transaction_reference_number = transaction_reference_number;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getTransaction_status() {
		return transaction_status;
	}

	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}

	public String getLane_code() {
		return lane_code;
	}

	public void setLane_code(String lane_code) {
		this.lane_code = lane_code;
	}

	public String getPlaza_code() {
		return plaza_code;
	}

	public void setPlaza_code(String plaza_code) {
		this.plaza_code = plaza_code;
	}

	public String getPlaza_name() {
		return plaza_name;
	}

	public void setPlaza_name(String plaza_name) {
		this.plaza_name = plaza_name;
	}

	public String getPlazaName() {
		return plazaName;
	}

	public void setPlazaName(String plazaName) {
		this.plazaName = plazaName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "TollExpenseData [tollExpenseDataId=" + tollExpenseDataId + ", push_id=" + push_id + ", customer_id="
				+ customer_id + ", customer_name=" + customer_name + ", customer_first_name=" + customer_first_name
				+ ", customer_last_name=" + customer_last_name + ", mobile_number=" + mobile_number + ", email_id="
				+ email_id + ", tag_id=" + tag_id + ", vehicle_id=" + vehicle_id + ", vehicle_no=" + vehicle_no
				+ ", transaction_datetime=" + transaction_datetime + ", reader_datetime=" + reader_datetime
				+ ", transaction_amount=" + transaction_amount + ", balance_amount=" + balance_amount
				+ ", transaction_reference_number=" + transaction_reference_number + ", transaction_id="
				+ transaction_id + ", transaction_type=" + transaction_type + ", transaction_status="
				+ transaction_status + ", lane_code=" + lane_code + ", plaza_code=" + plaza_code + ", plaza_name="
				+ plaza_name + ", plazaName=" + plazaName + ", direction=" + direction + ", lat=" + lat + ", longitude="
				+ longitude + ", rrn=" + rrn + ", remark=" + remark + ", comments=" + comments + ", currency="
				+ currency + "]";
	}
	
	

}
