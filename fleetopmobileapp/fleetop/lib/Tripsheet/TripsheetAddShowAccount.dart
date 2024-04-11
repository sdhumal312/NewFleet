import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../apicall.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'TripsheetShowAccountClose.dart';

class TripsheetAddShowAccount extends StatefulWidget {

  final Function tripsheetShowData;

  TripsheetAddShowAccount({this.tripsheetShowData,this.tripsheetId, this.navigateToSearch, this.accountCloseBySearch});
  final int tripsheetId;
  final bool navigateToSearch;
  final bool accountCloseBySearch;

  @override
  _TripsheetAddShowAccountState createState() => _TripsheetAddShowAccountState();
}

class _TripsheetAddShowAccountState extends State<TripsheetAddShowAccount> {
  bool navigateToSearch = false;
  bool accountCloseBySearch = false;
  bool vehicleDetailsState = false;
  bool showTripDetails = false;
  bool showAdvanceDetails = false;
  bool showExpenseDetails = false;
  bool showIncomeDetails = false;
  bool showTripSheetStatus = false;
  bool showTripSheetComment = false;
  bool showCommentDetails = false;
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
  double income;
  double advanceMinusExpense;
  String advanceTotal;
  String expenseTotal;
  String incomeTotal;
  int tripStatus;

  String accountPaidBy;
  String accountReference;
  double accountAmount;

  Map tripList = Map();
  List advanceList = List();
  List expenseList = List();
  List incomeList = List();
  List commentList = List();

  TextEditingController accountClosedBy = new TextEditingController();
  TextEditingController ref = new TextEditingController();
  TextEditingController amount = new TextEditingController();

  @override
  void initState() {
    super.initState();
    getTripsheetShowData(widget.tripsheetId);
    navigateToSearch = widget.navigateToSearch;
    accountCloseBySearch = widget.accountCloseBySearch;
    print("navigateToSearch...$navigateToSearch");
    print("accountCloseBySearch...$accountCloseBySearch");
    /*getTripsheetShowData(16420);*/
  }

  getTripsheetShowData(int tripId) async {
    print("data is  = $tripId");
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'tripsheetId': tripId.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.ADD_ACCOUNT_CLOSE_TRIPSHEET, data, URI.LIVE_URI, context);

    tripList = response['TripSheet'];
    advanceList = response['TripSheetAdvance'];
    expenseList = response['TripSheetExpense'];
    incomeList = response['TripSheetIncome'];
    commentList = response['TripComment'];
    advanceTotal = response['advanceTotal'].toString();
    expenseTotal = response['expenseTotal'].toString();
    incomeTotal = response['incomeTotal'].toString();
    accountPaidBy = response['paidBy'];


    if(commentList != null){
      showCommentDetails = true;
    }

