<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<style>
.label_font{
	font-weight: bold;
	font-size: larger;
}
.select2-container-multi .select2-choices {
    min-height: 38px;
}
.select2-container .select2-choice {
   height: 36px;
}
.row {
	width: 100%;
	margin: 10px auto;
	padding:1%;
}
.noBackGround{
	background: none;
}
.textStyle{
font-size: initial;
padding: 5px;
}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> / 
					<a href="<c:url value="/showIssues.in?Id=${issueEncId}"/>">Issue-<span id="issueNumber"></span></a>/
					<span>Issue Analysis</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/showIssues.in?Id=${issueEncId}"/>">
						<span> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_ISSUES')">
			<div class="row">
				<div class="col-sm-8 col-md-12">
					<input type="hidden" id="companyId" value="${companyId}">
					<input type="hidden" id="userId" value="${userId}">
					<input type="hidden" id="issueId" value="${issueId}">
					<input type="hidden" id="issueAnalysisId" >
					<input type="hidden" id="issueEncId" value="${issueEncId}">
					<div class="tab-content">
					<div class="box">
							<div class="box-body">
								 <label class="has-float-label ">
								  <span style="color: #2e74e6;font-size: 24px;">Issue Details</span>
								</label>
								<br>
								
								
								<div class="row" style="line-height:0.5px;">
									<div class="col col-sm-1 col-md-6">
										<div class="box box-success">
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Registration Number :</th>
															<td class="value"><span id="vehicle"></span></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Type :</th>
															<td class="value"><span id="vehicleType"></span></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Model :</th>
															<td class="value"><span id="vehicleModel"></span></td>
														</tr>
														<tr class="row">
															<th class="key">Summary :</th>
															<td class="value"><span id="complaint"></span></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="col col-sm-1 col-md-6">
										<div class="box box-success">
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row" >
															<th class="key">Category :</th>
															<td class="value"><span id="breakdownType"></span></td>
														</tr>
														<tr class="row">
															<th class="key">Driver:</th>
															<td class="value"><span id="driver"></span></td>
														</tr>
														<tr class="row">
															<th class="key">Route :</th>
															<td class="value"><span id="route"></span></td>
														</tr>
														<tr class="row">
															<th class="key">Date :</th>
															<td class="value"><span id="issueDate"></span></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div> 
								</div>
								
								
								
								
								
								<!-- <div class="col col-sm-1 col-md-3">
									 <label class="has-float-label">
									   <input type="text" class="browser-default" id="reason"  style="width: 100%;" readonly="readonly"></input>
									    <span style="color: #2e74e6;font-size: 18px;">Reason</span>
									  </label>
								</div> -->
								<!-- <div class="col col-sm-1 col-md-5">
									 <label class="has-float-label">
									   <input type="text" class="form-control browser-default custom-select" id="vehicle" readonly="readonly" >
									    <span style="color: #2e74e6;font-size: 18px;">Vehicle</span>
									  </label>
								</div>
								<div class="col col-sm-1 col-md-5">
									 <label class="has-float-label">
									   <input type="text" class="form-control browser-default custom-select" id="summary" readonly="readonly" >
									    <span style="color: #2e74e6;font-size: 18px;">Summary</span>
									  </label>
								</div> -->
								
							</div>
						</div>
						<br><br>
						<div class="box">
							<div class="box-body">
								 <label class="has-float-label ">
								  <span style="color: #2e74e6;font-size: 24px;">Issue Analysis</span>
								</label>
								<br>
								<div class="row" >
								<div class="col col-sm-1 col-md-6">
									 <label class="has-float-label">
									   <textarea class="browser-default textStyle" id="reason" rows="" cols="" style="width: 100%;"></textarea>
									    <span style="color: #2e74e6;font-size: 18px;">Reason<abbr title="required">*</abbr></span>
									  </label>
								</div>
								<div class="col col-sm-1 col-md-3 btn-group float-right" data-toggle="buttons" id ="Group" role="group" aria-label="Basic radio toggle button group" style="padding-bottom: 25px;">
								  <label class="btn btn-sm btn-info btnSelect active" id="mandatoryLabelId" onclick="checkAvoidable(1);">
								   <input type="hidden" class="btn-check" name="partDiscountTaxTypeId" id="mandatoryId"  value="false"  onclick="checkAvoidable(2);" autocomplete="off">  Mandatory
								  </label>
								  <label class="btn btn-sm btn-info btnSelect" id="avoidableLabelId" onclick="checkAvoidable(2);">
								   <input type="hidden" class="btn-check" name="partDiscountTaxTypeId" id="avoidableId"  value="false"  onclick="checkAvoidable(2);" autocomplete="off">  Avoidable
								  </label>
								  <input type="hidden" id="finalPartDiscountTaxTypId"  value="1">
								
							</div>
							<br>
									<div class="col col-sm-1 col-md-3 float-right ">
										 <input type="radio"
											class="btn-check" name="solution"
											id="temporaryId" autocomplete="off"> <label id ="labelTemporaryId"
											class="btn btn-outline-info" for="temporaryId">Temporary
										</label> <input type="radio" class="btn-check" name="solution"
											id="permanentId" autocomplete="off" checked > <label id="labelPermanentId"
											class="btn btn-outline-success" for="permanentId">Permanent</label><br>
											 <samp id="msgIssue" style="color: blue; "><b>New issue will be created if selected Temporary.</b></samp>
									</div>
								</div>
							<div class="row" >
								<div class="col col-sm-1 col-md-6">
									 <label class="has-float-label">
									   <textarea class="browser-default textStyle" id="tempSolution" rows="" cols="" style="width: 100%;"></textarea>
									    <span style="color: #2e74e6;font-size: 18px;">Temporary Solution </span>
									  </label>
								</div>
								<div class="col col-sm-1 col-md-6">
									 <label class="has-float-label">
									   <textarea class="browser-default textStyle" id="fixSolution" rows="" cols="" style="width: 100%;"></textarea>
									    <span style="color: #2e74e6;font-size: 18px;">Permanent Solution<abbr title="required">*</abbr></span>
									  </label>
								</div>
								</div>
								<div class="row" >
							<div class="col col-sm-1 col-md-8">
								 <label class="has-float-label">
								   <textarea class="browser-default textStyle" id="futurePlan" rows="" cols="" style="width: 100%;"></textarea>
								    <span style="color: #2e74e6;font-size: 18px;">Future Plan<abbr title="required">*</abbr></span>
								  </label>
							</div>
						</div>
							</div>
						</div>
						
						<div class="row" >
							<button type="submit" id="submit" onclick="saveIssueAnalysis();" class="btn btn-success" >Save Analysis</button> &nbsp;&nbsp;
							<a class=" btn btn-info" href="<c:url value="/showIssues.in?Id=${issueEncId}"/>">
							<span id="Cancel">Cancel</span></a>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/IssueAnalysis/AddIssueAnalysis.js"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	
</div>