import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/AutoCompleteUtilty/AutoCompleteUtility.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/Utility/Utility.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:fleetop/apicall.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:nice_button/nice_button.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import '../../CustomAutoComplete.dart';
import '../../fleetopuriconstant.dart';
import '../../flutteralert.dart';

class CreateWorkOrder extends KFDrawerContent {
  @override
  _CreateWorkOrderState createState() => _CreateWorkOrderState();
}

class _CreateWorkOrderState extends State<CreateWorkOrder> {
  double _width;
  Size size;
  String companyId;
  String userId;
  String email;
  List vehicleData = List();
  int vehicleId = 0;
  TextEditingController vehicleNumber = new TextEditingController();
  List driverData = List();
  int driverId = 0;
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
  TextEditingController jobTypeController = new TextEditingController();
  TextEditingController subjobTypeController = new TextEditingController();
  TextEditingController remarkController = new TextEditingController();
  TextEditingController woEndDate = new TextEditingController();
  TextEditingController woEndTime = new TextEditingController();
  TextEditingController woStartTime = new TextEditingController();
  TextEditingController driverNumber = new TextEditingController();
  TextEditingController dieselController = new TextEditingController();
  TextEditingController subLocationContro = new TextEditingController();
  TextEditingController serviceReminderContro = new TextEditingController();
  List<TextEditingController> rotNameControllerList =
      new List<TextEditingController>();
  List<TextEditingController> rotNumberControllerList =
      new List<TextEditingController>();
  final TimeOfDay time = null;
  DateTime mydate;
  List partLocationPermissionList = new List();
  int partLocationId = 0;
  int priorityId = 0;
  List priorotyList = new List();
  List serviceReminderData = new List();
  Map configuration = new Map();
  bool validateOdometerInWorkOrder = false;
  bool validateMinOdometerInWorkOrder = false;

  bool allowGPSIntegration = false;
  String minBackDate = "";
  bool showSubLocation = false;
  String mainLocationIds = "";
  Map gpsConfiguration = new Map();
  bool tallyConfig = false;
  int tallyCompanyId = 0;
  List tallyServiceData = List();
  List subLocationData = List();
  double vehicle_Odometer_old = 0;
  int vehicle_ExpectedOdameter = 0;
  List jobTypeList = new List();
  List jobSubTypeList = new List();
  int jobTypeId = 0;
  int subjobId = 0;
  Map jobTypeIdMap = new Map();
  Map subjobIdMap = new Map();
  Map<int, bool> rotShow = new Map<int, bool>();
  String new_workorders_location = "";
  int issueId = 0;
  int subLocationId = 0;
  int accidentId = 0;
  int serviceReminderId = 0;
  List<TextEditingController> jobTypeControllerList =
      new List<TextEditingController>();
  List<TextEditingController> subjobTypeControllerList =
      new List<TextEditingController>();
  List<TextEditingController> remarkControllerList =
      new List<TextEditingController>();
  bool startTimeForGroup = false;

  List<String> newTFList = new List<String>();
  int countTextField = 1;
  var jsonObject = {};
  bool showDriverNumberCol = false;
  bool showStartAndDueTimeField = false;
  bool AddNewWorkLocation = false;
  bool showOutWorkStationCol = false;
  bool showRouteCol = false;
  bool showPONoFeild = false;
  bool showDieselCol = false;
  bool TallyCompanyMasterInWO = false;
  bool showServRemindWhileCreatingWO = false;
  bool showJobTypeRemarkCol = false;
  bool autoCapitalAllTestFeilds = false;
  bool validateSubLocation = false;
  bool subLocation = false;
  var mainPartLocationType = 1;
  var subPartLocationType = 2;
  String vehicleodometerRangeOld = "";
  List serviceRemiderList = new List();
  List serviceRemiderListNew = new List();
  int serviceId = 0;
  Map serviceReminderMap = new Map();
  Map<String, bool> jobTypeEditableMap = new Map();
  Map<String, bool> subjobTypeEditableMap = new Map();
  Map suggestionTemp = new Map();
  @override
  void initState() {
    super.initState();
    setIntialDate();
    getSessionData();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    makedropdown();
    getWorkOrderInitialData();
    addTasks();
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

  getWorkOrderInitialData() async {
    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'isFromMob': 'true'
    };
    var response = await ApiCall.getDataFromApi(
        URI.GET_WORK_ORDER_INITIAL_DETAILS, data, URI.LIVE_URI, context);
    if (response != null) {
      setState(() {
        tallyConfig = response["tallyConfig"];
        minBackDate = response["minBackDate"];
        partLocationPermissionList = response["partLocationPermission"];
        configuration = response["configuration"];
        validateOdometerInWorkOrder = response["validateOdometerInWorkOrder"];
        gpsConfiguration = response["gpsConfiguration"];
        showSubLocation = response["showSubLocation"];
        mainLocationIds = response["mainLocationIds"];
        validateMinOdometerInWorkOrder =
            response["validateMinOdometerInWorkOrder"];
      });
      setConfigData();
    }
  }

