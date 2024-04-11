import 'dart:convert';
import 'dart:io';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:location/location.dart';

import '../fleetopuriconstant.dart';
import 'package:fleetop/Driver/GradientCard.dart';
import 'TripsheetShow.dart';
import 'TripsheetShowAccountClose.dart';

class SearchTripsheetByVehicle extends KFDrawerContent {
  final List fuelDataList;
  SearchTripsheetByVehicle({this.fuelDataList, Key key});

  @override
  _SearchTripsheetByVehicleState createState() =>
      _SearchTripsheetByVehicleState();
}

class _SearchTripsheetByVehicleState extends State<SearchTripsheetByVehicle>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;
  String vehicleId = '';

  List vehicleData = List();
  Map vehicleList = Map();
  List tripSheetList = List();

  TextEditingController vehicleName = new TextEditingController();

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
    email = prefs.getString("email");

    var data = {'companyId': companyId, 'userId': userId, 'email': email};
    checkAndSet();
  }

  checkAndSet() {
    if (widget.fuelDataList != null && widget.fuelDataList.isNotEmpty) {
      setState(() {
        tripSheetList = widget.fuelDataList;
      });
    }
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
      var tripDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vid': vehicleId,
      };

      var data = await ApiCall.getDataFromApi(
          URI.SEARCH_TRIPSHEET_BY_VEHICLE, tripDetails, URI.LIVE_URI, context);

      //print("output.....${data}");

      if (data != null) {
        if (data['tripSheet'] == null) {
          FlutterAlert.onInfoAlert(context, "Tripsheet Not Found !", "Info");
          setState(() {
            tripSheetList.length = 0;
          });
        } else if (data['tripSheet'] != null) {
          setState(() {
            tripSheetList = data['tripSheet'];
          });
        }

        print("output.....${tripSheetList.length}");
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool fieldValidation() {
    if (vehicleId == '' || vehicleId == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Valid Vehicle !", "Error");
      return false;
    }

    return true;
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Search Tripsheet By Vehicle'),
        backgroundColor: Colors.amber,
      ),
      // backgroundColor: AppTheme.white,
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
                                    Container(
                                      child: Padding(
                                        padding:
                                            const EdgeInsets.only(left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            SizedBox(
                                              height: 10.0,
                                            ),
                                            Align(
                                              alignment: Alignment.centerRight,
                                              child: Container(
                                                child: Icon(Icons.stars,
                                                    color: Colors.red,
                                                    size: 12),
                                              ),
                                            ),
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
                                                    hintText: 'Vehicle Number',
                                                    labelText:
                                                        'Vehicle Number'),
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
                                                vehicleId =
                                                    suggestion['vehicleId'];
                                              },
                                            ),
                                          ],
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
                                            color: Colors.amber,
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
                                                "Submit",
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
                                    SizedBox(height: 50),
                                    for (int i = 0;
                                        i < tripSheetList.length;
                                        i++)
                                      ExpansionTile(
                                        backgroundColor: Colors.cyanAccent,
                                        title: Text(
                                            " TS - ${tripSheetList[i]["tripSheetNumber"].toString()}"),
                                        leading: Icon(Icons.info),
                                        children: <Widget>[
                                          Container(
                                              height: 50,
                                              width: _width - 100,
                                              margin: EdgeInsets.only(
                                                  top: 20.0, left: 10),
                                              decoration: new BoxDecoration(
                                                color: Colors.green,
                                                borderRadius: BorderRadius.all(
                                                    Radius.circular(5.0)),
                                              ),
                                              child: MaterialButton(
                                                highlightColor:
                                                    Colors.transparent,
                                                splashColor:
                                                    Colors.purpleAccent,
                                                child: Padding(
                                                  padding: const EdgeInsets
                                                          .symmetric(
                                                      vertical: 10.0,
                                                      horizontal: 42.0),
                                                  child: Text(
                                                    "View Complete TripSheet Details",
                                                    style: TextStyle(
                                                        fontSize: 13,
                                                        color: AppTheme.white,
                                                        fontWeight:
                                                            FontWeight.w700),
                                                  ),
                                                ),
                                                onPressed: () {
                                                  _viewCompleteDetails(
                                                      tripSheetList[i]);
                                                },
                                              )),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Route : ",
                                            value: tripSheetList[i]["routeName"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Group : ",
                                            value: tripSheetList[i]
                                                    ["vehicle_Group"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Trip Date : ",
                                            value: tripSheetList[i]
                                                    ["tripOpenDate"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Advance : ",
                                            value: tripSheetList[i]
                                                    ["tripTotalAdvance"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Expense : ",
                                            value: tripSheetList[i]
                                                    ["tripTotalexpense"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Income : ",
                                            value: tripSheetList[i]
                                                    ["tripTotalincome"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Trip Status : ",
                                            value: tripSheetList[i]
                                                    ["tripStutes"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                        ],
                                      )
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

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
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

  _viewCompleteDetails(Map tripdata) {
    if (tripdata["tripSheetID"] != null) {
      int tsId = tripdata["tripSheetID"];
      int tripStatus = tripdata['tripStutesId'];

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
    }
  }
}
