<%@ include file="taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="box">
				<input type="hidden" id="mainLocationId"  value="${PartLocations.partlocation_id}">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
									href="<c:url value="/addPartLocations.in"/>">New Part
										Locations</a> / <span id="NewPartLocations">${PartLocations.partlocation_id}</span></small>
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
									<a href="createPartLocations.in" class="btn btn-success"><span
										class="fa fa-plus" id="AddPartLocations"> Create Part
											Locations</span></a>
								</sec:authorize>
								<c:if test="${subPartLocationTypeNeeded}">	
								<c:if test="${PartLocations.partLocationType == 1}">		
									<button type="button" class="btn btn-success" onclick="getSubLocations();">
										<span class="fa fa-plus" >Show Sub Location</span>
									</button>
								</c:if>	
								</c:if>				
							</div>
						</div>
						<div class="row">
							<div class="row">
							<c:choose>
								<c:when test="${PartLocations.partLocationType == 1}">
									<h4>
										Main Location: <a data-toggle="tip" data-original-title="Part Location Name">
											<c:out value="${PartLocations.partlocation_name}" />
										</a>
										
									</h4>
								</c:when>
								<c:otherwise>
									<h4>
										Main Location: <a data-toggle="tip" data-original-title="Part Location Name">
											<c:out value="${PartLocations.mainPartLocation}" />
										</a>	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
										Sub Location: <a data-toggle="tip" data-original-title="Part Location Name">
											<c:out value="${PartLocations.partlocation_name}" />
										</a>
										
									</h4>
								
								</c:otherwise>
							</c:choose>
								
							</div>
							<div class="col-md-3"></div>
						</div>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>Description : <a data-toggle="tip"
									data-original-title="Remarks"> <c:out
											value="${PartLocations.partlocation_description}" /></a></li>
							</ul>
						</div>
						
						<br>
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<fieldset>
								<div class="row">
									<div class="col-md-5">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Warehouse Address</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Address :</th>
															<td class="value"><c:out
																	value="${PartLocations.partlocation_address}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Street Address Line :</th>
															<td class="value"><c:out
																	value="${PartLocations.partlocation_streetaddress}" /></td>
														</tr>
														<tr class="row">
															<th class="key">City :</th>
															<td class="value"><c:out
																	value="${PartLocations.partlocation_city}" /></td>
														</tr>
														<tr class="row">
															<th class="key">State/Province/Region :</th>
															<td class="value"><c:out
																	value="${PartLocations.partlocation_state}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Country :</th>
															<td class="value"><c:out
																	value="${PartLocations.partlocation_country}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Zip/Postal Code :</th>
															<td class="value"><c:out
																	value="${PartLocations.partlocation_pincode}" /></td>
														</tr>
												</table>
											</div>
										</div>
									</div>
									<div class="col-md-5">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Shipping Address</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Address :</th>
															<td class="value"><c:out
																	value="${PartLocations.shippartlocation_address}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Street Address Line :</th>
															<td class="value"><c:out
																	value="${PartLocations.shippartlocation_streetaddress}" /></td>
														</tr>
														<tr class="row">
															<th class="key">City :</th>
															<td class="value"><c:out
																	value="${PartLocations.shippartlocation_city}" /></td>
														</tr>
														<tr class="row">
															<th class="key">State/Province/Region :</th>
															<td class="value"><c:out
																	value="${PartLocations.shippartlocation_state}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Country :</th>
															<td class="value"><c:out
																	value="${PartLocations.shippartlocation_country}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Zip/Postal Code :</th>
															<td class="value"><c:out
																	value="${PartLocations.shippartlocation_pincode}" /></td>
														</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
								<hr>
								<div class="row">
									<div class="col-md-5">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Contact 1</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Name :</th>
															<td class="value"><c:out
																	value="${PartLocations.contactFirName}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Mobile NO :</th>
															<td class="value"><c:out
																	value="${PartLocations.contactFirPhone}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Designation :</th>
															<td class="value"><c:out
																	value="${PartLocations.contactFirdescription}" /></td>
														</tr>
												</table>
											</div>
										</div>
									</div>
									<div class="col-md-5">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Contact 2</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Name :</th>
															<td class="value"><c:out
																	value="${PartLocations.contactSecName}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Mobile NO :</th>
															<td class="value"><c:out
																	value="${PartLocations.contactSecPhone}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Designation :</th>
															<td class="value"><c:out
																	value="${PartLocations.contactSecdescription}" /></td>
														</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<div class="modal fade" id="showSubPartLocation" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" >Sub Part Location</h4>
				</div>
				<div class="main-body">
					<div class="box">
						<div class="box-body">
			
							<div class="table-responsive">
								<table class="table">
								<thead>
									<tr>
										<th class="fit ar">Sr No</th>
										<th class="fit ar">Location Name</th>
										<th class="fit ar">Action</th>
									</tr>
								</thead>
								<tbody id="subLocationModelTable">
								
								</tbody>
			
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/showPartLocation.js" />"></script>