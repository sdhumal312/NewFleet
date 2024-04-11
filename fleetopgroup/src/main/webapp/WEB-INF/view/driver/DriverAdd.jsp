<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	
<style>
		.states, .cities, .countries { 
    padding: 9px; 
    border: solid 1px #517B97; 
    outline: 0; 
    background: -webkit-gradient(linear, left top, left 25, from(#FFFFFF), color-stop(4%, #CAD9E3), to(#FFFFFF)); 
    background: -moz-linear-gradient(top, #FFFFFF, #CAD9E3 1px, #FFFFFF 25px); 
    box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
    -moz-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
    -webkit-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 

    }
	</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <span>
						New Driver</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/getDriversList"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="row">

			<div class="row">
				<sec:authorize access="hasAuthority('ADD_DRIVER')">

					<div class="col-sm-2 col-md-2 ">
						<div class="box fixed">
							<div class="box-body">
								<ul class="nav nav-pills nav-stacked" id="myTabs">
									<li class="active"><a href="#driverPersonal"
										data-toggle="pill">Personal Information <abbr
											title="required">*</abbr></a></li>
									<li><a href="#driverGroup" data-toggle="pill">Group
											Information <abbr title="required">*</abbr>
									</a></li>
									<li><a href="#contactInfo" data-toggle="pill">Contact
											Information <abbr title="required">*</abbr>
									</a></li>
									<li><a href="#EmpInfo" data-toggle="pill">Employment
											Information <abbr title="required">*</abbr>
									</a></li>
									<li><a href="#DlInfo" data-toggle="pill">Driving
											License Information (DL) <abbr title="required">*</abbr>
									</a></li>
									<li><a href="#BankInfo" data-toggle="pill">Bank
											Information <abbr title="required">*</abbr>
									</a></li>
									<c:if test="${configuration.showReferenceData}">
									<li><a href="#RefInfo" data-toggle="pill">Reference
											Information</a></li>
											</c:if>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-sm-8 col-md-9">
						<form id="formDriver" action="<c:url value="/saveDriver.in"/>"
							method="post" name="formDriver" role="form" enctype="multipart/form-data"
							class="form-horizontal" onsubmit="return validateDriverSave()">
							<div class="tab-content">
								<div class="tab-pane active" id="driverPersonal">
									<div class="box">
										<div class="box-body">
										<input type="hidden" id="showOriginalDLRemark" value="${configuration.showOriginalDLRemark}" />
											<input type="hidden" id="showVehicle" value="${configuration.showVehicle}" />
										<%-- <input type="hidden" id="showDateOfBirth"value="${configuration.showDateOfBirth}" />
										<input type="hidden" id="showOriginalDLRemark" value="${configuration.showStartDate}" /> --%>
											<div class="row">
												<div id="grpdriverName" class="form-group">
													<label class="L-size control-label" for="driverfirstname">First
														Name :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text" id="driverfirstname"
															name="driver_firstname" required="required"
															onkeypress="return IsAlphaNumericFristName(event);"
															ondrop="return false;" maxlength="25"> <span
															id="driverNameIcon" class=""></span>
														<div id="driverNameErrorMsg" class="text-danger"></div>
														<label class="error" id="errorFristName"
															style="display: none"> </label>
													</div>
												</div>
												<div class="row">
													<div class="form-group">
														<label class="L-size string required control-label">Last
															Name :</label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_Lastname"
																onkeypress="return IsAlphaNumericLastName(event);"
																ondrop="return false;" maxlength="25"> <label
																class="error" id="errorLastName" style="display: none">
															</label>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="form-group">
														<label class="L-size string required control-label">Father
															Name :</label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_fathername" id="driver_fathername"
																onkeypress="return IsAlphaNumericFatherName(event);"
																ondrop="return false;" maxlength="25"> <label
																class="error" id="errorFatherName" style="display: none">
															</label>
														</div>
													</div>
												</div>
											</div>
										
											<div class="row">
												<div class="form-group" id="grpbirthDay">
													<label class="string L-size required control-label"
														for="driver_dateofbirth">Date Of Birth :<abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group input-append date"
															id="dateofbirth">
															<input type="text" class="form-text"
																id="driver_dateofbirth" name="driver_dateofbirth"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																 /> <span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span> <span id="birthDayIcon"
																class=""></span>
															<div id="birthDayErrorMsg" class="text-danger"></div>
														</div>
														<label class="error" id="errorDateofbirth"> </label>
													</div>
												</div>
											</div>
											<c:if test="${configuration.showQualification}">
												<div class="row">
													<div class="form-group">
														<label class="L-size string required control-label">Qualification:
														</label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_Qualification"
																onkeypress="return IsAlphaNumericQualification(event);"
																ondrop="return false;" maxlength="25"> <label
																class="error" id="errorQualification"
																style="display: none"> </label>
														</div>
													</div>
												</div>
											</c:if>
											<c:if test="${configuration.showBloodGroup}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Blood
														Group :</label>
													<div class="I-size">
														<select class="form-text" name="driver_bloodgroup">
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
														<select class="form-text select2" name="driver_languages"
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
									<c:if test="${configuration.showVehicle}">		
										<%-- <div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">
														Vehicle :</label>
													<div class="I-size">
														<select class="form-text" name="vid" id="vid">
															<option value="0">Please Select Vehicle</option>
															<c:forEach items="${vehicle}" var="vehicle">
																<option value="${vehicle.vid}">
																		<c:out value="${vehicle.vehicle_registration}" />
																</option>
															</c:forEach>
														</select>
														<input type="hidden" name = "driver_group" id= "driver_group"/>
													</div>
												</div>
											</div> --%>
																<div class="row1" id="grpmanufacturer" class="form-group">
																
																	<label class="L-size control-label" for="manufacurer">Vehicle :<abbr title="required">*</abbr>
																	</label>
																	<div class="I-size">
																	<input type="hidden" id="vid" name="vid" style="width: 100%;" 
																	 placeholder="Please Enter Vehicle" />
																	</div>
																</div>
										</c:if>	
										</div>
									</div>
									<div>

										<div class="pull-right">

											<a href="#driverGroup" data-toggle="pill"
												class="btn btn-success">Next </a>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="driverGroup">
									<div class="box">
										<div class="box-body">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Group
														Service :</label>
													<div class="I-size">
														<select class="form-text" name="vehicleGroupId" id="vehiclegroupid">
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																		<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
														<input type="hidden" name = "driver_group" id= "driver_group"/>
													</div>
												</div>
											</div>
											<hr>
											<div class="row">

												<div class="form-group">
													<!-- <div class="col-md-2">
												<label class="L-size control-label">Driver</label>
											</div> -->
													<div class=" col-sm-9 col-md-6  col-xs-10 col-md-offset-1">
														<label class="L-size control-label">Driver : </label>
														<div class="btn-group" id="status" data-toggle="buttons">
															<label class="btn btn-default btn-on btn-lg active">
																<input type="radio" value="1" name="driverStatusId"
																checked="checked">Active
															</label> <label class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="2" name="driverStatusId">InActive
															</label>
														</div>
													</div>
												</div>
											</div>
											<hr />
											<c:choose>
												<c:when test="${flavorWiseDriverSalary}">
													<div class="row">
														<div class="form-group">
															<label class="L-size control-label">Salary Type :
																<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<select class="form-text" name="driverSalaryTypeId">
																	<option value="1">PER DAY WISE SALARY</option>
																	<option value="2">TRIP WISE SALARY</option>
																	<option value="3">KM WISE SALARY</option>
																</select>
															</div>
														</div>
													</div>
												</c:when>
												<c:otherwise>
													<div class="row">
														<div class="form-group">
															<label class="L-size control-label">Salary Type :
																<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<select class="form-text" name="driverSalaryTypeId">
																	<option value="1">PER DAY WISE SALARY</option>
																</select>
															</div>
														</div>
													</div>
												</c:otherwise>
											
											</c:choose>
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">Per day Salary:
														<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_perdaySalary"
															onkeypress="return IsNumericMob(event);"
															ondrop="return false;" value="0" maxlength="5"> <label
															class="error" id="errorMob" style="display: none">
														</label>
													</div>
												</div>
											</div>
										<c:if test="${configuration.showESINumber}">
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">ESI Percentage
														:</label>
													<div class="I-size">
														<div class="input-group">
															<input type="text" class="form-text" value=""
																name="driver_esiamount"
																onkeypress="return IsAlphaNumericAddress(event);"
																ondrop="return false;" maxlength="5"> <span
																class="input-group-addon add-on"><span
																class="fa fa-percent"></span></span><label class="error"
																id="errorAddress" style="display: none"> </label>
														</div>
													</div>
												</div>

											</div>
										</c:if>
										<c:if test="${configuration.showPFNumber}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label"
														for="joinDate">PF Percentage :
													</label>
													<div class="I-size">
														<div class="input-group">
															<input type="text" class="form-text" value=""
																name="driver_pfamount"
																onkeypress="return IsAlphaNumericAddress(event);"
																ondrop="return false;" maxlength="5"> <span
																class="input-group-addon add-on"><span
																class="fa fa-percent"></span></span> <label class="error"
																id="errorAddress" style="display: none"> </label>
														</div>
													</div>
												</div>
											</div>
											</c:if>
										</div>
									</div>
									<div>
										<div class="pull-left">
											<a href="#driverPersonal" data-toggle="pill"
												class="btn btn-info">Prev </a>
										</div>
										<div class="pull-right">

											<a href="#contactInfo" data-toggle="pill"
												class="btn btn-success">Next </a>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="contactInfo">
									<div class="box">
										<div class="box-body">
											<div class="row">
											<c:if test="${configuration.showDriverEmail}">
												<div class="form-group">
													<label class="L-size string required control-label">Email
														Id :</label>
													<div class="I-size">
														<input type="email" class="form-text" name="driver_email"
															id="driver_email"
															onkeypress="return IsAlphaNumericEmail(event);"
															ondrop="return false;" maxlength="50"> <label
															class="error" id="errorEmail" style="display: none">
														</label> <label class="error" id="errorEmailValidate"> </label>

													</div>
												</div>
											</c:if>
												<div class="row">
													<div class="form-group" id="grpmobileNumber">
														<label class="L-size control-label" for="mobNumber">Mobile
															Number :<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<input type="text" class="form-text" id="mobNumber"
																name="driver_mobnumber"
																onkeypress="return IsNumericMob(event);"
																ondrop="return false;" maxlength="15"> <span
																id="mobNumberIcon" class=""></span>
															<div id="mobNumberErrorMsg" class="text-danger"></div>
															<label class="error" id="errorMob" style="display: none">
															</label>
														</div>
													</div>
												</div>
												
												<c:if test="${configuration.showHomePhoneNumber}">

												<div class="row">
													<div class="form-group">
														<label class="L-size control-label">Home Phone
															Number :</label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_homenumber"
																onkeypress="return IsNumericHome(event);"
																ondrop="return false;" maxlength="15"> <label
																class="error" id="errorHome" style="display: none">
															</label>
														</div>
													</div>
												</div>
											</c:if>
												<div class="row">
													<div class="form-group">
														<label class="L-size string required control-label">Address
															:</label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_address"
																onkeypress="return IsAlphaNumericAddress(event);"
																ondrop="return false;" maxlength="100"> <label
																class="error" id="errorAddress" style="display: none">
															</label>

														</div>
													</div>
												</div>
												<c:if test="${configuration.showAddress2}">
												<div class="row">
													<div class="form-group">
														<label class="L-size string required control-label">Address
															Line 2 :</label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_address2"
																onkeypress="return IsAlphaNumericAddress2(event);"
																ondrop="return false;" maxlength="100"> <label
																class="error" id="errorAddress2" style="display: none">
															</label>
														</div>
													</div>
												</div>
												</c:if>
											</div>
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Country
														:</label>
													<div class="I-size">
														<select name="driver_country"
															class="countries" style="width: 100%"
															id="countryId">
															<option value="">Select Country</option>
														</select> <label class="error" id="errorCountry"
															style="display: none"> </label>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">State
														:</label>
													<div class="I-size">
														<select name="driver_state"
															class="states" style="width: 100%"
															id="stateId">
															<option value="">Select State</option>
														</select> <label class="error" id="errorState"
															style="display: none"> </label>
													</div>
												</div>
											</div>

											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">City
														:</label>
													<div class="I-size">
														<select name="driver_city"
															class="cities" style="width: 100%"
															id="cityId">
															<option value="">Select City</option>
														</select> <label class="error" id="errorCity" style="display: none">
														</label>
													</div>
												</div>
											</div>

											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Postal
														Code :</label>
													<div class="I-size">
														<input type="text" class="form-text" name="driver_pincode"
															onkeypress="return IsNumericPin(event);"
															ondrop="return false;" maxlength="6"> <label
															class="error" id="errorPin" style="display: none">
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div>
										<div class="pull-left">
											<a href="#driverGroup" data-toggle="pill"
												class="btn btn-info">Prev </a>
										</div>
										<div class="pull-right">

											<a href="#EmpInfo" data-toggle="pill" class="btn btn-success">Next
											</a>
										</div>
									</div>
								</div>

								<div class="tab-pane" id="EmpInfo">
									<div class="box">
										<div class="box-body">
											<div class="row">
												<div class="form-group" id="grpempNumber">
													<label class="L-size string required control-label"
														for="empNumber">Employee Number :<abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_empnumber" id="empNumber"
															onkeypress="return IsAlphaNumericEmp(event);"
															ondrop="return false;" maxlength="25"> <span
															id="empNumberIcon" class=""></span>
														<div id="empNumberErrorMsg" class="text-danger"></div>
														<label class="error" id="errorEmp" style="display: none">
														</label>

													</div>
												</div>
											</div>
										<c:if test="${configuration.showInsuranceNumber}">	
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">Insurance
														Number :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_insuranceno"
															onkeypress="return IsInsuranceNumber(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorInsurance" style="display: none">
														</label>

													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${configuration.showESINumber}">
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">ESI Number :</label>
													<div class="I-size">
														<input type="text" class="form-text" name="driver_esino"
															onkeypress="return IsESINumber(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorESI" style="display: none">
														</label>
													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${configuration.showPFNumber}">
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">PF Number :</label>
													<div class="I-size">
														<input type="text" class="form-text" name="driver_pfno"
															onkeypress="return IsPFNumber(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorPF" style="display: none">
														</label>
													</div>
												</div>
											</div>
											</c:if>
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Job
														Title :</label>
													<div class="I-size">
														<select class="form-text" name="driJobId">
															<c:forEach items="${driverJobType}" var="driverJobType">
																<option value="${driverJobType.driJobId}">
																		<c:out value="${driverJobType.driJobType}" />
															</c:forEach>
														</select> <label class="error" id="errorJob" style="display: none">
														</label>
													</div>
												</div>
											</div>
											<c:if test="${configuration.showOtherTrainning}">
											<div class="row">
												<div class="form-group">
													<label class="L-size control-label">Other Training
														/ Specialization :</label>
													<div class="I-size">
														<select class="form-text select2" name="driver_trainings"
															multiple="multiple"
															data-placeholder="Select a Certificate Max 3"
															style="width: 100%;">
															<c:forEach items="${driverTrainingType}"
																var="driverTrainingType">
																<option value="${driverTrainingType.dri_TrainingType}">
																		<c:out value="${driverTrainingType.dri_TrainingType}" />
															</c:forEach>
														</select>

													</div>
												</div>
											</div>
											</c:if>
											<div class="row">
												<div class="form-group" id="grpjoinDate">
													<label class="L-size string required control-label"
														for="joinDate">Start Date :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group input-append date" id="StartDate">
															<input type="text" class="form-text" id="joinDate"
																name="driver_startdate" maxlength="10"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span>
														</div>
														<span id="joinDateIcon" class=""></span>
														<div id="joinDateErrorMsg" class="text-danger"></div>

													</div>
												</div>
											</div>
											<c:if test="${configuration.showLeaveDate}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Leave
														Date :</label>
													<div class="I-size">
														<div class="input-group input-append date" id="LeaveDate">
															<input type="text" class="form-text"
																name="driver_leavedate"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																maxlength="10" /> <span
																class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span>
														</div>
													</div>
												</div>
											</div>
											
											</c:if>
										</div>
									</div>
									<div>
										<div class="pull-left">
											<a href="#contactInfo" data-toggle="pill"
												class="btn btn-info">Prev </a>
										</div>
										<div class="pull-right">

											<a href="#DlInfo" data-toggle="pill" class="btn btn-success">Next
											</a>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="DlInfo">
									<div class="box">
										<div class="box-body">
											<div class="row">
												<div class="form-group" id="grpdlNumber">
													<label class="L-size string required control-label"
														for="dlNumber">DL Number : <abbr title="required">*</abbr> 
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_dlnumber" id="dlNumber"
															onkeypress="return IsAlphaNumericDl(event);"
															ondrop="return false;" maxlength="25"> <span
															id="dlNumberIcon" class=""></span>
														<div id="dlNumberErrorMsg" class="text-danger"></div>
														<label class="error" id="errorDl" style="display: none">
														</label>

													</div>
												</div>
											</div>
											<c:if test="${configuration.showBadgeNo}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Badge
														Number :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_badgenumber"
															onkeypress="return IsAlphaNumericBadge(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorBadge" style="display: none">
														</label>

													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${configuration.showLicenseClass}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">License
														Class :</label>
													<div class="I-size">
														<input type="text" class="form-text" name="driver_dlclass"
															onkeypress="return IsAlphaNumericClass(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorClass" style="display: none">
														</label>
													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${configuration.showLicenseState}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">License
														State :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_dlprovince"
															onkeypress="return IsAlphaNumericDlprovince(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorDlprovince" style="display: none">
														</label>
													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${configuration.showAuthorizedToDrive}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Authorised
														to Drive :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_authorised"
															onkeypress="return IsAlphaNumericAuthorise(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorAuthorise" style="display: none">
														</label>
													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${configuration.showOriginalDLRemark}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Original
														DL Remarks :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_dlOriginal"
															onkeypress="return IsAlphaNumericOrinalRemarls(event);"
															ondrop="return false;" maxlength="150"> <label
															class="error" id="errorOrinalRemarls"
															style="display: none"> </label>
													</div>
												</div>
											</div>
											</c:if>
									<!-- 	DRIVER RENEWAL REMINDER START -->
											<input type="hidden" id="renewalTypeConfigID" value="${configuration.showRenewalType}">
											<div class="row hide" id="renewalTypeId">
												<div class="form-group">
													<label class="L-size string required control-label">Renewal Type
														:<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text"  name="driverRenewalTypeId" id="grp_option">
															<option value="${driverDocType1.dri_id}">
															<c:out value="${driverDocType1.dri_DocType}" /></option>
														</select>
													</div>
												</div>
											</div>
											<input type="hidden" id="validityConfigID" value="${configuration.showRenewalValidity}">
											<div class="row1 hide" id="validityId">
												<label class="L-size control-label">Validity From
													&amp; To <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date">
														<input type="text" class="form-text"
															name="driver_dlfrom_show" id="DriverReminderdate" onchange="validateRenewal();"
															maxlength="25"> <span
															class="input-group-addon add-on"><span
															class="fa fa-calendar"></span></span>
													</div>
												</div>
											</div>
											<input type="hidden" id="thresholdConfigID" value="${configuration.showDueThreshold}">
											<div class="row hide" id="thresholdId">
												<div class="form-group">
													<label class="L-size string required control-label">Due Threshold
													:</label>
													<div class="I-size">
														<div class="col-md-2">
															<input type="text" class="form-text"
																name="driver_timethreshold" min="1" max="6"
																maxlength="1" value="1" required="required"
																onkeypress="return IsNumericTimeThru(event);"
																ondrop="return false;"> <label class="error"
																id="errorTimeThru" style="display: none"> </label>
														</div>
														<div class="col-md-2">
															<select class="form-text" name="driver_periedthreshold">
																<option value="0">day(s)</option>
																<option value="7">Week(s)</option>
																<option value="28">Month(s)</option>
															</select>
														</div>
													</div>
												</div>
											</div>
											
											<input type="hidden" id="docTypeConfigID" value="${configuration.showRenewalDoc}">
											<div class="form-group hide" id="docTypeId">
												<label class="L-size string required control-label">Upload Driver 
												Renewal Documents Browse  :</label>
												<div class="row">
													<div class="I-size">
														<input type="file" id="renewalDocId" onchange="validateRenewal();" name="input-file-preview" />
													</div>
												</div>
											</div>
										<!-- 	DRIVER RENEWAL REMINDER END -->
										</div>
									</div>
									<div>
										<div class="pull-left">
											<a href="#EmpInfo" data-toggle="pill" class="btn btn-info">Prev
											</a>
										</div>
										<div class="pull-right">

											<a href="#BankInfo" data-toggle="pill"
												class="btn btn-success">Next </a>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="BankInfo">
									<div class="box">
										<div class="box-body">
										<c:if test="${configuration.showBankAccount}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label"
														for="bank_ac">Bank Account Number : </label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_banknumber" id="bank_ac" pattern=".{6,25}"
															title="Six or more A/C number"
															onkeypress="return IsAlphaNumericAC(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorbank_ac" style="display: none">
														</label>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.showIFSCCode}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label"
														for="bank_ifsc">Bank IFSC Number :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_bankifsc" id="bank_ifsc" pattern=".{6,25}"
															title="Six or more IFSC number"
															onkeypress="return IsAlphaNumericIFSC(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorBadge" style="display: none">
														</label>

													</div>
												</div>
											</div>
											</c:if>
											
											<div class="row">
												<div class="form-group" id="grpaadharNumber">
													<label class="L-size string required control-label"
														for="aadharNumber" for="addhar_no">Aadhar Number :
														<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_aadharnumber" id="aadharNumber"
															pattern=".{6,50}" title="Six or more Aadhar number"
															onkeypress="return IsAlphaNumericAadhar(event);"
															ondrop="return false;" maxlength="50"> <span
															id="aadharNumberIcon" class=""></span>
														<div id="aadharNumberErrorMsg" class="text-danger"></div>
														<label class="error" id="errorClass" style="display: none">
														</label>
													</div>
												</div>
											</div>
										<c:if test="${configuration.showBankName}">
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label"
														for="bankname">Bank Name :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_bankname" id="bankname" pattern=".{3,250}"
															title="3 or more characters"
															onkeypress="return IsAlphaNumericBN(event);"
															ondrop="return false;" maxlength="250"> <label
															class="error" id="errorDlprovince" style="display: none">
														</label>
													</div>
												</div>
											</div>
											</c:if>
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label"
														for="pan_no">PAN Number :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_pannumber" id="pan_no" pattern=".{6,50}"
															title="Six or more PAN number"
															onkeypress="return IsAlphaNumericPAN(event);"
															ondrop="return false;" maxlength="50"> <label
															class="error" id="errorClass" style="display: none">
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div>
										<div class="pull-left">
											<a href="#DlInfo" data-toggle="pill" class="btn btn-info">Prev
											</a>
										</div>
										<c:if test="${configuration.showReferenceData}">
										<div class="pull-right">

											<a href="#RefInfo" data-toggle="pill" class="btn btn-success">Next
											</a>
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
													<label class="L-size string required control-label">Ref_Frist
														Name :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_reffristname"
															onkeypress="return IsAlphaNumericRFrist(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorRFrist" style="display: none">
														</label>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Ref_Last
														Name :</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_reflastname"
															onkeypress="return IsAlphaNumericRLast(event);"
															ondrop="return false;" maxlength="25"> <label
															class="error" id="errorRLast" style="display: none">
														</label>
													</div>
												</div>

											</div>
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Contact
														Number</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_refcontect"
															onkeypress="return IsNumericContent(event);"
															ondrop="return false;" maxlength="15"> <label
															class="error" id="errorContent" style="display: none">
														</label>

													</div>
												</div>
												<input type="hidden" class="form-text" name="createdBy"
													value="${user}">

											</div>
										</div>
										
										<input type="hidden" class="form-text" name="driver_photoid"
											value="1">
													
									</div>
									<div>
										<div class="pull-left">
											<a href="#BankInfo" data-toggle="pill" class="btn btn-info">Prev
											</a>
										</div>
									       </div>
									
								</div>
								</c:if>
								
							<div class="box-footer h-padded">
								<fieldset class="form-actions">
									<button type="submit" class="btn btn-success" onclick='return validateRenewal();'>Create
										Driver</button>
									<a class="btn btn-link" href="<c:url value="/getDriversList"/>">Cancel</a>
								</fieldset>
							</div>
							
						</form>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/Driver.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>	
	
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$(".select2").select2();
							$("#tagPicker").select2({
								closeOnSelect : !1
							});
							
							$('#driver_group').val($("#vehiclegroupid option:selected").text().trim());
							$('#vehiclegroupid').on('change', function() {
								$('#driver_group').val($("#vehiclegroupid option:selected").text().trim());
							});

							
		});
		/* $(function() {
			function a(a, b) {
				$("#dateRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
			}
			a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
				ranges: {
		            Today: [moment(), moment()],
		            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
		            "Last 7 Days": [moment().subtract(6, "days"), moment()],
		            "This Month": [moment().startOf("month"), moment().endOf("month")],
		            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
		        }
			}
			, a)
		}

		); */
		
	</script>
	
	<script>
			$(document).ready(function() {
				$(function() {
					$('#DriverReminderdate').daterangepicker();
				});
			});

			$(document).ready(function() {
				function t() {
					var t = $("#grp_option :selected"), n = t.text();
					$("#target").text(n + " Number :")
				}
				$("#grp_option").on("change", function() {
					t()
				}), $("#grp_option").change()
			});
		</script>
	
	
	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>