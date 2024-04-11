<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadinventorypartdocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="<c:url value="/addInventory.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Inventory</span></a>
						<a href="<c:url value="/addNewMasterParts.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Part</span></a>	
						<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>			
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a
							href="<c:url value="/YTIHISTORY/1.in"/>"
							class="btn btn-warning btn-sm">Your Transfer History</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importPartInventory.in"/>"
							enctype="multipart/form-data">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h3 class="panel-title">Import File</h3>
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<br>
										<div class="row1" id="grppartLocation" class="form-group">
											<label class="L-size control-label" for="location">Warehouse
												location :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="select2" name="locationId" id="location"
													style="width: 90%;">
													<option value="">-- Please select --></option>
													<c:forEach items="${PartLocationPermission}"
														var="PartLocationPermission">
														<option value="${PartLocationPermission.partLocationId}"><c:out
																value="${PartLocationPermission.partLocationName}" />
														</option>
													</c:forEach>
												</select> <input type='hidden' id='locationText' name="location"
													value='' /> <span id="partLocationIcon" class=""></span>
												<div id="partLocationErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<div class="L-size">
												<label> Import Only xlsx File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".xlsx" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Inventory Part Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="this.style.visibility = 'hidden'" name="commit"
											type="submit" value="Import Inventory Part files"
											class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											autocomplete="off" id="js-upload-submit" value="Add Document"
											data-toggle="modal">
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
									<script>
										$('#myButton').on('click',function() {
											$(".progress-bar").animate(
															{
																width : "100%"
															},2500);
											var $btn = $(this).button('loading')
											$btn.button('reset')
										})
									</script>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<input type="hidden" id="partsAdded" value="<%= request.getParameter("partsAdded") %>"/>
			<div class="row">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Inventory Quantity</span> <input
								type="hidden" value="${location}" id="statues"> <span
								class="info-box-number">ltr - ${TOTALLITRECOUNT} , Qty - ${TOTALQUANTITYCOUNT}</span>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-red"><i
							class="fa fa-bell"></i></span>
						<div class="info-box-content">
							<span style="margin-top: 20px;" class="info-box-text"><a
							href="<c:url value="/getLowStockDetails.in"/>"
							target="_blank" class="btn btn-warning btn-sm">Get Low Level Stock Details</a></span> 
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Inventory</span>
							<form action="<c:url value="/searchAllInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="SearchAllInv" type="text" required="required"
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
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-3">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Invoice</span>
							<form action="<c:url value="/searchAllInventoryInvoice.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="SearchAllInvInvoice" type="text" required="required"
										placeholder="Invoice Number"> <span
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
					</div>
				</div>
			</div>
			<c:if test="${param.saveInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Created Successfully.
				</div>
			</c:if>
			<c:if test="${param.updateInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Updated Successfully.
				</div>
			</c:if>
			<c:if test="${param.deleteInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Quantity Deleted Successfully.
				</div>
			</c:if>
			<c:if test="${deleteInventoryChild}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please Delete First From Inventory Location .....
				</div>
			</c:if>
			<c:if test="${param.alreadyInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.dangerLocation eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please delete this part inside all location Quantity...
				</div>
			</c:if>
			<c:if test="${param.dangerAllInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${duplicateInventory}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists Part Number = ${alreadyInventory} .
				</div>
			</c:if>
			<c:if test="${param.sequenceNotFound eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory sequence not found .
				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-10">
				
				
					<div class="main-tabs">
						<ul class="nav nav-pills">
						
						<li role="presentation" class="tab-link" id="partInvoice"><a
								href="<c:url value="/NewInvoiceList/1.in"/>">Part Invoice</a></li>
						
							<li role="presentation" class="active" id="All"><a
								href="<c:url value="/NewInventory/1.in"/>">ALL</a></li>
								<c:if test="${!empty PartLocations}">
							<c:forEach items="${PartLocations}" var="PartLocations">
								<li class="tab-link" id="${PartLocations.partlocation_name}"><a
									class="btn btn-link"
									href="<c:url value="/locationInventory/1.in?loc=${PartLocations.partlocation_id}"/>">
										${PartLocations.partlocation_name}</a></li>
							</c:forEach>
							</c:if>
						</ul>
					</div>
					<br>

					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable" class="table table-hover table-bordered">
											<thead>
												<tr>
													<th class="fit ar">Part NO</th>
													<th class="fit ar">Part_Name</th>
													<th class="fit ar">Category</th>
													<th class="fit ar">Quantity</th>
													<th class="fit ar">Actions</th>

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty InventoryAll}">
													<c:forEach items="${InventoryAll}" var="InventoryAll">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${InventoryAll.partnumber}" /> </a></td>
															<td class="fit ar"><a
																href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${InventoryAll.partname}" /></a></td>

															<td class="fit ar"><c:out
																	value="${InventoryAll.category}" /></td>

															<%-- <td class="fit ar"><a
																href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${InventoryAll.all_quantity}" /></a></td>
															<td> --%>
															<td class="fit ar">
															<a herf="#" onclick="showSubLocation('${InventoryAll.partname}','${InventoryAll.partid}');" >
																<c:out value="${InventoryAll.all_quantity}" /> ${InventoryAll.convertToStr}
															</a>
															</td>			

															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('VIEW_INVENTORY')">
																				<a
																					href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>">
																					<span class="fa fa-pencil"></span> Show Quantity
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_INVENTORY')">
																				<a
																					href="<c:url value="/deleteAllInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Part?')">
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
										</table>
									</div>
								</div>
							</div>
							<c:url var="firstUrl" value="/NewInventory/1" />
							<c:url var="lastUrl"
								value="/NewInventory/${deploymentLog.totalPages}" />
							<c:url var="prevUrl" value="/NewInventory/${currentIndex - 1}" />
							<c:url var="nextUrl" value="/NewInventory/${currentIndex + 1}" />
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
										<c:url var="pageUrl" value="/NewInventory/${i}" />
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
				</div>
			</div>
		</sec:authorize>
		<div class="modal fade" id="showSubLocation" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Main Location : &ensp; &nbsp; <span id="mainLocationName"></span></h4>
						<h4 class="modal-title">Part Name : &emsp; &emsp; <span id="partName"></span></h4>
					</div>
					<div class="modal-body">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table class="table">
									<thead>
										<tr>
											<th class="fit ar">Sr No</th>
											<th class="fit ar">Sub Location</th>
											<th class="fit ar">Quantity</th>
										</tr>
									</thead>
									<tbody id="subLocationModelTable">
									
									</tbody>
				
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close"><spring:message
									code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript">
		$(document ).ready(function() {
	   	 	if($("#partsAdded").val() != undefined && $("#partsAdded").val() > 0) {
	   	 		showMessage('success', $("#partsAdded").val()+ " Parts has been inserted!");
	   	 	}
		});
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IP/showSubLocationPartDetail.js" />"></script>
</div>