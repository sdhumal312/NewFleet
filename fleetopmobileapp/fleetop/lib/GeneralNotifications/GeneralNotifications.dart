import 'dart:ui';

import 'package:fleetop/GeneralNotifications/ReadNotification.dart';
import 'package:fleetop/GeneralNotifications/SendNotification.dart';
import 'package:fleetop/GeneralNotifications/UnreadNotification.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';

class GeneralNotifications extends KFDrawerContent {
  GeneralNotifications({
    Key key,
  });

  @override
  _GeneralNotificationsState createState() => _GeneralNotificationsState();
}

class _GeneralNotificationsState extends State<GeneralNotifications>
    with TickerProviderStateMixin {
  AnimationController animationController;
  double _width;
  Size size;
  double _height;
  int time = 1000;
  int currentIndex = 0;
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

  Widget getBody(int cv) {
    if (currentIndex == 0) {
      return Container(
        child: UnreadNotification(),
      );
    } else if (currentIndex == 1) {
      return Container(
        child: ReadNotification(),
      );
    } else if (currentIndex == 2) {
      return Container(
        child: SendNotification(),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: GradientAppBar(
        gradient:
            LinearGradient(colors: [Color(0xFF8E2DE2), Color(0xFF4A00E0)]),
        iconTheme: new IconThemeData(color: Colors.white),
        centerTitle: true,
        title: Container(
          child: Row(
            children: [
              IconButton(
                icon: Icon(
                  Icons.menu,
                  color: Colors.white,
                  size: 25,
                ),
                onPressed: () {
                  widget.onMenuPressed();
                },
              ),
              SizedBox(
                width: 80,
              ),
              const Text(
                "General Notification",
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 20.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold"),
              ),
            ],
          ),
        ),
      ),
      body: getBody(currentIndex),
      bottomNavigationBar: Container(
        decoration: BoxDecoration(color: Colors.white, boxShadow: [
          BoxShadow(blurRadius: 20, color: Colors.black.withOpacity(.1))
        ]),
        child: SafeArea(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 15.0, vertical: 8),
            child: GNav(
                color: Colors.deepOrange,
                rippleColor: Colors.grey[300],
                hoverColor: Colors.grey[100],
                gap: 6,
                activeColor: Colors.black,
                iconSize: 24,
                padding: EdgeInsets.symmetric(horizontal: 20, vertical: 12),
                duration: Duration(milliseconds: 400),
                tabBackgroundColor: Colors.grey[100],
                tabs: [
                  GButton(
                    hoverColor: Colors.amber,
                    icon: Icons.notifications,
                    text: 'UnRead Notification',
                  ),
                  GButton(
                    hoverColor: Colors.blue,
                    icon: Icons.notifications,
                    text: 'Read Notification',
                  ),
                  GButton(
                    hoverColor: Colors.deepOrange,
                    icon: Icons.notifications,
                    text: 'Send Notification',
                  ),
                ],
                selectedIndex: currentIndex,
                onTabChange: (index) {
                  print("index =$index");
                  setState(() {
                    currentIndex = index;
                  });
                }),
          ),
        ),
      ),
    );
  }
}
