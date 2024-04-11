<%@ include file="taglib.jsp" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendor/${vendor.vendorTypeId}/1.in"/>">Vendors</a>
					/ <span id="NewVehi">Show Vendor</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VENDOR_FIXEDFUEL')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Fixed Vendor Fuel Price</span>
						</button>
					</sec:authorize>
					<a class="btn btn-link"
						href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<div class="row">
						<div class="col-md-4">
							<h3>
								<a
									href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
									data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vendor.vendorName}" />
								</a>
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type "> <c:out
										value="${vendor.vendorTypeId}" /></a></li>
							<li>Phone : <a data-toggle="tip" data-original-title="Phone"><c:out
										value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No"><c:out
										value="${vendor.vendorPanNO}" /></a></li>
							<li>GST No : <a data-toggle="tip"
								data-original-title="GST No"> <c:out
										value="${vendor.vendorGSTNO}" /></a></li>

						</ul>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="addImport" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="<c:url value="/saveVendorFuelPrice.in"/>">

					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">Fixed Fuel Price</h3>
						</div>
						<input type="hidden" name="VENDORID" value="${vendor.vendorId}" />

						<div class="panel-body">
							<div class="form-horizontal">

								<div class="row1" id="grpfuelNumber" class="form-group">
									<label class="L-size control-label" for="vehicleFuel"><abbr
										title="required">*</abbr> Fuel Type : </label>
									<div class="I-size">
										<select class="select2" id="vehicleFuel" name="FID"
											style="width: 100%;">
											<c:forEach items="${vehiclefuelPermission}" var="vehiclefuelPermission">
												<option value="${vehiclefuelPermission.fid}">
													<c:out value="${vehiclefuelPermission.vFuel}" />
												</option>
											</c:forEach>

										</select> <span id="fuelNameIcon" class=""></span>
										<div id="fuelNameErrorMsg" class="text-danger"></div>
									</div>
								</div><br>
								<div class="row1">
									<label class="L-size control-label" id="VehicleRegisterDate">Price
										Date : </label>
									<div class="I-size">
										<div class="input-group input-append date"
											id="vehicle_RegisterDate">
											<input class="form-text" id="vehicleRegisterDate" readonly="readonly"
												name="FUEL_FIXED_DATE" type="text"
												data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="">
											<span class="input-group-addon add-on"> <span
												class="fa fa-calendar"></span>
											</span>
										</div>
									</div>
								</div><br>
								<div class="row1" id="grpfuelPrice" class="form-group">
									<label class="L-size control-label" for="fuel_price">Price/Unit
										:<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input class="form-text" id="fuel_price" name="FUEL_PERDAY_COST"
											type="text" maxlength="8" min="0" 
											onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											ondrop="return false;"> <span id="fuelLiterIcon"
											class=""></span>
										<div id="fuelLiterErrorMsg" class="text-danger"></div>
										<label class="error" id="errorPrice" style="display: none"></label>
										<p class="help-block">ex: 56.78</p>
									</div>
								</div>

							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									id="js-upload-submit" value="Add Document">Save Fixed Fuel Price</button>
								<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
							</div>

						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
		<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VENDOR')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty vendorFixed}">
								<div class="box">
									<div class="box-body">
										<table id="FuelTable" class="table table-hover table-striped">
											<thead>
												<tr>
													<th class="fit">Date</th>
													<th>Fuel Type</th>
													<th>Fuel Cost</th>
													<th class="fit ar">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${vendorFixed}" var="vendorFixed">
													<tr data-object-id="" class="ng-scope">

														<td><c:out value="${vendorFixed.FUEL_FIXED_DATE}" /></td>
														<td><c:out value="${vendorFixed.FUEL_NAME}" /></td>
														<td><c:out value="${vendorFixed.FUEL_PERDAY_COST}" /></td>
														<td class="fit"><div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('DELETE_VENDOR_FIXEDFUEL')">
																			<a
																				href="<c:url value="/${vendor.vendorId}/${SelectPage}/deleteVendorFuel?VPID=${vendorFixed.VFFID}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div></td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<c:url var="firstUrl"
									value="/VendorFuelPrice/${vendor.vendorId}/1" />
								<c:url var="lastUrl"
									value="/VendorFuelPrice/${vendor.vendorId}/${deploymentLog.totalPages}" />
								<c:url var="prevUrl"
									value="/VendorFuelPrice/${vendor.vendorId}/${currentIndex - 1}" />
								<c:url var="nextUrl"
									value="/VendorFuelPrice/${vendor.vendorId}/${currentIndex + 1}" />
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
												value="/VendorFuelPrice/${vendor.vendorId}/${i}" />
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
							</c:if>
							<c:if test="${empty vendorFixed}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<ul class="nav nav-list">
						<li><sec:authorize access="hasAuthority('EDIT_VENDOR')">
								<a
									href="<c:url value="/${vendor.vendorTypeId}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">
									Edit Vendor </a>
							</sec:authorize></li>
					</ul>
				</div>
			</div>
		</sec:authorize>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${vendor.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${vendor.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${vendor.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${vendor.lastupdated}" /></small>
		</div>
	</section>
	<script type="text/javascript">
		function isNumberKey(e, t) {
			console.log(e);
			console.log(t)
		    var n = e.which ? e.which : event.keyCode;
		    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != charcode) return !1;
		    var i = $(t).val().length,
		        a = $(t).val().indexOf(".");
		    return !(a > 0 && 46 == n) && !(a > 0 && i + 1 - a > 3)
		}
		
		</script>
	<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  	
		
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/Driver.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})

		});
	</script>
</div>