    print("tripList :: $tripList");

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
      tripFristDriverRoutePoint = tripList['tripFristDriverRoutePoint'].toString();
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
      income = tripList['tripTotalincome'];
      advanceMinusExpense = advance - expense;
      accountAmount = income - expense;
      tripStatus = tripList['tripStutesId'];
      accountClosedBy.text = accountPaidBy;
      amount.text = accountAmount.toString();

    });

  }

  Future closeTripAccount() async {
    if (!fieldValidation()) {
      return;
    } else {

      var closeTripAccountData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripsheetId': tripsheetId.toString(),
        'accountClosedById': userId,
        'accountClosedAmount': amount.text,
        'accountClosedRef': ref.text,
      };
      print("closeTripAccountData...$closeTripAccountData");
      var response = await ApiCall.getDataFromApi(
          URI.SAVE_ACCOUNT_CLOSE_TRIPSHEET, closeTripAccountData, URI.LIVE_URI, context);
      print("response...$response");
      if(response != null){
        if(response['accountClosed'] != null && response['accountClosed'] == true){
          //FlutterAlert.onErrorAlert(context, "Expense Already Added !", "Error");
          Alert(
            context: context,
            type: AlertType.success,
            title: "Account Close Tripsheet ",
            desc: " Tripsheet account closed Successfully ",
            buttons: [
              DialogButton(
                child: Text(
                  "OK",
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
                onPressed: ()
                {
                  if(accountCloseBySearch){
                    print("tripsheetId...$tripsheetId");
                    print("navigateToSearch...$navigateToSearch");
                    print("accountCloseBySearch...$accountCloseBySearch");
                    Navigator.push(context, new MaterialPageRoute(
                        builder: (context) => new TripsheetShowAccountClose (
                            tripsheetShowData : () =>  getTripsheetShowData(tripsheetId), tripsheetId: tripsheetId, navigateToSearch: false, accountCloseBySearch: true)));
                  } else {
                    Navigator.push(context, new MaterialPageRoute(
                        builder: (context) => new TripsheetShowAccountClose (
                            tripsheetShowData : () =>  getTripsheetShowData(tripsheetId), tripsheetId: tripsheetId, navigateToSearch: false, accountCloseBySearch : false)));
                  }

                },
                gradient: LinearGradient(colors: [
                  Colors.green,
                  Colors.deepPurple
                ]),
              ),

            ],
          ).show();
        }
      }

    }

  }

  bool fieldValidation() {

    if (ref.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Reference !", "Error");
      return false;
    }

    return true;
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.amber,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Account Close Tripsheet",
          style: new TextStyle(
            fontSize: 22,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
        ),
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () =>
          {
            Navigator.pop(context),
            Navigator.pop(context),
            widget.tripsheetShowData()
          },
        ),
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
                                style: TextStyle(fontWeight: FontWeight.w700,
                                    color: Colors.red,
                                    fontSize: 19)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${vehNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700,
                                    color: Colors.green,
                                    fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${route}",
                                style: TextStyle(fontWeight: FontWeight.w700,
                                    color: Colors.green,
                                    fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pink,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(
                                    left: 10, right: 10),
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
                                              fontWeight: FontWeight.w500
                                          ),
                                        ),
                                        /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                                        onPressed: () =>
                                        {
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
                            child: Stack(
                              children: <Widget>[
                                showTripInfo(context),
                                /*ola(context),*/
                              ],
                            )
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pink,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(
                                    left: 10, right: 10),
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
                                              fontWeight: FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () =>
                                        {
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
                            child: Column(
                              children: <Widget>[

                                if(advanceList != null)
                                  for(int i = 0; i < advanceList.length; i++)
                                    showAdvancePaymentDetails(
                                        advanceList[i], context),

                                showAdvancePaymentTotal(context),

                              ],
                            )
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.pink,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(
                                    left: 10, right: 10),
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
                                              fontWeight: FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () =>
                                        {
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
                            child: Column(
                              children: <Widget>[

                                if(expenseList != null)
                                  for(int i = 0; i < expenseList.length; i++)
                                    showExpensePaymentDetails(
                                        expenseList[i], context),

                                showExpensePaymentTotal(context)

                              ],
                            )
                        ),


                        SizedBox(height: 15),
                        Card(
                          color: Colors.pink,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(
                                    left: 10, right: 10),
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
                                              fontWeight: FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () =>
                                        {
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
                            child: Column(
                              children: <Widget>[

                                if(incomeList != null)
                                  for(int i = 0; i < incomeList.length; i++)
                                    showIncomePaymentDetails(
                                        incomeList[i], context),

                                showIncomePaymentTotal(context)

                              ],
                            )
                        ),

                        /*SizedBox(height: 15),
                        Card(
                          color: Colors.pinkAccent,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(
                                    left: 10, right: 10),
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
                                          showTripSheetStatus
                                              ? "Close"
                                              : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight: FontWeight.w500
                                          ),
                                        ),
                                        onPressed: () =>
                                        {
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
                            child: Column(
                              children: <Widget>[

                                *//*if(tripList != null)
                                  for(int i = 0;i<tripList.length;i++)*//*
                                showTripSheetStatusDetails(tripList, context),

                              ],
                            )
                        ),*/

                        SizedBox(height: 15),
                        Visibility(
                          visible: showCommentDetails,
                          child: Card(
                            color: Colors.pink,
                            child: Container(
                              height: 50,
                              child: Padding(
                                  padding: const EdgeInsets.only(
                                      left: 10, right: 10),
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
                                            showTripSheetComment
                                                ? "Close"
                                                : "Open",
                                            style: TextStyle(
                                                color: Colors.purpleAccent,
                                                fontSize: 20,
                                                fontWeight: FontWeight.w500
                                            ),
                                          ),
                                          onPressed: () =>
                                          {
                                            setState(() {
                                              showTripSheetComment =
                                              !showTripSheetComment;
                                            })
                                          },
                                        )
                                      ])),
                            ),
                          ),
                        ),
                        Visibility(
                            visible: showTripSheetComment,
                            child: Column(
                              children: <Widget>[

                                if(commentList != null)
                                  for(int i = 0; i < commentList.length; i++)
                                    showTripsheetCommentDetails(
                                        commentList[i], context),

                              ],
                            )
                        ),


                        SizedBox(height: 25),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Tripsheet Status ",
                                style: new TextStyle(
                                  color: Colors.pink,
                                  fontWeight: FontWeight.w700,
                                  fontSize: 15,
                                )
                            ),
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery.of(context).size.width,
                          color: Colors.pink,
                          margin: const EdgeInsets.only(left: 70.0, right: 70.0),
                        ),

                        SizedBox(height: 10),
                        Container(
                            child: Row(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                Container(
                                  margin: EdgeInsets.only(left:15),
                                  child:  RichText(
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
                                            text: " ${tripList['closeTripStatus']} ",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                               Container(
                                 margin: EdgeInsets.only(left:60),
                                 child:     RichText(
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
                                           text: " ${tripList['tripStutes'].toString()} ",
                                           style: new TextStyle(
                                             color: Colors.purple,
                                             fontWeight: FontWeight.w700,
                                           )
                                       ),
                                     ],
                                   ),
                                 ),
                               ),

                                ]
                            ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery
                              .of(context)
                              .size
                              .width,
                          color: Colors.black,
                          margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                        ),

                        SizedBox(height: 10),
                        Container(
                          child: Row(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Container(
                                  padding: EdgeInsets.only(left:15),
                                  child:  RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Paid By : ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: tripList['closeTripNameBy'] != null ? tripList['closeTripNameBy'].toString() : "--",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                                Container(
                                  padding: EdgeInsets.only(left:80),
                                  child:     RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Reference: ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: tripList['closeTripReference'] != null ? tripList['closeTripReference'].toString() : "--",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                              ]
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery
                              .of(context)
                              .size
                              .width,
                          color: Colors.black,
                          margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                        ),

                        SizedBox(height: 10),
                        Container(
                          child: Row(
                            // mainAxisAlignment: MainAxisAlignment.spaceAround,
                              children: [
                                Container(
                                  margin: EdgeInsets.only(left:15),
                                  child:  RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Close Date : ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: " ${tripList['closetripDate']}",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                                Container(
                                  margin: EdgeInsets.only(left:15),
                                  child:     RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Advance : ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: " \u20B9${advanceTotal} ",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                              fontSize: 15,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                              ]
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery
                              .of(context)
                              .size
                              .width,
                          color: Colors.black,
                          margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                        ),

                        SizedBox(height: 10),
                        Container(
                          child: Row(
                            // mainAxisAlignment: MainAxisAlignment.spaceAround,
                              children: [
                                Container(
                                  margin: EdgeInsets.only(left:15),
                                  child:  RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Income : ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: " \u20B9${incomeTotal}  ",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                              fontSize: 15,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                                Container(
                                  margin: EdgeInsets.only(left:80),
                                  child:     RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Expense : ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: "\u20B9${expenseTotal} ",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                              fontSize: 15,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                              ]
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery
                              .of(context)
                              .size
                              .width,
                          color: Colors.black,
                          margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                        ),

                        SizedBox(height: 10),
                        Container(
                          child: Row(
                             mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Container(
                                  child:  RichText(
                                    text: TextSpan(
                                      children: <TextSpan>[
                                        new TextSpan(
                                            text: " Advance - Expense : ",
                                            style: new TextStyle(
                                              color: AppTheme.darkText,
                                              fontWeight: FontWeight.w700,
                                            )
                                        ),

                                        new TextSpan(
                                            text: " \u20B9${advanceMinusExpense}  ",
                                            style: new TextStyle(
                                              color: Colors.purple,
                                              fontWeight: FontWeight.w700,
                                              fontSize: 15,
                                            )
                                        ),
                                      ],
                                    ),
                                  ),
                                ),

                              ]
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery
                              .of(context)
                              .size
                              .width,
                          color: Colors.black,
                          margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                        ),

                        SizedBox(height: 25),
                         Container(
                            child: Align(
                              alignment: Alignment.center,
                              child: Text(
                                  "Trip Balance ",
                                  style: new TextStyle(
                                    color: Colors.pink,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 15,
                                  )
                              ),
                            ),
                          ),
                        Container(
                            height: 2.0,
                            width: MediaQuery.of(context).size.width,
                            color: Colors.pink,
                            margin: const EdgeInsets.only(left: 70.0, right: 70.0),
                          ),

                        SizedBox(height: 10),
                        Container(
                            child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Container(
                                    child:  RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[

                                          new TextSpan(
                                              text: " \u20B9${amount.text} ",
                                              style: new TextStyle(
                                                color: Colors.purple,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 17,
                                              )
                                          ),

                                          new TextSpan(
                                              text: " - Balance ( Income - Expense ) ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 17,
                                              )
                                          ),


                                        ],
                                      ),
                                    ),
                                  ),

                                ]
                            ),
                          ),
                         Container(
                            height: 2.0,
                            width: MediaQuery
                                .of(context)
                                .size
                                .width,
                            color: Colors.black,
                            margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                          ),

                        SizedBox(height: 40),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                " For Office Use ",
                                style: new TextStyle(
                                  color: Colors.pink,
                                  fontWeight: FontWeight.w700,
                                  fontSize: 15,
                                )
                            ),
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery.of(context).size.width,
                          color: Colors.pink,
                          margin: const EdgeInsets.only(left: 70.0, right: 70.0),
                        ),

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 1.0, left: 10),
                            child: Container(
                              child: TextField(
                                enabled: false,
                                maxLines: 1,
                                controller: accountClosedBy,
                                keyboardType: TextInputType.number,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                  labelText: 'Trip Account Closed By',
                                  hintText: "",
                                  hintStyle: TextStyle(
                                      color: Colors.black),
                                  border: OutlineInputBorder(
                                      borderRadius:
                                      BorderRadius.circular(
                                          5.0)),
                                  icon: Icon(
                                    Icons.person_outline,
                                    color: Colors.greenAccent,
                                  ),

                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 1.0, left: 10),
                            child: Container(
                              child: TextField(
                                enabled: false,
                                maxLines: 1,
                                controller: amount,
                                keyboardType: TextInputType.number,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                  labelText: 'Amount',
                                  hintText: "",
                                  hintStyle: TextStyle(
                                      color: Colors.black),
                                  border: OutlineInputBorder(
                                      borderRadius:
                                      BorderRadius.circular(
                                          5.0)),
                                  icon: Icon(
                                    Icons.credit_card,
                                    color: Colors.brown,
                                  ),

                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 1.0, left: 10),
                            child: Container(
                              child: TextField(
                                maxLines: 1,
                                controller: ref,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                  labelText: 'Reference',
                                  hintText: "",
                                  hintStyle: TextStyle(
                                      color: Colors.black),
                                  border: OutlineInputBorder(
                                      borderRadius:
                                      BorderRadius.circular(
                                          5.0)),
                                  icon: Icon(
                                    Icons.library_books,
                                    color: Colors.amber,
                                  ),

                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child:
                            new  RaisedButton(
                              padding: const EdgeInsets.all(8.0),
                              textColor: Colors.white,
                              color: Colors.pink,
                              onPressed: closeTripAccount,
                              child: Padding(
                                padding:
                                const EdgeInsets.symmetric(
                                    vertical: 2.0,
                                    horizontal: 15.0),
                                child: Text(
                                  "Close Trip Account",
                                  style: TextStyle(
                                      fontSize: 18,
                                      color: Colors.white,
                                      fontWeight:
                                      FontWeight.w500),
                                ),
                              ),
                            ),
                          ),
                        ),



                        SizedBox(height: 30),


                      ]
                  )
              )
          )
      ),
    );
  }

  Widget showAdvancePaymentTotal(context)
  {
    return  Column(
      children: <Widget>[
        Container(
          height: 50,
          child: Padding(
              padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
              child: Row(
                  mainAxisAlignment:
                  MainAxisAlignment.center,
                  children: [

                    RichText(
                      text: TextSpan(
                        children: <TextSpan>[
                          new TextSpan(
                              text: " Total : ",
                              style: new TextStyle(
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                                fontSize: 16,
                              )
                          ),

                          new TextSpan(
                              text: " \u20B9${advanceTotal} ",
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
        Container(
          height: 2.0,
          width: MediaQuery.of(context).size.width,
          color: Colors.black,
          margin: const EdgeInsets.only(left: 10.0, right: 10.0),
        ),

      ],
    );

  }

  Widget showExpensePaymentTotal(context)
  {
    return  Column(
      children: <Widget>[
        Container(
          height: 50,
          child: Padding(
              padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
              child: Row(
                  mainAxisAlignment:
                  MainAxisAlignment.center,
                  children: [

                    RichText(
                      text: TextSpan(
                        children: <TextSpan>[
                          new TextSpan(
                              text: " Total : ",
                              style: new TextStyle(
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                                fontSize: 16,
                              )
                          ),

                          new TextSpan(
                              text: " \u20B9${expenseTotal} ",
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
        Container(
          height: 2.0,
          width: MediaQuery.of(context).size.width,
          color: Colors.black,
          margin: const EdgeInsets.only(left: 10.0, right: 10.0),
        ),

      ],
    );

  }

  Widget showIncomePaymentTotal(context)
  {
    return  Column(
      children: <Widget>[
        Container(
          height: 50,
          child: Padding(
              padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
              child: Row(
                  mainAxisAlignment:
                  MainAxisAlignment.center,
                  children: [

                    RichText(
                      text: TextSpan(
                        children: <TextSpan>[
                          new TextSpan(
                              text: " Total : ",
                              style: new TextStyle(
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                                fontSize: 16,
                              )
                          ),

                          new TextSpan(
                              text: " \u20B9${incomeTotal} ",
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
        Container(
          height: 2.0,
          width: MediaQuery.of(context).size.width,
          color: Colors.black,
          margin: const EdgeInsets.only(left: 10.0, right: 10.0),
        ),

      ],
    );

  }

  Widget showTripsheetCommentDetails(data, context) {
    Map commentData = new Map();
    commentData = data;
    if (commentList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showCommentAddedDetails(
                                context, commentData, "Comment Details");
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
          ),
        ],
      );
    }
    else {
      return Container();
    }
  }

  void showCommentAddedDetails(BuildContext context, Map data, String title) {
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
                    text: data['created_EMAIL'] != null ? data['created_EMAIL'].toString() : "--",
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showTripSheetStatusDetails(data, context) {
    Map statusData = new Map();
    statusData = data;
    if (tripList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                      MainAxisAlignment.center,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showStatusDetails(
                                context, statusData, "TripSheet Status");
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
          ),
        ],
      );
    }
    else {
      return Container();
    }
  }

  void showStatusDetails(BuildContext context, Map data, String title) {
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
                /*closedByTime != null ? closedByTime.toString() : "--"*/
                new TextSpan(
                    text: data['closeTripNameBy'] != null ? data['closeTripNameBy'].toString() : "--",
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
                    text: data['closeTripReference'] != null ? data['closeTripReference'].toString() : "--",
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showIncomePaymentDetails(data, context) {
    Map incomeData = new Map();
    incomeData = data;
    if (incomeList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showIncomePaidDetails(
                                context, incomeData, "Income Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " ${data['incomeName']} : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 13,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['incomeAmount']
                                      .toString()} ",
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
          ),
        ],
      );
    }
    else {
      return Container();
    }
  }

  void showIncomePaidDetails(BuildContext context, Map data, String title) {
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
                    text: (DateFormat("dd-MM-yyyy").format( new DateTime.fromMicrosecondsSinceEpoch(data['created'] * 1000)) ).toString(),
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
            width: MediaQuery
                .of(context)
                .size
                .width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showExpensePaymentDetails(data, context) {
    Map expenseData = new Map();
    expenseData = data;
    if (expenseList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showExpensePaidDetails(
                                context, expenseData, "Expense Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " ${data['expenseName']} : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 13,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['expenseAmount']
                                      .toString()} ",
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
          ),
        ],
      );
    }
    else {
      return Container();
    }
  }

  void showExpensePaidDetails(BuildContext context, Map data, String title) {
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
                    text: (DateFormat("dd-MM-yyyy").format( new DateTime.fromMicrosecondsSinceEpoch(data['created'] * 1000)) ).toString(),
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
            width: MediaQuery
                .of(context)
                .size
                .width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Widget showAdvancePaymentDetails(data, context) {
    Map advanceData = new Map();
    advanceData = data;
    if (advanceList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showAdvancePaidDetails(
                                context, advanceData, "Advance Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Advance  Amount : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['advanceAmount']
                                      .toString()} ",
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
          ),
        ],
      );
    }
    else {
      return Container();
    }
  }

  static void showAdvancePaidDetails(BuildContext context, Map data,
      String title) {
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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
                    text: (DateFormat("dd-MM-yyyy").format( new DateTime.fromMicrosecondsSinceEpoch(data['created'] * 1000)) ).toString(),
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
            width: MediaQuery
                .of(context)
                .size
                .width,
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

        GestureDetector(
          onTap: (){
            showTripExtraDetails(context, "Trip Details");
          },
          child :Card(
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

                        Container(
                          margin: const EdgeInsets.only(left: 125.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showTripExtraDetails(context, "Trip Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ),
        ),

        GestureDetector(
          onTap: (){
            showDriverExtraDetails(context, "Driver Details");
          },
          child :
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

                        Container(
                          margin: const EdgeInsets.only(left: 118.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDriverExtraDetails(context, "Driver Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ),

        GestureDetector(
          onTap: (){
            showDispatchExtraDetails(context, "Dispatch Details");
          },
          child :
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

                        Container(
                          margin: const EdgeInsets.only(left: 94.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDispatchExtraDetails(context, "Dispatch Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ),

      ],
    );

  }

  void showTripExtraDetails(BuildContext context, String title) {
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
                    text: " Trip Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: routeAttendancePoint != null ? routeAttendancePoint.toString() : "-",
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
                    text: " Booking No : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,

                    )
                ),

                new TextSpan(
                    text: tripBookref != null ? tripBookref.toString() : "--" ,
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
                    text: " Closing KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripClosingKM.toString() != 'null' ? tripClosingKM.toString() : "--" ,
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
                    text: " Usage KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripUsageKM.toString() != 'null' ? tripUsageKM.toString() : "--",
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

  void showDriverExtraDetails(BuildContext context, String title) {
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
                    text: " Driver 2 : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:   tripSecDriverName.toString() != 'null null'  ? tripSecDriverName.toString() +"/"+ tripSecDriverMobile.toString()  : "--" ,
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
                    text: " Cleaner : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripCleanerName.toString() != 'null null' ? tripCleanerName.toString() +"/"+ tripCleanerMobile.toString()  : "--",
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
                    text: " Driver 1 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripFristDriverRoutePoint != null ? tripFristDriverRoutePoint.toString() : "--" ,
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
                    text: " Driver 2 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:  tripSecDriverRoutePoint != null ? tripSecDriverRoutePoint.toString() : "--",
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
                    text: " Cleaner Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripCleanerRoutePoint != null ? tripCleanerRoutePoint.toString() : "--" ,
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

  void showDispatchExtraDetails(BuildContext context, String title) {
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
                    text: " Closed By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: closedBy != null ? closedBy.toString() : "--" ,
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
                    text: " Closed Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: cloesdLocation != null ? cloesdLocation.toString() : "--",
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
                    text: " Closed Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:  closedByTime != null ? closedByTime.toString() : "--",
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
                    text: " Route Remark : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: Remark != null ? Remark.toString() : "--",
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


}




