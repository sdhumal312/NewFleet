<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addRenewalType.in"/>"> New Renewal Type</a> /
						<span id="NewRenewalType">Edit Renewal Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addRenewalType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty renewalType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddRenewal">Edit Renewal Type</h1>
								</div>
								<div class="panel-body">
									<form action="updateRenewalType.in" method="post"
										onsubmit="return validateReTypeUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="Renewal_theft">Edit
												Renewal Type:</label>
											<div class="I-size">
												<input name="renewal_Type"
													value="${renewalType.renewal_Type}" id="ReTypeUpdate"
													Class="form-text" /> <label id="errorEditReType"
													class="error"></label> <input type="hidden"
													name="renewal_id" value="${renewalType.renewal_id}">

											</div>
										</div>
									<c:if test="${configuration.tallyIntegrationRequired}">
										<div class="row1" id="grpmanufacturer" class="form-group">
										    <input type="hidden" id="expenseIdEdit" value="${renewalType.expenseId}">
										    <input type="hidden" id="tallyExpenseNameEdit" value="${renewalType.tallyExpenseName}">
											<label class="L-size control-label" for="manufacurer">Tally Expense Head :<abbr title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="tallyExpenseId" name="expenseId" style="width: 100%;" value="0"
												  placeholder="Please Enter Tally Expense Name" />
											</div>
										</div>
									</c:if>	

										<div class="form-group">
											<label class="L-size control-label" for="Renewal_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addRenewalType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty renewalType}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="../vehicle/masterSideMenu.jsp"%>
			</div>
		</div>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalType.validate.js" />"></script>
		
		<script type="text/javascript">
		$(document).ready(function() {
			$('#tallyExpenseId').select2('data', {
	    		id : $('#expenseIdEdit').val(),
	    		text : $('#tallyExpenseNameEdit').val()
	    	});
		});	
		</script>
		
	</section>
</div>