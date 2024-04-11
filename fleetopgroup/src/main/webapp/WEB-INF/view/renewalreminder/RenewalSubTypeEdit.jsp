<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addRenewalSubType.in"/>"> New Renewal
							SubType</a> / <span id="NewRenewalType">Edit RenewalType</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addRenewalSubType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty renewalSubType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddRenewal">Edit Renewal Sub Type</h1>
								</div>
								<form action="updateRenewalSubType.in" method="post"
									onsubmit="return validateSubReTypeUpdate()">
									<div class="panel-body">
										<input name="renewal_Subid"
											value="${renewalSubType.renewal_Subid}" type="hidden" />
										<div class="row">
											<label class="L-size control-label"><span id="Type">Edit
													Type</span><abbr title="required">*</abbr></label>
											<div class="I-size">
												<select class="form-text selectType" name="renewal_id"
													style="width: 100%;" id="selectReType" required="required">
													<option value="${renewalSubType.renewal_id}"><c:out
															value="${renewalSubType.renewal_Type}" /></option>
													<c:forEach items="${renewalType}" var="renewalType">
														<option value="${renewalType.renewal_id}">${renewalType.renewal_Type}
														</option>
													</c:forEach>
												</select> <label id="errorReType" class="error"></label>
												<input id="renewalTypeVal" name="renewal_Type" type="hidden"/>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label">Edit Sub Type:</label>
											<div class="I-size">
												<input name="renewal_SubType"
													value="${renewalSubType.renewal_SubType}" id="SubReTypeUpdate"
													Class="form-text" /> <label id="erroSubReditSubReType"
													class="error"></label>

											</div>
										</div>

										<div class="row">
											<label class="L-size control-label" for="Renewal_theft">.</label>
											<div class="I-size">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addRenewalSubType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty renewalSubType}">
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
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalSubType.validate.js" />"></script>
		
		<script type="text/javascript">
		 $(document).ready(function() {
							$('#renewalTypeVal').val($("#selectReType option:selected").text().trim());
							$('#selectReType').on('change', function() {
								$('#renewalTypeVal').val($("#selectReType option:selected").text().trim());
							});

		});
	</script>
</div>