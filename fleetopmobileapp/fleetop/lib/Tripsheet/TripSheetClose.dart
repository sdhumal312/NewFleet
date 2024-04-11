import 'dart:convert';
import 'dart:io';

//import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
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
import 'TripsheetShowAfterClose.dart';

class TripSheetClose extends StatefulWidget {
  TripSheetClose({Key key, this.tripsheetId}) : super(key: key);
  final int tripsheetId;

  @override
  _TripSheetCloseState createState() => _TripSheetCloseState();
}

class _TripSheetCloseState extends State<TripSheetClose> {
  bool navigateToSearch = false;
  bool accountCloseBySearch = false;
  bool showTripDetails = false;
  bool showRoutePointDetails = false;
  bool showTime = false;
  bool allowGPSIntegration = false;
  bool showTripGpsClosingKM = false;
  bool showGpsCloseLocation = false;
  bool showClosingKm = true;
  bool showGPSOdometerMessage = false;
  String companyId;
  String email;
  String userId;
  int i = 0;
  int vehicleId;
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
  String incomeNameId;
  String driverHaltId;
  String haltPaidById;
  String haltPlaceId;
  String tripUsageKM;
  String tripFristDriverRoutePoint;
  String tripSecDriverRoutePoint;
  String tripCleanerRoutePoint;
  String closedBy;
  String cloesdLocation;
  String closedByTime;
  String loadType;
  String totalPOD;
  String Remark;
  String privousTime = '';

  TextEditingController tripGpsClosingKM = new TextEditingController();
  TextEditingController gpsCloseLocation = new TextEditingController();
  TextEditingController closingKm = new TextEditingController();
  TextEditingController amount = new TextEditingController();
  TextEditingController refNo = new TextEditingController();
  TextEditingController tripClosingDate = new TextEditingController();
  TextEditingController tripCloseTime = new TextEditingController();
  TextEditingController tripEndDiesel = new TextEditingController();

  Map tripList = Map();
  Map finalTripDto = Map();
  List payToData = List();
  List paidByData = List();
  List routePointList = List();
  var finalRouteList;

  double finalAmount;
  int finalClosingKm;
  int tripsheetOpeningKM;
  int tripsheetExpectedOdometer;
  int maxOdameter;
  int minAllowedKM = 0;
  int maxAllowedKM = 0;
  String odometerRange = '';
  bool meterNotWorking = false;
  bool showBalanceFuel = false;
  int tripClosingKMStatusId = 0;
  String reference = '0';
  var payToValue = "1";
  var payByValue;
  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");

  var gpsConfig;
  var gpsObject;

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

