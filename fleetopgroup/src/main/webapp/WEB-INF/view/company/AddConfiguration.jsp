<%@ include file="taglib.jsp"%>
<style>
/* layout.css Style */
#filedrag {
	border-width: 2px;
	margin-bottom: 20px;
	display: none;
	font-weight: bold;
	text-align: center;
	padding: 1em 0;
	margin: 1em 0;
	color: #555;
	border: 2px dashed #555;
	border-radius: 7px;
	cursor: default;
}

}
#filedrag.hover {
	color: #f00;
	border-color: #f00;
	border-style: solid;
	box-shadow: inset 0 3px 4px #888;
}

#messages {
	padding: 0 10px;
	margin: 1em 0;
	border: 0px solid #999;
}

#progress p {
	display: block;
	width: 240px;
	padding: 2px 5px;
	margin: 2px 0;
	border: 1px inset #446;
	border-radius: 5px;
	background: #eee url("progress.png") 100% 0 repeat-y;
}

#progress p.success {
	background: #0c0 none 0 0 no-repeat;
}

#progress p.failed {
	background: #c00 none 0 0 no-repeat;
}
</style>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/masterEnableConfiguration"/>">Add Configuration</a>
				</div>
				<div class="pull-right">
						<a class="btn btn-success" href="<c:url value="/masterCompany/1.in"/>"> <span
							class="fa fa-plus"> Cancel</span>
						</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="main-body">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h1 id="AddVehicle">Save Configuration</h1>
							</div>
							<div class="panel-body">
								
								<div class="row1" id="" class="form-group">
									<label class="L-size control-label">
									Select Configuration Module : 
									</label>
									<div class="I-size">
										<select class="select2 form-text" id="moduleId">
										</select>
									</div>
								</div>
								<div class="form-group">
									
									<div class="I-size">
										<fieldset class="form-actions">
											<button type="submit" id="saveModuleConfig" class="btn btn-success">SAVE</button>
										</fieldset>
									</div>
								</div>
							</div>
						</div>
							
					 </div>
				 </div>
				</div>
			</div>
			
	
	</section>
	
	
	<script type="text/javascript">
		//$(".select2").select2();
	</script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/companyConfiguration.js" />"></script>
</div>