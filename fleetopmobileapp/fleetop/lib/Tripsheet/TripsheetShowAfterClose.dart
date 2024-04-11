import 'dart:convert';
import 'dart:io';

import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../apicall.dart';
import '../fleetopuriconstant.dart';
import 'TripSheetAdvance.dart';
import '../flutteralert.dart';
import 'TripSheetClose.dart';
import 'TripSheetDriverHalt.dart';
import 'TripSheetExpense.dart';
import 'TripSheetIncome.dart';
import 'TripsheetComment.dart';
import 'TripsheetEdit.dart';
import 'TripsheetFuel.dart';
import 'TripsheetPOD.dart';



class TripsheetShowAfterClose extends StatefulWidget {
  TripsheetShowAfterClose({Key key, this.tripsheetId}) : super(key: key);
  final int tripsheetId;

  @override
  _TripsheetShowAfterCloseState createState() => _TripsheetShowAfterCloseState();
}

class _TripsheetShowAfterCloseState extends State<TripsheetShowAfterClose> {
  bool vehicleDetailsState = false;
  bool showTripDetails = false;
  bool showAdvanceDetails = false;
  bool showExpenseDetails = false;
  bool showIncomeDetails = false;
  bool showTripSheetStatus = false;
  bool showTripSheetComment = false;
  String companyId;
  String email;
  String userId;

  int tripsheetId;
  String createdDate;
  String tripsheetNumber;
  String vehNumber;
  String route;
  String tripOpenDate;
  String tripCloseDate;
  String vehicle_Group;
  String tripBookref;
  String routeAttendancePoint;
  String routeTotalLiter;
  String tripFristDriverName;
  String tripFristDriverMobile;
  String tripFristDriverRoutePoint;
  String tripSecDriverName;
  String tripSecDriverMobile;
  String tripSecDriverRoutePoint;
  String tripCleanerName;
  String tripCleanerMobile;
  String tripCleanerRoutePoint;
  String tripOpeningKM;
  String tripClosingKM;
  String tripUsageKM;
  String dispatchedBy;
  String dispatchedLocation;
  String dispatchedByTime;
  String closedBy;
  String cloesdLocation;
  String closedByTime;
  String loadType;
  String totalPOD;
  String Remark;
  double advance;
  double expense;
  double advanceMinusExpense;


  Map tripList = Map();
  List advanceList = List();
  List expenseList = List();
  List incomeList = List();
  List commentList = List();

  @override
  void initState() {
    super.initState();
    print("widgettripsheetId..........${widget.tripsheetId}");
    getTripsheetShowData(widget.tripsheetId);
    /*getTripsheetShowData(7989);*/
  }

