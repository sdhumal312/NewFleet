import 'package:fleetop/RenewalReminder/showRenewalReminder.dart';
import 'package:flutter/material.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../apicall.dart';
import '../appTheme.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';

class RenewalRemiderSearchDetails extends StatefulWidget {
  final List renewalReminderList;
  RenewalRemiderSearchDetails({
    Key key,
    this.renewalReminderList,
  }) : super(key: key);
  @override
  _RenewalRemiderSearchDetailsState createState() =>
      _RenewalRemiderSearchDetailsState();
}

class _RenewalRemiderSearchDetailsState
    extends State<RenewalRemiderSearchDetails> {
  String companyId;
  String userId;
  String email;
  double _width;
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

  @override
  Widget build(BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        appBar: GradientAppBar(
          centerTitle: true,
          gradient: LinearGradient(colors: [
            Color(0xff7a6fe9),
            AppTheme.col2,
          ]),
          iconTheme: new IconThemeData(color: Colors.white),
          title: const Text(
            "Renewal Reminder Details",
            style: TextStyle(
                color: Colors.white,
                fontSize: 20.0,
                fontWeight: FontWeight.bold,
                fontFamily: "WorkSansBold"),
          ),
        ),
        body: Stack(children: <Widget>[
          SingleChildScrollView(
            child: Column(
              children: renderData(),
            ),
          )
        ]));
  }

  List<Widget> renderData() {
    List<Widget> list = new List();
    if (widget.renewalReminderList != null &&
        widget.renewalReminderList.isNotEmpty) {
      for (int i = 0; i < widget.renewalReminderList.length; i++) {
        list.add(renderMainMenu(
            "RR-No : " +
                widget.renewalReminderList[i]["renewal_R_Number"].toString(),
            setElementInList(widget.renewalReminderList[i])));
      }
      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  Widget renderMainMenu(String title, List<Widget> wdList) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
      margin: EdgeInsets.only(left: 7, top: 8, right: 5),
      color: Color(0xff050859),
      child: ExpansionTile(
          trailing: Icon(Icons.keyboard_arrow_down, color: Colors.white),
          backgroundColor: Color(0xff050859),
          title: Text(
            title,
            style: TextStyle(
                fontFamily: "WorkSansSemiBold",
                fontSize: 17.0,
                fontWeight: FontWeight.w600,
                color: Colors.white),
          ),
          leading: Icon(Icons.info, color: Colors.white),
          children: wdList),
    );
  }

  Color getColor(int id) {
    if (id == 1) {
      return Colors.red;
    } else {
      return Colors.green;
    }
  }

  Color getColorString(String id) {
    if (id == 'Due Soon') {
      return Colors.yellow;
    } else if (id == 'Overdue') {
      return Colors.red;
    } else {
      return Colors.blue;
    }
  }

  Widget renderText(
      String ids, int id, String renType, String subRen, String renewalStatus) {
    Color c1 = getColorString(ids);
    Color c2 = getColor(id);
    return Container(
      margin: EdgeInsets.only(left: 7, top: 4, right: 5, bottom: 3),
      width: _width,
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(10.0),
          topRight: const Radius.circular(10.0),
          bottomLeft: const Radius.circular(10.0),
          bottomRight: const Radius.circular(10.0),
        ),
        gradient: new LinearGradient(
            colors: [
              Color(0xff050859),
              Color(0xff7a6fe9),
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: Column(
        children: [
          Text(
            "Renewal Types",
            style: TextStyle(
              fontSize: 18.0,
              fontWeight: FontWeight.w900,
              color: Colors.white,
            ),
          ),
          Text(
            ids ?? "",
            style: TextStyle(
              fontSize: 18.0,
              fontWeight: FontWeight.w900,
              color: c1,
            ),
          ),
          Text(
            renType ?? "" + " " + subRen ?? "",
            style: TextStyle(
              fontSize: 18.0,
              fontWeight: FontWeight.w900,
              color: Colors.black,
            ),
          ),
          Text(
            renewalStatus ?? "",
            style: TextStyle(
              fontSize: 18.0,
              fontWeight: FontWeight.w900,
              color: c2,
            ),
          ),
        ],
      ),
    );
  }

  List<Widget> setElementInList(Map fundData) {
    List<Widget> wlist = new List();

    wlist.add(
        tapableCard("RR-No:", fundData["renewal_R_Number"].toString(), true));

    wlist.add(
      tapableCard(
          "Vehicle:", fundData["vehicle_registration"].toString(), false),
    );
    wlist.add(renderText(
        fundData["renewal_dueRemDate"],
        fundData["renewal_staus_id"],
        fundData["renewal_type"],
        fundData["renewal_subtype"],
        fundData["renewal_status"]));
    wlist.add(
      tapableCard("Validity From:", fundData["renewal_from"].toString(), false),
    );
    wlist.add(
      tapableCard("Validity To:", fundData["renewal_to"].toString(), false),
    );
    wlist.add(
      tapableCard("Amount:", fundData["renewal_Amount"].toString(), false),
    );
    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
  }

  searchRenewalByNumber(String rrNumber) async {
    if (rrNumber != '') {
      var data = {
        'companyId': companyId,
        'renewalNumber': rrNumber,
        'userId': userId
      };
      var response = await ApiCall.getDataWithoutLoader(
          URI.SEARCH_RR_BY_NUMBER, data, URI.LIVE_URI);
      if (response != null) {
        if (response['renewalFound'] == true) {
          int renewalRemId = response['renewalId'];
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) =>
                    ShowRenewalReminder(renewalId: renewalRemId)),
          );
        } else {
          FlutterAlert.onErrorAlert(
              context, "Please Enter Valid Renewal Number !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid Renewal Number !", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Renewal Number !", "Error");
    }
  }

  Widget tapableCard(String title, String val, bool id) {
    return Container(
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(10.0),
          topRight: const Radius.circular(10.0),
          bottomLeft: const Radius.circular(10.0),
          bottomRight: const Radius.circular(10.0),
        ),
        gradient: new LinearGradient(
            colors: [
              Color(0xff050859),
              Color(0xff7a6fe9),
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      margin: EdgeInsets.only(left: 7, top: 2, right: 5),
      child: ListTile(
        // isThreeLine: true,
        onTap: () {
          if (id) {
            searchRenewalByNumber(val);
          }
        },
        leading: Icon(
          Icons.info_outline,
          color: Colors.white,
        ),
        subtitle: Text(
          val,
          style: TextStyle(
              color: Colors.white,
              fontSize: 17.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
        trailing: id
            ? ButtonTheme(
                buttonColor: Colors.white,
                minWidth: 100.0,
                height: 100.0,
                child: RaisedButton(
                  shape: new RoundedRectangleBorder(
                    borderRadius: new BorderRadius.circular(25.0),
                  ),
                  onPressed: () {
                    if (id) {
                      searchRenewalByNumber(val);
                    }
                  },
                  child: Text(
                    "Click",
                    style: TextStyle(
                        color: Colors.black,
                        fontSize: 14.0,
                        fontWeight: FontWeight.bold,
                        fontFamily: "WorkSansBold"),
                  ),
                ),
              )
            : Text(""),
        title: Text(
          title,
          style: TextStyle(
              color: Colors.white,
              fontSize: 17.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
      ),
    );
  }
}
