<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/DriverReport.in"/>">Driver Report</a> / <span>Search
						Report </span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
						<!-- <a href="#" id="exportDriver" class="btn btn-info"> <span
							class="fa fa-file-excel-o"></span>Export
						</a> -->
						<button class="btn btn-default btn-sm "
						onclick="advanceTableToExcel('div_print', 'Driver Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					</sec:authorize>
					<a class="btn btn-link" href="<c:url value="/getDriversList"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
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
				
				<c:if test="${configuration.downloadDriverDocumentInZip}">
					<div class="col-md-3 col-sm-4 col-xs-12">
						<div class="info-box-center">
							<div class="input-group">
								<button id="downloadZip" class="btn btn-primary">Download Documents</button>
							</div>
						</div>
					</div>
				</c:if>
				
			</div>
			<input type="hidden" id="companyId" value="${companyId}">
			<div class="row">
				<div class="main-body">
					<div id="div_print">
					<div class="box">
						<div class="box-body">
							<table id="DriverTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit ar">EMP-NO</th>
										<c:if test="${configuration.downloadDriverDocumentInZip}">
											<th>Download Document</th>
										</c:if>
										<th>Name</th>
										<th class="fit ar">DL-No</th>
										<th class="fit ar">Badge-No</th>
										<th class="fit ar">Phone</th>
										<th class="fit ar">Group</th>
										<th class="fit ar">Job</th>
										<th class="fit ar">Current Trip</th>
										<th class="fit ar">Status</th>
										<th class="fit ar">Vehicle</th>
										<th class="actions">Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty drivers}">
										<c:forEach items="${drivers}" var="driver">

											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><a
													href="showDriver.in?driver_id=${driver.driver_id}"
													target="_blank" data-toggle="tip"
													data-original-title="Click driver"> <c:out
															value="${driver.driver_empnumber}" />

												</a></td>
												<c:if test="${configuration.downloadDriverDocumentInZip}">
													<td class="fit ar">
														<input type="checkbox" id="downloadDocument" name="downloadDocument" value="${driver.driver_id}">
													</td>
												</c:if>
												<td><a
													href="showDriver.in?driver_id=${driver.driver_id}"
													target="_blank" data-toggle="tip"
													data-original-title="Click this driver Details"> <c:out
															value="${driver.driver_firstname}   " /> <c:out
															value="${driver.driver_Lastname}" /><c:out
															value="- ${driver.driver_fathername}" />
												</a></td>
												<td class="fit ar"><c:out
														value="${driver.driver_dlnumber}" /></td>
												<td class="fit ar"><c:out
														value="${driver.driver_badgenumber}" /></td>
												<td class="fit ar"><span class="fa fa-phone"
													aria-hidden="true" data-toggle="tipDown"
													data-original-title="Phone Number"> </span> <c:out
														value="${driver.driver_mobnumber}" />
												<td class="fit ar"><i class="icon"></i> <a href="#"><c:out
															value="${driver.driver_group}" /></a></td>
												<td class="fit ar"><i class="icon"></i> <a href="#"><c:out
															value="${driver.driver_jobtitle}" /></a></td>
												<td class="fit ar"><c:choose>
														<c:when test="${driver.tripSheetID != 0}">
															<a
																href="<c:url value="/showTripSheet.in?tripSheetID=${driver.tripSheetID}"/>"><c:out
																	value="TS-${driver.tripSheetNumber}" /></a>
														</c:when>
													</c:choose></td>
												<td class="fit ar"><c:out
														value="${driver.driver_active}" /></td>
														
												<td class="fit ar"><c:out
														value="${driver.vehicle_registration}" /></td>

												<td class="actions"><c:choose>
														<c:when test="${driver.driver_active == 'ACTIVE'}">
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"><span
																	class="fa fa-ellipsis-v"></span> </a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize access="hasAuthority('EDIT_DRIVER')">
																			<a href="editDriver.in?driver_id=${driver.driver_id}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_DRIVER')">
																			<a
																				href="deleteDriver.in?driver_id=${driver.driver_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</c:when>
														
														<c:when test="${driver.driver_active == 'INACTIVE' && configuration.editInActiveDriver}">
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"><span
																	class="fa fa-ellipsis-v"></span> </a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize access="hasAuthority('EDIT_DRIVER')">
																			<a href="editDriver.in?driver_id=${driver.driver_id}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_DRIVER')">
																			<a
																				href="deleteDriver.in?driver_id=${driver.driver_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</c:when>
													</c:choose></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverNewlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>		
</div>
