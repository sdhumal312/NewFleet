import 'package:bottom_navy_bar/bottom_navy_bar.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:fleetop/navigationHomeScreen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../fleetopuriconstant.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';

import 'TripsheetCreate.dart';

class TripsheetHome extends KFDrawerContent {
  @override
  _TripsheetHomeState createState() => _TripsheetHomeState();
}

class _TripsheetHomeState extends State<TripsheetHome> with TickerProviderStateMixin {
   double _height,_width;

  void _incrementCounter() {
    setState(() {
    });
  }

  @override
  Widget build(BuildContext context) {
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                ClipRRect(
                  borderRadius: BorderRadius.all(Radius.circular(32.0)),
                  child: Material(
                    shadowColor: Colors.transparent,
                    color: Colors.transparent,
                    child: IconButton(
                      icon: Icon(
                        Icons.menu,
                        color: Colors.black,
                      ),
                      onPressed: widget.onMenuPressed,
                    ),
                  ),
                ),
                Center(
                  child: Padding(
                    padding: const EdgeInsets.only(top: 4),
                    child: Text(
                      "Trip Sheet",
                      style: new TextStyle(
                        fontSize: 22,
                        color: AppTheme.darkText,
                        fontWeight: FontWeight.w700,
                      ),
                    ),
                  ),
                ),

              ],
            ),

            MaterialButton(
              highlightColor: Colors.transparent,
              color: Colors.pink,
              child: Padding(
                padding: const EdgeInsets.symmetric(
                    vertical: 10.0, horizontal: 42.0),
                child: Text(
                  "Create Tripsheet",
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: 25.0,
                      fontFamily: "WorkSansBold"),
                ),
              ),
              onPressed: () => save(),
              //openHomePage()
            )
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
  void save()
  {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => TripsheetCreate()),
    );
  }
}
