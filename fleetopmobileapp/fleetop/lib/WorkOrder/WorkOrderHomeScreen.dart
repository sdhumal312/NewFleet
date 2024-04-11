import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/SearchScreen/SearchScreen.dart';
import 'package:fleetop/WorkOrder/WOSearch.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'CreateWorkOrder/createWorkOrder.dart';
import 'WorkOrderStatusDetails.dart';

class WorkOrderHomeScreen extends KFDrawerContent {
  WorkOrderHomeScreen({
    Key key,
  });

  @override
  _WorkOrderHomeScreenState createState() => _WorkOrderHomeScreenState();
}

class _WorkOrderHomeScreenState extends State<WorkOrderHomeScreen>
    with TickerProviderStateMixin {
  AnimationController animationController;
  double _width;
  Size size;
  double _height;
  int time = 1000;
  String email;
  String userId;
  String companyName;
  bool isAllowToCreateWorkOrder = false;
  List<Items> itemsList = new List<Items>();
  List<String> permission = new List();
  makeAlist() {
    Items item1 = new Items(
        id: 1, title: "Search Work Order", img: "assets/img/search.png");
    Items item2 = new Items(
      id: 2,
      title: "Create Work Order",
      img: "assets/img/work-order-icon.png",
    );
    Items item3 = new Items(
      id: 3,
      title: "General Search",
      img: "assets/img/searchIconnew.png",
    );
    Items item4 = new Items(
      id: 4,
      title: "All WO Status Wise",
      img: "assets/img/workorderstatus.png",
    );
    itemsList.add(item1);
    if (isAllowToCreateWorkOrder) {
      itemsList.add(item2);
    }
    itemsList.add(item3);
    itemsList.add(item4);
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      userId = prefs.getString("userId");
      email = prefs.getString("email");
      companyName = prefs.getString("companyName");
      permission = prefs.getStringList("permission");
      isAllowToCreateWorkOrder =
          WorkOrderUtility.hasAuthority(permission, "ADD_WORKORDER");
    });
    makeAlist();
  }

  @override
  void initState() {
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
    getSessionData();
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        // backgroundColor: Color(0xff7a6fe9),
        body: Container(
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(35.0),
          topRight: const Radius.circular(35.0),
        ),
        gradient: new LinearGradient(
            colors: [
              Color(0xFF4A00E0),
              Color(0xFF4A00E0),
              // Color(0xFF57d0eb), Color(0xFF57d0eb)
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: Stack(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 45),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
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
                  Container(
                    margin: EdgeInsets.only(right: _width / 2 - 90),
                    child: Text(
                      'Work Order',
                      style: GoogleFonts.openSans(
                          textStyle: TextStyle(
                              color: Colors.white,
                              fontSize: 22,
                              fontWeight: FontWeight.w600)),
                    ),
                  ),
                ],
              ),
            ),
          ),
          SizedBox(
            height: 40,
          ),
          createWorkOrderBody(),
        ],
      ),
    ));
  }

  Widget createWorkOrderBody() {
    return Container(
        margin: EdgeInsets.only(top: 120, left: 2, right: 2, bottom: 0),
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: new BoxDecoration(
          borderRadius: new BorderRadius.only(
            topLeft: const Radius.circular(45.0),
            topRight: const Radius.circular(45.0),
          ),
          gradient: new LinearGradient(
              colors: [Colors.white, Colors.white],
              begin: const FractionalOffset(0.0, 0.0),
              end: const FractionalOffset(1.0, 1.0),
              stops: [0.0, 1.0],
              tileMode: TileMode.clamp),
        ),
        child: Container(
            margin: EdgeInsets.only(top: 40, left: 2, right: 2, bottom: 0),
            child: buildGriedView()));
  }

  Widget buildGriedView() {
    List<Items> myList = itemsList;
    return Container(
      child: GridView.count(
          childAspectRatio: 1.0,
          padding: EdgeInsets.only(left: 16, right: 16),
          crossAxisCount: 2,
          crossAxisSpacing: 18,
          mainAxisSpacing: 18,
          children: myList.map((data) {
            return InkWell(
              onTap: () {
                navigateScreen(data.id);
              },
              child: Container(
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10),
                    border: Border.all(color: Color(0xFF4A00E0))),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    Image.asset(
                      data.img,
                      width: 120,
                      height: 120,
                    ),
                    SizedBox(
                      height: 4,
                    ),
                    Text(
                      data.title,
                      style: GoogleFonts.openSans(
                          textStyle: TextStyle(
                              color: Colors.black,
                              fontSize: 16,
                              fontWeight: FontWeight.w600)),
                    ),
                  ],
                ),
              ),
            );
          }).toList()),
    );
  }

  Widget createGridItem(int position) {
    var color = Colors.white;
    var iconData = Icons.add;
    var iconColor = Colors.amberAccent;
    var str = "Search";
    switch (position) {
      case 0:
        color = Colors.white;
        iconData = Icons.search;
        iconColor = Colors.blueAccent;
        str = "\tSearch Work Order";
        break;
      case 1:
        color = Colors.white;
        str = "\tCreate Work Order";
        iconData = Icons.add;
        iconColor = Colors.amberAccent;
        break;
      case 2:
        color = Colors.white;
        str = "WO Report Search";
        iconData = Icons.search;
        iconColor = Colors.greenAccent;
        break;
      case 3:
        color = Colors.white;
        str = "All WO Status Wise";
        iconData = Icons.info;
        iconColor = Colors.pinkAccent;
        break;
      case 4:
        color = Colors.white;
        str = "WO Report Search" + "\n" + "\t\t Payment";
        iconData = FontAwesomeIcons.amazonPay;
        iconColor = Colors.cyanAccent;
        break;
    }

    return Builder(builder: (context) {
      return Container(
        height: 200,
        color: Colors.cyanAccent,
        margin: const EdgeInsets.only(left: 3, right: 3, bottom: 3, top: 3),
        child: Card(
          elevation: 5,
          color: color,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(12)),
          ),
          child: InkWell(
            onTap: () {
              if (position == 0) {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => WOSearch()),
                );
              } else if (position == 1) {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => WorkOrderStatusDetails()),
                );
              } else if (position == 2) {
                // Navigator.push(
                //   context,
                //   MaterialPageRoute(builder: (context) => RegionAndSubregionForDashboardDeliveryBusinessLoss()),
                // );
              } else if (position == 3) {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => WorkOrderStatusDetails()),
                );
              } else if (position == 4) {
                // Navigator.push(
                //   context,
                //   MaterialPageRoute(builder: (context) => RegionAndSubregionForOutstandingPayment()),
                // );
              }
            },
            child: Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
                  Container(
                    // color: Colors.cyan,
                    padding: const EdgeInsets.all(15.0),
                    decoration: new BoxDecoration(
                      shape: BoxShape.circle,
                      color: iconColor,
                    ),
                    child: new Icon(
                      iconData,
                      size: 40,
                      color: Colors.white,
                    ),
                  ),
                  Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(
                        str,
                        style: TextStyle(
                            color: Colors.black,
                            fontSize: 16.0,
                            fontWeight: FontWeight.w900,
                            fontFamily: "WorkSansBold"
                            //color: Colors.white),
                            ),
                      )),
                  Divider(
                    height: 05,
                    color: Colors.grey,
                  ),
                  // Padding(
                  //     padding: const EdgeInsets.all(8.0),
                  //     child: Text(
                  //       str,
                  //       style: TextStyle(
                  //           color: Colors.black,
                  //           fontSize: 17.0,
                  //           fontWeight: FontWeight.w900,
                  //           fontFamily: "WorkSansBold"
                  //           //color: Colors.white),
                  //           ),
                  //     ))
                ],
              ),
            ),
          ),
        ),
      );
    });
  }

  navigateScreen(int id) {
    switch (id) {
      case 1:
        Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => SearchScreen(
                    isWOSearch: true,
                  )),
        );
        break;
      case 2:
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => CreateWorkOrder()),
        );
        break;
      case 3:
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => SearchScreen()),
        );
        break;
      case 4:
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => WorkOrderStatusDetails()),
        );
        break;
      default:
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => WOSearch()),
        );
        break;
    }
  }
}

class Items {
  String title;
  String subtitle;
  String event;
  String img;
  int id;
  Items({this.title, this.subtitle, this.event, this.img, this.id});
}
