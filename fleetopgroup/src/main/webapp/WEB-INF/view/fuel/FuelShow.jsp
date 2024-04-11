<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a> / <small>Add
							Fuel Entry</small>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchFuelShow.in"/>" method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">F-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										required="required" min="1" placeholder="ID eg: 1234">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<c:choose>
							<c:when test="${fuel.fuel_document == true}">
								<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">

									<a class="btn btn-info"
										href="${pageContext.request.contextPath}/download/FuelDocument/${fuel.fuel_document_id}.in">
										<span class="fa fa-download"> Doc</span>
									</a>
								</sec:authorize>
							</c:when>
						</c:choose>
						<sec:authorize access="hasAuthority('EDIT_FUEL')">
							<a class="btn btn-success" href="FuelEntriesEdit?Id=${fuel.fuel_id}">
								<i class="fa fa-plus"></i> Edit Fuel
							</a>
						</sec:authorize>
						<c:if test="${gpsConfiguration.allowGPSIntegration}">
							<sec:authorize access="hasAuthority('ADD_FUEL')">
								<a class="btn btn-success" href="#" onclick="addGPSUsageData();">
									<em class="fa fa-plus"></em>GPS Usage
								</a>
							</sec:authorize>
						</c:if>
						<a class="btn btn-link" href="<c:url value="/Fuel/1.in"/>">Cancel</a>

					</div>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_FUEL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_FUEL')">
			
				<c:if test="${param.refExists eq true}">
					<div class="alert alert-warning">
						Fuel Reference number already exists for vendor !
					</div>
				</c:if>
				
				<div class="box-body">
					<div class="pull-left1">
						<h3 class="secondary-header-title">
								<a
								href="#"
								data-toggle="tip" data-original-title="Fuel Number">
								<c:out value="F-${fuel.fuel_Number}" />
							</a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-bell"> Type :</span> <a href=""
									data-toggle="tip" data-original-title="Fuel Type"> <c:out
											value="${fuel.fuel_type}" />
								</a></li>
								<li><span class="fa fa-usb"> Odometer :</span> <a href=""
									data-toggle="tip" data-original-title="Fuel OdoMeter"> <c:out
											value="${fuel.fuel_meter} Km" /></a></li>
								<li><span class="fa fa-usb"> Reference :</span> <a href=""
									data-toggle="tip" data-original-title="Fuel Reference No">
										<c:out value="${fuel.fuel_reference}" />
								</a></li>
								<c:if test="${fuel.fuel_meter_attributes == 1}">
									<li><span class="fa fa-info"> Meter attribute :</span> <a href=""
										data-toggle="tip" data-original-title="Fuel attribute">
											<c:out value="${fuel.meter_attributes_str}" />
									</a></li>
								</c:if>
								

							</ul>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_FUEL')">
			<div class="row">
				<div class="col-md-10 col-sm-12 col-xs-12">
					<div class="main-body">

						<div class="row">
							<div class="col-md-6 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Fuel Vehicle Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td class="value"><a
													target="_Blank"	href="<c:url value="/VehicleFuelDetails/1.in?vid=${fuel.vid}"/>"
														data-toggle="tip"
														data-original-title="Click Vehicle Details"> <c:out
																value="${fuel.vehicle_registration}" />
													</a></td>
												</tr>
												<tr class="row">
													<th class="key">Date :</th>
													<td class="value"><c:out value="${fuel.fuel_date}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Opening Odometer :</th>
													<td class="value"><c:out
															value="${fuel.fuel_meter_old} /Km" /></td>
												</tr>
												<tr class="row">
													<th class="key">Closing OdoMeter :</th>
													<td class="value"><c:out
															value="${fuel.fuel_meter} /Km" /></td>
												</tr>
												<tr class="row">
													<th class="key">Usage KM:</th>
													<td class="value"><c:out
															value="${fuel.fuel_usage}" /></td>
												</tr>
												<c:if test="${fuel.gpsUsageKM != null && fuel.gpsUsageKM > 0}">
												<tr class="row">
													<th class="key">GPS Usage :</th>
													<td class="value"><fmt:formatNumber type="number" maxFractionDigits="2" value="${fuel.gpsUsageKM}"/>
													</td>
												</tr>
												</c:if>
												<c:if test="${configuration.showRoute}">
												<tr class="row">
													<th class="key">Route :</th>
													<td class="value"><c:out
															value="${fuel.fuelRouteName}" /></td>
												</tr>
												</c:if>
												
												<c:if test="${fuel.gpsOdometer != null && fuel.gpsOdometer > 0}">
												<tr class="row">
													<th class="key">GPS OdoMeter :</th>
													<td class="value"><c:out
															value="${fuel.gpsOdometer}/KM" /></td>
												</tr>
												</c:if>
												
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Fuel Information</h3>
									</div>
									<div class="box-body no-padding">
									   <input type="hidden" id="fuelId" value="${fuel.fuel_id}">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Fuel Type :</th>
													<td class="value"><span class="label label-success"><c:out
																value="${fuel.fuel_type}" /></span></td>
												</tr>
												<tr class="row">
													<th class="key">Liter :</th>
													<td class="value"><c:out
															value="${fuel.fuel_liters} /Liters" /></td>
												</tr>
												<tr class="row">
													<th class="key">Price / Unit :</th>
													<td class="value"><i class="fa fa-inr"></i> <c:out
															value="${fuel.fuel_price}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Fuel Tank :</th>
													<td class="value"><c:choose>
															<c:when test="${fuel.fuel_tank_partial == 0}">
																<span class="label label-success"><c:out
																		value="Full" /></span>
															</c:when>
															<c:otherwise>
																<span class="label label-danger"><c:out
																		value="Partial" /></span>
															</c:otherwise>

														</c:choose></td>
												</tr>
												<tr class="row">
													<th class="key">TripSheet ID :</th>
													<td class="value"><c:choose>
															<c:when test="${fuel.fuel_TripsheetID != 0}">
																<a target="_blank"
																	href="<c:url value="/getTripsheetDetails.in?tripSheetID=${fuel.fuel_TripsheetID}"/>"
																	data-toggle="tip"
																	data-original-title="Click Tripsheet Details"><c:out
																		value="TS-${fuel.fuel_TripsheetNumber}" /> </a>
															</c:when>
															<c:otherwise>
																<c:out value="TS-${fuel.fuel_TripsheetID}" />
															</c:otherwise>
														</c:choose></td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Vendor Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Vendor Name :</th>
													<td class="value"><c:choose>
															<c:when test="${fuel.vendor_id == 0}">
																<a target="_blank" data-toggle="tip"
																	data-original-title="Click this Vendor Details"><c:out
																		value="${fuel.vendor_name}" /> </a>
															</c:when>
															<c:otherwise>
																<a href="ShowVendor.in?vendorId=${fuel.vendor_id}"
																	target="_blank" data-toggle="tip"
																	data-original-title="Click this Vendor Details"><c:out
																		value="${fuel.vendor_name}" /> </a>
															</c:otherwise>
														</c:choose></td>
												</tr>
												<tr class="row">
													<th class="key">Location :</th>
													<td class="value"><c:out
															value="${fuel.vendor_location}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Payment :</th>
													<td class="value"><c:choose>
															<c:when test="${fuel.fuel_vendor_paymode == 'PAID'}">
																<span class="label label-pill label-success"><c:out
																		value="PAID" /></span>
															</c:when>
															<c:otherwise>
																<span class="label label-pill label-danger"><c:out
																		value="NOT PAID" /></span>
															</c:otherwise>
														</c:choose></td>
												</tr>
												<tr class="row">
													<th class="key">Payment Date :</th>
													<td class="value"><c:out
															value="${fuel.fuelVendorPaymentDate}" /></td>
												</tr>
												<c:if test="${configuration.showPaymentMode}">
													<tr class="row">
														<th class="key">Payment Mode :</th>
														<td class="value"><c:out value="${fuel.fuel_payment}" /></td>
													</tr>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Cost Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Amount :</th>
													<td class="value"><c:out value="${fuel.fuel_amount}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Km/L :</th>
													<td class="value"><c:out value="${fuel.fuel_kml}" />
														Km /Liter</td>
												</tr>
												<tr class="row">
													<th class="key">Cost per Km :</th>
													<td class="value"><c:out value="${fuel.fuel_cost}" />
														/ Km</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Reference Info</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Reference :</th>
													<td class="value"><c:out
															value="${fuel.fuel_reference}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Comment :</th>
													<td class="value"><c:out value="${fuel.fuel_comments}" />
													</td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Driver Info</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Driver Name:</th>
													<c:if test="${!configuration.showDriverFullNameInFuel}">
														<td class="value"><c:out value="${fuel.driver_name}" /></td> 
													</c:if>		
													<c:if test="${configuration.showDriverFullNameInFuel}">
														<td class="value"><c:out value="${fuel.driver_name}  ${fuel.firstDriverLastName} - ${fuel.firstDriverFatherName}" /></td>
													</c:if>		
												</tr>
												<c:if test="${configuration.showDriver2}">
												<tr class="row">
													<th class="key">Driver2 Name  :</th>
													<c:if test="${!configuration.showDriverFullNameInFuel}">
														<td class="value"><c:out value="${fuel.fuelSecDriverName}" /> </td>
													</c:if>
													
													<c:if test="${configuration.showDriverFullNameInFuel}">
														<td class="value"><c:out value="${fuel.fuelSecDriverName} ${fuel.secDriverLastName} - ${fuel.secDriverFatherName} " /> </td>
													</c:if>
												</tr>
												</c:if>
												<c:if test="${configuration.showCleaner}">
												<tr class="row">
													<th class="key">Cleaner Name  :</th>
													<td class="value"><c:out value="${fuel.fuelCleanerName}" />
													</td>
												</tr>
												</c:if>
												<c:if test="${fuel.tallyCompanyId != null && fuel.tallyCompanyId > 0}">
												<tr class="row">
													<th class="key">Tally Company  :</th>
													<td class="value"><c:out value="${fuel.tallyCompanyName}" />
													</td>
												</tr>
												</c:if>

											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${fuel.createdBy}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${fuel.created}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${fuel.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${fuel.lastupdated}" /></small>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelShow.js" />"></script>
</div>