  getTripsheetShowData(int tripId) async {
    print("data is  = $tripId");
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {'companyId': companyId, 'userId' : userId, 'email' : email, 'tripsheetId' : tripId.toString()};
    var response = await ApiCall.getDataFromApi(
        URI.SHOW_TRIPSHEET, data, URI.LIVE_URI, context);

    tripList = response['TripSheet'];
    advanceList = response['TripSheetAdvance'];
    expenseList = response['TripSheetExpense'];
    incomeList = response['TripSheetIncome'];
    commentList = response['TripComment'];
    print("tripList :: $tripList");
    print("commentList................ $commentList");

    setState(() {
      tripsheetId = tripId;
      createdDate = tripList['created'];
      tripsheetNumber = tripList['tripSheetNumber'].toString();
      vehNumber = tripList['vehicle_registration'];
      route = tripList['routeName'];
      tripOpenDate = tripList['tripOpenDate'];
      tripCloseDate = tripList['closetripDate'];
      vehicle_Group = tripList['vehicle_Group'];
      tripBookref = tripList['tripBookref'];
      routeAttendancePoint = tripList['routeAttendancePoint'].toString();
      routeTotalLiter = tripList['routeTotalLiter'].toString();
      tripFristDriverName = tripList['tripFristDriverName'];
      tripFristDriverMobile = tripList['tripFristDriverMobile'];
      tripFristDriverRoutePoint = tripList['tripFristDriverRoutePoint'].toString();;
      tripSecDriverName = tripList['tripSecDriverName'];
      tripSecDriverMobile = tripList['tripSecDriverMobile'];
      tripSecDriverRoutePoint = tripList['tripSecDriverRoutePoint'].toString();
      tripCleanerName = tripList['tripCleanerName'];
      tripCleanerMobile = tripList['tripCleanerMobile'];
      tripCleanerRoutePoint = tripList['tripCleanerRoutePoint'].toString();
      tripOpeningKM = tripList['tripOpeningKM'].toString();
      tripClosingKM = tripList['tripClosingKM'].toString();
      tripUsageKM = tripList['tripUsageKM'].toString();
      dispatchedBy = tripList['dispatchedBy'];
      dispatchedLocation = tripList['dispatchedLocation'];
      dispatchedByTime = tripList['dispatchedByTime'];
      closedBy = tripList['closedBy'];
      cloesdLocation = tripList['cloesdLocation'];
      closedByTime = tripList['closedByTime'];
      loadType = tripList['loadTypeName'];
      totalPOD = tripList['noOfPOD'].toString();
      Remark = tripList['routeRemark'];
      advance = tripList['tripTotalAdvance'];
      expense = tripList['tripTotalexpense'];
      advanceMinusExpense = advance - expense;

    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.pink,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "View Tripsheet",
          style: new TextStyle(
            fontSize: 25,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
        ),
      ),

      floatingActionButton: new FloatingActionButton(
        onPressed: (){
          _settingModalBottomSheet(context,tripsheetId);
        },
        child: new Icon(Icons.add),
      ),


      backgroundColor: AppTheme.white,
      body: Container(
          child: SingleChildScrollView(
              child: Center(
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[

                        SizedBox(height: 5),
                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Created Date : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 17,
                                  )
                              ),

                              new TextSpan(
                                  text: " ${createdDate} ",
                                  style: new TextStyle(
                                    color: Colors.blue,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 17,
                                  )
                              ),
                            ],
                          ),
                        ),

                        SizedBox(height: 25),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Trip Number : TS - ${tripsheetNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.red, fontSize: 19)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${vehNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.green, fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${route}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.green, fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Tripsheet Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showTripDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                                        onPressed: () => {
                                          setState(() {
                                            showTripDetails =
                                            !showTripDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showTripDetails,
                            child:Stack(
                              children: <Widget>[
                                showTripInfo(context),
                                /*ola(context),*/
                              ],
                            )
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Advance Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showAdvanceDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () => {
                                          setState(() {
                                            showAdvanceDetails =
                                            !showAdvanceDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showAdvanceDetails,
                            child:Column(
                              children: <Widget>[

                                if(advanceList != null)
                                  for(int i = 0;i<advanceList.length;i++)
                                    showAdvancePaymentDetails(advanceList[i],context),

                              ],
                            )
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Expense Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showExpenseDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () => {
                                          setState(() {
                                            showExpenseDetails =
                                            !showExpenseDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showExpenseDetails,
                            child:Column(
                              children: <Widget>[

                                if(expenseList != null)
                                  for(int i = 0;i<expenseList.length;i++)
                                    showExpensePaymentDetails(expenseList[i],context),

                              ],
                            )
                        ),


                        SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Income Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showIncomeDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () => {
                                          setState(() {
                                            showIncomeDetails =
                                            !showIncomeDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showIncomeDetails,
                            child:Column(
                              children: <Widget>[

                                if(incomeList != null)
                                  for(int i = 0;i<incomeList.length;i++)
                                    showIncomePaymentDetails(incomeList[i],context),

                              ],
                            )
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "TripSheet Status ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showTripSheetStatus ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () => {
                                          setState(() {
                                            showTripSheetStatus =
                                            !showTripSheetStatus;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showTripSheetStatus,
                            child:Column(
                              children: <Widget>[

                                /*if(tripList != null)
                                  for(int i = 0;i<tripList.length;i++)*/
                                showTripSheetStatusDetails(tripList,context),

                              ],
                            )
                        ),

                        SizedBox(height: 30),

                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "TripSheet Comment ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showTripSheetComment ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () => {
                                          setState(() {
                                            showTripSheetComment =
                                            !showTripSheetComment;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showTripSheetComment,
                            child:Column(
                              children: <Widget>[

                                if(commentList != null)
                                  for(int i = 0;i<commentList.length;i++)
                                    showTripsheetCommentDetails(commentList[i],context),

                              ],
                            )
                        ),

                        SizedBox(height: 30),


                      ]
                  )
              )
          )
      ),
    );
  }

  Widget showTripsheetCommentDetails(data,context)
  {
    Map commentData = new Map();
    commentData = data;
    if(commentList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showCommentAddedDetails(context, commentData, "Comment Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Comment By : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " ${data['createdby']} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),
                            ],
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ],
      );
    }
    else
    {
      return Container();
    }
  }

  void showCommentAddedDetails(BuildContext context, Map data , String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Email : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['created_EMAIL']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Comment By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['createdby']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Place : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['created_PLACE']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Comment : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['trip_COMMENT']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),


          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['created_DATE']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showTripSheetStatusDetails(data,context)
  {
    Map statusData = new Map();
    statusData = data;
    if(tripList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showStatusDetails(context, statusData, "TripSheet Status");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Status : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " ${data['tripStutes'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),
                            ],
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ],
      );
    }
    else
    {
      return Container();
    }
  }

  void showStatusDetails(BuildContext context, Map data , String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Paid To : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['closeTripStatus']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Paid By :  ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['closeTripNameBy']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " (Advance - Expense) : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${advanceMinusExpense} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Reference : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['closeTripReference']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " TS-Close Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['closetripDate']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Status : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['tripStutes']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showIncomePaymentDetails(data,context)
  {
    Map incomeData = new Map();
    incomeData = data;
    if(incomeList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showIncomePaidDetails(context, incomeData, "Income Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Income : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['incomeAmount'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),
                            ],
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ],
      );
    }
    else
    {
      return Container();
    }
  }

  void showIncomePaidDetails(BuildContext context, Map data , String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Income Name : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['incomeName']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Type : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['incomeFixed']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Place : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['incomePlace']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Reference : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['incomeRefence']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Amount : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['incomeAmount']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Income Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['created']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showExpensePaymentDetails(data,context)
  {
    Map expenseData = new Map();
    expenseData = data;
    if(expenseList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showExpensePaidDetails(context, expenseData, "Expense Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Expense : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['expenseAmount'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),
                            ],
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ],
      );
    }
    else
    {
      return Container();
    }
  }