    print("companyId....$companyId");

    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'tripsheetId': tripId.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.ADD_CLOSE_TRIPSHEET, data, URI.LIVE_URI, context);

    tripList = response['TripSheet'];
    finalTripDto = response['finalTripDto'];
    routePointList = response['DisplayPoint'];
    allowGPSIntegration = response['allowGPSIntegration'];
    gpsConfig = json.decode(response['gpsConfig']);

    if (response['gpsObject'] != null) {
      gpsObject = json.decode(response['gpsObject']);
    }

    print("gpsConfig :: $gpsConfig");
    print("gpsObject :: $gpsObject");

    //print("tripList :: $tripList");
    //print("routePointList..... $routePointList");

    setState(() {
      tripsheetId = tripId;
      vehicleId = tripList['vid'];
      createdDate = finalTripDto['created'];
      tripsheetNumber = tripList['tripSheetNumber'].toString();
      vehNumber = tripList['vehicle_registration'];
      route = tripList['routeName'];
      tripOpenDate = finalTripDto['tripOpenDate'];
      tripCloseDate = finalTripDto['closetripDate'];
      vehicle_Group = tripList['vehicle_Group'];
      tripBookref = tripList['tripBookref'];
      routeAttendancePoint = tripList['routeAttendancePoint'].toString();
      routeTotalLiter = tripList['routeTotalLiter'].toString();
      tripFristDriverName = tripList['tripFristDriverName'];
      tripFristDriverMobile = tripList['tripFristDriverMobile'];
      tripSecDriverName = tripList['tripSecDriverName'];
      tripSecDriverMobile = tripList['tripSecDriverMobile'];
      tripCleanerName = tripList['tripCleanerName'];
      tripCleanerMobile = tripList['tripCleanerMobile'];
      tripOpeningKM = tripList['tripOpeningKM'].toString();
      tripClosingKM = tripList['tripClosingKM'].toString();
      dispatchedBy = tripList['dispatchedBy'];
      dispatchedLocation = tripList['dispatchedLocation'];
      dispatchedByTime = finalTripDto['dispatchedByTime'];
      closingKm.text = response['TripSheetKm'].toString();
      finalAmount = tripList['tripTotalAdvance'] != null
          ? tripList['tripTotalAdvance']
          : 0 - tripList['tripTotalexpense'] != null
              ? tripList['tripTotalexpense']
              : 0;
      amount.text = finalAmount.toString();
      refNo.text = reference;
      tripClosingDate.text = finalTripDto['closetripDate'].toString();
      payByValue = tripList['tripFristDriverID'].toString();
      tripsheetOpeningKM = tripList['tripOpeningKM'];
      tripsheetExpectedOdometer = response['ExpectedOdameterKm'];
      maxOdameter = tripsheetOpeningKM + tripsheetExpectedOdometer;
      showTime = response['ShowTime'];
      tripUsageKM = tripList['tripUsageKM'].toString();
      tripFristDriverRoutePoint =
          tripList['tripFristDriverRoutePoint'].toString();
      tripSecDriverRoutePoint = tripList['tripSecDriverRoutePoint'].toString();
      tripCleanerRoutePoint = tripList['tripCleanerRoutePoint'].toString();
      closedBy = tripList['closedBy'];
      cloesdLocation = tripList['cloesdLocation'];
      closedByTime = tripList['closedByTime'];
      loadType = tripList['loadTypeName'];
      totalPOD = tripList['noOfPOD'].toString();
      Remark = tripList['routeRemark'];

      minAllowedKM = response['minAllowedKM'];
      maxAllowedKM = response['maxAllowedKM'];
      odometerRange = "Odometer Range Is ${minAllowedKM} to ${maxAllowedKM}";
      showBalanceFuel = response['configuration']['tripOpenCloseFuelRequired'];
    });

    print("closeDate :: ${finalTripDto['closetripDate'].toString()}");
    print("tripOpenDate :: ${tripOpenDate}");
    print("tripCloseDate :: ${tripCloseDate}");

    var payToObj1 = {
      "payToId": '1',
      "payToName": 'OFFICE',
    };
    payToData.add(payToObj1);

    var payToObj2 = {
      "payToId": '2',
      "payToName": 'DRIVER',
    };
    payToData.add(payToObj2);

    setState(() {
      payToData;
    });

    var payByObj1 = {
      "payById": tripList['tripFristDriverID'].toString(),
      "payByName": tripList['tripFristDriverName'],
    };
    paidByData.add(payByObj1);

    var payByObj2 = {
      "payById": tripList['tripSecDriverID'].toString(),
      "payByName": tripList['tripSecDriverName'],
    };
    paidByData.add(payByObj2);

    setState(() {
      paidByData;
    });

    if (gpsObject != null) {
      setTripClosingDataforGps(gpsConfig, gpsObject);
    }
  }

  setTripClosingDataforGps(var configuration, var gpsData) {
    print("configuration...... :: $configuration");
    print("gpsData..... :: $gpsData");

    if (gpsObject != null && gpsObject != '') {
      if ((configuration['allowGPSIntegration'] == true ||
              allowGPSIntegration == 'true') &&
          configuration['gpsFlavor'] != 1) {
        if (gpsData['VEHICLE_TOTAL_KM'] != null &&
            gpsData['VEHICLE_GPS_WORKING']) {
          showTripGpsClosingKM = true;
          tripGpsClosingKM.text = gpsData['VEHICLE_TOTAL_KM'].toString();

          if (gpsData['GPS_LOCATION'] != null &&
              gpsData['GPS_LOCATION'].trim() != '') {
            showGpsCloseLocation = true;
            gpsCloseLocation.text = gpsData['GPS_LOCATION'];
          }

          if (configuration['gpsFlavor'] == 2) {
            showClosingKm = false;
          }
        } else {
          tripGpsClosingKM.text = '0';
          showGPSOdometerMessage = true;
        }
      }
    }
  }

  Future tripsheetClose() async {
    if (!fieldValidation()) {
      return;
    } else {
      final jsonEncoder = JsonEncoder();

      if (tripGpsClosingKM.text == '') {
        tripGpsClosingKM.text = '0';
      }

      if (tripEndDiesel.text == '') {
        tripEndDiesel.text = '0';
      }

      var saveTripSheetCloseData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripsheetId': tripsheetId.toString(),
        'tripClosingKMStatusId': tripClosingKMStatusId.toString(),
        'tripClosingKM': closingKm.text,
        'closeTripAmount': amount.text,
        'closeTripRefNo': refNo.text,
        'tripClosingDate': tripClosingDate.text,
        'tripCloseTime': tripCloseTime.text,
        'closeTripStatusId': payToValue,
        'closeTripNameById': payByValue,
        'tripGPSClosingKM': tripGpsClosingKM.text,
        'gpsCloseLocation': gpsCloseLocation.text,
        'tripEndDiesel': tripEndDiesel.text,
        'routePointList': jsonEncoder.convert(routePointList),
      };

      var response = await ApiCall.getDataFromApi(URI.SAVE_CLOSE_TRIPSHEET,
          saveTripSheetCloseData, URI.LIVE_URI, context);

      if (response != null) {
        Navigator.push(
            context,
            new MaterialPageRoute(
                builder: (context) => new TripsheetShow(
                    tripsheetId: tripsheetId,
                    navigateToSearch: false,
                    accountCloseBySearch: false)));
      }
      /*FlutterAlert.onSuccessAlert(context, " Tripsheet Closed Successfully !", " Tripsheet Close ");*/
      //refreshData();
      //getTripsheetShowData(tripsheetId);
    }
  }

  bool fieldValidation() {
    bool validateOdometer = true;

    if ((closingKm.text == '')) {
      FlutterAlert.onErrorAlert(
          context,
          "Please Enter Closing KM between ${minAllowedKM} & ${maxAllowedKM} !",
          "Error");
      return false;
    }

    if (meterNotWorking) {
      validateOdometer = false;
    }

    if (validateOdometer) {
      if (num.parse(closingKm.text) < minAllowedKM ||
          num.parse(closingKm.text) > maxAllowedKM) {
        FlutterAlert.onErrorAlert(
            context,
            "Please Enter Odometer in Range Of :" +
                minAllowedKM.toString() +
                " - " +
                maxAllowedKM.toString(),
            "Error");
        return false;
      }
    }

    // if (amount.text == '') {
    //   FlutterAlert.onErrorAlert(context, "Please Select Amount !", "Error");
    //   return false;
    // }

    // if (refNo.text == '') {
    //   FlutterAlert.onErrorAlert(
    //       context, "Please Select Reference Number !", "Error");
    //   return false;
    // }

    if (tripClosingDate.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Trip Closing Date !", "Error");
      return false;
    }

    if (showTime) {
      if (tripCloseTime.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Trip Closing Time !", "Error");
        return false;
      }
    }

    return true;
  }

  refreshData() {}

  // showTripGpsClosingKM
  // showGpsCloseLocation
  // showClosingKm
  // showGPSOdometerMessage

  gpsData() async {
    var gpsData = {
      'vehicleId': vehicleId.toString(),
      'companyId': companyId,
      'dispatchedByTime': tripClosingDate.text,
      'dispatchTime': tripCloseTime.text,
      'fromTripSheetClose': 'true',
      'tripSheetId': tripsheetId.toString(),
    };

    var data = await ApiCall.getDataFromApi(
        URI.VEHICLE_GPS_DATA_AT_TIME, gpsData, URI.LIVE_URI, context);

    print("data...$data");
    if (data != null) {
      print("A...${data['VEHICLE_TOTAL_KM']}");
      print("B...${data['VEHICLE_GPS_WORKING']}");

      if (data['VEHICLE_TOTAL_KM'] != null && data['VEHICLE_GPS_WORKING']) {
        print("1...");
        setState(() {
          tripGpsClosingKM.text = data['VEHICLE_TOTAL_KM'].toString();
        });
      } else {
        print("2...");
        setState(() {
          showTripGpsClosingKM = false;
          tripGpsClosingKM.text = '0';
          showClosingKm = true;
          showGPSOdometerMessage = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    if (tripCloseTime.text != privousTime) {
      privousTime = tripCloseTime.text;
      if (allowGPSIntegration == true) {
        gpsData();
      }
    }

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.amber,
        iconTheme: IconThemeData(
          color: Colors.white,
        ),
        centerTitle: true,
        title: Text(
          "Close Tripsheet",
          style: new TextStyle(
            fontSize: 22,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
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
                      )),
                  new TextSpan(
                      text: " ${createdDate} ",
                      style: new TextStyle(
                        color: Colors.blue,
                        fontWeight: FontWeight.w700,
                        fontSize: 17,
                      )),
                ],
              ),
            ),

            SizedBox(height: 25),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("Trip Number : TS - ${tripsheetNumber}",
                    style: TextStyle(
                        fontWeight: FontWeight.w700,
                        color: Colors.red,
                        fontSize: 19)),
              ),
            ),

            SizedBox(height: 5),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("${vehNumber}",
                    style: TextStyle(
                        fontWeight: FontWeight.w700,
                        color: Colors.green,
                        fontSize: 15)),
              ),
            ),

            SizedBox(height: 5),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("${route}",
                    style: TextStyle(
                        fontWeight: FontWeight.w700,
                        color: Colors.green,
                        fontSize: 15)),
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
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            " Tripsheet Details ",
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
                                  fontWeight: FontWeight.w500),
                            ),
                            /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                            onPressed: () => {
                              setState(() {
                                showTripDetails = !showTripDetails;
                              })
                            },
                          )
                        ])),
              ),
            ),
            Visibility(
                visible: showTripDetails,
                child: Stack(
                  children: <Widget>[
                    showTripInfo(context),
                    /*ola(context),*/
                  ],
                )),

            SizedBox(height: 15),
            Card(
              color: Colors.pink,
              child: Container(
                height: 50,
                child: Padding(
                    padding: const EdgeInsets.only(left: 10, right: 10),
                    child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            "RoutePoint Details ",
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
                              showRoutePointDetails ? "Close" : "Open",
                              style: TextStyle(
                                  color: Colors.purpleAccent,
                                  fontSize: 20,
                                  fontWeight: FontWeight.w500),
                            ),
                            /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                            onPressed: () => {
                              setState(() {
                                showRoutePointDetails = !showRoutePointDetails;
                              })
                            },
                          )
                        ])),
              ),
            ),
            Visibility(
                visible: showRoutePointDetails,
                child: Column(
                  children: <Widget>[
                    if (routePointList != null)
                      for (int i = 0; i < routePointList.length; i++)
                        showDriverRoutePointDetails(routePointList[i], context),
                  ],
                )),
