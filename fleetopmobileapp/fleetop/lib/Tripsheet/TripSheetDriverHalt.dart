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

class TripSheetDriverHalt extends StatefulWidget {

  final Function tripsheetShowData;

  TripSheetDriverHalt({this.tripsheetShowData, this.tripsheetId});
  final int tripsheetId;

  @override
  _TripSheetDriverHaltState createState() => _TripSheetDriverHaltState();
}

class _TripSheetDriverHaltState extends State<TripSheetDriverHalt> {
  bool showTripDetails = false;
  bool showdriverHaltDetails = false;
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
  String tripFristDriverId;
  String tripFristDriverName;
  String tripFristDriverMobile;
  String tripSecDriverId;
  String tripSecDriverName;
  String tripSecDriverMobile;
  String tripCleanerId;
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

  TextEditingController driverHalt = new TextEditingController();
  TextEditingController fromDate = new TextEditingController();
  TextEditingController toDate = new TextEditingController();
  TextEditingController haltAmount = new TextEditingController();
  TextEditingController haltReason = new TextEditingController();
  TextEditingController haltBy = new TextEditingController();
  TextEditingController haltPlace = new TextEditingController();

  Map tripList = Map();
  List driverHaltList = List();
  List driverNameData = List();
  Map driverNameList = Map();

  List driverDropDownList = List();
  List finalDriverDropDownList = List();
  List driverDropDownData = List();
  final format = DateFormat("dd-MM-yyyy");

  var defaultSelectedDriver = "0";

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
    var response = await ApiCall.getDataWithoutLoader(
        URI.ADD_DRIVER_HALT_TRIPSHEET, data, URI.LIVE_URI);

    tripList = response['TripSheet'];
    driverHaltList = response['TripSheetHalt'];

    setState(() {
      tripsheetId = tripId;
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
      tripFristDriverId = tripList['tripFristDriverID'].toString();
      tripFristDriverName = tripList['tripFristDriverName'];
      tripFristDriverMobile = tripList['tripFristDriverMobile'];
      tripSecDriverId = tripList['tripSecDriverID'].toString();
      tripSecDriverName = tripList['tripSecDriverName'];
      tripSecDriverMobile = tripList['tripSecDriverMobile'];
      tripCleanerId = tripList['tripCleanerID'].toString();
      tripCleanerName = tripList['tripCleanerName'];
      tripCleanerMobile = tripList['tripCleanerMobile'];
      tripOpeningKM = tripList['tripOpeningKM'].toString();
      tripClosingKM = tripList['tripClosingKM'].toString();
      dispatchedBy = tripList['dispatchedBy'];
      dispatchedLocation = tripList['dispatchedLocation'];
      dispatchedByTime = tripList['dispatchedByTime'];
      haltBy.text = response['haltBy'];
      haltPlace.text = response['place'];
      haltPaidById = response['haltPaidById'].toString();
      haltPlaceId = response['placeId'].toString();
      tripUsageKM = tripList['tripUsageKM'].toString();
      tripFristDriverRoutePoint = tripList['tripFristDriverRoutePoint'].toString();
      tripSecDriverRoutePoint = tripList['tripSecDriverRoutePoint'].toString();
      tripCleanerRoutePoint = tripList['tripCleanerRoutePoint'].toString();
      closedBy = tripList['closedBy'];
      cloesdLocation = tripList['cloesdLocation'];
      closedByTime = tripList['closedByTime'];
      loadType = tripList['loadTypeName'];
      totalPOD = tripList['noOfPOD'].toString();
      Remark = tripList['routeRemark'];
    });

    setState(() {
      driverDropDownList = [];
    });
    defaultSelectedDriver = "0";

    var objDriver0 = {
      "driverHaltId": "0",
      "driverHaltName": "Select Driver",
    };
    driverDropDownList.add(objDriver0);

    var objDriver1 = {
      "driverHaltId": tripFristDriverId,
      "driverHaltName": tripFristDriverName,
    };
    driverDropDownList.add(objDriver1);

    if(tripSecDriverId != "0"){
      var objDriver2 = {
        "driverHaltId": tripSecDriverId,
        "driverHaltName": tripSecDriverName,
      };
      driverDropDownList.add(objDriver2);
    }

