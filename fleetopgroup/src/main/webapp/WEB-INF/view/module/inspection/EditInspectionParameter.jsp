<%@page import="java.util.HashMap"%>
<%@ include file="taglib.jsp"%>
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addInspectionParameter.in"/>">Inspection Parameter
						</a> / Edit Inspection Parameter
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveInspectionParameter eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Inspection Parameter Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.addInspectionParameterDocument eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Inspection Parameter Created Successfully.
			</div>
		</c:if>
		<%-- <c:if test="${param.updateTripSheetOptions eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Sheet Extra Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteTripSheetOptions eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Sheet Extra Deleted Successfully.
			</div>
		</c:if> --%>
		<c:if test="${param.alreadyInspectionParameter eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Inspection Parameter Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		
		
		<section class="content-body">
		<div class="row">
		<div class="col-md-9 col-sm-9 col-xs-12">
				<div class="box box-success">
					<div class="box-header">
						<h3 class="box-title">Revise Parameter Document</h3>
					</div>
					<div class="box-body no-padding">
						
						<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<div class="panel-body">
								<div class="form-horizontal">
									<form action="reviseParameterDocument.in" method="post"
										enctype="multipart/form-data">
										<input type="hidden" name="parameterPhotoId" id="parameterPhotoId"
											value="${InspectionParameter.parameterPhotoId}"> <input
											type="hidden" name="inspectionParameterId" id="inspectionParameterId"
											value="${InspectionParameter.inspectionParameterId}">
										<div class="row1">
											<label class="L-size control-label">Parameter Document
												Name<abbr
												title="required">*</abbr> </label>
											<div class="I-size">
												<input class="form-text" id="parameterName" type="text"
													name="parameterName" required="required"
													value="${InspectionParameter.parameterName}"
													class="form-text" maxlength="45"
													ondrop="return false;"> <label class="error"
													id="errorDocumentReviseName" style="display: none">
												</label>

											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label"> File</label>
											<div class="I-size">
												<input type="file" name="file" id="file"></input>
											</div>
										</div>


										<fieldset>
											<div class="L-size control-label"></div>
											<input class="btn btn-primary" type="submit" value="Update">
											<a class="btn btn-link"
												href="addInspectionParameter.html">Cancel</a>

										</fieldset>

									</form>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
		</section>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Type.validate.js" />"></script>
</div>