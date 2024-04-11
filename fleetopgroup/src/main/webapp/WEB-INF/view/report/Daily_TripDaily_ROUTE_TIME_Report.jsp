<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<style>
.closeAmount th, td {
	text-align: center;
}

.closeRouteAmount td {
	text-align: center;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: center;
	font-weight: bold;
}

.actualkm {
	float: center;
}

.columnDaily {
	text-align: center;
}
</style>
<script>
function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}
</script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TDR.in"/>">Trip Collection Report</a> / <span>Daily
						Trip Collection Time Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Daily Trip Collection Route Time Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<div style="display: inline-block; width: 100px"></div>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	
			<section class="content">
				<sec:authorize access="hasAuthority('VIEW_DE_RO_TI_REPORT')">
					<div class="panel box box-primary">
						<!-- <div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionTimeRoute">Depot Wise Route Time
									Report </a>
							</h4>
						</div> -->
						<!-- <div id="TripCollectionTimeRoute" class="panel-collapse collapse"> -->
						
							<div class="box-body">
								<c:if test="${configuration.subrouteService}">
									<form action="DailyTripDailyRouteTimeReport" method="post">
								</c:if>	
								<c:if test="${!configuration.subrouteService}">
									<form action="DailyTripDailyRouteTimeReport2" method="post">
								</c:if>		
									<div class="form-horizontal ">
									<c:if test="${configuration.subrouteService}">
										<div class="row1">
											<label class="L-size control-label"> Depot Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" value="0" id="TCGroupWise2" name="VEHICLEGROUP"
													style="width: 100%;" value="ALL"
													placeholder="Please Enter 2 or more Group Name"  />
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label"> Route
												Service : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
													<select style="width: 100%;" name="ROUTE_ID"
														id='RouteList'>
													<option value="0"></option>
													</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> Sub Route
												Service : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
													<select style="width: 100%;" name="SUB_ROUTE_ID"
														id='RouteSubList'>
															<option value="0"></option>
													</select>
											</div>
										</div>
										</c:if>
										<c:if test="${!configuration.subrouteService}">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Sub Route
												Service :
											 	</label>
													<div class="I-size" id="vehicleSelect">
														<div class="col-md-9">
															<input type="hidden" id="TripRouteSubList1" name="SUB_ROUTE_ID1"
															style="width: 100%;" value="0"
															placeholder="Please Enter 2 or more Vehicle Name" />
														</div>
														<label id="errorVehicle" class="error"></label>
													</div>
											</div>
										</c:if>
										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">From Date: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="monthRangeSelector" class="form-text"
														name="FROM_DATERANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
											<label class="L-size control-label">To Date: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="monthRangeSelector2" class="form-text"
														name="TO_DATERANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										

										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success" onclick="return validateDepotTripWiseReport()">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
													<c:if test="${!empty TDRoute}">
													<div class="pull-right">
														<div style="display: inline-block; width: 100px"></div>
														<button class="btn btn-default btn-sm"
															onclick="printDiv('div_print')">
															<span class="fa fa-print"> Print</span>
														</button>
														<button class="btn btn-default btn-sm"
															onclick="advanceTableToExcel('advanceTable', 'Fleetop')">
															<span class="fa fa-file-excel-o"> Export to Excel</span>
														</button>
													</div>
													</c:if>
												</div>
											</div>
										</fieldset>
									</div>
									</form>
									
									
							</div>
						<!-- </div> -->
					</div>
				</sec:authorize>
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<c:if test="${!empty subRouteList}">
										<c:forEach items="${subRouteList}" var="subRouteList">
											<li role="presentation" id="${subRouteList.routeID}"><a
												href="<c:url value="DailyTripDailyRouteTimeReport?ROUTE_ID=${ROUTE_ID}&SUB_ROUTE_ID=${subRouteList.routeID}&VEHICLEGROUP=${VEHICLEGROUP}&FROM_DATERANGE=${TRIP_DATERANGE}&TO_DATERANGE=${TO_DATERANGE}"/>">${subRouteList.routeName}</a></li>

										</c:forEach>
									</c:if>
									
								</ul>
							</div>
						</div>
						<input type="hidden" value="${SUB_ROUTE_ID}" id="statues">
					</div>
	<c:if test="${!empty TDRoute}">
	<section class="content" id="content">
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
					<div id="div_print">
						<c:if test="${!empty TDRoute}">
							<div id="div_print">

								<section class="invoice">
									<div class="row invoice-info">
										<div class="col-xs-12">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<td><c:choose>
																	<c:when test="${company.company_id != null}">
																		<img
																			src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
																			class="img-rounded " alt="Company Logo" width="280"
																			height="40" />

																	</c:when>
																	<c:otherwise>
																		<i class="fa fa-globe"></i>
																		<c:out value="${company.company_name}" />
																	</c:otherwise>
																</c:choose></td>
															<td>Print By:
																${company.firstName}_${company.lastName}</td>
														</tr>
														<tr>
															<td colspan="2">Branch :<c:out
																	value=" ${company.branch_name}  , " /> Department :<c:out
																	value=" ${company.department_name}" />
															</td>
														</tr>
													</tbody>
												</table>

												<div id="sorttable-div" align="center"
													style="font-size: 10px" class="table-responsive ">
													<div class="row invoice-info">
														<table style="width: 95%">
															<tbody>
																<tr>
																	<td align="center"><span class="text-bold">
																			<c:out value="${VEHICLE_NAME}" /> Daily Trip Time
																			Report <c:out value=" - ${SEARCHDATE}" />
																	</span></td>
																</tr>
															</tbody>
														</table>
														<table id="advanceTable" style="width: 95%"
															class="table table-hover table-bordered table-striped">
															<thead>
																<tr>
																	<th>No</th>
																	<th>DATE</th>
																	<th>Vehicle</th> ${TDINCOME_NAME}
																	<th>Total.K.M</th>
																	<th>Diesel</th>
																	<c:if test="${configuration.showDieselAmount}">
																		<th>Diesel Amt</th>
																	</c:if>

																	<th>KMPL</th>

																	<c:if test="${configuration.showRfidPass}">
																		<th>RFID Pass</th>
																	</c:if>
																	<c:if test="${configuration.showRifdAmnt}">
																		<th>RFID Amt</th>
																	</c:if>

																	<th>Collection</th>
																	<!-- <th>Net Collection</th> -->
																	<th>Expense</th>
																	<th>Balance</th>
																	<!-- <th>Balance</th> -->

																</tr>
															</thead>
															<tbody>
																<%
														Integer hitsCount = 1;
													%>


																<c:forEach items="${TDRoute}" var="TDRoute">

																	<tr data-object-id="" class="closeAmount">
																		<td class="fit">
																			<%
																		out.println(hitsCount);
																					hitsCount += 1;
																	%>
																		</td>
																		<td><a target="_blank"
																			href="showTripDaily.in?ID=${TDRoute.TRIPDAILYID}"><c:out
																					value="${TDRoute.TRIP_OPEN_DATE}" /></a></td>
																		<td><a target="_blank"
																			href="showTripDaily.in?ID=${TDRoute.TRIPDAILYID}"><c:out
																					value="${TDRoute.VEHICLE_REGISTRATION}" /></a></td>
																		${TDRoute.CREATEDBY}
																		<td><c:out value="${TDRoute.TRIP_USAGE_KM}" /></td>
																		<td><c:out value="${TDRoute.TRIP_DIESEL}" /></td>

																		<c:if test="${configuration.showDieselAmount}">
																			<td><c:out value="${TDRoute.TRIP_DIESEL_AMOUNT}" />
																			</td>
																		</c:if>

																		<td><c:out value="${TDRoute.TRIP_DIESELKMPL}" /></td>
																		<c:if test="${configuration.showRfidPass}">
																			<td><c:out value="${TDRoute.TRIP_RFIDPASS}" />
																			</td>
																		</c:if>
																		<c:if test="${configuration.showRifdAmnt}">
																			<td><c:out value="${TDRoute.TRIP_RFID_AMOUNT}" />
																			</td>
																		</c:if>

																		<td><c:choose>

																				<c:when test="${configuration.hideActualCollection}">
																					<c:out value="${TDRoute.TOTAL_INCOME_COLLECTION}" />
																				</c:when>

																				<c:otherwise>
																					<c:out value="${TDRoute.TOTAL_INCOME}" />
																				</c:otherwise>
																			</c:choose></td>

																		<%-- <td>  <c:out value="${TDRoute.TOTAL_INCOME_COLLECTION}" /> </td> --%>

																		<td><c:out value="${TDRoute.TOTAL_EXPENSE}" /></td>

																		<td><c:choose>

																				<c:when test="${configuration.diffBalanceFormula}">
																					<c:out value="${TDRoute.TOTAL_NET_BALANCE}" />
																				</c:when>

																				<c:otherwise>
																					<c:out value="${TDRoute.TOTAL_BALANCE}" />
																				</c:otherwise>
																			</c:choose></td>

																		<%-- <td>
																<c:out value="${TDRoute.TOTAL_BALANCE}" />
																</td> --%>



																	</tr>

																</c:forEach>

																<tr data-object-id="" class="closeGroupAmount">
																	<td colspan="3"><c:out value="Total :" /></td>
																	${TDINCOME_COLUMN}
																	<td><c:out value="${TRIP_USAGE_KM}" /></td>
																	<td><c:out value="${TRIP_DIESEL}" /></td>

																	<c:if test="${configuration.showDieselAmount}">
																		<td><c:out value="${totalDieselAmount}" /></td>
																	</c:if>

																	<td><c:out value="${TRIP_DIESELKMPL}" /></td>
																	<c:if test="${configuration.showRfidPass}">
																		<td><c:out value="${TRIP_RFIDPASS}" /></td>
																	</c:if>
																	<c:if test="${configuration.showRifdAmnt}">
																		<td><c:out value="${TRIP_RFID_AMOUNT}" /></td>
																	</c:if>
																	<td><c:choose>

																			<c:when test="${configuration.hideActualCollection}">
																				<c:out value="${TOTAL_INCOME_COLLECTION}" />
																			</c:when>

																			<c:otherwise>
																				<c:out value="${TOTAL_INCOME}" />
																			</c:otherwise>
																		</c:choose></td>

																	<%-- <td><c:out value="${TOTAL_INCOME_COLLECTION}" /></td> --%>
																	<td><c:out value="${TOTAL_EXPENSE}" /></td>

																	<td><c:choose>

																			<c:when test="${configuration.diffBalanceFormula}">
																				<c:out value="${TOTAL_NET_BALANCE}" />
																			</c:when>

																			<c:otherwise>
																				<c:out value="${TOTAL_BALANCE}" />
																			</c:otherwise>
																		</c:choose></td>

																	<%-- <td>
															<c:out value="${TOTAL_NET_BALANCE}" />
																																						
															</td> --%>

																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</section>
							</div>
						</c:if>						
						<c:if test="${empty TDRoute}">
						<script>
							$(".invoice").addClass("hide");	
							setTimeout(function(){validateReport(); }, 500);
						</script>											
						</c:if>				
					</div>
				</sec:authorize>

			</section>
	</c:if>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripRouteFixedAdd.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateDepotTripWiseReport.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask();
			$('#monthRangeSelector').Monthpicker({
				monthLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
			});
			$('#monthRangeSelector2').Monthpicker({
				monthLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
			});
		})
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			if(document.getElementById("statues") != null){
				
				var e = document.getElementById("statues").value;
				switch (e) {
				case "ALL":
					document.getElementById("All").className = "active";
					break;
				case e:
					document.getElementById(e).className = "active"
				}
				if(${empty TDRoute}){
					showMessage('info','No record found !');
				}
			}
		}),$("#TripRouteSubList1").select2({
		       ajax: {
		           url: "getTripRouteSubList.in",
		           dataType: "json",
		           type: "POST",
		           contentType: "application/json",
		           data: function(e) {
		               return {
		                   term: e
		               }
		           },
		           results: function(e) {
		               return {
		                   results: $.map(e, function(e) {
		                       return {
		                           text: e.routeNo + " " + e.routeName,
		                           slug: e.slug,
		                           id: e.routeID
		                       }
		                   })
		               }
		           }
		       }
		   });
		
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
	</script>
</div>