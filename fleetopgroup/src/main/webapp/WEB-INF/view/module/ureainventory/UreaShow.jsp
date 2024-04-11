<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="UreaEntriesShowList.in">Urea Entry</a> / <small>Add
							Urea Entry</small>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">UE-</span></span> <input class="form-text" onkeyup="ureaEntriesSearchOnEnter(event);"
										id="ureaInvoiceNumber" name="Search" type="number"
										required="required" min="1" placeholder="ID eg: 1234">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" onclick="ureaEntriesSearch();"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
						
						<input type="hidden"  id="ureaEntriesId" value="${ureaEntriesId}">
						
						<sec:authorize access="hasAuthority('EDIT_FUEL')">
							<a class="btn btn-success" href="editUreaEntriesInvoice.in?Id=${ureaEntriesId}">
								<i class="fa fa-plus"></i> Edit Urea
							</a>
						</sec:authorize>
						<a class="btn btn-link" href="UreaEntriesShowList.in">Cancel</a>

					</div>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_FUEL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_FUEL')">
				<div class="box-body">
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="#" id="ureaEntriesNumber" ></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								
								<li><span class="fa fa-usb"> Odometer :</span> <a href="" id="ureaOdometer"
									data-toggle="tip" data-original-title="Urea OdoMeter"></a></li>
								<li><span class="fa fa-usb"> Reference :</span> <a href="" id="reference"
									data-toggle="tip" data-original-title="Reference No">
								</a></li>

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
						<input type="hidden" id="showFilledLocation" value="${configuration.showFilledLocation}">
							<div class="col-md-6 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Urea Vehicle Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td class="value"><span id="vehicle_registration"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Date :</th>
													<td class="value"><span id="ureaDate"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Opening Odometer :</th>
													<td class="value"><span id="openingOdometer"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Closing OdoMeter :</th>
													<td class="value"><span id="closingOdometer"></span></td>
												</tr>
												<c:if test="${!configuration.hideDriverAndRoute}">
												<tr class="row">
													<th class="key">Route :</th>
													<td class="value"><span id="routeName"></span></td>
												</tr>
												</c:if>
												<sec:authorize access="hasAuthority('METER_NOT_WORKING')">
												<tr class="row">
													<th class="key">Meter Working Status :</th>
													<td class="value">
													<span id="meterWorkingStatus" class="label label-default"></span></td>
												</tr>
												</sec:authorize>
												
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Urea Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												
												<tr class="row">
													<th class="key">Liter :</th>
													<td class="value"><span id="liters"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Price / Unit :</th>
													<td class="value"><i class="fa fa-inr"></i> <span id="unitRate"></span> </td>
												</tr>
												<tr class="row">
													<th class="key">Discount :</th>
													<td class="value"><span id="discount"></span> </td>
												</tr>
												<tr class="row">
													<th class="key">GST :</th>
													<td class="value"><span id="gst"></span> </td>
												</tr>
												
												<tr class="row">
													<th class="key">TripSheet ID :</th>
													<td class="value"><span id="tripSheetId"></span></td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
<%-- 								<div class="box box-success">
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
															value="${fuel.fuel_vendor_paymentdate}" /></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div> --%>
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
													<td class="value"><span id="amount"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Km/L :</th>
													<td class="value">100
														Km /Liter</td>
												</tr>
												<tr class="row">
													<th class="key">Cost per Km :</th>
													<td class="value"> <span id="costPerKM"></span>
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
													<td class="value"><span id="UreaReference"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Comment :</th>
													<td class="value"><span id="comment"></span>
													</td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								<c:if test="${!configuration.hideDriverAndRoute}">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Driver Info</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Driver Name :</th>
													<td class="value"><span id="firstDriver"></td>
												</tr>
												<tr class="row">
													<th class="key">Driver2 Name  :</th>
													<td class="value"><span id="secondDriver">
													</td>
												</tr>
												<tr class="row">
													<th class="key">Cleaner Name  :</th>
													<td class="value"><span id="cleaner">
													</td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								</c:if>
								
								<c:if test="${configuration.showFilledLocation}">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Filled Info</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Filled Location :</th>
													<td class="value"><span id="filledLocation"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Filled by  :</th>
													<td class="value"><span id="filledBy"></span>
													</td>
												</tr>
												<tr class="row">

											</tbody>
										</table>
									</div>
								</div>
								
								</c:if>
								
								
							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span> </small> | <small class="text-muted"><b>Created
						date: </b> <span id="createdOnStr"></span></small> | <small
					class="text-muted"><b>Last updated by :</b> <span id="lastModifiedBy"></span></small> | <small class="text-muted"><b>Last
						updated date:</b> <span id="lastModifiedStr"></span></small>
			</div>
		</sec:authorize>
	</section>
</div>
<script src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureaentries/showUreaEntry.js"></script>
<script src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureaentries/UreaEntriesUtility.js"></script>