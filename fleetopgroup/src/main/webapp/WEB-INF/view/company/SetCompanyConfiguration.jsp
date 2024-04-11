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

/* new code */
.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 21px;
}

.switch .input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 18px;
  width: 18px;
  left: 3px;
  bottom: 2px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

.input:checked + .slider {
   background-color: #2196F3; 
}

.input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

.input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}


#configData{
	margin-left: 5%;
}
.switchContain {
   display: flex;
   flex-wrap: wrap;
}
.switchContain > div {
  margin: 5px;
  background-color: #f1f1f1; 
  text-align: center;
  font-size: 13px
}
.grid{
  display: grid;
  gap: 0.4rem;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  height : 58px;
} 
#companyId{
	
}


/*new ends  */


</style>
<head>
 <!-- Add Bootstrap Switch CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.4/css/bootstrap3/bootstrap-switch.min.css">
</head>
 <!-- Add Bootstrap Switch CSS -->
    <!-- <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap-switch-button@1.1.0/css/bootstrap-switch-button.min.css" rel="stylesheet">
 -->
</head>

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
						href="<c:url value="/masterEnableCompanyConfiguration"/>">Set Company Configuration</a>
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
			<div class="">
				<div class="main-body">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h1 id="AddVehicle">Set Company Configuration</h1>
							</div>
							<div class="panel-body">
								
								<div class="form-horizontal ">
									<div class="row1" id="" class="form-group">
										<label class="L-size control-label">
										SELECT COMPANY : 
										</label>
										<div class="I-size">
											<select class="select2 form-text" id="companyId">
											</select>
										</div>
									</div>
									<div class="" id="" class="form-group">
										<label class="L-size control-label">
										SELECT CONFIGURATION MODULE : 
										</label>
										<div class="I-size">
											<select class="select2 form-text " id="moduleId">
											</select>
										</div>
									</div>
									<div class="form-group">
										<div class="I-size">
											<label class="L-size control-label"></label>
											<fieldset class="form-actions">
												<button type="submit" id="getConfigurationData" class="btn btn-success">SAVE</button>
											</fieldset>
										</div>
									</div>

									<div class="form-group">
										<div id="configData">
											<div class="row switchContain" id="switchContainer">
										        
										    </div>
										</div>
									</div>
								</div>
								
							</div>
						</div>
							
					 </div>
				 </div>
				</div>
			</div>
			

				<div class="modal fade" id="modalContainer" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
				  <div class="modal-dialog modal-dialog-centered modal-lg">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h4 class="modal-title" id="exampleModalLabel">Update Property</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      		<div class="row1">
				      			<label style="font-size:15px;">Property :</label>
				      			<div>
				      				<lable style="font-size:15px;" id="propertyText"></lable>
				      			</div>
				      		</div>
				        	<div class="row1">
								<label class="control-label">Value</label>
								<div class="">
									<input type="text" class="form-text" id="propertyValue" 
										name="" placeholder="Enter value"/> <label
										class="error"></label>
									<input type="hidden" id="companyConfigId">
									<input type="hidden" id="property">
								</div>
							</div>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				        <button type="button" class="btn btn-primary" onClick="updatePropertyValue();">Save changes</button>
				      </div>
				    </div>
				  </div>
				</div>
			
	</section>
	<script type="text/javascript">
	 $(document).ready(function () {
	      
	 });
	</script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/companyConfiguration.js" />"></script>
</div>