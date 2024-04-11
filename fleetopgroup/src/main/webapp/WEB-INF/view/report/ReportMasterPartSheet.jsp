<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newMasterParts/1.in"/>">Master Parts</a> / <span>Search
						Master Part</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/newMasterParts/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PARTS')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<!-- Small boxes (Stat box) -->
					<fieldset>
						<legend>Part Search</legend>

						<div class="row">

							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="MasterPartReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">

													<label class="L-size control-label">Part Name :</label>

													<div class="I-size">
														<input type="hidden" id="MasterpartSelect" name="partid"
															style="width: 100%;" required="required"
															placeholder="Please Enter 2 or more Part No or Name" />

													</div>
												</div>


												<div class="row1">
													<label class="L-size control-label">Category :</label>
													<div class="I-size">
														<select class="select2" name="categoryId"
															style="width: 100%;" id="category">
															<option value=""><!-- Please select --></option>
															<c:forEach items="${PartCategories}" var="PartCategories">
																<option value="${PartCategories.pcid}"><c:out
																		value="${PartCategories.pcName}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">${configuration.manufacturerTitle} :</label>
													<div class="I-size">
														<select class="select2" name="makerId" style="width: 100%;"
															id="make">
															<option value=""><!-- Please select --></option>
															<c:forEach items="${PartManufacturer}"
																var="PartManufacturer">

																<option value="${PartManufacturer.pmid}"><c:out
																		value="${PartManufacturer.pmName}" /></option>
															</c:forEach>
														</select> <label id="errormake" class="error"></label>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">OEM Brand :</label>
													<div class="I-size">
														<select class="select2" name="oemId" style="width: 100%;"
															id="oemId">
															<option value=""><!-- Please select --></option>
															<c:forEach items="${PartManufacturer}"
																var="PartManufacturer">

																<option value="${PartManufacturer.pmid}"><c:out
																		value="${PartManufacturer.pmName}" /></option>
															</c:forEach>
														</select> <label id="errormake" class="error"></label>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vehicle Make :</label>
													<div class="I-size">
														<input id="vehicleMake" name="vehicleMake" style="width: 100%;" type="text" placeholder=""/>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vehicle Modal :</label>
													<div class="I-size">
														<input id="vehicleModel" name="vehicleModel" style="width: 100%;" type="text" placeholder=""/>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Warranty Applicable :</label>
													<div class="I-size">
														 <select style="width: 100%;" id="warranty" name="warranty" class="select2">
														    <option selected value="-1"></option>
															<option value="true">Yes</option>
													    	<option value="false">No</option>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Part Type Category</label>
													<div class="I-size">
														 <select style="width: 100%;" name="partTypeCatgory"  id="partTypeCatgory" class="select2">
															<option selected value="-1"></option>
															<option value="1">Standard</option>
													    	<option value="2">Parent</option>
													    	<option value="3">Child</option>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Is Repairable</label>
													<div class="I-size">
														<select style="width: 100%;" id="repairable" name="repairable" class="select2">
														    <option selected value="-1"></option>
															<option value="true">Yes</option>
													    	<option value="false">No</option>
														</select>
													</div>
												</div>

											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="this.style.visibility = 'hidden'"
																	name="commit" type="submit" value="Search All">
															<a href="<c:url value="/newMasterParts/1.in"/>"
																class="btn btn-info"> <span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>

						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/partmaster/partmastersearch.js" />"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#category, #make").select2({
				placeholder : "Please Enter"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>

</div>