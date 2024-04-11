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
import 'package:fleetop/RenewalReminder/showRenewalReminder.dart';

class SearchRenewalByVehicle extends KFDrawerContent {

  SearchRenewalByVehicle({Key key});

  @override
  _SearchRenewalByVehicleState createState() => _SearchRenewalByVehicleState();
}

class _SearchRenewalByVehicleState extends State<SearchRenewalByVehicle> with TickerProviderStateMixin {
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
  List renewalList = List();
  Map configuration;

  bool showDueThreshold = false;
  bool showDueThresholdPeriod = false;
  bool showReceiptNumber = false;
  bool showVendorCol = false;
  bool showCashTranNumber = false;
  bool showPaidDate = false;
  bool showPaidBy = false;
  bool showStateAuthorization = false;
  bool showRemark = false;
  
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

    // var response = await ApiCall.getDataFromApi(
    //     URI.GET_RR_CONFIG_DATA, data, URI.LIVE_URI, context);

    // if (response != null) {
      
    // }
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

        var renewalDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vid': vehicleId,
      };

      print("renewalDetails....${renewalDetails}");

      var data = await ApiCall.getDataFromApi(
          URI.SEARCH_RENEWAL_BY_VEHICLE, renewalDetails, URI.LIVE_URI, context);

      //print("output.....${data}"); 

        if (data != null) {
            if (data['renewalNotFound'] == true) {
              FlutterAlert.onInfoAlert(context, "Renewal Reminder Not Found !", "Info");
                  setState(() {
                    renewalList.length = 0;
                  });
            }  else if (data['renewalFound'] == true) {
                  configuration = data['configuration'];
                  setState(() {
                    renewalList = data['renewalReminder'];

                    showReceiptNumber = configuration['receiptnumbershow'];
                    showCashTranNumber = configuration['cashtransactionshow'];
                    showPaidDate = configuration['paidDateshow'];
                    showPaidBy = configuration['paidbyshow'];
                    showStateAuthorization = configuration['optionalInformation'];
                    showRemark = configuration['optionalInformation'];
                    showVendorCol = configuration['showVendorCol'];

                  });
            } 

            print("output.....${renewalList.length}"); 

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
      title: const Text('Search Renewal By Vehicle'),
      backgroundColor: Colors.redAccent,
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
                                            color: Colors.redAccent,
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

                                    for(int i =0; i<renewalList.length; i++)

                                    ExpansionTile(
                                    backgroundColor:Colors.cyanAccent,
                                    title: Text(" RR - ${renewalList[i]["renewal_R_Number"].toString()} - ${renewalList[i]["renewal_type"].toString()} - ${renewalList[i]["renewal_subtype"].toString()}"),
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
                                            "View Complete Renewal Details",
                                            style: TextStyle(
                                                fontSize: 13,
                                                color: AppTheme.white,
                                                fontWeight:
                                                    FontWeight.w700),
                                          ),
                                        ),
                                        onPressed:()
                                        {
                                            _viewCompleteDetails(renewalList[i]);
                                        },
                                      )),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Renewal Type : ",
                                      value: renewalList[i]["renewal_type"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Renewal Sub Type :",
                                      value: renewalList[i]["renewal_subtype"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "From Date : ",
                                      value: renewalList[i]["renewal_from"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),
                                      
                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "To Date : ",
                                      value: renewalList[i]["renewal_to"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Time Threshold : ",
                                      value: renewalList[i]["renewal_timethreshold"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Amount :",
                                      value: renewalList[i]["renewal_Amount"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      if(showReceiptNumber)
                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Receipt No :",
                                      value: renewalList[i]["renewal_to"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      if(showVendorCol)
                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Vendor Name :",
                                      value: renewalList[i]["vendorName"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Payment Type :",
                                      value: renewalList[i]["renewal_paymentType"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      if(showCashTranNumber)
                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Cash Transaction Number :",
                                      value: renewalList[i]["renewal_PayNumber"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Paid Date :",
                                      value: renewalList[i]["renewal_dateofpayment"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Paid By :",
                                      value: renewalList[i]["renewal_paidby"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Renewal Status :",
                                      value: renewalList[i]["renewal_status"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      if(showStateAuthorization)
                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "State Authorization  :",
                                      value: renewalList[i]["renewal_PayNumber"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),

                                      if(showRemark)
                                      GradientCard(
                                      colorData: Colors.cyanAccent,
                                      valueColorChanged: false,
                                      name: "Remark  :",
                                      value: renewalList[i]["renewal_number"].toString(),
                                      icon: Icons.format_list_numbered,
                                      ),
                                      
                                      SizedBox(height: 10),
                                      if(renewalList[i]["renewalBase64Document"] != null)
                                       imageContainer(renewalList[i]["renewalBase64Document"]),
                                    
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

  _viewCompleteDetails(Map renewalData) {

    if(renewalData["renewal_id"] != null){
      Navigator.push(context, MaterialPageRoute(builder: (context) => ShowRenewalReminder(renewalId: renewalData["renewal_id"])),);
    }
      
  }

}
