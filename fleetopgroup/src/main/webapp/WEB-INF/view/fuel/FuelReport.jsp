<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a> / <a
						href="<c:url value="/FuelReport"/>">Fuel Report</a> / <small>Search
						Fuel Entry</small>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
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
					<sec:authorize access="hasAuthority('VIEW_FUEL')">
						<a href="#" id="exportReportFuelTable" class="btn btn-default"> <i
							class="fa fa-file-excel-o"> Export</i>
						</a>
					</sec:authorize>
					<a class="btn btn-default" href="<c:url value="/Fuel/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_FUEL')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<!-- /.box-header -->
						<div class="box-body">
							<table id="ReportFuelTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">ID</th>
										<th>Vehicle</th>
										<th class="fit ar">Ownership</th>
										<!-- <th class="fit ar">Group</th> -->
										<th>Driver</th>
										<th>Date</th>
										<th class="fit ar">Opening</th>
										<th class="fit ar">Closing</th>
										<!-- <th class="fit ar">Usage</th> -->
										<th class="fit ar">Volume</th>
										<th class="fit ar">Amount</th>
										<th class="fit ar">FE &amp; Cost</th>
										<!-- <th class="fit ar">Cost</th> -->
										<th class="fit ar">Ref</th>
										<th class="fit ar">Status</th>
										<th class="fit ar">Act.</th>
									</tr>
								</thead>
								<tbody>

									<c:if test="${!empty fuel}">
										<c:forEach items="${fuel}" var="fuel">

											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a target="_blank" 
													href="showFuel.in?FID=${fuel.fuel_id}"
													data-toggle="tip" data-original-title="Click Fuel Details"><c:out
															value="FT-${fuel.fuel_Number}" /></a></td>

												<td><a target="_blank" href="showFuel.in?FID=${fuel.fuel_id}"
													data-toggle="tip" data-original-title="Click Fuel Details"><c:out
															value="${fuel.vehicle_registration}" /> </a></td>
												<td class="fit ar"><c:out
														value="${fuel.vehicle_Ownership}" /> <br> <c:out
														value="${fuel.vehicle_group}" /></td>

												<%-- <td class="fit ar"><c:out value="${fuel.vehicle_group}"/></td> --%>
												<td><a target="_blank"  href="showDriver.in?driver_id=${fuel.driver_id}"
													data-toggle="tip" data-original-title="Driver empnumber"><c:out
															value="${fuel.driver_empnumber}" /> </a> <br> <c:out
														value="${fuel.driver_name}" /></td>
												<td><c:out value="${fuel.fuel_date}" /><br>
													<h6>
														<a
															<%-- href="ShowVendor.in?vendor_id=${fuel.vendor_id}" --%>
												data-toggle="tip"
															data-original-title="Vendor Name"> <c:out
																value="${fuel.vendor_name}" />-( <c:out
																value="${fuel.vendor_location}" /> )
														</a>
													</h6></td>
												<td class="fit ar"><c:out
														value="${fuel.fuel_meter_old}" /></td>
												<td class="fit ar"><c:out value="${fuel.fuel_meter}" /></td>
												<td class="fit ar"><abbr data-toggle="tip"
													data-original-title="Liters"><c:out
															value="${fuel.fuel_liters}" /></abbr> <c:if
														test="${fuel.fuel_tank_partial==1}">
														<abbr data-toggle="tip"
															data-original-title="Partial fuel-up"> <i
															class="fa fa-adjust"></i>
														</abbr>
													</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
												<td class="fit ar"><i class="fa fa-inr"></i> <c:out
														value="${fuel.fuel_amount}" /> <br> <abbr
													data-toggle="tip" data-original-title="Price"> <c:out
															value="${fuel.fuel_price}/liters" />
												</abbr></td>
												<td class="fit ar"><abbr data-toggle="tooltip"
													data-placement="right" data-original-title="Per Liters "><c:out
															value="${fuel.fuel_kml} " /> <c:if
															test="${fuel.fuel_kml != null}">
													Km/L
													</c:if></abbr><br> <abbr data-toggle="tooltip"
													data-placement="right" data-original-title="Per Km Cost">
														<c:out value="${fuel.fuel_cost} " /> <c:if
															test="${fuel.fuel_cost != null}">
													/Km
													</c:if>
												</abbr></td>
												<td class="fit ar"><c:out
														value="${fuel.fuel_reference}" /></td>
												<td><c:choose>
														<c:when test="${fuel.fuel_vendor_paymode == 'PAID'}">
															<span class="label label-pill label-success"><c:out
																	value="${fuel.fuel_vendor_paymode}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-danger"><c:out
																	value="${fuel.fuel_vendor_paymode}" /></span>
														</c:otherwise>
													</c:choose></td>
												<td class="fit ar">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-cog"></span> <span class="caret"></span>
														</a>
														<ul class="dropdown-menu pull-right">
															<li><sec:authorize access="hasAuthority('EDIT_FUEL')">
																	<a href="FuelEntriesEdit?Id=${fuel.fuel_id}"> <i
																		class="fa fa-edit"></i> Edit
																	</a>
																</sec:authorize></li>
															<li><sec:authorize access="hasAuthority('DELETE_FUEL')">
																<c:choose>
																	<c:when test="${fuel.fuel_TripsheetID > 0}">
																		<a
																			href="<c:url value="/removeTSFuel.in?TSID=${fuel.fuel_TripsheetID}&FID=${fuel.fuel_id}"/>">
																			<i class="fa fa-trash"> Delete</i></a>
																	
																	</c:when>
																	<c:otherwise>
																		<a href="deleteFuel.in?FID=${fuel.fuel_id}"> <i
																			class="fa fa-trash"></i> Delete
																		</a>
																	</c:otherwise>
																</c:choose>
																</sec:authorize></li>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<th class="fit ar" colspan="5">Total Amount:</th>
										<th class="fit " colspan="3"><i class="fa fa-inr"></i>
											${fuelTotalAmount}</th>
									</tr>

								</tfoot>
							</table>
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
	<script type="text/javascript">
	$(document).ready(function() {
	    $("#ReportFuelTable").DataTable( {
	        sScrollX:"100%", bScrollcollapse:!0, dom:"Blfrtip", 
	        buttons:["excel", {
	                             extend: "print",
	                             exportOptions: {
	                            	 columns: [':visible :not(:last-child)']
	                             }, 
	                             message: "<h3>This Page produced By Fleetop. Fuel Report</h3>"
	                          }
	        ], order:[[0, "desc"]], aoColumnDefs:[ {
	            bSortable: !1, aTargets: [2, 3, 4, 7, 8, 9]
	        }
	        ], lengthMenu:[[10, 50, 500, -1], [10, 50, 500, "All"]]
	    }
	    )
	}

	);
	</script>
</div>