<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
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
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newUserList/1.in"/>">User List</a> / <a>Create
						New User</a>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/newUserList/1.in"/>">Cancel
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<h4>Warning!</h4>
				This User email Id Already Created Successfully. Please Check.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('ADD_USER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_USER')">
			<div class="row">
				<div class="box">
					<div class="box-body">
						<div class="container">
							<ul class="tabs">
								<li class="tab-link current" data-tab="Details"><span
									id="Detail">USER BASIC DETAILS</span></li>
								<li class="tab-link" data-tab="personaldetails"><span>PERSONAL
										DETAILS</span></li>
								<li class="tab-link" data-tab="comapnydetails"><span>COMPANY
										DETAILS</span></li>

								<li class="tab-link" data-tab="subscribedetails"><span>SUBSCRIBE
										DETAILS</span></li>

							</ul>

							<form id="formUser" action="/" method="post" enctype="utf8"
								enctype="multipart/form-data" name="formUser" role="form"
								class="form-horizontal">
								<div id="Details" class="tab-content current">
									<div class="form-horizontal ">
										<fieldset>
											<legend id="Identification">User Basic Details </legend>
											<div class="row1" id="grpfirstName" class="form-group">
												<label class="L-size control-label" for="firstName">
													<spring:message code="label.user.firstName"></spring:message>
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" name="firstName" value=""
														id="firstName" placeholder="eg: sachin"
														pattern="[A-Za-z]{2, 50}" maxlength="50"
														title="four or more characters"
														onkeypress="return IsFirstName(event);"
														ondrop="return false;"><span id="firstNameIcon"
														class=""></span>
													<div id="firstNameErrorMsg" class="text-danger"></div>
													<label class="error" id="errorFirstName"
														style="display: none"> </label> <span id="firstNameError"
														class="alert alert-danger col-sm-6" style="display: none"></span>
												</div>

											</div>

											<div class="form-group row1" id="grplastName">
												<label class="L-size control-label" for="lastName">
													<spring:message code="label.user.lastName"></spring:message>
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" name="lastName" id="lastName"
														pattern="[A-Za-z]{0,50}" title="four or more characterss"
														maxlength="50" value="" placeholder="eg: tendulkar"
														onkeypress="return IsLastName(event);"
														ondrop="return false;"><span id="lastNameIcon"
														class=""></span>
													<div id="lastNameErrorMsg" class="text-danger"></div>
													<label class="error" id="errorLastName"
														style="display: none"> </label><span id="lastNameError"
														class="alert alert-danger col-sm-4" style="display: none"></span>
												</div>
											</div>
											<div class="form-group row1" id="grpuserEmail">
												<label class="L-size control-label" for="userEmail">
													User Email ID:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<input type="text" class="form-text " name="email"
															id="userEmail" maxlength="50"
															placeholder="eg: sachin${company.company_email_prefix}"
															pattern="[a-zA-Z0-9]+"
															<%-- pattern="[a-z0-9._%+-]+${company.company_email_prefix}" --%>
															title="Invalid email please enter only name" value=""
															required="required" onkeypress="return IsEmail(event);"
															ondrop="return false;"> <span
															class="input-group-addon">${company.company_email_prefix}</span>
													</div>
													<span id="userEmailIcon" class=""></span>
													<div id="userEmailErrorMsg" class="text-danger"></div>
													<span id="emailError" class="alert alert-danger col-sm-4"
														style="display: none">Already User email Id
														Created.</span> <label class="error" id="errorEmail"
														style="display: none"></label>
												</div>
											</div>

											<div class="form-group row" id="grpuserPassword">
												<label class="L-size control-label" for="password">
													<spring:message code="label.user.password"></spring:message>
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input id="password" class="form-text " name="password"
														value="" type="password"
														pattern="(?=.*[a-z]).{4,}"
														title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
														placeholder="eg: T.Sachin48@ " /> <span
														id="userPasswordIcon" class=""></span>
													<div id="userPasswordErrorMsg" class="text-danger"></div>
													<span id="passwordError"
														class="alert alert-danger col-sm-6" style="display: none"></span>
												</div>
											</div>
											<div class="form-group row" id="grpusermatchPassword">
												<label class="L-size control-label" for="matchPassword">
													<spring:message code="label.user.confirmPass"></spring:message>
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input id="matchPassword" class="form-text"
														name="matchingPassword" value="" type="password"
														pattern="(?=.*[a-z]).{4,}"
														title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
														placeholder="eg: T.Sachin48@ " /><span
														id="usermatchPasswordIcon" class=""></span>
													<div id="usermatchPasswordErrorMsg" class="text-danger"></div>
													<span id="globalError" class="alert alert-danger col-sm-6"
														style="display: none"></span>
												</div>
											</div>
											<div class="form-group row" id="grpuserRole">
												<label class="L-size control-label" for="userRole"><spring:message
														code="label.pages.roles" />:<abbr title="required">*</abbr></label>
												<div class="I-size">
													<select class="form-text select2" name="Roles"
														style="width: 100%;" id="userRole">
														<c:forEach items="${Role}" var="Role">
															<option value="${Role.name}">${Role.name}</option>
														</c:forEach>
													</select><span id="userRoleIcon" class=""></span>
													<div id="userRoleErrorMsg" class="text-danger"></div>
													<span id="globalError" class="alert alert-danger col-sm-4"
														style="display: none"></span>
												</div>
											</div>
											<div class="row1" id="grpuserCompany" class="form-group">
												<!-- <label class="L-size control-label" for="company_id">Company
													:<abbr title="required">*</abbr>
												</label> -->
												<div class="I-size">
													<%-- <select class="form-control select2" id="company_id"
														name="company_id" style="width: 100%;">
														<option value="" selected="selected">Please
															Select</option>
															<c:forEach items="${company}" var="company">
														<option value="${company.company_id}">
															<c:out value="${company.company_name} - " />
															<c:out value="${company.company_city}" />
														</option>
														</c:forEach>
													</select> --%>
													<span id="userCompanyIcon" class=""></span>
													<div id="userCompanyErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<div class="row1" id="grpuserDepartment" class="form-group">
												<label class="L-size control-label" for="DepartmentList">Department
													: <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-control select2" id="DepartmentList"
														name="department_id" style="width: 100%;">
														<c:forEach items="${department}" var="department">
															<option value="${department.depart_id}">${department.depart_name}</option>
														</c:forEach>
													</select> <span id="userDepartmentIcon" class=""></span>
													<div id="userDepartmentErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<div class="row1" id="grpuserBranch" class="form-group">
												<label class="L-size control-label" id="Type">Branch
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-control select2" id="BranchList"
														name="branch_id" style="width: 100%;">
														<c:forEach items="${Branch}" var="Branch">
														<option value="${Branch.branch_id}">${Branch.branch_name}</option>
														</c:forEach>
													</select> <span id="userBranchIcon" class=""></span>
													<div id="userBranchErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Designation :</label>
												<div class="I-size">
													<input class="string required form-text" name="designation"
														type="text" maxlength="150" value=""
														placeholder="eg: Manager " pattern=".{0, 150}"
														title="four or more characters"
														onkeypress="return IsDesignation(event);"
														ondrop="return false;"> <label class="error"
														id="errorDesignation" style="display: none"> </label>
												</div>
											</div>


										</fieldset>

									</div>
								</div>
								<!-- Personal  details -->
								<div id="personaldetails" class="tab-content">
									<div class="form-horizontal ">
										<fieldset>
											<legend id="Identification">User Personal Details </legend>
											<div class="row1" id="grpuserDOB" class="form-group">
												<label class="L-size control-label">Date Of Birth: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="renewal_to">
														<input class="form-text" id="userDOB" name="dateofbirth"
															type="text" data-inputmask="'alias': 'dd-mm-yyyy'"
															data-mask=""> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
													<span id="userDOBIcon" class=""></span>
													<div id="userDOBErrorMsg" class="text-danger"></div>
													<span id="DateBirthError"
														class="alert alert-danger col-sm-6" style="display: none"></span>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" id="Type">Gender
													: <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="radio" name="sex" id="male" value="MALE">
													<label for="male">Male</label> <input type="radio"
														name="sex" value="FEMALE" id="female"> <label
														for="female">Female</label>
												</div>
											</div>

											<div class="row1" id="grpuserpersonalEmail"
												class="form-group">
												<label class="L-size control-label" for="userpersonalEmail"><span
													id="RegistrationNumber">Personal Email :</span><abbr
													title="required">*</abbr></label>
												<div class="I-size">

													<input class="form-text" name="personal_email" type="email"
														maxlength="50" id="userpersonalEmail"
														pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
														title="enter email format"
														placeholder="eg: sachin@gmail.com "
														onkeypress="return IsPersonalEmail(event);"
														ondrop="return false;"> <span
														id="userpersonalEmailIcon" class=""></span>
													<div id="userpersonalEmailErrorMsg" class="text-danger"></div>
													<label class="error" id="errorPersonalEmail"
														style="display: none"> </label>
												</div>
											</div>
											<div class="row1" id="grpuserMobile" class="form-group">
												<label class="L-size control-label">Mobile Number: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" id="userMobile"
														name="mobile_number" type="text" maxlength="15"
														placeholder="eg: 988 00 04300" pattern=".{10,15}"
														title="Ten or more characters"
														onkeypress="return IsMobileNo(event);"
														ondrop="return false;"><span id="userMobileIcon"
														class=""></span>
													<div id="userMobileErrorMsg" class="text-danger"></div>
													<span id="errorMobileNo"
														class="alert alert-danger col-sm-6" style="display: none"></span>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Home Number : </label>
												<div class="I-size">
													<input class="form-text" id="home_number"
														name="home_number" type="text" maxlength="15"
														placeholder="eg: 888 00 00000" pattern=".{0,15}"
														title="Ten or more characters"
														onkeypress="return IsHomeNo(event);"
														ondrop="return false;"> <label class="error"
														id="errorHomeNo" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Work Number : </label>
												<div class="I-size">
													<input class="form-text" id="work_number"
														name="work_number" type="text" maxlength="15"
														placeholder="eg: 7635 00 00000" pattern=".{0,15}"
														title="Ten or more characters"
														onkeypress="return IsWorkNo(event);"
														ondrop="return false;"> <label class="error"
														id="errorWorkNo" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Address :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="address_line1" maxlength="150" type="text"
														placeholder="23/6 Main Street" pattern=".{0, 150}"
														title="four or more characters"
														onkeypress="return IsBranchAddress(event);"
														ondrop="return false;"> <label class="error"
														id="errorBranchAddress" style="display: none"> </label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Country :</label>
												<div class="I-size">
													<select name="country" class="select2 countries form-text"
														style="width: 100%" id="countryId">
														<option value="">Select Country</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">State/Province/Region
													:</label>
												<div class="I-size">

													<select name="state" class="select2 states form-text"
														style="width: 100%" id="stateId">
														<option value="">Select State</option>
													</select>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">City :</label>
												<div class="I-size">
													<select name="city" class="select2 cities form-text"
														style="width: 100%" id="cityId">
														<option value="">Select City</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Zip/Pin Code :</label>
												<div class="I-size">
													<input class="string required form-text" name="pincode"
														type="text" maxlength="6" placeholder="eg: 567890"
														onkeypress="return IsBranchPin(event);" pattern=".{0, 6}"
														title="four or more characters" ondrop="return false;">
													<label class="error" id="errorBranchPin"
														style="display: none"> </label>
												</div>
											</div>
											<br>
											<div class="row1">
												<label class="L-size control-label">Emergency
													Contact Name :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="emergency_person" type="text" maxlength="200"
														placeholder="eg: name" pattern=".{0, 15}"
														title="Ten or more characters"
														onkeypress="return IsEmergencyName(event);"
														ondrop="return false;"> <label class="error"
														id="errorEmergencyName" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Emergency Phone
													Number :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="emergency_number" type="text" maxlength="15"
														placeholder="eg: 999 89 56564" pattern=".{0, 15}"
														title="Ten or more characters"
														onkeypress="return IsEmergencyPhone(event);"
														ondrop="return false;"> <label class="error"
														id="errorEmergencyPhone" style="display: none"> </label>
												</div>
											</div>
										</fieldset>


									</div>
								</div>
								<!-- comapnydetails details -->
								<div id="comapnydetails" class="tab-content">
									<div class="form-horizontal ">
										<fieldset>
											<legend id="Class">Company Details</legend>

											<div class="row1">
												<label class="L-size control-label">Employee ID :</label>
												<div class="I-size">
													<input class="string required form-text" name="employes_id"
														type="text" maxlength="15" placeholder="eg: Hh6868"
														onkeypress="return IsEmployeeID(event);"
														pattern=".{0, 15}" title="Four or more characters"
														ondrop="return false;"> <label class="error"
														id="errorEmployeeID" style="display: none"> </label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Working Time
													From</label>
												<div class="I-size">
													<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" value="09:30"
																name="working_time_from"
																data-inputmask="'alias': '99:99'" data-mask="">
															<span class="input-group-addon"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
													</div>
													<label class="L-size control-label" id="ClassofVehicle">Time
														To</label>
													<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" value="20:30"
																name="working_time_to" data-inputmask="'alias': '99:99'"
																data-mask=""> <span class="input-group-addon">
																<span class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">ESI NUMBER: </label>
												<div class="I-size">
													<input class="form-text" name="esi_number" type="text"
														maxlength="30" placeholder="eg: GH6654" pattern=".{0, 30}"
														title="Four or more characters"
														onkeypress="return IsESINO(event);" ondrop="return false;">
													<label class="error" id="errorESINO" style="display: none">
													</label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">PF Number: </label>
												<div class="I-size">
													<input class="form-text" name="pf_number" type="text"
														maxlength="30" placeholder="eg: YU78686876"
														pattern=".{0, 30}" title="Four or more characters"
														onkeypress="return IsPFNo(event);" ondrop="return false;">
													<label class="error" id="errorPFNo" style="display: none">
													</label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">INSURANCE
													NUMBER: </label>
												<div class="I-size">
													<input class="form-text" name="insurance_number"
														type="text" maxlength="30" placeholder="eg: GG687687"
														onkeypress="return IsInsuranceNO(event);"
														pattern=".{0, 30}" title="Four or more characters"
														ondrop="return false;"> <label class="error"
														id="errorInsuranceNO" style="display: none"> </label>
												</div>
											</div>
											
											<c:if test="${configuration.showRfidNumber}">
											<div class="row1">
												<label class="L-size control-label">RFID CARD NUMBER: </label>
												<div class="I-size">
													<input class="form-text" name="rfidCardNo"
														type="text" maxlength="30" placeholder="eg: 0006696365"
														onkeypress="return IsRfidNO(event);"
														pattern=".{0, 30}" title="Four or more characters"
														ondrop="return false;"> <label class="error"
														id="errorRfidCardNo" style="display: none"> </label>
												</div>
											</div>
											</c:if>

										</fieldset>

									</div>
								</div>
								<!-- Subscribe details details -->
								<div id="subscribedetails" class="tab-content">
									<div class="form-horizontal ">
										<fieldset>
											<legend id="Class">Subscribe Details</legend>

											<div class="row1">
												<label class="L-size control-label">Subscribe :</label>
												<div class="I-size">
													<select class="select2 required form-text"
														style="width: 100%;" name="subscribe" multiple="multiple">
														<option value="SERVICE_REMINDER">SERVICE_REMINDER</option>
														<option value="RENEWAL_REMINDER">RENEWAL_REMINDER</option>
														<option value="DRIVER_REMINDER">DRIVER_REMINDER</option>
													</select> <label class="error" id="errorEmployeeID"
														style="display: none"> </label>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<fieldset class="form-actions">
									<div class="col-md-offset-1 col-md-5">
										<button type="submit" class="btn btn-success">Create
											User</button>
										<a class="btn btn-info"
											href="<c:url value="/newUserList/1.in"/>"><span
											id="Cancel">Cancel</span></a>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabs.js" />"></script>

	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/pwstrength.js" />"></script>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/UserValidate.js" />"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('form').submit(function(event) {
								register(event);
							});

							$(":password")
									.keyup(
											function() {
												if ($("#password").val() != $(
														"#matchPassword").val()) {
													$("#globalError")
															.show()
															.html(
																	'<spring:message code="PasswordMatches.user"></spring:message>');
												} else {
													$("#globalError").html("")
															.hide();
												}
											});

							options = {
								common : {
									minChar : 8
								},
								ui : {
									showVerdictsInsideProgressBar : true,
									showErrors : true,
									errorMessages : {
										wordLength : '<spring:message code="error.wordLength"/>',
										wordNotEmail : '<spring:message code="error.wordNotEmail"/>',
										wordSequences : '<spring:message code="error.wordSequences"/>',
										//wordLowercase : '<spring:message code="error.wordLowercase"/>',
										//wordUppercase : '<spring:message code="error.wordUppercase"/>',
									    wordOneNumber : '<spring:message code="error.wordOneNumber"/>',
										//wordOneSpecialChar : '<spring:message code="error.wordOneSpecialChar"/>'
									}
								}
							};
							$('#password').pwstrength(options);
						});

		function register(event) {
			event.preventDefault();
			$(".alert").html("").hide();
			$(".error-list").html("");
			if ($("#password").val() != $("#matchPassword").val()) {
				$("#globalError")
						.show()
						.html(
								'<spring:message code="PasswordMatches.user"></spring:message>');
				return;
			} else if ($("#firstName").val() == "") {
				$("#firstNameError")
						.show()
						.html(
								'<spring:message code="message.username"></spring:message>');
				return;
			} else if ($("#lastName").val() == "") {
				$("#lastNameError")
						.show()
						.html(
								'<spring:message code="message.username"></spring:message>');
				return;
			} else if ($("#userDOB").val() == "") {
				$("#DateBirthError")
						.show()
						.html(
								'<spring:message code="message.dateofbirth"></spring:message>');
				return;
			} else if ($("#userMobile").val() == "") {
				$("#errorMobileNo")
						.show()
						.html(
								'<spring:message code="message.mobile"></spring:message>');
				return;
			}
			var formData = $('form').serialize();
			showLayer();
			$
					.post(
							"<c:url value="/user/registrationFleetop"/>",
							formData,
							function(data) {
								if (data.message == "success") {
									window.location.href = '<c:url value="/newUserList/1.html?success=true"></c:url>';
								} else if (data.message == "already") {
									window.location.href = '<c:url value="/addUser.html?already=true"></c:url>';

								}

							})
					.fail(
							function(data) {
								if (data.responseJSON.error
										.indexOf("MailError") > -1) {
									window.location.href = "<c:url value="/emailError.html"></c:url>";
								} else if (data.message == "UserAlreadyExist") {
									$("#emailError")
											.show()
											.html(
													'<spring:message code="message.badEmail"></spring:message>');
								} else if (data.responseJSON.error
										.indexOf("InternalError") > -1) {
									window.location.href = "<c:url value="/login.html"></c:url>"
											+ "?message="
											+ data.responseJSON.message;
								} else {
									$("#emailError")
											.show()
											.html(
													'<spring:message code="message.badEmail"></spring:message>');
									var errors = $
											.parseJSON(data.responseJSON.message);
									$.each(errors, function(index, item) {
										$("#" + item.field + "Error").show()
												.html(item.defaultMessage);
									});
									errors = $
											.parseJSON(data.responseJSON.error);
									$.each(errors, function(index, item) {
										$("#globalError").show().append(
												item.defaultMessage + "<br>");
									});
								}
							});
		}
	</script>
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

	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>