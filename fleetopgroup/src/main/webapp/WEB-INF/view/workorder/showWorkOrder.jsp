<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />	
<div class="content-wrapper">
	
	<section class="content-header">
			<input type="hidden" id="addPartToken" value="${accessPartToken}">
			<input type="hidden" id="addLabourToken" value="${accessLabourToken}">
			<input type="hidden" id="showVehicleHealthStatus" value="${configuration.showVehicleHealthStatus}">
			<input type="hidden" id="addWOCompletionRemark" value="${configuration.addWOCompletionRemark}">
			<input type="hidden" id="addHoldRemark" value="${configuration.addHoldRemark}">
			<input type="hidden" id="addInprocessRemark" value="${configuration.addInprocessRemark}">
			<input type="hidden" id="driverId" value="${WorkOrder.workorders_driver_id}">
			<input type="hidden" id="driverName" value="${WorkOrder.workorders_drivername}">
			<input type="hidden" id="assigneeId" value="${WorkOrder.assigneeId}">
			<input type="hidden" id="assigneeName" value="${WorkOrder.assignee}">
			<input type="hidden" id="allTaskCOmpleted" value="${allTaskCOmpleted}">
			<input type="hidden" id="taskForIssueMandatory" value="${configuration.taskForIssueMandatory}">
			<input type="hidden" id="remarkForIssueMandatory" value="${configuration.remarkForIssueMandatory}">
			<input type="hidden" id="allPartAssigned" value="${allPartAssigned}">
			<input type="hidden" id="tyreAssginFromWOConfig" value="${configuration.tyreAssginFromWO}">
			<input type="hidden" id="tyreAssignmentJobType" value="${configuration.tyreAssignmentJobType}">
			<input type="hidden" id="tyreAssignmentSubJobType" value="${configuration.tyreAssignmentSubJobType}">
			<input type="hidden" id="accessIssueTaskToken" value="${accessIssueTaskToken}">
			<input type="hidden" id="showAllTaskCompleteOption" value="${configuration.showAllTaskCompleteOption}">
			<input type="hidden" id="showPartCategoriesList" value="${configuration.showPartCategoriesList}">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
						<a href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> /
						<span id="AllVehicl">Open Work Orders</span>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">WO-</span></span>
								<input class="form-text" id="searchByNumber"
									name="Search" type="number" min="1" required="required"
									placeholder="WO-NO eg:6878"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn" onclick="return serachWoByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>	
									</button>
								</span>
							</div>
						</div>
					</div>
					<div class="pull-right">
							<c:if test="${configuration.showAllTaskCompleteOption}">
								<a class="btn btn-info" href="#" onclick="markAllTaskCompletd();">Mark All Task Completed</a>
							</c:if>
							<c:if test="${configuration.showMakeApproval && WorkOrder.approvalStatusId <= 0}">
								<a class="btn btn-success" id="makeApproval" href="#" onclick="makeWorkOrderApproval();">Make Approval</a>
							</c:if>
						<a class="btn btn-danger" href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<div class="box">
			<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
				<input type="hidden" id="workOrderId" value="${WorkOrder.workorders_id}">
				<input type="hidden" id="vehicleNumber" value="${WorkOrder.vehicle_registration}">
				<div class="row">
					<div class="col-md-6">
						<h4>
							Work Order ${WorkOrder.workorders_Number } <span
								class="label label-pill label-warning"><c:out
									value="${WorkOrder.priority}" /></span>
						</h4>
						<h4>
							<a href="showVehicle.in?vid=${WorkOrder.vehicle_vid}"
								data-toggle="tip" data-original-title="Click Vehicle Info">
								<c:out value="${WorkOrder.vehicle_registration}" />
							</a>
						</h4>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-user"> Assignee:</span> <a
									data-toggle="tip" data-original-title="Assignee "><c:out
											value="${WorkOrder.assignee}" /></a></li>
								<li><i class="fa fa-bitcoin"> Start date:</i> <a
									data-toggle="tip" data-original-title="Start date"><c:out
											value="${WorkOrder.start_date}" /></a></li>
								<li><span class="fa fa-user"> Due date:</span> <a
									data-toggle="tip" data-original-title="Due date"><c:out
											value="${WorkOrder.due_date}" /></a></li>
								<li><span class="fa fa-user"> Indent | PO No:</span> <a
									data-toggle="tip" data-original-title="Indent | PO No"><c:out
											value="${WorkOrder.indentno}" /></a></li>
								<c:if test="${configuration.TallyCompanyMasterInWO}">			
									<li><span class="fa fa-user"> Tally Company Master:</span> <a
										data-toggle="tip" data-original-title="Tally Company Master"><c:out
												value="${WorkOrder.tallyCompanyName}" /></a></li>
								</c:if>
								<c:if test="${!configuration.multiIssueInWO}">
								<c:if test="${WorkOrder.issueNumber != null}">			
									<li><span class="fa fa-exclamation-circle"> Issue Number : </span> <a
										data-toggle="tip" data-original-title="${WorkOrder.issueSummary }"><c:out
												value="I-${WorkOrder.issueNumber} - ${WorkOrder.issueSummary }" /></a></li> 
								</c:if> 
								</c:if>
							</ul>
						</div>
					</div>
					
					<div class="col-md-5">
						<div class="row">
							
							<input type="hidden" id="statues" name="statues" value="${WorkOrder.workorders_status}">
							<input type="hidden" id="woId" value="${WorkOrder.workorders_id}">
							<input type="hidden" id="subLocationId" value="${WorkOrder.subLocationId}">
							<input type="hidden" id="subLocation" value="${WorkOrder.subLocation}">
							<input type="hidden" id="showSubLocation" value="${showSubLocation}">
							<input type="hidden" id="issueRemarkStatus" value="${issueRemarkStatus}">
							<input type="hidden" id="issueTaskStatus" value="${issueTaskStatus}">
							
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<a data-disable-with="..." data-method="post"
										data-remote="true" rel="nofollow"> <span id="status-open"
										class="status-led"> <i class="fa fa-circle"></i>
											<div class="status-text">Open</div>
										</span>
									</a> 
										<sec:authorize access="hasAuthority('INPROCESS_WORKORDER') ">
									<a data-method="post" data-remote="true"
										onclick="return changeStatusToInProgress(${WorkOrder.workorders_id});" rel="nofollow">
										 <span id="status-in-progress" class="status-led"> <i class="fa fa-circle"></i>
											<div class="status-text">In Progress</div>
										</span>
									</a>
											</sec:authorize>
									<sec:authorize access=" hasAuthority('ONHOLD_WORKORDER') ">
										<a data-disable-with="..." data-method="post" data-remote="true"
											 onclick="return changeStatusToHold(${WorkOrder.workorders_id});" rel="nofollow">
											<span id="status-on-hold" class="status-led">  <i class="fa fa-circle"></i>
											 <div class="status-text">On Hold</div>
											</span> 
										</a>
									</sec:authorize>
								</div>
								<div id="status-close">
								<input type="hidden" id="photoPendingForAnyPart" value="${photoPendingForAnyPart}">
									<a class="btn btn-success" data-disable-with="..." 
									onclick="return changeStatusToComplete(${WorkOrder.workorders_id});">Complete 
									</a>	
								</div>
								<div id="status-close">
									<button type="button" class="btn btn-default"
										data-toggle="modal" data-target="#addworkorderDocument"
										data-whatever="@mdo">
										<i class="fa fa-upload"></i>
									</button>
									<sec:authorize access="hasAuthority('DOWNLOAD_WORKORDER')">
										<c:choose>
										    <c:when test="${companyWisePrint}">
										    	<input type="button" class="btn btn-default fa fa-print" 
										    		onclick="getCompanyWiseWorkOrderPrint(${WorkOrder.workorders_id});" 
										    		value="Print" />
										    </c:when>    
										    <c:otherwise>
										    	<a style="width: 35%"
												href="PrintWorkOrders?id=${WorkOrder.workorders_id}"
												target="_blank" class="btn btn-default "><i
												class="fa fa-print"></i> Print</a>
										    </c:otherwise>
										</c:choose>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
				<br>
				
				<fieldset>
					<div class="row">
						<div class="col-md-11">
							<div class="table-responsive">
								
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th>Service Number</th>
											<c:if test="${configuration.showIssueInWO}">
											<th>Issue</th>
											</c:if>
											
											<c:if test="${configuration.showPartCategoriesList}">
												<th>Category Type</th>
											</c:if>
											
											<th class="fit"></th>
											<th>Task</th>
											<th class="fit ar">Parts Cost</th>
										<c:if test="${!configuration.removeFeildFromLaborTab || configuration.showLabourTotalCost}">
											<th class="fit ar">Labour Cost</th>
										</c:if>
											<th class="fit ar">Total</th>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty WorkOrderTask}">
											<c:forEach items="${WorkOrderTask}" var="WorkOrderTask">
												<tr data-object-id="" class="ng-scope">
														<td>
															<c:if test="${WorkOrderTask.service_Number == null}">
																	<c:out value="-"> </c:out>
															</c:if>
															<c:if test="${WorkOrderTask.service_Number != null}">
																<a title="Related To Service Program : ${WorkOrderTask.vehicle_registration }" href="ShowService.in?service_id=${WorkOrderTask.service_id}" target="_blank"> <span style="font-size: 12px;"> ${WorkOrderTask.service_Number} </span> </a>
 															</c:if>	
														</td>
													<c:if test="${configuration.showIssueInWO}">
														<td><c:if
																test="${WorkOrderTask.issueNumber != null && WorkOrderTask.issueNumber != 'null' && WorkOrderTask.issueNumber != ''}">
																<a
																	href="showIssues.in?Id=${WorkOrderTask.issueIdsEncry}"
																	target="_blank">I- ${WorkOrderTask.issueNumber}</a>

															</c:if></td>
													</c:if>
													
													<c:if test="${configuration.showPartCategoriesList}">
													      <td><c:out value="${WorkOrderTask.pcName}" /></td> 													
													</c:if>
													
													<td class="fit">
														<c:if test="${WorkOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if>
														<c:if test="${WorkOrderTask.mark_complete != 1}"> 
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if>
													</td>
												
													<td>
														<h4>
															<c:out value="${WorkOrderTask.job_typetask} - "></c:out>
															<c:out value="${WorkOrderTask.job_subtypetask}"></c:out>
														</h4>
														
														<c:if test="${showJobTypeRemarkCol}">															
															<h5>
																<b>Remark : </b><span id="workordertaskremark_${WorkOrderTask.workordertaskid}"><c:out value="${WorkOrderTask.jobTypeRemark}"></c:out></span>
																<c:if test="${showEditJobTypeRemark}">															
																	<a data-toggle="modal" data-target="#editTaskRemark_${WorkOrderTask.workordertaskid}" data-whatever="@mdo">Edit Remark</a>
																</c:if>
															</h5>
														</c:if>
														<div class="modal fade" id="editTaskRemark_${WorkOrderTask.workordertaskid}" role="dialog">
															<div class="modal-dialog">
																<div class="modal-content">
																		<div class="panel panel-default">
																			<div class="panel-heading clearfix">
																				<h3 class="panel-title">Edit Task Remark</h3>
																			</div>
																			<div class="panel-body">
																				<div class="form-horizontal">
																					<br>
																					<div class="row1">
																						<div class="L-size">
																							<label> Task Remark : </label>
																						</div>
																						<div class="I-size"> <!--latest-->
																							<textarea class="form-text"  name="taskRemark" id="taskRemark_${WorkOrderTask.workordertaskid}"
																								rows="3" maxlength="2240"  >${WorkOrderTask.jobTypeRemark}
																						</textarea>	
																						</div>
																					</div>
																				</div>
																				<div class="modal-footer">
																					<input class="btn btn-success"
																						onclick="editTaskRemark(${WorkOrderTask.workordertaskid})" name="commit"
																						value="Edit Remark"
																						data-dismiss="modal">
																					<button type="button" class="btn btn-link"
																						data-dismiss="modal">Close</button>
																				</div>
																			</div>
																		</div>
																</div>
															</div>
														</div>
														
														
														<div class="modal fade" id="Popup" role="dialog">
															<div class="modal-dialog modal-md">
																<div class="modal-content">
																	<div class="modal-header">
																	<input type="hidden" id="wotaskTopartId">
																		<h4 class="modal-title">Document List</h4>
																	</div>
																	<div class="modal-body">
																		<table id="documentTable"
																			style="width: 100%; display: none;"
																			class="table-responsive table">

																		</table>

																		<div class="modal-footer">
																			<button class="btn btn-primary"
																				style="width: 50%; margin-left: 25%; margin-top: 20%;"
																				data-dismiss="modal">Close</button>
																		</div>

																	</div>
																</div>
															</div>
														</div>


														<div class="row">
															<c:choose>
																<c:when test="${WorkOrderTask.last_occurred_woId != 0}">
																	<samp style="color: red;">
																		Last occurred on ${WorkOrderTask.last_occurred_date}
																		this task on Odometer = ${WorkOrderTask.last_occurred_odameter}.
																		 <a	href="showWorkOrder?woId=${WorkOrderTask.last_occurred_woId}"
																			target="_blank"><i class="fa fa-external-link"></i></a>
																	</samp>
																</c:when>
																<c:otherwise>
																	<samp style="color: blue;">Never logged for this task</samp>
																</c:otherwise>
															</c:choose>
														</div>
														<c:if test="${WorkOrderTask.mark_complete == null || WorkOrderTask.mark_complete == 0}">
															<div class="row">
																<input type="hidden" id="autoLabourAdd" value="${configuration.autoLabourAdd}">
																<input type="hidden" id="invoiceWisePartListConfig" value="${configuration.invoiceWisePartList}">
																<input type="hidden" id="inventoryId${WorkOrderTask.workordertaskid}" >
																
																<c:choose>
																	<c:when test="${configuration.tyreAssginFromWO == true && configuration.tyreAssignmentJobType eq WorkOrderTask.job_typetaskId && configuration.tyreAssignmentSubJobType eq WorkOrderTask.job_subtypetask_id }">
																		<a class="confirmation" onclick="return mountTyre(${WorkOrderTask.workordertaskid},${WorkOrderTask.job_typetaskId},${WorkOrderTask.job_subtypetask_id});" > Add Tyre | 
																		</a>
																	</c:when>
																	<c:otherwise>
																		<c:if test="${configuration.invoiceWisePartList}">
																			<a id="addParts${WorkOrderTask.workordertaskid}"
																				 onclick="getInvoiceWisePartList('${WorkOrderTask.workordertaskid}','${WorkOrder.workorders_location_ID}','${WorkOrder.subLocationId}');"
																				href="javascript:toggle2('changePart${WorkOrderTask.workordertaskid}','addParts${WorkOrderTask.workordertaskid}');"> Add Parts </a>
																		</c:if>
																	
																		<c:if test="${!configuration.invoiceWisePartList}">
																			<a id="addParts${WorkOrderTask.workordertaskid}" 
																				onclick="javascript:getInventoryListLoc('inventory_name${WorkOrderTask.workordertaskid}', '${WorkOrder.workorders_location}','${WorkOrder.subLocationId}','${WorkOrder.workorders_location_ID}');"
																				href="javascript:toggle2('changePart${WorkOrderTask.workordertaskid}','addParts${WorkOrderTask.workordertaskid}');"> Add Parts </a> | 
																		</c:if>
																	</c:otherwise>
																
																</c:choose>	
																	
																	
																	<a id="addLabor${WorkOrderTask.workordertaskid}" 
																		onclick="javascript:getTechinicionName('labername${WorkOrderTask.workordertaskid}');javascript:getROT_COST_Hour('${WorkOrderTask.job_subtypetask_id}', 'laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}' );"
																		href="javascript:toggle2Labor('changeLabor${WorkOrderTask.workordertaskid}','addLabor${WorkOrderTask.workordertaskid}');"> Add Labor</a> |
	
																<sec:authorize access="hasAuthority('COMPLETE_WORKORDER')">
																	<a id="addLabor${WorkOrderTask.workordertaskid}" 
																		onclick="markAsComplete(${WorkOrderTask.workordertaskid})"Mark Complete);
																		href="javascript:toggle2Labor('markAsComplete${WorkOrderTask.workordertaskid}','addLabor${WorkOrderTask.workordertaskid}');"> Mark Complete
																	</a>
																</sec:authorize>
															</div>
														</c:if>
														<c:if test="${WorkOrderTask.mark_complete == 1}">
															<div class="row">
																<sec:authorize access="hasAuthority('COMPLETE_WORKORDER')">
																			<a style="color: green" href="#">Task Completed</a>
																</sec:authorize>
															</div>
														</c:if>
														
														<div class="row">
														<input type="hidden" id="taskToPartDocument" value="${configuration.workOrderTaskToPartDocument}">
															<div class="col-md-11">
																<table class="table">
																	<c:if test="${!empty WorkOrderTaskPart}">
																			<thead>
																				<tr class="breadcrumb">
																					<th class="fit">Part</th>
																					<th class="fit">Qty</th>
																					<th class="fit">Each</th>
																					<th class="fit">Total</th>
																					<c:if test="${configuration.partsCreatedBy}">
																						<th class="fit">Part Added By</th>
																					</c:if>
																					<c:if test="${configuration.workOrderTaskToPartDocument}">
																						<th class="fit">Document</th>
																					</c:if>
																					<th class="fit">Actions</th>
																				</tr>
																			</thead>
																		<tbody>
																			<c:forEach items="${WorkOrderTaskPart}"
																				var="WorkOrderTaskPart">
																				<c:if test="${WorkOrderTaskPart.workordertaskid == WorkOrderTask.workordertaskid}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="icon">
																							<c:out value="${WorkOrderTaskPart.partname}" />
																							<c:out value="${WorkOrderTaskPart.partnumber}" />
																						</td>
																						<td class="fit">${WorkOrderTaskPart.quantity}</td>
																						<td class="fit"><i class="fa fa-inr"></i>
																							${WorkOrderTaskPart.parteachcost}
																						</td>
																						<td class="fit"><i class="fa fa-inr"></i>
																							${WorkOrderTaskPart.totalcost}
																						</td>
																						<c:if test="${configuration.workOrderTaskToPartDocument}">
																							
																							<td class="fit"></i> <c:if
																									test="${WorkOrderTaskPart.woPart_document}">
																									<a
																										onclick="getWOTaskToPartDoc('${WorkOrderTaskPart.workordertaskto_partid}')">Show
																										image </a>
																								</c:if>
																								<c:if test="${!WorkOrderTaskPart.woPart_document}"><a class="fa fa-exclamation-triangle" style="color: red;" onclick="showModaluploadWOTaskToPart('${WorkOrderTaskPart.workordertaskto_partid}')"> Upload document</a> </c:if>
																								</td>
																						</c:if>
																						<c:if test="${configuration.partsCreatedBy}">
																							<td class="value">${WorkOrderTaskPart.firstName}</td>
																						</c:if>
																						<td class="fit">
																							<div class="btn-group">
																								<a class="btn-sm dropdown-toggle"
																									data-toggle="dropdown" href="#"> <span
																									class="fa fa-cog"></span> <span class="caret"></span>
																								</a>
																								<ul class="dropdown-menu pull-right">
																									<c:if test="${configuration.workOrderTaskToPartDocument}">
																										<li><a
																											onclick="showModaluploadWOTaskToPart('${WorkOrderTaskPart.workordertaskto_partid}')"><span
																												class="fa fa-upload"></span>Upload File</a></li>
																									</c:if>
																									<li><sec:authorize access="hasAuthority('DELETE_WORKORDER')">
																											<a
																											class="confirmation"
																											onclick="return deletePartDetails('${WorkOrderTaskPart.workordertaskto_partid}','${WorkOrder.subLocationId}')">
																											<span class="fa fa-trash"></span> Delete
																										</a>
																									</sec:authorize></li>
																									<li><sec:authorize access="hasAuthority('ADD_WARRANTY_PARTS')">
																											<a
																											class="confirmation"
																											onclick="getWarrantyPartsListToSelect('${WorkOrderTaskPart.partid}','${WorkOrderTaskPart.quantity}', '${WorkOrderTaskPart.locationId}', '${WorkOrderTaskPart.workordertaskto_partid}','${WorkOrderTaskPart.assignedNoPart}')">
																											<span class="fa fa-plus"></span> Add Warranty/Asset Parts
																										</a>
																									</sec:authorize></li>
																								</ul>
																							</div>
																						</td>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</c:if>
																	<c:if test="${configuration.tyreAssginFromWO}">
																		<c:if test="${!empty tyreAssignment}">
																			<thead>
																				<tr class="breadcrumb">
																					<th class="fit">Tyre Position</th>
																					<th class="fit">Tyre Number</th>
																				</tr>
																			</thead>
																			<tbody>
																				<c:forEach items="${tyreAssignment}" var="tyreAssignment">
																					<c:if test="${tyreAssignment.transactionSubId == WorkOrderTask.workordertaskid}">
																						<tr data-object-id="" class="ng-scope">
																							<td class="fit"> ${tyreAssignment.POSITION} </td>
																							<td class="fit"> ${tyreAssignment.TYRE_SERIAL_NO} </td>
																						</tr>
																					</c:if>
																				</c:forEach>
																			</tbody>
																		</c:if>
																	</c:if>
																	<tbody style="border-top: 0px;">
																		<tr>
																			<td colspan="5">
																				<div class="">
																					<div id="changePart${WorkOrderTask.workordertaskid}" style="display: none">
																						<div class="row">
																							<div class="col-md-5">
																								<input type="hidden" name="workorders_id" id="lastworkorder_id" value="${WorkOrder.workorders_id}"> 
																								<input type="hidden" name="workordertaskid" value="${WorkOrderTask.workordertaskid}" id="workordertaskid">
																								<input type="hidden" name="partid" id="inventory_name${WorkOrderTask.workordertaskid}"
																								required="required" style="width: 100%;"
																								onchange="javascript:getQuantity('inventory_name${WorkOrderTask.workordertaskid}', 'parteachcost${WorkOrderTask.workordertaskid}', 'quantity${WorkOrderTask.workordertaskid}'); 
																								javascript:getlastdetails('${WorkOrderTask.job_typetaskId}','${WorkOrderTask.job_subtypetask_id}','${WorkOrder.vehicle_vid}', 'last_occurred${WorkOrderTask.workordertaskid}');"
																								placeholder="Please Enter 2 or more Part Name" />
																							</div>
																							<div class="col-md-1">
																								<input type="text" class="form-text" placeholder="Qty" name="quantity" value="0"
																								min="0" step="0.01" required="required" data-toggle="tip" data-original-title="Part Quantity" onkeyup="quantityValidation(${WorkOrderTask.workordertaskid});"
																				 				id="quantity${WorkOrderTask.workordertaskid}" onkeypress="return isNumberKeyWithDecimal(event,this.id);">
																							</div>
																							<div class="col-md-3">
																								<div class="row">
																									<input id="oldpart${WorkOrderTask.workordertaskid}" type="checkbox" name="oldpart"
																									class="L-size" value="RECEIVED" readonly="readonly">
																									<label class="I-size">Old Part Received </label>
																								</div>
																							</div>
																						</div>
																						<div class="help-block" id="last_occurred${WorkOrderTask.workordertaskid}">
																							<span class="loading ng-hide" id="loading">
																								<img alt="Loading" class="loading-img"
																								src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif"/>" >
																							</span>
																						</div>
																						<br>
																						<div class="row">
																							<div class="col-md-5 col-md-offset-2">
																								<input class="btn btn-success" type="submit" id="savePart" onclick="savePartDetails(${WorkOrderTask.workordertaskid});" value="Save Parts">
																							</div>
																						</div>
																					</div>
																				</div>
																			</td>
																		</tr>
																	</tbody>

																	<!-- labor cost add -->

																	<c:if test="${!empty WorkOrderTaskLabor}">
																		<thead>
																			<tr class="breadcrumb">
																				<th class="icon">Technician</th>
																				<th class="fit">Hours</th>
																			<c:if test="${!configuration.removeFeildFromLaborTab}">
																				<th class="fit">RateS</th>
																				<th class="fit">Dis</th>
																				<th class="fit">GST</th>
																			</c:if>
																			<c:if test="${!configuration.removeFeildFromLaborTab || configuration.showLabourTotalCost}">
																				<th class="fit">Total</th>
																			</c:if>
																				<th class="fit"></th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach items="${WorkOrderTaskLabor}" var="WorkOrderTaskLabor">
																				<c:if test="${WorkOrderTaskLabor.workordertaskid == WorkOrderTask.workordertaskid}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="icon">
																							<c:out value="${WorkOrderTaskLabor.labername}" />
																						</td>
																						<td class="fit">
																							${WorkOrderTaskLabor.laberhourscost}
																						</td>
																					<c:if test="${!configuration.removeFeildFromLaborTab}">
																						<td class="fit">
																							<i class="fa fa-inr"></i>${WorkOrderTaskLabor.eachlabercost}
																						</td>
																						<td class="fit">
																							${WorkOrderTaskLabor.laberdiscount} %
																						</td>
																						<td class="fit">
																							${WorkOrderTaskLabor.labertax} %
																						</td>
																					</c:if>
																					<c:if test="${!configuration.removeFeildFromLaborTab || configuration.showLabourTotalCost}">
																						<td class="fit"><i class="fa fa-inr"></i>
																							${WorkOrderTaskLabor.totalcost}
																						</td>
																					</c:if>
																						<td class="fit">
																							<div class="btn-group">
																								<a class="btn-sm dropdown-toggle"
																									data-toggle="dropdown" href="#"> <span
																									class="fa fa-cog"></span> <span class="caret"></span>
																								</a>
																								<ul class="dropdown-menu pull-right">
																									<li><sec:authorize access="hasAuthority('DELETE_WORKORDER')">
																										<a
																										class="confirmation"
																										onclick="return deleteLabourDetails(${WorkOrderTaskLabor.workordertaskto_laberid})">
																										<span class="fa fa-trash"></span> Delete
																									</a>
																									</sec:authorize></li>
																								</ul>
																							</div>
																						</td>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</c:if>

																	<input type="hidden" id="perHourDriverSalary" value="">
																	<input type="hidden" id="getPerDayWorkingHourForDriver" value="${configuration.getPerDayWorkingHourForDriver}">
																	
																	<tbody style="border-top: 0px;">
																		<tr data-object-id="" class="ng-scope">
																			<td colspan="6">
																				<div class="">
																					<div id="changeLabor${WorkOrderTask.workordertaskid}" style="display: none">
																						<div class="row">
																							<div class="col-md-3">
																								<input type="hidden" name="workorders_id" value="${WorkOrder.workorders_id}" id="workorders_id">
																								<input type="hidden" name="workordertaskid" value="${WorkOrderTask.workordertaskid}" id="workordertaskid">
																								<input id="labername${WorkOrderTask.workordertaskid}" type="text" style="width: 100%;"
																								   name="laberid" placeholder="Techinician Name" onchange="handleInputChange(this)">
																							</div>
																							<div class="col-md-1"> 
																								<input type="text" class="form-text" data-toggle="tip"  onpaste="return false"  required="required"
																								data-original-title="Laber work hour" name="laberhourscost" placeholder="time"
																								onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																								id="laberhourscost${WorkOrderTask.workordertaskid}"
																								onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');"
																								min="0.0">
																							</div>
																						<c:if test="${!configuration.removeFeildFromLaborTab}">
																							<div class="col-md-1"> 
																								<input type="text" name="eachlabercost" class="form-text" placeholder="cost" required="required" data-toggle="tip"  
																								data-original-title="Laber Cost" onpaste="return false"
																								onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																								id="eachlabercost${WorkOrderTask.workordertaskid}"
																								onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');">
																							</div>
																							<div class="col-md-1"> 
																								<input type="text" name="laberdiscount" class="form-text" placeholder="dis" value="0"  onpaste="return false"
																									required="required" data-toggle="tip" data-original-title="enter discount"
																									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																									id="laberdiscount${WorkOrderTask.workordertaskid}"
																									onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');">
																							</div>
																							<div class="col-md-1"> 
																								<input type="text" name="labertax" class="form-text" placeholder="GST"  onpaste="return false"
																									required="required" data-toggle="tip" data-original-title="enter GST" value="0"
																									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																									id="labertax${WorkOrderTask.workordertaskid}"
																									onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');">
																							</div>
																						</c:if>
																						<c:if test="${configuration.showLabourTotalCost}">
																							<div class="col-md-2">
																								<input type="text" name="totalcost" class="form-text" required="required"
																								data-toggle="tip" data-original-title="Laber Total Cost"
																								id="totalLaborcost${WorkOrderTask.workordertaskid}"
																								readonly="readonly">
																							</div>
																						</c:if>
																							<div class="fit"></div>
																						</div>
																						<br>
																						<div class="row">
																							<div class="col-md-5 col-md-offset-2">
																								<input class="btn btn-success" id="saveLabour" type="submit" onclick="saveLabourDetails(${WorkOrderTask.workordertaskid});"  value="Save Labor">
																							</div>
																						</div>
																					</div>
																				</div>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</td>
													<td class="fit ar"><i class="fa fa-inr"></i> 
														<c:out value="${WorkOrderTask.totalpart_cost}"></c:out>
													</td>
												<c:if test="${!configuration.removeFeildFromLaborTab}">
													<td class="fit ar"><i class="fa fa-inr"></i> 
														<c:out value="${WorkOrderTask.totallaber_cost}"></c:out>
													</td>
												</c:if>
													<td class="fit ar"><i class="fa fa-inr"></i> 
														<c:out value="${WorkOrderTask.totaltask_cost}"></c:out>
													</td>
													<td class="fit">
														<div class="btn-group">
															<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
																href="#"> <span class="fa fa-cog"></span> <span
																class="caret"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize access="hasAuthority('DELETE_WORKORDER')">
																		<a
																		class="confirmation"
																		onclick="return deleteTaskDetails(${WorkOrderTask.workordertaskid})">
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
									
									<tfoot>
										<tr class="breadcrumb">
											<th colspan="6"><a id="addwork" href="javascript:toggle2Task('changeWorkOrder','addwork');">
													Add Task</a>
											</th>
										</tr>
										<tr data-object-id="" class="ng-scope">
											<td colspan="6">
												<div class="">
													<div id="changeWorkOrder" style="display: none">
														<div class="row">
															<div class="col-md-6">
																<input type="hidden" name="workorders_id" value="${WorkOrder.workorders_id}" id="workorders_id">
																<input type="hidden" name="vehicle_vid" value="${WorkOrder.vehicle_vid}" id="vid">
																<input type="hidden" id="issueId">
																<samp id="issueMsg" style="color: blue; font-weight: bold;"></samp>
																<br>
																	<br>
																<div class="row1">
																	<label class="L-size control-label" for="priority">Job
																		Type 
																	</label>
																	<div class="I-size">
																		<input type="hidden" id="from" name="job_typetaskId"
																			style="width: 100%;" required="required"
																			placeholder="Please Enter 3 or more Job Type Name" />
																	</div>
																</div>
																<br>
																<div class="row1">
																	<label class="L-size control-label"
																		for="issue_description">Job Sub Type <abbr
																		title="required">*</abbr>
																	</label>
																	<div class="I-size">
																		<input type="hidden" id="to" name="job_subtypetask_id" style="width: 100%;"
																		 required="required" placeholder="Please Enter 3 or more Job Sub Type Name" />
																	</div>
																</div>
																<c:if test="${showJobTypeRemarkCol}">
																	<div class="row1 form-group jobTypeRemarkCol col-md-12">
																		<label class="L-size control-label" for="to">Job Type Remark</label>
																		<div class="I-size">
																			<div class="col-md-8">
																				<input type="text" class="form-text" id="taskRemark"
																					name="JobTypeRemark" maxlength="150" placeholder="Enter Remark" />
																			</div>
																		</div>
																	</div>																	
																</c:if>
																<div class="fit"></div>
															</div>
														</div>
														<c:if test="${configuration.showPartCategoriesList}">
															 <div class="row1 form-group jobTypeRemarkCol col-md-12">
																<label class="L-size control-label" for="categoryid" style="text-align:left">Category Type</label>
																	<!-- <input type="hidden" id="categoryId" value="0"> -->	
																<div class="I-size">
																	<!-- <div class="col-md-8">
																		<input type="text" readonly="readonly" id="categoryName" value="" class="form-text" >
																	</div> -->
																	<select class="form-text" name="categoryId" style="width: 70%; "    
																		id="categoryId">
																		<option value=""><!-- Please select --></option>
																		<c:forEach items="${PartCategories}" var="PartCategories">
																			<option value="${PartCategories.pcid}"><c:out
																					value="${PartCategories.pcName}" /></option>
																		</c:forEach>
																	</select>
																</div>
															</div>  
														</c:if>
														
														<br>
														<div class="row">
															<div class="col-md-5 col-md-offset-2">
																<input class="btn btn-success" type="submit" onclick="saveTaskDetails();" value="Save Task">
															</div>
														</div>
													</div>
												</div>
											</td>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
						<br>
							<div class="col-md-11">
						<div class="row">
						<c:if test="${!empty issueList}">
						<div class="table-responsive">
						<table class= "table table-hover table-bordered ">
								<thead>
									<tr>
										<th >Issue</th>
										<th >Reported Date</th>
										<th >Driver</th>
										<th >Assigned To</th>
										<th >Summary</th>
										<th >Add task</th>
									</tr>
									<tr>
								</thead>
								<tbody>
								<c:forEach items="${issueList}" var="issueList">
								<tr class="ng-scope">
								<td><a href="showIssues.in?Id=${issueList.ISSUES_ID_ENCRYPT}" target=_Blank><c:out value="${issueList.issuesNumberStr}"></c:out></a> </td>
								<td><c:out value="${issueList.ISSUES_REPORTED_DATE}"></c:out> </td>
								<td><c:out value="${issueList.ISSUES_DRIVER_NAME} _ ${issueList.driver_mobnumber}"></c:out> </td>
								<td><c:out value="${issueList.ISSUES_ASSIGN_TO_NAME}"></c:out> </td>
								<td><c:out value="${issueList.ISSUES_SUMMARY} "></c:out> </td>
								<td><c:if test="${issueList.workOrderTaskId != null && issueList.workOrderTaskId > 0}"><i class="fa fa-check" aria-hidden="true"></i></c:if><a href="javascript:toggle2Task('changeWorkOrder','addwork');" onclick="setIssueId(${issueList.ISSUES_ID},${issueList.ISSUES_NUMBER})">  Add Task</a></td>
						</tr>
							</c:forEach>
								</tbody>
							</table>
							</div>
						</c:if>
						
						
						</div>
						</div>
						<br>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<table class="table">
										<tbody>
										<c:if test="${WorkOrder.workorders_driver_id != null && WorkOrder.workorders_driver_id >0 }">
											<tr class="row">
												<th class="key">Driver Name :</th>
												<td class="value">
													<a href="showDriver.in?driver_id=${WorkOrder.workorders_driver_id}">${WorkOrder.workorders_drivername}</a>
												</td>
											</tr>
											</c:if>
											<c:if test="${configuration.showDriverNumberCol}">
												<tr class="row">
													<th class="key">Driver Number :</th>
													<td class="value">${WorkOrder.workorders_driver_number}</td>
												</tr>
											</c:if>
											<c:if test="${configuration.showOutWorkStationCol}">
												<tr class="row">
													<th class="key">Work Station :</th>
													<td class="value">${WorkOrder.out_work_station}</td>
												</tr>
											</c:if>
											<tr class="row">
											<c:if test="${!configuration.storeLocation}">
												<th class="key">Work Location :</th>
											</c:if>	
											<c:if test="${configuration.storeLocation}">
												<th class="key">Store Location :</th>
											</c:if>	
												<td class="value">${WorkOrder.workorders_location}
												<c:if test="${showSubLocation && WorkOrder.subLocation != null}">
												-(${WorkOrder.subLocation})
												</c:if>
												</td>
											</tr>
											<c:if test="${configuration.workLocation}">
												<tr class="row">
													<th class="key">Work Location :</th>
													<td class="value">${WorkOrder.workLocation}</td>
												</tr>
											</c:if>
											<c:if test="${configuration.showRouteCol}">
												<tr class="row">
													<th class="key">Route :</th>
													<td class="value">${WorkOrder.workorders_route}</td>
												</tr>
											</c:if>
											<tr class="row">
												<th class="key">Odometer :</th>
												<td class="value">${WorkOrder.vehicle_Odometer}</td>
											</tr>
											<c:if test="${WorkOrder.gpsWorkLocation != null}">
												<tr class="row">
													<th class="key">GPS Work Location :</th>
													<td class="value">${WorkOrder.gpsWorkLocation}</td>
												</tr>
											</c:if>
											<c:if test="${WorkOrder.gpsOdometer != null && WorkOrder.gpsOdometer > 0}">
												<tr class="row">
												<th class="key">GPS Odometer :</th>
												<td class="value">${WorkOrder.gpsOdometer}</td>
											</tr>
											</c:if>
											<c:if test="${configuration.showDieselCol}">
												<tr class="row">
													<th class="key">Diesel :</th>
													<td class="value">${WorkOrder.workorders_diesel}</td>
												</tr>
											</c:if>
											<tr class="row">
												<th class="key">Initial_Note :</th>
												<td class="value">${WorkOrder.initial_note}</td>
											</tr>
											<c:if test="${WorkOrder.workorders_statusId != workOrdersOnHoldId}">
											<tr class="row">
												<th class="key " Id="healthStatusId" ></th>
												<td class="value" Id="healthStatusName"></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showMakeApproval}">
												<tr class="row">
													<th class="key">Approval Status :</th>
													<td class="value" id="approvalStatus">${WorkOrder.approvalStatus}</td>
												</tr>						
											</c:if>									
										</tbody>
									</table>
								</div>
								
								<div class="col-md-3">
									<table class="table">
										<tbody>
											<tr class="row">
												<th class="key"><h4>SubTotal :</h4></th>
												<td class="value"><h4>
														<i class="fa fa-inr"></i>
														${WorkOrder.totalsubworktask_cost}
													</h4></td>
											</tr>
											<tr class="row">
												<th class="key"><h4>GST Cost :</h4></th>
												<td class="value"><h4>
														<a id="tax_cost" data-remote="true"
															href="javascript:toggle2Tax('work_order-tax_cost','tax_cost');">
															<i class="fa fa-inr"></i>
														</a> ${WorkOrder.totalworktax_cost}
													</h4>
													<div class="popup-edit hide-on-escape"
														id="work_order-tax_cost">
														<div class="row">
															<label class="control-label">GST Cost</label>
															<div class="input float optional work_order_tax_cost">
																<input autofocus="autofocus" class="form-text"
																	required="required" name="totalworktax_cost"
																	id="gstCost" step="any" type="number" value="0.00">
															</div>
														</div>
														<div class="row">
															<input class="btn btn-success" name="commit" onclick="updateWoGstCost();"
																type="submit" value="Apply">
														</div>
													</div>
												</td>
											</tr>
											<tr class="row">
												<th class="key"><h4>Total :</h4></th>
												<td class="value">
													<h4>
														<i class="fa fa-inr"></i> ${WorkOrder.totalworkorder_cost}
													</h4>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							
							<table class="table">
								<tfoot>
									<tr class="breadcrumb">
										<th colspan="6"><a href=""><i class="fa fa-plus"></i></a></th>
									</tr>
								</tfoot>
							</table>
							
						</div>
					</div>
				</fieldset>
			</sec:authorize>
		</div>
		
			<div class="modal fade" id="woTastToPartDoc" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
				
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">WorkOrder Document</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" id="workOrderTaskToPartId">
							<div class="row1">
								<div class="L-size">
									<label class="L-size control-label"> Browse: </label>
								</div>
								<div class="I-size">
									<input type="file" id="workOTaskToPartDocument" name="input-file-preview" multiple="multiple" required="required" />
								</div>
							</div>
							<br/>
							<br>
							<br>

						<div class="row1">
							<div class="L-size">
							<label class="L-size control-label">Description:</label>
							</div>
							<div class="I-size">
								<textarea class="form-text" id="description" name="description" rows="2"
									maxlength="150""> </textarea>
								
							</div>
						</div>
					</div>
					<br>
					<br>
						<div class="modal-footer">
							<input type="button" id="uploadSubmit" onclick="uploadFileWoTaskToPart()" value ="Upload" class="btn btn-primary">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
					
				</div>
			</div>
		</div>
		<div class="modal fade" id="addworkorderDocument" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post" enctype="multipart/form-data" id="fileUploadForm">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">WorkOrder Document</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="workorder_id"
								value="${WorkOrder.workorders_id}">
								
							<div class="row1">
								<div class="L-size">
									<label class="L-size control-label"> Browse: </label>
								</div>
								<div class="I-size">
									<input type="file" accept="image/png, image/jpeg, image/gif"
										name="input-file-preview" required="required" />
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" id="btnSubmit" class="btn btn-primary">
								<span>Upload</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="addWarrantyParts" role="dialog">
			<div class="modal-dialog" style="width: 780px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Warranty Part List</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" id="warrantyPartId">
							<input type="hidden" id="warrantyPartTaskId">
							<div>
								<a href="#">Already Assigned Parts</a>
							</div>
							<table class="table" id="assingedDataBody">
								<thead>
									<tr>
										<th>Sr </th>
										<th>Part Serial Number</th>
									</tr>
								</thead>
								<tbody id="assingedDataBody">
									
								</tbody>
							</table>
							<div class="warrantyData" style="display: none;">
								<a href="#">Available Parts</a>
							</div>
							<table class="table warrantyData" id="warrantyData" style="display: none;">
								<thead>
									<tr>
										<th>Select</th>
										<th>Part Serial Number</th>
										<th>Replace With</th>
									</tr>
								</thead>
								<tbody id="warrantyListBody">
									
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button id="saveAssign" type="submit" onclick="savePartWithWarrantyDetails();" class="btn btn-primary">
								<span >Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="completionRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">WorkOrder Completion Remark</h4>
						</div>
						<div class="modal-body">
						
