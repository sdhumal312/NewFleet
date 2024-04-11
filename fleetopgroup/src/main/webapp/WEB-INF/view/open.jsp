<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- <style>

.popup {
background-image: linear-gradient(to right,#ff8a00,#da1b60);
     position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
   /*  background:#0000; */
    height:350px;
    width:430px;
    border-radius:10px;
    border:2px solid #e5e5e5;
    font-family:'helvetica neue';
}

.popup:hover{zoom:1.3;}
.valid {
    height:30px;
    width:30px;
    background:#fff;
    margin:0 auto;
    margin-top:30px;
    border-radius:5px;
}

h2 {
    color:white;
    font-family:helvetica;
    font-size:.9em;
    text-align:center;
    font-weight:bold;
    line-height:10px;
    margin-top:26px;
}

.bottom-popup {
background-image: linear-gradient(to right,#ff8a00,#da1b60);
    width:100%;
    background:#0000;
    -webkit-border-bottom-right-radius: 7px;
    -webkit-border-bottom-left-radius: 7px;
    -moz-border-radius-bottomright: 7px;
    -moz-border-radius-bottomleft: 7px;
    border-bottom-right-radius: 7px;
    border-bottom-left-radius: 7px;
    margin-top:32px;
    padding:30px 0 30px 0;
}

#myPopup{
background-image: linear-gradient(to right,#ff8a00,#da1b60);
/* 	background:#0000; */
	color:#000000;
	font-family:helvetica;
	font-size:2em;
	padding-top:10px; 
	text-align:center;
	line-height:20px;
	text-shadow: 0px 0px 0px #4CB572; 
}
</style> -->
<div class="content-wrapper">
	<section class="content-header"></section>
	<section class="content">
		<c:if test="${param.message != null}">
			<div class="alert alert-success">${param.message}</div>
		</c:if>
		<br /> <br /> <br />
		
		<div align="center">
			 <sec:authorize access="hasAuthority('ADD_MASTER_DOCUMENT')">
					<!-- 	<a style="padding-left: 1100px;" data-toggle="modal"
							data-target="#MasterDocument"> <i class="fa fa-plus"></i> Add
							Vendor Document
						</a> -->
			</sec:authorize>
			<div class="intro-text">
			<input type="hidden" id="stockAlert" value="${configurationAlert.addNotification}">
				<h1 class="name" align="center">
					<img alt="" width="400" height="140"
						src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/FleetopLogo1.png"/>">
				</h1>
				<div>
					<p style="font-size: 16px;color: blue;">Fleetop Support Contact : 8097000075</p>
				</div>
				<div class="dashboard-box-left">
					<div class="line-board line-board-4" data-gs-width="4"
						style="height: 1080px;">

						<div class="row">
							<div class="alert alert-warning alert-dismissible" role="alert">
								<button type="button"
									onclick="this.parentNode.parentNode.removeChild(this.parentNode);"
									class="close" data-dismiss="alert"></button>
								<strong><i class="fa fa-warning"></i> Help!</strong>
								<marquee>
									<p style="font-size: 18pt">Dear user If any technical
										issues in fleetop please send full details to fleetop mailbox. 
										Mail ID : <span style="font-size: 18pt;color: white;">fleetopsupport@ivgroup.in</span></p>
								</marquee>
								<!-- <div id="popUp"></div> -->

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<div class="modal fade" id="PopupStock" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Stock Alert !!!</h4>
					</div>
					<div class="modal-body">
						<div class="row hide" id="fuelRow">
							<div>
								<h4>
									<label id="myPopupStockFuel" style="color: red; font: bold;">
										Low Fuel Alert !!</label>
								</h4>
							</div>
							<br> <br>
							<div>
								<table id="dataFuelTable" style="width: 90%; display: none;"
									class="table-responsive table">

								</table>
							</div>
						</div>
						<br /> <br /> <br />
						<div class="row hide" id="ureaRow">
							<div>
								<h4>
									<label id="myPopupStockUrea"
										style="color: red; font: bold; font-size: 3">Low Urea
										Alert !! </label>
								</h4>
							</div>
							<br> <br>
							<div>
								<table id="dataUreaTable" style="width: 90%; display: none;"
									class="table-responsive table">

								</table>
							</div>

						</div>
						<br>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="Popup" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
						<h4 class="modal-title">Welcome</h4>
					</div>
					<div class="modal-body">
						<div id="myPopup" >
					</div>

					<div class="modal-footer">
					<button class="btn btn-primary" style="width:50% ;margin-left: 25%; margin-top: 20%; "id="okPopup">Ok</button>
					</div>

				</div>
			</div>
		</div>
</div>

		<div class="modal fade" id="MasterDocument" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveVehicleDocument.in"
					enctype="multipart/form-data">

					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">New Master Document</h3>
						</div>
						<div class="panel-body">
							<div class="form-horizontal">

								
								<div class="row1">
									<div class="L-size">
										<label class="col-md-3"> Browse: </label>
									</div>
									<div class="I-size">
										<input type="file" accept="image/png, image/jpeg, image/gif"
											name="fileUpload" required="required" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									id="js-upload-submit" value="Add Document">Upload
									files</button>
								<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
							</div>

						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/master/showMarqueeAlert.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/master/ShowMarqueeStockAlert.js" />"></script>
</div>
