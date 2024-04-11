import 'dart:io';

import 'package:fleetop/LoginPage/signupscreen.dart';
import 'package:fleetop/LoginPage/validations.dart';
import 'package:fleetop/Main_Drawer/main_drawer.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fleetop/style/theme.dart' as MyTheme;
import 'package:shimmer/shimmer.dart';

import '../flutteralert.dart';
import '../navigationHomeScreen.dart';

class LoginScreen extends StatefulWidget {
  @override
  LoginScreenState createState() => new LoginScreenState();
}

class LoginScreenState extends State<LoginScreen> {
  BuildContext context;
  bool _obscureTextLogin = true;
  int time = 1000;
  var companyCode = TextEditingController();
  var email = TextEditingController();
  var password = TextEditingController();
  FocusNode companyCodeFocus = new FocusNode();
  FocusNode emailFocus = new FocusNode();
  FocusNode passFocus = new FocusNode();

  Future validateSession() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    bool sendOTP = preferences.getBool("sendOTP");
    if (sendOTP == false) {
      Navigator.of(context).pushReplacement(
          new MaterialPageRoute(builder: (context) => MainWidget()));
    }
  }

  @override
  void initState() {
    validateSession();
    super.initState();
  }

  void _handleSubmitted() {
    UserData user = new UserData();
    UserAuth userAuth = new UserAuth();
    print(companyCode);
    print(companyCode.text);
    if (companyCode.text == "") {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Company Code", "Error");
      return;
    }

    if (email.text == "") {
      FlutterAlert.onErrorAlert(context, "Please Enter Valid Email", "Error");
      return;
    }

    if (password.text == "") {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Password", "Error");
      return;
    }

    user.companyCode = companyCode.text;
    user.userName = email.text;
    user.password = password.text;

    userAuth.verifyUser(user, context).then((onValue) {
      if (onValue != null) {
        if (onValue['sendOTP'] == true) {
          Navigator.of(context).pushReplacement(
              new MaterialPageRoute(builder: (context) => new SignUpScreen()));
        } else if (onValue['mobileUser'] != null) {
          Navigator.of(context).pushReplacement(
              new MaterialPageRoute(builder: (context) => new MainWidget()));
        } else {
          print("else part");
        }
      }
    }).catchError((e) {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support", "Error");
    });
  }

  void _toggleLogin() {
    setState(() {
      _obscureTextLogin = !_obscureTextLogin;
    });
  }

  Future<bool> _onBackPressed() async => Alert(
        context: context,
        type: AlertType.info,
        title: "Exit Fleetop App",
        desc: "Do you want to Exit Fleetop App !",
        buttons: [
          DialogButton(
            child: Text(
              "Yes",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => exit(0),
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

  @override
  Widget build(BuildContext context) {
    var _width = MediaQuery.of(context).size.width;
    this.context = context;
    return WillPopScope(
      onWillPop: _onBackPressed,
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            decoration: BoxDecoration(
              color: Colors.white,
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.max,
              children: <Widget>[
                new ClipPath(
                  clipper: MyClipper(),
                  child: Container(
                    decoration: BoxDecoration(
                      image: new DecorationImage(
                        image: new AssetImage("assets/images/full-bloom.png"),
                        fit: BoxFit.cover,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: EdgeInsets.only(top: 20.0, bottom: 100.0),
                    child: Column(
                      children: <Widget>[
                        new Container(
                            margin: EdgeInsets.only(top: 20.0),
                            child: Shimmer.fromColors(
                                highlightColor: Colors.red,
                                baseColor: Colors.orange,
                                period: Duration(milliseconds: time),
                                child: Center(
                                  child: Image.asset(
                                    "assets/img/FleetopLogo.png",
                                    width: 300,
                                    height: 200,
                                  ),
                                )))
                      ],
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 40.0),
                  child: Text(
                    "Company Code",
                    style: TextStyle(color: Colors.grey, fontSize: 16.0),
                  ),
                ),
                Container(
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.grey.withOpacity(0.5),
                      width: 1.0,
                    ),
                    borderRadius: BorderRadius.circular(20.0),
                  ),
                  margin: const EdgeInsets.symmetric(
                      vertical: 10.0, horizontal: 20.0),
                  child: Row(
                    children: <Widget>[
                      new Padding(
                        padding: EdgeInsets.symmetric(
                            vertical: 10.0, horizontal: 15.0),
                        child: Icon(
                          Icons.people_outline,
                          color: Colors.grey,
                        ),
                      ),
                      Container(
                        height: 30.0,
                        width: 1.0,
                        color: Colors.grey.withOpacity(0.5),
                        margin: const EdgeInsets.only(left: 00.0, right: 10.0),
                      ),
                      new Expanded(
                        child: TextField(
                          onSubmitted: (text) {
                            companyCodeFocus.unfocus();
                            FocusScope.of(context).requestFocus(emailFocus);
                          },
                          focusNode: companyCodeFocus,
                          controller: companyCode,
                          decoration: InputDecoration(
                            border: InputBorder.none,
                            hintText: 'Enter your Company Code',
                            hintStyle: TextStyle(color: Colors.grey),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 40.0),
                  child: Text(
                    "Email",
                    style: TextStyle(color: Colors.grey, fontSize: 16.0),
                  ),
                ),
                Container(
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.grey.withOpacity(0.5),
                      width: 1.0,
                    ),
                    borderRadius: BorderRadius.circular(20.0),
                  ),
                  margin: const EdgeInsets.symmetric(
                      vertical: 10.0, horizontal: 20.0),
                  child: Row(
                    children: <Widget>[
                      new Padding(
                        padding: EdgeInsets.symmetric(
                            vertical: 10.0, horizontal: 15.0),
                        child: Icon(
                          Icons.person_outline,
                          color: Colors.grey,
                        ),
                      ),
                      Container(
                        height: 30.0,
                        width: 1.0,
                        color: Colors.grey.withOpacity(0.5),
                        margin: const EdgeInsets.only(left: 00.0, right: 10.0),
                      ),
                      new Expanded(
                        child: TextField(
                          onSubmitted: (text) {
                            emailFocus.unfocus();
                            FocusScope.of(context).requestFocus(passFocus);
                          },
                          focusNode: emailFocus,
                          controller: email,
                          decoration: InputDecoration(
                            border: InputBorder.none,
                            hintText: 'Enter your Email',
                            hintStyle: TextStyle(color: Colors.grey),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 40.0),
                  child: Text(
                    "Password",
                    style: TextStyle(color: Colors.grey, fontSize: 16.0),
                  ),
                ),
                Container(
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.grey.withOpacity(0.5),
                      width: 1.0,
                    ),
                    borderRadius: BorderRadius.circular(20.0),
                  ),
                  margin: const EdgeInsets.symmetric(
                      vertical: 10.0, horizontal: 20.0),
                  child: Row(
                    children: <Widget>[
                      new Padding(
                        padding: EdgeInsets.symmetric(
                            vertical: 10.0, horizontal: 15.0),
                        child: Icon(
                          Icons.lock_open,
                          color: Colors.grey,
                        ),
                      ),
                      Container(
                        height: 30.0,
                        width: 1.0,
                        color: Colors.grey.withOpacity(0.5),
                        margin: const EdgeInsets.only(left: 00.0, right: 10.0),
                      ),
                      new Expanded(
                        child: TextField(
                          onSubmitted: (text) {
                            passFocus.unfocus();
                          },
                          focusNode: passFocus,
                          controller: password,
                          obscureText: _obscureTextLogin,
                          decoration: InputDecoration(
                            border: InputBorder.none,
                            hintText: 'Enter your password',
                            hintStyle: TextStyle(color: Colors.grey),
                            suffixIcon: GestureDetector(
                              onTap: _toggleLogin,
                              child: Icon(
                                _obscureTextLogin
                                    ? FontAwesomeIcons.eyeSlash
                                    : FontAwesomeIcons.eye,
                                size: 15.0,
                                color: Colors.black,
                              ),
                            ),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
                Center(
                  child: Container(
                      height: 50,
                      width: _width - 130,
                      margin: EdgeInsets.only(top: 20.0, left: 10),
                      decoration: new BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(5.0)),
                        gradient: new LinearGradient(
                            colors: [
                              MyTheme.Colors.loginGradientEnd,
                              MyTheme.Colors.loginGradientStart
                            ],
                            begin: const FractionalOffset(0.2, 0.2),
                            end: const FractionalOffset(1.0, 1.0),
                            stops: [0.0, 1.0],
                            tileMode: TileMode.clamp),
                      ),
                      child: MaterialButton(
                        highlightColor: Colors.transparent,
                        splashColor: MyTheme.Colors.loginGradientEnd,
                        child: Padding(
                          padding: const EdgeInsets.symmetric(
                              vertical: 10.0, horizontal: 42.0),
                          child: Text(
                            "LogIn",
                            style: TextStyle(
                                color: Colors.white,
                                fontSize: 25.0,
                                fontFamily: "WorkSansBold"),
                          ),
                        ),
                        onPressed: _handleSubmitted,
                      )),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class MyClipper extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    Path p = new Path();
    p.lineTo(size.width, 0.0);
    p.lineTo(size.width, size.height * 0.80);
    p.arcToPoint(
      Offset(0.0, size.height * 0.85),
      radius: const Radius.elliptical(20.0, 5.0),
      rotation: 0.0,
    );
    p.lineTo(0.0, 0.0);
    p.close();
    return p;
  }

  @override
  bool shouldReclip(CustomClipper oldClipper) {
    return true;
  }
}
