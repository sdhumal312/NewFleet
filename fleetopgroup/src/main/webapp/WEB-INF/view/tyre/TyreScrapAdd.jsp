<%@ include file="taglib.jsp"%>
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
						href="<c:url value="/TyreInventoryNew/1.in"/>">Tyre Inventory</a>
					/ <a href="<c:url value="/ScrapFilter"/>">Scrap Filter
						Inventory</a> / <span>New Scrap Tyre </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/ScrapFilter"/>"
						class="btn btn-danger btn-sm"><span class="fa fa-search">
							Scrap Filter</span></a> <a href="<c:url value="/RetreadFilter"/>"
						class="btn btn-info btn-sm"><span class="fa fa-search">
							Retread Filter</span></a> <a class="btn btn-link btn-sm"
						href="<c:url value="/TyreInventoryNew/1.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="main-body">
					<h4>Create Scrap Tyre List</h4>
					<div class="box">
						<div class="box-body">
							<c:if test="${!empty Tyre}">
								<sec:authorize access="!hasAuthority('ADD_TYRE_SCRAP')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_TYRE_SCRAP')">
									<form id="frm-example" action="saveTyreScrapInfo.in"
										method="POST">
										<div class="form-horizontal ">
											<fieldset>
												<div class="col-md-10 col-md-offset-1">
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
																	<td class="fit"><c:if
																			test="${Tyre.TYRE_ASSIGN_STATUS_ID == 1}">
																			<input name="TyreID" value="${Tyre.TYRE_ID}"
																				id="example" type="checkbox" />
																		</c:if></td>

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
													<label class="L-size control-label" for="issue_description">
														Scrap Description :</label>
													<div class="I-size">
														<textarea class="text optional form-text"
															id="initial_note" name="ScrapDescription" rows="3">
				                                 </textarea>
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row">
													<div class="col-md-10 col-md-offset-2">
														<input class="btn btn-success" type="submit"
															value="Scrap Tyre"> <a class="btn btn-link"
															href="#">Cancel</a>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
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
