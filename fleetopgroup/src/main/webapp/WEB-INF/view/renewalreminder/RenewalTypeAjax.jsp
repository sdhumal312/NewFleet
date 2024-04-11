<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">	
	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message code="label.master.home" /></a> /
					<small><a href="<c:url value="/renewalTypeAjax.in"/>"> New Renewal Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-danger" data-toggle="modal"
							data-target="#addRenewaltypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddRenewalSubType"> Create
								Renewal Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		
		<div class="modal fade" id="addRenewaltypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="RenewalType">New Renewal Type</h4>
					</div>
					<div class="modal-body">
						<div class="row1">
							<label class="L-size control-label">Renewal Type</label>
							<div class="I-size">
								<input type="text" class="form-text" id="ReType"
									name="renewal_Type" placeholder="Enter Renewal Type" /> <label
									id="errorReType" class="error"></label>
							</div>
						</div>
						<br/>
						<c:if test="${configuration.tallyIntegrationRequired}">
							<div class="row1" id="grpmanufacturer" class="form-group">
								<label class="L-size control-label" for="manufacurer">Tally Expense Head :<abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="hidden" id="tallyExpenseId" name="expenseId" style="width: 100%;" value="0"
									  placeholder="Please Enter Tally Expense Name" />
								</div>
							</div>
						</c:if>
						<br />
						
						<c:if test="${configuration.ignoreRenewal}">
						<div class ="pull-right">
							<div class="row">
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="avoidAllow"> <samp style="color: blue;"><label class="form-check-label"
										for="avoidAllow"> Allow to Ignore Renewal </label></samp>
								</div>
							</div>
						</div>
						<br/>
						</c:if>

					</div>
					<div class="modal-footer">
						<button type="submit" onclick="saveRenewalType()" class="btn btn-primary">
							<span id="Save"><spring:message code="label.master.Save" /></span>
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
		
		<div class="modal fade" id="editRenewaltypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="RenewalType">Edit Renewal Type</h4>
					</div>
					<input type="hidden" id="renewalId" name="renewalId" />
					<div class="modal-body">
						<div class="row1">
							<label class="L-size control-label">Renewal Type</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editReType"
									name="renewal_Type" placeholder="Enter Renewal Type" /> <label
									id="errorReType" class="error"></label>
							</div>
						</div>
						<br/>
						<c:if test="${configuration.tallyIntegrationRequired}">
							<div class="row1" id="grpmanufacturer" class="form-group">
								<label class="L-size control-label" for="manufacurer">Tally Expense Head :<abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="hidden" id="editTallyExpenseId" name="expenseId" style="width: 100%;" value="0"
									  placeholder="Please Enter Tally Expense Name" />
								</div>
							</div>
						</c:if>
						<br/>
						<c:if test="${configuration.ignoreRenewal}">
							<div class ="pull-right">
							<div class="row">
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="editAvoidAllow"> <samp style="color: blue;"><label class="form-check-label"
										for="editAvoidAllow"> Allow to Ignore Renewal </label></samp>
								</div>
							</div>
						</div>
						<br/>
						</c:if>
					</div>
					<div class="modal-footer">
						<button type="submit" onclick="updateRenewalType()" class="btn btn-primary">
							<span id="Save">Update</span>
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
		
		<div class="row">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<h4>
							<span class="label label-info">Renewal Types</span>
						</h4>
						<div class="table-responsive">
							<table id="VendorPaymentTable" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalSubTypelanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalSubType.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalTypeAjax.js" />"></script>
	
</div>