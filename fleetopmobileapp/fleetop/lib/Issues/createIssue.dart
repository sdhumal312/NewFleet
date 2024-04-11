import 'dart:convert';
import 'dart:io';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/Issues/IssueNotifications/IssueNotifications.dart';
import 'package:fleetop/Issues/showIssue.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/PickAndDrop/showPickOrDropData.dart';
import 'package:fleetop/RenewalReminder/searchRenewalByVehicle.dart';
import 'package:fleetop/RenewalReminder/showRenewalReminder.dart';
import 'package:fleetop/Utility/Utility.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../CustomAutoComplete.dart';
import '../fleetopuriconstant.dart';

class CreateIssue extends KFDrawerContent {
  CreateIssue({Key key});

  @override
  _CreateIssueState createState() => _CreateIssueState();
}

class _CreateIssueState extends State<CreateIssue>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;
  int minOdometer = 0;
  int maxOdometer = 0;

  String vehicleId = '';
  String driverId = '';
  String branchId = '';
  String departmentId = '';
  int assignToId = 0;

  bool showVehicle = false;
  bool showDriver = false;
  bool showOdometer = false;
  bool vehicleGroup = false;
  bool showBranch = false;
  bool showDepartment = false;
  bool showCustomerName = false;

  bool validateOdometer = false;
  bool validateMinOdometer = false;

  List vehicleData = List();
  Map vehicleList = Map();
  Map driverNameList = Map();
  List driverNameData = List();
  Map branchNameList = Map();
  List branchNameData = List();
  Map departmentNameList = Map();
  List departmentNameData = List();
  Map issueLabelList = Map();
  List issueLabelData = List();
  Map assignList = Map();
  List assignData = List();

  Map configuration;
  List issueType = List();
  List mainLabelList = List();
  List vehicleGroupType = List();

  var selectedIssueType = '';
  int selectedLabelType = 0;
  var selectedGroupType = '';

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");

  TextEditingController issueSearch = new TextEditingController();
  TextEditingController vehicleName = new TextEditingController();
  TextEditingController driverName = new TextEditingController();
  TextEditingController odoMeter = new TextEditingController();
  TextEditingController branchName = new TextEditingController();
  TextEditingController departmentName = new TextEditingController();
  TextEditingController customerName = new TextEditingController();
  TextEditingController issueDate = new TextEditingController();
  TextEditingController issueTime = new TextEditingController();
  TextEditingController issueSummary = new TextEditingController();
  TextEditingController issueLabel = new TextEditingController();
  TextEditingController assignedTo = new TextEditingController();
  TextEditingController issueDescription = new TextEditingController();
  TextEditingController routeService = new TextEditingController();
  bool showCategoryOption = false;
  bool showRouteOption = false;
  bool setDefaultServerDateAndTime = false;
  List routeServiceData = List();
  List partCategoriesList = List();
  int routeServiceId = 0;
  int selectedPartCategoryId = 0;
  bool showPartCategoryVis = false;

  @override
  void initState() {
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    print("userId =$userId");
    email = prefs.getString("email");
    var data = {'companyId': companyId, 'userId': userId, 'email': email};
    var response = await ApiCall.getDataFromApi(
        URI.CREATE_ISSUE, data, URI.LIVE_URI, context);

    print(response);

    if (response != null) {
      var obj = {"labelId": "1", "labelName": "Normal"};
      mainLabelList.add(obj);

      var obj1 = {"labelId": 2, "labelName": "High"};
      mainLabelList.add(obj1);

      var obj2 = {"labelId": 3, "labelName": "Low"};
      mainLabelList.add(obj2);

      var obj3 = {"labelId": 4, "labelName": "Urgent"};

      mainLabelList.add(obj3);

      var obj4 = {"labelId": 5, "labelName": "Very Urgent"};
      mainLabelList.add(obj4);

      if (response['showBreakdownSelection']) {
        var obj5 = {"labelId": 6, "labelName": "Breakdown"};
        mainLabelList.add(obj5);
      }

      print("mainLabelList........${mainLabelList}");

      configuration = response['configuration'];
      print("configuration........${configuration}");
      showCategoryOption =
          Utility.hasKeyInConfig(configuration, "showCategoryOption");
      showRouteOption =
          Utility.hasKeyInConfig(configuration, "showRouteOption");
      setDefaultServerDateAndTime =
          Utility.hasKeyInConfig(configuration, "setDefaultServerDateAndTime");
      setState(() {
        showCustomerName = configuration['customerIssue'];
        mainLabelList = mainLabelList;
        issueType = response['IssueType'];
        vehicleGroupType = response['vehiclegroup'];
        validateOdometer = response['validateOdometerInIssues'];
        validateMinOdometer = response['validateMinOdometerInIssues'];
        partCategoriesList = response['PartCategories'];
      });
    }
    setDefaultValueAndData(response);
  }

  setDefaultValueAndData(Map response) {
    // if (showCategoryOption) {}
    // if (showRouteOption) {}
    if (setDefaultServerDateAndTime) {
      if (response != null && response.isNotEmpty) {
        setState(() {
          setState(() {
            issueDate.text = response["serverDateStr"].toString();
            issueTime.text = response["serverTimeStr"].toString();
          });
        });
      }
      setDefaultDateAndTime();
    }
  }

  Widget showPartCategory() {
    if (showCategoryOption && showPartCategoryVis) {
      return Container(
          margin: const EdgeInsets.only(top: 5.0, left: 10),
          height: 70,
          child: DropdownButtonHideUnderline(
            child: Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                    padding: EdgeInsets.all(17),
                    child: DropdownButton<String>(
                      hint: Text("Part Category"),
                      value: selectedPartCategoryId != 0
                          ? selectedPartCategoryId.toString()
                          : null,
                      isExpanded: true,
                      onChanged: (String newValue) {
                        setState(() {
                          selectedPartCategoryId = int.parse(newValue);
                        });
                      },
                      items: partCategoriesList.map((item) {
                        return new DropdownMenuItem(
                          child: new Text(item['pcName'],
                              style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 15.0,
                                  fontFamily: "WorkSansBold")),
                          value: item['pcid'].toString(),
                        );
                      }).toList(),
                    ))),
          ));
    } else {
      return Container();
    }
  }

  Widget showRoot() {
    print("showRouteOption = $showRouteOption");
    return Visibility(
        visible: showRouteOption,
        child: Container(
          child: CustomAutoComplete(
              suggestionList: routeServiceData,
              controller: routeService,
              hintLabel: 'Route',
              label: 'Route',
              dataKeyName: 'routeName',
              iconData: Icons.directions,
              onItemSelected: (suggestion) {
                setState(() {
                  routeService.text = suggestion['routeName'];
                  routeServiceId = suggestion['routeID'];
                });
              },
              onChanged: (pattern) {
                //method for getting data from server
                getRouteServiceDetails(pattern);
              }),
        ));
  }

  getRouteServiceDetails(String str) async {
    routeServiceId = 0;
    setState(() {
      routeServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TRIP_ROUTE_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["TripRouteList"] != null &&
            response["TripRouteList"].length > 0) {
          setState(() {
            routeServiceData = response["TripRouteList"];
          });
        } else {
          setState(() {
            routeServiceData = [];
          });
        }
      }
    } else {
      setState(() {
        routeServiceData = [];
      });
    }
  }

  setDefaultDateAndTime() {
    // DateTime now = new DateTime.now();
    // String act = FileUtility.getStandardDate(now.day);
    // String acmonth = FileUtility.getStandardMonth(now.month);
    // String hourAndMinute = FileUtility.getStandardTime(now.hour, now.minute);
    // String dt = act + "-" + acmonth + "-" + now.year.toString();
    // setState(() {
    //   issueDate.text = dt;
    //   issueTime.text = hourAndMinute;
    // });
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 0));
    return true;
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  Future _handleSubmitted() async {
    if (!fieldValidation()) {
      return;
    } else {
      var issueDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'issueType': selectedIssueType,
        'vid': vehicleId,
        'vGroup': selectedGroupType,
        'driverId': driverId,
        'customerName': customerName.text,
        'issuesBranch': branchId,
        'issuesDepartnment': departmentId,
        'odometer': odoMeter.text,
        'reportdDate': issueDate.text,
        'issueStartTime': issueTime.text,
        'issuesSummary': issueSummary.text,
        'issueLabel': selectedLabelType.toString(),
        'reportedById': userId,
        'subscribe': assignedTo.text,
        'description': issueDescription.text,
        'partCategory': selectedPartCategoryId.toString(),
        'FuelRouteList': routeServiceId.toString(),
        'subscriberIds': assignToId.toString()
      };

      print("routeServiceId....${routeServiceId}");

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_ISSUES_DETAILS, issueDetails, URI.LIVE_URI, context);

      print("output.....${data}");

      if (data != null) {
        if (data['sequenceNotFound'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else if (data['save'] == true) {
          refreshData();
          print("Id......${data['issueId']}");
          redirectToDisplay(data['issueId']);
        } else {
          FlutterAlert.onInfoAlert(context,
              "Issue Not Created, Please contact on Support !", "Info");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool fieldValidation() {
    print(odoMeter.text);
    print(maxOdometer);
    int validateIssueTypeID =
        selectedIssueType != '' ? int.parse(selectedIssueType) : 0;
    if (validateIssueTypeID == 0) {
      FlutterAlert.onErrorAlert(context, "Please Select Issue Type !", "Error");
      return false;
    }
    if (validateIssueTypeID == 1) {
      if (vehicleId == '' || vehicleId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Valid Vehicle !", "Error");
        return false;
      }

      if (validateMinOdometer) {
        if (int.parse(odoMeter.text) < minOdometer) {
          FlutterAlert.onErrorAlert(
              context,
              "Odometer should be greater than Current Vehicle Odometer " +
                  minOdometer.toString(),
              "Error");
          return false;
        }

        if (int.parse(odoMeter.text) > int.parse(maxOdometer.toString())) {
          FlutterAlert.onErrorAlert(
              context,
              "Odometer should be less than Maximum Vehicle Odometer " +
                  maxOdometer.toString(),
              "Error");
          return false;
        }
      }
    }

    if (validateIssueTypeID == 2) {
      if (driverId == '' || driverId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Valid Driver !", "Error");
        return false;
      }
    }

    if (validateIssueTypeID == 3) {
      if (selectedGroupType == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Vehicle Group !", "Error");
        return false;
      }
      if (branchId == '' || branchId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Valid Branch !", "Error");
        return false;
      }
      if (departmentId == '' || departmentId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Valid Branch !", "Error");
        return false;
      }
    }

    if (validateIssueTypeID == 5) {
      if (branchId == '' || branchId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Valid Branch !", "Error");
        return false;
      }
      if (departmentId == '' || departmentId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Valid Branch !", "Error");
        return false;
      }
    }

    if (issueDate.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Issue Reported Date !", "Error");
      return false;
    }

    if (issueTime.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Issue Reported Time !", "Error");
      return false;
    }

    if (issueSummary.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Issue Summary !", "Error");
      return false;
    }

    if (assignToId == 0) {
      FlutterAlert.onErrorAlert(context, "Please Select Assign To !", "Error");
      return false;
    }
    if (showCategoryOption && showPartCategoryVis) {
      if (selectedPartCategoryId <= 0) {
        FlutterAlert.onErrorAlert(context, "Please Select Category ", "Error");
        return false;
      }
    }
    return true;
  }

  refreshData() {
    selectedIssueType = '';
    vehicleId = '';
    vehicleName.text = '';
    driverId = '';
    driverName.text = '';
    odoMeter.text = '';
    selectedGroupType = '';
    branchId = '';
    branchName.text = '';
    departmentId = '';
    departmentName.text = '';
    customerName.text = '';
    issueDate.text = '';
    issueTime.text = '';
    issueSummary.text = '';
    selectedLabelType = 0;
    assignToId = 0;
    assignedTo.text = '';
    issueDescription.text = '';
  }

  Future<bool> redirectToDisplay(issueId) async => Alert(
        context: context,
        type: AlertType.success,
        title: "Success",
        desc: "Data Successfully Saved !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => issueDisplayData(issueId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  issueDisplayData(String issueId, context) async {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => ShowIssue(issueId: issueId)),
    );
  }

  searchIssueByNumber(String issueNumber) async {
    if (issueNumber != '') {
      var data = {
        'companyId': companyId,
        'issueNumber': issueNumber,
        'userId': userId
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.SEARCH_ISSUES_DETAILS, data, URI.LIVE_URI);
      print("response........${response}");
      if (response != null) {
        if (response['issueFound'] == true) {
          print("issueId........${response['issueId']}");
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => ShowIssue(issueId: response['issueId'])),
          );
        } else {
          FlutterAlert.onErrorAlert(
              context, "Please Enter Valid Issue Number !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid Issue Number !", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Issue Number !", "Error");
    }
  }

  getIssueNotificationDetails() async {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => IssueNotifications()),
    );
  }

  Widget appBarTitle = new Text("Create Issue");
  Icon actionIcon = new Icon(Icons.search);

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.pink,
          centerTitle: true,
          title: appBarTitle,
          leading: new IconButton(
            icon: new Icon(Icons.menu, color: Colors.white),
            onPressed: widget.onMenuPressed,
          ),
          actions: <Widget>[
            new IconButton(
              icon: actionIcon,
              onPressed: () {
                setState(() {
                  if (this.actionIcon.icon == Icons.search) {
                    this.actionIcon = new Icon(Icons.close);
                    this.appBarTitle = new TextField(
                      controller: issueSearch,
                      style: new TextStyle(
                        color: Colors.white,
                      ),
                      //focusNode: myFocusNode,
                      keyboardType: TextInputType.number,
                      decoration: new InputDecoration(
                        labelText: 'I - Number',
                        labelStyle: TextStyle(color: Colors.white),
                        icon: new RaisedButton(
                          padding: const EdgeInsets.all(2.0),
                          textColor: Colors.white,
                          color: Colors.amber,
                          onPressed: () {
                            searchIssueByNumber(issueSearch.text);
                          },
                          child: Padding(
                            padding: const EdgeInsets.symmetric(
                                vertical: 0.5, horizontal: 0.5),
                            child: Text(
                              "Search",
                              style: TextStyle(
                                  fontSize: 15,
                                  color: Colors.white,
                                  fontWeight: FontWeight.w500),
                            ),
                          ),
                        ),
                      ),
                    );
                  } else {
                    this.actionIcon =
                        new Icon(Icons.search, color: Colors.white);
                    this.appBarTitle = new Text("Create Issue");
                  }
                });
              },
            ),
            IconButton(
                icon: Icon(Icons.notification_important),
                onPressed: () {
                  getIssueNotificationDetails();
                })
          ]),
      backgroundColor: AppTheme.white,
      body: FutureBuilder(
        future: getData(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return SizedBox();
          } else {
            return Padding(
              padding: EdgeInsets.only(top: MediaQuery.of(context).padding.top),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Expanded(
                    child: FutureBuilder(
                      future: getData(),
                      builder: (context, snapshot) {
                        if (!snapshot.hasData) {
                          return SizedBox();
                        } else {
                          return Scaffold(
                            body: SingleChildScrollView(
                              padding: const EdgeInsets.all(8.0),
                              child: Center(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    SizedBox(
                                        height: issueType.length > 0 ? 10 : 0),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Visibility(
                                      visible: issueType.length > 0,
                                      child: Container(
                                        height: 70,
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: DropdownButtonHideUnderline(
                                              child: Card(
                                                  elevation: 0.5,
                                                  color: Colors.white70,
                                                  child: Container(
                                                      padding:
                                                          EdgeInsets.all(17),
                                                      child: DropdownButton<
                                                          String>(
                                                        hint: Text(
                                                            "Select Issue Type"),
                                                        value: selectedIssueType !=
                                                                ''
                                                            ? selectedIssueType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            selectedIssueType =
                                                                newValue;
                                                          });
                                                          showFieldsOnIssueType(
                                                              selectedIssueType);
                                                        },
                                                        items: issueType
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'issueTypeName'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'issueTypeId']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: showVehicle == true ? 10 : 0),
                                    Visibility(
                                        visible: showVehicle,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: vehicleData,
                                              controller: vehicleName,
                                              keyboardType:
                                                  TextInputType.number,
                                              hintLabel: 'Vehicle Name',
                                              label: 'Vehicle Name',
                                              dataKeyName: 'vehicleName',
                                              iconData: Icons.person,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  vehicleName.text =
                                                      suggestion['vehicleName'];
                                                  vehicleId =
                                                      suggestion['vehicleId'];
                                                  getOdoMeterDetails(
                                                      suggestion['vehicleId'],
                                                      context);
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getVehicleDetails(pattern);
                                              }),
                                        )),
                                    showPartCategory(),
                                    SizedBox(height: 10),
                                    Visibility(
                                        visible: showDriver,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: driverNameData,
                                              controller: driverName,
                                              hintLabel: 'Driver Name',
                                              label: 'Driver Name',
                                              dataKeyName: 'driverName',
                                              iconData: Icons.person,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  driverName.text =
                                                      suggestion['driverName'];
                                                  driverId =
                                                      suggestion['driver_id'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getDriverNameDetails(pattern);
                                              }),
                                        )),
                                    SizedBox(
                                        height: showOdometer == true ? 10 : 0),
                                    Visibility(
                                        visible: showOdometer,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                maxLines: 1,
                                                controller: odoMeter,
                                                keyboardType:
                                                    TextInputType.number,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    labelText: 'Odometer',
                                                    hintText: "Odometer",
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.departure_board,
                                                      color: Colors.black,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: vehicleGroup &&
                                                vehicleGroupType.length > 0
                                            ? 10
                                            : 0),
                                    Visibility(
                                      visible: vehicleGroup &&
                                          vehicleGroupType.length > 0,
                                      child: Container(
                                        height: 70,
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: DropdownButtonHideUnderline(
                                              child: Card(
                                                  elevation: 0.5,
                                                  color: Colors.white70,
                                                  child: Container(
                                                      padding:
                                                          EdgeInsets.all(17),
                                                      child: DropdownButton<
                                                          String>(
                                                        hint: Text(
                                                            "Select Vehicle Group"),
                                                        value: selectedGroupType !=
                                                                ''
                                                            ? selectedGroupType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            selectedGroupType =
                                                                newValue;
                                                          });
                                                        },
                                                        items: vehicleGroupType
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item['vGroup'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item['gid']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: showBranch == true ? 10 : 0),
                                    Visibility(
                                        visible: showBranch,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: branchNameData,
                                              controller: branchName,
                                              hintLabel: 'Branch Name',
                                              label: 'Branch Name',
                                              dataKeyName: 'branchName',
                                              iconData: Icons.person,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  branchName.text =
                                                      suggestion['branchName'];
                                                  branchId =
                                                      suggestion['branchId'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getBranchNameDetails(pattern);
                                              }),
                                        )),
                                    SizedBox(
                                        height:
                                            showDepartment == true ? 10 : 0),
                                    Visibility(
                                        visible: showDepartment,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList:
                                                  departmentNameData,
                                              controller: departmentName,
                                              hintLabel: 'Department Name',
                                              label: 'Department Name',
                                              dataKeyName: 'departmentName',
                                              iconData: Icons.person,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  departmentName.text =
                                                      suggestion[
                                                          'departmentName'];
                                                  departmentId = suggestion[
                                                      'departmentId'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getDepartmentNameDetails(
                                                    pattern);
                                              }),
                                        )),
                                    SizedBox(
                                        height:
                                            showCustomerName == true ? 10 : 0),
                                    Visibility(
                                        visible: showCustomerName,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                maxLines: 1,
                                                controller: customerName,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    hintText: "Customer Name",
                                                    labelText: "Customer Name",
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.location_city,
                                                      color: Colors.amber,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(height: 10),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            format: format,
                                            controller: issueDate,
                                            readOnly: true,
                                            initialValue: DateTime.now(),
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "Issue Reported Date",
                                                labelText:
                                                    "Issue Reported Date",
                                                hintStyle: TextStyle(
                                                    color: Color(0xff493240)),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.date_range,
                                                  color: Colors.blue,
                                                )),
                                            //format: format,
                                            onShowPicker:
                                                (context, currentValue) {
                                              return showDatePicker(
                                                  context: context,
                                                  firstDate: DateTime(1950),
                                                  initialDate: currentValue ??
                                                      DateTime.now(),
                                                  lastDate: DateTime.now());
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 10),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            readOnly: true,
                                            format: timeFormat,
                                            controller: issueTime,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "Issue Reported Time",
                                                labelText:
                                                    "Issue Reported Time",
                                                hintStyle: TextStyle(
                                                    color: Color(0xff493240)),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.access_time,
                                                  color: Colors.blue,
                                                )),
                                            onShowPicker:
                                                (context, currentValue) async {
                                              final time = await showTimePicker(
                                                context: context,
                                                initialTime:
                                                    TimeOfDay.fromDateTime(
                                                        currentValue ??
                                                            DateTime.now()),
                                                builder: (BuildContext context,
                                                    Widget child) {
                                                  return MediaQuery(
                                                    data: MediaQuery.of(context)
                                                        .copyWith(
                                                            alwaysUse24HourFormat:
                                                                true),
                                                    child: child,
                                                  );
                                                },
                                              );
                                              setState(() {
                                                issueTime.text = issueTime.text;
                                              });
                                              return DateTimeField.convert(
                                                  time);
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 10),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 3,
                                            controller: issueSummary,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Summary',
                                                hintText: 'Summary',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.comment,
                                                  color: Colors.pinkAccent,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    labelType(),
                                    showRoot(),
                                    SizedBox(height: 10),
                                    Container(
                                      child: CustomAutoComplete(
                                          suggestionList: assignData,
                                          controller: assignedTo,
                                          hintLabel: 'Assigned To',
                                          label: 'Assigned To',
                                          dataKeyName: 'user_email',
                                          iconData: Icons.person,
                                          onItemSelected: (suggestion) {
                                            setState(() {
                                              assignedTo.text =
                                                  suggestion['user_email'];
                                              assignToId =
                                                  suggestion['user_id'];
                                            });
                                          },
                                          onChanged: (pattern) {
                                            //method for getting data from server
                                            getAssignedToDetails(pattern);
                                          }),
                                    ),
                                    SizedBox(height: 10),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 3,
                                            controller: issueDescription,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Description',
                                                hintText: 'Description',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.comment,
                                                  color: Colors.pinkAccent,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 10),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            color: Colors.pink,
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(5.0)),
                                          ),
                                          child: MaterialButton(
                                            highlightColor: Colors.transparent,
                                            splashColor: Colors.purpleAccent,
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 10.0,
                                                      horizontal: 42.0),
                                              child: Text(
                                                "Create Issue",
                                                style: TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.white,
                                                    fontWeight:
                                                        FontWeight.w700),
                                              ),
                                            ),
                                            onPressed: _handleSubmitted,
                                          )),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          );
                        }
                      },
                    ),
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }

  Widget labelType() {
    if (mainLabelList != null && mainLabelList.isNotEmpty) {
      return Container(
        margin: EdgeInsets.only(
          top: 10,
        ),
        child: Column(
          children: <Widget>[
            Container(
              child: Text("Select Label Type",
                  style: TextStyle(
                      color: Colors.black,
                      fontSize: 17.0,
                      fontFamily: "WorkSansBold"
                      //color: Colors.white),
                      )),
            ),
            Container(
              child: DropdownButtonHideUnderline(
                child: Card(
                    color: Colors.white,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.0)),
                    borderOnForeground: true,
                    elevation: 5.0,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Select Label Type",
                                style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 18.0,
                                    fontFamily: "WorkSansBold"
                                    //color: Colors.white),
                                    )),
                            value: selectedLabelType != 0
                                ? selectedLabelType.toString()
                                : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                selectedLabelType = int.parse(newValue);
                              });
                            },
                            items: mainLabelList.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['labelName'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 18.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['labelId'].toString(),
                              );
                            }).toList()))),
              ),
            )
          ],
        ),
      );
    } else {
      return Container();
    }
  }

  showFieldsOnIssueType(String selectedIssueType) {
    print("selectedIssueType...${selectedIssueType}");

    int issueTypeId = int.parse(selectedIssueType);

    if (issueTypeId == 1) {
      setState(() {
        showVehicle = true;
        showDriver = true;
        showOdometer = true;
        vehicleGroup = false;
        showBranch = false;
        showDepartment = false;
        showPartCategoryVis = true;
      });
    }

    if (issueTypeId == 2) {
      setState(() {
        showVehicle = false;
        showDriver = true;
        showOdometer = false;
        vehicleGroup = false;
        showBranch = false;
        showDepartment = false;
        showPartCategoryVis = false;
      });
    }

    if (issueTypeId == 3) {
      setState(() {
        showVehicle = false;
        showDriver = false;
        showOdometer = false;
        vehicleGroup = true;
        showBranch = true;
        showDepartment = true;
        showPartCategoryVis = false;
      });
    }

    if (issueTypeId == 5) {
      setState(() {
        showVehicle = false;
        showDriver = false;
        showOdometer = false;
        vehicleGroup = false;
        showBranch = true;
        showDepartment = true;
        showPartCategoryVis = false;
      });
    }
  }

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
  }

  Future getOdoMeterDetails(String id, BuildContext context) async {
    vehicleId = id;
    var data = {'companyId': companyId, 'userId': userId, 'vehicleId': id};
    var response = await ApiCall.getDataFromApi(
        URI.GET_ODO_METER_DETAILS, data, URI.LIVE_URI, context);

    if (response != null) {
      if (response['oddMeterDetails'] != null) {
        var oddMeterDetails = response['oddMeterDetails'];
        var vehicleOdometer = oddMeterDetails['vehicle_Odometer'].toString();
        var expectedOdoMeter =
            oddMeterDetails['vehicle_ExpectedOdameter'].toString();

        setState(() {
          odoMeter.text = vehicleOdometer;
          minOdometer = int.parse(vehicleOdometer);
          maxOdometer =
              int.parse(vehicleOdometer) + int.parse(expectedOdoMeter);
        });
      }
    }
  }

  getVehicleDetails(String str) async {
    vehicleList = new Map();
    //fuelEntryTime.text = '';
    setState(() {
      vehicleData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_VEHICLE_DETAILS, data, URI.LIVE_URI);
      if (response != null) {
        if (response["vehicleList"] != null) {
          print(response["vehicleList"].length);
          for (int i = 0; i < response["vehicleList"].length; i++) {
            var obj = {
              "vehicleId": response['vehicleList'][i]['vid'].toString(),
              "vehicleName":
                  response['vehicleList'][i]['vehicle_registration'].toString()
            };
            vehicleList[obj['vehicleId']] = obj;
            setState(() {
              vehicleData = vehicleList.values.toList();
            });
          }
        } else {
          setState(() {
            vehicleData = [];
          });
          FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          vehicleData = [];
        });
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        vehicleData = [];
      });
    }
  }

  getDriverNameDetails(String str) async {
    driverNameList = new Map();
    driverId = '';
    setState(() {
      driverNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.DRIVER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["driverList"] != null) {
          print(response["driverList"].length);
          for (int i = 0; i < response["driverList"].length; i++) {
            var obj = {
              "driver_id": response['driverList'][i]['driver_id'].toString(),
              "driverName":
                  response['driverList'][i]['driver_empnumber'].toString() +
                      '-' +
                      response['driverList'][i]['driver_firstname'].toString() +
                      ' ' +
                      response['driverList'][i]['driver_Lastname'].toString()
            };
            driverNameList[obj['driver_id']] = obj;
            setState(() {
              driverNameData = driverNameList.values.toList();
            });
          }
        } else {
          setState(() {
            driverNameData = [];
          });
        }
      }
    } else {
      setState(() {
        driverNameData = [];
      });
    }
  }

  getBranchNameDetails(String str) async {
    branchNameList = new Map();
    branchId = '';
    setState(() {
      branchNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_BRANCH_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["BranchList"] != null) {
          print(response["BranchList"].length);
          for (int i = 0; i < response["BranchList"].length; i++) {
            var obj = {
              "branchId": response['BranchList'][i]['branch_id'].toString(),
              "branchName": response['BranchList'][i]['branch_name'].toString()
            };
            branchNameList[obj['branchId']] = obj;
            setState(() {
              branchNameData = branchNameList.values.toList();
            });
          }
        } else {
          setState(() {
            branchNameData = [];
          });
        }
      }
    } else {
      setState(() {
        branchNameData = [];
      });
    }
  }

  getDepartmentNameDetails(String str) async {
    departmentNameList = new Map();
    departmentId = '';
    setState(() {
      departmentNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_DEPARTMENT_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["DepartmentList"] != null) {
          print(response["DepartmentList"].length);
          for (int i = 0; i < response["DepartmentList"].length; i++) {
            var obj = {
              "departmentId":
                  response['DepartmentList'][i]['depart_id'].toString(),
              "departmentName":
                  response['DepartmentList'][i]['depart_name'].toString()
            };
            departmentNameList[obj['departmentId']] = obj;
            setState(() {
              departmentNameData = departmentNameList.values.toList();
            });
          }
        } else {
          setState(() {
            departmentNameData = [];
          });
        }
      }
    } else {
      setState(() {
        departmentNameData = [];
      });
    }
  }

  getAssignedToDetails(String str) async {
    assignList = new Map();
    assignToId = 0;
    setState(() {
      assignData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_USER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["UserList"] != null) {
          print(response["UserList"]);
          setState(() {
            assignData = response["UserList"];
          });
        } else {
          setState(() {
            assignData = [];
          });
        }
      }
    } else {
      setState(() {
        assignData = [];
      });
    }
  }
}
