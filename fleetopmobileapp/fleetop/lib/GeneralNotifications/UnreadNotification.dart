import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UnreadNotification extends StatefulWidget {
  @override
  _UnreadNotificationState createState() => _UnreadNotificationState();
}

class _UnreadNotificationState extends State<UnreadNotification> {
  String companyId;
  String userId;
  String email;
  List openList = new List();
  double _height;
  double _width;
  Size size;
  List notificationDataList = new List();
  Map data = new Map();
  int pageNumber = 1;
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
    onPressButton();
  }

  onPressButton() async {
    data = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'status': '1',
      'pageNumber': pageNumber.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.GET_UNREAD_NOTI, data, URI.LIVE_URI, context);
    if (response != null && response["notificationList"] != null) {
      if (response["notificationList"].length > 0) {
        setState(() {
          notificationDataList = response["notificationList"];
        });
      } else {
        setState(() {
          notificationDataList.clear();
        });
        FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
      }
    } else {
      setState(() {
        notificationDataList.clear();
      });
      FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
    }
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        body: WillPopScope(
      onWillPop: () {
        Navigator.pop(context);
        //  return viewedNotifications();
      },
      child: SingleChildScrollView(
        child: Column(
          children: <Widget>[renderNotification(), renderButtons()],
        ),
      ),
    ));
  }

  Widget renderButtons() {
    if (notificationDataList != null && notificationDataList.isNotEmpty) {
      return Container(
        margin: EdgeInsets.only(
          top: 10,
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: <Widget>[
            Container(
              margin: EdgeInsets.only(left: _width / 2 - 100, bottom: 10),
              child: RaisedButton(
                elevation: 5.0,
                onPressed: () {
                  if (pageNumber > 2) {
                    pageNumber = pageNumber - 1;
                    onPressButton();
                  }
                },
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
                color: Colors.greenAccent,
                child: IconButton(
                  onPressed: () {
                    if (pageNumber > 2) {
                      pageNumber = pageNumber - 1;
                      onPressButton();
                    }
                  },
                  icon: Icon(
                    Icons.arrow_back_ios,
                    size: 30,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.only(right: _width / 2 - 100, bottom: 10),
              child: RaisedButton(
                elevation: 5.0,
                onPressed: () {
                  pageNumber = pageNumber + 1;
                  onPressButton();
                },
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
                color: Colors.greenAccent,
                child: IconButton(
                  onPressed: () {
                    pageNumber = pageNumber + 1;
                    onPressButton();
                  },
                  icon: Icon(
                    Icons.arrow_forward_ios,
                    size: 30,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ],
        ),
      );
    } else {
      return Container();
    }
  }

  Widget renderNotification() {
    if (notificationDataList != null && notificationDataList.isNotEmpty) {
      return Card(
        shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(15.0)),
        borderOnForeground: true,
        elevation: 10.0,
        color: Color(0xFF4A00E0),
        child: Column(
          children: notiwidgetList(),
        ),
      );
    } else {
      return Container();
    }
  }

  List<Widget> notiwidgetList() {
    List<Widget> notiListwidget = new List<Widget>();
    for (int i = 0; i < notificationDataList.length; i++) {
      var dataS = notificationDataList[i];
      notiListwidget.add(slidableNotiData(dataS, i));
    }
    return notiListwidget;
  }

  String checkAndSet(String str) {
    print("str is =$str");
    if (str == null || str == "null") {
      return "--";
    }
    return str;
  }

  String maekNotificationString(Map data) {
    String str = "";
    String st1 = "";
    String st2 = "";
    String st3 = "";
    String st4 = "";
    try {
      if (data != null && data.isNotEmpty) {
        st1 = "Type        : " + data["txnTypeName"].toString();
        st2 = "Number   : " +
            checkAndSet(data["partRequisitionNumber"].toString());
        st3 = "Send By   : " + data["sendBy"].toString();
        st4 = "Msg          : " + data["alertMsg"].toString();
        str = st1 + "\n" + st2 + "\n" + st3 + "\n" + st4;
        return str;
      } else {
        return str;
      }
    } catch (e) {
      return str;
    }
  }

  Widget slidableNotiData(Map data, int index) {
    String str = maekNotificationString(data);
    return GestureDetector(
      onTap: () {},
      child: Slidable(
        actionPane: SlidableDrawerActionPane(),
        actionExtentRatio: 0.25,
        child: new Card(
          margin: EdgeInsets.only(top: 15, left: 10, right: 10),
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(15.0)),
          borderOnForeground: true,
          elevation: 10.0,
          color: Colors.black,
          child: new ListTile(
            isThreeLine: true,
            leading: Icon(
              Icons.notifications_active,
              color: Colors.white,
            ),
            title: new Text("Notification ",
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 15.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold")),
            subtitle: new Text(str,
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 15.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold")),
          ),
        ),
      ),
    );
  }
}