<!-- 									<div class="row1"> -->
<!-- 											<label class="L-size control-label" for="issue_description">Confirmed With Driver -->
<!-- 												</label> -->
<!-- 											<div class="I-size"> -->
<!-- 				                                 <input type="hidden" id="confirmedWithDriver" style="width: 100%;"  /> -->
<!-- 											</div> -->
<!-- 										</div> <br/> -->
<!-- 										<div class="row1"> -->
<!-- 											<label class="L-size control-label" for="issue_description">Confirmed With Assignee -->
<!-- 												</label> -->
<!-- 											<div class="I-size"> -->
<!-- 												<input type="hidden" id="confirmedWithAssignee" style="width: 100%;" /> -->
<!-- 											</div> -->
<!-- 										</div><br/> -->
										<div class="row1">
											<label class="L-size control-label" for="issue_description">Remark
												</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="woRemark" name="woRemark"
																rows="3" maxlength="1000"></textarea>
											</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="completeWOWithRemark();" id="btnSubmit" class="btn btn-primary">
								<span>Complete WO</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="holdRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">WorkOrder Hold Remark</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
											<label class="L-size control-label" for="issue_description">Remark
												</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="woHoRemark" name="woHoRemark"
																rows="3" maxlength="1000"></textarea>
											</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="HoldWOWithRemark();" id="btnSubmit" class="btn btn-primary">
								<span>On Hold WO</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="issueRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">WorkOrder Issue Remark</h4>
					</div>
					<div class="modal-body">
					<input type="hidden" id="issueIdRemark" value="0">
						<div class="row1">
											<label class="L-size control-label" for="issue_description">Confirmed With Driver
												</label>
											<div class="I-size">
				                                 <input type="hidden" id="confirmedWithDriver" style="width: 100%;"  />
											</div>
										</div> <br/>
										<div class="row1">
											<label class="L-size control-label" for="issue_description">Confirmed With Assignee
												</label>
											<div class="I-size">
												<input type="hidden" id="confirmedWithAssignee" style="width: 100%;" />
											</div>
										</div><br/>
					
						<div class="row1">
							<label class="L-size control-label" for="issue_description">Remark
							</label>
							<div class="I-size">
								<script language="javascript" src="jquery.maxlength.js"></script>
								<textarea class="text optional form-text" id="woIssueRemark"
									name="woIssueRemark" rows="3" maxlength="1000"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" onclick="saveWOIssueRemark();" id="btnSubmit"
							class="btn btn-primary">
							<span>Save Remark</span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="inProcessRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">WorkOrder In process Remark</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
											<label class="L-size control-label" for="issue_description">Remark
												</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="woInProRemark" name="woInProRemark"
																rows="3" maxlength="1000"></textarea>
											</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="inProcessWOWithRemark();" id="btnSubmit" class="btn btn-primary">
								<span>In-process WO</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="row">
							<ul class="timeline">

								<c:if test="${!empty WORemarkList}">
									<c:forEach items="${WORemarkList}" var="WORemarkList">

										<!-- timeline time label -->
										<li class="time-label"> <span style="background-color: orange;">
										<c:if test="${WORemarkList.remarkTypeId == 1}">
											<i class="fa fa-check"></i> 
										</c:if>
										<c:if test="${WORemarkList.remarkTypeId == 2}">
											<i class="fa fa-circle-o"></i> 
										</c:if>
										<c:if test="${WORemarkList.remarkTypeId == 3}">
											<i class="fa fa-pause"></i> 
										</c:if>
										<c:if test="${WORemarkList.remarkTypeId == 4}">
											<i class="fa fa-spinner fa-spin"></i> 
										</c:if>
										
										${WORemarkList.remarkType}</span> <span class="bg-red">
												${WORemarkList.createdOn}</span>  
												<c:if test="${WORemarkList.assigneeId != null && WORemarkList.assigneeId > 0}"><span class="bg-orange">Confirmed With Assignee : ${WORemarkList.assigneeName}</span></c:if> 
												<c:if test="${WORemarkList.driverId != null && WORemarkList.driverId  > 0}"><span class="bg-orange">Confirmed With Driver : ${WORemarkList.driverName}</span></c:if>
												<c:if test="${WORemarkList.remarkTypeId == 6}"><span class="bg-orange">Issue Number :  I- ${WORemarkList.issueNumber}</span></c:if></li>
										<li><i class="fa fa-comments bg-yellow"></i>
											<div class="timeline-item">
												<div class="timeline-body">
													<c:out value="${WORemarkList.remark}" />
												</div>
											</div></li>

									</c:forEach>
								</c:if>
							</ul>
				</div>

		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out value="${WorkOrder.createdBy}" /></small> |
			<small class="text-muted"><b>Created date: </b> <c:out value="${WorkOrder.created}" /></small> |
			<small class="text-muted"><b>Last updated by :</b> <c:out value="${WorkOrder.lastModifiedBy}" /></small> |
			<small class="text-muted"><b>Last updated date:</b> <c:out value="${WorkOrder.lastupdated}" /></small>
		</div>
		
	</section>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>	
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/showWorkOrder.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/workOrderUtility.js" />"></script>	

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var input_Statues = document.getElementById('statues').value;
						var wrapperStatues = $("#work-order-statuses"); //Fields wrapper
						switch (input_Statues) {
						case "OPEN":
							document.getElementById('status-open').className = 'status-led-open';

							break;
						case "INPROCESS":
							if(document.getElementById('status-in-progress') != null)
							document.getElementById('status-in-progress').className = 'status-led-in-progress';

							break;

						case "ONHOLD":
							if(document.getElementById('status-on-hold') != null)
							document.getElementById('status-on-hold').className = 'status-led-on-hold';

							break;

						case "COMPLETE":
							document.getElementById('status-ReOpen').style.display = 'block';

							break;
						}
					});
</script>


<script type="text/javascript">
	$(function() {

		$('[data-toggle="popover"]').popover()
	})
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#subtask_ID").select2();
		$("#tagPicker").select2({
			closeOnSelect : !1
		})
	});
	function getCompanyWiseWorkOrderPrint(workorders_id) {
		childwin = window.open('PrintCompanyWiseWorkOrders?workorders_id='+workorders_id,'newwindow', config='height=300,width=425, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, directories=no, status=no');
	}
</script>

<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
