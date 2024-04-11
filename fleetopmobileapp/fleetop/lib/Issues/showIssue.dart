import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/Issues/rejectIssue.dart';
import 'package:fleetop/Issues/reopenIssue.dart';
import 'package:fleetop/Issues/resolveIssue.dart';
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
import 'package:fleetop/Utility/Utility.dart';
import '../fleetopuriconstant.dart';
import 'closeIssue.dart';
import 'editIssue.dart';
import 'imageIssue.dart';

class ShowIssue extends KFDrawerContent {
  String issueId;
  bool navigateValue;
  ShowIssue({Key key, this.issueId, this.navigateValue});

  @override
  _ShowIssueState createState() => _ShowIssueState();
}

class _ShowIssueState extends State<ShowIssue> with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String issueId;
  String issueNumber;
  String issueType;
  String vehicleNumber;
  String driverName;
  int odometer;
  String vehicleGroup;
  String branch;
  String department;
  String reportedDate;
  String summary;
  String issueLabel;
  String reportedBy;
  String reportedTo;
  String status;
  int statusId;
  String description;

  bool vehicleIssue = false;
  bool driverIssue = false;
  bool refundIssue = false;
  bool otherIssue = false;

  bool showImage = false;
  bool showEdit = false;
  bool showResolve = false;
  bool showReject = false;
  bool showReOpen = false;
  bool showClose = false;

  String base64ImageString;
  bool imageState = false;
  bool imageAvailable = false;

  Map Issues = Map();

  Uint8List dataFromBase64String(String base64String) {
    return base64Decode(base64String);
  }

  Future<void> _shareImage() async {
    try {
      Uint8List uni = dataFromBase64String(base64ImageString);
      await Share.file('esys image', 'esys.png', uni, 'image/png');
    } catch (e) {
      print('error: $e');
    }
  }

  @override
  void initState() {
    getSessionData(widget.issueId, widget.navigateValue);
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData(String issueId, bool navigateValue) async {
    print("issueId..${issueId}");

    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var isdata = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'issueId': issueId,
    };

    print("isdata..${isdata}");

    var response = await ApiCall.getDataFromApi(
        URI.SHOW_ISSUES_DETAILS, isdata, URI.LIVE_URI, context);

    print("ola...${response['Issues']}");

    if (response != null) {
      Issues = response['Issues'];

      print("issue..${Issues['issues_NUMBER']}");
      print("issue11..${Issues}");

      setState(() {
        issueId = issueId;
        issueNumber = Issues['issues_NUMBER'].toString();
        issueType = Issues['issues_TYPE'];
        vehicleNumber = Issues['issues_VEHICLE_REGISTRATION'];
        driverName = Issues['issues_DRIVER_NAME'] != null
            ? Issues['issues_DRIVER_NAME']
            : "--";
        odometer = Issues['issues_ODOMETER'];
        vehicleGroup = Issues['issues_VEHICLE_GROUP'];
        branch = Issues['issues_BRANCH_NAME'];
        department = Issues['issues_DEPARTNMENT_NAME'];
        reportedDate = Issues['issues_REPORTED_DATE'];
        summary = Issues['issues_SUMMARY'];
        issueLabel = Issues['issues_LABELS'];
        reportedBy = Issues['issues_REPORTED_BY'];
        reportedTo = Issues['issues_ASSIGN_TO_NAME'];
        status = Issues['issues_STATUS'];
        statusId = Issues['issues_STATUS_ID'];
        description = Issues['issues_DESCRIPTION'];

        print("status..${status}");
        print("statusId..${statusId}");

        if (Issues['issues_TYPE_ID'] == 1) {
          vehicleIssue = true;
          driverIssue = false;
          refundIssue = false;
          otherIssue = false;
        }

        if (Issues['issues_TYPE_ID'] == 2) {
          vehicleIssue = false;
          driverIssue = true;
          refundIssue = false;
          otherIssue = false;
        }

        if (Issues['issues_TYPE_ID'] == 3) {
          vehicleIssue = false;
          driverIssue = false;
          refundIssue = true;
          otherIssue = false;
        }

        if (Issues['issues_TYPE_ID'] == 5) {
          vehicleIssue = false;
          driverIssue = false;
          refundIssue = false;
          otherIssue = true;
        }

        if (statusId == 1) {
          showImage = true;
          showEdit = true;
          showResolve = true;
          showReject = true;
          showReOpen = false;
          showClose = false;
        }

        if (statusId == 2) {
          showImage = false;
          showEdit = false;
          showResolve = false;
          showReject = false;
          showReOpen = true;
          showClose = false;
        }

        if (statusId == 2) {
          showImage = false;
          showEdit = false;
          showResolve = false;
          showReject = false;
          showReOpen = true;
          showClose = false;
        }

        if (statusId == 4) {
          showImage = true;
          showEdit = false;
          showResolve = false;
          showReject = false;
          showReOpen = true;
          showClose = true;
        }

        if (statusId == 5) {
          showImage = true;
          showEdit = false;
          showResolve = false;
          showReject = false;
          showReOpen = true;
          showClose = true;
        }

        if (response['issueImage'] != null) {
          setState(() {
            imageAvailable = true;
            base64ImageString = response['issueImage'];
          });
        }
      });
    }
  }

  Widget imageContainer() {
    return (Visibility(
        visible: (base64ImageString != null),
        child: Padding(
            padding: new EdgeInsets.all(8.0),
            child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 100,
              child: (base64ImageString != null)
                  ? Image.memory(base64Decode(base64ImageString),
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
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => EditIssue(issueId: issueId)),
    );
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Show Issue'),
        backgroundColor: Colors.pink,
      ),

      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          _settingModalBottomSheet(context);
        },
        child: new Icon(Icons.add),
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
                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Issue Details ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 20)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.pink,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 25),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Issue Number : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${issueNumber} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Issue Type : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${issueType} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(
                                        height: vehicleIssue == true ? 10 : 0),
                                    Visibility(
                                        visible:
                                            vehicleIssue == true ? true : false,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Vehicle Number : ",
                                                  style: new TextStyle(
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                              new TextSpan(
                                                  text: " ${vehicleNumber} ",
                                                  style: new TextStyle(
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible:
                                            vehicleIssue == true ? true : false,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(
                                        height: vehicleIssue == true ||
                                                driverIssue == true
                                            ? 10
                                            : 0),
                                    Visibility(
                                        visible: vehicleIssue == true ||
                                                driverIssue == true
                                            ? true
                                            : false,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Driver Name : ",
                                                  style: new TextStyle(
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                              new TextSpan(
                                                  text: " ${driverName} ",
                                                  style: new TextStyle(
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: vehicleIssue == true ||
                                                driverIssue == true
                                            ? true
                                            : false,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(
                                        height: vehicleIssue == true ? 10 : 0),
                                    Visibility(
                                        visible:
                                            vehicleIssue == true ? true : false,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Odometer : ",
                                                  style: new TextStyle(
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                              new TextSpan(
                                                  text: " ${odometer} ",
                                                  style: new TextStyle(
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible:
                                            vehicleIssue == true ? true : false,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(
                                        height: refundIssue == true ? 10 : 0),
                                    Visibility(
                                        visible:
                                            refundIssue == true ? true : false,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Vehicle Group : ",
                                                  style: new TextStyle(
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                              new TextSpan(
                                                  text: " ${vehicleGroup} ",
                                                  style: new TextStyle(
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible:
                                            refundIssue == true ? true : false,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(
                                        height: refundIssue == true ||
                                                otherIssue == true
                                            ? 10
                                            : 0),
                                    Visibility(
                                        visible: refundIssue == true ||
                                                otherIssue == true
                                            ? true
                                            : false,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Branch Name : ",
                                                  style: new TextStyle(
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                              new TextSpan(
                                                  text: " ${branch} ",
                                                  style: new TextStyle(
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: refundIssue == true ||
                                                otherIssue == true
                                            ? true
                                            : false,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(
                                        height: refundIssue == true ||
                                                otherIssue == true
                                            ? 10
                                            : 0),
                                    Visibility(
                                        visible: refundIssue == true ||
                                                otherIssue == true
                                            ? true
                                            : false,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Department Name : ",
                                                  style: new TextStyle(
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                              new TextSpan(
                                                  text: " ${department} ",
                                                  style: new TextStyle(
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                    fontSize: 18,
                                                  )),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: refundIssue == true ||
                                                otherIssue == true
                                            ? true
                                            : false,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Reported Date : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${reportedDate} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Summary : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${summary} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Issue Label : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${issueLabel} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Reported By : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${reportedBy} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Reported To : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${reportedTo} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Status : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${status} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Description : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: " ${description} ",
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Category Name : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: Utility.checkAndSet(
                                                  Issues["partCategoryName"]),
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Route Name  : ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                          new TextSpan(
                                              text: Utility.checkAndSet(
                                                  Issues["routeName"]),
                                              style: new TextStyle(
                                                color: Colors.blue,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 18,
                                              )),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: imageAvailable,
                                      child: Card(
                                        elevation: 0.5,
                                        color: Colors.pink,
                                        child: Container(
                                          height: 60,
                                          child: Padding(
                                              padding: const EdgeInsets.only(
                                                  left: 10, right: 10),
                                              child: Row(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment
                                                          .spaceBetween,
                                                  children: [
                                                    Text(
                                                      "Document ",
                                                      style: new TextStyle(
                                                        fontSize: 22,
                                                        color: AppTheme.white,
                                                        fontWeight:
                                                            FontWeight.w700,
                                                      ),
                                                    ),
                                                    DialogButton(
                                                      width: 100,
                                                      child: Text(
                                                        imageState
                                                            ? "Close"
                                                            : "Open",
                                                        style: TextStyle(
                                                            color: Colors.white,
                                                            fontSize: 25),
                                                      ),
                                                      color:
                                                          Colors.purpleAccent,
                                                      onPressed: () => {
                                                        setState(() {
                                                          imageState =
                                                              !imageState;
                                                        })
                                                      },
                                                    )
                                                  ])),
                                        ),
                                      ),
                                    ),
                                    Visibility(
                                      visible: imageState,
                                      child: imageContainer(),
                                    ),

                                    SizedBox(height: 20),
                                    Visibility(
                                      visible: imageState,
                                      child: Container(
                                        child: Padding(
                                            padding:
                                                const EdgeInsets.only(left: 10),
                                            child: Row(
                                                mainAxisAlignment:
                                                    MainAxisAlignment
                                                        .spaceBetween,
                                                children: [
                                                  FloatingActionButton(
                                                    backgroundColor:
                                                        Colors.blue,
                                                    foregroundColor:
                                                        Colors.white,
                                                    onPressed: () {
                                                      _shareImage();
                                                    },
                                                    child: Icon(Icons.share),
                                                  )
                                                ])),
                                      ),
                                    ),

                                    // SizedBox(height: 15),
                                    // Center(
                                    //   child: Container(
                                    //       height: 50,
                                    //       width: _width - 100,
                                    //       margin: EdgeInsets.only(
                                    //           top: 20.0, left: 10),
                                    //       decoration: new BoxDecoration(
                                    //         borderRadius: BorderRadius.all(
                                    //             Radius.circular(5.0)),
                                    //         color: Colors.pink
                                    //       ),
                                    //       child: MaterialButton(
                                    //         highlightColor: Colors.transparent,
                                    //         splashColor: Colors.purpleAccent,
                                    //         child: Padding(
                                    //           padding:
                                    //               const EdgeInsets.symmetric(
                                    //                   vertical: 10.0,
                                    //                   horizontal: 42.0),
                                    //           child: Text(
                                    //             "Edit Issue",
                                    //             style: TextStyle(
                                    //                 fontSize: 22,
                                    //                 color: AppTheme.white,
                                    //                 fontWeight:
                                    //                     FontWeight.w700),
                                    //           ),
                                    //         ),
                                    //         onPressed: _handleSubmitted,
                                    //       )),
                                    // ),
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

  void _settingModalBottomSheet(context) {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return ListView(
            children: <Widget>[
              Visibility(
                  visible: showImage,
                  child: Card(
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
                              builder: (context) =>
                                  ImageIssue(issueId: widget.issueId)),
                        )
                      },
                      leading: new Icon(
                        Icons.close,
                        color: Colors.red,
                      ),
                      title: Text(
                        "Add Document/Image",
                        style: new TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    ),
                  )),
              Visibility(
                  visible: showEdit,
                  child: Card(
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
                              builder: (context) =>
                                  EditIssue(issueId: widget.issueId)),
                        )
                      },
                      leading: new Icon(
                        Icons.edit,
                        color: Colors.lightBlueAccent,
                      ),
                      title: Text(
                        "Edit Issues",
                        style: new TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    ),
                  )),
              Visibility(
                  visible: showResolve,
                  child: Card(
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
                              builder: (context) =>
                                  ResolveIssue(issueId: widget.issueId)),
                        )
                      },
                      leading: new Icon(
                        Icons.fast_forward,
                        color: Colors.greenAccent,
                      ),
                      title: Text(
                        "Resolve Issues",
                        style: new TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    ),
                  )),
              Visibility(
                  visible: showReject,
                  child: Card(
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
                              builder: (context) =>
                                  RejectIssue(issueId: widget.issueId)),
                        )
                      },
                      leading: new Icon(
                        Icons.warning,
                        color: Colors.purpleAccent,
                      ),
                      title: Text(
                        "Reject Issues",
                        style: new TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    ),
                  )),
              Visibility(
                  visible: showReOpen,
                  child: Card(
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
                              builder: (context) =>
                                  ReOpenIssue(issueId: widget.issueId)),
                        )
                      },
                      leading: new Icon(
                        Icons.open_in_new,
                        color: Colors.amberAccent,
                      ),
                      title: Text(
                        "Reopen Issues",
                        style: new TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    ),
                  )),
              Visibility(
                  visible: showClose,
                  child: Card(
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
                              builder: (context) =>
                                  CloseIssue(issueId: widget.issueId)),
                        )
                      },
                      leading: new Icon(
                        Icons.close,
                        color: Colors.red,
                      ),
                      title: Text(
                        "Close Issues",
                        style: new TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    ),
                  )),
            ],
          );
        });
  }
}
