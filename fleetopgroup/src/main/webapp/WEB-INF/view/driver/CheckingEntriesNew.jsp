<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.fleetopgroup.persistence.dto.VehicleCheckingDetailsDto"%>
<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/CheckingEntry/1.in"/>">Checking Entry</a>
				</div>
				<div class="col-md-off-5">
					<sec:authorize access="hasAuthority('VIEW_CHECKING_ENTRY')">
					<div class="pull-right">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/CheckingEntry.in"/>"> <span class="fa fa-plus"></span>
							Add Checking Entry
						</a>
					</div>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_CHECKING_ENTRY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_CHECKING_ENTRY')">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-tint"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Checking Entries</span> <span
								class="info-box-number">${FuelCount}</span>
						</div>
					</div>
				</div>
				
			</sec:authorize>
		</div>
		
		<c:if test="${saveFuel}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data Successfully Created.
			</div>
		</c:if>
		<c:if test="${param.Success eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Checking Entry Data Successfully Created.
			</div>
		</c:if>
		<c:if test="${param.Update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Checking Data successfully Updated.
			</div>
		</c:if>
		
		<c:if test="${param.delete eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Checking Data successfully Deleted.
			</div>
		</c:if>
		
		<c:if test="${deleteFuel}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Checking Data Successfully Deleted.
			</div>
		</c:if>


		<div class="modal fade" id="editCheckingEntry" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="post" action="<c:url value="/importDriver.in"/>"
						enctype="multipart/form-data">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h3 class="panel-title">Edit Checking Entry</h3>
							</div>
							<div class="panel-body">
								<div class="form-horizontal">
									<br>
									<div class="row1">
										<div class="L-size">
											<label>Checking Inspector</label>
										</div>
										<div class="I-size">
											<input type="text" name="checkingins" id="checkingins"
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

							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	
		<div class="row">
			<div class="main-body">
				<sec:authorize access="!hasAuthority('VIEW_CHECKING_ENTRY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_CHECKING_ENTRY')">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="FuelTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th>Sr No.</th>
											<th>Chk Inspector</th>
											<th>Vehicle</th>
											<th>In Time</th>
											<th>Out Time</th>
											<th>In Place</th>
											<th>Out Place</th>
											<th>No Of Seat</th>
											<th>Remark</th>
											<th>Conductor</th>
											
											<th class="actions" class="icon">Actions</th>
										</tr>
									</thead>
									<tbody>
											<%
												Integer hitsCount = 1;
											%>
										<c:if test="${!empty pageList}">
											<c:forEach items="${pageList}" var="pageList">
												
												<%
												//VehicleCheckingDetailsDto	detailsDto = ${pageList};
												//Logger.getLogger("").debug("fsadfasfs ", detailsDto);
												%>
												
												<tr data-object-id="" class="ng-scope">
													<td class="fit">
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
													<td><a
														href="<c:url value="/showDriver.in?driver_id=${pageList.checkingInspectorId}"/>"
														data-toggle="tip" data-original-title="Driver empnumber"><c:out
																value="${pageList.checkingInspectorName}" /></td>
													<td><c:out value="${pageList.vehicle_registration}" /> 
														</td>
													
													<td><c:out value="${pageList.checkingTime}" /></td>
													<td><c:out value="${pageList.checkingOutTime}" /></td>
													<td><c:out value="${pageList.place}" /></td>
													<td><c:out value="${pageList.outPlace}" /></td>
													<td><c:out value="${pageList.noOfSeat}" /></td>
													<td><c:out value="${pageList.remark}" /></td>
													
													<td><a
														href="<c:url value="/showDriver.in?driver_id=${pageList.conductorId}"/>"
														data-toggle="tip" data-original-title="Driver empnumber"><c:out
																value="${pageList.conductorName}" /></td>
													
													<td class="fit">
															
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<%-- <li><sec:authorize
																				access="hasAuthority('EDIT_CHECKING_ENTRY')">
																				<button style="padding-left: 40px;" type="button" class="btn btn-default btn-sm"
																				 onclick="editCheckingEntry('${pageList}');"	data-toggle="modal" data-target="#editCheckingEntry" data-whatever="@mdo">
																					<i class="fa fa-edit"></i> Edit
																				</button>
																			</sec:authorize></li> --%>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_CHECKING_ENTRY')">
																				<a
																					href="<c:url value="/deleteVehicleChecking.in?ID=${pageList.checkingId}"/>">
																					<i class="fa fa-trash"></i> Delete
																				</a>
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
					<c:url var="firstUrl" value="/CheckingEntry/1" />
					<c:url var="lastUrl" value="/CheckingEntry/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/CheckingEntry/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/CheckingEntry/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/CheckingEntry/${i}" />
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
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script>
		$(function() {
			$('.clockpicker').clockpicker({
				  twelvehour: true
			});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/driver/checkingentry.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>