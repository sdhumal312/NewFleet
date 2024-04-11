import 'package:flutter/material.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:shared_preferences/shared_preferences.dart';

class WorkOrderMainData extends KFDrawerContent {
  WorkOrderMainData({
    Key key,
  });

  @override
  _WorkOrderMainDataState createState() => _WorkOrderMainDataState();
}

class _WorkOrderMainDataState extends State<WorkOrderMainData>
    with TickerProviderStateMixin {
  AnimationController animationController;
  double _width;
  Size size;
  double _height;
  int time = 1000;
  String email;
  String userId;
  String companyName;
  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      userId = prefs.getString("userId");
      email = prefs.getString("email");
      companyName = prefs.getString("companyName");
    });
  }

  @override
  void initState() {
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
    getSessionData();
  }

  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      body: Stack(
        children: [],
      ),
    );
  }
}
