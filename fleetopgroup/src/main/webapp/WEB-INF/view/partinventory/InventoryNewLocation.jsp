<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
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
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <span
						id="NewLocationInventory">${InventoryLocationHeadername}
						Inventory</span>
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
													style="width: 100%;">
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
							target="_blank" href="<c:url value="/getLowStockDetails.in"/>"
							class="btn btn-warning btn-sm">Get Low Level Stock Details</a></span> 
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Inventory</span>
							<form action="<c:url value="/searchLocationInventory.in"/>"
								method="post">
								<div class="input-group">
									<input type="hidden" name="locationId"  value="${locationId }">
									<input class="form-text" id="vehicle_registrationNumber"
										name="SearchLocInve" type="text" required="required"
										placeholder="Part NO/Name, Location"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<div class="main-tabs">
						<ul class="nav nav-pills">
						
						<li role="presentation"  id="partInvoice"><a
								href="<c:url value="/NewInvoiceList/1.in"/>">Part Invoice</a></li>
						
							<li role="presentation" id="All"><a
								href="<c:url value="/NewInventory/1.in"/>">ALL</a></li>
							<c:forEach items="${PartLocations}" var="PartLocations">

								<li class="tab-link" id="${PartLocations.partlocation_name}"><a
									class="btn btn-link"
									href="<c:url value="/locationInventory/1.in?loc=${PartLocations.partlocation_id}"/>">
										${PartLocations.partlocation_name}</a></li>

							</c:forEach>
						</ul>
					</div>
					<br>
					<h4>${location}-
						Inventory <span data-toggle="tip" title="" class="badge bg-red"
							data-original-title="${location} total Parts">${InventoryCount}</span>
					</h4>
					<input type="hidden" id="locationId" value="${locationId}">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<table id="InventoryTable_Location" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit ar">Part NO</th>
											<th class="fit ar">Part_Name</th>
											<th class="fit ar">Category</th>
											<th class="fit ar">Location</th>
											<th class="fit ar">Quantity</th>
											<c:if test="${showPartLocationOption}">
											<th class="fit ar">Aisle/row/bin</th>
											</c:if>
											<th class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty InventoryLocation}">
											<c:forEach items="${InventoryLocation}"
												var="InventoryLocation">
												<tr>
													<td class="fit ar">
												<input type="hidden" id="mainLocation" value="${InventoryLocation.inventory_location_id}">
												<input type="hidden" id="idd" value="${InventoryLocation.inventory_location_id}">
													
													<a
														href="<c:url value="/showInventory.in?inventory_all_id=${InventoryLocation.inventory_all_id}"/>"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"><c:out
																value="${InventoryLocation.partnumber}" /></a></td>
													<td class="fit ar"><a
														href="<c:url value="/showInventory.in?inventory_all_id=${InventoryLocation.inventory_all_id}"/>"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"
														data-toggle="tip" data-original-title="click Part Info"><c:out
																value="${InventoryLocation.partname}" /></a></td>
													<td class="fit ar"><c:out
															value="${InventoryLocation.category}" /></td>
													<td class="fit ar"><c:out
															value="${InventoryLocation.location}" /></td>
													<td class="fit ar">
														<a herf="#" onclick="showSubLocation('${InventoryLocation.partname}','${InventoryLocation.partid}');" >
														<c:out value="${InventoryLocation.location_quantity}" /> ${InventoryLocation.convertToStr} </a>
													</td>
													<c:if test="${showPartLocationOption}">
														<td class="fit ar">
														<c:out value="${InventoryLocation.aisle}-${InventoryLocation.bin}-${InventoryLocation.row}" />
													</td>
													</c:if>
													

													<td class="fit ar">
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
															</a>
															<ul class="dropdown-menu pull-right">

																<li><sec:authorize
																		access="hasAuthority('DELETE_INVENTORY')">
																		<a
																			href="<c:url value="/deleteLocationInventory.in?inventory_location_id=${InventoryLocation.inventory_location_id}"/>"
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
						<c:url var="firstUrl"
							value="/locationInventory/1.in?loc=${locationId}" />
						<c:url var="lastUrl"
							value="/locationInventory/${deploymentLog.totalPages}.in?loc=${locationId}" />
						<c:url var="prevUrl"
							value="/locationInventory/${currentIndex - 1}.in?loc=${locationId}" />
						<c:url var="nextUrl"
							value="/locationInventory/${currentIndex + 1}.in?loc=${locationId}" />
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
									<c:url var="pageUrl"
										value="/locationInventory/${i}.in?loc=${locationId}" />
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
	<script type="text/javascript">
		$(document ).ready(function() {
	   	 	if($("#partsAdded").val() != undefined && $("#partsAdded").val() > 0) {
	   	 		showMessage('success', $("#partsAdded").val()+ " Parts has been inserted!");
	   	 	}
		});
	</script>
	<script type="text/javascript">$(document).ready(function(){var e=document.getElementById("statues").value;switch(e){case"ALL":document.getElementById("All").className="active";break;case e:document.getElementById(e).className="active"}});</script>
</div>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IP/showSubLocationPartDetail.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>

