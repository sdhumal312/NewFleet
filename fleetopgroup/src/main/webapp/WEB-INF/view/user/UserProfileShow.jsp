<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap2-toggle.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a>
					<sec:authorize access="hasAuthority('VIEW_USER')"> / <a
							href="<c:url value="/newUserList/1.in"/>">User List</a>
					</sec:authorize>
					/ <a> User Profile</a>
				</div>
				<div class="pull-right">

					<sec:authorize access="hasAuthority('ADD_USER')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#myModalPhoto"> <i class="fa fa-plus"></i> Add
							Photo
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_USER')">
						<a class="btn btn-info"
							href="<c:url value="/editUserProfile?id=${userprofile.user_id}"/>">
							<span class="fa fa-pencil"></span> Edit Profile
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_USER')">
						<a href="PrintUserProfile?id=${userprofile.user_id}"
							target="_blank" class="btn btn-default"><i
							class="fa fa-print"></i> Print</a>
					</sec:authorize>
					<a class="btn btn-link" href="<c:url value="/newUserList/1.in"/>">Cancel
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveUserPhoto eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Photo Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.linkSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Linked Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.updateUserPhoto eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Photo Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.emptyDocument eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Photo is empty.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_USER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_USER')">
			<div class="row">
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="box">
						<div class="box-body">

							<div class="col-md-9 col-sm-10 col-xs-12">
								<c:choose>
									<c:when test="${userprofile.photo_id != null}">
										<a
											href="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
											class="zoom" data-title="User Profile"
											data-footer="${userprofile.firstName}" data-type="image"
											data-toggle="lightbox"> <img
											src="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
											class="img-circle"
											alt="<c:out value="${userprofile.firstName} " />" width="200"
											height="200" align="left" />
										</a>
									</c:when>
									<c:otherwise>
										<img src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/User-Icon.png" />" alt="User Profile"
											class="img-circle img-responsive" width="200" height="200"
											align="left" />
									</c:otherwise>
								</c:choose>


							</div>
							<div class="col-md-9 col-sm-9 col-xs-12">
								<input type="hidden" name="userprofile_id" id="userprofileId"
													value="${userprofile.userprofile_id}" /> 
													<input type="hidden" id="mobileNumber" value="${userprofile.mobile_number}">
													<input type="hidden" id="email" value="${userprofile.personal_email}">
													<input type="hidden" name="user_id" id="user_id" value="${userprofile.user_id}" />
													<input type="hidden" name="authenticationDetailsId" id="authenticationDetailsId"  />
								<h3>
									<c:out value="${userprofile.firstName} " />
									<c:out value="${userprofile.lastName}" />
								</h3>
								<small><cite
									title="${userprofile.city},  ${userprofile.state}, ${userprofile.country}">${userprofile.city}
										${userprofile.state}, ${userprofile.country} <i
										class="fa fa-map-marker"> </i>
								</cite></small>
								<p>
									<i class="fa fa-envelope"></i>
									<c:out value=" ${userprofile.user_email}"></c:out>
									<br /> <i class="fa fa-globe"></i><a> <c:out
											value=" ${userprofile.personal_email}"></c:out></a> <br /> <i
										class="fa fa-gift"></i>
									<c:out value=" ${userprofile.dateofbirth}"></c:out>
								</p>
							</div>
							<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="hidden" id="companyId" value="${companyId}">
								<fieldset>
									<legend>Company </legend>
									<br>
									<h5>
										Company :<a><c:out value=" ${userprofile.company_name}" /></a>
									</h5>
									<h5>
										Branch :<a><c:out value=" ${userprofile.branch_name}" /></a>
									</h5>
									<h5>
										Depart. :
										<c:out value=" ${userprofile.department_name}" />
									</h5>
									<h5>
										Design. :
										<c:out value="${userprofile.designation}" />
									</h5>
									<c:if test="${userConfig.showRfidNumber}">
										<h5>
											RFID CARD NO:<c:out value=" ${userprofile.rfidCardNo}" />
										</h5>
									</c:if>

								</fieldset>
							</div>
							<div class="col-md-9 col-sm-9 col-xs-12">
								<fieldset>
									<legend>Role </legend>
									<br>
									<h5>
										<c:if test="${!empty roles}">
											<c:forEach items="${roles}" var="roles">
												<h5>
													<c:out value="${roles}" />
													<br>
												</h5>
											</c:forEach>
										</c:if>
									</h5>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<div class="box">
						<div class="box-body">
							<fieldset>
								<legend>Personal Details </legend>
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="">Name :</th>
											<td class="" style="width: 2432452px;"><c:out
													value="${userprofile.firstName} " /> <c:out
													value="${userprofile.lastName}" /></td>
										</tr>
										<tr class="row">
											<th class="">Date of Birth :</th>
											<td class=""><c:out value="${userprofile.dateofbirth}" />
												<div class="text-muted">
													<!-- <small>3 years old</small> -->
												</div></td>
										</tr>
										<tr class="row">
											<th class="">Gender :</th>
											<td class="value"><c:out value="${userprofile.sex}" /></td>
										</tr>
										<tr class="row">
											<th class="">Personal Email :</th>
											<td class="value"><c:out
													value="${userprofile.personal_email}" /></td>
										</tr>
										<tr class="row">
											<th class="">Mobile Number :</th>
											<td class="value"><c:out
													value="${userprofile.home_number}" /><br> <c:out
													value="${userprofile.mobile_number}" /><br> <c:out
													value="${userprofile.work_number}" /></td>
										</tr>
										<tr class="row">
											<th class="">Address :</th>
											<td class="value"><address>
													<c:out value="${userprofile.address_line1}" />
													,<br>
													<c:out value="${userprofile.city} " />
													,
													<c:out value="${userprofile.state}" />
													,<br>
													<c:out value="${userprofile.country}" />
													, Pin :
													<c:out value="${userprofile.pincode}" />
													.
												</address></td>
										</tr>
										<tr class="row"></tr>
										<tr class="row">
											<th class="">Emergency Person :</th>
											<td class="value"><c:out
													value="${userprofile.emergency_person}" /></td>
										</tr>
										<tr class="row">
											<th class="">Emergency Number :</th>
											<td class="value"><c:out
													value="${userprofile.emergency_number}" /></td>
										</tr>
								</table>
							</fieldset>
							<fieldset style="margin: 0px 0;">
								<legend>Company Details </legend>
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="">Company Email :</th>
											<td class=""><c:out value="${userprofile.user_email} " /></td>
										</tr>
										<tr class="row">
											<th class="">Employee ID :</th>
											<td class=""><c:out value="${userprofile.employes_id}" />
												<div class="text-muted">
													<!-- <small>3 years old</small> -->
												</div></td>
										</tr>
										<tr class="row">
											<th class="">Working Time :</th>
											<td class="value"><c:out
													value="${userprofile.working_time_from} To " /> <c:out
													value="${userprofile.working_time_to}" /></td>
										</tr>
										<tr class="row">
											<th class="">ESI Number :</th>
											<td class="value"><c:out
													value="${userprofile.esi_number}" /></td>
										</tr>
										<tr class="row">
											<th class="">PF Number :</th>
											<td class="value"><c:out
													value="${userprofile.pf_number}" /></td>
										</tr>
										<tr class="row">
											<th class="">Insurance Number :</th>
											<td class="value"><c:out
													value="${userprofile.insurance_number}" /></td>
										</tr>
										<tr class="row">
											<th class="">Last Login On :</th>
											<td class="value"><c:out
													value="${userprofile.lastLoginDateStr}" /></td>
										</tr>
										<tr class="row">
											<th class="">Last Login IP :</th>
											<td class="value"><c:out
													value="${userprofile.lastLoginIP}" /></td>
										</tr>
								</table>
							</fieldset>
						</div>
					</div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="myModalPhoto" role="dialog">

					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<form method="post" action="uploadUserProfilePhoto.in"
								enctype="multipart/form-data">

								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Upload User Photo</h4>
								</div>
								<div class="modal-body">
								<input type="hidden" name="userprofile_id" id="userprofile_id"
													value="${userprofile.userprofile_id}" /> 
									<div class="form-horizontal">
										<div class="row1">
											<div class="L-size">
												
												<label class="col-md-3">Title/Photo Name</label>
											</div>
											<div class="I-size">
												<input type="text" name="photoname" class="form-text"
													maxlength="25"
													onkeypress="return IsDriverPhotoName(event);"
													required="required" ondrop="return false;"> <label
													class="error" id="errorPhotoName" style="display: none">
												</label>
											</div>
										</div>
										<div class="row">
											<div class="L-size"></div>
											<label class="L-size"></label>
											<div class="I-size">
												<input type="file" name="file" id="file" accept="image/*"
													required="required"></input>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary"
										id="js-upload-submit" value="Add Document">Upload
										Photo</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Connect Vendor Details -->
				<div class="modal fade" id="addVendorType" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<form action="saveUserFuelVendor.in" method="post"
								onsubmit="return validateStatus()">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="VehicleVendor">User Link to
										Fuel Vendor Create Details</h4>
								</div>
								<div class="modal-body">
									<div class="row1">
										<label class="L-size control-label">Vendor Name :</label>
										<div class="I-size">
											<input type="hidden" name="user_id"
												value="${userprofile.user_id}"> <input type="hidden"
												id="selectVendor" name="vendorId" style="width: 100%;"
												value="0" placeholder="Please Select Vendor Name" /> <label
												id="errorvStatus" class="error"></label>
										</div>
										<input type="hidden" value="${userprofile.vendorId}" id="Ovid">
										<input type="hidden" value="${userprofile.vendorName}"
											id="enterVendorName">
									</div>
									<br />
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-success">
										<span id="Save">Connect User</span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Connect VehicleGroup Details -->
				<div class="modal fade" id="addVehicleGroup" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<form action="saveUserVehicleGroup.in" method="post"
								onsubmit="return validateStatus()">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="VehicleVendor">User Link to
										Vehicle Group Details</h4>
								</div>
								<div class="modal-body">
									<div class="row1" id="grpvehicleGroup">
										<label class="L-size control-label">Vehicle Group :<abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" name="user_id"
												value="${userprofile.user_id}"> <select
												id="vehicleGroup" class="select2 required"
												style="width: 100%;" name=group_Permissions
												multiple="multiple">
												<c:forEach items="${vehicleGroup}" var="vehicleGroup">
													<option value="${vehicleGroup.gid}">
														<c:out value="${vehicleGroup.vGroup}" />
													</option>
												</c:forEach>
											</select> <span id="groupPermissionIcon" class=""></span>
											<div id="groupPermissionErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<br />
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-success">
										<span id="Save">Connect User</span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
				<div class="modal fade" id="addTallyCompany" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title">User Link to
										Tally Company</h4>
								</div>
								<div class="modal-body">
									<div class="row1">
										<label class="L-size control-label">Tally Company :<abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" name="user_id" id="userId"
												value="${userprofile.user_id}"/> 
											<select	id="tallyCompany" class="select2 required"
												style="width: 100%;" name="tallyCompany"
												multiple="multiple">
												<c:forEach items="${tallyCompanyList}" var="tallyCompany">
													<option value="${tallyCompany.tallyCompanyId}">
														<c:out value="${tallyCompany.companyName}" />
													</option>
												</c:forEach>
												</select>
										</div>
									</div>
									<br />
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-success">
										<span id="SaveT" onclick="saveTallyCompanyPermission();">Connect User</span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="CloseT"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
						</div>
					</div>
				</div>
				
				<!-- Connect Part Location Details -->
				<div class="modal fade" id="addPartLocation" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<form action="saveUserPartLocationPermission.in" method="post"
								onsubmit="return validateStatus()">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="VehicleVendor">User Link to
										Part Location Details</h4>
								</div>
								<div class="modal-body">
									<div class="row1" id="grpvehicleGroup">
										<label class="L-size control-label">Part Location :<abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" name="user_id"
												value="${userprofile.user_id}"> <select
												id="userPartLocation" class="select2 required"
												style="width: 100%;" name="partLocationPermission"
												multiple="multiple">
												<c:forEach items="${PartLocations}" var="PartLocations">
													<option value="${PartLocations.partlocation_id}">
														<c:out value="${PartLocations.partlocation_name}" />
													</option>
												</c:forEach>
											</select> <span id="groupPermissionIcon" class=""></span>
											<div id="groupPermissionErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<br />
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-success">
										<span id="Save">Connect User</span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
				<!-- Connect Cash Book Details -->
				<div class="modal fade" id="addCashBookPermission" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<form action="saveUserCashBookPermossion.in" method="post"
								onsubmit="return validateStatus()">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="cashBook">User Link to
										CashBook Details</h4>
								</div>
								<div class="modal-body">
									<div class="row1" id="grpUserCashBook">
										<label class="L-size control-label">CashBook Name :<abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
										<input type="hidden" name="user_id"
												value="${userprofile.user_id}"> 
											 <select id="userCashBook" class="select2 required"
												style="width: 100%;" name="cashBookPermission" multiple="multiple">
												<c:forEach items="${cashBook}" var="cashBook">
													<option value="${cashBook.NAMEID}">
														<c:out value="${cashBook.CASHBOOK_NAME}" />
													</option>
												</c:forEach>
											</select> <span id="cashBookPermissionIcon" class=""></span>
											<div id="cashBookPermissionErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<br />
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-success">
										<span id="Save">Connect User</span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
				<div class="modal fade" id="enableTwoFactorAuth" role="dialog">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="cashBook">Enable Two Factor Authentication</h4>
								</div>
								<div class="modal-body">
									<div class="row1" id="grpUserCashBook">
										<label class="L-size control-label">Authentication Required For :<abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<select class="form-text" id="otpRequiredType">
												<option value="1">Every Login</option>
												<option value="2">Once a Day</option>
												<option value="3">Once A Week</option>
												<option value="4">Once A Month</option>
											</select>
										</div>
									</div>
									<br />
									<c:if test="${configuration.OTPSendToType == 2 || configuration.OTPSendToType == 3 }">
										<div class="row" id="grpuserpersonalEmail"
												class="form-group">
												<label class="L-size control-label" for="userpersonalEmail"><span
													id="RegistrationNumber"> Email :</span><abbr
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
									</c:if>
											<br/>
											
											<c:if test="${configuration.OTPSendToType == 1 || configuration.OTPSendToType == 3 }">
												<div class="row" id="grpuserMobile" class="form-group">
													<label class="L-size control-label">Mobile Number: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input class="form-text" id="userMobile"
															name="mobile_number" type="text" maxlength="10"
															placeholder="eg: 9880004300" pattern=".{10,15}"
															title="Ten characters"
															onkeypress="return IsMobileNo(event);"
															ondrop="return false;"><span id="userMobileIcon"
															class=""></span>
														<div id="userMobileErrorMsg" class="text-danger"></div>
														<span id="errorMobileNo"
															class="alert alert-danger col-sm-6" style="display: none"></span>
													</div>
												</div>
											</c:if>
											
											
								</div>
								<div class="modal-footer">
									<button type="submit" onclick="enableTwoFactorValidation();" class="btn btn-success">
										Enable
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
						</div>
					</div>
				</div>
				
				<div class="col-md-2 col-sm-1 col-xs-12">
					<ul class="nav nav-list">
						<li class="active"><sec:authorize
								access="hasAuthority('VIEW_USER')">
								<a href="showUserProfile.in?id=${userprofile.user_id}">Overview</a>
							</sec:authorize></li>

						<li><sec:authorize access="hasAuthority('VIEW_USER')">
								<a
									href="showUserProfileDocument.in?id=${userprofile.userprofile_id}">
									Documents</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('ADD_USER')">
								<a data-toggle="modal" data-target="#addVendorType"
									data-whatever="@mdo"> Connect To Vendor</a>
							</sec:authorize></li>
						<li id="addVehicleGroupPer" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<a data-toggle="modal" data-target="#addVehicleGroup"
									data-whatever="@mdo"> Connect To Group</a>
							</sec:authorize></li>		
						<li id="addTallyCompanyPer" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<a data-toggle="modal" data-target="#addTallyCompany"
									data-whatever="@mdo"> Connect To Tally Company</a>			
							</sec:authorize></li>
						<li id="addCashBook" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<a data-toggle="modal" data-target="#addCashBookPermission"
									data-whatever="@mdo"> Connect To CashBook</a>
							</sec:authorize></li>
						<li id="addPartLocationPer" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<a data-toggle="modal" data-target="#addPartLocation"
									data-whatever="@mdo"> Connect To Part Location</a>
							</sec:authorize></li>
						<li id="enableTwoFactor" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<input id="toggletwofactor" onclick="enableDisableTwoFactor(${userprofile.twoFactorLogin}, ${userprofile.userprofile_id});" type="checkbox" data-toggle="toggle" data-on="Enable Two Factor" data-off="Disable Two Factor">
							</sec:authorize></li>	
							
						<li id="editTwoFactor" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<br/>
							<button type="submit" onclick="editTwoFactorDetails();" class="btn btn-success">
										Edit Two Factor Details
									</button>
							
							</sec:authorize></li>		
					<c:if test="${companyId == 4}">		
						<li id="addPartLocationPer2"><sec:authorize access="hasAuthority('ADD_USER')">
								<a href="#" onclick="releaseBlockedIps();"
									> Release Blocked IP's</a>
							</sec:authorize></li>	
					</c:if>
						<li id="refreshConfiguration">
								<a href="#" onclick="refreshConfiguration();"
									> Refresh Configuration</a>
							</li>	
						<li id="addPartLocationPer" style="display: none;"><sec:authorize access="hasAuthority('ADD_USER')">
								<a data-toggle="modal" data-target="#addPartLocation"
									data-whatever="@mdo"> Connect To Part Location</a>
							</sec:authorize></li>		
					</ul>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${userprofile.createdBy}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${userprofile.created}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${userprofile.lastModifiedBy}" /></small> | <small
					class="text-muted"><b>Last updated date:</b> <c:out
						value="${userprofile.lastupdated}" /></small>
			</div>
		</sec:authorize>

	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			var h = $("#Ovid").val(), i = $("#enterVendorName").val();
			$("#selectVendor").select2("data", {
				id : h,
				text : i
			});
			$('#vehicleGroup').select2().val([${vehicleGroupId}]).trigger("change");
			$('#userCashBook').select2().val([${cashBookIds}]).trigger("change");
			$('#userPartLocation').select2().val([${partLocationIds}]).trigger("change");
			
			if(${cashBookWisePermission}){
				$('#addCashBook').show();
			}else{
				$('#addCashBook').hide();
			} 
			if(${vehicleGroupWisePermission}){
				$('#addVehicleGroupPer').show();
			}else{
				$('#addVehicleGroupPer').hide();
			}
			if(${partLocationWisePermission}){
				$('#addPartLocationPer').show();
			}else{
				$('#addPartLocationPer').hide();
			}
			if(${isOTPValidationAtLogin}){
				$('#enableTwoFactor').show();
			}
			
			if(${tallyCompanyWisePermission}){
				$('#addTallyCompanyPer').show();
				$('#tallyCompany').select2().val([${tallyCompanyIds}]).trigger("change");
			}else{
				$('#addTallyCompanyPer').hide();
			}
			
		});
		function releaseBlockedIps(){
			var jsonObject			= new Object();

			
					$.ajax({
						 url: "releaseBlockedIps",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
						success : function(data) {
							alert('All Blocked IPS Are Released !');
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							debugger;
						}
					});


		}
		
		function refreshConfiguration(){
			var jsonObject			= new Object();
					$.ajax({
						 url: "refreshConfiguration",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
						success : function(data) {
							showMessage('success', 'configuration refreshed successfully !');
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							debugger;
						}
					});


		}
		
		function saveTallyCompanyPermission(){
			var jsonObject			= new Object();
			
			if($('#tallyCompany').val() == null || $('#tallyCompany').val() == ''){
				showMessage('info', 'Please select tally company !');
				return false;
			}
			var tallyArr		= $('#tallyCompany').val();
			var tallyCompanyId  = "";
			
			for(var i=0; i < tallyArr.length; i++){
				if(i+1 < tallyArr.length){
					tallyCompanyId += tallyArr[i]+",";
				}else{
					tallyCompanyId += tallyArr[i];
				}
				
			}
			jsonObject["userId"] 			=  $('#userId').val();
			jsonObject["tallyCompanyId"] 	= tallyCompanyId;
			jsonObject["companyId"] 		=  $('#companyId').val();
			
			 showLayer();
			$.ajax({
				 url: "saveTallyCompanyPermission",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
				success : function(data) {
					showMessage('success', 'Data Saved successfully !');
					location.replace("showUserProfile?id="+data.userId);
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					debugger;
					hideLayer();
				}
			}); 
		}
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrap2-toggle.min.js" />"></script>	
<script src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/twofactor/addTwoFactorAuth.js"></script>
			
<script>
  $(function() {
	  if(${userprofile.twoFactorLogin}){
	 	 $('#toggletwofactor').bootstrapToggle('off')
	 	 $('#editTwoFactor').show();
	  }else{
		  $('#toggletwofactor').bootstrapToggle('on')
	  }
  })
</script>
</div>