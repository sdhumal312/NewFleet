<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/PartRequisition/${SelectStatus}/${SelectPage}.in"/>">Part
						Requisition</a> / <span id="NewVehi">Show Inventory</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchAllInventory.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="partnumber" type="text" required="required"
									placeholder="Part NO/Name"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm" data-toggle="modal"
										data-target="#processing-modal">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a class="btn btn-link"
						href="<c:url value="/PartRequisition/${SelectStatus}/${SelectPage}.in"/>">Cancel</a>
					<sec:authorize access="hasAuthority('VIEW_QRCODE_INVENTORY')">
						<a href="<c:url value="/InventoryQRscan.in"/>"
							class="btn btn-success"><span class="fa fa-search">
								Scan Again</span></a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_REQUISITION_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_REQUISITION_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<c:if test="${param.success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Part Requisition Created Successfully.
						</div>
					</c:if>
					<c:if test="${param.saveTransferInventory eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Inventory Quantity Transfer Successfully.
						</div>
					</c:if>

					<div class="box">
						<div class="boxinside">
							<div class="row">
								<div class="pull-left">
									<span>Requisition Number :
										IR-${InvRequisition.INVRID_NUMBER}</span>
								</div>
								<div class="pull-right">
									<span>Created Date : ${InvRequisition.CREATED_DATE}</span>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<c:out value="${InvRequisition.REQUITED_LOCATION}" />
								</h4>
							</div>
							<div class="secondary-header-title">
								<table class="table">
									<tbody>
										<tr>
											<td>Send By : <a data-toggle="tip"
												data-original-title="Send By"> <c:out
														value="${InvRequisition.REQUITED_SENDNAME}" /></a></td>

											<td>Requited Date :<a data-toggle="tip"
												data-original-title="Requited Date"><c:out
														value="${InvRequisition.REQUITED_DATE}" /></a></td>
										</tr>
										<tr>
											<td>Reference No : <a data-toggle="tip"
												data-original-title="Fixed Point"> <c:out
														value="${InvRequisition.REQUITED_NUMBER}" /></a></td>
											<td>Assign To : <a data-toggle="tip"
												data-original-title="Volume Point"> <c:out
														value="${InvRequisition.REQUISITION_RECEIVEDNAME}" /></a></td>
										</tr>
										<tr>
											<td colspan="2">Required For Vehicle : <a target="_blank" href="showVehicle?vid=${InvRequisition.VID}" ><c:out
													value="${InvRequisition.vehicle_registration}" /> </a></td>
										</tr>
										<tr>
											<td colspan="2">Remarks : <c:out
													value="${InvRequisition.REQUITED_REMARK}" /></td>
										</tr>
									</tbody>
								</table>

							</div>
							<fieldset>
								<legend>Requisition Parts</legend>
								<div class="row">
								<input type="hidden" id="isPartPendingQtyZero" name="isPartPendingQtyZero" value="${isPartPendingQtyZero}">
									<div class="">
										<table class="table">
											<thead>
												<tr class="breadcrumb">
													<th class="fit">ID</th>
													<th class="fit">Part</th>
													<th class="fit ar">Quantity</th>
													<th class="fit ar">transfer Quantity</th>
													<th class="fit ar">Pending Quantity</th>
													<th class="fit ar">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty InvReqPart}">
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${InvReqPart}" var="InvReqPart">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><a
																href="<c:url value="/showMasterParts.in?partid=${InvReqPart.PART_ID}"/>"
																data-toggle="tip" data-original-title="part details"><c:out
																		value="${InvReqPart.partnumber}" /> <c:out
																		value="${InvReqPart.partname}" /></a></td>

															<td class="fit ar"><c:out
																	value="${InvReqPart.PART_REQUITED_QTY}" /></td>

															<td class="fit ar"><c:out
																	value="${InvReqPart.PART_TRANSFER_QTY}" /></td>
															<td class="fit ar"><c:out
																	value="${InvReqPart.PART_PENDING_QTY}" /></td>
															<td class="fit ar"><c:choose>
																	<c:when
																		test="${InvRequisition.REQUISITION_STATUS_ID == 1}">
																		<sec:authorize
																			access="hasAuthority('DELETE_INVENTORY')">
																			<a
																				href="<c:url value="/RemovePartRequisition.in?ID=${InvReqPart.INVRID}&&RID=${InvReqPart.INVRPARTID}" />"
																				data-toggle="tip" data-original-title="Click Remove"><font
																				color="red"><i class="fa fa-times">
																						Remove</i></font></a>
																		</sec:authorize>
																	</c:when>
																	<c:when
																		test="${InvRequisition.REQUISITION_STATUS_ID == 2}">
																		<sec:authorize
																			access="hasAuthority('TRANSFER_INVENTORY')">
																			<a class="btn btn-default btn-sm"
																				href="<c:url value="/transferInventoryReq.in?PARTID=${InvReqPart.PART_ID}&&REQPID=${InvReqPart.INVRPARTID}" />"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to Transfer Part?')">
																				<span class="fa fa-exchange"></span>
																			</a>
																		</sec:authorize>
																	</c:when>
																</c:choose></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
								<c:if test="${InvRequisition.REQUISITION_STATUS_ID == 1}">
									<div class="row">

										<fieldset>
											<legend>
												<a class=" btn btn-link "
													onclick="visibility('addMoreEnter', 'cancelMoreEnter');">
													<i class="fa fa-plus"> Required Add More Parts </i>
												</a>
											</legend>
											<div class="I-size" id="cancelMoreEnter"></div>
											<div id="addMoreEnter" class="contact_Hide">
												<form id="formPartInventory"
													action="<c:url value="/saveREQADDMORE.in"/>" method="post"
													enctype="multipart/form-data" name="formPartInventory"
													role="form" class="form-horizontal">
													<input type="hidden" name="INVRID"
														value="${InvRequisition.INVRID}">

													<div class="form-horizontal ">
														<div class="box">
															<div class="box-body">
																<div class="panel panel-success">
																	<div class="panel-body">
																		<div class="row1" id="grpinventoryPart"
																			class="form-group">
																			<label class="L-size control-label" for="searchpart">Search
																				Parts Number :<abbr title="required">*</abbr>
																			</label>
																			<div class="I-size">
																				<input type="hidden" id="searchpart_show"
																					name="partid_many" style="width: 100%;"
																					placeholder="Please Enter 2 or more Part Name or Part Number" />
																				<span id="inventoryPartIcon" class=""></span>
																				<div id="inventoryPartErrorMsg" class="text-danger"></div>
																			</div>
																		</div>
																		<div class="row1" id="grpquantity" class="form-group">
																			<label class="L-size control-label" for="quantity">Quantity
																				:</label>

																			<div class="I-size">
																				<input type="text" class="form-text"
																					name="quantity_many" min="0.0" id="quantity"
																					maxlength="4" placeholder="ex: 23.78"
																					required="required" data-toggle="tip"
																					data-original-title="enter Part Quantity"
																					onkeypress="return isNumberKey(event,this);"
																					ondrop="return false;"> <span
																					id="quantityIcon" class=""></span>
																				<div id="quantityErrorMsg" class="text-danger"></div>

																			</div>
																		</div>
																		<div class="row1">
																			<div class="input_fields_wrap_show">
																				<button class="add_field_button_show btn btn-info"
																					data-toggle="tip"
																					data-original-title="Click add one more part">
																					<i class="fa fa-plus"></i> Add More Parts
																				</button>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<fieldset class="form-actions">
															<div class="row1">
																<div class="col-md-10 col-md-offset-2">
																	<button type="submit" class="btn btn-success">Save
																		Requisition Parts</button>

																	<a class=" btn btn-link col-sm-offset-1"
																		onclick="visibility('addMoreEnter', 'cancelMoreEnter');">
																		<i class="fa fa-minus"> Cancel</i>
																	</a>

																</div>
															</div>
														</fieldset>
													</div>
												</form>
											</div>
										</fieldset>
									</div>

									<div class="row">
										<form action="<c:url value="/SentPartRequisition.in"/>"
											method="post">

											<input type="hidden" name="INVRID"
												value="${InvRequisition.INVRID}">
											<div class="form-horizontal">
												<fieldset>
													<legend>Sent Requisition Use</legend>
													<div class="row1">
														<label class="L-size control-label">Requisition
															Remarks :<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<textarea class="form-text" name="REQUITED_REMARK"
																required="required" maxlength="240" rows="2" cols="3">${InvRequisition.REQUITED_REMARK}</textarea>
														</div>
													</div>
													<div class="box-footer h-padded">
														<fieldset class="col-md-offset-3">
															<input class="btn btn-success"
																data-disable-with="Saving..." name="commit"
																type="submit" value="Sent Requisition"> <a
																class="btn btn-link"
																href="<c:url value="/PartRequisition/1/1.in"/>">Cancel</a>
														</fieldset>
													</div>
												</fieldset>
											</div>
										</form>

									</div>
								</c:if>
								<c:if test="${InvRequisition.REQUISITION_STATUS_ID == 2}">
									<div class="row">
										<form action="<c:url value="/SentPartTransfer.in"/>"
											method="post">

											<input type="hidden" name="INVRID"
												value="${InvRequisition.INVRID}">
											<div class="form-horizontal">
												<fieldset>
													<legend>Transfer Completed Use</legend>
													<div class="row1">
														<label class="L-size control-label">Transfer
															Remarks :<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<textarea class="form-text" name="REQUITED_REMARK"
																required="required" maxlength="240" rows="2" cols="3">${InvRequisition.REQUITED_REMARK}</textarea>
														</div>
													</div>
													<div class="box-footer h-padded">
														<fieldset class="col-md-offset-3">
															<input class="btn btn-success"
																data-disable-with="Saving..." name="commit"
																type="submit" value="Transfer Completed"  onclick="return confirmComplete();"> <a
																class="btn btn-link"
																href="<c:url value="/PartRequisition/1/1.in"/>">Cancel</a>
														</fieldset>
													</div>
												</fieldset>
											</div>
										</form>

									</div>
								</c:if>
								<c:if test="${InvRequisition.REQUISITION_STATUS_ID == 3}">
									<div class="row">
										<form action="<c:url value="/SentPartCompleted.in"/>"
											method="post">
											
											<input type="hidden" name="INVRID"
												value="${InvRequisition.INVRID}">
											<input type="hidden" name="showNotifyUser" id="showNotifyUser"
												value="${configuration.showNotifyUser}">
											<div class="form-horizontal">
												<fieldset>
													<legend>Completed Created User:</legend>
													<div class="row1">
														<label class="L-size control-label">Completed
															Remarks :<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<textarea class="form-text" name="REQUITED_REMARK"
																required="required" maxlength="240" rows="3" cols="3">${InvRequisition.REQUITED_REMARK}</textarea>
														</div>
													</div>
													
													<c:if test="${configuration.showNotifyUser}">
														<div class="row1" id="extendedTrip">
															<label class="L-size control-label">Notify User :</label>
															<div class="I-size">
																	<div class="">
																		<div class="btn-group" id="status" data-toggle="buttons" onchange="setNotificationAlertMsg();">
																			<label class="btn btn-default btn-on btn-lg">
																				<input type="radio" value="0" name="notifyUser"
																				id="isNotifyYes">Yes
																			</label> <label class="btn btn-default btn-off btn-lg active"> <input
																				type="radio" value="1" name="notifyUser" id="isNotifyNo" checked="checked"">No
																			</label>
																		</div>
																	</div>
																</div>
														</div>
													</c:if>
													<div id="notificationRow" style="display: none;">
														<div class="row1">
															<label class="L-size control-label">Notify To  :</label>
															<div class="I-size">
															 <input type="hidden" id="user" value="${InvRequisition.REQUISITION_RECEIVER_ID}">
															 <input type="hidden" id="userName" value="${InvRequisition.REQUISITION_RECEIVEDNAME}">
																<input type="text" id="userId" name="userId" 
																	   style="width: 100%;"  placeholder="Please Select" />
																<label id="errorDriver1" class="error"></label>
															</div>
														</div>
														<div class="row1">
															<label class="L-size control-label">Notification
																Msg :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<textarea class="form-text" name="alertMsg" id=alertMsg
																	 maxlength="1499" rows="3" cols="3">${InvRequisition.REQUITED_REMARK}</textarea>
															</div>
														</div>
													</div>
													<div class="box-footer h-padded">
														<fieldset class="col-md-offset-3">
															<input class="btn btn-success"
																data-disable-with="Saving..." name="commit"
																type="submit" value="Completed User" onclick="return validateNotification(event);"> <a
																class="btn btn-link"
																href="<c:url value="/PartRequisition/1/1.in"/>">Cancel</a>
														</fieldset>
													</div>
												</fieldset>
											</div>
										</form>

									</div>
								</c:if>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryReqValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/UserNotifications.js" />"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#location").select2();
			$("#addMoreEnter").hide();
			$("#tagPicker").select2({
				closeOnSelect : !1
			});
			$('#userId').select2('data', {
			        id : $('#user').val(),
			        text : $('#userName').val()
		    });
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>