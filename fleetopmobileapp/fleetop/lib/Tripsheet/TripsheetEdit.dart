import 'dart:convert';
import 'dart:io';

//import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import '../apicall.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'TripsheetShow.dart';

class TripSheetEdit extends StatefulWidget {

  final Function tripsheetShowData;

  TripSheetEdit({this.tripsheetShowData, this.tripsheetId});
  final int tripsheetId;

  @override
  _TripSheetEditState createState() => _TripSheetEditState();
}

class _TripSheetEditState extends State<TripSheetEdit> {
  bool showTripDetails = false;
  bool showDriver2 = false;
  bool showCleaner = false;
  bool showSubRoot = false;
  bool showLoadType = false;
  bool showPODdetails = false;
  bool showPayment = false;
  String companyId;
  String email;
  String userId;
  int i = 0;
  int tripsheetId;
  String createdDate;
  String tripsheetNumber;
  String vehNumber;
  String route;
  String tripOpenDate;
  String tripCloseDate;
  String vehicle_Group;
  String tripBookref;
  String routeAttendancePoint;
  String routeTotalLiter;
  String tripFristDriverName;
  String tripFristDriverMobile;
  String tripSecDriverName;
  String tripSecDriverMobile;
  String tripCleanerName;
  String tripCleanerMobile;
  String tripOpeningKM;
  String tripClosingKM;
  String dispatchedBy;
  String dispatchedLocation;
  String dispatchedByTime;
  String driverId;
  String driver2Id;
  String cleanerId;
  String routeServiceId;
  String vehicleId;
  String loadTypeId = '0';
  String tripUsageKM;
  String tripFristDriverRoutePoint;
  String tripSecDriverRoutePoint;
  String tripCleanerRoutePoint;
  String closedBy;
  String cloesdLocation;
  String closedByTime;
  String loadTypes;
  String totalPOD;
  String Remark;

  bool  showGPSOpeningKM = false;
  bool backDateTripsheet = false;
  bool showAlwaysDispatchTime = true;
  bool showClosingTime = true;
  String privousTime = '';
  int minOdometer = 0;
  int maxOdometer = 0;
  String odometerRange = '';
  String tripStatusId = '';
  String closetripKm = '0';
  int vehicle_ExpectedOdameter = 0;
  int currentOdometer;

  Map configuration;
  List vehicleData = List();
  Map vehicleList = Map();
  Map tripList = Map();
  List advanceDetail = List();
  Map advanceDetailMap = Map();
  Map fuelVendorList = Map();
  List fuelVendorData = List();
  Map driverNameList = Map();
  List driverNameData = List();
  Map cleanerNameList = Map();
  List cleanerNameData = List();
  Map routeServiceList = Map();
  List routeServiceData = List();
  List loadTypeData = List();
  Map loadTypeList = Map();


  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");
  List<bool> isPaymentSelected = [true, false];
  List<bool> isTankSelected = [true, false];
  TextEditingController vehicleName = new TextEditingController();
  TextEditingController vehicleGroup = new TextEditingController();
  TextEditingController fromDate = new TextEditingController();
  TextEditingController toDate = new TextEditingController();
  TextEditingController backDateDispatchTime = new TextEditingController();
  TextEditingController closingTime = new TextEditingController();
  TextEditingController openOdoMeter = new TextEditingController();
  TextEditingController driverName = new TextEditingController();
  TextEditingController driver2name = new TextEditingController();
  TextEditingController cleaner = new TextEditingController();
  TextEditingController routeService = new TextEditingController();
  TextEditingController loadType = new TextEditingController();
  TextEditingController numOfPod = new TextEditingController();

  @override
  void initState() {
    super.initState();
    getTripsheetShowData(widget.tripsheetId);
  }

