import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/AutoCompleteUtilty/AutoCompleteUtility.dart';
import 'package:fleetop/CustomAutoComplete.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:nice_button/NiceButton.dart';
import 'package:shared_preferences/shared_preferences.dart';

class EditWorkOrder extends KFDrawerContent {
  final Map woData;
  EditWorkOrder({Key key, this.woData});
  @override
  _EditWorkOrderState createState() => _EditWorkOrderState();
}

class _EditWorkOrderState extends State<EditWorkOrder> {
  bool showDriverNumberCol = false;
  bool TallyCompanyMasterInWO = false;
  bool showStartAndDueTimeField = false;
  double _height;
  double _width;
  Size size;
  String companyId;
  String userId;
  String email;
  int priorityId = 0;
  List priorotyList = new List();
  List vehicleData = List();
  int vehicleId = 0;
  List driverData = List();
  int driverId = 0;
  int subLocationId = 0;
  TextEditingController assigneeName = new TextEditingController();
  List assigneeData = List();
  int assigneeId = 0;
  TextEditingController driverName = new TextEditingController();
  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");
  TextEditingController fromDateController = new TextEditingController();
  TextEditingController toDateController = new TextEditingController();
  TextEditingController vehicleodometerRange = new TextEditingController();
  TextEditingController gpsodometerRange = new TextEditingController();
  TextEditingController initialNotes = new TextEditingController();
  TextEditingController tallyCompany = new TextEditingController();
  TextEditingController gpsLocation = new TextEditingController();
  TextEditingController vehicleNumber = new TextEditingController();
  TextEditingController woEndDate = new TextEditingController();
  TextEditingController woEndTime = new TextEditingController();
  TextEditingController woStartTime = new TextEditingController();
  TextEditingController driverNumber = new TextEditingController();
  TextEditingController outWorkStation = new TextEditingController();

  bool tallyConfig = false;
  int tallyCompanyId = 0;
  List tallyServiceData = List();
  String vehicleodometerRangeOld = "";
  int vehicle_ExpectedOdameter = 0;
  final TimeOfDay time = null;
  DateTime mydate;
  int Odometer = 0;
  bool allowGPSIntegration = false;
  String minBackDate = "";
  String serverDate = "";
  String workOrderId = "";
  String workorders_Number = "", workorders_document = "", Ovid = "";
  String Ovname = "";
  String Odid = "";
  String Odname = "";
  String Oassignee = "";
  String mainLocationIds = "";
  String editSubLocationId = "";
  String editSubLocation = "";
  bool showSubLocation = false;
  String workOrdersStatusId = "";
  String accidentId = "0";
  int issueId = 0;
  bool validateOdometerInWorkOrder = false;
  int OassigneeId = 0;
  bool startTimeForGroup = false;
  int partLocationId = 0;
  TextEditingController new_workorders_location = new TextEditingController();
  bool validateSubLocation = false;
  bool showgpsodometer = false;
  bool showOutWorkStationCol = false;
  bool showRouteCol = false;
  bool showPONoFeild = false;
  bool showDieselCol = false;
  Map configData = new Map();
  TextEditingController workorders_route = new TextEditingController();
  TextEditingController indentno = new TextEditingController();
  TextEditingController workorders_diesel = new TextEditingController();
  String vehicle_Odometer_old = "";
  @override
  void initState() {
    super.initState();
    getSessionData();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    makedropdown();
    setData();
  }

