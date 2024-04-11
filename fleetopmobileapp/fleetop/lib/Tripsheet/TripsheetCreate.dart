import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/CalenderPopUp/calenderpopupview.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/Tripsheet/searchTripsheetByVehicle.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import '../apicall.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'TripsheetShow.dart';
import 'TripsheetShowAccountClose.dart';

class TripsheetCreate extends KFDrawerContent {
  @override
  _TripsheetCreateState createState() => _TripsheetCreateState();
}

class _TripsheetCreateState extends State<TripsheetCreate>
    with TickerProviderStateMixin {
  AnimationController animationController;
  Function openD;
  bool accountCloseBySearch = false;
  bool multiple = true;
  bool navigateToSearch;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  bool showPayment = false;
  String userId;
  String vehicleNameText;
  String vehicleId;
  String driverId;
  String driver2Id = '0';
  String driverAdvanceId;
  String cleanerId = '0';
  String routeServiceId;
  int newRouteId = 0;
  String loadTypeId = '0';
  bool showVehicleDropdown = false;
  String advancePlaceId;
  int tripsheetId;
  int tripStatus;
  int noOfDaysForBackDate = 0;
  String backDateDispatchDate;
  String privousTime = '';

  List vehicleData = List();
  Map vehicleList = Map();
  List driverNameData = List();
  Map driverNameList = Map();
  List cleanerNameData = List();
  Map cleanerNameList = Map();
  List routeServiceData = List();
  Map routeServiceList = Map();
  List loadTypeData = List();
  Map loadTypeList = Map();

  Map configuration;
  bool allowBackDateEntry = false;
  bool showBackDateTime = false;
  bool showDriver2 = false;
  bool showCleaner = false;
  bool showRouteService = true;
  bool showSubRoot = false;
  bool showNewRoot = false;
  bool showLoadType = false;
  bool showPODdetails = false;
  bool showAdvanceDriver = false;
  bool validateOdometerInTripSheet;
  bool validateMinOdometerInTripSheet;
  bool allowGPSIntegration = false;
  bool showGPSWorking = false;
  bool showGPSLocation = false;
  bool showGPSOdometerMessage = false;
  bool showOpeningKM = true;
  bool showGPSOpeningKM = false;
  bool showStartDisel = false;
  String myfromdate;
  int currentOdometer;
  int vehicle_ExpectedOdameter;
  int finalExpectedOdometer;

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");

  DateTime startDate = DateTime.now();
  DateTime endDate = DateTime.now();

  List<bool> isSelected = [true, false];
  TextEditingController tripsheetNumber = new TextEditingController();
  TextEditingController vehicleName = new TextEditingController();
  TextEditingController vehicleGroup = new TextEditingController();
  TextEditingController gpsLocation = new TextEditingController();
  TextEditingController fromDate = new TextEditingController();
  TextEditingController toDate = new TextEditingController();
  TextEditingController backDateDispatchTime = new TextEditingController();
  TextEditingController openOdoMeter = new TextEditingController();
  TextEditingController gpsOpenOdoMeter = new TextEditingController();
  TextEditingController driverName = new TextEditingController();
  TextEditingController driver2name = new TextEditingController();
  TextEditingController cleaner = new TextEditingController();
  TextEditingController routeService = new TextEditingController();
  TextEditingController subRoute = new TextEditingController();
  TextEditingController newRoute = new TextEditingController();
  TextEditingController bookingReference = new TextEditingController();
  TextEditingController loadType = new TextEditingController();
  TextEditingController tripStartDiesel = new TextEditingController();
  TextEditingController numOfPod = new TextEditingController();
  TextEditingController driverAdvance = new TextEditingController();
  TextEditingController advanceAmnt = new TextEditingController();
  TextEditingController advanceRef = new TextEditingController();
  TextEditingController advancePaidBy = new TextEditingController();
  TextEditingController place = new TextEditingController();
  TextEditingController remarks = new TextEditingController();

  int minOdometer = 0;
  int maxOdometer = 0;
  bool showOdometerRange = false;
  String odometerRange = '';
  bool backDateTripsheet = false;
  bool showAlwaysDispatchTime = true;

  @override
  void initState() {
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  void showNewRouteOption() {
    if (showNewRoot) {
      setState(() {
        showNewRoot = false;
        showRouteService = true;
        newRoute.text = '';
      });
    } else {
      setState(() {
        showNewRoot = true;
        showRouteService = false;
        routeService.text = '';
        routeServiceId = '0';
      });
    }
  }

  void showPaymentOption() {
    if (showPayment) {
      setState(() {
        showPayment = false;
        showAdvanceDriver = false;
      });
    } else {
      setState(() {
        showPayment = true;
        showAdvanceDriver = true;
      });
    }
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    var data = {'companyId': companyId, 'userId': userId, 'email': email};
    var response = await ApiCall.getDataFromApi(
        URI.CREATE_TRIPSHEET, data, URI.LIVE_URI, context);
    print("response :: $response");
    configuration = response['configuration'];

    setState(() {
      showDriver2 = configuration['driver2'];
      showCleaner = configuration['cleaner'];
      showSubRoot = configuration['showSubroute'];
      showLoadType = configuration['showLoadType'];
      showPODdetails = configuration['showPODdetails'];
      showAdvanceDriver = configuration['showAdvanceDriver'];
      allowBackDateEntry = configuration['allowBackDateEntry'];
      noOfDaysForBackDate = configuration['noOfDaysForBackDate'];
      showStartDisel = configuration['tripOpenCloseFuelRequired'];
      showAlwaysDispatchTime = configuration['showAlwaysDispatchTime'];
      print("noOfDaysForBackDate......${noOfDaysForBackDate}");
      advancePaidBy.text = response['paidBy'];
      place.text = response['place'];
      advancePlaceId = response['placeId'].toString();
      validateOdometerInTripSheet = response['validateOdometerInTripSheet'];
      validateMinOdometerInTripSheet =
          response['validateMinOdometerInTripSheet'];
      allowGPSIntegration = response['allowGPSIntegration'];
      //showGPSOpeningKM = allowGPSIntegration;
    });

    if (allowBackDateEntry) {
      startDate = DateTime.now().subtract(Duration(days: noOfDaysForBackDate));
    }
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 0));
    return true;
  }

  Future _handleDispatch() async {
    if (!fieldValidation()) {
      return;
    } else {
      if (numOfPod.text == '') {
        numOfPod.text = '0';
      }

      if (tripStartDiesel.text == '') {
        tripStartDiesel.text = '0';
      }

      var tripsheetData = {
        // 'email': email,
        'userId': userId,
        'companyId': companyId,
        'vid': vehicleId,
        'vehicle_group': vehicleGroup.text,
        'gpsLocation': gpsLocation.text,
        'fromDate': fromDate.text,
        'toDate': toDate.text,
        'backDateDispatchDate': backDateDispatchDate,
        'backDateDispatchTime': backDateDispatchTime.text,
        'dispatchByTime': backDateDispatchTime.text,
        'openOdoMeter': openOdoMeter.text,
        'gpsOpenOdoMeter': gpsOpenOdoMeter.text,
        'driverId': driverId,
        'driver2Id': driver2Id,
        'cleanerId': cleanerId,
        'routeServiceId': routeServiceId,
        'subRoute': subRoute.text,
        'newRouteId': newRouteId.toString(),
        'newRouteName': newRoute.text,
        'bookingReference': bookingReference.text,
        "loadTypeId": loadTypeId,
        "tripStartDiesel": tripStartDiesel.text,
        "numOfPod": numOfPod.text,
        'advanceAmnt': advanceAmnt.text,
        'advanceRef': advanceRef.text,
        'advancePaidBy': userId,
        'place': advancePlaceId,
        'remarks': remarks.text,
        'tripstatusId': '2',
      };

      print(tripsheetData);
      var dispatchData = await ApiCall.getDataFromApi(
          URI.DISPATCH_OR_SAVE_TRIPSHEET, tripsheetData, URI.LIVE_URI, context);

      if (dispatchData != null) {
        if (dispatchData['TIDMandatory'] != null) {
          FlutterAlert.onInfoAlert(
              context, "" + dispatchData['TIDMandatory'], "Info");
        } else if (dispatchData['contactAdmin'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else if (dispatchData['odoMeter'] != null) {
          FlutterAlert.onInfoAlert(
              context, "" + dispatchData['odoMeter'], "Info");
        } else if (dispatchData['vehicleStatus'] != null) {
          FlutterAlert.onInfoAlert(
              context, "" + dispatchData['vehicleStatus'], "Info");
        } else {
          refreshData();

          if (dispatchData['tripsheetId'] != null) {
            tripsheetId = dispatchData['tripsheetId'];
            Navigator.push(
                context,
                new MaterialPageRoute(
                    builder: (context) => new TripsheetShow(
                        tripsheetId: tripsheetId,
                        navigateToSearch: false,
                        accountCloseBySearch: false)));
          }
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  refreshData() {
    vehicleId = '';
    vehicleName.text = '';
    vehicleGroup.text = '';
    gpsLocation.text = '';
    fromDate.text = '';
    toDate.text = '';
    backDateDispatchDate = '';
    backDateDispatchTime.text = '';
    showBackDateTime = false;
    openOdoMeter.text = '';
    gpsOpenOdoMeter.text = '';
    gpsLocation.text = '';
    showOdometerRange = false;
    showGPSOdometerMessage = false;
    driverId = '';
    driver2Id = '0';
    cleanerId = '0';
    routeServiceId = '';
    driverName.text = '';
    driver2name.text = '';
    cleaner.text = '';
    routeService.text = '';
    subRoute.text = '';
    newRoute.text = '';
    bookingReference.text = '';
    loadTypeId = '0';
    loadType.text = '';
    numOfPod.text = '';
    driverAdvanceId = '';
    driverAdvance.text = '';
    advanceAmnt.text = '';
    advanceRef.text = '';
    remarks.text = '';
    /*advancePaidBy.text = '';
    place.text = '';
    advancePlaceId = '';*/
  }

  bool fieldValidation() {
    if (vehicleId == '' || vehicleId == '0' || vehicleId == null) {
      FlutterAlert.onErrorAlert(
          context, "Please Select Valid Vehicle !", "Error");
      return false;
    }
    if (fromDate.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select From Date !", "Error");
      return false;
    }
    if (toDate.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select To Date !", "Error");
      return false;
    }
    if (showAlwaysDispatchTime) {
      if (backDateDispatchTime.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Dispatch Time !", "Error");
        return false;
      }
    }
    if (openOdoMeter.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Enter Odometer !", "Error");
      return false;
    }
    if (num.parse(openOdoMeter.text) < minOdometer ||
        num.parse(openOdoMeter.text) > maxOdometer) {
      FlutterAlert.onErrorAlert(
          context,
          "Please Enter Odometer in Range Of :" +
              minOdometer.toString() +
              " - " +
              maxOdometer.toString(),
          "Error");
      return false;
    }
    if (driverId == '' || driverId == '0' || driverId == null) {
      FlutterAlert.onErrorAlert(context, "Please Enter Driver !", "Error");
      return false;
    }
    if (showRouteService) {
      if (routeServiceId == '' ||
          routeServiceId == '0' ||
          routeServiceId == null) {
        FlutterAlert.onErrorAlert(context, "Please Enter Route !", "Error");
        return false;
      }
    }
    if (showLoadType) {
      if (loadTypeId == '' || loadTypeId == '0' || loadTypeId == null) {
        FlutterAlert.onErrorAlert(context, "Please Enter Load Type !", "Error");
        return false;
      }
    }

    return true;
  }

  searchTripsheetByNumber() async {
    if (!tripsheetNumberValidation()) {
      return;
    } else {
      var tripsheetSearchData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripSheetNumber': tripsheetNumber.text,
      };

      print(tripsheetSearchData);
      var responseTripsheetNumber = await ApiCall.getDataFromApi(
          URI.SEARCH_TRIPSHEET, tripsheetSearchData, URI.LIVE_URI, context);

      if (responseTripsheetNumber != null) {
        if (responseTripsheetNumber['TripSheet'] != null) {
          tripStatus = responseTripsheetNumber['TripSheet']['tripStutesId'];
          int tsId = responseTripsheetNumber['TripSheet']['tripSheetID'];

          if (tripStatus == 2 || tripStatus == 3) {
            Navigator.push(
                context,
                new MaterialPageRoute(
                    builder: (context) => new TripsheetShow(
                        tripsheetId: tsId,
                        navigateToSearch: true,
                        accountCloseBySearch: true)));
          }

          if (tripStatus == 4) {
            Navigator.push(
                context,
                new MaterialPageRoute(
                    builder: (context) => new TripsheetShowAccountClose(
                        tripsheetId: tsId,
                        navigateToSearch: true,
                        accountCloseBySearch: false)));
          }
        } else {
          showTripNumberNotFound();
        }
      }
    }
  }

  bool tripsheetNumberValidation() {
    if (tripsheetNumber.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Tripsheet Number !", "Error");
      return false;
    }
    return true;
  }

  void _handledate() {
    FocusScope.of(context).requestFocus(FocusNode());
    showDemoDialog(context: context);
  }

  showDemoDialog({BuildContext context}) {
    showDialog(
      context: context,
      builder: (BuildContext context) => CalendarPopupView(
        barrierDismissible: true,
        minimumDate: startDate,
        initialStartDate: DateTime.now(),
        initialEndDate: DateTime.now(),
        onApplyClick: (DateTime startData, DateTime endData) {
          setState(() {
            fromDate.text = '';
            toDate.text = '';

            if (startData != null) {
              DateTime currentDate = DateTime.now().subtract(Duration(days: 1));
              fromDate.text = DateFormat("dd-MM-yyyy").format(startData);

              backDateDispatchDate = fromDate.text;
              if (startData.compareTo(currentDate) < 0) {
                showBackDateTime = true;
                backDateDispatchDate = fromDate.text;
                showToast();
              } else {
                // showBackDateTime = false;
                // backDateDispatchDate = '';
                // backDateDispatchTime.text = '';
              }
            } else {
              fromDate.text = DateFormat("dd-MM-yyyy").format(DateTime.now());
            }

            if (endData != null) {
              DateTime currentDate = DateTime.now().subtract(Duration(days: 1));
              toDate.text = DateFormat("dd-MM-yyyy").format(endData);
              //showBackDateMessage();
            } else {
              toDate.text = DateFormat("dd-MM-yyyy").format(DateTime.now());
            }
          });
          // getAllData(startData, endData);
        },
        onCancelClick: () {},
      ),
    );
  }

  void showToast() {
    Fluttertoast.showToast(
        msg:
            'You are creating Back Date Tripsheet. Please enter dispatch time !',
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIos: 60,
        backgroundColor: Colors.pink,
        textColor: Colors.white);
  }

  void showTripNumberNotFound() {
    Alert(
      context: context,
      type: AlertType.error,
      title: "Info",
      desc: " Tripsheet Not Found ! Please enter valid Tripsheet Number ",
      buttons: [
        DialogButton(
          child: Text(
            "OK",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () {
            Navigator.pop(context);
          },
          gradient: LinearGradient(colors: [Colors.green, Colors.deepPurple]),
        ),
      ],
    ).show();
  }

  getVehicleGPSDataAtTime() async {
    var gpsData = {
      'vehicleId': vehicleId,
      'companyId': companyId,
      'dispatchedByTime': backDateDispatchDate,
      'dispatchTime': backDateDispatchTime.text
    };

    var data = await ApiCall.getDataFromApi(
        URI.VEHICLE_GPS_DATA_AT_TIME, gpsData, URI.LIVE_URI, context);
    if (data != null) {
      if (data['VEHICLE_TOTAL_KM'] != null) {
        gpsOpenOdoMeter.text = data['VEHICLE_TOTAL_KM'].toString();
      }
    }
  }

  clearTime() {
    setState(() {
      backDateDispatchTime.text = '';
    });
  }

  Icon actionIcon = new Icon(Icons.search, color: Colors.pink);
  Widget appBarTitle = new Text("Create Tripsheet");
  FocusNode myFocusNode = new FocusNode();

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    if (backDateDispatchTime.text != privousTime) {
      privousTime = backDateDispatchTime.text;

      if (backDateDispatchTime.text != '') {
        getLastNextTripSheetDetails();
      }

      if (allowGPSIntegration == true) {
        if (vehicleId != null) {
          getVehicleGPSDataAtTime();
        } else {
          Fluttertoast.showToast(
              msg: 'Please Select Vehicle !',
              toastLength: Toast.LENGTH_SHORT,
              gravity: ToastGravity.BOTTOM,
              timeInSecForIos: 60,
              backgroundColor: Colors.pink,
              textColor: Colors.white);
          clearTime();
        }
      }
    }

    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.amber,
          centerTitle: true,
          title: appBarTitle,
          leading: new IconButton(
            icon: new Icon(Icons.menu, color: Colors.blue),
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
                      controller: tripsheetNumber,
                      style: new TextStyle(
                        color: Colors.pink,
                      ),
                      focusNode: myFocusNode,
                      keyboardType: TextInputType.number,
                      decoration: new InputDecoration(
                        labelText: 'TS - Number',
                        labelStyle: TextStyle(
                            color: myFocusNode.hasFocus
                                ? Colors.blue
                                : Colors.pink),
                        icon: new RaisedButton(
                          padding: const EdgeInsets.all(2.0),
                          textColor: Colors.white,
                          color: Colors.pink,
                          onPressed: searchTripsheetByNumber,
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
                        new Icon(Icons.search, color: Colors.pink);
                    this.appBarTitle = new Text("Create Tripsheet");
                  }
                });
              },
            ),
          ]),
      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          _settingModalBottomSheet(context);
        },
        child: new Icon(Icons.add),
      ),
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
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Flexible(
                    fit: FlexFit.loose,
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
                                  mainAxisSize: MainAxisSize.min,
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Column(
                                          mainAxisSize: MainAxisSize.min,
                                          children: <Widget>[
                                            SizedBox(height: 10.0),
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                                  false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                                  TextFieldConfiguration(
                                                controller: vehicleName,
                                                keyboardType:
                                                    TextInputType.number,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.directions_bus,
                                                      color: Colors.black,
                                                    ),
                                                    hintText: '',
                                                    labelText:
                                                        'Vehicle Number*'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getVehicleNumberSuggestions(
                                                    pattern);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(
                                                      Icons.local_shipping),
                                                  title: Text(suggestion[
                                                      'vehicleName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                vehicleName.text =
                                                    suggestion['vehicleName'];
                                                getOdoMeterDetails(
                                                    suggestion['vehicleId']);
                                                //checkVehicleStatus(suggestion['vehicleId']);
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            enabled: false,
                                            maxLines: 1,
                                            controller: vehicleGroup,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Vehicle Group',
                                                hintText: "",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.directions_bus,
                                                  color: Colors.pink,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showGPSLocation,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 3,
                                              controller: gpsLocation,
                                              enabled: false,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'GPS Location',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.gps_fixed,
                                                    color:
                                                        Colors.deepPurpleAccent,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    new RaisedButton(
                                      padding: const EdgeInsets.all(1.0),
                                      textColor: Colors.white,
                                      color: Colors.pink,
                                      onPressed: _handledate,
                                      child: Padding(
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 1.0, horizontal: 11.0),
                                        child: Text(
                                          "Select Date",
                                          style: TextStyle(
                                              fontSize: 15,
                                              color: Colors.white,
                                              fontWeight: FontWeight.w500),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 1,
                                            enabled: false,
                                            controller: fromDate,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'From Date',
                                                hintText: "",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.date_range,
                                                  color: Colors.blue,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 1,
                                            enabled: false,
                                            controller: toDate,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'To Date',
                                                hintText: "",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.date_range,
                                                  color: Colors.blue,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),

                                    /*Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            format: format,
                                            controller: fromDate,
                                            initialValue: DateTime.now(),
                                            style:
                                            TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "",
                                                labelText: "From Date*",
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

                                            onShowPicker:
                                                (context, currentValue) {
                                                  return showDatePicker(
                                                  context: context,
                                                  firstDate: startDate,
                                                  initialDate: currentValue ??
                                                      DateTime.now(),
                                                  lastDate: DateTime(2055));

                                            },
                                          ),
                                        ),
                                      ),
                                    ),

                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            format: format,
                                            controller: toDate,
                                            initialValue: DateTime.now(),
                                            style:
                                            TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "",
                                                labelText: "To Date*",
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
                                                  firstDate: DateTime.now(),
                                                  initialDate: currentValue ??
                                                      DateTime.now(),
                                                  lastDate: DateTime(2021));
                                            },
                                          ),
                                        ),
                                      ),
                                    ),*/

                                    SizedBox(height: 15),
                                    Visibility(
                                        visible: showAlwaysDispatchTime,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: DateTimeField(
                                                format: timeFormat,
                                                controller:
                                                    backDateDispatchTime,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    hintText:
                                                        "Enter Dispatch Time",
                                                    labelText:
                                                        "Enter Dispatch Time",
                                                    hintStyle: TextStyle(
                                                        color:
                                                            Color(0xff493240)),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.access_time,
                                                      color: Colors
                                                          .lightGreenAccent,
                                                    )),
                                                onShowPicker: (context,
                                                    currentValue) async {
                                                  final time =
                                                      await showTimePicker(
                                                    context: context,
                                                    initialTime:
                                                        TimeOfDay.fromDateTime(
                                                            currentValue ??
                                                                DateTime.now()),
                                                    builder:
                                                        (BuildContext context,
                                                            Widget child) {
                                                      return MediaQuery(
                                                        data: MediaQuery.of(
                                                                context)
                                                            .copyWith(
                                                                alwaysUse24HourFormat:
                                                                    true),
                                                        child: child,
                                                      );
                                                    },
                                                  );
                                                  setState(() {
                                                    backDateDispatchTime.text =
                                                        backDateDispatchTime
                                                            .text;
                                                  });
                                                  return DateTimeField.convert(
                                                      time);
                                                },
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showOpeningKM,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              enabled: true,
                                              maxLines: 1,
                                              controller: openOdoMeter,
                                              keyboardType:
                                                  TextInputType.number,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Open Odometer*',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.departure_board,
                                                    color: Colors.brown,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    Visibility(
                                      visible: showGPSOdometerMessage,
                                      child: Container(
                                        child: Align(
                                          alignment: Alignment.center,
                                          child:
                                              Text("GPS NOT WORKING/ATTACHED !",
                                                  style: new TextStyle(
                                                    color: Colors.red,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 15,
                                                  )),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height:
                                            showOdometerRange == true ? 5 : 0),
                                    Visibility(
                                        visible: showOdometerRange,
                                        child: Container(
                                          child: Align(
                                            alignment: Alignment.center,
                                            child: Text(odometerRange,
                                                style: new TextStyle(
                                                  color: Colors.red,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15,
                                                )),
                                          ),
                                        )),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showGPSOpeningKM,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              enabled: false,
                                              maxLines: 1,
                                              controller: gpsOpenOdoMeter,
                                              keyboardType:
                                                  TextInputType.number,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText:
                                                      'GPS Opening Odometer*',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.departure_board,
                                                    color: Colors.brown,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            SizedBox(
                                              height: 10.0,
                                            ),
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                                  false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                                  TextFieldConfiguration(
                                                controller: driverName,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.person,
                                                      color: Colors.cyan,
                                                    ),
                                                    hintText: '',
                                                    labelText: 'Driver Name'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getDriverNameSuggestions(
                                                    pattern, 1);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(Icons.person),
                                                  title: Text(
                                                      suggestion['driverName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                driverName.text =
                                                    suggestion['driverName'];
                                                driverId =
                                                    suggestion['driver_id'];
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showDriver2,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Column(
                                            children: <Widget>[
                                              SizedBox(
                                                height: 10.0,
                                              ),
                                              TypeAheadField(
                                                hideSuggestionsOnKeyboardHide:
                                                    false,
                                                hideOnEmpty: true,
                                                textFieldConfiguration:
                                                    TextFieldConfiguration(
                                                  controller: driver2name,
                                                  decoration: InputDecoration(
                                                      border:
                                                          OutlineInputBorder(
                                                              borderRadius:
                                                                  BorderRadius
                                                                      .circular(
                                                                          5.0)),
                                                      icon: Icon(
                                                        Icons.person,
                                                        color:
                                                            Colors.greenAccent,
                                                      ),
                                                      hintText: '',
                                                      labelText:
                                                          'Driver2 Name'),
                                                ),
                                                suggestionsCallback:
                                                    (pattern) async {
                                                  return await getDriverNameSuggestions(
                                                      pattern, 2);
                                                },
                                                itemBuilder:
                                                    (context, suggestion) {
                                                  return ListTile(
                                                    leading: Icon(Icons.person),
                                                    title: Text(suggestion[
                                                        'driverName']),
                                                    // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                  );
                                                },
                                                onSuggestionSelected:
                                                    (suggestion) {
                                                  driver2name.text =
                                                      suggestion['driverName'];
                                                  driver2Id =
                                                      suggestion['driver_id'];
                                                },
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showCleaner,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Column(
                                            children: <Widget>[
                                              SizedBox(
                                                height: 10.0,
                                              ),
                                              TypeAheadField(
                                                hideSuggestionsOnKeyboardHide:
                                                    false,
                                                hideOnEmpty: true,
                                                textFieldConfiguration:
                                                    TextFieldConfiguration(
                                                  controller: cleaner,
                                                  decoration: InputDecoration(
                                                      border:
                                                          OutlineInputBorder(
                                                              borderRadius:
                                                                  BorderRadius
                                                                      .circular(
                                                                          5.0)),
                                                      icon: Icon(
                                                        Icons.person,
                                                        color: Colors.cyan,
                                                      ),
                                                      hintText: '',
                                                      labelText:
                                                          'Cleaner Name'),
                                                ),
                                                suggestionsCallback:
                                                    (pattern) async {
                                                  return await getCleanerNameSuggestions(
                                                      pattern);
                                                },
                                                itemBuilder:
                                                    (context, suggestion) {
                                                  return ListTile(
                                                    leading: Icon(Icons.person),
                                                    title: Text(suggestion[
                                                        'driverName']),
                                                    // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                  );
                                                },
                                                onSuggestionSelected:
                                                    (suggestion) {
                                                  cleaner.text =
                                                      suggestion['driverName'];
                                                  cleanerId =
                                                      suggestion['driver_id'];
                                                },
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showRouteService,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Column(
                                            children: <Widget>[
                                              SizedBox(
                                                height: 10.0,
                                              ),
                                              TypeAheadField(
                                                hideSuggestionsOnKeyboardHide:
                                                    false,
                                                hideOnEmpty: true,
                                                textFieldConfiguration:
                                                    TextFieldConfiguration(
                                                  controller: routeService,
                                                  decoration: InputDecoration(
                                                      border:
                                                          OutlineInputBorder(
                                                              borderRadius:
                                                                  BorderRadius
                                                                      .circular(
                                                                          5.0)),
                                                      icon: Icon(
                                                        Icons.room,
                                                        color:
                                                            Colors.greenAccent,
                                                      ),
                                                      hintText: '',
                                                      labelText:
                                                          'Route Service*'),
                                                ),
                                                suggestionsCallback:
                                                    (pattern) async {
                                                  return await getRouteServiceSuggestions(
                                                      pattern);
                                                },
                                                itemBuilder:
                                                    (context, suggestion) {
                                                  return ListTile(
                                                    leading: Icon(Icons.person),
                                                    title: Text(suggestion[
                                                        'routeName']),
                                                    // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                  );
                                                },
                                                onSuggestionSelected:
                                                    (suggestion) {
                                                  routeService.text =
                                                      suggestion['routeName'];
                                                  routeServiceId =
                                                      suggestion['routeID'];
                                                },
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: FlatButton.icon(
                                          onPressed: showNewRouteOption,
                                          icon: Icon(Icons.add),
                                          label: Text("New Route")),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showNewRoot,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: newRoute,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Enter Route Name',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.room,
                                                    color: Colors.pink,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showSubRoot,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: subRoute,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Sub Route',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.room,
                                                    color: Colors.lightGreen,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 1,
                                            controller: bookingReference,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Booking Reference',
                                                hintText: "",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.photo_library,
                                                  color: Colors.yellow,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showLoadType,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Column(
                                            children: <Widget>[
                                              SizedBox(
                                                height: 10.0,
                                              ),
                                              TypeAheadField(
                                                hideSuggestionsOnKeyboardHide:
                                                    false,
                                                hideOnEmpty: true,
                                                textFieldConfiguration:
                                                    TextFieldConfiguration(
                                                  controller: loadType,
                                                  decoration: InputDecoration(
                                                      border:
                                                          OutlineInputBorder(
                                                              borderRadius:
                                                                  BorderRadius
                                                                      .circular(
                                                                          5.0)),
                                                      icon: Icon(
                                                        Icons
                                                            .check_box_outline_blank,
                                                        color: Colors
                                                            .lightGreenAccent,
                                                      ),
                                                      hintText: '',
                                                      labelText: 'Load Type*'),
                                                ),
                                                suggestionsCallback:
                                                    (pattern) async {
                                                  return await getLoadTypeSuggestions(
                                                      pattern);
                                                },
                                                itemBuilder:
                                                    (context, suggestion) {
                                                  return ListTile(
                                                    leading: Icon(Icons.person),
                                                    title: Text(
                                                        suggestion['loadName']),
                                                    // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                  );
                                                },
                                                onSuggestionSelected:
                                                    (suggestion) {
                                                  loadType.text =
                                                      suggestion['loadName'];
                                                  loadTypeId =
                                                      suggestion['loadID'];
                                                },
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showStartDisel,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: tripStartDiesel,
                                              keyboardType:
                                                  TextInputType.number,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Last Fuel',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.photo_library,
                                                    color: Colors.pink,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showPODdetails,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: numOfPod,
                                              keyboardType:
                                                  TextInputType.number,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Number Of POD',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.photo_library,
                                                    color: Colors.pink,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 3,
                                            controller: remarks,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Remarks',
                                                hintText: '',
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
                                    SizedBox(height: 15),
                                    Container(
                                      child: FlatButton.icon(
                                          onPressed: showPaymentOption,
                                          icon: Icon(Icons.add),
                                          label: Text("Advance Payment")),
                                    ),

                                    /*Visibility(
                                      visible: showPayment,
                                      child: Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            SizedBox(
                                              height: 10.0,
                                            ),
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                              false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                              TextFieldConfiguration(
                                                controller: driverAdvance,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                        BorderRadius
                                                            .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.person,
                                                      color: Colors.greenAccent,
                                                    ),
                                                    hintText: '',
                                                    labelText: 'Driver Advance Name'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getDriverNameSuggestions(
                                                    pattern,3);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(
                                                      Icons.person),
                                                  title: Text(suggestion[
                                                  'driverName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                    driverAdvance.text =
                                                suggestion['driverName'];
                                                    driverAdvanceId = suggestion['driver_id'];
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    ),*/

                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showPayment,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 1.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: advanceAmnt,
                                              keyboardType:
                                                  TextInputType.number,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Advance Amount',
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.credit_card,
                                                    color: Colors.blueAccent,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showPayment,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: advanceRef,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText:
                                                      'Advance Reference',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.credit_card,
                                                    color: Colors.lightBlue,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showPayment,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              enabled: false,
                                              maxLines: 1,
                                              controller: advancePaidBy,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Advance Paid By',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.person,
                                                    color: Colors.lightGreen,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showPayment,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              enabled: false,
                                              maxLines: 1,
                                              controller: place,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText: 'Place',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.place,
                                                    color:
                                                        Colors.lightBlueAccent,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Center(
                                      child: Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        children: <Widget>[
                                          new Row(
                                            mainAxisAlignment:
                                                MainAxisAlignment.spaceEvenly,
                                            children: <Widget>[
                                              /*new  RaisedButton(
                                                 padding: const EdgeInsets.all(8.0),
                                                 textColor: Colors.white,
                                                 color: Colors.lightBlueAccent,
                                                 onPressed: _handleSave,
                                                 child: Padding(
                                                   padding:
                                                   const EdgeInsets.symmetric(
                                                       vertical: 2.0,
                                                       horizontal: 15.0),
                                                   child: Text(
                                                     "Save",
                                                     style: TextStyle(
                                                         fontSize: 18,
                                                         color: AppTheme.darkText,
                                                         fontWeight:
                                                         FontWeight.w500),
                                                   ),
                                                 ),
                                               ),*/

                                              new RaisedButton(
                                                padding:
                                                    const EdgeInsets.all(8.0),
                                                textColor: Colors.white,
                                                color: Colors.pink,
                                                onPressed: _handleDispatch,
                                                child: Padding(
                                                  padding: const EdgeInsets
                                                          .symmetric(
                                                      vertical: 2.0,
                                                      horizontal: 15.0),
                                                  child: Text(
                                                    "Dispatch",
                                                    style: TextStyle(
                                                        fontSize: 18,
                                                        color: Colors.white,
                                                        fontWeight:
                                                            FontWeight.w500),
                                                  ),
                                                ),
                                              ),
                                            ],
                                          )
                                        ],
                                      ),
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

  getLastNextTripSheetDetails() async {
    var data = {
      'userId': userId,
      'vehicleId': vehicleId,
      'companyId': companyId,
      'dispatchedByTime': fromDate.text,
      'dispatchedToByTime': toDate.text,
      'dispatchTime': backDateDispatchTime.text
    };

    var response = await ApiCall.getDataWithoutLoader(
        URI.GET_LAST_NEXT_TSDETAILS, data, URI.LIVE_URI);

    if (response != null) {
      if (response['inBetweenTripSheet'] != null) {
        var dispatchDateTime = fromDate.text.split("-").reversed.join("-") +
            " " +
            backDateDispatchTime.text +
            ":00";
        var nextDispatchDateTime =
            response['inBetweenTripSheet']['dispatchedByTime'];
        var nextClosedByTime = response['inBetweenTripSheet']['closedByTime'];

        if (nextDispatchDateTime.compareTo(dispatchDateTime) < 0 &&
            dispatchDateTime.compareTo(nextClosedByTime) < 0) {
          setState(() {
            backDateDispatchTime.text = '';
            privousTime = '';
          });

          Fluttertoast.showToast(
              msg: "You already Have Tripsheet TS- ${response['inBetweenTripSheet']['tripSheetNumber']} " +
                  "On Same Date And Time Please Select Another Date Or Time.",
              toastLength: Toast.LENGTH_SHORT,
              gravity: ToastGravity.BOTTOM,
              timeInSecForIos: 60,
              backgroundColor: Colors.pink,
              textColor: Colors.white);
        }
      } else {
        if (response['tripSheet'] != null &&
            response['nextTripSheet'] != null) {
          setState(() {
            openOdoMeter.text =
                response['tripSheet']['tripClosingKM'].toString();
          });

          if (response['nextTripSheet']['tripOpeningKM'] >
              response['tripSheet']['tripClosingKM']) {
            setOdometerRange(response['tripSheet']['tripClosingKM'],
                response['nextTripSheet']['tripOpeningKM']);
          } else {
            setOdometerRange(
                response['tripSheet']['tripClosingKM'],
                response['tripSheet']['tripClosingKM'] +
                    vehicle_ExpectedOdameter);
          }
        } else if (response['tripSheet'] != null &&
            response['nextTripSheet'] == null) {
          setState(() {
            openOdoMeter.text =
                response['tripSheet']['tripClosingKM'].toString();
          });
          setOdometerRange(
              response['tripSheet']['tripClosingKM'],
              response['tripSheet']['tripClosingKM'] +
                  vehicle_ExpectedOdameter);
        } else if (response['nextTripSheet'] != null &&
            response['tripSheet'] == null) {
          setState(() {
            openOdoMeter.text = (response['nextTripSheet']['tripOpeningKM'] -
                    vehicle_ExpectedOdameter)
                .toString();
          });

          if (response['nextTripSheet']['tripOpeningKM'] -
                  vehicle_ExpectedOdameter >
              0) {
            setOdometerRange(
                response['nextTripSheet']['tripOpeningKM'] -
                    vehicle_ExpectedOdameter,
                response['nextTripSheet']['tripOpeningKM']);
          } else {
            setOdometerRange(0, response['nextTripSheet']['tripOpeningKM']);
          }
        } else if (response['nextTripSheet'] == null &&
            response['tripSheet'] == null &&
            !backDateTripsheet) {
          setState(() {
            openOdoMeter.text = (currentOdometer).toString();
          });
          setOdometerRange(0, currentOdometer + vehicle_ExpectedOdameter);
        } else if (response['nextTripSheet'] == null &&
            response['tripSheet'] == null &&
            backDateTripsheet) {
          setState(() {
            openOdoMeter.text = (currentOdometer).toString();
          });
          setOdometerRange(0, currentOdometer + vehicle_ExpectedOdameter);
        }

        if (response['tripEndDiesel'] != null &&
            response['tripEndDiesel'] > 0) {
          setState(() {
            tripStartDiesel.text = response['tripEndDiesel'].toString();
          });
        }
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support !", "Error");
    }
  }

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 3));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
  }

  Future<List> getDriverNameSuggestions(String query, int checkDriver) async {
    getDriverNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(driverNameData.length, (index) {
      return driverNameData[index];
    });
  }

  Future<List> getCleanerNameSuggestions(String query) async {
    getCleanerNameDetails(query);
    await Future.delayed(Duration(seconds: 3));

    return List.generate(cleanerNameData.length, (index) {
      return cleanerNameData[index];
    });
  }

  Future<List> getRouteServiceSuggestions(String query) async {
    print(query);
    getRouteServiceDetails(query);
    await Future.delayed(Duration(seconds: 3));
    print(routeServiceData);
    return List.generate(routeServiceData.length, (index) {
      return routeServiceData[index];
    });
  }

  Future<List> getLoadTypeSuggestions(String query) async {
    print(query);
    getLoadTypeDetails(query);
    await Future.delayed(Duration(seconds: 3));
    print(loadTypeData);
    return List.generate(loadTypeData.length, (index) {
      return loadTypeData[index];
    });
  }

  getDriverNameDetails(String str, int checkDriver) async {
    driverNameList = new Map();
    if (checkDriver == 1) {
      driverId = '';
    } else if (checkDriver == 2) {
      driver2Id = '';
    } else {
      driverAdvanceId = '';
    }
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

  getCleanerNameDetails(String str) async {
    cleanerNameList = new Map();
    cleanerId = '';
    setState(() {
      cleanerNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.CLEANER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["cleanerList"] != null) {
          print(response["cleanerList"].length);
          for (int i = 0; i < response["cleanerList"].length; i++) {
            var obj = {
              "driver_id": response['cleanerList'][i]['driver_id'].toString(),
              "driverName": response['cleanerList'][i]['driver_empnumber']
                      .toString() +
                  '-' +
                  response['cleanerList'][i]['driver_firstname'].toString() +
                  ' ' +
                  response['cleanerList'][i]['driver_Lastname'].toString()
            };
            cleanerNameList[obj['driver_id']] = obj;
            setState(() {
              cleanerNameData = cleanerNameList.values.toList();
            });
          }
        } else {
          setState(() {
            cleanerNameData = [];
          });
        }
      }
    } else {
      setState(() {
        cleanerNameData = [];
      });
    }
  }

  getRouteServiceDetails(String str) async {
    routeServiceList = new Map();
    routeServiceId = '';
    setState(() {
      routeServiceData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};

      var response = await ApiCall.getDataWithoutLoader(
          URI.TRIP_ROUTE_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["TripRouteList"] != null) {
          print(response["TripRouteList"]);
          for (int i = 0; i < response["TripRouteList"].length; i++) {
            var obj = {
              "routeID": response['TripRouteList'][i]['routeID'].toString(),
              "routeName": response['TripRouteList'][i]['routeName'].toString()
            };
            routeServiceList[obj['routeID']] = obj;
            setState(() {
              routeServiceData = routeServiceList.values.toList();
            });
          }
        } else {
          setState(() {
            routeServiceData = [];
          });
        }
      }
    } else {
      print("out");
      setState(() {
        routeServiceData = [];
      });
    }
  }

  getLoadTypeDetails(String str) async {
    loadTypeList = new Map();
    loadTypeId = '';
    setState(() {
      loadTypeData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};

      var response = await ApiCall.getDataWithoutLoader(
          URI.LOAD_TYPE_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["LoadTypesList"] != null) {
          print(response["LoadTypesList"]);
          for (int i = 0; i < response["LoadTypesList"].length; i++) {
            var obj = {
              "loadID": response['LoadTypesList'][i]['loadTypesId'].toString(),
              "loadName":
                  response['LoadTypesList'][i]['loadTypeName'].toString()
            };
            loadTypeList[obj['loadID']] = obj;
            setState(() {
              loadTypeData = loadTypeList.values.toList();
            });
          }
        } else {
          setState(() {
            loadTypeData = [];
          });
        }
      }
    } else {
      print("out");
      setState(() {
        loadTypeData = [];
      });
    }
  }

  getVehicleDetails(String str) async {
    vehicleList = new Map();
    vehicleGroup.text = '';
    openOdoMeter.text = '';
    vehicleId = '';
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

  Future getOdoMeterDetails(String id) async {
    vehicleId = id;
    var data = {
      'companyId': companyId,
      'vehicleId': id,
      'dispatchedByTime':
          (DateFormat("dd-MM-yyyy").format(DateTime.now())).toString(),
      'dispatchTime': timeFormat.format(DateTime.now()).toString(),
    };
    print("Initialdata...${data}");

    var response = await ApiCall.getDataFromApi(
        URI.GET_LAST_TSDETAILS, data, URI.LIVE_URI, context);

    if (response != null) {
      if (response['serviceOverDue'] == true) {
        FlutterAlert.onErrorAlert(
            context,
            "There is mandetory service reminder which is overdue, you can not create tripsheet !",
            "Error");
        setState(() {
          vehicleId = '';
          vehicleName.text = '';
        });
      } else {
        if (response['gpsObject'] != null &&
            response['gpsObject']['VEHICLE_TOTAL_KM'] != null &&
            response['gpsObject']['isOdometerReading']) {
          setState(() {
            showGPSOpeningKM = true;
            gpsOpenOdoMeter.text =
                response['gpsObject']['VEHICLE_TOTAL_KM'].toString();
          });
        } else {
          setState(() {
            gpsOpenOdoMeter.text = '0';
          });
        }

        currentOdometer = response['vehicle']['vehicle_Odometer'];
        vehicle_ExpectedOdameter =
            response['vehicle']['vehicle_ExpectedOdameter'];

        if (response['tripSheet'] != null) {
          if (response['tripSheet']['tripClosingKM'] >=
              response['vehicle']['vehicle_Odometer']) {
            setState(() {
              openOdoMeter.text =
                  response['tripSheet']['tripClosingKM'].toString();
            });
          } else {
            setState(() {
              openOdoMeter.text =
                  response['vehicle']['vehicle_Odometer'].toString();
            });
          }

          if (response['tripEndDiesel'] != null &&
              response['tripEndDiesel'] > 0) {
            setState(() {
              tripStartDiesel.text = response['tripEndDiesel'].toString();
            });
          }

          setOdometerRange(
              response['tripSheet']['tripClosingKM'],
              num.parse(openOdoMeter.text) +
                  response['vehicle']['vehicle_ExpectedOdameter']);
        } else {
          setState(() {
            openOdoMeter.text =
                response['vehicle']['vehicle_Odometer'].toString();
          });
          setOdometerRange(
              num.parse(openOdoMeter.text),
              num.parse(openOdoMeter.text) +
                  response['vehicle']['vehicle_ExpectedOdameter']);
        }

        if (response['vehicle'] != null) {
          vehicleGroup.text = response['vehicle']['vehicle_Group'];

          if (response['vehicle']['driverFirstName'] != null) {
            setState(() {
              driverId = response['vehicle']['partlocation_id'].toString();
              driverName.text = response['vehicle']['driverFirstName'];
            });
          }

          if (response['vehicle']['vehicle_RouteName'] != null) {
            setState(() {
              routeServiceId = response['vehicle']['routeID'].toString();
              routeService.text = response['vehicle']['vehicle_RouteName'];
            });
          }
        }
      }

      checkVehicleStatus(vehicleId);
    } else {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support !", "Error");
    }
  }

  setOdometerRange(int startOdometer, int endOdometer) {
    setState(() {
      showOdometerRange = true;
      odometerRange = "Odometer Range is ${startOdometer} to ${endOdometer}";
      minOdometer = startOdometer;
      maxOdometer = endOdometer;
    });
  }

  // Future getOdoMeterDetails(String id) async {
  //   vehicleId = id;
  //   var data = {'companyId': companyId, 'vehicleId': id};
  //   var response = await ApiCall.getDataFromApi(
  //       URI.GET_ODO_METER_DETAILS, data, URI.LIVE_URI, context);

  //   if (response != null) {
  //     if (response['oddMeterDetails'] != null) {
  //       vehicleGroup.text = response['oddMeterDetails']['vehicle_Group'];
  //       openOdoMeter.text = response['oddMeterDetails']['vehicle_Odometer'].toString();
  //       currentOdometer = response['oddMeterDetails']['vehicle_Odometer'];
  //       vehicle_ExpectedOdameter = response['oddMeterDetails']['vehicle_ExpectedOdameter'];
  //       finalExpectedOdometer = currentOdometer + vehicle_ExpectedOdameter;
  //       showGPSWorking = response['oddMeterDetails']['gpsWorking'];

  //       if(showGPSWorking){

  //         if(response['oddMeterDetails']['vehicle_Location'] != null){
  //           setState(() {
  //             showGPSLocation = true;
  //             showGPSOdometerMessage = false;
  //             showOpeningKM = false;
  //             showGPSOpeningKM = true;
  //           });
  //           gpsLocation.text = response['oddMeterDetails']['vehicle_Location'];
  //           gpsOpenOdoMeter.text = response['oddMeterDetails']['gpsOdameter'].toString();
  //         } else {
  //           showGPSLocation = false;

  //             setState(() {
  //               showGPSOpeningKM = allowGPSIntegration;
  //             });

  //             if(showGPSOpeningKM){
  //               setState(() {
  //                 showGPSOpeningKM = true;
  //                 gpsOpenOdoMeter.text = response['oddMeterDetails']['gpsOdameter'].toString();
  //                 showGPSOdometerMessage = false;
  //               });
  //             }
  //         }

  //       } else {
  //         setState(() {
  //           showOpeningKM = true;
  //           showGPSOdometerMessage = true;
  //           showGPSOpeningKM = false;
  //         });
  //       }

  //     }
  //   }
  //   checkVehicleStatus(vehicleId);

  // }

  Future checkVehicleStatus(String id) async {
    vehicleId = id;
    var data = {'companyId': companyId, 'vehicleId': id};

    var response = await ApiCall.getDataFromApi(
        URI.CHECK_VEHICLE_STATUS_ON_TRIPSHEET, data, URI.LIVE_URI, context);

    if (response != null) {
      if (response['vehicleStatusId'] != null &&
          response['vehicleStatusId'] == 6) {
        Alert(
          context: context,
          type: AlertType.error,
          title: "Info",
          desc:
              " You Can not create TripSheet , Vehicle Status is in  ${response['currentVehicleStatus']} ",
          buttons: [
            DialogButton(
              child: Text(
                "OK",
                style: TextStyle(color: Colors.white, fontSize: 20),
              ),
              onPressed: () {
                refreshVehicleData();
                Navigator.pop(context);
              },
              gradient:
                  LinearGradient(colors: [Colors.green, Colors.deepPurple]),
            ),
          ],
        ).show();
      }

      if (response['vehicleStatus'] != null) {
        Alert(
          context: context,
          type: AlertType.error,
          title: "Info",
          desc:
              " Vehicle is already in Trip, please close Tripsheet TS - ${response['vehicleStatus']} first. " +
                  " You can't Dispatch Tripsheet. ",
          buttons: [
            DialogButton(
              child: Text(
                "OK",
                style: TextStyle(color: Colors.white, fontSize: 20),
              ),
              onPressed: () {
                refreshVehicleData();
                Navigator.pop(context);
              },
              gradient:
                  LinearGradient(colors: [Colors.green, Colors.deepPurple]),
            ),
          ],
        ).show();
      }
    }
  }

  refreshVehicleData() {
    vehicleId = '';
    vehicleName.text = '';
    vehicleGroup.text = '';
    openOdoMeter.text = '';
    gpsOpenOdoMeter.text = '';
    setState(() {
      showGPSOdometerMessage = false;
      showOdometerRange = false;
    });
  }

  void _settingModalBottomSheet(context) {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return ListView(
            children: <Widget>[
              Card(
                elevation: 5,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(30),
                      topRight: Radius.circular(30)),
                  //side: BorderSide(width: 5, color: Colors.green)
                ),
                child: ListTile(
                  trailing: Icon(Icons.keyboard_arrow_right),
                  onTap: () => {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => SearchTripsheetByVehicle()),
                    )
                  },
                  leading: new Icon(
                    Icons.directions_bus,
                    color: Colors.red,
                  ),
                  title: Text(
                    "Search Tripsheet By Vehicle",
                    style: new TextStyle(
                      fontSize: 18,
                      color: Colors.black,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ],
          );
        });
  }

  /*Widget appBar() {
    return SizedBox(
      height: AppBar().preferredSize.height,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Visibility(
            visible: true,
            child: Padding(
              padding: EdgeInsets.only(top: 8, left: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
              ),
            ),
          ),
          Visibility(
            visible: true,
            child: Expanded(
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.only(top: 4),
                  child: Text(
                    "Create Tripsheet",
                    style: new TextStyle(
                      fontSize: 22,
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ),
          ),
          Visibility(
            visible: true,
            child: Padding(
              padding: EdgeInsets.only(top: 2, right: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
                color: Colors.white,
                child: Material(
                  color: Colors.transparent,
                  child: InkWell(
                    borderRadius: new BorderRadius.circular(
                        AppBar().preferredSize.height),
                    child: Icon(
                      Icons.search,
                      color: AppTheme.dark_grey,
                    ),
                    onTap: () {
                      */ /*Navigator.of(context).push(new MaterialPageRoute(
                          builder: (context) => new SearchFuelEntry()));*/ /*
                    },
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }*/

}
