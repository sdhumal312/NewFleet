<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	
<style>
	.states, .cities, .countries { 
	    border: solid 1px #517B97; 
	    outline: 0; 
	    box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
	    -moz-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
	    -webkit-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
    }
   .control-label{
   		font-weight: initial;
   } 
   .left-margin{
   	margin-left: -9%;
   }
   
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / 
						<span> New Driver</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/getDriversList"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="row">
			<sec:authorize access="hasAuthority('ADD_DRIVER')">
				<div class="col-sm-2 col-md-2 ">
					<div class="box fixed">
						<div class="box-body">
							<ul class="nav nav-pills nav-stacked" id="myTabs">
								<li class="active">
									<a href="#driverPersonal" data-toggle="pill">Basic Information
										<abbr title="required">*</abbr>
									</a>
								</li>
								<li><a href="#driverGroup" data-toggle="pill">Group Information </a></li>
								<li><a href="#contactInfo" data-toggle="pill">Contact Information </a></li>
								<li><a href="#EmpInfo" data-toggle="pill">Employment Information </a></li>
								<li><a href="#DlInfo" data-toggle="pill">Driving License Information (DL) </a></li>
								<li><a href="#BankInfo" data-toggle="pill">Bank Information </a></li>
								<c:if test="${configuration.showReferenceData}">
									<li><a href="#RefInfo" data-toggle="pill">Reference Information</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
				<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
				<div class="col-sm-8 col-md-9">
					<div class="tab-content">
						<div class="tab-pane active" id="driverPersonal">
							<div class="box">
								<div class="box-body">
									<input type="hidden" id="showOriginalDLRemark" value="${configuration.showOriginalDLRemark}" />
									<input type="hidden" id="validateDateOfBirth" value="${configuration.validateDateOfBirth}" />
									<input type="hidden" id="showDateOfBirth" value="${configuration.showDateOfBirth}" />
									<input type="hidden" id="validateGroup" value="${configuration.showGroup}" />
									<input type="hidden" id="validateVehicle" value="${configuration.validateVehicle}" />
									<input type="hidden" id="validateSalaryType" value="${configuration.showSalaryType}" />
									<input type="hidden" id="validatePerDaySalary" value="${configuration.showPerDaySalary}" />
									<input type="hidden" id="validateEmployeeNumber" value="${configuration.validateEmployeeNumber}" />
									<input type="hidden" id="validateInsuranceNumber" value="${configuration.validateInsuranceNumber}" />
									<input type="hidden" id="validateStartDate" value="${configuration.validateStartDate}" />
									<input type="hidden" id="validateMobileNo" value="${configuration.validateMobileNo}" />
									<input type="hidden" id="validateJobTitle" value="${configuration.validateJobTitle}" />
									<input type="hidden" id="validateDLNumber" value="${configuration.validateDLNumber}" />
									<input type="hidden" id="validateRenewalType" value="${configuration.validateRenwalType}">
									<input type="hidden" id="showRenewalValidity" value="${configuration.validateRenewalPeriod}">
									<input type="hidden" id="validateDueThreshold" value="${configuration.validateRenewalThreshold}">
									<input type="hidden" id="validateRenewalDoc" value="${configuration.validateRenwalDoc}">
									<input type="hidden" id="allowDLNumber" value="${configuration.allowDLNumber}">
									<input type="hidden" id="validateAadhar" value="${configuration.validateAadhar}">
									<input type="hidden" id="jobTypeWiseAutoGenerateEmpNo" value="${configuration.jobTypeWiseAutoGenerateEmpNo}">
									<input type="hidden" id="autoGenerateEmpNo" value="${configuration.autoGenerateEmpNo}">
									
									
									<div class="row">
										<div class="form-group">
											<label class="L-size control-label" for="driverfirstname">First Name :
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="driverFirstName"
													onkeypress="return IsAlphaNumericFristName(event);"
													ondrop="return false;" maxlength="25"> 
													
											</div>
										</div>
									</div>	<br/>
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">Last Name :</label>
											<div class="I-size">
												<input type="text" class="form-text" id="driverLastName"
													onkeypress="return IsAlphaNumericLastName(event);"
													ondrop="return false;" maxlength="25"> 
											</div>
										</div>
									</div><br/>
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">Father Name :</label>
											<div class="I-size">
												<input type="text" class="form-text" id="driverFatherName"
													onkeypress="return IsAlphaNumericFatherName(event);"
													ondrop="return false;" maxlength="25"> 
											</div>
										</div>
									</div><br/>
									
									<div class="row" >
										<div class=" col-sm-8 col-md-1 " style="margin-left: 10%;">DOB :
											<c:if test="${configuration.validateDateOfBirth}">
												<abbr title="required">*</abbr>
											</c:if>                                                                                      
										</div>
								       <div class=" col-sm-8 col-md-3 ">
											<div class="I-size">
												<div class="input-group input-append date" id="dateofbirth">
													<input type="text" class="form-text left-margin" id="driverDateOfBirth"  readonly="readonly"
														ondrop="return false;"	data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
													<span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span> 
													<span id="birthDayIcon" class=""></span>
												</div>
											</div>
										</div>
								        <div class=" col-sm-8 col-md-1 ">Mobile NO:
											<c:if test="${configuration.validateMobileNo}">
												<abbr title="required">*</abbr>
											</c:if>                   
										</div>
								        <div class=" col-sm-8 col-md-3 ">
								        	<div class="I-size">
												<input type="text" class="form-text" id="mobileNo"
													onkeypress="return isNumberKey(event);"
													onblur="return isMobileNum(this);"
													ondrop="return false;" maxlength="10"> 
											</div>
								        </div>
									</div> <br/>
									
									<div class="row">
										<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">Job Title :
											<c:if test="${configuration.validateJobTitle}">
												<abbr title="required">*</abbr>
											</c:if>  
										</div>
									    <div class=" col-sm-8 col-md-3">
									    	<div class="col-lg-9">
												<select class="form-text left-margin" id="driJobId" onchange="dlNumberValidation();" >
													<c:forEach items="${driverJobType}" var="driverJobType">
														<option value="${driverJobType.driJobId}-${driverJobType.driJobType}">
														<c:out value="${driverJobType.driJobType}" />
													</c:forEach>
												</select> 
											</div>
									    </div>
									    <div class=" col-sm-8 col-md-1">DL NO :
									    	<c:if test="${configuration.validateDLNumber}">
												<abbr id="dlAsterix" class="" title="required">*</abbr>
											</c:if>   
									    </div>
									   	<div class=" col-sm-8 col-md-3">
									   		<div class="I-size">
												<input type="text" class="form-text" id="dlNumber"
													onkeypress="return IsAlphaNumericDl(event);"
													ondrop="return false;" maxlength="25"> 
											</div>
									   	</div>
									</div><br/>
									
									<div class="row">
										<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">EMP No :
											<c:if test="${configuration.validateEmployeeNumber}">
												<abbr title="required">*</abbr>
											</c:if>
										</div>
										<div class=" col-sm-8 col-md-3" >
											<div class="I-size">
												<input type="text" class="form-text left-margin" id="empNumber"
													onkeypress="return IsAlphaNumericEmp(event);"
													ondrop="return false;" maxlength="25">
											</div>
										</div>
										<c:if test="${configuration.showAadharNumber}">
											<div class=" col-sm-8 col-md-1" >Aadhar No
												 <c:if test="${configuration.validateAadhar}">
													<abbr title="required">*</abbr>
												</c:if> 
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="I-size">
													<input type="text" class="form-text" id="aadharNumber"
														pattern=".{6,50}" title="Six or more Aadhar number"
														onkeypress="return isNumberKey(event);"
														ondrop="return false;" maxlength="12">
												</div>
											</div>
										</c:if>
									</div><br/>
									<div class="row">
										<c:if test="${configuration.showPanNo}">
											<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">PAN No :
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="I-size">
													<input type="text" class="form-text left-margin" id="panNumber" pattern=".{6,50}"
														title="Six or more PAN number" onkeypress="return IsAlphaNumericPAN(event);"
														ondrop="return false;" maxlength="50">
												</div>	
											</div>
										</c:if>
										<c:if test="${configuration.showStartDate}">
											<div class=" col-sm-8 col-md-1"
											<c:if test="${!configuration.showPanNo}">
											style="margin-left: 10%;"
											</c:if>	 >Joining Date:
												<c:if test="${configuration.validateStartDate}">
													<abbr title="required">*</abbr>
												</c:if> 
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" id="joinDate" maxlength="10" readonly="readonly"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
													<span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="joinDateIcon" class=""></span>
												<div id="joinDateErrorMsg" class="text-danger"></div>
											</div>
											</div>
										</c:if>	
									</div>	<br/>	
									
									<!-- 	DRIVER RENEWAL REMINDER START -->
									<div class="row">
										<c:if test="${configuration.showRenewalType}">
											<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">Renewal Type :
												<c:if test="${configuration.validateRenwalType}">
													<abbr title="required">*</abbr>
												</c:if>  
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="col-lg-9">
													<select class="form-text left-margin"  id="driverRenewalTypeId" >
														<option value="${driverDocType.dri_id}">
														<c:out value="${driverDocType.dri_DocType}" /></option>
													</select>
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showRenewalValidity}">	
											<div class=" col-sm-8 col-md-1" >Validity From &amp; To 
												<c:if test="${configuration.validateRenewalPeriod}">
													<abbr title="required">*</abbr>
												</c:if>     
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="I-size">
													<div class="input-group input-append date">
														<input type="text" class="form-text" id="DriverReminderdate"  maxlength="25" readonly="readonly"> 
															<span class="input-group-addon add-on">
																<span class="fa fa-calendar"></span>
															</span>
													</div>
												</div>
											</div>
										</c:if>		
									</div>	
									<br>
									
									<div class="row">
										<c:if test="${configuration.showDueThreshold}">
											<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">Due Threshold :
												<c:if test="${configuration.validateRenewalThreshold}">
													<abbr title="required">*</abbr>
												</c:if> 
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="col-lg-2 left-margin" >
													<input type="text" class="form-text " id="driverDueThreshold"
														name="driver_timethreshold" min="1" max="6"
														maxlength="1" value="1" required="required"
														onkeypress="return IsNumericTimeThru(event);"
														ondrop="return false;"> <label class="error"
														id="errorTimeThru" style="display: none"> </label>
												</div>
												<div class="col-lg-6">
													<select class="form-text" id="driverDueThresholdPeriod">
														<option value="0">day(s)</option>
														<option value="7">Week(s)</option>
														<option value="28">Month(s)</option>
													</select>
												</div>
											</div>	
										</c:if>
										<c:if test="${configuration.showRenewalDoc}">	
											<div class=" col-sm-8 col-md-1" >Driver Renewal Document: 
											<abbr title="required">*</abbr>
											</div>
											
											<div class=" col-sm-8 col-md-3" >
												<div class="I-size">
													<input type="file" id="renewalDocId"  name="input-file-preview" />
												</div>
											</div>
										</c:if>	
										<c:if test="${!configuration.showRenewalDoc}">
											<input class="hide" type="file" value="" name="input-file-preview" />
										</c:if>
									</div><br/>
								<!-- 	DRIVER RENEWAL REMINDER END -->
									<div class="row">
										<c:if test="${configuration.showVehicle}">	
											<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">Vehicle :
												<c:if test="${configuration.validateVehicle}">
													<abbr title="required">*</abbr>
												</c:if>  
											</div>
											<div class=" col-sm-8 col-md-3" >
												<div class="col-lg-9">
													<input type="hidden" class="left-margin" id="vid" name="vid" style="width: 100%;" 
													placeholder="Please Enter Vehicle" />
											</div>
											</div>
										</c:if>
									</div>
									<br/>
									<c:if test="${configuration.showSalariedEmp}">
									<div class="row">
											<div class=" col-sm-8 col-md-1" style="margin-left: 10%;">Is Salaried :</div>
												<div class="btn-group" id="status" data-toggle="buttons">
															<label id="debitlebel" class="btn btn-default btn-on btn-lg active">
																<input type="radio" value="1" name="salariedId"
																id="salariedNo">No
															</label> <label id="creditlebel" class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="2" name="salariedId" id="salariedYes">Yes
															</label>
												</div>
									</div>
									</c:if>
								</div>
							</div>
							<div class="pull-right">
								<a href="#driverGroup" data-toggle="pill"
									class="btn btn-success">Next </a>
							</div>
						</div>
						<div class="tab-pane" id="driverGroup">
							<div class="box">
								<div class="box-body">
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">Group Service :
												<c:if test="${configuration.validateGroup}">
													<abbr title="required">*</abbr>
												</c:if>   
											</label>
											
											<div class="I-size">
												<select class="form-text" id="vGroup">
													<c:forEach items="${vehiclegroup}" var="vehiclegroup">
														<option value="${vehiclegroup.gid}">
																<c:out value="${vehiclegroup.vGroup}" />
														</option>
													</c:forEach>
												</select>
												<!-- <input type="hidden" name = "driver_group" id= "driver_group"/> -->
											</div>
										</div>
									</div><br/>
									<div class="row">
										<div class="form-group">
											<div class=" col-sm-9 col-md-6  col-xs-10 col-md-offset-1">
												<label class="L-size control-label">Driver Status: </label>
													<div class="I-size">
														<select class="form-text" id="driverStatusId">
															<option value="1" >Active</option>
															<option value="2" >Inactive</option>
															<option value="6" >Suspend</option>
															<option value="7" >Hold</option>
															<option value="8" >Resign</option>
														</select>
													</div>
												
												<!-- <div class="btn-group" id="status" data-toggle="buttons">
													<label class="btn btn-default btn-on btn-lg active" style="margin-left: 8px;"     >
														<input type="radio" value="1" id="activeStatusId" name="driverStatusId" checked="checked"  onclick="showSelectedStatus();" >Active
													</label> 
													<label class="btn btn-default btn-off btn-lg"> 
														<input type="radio" value="2" id="inactiveStatusId" name="driverStatusId"  onclick="showSelectedStatus();" >InActive
													</label>
												</div> -->
											</div>
										</div>
									</div>
									<br>
									<div class="row">
										<div class="form-group">
											<label class="L-size control-label">Salary Type :
												 <c:if test="${configuration.validateSalaryType}">
													<abbr title="required">*</abbr>
												</c:if>           
											</label>
											<c:choose>
												<c:when test="${flavorWiseDriverSalary}">
													<div class="I-size">
														<select class="form-text" id="driverSalaryTypeId">
															<option value="1">PER DAY WISE SALARY</option>
															<option value="2">TRIP WISE SALARY</option>
															<option value="3">KM WISE SALARY</option>
														</select>
													</div>
												</c:when>
												<c:otherwise>
													<div class="I-size">
													<select class="form-text" id="driverSalaryTypeId">
														<option value="1">PER DAY WISE SALARY</option>
													</select>
												</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div><br/>
									<div class="row">
										<div class="form-group">
											<label class="L-size control-label">Per day Salary:
												 <c:if test="${configuration.validatePerDaySalary}">
													<abbr title="required">*</abbr>
												</c:if>           
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="driverPerDaySalary"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;"  maxlength="5">
											</div>
										</div>
									</div>
									<br/>
									<c:if test="${configuration.showESINumber}">
										<div class="row">
											<div class="form-group">
												<label class="L-size control-label">ESI Percentage :</label>
												<div class="I-size">
													<div class="input-group input-append">
														<input type="text" class="form-text" id="driverESIAmount"
															onkeypress="return isNumberKeyWithDecimal(event,this.id);"
															ondrop="return false;" maxlength="5"> 
														<span class="input-group-addon add-on">
															<span class="fa fa-percent"></span>
														</span>
													</div>
												</div>
											</div>
										</div><br/>
									</c:if>
									<c:if test="${configuration.showPFNumber}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label" for="joinDate">PF Percentage : </label>
												<div class="I-size">
													<div class="input-group input-append">
														<input type="text" class="form-text" id="driverPFAmount"
															onkeypress="return isNumberKeyWithDecimal(event,this.id);"
															ondrop="return false;" maxlength="5"> 
															<span class="input-group-addon add-on">
																<span class="fa fa-percent"></span>
															</span> 
													</div>
												</div>
											</div>
										</div><br/>
									</c:if>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#driverPersonal" data-toggle="pill" class="btn btn-info">Prev </a>
								</div>
								<div class="pull-right">
									<a href="#contactInfo" data-toggle="pill" class="btn btn-success">Next </a>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="contactInfo">
							<div class="box">
								<div class="box-body">
									<div class="row">
										<c:if test="${configuration.showDriverEmail}">
											<div class="form-group">
												<label class="L-size string required control-label">Email Id :</label>
												<div class="I-size">
													<input type="email" class="form-text" id="driverEmail"
														onkeypress="return IsAlphaNumericEmail(event);"
														ondrop="return false;" maxlength="50"> 
												</div>
											</div>
										</c:if>
									</div>	
									<div>
										<c:if test="${configuration.showHomePhoneNumber}">
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">Home Phone Number :</label>
													<div class="I-size">
														<input type="text" class="form-text" id="driverHomePhoneNo"
															onkeypress="return IsNumericHome(event);"
															ondrop="return false;" maxlength="15">
													</div>
												</div>
											</div>
										</c:if>
									</div>	
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">Address :</label>
											<div class="I-size">
												<input type="text" class="form-text" id="driverAddress"
													onkeypress="return IsAlphaNumericAddress(event);"
													ondrop="return false;" maxlength="100">

											</div>
										</div>
									</div>
									<div>
										<c:if test="${configuration.showAddress2}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Address Line 2 :</label>
													<div class="I-size">
														<input type="text" class="form-text" id="driverAddress2"
															onkeypress="return IsAlphaNumericAddress2(event);"
															ondrop="return false;" maxlength="100">
													</div>
												</div>
											</div>
										</c:if>
									</div>
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">Country :
												<c:if test="${configuration.showCountry}">
													<abbr title="required">*</abbr>
												</c:if>  
											</label>	   
											<div class="I-size">
												<select id="countryId" class="countries form-text" style="width: 100%" >
													<option value="">Select Country</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">State :
												<c:if test="${configuration.showState}">
													<abbr title="required">*</abbr>
												</c:if>  
											</label>
											<div class="I-size">
												<select class="states form-text" style="width: 100%" id="stateId">
													<option value="">Select State</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">City :
												<c:if test="${configuration.showCity}">
													<abbr title="required">*</abbr>
												</c:if>  
											</label>
											<div class="I-size">
												<select class="cities form-text" style="width: 100%" id="cityId">
													<option value="">Select City</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="form-group">
											<label class="L-size string required control-label">Postal Code :</label>
											<div class="I-size">
												<input type="text" class="zip form-text" id="pinCode"
													onkeypress="return isNumberKey(event);"
													ondrop="return false;" maxlength="6">
											</div>
										</div>
									</div>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#driverGroup" data-toggle="pill" class="btn btn-info">Prev </a>
								</div>
								<div class="pull-right">
									<a href="#EmpInfo" data-toggle="pill" class="btn btn-success">Next </a>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="EmpInfo">
							<div class="box">
								<div class="box-body">
									<c:if test="${configuration.showQualification}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Qualification: </label>
												<div class="I-size">
													<input type="text" class="form-text" id="driverQualification"
														onkeypress="return IsAlphaNumericQualification(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showBloodGroup}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Blood Group :</label>
												<div class="I-size">
													<select class="form-text" id="driverBloodGroup">
														<option value=""></option>
														<option value="O+">O +</option>
														<option value="O-">O -</option>
														<option value="A+">A +</option>
														<option value="A-">A -</option>
														<option value="B+">B +</option>
														<option value="B-">B -</option>
														<option value="AB+">AB +</option>
														<option value="AB-">AB -</option>
													</select>
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showLanguage}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Languages:
												</label>
												<div class="I-size">
													<select class="select2" id="driverLanguage"
														multiple="multiple" data-placeholder="Select a languages"
														style="width: 100%;">
														<option>English</option>
														<option>Hindi</option>
														<option>Tamil</option>
														<option>Kannada</option>
														<option>Telugu</option>
														<option>Marathi</option>
														<option>Malayalam</option>
														<option>Odia</option>
														<option>Gujarati</option>
														<option>Punjabi</option>
													</select>
												</div>
											</div>
										</div>
									</c:if>
									<br>
									<c:if test="${configuration.showInsuranceNumber}">	
										<div class="row">
											<div class="form-group">
												<label class="L-size control-label">Insurance Number :
													<c:if test="${configuration.validateInsuranceNumber}">
														<abbr title="required">*</abbr>
													</c:if>     
												</label>
												<div class="I-size">
													<input type="text" class="form-text" id="insuranceNo"
														onkeypress="return IsInsuranceNumber(event);"
														ondrop="return false;" maxlength="25"> 
												</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${configuration.showESINumber}">
										<div class="row">
											<div class="form-group">
												<label class="L-size control-label">ESI Number :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="ESINumber"
														onkeypress="return IsESINumber(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showPFNumber}">
										<div class="row">
											<div class="form-group">
												<label class="L-size control-label">PF Number :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="PFNumber"
														onkeypress="return IsPFNumber(event);"
														ondrop="return false;" maxlength="25"> 
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showOtherTrainning}">
										<div class="row">
											<div class="form-group">
												<label class="L-size control-label">Other Training :</label>
												<div class="I-size">
													<select class="select2" id="driverTraining" multiple="multiple"
														data-placeholder="Select a Certificate Max 3" style="width: 100%;">
														<c:forEach items="${driverTrainingType}" var="driverTrainingType">
															<option value="${driverTrainingType.dri_TrainingType}">
																<c:out value="${driverTrainingType.dri_TrainingType}" />
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</c:if>
									<br>
									
									<c:if test="${configuration.showLeaveDate}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Leave Date :</label>
												<div class="I-size">
													<div class="input-group input-append date" id="LeaveDate">
														<input type="text" class="form-text" id="leaveDate" readonly="readonly"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" maxlength="10" /> 
														<span class="input-group-addon add-on">
															<span class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
										</div>
									</c:if>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#contactInfo" data-toggle="pill" class="btn btn-info">Prev </a>
								</div>
								<div class="pull-right">
									<a href="#DlInfo" data-toggle="pill" class="btn btn-success">Next </a>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="DlInfo">
							<div class="box">
								<div class="box-body">
									<c:if test="${configuration.showBadgeNo}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Badge Number :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="badgeNumber"
														onkeypress="return IsAlphaNumericBadge(event);"
														ondrop="return false;" maxlength="25"> 
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showLicenseClass}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">License Class :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="DL_Class"
														onkeypress="return IsAlphaNumericClass(event);"
														ondrop="return false;" maxlength="25"> 
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showLicenseState}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">License State :</label>
												<div class="I-size">
													<select class="dlState" style="width: 100%" id="DL_State">
												</select>	
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showAuthorizedToDrive}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Authorized to Drive :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="driverAuthorized"
														onkeypress="return IsAlphaNumericAuthorise(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showOriginalDLRemark}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Original DL Remarks :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="DL_Original"
														onkeypress="return IsAlphaNumericOrinalRemarls(event);"
														ondrop="return false;" maxlength="150">
												</div>
											</div>
										</div>
									</c:if>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#EmpInfo" data-toggle="pill" class="btn btn-info">Prev </a>
								</div>
								<div class="pull-right">
									<a href="#BankInfo" data-toggle="pill" class="btn btn-success">Next </a>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="BankInfo">
							<div class="box">
								<div class="box-body">
									<c:if test="${configuration.showBankAccount}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label" for="bank_ac">Bank Account Number : </label>
												<div class="I-size">
													<input type="text" class="form-text" id="bankACNumber" pattern=".{6,25}"
														title="Six or more A/C number" onkeypress="return IsAlphaNumericAC(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showIFSCCode}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label" for="bank_ifsc">Bank IFSC Number :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="bankIFCSNumber" pattern=".{6,25}"
														title="Six or more IFSC number" onkeypress="return IsAlphaNumericIFSC(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${configuration.showBankName}">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label" for="bankname">Bank Name :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="bankName" pattern=".{3,250}"
														title="3 or more characters" onkeypress="return IsAlphaNumericBN(event);"
														ondrop="return false;" maxlength="250">
												</div>
											</div>
										</div>
									</c:if>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#DlInfo" data-toggle="pill" class="btn btn-info">Prev </a>
								</div>
								<c:if test="${configuration.showReferenceData}">
									<div class="pull-right">
										<a href="#RefInfo" data-toggle="pill" class="btn btn-success">Next </a>
									</div>
								</c:if>
							</div>
						</div>
						<c:if test="${configuration.showReferenceData}"> 
							<div class="tab-pane" id="RefInfo">
								<div class="box">
									<div class="box-body">
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Ref_Frist Name :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="referenceFirstName"
														onkeypress="return IsAlphaNumericRFrist(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Ref_Last Name :</label>
												<div class="I-size">
													<input type="text" class="form-text" id="referenceLastName"
														onkeypress="return IsAlphaNumericRLast(event);"
														ondrop="return false;" maxlength="25">
												</div>
											</div>
										</div>
										<div class="row">
											<div class="form-group">
												<label class="L-size string required control-label">Contact Number</label>
												<div class="I-size">
													<input type="text" class="form-text" id="referenceContactNo"
														onblur="return isMobileNum(this);"
														ondrop="return false;" maxlength="10">
												</div>
											</div>
											
										</div>
									</div>
									<input type="hidden" class="form-text" id="driverPhotoId" value="1">
								</div>
								<div>
									<div class="pull-left">
										<a href="#BankInfo" data-toggle="pill" class="btn btn-info">Prev </a>
									</div>
								</div>
							</div>
						</c:if>
						<div class="box-footer h-padded">
							<fieldset class="form-actions">
								<input type="button" value="Submit" id="btnSubmit" class="btn btn-success"/>	
								<a class="btn btn-link" href="<c:url value="/getDriversList"/>">Cancel</a>
							</fieldset>
						</div>
					</div>
				</div>	
				</form>
			</sec:authorize>
		</div>
	</section>
	<div class="modal fade" id="addRemarkModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Add Remark</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type">Remark :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="remark"
									maxlength="249" name="description"
									placeholder="Enter description" />
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" id="saveRemark" onclick="saveRemark();" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/driverDetailsValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/AddDriverDetails.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>