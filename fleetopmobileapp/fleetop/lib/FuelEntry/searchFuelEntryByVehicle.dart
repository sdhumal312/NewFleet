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

class SearchFuelEntryByVehicle extends KFDrawerContent {

  SearchFuelEntryByVehicle({Key key});

  @override
  _SearchFuelEntryByVehicleState createState() => _SearchFuelEntryByVehicleState();
}

class _SearchFuelEntryByVehicleState extends State<SearchFuelEntryByVehicle> with TickerProviderStateMixin {
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
  List fuelList = List();
  
  String base64ImageString;
  bool imageState = false;
  bool imageAvailable = false;

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
    
    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email
    };
    
  }

  Widget imageContainer(String img) {
    return (Visibility(
        visible: (img != null),
        child: Padding(
            padding: new EdgeInsets.all(8.0),
            child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 100,
              child: (img != null)
                  ? Image.memory(base64Decode(img),
                      fit: BoxFit.fill)
                  : Image.asset("assets/img/signUp.png", fit: BoxFit.fill),
            ))));
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

      if(!fieldValidation()){
          return;
      } else {

        var fuelDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vid': vehicleId,
      };

      var data = await ApiCall.getDataFromApi(
          URI.SEARCH_FUEL_ENTRY_BY_VEHICLE, fuelDetails, URI.LIVE_URI, context);

        if (data != null) {
            if (data['fuelNotFound'] == true) {
              FlutterAlert.onInfoAlert(context, "Fuel Entry Not Found !", "Info");
                  setState(() {
                    fuelList.length = 0;
                  });
            }  else if (data['fuelFound'] == true) {
                  setState(() {
                    fuelList = data['fuelList'];
                  });
            } 
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
      title: const Text('Search Fuel Entry By Vehicle'),
      backgroundColor: Colors.blue,
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

                                    SizedBox(height:10),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            color: Colors.blue,
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
                                    
                                    SizedBox(height:50),

                                    for(int i =0; i<fuelList.length; i++)

                                    ExpansionTile(
                                    backgroundColor:Colors.cyanAccent,
                                    title: Text(" F - ${fuelList[i]["fuel_Number"].toString()}"),
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
                                          highlightColor: Colors.transparent,
                                          splashColor: Colors.purpleAccent,
                                          child: Padding(
                                            padding:
                                                const EdgeInsets.symmetric(
                                                    vertical: 10.0,
                                                    horizontal: 42.0),
                                            child: Text(
                                              "View Complete Fuel Details",
                                              style: TextStyle(
                                                  fontSize: 15,
                                                  color: AppTheme.white,
                                                  fontWeight:
                                                      FontWeight.w700),
                                            ),
                                          ),
                                          onPressed:()
                                          {
                                             _viewCompleteDetails(fuelList[i]);
                                          },
                                        )),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Vehicle : ",
                                      value: fuelList[i]["vehicle_registration"].toString(),
                                      icon: Icons.format_list_numbered,
                                      
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Driver : ",
                                      value: fuelList[i]["driver_name"] != null ? fuelList[i]["driver_name"].toString() : "-",
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Date : ",
                                      value: fuelList[i]["fuel_date"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Vendor : ",
                                      value: fuelList[i]["vendor_name"] != null ? fuelList[i]["vendor_name"].toString() : "-",
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Open Km : ",
                                      value: fuelList[i]["fuel_meter_old"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Close Km : ",
                                      value: fuelList[i]["fuel_meter"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Usage : ",
                                      value: fuelList[i]["fuel_usage"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Amount : ",
                                      value: fuelList[i]["fuel_amount"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Volume : ",
                                      value: fuelList[i]["fuel_liters"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "FE : ",
                                      value: fuelList[i]["fuel_kml"] != null ? fuelList[i]["fuel_kml"].toString() : "-",
                                      icon: Icons.format_list_numbered,
                                      ),

                                      SizedBox(height: 10),
                                      if(fuelList[i]["fuelBase64Document"] != null)
                                       imageContainer(fuelList[i]["fuelBase64Document"]),
                                      
                                    
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

  _viewCompleteDetails(Map fueldata) {

    print("fueldata...$fueldata");

    if(fueldata["fuel_id"] != null){
      Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) =>
              new ShowFuelDetails(fueldata: fueldata)));
    }
      
  } 

}