  setData() {
    if (widget.woData != null && widget.woData.isNotEmpty) {
      validateOdometerInWorkOrder =
          widget.woData["validateOdometerInWorkOrder"];
      configData = widget.woData["configuration"];
      Odometer = widget.woData["vehicle"]["vehicle_Odometer"];
      allowGPSIntegration =
          widget.woData["gpsConfiguration"]["allowGPSIntegration"];
      companyId = widget.woData["companyId"].toString();
      tallyConfig = widget.woData["tallyConfig"];
      minBackDate = widget.woData["minBackDate"];
      serverDate = widget.woData["serverDate"];
      workOrderId = widget.woData["WorkOrder"]["workorders_id"].toString();
      workorders_Number =
          widget.woData["WorkOrder"]["workorders_Number"].toString();
      workorders_document =
          widget.woData["WorkOrder"]["workorders_document"].toString();
      Ovid = widget.woData["WorkOrder"]["vehicle_vid"].toString();
      vehicleId = widget.woData["WorkOrder"]["vehicle_vid"];
      partLocationId = widget.woData["WorkOrder"]["workorders_location_ID"];
      Ovname = widget.woData["WorkOrder"]["vehicle_registration"].toString();
      vehicleNumber.text = Ovname;
      Odid = widget.woData["WorkOrder"]["workorders_driver_id"].toString();
      Odname = widget.woData["WorkOrder"]["workorders_drivername"].toString();
      driverId = widget.woData["WorkOrder"]["workorders_driver_id"];
      driverName.text = Odname == "null" ? "" : Odname;
      Oassignee = widget.woData["WorkOrder"]["assignee"].toString();
      OassigneeId = widget.woData["WorkOrder"]["assigneeId"];
      assigneeId = widget.woData["WorkOrder"]["assigneeId"];
      assigneeName.text = Oassignee;
      showStartAndDueTimeField =
          widget.woData["configuration"]["showStartAndDueTimeField"];
      issueId = widget.woData["issueId"];
      showSubLocation = widget.woData["showSubLocation"];
      mainLocationIds = widget.woData["mainLocationIds"];
      editSubLocationId =
          widget.woData["WorkOrder"]["subLocationId"].toString();
      editSubLocation = widget.woData["subLocation"];
      workOrdersStatusId =
          widget.woData["WorkOrder"]["workorders_statusId"].toString();
      accidentId = widget.woData["WorkOrder"]["accidentId"].toString();
      TallyCompanyMasterInWO = widget.woData["tallyConfig"];
      tallyCompanyId = widget.woData["WorkOrder"]["tallyCompanyId"];
      tallyCompany.text = widget.woData["WorkOrder"]["tallyCompanyName"];
      initialNotes.text = widget.woData["WorkOrder"]["initial_note"];
      fromDateController.text = widget.woData["WorkOrder"]["start_date"];
      toDateController.text = widget.woData["WorkOrder"]["due_date"];
      new_workorders_location.text =
          widget.woData["WorkOrder"]["workorders_location"];
      vehicleodometerRange.text =
          widget.woData["WorkOrder"]["vehicle_Odometer"].toString();
      vehicleodometerRangeOld = vehicleodometerRange.text;
      priorityId = widget.woData["WorkOrder"]["priorityId"];
      outWorkStation.text = widget.woData["WorkOrder"]["out_work_station"];
      workorders_route.text = widget.woData["WorkOrder"]["workorders_route"];
      vehicle_Odometer_old =
          widget.woData["WorkOrder"]["vehicle_Odometer_old"].toString();
      indentno.text = widget.woData["WorkOrder"]["indentno"];
      if (widget.woData["WorkOrder"]["gpsOdometer"] != null &&
          widget.woData["WorkOrder"]["gpsOdometer"] > 0) {
        showgpsodometer = true;
      }
      if (showStartAndDueTimeField != null && showStartAndDueTimeField) {
        woStartTime.text = widget.woData["WorkOrder"]["start_time"].toString();
        woEndTime.text = widget.woData["WorkOrder"]["due_time"].toString();
      }
      if (configData != null && configData.isNotEmpty) {
        if (configData["showOutWorkStationCol"] != null &&
            configData["showOutWorkStationCol"]) {
          showOutWorkStationCol = true;
        }
        if (configData["showRouteCol"] != null && configData["showRouteCol"]) {
          workorders_route.text = FileUtility.setStringData(
              widget.woData["WorkOrder"]["workorders_route"].toString());
          showRouteCol = true;
        }
        if (configData["showPONoFeild"] != null &&
            configData["showPONoFeild"]) {
          indentno.text = FileUtility.setStringData(
              widget.woData["WorkOrder"]["indentno"].toString());
          showPONoFeild = true;
        }
        if (configData["showDieselCol"] != null &&
            configData["showDieselCol"]) {
          workorders_diesel.text = FileUtility.setStringData(
              widget.woData["WorkOrder"]["workorders_diesel"]);
          showDieselCol = true;
        }
      }
    }
  }

