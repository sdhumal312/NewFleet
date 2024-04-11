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
import 'editPickOrDrop.dart';

class ShowPickOrDrop extends KFDrawerContent {

  final int dispatchPickAndDropId;
  bool navigateValue;
  ShowPickOrDrop({Key key, this.dispatchPickAndDropId, this.navigateValue});
 

  

  @override
  _ShowPickOrDropState createState() => _ShowPickOrDropState();
}

class _ShowPickOrDropState extends State<ShowPickOrDrop> with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String tripSheetNumber;
  String journeyDateStr;
  String vehicleRegistration;
  String driverName;
  String vendorName;
  String pickOrDropStatusStr;
  String locationName;
  double rate;
  int usageKm;
  double amount;
  double advance;
  String remark;
  int pickAndDropId;
  int locationId;
  int vendorId;

  Map pickOrDropDetails = Map();

  TextEditingController vehicleName = new TextEditingController();


  @override
  void initState() {
    getSessionData(widget.dispatchPickAndDropId, widget.navigateValue);
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData(int dispatchPickAndDropId, bool navigateValue) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    
    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'tripsheetPickAndDropId': dispatchPickAndDropId.toString()
    };

    var response = await ApiCall.getDataFromApi(
        URI.SHOW_PICK_OR_DROP_DATA, data, URI.LIVE_URI, context);
    print(response);

    if (response != null) {

      pickOrDropDetails = response['pickOrDropDetails'];

      setState(() {

      pickAndDropId = dispatchPickAndDropId;
      journeyDateStr = pickOrDropDetails['journeyDateStr'];
      tripSheetNumber = pickOrDropDetails['tripSheetNumber'].toString();
      vehicleRegistration = pickOrDropDetails['vehicleRegistration'];
      driverName = pickOrDropDetails['driverName'];
      vendorId = pickOrDropDetails['vendorId'];

      if(vendorId > 0){
        vendorName = pickOrDropDetails['vendorName'];
      } else {
        vendorName = pickOrDropDetails['newVendorName'];
      }  
      
      pickOrDropStatusStr = pickOrDropDetails['pickOrDropStatusStr'];
      locationId = pickOrDropDetails['pickOrDropId'];

      if(locationId > 0){
        locationName = pickOrDropDetails['locationName'];
      } else {
        locationName = pickOrDropDetails['newPickOrDropLocationName'];
      }

      rate = pickOrDropDetails['rate'];
      usageKm = pickOrDropDetails['tripUsageKM'];
      amount = pickOrDropDetails['amount'];
      advance = pickOrDropDetails['tripTotalAdvance'];
      remark = pickOrDropDetails['remark'];

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

     Navigator.push(context, MaterialPageRoute(builder: (context) => EditPickOrDrop(tripsheetPickAndDropId: pickAndDropId)),);

  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    
    return Scaffold(
      // appBar: AppBar(
      // title: const Text('View Pick Or Drop Details'),
      
      // ),

      appBar: AppBar(
      title: new Text(
        "Show Pick And Drop Details",
        style: new TextStyle(color: Colors.white),
      ),
      backgroundColor: Colors.lightGreen,
      leading: new IconButton(
        icon: new Icon(Icons.arrow_back),
        onPressed: () {

          if(!widget.navigateValue){
            print("yes....");
              Navigator.pop(context);
          } else {
              Navigator.pop(context);
              Navigator.pop(context);
              Navigator.pop(context);
          }

          // Navigator.push(
          //   context,
          //   MaterialPageRoute(builder: (context) => ThirdScreen()),
          // );
        },
      ),
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

                                    SizedBox(height: 5),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Journey Date : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${journeyDateStr} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
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
                                            "Trip Number : TS - ${tripSheetNumber}",
                                            style: TextStyle(fontWeight: FontWeight.w700,
                                                color: Colors.red,
                                                fontSize: 23)
                                        ),
                                      ),
                                    ),

                                    SizedBox(height: 20),
                                    Container(
                                      child: Align(
                                        alignment: Alignment.center,
                                        child: Text(
                                            "${vehicleRegistration}",
                                            style: TextStyle(fontWeight: FontWeight.w700,
                                                color: Colors.green,
                                                fontSize: 20)
                                        ),
                                      ),
                                    ),

                                    SizedBox(height: 40),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Tripsheet Details ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 20
                                              )
                                          ),
                                          
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.pink,
                                      margin: const EdgeInsets.only(left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Driver Name : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${driverName}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Party Name : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${vendorName}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Pick/Drop Status : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${pickOrDropStatusStr}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Pick/Drop Location Name : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${locationName}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Rate : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${rate}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Trip Usage KM : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${usageKm}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Amount : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${amount}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Advance : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${advance}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Remark : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
                                              )
                                          ),

                                          new TextSpan(
                                              text: " ${remark}",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 15
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

                                    SizedBox(height: 15),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(5.0)),
                                            color: Colors.lightGreen
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
                                                "Edit Tripsheet",
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

  
  

}
