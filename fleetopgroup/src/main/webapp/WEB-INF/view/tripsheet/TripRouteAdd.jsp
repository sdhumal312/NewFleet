<%@ include file="taglib.jsp"%>

<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<style>
  table {
    table-layout:fixed;
}

table td {
    overflow:hidden;
}
}
</style> 
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripRoute/1.in"/>">New Trip Route</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createTripRoute.in"/>"> <span
							class="fa fa-plus"> Create Trip Route</span>
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TRIP_CHECK_POINTS_PARAMETER')">
						<a class="btn btn-info btn-sm" href="#" onclick="addTripCheckPointParameter()"> <span
							class="fa fa-plus">Trip CheckPoint Parameter</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-map"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Route</span> <span
								class="info-box-number">${TotalRoute}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Route</span>
							<form action="<c:url value="/searchRoute.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" name="Route" type="text"
										required="required" placeholder="Route Name, NO">
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
		<c:if test="${param.saveTripRoute eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Route Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteTripRoute  eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Route Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.notdeleteTripRoute eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Route Not Deleted Successfully. Please Remove All fixed
				Expense
			</div>
		</c:if>
		<c:if test="${param.alreadyTripRoute  eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Route Already Exists.
			</div>
		</c:if>

		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table id="TripRouteTable" class="table table-striped">
										<thead>
											<tr>
												<th class="fit">Route No</th>
												<th>Route Name</th>
												<c:if test="${subRouteTypeNeeded}">
												<th>Route Type</th>
												</c:if>
												<th class="fit ar">Approximate KM</th>
												<th>Description</th>
												<th>Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty TripRoute}">
												<c:forEach items="${TripRoute}" var="TripRoute">
													<tr>
														<td><a
															href="<c:url value="/showTripRoute.in?routeID=${TripRoute.routeID}"/>"><c:out
																	value="${TripRoute.routeNo}" /></a></td>
														<td><a
															href="<c:url value="/showTripRoute.in?routeID=${TripRoute.routeID}"/>"><c:out
																	value="${TripRoute.routeName}" /></a></td>
														<c:if test="${subRouteTypeNeeded}">
														
															<td><c:choose>
																	<c:when test="${TripRoute.routeType == 1}">
																		<c:out value="MAIN ROUTE" />
																	</c:when>
																	<c:when test="${TripRoute.routeType == 2}">
																		<c:out value="SUB ROUTE" />
																	</c:when>
																</c:choose> </td>
														</c:if>
														
														<td><c:out value="${TripRoute.routeApproximateKM}" /></td>
														<td><c:out value="${TripRoute.routeRemarks}" /></td>
														<td>
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="<c:url value="/editTripRoute.in?routeID=${TripRoute.routeID}"/>">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="<c:url value="/deleteTripRoute.in?routeID=${TripRoute.routeID}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete ?')">
																				<span class="fa fa-trash"></span> Delete
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
						<c:url var="firstUrl" value="/newTripRoute/1" />
						<c:url var="lastUrl"
							value="/newTripRoute/${deploymentLog.totalPages}" />
						<c:url var="prevUrl" value="/newTripRoute/${currentIndex - 1}" />
						<c:url var="nextUrl" value="/newTripRoute/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/newTripRoute/${i}" />
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
		</div>
	</section>
</div>
	<div class="modal fade" id="addTripCheckPointParameterModal" role="dialog">
		<div class="modal-dialog" style="width:80%" >
			<!-- Modal content-->
			<div class="modal-content">
				<input type="hidden" id="companyId" value="${companyId}">
				<input type="hidden" id="userId" value="${userId}">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Add Trip CheckPoints Parameter</h4>
					</div>
					<div class="modal-body">
						<fieldset>
						<div class="row box-body">
							<div class="table-responsive">
								<table  class="table table-hover table-bordered">
									<thead>
										<tr>
											<th><h5>Sr No</h5></th>
											<th><h5>CheckPoint Parameter Name</h5></th>
											<th><h5>Description</h5></th>
											<th><h5>Action</h5></th>
										</tr>
									</thead>
									<tbody id="tripCheckPointParameterTable">
									</tbody>
								
								</table>
							</div>
						</div>
						<br>
						</fieldset>
						<fieldset>
						<div class="row box">
							<div class=" col-sm-8 col-md-2" style="font-weight: bold;">CheckPoint Parameter Name :
									<abbr title="required">*</abbr>
							</div>
						    <div class=" col-sm-8 col-md-3">
						    	<div class="col-lg-9">
									<input type="text" class="form-text" required="required"
									maxlength="150"  id="checkPointParameterName"
									placeholder="Enter  Name" /> 
								</div>
						    </div>
						    <div class=" col-sm-8 col-md-1" style="font-weight: bold;">Description :
						    	
						    </div>
						   	<div class=" col-sm-8 col-md-3">
						   		<div class="I-size">
						   			<textarea class="form-text" id="addDescription" rows="3"
						   			style="width: 411px; height: 50px;"  maxlength="250">
									</textarea>
								</div>
							</div>
					   	</div>
						</fieldset>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="saveTripCheckPointParameter();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCheckPointParameter.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>