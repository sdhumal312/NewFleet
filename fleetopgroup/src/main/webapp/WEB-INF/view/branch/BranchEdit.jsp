<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/vehicle.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/newBranch.in"/>">Branch Details</a> / <span
							id="NewVehicle">Edit Branch</span>
					</div>
					<div class="pull-right">
						<a class="btn btn-link" href="<c:url value="/newBranch.in"/>">
							<span id="AddVehicle"> Cancel</span>
						</a>
					</div>
				</div>
				<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_BRANCH')">

					<div class="pull-left">
						<h3 class="secondary-header-title">
							<a href="showBranch.in?branch_id=${branch.branch_id}"> <c:out
									value="${branch.branch_name}" /> - <c:out
									value="${branch.branch_code}" /></a>
						</h3>
						
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="email"> </span> <span
									class="text-muted"><c:out value="${branch.branch_email}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Phone Number"> </span> <span
									class="text-muted"><c:out
											value="${branch.branch_phonenumber}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Phone Number"> </span> <span
									class="text-muted"><c:out
											value="${branch.branch_mobilenumber}" /></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="main">
			<div class="main-body">
				<sec:authorize access="!hasAuthority('ADD_BRANCH')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_BRANCH')">
					<div class="row">
						<div class="box">
							<div class="box-body">
								<div class="container">
									<ul class="tabs">
										<li class="tab-link current" data-tab="Details"><span
											id="Detail">BASIC DETAILS</span></li>
										<c:if test="${configuration.showBranchInchargeAndTime}">		
											<li class="tab-link" data-tab="incharge"><span>INCHARGE
												&amp; TIME DETAILS</span></li>
										</c:if>	
										<c:if test="${configuration.showBranchProperty}">			
											<li class="tab-link" data-tab="property"><span id="">PROPERTY
												DETAILS</span></li>
										</c:if>
										<c:if test="${configuration.showBranchOwnership}">				
											<li class="tab-link" data-tab="ownership"><span id="">OWNERSHIP
												DETAILS</span></li>
										</c:if>		

									</ul>
									<form id="formEditBranch"
										action="<c:url value="/updateBranch.in"/>" method="post"
										enctype="multipart/form-data" name="formEditBranch"
										role="form" class="form-horizontal">
										<div id="Details" class="tab-content current">
											<div class="form-horizontal ">
												<fieldset>
													<legend id="Identification">Branch Basic Details </legend>
													<div class="row1" id="grpbranchName" class="form-group">
														<label class="L-size control-label" for="branchName"><span
															id="RegistrationNumber">Branch Name :</span><abbr
															title="required">*</abbr></label>
														<div class="I-size">

															<input name="branch_id" type="hidden" required="required"
																value="${branch.branch_id}">
															<!-- branch Id -->
															<input class="form-text" id="branchName"
																name="branch_name" type="text" 
																maxlength="200" placeholder="Enter Branch Name"
																value="${branch.branch_name}" readonly="readonly"
																onkeypress="return IsBranchName(event);"
																ondrop="return false;"> <span
																id="branchNameIcon" class=""></span>
															<div id="branchNameErrorMsg" class="text-danger"></div>
															<label class="error" id="errorBranchName"
																style="display: none"> </label>
														</div>
													</div>
													<div class="row1" id="grpbranchCode" class="form-group">
														<label class="L-size control-label" for="branchCode">Branch Code <abbr
															title="required">*</abbr>
														</label>
														<div class="I-size">
															<input class="form-text" id="branchCode"
																name="branch_code" type="text" maxlength="30" 
																placeholder="Enter Branch Code" 
																value="${branch.branch_code}"
																onkeypress="return IsBranchCode(event);"
																ondrop="return false;"><span id="branchCodeIcon"
																class=""></span>
															<div id="branchCodeErrorMsg" class="text-danger"></div> <label class="error"
																id="errorBranchCode" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Address :</label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_address" maxlength="150" type="text"
																placeholder="Enter Branch Address"
																value="${branch.branch_address}"
																onkeypress="return IsBranchAddress(event);"
																ondrop="return false;"> <label class="error"
																id="errorBranchAddress" style="display: none"> </label>
														</div>
													</div>

													<div class="row1">
														<label class="L-size control-label">Country :</label>
														<div class="I-size">
															<select name="branch_country"
																class="select2 countries form-text" style="width: 100%"
																id="countryId">
																<option value="${branch.branch_country}"
																	selected="selected">${branch.branch_country}</option>
															</select>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">State/Province/Region
															:</label>
														<div class="I-size">

															<select name="branch_state"
																class="select2 states form-text" style="width: 100%"
																id="stateId">
																<option value="${branch.branch_state}"
																	selected="selected">${branch.branch_state}</option>
																<option value="">Select State</option>
															</select>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">City :</label>
														<div class="I-size">
															<select name="branch_city"
																class="select2 cities form-text" style="width: 100%"
																id="cityId">
																<option value="${branch.branch_city}"
																	selected="selected">${branch.branch_city}</option>
																<option value="">Select City</option>
															</select>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Zip/Pin Code :</label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_pincode" type="text" maxlength="6"
																placeholder="Enter Pincode"
																value="${branch.branch_pincode}"
																onkeypress="return IsBranchPin(event);"
																ondrop="return false;"> <label class="error"
																id="errorBranchPin" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Branch
															landMark :</label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_landmark" type="text" maxlength="200"
																placeholder="Enter Branch LandMark"
																value="${branch.branch_landmark}"
																onkeypress="return IsBranchLandMark(event);"
																ondrop="return false;"> <label class="error"
																id="errorBranchLandMark" style="display: none">
															</label>
														</div>
													</div>

													<div class="row1" id="grpbranchCompany" class="form-group">
														<!-- <label class="L-size control-label" for="branchCompany">Company
															<abbr title="required">*</abbr>
														</label> -->
														<div class="I-size">
															<%-- <select class="form-control select2" name="company_id"
																style="width: 100%;"  id="branchCompany">
																<c:forEach items="${company}" var="company">
																	<c:if test="${company.company_id == branch.company_id}">
																		<option value="${branch.company_id}"
																			selected="selected">
																			<c:out value="${branch.company_name}" />
																		</option>
																	</c:if>
																	<option value="${company.company_id}">
																		<c:out value="${company.company_name}" />
																	</option>
																</c:forEach>
															</select> --%><span id="branchCompanyIcon" class=""></span>
															<div id="branchCompanyErrorMsg" class="text-danger"></div>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Branch Email :</label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_email" maxlength="50" type="email"
																placeholder="Enter Branch Email"
																value="${branch.branch_email}"
																onkeypress="return IsBranchEmail(event);"
																ondrop="return false;"> <label class="error"
																id="errorBranchEmail" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Branch Phone
															Number :</label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_phonenumber" type="text" maxlength="15"
																placeholder="Enter Branch Phone"
																value="${branch.branch_phonenumber}"
																onkeypress="return IsBranchPhone(event);"
																ondrop="return false;"> <label class="error"
																id="errorBranchPhone" style="display: none"> </label>
														</div>
													</div>
													<div class="row1" id="grpbranchMobile" class="form-group">
														<label class="L-size control-label" for="branchMobile">Branch Mobile
															Number <abbr title="required">*</abbr>:</label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_mobilenumber" type="text" maxlength="15"
																placeholder="Enter Branch Phone" id="branchMobile"
																value="${branch.branch_mobilenumber}"
																onkeypress="return IsBranchMobile(event);"
																ondrop="return false;"><span
																id="branchMobileIcon" class=""></span>
															<div id="branchMobileErrorMsg" class="text-danger"></div> <label class="error"
																id="errorBranchMobile" style="display: none"> </label>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
										<c:if test="${configuration.showBranchInchargeAndTime}">
										<!-- Specifications details -->
										<div id="incharge" class="tab-content">
											<div class="form-horizontal ">
												<fieldset>
													<legend id="Class">InCharge</legend>
													<div class="row1" id="grpbranchIncharge" class="form-group">
														<label class="L-size control-label" for="branchIncharge">Branch
															InCharge :<abbr title="required">*</abbr></label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_incharge" type="text" maxlength="200"
																placeholder="Enter Name" id="branchIncharge"
																value="${branch.branch_incharge}"
																onkeypress="return IsBranchInCharge(event);"
																ondrop="return false;"><span
																id="branchInchargeIcon" class=""></span>
															<div id="branchInchargeErrorMsg" class="text-danger"></div> <label class="error"
																id="errorBranchInCharge" style="display: none">
															</label>
														</div>
													</div>
													<div class="row1" id="grpbranchInmobile" class="form-group">
														<label class="L-size control-label" for="branchInmobile">Branch
															InCharge Phone :<abbr title="required">*</abbr></label>
														<div class="I-size">
															<input class="string required form-text"
																name="branch_incharge_phone" type="text" maxlength="15"
																placeholder="Enter Branch InCharge Phone" id="branchInmobile"
																value="${branch.branch_incharge_phone}"
																onkeypress="return IsBranchInChargePhone(event);"
																ondrop="return false;"> <span
																id="branchInmobileIcon" class=""></span>
															<div id="branchInmobileErrorMsg" class="text-danger"></div><label class="error"
																id="errorBranchInChargePhone" style="display: none">
															</label>
														</div>
													</div>

													<div class="row1" >
														<label class="L-size control-label" id="ClassofVehicle">Branch
															Time From</label>
														<div class="I-size">
															<div class="L-size">
																<div class="input-group clockpicker">
																	<input type="text" class="form-text"
																		value="${branch.branch_time_from}"
																		name="branch_time_from"> <span
																		class="input-group-addon"> <i
																		class="fa fa-clock-o" aria-hidden="true"></i>
																	</span>
																</div>
															</div>
															<label class="L-size control-label" id="ClassofVehicle">Time
																To</label>
															<div class="L-size">
																<div class="input-group clockpicker">
																	<input type="text" class="form-text"
																		value="${branch.branch_time_to}" name="branch_time_to">
																	<span class="input-group-addon"> <span
																		class="fa fa-clock-o" aria-hidden="true"></span>
																	</span>
																</div>
															</div>
														</div>
													</div>

												</fieldset>
											</div>
										</div>
										</c:if>	
										<c:if test="${configuration.showBranchProperty}">
										<!-- Property details -->
										<div id="property" class="tab-content">
											<div class="form-horizontal ">
												<fieldset>
													<legend id="Weight">Property Details</legend>
													<div class="row1">
														<label class="L-size control-label">Annual
															Increment Percentage : </label>
														<div class="I-size">
															<input class="form-text" id="annual_increment"
																name="annual_increment" type="text" maxlength="3"
																required placeholder="Enter Percentage"
																value="${branch.annual_increment}"
																onkeypress="return IsAnnualPer(event);"
																ondrop="return false;"> <label class="error"
																id="errorAnnualPer" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">ADVANCE PAID:
															: </label>
														<div class="I-size">
															<input class="form-text" id="advance_paid"
																name="advance_paid" type="text" maxlength="7" required
																placeholder="Enter Advance Amount"
																value="${branch.advance_paid}"
																onkeypress="return IsAnnualPer(event);"
																ondrop="return false;"> <label class="error"
																id="errorAnnualPer" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Lease Amount
															PAID: : </label>
														<div class="I-size">
															<input class="form-text" id="lease_amount"
																name="lease_amount" type="text" maxlength="7" required
																placeholder="Enter Lease Amount"
																value="${branch.lease_amount}"
																onkeypress="return IsLeaseAmount(event);"
																ondrop="return false;"> <label class="error"
																id="errorLeaseAmount" style="display: none"> </label>
														</div>
													</div>
													<div class="row1" id="grpbranchRent" class="form-group">
														<label class="L-size control-label" for="branchRent">Monthly RENT:<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<input class="form-text" id="branchRent"
																name="monthly_rent" type="text" maxlength="7" 
																placeholder="Enter Monthly Rent"
																value="${branch.monthly_rent}"
																onkeypress="return IsMonthlyRent(event);"
																ondrop="return false;"><span id="branchRentIcon"
																class=""></span>
															<div id="branchRentErrorMsg" class="text-danger"></div> <label class="error"
																id="errorMonthlyRent" style="display: none"> </label>
														</div>
													</div>
													<div class="row1" id="grpbranchRentdate" class="form-group">
														<label class="L-size control-label" for="branchRentdate">Monthly RENT
															Payable DATE:<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<div class="input-group input-append date"
																id="renewal_to">
																<input class="form-text" id="branchRentdate"
																	 name="monthly_rent_date"
																	type="text" value="${branch.monthly_rent_date}"
																	data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="">
																<span class="input-group-addon add-on"> <span
																	class="fa fa-calendar"></span>
																</span>
															</div>
															<span id="branchRentdateIcon" class=""></span>
															<div id="branchRentdateErrorMsg" class="text-danger"></div>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Service TAX
															Number:: </label>
														<div class="I-size">
															<input class="form-text" id="owner1_mobile"
																name="branch_serviceTax_no" type="text" maxlength="30"
																 placeholder="Enter Service GST NO"
																value="${branch.branch_serviceTax_no}"
																onkeypress="return IsServiceTax(event);"
																ondrop="return false;"> <label class="error"
																id="errorServiceTax" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Branch
															Electricity Meter Number: </label>
														<div class="I-size">
															<input class="form-text" id="owner1_mobile"
																name="branch_electricity_no" type="text" maxlength="30"
																 placeholder="Enter Electricity meter No"
																value="${branch.branch_electricity_no}"
																onkeypress="return IsElectricityNO(event);"
																ondrop="return false;"> <label class="error"
																id="errorElectricityNO" style="display: none"> </label>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showBranchOwnership}">		
										<!-- Ownership details -->
										<div id="ownership" class="tab-content">
											<div class="form-horizontal ">
												<fieldset>
													<legend id="Class">Ownership One</legend>
													<div class="row1">
														<label class="L-size control-label" id="Type">Branch
															Ownership Type</label>
														<div class="I-size">
															<select class="form-control select2"
																name="branch_ownership_type" style="width: 100%;">
																<option value="${branch.branch_ownership_type}"
																	selected="selected">${branch.branch_ownership_type}</option>
																<option value="OWNED">OWNED</option>
																<option value="LEASED">LEASED</option>
																<option value="RENTED">RENTED</option>
																<option value="AGENCY">AGENCY</option>
																<option value="TIE UP">TIE UP</option>
															</select>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner One Name
															: </label>
														<div class="I-size">
															<input class="form-text" id="owner1_name"
																name="owner1_name" type="text" maxlength="150"
																placeholder="Enter Owner name"
																value="${branch.owner1_name}"
																onkeypress="return IsOwnerName(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwnerName" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner One PAN
															No : </label>
														<div class="I-size">
															<input class="form-text" id="owner1_pan"
																name="owner1_pan" type="text" maxlength="15"
																placeholder="Enter Owner PAN NO"
																value="${branch.owner1_pan}"
																onkeypress="return IsOwnerPAN(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwnerPAN" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner One
															Address : </label>
														<div class="I-size">
															<input class="form-text" id="owner1_address"
																name="owner1_address" type="text" maxlength="180"
																placeholder="Enter Owner Address "
																value="${branch.owner1_address}"
																onkeypress="return IsOwnerAddress(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwnerAddress" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner One
															Phone No : </label>
														<div class="I-size">
															<input class="form-text" id="owner1_mobile"
																name="owner1_mobile" type="text" maxlength="15"
																placeholder="Enter Owner Mobile"
																value="${branch.owner1_mobile}"
																onkeypress="return IsOwnerMobile(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwnerMobile" style="display: none"> </label>
														</div>
													</div>
												</fieldset>
												<fieldset>
													<legend id="Capacity">Ownership Two</legend>
													<div class="row1">
														<label class="L-size control-label">Owner Two Name
															: </label>
														<div class="I-size">
															<input class="form-text" id="owner1_name"
																name="owner2_name" type="text" maxlength="150"
																placeholder="Enter Owner name"
																value="${branch.owner2_name}"
																onkeypress="return IsOwner2Name(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwner2Name" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner Two PAN
															No : </label>
														<div class="I-size">
															<input class="form-text" id="owner1_pan"
																name="owner2_pan" type="text" maxlength="15"
																placeholder="Enter Owner PAN NO"
																value="${branch.owner2_pan}"
																onkeypress="return IsOwner2PAN(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwner2PAN" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner Two
															Address : </label>
														<div class="I-size">
															<input class="form-text" id="owner1_address"
																name="owner2_address" type="text" maxlength="180"
																placeholder="Enter Owner Address "
																value="${branch.owner2_address}"
																onkeypress="return IsOwner2Address(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwner2Address" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Owner Two
															Phone No : </label>
														<div class="I-size">
															<input class="form-text" id="owner1_mobile"
																name="owner2_mobile" type="text" maxlength="15"
																placeholder="Enter Owner Mobile"
																value="${branch.owner2_mobile}"
																onkeypress="return IsOwner2Mobile(event);"
																ondrop="return false;"> <label class="error"
																id="errorOwner2Mobile" style="display: none"> </label>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
										</c:if>	
										<fieldset class="form-actions">
											<div class="pull-left">
												<button type="submit" class="btn btn-success">Update
													Branch</button>
												<a class="btn btn-link" href="newBranch.in"><span
													id="Cancel">Cancel</span></a>
											</div>

										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabs.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/BR/BranchValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			}), $("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$('.clockpicker').clockpicker();
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>