  setConfigData() {
    if (gpsConfiguration != null &&
        gpsConfiguration.containsKey("allowGPSIntegration")) {
      allowGPSIntegration = gpsConfiguration["allowGPSIntegration"];
    }
    if (configuration != null && configuration.isNotEmpty) {
      startTimeForGroup = configuration["showStartAndDueTimeField"];
      showServRemindWhileCreatingWO =
          configuration["showServRemindWhileCreatingWO"];
      showDriverNumberCol = configuration["showDriverNumberCol"];
      showStartAndDueTimeField = configuration["showStartAndDueTimeField"];
      AddNewWorkLocation = configuration["AddNewWorkLocation"];
      showOutWorkStationCol = configuration["showOutWorkStationCol"];
      showRouteCol = configuration["showRouteCol"];
      showPONoFeild = configuration["showPONoFeild"];
      showDieselCol = configuration["showDieselCol"];
      TallyCompanyMasterInWO = configuration["TallyCompanyMasterInWO"];
      showJobTypeRemarkCol = configuration["showJobTypeRemarkCol"];
      autoCapitalAllTestFeilds = configuration["autoCapitalAllTestFeilds"];
    }
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        floatingActionButton: Visibility(
          //visible: showAddbtn,
          child: FloatingActionButton(
            backgroundColor: Colors.blue,
            child: Icon(
              Icons.add,
              color: Colors.white,
            ),
            onPressed: addTasks,
          ),
        ),
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
                          'Create Work Order',
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
        child: createWorkOrderBody(),
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
              lastDate: DateTime.now());
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

  setIntialDate() {
    DateTime now = new DateTime.now();
    String hourAndMinute = FileUtility.getStandardTime(now.hour, now.minute);
    String dt = FileUtility.getStandardDate(now.day).toString() +
        "-" +
        FileUtility.getStandardMonth(now.month).toString() +
        "-" +
        now.year.toString();
    fromDateController.text = dt.toString();
    woStartTime.text = hourAndMinute;
    //  toDateController.text = dt.toString();
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
              lastDate: DateTime(DateTime.now().year + 1));
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

