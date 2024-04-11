<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/viewRenewalReminder.in"/>"> RenewalReminder</a>/ <small> Search Renewal Reminder</small>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
	<input type="hidden" id="searchByDifferentFilter" value="${search}">
		<div class="row">
			<div class="main-body">
				<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="renewalSearchTable" class="table table-striped table-bordered" >
							
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/NewVehiclelanguage.js" />"></script> --%>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderSearch.js" />"></script>	
</div>



