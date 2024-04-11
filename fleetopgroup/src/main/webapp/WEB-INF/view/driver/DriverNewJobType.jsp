<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('IMPORT_DRIVER')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/2.in">
							<i class="fa fa-download"></i>
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IMPORT_DRIVER')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<c:if test="${configuration.driverRenewalReceipt}">					
						<a class="btn btn-success btn-sm" href="#"
							onclick="renewalReceipt();"> <span class="fa fa-plus"></span>
							Driver renewal receipt
						</a>
						</c:if>
					<sec:authorize access="hasAuthority('ADD_DRIVER')">
						<c:if test="${!configuration.addDriverDetailsAjax}">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addDriver.in"/>"> <span
								class="fa fa-plus"></span> Add Driver
							</a>
						</c:if>
						<c:if test="${configuration.addDriverDetailsAjax}">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addDriverDetails.in"/>"> <span
								class="fa fa-plus"></span> Add Driver
							</a>
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_DRIVER_BATA')">
						<a class="btn btn-warning btn-sm"
						href="<c:url value="/DriverHaltNew?driverId=${driver.driver_id}"/>"> <span
							class="fa fa-plus"></span> Add Halt Beta
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_DRIVER')">

						<a class="btn btn-info btn-sm"
							href="<c:url value="/DriverReport"/>"> <span
							class="fa fa-search"></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_ALL_DRIVER_SALARY')">

						<a class="btn btn-info btn-sm"
							href="<c:url value="/EditDriverSalaryForGroup"/>"> <span
							class="fa fa-pencil"></span> Edit Salary Details
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_CHECKING_ENTRY')">

						<a class="btn btn-success btn-sm"
							href="<c:url value="/CheckingEntry/1.in"/>"> <span
							class="fa fa-plus"></span> Checking Entry
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- <form method="post" action="some_page" class="inline">
  <input type="hidden" name="extra_submit_param" value="extra_submit_value">
  <button type="submit" name="submit_param" value="submit_value" class="link-button">
    This is a link that sends a POST request
  </button>
