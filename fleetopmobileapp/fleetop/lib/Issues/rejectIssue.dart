import 'dart:convert';
import 'dart:io';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/Issues/showIssue.dart';
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
import 'editIssue.dart';

class RejectIssue extends KFDrawerContent {
  final String issueId;
  bool navigateValue;
  RejectIssue({Key key, this.issueId, this.navigateValue});

  @override
  _RejectIssueState createState() => _RejectIssueState();
}

class _RejectIssueState extends State<RejectIssue>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String issId;

  TextEditingController issueDescription = new TextEditingController();

  @override
  void initState() {
    getSessionData(widget.issueId, widget.navigateValue);
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData(String idIssue, bool navigateValue) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    setState(() {
      issId = idIssue;
    });
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
    var issueDetails = {
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'issuesId': issId,
      'description': issueDescription.text
    };
    print("issuesId....$issId");
    var data = await ApiCall.getDataFromApi(
        URI.REJECT_ISSUES_DETAILS, issueDetails, URI.LIVE_URI, context);

    if (data != null) {
      if (data['save'] == true) {
        refreshData();
        redirectToDisplay(issId);
      } else {
        FlutterAlert.onInfoAlert(
            context, "Issue Not Rejected, Please contact on Support !", "Info");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support !", "Error");
    }
  }

  refreshData() {
    issueDescription.text = '';
  }

  Future<bool> redirectToDisplay(issueId) async => Alert(
        context: context,
        type: AlertType.success,
        title: "Success",
        desc: "Issue Successfully Rejected !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => issueDisplayData(issueId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  issueDisplayData(String issueId, context) async {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => ShowIssue(issueId: issueId)),
    );
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Reject Issue'),
        backgroundColor: Colors.pink,
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
                                              text: " Reject Issue Details ",
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
                                    SizedBox(height: 10),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 3,
                                            controller: issueDescription,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Description',
                                                hintText: 'Description',
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
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                              borderRadius: BorderRadius.all(
                                                  Radius.circular(5.0)),
                                              color: Colors.pink),
                                          child: MaterialButton(
                                            highlightColor: Colors.transparent,
                                            splashColor: Colors.purpleAccent,
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 10.0,
                                                      horizontal: 42.0),
                                              child: Text(
                                                "Reject Issue",
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
