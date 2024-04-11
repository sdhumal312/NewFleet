<%@ include file="../../taglib.jsp"%>
<script>

</script>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/BatteryInventory.in"/>">Battery Inventory</a>
					/ <a href="<c:url value="/ScrapBatteryFilter"/>">Scrap Filter
						Inventory</a> / <span>New Scrap Battery </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/ScrapBatteryFilter"/>"
						class="btn btn-danger btn-sm"><span class="fa fa-search">
							Scrap Filter</span></a> <a class="btn btn-link btn-sm"
						href="<c:url value="/BatteryInventory.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<!-- body -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_BATTERY_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_BATTERY_INVENTORY')">
			<div class="row">
				<div class="main-body">
				
		<div class="modal fade" id="editrenewalPeriod" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Battery Scrap Remark</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" id="tempId" />
									<fieldset>
							<legend>Scrap Reason</legend>
							<div class="box">
								<div class="box-body">
									<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<textarea class="form-text" id="scrapRemark"
													name="advanceRemarks" maxlength="1000">
														
													</textarea>
											</div>
										</div>
									<br>
								</div>
							</div>
						</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save" onclick="saveScrapRemark();">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
				
					<h4>Create Scrap Battery List</h4>
					<div class="box">
						<div class="box-body">
							<c:if test="${!empty Battery}">
								<sec:authorize access="!hasAuthority('ADD_BATTERY_SCRAP')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_BATTERY_SCRAP')">
									<form id="frm-example" action="saveBatteryScrapInfo.in"
										method="POST">
										<div class="form-horizontal ">
											<fieldset>
												<div class="col-md-10 col-md-offset-1">
													<table id="VendorApplovalList"
														class="table table-bordered table-striped">
														<thead>
															<tr>
																<th class="fit"><!-- <input name="select_all" value="1"
																	id="example-select-all" type="checkbox" /> --></th>
																<th class="fit ar">Battery NO</th>
																<th class="fit ar">Manufacturer</th>
																<th class="fit ar">Model</th>
																<th class="fit ar">Usage</th>
																<th class="fit ar">Capacity</th>
																<th class="fit ar">Location</th>
																<th class="fit ar">Status</th>
																<th Class="fit ar">Reason</th>
															</tr>
														</thead>
														<tfoot>
															<tr>
																<th class="fit"></th>
																<th class="fit ar">Battery NO</th>
																<th class="fit ar">Manufacturer</th>
																<th class="fit ar">Model</th>
																<th class="fit ar">Usage</th>
																<th class="fit ar">Capacity</th>
																<th class="fit ar">Location</th>
																<th class="fit ar">Status</th>
																<th Class="fit ar">Reason</th>
															</tr>
														</tfoot>
														<tbody id="vendorList">
															<c:forEach items="${Battery}" var="Battery">
																<tr>
																	<td class="fit"><c:if
																			test="${Battery.batteryStatusId == 1}">
																			<input name="batteryIds" value="${Battery.batteryId}"
																				id="example_${Battery.batteryId}" onclick="checkForWarrantyStatus(this,${Battery.warrantyStatusId});" type="checkbox" />
																		</c:if></td>

																	<td class="fit ar"><a
																		href="showBatteryInformation.in?Id=${Battery.batteryId}"
																		data-toggle="tip"
																		data-original-title="Click Tyre Inventory INFO"><c:out
																				value="${Battery.batterySerialNumber}" /></a></td>

																	<td class="fit ar"><a
																		href="showBatteryInformation.in?Id=${Battery.batteryId}"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${Battery.manufacturerName}" /> </a></td>
																	<td class="fit ar"><c:out
																			value="${Battery.batteryType}" /></td>
																	<td class="fit ar"><c:out
																			value="${Battery.usesNoOfTime}" /></td>
																	<td class="fit ar"><c:out
																			value="${Battery.batteryCapacity}" /></td>
																	<td class="fit ar"><c:out
																			value="${Battery.locationName}" /></td>
																	<td class="fit ar"><c:out
																			value="${Battery.batteryStatus}" /></td>
																			
																	 <td> <div class="fit ar" width=100% >
																			<textarea   class="text optional form-text"
																			id="initial_note_${Battery.batteryId}"   
																			name="ScrapReason"  style="display:none; rows="2">
									                                 </textarea>
																		</div></td> 
																	<%-- <input name="batteryScrapRemark" value="${Battery.batteryId}"
																				id="batteryScrapRemark_${Battery.batteryId}" type="hidden" /> --%>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
												<div class="row1">
													<label class="L-size control-label" for="issue_description">
														Scrap Description :</label>
													<div class="I-size">
														<textarea class="text optional form-text"
															id="initial_note" name="ScrapDescription" rows="3">
				                                 </textarea>
													</div>
													
												</div>
												<!-- <div class="row1">
													<label class="L-size control-label" for="issue_description">
														Scrap Reason :</label>
													<div class="I-size" width=100% >
														<textarea  class="text optional form-text"
															id="initial_note1" name="ScrapDescription" rows="2">
				                                 </textarea>
													</div>
												</div> -->
											</fieldset>
											<fieldset class="form-actions">
												<div class="row">
													<div class="col-md-10 col-md-offset-2">
														<input class="btn btn-success" type="submit" 
															value="Scrap Battery"> <a class="btn btn-link"
															href="ScrapBatteryFilter">Cancel</a>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</sec:authorize>
							</c:if>
							<c:if test="${empty Battery}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>

		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovallanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.columnFilter.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryTyreValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/validateBatteryScrap.js" />"></script>
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#terms").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>
