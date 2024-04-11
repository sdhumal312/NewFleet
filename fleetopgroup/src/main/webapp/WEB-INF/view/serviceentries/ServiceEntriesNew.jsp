<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newServiceEntries/${SelectStatus}/1.in"/>"> Service
						Entries</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchServiceEntriesShow.in"/>"
							method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">SE-</span></span>
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="number" min="1" required="required"
									placeholder="ID eg: 2323"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createServiceEntries?issue=0,0"/>"> <span
							class="fa fa-plus"></span> Create Service Entries
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createServiceEntries?issue=0,0"/>"> 
							<span class="fa fa-plus"></span> Create New Service Entries
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/ServiceEntriesReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#searchServiceEntriesByDate" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="click this for trip Details"> <span
							class="fa fa-search"></span> Search Service Entries By Date
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-safari"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Service Entries</span> <span
								class="info-box-number">${totalserviceentriescount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search ServiceEntries</span>
							<form action="<c:url value="/searchServiceEntries.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="Search" type="number" required="required" min="1"
										placeholder="ID, V-Name, Invoice-No"> <span
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
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>
		<c:if test="${param.saveServiceEntries eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This ServiceEntries Created successfully .
			</div>
		</c:if>

		<c:if test="${param.deleteServiceEntries eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This ServiceEntries Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.NoDocument eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This ServiceEntries Not Upload Document Please.. Upload .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This ServiceEntries Not Create successfully .
			</div>
		</c:if>
		<c:if test="${param.deleteAllTask eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Please Delete all Job and Sub Job...
			</div>
		</c:if>

		<c:if test="${param.alreadyCreatedWorkOrder eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This ServiceEntries Reminder was already created. Please Wait for
				Complete Work Order.
			</div>
		</c:if>
		
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="1"><a
										href="<c:url value="/newServiceEntries/1/1.in"/>">OPEN</a></li>
									<li role="presentation" id=2><a
										href="<c:url value="/newServiceEntries/2/1.in"/>">INPROCESS</a></li>
									<li role="presentation" id="3"><a
										href="<c:url value="/newServiceEntries/3/1.in"/>">COMPLETED</a></li>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>

		<div class="row">
		
			<div class="modal fade" id="searchServiceEntriesByDate" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Service Entries By Date</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/searchServiceEntriesByDate.in"/>" method="post"
						enctype="multipart/form-data" name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
							
							<div class="row" id="grpReportDailydate" class="form-group">
								<!-- <div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="searchDate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div> -->
								
								<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="ReportDailydate" class="form-text"
														name="searchDate" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
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
		
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">Id</th>
											<th>Vehicle</th>
											<th>Vendor</th>
											<th class="fit ar">Invoice Number</th>
											<th class="fit ar">Job Number</th>
											<th class="fit ar">Cost</th>
											<th class="fit ar">Paid By</th>
											<th class="fit ar">Last modified By</th>
											<th class="fit ar">Document</th>
											<th class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>

										<c:if test="${!empty ServiceEntries}">
											<c:forEach items="${ServiceEntries}" var="ServiceEntries">

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="<c:url value="/ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"/>"><c:out
																value="SE-${ServiceEntries.serviceEntries_Number}" /></a></td>
													<td><a
														href="<c:url value="/VehicleServiceEntriesDetails.in?vid=${ServiceEntries.vid}"/>"
														data-toggle="tip"
														data-original-title="Click this vehicle Details"> <c:out
																value="${ServiceEntries.vehicle_registration}" />

													</a> <br> <c:out value="${ServiceEntries.vehicle_Group}" /></td>

													<td><c:out value="${ServiceEntries.vendor_name}" /><br>
														<c:out value="${ServiceEntries.vendor_location}" /></td>

													<td class="fir ar"><c:out
															value="${ServiceEntries.invoiceNumber}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.jobNumber}" /></td>

													<td class="fir ar"><c:out
															value="${ServiceEntries.totalserviceROUND_cost}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.service_paidby}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.lastModifiedBy}" /></td>
															

													<td><c:choose>
															<c:when test="${ServiceEntries.service_document == true}">
																<sec:authorize
																	access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
																	<a
																		href="${pageContext.request.contextPath}/download/serviceDocument/${ServiceEntries.service_document_id}.in">
																		<span class="fa fa-download"> Doc</span>
																	</a>
																</sec:authorize>
															</c:when>
														</c:choose></td>

													<td class="fir ar">
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-ellipsis-v"></span>
															</a>

															<ul class="dropdown-menu pull-right">
																<c:choose>
																	<c:when
																		test="${ServiceEntries.serviceEntries_statusId != 3}">

																		<li><sec:authorize
																				access="hasAuthority('EDIT_SERVICE_ENTRIES')">
																				 <a
																					href="<c:url value="/editServiceEntries.in?SEID=${ServiceEntries.serviceEntries_id}"/>">
																					<i class="fa fa-edit"></i> Edit
																				</a> 
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																				<a
																					href="<c:url value="/deleteServiceEntries.in?SEID=${ServiceEntries.serviceEntries_id}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure? Delete ')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</c:when>
																	<c:otherwise>
																		<li><span class="label label-warning"><i
																				class="fa fa-dot-circle-o"></i> <c:out
																					value="SERVICE COMPLETED" /></span></li>
																	</c:otherwise>
																</c:choose>
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
					<c:url var="firstUrl" value="/newServiceEntries/${SelectStatus}/1" />
					<c:url var="lastUrl"
						value="/newServiceEntries/${SelectStatus}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/newServiceEntries/${SelectStatus}/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/newServiceEntries/${SelectStatus}/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/newServiceEntries/${SelectStatus}/${i}" />
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
			</sec:authorize>
		</div>
	</section>
	<input type="hidden" value="${SelectStatus}" id="statues">
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
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
		
		$(function() {
			function a(a, b) {
				$("#ReportDailydate").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
			}
			a(moment().subtract(1, "days"), moment()), $("#ReportDailydate").daterangepicker( {
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

		);
		
		
	</script>
	
</div>