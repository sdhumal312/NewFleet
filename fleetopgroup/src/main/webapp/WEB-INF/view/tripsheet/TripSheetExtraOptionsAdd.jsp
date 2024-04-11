<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
									href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Show
									TripSheet</a> / <small>Add Expense TripSheet</small>
							</div>
							<div class="pull-right"></div>
						</div>
						<sec:authorize access="!hasAuthority('ADD_EXPENSE_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
							<div class="row">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripSheet.created}</h5>
									</div>
								</div>

								<div class="row">
									<h4 align="center">
										<a href="showVehicle.in?vid=${TripSheet.vid}"
											data-toggle="tip" data-original-title="Click Vehicle Info">
											<c:out value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>
								<div class="col-md-3"></div>
							</div>
							<div class="row">
								<h4 align="center">${TripSheet.routeName}</h4>
							</div>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <c:out
												value="${TripSheet.tripOpenDate}  TO" /></a> <a
										data-toggle="tip" data-original-title="Trip Close Date"> <c:out
												value="  ${TripSheet.closetripDate}" /></a></li>
									<li>Group Service : <a data-toggle="tip"
										data-original-title="Group Service"><c:out
												value="${TripSheet.vehicle_Group}" /></a></li>
									<li>Booking No : <a data-toggle="tip"
										data-original-title="Booking No"> <c:out
												value="${TripSheet.tripBookref}" /></a></li>
									<li>Driver 1 : <a data-toggle="tip"
										data-original-title="Driver 1"> <c:out
												value="${TripSheet.tripFristDriverName}" /></a></li>
									<li>Driver 2 : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripSheet.tripSecDriverName}" /></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><c:out
												value="${TripSheet.tripCleanerName}" /></a></li>
									<li>Opening KM : <a data-toggle="tip"
										data-original-title="Opening KM"><c:out
												value="${TripSheet.tripOpeningKM}" /></a></li>
									<li>Closing KM : <a data-toggle="tip"
										data-original-title="closing KM"> <c:out
												value="${TripSheet.tripClosingKM}" /></a></li>
								</ul>
							</div>
							<br>
							
							<table class="table">
									<tbody>
									
									<tr>
										 <script> console.log("Hello")</script>
											<td><c:if test="${!empty TripSheetExtraOptions}"> 
                                           <script> console.log("Hi")</script>
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Extra Name</th>
																<th class="fit ar">Quantity</th>
																<th class="fit ar">Description</th>
																<th class="fit ar">Extra Date</th>
																<th class="fit ar">Action</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															  <c:forEach items="${TripSheetExtraOptions}"
																var="TripSheetExtraOptions">

																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetExtraOptions.tripsheetextraname}" /></td>
																	<td class="fit ar">
																	<c:out value="${TripSheetExtraOptions.tripSheetExtraQuantity}" />
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetExtraOptions.tripsheetextradescription}" /></td>
																			
																	<td class="fit ar"><c:out
																			value="${TripSheetExtraOptions.created}" /></td>
																	<td class="fit ar"><a
																href="removeExtra.in?tripExtraID=${TripSheetExtraOptions.tripExtraID}"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>		
																</tr>
 

															</c:forEach>  
															
														</tbody>
													</table>
												 </c:if> 
												</td> 
										</tr>
									
									</tbody>
									</table>
							
							<form action="updateExtraOptions.in" method="post">
								<input type="hidden" name="TripSheetID"
									value="${TripSheet.tripSheetID}">
								<div class="form-horizontal">

									<fieldset>
										<legend>New Extra Details</legend>
										<div class="row1">
											<label class="L-size control-label"></label>

											<div class="col-md-9">
												<div class="col-md-4">
													<label class="control-label" >Extra Name</label>

												</div>
												<div class="col-md-2">
													<label class="control-label">Extra Quantity</label>

												</div>
												<div class="col-md-2">
													<label class="control-label">Description</label>
												</div>
											</div>
										</div>
										<div class="row1">
											<div class="col-md-4">
												<select class="form-text select2" style="width: 100%;" 
													name="tripsheetextraname" id="Extra" required="required">

												</select>
											</div>
											<div class="col-md-3">
												<input type="number" class="form-text" name="TripSheetExtraQuantity"
													placeholder="Quantity" min="0" required="required">
											</div>
											<div class="col-md-3">
												<input type="text" class="form-text" name="TripSheetExtraDescription"
													placeholder="Description" >
											</div>
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-success">
													<i class="fa fa-plus"></i>
												</button>
											</div>
										</div>
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..."
												name="commit" type="submit" value="Save Extra"> <a
												class="btn btn-link"
												href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
							</form>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetExtraOptions.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
</div>