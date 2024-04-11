import 'dart:async';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:fleetop/style/theme.dart' as Theme;

class UpdateForce extends StatefulWidget {
  @override
  _UpdateForceState createState() => _UpdateForceState();
}
class _UpdateForceState extends State<UpdateForce> {
  @override
  void initState() {
    super.initState();
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
                    Theme.Colors.loginGradientStart,
                    Theme.Colors.loginGradientEnd
                  ],
                  begin: const FractionalOffset(0.0, 0.0),
                  end: const FractionalOffset(1.0, 1.0),
                  stops: [0.0, 1.0],
                  tileMode: TileMode.clamp),
            ),
            child: Column(
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(top: 120.0),
                  width: 400,
                  height: 400,
                  child: SvgPicture.asset(
                    'assets/svgs/forceupdate.svg',
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(top: 2.0),
                  child: Container(
                    padding: EdgeInsets.only(top: 80.0),
                    child: Text(
                      "Your App is Outdated !",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: 20.0,
                          fontFamily: "WorkSansSemiBold"),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(top: 20.0),
                  child: Container(
                    child: RaisedButton(
                        color: Colors.amber,
                        shape: RoundedRectangleBorder(
                            borderRadius:
                                BorderRadius.all(Radius.circular(40.0))),
                        child: Padding(
                          padding: const EdgeInsets.symmetric(
                              vertical: 10.0, horizontal: 42.0),
                          child: Text(
                            "UPDATE NOW",
                            style: TextStyle(
                                color: Colors.white,
                                fontSize: 25.0,
                                fontFamily: "WorkSansBold"),
                          ),
                        ),
                        onPressed: () => checkForUpdate()),
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}

checkForUpdate() async {
  String url =
      "https://play.google.com/store/apps/details?id=com.ivgroup.fleetop";
  if (await canLaunch(url)) {
    await launch(url);
  } else {
    throw 'Could not open the App.';
  }
}
