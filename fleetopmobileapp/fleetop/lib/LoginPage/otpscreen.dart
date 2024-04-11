import 'dart:async';
import 'dart:core';

import 'package:device_info/device_info.dart';
import 'package:fleetop/LoginPage/signupscreen.dart';
import 'package:fleetop/Main_Drawer/main_drawer.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:get_ip/get_ip.dart';
import 'package:firebase_messaging/firebase_messaging.dart';

class OtpScreen extends StatefulWidget {
  OtpScreen({this.data});
  final Map data;

  @override
  OtpScreenState createState() => OtpScreenState(signUpData: data);
}

class OtpScreenState extends State<OtpScreen> {
  OtpScreenState({this.signUpData});
  final Map signUpData;
  String userId;
  String companyId;
  String macAddress;
  String ipAddress;
  String deviceName;
  String mobileUniqueId;
  String name;
  String emailId;
  String userAddress;
  String mobnumber;
  String otpNumber;
  bool showOTP = false;
  bool text1 = true;
  Timer timer;
  int start = 120;
  bool showResentOTP = false;
  bool showotpcounter = true;

  TextEditingController controller1 = new TextEditingController();
  TextEditingController controller2 = new TextEditingController();
  TextEditingController controller3 = new TextEditingController();
  TextEditingController controller4 = new TextEditingController();
  TextEditingController controller5 = new TextEditingController();
  TextEditingController controller6 = new TextEditingController();

  TextEditingController currController = new TextEditingController();
  String tokenForNotification = "";
  final FirebaseMessaging firebaseMessaging = FirebaseMessaging();
  @override
  void dispose() {
    super.dispose();
    controller1.dispose();
    controller2.dispose();
    controller3.dispose();
    controller4.dispose();
    controller5.dispose();
    controller6.dispose();
    timer.cancel();
  }

  @override
  void initState() {
    verifyData();
    storeDeviceData();
    super.initState();
    currController = controller1;
    getData();
    startTimer();
  }

  verifyData() {
    firebaseMessaging.getToken().then((token) async {
      setState(() {
        tokenForNotification = token;
      });
      print("tokenForNotification = $tokenForNotification");
    });
  }

  Future storeDeviceData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    DeviceInfoPlugin deviceInfo = DeviceInfoPlugin();
    AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;

    userId = prefs.getString('userId');
    companyId = prefs.getString('companyId');
    macAddress = "0";

    try {
      ipAddress = await GetIp.ipAddress;
    } on PlatformException {
      ipAddress = '0';
    }

