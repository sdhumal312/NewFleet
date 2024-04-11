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
					<small><a href="<c:url value="/renewalSubTypeAjax.in"/>"> New Renewal SubType</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addRenewalSubtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddRenewalSubType"> Create
								Renewal Sub Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		
		<div class="modal fade" id="addRenewalSubtypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="RenewalSubType">New Renewal
								SubType</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label"><span id="Type">Renewal
										Type</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<select class="form-text selectType" name="renewal_id"
										style="width: 100%;" id="selectReType">
										<c:forEach items="${renewalType}" var="renewalType">
											<option value="${renewalType.renewal_id}">${renewalType.renewal_Type}
											</option>
										</c:forEach>
									</select> <label id="errorReType" class="error"></label>
									<input id="renewalTypeVal" name="renewal_Type" type="hidden" />
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label"><span id="SubType">Renewal
										SubType</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="text" class="form-text" id="SubReType"
										name="renewal_SubType" placeholder="Enter RenewalSub Type" />
									<label id="errorSubReType" class="error"></label>
								</div>
							</div>
							<sec:authorize access="hasAuthority('IS_MANDATORY_SUB_RENEWAL')">
							<div class="row">
								<label class="L-size control-label"><span id="SubType">IsMandatory
										</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="checkbox" id="isMandatory" name="isMandatory" />
								</div>
							</div>
							</sec:authorize>
							<br /> <br /> <br />
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="saveRenewalSubType()" class="btn btn-primary">
								<span id="Save"><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close"><spring:message code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="editRenewalSubtypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="RenewalSubType">Update Renewal
								SubType</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label"><span id="Type">Renewal
										Type</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="hidden" id="editSelectReType" name="renewalTypeId"
									style="width: 100%;" />
									<div id="renewalTypeErrorMsg" class="text-danger"></div>
								</div>
							</div>
							</br>
							<div class="row">
								<label class="L-size control-label"><span id="SubType">Renewal
										SubType</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="text" class="form-text" id="editSubReType"
										name="renewal_SubType" placeholder="Enter RenewalSub Type" />
									<label id="errorSubReType" class="error"></label>
								</div>
								<input type="hidden" id="editSubTypeId" name="editSubTypeName"/>
									
							</div>
							<sec:authorize access="hasAuthority('IS_MANDATORY_SUB_RENEWAL')">
							<div class="row">
								<label class="L-size control-label"><span id="SubType">IsMandatory
										</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="checkbox" id="editIsMandatory" name="isMandatory" />
								</div>
							</div>
							</sec:authorize>
							<br /> <br /> <br />
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="updateRenewalSubType()" class="btn btn-primary">
								<span id="update">Update</span>
							</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close"><spring:message code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="row">
		<input type="hidden" id="editMandatorySubRenewal" value="${editMandatorySubRenewal}">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<h4>
							<span class="label label-info">Mandatory Sub Renewals</span>
						</h4>
						<div class="table-responsive">
							<table id="VendorPaymentTable" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<h4>
							<span class="label label-success">Non-Mandatory Sub Renewals</span>
						</h4>
						<div class="table-responsive">
							<table id="VendorPaymentTable1" class="table table-hover table-bordered">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalSubTypeAjax.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#renewalTypeVal').val($("#selectReType option:selected").text().trim());
			$('#selectReType').on('change', function() {
				$('#renewalTypeVal').val($("#selectReType option:selected").text().trim());
			});
		});
	</script>
</div>