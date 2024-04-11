<%@ include file="taglib.jsp"%>

<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a> / <span
						id="NewVehi">New Vendor</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Vendor Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Vendor Already Exists
		</div>
	</c:if>
	<c:if test="${param.PanNo eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			 <h5>Pan Card No Already Exists</h5>
		</div>
	</c:if>
	
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_VENDOR')">
				<div class="col-md-offset-1 col-md-9">
					<form id="formVendor" action="<c:url value="/saveVendor.in"/>"
						method="post" enctype="multipart/form-data" name="formVendor"
						role="form" class="form-horizontal">

						<div class="form-horizontal ">
							<fieldset>
								<legend>Vendor Basic Info</legend>
								<div class="box">
									<div class="box-body">
									<input type="hidden" id="showPrimaryEmail" value="${vendorConfig.showPrimaryEmail }">
									<input type="hidden" id="validatePrimaryContactName" value="${vendorConfig.validatePrimaryContactName }">
									<input type="hidden" id="validatePrimaryContactNum" value="${vendorConfig.validatePrimaryContactNum }">
									<input type="hidden" id="validatePrimaryContactEmail" value="${vendorConfig.validatePrimaryContactEmail }">
									<input type="hidden" id="PanCardNoMandatory" value="${vendorConfig.PanCardMandatory }">
									<input type="hidden" id="TDSPercent" value="${vendorConfig.TDSPercent}">
									
										<div class="row1" id="grpvendorName" class="form-group">
											<label class="L-size control-label" for="vendorName">Vendor
												Name Or Number :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="vendorName"
													required="required" maxlength="75" id="vendorName"
													onkeypress="return IsVendorName(event);"
													ondrop="return false;"><span id="vendorNameIcon"
													class=""></span>
												<div id="vendorNameErrorMsg" class="text-danger"></div>
												<label class="error" id="errorVendorName"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grpphoneNumber" class="form-group">
											<label class="L-size control-label" for="phoneNumber">Phone
												Number :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="vendorPhone"
													maxlength="15" id="phoneNumber"
													onkeypress="return IsVendorPhone(event);"
													ondrop="return false;"><span id="phoneNumberIcon"
													class=""></span>
												<div id="phoneNumberErrorMsg" class="text-danger"></div>
												<label class="error" id="errorVendorPhone"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grpvendorType" class="form-group">
											<label class="L-size control-label" for="vendorType">Vendor
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="select2 form-text " name="vendorTypeId" onclick="showHideOwnPetrolPump();" 
													id="vendorType" onchange="showHideOwnPetrolPump();">
													<c:forEach items="${vendorType}" var="vendorType">
														<option value="${vendorType.vendor_Typeid}">${vendorType.vendor_TypeName}</option>
													</c:forEach>
												</select> <span id="vendorTypeIcon" class=""></span>
												<div id="vendorTypeErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<c:if test="${vendorConfig.allowOwnPetrolPump}">
											<div class="row1" id="ownPetrolPumpId" class="form-group">
												<label class="L-size control-label" for="vendorType">IS OWN
													Petrol Pump :
												</label>
												<div class="I-size">
													<select class="select2 form-text " name="ownPetrolPump"
														id="ownPetrolPump">
														<option value="0">NO</option>
														<option value="1">YES</option>
													</select> 
												</div>
											</div>
										</c:if>
										<div class="row1" id="grpvendorLocation" class="form-group">
											<label class="L-size control-label" for="vendorLocation">Vendor
												Location :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="vendorLocation"
													maxlength="150" id="vendorLocation"
													onkeypress="return IsVendorLocation(event);"
													ondrop="return false;"><span
													id="vendorLocationIcon" class=""></span>
												<div id="vendorLocationErrorMsg" class="text-danger"></div>
												<label class="error" id="errorVendorLocation"
													style="display: none"> </label>
											</div>
										</div>
										<c:if test="${vendorConfig.showWebsite }">
											<div class="row">
												<label class="L-size control-label">Website :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="vendorWebsite" type="text" maxlength="150"
														value="http://" onkeypress="return IsVendorWesite(event);"
														ondrop="return false;"> <label class="error"
														id="errorVendorWesite" style="display: none"> </label>
													<p class="help-block">Web page URL starting with
														"http://"</p>
												</div>
											</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Address :</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorAddress1" maxlength="150" type="text"
													onkeypress="return IsVendorAddress(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorAddress" style="display: none"> </label>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label">Address Line 2 :</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorAddress2" maxlength="150" type="text"
													onkeypress="return IsVendorAddress2(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorAddress2" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Country :</label>
											<div class="I-size">
												<select name="vendorCountry"
													class="select2 countries form-text" style="width: 100%"
													id="countryId">
													<option value="">Select Country</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">State/Province/Region
												:</label>
											<div class="I-size">
												<select name="vendorState" class="select2 states form-text"
													style="width: 100%" id="stateId">
													<option value="">Select State</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">City :</label>
											<div class="I-size">
												<select name="vendorCity" class="select2 cities form-text"
													style="width: 100%" id="cityId">
													<option value="">Select City</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Zip/Pin Code :</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorPincode" type="text" maxlength="6"
													onkeypress="return IsVendorPin(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorPin" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<input class="text optional form-text" name="vendorRemarks"
													type="text" maxlength="200"
													onkeypress="return IsVendorRemark(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorRemark" style="display: none"> </label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend> Payment Details </legend>
								<div class="box">
									<div class="box-body">
										<c:if test="${vendorConfig.showPaymentTerm }">
											<div class="row1">
												<label class="L-size control-label">Term :</label>
												<div class="I-size">
													<select class="form-text" name="vendorTermId" id="select3"
														required="required">
														<option value="1">CASH</option>
														<option value="2">CREDIT</option>
														<option value="3">NEFT</option>
														<option value="4">RTGS</option>
														<option value="5">IMPS</option>
														<option value="6">DD</option>
														<option value="7">CHEQUE</option>
													</select>
												</div>
											</div>
										</c:if>
										
										<div class="row1" id="VendorPanNo" class="form-group">
											<label class="L-size control-label">PAN Card NO :  
												<c:if test="${vendorConfig.PanCardMandatory}">
														<abbr title="required">*</abbr>
												</c:if>
											</label>
										    <div class="I-size">
												<input class="form-text" id="VenPanNo" name="vendorPanNO" placeholder=""
													type="text" maxlength="50"
													onkeypress="return IsVendorPanNO(event);"
													ondrop="return false;"> <span id="vendorPanNoIcon" class=""></span>
													<div id="vendorPanNoErrorMsg" class="text-danger"></div>
													<label class="error"
													id="errorVendorPanNO" style="display: none"> </label>

											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">GST NO :</label>
											<div class="I-size">
												<input class="form-text" name="vendorGSTNO" placeholder=""
													type="text" maxlength="50"
													onkeypress="return IsVendorTaxNo(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorTaxNo" style="display: none"> </label>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label">GST Registered :</label>
											<div class="I-size">
												<select class="form-text" name="vendorGSTRegisteredId"
													id="select3" required="required">
													<option value="1">Turnover
														Below 25 lakhs</option>
													<option value="2">Turnover
														Above 25 lakhs</option>

												</select> <label class="error" id="errorVendorVatNo"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Credit Limit :</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorCreditLimit" type="text" maxlength="50"
													onkeypress="return IsVendorCreditLimit(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorCreditLimit" style="display: none">
												</label>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label">Advance Paid :</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorAdvancePaid" maxlength="50" type="text"
													onkeypress="return IsVendorAdvancePaid(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorAdvancePaid" style="display: none">
												</label>
											</div>
										</div>
										
										<c:if test="${vendorConfig.TDSPercent}">
											<div class="row1" id="VendorTDSAmount">
												<label class="L-size control-label">TDS % :</label>
												<div class="I-size">
													<input class=" required form-text" step="any"
														name="vendorTDSPercent" maxlength="50" type="number" id="vendorTDS"
														onkeypress="return IsVendorTDS(event);"
														ondrop="return false;"> 
													<span id="vendorTDSIcon" class=""></span>
													<div id="vendorTDSErrorMsg" class="text-danger"></div>
														
													<label class="error" id="errorVendorTDS" style="display: none"></label>
												</div>
											</div>
									   </c:if>
										
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>
									Contact <a class=" btn btn-default"
										onclick="visibility('contactTwo');"> <i class="fa fa-plus"></i>
									</a>
								</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpcontactName" class="form-group">
											<label class="L-size control-label" for="contactName">Primary
												Contact Name :<c:if test="${vendorConfig.validatePrimaryContactName}">
												<abbr title="required">*</abbr>
												</c:if>
											</label>
											<div class="I-size">
												<input class="form-text" name="vendorFirName" placeholder=""
													type="text" maxlength="50" id="contactName"
													onkeypress="return IsVendorConName(event);"
													ondrop="return false;"> <span id="contactNameIcon"
													class=""></span>
												<div id="contactNameErrorMsg" class="text-danger"></div>
												<label class="error" id="errorVendorConName"
													style="display: none"> </label>

											</div>
										</div>

										<div class="row1" id="grpcontactPhone" class="form-group">
											<label class="L-size control-label" for="contactPhone">Primary
												Phone Number :<c:if test="${vendorConfig.validatePrimaryContactNumber}">
												<abbr title="required">*</abbr>
												</c:if>
											</label>
											<div class="I-size">
												<input class="string required form-text" id="contactPhone"
													name="vendorFirPhone" type="text" maxlength="15"
													onkeypress="return IsVendorConPhone(event);"
													ondrop="return false;"><span id="contactPhoneIcon"
													class=""></span>
												<div id="contactPhoneErrorMsg" class="text-danger"></div>
												<label class="error" id="errorVendorConPhone"
													style="display: none"> </label>

											</div>
										</div>
										
								<c:if test="${vendorConfig.showPrimaryEmail }">
										<div class="row1" id="grpcontactEmail" class="form-group">
											<label class="L-size control-label" for="contactEmail">Primary
												Email : 
												<c:if test="${vendorConfig.validatePrimaryContactEmail}">
												<abbr title="required">*</abbr>
												</c:if>
											</label>
											<div class="I-size">
												<input class="string required form-text" id="contactEmail"
													name="vendorFirEmail" maxlength="50" type="email"
													
													
													ondrop="return false;"><span id="contactEmailIcon"
													class=""></span>
												<div id="contactEmailErrorMsg" class="text-danger"></div>
												<label class="error" id="errorVendorConEmail"
													style="display: none"> </label>
											</div>
										</div>
								</c:if>		
										<div id="contactTwo" class="contact_Hide">
											<hr>
											<div class="row1">
												<label class="L-size control-label">Secondary
													Contact Name:</label>
												<div class="I-size">
													<input class="form-text" name="vendorSecName"
														placeholder="" type="text" maxlength="50"
														onkeypress="return IsVendorConName2(event);"
														ondrop="return false;"> <label class="error"
														id="errorVendorConName2" style="display: none"> </label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Secondary Phone
													Number :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="vendorSecPhone" type="text" maxlength="15"
														onkeypress="return IsVendorConPhone2(event);"
														ondrop="return false;"> <label class="error"
														id="errorVendorConPhone2" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Secondary Email
													:</label>
												<div class="I-size">
													<input class="string required form-text"
														name="vendorSecEmail" type="email" maxlength="50"
														onkeypress="return IsVendorConEmail2(event);"
														ondrop="return false;"> <label class="error"
														id="errorVendorConEmail2" style="display: none"> </label>
												</div>
												<a class=" btn btn-default col-sm-offset-1"
													onclick="visibility('contactTwo');"> <i
													class="fa fa-minus"></i>
												</a>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend> Bank Details </legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Bank Name</label>
											<div class="I-size">
												<input class="form-text" name="vendorBankName"
													placeholder="" type="text" maxlength="50"
													onkeypress="return IsVendorBankName(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorBankName" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Bank branch Name</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorBankBranch" type="text" maxlength="50"
													onkeypress="return IsVendorBankBranch(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorBankBranch" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Account Number</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorBankAccno" type="text" maxlength="25"
													onkeypress="return IsVendorBankAC(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorBankAC" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">IFSC Code</label>
											<div class="I-size">
												<input class="string required form-text"
													name="vendorBankIfsc" type="text" maxlength="25"
													onkeypress="return IsVendorBankIF(event);"
													ondrop="return false;"> <label class="error"
													id="errorVendorBankIF" style="display: none"> </label>

											</div>
										</div>

									</div>
								</div>
							</fieldset>

							<fieldset class="form-actions">
								<div class="row1">
									<div class="col-md-10">
										<button type="submit" class="btn btn-success">Create
											Vendor</button>
										<a class="btn btn-info" href="<c:url value="/vendorHome.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					</form>

				</div>
			</sec:authorize>

		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
			
		});
	</script>

	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>