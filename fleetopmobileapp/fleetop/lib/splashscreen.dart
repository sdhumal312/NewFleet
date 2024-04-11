import 'dart:async';
import 'package:fleetop/LoginPage/loginscreen.dart';
import 'package:fleetop/updateForce.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'AppAutoUpdate/AppUpdateConstant.dart';
import 'apicall.dart';

class AnimatedSplashScreen extends StatefulWidget {
  @override
  SplashScreenState createState() => new SplashScreenState();
}

class SplashScreenState extends State<AnimatedSplashScreen>
    with SingleTickerProviderStateMixin {
  var _visible = true;
  double currentVersionCode = AppUpdateConstant.appVersionCodeName;
  double versionCode = 0;

  AnimationController animationController;
  Animation<double> animation;

  startTime() async {
    var _duration = new Duration(seconds: 3);
    if (versionCode > currentVersionCode) {
      return new Timer(_duration, forceUpdatePage);
    } else {
      return new Timer(_duration, navigationPage);
    }
  }

  void navigationPage() {
    Navigator.of(context).pushReplacement(
        new MaterialPageRoute(builder: (context) => LoginScreen()));
  }

  void forceUpdatePage() {
    Navigator.of(context).pushReplacement(
        new MaterialPageRoute(builder: (context) => UpdateForce()));
  }

  checkAppVersion() async {
    try {
      double appVersionCode = 0.0;
      appVersionCode = await ApiCall.getPlayStoreVersion(appVersionCode);
      setState(() {
        versionCode = appVersionCode;
      });
      startTime();
    } catch (e) {
      startTime();
    }
  }

  @override
  void initState() {
    checkAppVersion();
    super.initState();
    animationController = new AnimationController(
        vsync: this, duration: new Duration(seconds: 2));
    animation =
        new CurvedAnimation(parent: animationController, curve: Curves.easeOut);

    animation.addListener(() => this.setState(() {}));
    animationController.forward();

    setState(() {
      _visible = !_visible;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        fit: StackFit.expand,
        children: <Widget>[
          Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height >= 775.0
                ? MediaQuery.of(context).size.height
                : 775.0,
            decoration: new BoxDecoration(
              gradient: new LinearGradient(
                  colors: [
                    Colors.yellow[50],
                    Colors.amber,
                  ],
                  begin: const FractionalOffset(0.0, 0.0),
                  end: const FractionalOffset(1.0, 1.0),
                  stops: [0.0, 1.0],
                  tileMode: TileMode.clamp),
            ),
            child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  new Image.asset(
                    "assets/img/FleetopLogo.png",
                    width: animation.value * 400,
                    height: animation.value * 400,
                  ),
                ]),
          ),
        ],
      ),
    );
  }
}