</form> -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-user"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectDriverJob}</span> <input
								type="hidden" value="${SelectDriverJob}" id="statues"> <span
								class="info-box-number">${DriverCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Today's DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/TodayDLRenewal.in"/>">${TodayDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				<!--  changes-->
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Tomorrow DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/TomorrowDLRenewal.in"/>">${TomorrowDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				<!--  7 days -->
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Next 7 Days DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/NextSevenDLRenewal.in"/>">${NextSevenDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				
				<!--  -->
				
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Next 15 Days DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/NextFifteenDLRenewal.in"/>">${NextFifteenDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				<!--  next month -->
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Next Month DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/NextMonthDLRenewal.in"/>">${NextMonthDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Driver</span>
							<form action="<c:url value="/searchDriver.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" name="driver_firstname" type="text"
										pattern=".{3,}" required title="3 characters minimum"
										required="required" placeholder="EMP-NO, Driver Name">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>

		<c:if test="${param.InAnotherTrip eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				${InAnother} Please Check..
			</div>
		</c:if>

		<c:if test="${param.sequenceNotFound eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Sequence Not Found Please Contact To System Administrator !
			</div>
		</c:if>

		<c:if test="${param.alreadyDriver eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver was Already created.
			</div>
		</c:if>
		<c:if test="${param.salaryUpdateError eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Some Error Occured Salary Details Not Updated ! .
			</div>
		</c:if>
		<c:if test="${param.salaryUpdated eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Salary updated successfully !.
			</div>
		</c:if>

		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addImport" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="post" action="<c:url value="/importDriver.in"/>"
						enctype="multipart/form-data">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h3 class="panel-title">Import Driver CSV File</h3>
							</div>
							<div class="panel-body">
								<div class="form-horizontal">
									<br>
									<div class="row1">
										<div class="L-size">
											<label> Import Only CSV File: </label>
										</div>
										<div class="I-size">
											<input type="file" accept=".csv" name="import"
												required="required" />
										</div>
									</div>
								</div>
								<div class="row1 progress-container">
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: 0%">Upload Your Driver Entries Please
											wait..</div>
									</div>
								</div>
								<div class="modal-footer">
									<input class="btn btn-success"
										onclick="this.style.visibility = 'hidden'" name="commit"
										type="submit" value="Import Driver files" id="myButton"
										data-loading-text="Loading..." class="btn btn-primary"
										autocomplete="off" id="js-upload-submit" value="Add Document"
										data-toggle="modal" data-target="#processing-modal">
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
								<script>
									$('#myButton')
											.on(
													'click',
													function() {
														//alert("hi da")
														$(".progress-bar")
																.animate(
																		{
																			width : "100%"
																		}, 2500);
														var $btn = $(this)
																.button(
																		'loading')
														// business logic...

														$btn.button('reset')
													})
								</script>

							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="RenewalReminder" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Renewal Date</h4>
					</div>
					<form action="<c:url value="/DriverR.in"/>" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="RRDate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
			<div class="modal fade" id="renewalReceipt" role="dialog">
			<div class="modal-dialog modal-xl">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Driver renewal receipt</h4>
					</div>
						<div class="modal-body">
							<div class="row">
											<label class="L-size control-label">Driver
												Name </label>
											<div class="I-size">
													<input type="hidden" id="driverList1"
														name="driverList1" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more Driver Name" />
											</div>
										</div>
										<br>
									<div class="row">
															<label class="L-size control-label">Renewal Type
																:<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<select class="form-text" name="driverRenewalTypeId"
																	id="driverRenewalTypeId">

																	<c:forEach items="${driverDocType}" var="driverDocType">
																		<option value="${driverDocType.dri_id}"><c:out
																				value="${driverDocType.dri_DocType}" /></option>
																	</c:forEach>
																</select>
															</div>
														</div>
														<br>
										 	<div class="row">
											<label class="L-size control-label">Application no.
												:
											</label>
											<div class="I-size">
												<input class="form-text" id="applicationNo" maxlength="50"
													name="applicationNo" type="text"
													>
											</div>
										</div>
										<br>
							<div class="row">
											<label class="L-size control-label" >Receipt no.
												:
											</label>
											<div class="I-size">
												<input class="form-text" id="receiptNo" maxlength="50"
													name="receiptNo" type="text"
													>
											</div>
										</div>
										<br>
								<div class="row">
											<label class="L-size control-label" >Receipt Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="fuel_date" readonly="readonly"
														id="fuelDate" required="required" 
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<br>
										<div class="row">
										<label class="L-size control-label">Receipt Upload</label>
										<div class="I-size">
										<input type="file" id="fileUpload" required="required"/>
										</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success" onclick="addRenewalReceipt();">Add receipt</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="main-body">
					<div class="box">

						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<c:if test="${!empty driverJobType}">
										<c:forEach items="${driverJobType}" var="driverJobType">
											<li role="presentation" id="${driverJobType.driJobType}"><a
												href="<c:url value="/driver/${driverJobType.driJobId}/1.in"/>">${driverJobType.driJobType}</a></li>
										</c:forEach>
									</c:if>
									<li role="presentation" id="overDueClr">
									<a class="" onclick="showList(0,1)" style="color: red;">OverDue ${totalOverDueCount}
										 </a></li>
									
									<li role="presentation" id="dueSoonClr">
									<a onclick="showList(1,2)"  style="color: #e08e0b;">DueSoon ${totalDueSoonCount}</a></li>
									
									<c:if test="${configuration.driverRenewalReceipt}">	
									<li role="presentation" id="renewalReceiptClr">
									<a onclick="showList(1,3)"  style="color: #e08e0b;">Renewal Receipt <span id="renewalReceiptCount" >${DriverrenewalReminderCount}</span></a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
	<div class="row overAndDue hide" id="tabsData">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<input type="hidden" id="startPage" value="${SelectPage}"> 
							<table id="viewOverAndDueData" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
				<div class="text-center">
					<ul id="navigationBar" class="pagination pagination-lg pager">
					</ul>
				</div>
			</div>
		</div>
					<div class="box driverData">
						<div class="box-body">
							<div class="table-responsive">
								<table id="DriverTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit ar">EMP-NO</th>
											<th>Name</th>
											<th class="fit ar">DL-No</th>
											<th class="fit ar">Badge-No</th>
											<th class="fit ar">Phone</th>
											<th class="fit ar">Group</th>
											<!-- <th class="fit ar">Job</th> -->
											<th class="fit ar">Current Trip</th>
											<th class="fit ar">Status</th>
											<th class="actions" class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty drivers}">
											<c:forEach items="${drivers}" var="driver">
												<tr data-object-id="" class="ng-scope">
													<td class="fit ar"><a
														href="<c:url value="/${SelectJob}/${SelectPage}/showDriver.in?driver_id=${driver.driver_id}"/>"
														data-toggle="tip" data-original-title="Click driver">
															<c:out value="${driver.driver_empnumber}" />

													</a></td>
													<td><a
														href="<c:url value="/${SelectJob}/${SelectPage}/showDriver.in?driver_id=${driver.driver_id}"/>"
														data-toggle="tip"
														data-original-title="Click this driver Details"> <c:out
																value="${driver.driverFullName}   " /> 
													</a></td>
													<td class="fit ar"><c:out
															value="${driver.driver_dlnumber}" /></td>
													<td class="fit ar"><c:out
															value="${driver.driver_badgenumber}" /></td>
													<td class="fit ar"><span class="fa fa-phone"
														aria-hidden="true" data-toggle="tipDown"
														data-original-title="Phone Number"> </span> <c:out
															value="${driver.driver_mobnumber}" />
													<td class="fit ar"><c:out
															value="${driver.driver_group}" /></td>
													<%-- <td class="fit ar"><a href="#"><c:out
																value="${driver.driver_jobtitle}" /></a></td> --%>
													<td class="fit ar"><c:choose>
															<c:when
																test="${driver.tripSheetID != 0 && driver.tripSheetNumber != null}">
																<a
																	href="<c:url value="/getTripsheetDetails.in?tripSheetID=${driver.tripSheetID}"/>"><c:out
																		value="TS-${driver.tripSheetNumber}" /></a>
															</c:when>
														</c:choose></td>
													<td class="fit ar"><c:out
															value="${driver.driver_active}" /></td>

													<td class="fit ar">
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"><span
																class="fa fa-ellipsis-v"></span> </a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																		access="hasAuthority('EDIT_DRIVER')">
																		<c:if test="${driver.driver_active == 'ACTIVE'}">
																			<a
																			href="<c:url value="/editDriver.in?driver_id=${driver.driver_id}"/>">
																			<span class="fa fa-pencil"></span> Edit
																		</a>
																		</c:if>
																		</sec:authorize>
																		<sec:authorize
																		access="hasAuthority('INACTIVE_DRIVER_EDIT')">
																		<c:if test="${driver.driver_active == 'INACTIVE'}">
																			<a
																			href="<c:url value="/editDriver.in?driver_id=${driver.driver_id}"/>">
																			<span class="fa fa-pencil"></span> Edit
																		</a>
																		</c:if>
																		</sec:authorize>
																		
																	</li>
																<li><sec:authorize
																		access="hasAuthority('DELETE_DRIVER')">
																		<c:if test="${driver.driver_active != 'SUSPEND'}">
																			<a
																				href="<c:url value="/deleteDriver.in?driver_id=${driver.driver_id}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</c:if>
																	</sec:authorize></li>
															</ul>
														</div>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				<div class="text-center driverData">
					<c:url var="firstUrl" value="/driver/${SelectJob}/1" />
					<c:url var="lastUrl"
						value="/driver/${SelectJob}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl"
						value="/driver/${SelectJob}/${currentIndex - 1}" />
					<c:url var="nextUrl"
						value="/driver/${SelectJob}/${currentIndex + 1}" />
				
						<ul class="pagination pagination-lg pager">
							<c:choose>
								<c:when test="${currentIndex == 1}">
									<li class="disabled"><a href="#">&lt;&lt;</a></li>
									<li class="disabled"><a href="#">&lt;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${firstUrl}">&lt;&lt;</a></li>
									<li><a href="${prevUrl}">&lt;</a></li>
								</c:otherwise>
							</c:choose>
							<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
								<c:url var="pageUrl" value="/driver/${SelectJob}/${i}" />
								<c:choose>
									<c:when test="${i == currentIndex}">
										<li class="active"><a href="${pageUrl}"><c:out
													value="${i}" /></a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${currentIndex == deploymentLog.totalPages}">
									<li class="disabled"><a href="#">&gt;</a></li>
									<li class="disabled"><a href="#">&gt;&gt;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${nextUrl}">&gt;</a></li>
									<li><a href="${lastUrl}">&gt;&gt;</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/driverOverAndDue.js" />"></script>
		
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
		
		<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<!-- /.content -->
	<script type="text/javascript">
		$(document).ready(function() {
			$(".nav-tabs a").click(function() {
				$(this).button("loading").delay(500).queue(function() {
					$(this).button("reset"), $(this).dequeue()
				})
			})
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
	</script>

</div>