  getTripsheetShowData(int tripId) async {
    print("data is  = $tripId");

    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {'companyId': companyId, 'userId' : userId, 'email' : email, 'tripsheetId' : tripId.toString()};
    var response = await ApiCall.getDataFromApi(
        URI.EDIT_TRIPSHEET, data, URI.LIVE_URI, context);

    configuration = response['configuration'];
    tripList = response['TripSheet'];
    minOdometer = response['minAllowed'];
    maxOdometer = response['maxAllowed'];
    showClosingTime = response['TripClosed'];
    vehicle_ExpectedOdameter = response['vehicle_ExpectedOdameter'];

    setState(() {
      tripsheetId = tripId;
      vehicleId = tripList['vid'].toString();
      createdDate = tripList['created'];
      tripsheetNumber = tripList['tripSheetNumber'].toString();
      vehNumber = tripList['vehicle_registration'];
      route = tripList['routeName'];
      tripOpenDate = tripList['tripOpenDate'];
      tripCloseDate = tripList['closetripDate'];
      vehicle_Group = tripList['vehicle_Group'];
      tripBookref = tripList['tripBookref'];
      routeAttendancePoint = tripList['routeAttendancePoint'].toString();
      routeTotalLiter = tripList['routeTotalLiter'].toString();
      tripFristDriverName = tripList['tripFristDriverName'];
      tripSecDriverName = tripList['tripSecDriverName'];
      tripCleanerName = tripList['tripCleanerName'];
      tripOpeningKM = tripList['tripOpeningKM'].toString();
      tripClosingKM = tripList['tripClosingKM'].toString();
      dispatchedBy = tripList['dispatchedBy'];
      dispatchedLocation = tripList['dispatchedLocation'];
      dispatchedByTime = tripList['dispatchedByTime'];
      vehicleName.text = tripList['vehicle_registration'];
      vehicleGroup.text = tripList['vehicle_Group'];
      fromDate.text = tripList['tripOpenDate'];
      toDate.text = tripList['closetripDate'];
      backDateDispatchTime.text = tripList['dispatchedTime'];
      closingTime.text = tripList['closedTripTime'];
      openOdoMeter.text = tripList['tripOpeningKM'].toString();
      driverId = tripList['tripFristDriverID'].toString();
      driverName.text = tripList['tripFristDriverName'].toString();
      driver2Id = tripList['tripSecDriverID'].toString();
      driver2name.text = tripList['tripSecDriverName'].toString();
      cleanerId = tripList['tripCleanerID'].toString();
      cleaner.text = tripList['tripCleanerName'].toString();
      routeServiceId = tripList['routeID'].toString();
      routeService.text = tripList['routeName'].toString();
      loadTypeId = tripList['loadTypesId'].toString();
      loadType.text = tripList['loadTypeName'].toString();
      numOfPod.text = tripList['noOfPOD'].toString();
      showSubRoot = configuration['showSubroute'];
      showDriver2 = configuration['driver2'];
      showCleaner = configuration['cleaner'];
      showLoadType = configuration['showLoadType'];
      showPODdetails = configuration['showPODdetails'];
      showAlwaysDispatchTime = configuration['showAlwaysDispatchTime'];
      tripUsageKM = tripList['tripUsageKM'].toString();
      tripFristDriverRoutePoint = tripList['tripFristDriverRoutePoint'].toString();
      tripSecDriverRoutePoint = tripList['tripSecDriverRoutePoint'].toString();
      tripCleanerRoutePoint = tripList['tripCleanerRoutePoint'].toString();
      closedBy = tripList['closedBy'];
      cloesdLocation = tripList['cloesdLocation'];
      closedByTime = tripList['closedByTime'];
      loadTypes = tripList['loadTypeName'];
      totalPOD = tripList['noOfPOD'].toString();
      Remark = tripList['routeRemark'];
      tripStatusId = tripList['tripStutesId'].toString();
      closetripKm = tripList['tripClosingKM'].toString();

      odometerRange = "Odometer Range is ${minOdometer} to ${maxOdometer}";
    });

  }

