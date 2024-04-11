import 'dart:math';

import 'package:device_info/device_info.dart';
import 'package:fleetop/LoginPage/loginscreen.dart';
import 'package:fleetop/LoginPage/otpscreen.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../fleetopuriconstant.dart';

class SignUpScreen extends StatefulWidget {
  SignUpScreen({Key key}) : super(key: key);

  @override
  _SignUpScreenState createState() => _SignUpScreenState();
}

class _SignUpScreenState extends State<SignUpScreen> {
  TextEditingController nameController = new TextEditingController();
  TextEditingController emailController = new TextEditingController();
  TextEditingController mobileNumberController = new TextEditingController();
  TextEditingController addressController = new TextEditingController();
  String companyId;

  static final _random = Random();

  AnimationController animationController;
  bool multiple = true;

  Future _getDeviceData() async {
    DeviceInfoPlugin deviceInfo = DeviceInfoPlugin();
    AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;
    return androidInfo;
  }

  int getOtpNumber(int digitCount) {
    if (digitCount > 17 || digitCount < 1)
      throw new RangeError.range(0, 1, 17, "Digit Count");
    var digit = _random.nextInt(9) + 1; // first digit must not be a zero
    int n = digit;

    for (var i = 0; i < digitCount - 1; i++) {
      digit = _random.nextInt(10);
      n *= 10;
      n += digit;
    }
    return n;
  }

  @override
  void initState() {
    getSessionData();
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
  }

  void sendOtpMessage(otpNumber) async {
    var otpData = {
      'emailId': emailController.text.trim(),
      'mobileNumber': mobileNumberController.text,
      'companyId': companyId,
      'otpMessage': otpNumber +
          '  is your OTP for registration on Fleetop mobile services.Do not share your OTP with anyone. FLEETOP SOLUTIONS PRIVATE LIMITED'
    };
    print(otpData);
    var resultData = await ApiCall.getDataWithoutLoader(
        URI.SEND_OTP_URI, otpData, URI.LIVE_URI);
  }

  bool _phoneNumberValidator() {
    Pattern pattern = r'^(?:[+0]9)?[0-9]{10}$';
    String mobileNumber = mobileNumberController.text;
    print(mobileNumber);
    RegExp regex = new RegExp(pattern);
    print(regex.hasMatch(mobileNumber));
    if (!regex.hasMatch(mobileNumber))
      return false;
    else
      return true;
  }

