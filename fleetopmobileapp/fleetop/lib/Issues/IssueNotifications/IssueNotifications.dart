import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

class IssueNotifications extends StatefulWidget {
  final Map NotiData;

  IssueNotifications({Key key, this.NotiData}) : super(key: key);

  @override
  _IssueNotificationsState createState() => new _IssueNotificationsState();
}

class _IssueNotificationsState extends State<IssueNotifications> {
  SharedPreferences sharedPreferences;
  List notificationDetailsList = new List();
  double _width;
  Size size;
  double _height;
  String companyId;
  String userId;
  var ISSUE_NOTIF_IDENTIFIER = 1;
  int pageNumber = 0;

  @override
  void initState() {
    getSessionData();
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    getIssueNotificationDetails();
  }

  getIssueNotificationDetails() async {
    var outData = {
      "pageNo": pageNumber.toString(),
      "companyId": companyId,
      "userId": userId,
      "moduleIdentifier": ISSUE_NOTIF_IDENTIFIER.toString()
    };
    var res = await ApiCall.getDataFromApi(
        URI.GET_MOB_NOTIFICATION_DETAILS, outData, URI.LIVE_URI, context);
    if (res != null && res["mobileNotificationList"] != null) {
      setState(() {
        notificationDetailsList = res["mobileNotificationList"];
      });
    } else {
      FlutterAlert.onErrorAlert(context, "No Notifications Found !", "Info");
    }
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return RefreshIndicator(
        onRefresh: () => getIssueNotificationDetails(),
        child: Scaffold(
            appBar: GradientAppBar(
              centerTitle: true,
              gradient: LinearGradient(
                  colors: [Color(0xFF8E2DE2), Color(0xFF4A00E0)]),
              iconTheme: new IconThemeData(color: Colors.white),
              title: const Text(
                "Notifications",
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 20.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold"),
              ),
              actions: <Widget>[],
            ),
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
            )));
  }

  Widget renderButtons() {
    if (notificationDetailsList != null && notificationDetailsList.isNotEmpty) {
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
                  if (pageNumber != 0) {
                    pageNumber = pageNumber - 1;
                    getIssueNotificationDetails();
                  }
                },
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
                color: Colors.greenAccent,
                child: IconButton(
                  onPressed: () {
                    if (pageNumber != 0) {
                      pageNumber = pageNumber - 1;
                      getIssueNotificationDetails();
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
                  getIssueNotificationDetails();
                },
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
                color: Colors.greenAccent,
                child: IconButton(
                  onPressed: () {
                    pageNumber = pageNumber + 1;
                    getIssueNotificationDetails();
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
    if (notificationDetailsList != null && notificationDetailsList.isNotEmpty) {
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
    List<Widget> notiList = new List<Widget>();
    for (int i = 0; i < notificationDetailsList.length; i++) {
      var data = notificationDetailsList[i];
      notiList.add(slidableNotiData(data, i));
    }
    return notiList;
  }

  Widget slidableNotiData(Map data, int index) {
    String notiData = data["notification"];
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
            title: new Text("Issue Notification ",
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 15.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold")),
            subtitle: new Text(notiData,
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 15.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold")),
          ),
        ),
        // secondaryActions: <Widget>[
        //   new IconSlideAction(
        //     caption: 'Delete ?',
        //     color: Colors.red,
        //     icon: Icons.delete,
        //     onTap: () => showAlert(
        //         data, index, context, "Delete This Notification ?", "Info"),
        //   ),
        // ],
      ),
    );
  }

  showAlert(
      Map data, int index, BuildContext context, String mes, String title) {
    Alert(
      context: context,
      type: AlertType.info,
      title: title,
      desc: mes,
      buttons: [
        DialogButton(
          child: Text(
            "Delete",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          //  onPressed: () => addInDeleteList(data, index),
          gradient: LinearGradient(colors: [
            Color.fromRGBO(116, 116, 191, 1.0),
            Color.fromRGBO(52, 138, 199, 1.0)
          ]),
        ),
        DialogButton(
          child: Text(
            "Cancel",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => Navigator.pop(context),
          gradient: LinearGradient(colors: [
            Color.fromRGBO(116, 116, 191, 1.0),
            Color.fromRGBO(52, 138, 199, 1.0)
          ]),
        )
      ],
    ).show();
  }
}
