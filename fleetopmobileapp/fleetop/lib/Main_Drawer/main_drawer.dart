import 'dart:io';
import 'dart:typed_data';

import 'package:fleetop/Dashboard/dashboard.dart';
import 'package:fleetop/Driver/createDriverRenewal.dart';
import 'package:fleetop/FuelEntry/fuelentry.dart';
import 'package:fleetop/GeneralNotifications/GeneralNotifications.dart';
import 'package:fleetop/Home_screen/homescreen.dart';
import 'package:fleetop/Issues/createIssue.dart';

import 'package:fleetop/LoginPage/loginscreen.dart';
import 'package:fleetop/Main_Drawer/class_builder.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/PickAndDrop/createPickOrDrop.dart';
import 'package:fleetop/RenewalReminder/createRenewalReminder.dart';
import 'package:fleetop/SearchScreen/SearchScreen.dart';
import 'package:fleetop/Tripsheet/TripsheetCreate.dart';
import 'package:fleetop/WorkOrder/WorkOrderHomeScreen.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class MainWidget extends StatefulWidget {
  MainWidget({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MainWidgetState createState() => _MainWidgetState();
}

class _MainWidgetState extends State<MainWidget> with TickerProviderStateMixin {
  KFDrawerController _drawerController;
  String userFullName;
  String companyName;
  bool showPickAndDrop = false;
  bool showFuelEntryModule = false;
  bool showworkSummaryModule = false;
  bool showTripSheetModule = false;
  bool showTriprenewalReminderModule = false;
  bool showdriverRenewalModule = false;
  bool showIssuesModule = false;
  bool showWorkOrderModule = false;
  List<KFDrawerItem> drawerList = new List();
  List<String> permission = new List();
  FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin =
      new FlutterLocalNotificationsPlugin();
  final FirebaseMessaging firebaseMessaging = FirebaseMessaging();
  String tokenForNotification;
  String userId;
  String companyId;
  bool isImageExist = false;
  var accountGroupLogo;
  @override
  void initState() {
    verifyData();
    getSessionData();
    super.initState();
    ClassBuilder.registerClasses();
    _drawerController = KFDrawerController(
      initialPage: ClassBuilder.fromString('HomeScreen'),
      items: drawerList,
    );
  }

  Future<ByteData> loadAsset() async {
    return await rootBundle.load('assets/sounds/notificationSound.mp3');
  }

  showCustomNotification(Map<String, dynamic> notification) async {
    if (notification != null &&
        notification.isNotEmpty &&
        notification["notification"] != null &&
        notification["notification"]["body"] != null) {
      var dat = notification["notification"]["body"];
      String data_zero = notification["notification"]["title"];
      var android = new AndroidNotificationDetails(
          'channelId', 'channelName', 'channelDescription',
          importance: Importance.Max,
          priority: Priority.High,
          playSound: true,
          sound: RawResourceAndroidNotificationSound('notificationsound'),
          ticker: 'ticker');
      var iOS = new IOSNotificationDetails();
      var platform = new NotificationDetails(android, iOS);

      await flutterLocalNotificationsPlugin.show(
          0, data_zero, dat.toString(), platform);
      var pendingNotificationRequests =
          await flutterLocalNotificationsPlugin.pendingNotificationRequests();
      await flutterLocalNotificationsPlugin.show(
          0, data_zero, dat.toString(), platform);
    }
  }

  verifyData() {
    firebaseMessaging.getToken().then((token) async {
      setState(() {
        tokenForNotification = token;
      });
      print("tokenForNotification =$tokenForNotification");
      updateUserToken();
    });
  }

  updateUserToken() async {
    if (tokenForNotification != null && tokenForNotification.length > 0) {
      var data = {
        'userId': userId,
        'companyId': companyId,
        'tokenForNotification': tokenForNotification
      };
      await ApiCall.getDataWithoutLoader(
          URI.UPDATE_USER_TOKEN, data, URI.LIVE_URI);
    }
  }

  initiateNotification() {
    var android = new AndroidInitializationSettings("ic_launcher");
    var ios = new IOSInitializationSettings();
    var initializationSettings = new InitializationSettings(android, ios);
    flutterLocalNotificationsPlugin.initialize(initializationSettings);
    firebaseMessaging.configure(
        //onBackgroundMessage: myBackgroundMessageHandler,
        onMessage: (Map<String, dynamic> message) async {
      showCustomNotification(message);
    }, onResume: (Map<String, dynamic> message) async {
      showCustomNotification(message);
    }, onLaunch: (Map<String, dynamic> message) async {
      showCustomNotification(message);
    });
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    permission = prefs.getStringList("permission");
    print("permission =$permission");
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    accountGroupLogo = 'assets/companyLogos/${companyId.toString()}.png';

    //assetExist(accountGroupLogo);
    setState(() {});
    if (permission != null && permission.isNotEmpty) {
      if (permission != null && permission.contains("FLAVOR_FOUR_PRIVILEGE")) {
        setState(() {
          showPickAndDrop = true;
        });
      }
      if (permission.contains("VIEW_FUEL")) {
        setState(() {
          showFuelEntryModule = true;
        });
      }
      if (permission.contains("SHOW_WORK_SUMMARY")) {
        setState(() {
          showworkSummaryModule = true;
        });
      }
      if (permission.contains("VIEW_TRIPSHEET")) {
        setState(() {
          showTripSheetModule = true;
        });
      }
      if (permission.contains("VIEW_RENEWAL")) {
        setState(() {
          showTriprenewalReminderModule = true;
        });
      }
      if (permission.contains("ADDEDIT_DRIVER_REMINDER")) {
        setState(() {
          showdriverRenewalModule = true;
        });
      }
      if (permission.contains("VIEW_ISSUES")) {
        setState(() {
          showIssuesModule = true;
        });
      }
      setState(() {
        showWorkOrderModule =
            WorkOrderUtility.hasAuthority(permission, "VIEW_WORKORDER");
      });

      initiateNotification();
    }

    setState(() {
      userFullName =
          prefs.getString("firstName") + " " + prefs.getString("lastName");
    });

    setState(() {
      companyName = prefs.getString("companyName");
    });

    addItemstoList();
  }

  Future<bool> _onBackPressed() {
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
  }

  Future<bool> assetExist(String asset) {
    return rootBundle.load(asset).then((value) {
      setState(() {
        isImageExist = true;
      });
    }).catchError((err) {
      setState(() {
        isImageExist = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: _onBackPressed,
      child: Scaffold(
        body: KFDrawer(
          controller: _drawerController,
          header: Align(
            alignment: Alignment.centerLeft,
            child: Container(
              padding: EdgeInsets.symmetric(horizontal: 16.0),
              width: MediaQuery.of(context).size.width * 0.6,
              child: Scrollbar(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    Container(
                      child: Shimmer.fromColors(
                        highlightColor: Colors.black,
                        baseColor: Colors.cyan,
                        period: Duration(milliseconds: 1000),
                        child: Text(
                          companyName.toString(),
                          style: GoogleFonts.openSans(
                              textStyle: TextStyle(
                                  fontSize: 25, fontWeight: FontWeight.w600)),
                        ),
                      ),
                    ),
                    SizedBox(height: 02),
                    Container(
                      height: 120,
                      width: 120,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        boxShadow: <BoxShadow>[
                          // BoxShadow(
                          //     color: AppTheme.grey.withOpacity(0.6),
                          //     offset: Offset(2.0, 4.0),
                          //     blurRadius: 6),
                        ],
                      ),
                      child: ClipRRect(
                        borderRadius: BorderRadius.all(Radius.circular(60.0)),
                        child: Image.asset("assets/img/FleetopLogo.png"),
                      ),
                    ),
                    // Container(
                    //   margin: EdgeInsets.only(top: 10, right: 05),
                    //   child: Image.asset(
                    //     "assets/img/FleetopLogo.png",
                    //     width: 100,
                    //     height: 100,
                    //     fit: BoxFit.cover,
                    //     // height:200,
                    //   ),
                    // ),
                    Padding(
                        padding: const EdgeInsets.only(top: 8, left: 4),
                        child: Shimmer.fromColors(
                          highlightColor: Colors.black,
                          baseColor: Colors.cyan,
                          period: Duration(milliseconds: 1000),
                          child: Text(userFullName.toString(),
                              style: GoogleFonts.openSans(
                                  fontSize: 18, fontWeight: FontWeight.w600)),
                        )),
                  ],
                ),
              ),
            ),
          ),
          footer: KFDrawerItem(
            text: Text(
              'Log Out',
              style: GoogleFonts.openSans(
                  textStyle:
                      TextStyle(fontSize: 16, fontWeight: FontWeight.w600)),
            ),
            icon: Icon(
              Icons.power_settings_new,
              color: Colors.black,
            ),
            onPressed: () {
              navigateToLogin();
            },
          ),
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                AppTheme.drawerColor,
                AppTheme.drawerColor,
              ],
              tileMode: TileMode.repeated,
            ),
          ),
        ),
      ),
    );
  }

  bool disableWorkOrderForSomeGroups() {
    if (companyId == "33") {
      return false;
    }
    return true;
  }

  Future navigateToLogin() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.clear();
    Navigator.of(context).pushReplacement(
        new MaterialPageRoute(builder: (context) => LoginScreen()));
  }

  addItemstoList() {
    drawerList.add(KFDrawerItem.initWithPage(
      text: Text('Home',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600))),
      icon: Icon(Icons.home, color: Colors.black),
      page: HomeScreen(),
    ));
    drawerList.add(KFDrawerItem.initWithPage(
      text: Text('Notifications',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600))),
      icon: Icon(Icons.home, color: Colors.black),
      page: GeneralNotifications(),
    ));
    if (showFuelEntryModule) {
      drawerList.add(KFDrawerItem.initWithPage(
        text: Text(
          'Fuel Entry',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600)),
        ),
        icon: Icon(Icons.local_gas_station, color: Colors.black),
        page: FuelEntry(),
      ));
    }
    if (showworkSummaryModule) {
      drawerList.add(KFDrawerItem.initWithPage(
        text: Text('Work Summary',
            style: GoogleFonts.openSans(
                textStyle: TextStyle(
                    color: Colors.black,
                    fontSize: 16,
                    fontWeight: FontWeight.w600))),
        icon: Icon(Icons.dashboard, color: Colors.black),
        page: Dashboard(),
      ));
    }
    if (showTripSheetModule) {
      drawerList.add(KFDrawerItem.initWithPage(
        text: Text(
          'Tripsheet',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600)),
        ),
        icon: Icon(Icons.directions_bus, color: Colors.black),
        page: TripsheetCreate(),
      ));
    }
    if (showdriverRenewalModule) {
      drawerList.add(KFDrawerItem.initWithPage(
        text: Text(
          'Renewal Reminder',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600)),
        ),
        icon: Icon(Icons.alarm, color: Colors.black),
        page: CreateRenewalReminder(),
      ));
      drawerList.add(KFDrawerItem.initWithPage(
        text: Text(
          'Driver Renewal ',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600)),
        ),
        icon: Icon(Icons.person, color: Colors.black),
        page: CreateDriverRenewal(),
      ));
    }
    if (showPickAndDrop) {
      drawerList.add(KFDrawerItem.initWithPage(
        text: Text(
          'Pick Or Drop',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600)),
        ),
        icon: Icon(Icons.directions_car, color: Colors.black),
        page: CreatePickOrDrop(),
      ));
    }
    // drawerList.add(KFDrawerItem.initWithPage(
    //   text: Text(
    //     'Inventory',
    //     style: TextStyle(color: Colors.white),
    //   ),
    //   icon: Icon(Icons.person, color: Colors.white),
    //   page: ClassBuilder.fromString('FuelInventory'),
    // ));
    if (showIssuesModule) {
      drawerList.add(
        KFDrawerItem.initWithPage(
          text: Text(
            'Issues ',
            style: GoogleFonts.openSans(
                textStyle: TextStyle(
                    color: Colors.black,
                    fontSize: 16,
                    fontWeight: FontWeight.w600)),
          ),
          icon: Icon(Icons.warning, color: Colors.black),
          page: CreateIssue(),
        ),
      );
    }
    if (showWorkOrderModule && disableWorkOrderForSomeGroups()) {
      drawerList.add(
        KFDrawerItem.initWithPage(
          text: Text(
            'Work Order ',
            style: GoogleFonts.openSans(
                textStyle: TextStyle(
                    color: Colors.black,
                    fontSize: 16,
                    fontWeight: FontWeight.w600)),
          ),
          icon: Icon(Icons.add, color: Colors.black),
          page: WorkOrderHomeScreen(),
        ),
      );
    }
    drawerList.add(
      KFDrawerItem.initWithPage(
        text: Text(
          'Search',
          style: GoogleFonts.openSans(
              textStyle: TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.w600)),
        ),
        icon: Icon(Icons.search, color: Colors.black),
        page: SearchScreen(),
      ),
    );
  }
}