  Widget createWorkOrderBody() {
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
            renderText("Vehicle "),
            vehicleNumberAC(),
            driverNameAC(),
            //  renderText("Service Reminder"),
            serviceReminderAC(),
            // serviceReminderDropDown(),
            Visibility(
              visible: showDriverNumberCol,
              child: Column(
                children: [
                  renderText("Driver No"),
                  driverNoTF(),
                ],
              ),
            ),
            renderText("Assigned To"),
            assigneeNameAC(),
            fromDateTF(),
            renderFromTime(),
            toDateTF(),
            renderToTime(),
            gpsLocationTF(),
            renderText("Work location"),
            partLocationPermissionDropdwon(),
            Visibility(
              visible: subLocation,
              child: Column(
                children: [
                  renderText("Sub Location"),
                  renderSubLocation(),
                ],
              ),
            ),
            renderText("Odometer"),
            odometerRangeTF(),
            gpsodometerRangeTF(),
            priorotyDropdwon(),
            Visibility(
                visible: showDieselCol != null ? showDieselCol : false,
                child: dieselLiterTF()),
            Visibility(
                visible: TallyCompanyMasterInWO != null
                    ? TallyCompanyMasterInWO
                    : false,
                child: renderText("Tally Company Name")),
            Visibility(
                visible: TallyCompanyMasterInWO != null
                    ? TallyCompanyMasterInWO
                    : false,
                child: tallyCompanyAC()),
            renderText("Add Tasks"),
            Container(
              child: Column(
                children: renderDynamicTasks(),
              ),
            ),
            iitialNotesTF(),
            submeetBtn()
          ]),
    );
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

  Widget submeetBtn() {
    return Center(
      child: NiceButton(
        radius: 40,
        text: "Save",
        icon: Icons.save,
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

  Widget renderText(String text) {
    return Center(
      child: Text(
        text + " * ",
        style: TextStyle(
            color: Colors.red,
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
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

  Widget dieselLiterTF() {
    return Container(
      margin: EdgeInsets.only(top: 10, left: 10, bottom: 10, right: 10),
      child: Container(
        child: TextField(
          inputFormatters: [
            WhitelistingTextInputFormatter(RegExp("[0-9]")),
            BlacklistingTextInputFormatter(RegExp("[.]")),
          ],
          onChanged: (String text) {},
          keyboardType: TextInputType.numberWithOptions(decimal: false),
          maxLines: 1,
          controller: dieselController,
          style: TextStyle(color: Colors.black),
          decoration: InputDecoration(
            prefixIcon: Icon(
              Icons.comment,
              color: Colors.black,
            ),
            labelText: 'Fuel',
            hintText: 'Fuel',
            hintStyle: TextStyle(color: Colors.black),
            border:
                OutlineInputBorder(borderRadius: BorderRadius.circular(5.0)),
          ),
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

  Widget partLocationPermissionDropdwon() {
    return partLocationPermissionList == null ||
            partLocationPermissionList.isEmpty
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
                            hint: Text("Please Select Work location "),
                            value: partLocationId != 0
                                ? partLocationId.toString()
                                : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                partLocationId = int.parse(newValue);
                              });
                              showSubLocationDropDown();
                            },
                            items: partLocationPermissionList.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['partlocation_name'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['partlocation_id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
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

  Widget vehicleNumberAC() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 2),
      child: CustomAutoComplete(
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
    getServiceReminderList();
  }

  Widget serviceReminderDropDown() {
    return serviceRemiderList == null || serviceRemiderList.isEmpty
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
                            hint: Text("Please Select Service Reminder"),
                            value: serviceId != 0 ? serviceId.toString() : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                serviceId = int.parse(newValue);
                              });
                            },
                            items: serviceRemiderList.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(getExactLable(item),
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['service_id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  String getExactLable(Map data) {
    try {
      String res = data["service_type"] +
          "-" +
          data["service_subtype"] +
          "-" +
          Utility.timestampToStringWithDateFromat(data["time_servicedate"]);
      return res;
    } catch (e) {
      return "";
    }
  }

  void manipulateList() {
    List tempList = [];
    if (serviceRemiderList != null && serviceRemiderList.isNotEmpty) {
      for (var data in serviceRemiderList) {
        var obj = {
          'label': getExactLable(data),
          'id': data["service_id"].toString()
        };
        serviceReminderMap[data["service_id"].toString()] = data;
        tempList.add(obj);
      }
      setState(() {
        serviceRemiderList = tempList;
      });
    }
  }

  getServiceReminderList() async {
    var data = {
      'isFromMob': 'true',
      'companyId': companyId,
      'vehicleID': vehicleId.toString(),
      'userId': userId
    };
    var response = await ApiCall.getDataWithoutLoader(
        URI.GET_SERVICE_REMIDENDER, data, URI.LIVE_URI);
    if (response != null) {
      if (response["servReminderList"] != null) {
        setState(() {
          serviceRemiderList = response["servReminderList"];
          serviceRemiderListNew = response["servReminderList"];
        });
        manipulateList();
        print("serviceRemiderList =$serviceRemiderList");
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

  removeData(int index) {
    if (index > 0) {
      jobTypeControllerList.removeAt(index);
      subjobTypeControllerList.removeAt(index);
      remarkControllerList.removeAt(index);
      rotNameControllerList.remove(index);
      rotNumberControllerList.remove(index);
      newTFList.removeAt(index);
      countTextField--;
      setState(() {});
    }
  }

  showRotHandle(int index) {
    subjobIdMap[index.toString()] = 0;
    subjobTypeControllerList[index].text = "";
    setState(() {
      rotShow[index] = true;
    });
  }

  hideRotHandle(int index) {
    rotNameControllerList[index].text = "";
    rotNumberControllerList[index].text = "";
    setState(() {
      rotShow[index] = false;
    });
  }

  Widget rotWidget(int index) {
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
        controller: rotNameControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.facebookMessenger,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'ROT :',
          hintText: 'ROT',
        ),
        autofocus: false,
      ),
    );
  }

  Widget rotNumberWidget(int index) {
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
        controller: rotNumberControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.facebookMessenger,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'ROT No :',
          hintText: 'ROT No',
        ),
        autofocus: false,
      ),
    );
  }

  Widget renderTask(int index) {
    return Card(
      color: Colors.white,
      // margin: EdgeInsets.only(left: 7, right: 7),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(25.0),
      ),
      borderOnForeground: true,
      elevation: 1.0,
      child: Column(
        children: [
          jobTypeWidget(index),
          Visibility(
              visible: rotShow[index] == null || !rotShow[index],
              child: subjobTypeWidget(index)),
          remarkTF(index),
          Visibility(
            visible: rotShow[index] != null && rotShow[index],
            child: Container(
              child: Column(
                children: [
                  rotWidget(index),
                  rotNumberWidget(index),
                ],
              ),
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Container(
                child: Visibility(
                  visible: index > 0,
                  child: RaisedButton(
                    elevation: 5.0,
                    onPressed: () {
                      removeData(index);
                    },
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    ),
                    color: Colors.red,
                    child: IconButton(
                      onPressed: () {
                        removeData(index);
                      },
                      icon: Icon(
                        Icons.delete,
                        size: 30,
                        color: Colors.white,
                      ),
                    ),
                  ),
                ),
              ),
              Container(
                margin: index == 0
                    ? EdgeInsets.only(right: _width / 2 - 160)
                    : EdgeInsets.only(right: 0.0),
                child: RaisedButton(
                  elevation: 5.0,
                  onPressed: () {
                    showRotHandle(index);
                  },
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  ),
                  color: Colors.green,
                  child: IconButton(
                    onPressed: () {
                      if (rotShow[index] != null && rotShow[index]) {
                        hideRotHandle(index);
                      } else {
                        showRotHandle(index);
                      }
                    },
                    icon: Icon(
                      rotShow[index] != null && rotShow[index]
                          ? Icons.delete
                          : Icons.add,
                      size: 30,
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ],
          ),
          SizedBox(
            height: 20,
          )
        ],
      ),
    );
  }

  Widget driverNoTF() {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        maxLength: 10,
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
          counterText: '',
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

  Widget remarkTF(int index) {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.text,
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: remarkControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.facebookMessenger,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Remark :',
          hintText: 'Remark',
        ),
        autofocus: false,
      ),
    );
  }

  bool checkAllowToEdit(Map coll, int index) {
    try {
      if (coll != null && coll.isNotEmpty) {
        return coll[index.toString()];
      } else {
        return true;
      }
    } catch (e) {
      return true;
    }
  }

  Widget jobTypeWidget(int index) {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: CustomAutoComplete(
          enabled: checkAllowToEdit(jobTypeEditableMap, index),
          resetData: () {
            setState(() {
              jobTypeControllerList[index].text = '';
              jobTypeIdMap[index.toString()] = 0;
            });
          },
          suggestionList: jobTypeList,
          controller: jobTypeControllerList[index],
          hintLabel: 'Job Type',
          label: 'Job Type',
          dataKeyName: 'job_Type',
          iconData: Icons.directions,
          onItemSelected: (suggestion) {
            setState(() {
              jobTypeControllerList[index].text = suggestion['job_Type'];
              jobTypeIdMap[index.toString()] = suggestion['job_id'];
            });
          },
          onChanged: (pattern) {
            //method for getting data from server
            getJobTypelist(pattern);
          }),
    );
  }

  getJobTypeDetails(String subjobid, int index) async {
    var data = {'subJobId': subjobid, 'companyId': companyId, 'userId': userId};
    var response = await ApiCall.getDataWithoutLoader(
        URI.GET_JOB_DET_FROM_SUB_JOB_ID, data, URI.LIVE_URI);
    if (response != null && response["jobTypeDetails"] != null) {
      setState(() {
        jobTypeIdMap[index.toString()] =
            response["jobTypeDetails"]['job_TypeId'];
        jobTypeControllerList[index].text =
            response["jobTypeDetails"]["job_Type"];
      });
    }
  }

  Widget subjobTypeWidget(int index) {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: CustomAutoComplete(
          enabled: checkAllowToEdit(subjobTypeEditableMap, index),
          suggestionList: jobSubTypeList,
          controller: subjobTypeControllerList[index],
          hintLabel: 'Job Sub Type ',
          label: 'Job Sub Type ',
          dataKeyName: 'job_ROT',
          iconData: Icons.directions,
          resetData: () {
            //jobTypeIdMap[index.toString()] = 0;
            subjobIdMap[index.toString()] = 0;
            subjobTypeControllerList[index].text = "";
            // jobTypeControllerList[index].text = "";
          },
          onItemSelected: (suggestion) {
            setState(() {
              subjobIdMap[index.toString()] = suggestion['job_Subid'];
              subjobTypeControllerList[index].text = suggestion['job_ROT'];
            });
            getJobTypeDetails(suggestion['job_Subid'].toString(), index);
          },
          onChanged: (pattern) {
            //method for getting data from server
            getsubJobTypelist(pattern);
          }),
    );
  }

  getJobTypelist(String str) async {
    setState(() {
      jobTypeList = [];
    });
    if (str != '' && str.length > 0) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_JOB_TYPE_WO, data, URI.LIVE_URI);
      if (response != null && response["jobTypeList"] != null) {
        setState(() {
          jobTypeList = response["jobTypeList"];
        });
      } else {
        setState(() {
          jobTypeList = [];
        });
      }
    }
  }

  getsubJobTypelist(String str) async {
    setState(() {
      jobSubTypeList = [];
    });
    if (str != '' && str.length > 0) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_SUB_JOB_TYPE_WO, data, URI.LIVE_URI);
      if (response != null && response["subjobTypeList"] != null) {
        setState(() {
          jobSubTypeList = response["subjobTypeList"];
        });
      } else {
        setState(() {
          jobSubTypeList = [];
        });
      }
    }
  }

  void showMessage(String tit, String descn) {
    FlutterAlert.onInfoAlert(context, descn, tit);
  }

  showSubLocationDropDown() {
    List mainlocationIdsList = mainLocationIds.split(",");
    var showSubLocationForMainLocation = false;
    if (mainlocationIdsList != null && mainlocationIdsList.isNotEmpty) {
      for (var i = 0; i < mainlocationIdsList.length; i++) {
        if (partLocationId == int.parse(mainlocationIdsList[i].toString())) {
          showSubLocationForMainLocation = true;
        }
      }
    }

    if (showSubLocationForMainLocation == true) {
      setState(() {
        validateSubLocation = true;
        subLocation = true;
      });
    } else {
      setState(() {
        validateSubLocation = false;
        subLocation = false;
        subLocationId = 0;
      });
    }
  }

  getSublocation(String str) async {
    setState(() {
      subLocationData.clear();
    });
    var data = {
      'companyId': companyId,
      'term': str,
      'userId': userId,
      'isFromMob': 'true',
      'mainLocationId': partLocationId.toString(),
      'locationType': subPartLocationType.toString()
    };
    var response = await ApiCall.getDataWithoutLoader(
        URI.GET_PART_LOCATION_BY_MAIN_LOCATION_ID, data, URI.LIVE_URI);
    if (response != null && response["partLocationList"] != null) {
      setState(() {
        subLocationData = response["partLocationList"];
      });
    }
  }

  getServiceName(String str) {}
  Widget serviceReminderAC() {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: Container(
        child: CustomAutoComplete(
            suggestionList: serviceRemiderList,
            controller: serviceReminderContro,
            hintLabel: 'Service Reminder ',
            label: 'Service Reminder',
            dataKeyName: 'label',
            iconData: Icons.directions,
            resetData: () {
              setState(() {
                serviceReminderContro.text = '';
                serviceReminderId = 0;
              });
              onItemSelectedOfServiceRemnider(serviceReminderId);
            },
            onItemSelected: (suggestion) {
              setState(() {
                suggestionTemp = suggestion;
                serviceReminderContro.text = suggestion['label'];
                serviceReminderId = int.parse(suggestion['id']);
              });
              onItemSelectedOfServiceRemnider(serviceReminderId);
            },
            onChanged: (pattern) {}),
      ),
    );
  }

  onItemSelectedOfServiceRemnider(int id) {
    print("id =$id");
    if (id != 0) {
      Map addedValue = serviceReminderMap[id.toString()];
      print("addedValue =$addedValue");
      if (addedValue != null && addedValue.isNotEmpty) {
        var servDate = addedValue["date"];
        setState(() {
          woEndDate.text = servDate;
        });
        if (serviceRemiderListNew != null && serviceRemiderListNew.isNotEmpty) {
          var a = serviceRemiderListNew;
          for (var i = 0; i < a.length; i++) {
            if (a[i]["service_id"] == serviceReminderId) {
              jobTypeControllerList[i].text = a[i]["service_type"];
              subjobTypeControllerList[i].text = a[i]["service_subtype"];
              if (serviceRemiderListNew.length == 1 || serviceReminderId == 0) {
                jobTypeIdMap[i.toString()] = a[i]["serviceTypeId"];
                subjobIdMap[i.toString()] = a[i]["serviceSubTypeId"];
                subjobTypeEditableMap[i.toString()] = false;
                jobTypeEditableMap[i.toString()] = false;
              }
            }
            print("jobTypeIdMap =$jobTypeIdMap");
            print("subjobIdMap =$subjobIdMap");
          }
        }
      }
    } else {
      print("******");
      if (serviceRemiderListNew != null && serviceRemiderListNew.isNotEmpty) {
        var a = serviceRemiderListNew;
        var serviceId = suggestionTemp["id"];
        print("serviceId =$serviceId");
        for (var i = 0; i < a.length; i++) {
          if (int.parse(a[i]["service_id"].toString()) ==
              int.parse(serviceId.toString())) {
            print("*****vpvp");
            jobTypeControllerList[i].text = '';
            subjobTypeControllerList[i].text = '';
            jobTypeIdMap[i.toString()] = 0;
            subjobIdMap[i.toString()] = 0;
            subjobTypeEditableMap[i.toString()] = true;
            jobTypeEditableMap[i.toString()] = true;
          }
          print("jobTypeIdMap =$jobTypeIdMap");
          print("subjobIdMap =$subjobIdMap");
        }
      }
    }
  }

  Widget renderSubLocation() {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: Container(
        child: CustomAutoComplete(
            suggestionList: subLocationData,
            controller: subLocationContro,
            hintLabel: 'Sub location ',
            label: 'Sub location',
            dataKeyName: 'partlocation_name',
            iconData: Icons.directions,
            resetData: () {
              setState(() {
                subLocationContro.text = '';
                subLocationId = 0;
              });
            },
            onItemSelected: (suggestion) {
              setState(() {
                subLocationContro.text = suggestion['partlocation_name'];
                subLocationId = suggestion['partlocation_id'];
              });
            },
            onChanged: (pattern) {
              getSublocation(pattern);
            }),
      ),
    );
  }

  bool allvalidations() {
    if (vehicleId <= 0) {
      showMessage('Info', 'Please Select Vehicle !');
      return false;
    }
    // if (driverId <= 0) {
    //   showMessage('Info', 'Please Select Driver  !');
    //   return false;
    // }
    if (assigneeName == null ||
        assigneeName.text == "" ||
        assigneeName.text.length == 0) {
      showMessage('Info', 'Please Select Assigned To !');
      return false;
    }
    if (assigneeId <= 0) {
      showMessage('Info', 'Please Select Assigned To !');
      return false;
    }
    if (fromDateController.text == "" || fromDateController.text.length == 0) {
      showMessage('Info', 'Please Select Start Date');
      return false;
    }
    if (startTimeForGroup != null && startTimeForGroup) {
      if (woStartTime.text == "" || woStartTime.text.length == 0) {
        showMessage('Info', 'Please Select Start Time !');
        return false;
      }
    }
    if (toDateController.text == "" || toDateController.text.length == 0) {
      showMessage('Info', 'Please Select Due Date');
      return false;
    }
    bool dateCheck = checkStartAndEndDateOFWO();
    bool equalCheck = checkBothDateAreEqual();
    if (dateCheck != null && !dateCheck && !equalCheck) {
      setState(() {
        toDateController.text = '';
      });
      showMessage('Info', 'Due Date Can Not Be Greater Than Start Date !');
      return false;
    }
    if (startTimeForGroup != null && startTimeForGroup) {
      if (woEndTime.text == "" || woEndTime.text.length == 0) {
        showMessage('Info', 'Please Select Due Time !');
        return false;
      }
    }
    if (partLocationId <= 0 && new_workorders_location == "") {
      showMessage('Info', 'Please Select Work Location !');
      return false;
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
      if (validateSubLocation && subLocationId == 0) {
        showMessage('info', 'Please Select Sub Location');
        return false;
      }
    }
    if (vehicleodometerRange.text == "" ||
        vehicleodometerRange.text.length == 0 ||
        int.parse(vehicleodometerRange.text) == 0) {
      showMessage('Info', 'Please Enter Odometer');
      return false;
    }
    if (tallyConfig) {
      if (tallyCompanyId <= 0 || tallyCompany.text == "") {
        showMessage('Info', 'Please Select Tally Company Master');
        return false;
      }
    } else {
      tallyCompanyId = 0;
    }
    var ab = vehicleodometerRange.text != null &&
            vehicleodometerRange.text.length > 0
        ? int.parse(vehicleodometerRange.text)
        : 0;

    var expectedOdo = vehicle_ExpectedOdameter + ab;
    var odometer =
        vehicleodometerRangeOld != "" ? int.parse(vehicleodometerRangeOld) : 0;
    var tripOpeningKM = checkAndSetNum(vehicleodometerRange);
    var selectedData = fromDateController.text.split("-");
    var now = new DateTime.now();
    var berlinWallFellDate = new DateTime.utc(int.parse(selectedData[2]),
        int.parse(selectedData[1]), int.parse(selectedData[0]));
    if (validateMinOdometerInWorkOrder != null &&
        validateMinOdometerInWorkOrder) {
      if (tripOpeningKM == 0) {
        showMessage('Info', 'Please Enter Odometer');
        return false;
      }
      if (fromDateController.text != "") {
        if (berlinWallFellDate.compareTo(now) <= 0) {
          if (tripOpeningKM > 0 && tripOpeningKM < odometer) {
            showMessage('Info',
                'Trip Odometer Should Not Be Less Than ' + odometer.toString());
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
      showMessage('Info',
          'Trip Odometer cannot be greater than ' + expectedOdo.toString());
      return false;
    }
    var count = countTextField - 1;
    if (jobTypeIdMap != null && jobTypeIdMap.isNotEmpty) {
      for (int i = 0; i <= count - 1; i++) {
        int id = jobTypeIdMap[i.toString()] != null
            ? int.parse(jobTypeIdMap[i.toString()].toString())
            : 0;
        if (id == 0) {
          showMessage('Info', 'Please select JobType !');
          return false;
        }
      }
    } else {
      showMessage('Info', 'Please select JobType !');
      return false;
    }
    if (remarkControllerList != null && remarkControllerList.isNotEmpty) {
      for (TextEditingController controller in remarkControllerList) {
        if (controller.text == '' || controller.text.length == 0) {
          showMessage('Info', 'Please Enter Remark!');
          return false;
        }
      }
    } else {
      showMessage('Info', 'Please Enter Remark!');
      return false;
    }
    if (count == 1) {
      var to =
          subjobIdMap["0"] != null ? int.parse(subjobIdMap["0"].toString()) : 0;
      var subReType = rotNameControllerList[0].text;
      if ((to == 0) && (subReType == null || subReType.trim() == '')) {
        showMessage('Info', 'Please enter Job Sub Type !');
        return false;
      }
    } else {
      for (var i = 0; i <= count - 1; i++) {
        var to1 = subjobIdMap[i.toString()] != null
            ? int.parse(subjobIdMap[i.toString()].toString())
            : 0;
        var subReType1 = checkAndSet(rotNameControllerList[i]);
        if (to1 == 0 &&
            (subReType1.length == 0 ||
                subReType1 == null ||
                subReType1.trim() == '')) {
          showMessage('Info', 'Please enter Job Sub Type !');
          return false;
        }
      }
    }
    return true;
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

  bool validateDate() {
    if (startTimeForGroup) {
      if (woEndTime.text == '' || woEndTime.text.length == 0) {
        setState(() {
          woEndTime.text = woStartTime.text;
        });
      }
      var startDateTime = fromDateController.text + ' ' + woStartTime.text;
      var endDateTime = toDateController.text + ' ' + woEndTime.text;
      setState(() {
        fromDateController.text = startDateTime.toString();
        toDateController.text = endDateTime.toString();
      });
    } else {
      var startDateTime = fromDateController.text + ' ' + ' 00:00:00';
      var endDateTime = toDateController.text + ' ' + ' 00:00:00';
      // setState(() {
      //   fromDateController.text = startDateTime.toString();
      //   toDateController.text = endDateTime.toString();
      // });
    }
    return true;
    // var isafter = moment(startDateTime).isAfter(endDateTime);
    // console.log("isafter =", isafter);
    // console.log("startDateTime =", startDateTime);
    // console.log("endDateTime =", endDateTime);
    // console.log("#startTimeForGroup", $("#startTimeForGroup").val());
    // if (isafter) {

    //   $('#woEndTime').val('');
    //   $('#woEndDate').val('');
    //   showMessage('info', 'Due Date Can Not Be Greater Than Start Date');
    //   return false;
    // }
  }

  int checkAndSetNum(TextEditingController controller) {
    if (controller == null ||
        controller.text == '' ||
        controller.text.length == 0) {
      return 0;
    } else {
      return int.parse(controller.text);
    }
  }

  bool validateOdometer() {
    var ab = vehicleodometerRange.text != null &&
            vehicleodometerRange.text.length > 0
        ? int.parse(vehicleodometerRange.text)
        : 0;

    var expectedOdo = vehicle_ExpectedOdameter + ab;
    var Odometer = checkAndSetNum(vehicleodometerRange);
    var tripOpeningKM = checkAndSetNum(vehicleodometerRange);
    var selectedData = fromDateController.text.split("-");
    var now = new DateTime.now();
    var berlinWallFellDate = new DateTime.utc(int.parse(selectedData[2]),
        int.parse(selectedData[1]), int.parse(selectedData[0]));

    if (validateMinOdometerInWorkOrder == true) {
      if (fromDateController.text != "") {
        if (berlinWallFellDate.compareTo(now) > 0) {
          if (tripOpeningKM > 0 && tripOpeningKM < Odometer) {
            showMessage('Info',
                'Trip Odometer Should Not Be Less Than ' + Odometer.toString());
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
      showMessage('Info',
          'Trip Odometer cannot be greater than ' + expectedOdo.toString());
      return false;
    }

    return true;
  }

  void validateWorkOrderData() {
    bool resFinal = allvalidations();
    if (resFinal != null && resFinal) {
      FlutterAlert.confirmationAlertWithMethod(
          context,
          'Are you sure you want to Create Workorder  ?',
          'Create Workorder',
          saveWorkOrderDetails);
    } else {
      // showMessage('Info', 'Please Enter All Required Data');
    }
  }

  void addTasks() {
    setState(() {
      newTFList.add("");
      jobTypeControllerList.add(new TextEditingController());
      subjobTypeControllerList.add(new TextEditingController());
      remarkControllerList.add(new TextEditingController());
      rotNameControllerList.add(new TextEditingController());
      rotNumberControllerList.add(new TextEditingController());
      countTextField++;
      rotShow[countTextField] = false;
    });
  }

  List<Widget> renderDynamicTasks() {
    List<Widget> widgetList = new List();
    if (newTFList.isNotEmpty || newTFList.length != 0)
      for (int i = 0; i < newTFList.length; i++) {
        widgetList.add(renderTask(i));
      }
    if (widgetList != null && widgetList.isNotEmpty) {
      return widgetList;
    } else {
      widgetList.add(new Container());
      return widgetList;
    }
  }

  String checkAndSet(TextEditingController controller) {
    if (controller == null ||
        controller.text == '' ||
        controller.text.length == 0) {
      return "";
    } else {
      return controller.text;
    }
  }

  makeAlist() {
    var array = new List();
    for (int i = 0; i < countTextField - 1; i++) {
      var obj = {
        'jobType': jobTypeIdMap[i.toString()].toString(),
        'jobSubType': subjobIdMap[i.toString()].toString(),
        'remark': checkAndSet(remarkControllerList[i]),
        'wo_ROT': checkAndSet(rotNameControllerList[i]),
        'wo_ROT_number': checkAndSet(rotNumberControllerList[i])
      };
      array.add(obj);
    }
    if (array == null || array.isEmpty) {
      FlutterAlert.onInfoAlert(context, 'Please Add Task', 'Info');
    }
    jsonObject["workOrderDetails"] = json.encode(array);
  }

  saveWorkOrderDetails() async {
    jsonObject = {
      'vid': vehicleId.toString(),
      'companyId': companyId.toString(),
      'userId': userId.toString(),
      'isFromMob': 'true',
      'assignedTo': assigneeId.toString(),
      'driverId': driverId.toString(),
      'gpsOdometer': '',
      'gpsWorkLocation': gpsLocation.text,
      'initial_note': initialNotes.text,
      'locationId': partLocationId.toString(),
      'priority': priorityId.toString(),
      'tallyCompanyId': tallyCompanyId.toString(),
      'location': partLocationId.toString(),
    };
    jsonObject["validateDoublePost"] = 'true';
    jsonObject["woStartDate"] = fromDateController.text;
    jsonObject["woStartTime"] = woStartTime.text;
    jsonObject["woEndDate"] = toDateController.text;
    jsonObject["woEndTime"] = woEndTime.text;
    jsonObject["vehicle_Odometer_Old"] =
        vehicle_Odometer_old.toInt().toString();
    jsonObject["vehicle_Odometer"] = vehicleodometerRange.text;
    jsonObject["gpsOdometer"] = gpsodometerRange.text;
    jsonObject["issueId"] = issueId.toString();
    jsonObject["subLocationId"] = subLocationId.toString();
    jsonObject["accidentId"] = accidentId.toString();
    jsonObject["serviceReminderId"] = serviceReminderId.toString();
    jsonObject["driverNumber"] = checkAndSet(driverNumber);
    jsonObject["workorders_diesel"] = checkAndSet(dieselController);
    makeAlist();
    var response = await ApiCall.getDataWithoutLoader(
        URI.SAVE_WORK_ORDER, jsonObject, URI.LIVE_URI);
    if (response != null) {
      if (response["workOrder"] != null) {
        if (response["workOrder"]["workorders_id"] > 0) {
          FlutterAlert.dialogueSuccess(
              context,
              'WorkOrder No : ' +
                  response["workOrder"]["workorders_Number"].toString() +
                  "   "
                      'Sucessfully Created',
              'Work Order');
        } else {
          if (response["vehicleStatusString"] != null) {
            FlutterAlert.onInfoAlert(
                context,
                'Vehicle In ' '  ' +
                    response["vehicleStatusString"] +
                    '   ' "Status",
                'Info');
          } else {
            FlutterAlert.onInfoAlert(
                context, 'Something Went Wrong Pls Contact on Support', 'Info');
          }
        }
      } else {
        if (response["vehicleStatusString"] != null) {
          FlutterAlert.onInfoAlert(
              context,
              'Vehicle In ' '  ' +
                  response["vehicleStatusString"] +
                  '   ' "Status",
              'Info');
        } else {
          FlutterAlert.onInfoAlert(
              context, 'Something Went Wrong Pls Contact on Support', 'Info');
        }
      }
    } else {
      FlutterAlert.dialogueFailure(
          context, 'Failed To Create WorkOrder', 'Work Order');
    }
  }
}