    deviceName = androidInfo.model.toString();
    mobileUniqueId = androidInfo.androidId.toString();
  }

  void getData() {
    if (signUpData != null && signUpData.toString().length > 0) {
      if (signUpData["mobileNumber"] != null &&
          signUpData["mobileNumber"].toString().length == 10) {
        setState(() {
          mobnumber = signUpData["mobileNumber"].toString();
          emailId = signUpData["emailId"].toString();
        });
      }
    }
  }

  void startTimer() {
    const oneSec = const Duration(seconds: 1);
    timer = new Timer.periodic(
      oneSec,
      (Timer timer) => setState(
        () {
          if (start < 1) {
            timer.cancel();
            setState(() {
              showResentOTP = true;
              showotpcounter = false;
            });
          } else {
            start = start - 1;
          }
        },
      ),
    );
  }

  Future<bool> _onWillPop() {
    Navigator.of(context).pushReplacement(
        new MaterialPageRoute(builder: (context) => new SignUpScreen()));
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> widgetList = [
      Padding(
        padding: EdgeInsets.only(left: 0.0, right: 2.0),
        child: new Container(
          color: Colors.transparent,
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(right: 2.0, left: 2.0),
        child: new Container(
            alignment: Alignment.center,
            decoration: new BoxDecoration(
                color: Color.fromRGBO(0, 0, 0, 0.1),
                border: new Border.all(
                    width: 1.0, color: Color.fromRGBO(0, 0, 0, 0.1)),
                borderRadius: new BorderRadius.circular(4.0)),
            child: new TextField(
              inputFormatters: [
                LengthLimitingTextInputFormatter(1),
              ],
              enabled: false,
              controller: controller1,
              autofocus: false,
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 24.0, color: Colors.black),
            )),
      ),
      Padding(
        padding: const EdgeInsets.only(right: 2.0, left: 2.0),
        child: new Container(
          alignment: Alignment.center,
          decoration: new BoxDecoration(
              color: Color.fromRGBO(0, 0, 0, 0.1),
              border: new Border.all(
                  width: 1.0, color: Color.fromRGBO(0, 0, 0, 0.1)),
              borderRadius: new BorderRadius.circular(4.0)),
          child: new TextField(
            inputFormatters: [
              LengthLimitingTextInputFormatter(1),
            ],
            controller: controller2,
            autofocus: false,
            enabled: false,
            keyboardType: TextInputType.number,
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 24.0, color: Colors.black),
          ),
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(right: 2.0, left: 2.0),
        child: new Container(
          alignment: Alignment.center,
          decoration: new BoxDecoration(
              color: Color.fromRGBO(0, 0, 0, 0.1),
              border: new Border.all(
                  width: 1.0, color: Color.fromRGBO(0, 0, 0, 0.1)),
              borderRadius: new BorderRadius.circular(4.0)),
          child: new TextField(
            inputFormatters: [
              LengthLimitingTextInputFormatter(1),
            ],
            keyboardType: TextInputType.number,
            controller: controller3,
            textAlign: TextAlign.center,
            autofocus: false,
            enabled: false,
            style: TextStyle(fontSize: 24.0, color: Colors.black),
          ),
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(right: 2.0, left: 2.0),
        child: new Container(
          alignment: Alignment.center,
          decoration: new BoxDecoration(
              color: Color.fromRGBO(0, 0, 0, 0.1),
              border: new Border.all(
                  width: 1.0, color: Color.fromRGBO(0, 0, 0, 0.1)),
              borderRadius: new BorderRadius.circular(4.0)),
          child: new TextField(
            inputFormatters: [
              LengthLimitingTextInputFormatter(1),
            ],
            textAlign: TextAlign.center,
            controller: controller4,
            autofocus: false,
            enabled: false,
            style: TextStyle(fontSize: 24.0, color: Colors.black),
          ),
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(right: 2.0, left: 2.0),
        child: new Container(
          alignment: Alignment.center,
          decoration: new BoxDecoration(
              color: Color.fromRGBO(0, 0, 0, 0.1),
              border: new Border.all(
                  width: 1.0, color: Color.fromRGBO(0, 0, 0, 0.1)),
              borderRadius: new BorderRadius.circular(4.0)),
          child: new TextField(
            inputFormatters: [
              LengthLimitingTextInputFormatter(1),
            ],
            textAlign: TextAlign.center,
            controller: controller5,
            autofocus: false,
            enabled: false,
            style: TextStyle(fontSize: 24.0, color: Colors.black),
          ),
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(right: 2.0, left: 2.0),
        child: new Container(
          alignment: Alignment.center,
          decoration: new BoxDecoration(
              color: Color.fromRGBO(0, 0, 0, 0.1),
              border: new Border.all(
                  width: 1.0, color: Color.fromRGBO(0, 0, 0, 0.1)),
              borderRadius: new BorderRadius.circular(4.0)),
          child: new TextField(
            inputFormatters: [
              LengthLimitingTextInputFormatter(1),
            ],
            textAlign: TextAlign.center,
            controller: controller6,
            autofocus: false,
            enabled: false,
            style: TextStyle(fontSize: 24.0, color: Colors.black),
          ),
        ),
      ),
      Padding(
        padding: EdgeInsets.only(left: 2.0, right: 0.0),
        child: new Container(
          color: Colors.transparent,
        ),
      ),
    ];

    return WillPopScope(
      onWillPop: _onWillPop,
      child: new Scaffold(
        resizeToAvoidBottomPadding: false,
        appBar: AppBar(
          backgroundColor: Colors.amber,
          iconTheme: IconThemeData(
            color: Colors.black,
          ),
          centerTitle: true,
          title: Text(
            "Enter OTP",
            style: new TextStyle(
              fontSize: 30,
              color: AppTheme.darkText,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
        backgroundColor: Color(0xFFeaeaea),
        body: Container(
          child: Column(
            children: <Widget>[
              Flexible(
                child: Column(
                  mainAxisSize: MainAxisSize.max,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.only(top: 4.0, bottom: 2.0),
                      child: Text(
                        "Verifying your number!",
                        style: TextStyle(
                            fontSize: 18.0, fontWeight: FontWeight.bold),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(
                          left: 16.0, top: 2.0, right: 16.0),
                      child: Text(
                        "Please type the verification code sent to",
                        style: TextStyle(
                            fontSize: 15.0, fontWeight: FontWeight.normal),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(
                          left: 30.0, top: 2.0, right: 30.0),
                      child: Text(
                        mobnumber != null ? mobnumber : "------",
                        style: TextStyle(
                            fontSize: 15.0,
                            fontWeight: FontWeight.bold,
                            color: Colors.red),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(
                          left: 30.0, top: 2.0, right: 30.0),
                      child: Text(
                        emailId != null ? emailId : "------",
                        style: TextStyle(
                            fontSize: 15.0,
                            fontWeight: FontWeight.bold,
                            color: Colors.red),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(top: 10.0),
                      child: Image(
                        image: AssetImage('assets/img/otp-icon.png'),
                        height: 120.0,
                        width: 120.0,
                      ),
                    ),
                    Visibility(
                      visible: showotpcounter,
                      child: Container(
                        margin: const EdgeInsets.only(left: 180, top: 1.0),
                        child: CircleAvatar(
                          backgroundColor: Colors.white,
                          foregroundColor: Colors.black,
                          radius: 30,
                          child: Text(start.toString()),
                        ),
                      ),
                    ),
                    Visibility(
                      visible: showResentOTP,
                      child: Container(
                          margin: const EdgeInsets.only(left: 180, top: 1.0),
                          child: FlatButton.icon(
                              onPressed: reSendOtp,
                              icon: Icon(Icons.refresh),
                              label: Text("RESEND"))),
                    )
                  ],
                ),
                flex: 90,
              ),
              Flexible(
                child: Column(
                    mainAxisSize: MainAxisSize.max,
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: <Widget>[
                      GridView.count(
                          crossAxisCount: 8,
                          mainAxisSpacing: 10.0,
                          shrinkWrap: true,
                          primary: false,
                          scrollDirection: Axis.vertical,
                          children: List<Container>.generate(
                              8,
                              (int index) =>
                                  Container(child: widgetList[index]))),
                    ]),
                flex: 20,
              ),
              Flexible(
                child: Column(
                  mainAxisSize: MainAxisSize.max,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    new Container(
                      child: Padding(
                        padding: const EdgeInsets.only(
                            left: 8.0, top: 10.0, right: 8.0, bottom: 0.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: <Widget>[
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("1");
                              },
                              child: Text("1",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("2");
                              },
                              child: Text("2",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("3");
                              },
                              child: Text("3",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                          ],
                        ),
                      ),
                    ),
                    new Container(
                      child: Padding(
                        padding: const EdgeInsets.only(
                            left: 8.0, top: 4.0, right: 8.0, bottom: 0.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: <Widget>[
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("4");
                              },
                              child: Text("4",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("5");
                              },
                              child: Text("5",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("6");
                              },
                              child: Text("6",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                          ],
                        ),
                      ),
                    ),
                    new Container(
                      child: Padding(
                        padding: const EdgeInsets.only(
                            left: 8.0, top: 4.0, right: 8.0, bottom: 0.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: <Widget>[
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("7");
                              },
                              child: Text("7",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("8");
                              },
                              child: Text("8",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("9");
                              },
                              child: Text("9",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                          ],
                        ),
                      ),
                    ),
                    new Container(
                      child: Padding(
                        padding: const EdgeInsets.only(
                            left: 8.0, top: 4.0, right: 8.0, bottom: 0.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: <Widget>[
                            MaterialButton(
                                onPressed: () {
                                  deleteText();
                                },
                                child: Image.asset('assets/img/delete.png',
                                    width: 25.0, height: 25.0)),
                            MaterialButton(
                              onPressed: () {
                                inputTextToField("0");
                              },
                              child: Text("0",
                                  style: TextStyle(
                                      fontSize: 25.0,
                                      fontWeight: FontWeight.w400),
                                  textAlign: TextAlign.center),
                            ),
                            MaterialButton(
                                onPressed: () {
                                  validateOtp(context);
                                },
                                child: Image.asset('assets/img/success.png',
                                    width: 25.0, height: 25.0)),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                flex: 90,
              ),
            ],
          ),
        ),
      ),
    );
  }

  void inputTextToField(String str) {
    //Edit first textField
    if (currController == controller1) {
      controller1.text = str;
      currController = controller2;
    }

    //Edit second textField
    else if (currController == controller2) {
      controller2.text = str;
      currController = controller3;
    }

    //Edit third textField
    else if (currController == controller3) {
      controller3.text = str;
      currController = controller4;
    }

    //Edit fourth textField
    else if (currController == controller4) {
      controller4.text = str;
      currController = controller5;
    }

    //Edit fifth textField
    else if (currController == controller5) {
      controller5.text = str;
      currController = controller6;
    }

    //Edit sixth textField
    else if (currController == controller6) {
      controller6.text = str;
      currController = controller6;
    }
  }

  void deleteText() {
    if (currController.text.length == 0) {
    } else {
      currController.text = "";
      currController = controller5;
      return;
    }

    if (currController == controller1) {
      controller1.text = "";
    } else if (currController == controller2) {
      controller1.text = "";
      currController = controller1;
    } else if (currController == controller3) {
      controller2.text = "";
      currController = controller2;
    } else if (currController == controller4) {
      controller3.text = "";
      currController = controller3;
    } else if (currController == controller5) {
      controller4.text = "";
      currController = controller4;
    } else if (currController == controller6) {
      controller5.text = "";
      currController = controller5;
    }
  }

  String makeOtp() {
    if (controller1.text.toString() != null &&
        controller1.text.toString().length == 1 &&
        controller2.text.toString() != null &&
        controller2.text.toString().length == 1 &&
        controller3.text.toString() != null &&
        controller3.text.toString().length == 1 &&
        controller4.text.toString() != null &&
        controller4.text.toString().length == 1 &&
        controller5.text.toString() != null &&
        controller5.text.toString().length == 1 &&
        controller6.text.toString() != null &&
        controller6.text.toString().length == 1) {
      setState(() {
        otpNumber = controller1.text.toString() +
            controller2.text.toString() +
            controller3.text.toString() +
            controller4.text.toString() +
            controller5.text.toString() +
            controller6.text.toString();
      });
      return otpNumber;
    } else {
      return otpNumber;
    }
  }

  void reSendOtp() async {
    setState(() {
      start = 120;
      showResentOTP = false;
      showotpcounter = true;
    });
    startTimer();
    var otpData = {
      'mobileNumber': signUpData['mobileNumber'],
      'companyId': signUpData['companyId'],
      'otpMessage': otpNumber +
          ' is your OTP for registration on Fleetop mobile services.Do not share your OTP with anyone.'
    };
    var resultData = await ApiCall.getDataFromApi(
        URI.SEND_OTP_URI, otpData, URI.LIVE_URI, context);

    print(resultData['otpSend']);
    if (resultData != null) {
      if (resultData['otpSend'] == true) {
        FlutterAlert.onSuccessAlert(
            context,
            "OTP successfully send on " + signUpData['mobileNumber'] + "",
            "Info");
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support", "Error");
    }
  }

  Future registerUser() async {
    var data = {
      'userId': userId,
      'companyId': signUpData['companyId'],
      'macAddress': macAddress,
      'ipAddress': ipAddress,
      'mobileUniqueId': mobileUniqueId,
      'deviceName': deviceName,
      'name': signUpData['name'],
      'emailId': signUpData['emailId'],
      'userAddress': signUpData['address'],
      'mobileNumber': mobnumber,
      'tokenForNotification': tokenForNotification
    };
    var resultData = await ApiCall.getDataFromApi(
        URI.MOBILE_APP_USER_REGISTRATION, data, URI.LIVE_URI, context);

    if (resultData != null) {
      if (resultData['success'] == true) {
        SharedPreferences prefs = await SharedPreferences.getInstance();
        await prefs.setBool('sendOTP', false);
        Navigator.of(context).pushReplacement(
            new MaterialPageRoute(builder: (context) => new MainWidget()));
      }
    }
  }

  void validateOtp(BuildContext context) async {
    print("test");
    String res = makeOtp();

    if (res != null || res.length < 6) {
      if (res.toString() == signUpData['otpNumber'].toString()) {
        registerUser();
      } else {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid OTP Number !", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid OTP Number !", "Error");
    }
  }
}
