<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">

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
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / Edit
					Driver / <span><c:out value="${driver.driver_firstname}" />
						<c:out value="${driver.driver_Lastname}" /></span>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-user" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>


		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="main-body">
			<div class="row">
				<sec:authorize access="hasAuthority('EDIT_DRIVER')">
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
						<form id="formDriver" action="<c:url value="updateDriver.in"/>"
							method="post" name="formDriver" role="form"
							class="form-horizontal" onsubmit="return validateDriverUpdate()">

							<div class="tab-content">
								<div class="tab-pane active" id="driverPersonal">
									<div class="box">
										<div class="box-body">

											<input type="hidden" name="driver_id"
												value="<c:out value="${driver.driver_id}"/>"> <input
												type="hidden" name="driver_photoid"
												value="<c:out value="${driver.driver_photoid}"/>">
											
									<input type="hidden" id="showOriginalDLRemark" value="${configuration.showOriginalDLRemark}" />
											<div class="row">
												<div id="grpdriverName" class="form-group">
													<label class="L-size control-label" for="driverfirstname">First
														Name :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text" id="driverfirstname"
															name="driver_firstname" required="required"
															value="<c:out value="${driver.driver_firstname}"/>"
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
																value="<c:out value="${driver.driver_Lastname}"/>"
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
																name="driver_fathername"
																value="<c:out value="${driver.driver_fathername}"/>"
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
															<input type="text" class="form-text" readonly="readonly"
																value="<c:out value="${driver.driver_dateofbirth}"/>"
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
																value="<c:out value="${driver.driver_Qualification}"/>"
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
															<option
																value="<c:out value="${driver.driver_bloodgroup}"/>">
																<c:out value="${driver.driver_bloodgroup}" />
															</option>
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
															<option selected="selected"
																value="<c:out value="${driver.driver_languages}"/>">
																<c:out value="${driver.driver_languages}" />
															</option>
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
															<option value="${driver.vid}">${driver.vehicle_registration}</option>
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
														<select class="form-text" name="vehicleGroupId" id="vehicleGroupId">
															<option value="${driver.vehicleGroupId}"><c:out value="${driver.driver_group}" /></option>
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<c:if
																	test="${vehiclegroup.vGroup != driver.driver_group}">
																	<option value="${vehiclegroup.gid}">
																		<c:out value="${vehiclegroup.vGroup}" />
																	</option>
																</c:if>

															</c:forEach>
														</select>
													</div>
													<input type="hidden" name="driver_group" id="driver_group"/>
												</div>
											</div>
											<hr>
											<div class="row">

												<div class="form-group">
													<label class="L-size string required control-label">Driver
														Status :</label>
													<div class="I-size">
														<select class="form-text" name="driverStatusId" id="DriverStatusValue">
															<option value="${driver.driverStatusId}"><c:out
																	value="${driver.driver_active}" /></option>
															<option value="1"><c:out value="ACTIVE" /></option>
															<option value="2"><c:out value="INACTIVE" /></option>
															<option value="3"><c:out value="TRIPROUTE" /></option>
														</select>
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
																	<option value="<c:out value="${driver.driverSalaryType}"/>">
																		<c:out value="${driver.driverSalaryType}" />
																	</option>
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
															value="${driver.driver_perdaySalary}"
															onkeypress="return isNumberKey(event);"
															ondrop="return false;" value="0" maxlength="8"> <label
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
																<input type="text" class="form-text"
																	name="driver_esiamount"
																	value="${driver.driver_esiamount}"
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
															for="joinDate">PF Percentage : </label>
														<div class="I-size">
															<div class="input-group">
																<input type="text" class="form-text"
																	name="driver_pfamount" value="${driver.driver_pfamount}"
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
																value="<c:out value="${driver.driver_email}"/>"
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
																value="<c:out value="${driver.driver_mobnumber}"/>"
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
																value="<c:out value="${driver.driver_homenumber}"/>"
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
																value="<c:out value="${driver.driver_address}"/>"
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
																value="<c:out value="${driver.driver_address2}"/>"
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
															class="select2 countries form-text" style="width: 100%"
															id="countryId">
															<option value="${driver.driver_country}">${driver.driver_country}</option>
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
															class="select2 states form-text" style="width: 100%"
															id="stateId">
															<option value="${driver.driver_state}">${driver.driver_state}</option>
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
															class="select2 cities form-text" style="width: 100%"
															id="cityId">
															<option value="${driver.driver_city}">${driver.driver_city}</option>
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
															value="<c:out value="${driver.driver_pincode}"/>"
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
															value="<c:out value="${driver.driver_empnumber}"/>"
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
															value="${driver.driver_insuranceno}"
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
															value="${driver.driver_esino}" ondrop="return false;"
															maxlength="25"> <label class="error"
															id="errorESI" style="display: none"> </label>
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
															value="${driver.driver_pfno}" ondrop="return false;"
															maxlength="25"> <label class="error" id="errorPF"
															style="display: none"> </label>
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
															<option value="${driver.driJobId}"><c:out value="${driver.driver_jobtitle}" /></option>
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
															<option selected="selected"
																value="<c:out value="${driver.driver_trainings}"/>">
																<c:out value="${driver.driver_trainings}" />
															</option>
															<c:forEach items="${driverTrainingType}"
																var="driverTrainingType">
																<option><c:out
																		value="${driverTrainingType.dri_TrainingType}" /></option>
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
																value="<c:out value="${driver.driver_startdate}"/>"
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
																name="driver_leavedate" readonly="readonly"
																 
																value="<c:out value="${driver.driver_leavedate}"/>"
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
														for="dlNumber">DL Number :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_dlnumber" id="dlNumber"
															value="<c:out value="${driver.driver_dlnumber}"/>"
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
															value="<c:out value="${driver.driver_badgenumber}"/>"
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
															value="<c:out value="${driver.driver_dlclass}"/>"
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
															value="<c:out value="${driver.driver_dlprovince}"/>"
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
															value="${driver.driver_authorised}"
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
															value="${driver.driver_dlOriginal}"
															onkeypress="return IsAlphaNumericOrinalRemarls(event);"
															ondrop="return false;" maxlength="150"> <label
															class="error" id="errorOrinalRemarls"
															style="display: none"> </label>
													</div>
												</div>
											</div>
											</c:if>

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
															name="driver_banknumber" id="bank_ac"
															value="${driver.driver_banknumber}" pattern=".{6,25}"
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
															value="${driver.driver_bankifsc}"
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
															pattern=".{6,50}" value="${driver.driver_aadharnumber}"
															title="Six or more Aadhar number"
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
															value="${driver.driver_bankname}"
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
															value="${driver.driver_pannumber}"
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
															value="<c:out value="${driver.driver_reffristname}"/>"
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
															value="<c:out value="${driver.driver_reflastname}"/>"
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
															value="<c:out value="${driver.driver_refcontect}"/>"
															onkeypress="return IsNumericContent(event);"
															ondrop="return false;" maxlength="15"> <label
															class="error" id="errorContent" style="display: none">
														</label>

													</div>
												</div>
												<input type="hidden" class="form-text" name="createdBy"
													value="${driver.createdBy}"> <input type="hidden"
													class="form-text" name="lastModifiedBy" value="${user}">

											</div>
										</div>
										<input type="hidden" class="form-text" name="driver_photoid"
											value="1">
										<input type="hidden" id="vehicleId" value="${driver.vid}">
										<input type="hidden" id="vehicleNumber" value="${driver.vehicle_registration}">	
									</div>
								</div>
								</c:if>
							</div>
							<div class="box-footer h-padded">
								<fieldset class="form-actions">
									<button type="submit" class="btn btn-success">Update
										Driver</button>
									<a class="btn btn-link"
										href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>
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

	<script type="text/javascript">
	$(document).ready(function(){
		$('#vid').select2('data', {
			id : $('#vehicleId').val(),
			text : $('#vehicleNumber').val()
		});
	});
	/* $(document).ready(function(){$(".select2").select2();$("#tagPicker").select2({closeOnSelect:!1})}); */
	</script>
	<!-- <script type="text/javascript">
	$(document).ready(function(){var e=document.getElementById("DriverStatusValue").value;"active"===e.toLowerCase()?(document.getElementById("Active").checked=!0,document.getElementById("Active-label").className="btn btn-default btn-on btn-lg active"):(document.getElementById("InActive").checked=!0,document.getElementById("InActive-label").className="btn btn-default btn-on btn-lg active")});
	</script>  -->
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
							setTimeout(function(){ 
								
								}, 
							1000);
							
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>

</div>