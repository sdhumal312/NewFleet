<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<style>
.driverheader {
	width: 100%;
	margin: 0 auto;
}

.driverheader h3 {
	font-size: 15px;
	font-weight: 700;
	text-align: center;
	padding: 10px;
	margin: 0 auto;
}

.driverheader .table>tbody>tr>th {
	float: left;
	width: 35%;
	font-size: 13px;
	text-align: right;
}

.driverheader .table>tbody>tr>td {
	float: left;
	width: 60%;
	font-size: 13px;
	text-align: -webkit-auto;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/driver/${driver.driJobId}/${SelectPage}"/>">${SelectJob}</a>
					/ <span><c:out value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" />
							 <c:out value="- ${driver.driver_fathername}" />
							</span>
				</div>
				<div class="pull-right">
					<c:choose>
						<c:when test="${driver.driver_active != 'TRIPROUTE'}">
							<sec:authorize access="hasAuthority('EDIT_DRIVER')">
								<a class="btn btn-success btn-sm"
									href="<c:url value="/editDriver.in?driver_id=${driver.driver_id}"/>">
									<i class="fa fa-pencil"></i> Edit Driver
								</a>
							</sec:authorize>
							<sec:authorize access="hasAuthority('DELETE_DRIVER')">
								<a
									href="<c:url value="/deleteDriver.in?driver_id=${driver.driver_id}"/>"
									class="btn btn-info btn-sm" class="confirmation"
									onclick="return confirm('Are you sure? Delete ')"> <span
									class="fa fa-trash"></span> Delete
								</a>
							</sec:authorize>
						</c:when>
					</c:choose>
					<sec:authorize access="hasAuthority('PRINT_DRIVER')">
						<a href="<c:url value="/PrintDriver?id=${driver.driver_id}"/>"
							target="_blank" class="btn btn-default btn-sm"><i
							class="fa fa-print"></i> Print</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content-body">
		<c:if test="${param.saveDriver eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver was successfully Created.
			</div>
		</c:if>
		<c:if test="${NoRecordFound}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				No Record Found! OR You Don't Have Permission TO View !
			</div>
		</c:if>
		<c:if test="${param.update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Updated successfully .
			</div>
		</c:if>
		<c:if test="${param.success eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Advance Created successfully .
			</div>
		</c:if>
		<c:if test="${param.uploadDriver eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver was successfully Updated.
			</div>
		</c:if>
		<c:if test="${param.alreadyDriver eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver was Already created.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="col-md-9 col-sm-8 col-xs-12">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<div class="col-md-6 col-sm-6 col-xs-6">
									<h2>
										<a
											href="<c:url value="/${SelectJob}/${SelectPage}/showDriver.in?driver_id=${driver.driver_id}"/>">
											<c:out value="${driver.driver_empnumber}  " /> <c:out
												value="${driver.driver_firstname} " />
												 <c:out value="${driver.driver_Lastname}" />
												 <c:out value="- ${driver.driver_fathername}" />
										</a>
									</h2>
									<p>
										<strong>Job: </strong>
										<c:out value="${driver.driver_jobtitle}  " />
										<strong> Status: </strong><span class="label label-success"><c:out
												value="${driver.driver_active}" /></span>
										<c:if test="${configuration.addStatusChangeRemark}">
											<a href="javascript:void(0)"
												class="p-3 mb-2 bg-warning text-dark"
												onclick="getStatusChangeHistory(${driver.driver_id},1)">(Status
												History)</a>
												</c:if>
									</p>
									<p>
										<strong>DL No: </strong>
										<c:out value="${driver.driver_dlnumber} " />

										<strong> Badge No: </strong>
										<c:out value="${driver.driver_badgenumber}" />
									</p>
									<p>
										<strong>License state: </strong>
										<c:out value="${driver.driver_dlprovince} " />
									</p>
									<%-- <p>
										<strong>Phone :</strong>
										<c:out value="${driver.driver_mobnumber} , " />
										<c:out value="${driver.driver_homenumber}" />
									</p> --%>
									<c:if test="${configuration.showOtherTrainning }">
										<p>
											<strong>Training Skills: </strong>
											<c:out value="${driver.driver_trainings}" />
										</p>
									</c:if>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-4 text-center">
									<figure>
										<c:choose>
											<c:when test="${driver.driver_photoid != null}">
												<a
													href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
													class="zoom" data-title="Driver Photo" data-footer=""
													data-type="image" data-toggle="lightbox"> <img
													src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
													class="img-rounded" alt="Driver Profile" width="220"
													height="220" />
												</a>
											</c:when>
											<c:otherwise>
												<img src="resources/images/User-Icon.png"
													alt="Driver Profile" class="img-rounded" width="220"
													height="220" align="left" />
											</c:otherwise>
										</c:choose>
									</figure>
								</div>
							</div>
							<div class="row divider text-center">
								<div class="col-md-2 col-sm-2 col-xs-2 emphasis">
									<h2>
										<c:choose>
											<c:when test="${driver.tripSheetID != 0 && tripSheetNumber != null}">
												<strong><a
													href="<c:url value="/getTripsheetDetails.in?tripSheetID=${driver.tripSheetID}"/>"><c:out
															value="TS-${tripSheetNumber}" /></a></strong>
											</c:when>
											<c:otherwise>

												<strong>-</strong>
											</c:otherwise>
										</c:choose>

									</h2>
									<p>
										<small>Current Trip</small>
									</p>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-2 emphasis">
									<h2>
										<strong><a href ="javascript:void(0)" onclick="getDriverWiseCommentsList(${driver.driver_id})">${LastComment}</a></strong>
									</h2>
									<p>
										<small>Last 3 months Comments</small>
									</p>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-2 emphasis">
									<h2>
										<strong><a href="javascript:void(0)" onclick="getDriverWiseIssuesList(${driver.driver_id})"> ${LastIssues}</a></strong>
										
									</h2>
									<p>
										<small>Last 3 months Issues</small>
									</p>
								</div>
								<div class="col-md-3 col-sm-2 col-xs-2  emphasis">
									<h2>
										<strong> <a href="#" onclick="getDriverWiseFuelMileage(${driver.driver_id})">
										${LastFuelmileage}
										</a>
										</strong>
									</h2>
									<p>
										<small>Last 3 months Fuel mileage</small>
									</p>
								</div>
							</div>
						</div>
					</div>
					<div class="box">
						<div class="box-body">
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="driverheader">
									<h3 class="box-title" >Driver Personal Details</h3>
									<table class="table">
										<tbody>
											<tr class="row">
												<th>Name :</th>
												<td><c:out value="${driver.driver_firstname}" /> <c:out
														value="${driver.driver_Lastname}" /></td>
											</tr>
											<tr class="row">
												<th>Father Name :</th>
												<td><c:out value="${driver.driver_fathername}" /></td>
											</tr>
											<tr class="row">
												<th>Date of Birth :</th>
												<td><c:out value="${driver.driver_dateofbirth}" /> <!-- <div class="text-muted">
													<small>3 years old</small>
												</div> --></td>
											</tr>
											<c:if test="${configuration.showQualification }">
											<tr class="row">
												<th>Qualification :</th>
												<td><c:out value="${driver.driver_Qualification}" /></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showBloodGroup }">
											<tr class="row">
												<th>Blood Group :</th>
												<td><c:out value="${driver.driver_bloodgroup}" /></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showLanguage }">
											<tr class="row">
												<th>Languages :</th>
												<td><c:out value="${driver.driver_languages}" /></td>
											</tr>
											</c:if>
											<tr class="row">
												<th>Phone :</th>
												<td>
													<a href="tel:<c:out value="${driver.driver_mobnumber}"/>">
														<c:out value="${driver.driver_mobnumber}" />
													</a> <span class="label label-default">mobile</span>
													<div class="t-padded">
														<a href="tel:<c:out value="${driver.driver_homenumber}"/>">
															<c:out value="${driver.driver_homenumber}" />
														</a> <span class="label label-default">home</span>
													</div>
												</td>
											</tr>
											<tr class="row">
												<th>Address :</th>
												<td>
													<address>
														<c:out value="${driver.driver_address}" />
														,<br>
														<c:out value="${driver.driver_address2}" />
														<br>
														<c:out value="${driver.driver_city}" />
														,
														<c:out value="${driver.driver_state}" />
														<br>
														<c:out value="${driver.driver_country}" />
														-Pin :
														<c:out value="${driver.driver_pincode}" />
													</address>
												</td>
											</tr>
											<c:if test="${configuration.showDriverEmail}">
												<tr class="row">
													<th>Email :</th>
													<td><a
														href="mailto:<c:out value="${driver.driver_email}"/>">
															<c:out value="${driver.driver_email}" />
													</a></td>
												</tr>
											</c:if>
											<tr class="row">
												<th>Reference Name :</th>
												<td><c:out value="${driver.driver_reffristname}" /> <c:out
														value="${driver.driver_reflastname}" /></td>
											</tr>
											<tr class="row">
												<th>Contact Number :</th>
												<td><c:out value="${driver.driver_refcontect}" /></td>
											</tr>
											<tr class="row">
												<th>Vehicle :</th>
												<td><c:out value="${driver.vehicle_registration}" /></td>
											</tr>
											<tr class="row">
												<th>Is Salaried :</th>
												<td><c:out value="${driver.salariedStr}" /></td>
											</tr>
											<tr class="row">
												<th>Remark :</th>
												<td><c:out value="${driver.remark}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="col-md-5 col-sm-5 col-xs-5 text-center">
								<div class="driverheader">
									<h3>Driver Renewal Reminder</h3>
									<table class="table">
										<tbody>
											<c:if test="${!empty driverReminder}">
												<c:forEach items="${driverReminder}" var="driverReminder">
													<tr class="row">
														<th><c:out
																value="${driverReminder.driver_remindertype}  - " /> <c:out
																value="${driverReminder.driver_dlnumber}" /></th>


														<td class="icon"><c:out
																value="${driverReminder.driver_dlto_show}" /><font
															color="#FF6666"> ( <c:out
																	value="${driverReminder.driver_dueDifference}" /> )
														</font>
															<ul class="list-inline no-margin">
																<li><font color="#999999"> Due soon on <c:out
																			value="${driverReminder.driver_renewaldate}" />
																</font></li>

															</ul></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									<h3>Company Details</h3>
									<table class="table">
										<tbody>
											<tr class="row">
												<th>Employee Number :</th>
												<td><c:out value="${driver.driver_empnumber}" /></td>
											</tr>
											<tr class="row">
												<th>Basic Salary :</th>
												<td><c:out value="${driver.driver_perdaySalary}" /></td>
											</tr>
											<c:if test="${configuration.showESINumber}">
											<tr class="row">
												<th>ESI Percentage :</th>
												<td><c:out value="${driver.driver_esiamount}" /></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showPFNumber}">
											<tr class="row">
												<th>PF Percentage :</th>
												<td><c:out value="${driver.driver_pfamount}" /></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showInsuranceNumber}">
											<tr class="row">
												<th>Insurance Number :</th>
												<td><c:out value="${driver.driver_insuranceno}" /></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showESINumber}">
											<tr class="row">
												<th>ESI Number :</th>
												<td><c:out value="${driver.driver_esino}" /></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showPFNumber}">
											<tr class="row">
												<th>PF Number :</th>
												<td><c:out value="${driver.driver_pfno}" /></td>
											</tr>
											</c:if>
											<tr class="row">
												<th>Start Date :</th>
												<td><c:out value="${driver.driver_startdate}" />
													<div class="text-muted">
														<!-- <small>Yesterday</small> -->
													</div></td>
											</tr>
											<tr class="row">
												<th>Leave Date :</th>
												<td><c:out value="${driver.driver_leavedate}" /></td>
											</tr>

											<tr class="row">
												<th>Aadhar Number :</th>
												<td><c:out value="${driver.driver_aadharnumber}" /></td>
											</tr>
											<c:if test="${configuration.showBankName}">
												<tr class="row">
													<th>Bank Name :</th>
													<td><c:out value="${driver.driver_bankname}" /></td>
												</tr>
											</c:if>
											<c:if test="${configuration.showBankAccount}">
												<tr class="row">
													<th>Bank A/C Number :</th>
													<td><c:out value="${driver.driver_banknumber}" /></td>
												</tr>
											</c:if>
											<c:if test="${configuration.showIFSCCode}">
												<tr class="row">
													<th>Bank IFSC Number :</th>
													<td><c:out value="${driver.driver_bankifsc}" /></td>
												</tr>
											</c:if>
											<tr class="row">
												<th>PAN Number :</th>
												<td><c:out value="${driver.driver_pannumber}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-1 col-md-2  col-xs-12">
					<%@include file="DriverSideMenu.jsp"%>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${driver.createdBy}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${driver.created}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${driver.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${driver.lastupdated}" /></small>
			</div>
		</sec:authorize>
		<div class="modal fade" id="statusChangeDetails" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">Status Change Details :</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="taskTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
				</div>
						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
			<div class="modal fade" id="showDetails" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">Last 3 months  <span id="headerName"></span>:</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="detailsTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
				</div>
						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
	</section>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabs.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/showDriver.js" />"></script>