  Future updateTripSheet() async {
    if (!fieldValidation()) {
      return;
    } else {

      if(numOfPod.text == ''){
        numOfPod.text = '0';
      }

      if(loadTypeId == 'null'){
        loadTypeId = '0';
      }

      var updateTripsheetData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripsheetId': tripsheetId.toString(),
        'vid': vehicleId,
        'vehicle_group': vehicleGroup.text,
        'fromDate': fromDate.text,
        'toDate': toDate.text,
        'openOdoMeter': openOdoMeter.text,
        'driverId': driverId,
        'driver2Id': driver2Id,
        'cleanerId': cleanerId,
        'routeServiceId': routeServiceId,
        'loadTypeId': loadTypeId,
        'numOfPod': numOfPod.text,
        'tripstatusId': tripStatusId,
        'gpsLocation': '',
        'dispatchByTime': backDateDispatchTime.text,
        'backDateDispatchTime': backDateDispatchTime.text,
        'backDateDispatchDate': fromDate.text,
        'closetripKm': closetripKm,
        'tripEndTime': closingTime.text,
      };

      print("update TripsheetData ::::: ${updateTripsheetData}");

      var data = await ApiCall.getDataFromApi(
          URI.UPDATE_TRIPSHEET, updateTripsheetData, URI.LIVE_URI, context);

      if (data != null) {
        if (data['alreadyExist'] == true) {
          FlutterAlert.onInfoAlert(context, "Already Exist!", "Info");
        } else if (data['contactAdmin'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else if (data['odoMeter'] != null) {
          FlutterAlert.onInfoAlert(context, "" + data['odoMeter'], "Info");
        } else if (data['vehicleStatus'] != null) {
          FlutterAlert.onInfoAlert(context, "" + data['vehicleStatus'], "Info");
        } else {

          Alert(
            context: context,
            type: AlertType.success,
            title: "Info",
            desc: " Tripsheet Updated Successfully ! ",
            buttons: [
              DialogButton(
                child: Text(
                  "OK",
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
                onPressed: ()
                {
                  Navigator.pop(context);
                  Navigator.pop(context);
                  Navigator.pop(context);
                  widget.tripsheetShowData();
                },
                gradient: LinearGradient(colors: [
                  Colors.green,
                  Colors.deepPurple
                ]),
              ),

            ],
          ).show();

          /*FlutterAlert.onSuccessAlert(
              context, " Tripsheet Updated Successfully !", " Tripsheet Updated ");
          getTripsheetShowData(tripsheetId);*/
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }

    }

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

    if(showAlwaysDispatchTime){
      if (backDateDispatchTime.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Dispatch Time !", "Error");
      return false;
      }
    }

    if(showClosingTime){
      if (closingTime.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Closing Time !", "Error");
      return false;
      }
    }

    if (openOdoMeter.text == '' || openOdoMeter.text == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter Odometer !", "Error");
      return false;
    }

    if(num.parse(openOdoMeter.text) < minOdometer || num.parse(openOdoMeter.text) > maxOdometer){
        FlutterAlert.onErrorAlert(context, "Please Enter Odometer in Range Of :"+minOdometer.toString()+" - "+maxOdometer.toString() , "Error");
        return false;
    }

    if (driverId == '' || driverId == '0'|| driverId == null) {
      FlutterAlert.onErrorAlert(context, "Please Enter Driver !", "Error");
      return false;
    }

    if (routeServiceId == '' || routeServiceId == null) {
      FlutterAlert.onErrorAlert(context, "Please Enter Route !", "Error");
      return false;
    }

    if(showLoadType){
      if (loadTypeId == '' || loadTypeId == '0'|| loadTypeId == null) {
        FlutterAlert.onErrorAlert(context, "Please Enter Load Type !", "Error");
        return false;
      }
    }

    return true;
  }


  @override
  Widget build(BuildContext context) {

    if (backDateDispatchTime.text != privousTime) {

      privousTime = backDateDispatchTime.text;

      if(backDateDispatchTime.text != ''){
        print("ola...");
        getLastNextTripSheetDetails();
      }
      
    }

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.amber,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Edit Tripsheet",
          style: new TextStyle(
            fontSize: 22,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
        ),
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () =>
          {
            /*Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripsheetShow (tripsheetId :tripsheetId)))*/
            Navigator.pop(context),
            Navigator.pop(context),
            widget.tripsheetShowData()
          },
        ),
      ),


      backgroundColor: AppTheme.white,
      body: Container(
          child: SingleChildScrollView(
              child: Center(
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[

                        SizedBox(height: 5),
                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Created Date : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 17,
                                  )
                              ),

                              new TextSpan(
                                  text: " ${createdDate} ",
                                  style: new TextStyle(
                                    color: Colors.blue,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 17,
                                  )
                              ),
                            ],
                          ),
                        ),

                        SizedBox(height: 25),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Trip Number : TS - ${tripsheetNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.red, fontSize: 19)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${vehNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.green, fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${route}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.green, fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.blue,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Tripsheet Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showTripDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                                        onPressed: () => {
                                          setState(() {
                                            showTripDetails =
                                            !showTripDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showTripDetails,
                            child:Stack(
                              children: <Widget>[
                                showTripInfo(context),
                                /*ola(context),*/
                              ],
                            )
                        ),
//
                        SizedBox(height: 60),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Edit Tripsheet Details ",
                                style: new TextStyle(
                                  color: Colors.blueAccent,
                                  fontWeight: FontWeight.w700,
                                  fontSize: 15,
                                )
                            ),
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery.of(context).size.width,
                          color: Colors.blueAccent,
                          margin: const EdgeInsets.only(left: 80.0, right: 80.0),
                        ),

                        // SizedBox(height: 15),
                        // Container(
                        //   child: Padding(
                        //     padding: const EdgeInsets.only(
                        //         top: 5.0, left: 10),
                        //     child: Container(
                        //       child: TextField (
                        //         maxLines: 1,
                        //         controller: vehicleName,
                        //         style:
                        //         TextStyle(color: Colors.black),
                        //         decoration: InputDecoration(
                        //             labelText: 'Vehicle',
                        //             hintText: "",
                        //             hintStyle: TextStyle(
                        //                 color: Colors.black),
                        //             border: OutlineInputBorder(
                        //                 borderRadius:
                        //                 BorderRadius.circular(
                        //                     5.0)),
                        //             icon: Icon(
                        //               Icons.directions_bus,
                        //               color: Colors.lightGreen,
                        //             )),
                        //       ),
                        //     ),
                        //   ),
                        // ),

                        SizedBox(height: 10.0),
                        TypeAheadField(
                          hideSuggestionsOnKeyboardHide:
                          false,
                          hideOnEmpty: true,
                          textFieldConfiguration:
                          TextFieldConfiguration(
                          controller: vehicleName,
                          keyboardType: TextInputType.number,
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
                                  vehicleName.text = suggestion['vehicleName'];
                                  getOdoMeterDetails(suggestion['vehicleId']);
                                  //checkVehicleStatus(suggestion['vehicleId']);
                                },
                              ),
                         

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child: Container(
                              child: TextField(
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
                                      Icons.group,
                                      color: Colors.lightBlueAccent,
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
                                //format: format,
                                onShowPicker:
                                    (context, currentValue) {
                                  return showDatePicker(
                                      context: context,
                                      firstDate: DateTime(2020),
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
                                      firstDate: DateTime(2020),
                                      initialDate: currentValue ??
                                          DateTime.now(),
                                      lastDate: DateTime(2055));
                                },
                              ),
                            ),
                          ),
                        ),

