import 'dart:ui';

import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:shimmer/shimmer.dart';
import 'package:url_launcher/url_launcher.dart';

class HomeScreen extends KFDrawerContent {
  HomeScreen({
    Key key,
  });

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with TickerProviderStateMixin {
  AnimationController animationController;
  double _width;
  Size size;
  double _height;
  int time = 1000;

  @override
  void initState() {
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
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

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      backgroundColor: AppTheme.white,
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
                  Row(
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
                      Padding(
                        padding: EdgeInsets.only(left: 100),
                        child: Text(
                          "Home",
                          style: new TextStyle(
                            fontSize: 22,
                            color: AppTheme.darkText,
                            fontWeight: FontWeight.w700,
                          ),
                        ),
                      )
                    ],
                  ),
                  Expanded(
                    child: FutureBuilder(
                      future: getData(),
                      builder: (context, snapshot) {
                        if (!snapshot.hasData) {
                          return SizedBox();
                        } else {
                          return Scaffold(
                            body: Container(
                              decoration: BoxDecoration(
                                color: Colors.blue.withOpacity(0.0),
                                image: DecorationImage(
                                  image: AssetImage("assets/img/backimage.jpg"),
                                  fit: BoxFit.cover,
                                ),
                              ),
                              child: Column(
                                children: <Widget>[
                                  new Container(
                                    height: size.height / 2,
                                    child: new Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.center,
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: <Widget>[
                                        new Center(
                                            child: new Image.asset(
                                          "assets/img/FleetopLogo.png",
                                          width: 300,
                                          height: 200,
                                        )),
                                        SizedBox(height: 20),
                                        new Center(
                                            child: Shimmer.fromColors(
                                          highlightColor: Colors.black,
                                          baseColor: Colors.red,
                                          period: Duration(milliseconds: time),
                                          child: FlatButton(
                                            onPressed: () {
                                              launch(
                                                  "mailto:<fleetopsupport@ivgroup.in>?subject=YOUR SUBJECT &body= YOUR CONTENT");
                                            },
                                            child: Text(
                                              "Mail Id :fleetopsupport@ivgroup.in",
                                              style: TextStyle(
                                                  fontSize: 21,
                                                  color: Colors.grey.shade300),
                                            ),
                                          ),
                                        ))
                                      ],
                                    ),
                                  ),
                                ],
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

  Widget appBar() {
    return SizedBox(
      height: AppBar().preferredSize.height,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Visibility(
            visible: true,
            child: Padding(
              padding: EdgeInsets.only(top: 8, left: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
              ),
            ),
          ),
          Visibility(
            visible: true,
            child: Expanded(
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.only(top: 4),
                  child: Text(
                    "Home",
                    style: new TextStyle(
                      fontSize: 22,
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ),
          ),
          Visibility(
            visible: true,
            child: Padding(
              padding: EdgeInsets.only(top: 2, right: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
                color: Colors.white,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
