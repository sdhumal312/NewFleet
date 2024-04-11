<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newRole.html?id=${user.id}"/>"> <spring:message
							code="label.master.NewVehicleRole" /></a> /
					<spring:message code="label.master.AddRole" />
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_ROLE')">
						<a type="button" class="btn btn-success btn-sm"
							href="<c:url value="/addRole.html?id=${user.id}" />"> <i
							class="fa fa-plus"></i> <spring:message
								code="label.master.AddRole" />
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_ROLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ROLE')">
				<div class="col-md-offset-1 col-xs-9">
					<!-- main body in vehicle page -->
					<div class="main-body">
						<form id="myForm" method="post"
							onsubmit="return validateRoleUpdate()">
							<div class="box">
								<div class="box-header">
									<h3 class="box-title">
										<spring:message code="label.master.AddRole" />
									</h3>
								</div>
								<div class="box-body">
									<div class="row1">
										<label class="L-size control-label" for="vehicle_theft">
											<spring:message code="label.master.NewVehicleRole" /> :
										</label>
										<div class="I-size">
											<input name="id" type="hidden" value="${role.id}"
												Class="form-text" required="required" /> <input name="name"
												id="vRoleUpdate" value="${role.name}" Class="form-text"
												readonly="readonly" required="required"
												placeholder="Enter Role Name" /> <label id="errorEditRole"
												class="error"></label>