                        // SizedBox(height: 15),
                        // Container(
                        //   child: Padding(
                        //     padding: const EdgeInsets.only(
                        //         top: 5.0, left: 10),
                        //     child: Container(
                        //       child: DateTimeField(
                        //         format: format,
                        //         controller: toDate,
                        //         initialValue: DateTime.now(),
                        //         style:
                        //         TextStyle(color: Colors.black),
                        //         decoration: InputDecoration(
                        //             hintText: "",
                        //             labelText: "To Date*",
                        //             hintStyle: TextStyle(
                        //                 color: Color(0xff493240)),
                        //             border: OutlineInputBorder(
                        //                 borderRadius:
                        //                 BorderRadius.circular(
                        //                     5.0)),
                        //             icon: Icon(
                        //               Icons.date_range,
                        //               color: Colors.blue,
                        //             )),
                        //         //format: format,
                        //         onShowPicker:
                        //             (context, currentValue) {
                        //           return showDatePicker(
                        //               context: context,
                        //               firstDate: DateTime(2020),
                        //               initialDate: currentValue ??
                        //                   DateTime.now(),
                        //               lastDate: DateTime(2020));
                        //         },
                        //       ),
                        //     ),
                        //   ),
                        // ),

                        SizedBox(height: 15),
                        Visibility(
                        visible: showAlwaysDispatchTime,
                        child:Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child: Container(
                              child: DateTimeField(
                                format: timeFormat,
                                controller: backDateDispatchTime,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    hintText: "Enter Dispatch Time",
                                    labelText: "Enter Dispatch Time",
                                    hintStyle: TextStyle(
                                        color: Color(0xff493240)),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.access_time,
                                      color: Colors.lightGreenAccent,
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
                                    backDateDispatchTime.text =
                                        backDateDispatchTime.text;
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
                        visible: showClosingTime,
                        child:Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child: Container(
                              child: DateTimeField(
                                format: timeFormat,
                                controller: closingTime,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    hintText: "Enter Closing Time",
                                    labelText: "Enter Closing Time",
                                    hintStyle: TextStyle(
                                        color: Color(0xff493240)),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.access_time,
                                      color: Colors.lightGreenAccent,
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
                                    closingTime.text =
                                        closingTime.text;
                                  });
                                  return DateTimeField.convert(
                                      time);
                                },
                              ),
                            ),
                          ),
                        )),

                        SizedBox(height: 20),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                left: 10),
                            child: Column(
                              children: <Widget>[
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
                                        hintText: 'Driver Name',
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
                                          hintText: 'Driver2 Name',
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
                                            color: Colors.amber,
                                          ),
                                          hintText: 'Cleaner Name',
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
                                top: 1.0, left: 10),
                            child: Container(
                              child: TextField(
                                maxLines: 1,
                                controller: openOdoMeter,
                                keyboardType: TextInputType.number,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                  labelText: 'Open Odometer',
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
                                  ),

                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height:5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                odometerRange,
                                style: new TextStyle(
                                  color: Colors.red,
                                  fontWeight: FontWeight.w700,
                                  fontSize: 15,
                                )
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
                                    controller: routeService,
                                    decoration: InputDecoration(
                                        border:
                                        OutlineInputBorder(
                                            borderRadius:
                                            BorderRadius
                                                .circular(
                                                5.0)),
                                        icon: Icon(
                                          Icons.directions,
                                          color: Colors.black,
                                        ),
                                        hintText: 'Route Service',
                                        labelText:
                                        'Route Service'),
                                  ),
                                  suggestionsCallback:
                                      (pattern) async {
                                    return await getRouteServiceSuggestions(
                                        pattern);
                                  },
                                  itemBuilder:
                                      (context, suggestion) {
                                    return ListTile(
                                      leading:
                                      Icon(Icons.directions),
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
                                          border: OutlineInputBorder(
                                              borderRadius:
                                              BorderRadius
                                                  .circular(5.0)),
                                          icon: Icon(
                                            Icons.check_box_outline_blank,
                                            color: Colors.lightGreenAccent,
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
                                        leading: Icon(
                                            Icons.person),
                                        title: Text(suggestion[
                                        'loadName']),
                                        // subtitle: Text('\$${suggestion['vehicleId']}'),
                                      );
                                    },
                                    onSuggestionSelected:
                                        (suggestion) {
                                      loadType.text =
                                      suggestion['loadName'];
                                      loadTypeId = suggestion['loadID'];
                                    },
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Visibility(
                          visible: showPODdetails,
                          child:Container(
                            child: Padding(
                              padding: const EdgeInsets.only(
                                  top: 5.0, left: 10),
                              child: Container(
                                child: TextField(
                                  maxLines: 1,
                                  controller: numOfPod,
                                  style:
                                  TextStyle(color: Colors.black),
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
                            child:
                            new  RaisedButton(
                              padding: const EdgeInsets.all(8.0),
                              textColor: Colors.white,
                              color: Colors.pink,
                              onPressed: updateTripSheet,
                              child: Padding(
                                padding:
                                const EdgeInsets.symmetric(
                                    vertical: 2.0,
                                    horizontal: 15.0),
                                child: Text(
                                  "Update TripSheet",
                                  style: TextStyle(
                                      fontSize: 18,
                                      color: Colors.white,
                                      fontWeight:
                                      FontWeight.w500),
                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 25),

                      ]
                  )
              )
          )
      ),
    );
  }

  getLastNextTripSheetDetails() async {

    var data = {'userId' : userId ,
    'companyId': companyId,
    'vehicleId': vehicleId,
    'dispatchedByTime': fromDate.text,
    'dispatchedToByTime': toDate.text,
    'dispatchTime': backDateDispatchTime.text,
    'tripSheetId': tripsheetId.toString()
    };

    print("data...${data}");
    
    var response =  await ApiCall.getDataWithoutLoader(
          URI.GET_LAST_NEXT_TSDETAILS_EDIT, data, URI.LIVE_URI);

    if(response != null){

      if(response['inBetweenTripSheet'] != null){
        var dispatchDateTime = fromDate.text.split("-").reversed.join("-") +" "+ backDateDispatchTime.text +":00";
        var dispatchedToByTime = toDate.text.split("-").reversed.join("-") +" "+ backDateDispatchTime.text +":00";
        var nextDispatchDateTime = response['inBetweenTripSheet']['dispatchedByTime'];
        var nextClosedByTime = response['inBetweenTripSheet']['closedByTime'];

        int tripId = tripsheetId;
        int tId = response['inBetweenTripSheet']['tripSheetID'];
        bool value = true;

        if(tripId == tId){
          value = false;
        } else {
          value = true;
        }

        print("value....${value}");

        if( (nextDispatchDateTime.compareTo(dispatchDateTime) < 0 && dispatchDateTime.compareTo(nextClosedByTime) < 0) || (nextDispatchDateTime.compareTo(dispatchedToByTime) < 0 && dispatchedToByTime.compareTo(nextClosedByTime) < 0 )){
          print("value1....${value}");
          if(value){

            setState(() {
              backDateDispatchTime.text = '';
              privousTime = '';
            });

            Fluttertoast.showToast(
                msg: "You already Have Tripsheet TS- ${response['inBetweenTripSheet']['tripSheetNumber']} "
                  +"On Same Date And Time Please Select Another Date Or Time.",
                toastLength: Toast.LENGTH_SHORT,
                gravity: ToastGravity.BOTTOM,
                timeInSecForIos: 60,
                backgroundColor: Colors.pink,
                textColor: Colors.white
            );

          }
          
        } 

      } else {
          if(response['tripSheet'] != null && response['nextTripSheet'] != null){
          setState(() {
                openOdoMeter.text = response['tripSheet']['tripClosingKM'].toString();
          });

          if(response['nextTripSheet']['tripOpeningKM'] > response['tripSheet']['tripClosingKM']){
            setOdometerRange(response['tripSheet']['tripClosingKM'], response['nextTripSheet']['tripOpeningKM']);
          }else {
            setOdometerRange(response['tripSheet']['tripClosingKM'], response['tripSheet']['tripClosingKM'] + vehicle_ExpectedOdameter);
          }

        } else if (response['tripSheet'] != null && response['nextTripSheet'] == null){
          setState(() {
                openOdoMeter.text = response['tripSheet']['tripClosingKM'].toString();
          });
          setOdometerRange(response['tripSheet']['tripClosingKM'], response['tripSheet']['tripClosingKM'] + vehicle_ExpectedOdameter);
        
        } else if(response['nextTripSheet'] != null && response['tripSheet'] == null){
          setState(() {
                openOdoMeter.text = (response['nextTripSheet']['tripOpeningKM'] - vehicle_ExpectedOdameter).toString();
          });

          if(response['nextTripSheet']['tripOpeningKM'] - vehicle_ExpectedOdameter > 0){
            setOdometerRange(response['nextTripSheet']['tripOpeningKM'] - vehicle_ExpectedOdameter, response['nextTripSheet']['tripOpeningKM']);
          }else {
            setOdometerRange(0, response['nextTripSheet']['tripOpeningKM']);
          }

        } else if (response['nextTripSheet'] == null && response['tripSheet'] == null && !backDateTripsheet){
          setState(() {
                openOdoMeter.text = (minOdometer).toString();
          });
          setOdometerRange(0, minOdometer + vehicle_ExpectedOdameter);
        
        } else if (response['nextTripSheet'] == null && response['tripSheet'] == null && backDateTripsheet){
          setState(() {
                openOdoMeter.text = (minOdometer).toString();
          });
          setOdometerRange(0, minOdometer + vehicle_ExpectedOdameter);
        }

      }
         
    } else {
      FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
    }      

  }

  setOdometerRange(int startOdometer, int endOdometer){
    setState(() {
        odometerRange = "Odometer Range is ${startOdometer} to ${endOdometer}";
        minOdometer = startOdometer;
        maxOdometer = endOdometer;
    });
  }

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 3));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
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
    var data = {'companyId': companyId, 
    'vehicleId': id,
    'dispatchedByTime': (DateFormat("dd-MM-yyyy").format(DateTime.now())).toString(),
    'dispatchTime': timeFormat.format(DateTime.now()).toString(),
    };
    print("Initialdata...${data}");

    var response = await ApiCall.getDataFromApi(
        URI.GET_LAST_TSDETAILS, data, URI.LIVE_URI, context);

    if(response != null){

      if(response['serviceOverDue'] == true){
        FlutterAlert.onErrorAlert(
            context, "There is mandetory service reminder which is overdue, you can not create tripsheet !", "Error");
        setState(() {
          vehicleId = '';
          vehicleName.text = '';
        });

      } else {

        currentOdometer = response['vehicle']['vehicle_Odometer'];
        vehicle_ExpectedOdameter = response['vehicle']['vehicle_ExpectedOdameter'];

        if(response['tripSheet'] != null){
          if(response['tripSheet']['tripClosingKM'] >= response['vehicle']['vehicle_Odometer']){
            setState(() {
              openOdoMeter.text = response['tripSheet']['tripClosingKM'].toString();
            });
          } else {
            setState(() {
              openOdoMeter.text = response['vehicle']['vehicle_Odometer'].toString();
            });
          }

          setOdometerRange(response['tripSheet']['tripClosingKM'], num.parse(openOdoMeter.text) + response['vehicle']['vehicle_ExpectedOdameter']);

        } else {
          setState(() {
              openOdoMeter.text = response['vehicle']['vehicle_Odometer'].toString();
          });
          setOdometerRange(num.parse(openOdoMeter.text), num.parse(openOdoMeter.text) + response['vehicle']['vehicle_ExpectedOdameter']);
        }

      }

      checkVehicleStatus(vehicleId);

    } else {
      FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
    }   

  } 


  Future<List> getDriverNameSuggestions(String query, int checkDriver) async {
    getDriverNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(driverNameData.length, (index) {
      return driverNameData[index];
    });
  }

  getDriverNameDetails(String str, int checkDriver) async {
    driverNameList = new Map();
    if (checkDriver == 1) {
      driverId = '';
    } else {
      driver2Id = '';
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

  Future<List> getCleanerNameSuggestions(String query) async {
    getCleanerNameDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(cleanerNameData.length, (index) {
      return cleanerNameData[index];
    });
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

  Future<List> getRouteServiceSuggestions(String query) async {
    getRouteServiceDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(routeServiceData.length, (index) {
      return routeServiceData[index];
    });
  }

  getRouteServiceDetails(String str) async {
    routeServiceList = new Map();
    routeServiceId = '';
    setState(() {
      routeServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TRIP_ROUTE_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["TripRouteList"] != null) {
          print(response["TripRouteList"].length);
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
      setState(() {
        routeServiceData = [];
      });
    }
  }

  Future<List> getLoadTypeSuggestions(String query) async {
    print(query);
    getLoadTypeDetails(query);
    await Future.delayed(Duration(seconds: 1));
    print(loadTypeData);
    return List.generate(loadTypeData.length, (index) {
      return loadTypeData[index];
    });
  }

  getLoadTypeDetails(String str) async {
    loadTypeList = new Map();
    loadTypeId = '';
    setState(() {
      loadTypeData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'userId' : '135' ,'companyId': '14', 'term': str};

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

  Future checkVehicleStatus(String id) async {
    vehicleId = id;
    var data = {'companyId': companyId, 'vehicleId': id};

    var response = await ApiCall.getDataFromApi(
        URI.CHECK_VEHICLE_STATUS_ON_TRIPSHEET, data, URI.LIVE_URI, context);

    if (response != null) {
      if (response['vehicleStatusId'] != null && response['vehicleStatusId'] == 6) {
        Alert(
          context: context,
          type: AlertType.error,
          title: "Info",
          desc: " You Can not create TripSheet , Vehicle Status is in  ${response['currentVehicleStatus']} ",
          buttons: [
            DialogButton(
              child: Text(
                "OK",
                style: TextStyle(color: Colors.white, fontSize: 20),
              ),
              onPressed: ()
              {
                //refreshVehicleData();
                Navigator.pop(context);
              },
              gradient: LinearGradient(colors: [
                Colors.green,
                Colors.deepPurple
              ]),
            ),

          ],
        ).show();
      }

      if (response['vehicleStatus'] != null) {
        Alert(
          context: context,
          type: AlertType.error,
          title: "Info",
          desc: " Vehicle is already in Trip, please close Tripsheet TS - ${response['vehicleStatus']} first. "
                  +" You can't Dispatch Tripsheet. ",
          buttons: [
            DialogButton(
              child: Text(
                "OK",
                style: TextStyle(color: Colors.white, fontSize: 20),
              ),
              onPressed: ()
              {
                  //refreshVehicleData();
                  Navigator.pop(context);
              },
              gradient: LinearGradient(colors: [
                Colors.green,
                Colors.deepPurple
              ]),
            ),

          ],
        ).show();
      }
    }

  }


  Widget showTripInfo(context)
  {
    return  Column(
      children: <Widget>[

        GestureDetector(
          onTap: (){
            showTripExtraDetails(context, "Trip Details");
          },
          child :Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                  child: Row(
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.directions_bus,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showTripExtraDetails(context, "Trip Details");
                          },
                        ),

                        Text(
                            " Trip Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 16,
                            )
                        ),

                        Container(
                          margin: const EdgeInsets.only(left: 125.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showTripExtraDetails(context, "Trip Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ),
        ),

        GestureDetector(
          onTap: (){
            showDriverExtraDetails(context, "Driver Details");
          },
          child :
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.start,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.people,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDriverExtraDetails(context, "Driver Details");
                          },
                        ),

                        Text(
                            " Driver Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )
                        ),

                        Container(
                          margin: const EdgeInsets.only(left: 118.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDriverExtraDetails(context, "Driver Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ),

        GestureDetector(
          onTap: (){
            showDispatchExtraDetails(context, "Dispatch Details");
          },
          child :
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.start,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.access_time,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDispatchExtraDetails(context, "Dispatch Details");
                          },
                        ),

                        Text(
                            " Dispatch Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )
                        ),

                        Container(
                          margin: const EdgeInsets.only(left: 94.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDispatchExtraDetails(context, "Dispatch Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ),

      ],
    );

  }

  void showTripExtraDetails(BuildContext context, String title) {
    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " DOJ : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripOpenDate} to ${tripCloseDate} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Trip Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: routeAttendancePoint != null ? routeAttendancePoint.toString() : "-",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Trip Route Volume : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${routeTotalLiter} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Booking No : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,

                    )
                ),

                new TextSpan(
                    text: tripBookref != null ? tripBookref.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Group Service : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${vehicle_Group} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Opening KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripOpeningKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closing KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripClosingKM.toString() != 'null' ? tripClosingKM.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Usage KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripUsageKM.toString() != 'null' ? tripUsageKM.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),


        ],
      ),

    ).show();
  }

  void showDriverExtraDetails(BuildContext context, String title) {
    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripFristDriverName} / ${tripFristDriverMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 2 : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:   tripSecDriverName.toString() != 'null null'  ? tripSecDriverName.toString() +"/"+ tripSecDriverMobile.toString()  : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Cleaner : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripCleanerName.toString() != 'null null' ? tripCleanerName.toString() +"/"+ tripCleanerMobile.toString()  : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 1 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripFristDriverRoutePoint != null ? tripFristDriverRoutePoint.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 2 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:  tripSecDriverRoutePoint != null ? tripSecDriverRoutePoint.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Cleaner Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripCleanerRoutePoint != null ? tripCleanerRoutePoint.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  void showDispatchExtraDetails(BuildContext context, String title) {
    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedBy} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedLocation}  ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedByTime} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: closedBy != null ? closedBy.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: cloesdLocation != null ? cloesdLocation.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:  closedByTime != null ? closedByTime.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Route Remark : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: Remark != null ? Remark.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }



}




