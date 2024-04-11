<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchFuelShow.in"/>" method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">F-</span></span>
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="number" required="required" min="1"
									placeholder="ID eg: 1234"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
				<c:if test="${configuration.allowCustomExcelDownload}">
					<sec:authorize access="hasAuthority('IMPORT_FUEL')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLS Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadfuelentrydocument.in">
							<i class="fa fa-download"></i>
						</a>
						
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>

					</sec:authorize>
				</c:if>
				<c:if test="${!configuration.allowCustomExcelDownload}">
					 <sec:authorize access="hasAuthority('IMPORT_FUEL')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/4.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>

					</sec:authorize> 
				</c:if>
					 <sec:authorize access="hasAuthority('ADD_FUEL')">
						 <c:if test="${!configuration.AllowWSFuelEntry}">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addFuel.in"/>"> <span class="fa fa-plus"></span>
								Add Fuel Entry
							</a>
						</c:if>
						<c:if test="${configuration.AllowWSFuelEntry}">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addFuelEntries.in"/>"> <span class="fa fa-plus"></span>
								Add Fuel Entry
							</a>
						</c:if>

					</sec:authorize> 
					
					
					<sec:authorize access="hasAuthority('VIEW_FUEL')">
						<a class="btn btn-info btn-sm" href="<c:url value="/FuelReport"/>">
							<span class="fa fa-search"></span> Search
						</a>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_FUEL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_FUEL')">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-tint"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Fuel Entries</span> <span
								class="info-box-number">${FuelCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Fuel Entries</span>
							<form action="<c:url value="/searchFuel.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="Search" type="text" required="required"
										placeholder="FT_ID, VID, REF-No, Date"> <span
										class="input-group-btn">
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
			</sec:authorize>
		</div>
		<c:if test="${param.failedFuelList != null}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Following Fuel Entry Not Added To Validation Error : ${param.failedFuelList}<br>
			</div>
		</c:if>
		<c:if test="${param.duplicate != null}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Following Fuel Entry Not Added Due To Duplicate Entries : ${param.duplicate}<br>
			</div>
		</c:if>
		<c:if test="${param.closeStatus eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>

				${VMandatory}<br> You should be close first TripSheet or change
				status or close workOrder .
			</div>
		</c:if>
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Fuel Entry Not Available or You don't have access to view this.<br>
			</div>
		</c:if>
		<c:if test="${saveFuel}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data Successfully Created.
			</div>
		</c:if>
		<c:if test="${param.Success eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data Successfully Created.
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Entries Fuel Reference ,Vehicle Name, Vendor Name Already
				created. Or This Fuel Entries Can't be Edit or Delete .. This Fuel
				Entry is Approved or Payment Mode
			</div>
		</c:if>
		<c:if test="${param.Update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data successfully Updated.
			</div>
		</c:if>
		<c:if test="${param.tripAccClosed eq true}">
			<div class="alert alert-info">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Cannot Delete TripSheet A/c Already Closed !.
			</div>
		</c:if>
		<c:if test="${param.FuelSequenceCounterMissing eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Fuel Sequence Counter is Missing For Company.. Please Contact System Administrator !
			</div>
		</c:if>
		<c:if test="${param.delete eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data successfully Deleted.
			</div>
		</c:if>
		<c:if test="${param.duplicateFuelEntries eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel EntriesFuel Reference ,Vehicle Name, Vendor Name Already
				created. (or) This Duplicate Entries Reference Number.
			</div>
		</c:if>
		
		<c:if test="${deleteFuel}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data Successfully Deleted.
			</div>
		</c:if>
		
		<c:if test="${param.ApprovedValidate eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Entries Can't be Edit or Delete .. This Fuel Entry is
				Approved or Payment Mode
			</div>
		</c:if>
		
		<c:if test="${importEmpty}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty Duplicate}">
					<c:forEach items="${Duplicate}" var="Duplicate">
				      ${CountDuplicate} Duplicate entry Please Check First <c:out
							value="${Duplicate}" />
						<br>
					</c:forEach>
				</c:if>
				This CSV Import Fuel File All Row Fields Required.<br>For
				ex:-Vehicle_name, Fuel_date, Vehicle_oddmeter, Fuel_liter,
				Fuel_price, Fuel_tank (Full or Partial), Vendor_name,
				Vendor_location, Fuel_Reference_no <br> Please Fill the
				Required Row Field..
			</div>
		</c:if>
		<c:if test="${importEmptyVehicle}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty DuplicateVehicle}">
					Duplicate Vehicle entry Please Check First Import File <c:out
						value="${DuplicateVehicle}" />
					<br>
				      This CSV Import Fuel File All Row Fields Required.<br>For
				ex:-Vehicle_name, Fuel_date, Vehicle_oddmeter, Fuel_liter,
				Fuel_price, Fuel_tank (Full or Partial), Vendor_name,
				Vendor_location, Fuel_Reference_no <br> Please Fill
				the Required Row Field.. 
				</c:if>
			</div>
		</c:if>
		<c:if test="${OddmeterError}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty Duplicateodameter}">
				 ${CountDuplicateOdameter} Missing OddMeter entry Please Check.. <br>
					Last Less than Reading ${Duplicateodameter}  uploaded. Please check the Import File
				</c:if>
			</div>
		</c:if>
		<c:if test="${dateError}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty dateError}">
				 ${dateError} Date Format is Wrong. Please check the Import File. Make ( DD-MM-YYYY ) Format to all 
				</c:if>
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${updateVendor}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Data successfully Updated.
			</div>
		</c:if>
		<c:if test="${dangerVendor}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor was NOt Deleted.
			</div>
		</c:if>
		<c:if test="${duplicateFuelEntries}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Entries was Already created..(or) This Duplicate Entries
				Reference Number.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addImport" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
				<c:if test="${configuration.allowCustomExcelDownload}">
				<form method="post" action="<c:url value="/importFuelEntry.in"/>"
						enctype="multipart/form-data">
				</c:if>
				<c:if test="${!configuration.allowCustomExcelDownload}">
				<form method="post" action="<c:url value="/importFuel.in"/>"
						enctype="multipart/form-data">
				</c:if>
					
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h3 class="panel-title">Import File</h3>
							</div>
							<div class="panel-body">
								<div class="form-horizontal">
									<br>
									<c:if test="${configuration.allowCustomExcelDownload}">
									<div class="row1">
										<div class="L-size">
											<label> Import Only XLS File: </label>
										</div>
										<div class="I-size">
											<input type="file" accept=".xls" name="import"
												required="required" />
										</div>
									</div>
									</c:if>
									<c:if test="${!configuration.allowCustomExcelDownload}">
									<div class="row1">
										<div class="L-size">
											<label> Import Only CSV File: </label>
										</div>
										<div class="I-size">
											<input type="file" accept=".csv" name="import"
												required="required" />
										</div>
									</div>
									</c:if>
								</div>
								<div class="row1 progress-container">
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: 0%">Upload Your Fuel Entries Please
											wait..</div>
									</div>
								</div>
								<div class="modal-footer">
									<input class="btn btn-success"
										onclick="this.style.visibility = 'hidden'" name="commit"
										type="submit" value="Import Fuel Entries" id="myButton"
										data-loading-text="Loading..." class="btn btn-primary"
										autocomplete="off" id="js-upload-submit" value="Add Document"
										data-toggle="modal" data-target="#processing-modal">
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
								<script>
									$('#myButton').on('click', function() {
										//alert("hi da")
										$(".progress-bar").animate({
											width : "100%"
										}, 2500);
										var $btn = $(this).button('loading')
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
		<div class="row">
		<c:if test="${param.importSave  eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Imported Successfully ${CountSuccess} Fuel Data.
			</div>
		</c:if>
			<div class="main-body">
				<sec:authorize access="!hasAuthority('VIEW_FUEL')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_FUEL')">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="FuelTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th>ID</th>
											<th>Vehicle</th>
											<!-- <th>Ownership</th> -->
											<th>Driver</th>
											<th>Date</th>
											<th>Opens(Km)</th>
											<th>Close(Km)</th>
											<th>Usage</th>
											<th>Volume</th>
											<th>Amount</th>
											<th>FE</th>
											<!-- <th>Cost</th>
											<th>Doc</th> -->
											<th class="actions" class="icon">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty fuel}">
											<c:forEach items="${fuel}" var="fuel">
												<tr data-object-id="" class="ng-scope">
													<td><a
														href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
														data-toggle="tip" data-original-title="Click Fuel Details"><c:out
																value="F-${fuel.fuel_Number}" /></a></td>
													<td><a
														href="<c:url value="/VehicleFuelDetails/1.in?vid=${fuel.vid}"/>"
														data-toggle="tip"
														data-original-title="Click Vehicle Details"><c:out
																value="${fuel.vehicle_registration}" /> </a><br> <c:choose>
															<c:when test="${fuel.fuel_TripsheetID != 0 && fuel.fuel_TripsheetNumber != null}">
																<a target="_blank"
																	href="<c:url value="/getTripsheetDetails.in?tripSheetID=${fuel.fuel_TripsheetID}"/>"
																	data-toggle="tip"
																	data-original-title="Click Tripsheet Details"><c:out
																		value="TS-${fuel.fuel_TripsheetNumber}" /> </a>
															</c:when>
															<%-- <c:otherwise>
																<c:out value="TS-${fuel.fuel_TripsheetNumber}" />
															</c:otherwise> --%>
														</c:choose></td>
													<%-- <td><c:out value="${fuel.vehicle_Ownership}" /> <br>
														<c:out value="${fuel.vehicle_group}" /></td> --%>
													<td><a
														href="<c:url value="/showDriver.in?driver_id=${fuel.driver_id}"/>"
														data-toggle="tip" data-original-title="Driver empnumber"><c:out
																value="${fuel.driver_empnumber}" /><br> </a> <c:out
															value="${fuel.driver_name}" /></td>
													<td><c:out value="${fuel.fuel_date}" /><br>
														<h6>
															<a data-toggle="tip" data-original-title="Vendor Name">
																<c:out value="${fuel.vendor_name}" />-( <c:out
																	value="${fuel.vendor_location}" /> )
															</a>
														</h6></td>
													<td><c:out value="${fuel.fuel_meter_old}" /></td>
													<td><c:out value="${fuel.fuel_meter}" /></td>

													<td><c:out value="${fuel.fuel_usage} km" /></td>

													<td><abbr data-toggle="tip"
														data-original-title="Liters"><c:out
																value="${fuel.fuel_liters}" /></abbr> <c:if
															test="${fuel.fuel_tank_partial==1}">
															<abbr data-toggle="tip"
																data-original-title="Partial fuel-up"> <i
																class="fa fa-adjust"></i>
															</abbr>
														</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
													<td><i class="fa fa-inr"></i> <c:out
															value="${fuel.fuel_amount}" /> <br> <abbr
														data-toggle="tip" data-original-title="Price"> <c:out
																value="${fuel.fuel_price}/liters" />
													</abbr></td>
													<td>
														<fmt:formatNumber type="number" maxFractionDigits="2" value="${fuel.fuel_kml}" />
													<c:if
															test="${fuel.fuel_kml != null}">
													Km/L
													</c:if></td>
													<%-- <td><c:out value="${fuel.fuel_cost} " /> <c:if
															test="${fuel.fuel_cost != null}">
													/Km
													</c:if></td>
													<td><c:choose>
															<c:when test="${fuel.fuel_document == true}">
																<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">

																	<a
																		href="${pageContext.request.contextPath}/download/FuelDocument/${fuel.fuel_document_id}.in">
																		<span class="fa fa-download"> Doc</span>
																	</a>
																</sec:authorize>
															</c:when>
														</c:choose></td> --%>
													<td class="fit"><%-- <c:choose>
															<c:when test="${fuel.fuel_TripsheetID != 0 && fuel.fuel_TripsheetNumber != null}">
																<a target="_blank"
																	href="<c:url value="/getTripsheetDetails.in?tripSheetID=${fuel.fuel_TripsheetID}"/>"
																	data-toggle="tip"
																	data-original-title="Click Tripsheet Details"><c:out
																		value="TS-${fuel.fuel_TripsheetNumber}" /> </a>
															</c:when>
															<c:otherwise> --%>
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																	<%-- <c:if test="${empty fuel.fuel_vendor_paymentdate}"> --%>
																		<li><sec:authorize
																				access="hasAuthority('EDIT_FUEL')">
																				<a
																					href="<c:url value="/FuelEntriesEdit?Id=${fuel.fuel_id}"/>">
																					<i class="fa fa-edit"></i> Edit
																				</a>
																			</sec:authorize></li>
																		<%-- </c:if> --%>	
																		<li><sec:authorize
																				access="hasAuthority('DELETE_FUEL')">
																				<c:choose>
																				<c:when test="${fuel.fuel_TripsheetID > 0}">
																					<a
																						href="<c:url value="/removeTSFuel.in?TSID=${fuel.fuel_TripsheetID}&FID=${fuel.fuel_id}"/>">
																						<i class="fa fa-trash"> Delete</i></a>
																				
																				</c:when>
																				<c:otherwise>
																					<a
																						href="<c:url value="/deleteFuel.in?FID=${fuel.fuel_id}"/>">
																						<i class="fa fa-trash"></i> Delete
																					</a>
																				
																				</c:otherwise>
																				</c:choose>
																			</sec:authorize></li>
																	</ul>
																</div>
															<%-- </c:otherwise>
														</c:choose> --%></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/Fuel/1" />
					<c:url var="lastUrl" value="/Fuel/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/Fuel/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/Fuel/${currentIndex + 1}" />
					<div class="text-center">
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
								<c:url var="pageUrl" value="/Fuel/${i}" />
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
				</sec:authorize>
			</div>
		</div>
	</section>
</div>
<div class="modal fade" id="fuelAlertModal" role="dialog" >
		<div class="modal-dialog" style="width:1250px;">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Missing Fuel Entries </h4>
					</div>
					<div class="modal-body">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<input type="hidden" id="startPage" value="${SelectPage}"> 
									<table class="table table-hover table-bordered">
										<thead>
											<th> SR NO </th>
											<th> Vehicle Registration </th>
											<th> Last Fuel Entry Number </th>
											<th> Last Fuel Entry Date </th>
											<th> Last Fuel Entry Odometer </th>
											<th> Current Odometer</th>
											<th> Max Odometer </th>
										</thead>
										<tbody id="fuelAlertTable"  ></tbody>
									</table>
									<br>
									<div id="noFuelEntry" class="hide">
									<h4 class="modal-title" > Vehicle Without Fuel Entry</h4>
										<table class="table table-hover table-bordered">
											<thead>
												<th> SR NO </th>
												<th> Vehicle Registration </th>
												<th> Current Odometer</th>
												<th> Max Odometer </th>
											</thead>
											<tbody id="noFuelTable"  ></tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>
					</div>
					<div class="modal-footer">
						
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelAlertList.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>