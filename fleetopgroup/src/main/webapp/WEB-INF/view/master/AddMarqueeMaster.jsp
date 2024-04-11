<%@page import="ch.qos.logback.classic.Logger"%>
<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message code="label.master.home" /></a> 
					/ <a href="<c:url value="addMarqueeMaster1.in"/>">Marquee Master </a>
				</div>
				<div class="pull-right">
					<div>
						<button type="submit" class="btn btn-success"
							id="createMarqueeMaster" data-toggle="modal"
							data-target="#addMarqueeMaster" data-whatever="@mdo"
							onclick="return addMarqueeMaster(); ">Add Marquee Master</button>
						<!-- <button class="add_field_button btn btn-success" class="input_fields_wrap" id ="btn" value = "0" onclick ="addNewField();"><i class="fa fa-plus"></i> -->
					</div>
				</div>
				
			</div>
		</div>
	</section>
	<div class="modal fade" id="addMarqueeMaster" role="dialog">
			<div class="modal-dialog" style="width:1000px;">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType">Add Marquee Master</h4>
					</div>
					<div class="modal-body"> 
						<div class="row1">
							<div class="input_fields_wrap">Please Select Company
							<div id='CompanyList'>  </div>
								
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="saveMarqueeMessage();" id="saveMessage">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="editMarqueeMaster" role="dialog">
			<div class="modal-dialog" style="width:1000px;">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType">Edit Marquee Message</h4>
					</div>
					<div class="modal-body"> 
						
						<div class="row1">
							<div class="input_fields_wrap">
							<div id='editMessageTable'>  </div>
								<!-- <button class="add_field_button btn btn-success" class="input_fields_wrap" id ="btn" value = "0" onclick ="addMessageField();"><i class="fa fa-plus"></i> -->
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="return updateMarqueeMessage(this);" id="updateMessage">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="tab-pane">
				<div class="panel box box-danger">
					<div class="box">
						<h3 style="text-align: center;">Marquee Master List</h3>
						<div class="box-body">
							<div style="align: center;" class="">
								<div id='MarqueeMasterDetailsTable'></div>	
							</div>
						</div>	
					</div>
				</div>
			</div>
		</div>
		<div class="main-body">
			<div class="box">
				<div class="box-body">
				 
					<div class="table-responsive">
						<input type="hidden" id="startPage" value="${SelectPage}">
						<table id="VendorPaymentTable" class="table table-hover table-bordered">
						</table>
					</div>
				</div>
			</div>
			<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
						</ul>
			</div>
		</div>
	</section>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<%-- <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script> --%>
	
	<%-- <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/jscolor.js"/></script> --%>
	<script type="text/javascript" 
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/master/addMarqueeMaster.js" />"></script>
</div>