    if(tripCleanerId != "0"){
      var objCleaner = {
        "driverHaltId": tripCleanerId,
        "driverHaltName": tripCleanerName,
      };
      driverDropDownList.add(objCleaner);
    }
      setState(() {
        driverDropDownList;
      });
  }

  Future addDriverHalt() async {
    if (!fieldValidation()) {
      return;
    } else {

      var saveDriverHaltData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripsheetId': tripsheetId.toString(),
        'driverHaltId': defaultSelectedDriver,
        'fromDate': fromDate.text,
        'toDate': toDate.text,
        'haltAmount': haltAmount.text,
        'haltReason': haltReason.text,
        'haltPaidById': haltPaidById,
        'haltPlaceId': haltPlaceId,
      };

      var response = await ApiCall.getDataFromApi(
          URI.SAVE_DRIVER_HALT_TRIPSHEET, saveDriverHaltData, URI.LIVE_URI, context);

      FlutterAlert.onSuccessAlert(
          context, " Tripsheet DriverHalt Added Successfully !", " Tripsheet DriverHalt ");
      refreshData();
      getTripsheetShowData(tripsheetId);
    }

  }

  bool fieldValidation() {

    if (defaultSelectedDriver == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter Driver !", "Error");
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
    if (haltAmount.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select DriverHalt  Amount !", "Error");
      return false;
    }
    if (haltReason.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select DriverHalt  Reason !", "Error");
      return false;
    }

    return true;
  }

  refreshData() {
    driverHaltId = '';
    driverHalt.text = '';
    fromDate.text = '';
    toDate.text = '';
    haltAmount.text = '';
    haltReason.text = '';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.amber,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Add DriverHalt",
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


                        SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "DriverHalt Details ",
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
                                          showdriverHaltDetails ? "Close" : "Open",
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
                                            showdriverHaltDetails =
                                            !showdriverHaltDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showdriverHaltDetails,
                            child:Column(
                              children: <Widget>[

                                if(driverHaltList != null)
                                  for(int i = 0;i<driverHaltList.length;i++)
                                    showDriverHaltPaymentDetails(driverHaltList[i],context),

                              ],
                            )
                        ),
//

                        SizedBox(height: 60),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Add Driver Halt Details ",
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

                         /*Container(
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
                                      controller: driverHalt,
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
                                          labelText: 'Driver Halt Name'),
                                    ),
                                    suggestionsCallback:
                                        (pattern) async {
                                      return await getDriverNameSuggestions(
                                          pattern);
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
                                          driverHalt.text =
                                      suggestion['driverName'];
                                          driverHaltId = suggestion['driver_id'];
                                    },
                                  ),
                                ],
                              ),
                            ),
                          ),*/

                        SizedBox(height: 15),
                        Container(
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
                                              "Driver Halt List"),
                                          value:
                                          defaultSelectedDriver,
                                          isExpanded: true,
                                          onChanged:
                                              (String newValue) {
                                            setState(() {
                                              defaultSelectedDriver =
                                                  newValue;
                                            });
                                          },
                                          items: driverDropDownList
                                              .map((item) {
                                            return new DropdownMenuItem(
                                              child: new Text(
                                                  item[
                                                  'driverHaltName'],
                                                  style: TextStyle(
                                                      color: Colors
                                                          .black,
                                                      fontSize:
                                                      20.0,
                                                      fontFamily:
                                                      "WorkSansBold")),
                                              value: item[
                                              'driverHaltId']
                                                  .toString(),
                                            );
                                          }).toList(),
                                        ))),
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
                                      firstDate: DateTime.now(),
                                      initialDate: currentValue ??
                                          DateTime.now(),
                                      lastDate: DateTime(2021));
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
                        ),


                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 1.0, left: 10),
                            child: Container(
                              child: TextField(
                                maxLines: 1,
                                controller: haltAmount,
                                keyboardType: TextInputType.number,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                  labelText: 'Halt Per Day Bata Amount',
                                  hintText: "",
                                  hintStyle: TextStyle(
                                      color: Colors.black),
                                  border: OutlineInputBorder(
                                      borderRadius:
                                      BorderRadius.circular(
                                          5.0)),
                                  icon: Icon(
                                    Icons.credit_card,
                                    color: Colors.redAccent,
                                  ),

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
                                controller: haltReason,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    labelText: 'Halt Reason',
                                    hintText: "",
                                    hintStyle: TextStyle(
                                        color: Colors.black),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.library_books,
                                      color: Colors.amber,
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
                              child: TextField (
                                enabled: false,
                                maxLines: 1,
                                controller: haltBy,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    labelText: 'Halt By',
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

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child: Container(
                              child: TextField(
                                enabled: false,
                                maxLines: 1,
                                controller: haltPlace,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    labelText: 'Halt Place',
                                    hintText: "",
                                    hintStyle: TextStyle(
                                        color: Colors.black),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.place,
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
                            child:
                            new  RaisedButton(
                              padding: const EdgeInsets.all(8.0),
                              textColor: Colors.white,
                              color: Colors.pink,
                              onPressed: addDriverHalt,
                              child: Padding(
                                padding:
                                const EdgeInsets.symmetric(
                                    vertical: 2.0,
                                    horizontal: 15.0),
                                child: Text(
                                  "Add DriverHalt",
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


  Widget showDriverHaltPaymentDetails(data,context)
  {
    print("ola... ${data} ");
    Map driverHaltData = new Map();
    driverHaltData = data;
    if(driverHaltList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
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
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.spaceBetween,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDriverHaltPaidDetails(context, driverHaltData, "DriverHalt Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " HaltAmount : ",
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


                        new IconButton(
                          icon: new Icon(
                            Icons.delete,
                            color: Colors.redAccent,
                          ),
                          onPressed: () {
                            deleteHaltDetails(driverHaltData);
                          },
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ],
      );
    }
    else
    {
      return Container();
    }
  }

  void showDriverHaltPaidDetails(BuildContext context, Map data , String title) {

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
                    )
                ),

                new TextSpan(
                    text: " ${data['driver_NAME']} ",
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
                    text: " Place : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['halt_PLACE']} ",
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
                    text: " Paid By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['halt_PAIDBY']} ",
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
                    text: " Amount : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['halt_AMOUNT']} ",
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
                    text: " Halt Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: (DateFormat("dd-MM-yyyy").format( new DateTime.fromMicrosecondsSinceEpoch(data['halt_DATE_FROM_ON'] * 1000)) ).toString() +"  to  "+
                        (DateFormat("dd-MM-yyyy").format( new DateTime.fromMicrosecondsSinceEpoch(data['halt_DATE_TO_ON'] * 1000)) ).toString(),
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

  Future<bool> deleteHaltDetails(driverHaltData){
    Alert(
      context: context,
      type: AlertType.info,
      title: "Income Info",
      desc: "Do you want to Delete Driver Halt Details ?",
      buttons: [
        DialogButton(
          child: Text(
            "Yes",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => deleteRequest(driverHaltData),
          gradient: LinearGradient(colors: [
            Colors.green,
            Colors.deepPurple
          ]),
        ),
        DialogButton(
          child: Text(
            "No",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => Navigator.pop(context),
          gradient: LinearGradient(colors: [
            Colors.red,
            Colors.black
          ]),
        )
      ],
    ).show();
  }

  deleteRequest(driverHaltData) async{
    Navigator.pop(context);

    var deletedriverHaltData = {
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'tripsheetId': tripsheetId.toString(),
      'driverHaltId' : '${driverHaltData['dhid']}',
    };

    var response =  await ApiCall.getDataFromApi(
        URI.REMOVE_DRIVER_HALT_TRIPSHEET, deletedriverHaltData, URI.LIVE_URI, context);

    FlutterAlert.onSuccessAlert(
        context, " Tripsheet DriverHalt Deleted Successfully !", " Tripsheet DriverHalt ");

    getTripsheetShowData(tripsheetId);
  }

  Future<List> getDriverNameSuggestions(String query) async {
    getDriverNameDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(driverNameData.length, (index) {
      return driverNameData[index];
    });
  }

  getDriverNameDetails(String str) async {
    driverNameList = new Map();
    driverHaltId = '';

    setState(() {
      driverNameData = [];
    });

    if (str != null && str.length >= 2) {
      var data = {'userId' : '135' ,'companyId': '14', 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.DRIVER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["driverList"] != null) {
          print(response["driverList"].length);
          for (int i = 0; i < response["driverList"].length; i++) {
            var obj = {
              "driver_id": response['driverList'][i]['driver_id'].toString(),
              "driverName":
              response['driverList'][i]['driver_empnumber'].toString() +'-'+response['driverList'][i]['driver_firstname'].toString() +' '+ response['driverList'][i]['driver_Lastname'].toString()
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


/*Widget tripDetailsInfoContainer() {
    *//*var routeName =
    (fueldata['fuelRouteName'] != null ? fueldata['fuelRouteName'] : "");*//*
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

