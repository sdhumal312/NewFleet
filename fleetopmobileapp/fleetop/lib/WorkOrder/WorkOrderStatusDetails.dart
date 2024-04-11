import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../appTheme.dart';
import 'WOSearch.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:flip_box_bar/flip_box_bar.dart';

import 'WorkOrderAllStatusScreens/CompletedStatusOrder.dart';
import 'WorkOrderAllStatusScreens/InProcessStatusOrder.dart';
import 'WorkOrderAllStatusScreens/OnHoldStatusOrder.dart';
import 'WorkOrderAllStatusScreens/OpenStatusOrder.dart';
import 'WorkOrderHomeScreen.dart';
import 'WorkOrderUtility.dart';

class WorkOrderStatusDetails extends StatefulWidget {
  @override
  _WorkOrderStatusDetailsState createState() => _WorkOrderStatusDetailsState();
}

class _WorkOrderStatusDetailsState extends State<WorkOrderStatusDetails> {
  String companyId;
  String userId;
  String email;
  int currentIndex = 0;

  @override
  void initState() {
    super.initState();
    getSessionData();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
  }

  Widget getBody(int cv) {
    if (currentIndex == 0) {
      WorkOrderUtility.pageNumber = 1;
      return Container(
        child: OpenStatusOrder(),
      );
    } else if (currentIndex == 1) {
      WorkOrderUtility.pageNumber = 1;
      return Container(
        child: InProcessStatusOrder(),
      );
    } else if (currentIndex == 2) {
      WorkOrderUtility.pageNumber = 1;
      return Container(
        child: OnHoldStatusOrder(),
      );
    } else if (currentIndex == 3) {
      WorkOrderUtility.pageNumber = 1;
      return Container(
        child: CompletedStatusOrder(),
      );
    } else {
      WorkOrderUtility.pageNumber = 1;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: GradientAppBar(
      //   centerTitle: true,
      //   gradient: LinearGradient(colors: [
      //     Color(0xff7a6fe9),
      //     AppTheme.col2,
      //   ]),
      //   iconTheme: new IconThemeData(color: Colors.white),
      //   title: const Text(
      //     "Work Order Details",
      //     style: TextStyle(
      //         color: Colors.white,
      //         fontSize: 20.0,
      //         fontWeight: FontWeight.bold,
      //         fontFamily: "WorkSansBold"),
      //   ),
      // ),
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
                    icon: Icons.open_in_browser,
                    text: 'Open',
                  ),
                  GButton(
                    hoverColor: Colors.blue,
                    icon: FontAwesomeIcons.procedures,
                    text: 'In Proc',
                  ),
                  GButton(
                    hoverColor: Colors.deepOrange,
                    icon: FontAwesomeIcons.stopCircle,
                    text: 'On Hold',
                  ),
                  GButton(
                    hoverColor: Colors.red,
                    icon: Icons.done,
                    text: 'Completed',
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