  void makedropdown() {
    var obj1 = {'priorityName': 'NORMAL', 'priorityId': 1};
    var obj2 = {'priorityName': 'HIGH', 'priorityId': 2};
    var obj3 = {'priorityName': 'LOW', 'priorityId': 3};
    var obj4 = {'priorityName': 'URGENT', 'priorityId': 4};
    var obj5 = {'priorityName': 'VERY URGENT', 'priorityId': 4};
    priorotyList.add(obj1);
    priorotyList.add(obj2);
    priorotyList.add(obj3);
    priorotyList.add(obj4);
    priorotyList.add(obj5);
    setState(() {
      priorityId = 1;
    });
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        // backgroundColor: Color(0xff7a6fe9),
        body: Container(
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(35.0),
          topRight: const Radius.circular(35.0),
        ),
        gradient: new LinearGradient(
            colors: [
              Color(0xFF4A00E0),
              Color(0xFF4A00E0),
              // Color(0xFF57d0eb), Color(0xFF57d0eb)
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: Stack(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 40),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  IconButton(
                    icon: Icon(
                      Icons.menu,
                      color: Colors.white,
                      size: 25,
                    ),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                  Container(
                    margin: EdgeInsets.only(right: _width / 2 - 118),
                    child: Text(
                      'Edit Work Order',
                      style: GoogleFonts.openSans(
                          textStyle: TextStyle(
                              color: Colors.white,
                              fontSize: 20,
                              fontWeight: FontWeight.w600)),
                    ),
                  ),
                ],
              ),
            ),
          ),
          upperBody()
        ],
      ),
    ));
  }

  Widget upperBody() {
    return Scrollbar(
      child: Container(
        child: editWorkOrderBody(),
      ),
    );
  }

  Widget editWorkOrderBody() {
    return Container(
      margin: EdgeInsets.only(top: 110, left: 2, right: 2, bottom: 0),
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height >= 775.0
          ? MediaQuery.of(context).size.height
          : 775.0,
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(45.0),
          topRight: const Radius.circular(45.0),
        ),
        gradient: new LinearGradient(
            colors: [Colors.white, Colors.white],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: ListView(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          children: <Widget>[
            vehicleNumberAC(),
            driverNameAC(),
            Visibility(
              visible: showDriverNumberCol,
              child: Column(
                children: [
                  WorkOrderUtility.renderText("Driver No"),
                  driverNoTF(),
                ],
              ),
            ),
            assigneeNameAC(),
            fromDateTF(),
            renderFromTime(),
            toDateTF(),
            renderToTime(),
            gpsLocationTF(),
            WorkOrderUtility.renderText("Work location"),
            workLocationPermissionTF(),
            // Visibility(
            //   visible: subLocation,
            //   child: Column(
            //     children: [
            //       WorkOrderUtility.renderText("Sub Location"),
            //       renderSubLocation(),
            //     ],
            //   ),
            // ),
            WorkOrderUtility.renderText("Odometer"),
            odometerRangeTF(),
            Visibility(visible: showgpsodometer, child: gpsodometerRangeTF()),
            Visibility(
              visible: showOutWorkStationCol != null && showOutWorkStationCol,
              child: outWorkStationTF(),
            ),
            Visibility(
              visible: showRouteCol != null && showRouteCol,
              child: workOrderRouteTF(),
            ),
            Visibility(
              visible: showPONoFeild != null && showPONoFeild,
              child: poNumberTF(),
            ),
            Visibility(
              visible: showDieselCol != null && showDieselCol,
              child: dieselTF(),
            ),
            priorotyDropdwon(),
            Visibility(
                visible: TallyCompanyMasterInWO != null
                    ? TallyCompanyMasterInWO
                    : false,
                child: tallyCompanyAC()),

            iitialNotesTF(),
            updateWorkOrder()
          ]),
    );
  }

  Widget workLocationPermissionTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        enabled: false,
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: new_workorders_location,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.tasks,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Work Location :',
          hintText: 'Work Location:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget driverNoTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: driverNumber,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.truck,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Driver No :',
          hintText: 'Driver No:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget outWorkStationTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: outWorkStation,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.truck,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Out Work Station :',
          hintText: 'Out Work Station:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget workOrderRouteTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: workorders_route,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.truck,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Route :',
          hintText: 'Route:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget poNumberTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: indentno,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.truck,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'PO. No :',
          hintText: 'PO. No:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget dieselTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: workorders_diesel,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.truck,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Diesel :',
          hintText: 'Diesel:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget updateWorkOrder() {
    return Center(
      child: NiceButton(
        radius: 40,
        text: "Edit",
        icon: Icons.edit,
        gradientColors: [
          Color(0xFF4A00E0),
          Color(0xFF4A00E0),
        ],
        onPressed: () {
          validateWorkOrderData();
        },
      ),
    );
  }

  validateWorkOrderData() async {
    var res = validateEditData();
    if (res != null && res) {
      var jsonObject = {};
      jsonObject["workOrderId"] = workOrderId;
      jsonObject["woNumber"] = workorders_Number;
      jsonObject["vid"] = vehicleId.toString();
      jsonObject["driverId"] = driverId.toString();
      jsonObject["driverNumber"] = driverNumber.text;
      jsonObject["assignedTo"] = assigneeId.toString();
      jsonObject["woStartDate"] = fromDateController.text;
      jsonObject["woStartTime"] = woStartTime.text;
      jsonObject["woEndDate"] = toDateController.text;
      jsonObject["woEndTime"] = woEndTime.text;
      jsonObject["outWorkStation"] = outWorkStation.text;
      jsonObject["gpsWorkLocation"] = gpsLocation.text;
      jsonObject["location"] = partLocationId.toString();
      jsonObject["workorders_route"] = workorders_route.text;
      jsonObject["vehicle_Odometer_Old"] = vehicle_Odometer_old.toString();
      jsonObject["vehicle_Odometer"] = vehicleodometerRange.text;
      jsonObject["gpsOdometer"] = gpsodometerRange.text;
      jsonObject["workorders_diesel"] = workorders_diesel.text;
      jsonObject["indentno"] = indentno.text;
      jsonObject["priority"] = priorityId.toString();
      jsonObject["initial_note"] = initialNotes.text;
      jsonObject["tallyCompanyId"] = tallyCompanyId.toString();
      jsonObject["subLocationId"] = subLocationId.toString();
      jsonObject["workOrdersStatusId"] = workOrdersStatusId.toString();
      jsonObject["accidentId"] = accidentId.toString();
      jsonObject["companyId"] = companyId.toString();
      jsonObject["userId"] = userId.toString();
      jsonObject["isFromMob"] = 'true';
      var data = await ApiCall.getDataFromApi(
          URI.EDIT_WO_FROM_MOB, jsonObject, URI.LIVE_URI, context);
      if (data != null) {
        if (data["inTripRoute"] != null && data["inTripRoute"]) {
          WorkOrderUtility.showMessage(
              'errors',
              'vehicle in Triproute Status Hence Cannot Update WorkOrder !',
              context);
        }
        if (data["changeAccidentDetails"] != null &&
            data["changeAccidentDetails"]) {
          WorkOrderUtility.showMessage(
              'info',
              'You cannot change vehicle number/ Driver as this entry related to vehicle accident !',
              context);
        }
        if (data["inSold"] != null && data["inSold"]) {
          WorkOrderUtility.showMessage(
              'errors',
              'vehicle in Sold Status Hence Cannot Update WorkOrder !',
              context);
        }
        if (data["inActive"] != null && data["inActive"]) {
          WorkOrderUtility.showMessage(
              'errors',
              'vehicle in InActive Status Hence Cannot Update WorkOrder !',
              context);
        }
        if (data["inSurrender"] != null && data["inSurrender"]) {
          WorkOrderUtility.showMessage(
              'errors',
              'vehicle in Surrender Status Hence Cannot Update WorkOrder !',
              context);
        }
        if (data["inAccident"] != null && data["inAccident"]) {
          WorkOrderUtility.showMessage(
              'errors',
              'vehicle in Accident Status Hence Cannot Update WorkOrder !',
              context);
          return false;
        }
        if (data["inWorkShop"] != null && data["inWorkShop"]) {
          WorkOrderUtility.showMessage(
              'errors',
              'selected Vehicle in Workshop Status Hence Cannot Update WorkOrder !',
              context);
        }
        if (data["workOrderUpdated"] != null && data["workOrderUpdated"]) {
          Navigator.pop(context);
          // WorkOrderUtility.showMessage(
          //     'success', 'WorkOrder Updated !', context);
        }

        if (data["emptyWO"] != null && data["emptyWO"]) {
          WorkOrderUtility.showMessage(
              'errors', 'WorkOrder cannot be Updated !', context);
        }
      } else {
        WorkOrderUtility.showMessage('error', 'Some error occured!', context);
      }
    }
  }

  bool checkStartAndEndDateOFWO() {
    try {
      var endDate = format.parse(toDateController.text);
      var selectedData = fromDateController.text.split("-");
      var berlinWallFellDate = new DateTime.utc(int.parse(selectedData[2]),
          int.parse(selectedData[1]), int.parse(selectedData[0]));
      if (berlinWallFellDate.compareTo(endDate) > 0) {
        return false;
      } else {
        return true;
      }
    } catch (e) {
      return false;
    }
  }

  bool checkBothDateAreEqual() {
    try {
      String startDate = fromDateController.text;
      String endDate = toDateController.text;
      if (startDate == endDate) {
        return true;
      } else {
        return false;
      }
    } catch (e) {
      return false;
    }
  }

  bool validateEditData() {
    if (vehicleId <= 0) {
      WorkOrderUtility.showMessage(
          'errors', 'Please Select Vehicle !', context);
      return false;
    }
    if (assigneeId <= 0) {
      WorkOrderUtility.showMessage(
          'Info', 'Please Select Assigned To !', context);
      return false;
    }
    if (assigneeId <= 0) {
      WorkOrderUtility.showMessage(
          'Info', 'Please Select Assigned To !', context);
      return false;
    }
    if (fromDateController.text == "" || fromDateController.text.length == 0) {
      WorkOrderUtility.showMessage('Info', 'Please Select Start Date', context);
      return false;
    }
    if (startTimeForGroup != null && startTimeForGroup) {
      if (woStartTime.text == "" || woStartTime.text.length == 0) {
        WorkOrderUtility.showMessage(
            'Info', 'Please Select Start Time !', context);
        return false;
      }
    }
    if (toDateController.text == "" || toDateController.text.length == 0) {
      WorkOrderUtility.showMessage('Info', 'Please Select Due Date', context);
      return false;
    }
    bool dateCheck = checkStartAndEndDateOFWO();
    bool equalCheck = checkBothDateAreEqual();
    if (dateCheck != null && !dateCheck && !equalCheck) {
      setState(() {
        toDateController.text = '';
      });
      WorkOrderUtility.showMessage(
          'Info', 'Due Date Can Not Be Greater Than Start Date !', context);
      return false;
    }
    if (startTimeForGroup != null && startTimeForGroup) {
      if (woEndTime.text == "" || woEndTime.text.length == 0) {
        WorkOrderUtility.showMessage(
            'Info', 'Please Select Due Time !', context);
        return false;
      }
    }
    if (partLocationId <= 0 && new_workorders_location.text == "") {
      WorkOrderUtility.showMessage(
          'Info', 'Please Select Work Location !', context);
      return false;
    }

    if (vehicleodometerRange.text == "" ||
        vehicleodometerRange.text.length == 0 ||
        int.parse(vehicleodometerRange.text) == 0) {
      WorkOrderUtility.showMessage('Info', 'Please Enter Odometer', context);
      return false;
    }

    if (tallyConfig) {
      if (tallyCompanyId <= 0 || tallyCompany.text == "") {
        WorkOrderUtility.showMessage(
            'Info', 'Please Select Tally Company Master', context);
        return false;
      }
    } else {
      tallyCompanyId = 0;
    }

    if (showSubLocation != null && showSubLocation) {
      List mainlocationIdsList = mainLocationIds.split(",");
      if (mainlocationIdsList != null && mainlocationIdsList.isNotEmpty) {
        for (var i = 0; i < mainlocationIdsList.length; i++) {
          if (partLocationId == int.parse(mainlocationIdsList[i].toString())) {
            validateSubLocation = true;
          }
        }
      }
      if (validateSubLocation != null && validateSubLocation) {
        if (subLocationId == 0) {
          WorkOrderUtility.showMessage(
              'info', 'Please Select SubLocation', context);
          return false;
        }
      }
      if (validateSubLocation && subLocationId == 0) {
        WorkOrderUtility.showMessage(
            'info', 'Please Select Sub Location', context);
        return false;
      }
    }
    var ab = vehicleodometerRange.text != null &&
            vehicleodometerRange.text.length > 0
        ? int.parse(vehicleodometerRange.text)
        : 0;

    var expectedOdo = vehicle_ExpectedOdameter + ab;
    var odometer = int.parse(vehicleodometerRangeOld);
    var tripOpeningKM = FileUtility.checkAndSetNum(vehicleodometerRange);
    var selectedData = fromDateController.text.split("-");
    var now = new DateTime.now();
    var berlinWallFellDate = new DateTime.utc(int.parse(selectedData[2]),
        int.parse(selectedData[1]), int.parse(selectedData[0]));
    if (validateOdometerInWorkOrder != null && validateOdometerInWorkOrder) {
      if (tripOpeningKM == 0) {
        WorkOrderUtility.showMessage('Info', 'Please Enter Odometer', context);
        return false;
      }
      if (fromDateController.text != "") {
        if (berlinWallFellDate.compareTo(now) <= 0) {
          if (tripOpeningKM > 0 && tripOpeningKM < odometer) {
            WorkOrderUtility.showMessage(
                'Info',
                'Trip Odometer Should Not Be Less Than ' + odometer.toString(),
                context);
            return false;
          }
        }
      }
    }
    if (!validateOdometerInWorkOrder) {
      return true;
    }
    if (vehicle_ExpectedOdameter.toString() == '' ||
        vehicle_ExpectedOdameter.toString().length == 0) {
      return true;
    }

    if (tripOpeningKM > 0 && tripOpeningKM > expectedOdo) {
      WorkOrderUtility.showMessage(
          'Info',
          'Trip Odometer cannot be greater than ' + expectedOdo.toString(),
          context);
      return false;
    }
    return true;
  }

  Widget iitialNotesTF() {
    return Container(
      margin: EdgeInsets.only(top: 10, left: 10, bottom: 10, right: 10),
      child: Container(
        child: TextField(
          maxLines: 4,
          controller: initialNotes,
          style: TextStyle(color: Colors.black),
          decoration: InputDecoration(
              labelText: 'Initial Notes',
              hintText: 'Initial Notes',
              hintStyle: TextStyle(color: Colors.black),
              border:
                  OutlineInputBorder(borderRadius: BorderRadius.circular(5.0)),
              icon: Icon(
                Icons.comment,
                color: Colors.pinkAccent,
              )),
        ),
      ),
    );
  }

  Widget tallyCompanyAC() {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: Visibility(
          visible: tallyConfig,
          child: Container(
            child: CustomAutoComplete(
                suggestionList: tallyServiceData,
                controller: tallyCompany,
                hintLabel: 'Tally Company',
                label: 'Tally Company',
                dataKeyName: 'companyName',
                iconData: Icons.directions,
                onItemSelected: (suggestion) {
                  setState(() {
                    tallyCompany.text = suggestion['companyName'];
                    tallyCompanyId = suggestion['tallyCompanyId'];
                  });
                },
                onChanged: (pattern) {
                  //method for getting data from server
                  getTallyCompanyDetails(pattern);
                }),
          )),
    );
  }

  getTallyCompanyDetails(String str) async {
    tallyCompanyId = 0;
    setState(() {
      tallyServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TALLY_COMPANY_LIST, data, URI.LIVE_URI);

      if (response != null) {
        if (response["TallyCmpnyList"] != null) {
          setState(() {
            tallyServiceData = response["TallyCmpnyList"];
          });
        } else {
          setState(() {
            tallyServiceData = [];
          });
        }
      }
    } else {
      setState(() {
        tallyServiceData = [];
      });
    }
  }

  Widget priorotyDropdwon() {
    return priorotyList == null || priorotyList.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              margin: EdgeInsets.only(top: 2, left: 5, right: 5),
              width: _width,
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 0.5,
                    color: Colors.white70,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Please Select Prioroty"),
                            value:
                                priorityId != 0 ? priorityId.toString() : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                priorityId = int.parse(newValue);
                              });
                            },
                            items: priorotyList.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['priorityName'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['priorityId'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  Widget odometerRangeTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        inputFormatters: [
          WhitelistingTextInputFormatter(RegExp("[0-9]")),
          BlacklistingTextInputFormatter(RegExp("[.]")),
        ],
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: vehicleodometerRange,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.weight,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Odometer',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget renderFromTime() {
    return Visibility(
        visible: showStartAndDueTimeField != null && showStartAndDueTimeField,
        child: Container(
          child: woStartTimeTF(),
        ));
  }

  Widget renderToTime() {
    return Visibility(
        visible: showStartAndDueTimeField != null && showStartAndDueTimeField,
        child: Container(
          child: woEndTimeTF(),
        ));
  }

  Widget woStartTimeTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: DateTimeField(
        enabled: true,
        readOnly: true,
        format: timeFormat,
        controller: woStartTime,
        style: TextStyle(color: Colors.black),
        decoration: InputDecoration(
          prefixIcon: Icon(
            Icons.access_time,
            color: Colors.blue,
          ),
          hintText: "Select Start WO Time",
          labelText: "Select Start WO Time",
          hintStyle: TextStyle(color: Color(0xff493240)),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(5.0)),
        ),
        onShowPicker: (context, currentValue) async {
          final time = await showTimePicker(
            context: context,
            initialTime: TimeOfDay.fromDateTime(currentValue ?? DateTime.now()),
            builder: (BuildContext context, Widget child) {
              return MediaQuery(
                data: MediaQuery.of(context)
                    .copyWith(alwaysUse24HourFormat: true),
                child: child,
              );
            },
          );
          setState(() {
            woStartTime.text = FileUtility.getTime(time.toString());
          });

          return DateTimeField.convert(time);
        },
      ),
    );
  }

  Widget woEndTimeTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: DateTimeField(
        enabled: true,
        readOnly: true,
        format: timeFormat,
        controller: woEndTime,
        style: TextStyle(color: Colors.black),
        decoration: InputDecoration(
          prefixIcon: Icon(
            Icons.access_time,
            color: Colors.blue,
          ),
          hintText: "Select Due WO Time",
          labelText: "Select Due WO Time",
          hintStyle: TextStyle(color: Color(0xff493240)),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(5.0)),
        ),
        onShowPicker: (context, currentValue) async {
          final time = await showTimePicker(
            context: context,
            initialTime: TimeOfDay.fromDateTime(currentValue ?? DateTime.now()),
            builder: (BuildContext context, Widget child) {
              return MediaQuery(
                data: MediaQuery.of(context)
                    .copyWith(alwaysUse24HourFormat: true),
                child: child,
              );
            },
          );
          setState(() {
            woEndTime.text = FileUtility.getTime(time.toString());
          });

          return DateTimeField.convert(time);
        },
      ),
    );
  }

  Widget gpsLocationTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        readOnly: true,
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: gpsLocation,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.locationArrow,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'GPS Location :',
          hintText: 'GPS Location',
        ),
        autofocus: false,
      ),
    );
  }

  Widget gpsodometerRangeTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        readOnly: true,
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: gpsodometerRange,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.locationArrow,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'GPS Odometer',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget fromDate() {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, bottom: 10, right: 10),
      height: 60.0,
      child: DateTimeField(
        readOnly: true,
        controller: fromDateController,
        initialValue: fromDateController.text != null ? mydate : DateTime.now(),
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        decoration: InputDecoration(
          hintText: DateTime.now().toString(),
          labelText: "From Date",
          hintStyle: TextStyle(color: Color(0xff493240)),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
        ),
        format: format,
        onShowPicker: (context, currentValue) async {
          final date = await showDatePicker(
              context: context,
              firstDate: DateTime(DateTime.now().month - 2),
              initialDate: currentValue ?? DateTime.now(),
              lastDate: DateFormat('dd-MM-yyyy').parse(serverDate));
          if (date != null) {
            setState(() {
              fromDateController.text = date.day.toString() +
                  '-' +
                  date.month.toString() +
                  '-' +
                  date.year.toString();
            });
            return DateTimeField.combine(date, time);
          } else {
            return currentValue;
          }
        },
      ),
    );
  }

  Widget toDate() {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, bottom: 10, right: 10),
      height: 60.0,
      child: DateTimeField(
        readOnly: true,
        controller: toDateController,
        initialValue: toDateController.text != null ? mydate : DateTime.now(),
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        decoration: InputDecoration(
          hintText: DateTime.now().toString(),
          labelText: "To Date",
          hintStyle: TextStyle(color: Color(0xff493240)),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
        ),
        format: format,
        onShowPicker: (context, currentValue) async {
          final date = await showDatePicker(
              context: context,
              firstDate: DateTime(DateTime.now().month - 2),
              initialDate: currentValue ?? DateTime.now(),
              lastDate: DateFormat('dd-MM-yyyy').parse(serverDate));
          if (date != null) {
            setState(() {
              toDateController.text = date.day.toString() +
                  '-' +
                  date.month.toString() +
                  '-' +
                  date.year.toString();
            });
            return DateTimeField.combine(date, time);
          } else {
            return currentValue;
          }
        },
      ),
    );
  }

  Widget fromDateTF() {
    return Container(
      child: Column(
        children: [
          WorkOrderUtility.renderText("Start Date"),
          fromDate(),
        ],
      ),
    );
  }

  Widget toDateTF() {
    return Container(
      child: Column(
        children: [WorkOrderUtility.renderText("Due Date"), toDate()],
      ),
    );
  }

  Widget assigneeNameAC() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 2),
      child: AutoCompleteUtility(
          textLimit: 12,
          suggestionList: assigneeData,
          controller: assigneeName,
          hintLabel: 'Assignee Name',
          label: 'Assignee Name',
          dataKeyName: ['firstName', 'lastName'],
          iconData: FontAwesomeIcons.user,
          resetData: () {
            setState(() {
              assigneeId = 0;
              assigneeName.text = "";
            });
          },
          onItemSelected: (suggestion) {
            setState(() {
              assigneeId = suggestion["userprofile_id"];
              assigneeName.text = suggestion["firstName"].toString() +
                  " " +
                  suggestion["lastName"].toString();
            });
          },
          onChanged: (pattern) {
            assigneeAutocomplete(pattern);
          }),
    );
  }

  assigneeAutocomplete(String str) async {
    setState(() {
      assigneeData = [];
    });
    if (str != '' && str.length > 0) {
      var data = {
        'companyId': companyId,
        'term': str,
        'userId': userId,
        'isFromMob': 'true'
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_WORK_ORDER_USER_DETAILS, data, URI.LIVE_URI);
      if (response != null && response["userNameList"] != null) {
        setState(() {
          assigneeData = response["userNameList"];
        });
      } else {
        setState(() {
          assigneeData = [];
        });
      }
    }
  }

  Widget driverNameAC() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 2),
      child: AutoCompleteUtility(
          textLimit: 12,
          suggestionList: driverData,
          controller: driverName,
          hintLabel: 'Driver Name',
          label: 'Driver Name',
          dataKeyName: [
            "driver_empnumber",
            "driver_firstname",
            "driver_mobnumber"
          ],
          iconData: FontAwesomeIcons.truck,
          resetData: () {
            setState(() {
              driverId = 0;
              driverName.text = "";
            });
          },
          onItemSelected: (suggestion) {
            setState(() {
              driverId = suggestion["driver_id"];
              driverName.text = suggestion["driver_empnumber"] +
                  "-" +
                  suggestion["driver_firstname"] +
                  "-" +
                  suggestion["driver_mobnumber"];
            });
          },
          onChanged: (pattern) {
            driverAutocomplete(pattern);
          }),
    );
  }

  driverAutocomplete(String str) async {
    setState(() {
      driverData = [];
    });
    if (str != '' && str.length > 0) {
      var data = {
        'companyId': companyId,
        'term': str,
        'userId': userId,
        'isFromMob': 'true'
      };
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_WORK_ORDER_DRIVERLIST, data, URI.LIVE_URI);
      if (response != null && response["driverList"] != null) {
        setState(() {
          driverData = response["driverList"];
        });
      } else {
        setState(() {
          driverData = [];
        });
      }
    }
  }

  Widget vehicleNumberAC() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 2),
      child: CustomAutoComplete(
          resetData: () {
            setState(() {
              vehicleId = 0;
              vehicleNumber.text = '';
            });
          },
          textLimit: 12,
          suggestionList: vehicleData,
          controller: vehicleNumber,
          hintLabel: 'Vehicle Number',
          label: 'Vehicle Number',
          dataKeyName: 'vehicle_registration',
          iconData: FontAwesomeIcons.truck,
          onItemSelected: (suggestion) {
            setState(() {
              vehicleId = suggestion["vid"];
              vehicleNumber.text =
                  suggestion["vehicle_registration"].toString();
            });
            onSelectedVehicle(vehicleId);
          },
          onChanged: (pattern) {
            vehicleNumberSearchForRenewalReminder(pattern);
          }),
    );
  }

  onSelectedVehicle(int vid) async {
    var data = {
      'companyId': companyId,
      'vid': vid.toString(),
      'userId': userId
    };
    var response = await ApiCall.getDataWithoutLoader(
        URI.GET_ODO_DETAILS_FOR_WO, data, URI.LIVE_URI);
    if (response != null && response["wadd"] != null) {
      if (response["wadd"]["vehicle_Odometer"] != null &&
          response["wadd"]["vehicle_Odometer"] > 0) {
        setState(() {
          vehicleodometerRange.text =
              response["wadd"]["vehicle_Odometer"].toString();
          vehicleodometerRangeOld =
              response["wadd"]["vehicle_Odometer"].toString();
          gpsodometerRange.text =
              int.parse(response["wadd"]["gpsOdameter"].toInt().toString())
                  .toString();
          gpsLocation.text = response["wadd"]["gpsLocation"] != null
              ? response["wadd"]["gpsLocation"].toString()
              : "";
          vehicle_ExpectedOdameter =
              response["wadd"]["vehicle_ExpectedOdameter"];
        });
      }
    }
  }

  vehicleNumberSearchForRenewalReminder(String str) async {
    setState(() {
      vehicleData = [];
    });
    if (str != '' && str.length > 0) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_VEHICLE_DETAILS, data, URI.LIVE_URI);
      if (response != null && response["vehicleList"] != null) {
        setState(() {
          vehicleData = response["vehicleList"];
        });
      } else {
        setState(() {
          vehicleData = [];
        });
      }
    }
  }
}
