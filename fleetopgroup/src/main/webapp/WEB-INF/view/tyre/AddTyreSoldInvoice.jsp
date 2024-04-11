<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventory/1.in"/>">Tyre Inventory</a>
					 <span>Sold Tyre </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/soldFilter"/>"
						class="btn btn-info btn-sm"><span class="fa fa-search">
							Sold Filter</span></a> <a class="btn btn-link btn-sm"
						href="<c:url value="/TyreInventory/1.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_TYRE_RETREAD')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_TYRE_RETREAD')">
		<div class="row">
		<c:if test="${param.sequenceNotFound eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
			<%-- 	<span id="errMsg" value="${sequenceNotFoundMessag}"></span> --%>
			Sequence not found please contact to system Administrator
			
			</div>
		</c:if>
		</div>
			<div class="row">
				<div class="main-body">
					<h4>Create Sold Tyre List</h4>
					<div class="box">
						<div class="box-body">
							<c:if test="${!empty Tyre}">
								<sec:authorize access="!hasAuthority('ADD_TYRE_RETREAD')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_TYRE_RETREAD')">
									<!-- <form id="frm-example" action="saveTyreSoldInvoice.in"
										method="POST"> -->
										<div class="form-horizontal ">
											<fieldset>
												<div class="col-md-10 col-md-offset-1">
												<input type="hidden" id="soldType" value="${soldType}">
												
													<table id="VendorApplovalList"
														class="table table-bordered table-striped">
														<thead>
															<tr>
																<th class="fit"><input name="select_all" value="1"
																	id="example-select-all" type="checkbox" /></th>
																<th class="fit ar">Tyre NO</th>
																<th class="fit ar">Manufacturer</th>
																<th class="fit ar">Model</th>
																<th class="fit ar">Usage</th>
																<th class="fit ar">Tyre Size</th>
																<th class="fit ar">Location</th>
																<th class="fit ar">Status</th>

															</tr>
														</thead>
														<tfoot>
															<tr>
																<th class="fit"></th>
																<th class="fit ar">Tyre NO</th>
																<th class="fit ar">Manufacturer</th>
																<th class="fit ar">Model</th>
																<th class="fit ar">Usage</th>
																<th class="fit ar">Tyre Size</th>
																<th class="fit ar">Location</th>
																<th class="fit ar">Status</th>
															</tr>
														</tfoot>
														<tbody id="vendorList">

															<c:forEach items="${Tyre}" var="Tyre">
																<tr>
																	<td class="fit">
																			<input name="TyreID" value="${Tyre.TYRE_ID}"
																				id="example" type="checkbox" />
																		</td>

																	<td class="fit ar"><a
																		href="showTyreInfo.in?Id=${Tyre.TYRE_ID}"
																		data-toggle="tip"
																		data-original-title="Click Tyre Inventory INFO"><c:out
																				value="${Tyre.TYRE_NUMBER}" /></a></td>

																	<td class="fit ar"><a
																		href="showTyreInfo.in?Id=${Tyre.TYRE_ID}"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${Tyre.TYRE_MANUFACTURER}" /> </a></td>
																	<td class="fit ar"><c:out
																			value="${Tyre.TYRE_MODEL}" /></td>
																	<td class="fit ar"><c:out
																			value="${Tyre.TYRE_USEAGE}" /></td>
																	<td class="fit ar"><c:out
																			value="${Tyre.TYRE_SIZE}" /></td>
																	<td class="fit ar"><c:out
																			value="${Tyre.WAREHOUSE_LOCATION}" /></td>
																	<td class="fit ar"><c:out
																			value="${Tyre.TYRE_ASSIGN_STATUS}" /></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
												<div class="row1">
													<label class="L-size control-label">Sold Date :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="input-group input-append date" id="opendDate">
															<input type="text" class="form-text" name="TR_OPEN_DATE" id="invoiceDate"
																required="required"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>
												
									<c:if test="${soldType == 4}">
										<div class="row1">
											<label class="L-size control-label"></label>
											<div class="col-md-9">
												<div class="col-md-1">
													<label class="control-label">Weight</label>
												</div>
												<div class="col-md-3">
													<label class="control-label">Sold Cost/Kg</label>
												</div>
												<div class="col-md-1">
													<label class="control-label">Discount</label>
												</div>
												<div class="col-md-1">
													<label class="control-label">GST</label>
												</div>
												<div class="col-md-3">
													<label class="control-label">Total</label>
												</div>
											</div>
										</div>
												<div class="row1">
											<label class="L-size control-label" for="quantity"> </label>
											<div class="col-md-9">
												<div class="col-md-1">
													<input type="text" class="form-text"
														name="unitprice_many" id="weight" maxlength="7"
														min="0.0" placeholder="Weight" required="required"
														data-toggle="tip" data-original-title="enter Weight"
														onkeypress="return isNumberKey(event,this);"
														onkeyup="javascript:sumthere('weight','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"> <span id="unitpriceIcon"
														class=""></span>
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-3">
													<input type="text" class="form-text"
														name="unitprice_many" id="unitprice" maxlength="7"
														min="0.0" placeholder="Unit Cost" required="required" 
														data-toggle="tip" data-original-title="enter Unit Price"
														onkeypress="return isNumberKey(event,this);"
														onkeyup="javascript:sumthere('weight','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"> <span id="unitpriceIcon"
														class=""></span>
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>

												<div class="col-md-1">
													<input type="text" class="form-text" name="discount_many"
														min="0.0" id="discount" maxlength="5"
														placeholder="Discount" required="required"
														data-toggle="tip" data-original-title="enter Discount"
														onkeypress="return isNumberKeyQut(event,this);"
														onkeyup="javascript:sumthere('weight','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"><span id="discountIcon"
														class=""></span>
													<div id="discountErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-1">
													<input type="text" class="form-text" name="tax_many"
														id="tax" maxlength="5" min="0.0" placeholder="GST"
														required="required" data-toggle="tip"
														data-original-title="enter GST"
														onkeypress="return isNumberKeyQut(event,this);"
														onkeyup="javascript:sumthere('weight','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"><span id="taxIcon"
														class=""></span>
													<div id="taxErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-3">
													<input type="text" class="form-text" maxlength="8"
														value="0.0" min="0.0" name="totalCost" id="totalCost" readonly="readonly"
														data-toggle="tip" data-original-title="Total Cost"
														onkeypress="return isNumberKey(event,this);"
														ondrop="return false;">
												</div>
											</div>
										</div>
									</c:if>		
										<div class="row1">
											<label class="L-size control-label" id="Type">Description
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="descriptionId"
													maxlength="249" name="description"
													placeholder="Enter description" />
											</div>
										</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<div class="col-md-10 col-md-offset-2">
														<button type="submit" id="submit" class="btn btn-success">Create Sold Tyre Invoice</button>
														<a class="btn btn-link" href="TyreInventory.in/1">Cancel</a>
													</div>
												</div>
											</fieldset>
										</div>
									<!-- </form> -->
								</sec:authorize>
							</c:if>
							<c:if test="${empty Tyre}">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/AddTyreSoldInvoice.js" />"></script>	
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