<input type="hidden" id="companyId" value="${companyId}" name="companyId">
										</div>
									</div>
								</div>
							</div>
							<div class="box">
								<div class="box-header">
									<h3 class="box-title">
										<spring:message code="label.pages.rolepermission" />
									</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<input type="hidden" name="privileges" value="READ_PRIVILEGE" />

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												<spring:message code="label.pages.mastersettings" /> :
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_PRIVILEGE')}"> 
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VIEW_PRIVILEGE"
													id="All-master" autocomplete="off" /> <spring:message
														code="label.pages.masterview" />
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_PRIVILEGE')}"> 
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_PRIVILEGE"
													id="All-master" autocomplete="off" /> <spring:message
														code="label.pages.mastercreate" />
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_PRIVILEGE')}"> 
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="EDIT_PRIVILEGE"
													id="All-master" autocomplete="off" /> <spring:message
														code="label.pages.masteredit" />
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_PRIVILEGE')}"> 
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_PRIVILEGE"
												id="All-master" autocomplete="off" /> <spring:message
													code="label.pages.masterdelete" />
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_BANK_ACCOUNT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BANK_ACCOUNT"
												id="All-master" autocomplete="off" /> Add Bank Account
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_BANK_ACCOUNT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_BANK_ACCOUNT"
												id="All-master" autocomplete="off" /> View Bank Account
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_MASTER_DOCUMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_MASTER_DOCUMENT"
													id="All-master" autocomplete="off" /> Add Master Document
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('RR_NOTIFICATION')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="RR_NOTIFICATION"
													id="All-master" autocomplete="off" /> Renewal Reminder Notification
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('NUM_OF_BACK_DATE_FOR_ADMIN')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="NUM_OF_BACK_DATE_FOR_ADMIN"
													id="All-master" autocomplete="off" /> Back Date For Admin
												</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" id="master" autocomplete="off" /> <spring:message
													code="label.pages.masterall" />
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<div class="box">
								<div class="box-header">
									<h3 class="box-title">Role_User Details</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">

										<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
												Company :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_COMPANY')}">	
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VIEW_COMPANY"
													id="company" autocomplete="off" /> View Company Details
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_COMPANY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_COMPANY"
													id="company" autocomplete="off" /> Create/Edit New Sub
													Company Details
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('ADD_BANK_COMPANY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_BANK_COMPANY"
													id="company" autocomplete="off" /> Create\Edit\Delete Bank
													Details
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_DIRECTOR_COMPANY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ADD_DIRECTOR_COMPANY" id="company" autocomplete="off" />
													Create\Edit\Delete Owner/Director Details
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_SUB_COMPANY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ADD_SUB_COMPANY" id="company" autocomplete="off" />
													Add Sub Company
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_SUB_COMPANY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="SHOW_SUB_COMPANY" id="company" autocomplete="off" />
													Show Sub Company
												</label>
											</c:if>
										</div>
										<br>
										<div class="row1">
											<c:if test = "${feildPrivilege.contains('VIEW_DASHBOARD')}">
											<label class="L-size control-label" for="vehicle_theft">
												Company DashBoard :</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DASHBOARD')}">	
												<label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="VIEW_DASHBOARD" id="company" autocomplete="off" />
													View Company DashBoard
												</label>
											</c:if>
										</div>
										<div class="row1">
											<c:if test = "${feildPrivilege.contains('SHOW_WORK_SUMMARY')}">
											<label class="L-size control-label" for="vehicle_theft">
												Work Summary (New dashboard) :</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_WORK_SUMMARY')}">	
												<label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="SHOW_WORK_SUMMARY" id="company" autocomplete="off" />
													View Work Summary
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_CONSUMPTION_SUMMARY')}">	
												<label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="SHOW_CONSUMPTION_SUMMARY" id="company" autocomplete="off" />
													Show Consumption Summary
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_COMPANYLIST_DASHBOARD')}">	
												<label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="SHOW_COMPANYLIST_DASHBOARD" id="company" autocomplete="off" />
													Show CompanyList Dropdown For Work Summary
												</label>
											</c:if>
										</div>
										<br>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Branch
												:</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_BRANCH')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_BRANCH"
												id="company" autocomplete="off" /> View Branch Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_BRANCH')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BRANCH"
												id="company" autocomplete="off" /> Create\Edit\Delete
												Branch Details
											</label>
											</c:if>
										</div>
										<br>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Department
												:</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DEPARTMENT')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DEPARTMENT"
												id="company" autocomplete="off" /> View Department Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_DEPARTMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_DEPARTMENT"
													id="company" autocomplete="off" /> Create\Edit\Delete
													Department Details
												</label>
											</c:if>
										</div>
										<br>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												User :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_USER')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_USER"
												id="company" autocomplete="off" /> View User Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_USER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_USER"
												id="company" autocomplete="off" /> Create New User Vehicle
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('EDIT_USER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_USER"
												id="company" autocomplete="off" /> Edit/UpdatePassword User
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ACTIVE_USER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ACTIVE_USER"
												id="company" autocomplete="off" /> Active/InActive User
												Details
											</label>
											</c:if>
										</div>
										<br>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Role :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_ROLE')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_ROLE"
												id="company" autocomplete="off" /> View Role Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_ROLE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_ROLE"
													id="company" autocomplete="off" /> Create/Edit Role Vehicle
													Details
												</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Job Type :</label> 
											<c:if test = "${feildPrivilege.contains('ADD_JOB_TYPE')}">		
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_JOB_TYPE"
													id="company" autocomplete="off" /> Add Job Type
												</label> 
											</c:if>	
											<c:if test = "${feildPrivilege.contains('ADD_JOB_SUB_TYPE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_JOB_SUB_TYPE"
													id="company" autocomplete="off" /> Add Job SubType
													Details
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_REASON_REPAIR_TYPES')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_REASON_REPAIR_TYPES"
													id="company" autocomplete="off" /> Add Reason Repair Type
													Details
												</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="All-company" autocomplete="off" />
												All Company Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_4" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Vehicle</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VEHICLE"
												id="vehicle" autocomplete="off" /> View Vehicle Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_VEHICLE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_VEHICLE"
												id="vehicle" autocomplete="off" /> Create New Vehicle
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('EDIT_VEHICLE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_VEHICLE"
												id="vehicle" autocomplete="off" /> Edit/Update Vehicle
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VEHICLE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_VEHICLE"
												id="vehicle" autocomplete="off" /> Delete Vehicle Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Import :</label> 
											<c:if test = "${feildPrivilege.contains('IMPORT_VEHICLE')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="IMPORT_VEHICLE"
												id="vehicle" autocomplete="off" /> Import Vehicle Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DOWNLOND_VEHICLE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DOWNLOND_VEHICLE"
												id="vehicle" autocomplete="off" />Download/Print Vehicle
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Document :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VEHICLE_DOCUMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_VEHICLE_DOCUMENT" id="vehicle"
												autocomplete="off" />Create &amp; Edit/Update Vehicle
												Document Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VEHICLE_DOCUMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="DELETE_VEHICLE_DOCUMENT" id="vehicle"
													autocomplete="off" />Delete Vehicle Document Details
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Photo :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VEHICLE_PHOTO')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_VEHICLE_PHOTO" id="vehicle"
												autocomplete="off" />Create &amp; Edit/Update Vehicle
												Photos Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VEHICLE_PHOTO')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VEHICLE_PHOTO" id="vehicle" autocomplete="off" />Delete
												Vehicle Photos Details
											</label>
										</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Purchase Info :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VEHICLE_PURCHASE')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="ADDEDIT_VEHICLE_PURCHASE" id="vehicle"
												autocomplete="off" />Create &amp; Edit/Update Vehicle
												Purchase Info Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VEHICLE_PURCHASE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VEHICLE_PURCHASE" id="vehicle"
												autocomplete="off" />Delete Vehicle Purchase Info Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Odometer History :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VEHICLE_ODOMETER')}">
												<label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="ADDEDIT_VEHICLE_ODOMETER" id="vehicle"
													autocomplete="off" />Create New Vehicle Odometer
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Comment :</label> 
											<c:if test = "${feildPrivilege.contains('EDIT_PRIVILEGE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_VEHICLE_COMMENT" id="vehicle"
												autocomplete="off" />Add &amp; Edit/Update Vehicle Comment
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_PRIVILEGE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VEHICLE_COMMENT" id="vehicle"
												autocomplete="off" />Delete Vehicle Comment Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Tyre :</label> 
											<c:if test = "${feildPrivilege.contains('EDIT_PRIVILEGE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_VEHICLE_TYRE" id="vehicle" autocomplete="off" />Add
												&amp; Edit/Update Vehicle Tyre Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VEHICLE_TYRE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VEHICLE_TYRE" id="vehicle" autocomplete="off" />Delete
												Vehicle Tyre Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle EMI :</label> 
											<c:if test = "${feildPrivilege.contains('ADD_EMI_DETAILS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_EMI_DETAILS" id="vehicle" autocomplete="off" />Add
												 EMI Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_EMI_DETAILS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_EMI_DETAILS" id="vehicle" autocomplete="off" />View
												EMI Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_EMI_DETAILS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_EMI_DETAILS" id="vehicle" autocomplete="off" />Edit
												EMI Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Master :</label> 
											<c:if test = "${feildPrivilege.contains('SHOW_VEHICLE_TOLL_MASTER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_VEHICLE_TOLL_MASTER" id="vehicle" autocomplete="off" />Vehicle Toll 
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_VEHICLE_GPS_MASTER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_VEHICLE_GPS_MASTER" id="vehicle" autocomplete="off" />Vehicle GPS
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('METER_NOT_WORKING')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="METER_NOT_WORKING" id="vehicle" autocomplete="off" />Meter Not Working
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_VEHICLE_MANUFACTURER_MASTER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_VEHICLE_MANUFACTURER_MASTER" id="vehicle" autocomplete="off" />Vehicle Manufacturer
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_VEHICLE_MODEL_MASTER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_VEHICLE_MODEL_MASTER" id="vehicle" autocomplete="off" />Vehicle Model
											</label>
											</c:if>
												<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE_BODY_MAKER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VEHICLE_BODY_MAKER" id="vehicle" autocomplete="off" />Vehicle Body Maker
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Tyre Assignment:</label> 
											<c:if test = "${feildPrivilege.contains('VEHICLE_TYRE_ASSIGNMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLE_TYRE_ASSIGNMENT" id="vehicle" autocomplete="off" />Vehicle Tyre Assignment
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Accident Type :</label> 
											<c:if test = "${feildPrivilege.contains('VEHICLE_ACCIDENT_TYPE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLE_ACCIDENT_TYPE" id="vehicle" autocomplete="off" />Vehicle Accident Type 
											</label>
											</c:if>
										</div>
											<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Bus Booking Location:</label>
												<c:if test = "${feildPrivilege.contains('BUS_BOOKING_LOCATION')}">
												 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="BUS_BOOKING_LOCATION" id="vehicle" autocomplete="off" />
												show Bus Location 
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="All-vehicle" autocomplete="off" />
												All Vehicle Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_5" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Driver</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DRIVER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DRIVER"
												id="driver" autocomplete="off" /> View Driver Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_DRIVER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_DRIVER"
												id="driver" autocomplete="off" /> Create New Driver Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DRIVER_TRAINING_TYPE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DRIVER_TRAINING_TYPE"
												id="driver" autocomplete="off" /> Driver Training Type
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('EDIT_DRIVER')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="EDIT_DRIVER"
													id="driver" autocomplete="off" /> Edit/Update Driver
													Details
												</label> 
											</c:if>
											<c:if
												test="${feildPrivilege.contains('INACTIVE_DRIVER_EDIT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="INACTIVE_DRIVER_EDIT" id="driver" autocomplete="off" />
													Update Inactive Driver Details
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="DELETE_DRIVER"
													id="driver" autocomplete="off" /> Delete Driver Details
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_ALL_DRIVER_SALARY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="EDIT_ALL_DRIVER_SALARY"
													id="driver" autocomplete="off" /> EDIT ALL Driver Salary Details
												</label>
											</c:if>
										</div>
								
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Import :</label>
											<c:if test = "${feildPrivilege.contains('IMPORT_DRIVER')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="IMPORT_DRIVER"
												id="driver" autocomplete="off" /> Import Driver Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DOWNLOND_DRIVER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DOWNLOND_DRIVER"
												id="driver" autocomplete="off" />Download Driver Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Bata :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DRIVER_BATA')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DRIVER_BATA"
												id="driver" autocomplete="off" /> View Driver Bata Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_DRIVER_BATA')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_DRIVER_BATA"
												id="driver" autocomplete="off" /> Create Driver Bata
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('EDIT_DRIVER_BATA')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_DRIVER_BATA"
												id="driver" autocomplete="off" /> Edit/Update Driver Bata
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_BATA')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_DRIVER_BATA"
												id="driver" autocomplete="off" /> Delete Driver Bata
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Bata :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DRIVER_ATTENDANCE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DRIVER_ATTENDANCE" id="driver"
												autocomplete="off" /> View Driver Attendance Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DRIVER_ATTENDANCEPOINT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DRIVER_ATTENDANCEPOINT" id="driver"
												autocomplete="off" /> View Driver Attendance Point Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Document :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DRIVER_DOCUMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_DRIVER_DOCUMENT" id="driver"
												autocomplete="off" />Create &amp; Edit/Update Driver
												Document Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_DOCUMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_DRIVER_DOCUMENT" id="driver"
												autocomplete="off" />Delete Driver Document Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Reminder :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DRIVER_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_DRIVER_REMINDER" id="driver"
												autocomplete="off" />Create &amp; Edit/Update Driver
												Reminders Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_DRIVER_REMINDER" id="driver"
												autocomplete="off" />Delete Driver Reminders Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Comment :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DRIVER_COMMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ADDEDIT_DRIVER_COMMENT" id="driver"
													autocomplete="off" />Create &amp; Edit/Update Driver
													Comments Details
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_COMMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="DELETE_DRIVER_COMMENT" id="driver" autocomplete="off" />Delete
													Driver Comments Details
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Photo :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DRIVER_PHOTO')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_DRIVER_PHOTO" id="driver" autocomplete="off" />Add
												&amp; Edit/Update Driver Photo Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_PHOTO')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_DRIVER_PHOTO" id="driver" autocomplete="off" />Delete
												Driver Photo Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('PRINT_DRIVER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="PRINT_DRIVER"
												id="driver" autocomplete="off" /> Print Driver Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Advance :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DRIVER_ADVANCE')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DRIVER_ADVANCE" id="driver" autocomplete="off" />View
												Driver Advance Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DRIVER_ADVANCE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_DRIVER_ADVANCE" id="driver"
												autocomplete="off" />Create Driver Advance Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_ADVANCE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_DRIVER_ADVANCE" id="driver" autocomplete="off" />
												Delete Driver Advance Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Salary :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DRIVER_SALARY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VIEW_DRIVER_SALARY"
													id="driver" autocomplete="off" />View Driver Salary Details
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DRIVER_SALARY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ADDEDIT_DRIVER_SALARY" id="driver" autocomplete="off" />Create
													Driver Salary Details
												</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Salary :</label> 
											<c:if test = "${feildPrivilege.contains('PAY_DRIVER_SALARY')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="PAY_DRIVER_SALARY"
												id="driver" autocomplete="off" />Pay Driver Salary Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DRIVER_SALARY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_DRIVER_SALARY" id="driver" autocomplete="off" />Delete
												Driver Salary Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Checking Entry :</label> 
											<c:if test = "${feildPrivilege.contains('ADD_CHECKING_ENTRY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_CHECKING_ENTRY"
												id="driver" autocomplete="off" />Add Checking Entry
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_CHECKING_ENTRY')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_CHECKING_ENTRY" id="driver" autocomplete="off" />View Checking Entry
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_CHECKING_ENTRY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_CHECKING_ENTRY" id="driver" autocomplete="off" />Edit Checking Entry
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_CHECKING_ENTRY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_CHECKING_ENTRY" id="driver" autocomplete="off" />Delete Checking Entry
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"> Driver Basic Details :</label> 
											<c:if test = "${feildPrivilege.contains('DRIVER_BASIC_DETAILS')}">	
												<label class="checkbox-inline"> 
													<input type="checkbox" name="privileges" value="DRIVER_BASIC_DETAILS"
													id="driver" autocomplete="off" />Driver Basic Details
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DRIVER_BASIC_DETAILS_TYPE')}">
												<label class="checkbox-inline"> 
													<input type="checkbox" name="privileges" value="DRIVER_BASIC_DETAILS_TYPE" 
													id="driver" autocomplete="off" />Driver Basic Details Type
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="All-Driver" autocomplete="off" />
												All Driver Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_6" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Renewal Reminder</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Renewal :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_RENEWAL')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_RENEWAL"
												id="renewal" autocomplete="off" /> View Renewal Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ALLOW_ALL_STATUS_RENEWAL')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ALLOW_ALL_STATUS_RENEWAL"
												id="renewal" autocomplete="off" /> Allow All Status Renewal
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_RENEWAL_REVISE')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_RENEWAL_REVISE"
												id="renewal" autocomplete="off" /> View Renewal Revise
											</label>
											</c:if>
										</div>
										<div class="row1">
											<c:if test = "${feildPrivilege.contains('ADD_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_RENEWAL"
												id="renewal" autocomplete="off" /> Create New Renewal
												Details
											</label> 
											</c:if>
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_RENEWAL"
												id="renewal" autocomplete="off" /> Edit/Revise Renewal
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_RENEWAL"
												id="renewal" autocomplete="off" /> Delete Renewal Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_RENEWAL_PERIOD')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_RENEWAL_PERIOD"
												id="renewal" autocomplete="off" /> Edit Renewal Period
											</label>
											</c:if>
										</div>
										<div class="row1">
											<c:if test = "${feildPrivilege.contains('DELETE_RENEWAL_DOCUMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="DELETE_RENEWAL_DOCUMENT"
													id="renewal" autocomplete="off" />Delete Renewal Document
												</label> 
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Renewal Import :</label> 
											<c:if test = "${feildPrivilege.contains('IMPORT_RENEWAL')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="IMPORT_RENEWAL"
												id="renewal" autocomplete="off" /> Import Renewal Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DOWNLOND_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DOWNLOND_RENEWAL"
												id="renewal" autocomplete="off" />Download Renewal Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('PRINT_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="PRINT_RENEWAL"
												id="renewal" autocomplete="off" /> Print Renewal Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('RE_UPLOAD_RENEWAL_DOCUMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="RE_UPLOAD_RENEWAL_DOCUMENT"
												id="renewal" autocomplete="off" />Re Upload Renewal Document</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Renewal Approval :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_APPROVEL_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_APPROVEL_RENEWAL" id="renewal"
												autocomplete="off" />View Approval Renewal Reminder
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_APPROVEL_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_APPROVEL_RENEWAL" id="renewal" autocomplete="off" />Create
												Renewal Reminder Approval
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Renewal Approval :</label> 
											<c:if test = "${feildPrivilege.contains('APPROVED_RENEWAL')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="APPROVED_RENEWAL"
													id="renewal" autocomplete="off" />Approval Renewal Reminder
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CREATE_RR_APPROVAL')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="CREATE_RR_APPROVAL"
													id="renewal" autocomplete="off" />Create Renewal Approval
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('PAYMENT_APPROVEL_RENEWAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="PAYMENT_APPROVEL_RENEWAL" id="renewal"
												autocomplete="off" /> Make Payment Renewal Reminder
												Approval

											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_APPROVEL_RENEWAL')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_APPROVEL_RENEWAL" id="renewal"
												autocomplete="off" /> Delete Renewal Reminder Approval
											</label>
											</c:if>
										</div>
										
											<div class="row1">
											<label class="L-size control-label" for=""> Mandatory
												sub Renewals :</label>

											<c:if test="${feildPrivilege.contains('EDIT_MANDATORY_SUB_RENEWAL')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="EDIT_MANDATORY_SUB_RENEWAL" id="renewal"
													autocomplete="off" /> Edit Mandatory Sub Renewal
												</label>
											</c:if>
												<c:if test="${feildPrivilege.contains('IS_MANDATORY_SUB_RENEWAL')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="IS_MANDATORY_SUB_RENEWAL" id="renewal"
													autocomplete="off" /> is Mandatory checkbox for Sub Renewal
												</label>
											</c:if>

										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="All-Renewal" autocomplete="off" />
												All Renewal Permission
											</label>
										</div>
									</div>
								</div>
							</div>

							<div class="box" id="module_8" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Service Reminder</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
														Service Program :</label> 
													<c:if test = "${feildPrivilege.contains('ADD_SERVICE_PROGRAM')}">	
														<label class="checkbox-inline"> <input
															type="checkbox" name="privileges"
															value="ADD_SERVICE_PROGRAM" id="service_reminder"
															autocomplete="off" /> Add Service Program
														</label>
													</c:if>
													<c:if test = "${feildPrivilege.contains('VIEW_SERVICE_PROGRAM')}">
														 <label class="checkbox-inline"> <input
															type="checkbox" name="privileges"
															value="VIEW_SERVICE_PROGRAM" id="service_reminder"
															autocomplete="off" />View Service Program
														</label>
													</c:if>
											</div>
											<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
														Service Program :</label> 
													<c:if test = "${feildPrivilege.contains('EDIT_SERVICE_PROGRAM')}">	
														<label class="checkbox-inline"> <input
															type="checkbox" name="privileges"
															value="EDIT_SERVICE_PROGRAM" id="service_reminder"
															autocomplete="off" /> Edit Service Program
														</label>
													</c:if>
													<c:if test = "${feildPrivilege.contains('DELETE_SERVICE_PROGRAM')}">
														 <label class="checkbox-inline"> <input
															type="checkbox" name="privileges"
															value="DELETE_SERVICE_PROGRAM" id="service_reminder"
															autocomplete="off" />Delete Service Program
														</label>
													</c:if>
											</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Program :</label> 
											<c:if test = "${feildPrivilege.contains('DELETE_SERVICE_SCHEDULE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="DELETE_SERVICE_SCHEDULE" id="service_reminder"
													autocomplete="off" /> Delete Service Schedule
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_SERVICE_ASSIGNMENT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="DELETE_SERVICE_ASSIGNMENT" id="service_reminder"
													autocomplete="off" />Delete Service Program Assignment
												</label>
											</c:if>
										</div>
									
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Reminder :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_SERVICE_REMINDER')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" /> View Service Reminder Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_SERVICE_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" /> Create New Service Reminder Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SKIP_SERVICE_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SKIP_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" /> Skip Service Reminder
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_SERVICE_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" /> Edit Service Reminder Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_SERVICE_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" /> Delete Service Reminder Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_SERVICE_REMINDER_CALENDAR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_SERVICE_REMINDER_CALENDAR" id="service_reminder"
												autocomplete="off" /> View Service Reminder Calendar
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service To WorkOrder :</label> 
											<c:if test = "${feildPrivilege.contains('WORKORDER_SERVICE_REMINDER')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="WORKORDER_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" />Create Service Reminder To WorkOrder
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('PRINT_SERVICE_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="PRINT_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" />Print Service Reminder
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_ROT_FOR_SERVICE_REMINDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_ROT_FOR_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" />View ROT in Service Reminder
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label> 
											<c:if test = "${feildPrivilege.contains('SE_SERVICE_REMINDER')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="SE_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" />Create Service Entry From Service Reminder
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DEALER_SERVICE_REMINDER')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DEALER_SERVICE_REMINDER" id="service_reminder"
												autocomplete="off" />Create DSE From Service Reminder
											</label> 
											</c:if>
										</div>	
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-service_reminder"
												autocomplete="off" /> All Service Reminder Permission
											</label>

										</div>
									</div>
								</div>
							</div>

							<div class="box" id="module_9" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Service Entries</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Entries :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_SERVICE_ENTRIES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" /> View Service Entries Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_SERVICE_ENTRIES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" /> Create New Service Entries Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_SERVICE_ENTRIES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" /> Edit Service Entries Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_SERVICE_ENTRIES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" /> Delete Service Entries Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Entries Document :</label> 
											<c:if test = "${feildPrivilege.contains('DOWNLOAD_SERVICE_ENTRIES')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DOWNLOAD_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" />Download Service Entries Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('REOPEN_SERVICE_ENTRIES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="REOPEN_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" /> Re-open Service Entries Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Dealer Service :</label> 
											<c:if test = "${feildPrivilege.contains('DEALER_SERVICE_ENTRIES')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DEALER_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" />Dealer Service Entries 
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DEALER_SERVICE_ENTRIES')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_DEALER_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" />View Dealer Service Entries 
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('REOPEN_DSE')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="REOPEN_DSE" id="service_entries"
												autocomplete="off" />Reopen Dealer Service Entries 
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DSE_METER_NOT_WORKING')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DSE_METER_NOT_WORKING" id="service_entries"
												autocomplete="off" />DSE Meter Not Working 
											</label> 
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"> </label> 
											<c:if test = "${feildPrivilege.contains('ADD_DEALER_SERVICE_ENTRIES')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="ADD_DEALER_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" />Add Dealer Service Entries 
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_DEALER_SERVICE_ENTRIES')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="EDIT_DEALER_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" />Edit Dealer Service Entries 
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_DEALER_SERVICE_ENTRIES')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DELETE_DEALER_SERVICE_ENTRIES" id="service_entries"
												autocomplete="off" />Delete Dealer Service Entries 
											</label> 
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												</label> 
											<c:if test = "${feildPrivilege.contains('EDIT_DSE_PART')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="EDIT_DSE_PART" id="service_entries"
												autocomplete="off" />Edit Dealer Service Part
											</label> 
											</c:if>
											
										<c:if test = "${feildPrivilege.contains('EDIT_DSE_LABOUR')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="EDIT_DSE_LABOUR" id="service_entries"
												autocomplete="off" />Edit Dealer Service Labour
											</label> 
											</c:if>
											
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-service_entries"
												autocomplete="off" /> All Service Entries Permission
											</label>

										</div>
									</div>
								</div>

							</div>
							
							<div class="box" id="module_10" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Fuel Entries</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Fuel Entries :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_FUEL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_FUEL"
												id="fuel_entries" autocomplete="off" /> View Fuel Entries
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_FUEL')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_FUEL"
												id="fuel_entries" autocomplete="off" /> Create New Fuel
												Entries
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_FUEL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_FUEL"
												id="fuel_entries" autocomplete="off" /> Edit Fuel Entries

											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_FUEL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_FUEL"
												id="fuel_entries" autocomplete="off" /> Delete Fuel Entries
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Fuel Document :</label> 
											<c:if test = "${feildPrivilege.contains('IMPORT_FUEL')}">
												<label class="checkbox-inline">
												<input type="checkbox" name="privileges" value="IMPORT_FUEL"
												id="fuel_entries" autocomplete="off" />Import Fuel Entries
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Fuel Vendor Entries :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_FUELVENDOR')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_FUELVENDOR" id="fuel_entries" autocomplete="off" />
												View Fuel Vendor Entries
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_FUELVENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_FUELVENDOR"
												id="fuel_entries" autocomplete="off" /> Create New Fuel
												Vendor Entries
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_FUELVENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_FUELVENDOR"
												id="fuel_entries" autocomplete="off" /> Edit Fuel Vendor
												Entries
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_FUELVENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_FUELVENDOR"
												id="fuel_entries" autocomplete="off" /> Delete Fuel Vendor
												Entries
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-fuel_entries"
												autocomplete="off" /> All Fuel Entries Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<c:if test = "${feildPrivilege.contains('ADD_REFRESHMENT_INVENTORY')}">
							<div class="box" id="module_22" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Refreshment Entries</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Refreshment Entries :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_REFRESHMENT_INVENTORY"
												id="refreshment_entries" autocomplete="off" /> Add Refreshment Entries
											</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_REFRESHMENT_INVENTORY"
												id="refreshment_entries" autocomplete="off" /> Delete Refreshment Vendor
												Entries
											</label>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-refreshment_entries"
												autocomplete="off" /> All Refreshment Entries Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							</c:if>
							<c:if test = "${feildPrivilege.contains('CENTRALISED_REPAIR')}">
							<div class="box" id="module_28" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Repair</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
										<c:if test = "${feildPrivilege.contains('CENTRALISED_REPAIR')}">
											<label class="L-size control-label" for="vehicle_theft">
												Repair :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CENTRALISED_REPAIR"
												id="centralisedRepair" autocomplete="off" /> Repair 
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="allCentralisedRepair"
												autocomplete="off" /> All Repair Entries Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							</c:if>	
							
						<c:if test = "${feildPrivilege.contains('VIEW_UREA_ENTRY')}">
							<div class="box-header">
									<h3 class="box-title">Urea Entries</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Urea Entries :</label> 
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_UREA_ENTRY"
												id="urea_entries" autocomplete="off" /> View Urea Entries
											</label> 
											<c:if test = "${feildPrivilege.contains('ADD_UREA_ENTRY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_UREA_ENTRY"
													id="urea_entries" autocomplete="off" /> Create New Urea
													Entries
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_UREA_ENTRY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_UREA_ENTRY"
												id="urea_entries" autocomplete="off" /> Edit Urea Entries

											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_UREA_ENTRY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="DELETE_UREA_ENTRY"
													id="urea_entries" autocomplete="off" /> Delete Urea Entries
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('SHOW_UREA_ENTRY_IN_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SHOW_UREA_ENTRY_IN_TRIPSHEET"
												id="urea_entries" autocomplete="off" /> Show Urea Entries In Tripsheet

											</label> 
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											Urea Requistion Transfer </label>
											<c:if test = "${feildPrivilege.contains('UREA_REQUISITION_TRANSFER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="UREA_REQUISITION_TRANSFER"
												id="urea_entries" autocomplete="off" /> View Urea Requisition And Transfer

											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('YOUR_UREA_REQUISITION_AND_TRANFER_HISTORY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="YOUR_UREA_REQUISITION_AND_TRANFER_HISTORY"
													id="urea_entries" autocomplete="off" /> Your Urea Requisition And Transfer History
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CREATE_UREA_REQUISITION')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="CREATE_UREA_REQUISITION"
													id="urea_entries" autocomplete="off" /> Create Urea Requisition
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-urea_entries"
												autocomplete="off" /> All Urea Entries Permission
											</label>

										</div>
									</div>
								</div>
								</c:if>
							
							<div class="box" id="module_12" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Vendor</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor Type Master :</label> 
											<c:if test = "${feildPrivilege.contains('SAVE_NEW_VENDOR_TYPE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SAVE_NEW_VENDOR_TYPE"
												id="vendor_entries" autocomplete="off" /> Save New Vendor Type
												Only For Fleetop Company
											</label> 
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_VENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VENDOR"
												id="vendor_entries" autocomplete="off" /> View Vendor
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_VENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_VENDOR"
												id="vendor_entries" autocomplete="off" /> Create New Vendor
												Details
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_VENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_VENDOR"
												id="vendor_entries" autocomplete="off" /> Edit Vendor
												Details

											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_VENDOR"
												id="vendor_entries" autocomplete="off" /> Delete Vendor
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_VENDOR_PAYMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_VENDOR_PAYMENT"
												id="vendor_entries" autocomplete="off" />Add Vendor Payment</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VENDOR_PAYMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VENDOR_PAYMENT"
												id="vendor_entries" autocomplete="off" />View Vendor Payment List</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor Fixed Part Price:</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VENDOR_FIXEDPART')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="ADDEDIT_VENDOR_FIXEDPART" id="vendor_entries"
												autocomplete="off" />Create &amp; Edit/Update Vendor Fixed
												Part Price
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VENDOR_FIXEDPART')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VENDOR_FIXEDPART" id="vendor_entries"
												autocomplete="off" />Delete Vendor Fixed Part Price
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor Fixed Fuel Price:</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VENDOR_FIXEDFUEL')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="ADDEDIT_VENDOR_FIXEDFUEL" id="vendor_entries"
												autocomplete="off" />Create &amp; Edit/Update Vendor Fixed
												Fuel Price
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VENDOR_FIXEDFUEL')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VENDOR_FIXEDFUEL" id="vendor_entries"
												autocomplete="off" />Delete Vendor Fixed Fuel Price
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor Document:</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_VENDOR_DOCUMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_VENDOR_DOCUMENT" id="vendor_entries"
												autocomplete="off" />Create &amp; Edit/Update Vendor
												Document
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VENDOR_DOCUMENT')}">	
										<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_VENDOR_DOCUMENT" id="vendor_entries"
												autocomplete="off" />Delete Vendor Document
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor Approval List:</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_APPROVEL_VENDOR')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_APPROVEL_VENDOR" id="vendor_entries"
												autocomplete="off" />View Vendor Approval
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_APPROVEL_VENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_APPROVEL_VENDOR" id="vendor_entries"
												autocomplete="off" />Create Vendor Approval
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('PAYMENT_APPROVEL_VENDOR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="PAYMENT_APPROVEL_VENDOR" id="vendor_entries"
												autocomplete="off" /> Make Payment Vendor Approval List

											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_APPROVEL_VENDOR')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="DELETE_APPROVEL_VENDOR" id="vendor_entries"
													autocomplete="off" /> Delete Vendor Approval
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_LORRY_HIRE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ADD_LORRY_HIRE" id="vendor_entries"
													autocomplete="off" /> Add Vendor Lorry Hire
	
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_LORRY_HIRE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_LORRY_HIRE" id="vendor_entries"
												autocomplete="off" /> View Vendor Lorry Hire
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_LORRY_HIRE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_LORRY_HIRE" id="vendor_entries"
												autocomplete="off" /> Edit Vendor Lorry Hire
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('DELETE_LORRY_HIRE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="DELETE_LORRY_HIRE" id="vendor_entries"
													autocomplete="off" /> Delete Vendor Lorry Hire
	
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_MORE_LORRY_CHARGES')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ADD_MORE_LORRY_CHARGES" id="vendor_entries"
													autocomplete="off" /> Add More Lorry Charges
	
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('LORRY_HIRE_PAYMENT')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="LORRY_HIRE_PAYMENT" id="vendor_entries"
													autocomplete="off" /> Add Vendor Lorry Hire Payment
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-vendor_entries"
												autocomplete="off" /> All Vendor Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_14" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">TripSheet</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Dispatch TripSheet :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_TRIPSHEET')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" /> View TripSheet Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_TRIPSHEET"
												id="tripsheet_entries" autocomplete="off" /> Create New
												TripSheet Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TRIPSHEET_ONLY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VIEW_TRIPSHEET_ONLY"
													id="tripsheet_entries" autocomplete="off" />View TripSheet Only
												</label>
											</c:if>	
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_TRIPSHEET"
												id="tripsheet_entries" autocomplete="off" /> Edit Vendor
												TripSheet

											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_TRIPSHEET')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_TRIPSHEET"
												id="tripsheet_entries" autocomplete="off" /> Delete
												TripSheet Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Manage TripSheet:</label> 
											<c:if test = "${feildPrivilege.contains('ADD_ADVANCE_TRIPSHEET')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_ADVANCE_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" />Add TripSheet Advance
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_EXPENSE_TRIPSHEET')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_EXPENSE_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" />Add TripSheet Expense
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_ROUTE_WISE_TRIPSHEET_WEIGHT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_ROUTE_WISE_TRIPSHEET_WEIGHT" id="tripsheet_entries"
												autocomplete="off" />Add Route Wise Tripsheet Weight
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_INCOME_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_INCOME_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" />Add TripSheet Income

											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('CLOSE_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CLOSE_TRIPSHEET"
												id="tripsheet_entries" autocomplete="off" /> Close
												TripSheet Details
											</label>
											</c:if>
												<c:if test = "${feildPrivilege.contains('CHECK_GPS_FASTAG_BEFORE_TRIPCLOSE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CHECK_GPS_FASTAG_BEFORE_TRIPCLOSE"
												id="CHECK_GPS_FASTAG_BEFORE_TRIPCLOSE" autocomplete="off" /> Close
												TripSheet if NO gps or fastag data
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_TRIPSHEET_ROUTE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_TRIPSHEET_ROUTE" id="tripsheet_entries"
												autocomplete="off" />Add New TripSheet Route

											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_TRIP_ROUTE_EXPENSE_RANGE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_TRIP_ROUTE_EXPENSE_RANGE" id="tripsheet_entries"
												autocomplete="off" />Add Trip Route Expense Range

											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE" id="tripsheet_entries"
												autocomplete="off" />Add Trip Route Expense Out Of Range

											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_TRIP_ROUTE_POINT')}">
											<label class="L-size control-label" for="vehicle_theft">
											</label> <label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="EDIT_TRIP_ROUTE_POINT" id="tripsheet_entries"
													autocomplete="off" />Edit Trip Route Point
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('BUS_BOOKING_DETAILS')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="BUS_BOOKING_DETAILS" id="tripsheet_entries"
													autocomplete="off" />Bus Booking
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_B_E_INCOME_TRIPBALANCE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="VIEW_B_E_INCOME_TRIPBALANCE" id="tripsheet_entries"
													autocomplete="off" />View B & E Income TripBalance
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Trip Check Points:</label>
											<c:if test = "${feildPrivilege.contains('ADD_TRIP_CHECK_POINTS')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_TRIP_CHECK_POINTS"
													id="tripsheet_entries" autocomplete="off" />Add Trip CheckPoint
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_TRIP_CHECK_POINTS_PARAMETER')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_TRIP_CHECK_POINTS_PARAMETER"
													id="tripsheet_entries" autocomplete="off" />Add Trip CheckPoint Parameter
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('TRIP_CHECK_POINT_INSPECTION')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="TRIP_CHECK_POINT_INSPECTION"
													id="tripsheet_entries" autocomplete="off" />Trip CheckPoint Inspection
												</label> 
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Halt TripSheet:</label>
											<c:if test = "${feildPrivilege.contains('ADD_HALT_TRIPSHEET')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_HALT_TRIPSHEET"
													id="tripsheet_entries" autocomplete="off" />Create
													TripSheet Halt Amount
												</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_FUEL_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_FUEL_TRIPSHEET"
												id="tripsheet_entries" autocomplete="off" />Create
												TripSheet Fuel Amount
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												TripSheet A/C Payment:</label>
											<c:if test = "${feildPrivilege.contains('VIEW_ACCOUNT_TRIPSHEET')}">
												 <label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_ACCOUNT_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" />View TripSheets Account
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('CLOSE_ACCOUNT_TRIPSHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="CLOSE_ACCOUNT_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" />Close TripSheet Account
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('REMOVE_TRIP_INCOME')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="REMOVE_TRIP_INCOME" id="tripsheet_entries"
												autocomplete="off" />Remove TripSheet Income
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_TRIPSHEET_CLOSE_STATUS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_TRIPSHEET_CLOSE_STATUS" id="tripsheet_entries"
												autocomplete="off" />Show Tripsheet Close Status
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												TripSheet Comment:</label> 
											<c:if test = "${feildPrivilege.contains('ADD_COMMENT_TRIPSHEET')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_COMMENT_TRIPSHEET" id="tripsheet_entries"
												autocomplete="off" />Create &amp; Edit/Update TripSheets
												Comment
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('IVCARGO_INTEGRATION_NEEDED')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="IVCARGO_INTEGRATION_NEEDED" id="tripsheet_entries"
												autocomplete="off" /> IV Cargo Integration (LHPV)
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('TRIP_LOADING_SHEET_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="TRIP_LOADING_SHEET_REPORT" id="tripsheet_entries"
													autocomplete="off" /> TripSheet Loading Sheet Details Report
												</label>
											</c:if>
										</div>
									    <div class="row1">
											<c:if test = "${feildPrivilege.contains('FLAVOR_TWO_PRIVILEGE')}">
												<label class="L-size control-label" for="vehicle_theft">
												 Daily Trip Collection:</label> 
												<c:if test = "${feildPrivilege.contains('RE_OPEN_DAILY_TRIP_COLLECTION')}"> 
												 <label class="checkbox-inline"> <input
														type="checkbox" name="privileges" readonly="readonly"
														value="RE_OPEN_DAILY_TRIP_COLLECTION" id="tripsheet_entries"
														autocomplete="off" /> Re-Open Trip Collection
													</label>
												</c:if>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												TripSheet Extra Option:</label> 
											<c:if test = "${feildPrivilege.contains('TRIP_SHEET_OPTIONS_EXTRA')}">	
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="TRIP_SHEET_OPTIONS_EXTRA" id="tripsheet_extra"
												autocomplete="off" />View TripSheet Extra
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UPDATE_CLOSING_KM')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="UPDATE_CLOSING_KM" id="tripsheet_extra"
												autocomplete="off" />Edit Closing KM
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UPDATE_OPENING_KM')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="UPDATE_OPENING_KM" id="tripsheet_entries"
												autocomplete="off" />Edit Opening KM
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('ADD_ADVANCE_AFTER_TRIP_CLOSE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_ADVANCE_AFTER_TRIP_CLOSE" id="tripsheet_entries"
												autocomplete="off" />Add Advance After Tripsheet Close
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('ADD_LOAD_TYPES')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_LOAD_TYPES" id="tripsheet_entries"
												autocomplete="off" />Load Type Master
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('LOAD_EDIT_MASTER')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="LOAD_EDIT_MASTER" id="tripsheet_entries"
												autocomplete="off" />Load Edit Master
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('LOAD_DELETE_MASTER')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="LOAD_DELETE_MASTER" id="tripsheet_entries"
												autocomplete="off" />Load Delete Master
											</label>
											</c:if>
										</div>
										<c:if test = "${feildPrivilege.contains('ALLOW_EXTENDED_TRIP')}">
											<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
													Extended TripSheet Option:</label> <label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="ALLOW_EXTENDED_TRIP" id="tripsheet_entries"
													autocomplete="off" />Extended TripSheet
												</label>
																																	
											</div>
										</c:if>
										<c:if test = "${feildPrivilege.contains('TRIPSHEET_EDIT_AFTER_CLOSE')}">
											<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
													Tripsheet Edit After Close:</label> <label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="TRIPSHEET_EDIT_AFTER_CLOSE" id="tripsheet_entries"
													autocomplete="off" />Tripsheet Edit After Close
												</label>
												
												<label class="L-size control-label" for="vehicle_theft">
													Change Status After TripSheet Close:</label> <label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="CHANGE_STATUS" id="tripsheet_entries"
													autocomplete="off" />Change Status After TripSheet Close
												</label>
																																	
											</div>
										</c:if>
										<c:if test = "${feildPrivilege.contains('SHOW_TRIPSHEET_DOCUMENT')}">
											<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
													Show TripSheet Document:</label> <label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="SHOW_TRIPSHEET_DOCUMENT" id="tripsheet_entries"
													autocomplete="off" />Show Tripsheet Document
												</label>
																																	
											</div>
										</c:if>
										<c:if test = "${feildPrivilege.contains('BUS_BOOKING_DETAILS')}">
											<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
													Bus Booking:</label> <label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="BUS_BOOKING_DETAILS" id="tripsheet_entries"
													autocomplete="off" />Bus Booking
												</label>
																																	
											</div>
										</c:if>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-tripsheet_entries"
												autocomplete="off" /> All TripSheet Permission
											</label>
											
											

										</div>
									</div>
								</div>
							</div>

							<div class="box" id="module_15" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Parts</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_PARTS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PARTS"
												id="parts" autocomplete="off" /> View Part Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_PARTS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_PARTS"
												id="parts" autocomplete="off" /> Create New Part Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_PARTS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_PARTS"
												id="parts" autocomplete="off" /> Edit/Update Part Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_PARTS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_PARTS"
												id="parts" autocomplete="off" /> Delete Part Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_PART_SUB_CATEGORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SHOW_PART_SUB_CATEGORY"
												id="parts" autocomplete="off" /> Part Sub Category
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('LABOUR_MASTER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="LABOUR_MASTER"
												id="parts" autocomplete="off" /> Labour Master
											</label>
											</c:if>
										</div>
														<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												</label> 
												<c:if test="${feildPrivilege.contains('LOCATIONWISE_PART_POSITION')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="LOCATIONWISE_PART_POSITION" id="parts"
												autocomplete="off" /> Add Location wise part position
											</label></c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part Import :</label>
											<c:if test = "${feildPrivilege.contains('IMPORT_PARTS')}">
												 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="IMPORT_PARTS"
												id="parts" autocomplete="off" /> Import Part Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('FLAVOR_TWO_PRIVILEGE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DOWNLOND_PARTS"
												id="parts" autocomplete="off" />Download Part Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part Comment :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_PARTS_COMMENT')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_PARTS_COMMENT" id="parts" autocomplete="off" />Create
												&amp; Edit/Update Part Comments Details
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part Photo :</label> 
											<c:if test = "${feildPrivilege.contains('FLAVOR_TWO_PRIVILEGE')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_PARTS_PHOTO" id="parts" autocomplete="off" />Add
												&amp; Delete Part Photo Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_QRCODE_PARTS')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_QRCODE_PARTS"
												id="parts" autocomplete="off" />Create Part QR Code Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-parts" autocomplete="off" />
												All Part Permission
											</label>

										</div>
									</div>
								</div>
							</div>

							<div class="box" id="module_16" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Purchase Orders</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Purchase Orders :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_PURCHASE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PURCHASE"
												id="purchase" autocomplete="off" /> View Purchase Order
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_PURCHASE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_PURCHASE"
												id="purchase" autocomplete="off" /> Create New Purchase
												Order Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_AFTER_ORDERED_PURCHASE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="EDIT_AFTER_ORDERED_PURCHASE"
													id="purchase" autocomplete="off" /> Edit PO After Ordered
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_AFTER_RECEIVED_PURCHASE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="EDIT_AFTER_RECEIVED_PURCHASE"
													id="purchase" autocomplete="off" /> Edit PO After Received 
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('EDIT_PURCHASE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_PURCHASE"
												id="purchase" autocomplete="off" /> Edit/Update Purchase
												Order Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_PURCHASE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_PURCHASE"
												id="purchase" autocomplete="off" /> Delete Purchase Order
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_PURCHASE_AFTER_APPROVAL')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_PURCHASE_AFTER_APPROVAL"
												id="purchase" autocomplete="off" /> Delete Purchase Order Details After Approval
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Purchase Order :</label> 
											<c:if test = "${feildPrivilege.contains('DOWNLOND_PURCHASE')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DOWNLOND_PURCHASE"
												id="purchase" autocomplete="off" />Download / Print
												Purchase Order Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('APPROVE_BELOW_FIFTY')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="APPROVE_BELOW_FIFTY"
												id="purchase" autocomplete="off" /> Approve Below 50000
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('APPROVE_ABOVE_FIFTY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="APPROVE_ABOVE_FIFTY"
												id="purchase" autocomplete="off" /> Approve Above 50000
											</label>
											</c:if>
											

										</div>
										<div class="row1">
											<c:if test = "${feildPrivilege.contains('ORDERED_PURCHASE')}">
											<label class="L-size control-label" for="vehicle_theft">
												PO Function :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ORDERED_PURCHASE"
												id="purchase" autocomplete="off" /> Ordered Purchase Order
												Parts
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('RECEIVED_PURCHASE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="RECEIVED_PURCHASE"
												id="purchase" autocomplete="off" /> Received Purchase Order
												Parts
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('RECEIVED_PURCHASE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="COMPLETE_PURCHASE"
												id="purchase" autocomplete="off" /> Complete Purchase Order
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('REOPEN_PURCHASE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="REOPEN_PURCHASE"
												id="purchase" autocomplete="off" /> Re-Open Purchase Order
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CHANGE_PO_FROM_RECEIVED_TO_ORDERED')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CHANGE_PO_FROM_RECEIVED_TO_ORDERED"
												id="purchase" autocomplete="off" /> Change PO Status from Received to Ordered
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-purchase" autocomplete="off" />
												All Purchase Order Permission
											</label>
										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_13" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Inventory</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"> </label> 
											<c:if test = "${feildPrivilege.contains('VIEW_INVENTORY')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_INVENTORY" id="inventory"
												autocomplete="off" /> View Inventory
											</label> 
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part Requisition :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_REQUISITION_INVENTORY')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_REQUISITION_INVENTORY" id="inventory"
												autocomplete="off" /> View Part Requisition Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_REQUISITION_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_REQUISITION_INVENTORY" id="inventory"
												autocomplete="off" /> Create Part Requisition Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('DELETE_REQUISITION_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_REQUISITION_INVENTORY" id="inventory"
												autocomplete="off" /> Delete Part Requisition Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_QRCODE_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_QRCODE_INVENTORY" id="inventory"
												autocomplete="off" /> View Inventory QR Code Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part Inventory :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_PART_INVENTORY')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PART_INVENTORY"
												id="inventory" autocomplete="off" /> View Part Inventory
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_INVENTORY')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_INVENTORY"
												id="inventory" autocomplete="off" /> Create Part Inventory
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_INVENTORY"
												id="inventory" autocomplete="off" /> Edit/Update Part
												Inventory Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_INVENTORY"
												id="inventory" autocomplete="off" /> Delete Part Inventory
												Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('TRANSFER_MULTI_TYRE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="TRANSFER_MULTI_TYRE" id="inventory"
												autocomplete="off" /> Transfer Multiple Tyre
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('RECEIVE_MULTI_TYRE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="RECEIVE_MULTI_TYRE" id="inventory"
												autocomplete="off" /> Receive Multiple Tyre
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Transfer :</label> 
											<c:if test = "${feildPrivilege.contains('TRANSFER_INVENTORY')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="TRANSFER_INVENTORY"
												id="inventory" autocomplete="off" />Transfer Inventory
												Parts
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Tyre Inventory :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_TYRE_INVENTORY')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TYRE_INVENTORY" id="inventory"
												autocomplete="off" /> View Tyre Inventory Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_TYRE_INVENTORY')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_TYRE_INVENTORY"
												id="inventory" autocomplete="off" /> Create Tyre Inventory
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_SOLD_FILTER')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_SOLD_FILTER"
												id="inventory" autocomplete="off" /> Tyre Sold Filter
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('EDIT_TYRE_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_TYRE_INVENTORY" id="inventory"
												autocomplete="off" /> Edit/Update Tyre Inventory Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_TYRE_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_TYRE_INVENTORY" id="inventory"
												autocomplete="off" /> Delete Tyre Inventory Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Tyre Retread :</label> 
											<c:if test = "${feildPrivilege.contains('ADD_TYRE_RETREAD')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_TYRE_RETREAD"
												id="inventory" autocomplete="off" /> Create Tyre Retread
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_TYRE_RETREAD')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_TYRE_RETREAD"
												id="inventory" autocomplete="off" /> Edit Tyre Retread
												Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('SEND_TYRE_RETREAD')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SEND_TYRE_RETREAD"
												id="inventory" autocomplete="off" /> Send Tyre Retread
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('RECEIVED_TYRE_RETREAD')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="RECEIVED_TYRE_RETREAD" id="inventory"
												autocomplete="off" /> Received Tyre Retread Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('REOPEN_TYRE_RETREAD')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="REOPEN_TYRE_RETREAD" id="inventory"
												autocomplete="off" /> Re-Open Tyre Retread Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DOWNLOND_TYRE_RETREAD')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DOWNLOND_TYRE_RETREAD" id="inventory"
												autocomplete="off" /> Download Tyre Retread Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('ADD_TYRE_SCRAP')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_TYRE_SCRAP"
												id="inventory" autocomplete="off" />Create Available to
												Scrap Tyre Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_SCRAP_AVAILABLE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_SCRAP_AVAILABLE" id="inventory"
												autocomplete="off" /> Create Scrap to Available Tyre
												Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('TRANSFER_TYRE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="TRANSFER_TYRE"
												id="inventory" autocomplete="off" />Transfer Tyre Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TYRE_LIFECYCLE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TYRE_LIFECYCLE" id="inventory"
												autocomplete="off" /> View Tyre Life Cycle Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('TYRERETREAD_PRINT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="TYRERETREAD_PRINT" id="inventory"
												autocomplete="off" /> Print Tyre Retread
											</label>
											</c:if>
										</div>
										
									<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Upholstery Inventory :</label> 
											<c:if test = "${feildPrivilege.contains('CLOTH_INVENTORY_SETTING')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CLOTH_INVENTORY_SETTING"
												id="inventory" autocomplete="off" /> View Upholstery Inventory Setting
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_CLOTH_TYPES')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_CLOTH_TYPES"
												id="inventory" autocomplete="off" /> Add Upholstery Types
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_CLOTH_TYPES_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_CLOTH_TYPES_INVENTORY"
												id="inventory" autocomplete="off" /> Add Upholstery Inventory
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_CLOTH_TYPES_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_CLOTH_TYPES_INVENTORY"
												id="inventory" autocomplete="off" />View Upholstery Inventory
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_CLOTH_INVENTORY')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_CLOTH_INVENTORY" id="inventory"
												autocomplete="off" /> Delete Upholstery Inventory
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_CLOTH_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_CLOTH_INVENTORY" id="inventory"
												autocomplete="off" />Edit Upholstery Inventory
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_LAUNDRY_RATE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_LAUNDRY_RATE"
												id="inventory" autocomplete="off" />Add Laundry Rate
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_VEHICLE_CLOTH_INVENTORY_DETAILS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_VEHICLE_CLOTH_INVENTORY_DETAILS" id="inventory"
												autocomplete="off" />Add Upholstery Inventory Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_VEHICLE_TOOL_BOX_DETAILS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_VEHICLE_TOOL_BOX_DETAILS" id="inventory"
												autocomplete="off" />Add ToolBox Details
											</label> 
											</c:if>
										</div>
										<c:if test = "${feildPrivilege.contains('VIEW_UREA_INVENTORY')}">
											<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
													Urea Inventory :</label> <label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VIEW_UREA_INVENTORY"
													id="inventory" autocomplete="off" /> View Urea Inventory
												</label>
												<c:if test = "${feildPrivilege.contains('ADD_UREA_INVENTORY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_UREA_INVENTORY"
													id="inventory" autocomplete="off" /> Add Urea Inventory
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_UREA_INVENTORY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="DELETE_UREA_INVENTORY"
													id="inventory" autocomplete="off" /> Delete Urea Inventory
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_UREA_TYPES')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_UREA_TYPES"
													id="inventory" autocomplete="off" /> Urea Manufacturer Master
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UREA_EDIT_MASTER')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UREA_EDIT_MASTER"
													id="inventory" autocomplete="off" /> Urea Edit Master
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UREA_DELETE_MASTER')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UREA_DELETE_MASTER"
													id="inventory" autocomplete="off" /> Urea Delete Master
												</label>
											</c:if>
											
											</div>
										</c:if>
										
										<c:if test = "${feildPrivilege.contains('FUEL_INVENTORY')}">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Fuel Inventory :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="FUEL_INVENTORY"
												id="inventory" autocomplete="off" /> View Fuel Inventory
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_FUEL_INVENTORY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ADD_FUEL_INVENTORY"
													id="inventory" autocomplete="off" /> Add Fuel Inventory
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_FUEL_INVENTORY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="EDIT_FUEL_INVENTORY"
													id="inventory" autocomplete="off" /> Edit Fuel Inventory
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_FUEL_INVENTORY')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="DELETE_FUEL_INVENTORY"
													id="inventory" autocomplete="off" /> Delete Fuel Inventory
												</label>
												</c:if>
												<c:if test = "${feildPrivilege.contains('FUEL_TRANSFER')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="FUEL_TRANSFER"
												id="inventory" autocomplete="off" /> Fuel Transfer
											</label>
											</c:if>
												
										</div>
										</c:if>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-inventory"
												autocomplete="off" /> All Inventory Permission
											</label>
										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_17" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Work Order</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Work Order :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_WORKORDER')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_WORKORDER"
												id="workorder" autocomplete="off" /> View Work Order
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_WORKORDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_WORKORDER"
												id="workorder" autocomplete="off" /> Create New Work Order
												Details
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_WORKORDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_WORKORDER"
												id="workorder" autocomplete="off" /> Edit Work Order
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_WORKORDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_WORKORDER"
												id="workorder" autocomplete="off" /> Delete Work Order
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_WARRANTY_PARTS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_WARRANTY_PARTS"
												id="workorder" autocomplete="off" /> Add Warranty Parts
												Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('JOB_CARD_PRINT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="JOB_CARD_PRINT"
												id="workorder" autocomplete="off" /> JOb card Print
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Work Order :</label> 
											<c:if test = "${feildPrivilege.contains('COMPLETE_WORKORDER')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="COMPLETE_WORKORDER"
												id="workorder" autocomplete="off" />Complete Work Order
												
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('REOPEN_WORKORDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="REOPEN_WORKORDER"
												id="workorder" autocomplete="off" /> Re-open Work
												Order 
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ONHOLD_WORKORDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ONHOLD_WORKORDER"
												id="workorder" autocomplete="off" /> On Hold Work
												Order 
											</label>
											</c:if>
												<c:if test = "${feildPrivilege.contains('INPROCESS_WORKORDER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="INPROCESS_WORKORDER"
												id="workorder" autocomplete="off" />  In-process Work
												Order 
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('JOB_CARD_SERVICE_REMINDER_PRINT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="JOB_CARD_SERVICE_REMINDER_PRINT"
												id="workorder" autocomplete="off" />  Job Card Service Reminder Print
												Order 
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Work Order Document :</label>
											<c:if test = "${feildPrivilege.contains('DOWNLOAD_WORKORDER')}">
												 <label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DOWNLOAD_WORKORDER" id="workorder" autocomplete="off" />Download
												Work Order Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-workorder"
												autocomplete="off" /> All Work Order Permission
											</label>

										</div>
									</div>
								</div>

							</div>
							
							<div class="box" id="module_11" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Issues</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues All :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_ALL_ISSUES')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_ALL_ISSUES"
												id="issues" autocomplete="off" /> View All Issues Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('OPERATE_ALL_ISSUES')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="OPERATE_ALL_ISSUES"
												id="issues" autocomplete="off" /> Operate All Issue
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_ISSUES')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_ISSUES"
												id="issues" autocomplete="off" /> View Issues Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_ISSUES')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_ISSUES"
												id="issues" autocomplete="off" /> Create New Issues Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CREATE_WO_FROM_ISSUE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CREATE_WO_FROM_ISSUE"
												id="issues" autocomplete="off" /> Create WO From Issues
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CREATE_OM_FROM_ISSUE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CREATE_OM_FROM_ISSUE"
												id="issues" autocomplete="off" /> Create Outside Maintenance From Issues
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CREATE_DSE_FROM_ISSUE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CREATE_DSE_FROM_ISSUE"
												id="issues" autocomplete="off" /> Create DSE From Issues
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
												<c:if
												test="${feildPrivilege.contains('SHOW_VEHICLE_BREAKDOWN')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="SHOW_VEHICLE_BREAKDOWN" id="issues"
													autocomplete="off" /> Show vehicle breakDown
												</label>
											</c:if>
											<c:if
												test="${feildPrivilege.contains('CHANGE_ISSUE_TYPE_BREAKDOWN')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="CHANGE_ISSUE_TYPE_BREAKDOWN" id="issues"
													autocomplete="off" /> Change Issue Type to breakdown
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_ISSUES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_ISSUES"
												id="issues" autocomplete="off" /> Edit Issues Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_ISSUES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_ISSUES"
												id="issues" autocomplete="off" /> Delete Issues Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<c:if test = "${feildPrivilege.contains('REOPEN_ISSUES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="REOPEN_ISSUES"
												id="issues" autocomplete="off" /> Re-open Issues Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('CLOSE_ISSUES')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CLOSE_ISSUES"
												id="issues" autocomplete="off" /> Close Issues Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DOWNLOAD_ISSUES')}">
											<label class="L-size control-label" for="vehicle_theft">
												Issues Document :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DOWNLOAD_ISSUES"
												id="issues" autocomplete="off" />Download Issues Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues Document :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_ISSUES_DOCUMENT')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_ISSUES_DOCUMENT" id="issues"
												autocomplete="off" /> Create Issues Document Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_ISSUES_DOCUMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_ISSUES_DOCUMENT" id="issues"
												autocomplete="off" /> Delete Issues Document Details
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues Comment :</label>
											<c:if test = "${feildPrivilege.contains('ADDEDIT_ISSUES_COMMENT')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_ISSUES_COMMENT" id="issues"
												autocomplete="off" /> Create Issues Comment Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_ISSUES_COMMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_ISSUES_COMMENT" id="issues" autocomplete="off" />
												Delete Issues Comment Details
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues Photo :</label> 
											<c:if test = "${feildPrivilege.contains('ADDEDIT_ISSUES_PHOTO')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADDEDIT_ISSUES_PHOTO" id="issues" autocomplete="off" />
												Create Issues Photo Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_ISSUES_PHOTO')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_ISSUES_PHOTO" id="issues" autocomplete="off" />
												Delete Issues Photo Details
											</label>
											</c:if>

										</div>
											<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues Analysis :</label> 
											<c:if test = "${feildPrivilege.contains('ADD_ISSUE_ANALYSIS')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_ISSUE_ANALYSIS" id="issues" autocomplete="off" />
												Add Issues Analysis
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_ISSUE_ANALYSIS')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_ISSUE_ANALYSIS" id="issues" autocomplete="off" />
												Show Issues Analysis
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-issues" autocomplete="off" />
												All Issues Permission
											</label>
										
										</div>
									</div>
								</div>

							</div>
							
							<div class="box" id="module_20" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">CashBook</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												CashBook :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_CASHBOOK')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_CASHBOOK"
												id="CashBook" autocomplete="off" /> View CashBook Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_CASHBOOK')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_CASHBOOK"
												id="CashBook" autocomplete="off" /> Create New CashBook
												Details
											</label>
											</c:if>

										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('EDIT_CASHBOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_CASHBOOK"
												id="CashBook" autocomplete="off" /> Edit CashBook Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_CASHBOOK')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_CASHBOOK"
												id="CashBook" autocomplete="off" /> Delete CashBook Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('CASHBOOK_VOUCHER_NO_UPDATE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="CASHBOOK_VOUCHER_NO_UPDATE" id="CashBook"
												autocomplete="off" />Edit Cash Voucher No.
											</label>
											</c:if>
										</div>
										<!--  -->
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('VIEW_MAIN_CASH_BOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_MAIN_CASH_BOOK" id="CashBook"
												autocomplete="off" />View MAIN CashBook
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('VIEW_SSTS_CASH_BOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_SSTS_CASH_BOOK" id="CashBook"
												autocomplete="off" />View SSTS CashBook
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<c:if test = "${feildPrivilege.contains('VIEW_JBL_CASH_BOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_JBL_CASH_BOOK" id="CashBook"
												autocomplete="off" />View JBL CashBook
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_COMBINED_CASH_BOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_COMBINED_CASH_BOOK" id="CashBook"
												autocomplete="off" />View JBL CashBook
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												CashBook Document :</label>
											<c:if test = "${feildPrivilege.contains('DOWNLOAD_CASHBOOK')}">	
											 <label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DOWNLOAD_CASHBOOK" id="CashBook" autocomplete="off" />Print
												CashBook Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADDEDIT_DOCUMENT_CASHBOOK')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="ADDEDIT_DOCUMENT_CASHBOOK" id="CashBook" autocomplete="off" />Create
												CashBook Document Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('ADD_LAST_CASHBOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_LAST_CASHBOOK"
												id="CashBook" autocomplete="off" /> Create Last CashBook
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_MISSING_CASHBOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_MISSING_CASHBOOK" id="CashBook"
												autocomplete="off" /> Create Missing CashBook Details
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('CLOSE_CASHBOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CLOSE_CASHBOOK"
												id="CashBook" autocomplete="off" /> Close CashBook
												Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('APPROVED_CASHBOOK')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="APPROVED_CASHBOOK" id="CashBook"
												autocomplete="off" /> Approved CashBook Details
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-CashBook" autocomplete="off" />
												All CashBook Permission
											</label>

										</div>
									</div>
								</div>

							</div>
							
						<div class="box" id="module_18" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Battery</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Battery :</label>
											<c:if test = "${feildPrivilege.contains('ADD_BATTERY_MANFATURER')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BATTERY_MANFATURER"
												id="Battery" autocomplete="off" /> Add Battery Manufacturer
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_BATTERY_TYPE')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BATTERY_TYPE"
												id="Battery" autocomplete="off" /> Add Battery Model
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_BATTERY_CAPACITY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BATTERY_CAPACITY"
												id="Battery" autocomplete="off" /> Add Battery Capacity
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_BATTERY_SCRAP')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BATTERY_SCRAP"
												id="Battery" autocomplete="off" /> Add Battery Scrap
											</label>   
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Battery Inventory :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_BATTERY_INVENTORY')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_BATTERY_INVENTORY" id="Battery"
												autocomplete="off" /> View Battery Inventory Details
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_BATTERY_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ADD_BATTERY_INVENTORY"
												id="Battery" autocomplete="off" /> Create Battery Inventory
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_BATTERY_INVENTORY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_BATTERY_INVENTORY"
												id="Battery" autocomplete="off" /> Delete Battery Inventory
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('EDIT_BATTERY')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="EDIT_BATTERY"
												id="Battery" autocomplete="off" /> Edit Battery
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('BATTERY_PRINT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="BATTERY_PRINT"
												id="Battery" autocomplete="off" /> Battery Print
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Battery Transfer :</label> 
											<c:if test = "${feildPrivilege.contains('TRANSFER_BATTERY')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="TRANSFER_BATTERY" id="Battery"
												autocomplete="off" /> Battery Transfer
											</label> 
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-Battery" autocomplete="off" />
												All Battery Permission
											</label>

										</div>
									</div>
								</div>

							</div>

							<div class="box" id="module_19" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Vehicle Inspection</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Inspection :</label> 
											<c:if test = "${feildPrivilege.contains('ADD_VEHICLE_INSPECTION_PARAMETER')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="ADD_VEHICLE_INSPECTION_PARAMETER" id="Inspection"
												autocomplete="off" /> Add Inspecton Parameter
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_VEHICLE_INSPECTION_SHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_VEHICLE_INSPECTION_SHEET" id="Inspection"
												autocomplete="off" /> Add Inspection Sheet
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ADD_VEHICLE_ASSIGNMENT_TO_SHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="ADD_VEHICLE_ASSIGNMENT_TO_SHEET" id="Inspection"
												autocomplete="off" /> Add Vehicle Sheet Assignment
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE_ASSIGNMENT_TO_SHEET')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VEHICLE_ASSIGNMENT_TO_SHEET" id="Inspection"
												autocomplete="off" /> View Vehicle Sheet Asignment
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE_INSPECTION_MODULE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VEHICLE_INSPECTION_MODULE" id="Inspection"
												autocomplete="off" /> View Vehicle Inspection Module
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VEHICLE_INSPECTION_SHEET_EDIT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VEHICLE_INSPECTION_SHEET_EDIT"
													id="Inspection" autocomplete="off" /> View Vehicle Inspection Edit
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_ALL_BRANCH_INSPECTION')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="SHOW_ALL_BRANCH_INSPECTION"
													id="Inspection" autocomplete="off" /> Show All Branch Inspection
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											Inspection Parameter :</label> 
											<c:if test = "${feildPrivilege.contains('EDIT_INSPECTION_PARAMETER')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="EDIT_INSPECTION_PARAMETER" id="Inspection"
												autocomplete="off" /> Edit Inspecton Parameter
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_INSPECTION_PARAMETER')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_INSPECTION_PARAMETER" id="Inspection"
												autocomplete="off" /> Delete Inspection Sheet
											</label>
											</c:if>
											<c:if
												test="${feildPrivilege.contains('REMOVE_SHEET_ASSIGN_TO_VEHICLE_TYPE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="REMOVE_SHEET_ASSIGN_TO_VEHICLE_TYPE" id="Inspection"
													autocomplete="off" /> Remove Sheet Assign to Vehicle Type
												</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-Inspection"
												autocomplete="off" /> All Inspection Permission
											</label>
										</div>

									</div>
								</div>

							</div>
							
							<div class="box" id="module_25" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Tally</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
										<c:if test = "${feildPrivilege.contains('TALLY_COMPANY_MASTER')}">
											<label class="L-size control-label" for="vehicle_theft">
												Tally Master :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="TALLY_COMPANY_MASTER" id="tallyCompany"
												autocomplete="off" /> Show Tally Company Master
											</label> 
										</c:if>	
										</div>
										
									</div>
								</div>
							</div>		
							<div class="box" id="module_26" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Vehicle Accident</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Accident :</label> 
												<c:if test = "${feildPrivilege.contains('ADD_VEHICLE_ACCIDENT')}">
													<label class="checkbox-inline">
														<input type="checkbox" name="privileges"
														value="ADD_VEHICLE_ACCIDENT" id="vehicleAccident"
														autocomplete="off" /> Add Vehicle Accident
													</label> 
												</c:if>	
											<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE_ACCIDENT')}">
												<label class="checkbox-inline">
													<input type="checkbox" name="privileges"
													value="VIEW_VEHICLE_ACCIDENT" id="vehicleAccident"
													autocomplete="off" /> Show Vehicle Accident
												</label> 
											</c:if>
										</div>
										<div class="row1">
										<label class="L-size control-label" for="vehicle_theft">
												</label>
											<c:if test = "${feildPrivilege.contains('EDIT_VEHICLE_ACCIDENT')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="EDIT_VEHICLE_ACCIDENT" id="vehicleAccident"
												autocomplete="off" /> Edit Vehicle Accident
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_VEHICLE_ACCIDENT')}"> 
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="DELETE_VEHICLE_ACCIDENT" id="vehicleAccident"
												autocomplete="off" /> Delete Vehicle Accident
											</label> 
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="allVehicleAccident"
												autocomplete="off" /> All Vehicle Accident Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							<div class="box" id="module_21" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Marquee Setting</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
										<c:if test = "${feildPrivilege.contains('MARQUEE_MASTER')}">
											<label class="L-size control-label" for="vehicle_theft">
												Marquee Setting :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="MARQUEE_MASTER" id="marquee_master"
												autocomplete="off" /> View Marquee Master
											</label> 
										</c:if>	
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-marquee_master"
												autocomplete="off" /> All Marquee Master Permission
											</label>

										</div>
									</div>
								</div>
							</div>
							
							<div class="box" id="module_24" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">PickAndDrop Setting</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
									
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												PickAndDrop Flavour :</label>
											<c:if test = "${feildPrivilege.contains('FLAVOR_FOUR_PRIVILEGE')}">
												 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="FLAVOR_FOUR_PRIVILEGE" id="pickAndDrop_master"
												autocomplete="off" /> View PickAndDrop Flavour
												</label> 
											</c:if>	
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												PickAndDrop Master :</label>
											<c:if test = "${feildPrivilege.contains('PICK_AND_DROP_MASTER')}">
												 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="PICK_AND_DROP_MASTER" id="pickAndDrop_master"
												autocomplete="off" /> View PickAndDrop Master
												</label> 
											</c:if>	
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											PickAndDrop Module :</label> 
											<c:if test = "${feildPrivilege.contains('CREATE_PICK_OR_DROP')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="CREATE_PICK_OR_DROP" id="Inspection"
												autocomplete="off" /> Create Pick Or Drop Tripsheet
											</label> 
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('EDIT_PICK_OR_DROP')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="EDIT_PICK_OR_DROP" id="Inspection"
												autocomplete="off" /> Edit Pick Or Drop Tripsheet
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('DELETE_PICK_OR_DROP')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DELETE_PICK_OR_DROP" id="Inspection"
												autocomplete="off" /> Delete Pick Or Drop Tripsheet
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											Invoice & Payment :</label> 
											<c:if test = "${feildPrivilege.contains('CREATE_PICK_OR_DROP_INVOICE')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="CREATE_PICK_OR_DROP_INVOICE" id="Inspection"
												autocomplete="off" /> Create Pick Or Drop Invoice
											</label> 
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('CANCEL_PICK_OR_DROP_INVOICE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="CANCEL_PICK_OR_DROP_INVOICE" id="Inspection"
												autocomplete="off" /> Cancel Pick Or Drop Invoice
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('CREATE_PICK_OR_DROP_INVOICE_PAYMENT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="CREATE_PICK_OR_DROP_INVOICE_PAYMENT" id="Inspection"
												autocomplete="off" /> Create Pick Or Drop Invoice Payment
											</label>
											</c:if>
										</div>
										
									</div>
								</div>
							</div>

							<div class="box" id="module_27" style="display: none;">
								<div class="box-header">
									<h3 class="box-title">Requisition Setting</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
										<div class="row1">
											<c:if
												test="${feildPrivilege.contains('ALL_REQUISITION_PERMISSIONS')}">
												<label class="L-size control-label" for="vehicle_theft">
													</label>
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="ALL_REQUISITION_PERMISSIONS"
													id="ALL_REQUISITION_PERMISSIONS" autocomplete="off" />Requisition Super Admin Permission
												</label>
											</c:if>
												<c:if
												test="${feildPrivilege.contains('REQUISITION_MARK_AS_COMPLETE')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="REQUISITION_MARK_AS_COMPLETE" id="REQUISITION"
													autocomplete="off" />Mark Requisition as Complete
												</label>
											</c:if>
										</div>
												<div class="row1">
												<label class="L-size control-label" for="vehicle_theft">
													</label>
											<c:if test = "${feildPrivilege.contains('CREATE_REQUISITION')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CREATE_REQUISITION"
												id="CREATE_REQUISITION" autocomplete="off" />Create
												Requisition
											</label> </c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_REQUISITION')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_REQUISITION"
												id="VIEW_REQUISITION" autocomplete="off" />View Requisition
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DELETE_REQUISITION')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DELETE_REQUISITION"
												id="DELETE_REQUISITION" autocomplete="off" />Delete
												Requisition
											</label>
											</c:if>
										</div>
									</div>
								</div>
							</div>


							<div class="box" id="module_2" style="display: none;">
							
							<div class="box">
								<div class="box-header">
									<h3 class="box-title">Report</h3>
								</div>
								<div class="box-body">
									<div class="[ form-group ]">
								<c:if test = "${feildPrivilege.contains('FLAVOR_TWO_PRIVILEGE')}">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Important Report :</label>
											<c:if test = "${feildPrivilege.contains('IMPORTANT_REPORT')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="IMPORTANT_REPORT"
												id="Report" autocomplete="off" />
											</label> 
											</c:if>

										</div>
									</c:if>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_VE_WI_RE_REPORT')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VE_WI_RE_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise Repair Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE_REPORT')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VEHICLE_REPORT" id="Report" autocomplete="off" />
												View Vehicle Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_ALL_VE_WI_SE_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_ALL_VE_WI_SE_REPORT" id="Report"
												autocomplete="off" /> All Vehicle Service Report Details
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VH_COMMENT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VH_COMMENT_REPORT" id="Report"
												autocomplete="off" /> Vehicle Comment Report
											</label>
											</c:if>
											</div>
											
											<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_PART_CONSUMTION_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLE_WISE_PART_CONSUMTION_REPORT" id="Report"
												autocomplete="off" /> Vehicle Wise Part Consumption Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DEPOT_WISE_PARTCONSUMED_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DEPOT_WISE_PARTCONSUMED_REPORT" id="Report" autocomplete="off" />
												Depot Wise Part Consumed Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VEHICLE_REPAIR_AND_PART_CONSUMPTION_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="VEHICLE_REPAIR_AND_PART_CONSUMPTION_REPORT" id="Report" autocomplete="off" />
													Vehicle Repair And Part Consumption Report
												</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('VEHICLE_ROUTE_CHANGE_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="VEHICLE_ROUTE_CHANGE_REPORT" id="Report" autocomplete="off" />
													Vehicle Route Change Report
												</label>
											</c:if>
											</div>
									<div class="row1">
										<label class="L-size control-label" for="vehicle_theft">
												</label>
											<c:if test = "${feildPrivilege.contains('VEHICLE_ROUTE_CHANGE_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="VEHICLE_ROUTE_CHANGE_REPORT" id="Report" autocomplete="off" />
													Vehicle Route Change Report
												</label>
											</c:if>
												<c:if test = "${feildPrivilege.contains('VEHICLE_INCIDENT_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="VEHICLE_INCIDENT_REPORT" id="Report" autocomplete="off" />
													Vehicle Incident Report
												</label>
											</c:if>
										</div>
											
											<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_BATTERY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLE_WISE_BATTERY_REPORT" id="Report"
												autocomplete="off" /> Vehicle Wise Battery Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_TYRE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLE_WISE_TYRE_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise Tyre Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('BANK_WISE_VEHICLE_EMI_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="BANK_WISE_VEHICLE_EMI_REPORT" id="Report" autocomplete="off" />
												Bank Wise Vehicle EMI Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_EMI_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLE_WISE_EMI_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise EMI Report
											</label>
											</c:if>
											</div>

										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Driver Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_DE_REPORT')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DR_DE_REPORT"
												id="Report" autocomplete="off" /> Driver details Report
											</label>
											</c:if>
													<c:if test="${feildPrivilege.contains('NEW_DRIVER_DETAILS_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="NEW_DRIVER_DETAILS_REPORT"
												id="Report" autocomplete="off" /> New Driver details Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_CO_RE_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_CO_RE_REPORT" id="Report" autocomplete="off" />
												Driver / Conductor All Remarks Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DL_EX_DA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DL_EX_DA_REPORT" id="Report" autocomplete="off" />
												DL Expiry Date Range Report
											</label>
											</c:if>
													<c:if test = "${feildPrivilege.contains('DRIVER_RENEWAL_REMINDER_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_RENEWAL_REMINDER_REPORT" id="Report" autocomplete="off" />
												Driver renewal reminder Reports
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_MO_ES_PF_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_MO_ES_PF_REPORT" id="Report"
												autocomplete="off" /> Driver Month wise ESI, PF Date Range
												Report
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DR_LO_HA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_LO_HA_REPORT" id="Report" autocomplete="off" />
												Driver Local Halt Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_LO_HA_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_LO_HA_REPORT" id="Report" autocomplete="off" />
												Driver Local Halt Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_TS_HA_BA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TS_HA_BA_REPORT" id="Report" autocomplete="off" />
												TripSheet HaltBata Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DE_PE_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DE_PE_REPORT"
												id="Report" autocomplete="off" /> Depot Wise Penalty Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DR_CO_PE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_CO_PE_REPORT" id="Report" autocomplete="off" />
												Driver / Conductor Wise Penalty Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DO_AD_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DO_AD_REPORT"
												id="Report" autocomplete="off" /> Depot Wise Advance Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DR_CO_AD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_CO_AD_REPORT" id="Report" autocomplete="off" />
												Driver / Conductor Wise Advance Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_AD_PE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_AD_PE_REPORT"
												id="Report" autocomplete="off" /> Advance / Penalty Depot
												Wise Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DRIVER_COMMENT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_COMMENT_REPORT" id="Report" autocomplete="off" />
												Driver Comment Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('DRIVER_TRIPSHEET_ADVANCE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_TRIPSHEET_ADVANCE_REPORT" id="Report" autocomplete="off" />
												Driver Tripsheet Advance Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('DRIVER_BASIC_DETAILS_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_BASIC_DETAILS_REPORT" id="Report" autocomplete="off" />
												Driver Basic Details Report
											</label>
											</c:if>
													<c:if test = "${feildPrivilege.contains('DRIVER_INCIDENT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_INCIDENT_REPORT" id="Report" autocomplete="off" />
												Driver Basic Incident Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DRIVER_LEDGER_ACCOUNT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_LEDGER_ACCOUNT_REPORT" id="Report" autocomplete="off" />
                                               show  Driver Ledger Account Report											
                                                 </label>
                                                 </c:if>
											<c:if test = "${feildPrivilege.contains('DRIVERS_LEDGER_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVERS_LEDGER_REPORT" id="Report" autocomplete="off" />
												Driver Ledger Report
											</label>
											</c:if>

											<c:if test = "${feildPrivilege.contains('DRIVER_SALARY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="DRIVER_SALARY_REPORT" id="Report" autocomplete="off" />
												Driver Salary Report
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Renewal Reminder Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_RR_CO_REPORT')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_RR_CO_REPORT" id="Report" autocomplete="off" />
												Renewal Reminder Compliance Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TO_VE_CO_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TO_VE_CO_REPORT" id="Report" autocomplete="off" />
												Total Vehicle Wise Compliance Report
											</label>
											</c:if>
											
												<c:if test = "${feildPrivilege.contains('MANDATORY_RENEWAL_PENDING_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="MANDATORY_RENEWAL_PENDING_REPORT" id="Report" autocomplete="off" />
											     Mandatory Renewal Pending Report 
											</label>
											</c:if>
											
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_TO_GO_CO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TO_GO_CO_REPORT" id="Report" autocomplete="off" />
												Total Group Wise Compliance Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_AP_RR_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_AP_RR_REPORT"
												id="Report" autocomplete="off" /> Approval Renewal Reminder
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('RENEWAL_EXPIRY_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="RENEWAL_EXPIRY_REPORT"
												id="Report" autocomplete="off" /> Renewal SubType Wise Expiry
												Report
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_RR_OVERDUE_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_RR_OVERDUE_REPORT"
												id="Report" autocomplete="off" /> Renewal Overdue/Duesoon
												Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Fuel Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_VE_FU_MI_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VE_FU_MI_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise Fuel Mileage Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_FU_RA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_FU_RA_REPORT"
												id="Report" autocomplete="off" /> Fuel Range Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_FU_CO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_FU_CO_REPORT"
												id="Report" autocomplete="off" /> Daily Fuel Consumption
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('USER_WISE_FUEL_ENTRY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="USER_WISE_FUEL_ENTRY_REPORT"
												id="Report" autocomplete="off" /> User Wise Fuel Entry Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('FUEL_EFFICIENCY_DATA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="FUEL_EFFICIENCY_DATA_REPORT"
												id="Report" autocomplete="off" /> Fuel Efficiency Data 
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('DATE_WISE_FUEL_ENTRY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DATE_WISE_FUEL_ENTRY_REPORT"
												id="Report" autocomplete="off" /> Date Wise Fuel Entry Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('Driver_Wise_Fuel_Entry_Report')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="Driver_Wise_Fuel_Entry_Report"
												id="Report" autocomplete="off" /> Driver Wise Fuel Entry Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
										
											<c:if test = "${feildPrivilege.contains('ALL_VEHICLES_MILEAGE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ALL_VEHICLES_MILEAGE_REPORT"
												id="Report" autocomplete="off" /> All Vehicle Mileage Report
											</label>
											</c:if>
										
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('TRIPSHEET_WISE_FUEL_ENTRY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="TRIPSHEET_WISE_FUEL_ENTRY_REPORT"
												id="Report" autocomplete="off" /> TripSheet Wise FuelEntry 
												Report
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Attendance Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_AT_REPORT')}">	
											 <label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_DR_AT_REPORT" id="Report" autocomplete="off" />
												Driver Attendance Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_GO_DR_AT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_GO_DR_AT_REPORT" id="Report" autocomplete="off" />
												Depot Wise Driver Attendance Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_PO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DR_PO_REPORT"
												id="Report" autocomplete="off" /> Driver Attendance Point
												Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_GO_DR_PO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_GO_DR_PO_REPORT" id="Report" autocomplete="off" />
												Group Wise Driver Point Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_DO_DA_AT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DO_DA_AT_REPORT" id="Report" autocomplete="off" />
												Depot Wise Driver Attendance Report
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Reminder Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_OV_SR_REPORT')}">
											 <label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_OV_SR_REPORT" id="Report" autocomplete="off" />
												Overdue Service Reminder Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DU_SR_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DU_SR_REPORT"
												id="Report" autocomplete="off" /> DueSoon Service Reminder
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_ACTIVE_SR')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VEHICLE_WISE_ACTIVE_SR"
												id="Report" autocomplete="off" /> Vehicle Wise Active Service Reminder
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Service Entries Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_VEN_SE_REPORT')}">	
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_VEN_SE_REPORT" id="Report" autocomplete="off" />
												Vendor Wise Service Entries Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VEL_SE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VEL_SE_REPORT"
												id="Report" autocomplete="off" /> Vehicle Wise Service
												Entries Report
											</label>
											</c:if>
										</div>
											<div class="row1">
												<c:if test="${feildPrivilege.contains('VIEW_DSE_REPORT')}">
												<label class="L-size control-label" for="vehicle_theft">
													Dealer Service Report :</label>
													<label class="checkbox-inline"> <input
														type="checkbox" name="privileges" value="VIEW_DSE_REPORT"
														id="Report" autocomplete="off" /> Dealer Service Report
													</label>
												</c:if>
											</div>
											<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Issues Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_IS_DA_ST_REPORT')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_IS_DA_ST_REPORT" id="Report" autocomplete="off" />
												Issues Daily Status Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_IS_RE_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_IS_RE_REPORT"
												id="Report" autocomplete="off" /> Issues Reported Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CHANGE_ISSUE_TYPE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="CHANGE_ISSUE_TYPE_REPORT" id="Report" autocomplete="off" />
												Issue Type changed to BreakDown Report
											</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
										<c:if test = "${feildPrivilege.contains('SHOW_ANALYSIS_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="SHOW_ANALYSIS_REPORT" id="Report" autocomplete="off" />
												BreakDown Analysis Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_VEHICLE_BREAKDOWN_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="SHOW_VEHICLE_BREAKDOWN_REPORT" id="Report" autocomplete="off" />
													Vehicle & Vehicle BreakDown Report
												</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												TripSheet Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_US_TS_AD_REPORT')}">		
										<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_US_TS_AD_REPORT" id="Report" autocomplete="off" />
												User Wise Trip sheet Advances Report
											</label> 
										</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_BR_TS_AD_REPORT')}">	
										<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_BR_TS_AD_REPORT" id="Report" autocomplete="off" />
												Branch Wise Trip sheet Advances Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_DA_TS_AD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DA_TS_AD_REPORT" id="Report" autocomplete="off" />
												Day wise Trip sheet Advances Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TS_DA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_TS_DA_REPORT"
												id="Report" autocomplete="off" /> Trip sheet date Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_CL_TS_AD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_CL_TS_AD_REPORT" id="Report" autocomplete="off" />
												Closed wise TripSheet Advances Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TS_CO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_TS_CO_REPORT"
												id="Report" autocomplete="off" /> Trip Sheet Collection
												Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_TS_ST_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_TS_ST_REPORT"
												id="Report" autocomplete="off" /> Trip Sheet Status Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TS_RO_DI_KM_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TS_RO_DI_KM_REPORT" id="Report"
												autocomplete="off" /> Trip Sheet Route Wise Difference KM
												&amp; Volume Report
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('Create_Day_Wise_Expense')}">
											<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="Create_Day_Wise_Expense" id="Report"
													autocomplete="off" /> Create Day Wise Expense Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VOUCHER_DATE_WISE_EXPENSE')}">
												<label class="checkbox-inline"> <input
														type="checkbox" name="privileges"
														value="Create_Day_Wise_Expense" id="Report"
														autocomplete="off" /> Voucher Date Wise Expense Report
												</label>
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft"> </label> 
											<c:if test = "${feildPrivilege.contains('TRIPSHEET_COLLECTION_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="TRIPSHEET_COLLECTION_REPORT" id="Report"
													autocomplete="off" /> TripSheet Collection Report New 
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('TRIPSHEET_INCOME_REPORT_BY_INCOME_NAME')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="TRIPSHEET_INCOME_REPORT_BY_INCOME_NAME"
													id="Report" autocomplete="off" /> Income Name Wise Summary Report
												</label>
											</c:if>
										</div>
											
											
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_ROUTE_WISE_TS_REPORT')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="VIEW_ROUTE_WISE_TS_REPORT" id="Report"
													autocomplete="off" /> Route Wise Trip Sheet Report
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_DUE_AMOUNT_REPORT')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="SHOW_DUE_AMOUNT_REPORT" id="Report"
													autocomplete="off" /> Tripsheet DueAmount Module
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('B_INCOME')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="B_INCOME" id="Report"
													autocomplete="off" /> B INCOME PAYMENT
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('E_INCOME')}">
												 <label class="checkbox-inline"> <input
													type="checkbox" name="privileges"
													value="E_INCOME" id="Report"
													autocomplete="off" /> E INCOME PAYMENT
												</label>
											</c:if>
										</div>
										<c:if test = "${feildPrivilege.contains('FLAVOR_TWO_PRIVILEGE')}">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												TripDaily Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DE_DA_TD_CB_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DE_DA_TD_CB_REPORT" id="Report"
												autocomplete="off" /> Depot Wise Daily Trip Collection &amp;
												CashBook Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DE_DA_TD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DE_DA_TD_REPORT" id="Report" autocomplete="off" />
												Depot Wise Daily Trip Collection Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_DE_VE_TI_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DE_VE_TI_REPORT" id="Report" autocomplete="off" />
												Depot Wise Vehicle Time Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DE_RO_TI_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DE_RO_TI_REPORT" id="Report" autocomplete="off" />
												Depot Wise Route Time Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_VE_DA_TD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VE_DA_TD_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise Date Range Weekly, Monthly, Yearly Trip
												Collection Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DA_FU_MI_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DA_FU_MI_REPORT" id="Report" autocomplete="off" />
												Driver Wise Fuel Mileage Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_CO_TD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_CO_TD_REPORT"
												id="Report" autocomplete="off" /> Conductor Wise Trip
												Collection Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_RO_DA_TD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_RO_DA_TD_REPORT" id="Report" autocomplete="off" />
												Route Wise Date Range Weekly, Monthly, Yearly Trip
												Collection Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_AL_RO_DA_TD_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_AL_RO_DA_TD_REPORT" id="Report"
												autocomplete="off" /> All Depot Wise Date Range Weekly,
												Monthly, Yearly Trip Collection Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DA_TD_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DA_TD_REPORT"
												id="Report" autocomplete="off" /> Date Range Wise Trip
												Collection Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('TRIP_SHEET_EXPENSE_NAME')}">
												<label class="checkbox-inline"> <input
														type="checkbox" name="privileges" value="TRIP_SHEET_EXPENSE_NAME"
														id="Report" autocomplete="off" /> Trip Collection Expense 
														Name Report
													</label>
											</c:if>
										</div>	
													
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('DEPOT_WISE_DATE_RANGE_WEEKLY_MONTHLY_YEARLY_TCR')}">
												<label class="checkbox-inline"> <input
														type="checkbox" name="privileges" value="DEPOT_WISE_DATE_RANGE_WEEKLY_MONTHLY_YEARLY_TCR"
														id="Report" autocomplete="off" /> 
														Depot Wise Date Range Weekly,Monthly,Yearly Trip Collection Report
													</label>
											</c:if>
										
										</div>													
												
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_FUEL_MILEAGE_REPORTS')}">
												<label class="checkbox-inline"> <input
														type="checkbox" name="privileges" value="VEHICLE_WISE_FUEL_MILEAGE_REPORTS"
														id="Report" autocomplete="off" /> 
														Vehicle Wise Fuel Mileage Report
													</label>
											</c:if>
										</div>										
										
										
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('DEPOT_WISE_DAILY_TRIP_COLLECTION_STATUS_REPORT')}">
												<label class="checkbox-inline"> <input
														type="checkbox" name="privileges" value="DEPOT_WISE_DAILY_TRIP_COLLECTION_STATUS_REPORT"
														id="Report" autocomplete="off" /> 
														Depot Wise Daily Trip Collection Status Report 
													</label>
											</c:if>
										</div>										
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												TripCollection Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_DA_TC_REPORT')}">
											<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_DA_TC_REPORT" id="Report" autocomplete="off" />
												Daily Trip Collection Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VE_TC_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VE_TC_REPORT"
												id="Report" autocomplete="off" /> Vehicle Wise Trip
												Collection Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('TRIP_SHEET_EXPENSE')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="TRIP_SHEET_EXPENSE"
												id="Report" autocomplete="off" /> TripSheet EXPENSE
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_GR_TC_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_GR_TC_REPORT"
												id="Report" autocomplete="off" /> Group Wise Trip
												Collection Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TO_DR_BA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TO_DR_BA_REPORT" id="Report" autocomplete="off" />
												Total Driver Balance Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_TO_CO_BA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_TO_CO_BA_REPORT" id="Report" autocomplete="off" />
												Total Conductor Balance Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_JA_TC_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_JA_TC_REPORT" id="Report" autocomplete="off" />
												Driver JAMA Collection Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_CO_JA_TC_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_CO_JA_TC_REPORT" id="Report" autocomplete="off" />
												Conductor JAMA Collection Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DR_AD_JA_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DR_AD_JA_REPORT" id="Report" autocomplete="off" />
												Driver Advance JAMA Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_CO_AD_JA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_CO_AD_JA_REPORT" id="Report" autocomplete="off" />
												Conductor Advance JAMA Report
											</label>
											</c:if>
										</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Part Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_PA_ST_REPORT')}">	
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PA_ST_REPORT"
												id="Report" autocomplete="off" /> Part Stock Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DA_IN_ST_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_DA_IN_ST_REPORT" id="Report" autocomplete="off" />
												Date Wise Inventory Stock Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_PA_PU_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PA_PU_REPORT"
												id="Report" autocomplete="off" /> Part Purchase Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_PA_TR_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PA_TR_REPORT"
												id="Report" autocomplete="off" /> Part Transfered Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_PA_TR_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_PA_TR_REPORT"
												id="Report" autocomplete="off" /> Part Wise Consumtion Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('PART_PURCHASE_INVOICE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="PART_PURCHASE_INVOICE_REPORT"
												id="Report" autocomplete="off" /> Part Purchase Invoice Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('TECHNICIAN_WISE_PART_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="TECHNICIAN_WISE_PART_REPORT"
												id="Report" autocomplete="off" /> Technician Wise Part Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('WARRANTY_PART_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="WARRANTY_PART_REPORT"
												id="Report" autocomplete="off" /> Warranty Part Repot
											</label> 
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('PART_REQUISITION_STATUS_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="PART_REQUISITION_STATUS_REPORT"
												id="Report" autocomplete="off" /> Part Requisition Status Wise Report
											</label> 
											</c:if>
											
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Tyre Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_TY_PO_REPORT')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_TY_PO_REPORT"
												id="Report" autocomplete="off" /> Tyre Purchase Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_TY_ST_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_TY_ST_REPORT"
												id="Report" autocomplete="off" /> Tyre Stock Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_TY_RE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_TY_RE_REPORT"
												id="Report" autocomplete="off" /> Tyre Retread Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('TYRE_SENT_FOR_RETREADING_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="TYRE_SENT_FOR_RETREADING_REPORT"
												id="Report" autocomplete="off" /> Tyre Sent for Retreading Report
											</label>
											</c:if>
														<c:if test = "${feildPrivilege.contains('VEHICLEWISE_TYRE_ASSIGN_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VEHICLEWISE_TYRE_ASSIGN_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise Tyre Assign Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('Tyre_Retread_Invoice_Report')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="Tyre_Retread_Invoice_Report"
												id="Report" autocomplete="off" /> Tyre Retread Invoice Report
											</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('VEHICLE_TYRE_ASSIGNMENT_HISTORY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VEHICLE_TYRE_ASSIGNMENT_HISTORY_REPORT"
												id="Report" autocomplete="off" /> Vehicle Tyre Assignment History Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Purchase Order Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_VE_PO_REPORT')}">
												<label class="checkbox-inline">
												<input type="checkbox" name="privileges"
												value="VIEW_VE_PO_REPORT" id="Report" autocomplete="off" />
												Vendor Wise Purchase Order Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_DA_PO_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_DA_PO_REPORT"
												id="Report" autocomplete="off" /> Date Range Wise Purchase
												Order Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_PU_TO_PA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_PU_TO_PA_REPORT" id="Report" autocomplete="off" />
												Purchase Total Parts Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_PURCHASE_ORDER_STATUS_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_PURCHASE_ORDER_STATUS_REPORT" id="Report" autocomplete="off" />
												Purchase Order Status Wise Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												WorkOrder Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_LO_WO_REPORT')}">
												 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_LO_WO_REPORT"
												id="Report" autocomplete="off" /> Location Wise WorkOrder
												Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VE_WO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VE_WO_REPORT"
												id="Report" autocomplete="off" /> Vehicle Wise WorkOrder
												Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_GR_WO_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_GR_WO_REPORT"
												id="Report" autocomplete="off" /> Group Wise WorkOrder
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_WO_PA_CO_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_WO_PA_CO_REPORT" id="Report" autocomplete="off" />
												WorkOrder Part Consuming Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VEHICLE_WISE_WORK_ORDER_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_VEHICLE_WISE_WORK_ORDER_REPORT" id="Report" autocomplete="off" />
												Vehicle Wise Work Order Report
											</label>
											</c:if>
										</div>

										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												CashBook Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_CB_DA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_CB_DA_REPORT"
												id="Report" autocomplete="off" /> Cash Book Date Range
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_CA_DA_REPORT')}">
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_CA_DA_REPORT"
												id="Report" autocomplete="off" /> Cash Date Range Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VIEW_CB_PR_DA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges"
												value="VIEW_CB_PR_DA_REPORT" id="Report" autocomplete="off" />
												CashBook Payment/Receipt Date Range Report
											</label> 
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_CB_ST_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_CB_ST_REPORT"
												id="Report" autocomplete="off" /> CashBook Status Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vendor Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_VEN_REPORT')}">
												 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VEN_REPORT"
												id="Report" autocomplete="off" /> Vendor Payment
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_VEN_GST_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_VEN_GST_REPORT"
												id="Report" autocomplete="off" /> Vendor GST
												Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VENDOR_WISE_PAYMENT_STATUS_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VENDOR_WISE_PAYMENT_STATUS_REPORT"
												id="Report" autocomplete="off" /> Vendor Wise Payment Status
												Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Lorry Hire Report :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="LORRY_HIRE_PAYMENT_REPORT"
												id="Report" autocomplete="off" /> Vendor Lorry Hire Payment
												Report
											</label>
											<c:if test = "${feildPrivilege.contains('VENDOR_LORRY_HIRE_DETAILS_REPORT')}">
												<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VENDOR_LORRY_HIRE_DETAILS_REPORT"
												id="Report" autocomplete="off" /> 
														Vendor Lorry Hire Details Report
												</label>
											</c:if>
										</div>									
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Battery Report :</label>
											<c:if test = "${feildPrivilege.contains('VIEW_BATTERY_REPORT')}">	
											 <label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_BATTERY_REPORT"
												id="Report" autocomplete="off" /> Battery Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_BATTERY_SCRAP_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_BATTERY_SCRAP_REPORT"
												id="Report" autocomplete="off" /> Battery Scrap Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_BATTERY_STOCK_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_BATTERY_STOCK_REPORT"
												id="Report" autocomplete="off" /> Battery Stock Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('BATTERY_PURCHASE_INVOICE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="BATTERY_PURCHASE_INVOICE_REPORT"
												id="Report" autocomplete="off" /> Battery Purchase Invoice Report
											</label>
											</c:if>
												<c:if test = "${feildPrivilege.contains('BATTERY_WISE_HISTORY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="BATTERY_WISE_HISTORY_REPORT"
												id="Report" autocomplete="off" /> Battery History Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Profit & Loss Report :</label> 
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_USES_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VEHICLE_WISE_USES_REPORT"
												id="Report" autocomplete="off" /> Vehicle wise usage and movement report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ROUTE_WISE_USES_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="ROUTE_WISE_USES_REPORT"
												id="Report" autocomplete="off" /> Route wise usage and movement report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('VIEW_P_AND_L_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_P_AND_L_REPORT"
												id="Report" autocomplete="off" /> Vehicle Wise Profit And Loss Report
											</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('VEHICLE_GROUP_WISE_P_AND_L_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="VEHICLE_GROUP_WISE_P_AND_L_REPORT"
													id="Report" autocomplete="off" /> Vehicle Group Wise Profit And Loss Report
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('ROUTE_WISE_P_AND_L_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="ROUTE_WISE_P_AND_L_REPORT"
													id="Report" autocomplete="off" />  Route Wise Profit And Loss Report
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label>
											<c:if test = "${feildPrivilege.contains('GROUP_WISE_USAGE_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="GROUP_WISE_USAGE_REPORT"
													id="Report" autocomplete="off" /> Group Wise Vehicle usage and movement report
												</label>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Vehicle Inspection Report :</label> 
											<c:if test = "${feildPrivilege.contains('VEHICLE_WISE_ISPECTION_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VEHICLE_WISE_ISPECTION_REPORT"
												id="Report" autocomplete="off" /> Vehicle wise inspection report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('GROUP_WISE_ISPECTION_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="GROUP_WISE_ISPECTION_REPORT"
												id="Report" autocomplete="off" /> Group wise inspection report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Checking Report :</label> 
											<c:if test = "${feildPrivilege.contains('CHECKING_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CHECKING_REPORT"
												id="Report" autocomplete="off" /> Checking Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('CONDUCTOR_HISTORY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="CONDUCTOR_HISTORY_REPORT"
												id="Report" autocomplete="off" /> Conductor History Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Upholstery Report :</label> 
											<c:if test = "${feildPrivilege.contains('UPHOLSTERY_PURCHASE_INVOICE_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="UPHOLSTERY_PURCHASE_INVOICE_REPORT"
												id="Report" autocomplete="off" /> Upholstery Purchase Invoice Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UPHOLSTERY_STOCK_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UPHOLSTERY_STOCK_REPORT"
													id="Report" autocomplete="off" /> Upholstery Stock Report
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UPHOLSTERY_ASSIGNMENT_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UPHOLSTERY_ASSIGNMENT_REPORT"
													id="Report" autocomplete="off" /> Upholstery Assignment Report
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('UPHOLSTERY_LOSS_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UPHOLSTERY_LOSS_REPORT"
													id="Report" autocomplete="off" /> Upholstery Loss Report
												</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
											</label> 
											<c:if test = "${feildPrivilege.contains('UPHOLSTERY_STOCK_TRANSFER_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UPHOLSTERY_STOCK_TRANSFER_REPORT"
													id="Report" autocomplete="off" /> Stock Transfer report
												</label>
											</c:if>
											
											<c:if test = "${feildPrivilege.contains('UPHOLSTERY_SENT_TO_LAUNDRY_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="UPHOLSTERY_SENT_TO_LAUNDRY_REPORT"
													id="Report" autocomplete="off" /> Upholstery Sent To Laundry Report
												</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												WareHouse Location Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_WAREHOUSE_LOCATION_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_WAREHOUSE_LOCATION_REPORT"
												id="Report" autocomplete="off" /> View Warehouse Location Report
											</label>
											</c:if>		
											<c:if test = "${feildPrivilege.contains('WAREHOUSELOCATION_WISE_COST_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="WAREHOUSELOCATION_WISE_COST_REPORT"
												id="Report" autocomplete="off" /> WareHouse Location Wise Cost Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Urea Report :</label> 
											<c:if test = "${feildPrivilege.contains('VIEW_UREA_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="VIEW_UREA_REPORT"
												id="Report" autocomplete="off" /> View Urea Report
											</label>
											</c:if>	
											<c:if test = "${feildPrivilege.contains('DATE_WISE_UREA_ENTRY_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="DATE_WISE_UREA_ENTRY_REPORT"
												id="Report" autocomplete="off" /> Date Wise UreaEntry Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Pick Or Drop Report :</label> 
											<c:if test = "${feildPrivilege.contains('SHOW_PICK_DROP_REPORTS')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SHOW_PICK_DROP_REPORTS"
												id="Report" autocomplete="off" /> View Pick Or Drop Report
											</label>
											</c:if>	
											<c:if test = "${feildPrivilege.contains('SHOW_PARTY_WISE_TRIP_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SHOW_PARTY_WISE_TRIP_REPORT"
												id="Report" autocomplete="off" /> Party Wise Trip Report
											</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('SHOW_PARTY_WISE_INVOICE_PAYMENT_REPORT')}">
											<label class="checkbox-inline"> <input
												type="checkbox" name="privileges" value="SHOW_PARTY_WISE_INVOICE_PAYMENT_REPORT"
												id="Report" autocomplete="off" /> Party Wise Invoice Payment Report
											</label>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Refreshment Report :</label> 
											<c:if test = "${feildPrivilege.contains('REFRESHMENT_CONSUMPTION_REPORT')}">
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="REFRESHMENT_CONSUMPTION_REPORT"
													id="Report" autocomplete="off" /> Refreshment Consumption Report
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('REFRESHMENT_STOCK_REPORT')}">
												<label class="checkbox-inline"> <input
														type="checkbox" name="privileges" value="REFRESHMENT_STOCK_REPORT"
														id="Report" autocomplete="off" /> Refreshment Stock Report
												</label>
											</c:if>
											
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												Comparison Report :</label> 
											<c:if test = "${feildPrivilege.contains('COMPARISION_REPORT')}">	
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="COMPARISION_REPORT"
													id="Report" autocomplete="off" /> Vehicle Comparison Report
												</label>
											</c:if>
											<c:if test = "${feildPrivilege.contains('KM_COMPARISON_REPORT')}">	
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="KM_COMPARISON_REPORT"
													id="Report" autocomplete="off" /> TripSheet Manual Vs GPS KM Comparision Report
												</label>
											</c:if>
										</div>

											<div class="row1">
												<label class="L-size control-label"> User Activity
													Report</label>
												<c:if
													test="${feildPrivilege.contains('SHOW_USER_ACTIVITY_REPORT')}">
													<label class="checkbox-inline"> <input
														type="checkbox" name="privileges"
														value="SHOW_USER_ACTIVITY_REPORT" id="Report"
														autocomplete="off" /> User Wise Activity Report
													</label>
												</c:if>
											</div>
											<div class="row1">
											<label class="L-size control-label" >
												Repair Stock Report</label> 
											<c:if test = "${feildPrivilege.contains('REPAIR_STOCK_REPORT')}">	
												<label class="checkbox-inline"> <input
													type="checkbox" name="privileges" value="REPAIR_STOCK_REPORT"
													id="Report" autocomplete="off" />Repair Stock Report
												</label>
											</c:if>
										</div>
											<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">
												All :</label> <label class="checkbox-inline"> <input
												type="checkbox" name="" id="all-Report" autocomplete="off" />
												All Report Permission
											</label>

										</div>
									</div>
								</div>

							</div>


							</div>
							<div class="form-group">
								<label class="L-size control-label" for="vehicle_theft"></label>
								<div class="col-sm-5">
									<fieldset class="form-actions">
										<a class="btn btn-link" href="newRole.html">Cancel</a> <input
											type="button" class="btn btn-info" id="btnResetCheckBox"
											value="Reset Check Box" />
									</fieldset>
								</div>
							</div>
						</form>

					</div>
				</div>
			</sec:authorize>
		</div>

	</section>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/roleAddToCompany.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var initValues = ${privileges};
						$('#btnResetCheckBox')
								.click(
										function() {
											$('#myForm')
													.find(
															':checkbox[name^="privileges"]')
													.each(
															function() {
																$(this)
																		.prop(
																				"checked",
																				($
																						.inArray(
																								$(
																										this)
																										.val(),
																								initValues) != -1));
															});
										});
						$('#btnResetCheckBox').trigger('click');
					});
</script>
