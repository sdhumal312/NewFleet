<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/viewWorkOrder.in"/>">OPEN</a> / <a
							href="<c:url value="/WorkOrdersINPROCESS/1.in"/>">IN PROCESS</a>
						/ <a href="<c:url value="/WorkOrdersONHOLD/1.in"/>">ON HOLD</a> /
						<a href="<c:url value="/WorkOrdersCOMPLETE/1.in"/>">COMPLETED</a>
						/ <span id="AllVehicl">Open Work Orders</span>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchWorkOrderShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">WO-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="WO-NO eg:6878">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<a href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
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

							</ul>
						</div>
					</div>
					<div class="col-md-5">
						<div class="row">
							<input type="hidden" id="statues" name="statues"
								value="${WorkOrder.workorders_status}">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<a data-disable-with="..." data-method="post"
										data-remote="true" rel="nofollow"> <span id="status-open"
										class="status-led"> <i class="fa fa-circle"></i>
											<div class="status-text">Open</div>
									</span>
									</a> <a data-method="post" data-remote="true"
										href="work_orders_INPROCESS.in?workorders_id=${WorkOrder.workorders_id}"
										rel="nofollow"> <span id="status-in-progress"
										class="status-led"> <i class="fa fa-circle"></i>
											<div class="status-text">In Progress</div>
									</span>
									</a>
									<sec:authorize access="hasAuthority('REOPEN_WORKORDER')">
										<a data-disable-with="..." data-method="post"
											data-remote="true"
											href="work_orders_ONHOLD.in?workorders_id=${WorkOrder.workorders_id}"
											rel="nofollow"><span id="status-on-hold"
											class="status-led"> <i class="fa fa-circle"></i>
												<div class="status-text">On Hold</div>
										</span> </a>
									</sec:authorize>
								</div>
								<div id="status-close">
									<a class="btn btn-success" data-disable-with="..."
										href="work_orders_COMPLETE.in?workorders_id=${WorkOrder.workorders_id}">Complete</a>
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
					<div class="modal fade" id="addworkorderDocument" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post" action="uploadWorkOrderDocument.in"
									enctype="multipart/form-data">
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
										<button type="submit" class="btn btn-primary">
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
					<div class="row">
						<c:if test="${deleteFristParts}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Should be Delete First Task Parts and Technician
							</div>
						</c:if>
						<c:if test="${param.UploadSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This WorkOrder Document Upload Successfully.
							</div>
						</c:if>
						<c:if test="${param.NoAuthen eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								No Authentication to access this WorkOrder Location is
								different.
							</div>
						</c:if>
						<div class="col-md-11">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
										<c:if test="${configuration.showServRemindWhileCreatingWO}">
											<th>Service Number</th>
										</c:if>	
											<th class="fit"></th>
											<th>Task</th>
											<th class="fit ar">Parts Cost</th>
											<th class="fit ar">Labour Cost</th>
											<th class="fit ar">Total</th>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty WorkOrderTask}">
											<c:forEach items="${WorkOrderTask}" var="WorkOrderTask">
												<tr data-object-id="" class="ng-scope">
												<c:if test="${configuration.showServRemindWhileCreatingWO}">		
														<td>
														<c:if test="${WorkOrderTask.service_Number == null}">
															<c:out value="-"> </c:out>
														</c:if>
														<c:if test="${WorkOrderTask.service_Number != null}">
														<a href="ShowService.in?service_id=${WorkOrderTask.service_id}" target="_blank"> <span style="font-size: 12px;"> S-${WorkOrderTask.service_Number} </span> </a>
 														</c:if>	
														</td>
												</c:if>		
													<td class="fit"><c:if
															test="${WorkOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${WorkOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
														
													<!-- Tast table to assing part value table -->
													<td><h4>
															<c:out value="${WorkOrderTask.job_typetask} - "></c:out>
															<c:out value="${WorkOrderTask.job_subtypetask}"></c:out>
														</h4>
															<c:if test="${showEditJobTypeRemark}">															
																<h5>
																	<b>Remark : </b><span id="workordertaskremark_${WorkOrderTask.workordertaskid}"><c:out value="${WorkOrderTask.jobTypeRemark}"></c:out></span>
																	<a data-toggle="modal" data-target="#editTaskRemark_${WorkOrderTask.workordertaskid}" data-whatever="@mdo">Edit Remark</a>
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
																							<input type="text" maxlength="255" class="form-text" name="taskRemark" id="taskRemark_${WorkOrderTask.workordertaskid}"
																								value="<c:out value="${WorkOrderTask.jobTypeRemark}"></c:out>" />
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
														<div class="row">
															<c:choose>
																<c:when test="${WorkOrderTask.last_occurred_woId != 0}">
																	<samp style="color: red;">
																		Last occurred on ${WorkOrderTask.last_occurred_date}
																		this task on Odometer =
																		${WorkOrderTask.last_occurred_odameter}. <a
																			href="showWorkOrder?woId=${WorkOrderTask.last_occurred_woId}"
																			target="_blank"><i class="fa fa-external-link"></i></a>
																	</samp>
																</c:when>
																<c:otherwise>
																	<samp style="color: blue;">Never logged for is
																		task</samp>
																</c:otherwise>
															</c:choose>
														</div>
														<div class="row">
															<a id="addParts${WorkOrderTask.workordertaskid}"
																onload=""
																onclick="javascript:getInventoryListLoc('inventory_name${WorkOrderTask.workordertaskid}', '${WorkOrder.workorders_location}');"
																href="javascript:toggle2('changePart${WorkOrderTask.workordertaskid}','addParts${WorkOrderTask.workordertaskid}');">
																Add Parts </a> | <a
																id="addLabor${WorkOrderTask.workordertaskid}"
																onclick="javascript:getTechinicionName('labername${WorkOrderTask.workordertaskid}');javascript:getROT_COST_Hour('${WorkOrderTask.job_subtypetask_id}', 'laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}' );"
																href="javascript:toggle2Labor('changeLabor${WorkOrderTask.workordertaskid}','addLabor${WorkOrderTask.workordertaskid}');">
																Add Labour</a> |
															<sec:authorize
																access="hasAuthority('COMPLETE_WORKORDER')">
																<a data-method="post"
																	href="saveWorkOrderTaskCompletion.in?workordertaskid=${WorkOrderTask.workordertaskid}">Mark
																	Complete</a>
															</sec:authorize>
														</div>
														<div class="row">
															<div class="col-md-11">

																<table class="table">
																	<c:if test="${!empty WorkOrderTaskPart}">
																		<thead>
																			<tr class="breadcrumb">
																				<th class="icon ar" width="50%">Part</th>
																				<th class="icon ar" width="15%">Qty</th>
																				<th class="icon ar" width="10%">Each</th>
																				<th class="icon ar" width="10%">Total</th>
																				<c:if test="${configuration.partsCreatedBy}">
																				<th class="icon ar" width="15%">Part Added By</th>
																				</c:if>																				
																				<th class="fit">Actions</th>
																			</tr>
																		</thead>
																		<tbody>

																			<c:forEach items="${WorkOrderTaskPart}"
																				var="WorkOrderTaskPart">
																				<c:if
																					test="${WorkOrderTaskPart.workordertaskid == WorkOrderTask.workordertaskid}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="icon"><c:out
																								value="${WorkOrderTaskPart.partname}" /> <c:out
																								value="${WorkOrderTaskPart.partnumber}" /></td>
																						<td class="icon ar">${WorkOrderTaskPart.quantity}</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskPart.parteachcost}</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskPart.totalcost}</td>
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
																									<%-- <li><a
																								href="editWorkOrderTaskPart.in?workordertaskto_partid=${WorkOrderTaskPart.workordertaskto_partid}">
																									<i class="fa fa-edit"></i> Edit
																							</a></li> --%>
																									<li><sec:authorize
																											access="hasAuthority('DELETE_WORKORDER')">
																											<a
																												href="deleteWorkOrderTaskToPart.in?workordertaskto_partid=${WorkOrderTaskPart.workordertaskto_partid}"
																												class="confirmation"
																												onclick="return confirm('Are you sure? Delete ')">
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

																	<tbody style="border-top: 0px;">
																		<tr>
																			<td colspan="5">
																				<div class="">
																					<div
																						id="changePart${WorkOrderTask.workordertaskid}"
																						style="display: none">
																						<form action="saveWorkOrderTaskPart.in"
																							method="post">
																							<div class="row">
																								<div class="col-md-5">
																									<input type="hidden" name="workorders_id"
																										id="lastworkorder_id"
																										value="${WorkOrder.workorders_id}"> <input
																										type="hidden" name="workordertaskid"
																										value="${WorkOrderTask.workordertaskid}"
																										id="workordertaskid"> <input
																										type="hidden" name="partid"
																										id="inventory_name${WorkOrderTask.workordertaskid}"
																										required="required" style="width: 100%;"
																										required="required"
																										onchange="javascript:getQuantity('inventory_name${WorkOrderTask.workordertaskid}', 'parteachcost${WorkOrderTask.workordertaskid}', 'quantity${WorkOrderTask.workordertaskid}'); 
																									 javascript:getlastdetails('${WorkOrderTask.job_typetaskId}','${WorkOrderTask.job_subtypetask_id}','${WorkOrder.vehicle_vid}', 'last_occurred${WorkOrderTask.workordertaskid}');"
																										placeholder="Please Enter 2 or more Part Name" />

																								</div>
																								<div class="col-md-1">
																									<input type="text" class="form-text"
																										placeholder="Qty" name="quantity" value="0"
																										min="0" step="0.01" required="required"
																										data-toggle="tip" 
																										data-original-title="Part Quantity"
																						 				id="quantity${WorkOrderTask.workordertaskid}" onkeypress="return isNumberKeyWithDecimal(event,this.id);">
																										
																										
																								</div>

																								<div class="col-md-3">
																									<div class="row">
																										<input id="oldpart${WorkOrderTask.workordertaskid}" type="checkbox" name="oldpart"
																											class="L-size" value="RECEIVED"
																											readonly="readonly"> <label
																											class="I-size">Old Part Received </label>

																									</div>
																								</div>

																								<!-- <div class="fit"></div> -->
																							</div>
																							<div class="help-block"
																								id="last_occurred${WorkOrderTask.workordertaskid}">
																								<span class="loading ng-hide" id="loading">
																									<img alt="Loading" class="loading-img"
																									src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif"/>" >
																								</span>

																							</div>
																							<br>
																							<div class="row">
																								<div class="col-md-5 col-md-offset-2">

																									<input class="btn btn-success" type="submit"
																										value="Save Parts">
																								</div>
																							</div>
																						</form>
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
																				<th class="icon ar">Hours</th>
																				<th class="icon ar">RateS</th>
																				<th class="icon ar">Dis</th>
																				<th class="icon ar">GST</th>
																				<th class="icon ar">Total</th>
																				<th class="fit"></th>
																			</tr>
																		</thead>
																		<tbody>

																			<c:forEach items="${WorkOrderTaskLabor}"
																				var="WorkOrderTaskLabor">
																				<c:if
																					test="${WorkOrderTaskLabor.workordertaskid == WorkOrderTask.workordertaskid}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="icon"><c:out
																								value="${WorkOrderTaskLabor.labername}" /></td>
																						<td class="icon ar">${WorkOrderTaskLabor.laberhourscost}</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskLabor.eachlabercost}</td>
																						<td class="icon ar">
																							${WorkOrderTaskLabor.laberdiscount} %</td>
																						<td class="icon ar">
																							${WorkOrderTaskLabor.labertax} %</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskLabor.totalcost}</td>
																						<td class="fit">
																							<div class="btn-group">
																								<a class="btn-sm dropdown-toggle"
																									data-toggle="dropdown" href="#"> <span
																									class="fa fa-cog"></span> <span class="caret"></span>
																								</a>

																								<ul class="dropdown-menu pull-right">
																									<%-- <li><a
																								href="editWorkOrderTaskLabor.in?workordertaskto_laberid=${WorkOrderTaskLabor.workordertaskto_laberid}">
																									<i class="fa fa-edit"></i> Edit
																							</a></li> --%>
																									<li><sec:authorize
																											access="hasAuthority('DELETE_WORKORDER')">
																											<a
																												href="deleteWorkOrderTaskToLabor.in?workordertaskto_laberid=${WorkOrderTaskLabor.workordertaskto_laberid}"
																												class="confirmation"
																												onclick="return confirm('Are you sure? Delete ')">
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

																	<tbody style="border-top: 0px;">
																		<tr data-object-id="" class="ng-scope">
																			<td colspan="6">
																				<div class="">
																					<div
																						id="changeLabor${WorkOrderTask.workordertaskid}"
																						style="display: none">
																						<form action="saveWorkOrderTaskLabor.in"
																							method="post">
																							<div class="row">
																								<div class="col-md-3">
																									<input type="hidden" name="workorders_id"
																										value="${WorkOrder.workorders_id}"
																										id="workorders_id"> <input
																										type="hidden" name="workordertaskid"
																										value="${WorkOrderTask.workordertaskid}"
																										id="workordertaskid"> <input
																										id="labername${WorkOrderTask.workordertaskid}"
																										type="text" style="width: 100%;"
																										name="laberid"
																										placeholder="Techinician Name">
																								</div>
																								<div class="col-md-1">  <!--onkeypress="return isLabertimeKeyQut(event);" Original Code  -->
																									<input type="text" class="form-text"
																										data-toggle="tip"  onpaste="return false" 
																										data-original-title="Laber work hour"
																										name="laberhourscost" placeholder="time"
																										required="required"
																										onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										id="laberhourscost${WorkOrderTask.workordertaskid}"
																										onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');"
																										min="0.0">
																								</div>
																								<div class="col-md-1"> <!--onkeypress="return isLaberCostKeyQut(event);" Original Code -->
																									<input type="text" name="eachlabercost"
																										class="form-text" placeholder="cost"
																										required="required" data-toggle="tip"  onpaste="return false"
																										data-original-title="Laber Cost"
																										onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										id="eachlabercost${WorkOrderTask.workordertaskid}"
																										onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');">
																								</div>
																								<div class="col-md-1"> <!--onkeypress="return isLaberDisKeyQut(event);" Original Code  -->
																									<input type="text" name="laberdiscount"
																										class="form-text" placeholder="dis" value="0"  onpaste="return false"
																										required="required" data-toggle="tip"
																										data-original-title="enter discount"
																										onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										id="laberdiscount${WorkOrderTask.workordertaskid}"
																										onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');">
																								</div>
																								<div class="col-md-1"> <!--onkeypress="return isLaberTaxKeyQut(event);" Original Code -->
																									<input type="text" name="labertax"
																										class="form-text" placeholder="GST"  onpaste="return false"
																										required="required" data-toggle="tip"
																										data-original-title="enter GST" value="0"
																										onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										id="labertax${WorkOrderTask.workordertaskid}"
																										onkeyup="javascript:sumthere('laberhourscost${WorkOrderTask.workordertaskid}', 'eachlabercost${WorkOrderTask.workordertaskid}', 'laberdiscount${WorkOrderTask.workordertaskid}', 'labertax${WorkOrderTask.workordertaskid}', 'totalLaborcost${WorkOrderTask.workordertaskid}');">
																								</div>
																								<div class="col-md-2">
																									<input type="text" name="totalcost"
																										class="form-text" required="required"
																										data-toggle="tip"
																										data-original-title="Laber Total Cost"
																										id="totalLaborcost${WorkOrderTask.workordertaskid}"
																										readonly="readonly">
																								</div>
																								<div class="fit"></div>
																							</div>
																							<br>
																							<div class="row">
																								<div class="col-md-5 col-md-offset-2">

																									<input class="btn btn-success" type="submit"
																										value="Save Labor">
																								</div>
																							</div>
																						</form>
																					</div>
																				</div>
																			</td>

																		</tr>
																	</tbody>

																</table>
															</div>
														</div></td>
													<td class="fit ar"><i class="fa fa-inr"></i> <c:out
															value="${WorkOrderTask.totalpart_cost}"></c:out></td>
													<td class="fit ar"><i class="fa fa-inr"></i> <c:out
															value="${WorkOrderTask.totallaber_cost}"></c:out></td>
													<td class="fit ar"><i class="fa fa-inr"></i> <c:out
															value="${WorkOrderTask.totaltask_cost}"></c:out></td>
													<td class="fit">
														<div class="btn-group">
															<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
																href="#"> <span class="fa fa-cog"></span> <span
																class="caret"></span>
															</a>

															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																		access="hasAuthority('DELETE_WORKORDER')">
																		<a
																			href="deleteWorkOrderTask.in?workordertaskid=${WorkOrderTask.workordertaskid}"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Delete ')">
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
											<th colspan="6"><a id="addwork"
												href="javascript:toggle2Task('changeWorkOrder','addwork');">
													Add Task</a></th>
										</tr>
										<tr data-object-id="" class="ng-scope">
											<td colspan="6">
												<div class="">
													<div id="changeWorkOrder" style="display: none">
														<form action="saveWorkOrdersOpenTask.in" method="post">
															<div class="row">
																<div class="col-md-6">
																	<input type="hidden" name="workorders_id"
																		value="${WorkOrder.workorders_id}" id="workorders_id">

																	<input type="hidden" name="vehicle_vid"
																		value="${WorkOrder.vehicle_vid}">

																	<div class="row1">
																		<label class="L-size control-label" for="priority">Job
																			Type </label>
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
																			<input type="hidden" id="to"
																				name="job_subtypetask_id" style="width: 100%;"
																				required="required"
																				placeholder="Please Enter 3 or more Job Sub Type Name" />
																			<!-- <select style="width: 100%;" name="job_subtypetask_id"
																				id="subtask_ID" required="required">
																			</select> -->
																		</div>
																	</div>
																	<c:if test="${showJobTypeRemarkCol}">
																		<div class="row1 form-group jobTypeRemarkCol col-md-12">
																			<label class="L-size control-label" for="to">Job Type Remark</label>
																			<div class="I-size">
																				<div class="col-md-8">
																					<input type="text" class="form-text" id="JobTypeRemark"
																						name="JobTypeRemark" maxlength="150"
																						placeholder="Enter Remark" />
																				</div>
																			</div>
																		</div>																	
																	</c:if>
																	<div class="fit"></div>
																</div>
															</div>
															<br>
															<div class="row">
																<div class="col-md-5 col-md-offset-2">

																	<input class="btn btn-success" type="submit" onclick="return woValidate();"
																		value="Save Task">
																</div>
															</div>
														</form>
													</div>
												</div>
											</td>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<table class="table">
										<tbody>
											<tr class="row">
												<th class="key">Driver Name :</th>
												<td class="value"><a
													href="showDriver.in?driver_id=${WorkOrder.workorders_driver_id}">${WorkOrder.workorders_drivername}</a></td>
											</tr>
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
												<th class="key">Work Location :</th>
												<td class="value">${WorkOrder.workorders_location}</td>
											</tr>
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
														<form accept-charset="UTF-8"
															action="workOrdersUpdate_tax_cost.in?workorders_id=${WorkOrder.workorders_id}"
															method="post" novalidate="novalidate">

															<div class="row">
																<label class="control-label">GST Cost</label>
																<div class="input float optional work_order_tax_cost">
																	<input autofocus="autofocus" class="form-text"
																		required="required" name="totalworktax_cost"
																		step="any" type="number" value="0.00">
																</div>
															</div>
															<div class="row">
																<input class="btn btn-success" name="commit"
																	type="submit" value="Apply">
															</div>
														</form>
													</div></td>
											</tr>
											<tr class="row">
												<th class="key"><h4>Total :</h4></th>
												<td class="value"><h4>
														<i class="fa fa-inr"></i> ${WorkOrder.totalworkorder_cost}
													</h4></td>
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
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${WorkOrder.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${WorkOrder.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${WorkOrder.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${WorkOrder.lastupdated}" /></small>
		</div>
	</section>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/WorkOrdersValidate.js" />"></script>

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
							document.getElementById('status-in-progress').className = 'status-led-in-progress';

							break;

						case "ONHOLD":
							document.getElementById('status-on-hold').className = 'status-led-on-hold';

							break;

						case "COMPLETE":
							document.getElementById('status-ReOpen').style.display = 'block';

							break;
						}
					});
</script>

<!-- get the Inventory Drop Down down -->
<c:url var="findInventoryURL" value="getInventoryList.in" />

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
<script type="text/javascript">
	function editTaskRemark(workordertaskid) {
		console.log("workordertaskid : " + workordertaskid);
		if (confirm("Are you sure?") == true) {
			showLayer();
			var jsonObject					= new Object();

			jsonObject["workordertaskid"]	= workordertaskid;
			jsonObject["remark"]			= $("#taskRemark_"+workordertaskid).val();

			console.log("jsonObject : " , jsonObject);
			$.ajax({
				url: "workOrderWS/updateTaskRemarkById.do",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					if(data.taskRemark != undefined) {
						$("#workordertaskremark_"+workordertaskid).html(data.taskRemark);
					}
				},
				error: function (e) {
					console.log("Error : " , e);
				}
			});
			setTimeout(function(){ hideLayer(); }, 500);
		}
	}
</script>

<!-- get the Inventory Quantity and Unit price -->
<c:url var="findInventoryURL" value="getInventoryQuantityList.in" />
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 