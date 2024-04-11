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
					<a href="open" >Home</a>/ <a href="getRRNotificationList.in">RR Notification</a>
				</div>
			</div>
		</div>
	</section>
	<section class="panel panel-success">
		<div class="row" id="searchData">
			<div id="countDiv"  class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
							<span class="info-box-text" id="countId"></span> 
							<span id="totalRRCount" class="info-box-number"></span>
						</div>
					</div>
				</div>
			</div>
	</section>
	<div id="listTab" class="main-tabs"> <!-- style="display: none;" -->
		<ul class="nav nav-pills" id="list">
			<li class="active"  id="dueSoonTab"><a href="#" id="dueSoon">Due Soon</a></li>	
			<li class="" id="overDueTab"><a href="#" id="overDue" >Over Due</a></li>
		</ul>
  	</div>
	<div class="content" >
		<div class="main-body">
			<div class="box">
				<div class="box-body">
					<div class="table-responsive">
						<input type="hidden" id="startPage" value="${SelectPage}"> 
						<table  class="table">
							<thead style="font-size: large;">
								<tr>
									<td>No</td>
									<td>RR Number</td>
									<td>Vehicle Number </td>
									<td>Vehicle Group</td>
									<td>Renewal Type</td>
									<td>Validity From</td>
									<td>Validity To</td>
									<!-- <td>ACtion</td> -->
								</tr>
							</thead>
							<tbody id="rrTable">
							</tbody>
						
						</table>
					</div>
				</div>
			</div>
			<div class="text-center">
				<ul id="navigationBar" class="pagination pagination-lg pager"> </ul>
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/notifications/RRNotificationAlert.js"></script>