  void showExpensePaidDetails(BuildContext context, Map data , String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        children: <Widget>[

          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Expense Name : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseName']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Type : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseFixed']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Place : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expensePlace']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Reference : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseRefence']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Amount : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseAmount']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Expense Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['created']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showAdvancePaymentDetails(data,context)
  {
    Map advanceData = new Map();
    advanceData = data;
    if(advanceList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showAdvancePaidDetails(context, advanceData, "Advance Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Adv  Amount : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['advanceAmount'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),
                            ],
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ],
      );
    }
    else
    {
      return Container();
    }
  }

  static void showAdvancePaidDetails(BuildContext context, Map data , String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        children: <Widget>[

          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Advance Place : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['advancePlace']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Advance Paid By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['advancePaidby']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Reference : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['advanceRefence']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Amount : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['advanceAmount']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Advance Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['created']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showTripInfo(context)
  {
    return  Column(
      children: <Widget>[

        Card(
          color:Colors.white70,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.only(
                bottomRight: Radius.circular(30),
                topRight: Radius.circular(30),
                bottomLeft: Radius.circular(30),
                topLeft: Radius.circular(30)),
            //side: BorderSide(width: 5, color: Colors.green)
          ),
          child: Container(
            height: 50,
            child: Padding(
                padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                child: Row(
                    children: [

                      new IconButton(
                        icon: new Icon(
                          Icons.directions_bus,
                          color: Colors.green,
                        ),
                        onPressed: () {
                          showTripExtraDetails(context, "Trip Details");
                        },
                      ),

                      Text(
                          " Trip Details ",
                          style: new TextStyle(
                            color: Colors.purple,
                            fontWeight: FontWeight.w700,
                            fontSize: 16,
                          )
                      ),

                      new IconButton(
                        icon: new Icon(
                          Icons.arrow_forward,
                          color: Colors.green,
                        ),
                        onPressed: () {
                          showTripExtraDetails(context, "Trip Details");
                        },
                      ),

                    ]
                )
            ),
          ),
        ) ,

        Card(
          color:Colors.white70,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.only(
                bottomRight: Radius.circular(30),
                topRight: Radius.circular(30),
                bottomLeft: Radius.circular(30),
                topLeft: Radius.circular(30)),
            //side: BorderSide(width: 5, color: Colors.green)
          ),
          child: Container(
            height: 50,
            child: Padding(
                padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                child: Row(
                    mainAxisAlignment:
                    MainAxisAlignment.start,
                    children: [

                      new IconButton(
                        icon: new Icon(
                          Icons.people,
                          color: Colors.green,
                        ),
                        onPressed: () {
                          showDriverExtraDetails(context, "Driver Details");
                        },
                      ),

                      Text(
                          " Driver Details ",
                          style: new TextStyle(
                            color: Colors.purple,
                            fontWeight: FontWeight.w700,
                            fontSize: 15,
                          )
                      ),

                      new IconButton(
                        icon: new Icon(
                          Icons.arrow_forward,
                          color: Colors.green,
                        ),
                        onPressed: () {
                          showDriverExtraDetails(context, "Driver Details");
                        },
                      ),

                    ]
                )
            ),
          ),
        ) ,

        Card(
          color:Colors.white70,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.only(
                bottomRight: Radius.circular(30),
                topRight: Radius.circular(30),
                bottomLeft: Radius.circular(30),
                topLeft: Radius.circular(30)),
            //side: BorderSide(width: 5, color: Colors.green)
          ),
          child: Container(
            height: 50,
            child: Padding(
                padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                child: Row(
                    mainAxisAlignment:
                    MainAxisAlignment.start,
                    children: [

                      new IconButton(
                        icon: new Icon(
                          Icons.access_time,
                          color: Colors.green,
                        ),
                        onPressed: () {
                          showDispatchExtraDetails(context, "Dispatch Details");
                        },
                      ),

                      Text(
                          " Dispatch Details ",
                          style: new TextStyle(
                            color: Colors.purple,
                            fontWeight: FontWeight.w700,
                            fontSize: 15,
                          )
                      ),

                      new IconButton(
                        icon: new Icon(
                          Icons.arrow_forward,
                          color: Colors.green,
                        ),
                        onPressed: () {
                          showDispatchExtraDetails(context, "Dispatch Details");
                        },
                      ),

                    ]
                )
            ),
          ),
        ) ,


      ],
    );

  }

  void showTripExtraDetails (BuildContext context, String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " DOJ : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripOpenDate} to ${tripCloseDate} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Trip Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${routeAttendancePoint} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Trip Route Volume : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${routeTotalLiter} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Booking No : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,

                    )
                ),

                new TextSpan(
                    text: " ${tripBookref} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Group Service : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${vehicle_Group} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Opening KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripOpeningKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closing KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripClosingKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Usage KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripUsageKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),


        ],
      ),

    ).show();
  }

  void showDriverExtraDetails (BuildContext context, String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripFristDriverName} / ${tripFristDriverMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 2 : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripSecDriverName} / ${tripSecDriverMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Cleaner : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripCleanerName} / ${tripCleanerMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 1 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripFristDriverRoutePoint} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 2 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripSecDriverRoutePoint} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Cleaner Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripCleanerRoutePoint} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

        ],
      ),

    ).show();
  }

  void showDispatchExtraDetails (BuildContext context, String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedBy} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedLocation}  ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedByTime} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${closedBy} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${cloesdLocation}  ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${closedByTime} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Load Type : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${loadType} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Number Of POD : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${totalPOD} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),


          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Route Remark : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${Remark} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),

        ],
      ),

    ).show();
  }

}