  Future<bool> _onWillPop() async => Alert(
        context: context,
        type: AlertType.info,
        title: "Info",
        desc: "Are you sure,Do you want to go back to Login Page ?",
        buttons: [
          DialogButton(
            child: Text(
              "Yes",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => Navigator.of(context).push(
                new MaterialPageRoute(builder: (context) => new LoginScreen())),
            gradient: LinearGradient(colors: [Colors.green, Colors.deepPurple]),
          ),
          DialogButton(
            child: Text(
              "No",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => Navigator.pop(context),
            gradient: LinearGradient(colors: [Colors.red, Colors.black]),
          )
        ],
      ).show();

  void _handleSignUp() {
    String otpNumber = getOtpNumber(6).toString();

    if (nameController.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Enter Name !", "Error");
      return;
    }
    if (emailController.text == '' || emailController.text.length == 0) {
      FlutterAlert.onErrorAlert(context, "Please Enter EmailId !", "Error");
      return;
    }
    if (_phoneNumberValidator() == false) {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Mobile Number !", "Error");
      return;
    }

    if (emailController.text != '') {
      if (!RegExp(r"^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
          .hasMatch(emailController.text)) {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid Email Id!", "Error");
        return;
      }
    }
    sendOtpMessage(otpNumber);

    var data = {
      'name': nameController.text,
      'emailId': emailController.text,
      'mobileNumber': mobileNumberController.text,
      'address': addressController.text,
      'otpNumber': otpNumber,
      'companyId': companyId
    };
    print(data);

    Navigator.of(context).push(
        new MaterialPageRoute(builder: (context) => new OtpScreen(data: data)));
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: _onWillPop,
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.amber,
          iconTheme: IconThemeData(
            color: Colors.black,
          ),
          centerTitle: true,
          title: Text(
            "Sign Up",
            style: new TextStyle(
              fontSize: 30,
              color: AppTheme.darkText,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
        backgroundColor: AppTheme.white,
        body: Padding(
          padding: new EdgeInsets.all(5.0),
          child: Stack(
            children: <Widget>[
              new SingleChildScrollView(
                  child: new Container(
                padding: new EdgeInsets.all(16.0),
                child: new Column(
                  children: <Widget>[
                    new Container(
                      child: new Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          new Center(
                              child: new Image.asset(
                            "assets/img/signUp.png",
                            width: 250,
                            height: 150,
                          ))
                        ],
                      ),
                    ),
                    SizedBox(height: 25),
                    new Container(
                      child: new Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          new Form(
                            child: new Column(
                              children: <Widget>[
                                Align(
                                  alignment: Alignment.centerRight,
                                  child: Container(
                                    child: Icon(Icons.stars,
                                        color: Colors.red, size: 12),
                                  ),
                                ),
                                TextField(
                                  maxLines: 1,
                                  controller: nameController,
                                  style: TextStyle(color: Colors.black),
                                  decoration: InputDecoration(
                                      labelText: 'Name',
                                      hintText: "Name",
                                      hintStyle: TextStyle(color: Colors.black),
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(5.0)),
                                      icon: Icon(
                                        Icons.person,
                                        color: Colors.green,
                                      )),
                                ),
                                SizedBox(height: 15),
                                TextField(
                                  maxLines: 1,
                                  controller: emailController,
                                  style: TextStyle(color: Colors.black),
                                  decoration: InputDecoration(
                                      labelText: 'Email',
                                      hintText: "Email",
                                      hintStyle: TextStyle(color: Colors.black),
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(5.0)),
                                      icon: Icon(
                                        Icons.email,
                                        color: Colors.blue,
                                      )),
                                ),
                                SizedBox(height: 15),
                                Align(
                                  alignment: Alignment.centerRight,
                                  child: Container(
                                    child: Icon(Icons.stars,
                                        color: Colors.red, size: 12),
                                  ),
                                ),
                                TextField(
                                  maxLines: 1,
                                  controller: mobileNumberController,
                                  style: TextStyle(color: Colors.black),
                                  keyboardType: TextInputType.number,
                                  maxLength: 10,
                                  decoration: InputDecoration(
                                      labelText: 'Mobile Number',
                                      hintText: "Mobile Number",
                                      hintStyle: TextStyle(color: Colors.black),
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(5.0)),
                                      icon: Icon(
                                        Icons.phone,
                                        color: Colors.purple,
                                      )),
                                ),
                                SizedBox(height: 15),
                                TextField(
                                  maxLines: 2,
                                  controller: addressController,
                                  style: TextStyle(color: Colors.black),
                                  decoration: InputDecoration(
                                      labelText: 'Address',
                                      hintText: "Address",
                                      hintStyle: TextStyle(color: Colors.black),
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(5.0)),
                                      icon: Icon(
                                        Icons.location_city,
                                        color: Colors.orange,
                                      )),
                                ),
                                SizedBox(height: 15),
                                new Container(
                                  height: 50,
                                  width: 200,
                                  child: DialogButton(
                                    child: Text(
                                      "Sign Up",
                                      style: new TextStyle(
                                        fontSize: 30,
                                        color: AppTheme.darkerText,
                                        fontWeight: FontWeight.w700,
                                      ),
                                    ),
                                    onPressed: () => _handleSignUp(),
                                    gradient: LinearGradient(colors: [
                                      Colors.green,
                                      Colors.lightBlue
                                    ]),
                                  ),
                                )
                              ],
                            ),
                          ),
                        ],
                      ),
                    )
                  ],
                ),
              ))
            ],
          ),
        ),
      ),
    );
  }
}
