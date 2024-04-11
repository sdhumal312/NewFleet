import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/Tripsheet/searchTripsheetByVehicle.dart';
import 'package:fleetop/WorkOrder/WorkOrderDetailsByVehicleId.dart';
import 'package:fleetop/WorkOrder/WorkOrderSearchDetails.dart';
import 'package:fleetop/apicall.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:intl/intl.dart';
import 'package:nice_button/nice_button.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fleetop/appTheme.dart';
import '../CustomAutoComplete.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'FuelSearchByDateAndVehicle.dart';
import 'RenewalRemiderSearchDetails.dart';

class SearchScreen extends KFDrawerContent {
  final bool isWOSearch;
  SearchScreen({Key key, this.isWOSearch});
  @override
  _SearchScreenState createState() => _SearchScreenState();
}

class _SearchScreenState extends State<SearchScreen> {
  double _height;
  double _width;
  Size size;
  int typeSelected = 0;
  List searchOption = new List();
  TextEditingController numController = new TextEditingController();

  String companyId;
  String userId;
  String email;
  List workOrderList = new List();
  List vehicleData = List();
  int vehicleId = 0;
  List renewalTypeData = List();
  List renewalSubTypeData = List();
  List approvalStatusList = List();
  int approvalStatusId = 0;
  int selectedRenewalTypeId = 0;
  int selectedRenewalSubTypeId = 0;
  List renewalReminderList = List();
  List fuelDataList = List();
  TextEditingController vehicleNumber = new TextEditingController();
  final format = DateFormat("yyyy-MM-dd");
  TextEditingController fromDateController = new TextEditingController();
  TextEditingController toDateController = new TextEditingController();
  final TimeOfDay time = null;
  DateTime mydate;
  @override
  void initState() {
    super.initState();
    //setIntialDate();
    getSessionData();
    makeSearchDropDown();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    var data = {'companyId': companyId, 'userId': userId, 'email': email};
    var response = await ApiCall.getDataFromApi(
        URI.GET_RR_CONFIG_DATA, data, URI.LIVE_URI, context);
    if (response != null) {
      setState(() {
        renewalTypeData = response['renewalTypeList'];
      });
    }
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
              Color(0xff7a6fe9),
              AppTheme.col2,
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: Stack(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 30),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  widget.isWOSearch != null && widget.isWOSearch
                      ? IconButton(
                          icon: Icon(
                            Icons.arrow_back_ios,
                            color: Colors.white,
                            size: 25,
                          ),
                          onPressed: () {
                            Navigator.pop(context);
                          },
                        )
                      : IconButton(
                          icon: Icon(
                            Icons.menu,
                            color: Colors.white,
                            size: 25,
                          ),
                          onPressed: widget.onMenuPressed,
                        ),
                  Container(
                    margin: EdgeInsets.only(right: _width / 2 - 45),
                    child: Text(
                      'Search',
                      style: TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.w500,
                        fontSize: 22,
                      ),
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
        child: searchBody(),
      ),
    );
  }

  Widget searchBody() {
    return Container(
      margin: EdgeInsets.only(top: 120, left: 2, right: 2, bottom: 0),
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
          Column(
            children: <Widget>[
              SizedBox(height: 5),
              showDropdwon(),
              Visibility(
                  visible: typeSelected == 2,
                  child: renewalReminderSearchData()),
              Visibility(
                visible: typeSelected == 3,
                child: fuelSearchData(),
              ),
              Visibility(
                visible: typeSelected == 4,
                child: tripSearchData(),
              ),
              Visibility(
                visible: typeSelected == 5,
                child: workOrdeySearchByVehicleNumber(),
              ),
              Visibility(
                visible: typeSelected != 2 &&
                    typeSelected != 3 &&
                    typeSelected != 4 &&
                    typeSelected != 5,
                child: Container(
                  margin: EdgeInsets.only(top: 10, left: 5, right: 5),
                  width: _width,
                  height: 70,
                  child: TextFormField(
                    controller: numController,
                    style: TextStyle(
                        fontFamily: "WorkSansSemiBold",
                        fontSize: 16.0,
                        color: Colors.black),
                    decoration: InputDecoration(
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(10.0)),
                      ),
                      prefixIcon: Icon(
                        Icons.format_list_numbered,
                        size: 22.0,
                        color: Colors.black,
                      ),
                      labelText: 'Enter Number',
                      hintText: 'Enter Number',
                      hintStyle: TextStyle(
                          color: Colors.black,
                          fontFamily: "WorkSansSemiBold",
                          fontSize: 17.0),
                    ),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  margin: EdgeInsets.only(
                    top: 15,
                  ),
                  child: NiceButton(
                    radius: 40,
                    text: "Search",
                    icon: Icons.search,
                    gradientColors: [
                      Color(0xff7a6fe9),
                      AppTheme.col2,
                    ],
                    onPressed: () {
                      searchNumber();
                    },
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget showDropdwon() {
    return searchOption.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              margin: EdgeInsets.only(top: 10, left: 5, right: 5),
              width: _width,
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 0.5,
                    color: Colors.white70,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Please Select Search Type"),
                            value: typeSelected != 0
                                ? typeSelected.toString()
                                : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                typeSelected = int.parse(newValue);
                              });
                              //onTypeValueChange(typeSelected);
                            },
                            items: searchOption.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['label'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  getRenewalSubTypeList(String renewalId) async {
    setState(() {
      renewalSubTypeData = [];
      selectedRenewalSubTypeId = 0;
    });
    if (renewalId != null) {
      var data = {
        'companyId': companyId,
        'renewalId': renewalId,
        'userId': userId
      };
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_RR_SUBTYPE_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["renewalSubTypeList"] != null) {
          setState(() {
            renewalSubTypeData = response['renewalSubTypeList'];
          });
        } else {
          setState(() {
            renewalSubTypeData = [];
          });
          FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          renewalSubTypeData = [];
        });
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        renewalSubTypeData = [];
      });
    }
  }

  Widget renewalReminderSearchData() {
    return Container(
      child: Column(
        children: [
          vehicleNumberAC(),
          renderText("Select Renewal Type"),
          remewalTypeDropDown(),
          Visibility(
            visible: selectedRenewalTypeId > 0,
            child: renderText("Select SubRenewal Type"),
          ),
          subRemewalTypeDropDown(),
          fromDateAndToDate(),
          renderText("Select Approval Type"),
          approvalStatusDropDown(),
        ],
      ),
    );
  }

  Widget fuelSearchData() {
    return Container(
      child: Column(
        children: [
          vehicleNumberAC(),
          fromDateAndToDate(),
        ],
      ),
    );
  }

  Widget workOrdeySearchByVehicleNumber() {
    return Container(
      child: Column(
        children: [
          vehicleNumberAC(),
        ],
      ),
    );
  }

  Widget tripSearchData() {
    return Container(
      child: Column(
        children: [
          vehicleNumberAC(),
        ],
      ),
    );
  }

  Widget approvalStatusDropDown() {
    return approvalStatusList.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              margin: EdgeInsets.only(top: 10, left: 5, right: 5),
              width: _width,
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 0.5,
                    color: Colors.white70,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Please Select Approval Type"),
                            value: approvalStatusId.toString(),
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                approvalStatusId = int.parse(newValue);
                              });
                            },
                            items: approvalStatusList.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['label'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  Widget fromDateAndToDate() {
    return Container(
      child: Column(
        children: [fromDate(), toDate()],
      ),
    );
  }

  Widget fromDate() {
    return Container(
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
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
              firstDate: DateTime(DateTime.now().year - 50),
              initialDate: currentValue ?? DateTime.now(),
              lastDate: DateTime(DateTime.now().year + 1));
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

    String dt = now.year.toString() +
        "-" +
        now.month.toString() +
        "-" +
        now.day.toString();
    toDateController.text = dt.toString();
    dt = now.year.toString() +
        "-" +
        now.month.toString() +
        "-" +
        (now.day - 1).toString();
    fromDateController.text = dt.toString();
  }

  Widget toDate() {
    return Container(
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
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
              firstDate: DateTime(DateTime.now().year - 50),
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

  Widget renderText(String text) {
    return Container(
      child: Text(
        text,
        style: TextStyle(
            fontFamily: "WorkSansSemiBold",
            fontSize: 17.0,
            fontWeight: FontWeight.normal,
            color: Colors.black),
      ),
    );
  }

  Widget remewalTypeDropDown() {
    return renewalTypeData.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              margin: EdgeInsets.only(top: 10, left: 5, right: 5),
              width: _width,
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 0.5,
                    color: Colors.white70,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Please Select Renewal Type"),
                            value: selectedRenewalTypeId != 0
                                ? selectedRenewalTypeId.toString()
                                : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                selectedRenewalTypeId = int.parse(newValue);
                              });
                              getRenewalSubTypeList(newValue);
                            },
                            items: renewalTypeData.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['renewal_Type'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['renewal_id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  Widget subRemewalTypeDropDown() {
    return renewalSubTypeData.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              margin: EdgeInsets.only(top: 10, left: 5, right: 5),
              width: _width,
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 0.5,
                    color: Colors.white70,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Please Select SubRenewal Type"),
                            value: selectedRenewalSubTypeId != 0
                                ? selectedRenewalSubTypeId.toString()
                                : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                selectedRenewalSubTypeId = int.parse(newValue);
                              });
                            },
                            items: renewalSubTypeData.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['renewal_SubType'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['renewal_Subid'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  void makeSearchDropDown() {
    var objWO = {"id": 1, "label": 'WORK ORDER-NO SEARCH'};
    var objRR = {"id": 2, "label": 'RENEWAL-REMINDER SEARCH'};
    var objfuel = {"id": 3, "label": 'FUEL SEARCH'};
    var objTrip = {"id": 4, "label": 'TRIPSHEET SEARCH BY VEHICLE'};
    var objWOVEHICLE = {"id": 5, "label": 'WORK ORDER-VEHICLE SEARCH'};
    if (widget.isWOSearch != null && widget.isWOSearch) {
      searchOption.add(objWO);
      searchOption.add(objWOVEHICLE);
      setState(() {
        typeSelected = 1;
      });
    } else {
      searchOption.add(objRR);
      searchOption.add(objfuel);
      searchOption.add(objTrip);
      setState(() {
        typeSelected = 2;
      });
    }
    makeApprovalStatusList();
  }

  searchFuelEntryByVehicleId() async {
    if (vehicleId == 0) {
      FlutterAlert.onInfoAlert(context, "Please Select Vehicle No!", "Info");
      return;
    }
    var tripDetails = {
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'vid': vehicleId.toString(),
    };
    var data = await ApiCall.getDataFromApi(
        URI.SEARCH_TRIPSHEET_BY_VEHICLE, tripDetails, URI.LIVE_URI, context);
    if (data != null) {
      if (data['tripSheet'] == null) {
        FlutterAlert.onInfoAlert(context, "Tripsheet Not Found !", "Info");
      } else if (data['tripSheet'] != null) {
        List tripSheetList = new List();
        setState(() {
          tripSheetList = data['tripSheet'];
        });
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) =>
                new SearchTripsheetByVehicle(fuelDataList: tripSheetList)));
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support !", "Error");
    }
  }

  void makeApprovalStatusList() {
    var all = {"id": 0, "label": 'All'};
    var np = {"id": 1, "label": 'Not Approved'};
    var ap = {"id": 2, "label": 'Approved'};
    approvalStatusList.add(all);
    approvalStatusList.add(np);
    approvalStatusList.add(ap);
    setState(() {
      approvalStatusId = 0;
    });
  }

  getWorkOrderSearchDetails(int id) async {
    if (id == 2) {
      if (vehicleId == 0) {
        FlutterAlert.onInfoAlert(context, "Please Select Vehicle No!", "Info");
        return;
      }
    }
    var woSerachData = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'searchTerm': numController.text,
    };
    var response = await ApiCall.getDataFromApi(
        URI.SEARCH_WORK_ORDER, woSerachData, URI.LIVE_URI, context);
    if (response != null && response["WorkOrder"] != null) {
      setState(() {
        workOrderList = response["WorkOrder"];
      });
      Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => WorkOrderSearchDetails(
                  workOrderList: workOrderList,
                )),
      );
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  searchNumber() {
    if (typeSelected == 0) {
      FlutterAlert.onInfoAlert(context, "Please Select Search Type !", "Info");
      return false;
    }
    if (typeSelected != 2 &&
        typeSelected != 3 &&
        typeSelected != 4 &&
        typeSelected != 5 &&
        (numController.text == '' || numController.text.trim().length == 0)) {
      FlutterAlert.onInfoAlert(context, "Please Enter Number !", "Info");
      return false;
    }
    if (typeSelected == 1) {
      getWorkOrderSearchDetails(0);
    }
    if (typeSelected == 2) {
      getRenewalRemiderDetails();
    }
    if (typeSelected == 3) {
      getFuelSearchDetails();
    }
    if (typeSelected == 4) {
      searchFuelEntryByVehicleId();
    }
    if (typeSelected == 5) {
      getWorkOrderDetailsByVehicleId();
    }
  }

  getWorkOrderDetailsByVehicleId() async {
    if (vehicleId == 0) {
      FlutterAlert.onInfoAlert(context, "Please Select Vehicle No!", "Info");
      return;
    }
    var woSerachData = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'searchTerm': numController.text,
      'vehicleId': vehicleId.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.GET_WORK_ORDER_DETAILS_BY_VEHICLE_ID,
        woSerachData,
        URI.LIVE_URI,
        context);
    if (response != null && response["WorkOrder"] != null) {
      workOrderList = response["WorkOrder"];
      Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) =>
              new WorkOrderDetailsByVehicleId(workorderList: workOrderList)));
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  String getDateRange() {
    if (fromDateController.text == '' || toDateController == '') {
      return "";
    } else {
      String dv = fromDateController.text.trim() +
          " " +
          "to" +
          " " +
          toDateController.text.trim();
      return dv;
    }
  }

  getFuelSearchDetails() async {
    String dateRange = getDateRange();
    if (vehicleId == 0 || vehicleNumber.text == '') {
      FlutterAlert.onInfoAlert(
          context, "Please Select Vehicle Number !", "Info");
      return false;
    }
    var data = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'vid': vehicleId.toString(),
      'dateRange': dateRange
    };
    var response = await ApiCall.getDataFromApi(
        URI.SEARCH_FUEL_ENTRY_BY_VEHICLE_OR_DATE_RANGE,
        data,
        URI.LIVE_URI,
        context);
    if (response != null && response["fuel"] != null) {
      setState(() {
        fuelDataList = response["fuel"];
      });
      Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => FuelSearchByDateAndVehicle(
                  fuelDataList: fuelDataList,
                )),
      );
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  getRenewalRemiderDetails() async {
    String dateRange = getDateRange();
    if (vehicleId == 0 || vehicleNumber.text == '') {
      FlutterAlert.onInfoAlert(
          context, "Please Select Vehicle Number !", "Info");
      return false;
    }
    // if (selectedRenewalTypeId == 0) {
    //   FlutterAlert.onInfoAlert(
    //       context, "Please Select Renewal Type  !", "Info");
    //   return false;
    // }
    // if (selectedRenewalSubTypeId == 0) {
    //   FlutterAlert.onInfoAlert(
    //       context, "Please Select SubRenewal Type  !", "Info");
    //   return false;
    // }
    // if (approvalStatusId == 0) {
    //   FlutterAlert.onInfoAlert(
    //       context, "Please Select Approval Type  !", "Info");
    //   return false;
    // }
    var data = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'vid': vehicleId.toString(),
      'typeId': selectedRenewalTypeId.toString(),
      'subTypeId': selectedRenewalSubTypeId.toString(),
      'dateRange': dateRange
    };

    var response = await ApiCall.getDataFromApi(
        URI.GET_REN_DET_BY_VEHI_RENE_TYPE, data, URI.LIVE_URI, context);
    if (response != null && response["RenewalReminder"] != null) {
      setState(() {
        renewalReminderList = response["RenewalReminder"];
      });
      Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => RenewalRemiderSearchDetails(
                  renewalReminderList: renewalReminderList,
                )),
      );
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  Widget vehicleNumberAC() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 10),
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
          },
          onChanged: (pattern) {
            vehicleNumberSearchForRenewalReminder(pattern);
          }),
    );
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
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    }
  }
}