void _settingModalBottomSheet(context, int tripsheetId){
  showModalBottomSheet(
      context: context,
      builder: (BuildContext bc){
        return ListView(
          children: <Widget>[

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                 // Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripsheetAdvance (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.credit_card,
                  color: Colors.lightBlueAccent,
                ),
                title: Text(
                  "Advance",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),

            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripSheetExpense (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.monetization_on,
                  color: Colors.red,
                ),
                title: Text(
                  "Expense",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripSheetIncome (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.attach_money,
                  color: Colors.green,
                ),
                title: Text(
                  "Income",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripSheetClose (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.directions_bus,
                  color: Colors.purple,
                ),
                title: Text(
                  "Close Trip",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripSheetFuel (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.local_gas_station,
                  color: Colors.blue,
                ),
                title: Text(
                  "Fuel Entries",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripSheetDriverHalt (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.people,
                  color: Colors.yellow,
                ),
                title: Text(
                  "Driver Halt",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                ),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripSheetEdit (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.edit,
                  color: Colors.pink,
                ),
                title: Text(
                  "Edit",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {

                },

                leading: new Icon(
                  Icons.delete,
                  color: Colors.black,
                ),
                title: Text(
                  "Delete",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripsheetComment (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.comment,
                  color: Colors.redAccent,
                ),
                title: Text(
                  "Comment",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            Card(
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(30),
                    topRight: Radius.circular(30)),
                //side: BorderSide(width: 5, color: Colors.green)
              ),
              child: ListTile(
                trailing: Icon(Icons.keyboard_arrow_right),
                onTap: () => {
                  Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripsheetPOD (tripsheetId :tripsheetId)))
                },

                leading: new Icon(
                  Icons.library_books,
                  color: Colors.amber,
                ),
                title: Text(
                  "POD",
                  style: new TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.w700,
                  ),
                ),

              ),
            ),

            /*Card(
                elevation: 5,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(30),
                      topRight: Radius.circular(30)),
                  //side: BorderSide(width: 5, color: Colors.green)
                ),
                child: ListTile(
                  trailing: Icon(Icons.keyboard_arrow_right),
                  onTap: () => {},

                  leading: new Icon(
                    Icons.add,
                    color: Colors.brown,
                  ),
                  title: Text(
                    "Extra",
                    style: new TextStyle(
                      fontSize: 18,
                      color: Colors.black,
                      fontWeight: FontWeight.w700,
                    ),
                  ),

                ),
              ),*/

            /*Card(
                elevation: 5,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(30),
                      topRight: Radius.circular(30)),
                  //side: BorderSide(width: 5, color: Colors.green)
                ),
                child: ListTile(
                  trailing: Icon(Icons.keyboard_arrow_right),
                  onTap: () => {},

                  leading: new Icon(
                    Icons.picture_as_pdf,
                    color: Colors.amber,
                  ),
                  title: Text(
                    "Add IV Cargo L.S.",
                    style: new TextStyle(
                      fontSize: 18,
                      color: Colors.black,
                      fontWeight: FontWeight.w700,
                    ),
                  ),

                ),
              ),

              Card(
                elevation: 5,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(30),
                      topRight: Radius.circular(30)),
                  //side: BorderSide(width: 5, color: Colors.green)
                ),
                child: ListTile(
                  trailing: Icon(Icons.keyboard_arrow_right),
                  onTap: () => {},

                  leading: new Icon(
                    Icons.delete_sweep,
                    color: Colors.purpleAccent,
                  ),
                  title: Text(
                    "Urea Entries",
                    style: new TextStyle(
                      fontSize: 18,
                      color: Colors.black,
                      fontWeight: FontWeight.w700,
                    ),
                  ),

                ),
              ),*/



          ],

        );
      }
  );
}

// All Old Code below for Reference

/*
appBar: AppBar(
backgroundColor: Colors.amber,
centerTitle: true,
title:appBarTitle,
*/
/*iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Create Tripsheet",
          style: new TextStyle(
            fontSize: 23,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
        ),*//*

leading: new IconButton(
icon: new Icon(Icons.menu, color: Colors.white),
onPressed: widget.onMenuPressed,
),
actions: <Widget>[
new IconButton(icon: actionIcon,onPressed:(){
setState(() {
if ( this.actionIcon.icon == Icons.search){
this.actionIcon = new Icon(Icons.close);
this.appBarTitle = new TextField(
style: new TextStyle(
color: Colors.white,

),
decoration: new InputDecoration(
prefixIcon: new Icon(Icons.search,color: Colors.white),
hintText: "Search...",
hintStyle: new TextStyle(color: Colors.white)
),
);}
else {
this.actionIcon = new Icon(Icons.search);
this.appBarTitle = new Text("AppBar Title");
}


});
} ,),]
),*/
