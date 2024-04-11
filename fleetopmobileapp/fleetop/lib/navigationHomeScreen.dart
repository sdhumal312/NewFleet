import 'dart:io';

import 'package:fleetop/Dashboard/dashboard.dart';
import 'package:fleetop/FuelEntry/fuelentry.dart';
import 'package:fleetop/Home_screen/homescreen.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/customDrawer/drawerUserController.dart';
import 'package:fleetop/customDrawer/homeDrawer.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';

class NavigationHomeScreen extends StatefulWidget {
  NavigationHomeScreen({this.loginData});
  final Map loginData;
  
  @override
  _NavigationHomeScreenState createState() => _NavigationHomeScreenState(loginData : loginData);
}

class _NavigationHomeScreenState extends State<NavigationHomeScreen> {
  _NavigationHomeScreenState({this.loginData});
  final Map loginData;
  Widget screenView;
  DrawerIndex drawerIndex;
  AnimationController sliderAnimationController;

  @override
  void initState() {
    drawerIndex = DrawerIndex.HOME;
    screenView = HomeScreen();
    super.initState();
  }
 
  Future<bool> _onBackPressed(){
   Alert(
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
           gradient: LinearGradient(colors: [
           Colors.green,
           Colors.deepPurple
          ]),
        ),
        DialogButton(
          child: Text(
            "No",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => Navigator.pop(context),
          gradient: LinearGradient(colors: [
            Colors.red,
            Colors.black
          ]),
        )
      ],
    ).show();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
       onWillPop: _onBackPressed,
      child : Container(
      color: AppTheme.nearlyWhite,
      child: SafeArea(
        top: false,
        bottom: false,
        child: Scaffold(
          backgroundColor: AppTheme.nearlyWhite,
          body: DrawerUserController(
            screenIndex: drawerIndex,
            drawerWidth: MediaQuery.of(context).size.width * 0.75,
            animationController: (AnimationController animationController) {
              sliderAnimationController = animationController;
            },
            onDrawerCall: (DrawerIndex drawerIndexdata) {
              changeIndex(drawerIndexdata);
            },
            screenView: screenView,
          ),
        ),
      ),
      ),
    );
  }

  void changeIndex(DrawerIndex drawerIndexdata) {
    if (drawerIndex != drawerIndexdata) {
      drawerIndex = drawerIndexdata;
      if (drawerIndex == DrawerIndex.HOME) {
        setState(() {
          screenView = HomeScreen();
        });
      } 
      else if (drawerIndex == DrawerIndex.FUELENTRY) {
        setState(() {
          screenView = FuelEntry();
        });
       } 
      else if (drawerIndex == DrawerIndex.DASHBOARD) {
        setState(() {
          screenView = Dashboard();
        });
      } 
      else {
        //do in your way......
      }
    }
  }
}
