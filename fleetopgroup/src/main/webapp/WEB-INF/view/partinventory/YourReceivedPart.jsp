<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a>
				</div>
				<div class="pull-right"></div>
			</div>
		</div>
	</section>
	<section class="content">
		<!-- Modal -->
		<div class="modal fade" id="editTyreRetreadNumber" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="<c:url value="/savetransferInventoryRec.in"/>"
						method="POST" name="vehicleType">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Receive Transfer
								Quantity</h4>
								<input type="hidden" name="showNotifyUser" id="showNotifyUser"
												value="${configuration.showNotifyUser}">
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Part Name
									:<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" class="form-text" value=""
										required="required" name="inventory_transfer_id"
										id="inventory_transfer_id" />
									<!-- Tyre Serial Num -->
									<input type="hidden" class="form-text"  value=""
										required="required" name="transfer_receivedby_ID" 
										id="transfer_receivedby_ID">
									<input type="text" class="form-text" value=""
										id="transfer_partnumber" readonly="readonly"
										required="required" /> <label id="errorvType" class="error"></label>
								</div>
							</div> <br>
							<div class="row">
								<label class="L-size control-label" id="Type">Receive
									Quantity :<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<!-- Amount Num -->
									<input type="text" class="form-text" value=""
										name="transfer_quantity" readonly="readonly"
										required="required"
										id="transfer_quantity">
								</div>
							</div>
							<br>
							<div class="row">
								<label class="L-size control-label" id="Type">Receive
									Remark :<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<!-- Amount Num -->
									<input type="text" class="form-text" data-toggle="tip"
										data-original-title="Renewal Remark" value=""
										name="transfer_receivedReason" required="required"
										id="transfer_receivedReason">
								</div>
							</div>
							<br />
							<input type="hidden" id="INVRID" name="INVRID">
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
														<br/>
													</c:if>
													<br/>
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
														<br/>
														<div class="row1">
															<label class="L-size control-label">Notification
																Msg :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<textarea class="form-text" name="alertMsg" id=alertMsg
																	 maxlength="1499" rows="3" cols="3"></textarea>
															</div>
														</div>
													</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">
								<span id="Save" onclick="return validateNotification();">Receive</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	<!-- Reject Model -->

		<div class="modal fade" id="rejectModel" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="VehicleType1">Reject Transfer Quantity</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type">Part Name
								:<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
							<input type="hidden" id="TransferID">
							<input type="hidden" id="partNumber">
								<input type="text" class="form-text" value=""
									id="rejectedPartName" readonly="readonly"
									required="required" /> <label id="errorvType" class="error"></label>
							</div>
						</div> <br>
						<div class="row">
							<label class="L-size control-label" id="Type">Reject
								Quantity :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<!-- Amount Num -->
								<input type="text" class="form-text" value=""
									name="rejectQuantity" readonly="readonly"
									id="rejectQuantity">
							</div>
						</div>
						<br>
						<div class="row">
							<label class="L-size control-label" id="Type">Reject
								Remark :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<!-- Amount Num -->
								<input type="hidden" class="form-text" data-toggle="tip"
									data-original-title="Renewal Remark"  id="receivedBy">
								<input type="text" class="form-text" data-toggle="tip" id="rejectRemark"
									data-original-title="Renewal Remark" >
							</div>
						</div>
						<br />
					</div>
						<div class="modal-footer">
							<button type="submit" onclick="return rejectPartRequisition();"class="btn btn-danger">
								<span id="Save">Reject</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>		
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="main-body">
					<div class="box">

						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" ><a
										href="<c:url value="/YTIHISTORY/1.in"/>"> TRANSFER PARTS</a></li>

									<li role="presentation" class="active"><a
										href="<c:url value="/YTIRECEIVEDHISTORY/1.in"/>"> RECEIVED
											PARTS</a></li>

								</ul>
							</div>
						</div>
					</div>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="InventoryTable_Location" class="table table-striped">
									<thead>
										<tr>
											<th>Id</th>
											<th>Part</th>
											<th>Transfer From &amp; To</th>
											<th>Quantity</th>
											<th>Transfer_By / T-Date</th>
											<th>Received By</th>
											<th>RECEIVE PART</th>
											<th>REJECT PART</th>

										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty InventoryTransfer}">
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${InventoryTransfer}"
												var="InventoryTransfer">
												<tr>
													<td>
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>

													<td><a
														href="<c:url value="/showDetailsInventory.in?inventory_id=${InventoryTransfer.transfer_inventory_id}"/>"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"><c:out
																value="${InventoryTransfer.transfer_partnumber}" /><br>
															<c:out value="${InventoryTransfer.transfer_partname}" /></a></td>
													<td><c:out
															value="${InventoryTransfer.transfer_from_location}" /> <br>
														<c:out
															value="${InventoryTransfer.transfer_to_location}   " /></td>


													<td><span class="label label-info"><c:out
																value="${InventoryTransfer.transfer_quantity}" /></span><br>
														<c:out value=" ${InventoryTransfer.transfer_description}" /></td>
													<td><c:out value="${InventoryTransfer.transfer_by}" />
														<c:out value=" -In- ${InventoryTransfer.transfer_via}" /><br>
														<c:out value=" ${InventoryTransfer.transfer_date}" /></td>
													<td><c:out
															value="${InventoryTransfer.transfer_receivedby}" /><br>
														<c:out value=" ${InventoryTransfer.transfer_receiveddate}" /></td>
													<td>
														<sec:authorize access="hasAuthority('RECEIVED_PURCHASE')">
														<c:choose>
															<c:when
																test="${InventoryTransfer.TRANSFER_STATUS =='RECEIVED' }">
																<span class="label label-success"><c:out
																		value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
															</c:when>
															<c:when
																test="${InventoryTransfer.TRANSFER_STATUS =='REJECTED' }">
																<span class="label label-success"><c:out
																		value="" /></span>
															</c:when>
															<c:otherwise>
																<a
																	onclick="javascript:edit_RenewaLAmount('${InventoryTransfer.inventory_transfer_id}', '${InventoryTransfer.transfer_partnumber}',  '${InventoryTransfer.transfer_partname}', '${InventoryTransfer.transfer_quantity}','${InventoryTransfer.transfer_receivedby_ID}', '${InventoryTransfer.INVRID}');"
																	id="editTyreSerialInput"
																	class="btn btn-success btn-sm"> <span
																	class="fa fa-download"></span> Receive Parts
																</a>
															</c:otherwise>
														</c:choose>
														</sec:authorize>
													</td>
													<td>
													
													<input type="hidden" id="transferID" name="transferName" value="${InventoryTransfer.inventory_transfer_id}">
													<input type="hidden" id="transferStatusID_${InventoryTransfer.inventory_transfer_id}" name="transferStatus" value="${InventoryTransfer.TRANSFER_STATUS_ID}">
														<c:choose>
															<c:when
																test="${InventoryTransfer.TRANSFER_STATUS =='RECEIVED'}">
																<span class="label label-Danger"><c:out
																		value="" /></span>
															</c:when>
															<c:when
																test="${InventoryTransfer.TRANSFER_STATUS =='REJECTED'}">
																<span class="label label-danger"><c:out
																		value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
															</c:when>
															<c:otherwise>
															<a id="rejectRequisition_${InventoryTransfer.inventory_transfer_id}" 
															 onclick="setRejectRequisitionDetails('${InventoryTransfer.inventory_transfer_id}', '${InventoryTransfer.transfer_partnumber}',  '${InventoryTransfer.transfer_partname}', '${InventoryTransfer.transfer_quantity}',  '${InventoryTransfer.transfer_receivedby_ID}');"
																class="btn btn-danger btn-sm hide" > <span
																class="fa fa-upload"></span> Reject Part
															</a>
															
															</c:otherwise>
															</c:choose>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/YTIRECEIVEDHISTORY/1" />
					<c:url var="lastUrl"
						value="/YTIRECEIVEDHISTORY/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/YTIRECEIVEDHISTORY/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/YTIRECEIVEDHISTORY/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/YTIRECEIVEDHISTORY/${i}" />
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
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript">
		function edit_RenewaLAmount(inventory_transfer_id, transfer_partnumber,
				transfer_partname, transfer_quantity, transfer_receivedby_ID, INVRID) {
			document.getElementById("inventory_transfer_id").value = inventory_transfer_id;
			document.getElementById("transfer_partnumber").value = transfer_partnumber
					+ " " + transfer_partname;
			document.getElementById("transfer_quantity").value = transfer_quantity;
			document.getElementById("transfer_receivedby_ID").value = transfer_receivedby_ID;
			document.getElementById("INVRID").value = INVRID;

			$("#editTyreRetreadNumber").modal();
		}
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/part/yourReceivedPart.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/UserNotifications.js" />"></script>	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>	
</div>