//

            SizedBox(height: 60),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("Office Use ",
                    style: new TextStyle(
                      color: Colors.blueAccent,
                      fontWeight: FontWeight.w700,
                      fontSize: 15,
                    )),
              ),
            ),
            Container(
              height: 2.0,
              width: MediaQuery.of(context).size.width,
              color: Colors.blueAccent,
              margin: const EdgeInsets.only(left: 80.0, right: 80.0),
            ),

            SizedBox(height: 15),
            Visibility(
              visible: showBalanceFuel,
              child: Container(
                child: Padding(
                  padding: const EdgeInsets.only(top: 5.0, left: 10),
                  child: Container(
                    child: TextField(
                      maxLines: 1,
                      controller: tripEndDiesel,
                      keyboardType: TextInputType.number,
                      style: TextStyle(color: Colors.black),
                      decoration: InputDecoration(
                          labelText: 'Balance Fuel',
                          hintText: "",
                          hintStyle: TextStyle(color: Colors.black),
                          border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5.0)),
                          icon: Icon(
                            Icons.photo_library,
                            color: Colors.pink,
                          )),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 10),
            Visibility(
              visible: showTripGpsClosingKM,
              child: Container(
                child: Padding(
                  padding: const EdgeInsets.only(top: 1.0, left: 10),
                  child: Container(
                    child: TextField(
                      maxLines: 1,
                      controller: tripGpsClosingKM,
                      enabled: false,
                      keyboardType: TextInputType.number,
                      style: TextStyle(color: Colors.black),
                      decoration: InputDecoration(
                        labelText: 'GPS Closing KM',
                        hintText: "",
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.gps_fixed,
                          color: Colors.red,
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Visibility(
              visible: showGpsCloseLocation,
              child: Container(
                child: Padding(
                  padding: const EdgeInsets.only(top: 1.0, left: 10),
                  child: Container(
                    child: TextField(
                      maxLines: 3,
                      enabled: false,
                      controller: gpsCloseLocation,
                      keyboardType: TextInputType.number,
                      style: TextStyle(color: Colors.black),
                      decoration: InputDecoration(
                        labelText: 'GPS Closing Location',
                        hintText: "",
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.location_on,
                          color: Colors.purple,
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Visibility(
              visible: showClosingKm,
              child: Align(
                alignment: Alignment.centerRight,
                child: Container(
                  child: Icon(Icons.stars, color: Colors.red, size: 12),
                ),
              ),
            ),
            Visibility(
              visible: showClosingKm,
              child: Container(
                child: Padding(
                  padding: const EdgeInsets.only(top: 1.0, left: 10),
                  child: Container(
                    child: TextField(
                      maxLines: 1,
                      controller: closingKm,
                      keyboardType: TextInputType.number,
                      style: TextStyle(color: Colors.black),
                      decoration: InputDecoration(
                        labelText: 'Closing KM',
                        hintText: "",
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.shutter_speed,
                          color: Colors.amber,
                        ),
                      ),
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
                  child: Text("GPS NOT WORKING/ATTACHED !",
                      style: new TextStyle(
                        color: Colors.red,
                        fontWeight: FontWeight.w700,
                        fontSize: 15,
                      )),
                ),
              ),
            ),

            SizedBox(height: 5),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text(odometerRange,
                    style: new TextStyle(
                      color: Colors.red,
                      fontWeight: FontWeight.w700,
                      fontSize: 15,
                    )),
              ),
            ),

            SizedBox(height: 5),
            CheckboxListTile(
              title: Text("Meter Not Working"),
              value: meterNotWorking,
              onChanged: (newValue) {
                if (!meterNotWorking) {
                  setState(() {
                    tripClosingKMStatusId = 1;
                    meterNotWorking = true;
                  });
                } else {
                  setState(() {
                    tripClosingKMStatusId = 0;
                    meterNotWorking = false;
                  });
                }
              },
              controlAffinity:
                  ListTileControlAffinity.leading, //  <-- leading Checkbox
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 1.0, left: 10),
                child: Container(
                  child: TextField(
                    maxLines: 1,
                    controller: amount,
                    keyboardType: TextInputType.number,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                      labelText: 'Amount',
                      hintText: "",
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.credit_card,
                        color: Colors.greenAccent,
                      ),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Container(
              height: 70,
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: DropdownButtonHideUnderline(
                    child: Card(
                        elevation: 0.5,
                        color: Colors.white,
                        child: Container(
                            padding: EdgeInsets.all(17),
                            child: DropdownButton<String>(
                              hint: Text("Pay To"),
                              value: payToValue,
                              isExpanded: true,
                              onChanged: (String newValue) {
                                setState(() {
                                  payToValue = newValue;
                                });
                              },
                              items: payToData.map((item) {
                                return new DropdownMenuItem(
                                  child: new Text(item['payToName'],
                                      style: TextStyle(
                                          color: Colors.black,
                                          fontSize: 20.0,
                                          fontFamily: "WorkSansBold")),
                                  value: item['payToId'].toString(),
                                );
                              }).toList(),
                            ))),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Container(
              height: 70,
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: DropdownButtonHideUnderline(
                    child: Card(
                        elevation: 0.5,
                        color: Colors.white,
                        child: Container(
                            padding: EdgeInsets.all(17),
                            child: DropdownButton<String>(
                              hint: Text("Pay By"),
                              value: payByValue,
                              isExpanded: true,
                              onChanged: (String newValue) {
                                setState(() {
                                  payByValue = newValue;
                                });
                              },
                              items: paidByData.map((item) {
                                return new DropdownMenuItem(
                                  child: new Text(item['payByName'],
                                      style: TextStyle(
                                          color: Colors.black,
                                          fontSize: 20.0,
                                          fontFamily: "WorkSansBold")),
                                  value: item['payById'].toString(),
                                );
                              }).toList(),
                            ))),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 1.0, left: 10),
                child: Container(
                  child: TextField(
                    maxLines: 1,
                    controller: refNo,
                    keyboardType: TextInputType.number,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                      labelText: 'Reference Number',
                      hintText: "",
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.content_paste,
                        color: Colors.deepOrange,
                      ),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: DateTimeField(
                    format: format,
                    controller: tripClosingDate,
                    enabled: true,
                    initialValue: DateTime.now(),
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        hintText: "",
                        labelText: "tripClosingDate*",
                        hintStyle: TextStyle(color: Color(0xff493240)),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.date_range,
                          color: Colors.blue,
                        )),
                    //format: format,
                    onShowPicker: (context, currentValue) {
                      return showDatePicker(
                          context: context,
                          firstDate: DateTime.now(),
                          initialDate: currentValue ?? DateTime.now(),
                          lastDate: DateTime.now());
                    },
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: DateTimeField(
                    format: timeFormat,
                    controller: tripCloseTime,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        hintText: "Select Time",
                        labelText: "Select Time",
                        hintStyle: TextStyle(color: Color(0xff493240)),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.access_time,
                          color: Colors.lightGreenAccent,
                        )),
                    onShowPicker: (context, currentValue) async {
                      final time = await showTimePicker(
                        context: context,
                        initialTime: TimeOfDay.fromDateTime(
                            currentValue ?? DateTime.now()),
                        builder: (BuildContext context, Widget child) {
                          return MediaQuery(
                            data: MediaQuery.of(context)
                                .copyWith(alwaysUse24HourFormat: true),
                            child: child,
                          );
                        },
                      );
                      setState(() {
                        tripCloseTime.text = tripCloseTime.text;
                      });
                      return DateTimeField.convert(time);
                    },
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: new RaisedButton(
                  padding: const EdgeInsets.all(8.0),
                  textColor: Colors.white,
                  color: Colors.pink,
                  onPressed: tripsheetClose,
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 2.0, horizontal: 15.0),
                    child: Text(
                      "Close TripSheet",
                      style: TextStyle(
                          fontSize: 18,
                          color: Colors.white,
                          fontWeight: FontWeight.w500),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 25),
          ])))),
    );
  }

  Widget showDriverRoutePointDetails(data, context) {
    Map routePointData = new Map();
    routePointData = data;
    if (routePointList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showPointDetails(
                                context, routePointData, "RoutePoint Details");
                          },
                        ),
                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Driver : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )),
                              new TextSpan(
                                  text: " ${data['driver_NAME'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )),
                            ],
                          ),
                        ),
                      ])),
            ),
          ),
        ],
      );
    } else {
      return Container();
    }
  }

  void showPointDetails(BuildContext context, Map data, String title) {
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
                    text: " Driver Name : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['driver_NAME']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " TripRoute Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['trip_ROUTE_POINT']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Halt Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['halt_POINT']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Total : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['point_TOTAL']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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

  Widget showTripInfo(context) {
    return Column(
      children: <Widget>[
        GestureDetector(
          onTap: () {
            showTripExtraDetails(context, "Trip Details");
          },
          child: Card(
            color: Colors.white70,
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
                  child: Row(children: [
                    new IconButton(
                      icon: new Icon(
                        Icons.directions_bus,
                        color: Colors.green,
                      ),
                      onPressed: () {
                        showTripExtraDetails(context, "Trip Details");
                      },
                    ),
                    Text(" Trip Details ",
                        style: new TextStyle(
                          color: Colors.purple,
                          fontWeight: FontWeight.w700,
                          fontSize: 16,
                        )),
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
                  ])),
            ),
          ),
        ),
        GestureDetector(
          onTap: () {
            showDriverExtraDetails(context, "Driver Details");
          },
          child: Card(
            color: Colors.white70,
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
                      mainAxisAlignment: MainAxisAlignment.start,
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
                        Text(" Driver Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )),
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
                      ])),
            ),
          ),
        ),
        GestureDetector(
          onTap: () {
            showDispatchExtraDetails(context, "Dispatch Details");
          },
          child: Card(
            color: Colors.white70,
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
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        new IconButton(
                          icon: new Icon(
                            Icons.access_time,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDispatchExtraDetails(
                                context, "Dispatch Details");
                          },
                        ),
                        Text(" Dispatch Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )),
                        Container(
                          margin: const EdgeInsets.only(left: 94.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDispatchExtraDetails(
                                  context, "Dispatch Details");
                            },
                          ),
                        ),
                      ])),
            ),
          ),
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
                    )),
                new TextSpan(
                    text: " ${tripOpenDate} to ${tripCloseDate} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: routeAttendancePoint != null
                        ? routeAttendancePoint.toString()
                        : "-",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${routeTotalLiter} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripBookref != null ? tripBookref.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${vehicle_Group} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${tripOpeningKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripClosingKM.toString() != 'null'
                        ? tripClosingKM.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripUsageKM.toString() != 'null'
                        ? tripUsageKM.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${tripFristDriverName} / ${tripFristDriverMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripSecDriverName.toString() != 'null null'
                        ? tripSecDriverName.toString() +
                            "/" +
                            tripSecDriverMobile.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripCleanerName.toString() != 'null null'
                        ? tripCleanerName.toString() +
                            "/" +
                            tripCleanerMobile.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripFristDriverRoutePoint != null
                        ? tripFristDriverRoutePoint.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripSecDriverRoutePoint != null
                        ? tripSecDriverRoutePoint.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripCleanerRoutePoint != null
                        ? tripCleanerRoutePoint.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${dispatchedBy} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${dispatchedLocation}  ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${dispatchedByTime} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: closedBy != null ? closedBy.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: cloesdLocation != null
                        ? cloesdLocation.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: closedByTime != null ? closedByTime.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: Remark != null ? Remark.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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

/*Widget tripDetailsInfoContainer() {
    */ /*var routeName =
    (fueldata['fuelRouteName'] != null ? fueldata['fuelRouteName'] : "");*/ /*
    return (Container(
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
            ],
          )),
    ));
  }*/

}

/*Widget showDriverHaltPaymentTotal(data,context)
{
    return  Column(
      children: <Widget>[
        Container(
          height: 50,
          child: Padding(
              padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
              child: Row(
                  mainAxisAlignment:
                  MainAxisAlignment.center,
                  children: [

                    RichText(
                      text: TextSpan(
                        children: <TextSpan>[
                          new TextSpan(
                              text: " Total : ",
                              style: new TextStyle(
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                                fontSize: 16,
                              )
                          ),

                          new TextSpan(
                              text: " \u20B9${data['halt_AMOUNT'].toString()} ",
                              style: new TextStyle(
                                color: Colors.purple,
                                fontWeight: FontWeight.w700,
                                fontSize: 16,
                              )
                          ),
                        ],
                      ),
                    ),

                  ]
              )
          ),
        ),
        Container(
          height: 2.0,
          width: MediaQuery.of(context).size.width,
          color: Colors.black,
          margin: const EdgeInsets.only(left: 10.0, right: 10.0),
        ),

      ],
    );

}*/
