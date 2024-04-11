<%@ include file="taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
						<a href="open" >Home</a>/ <a href="getUserNotificationList.in">Unread Notification</a>
				</div>
				
			</div>
		</div>
	</section>
	<section class="panel panel-success">
		<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Unread Notifications</span> 
							<span id="totalClothInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
				
			</div>
			
	</section>
		<div id="listTab" class="main-tabs" style="display: none;">
						<ul class="nav nav-pills" id="list">
							<li role="presentation" id="All"><a
								href="#" onclick="getUserNotificationList(1);">Unread Notification</a></li>
							<li class="tab-link" role="presentation" id="AllStock"><a
								href="#" onclick="getReadNotificationList(1);">Read Notification</a></li>	
							<li class="tab-link" role="presentation" id="AllSent"><a
								href="#" onclick="getSentNotificationList(1);">Sent Notification</a></li>	
						</ul>
	  </div>
	<div class="content" >
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

	</div>
	<div class="modal fade" id="partModal" role="dialog">
				<div class="modal-dialog modal-lg" style="width: 1000px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn btn-danger"
								data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Notification Details</h4>
						</div>
						<div class="modal-body">
								<div class="box" id="modalBody">
									
								</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default">
								<span id="Close" onclick="getUserNotificationList(1);">Close</span>
							</button>
						</div>
					</div>
				</div>
			</div>
			
				<div class="modal fade" id="partModal2" role="dialog">
				<div class="modal-dialog modal-lg" style="width: 1000px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn btn-danger"
								data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Notification Details</h4>
						</div>
						<div class="modal-body">
								<div class="box" id="modalBody2">
									
								</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close" >Close</span>
							</button>
						</div>
					</div>
				</div>
			</div>
	
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/notifications/ViewNotificationList.js"></script>
