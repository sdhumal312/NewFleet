import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/PickAndDrop/searchPickOrDropByVehicle.dart';
import 'package:fleetop/PickAndDrop/showPickOrDropData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import '../fleetopuriconstant.dart';

class CreatePickOrDrop extends KFDrawerContent {
  CreatePickOrDrop({
    Key key,
  });

  @override
  _CreatePickOrDropState createState() => _CreatePickOrDropState();
}

class _CreatePickOrDropState extends State<CreatePickOrDrop>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;
  String vehicleNameText;
  String vehicleId = '';
  String vendorId = '';
  String privousTime = '';
  String driverId = '';
  String vendorPartyId = '';
  String locationId = '';

  bool alwaysVisible = true;
  bool showNewPartyButton = false;
  bool showPartyMaster = true;
  bool showNewParty = false;
  bool showPickDropLocation = true;
  bool showNewRoot = false;

  List vehicleData = List();
  Map vehicleList = Map();
  Map configuration;
  Map driverNameList = Map();
  List driverNameData = List();

  Map partyNameList = Map();
  List partyNameData = List();
  Map locationNameList = Map();
  List locationNameData = List();

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("H:mm");
  List<bool> isTankSelected = [true, false];
  List<bool> isPaymentSelected = [true, false];

  TextEditingController vehicleName = new TextEditingController();
  TextEditingController fuelEntryDate = new TextEditingController();
  TextEditingController fuelEntryTime = new TextEditingController();
  TextEditingController driverName = new TextEditingController();
  TextEditingController partyName = new TextEditingController();
  TextEditingController newParty = new TextEditingController();
  TextEditingController rate = new TextEditingController();
  TextEditingController locationName = new TextEditingController();
  TextEditingController newRoute = new TextEditingController();
  TextEditingController totalKm = new TextEditingController();
  TextEditingController advance = new TextEditingController();
  TextEditingController amount = new TextEditingController();
  TextEditingController fuelEntryComments = new TextEditingController();
  TextEditingController pickAndDropSearch = new TextEditingController();

  @override
  void initState() {
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    var data = {'companyId': companyId};
    var response = await ApiCall.getDataFromApi(
        URI.INITIALIZE_FUEL_ENTRY_DATA, data, URI.LIVE_URI, context);
    if (response != null) {
      configuration = response['initializeObj']['configuration'];

      setState(() {});
    }
  }

  void showNewPartyOption() {
    if (showNewParty) {
      setState(() {
        showNewParty = false;
        showPartyMaster = true;
        newParty.text = '';
      });
    } else {
      setState(() {
        showNewParty = true;
        showPartyMaster = false;
        partyName.text = '';
        vendorPartyId = '0';
      });
    }
  }

  void showNewRouteOption() {
    if (showNewRoot) {
      setState(() {
        showNewRoot = false;
        showPickDropLocation = true;
        newRoute.text = '';
      });
    } else {
      setState(() {
        showNewRoot = true;
        showPickDropLocation = false;
        locationName.text = '';
        locationId = '0';
      });
    }
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 0));
    return true;
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  Future _handleSubmitted() async {
    if (!fieldValidation()) {
      return;
    } else {
      var tripData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vid': vehicleId,
        'journeyDate': fuelEntryDate.text,
        'journeyTime': fuelEntryTime.text,
        'tripFristDriverID': driverId,
        'vendorId': vendorPartyId,
        'newParty': newParty.text,
        'rate': rate.text,
        'pickOrDropStatus': isPaymentSelected[0] == true ? '1' : '2',
        'pickOrDropId': locationId,
        'newRouteName': newRoute.text,
        'tripUsageKM': totalKm.text,
        'amount': amount.text,
        'tripTotalAdvance': advance.text,
        'remark': fuelEntryComments.text,
      };

      print("olaaa....${tripData}");

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_PICK_OR_DROP_DATA, tripData, URI.LIVE_URI, context);

      print("uber....${data}");

      if (data != null) {
        if (data['sequenceNotFound'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else {
          refreshData();
          print("Id......${data['dispatchPickAndDropId']}");
          redirectToDisplay(data['dispatchPickAndDropId']);
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  refreshData() {
    vehicleId = '';
    vehicleName.text = '';
    fuelEntryDate.text = '';
    fuelEntryTime.text = '';
    driverId = '';
    driverName.text = '';
    vendorPartyId = '';
    partyName.text = '';
    newParty.text = '';
    rate.text = '';
    locationId = '';
    locationName.text = '';
    newRoute.text = '';
    totalKm.text = '';
    advance.text = '';
    amount.text = '';
    fuelEntryComments.text = '';
  }

  bool fieldValidation() {
    if (vehicleId == '' || vehicleId == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Valid Vehicle !", "Error");
      return false;
    }

    if (fuelEntryDate.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Valid Date !", "Error");
      return false;
    }

    if (fuelEntryTime.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Valid Time !", "Error");
      return false;
    }

    if (driverId == '' || driverId == '0') {
      FlutterAlert.onErrorAlert(context, "Please Select Driver !", "Error");
      return false;
    }

    if (showPartyMaster) {
      if (vendorPartyId == '' || vendorPartyId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Party Name !", "Error");
        return false;
      }
    } else {
      if (newParty.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select New Party Name !", "Error");
        return false;
      }
    }

    if (rate.text == '' || rate.text == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter a Rate !", "Error");
      return false;
    }

    if (rate.text == '' || rate.text == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter a Rate !", "Error");
      return false;
    }

    if (showPickDropLocation) {
      if (locationId == '' || locationId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Pick/Drop Location Name !", "Error");
        return false;
      }
    } else {
      if (newRoute.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select New Pick/Drop Location Name !", "Error");
        return false;
      }
    }

    if (totalKm.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Enter Total Km !", "Error");
      return false;
    }

    if (amount.text == '' || amount.text == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter Amount !", "Error");
      return false;
    }

    return true;
  }

  // Future _handleSubmitted() async {
  //   redirectToDisplay(40);
  // }

  Future<bool> redirectToDisplay(dispatchPickAndDropId) async => Alert(
        context: context,
        type: AlertType.success,
        title: "Success",
        desc: "Data Successfully Saved !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => getPickAndDropData(dispatchPickAndDropId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  getPickAndDropData(int PickAndDropId, context) async {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(
          builder: (context) => ShowPickOrDrop(
              dispatchPickAndDropId: PickAndDropId, navigateValue: false)),
    );
  }

  searchPickOrDropByNumber(String pickOrDropNumber) async {
    print("rrNumber...${pickOrDropNumber}");

    if (pickOrDropNumber != '') {
      var data = {
        'companyId': companyId,
        'tripsheetNumber': pickOrDropNumber,
        'userId': userId
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.SEARCH_PICK_OR_DROP_DATA, data, URI.LIVE_URI);
      print("response........${response}");
      if (response != null) {
        if (response['tripsheetNoFound'] == true) {
          int tripsheetPickAndDropId = response['tripsheetPickAndDropId'];
          print("tripsheetPickAndDropId........${tripsheetPickAndDropId}");
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => ShowPickOrDrop(
                    dispatchPickAndDropId: tripsheetPickAndDropId,
                    navigateValue: false)),
          );
        } else {
          FlutterAlert.onErrorAlert(
              context, "Please Enter Valid Tripsheet Number !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid Tripsheet Number !", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Tripsheet Number !", "Error");
    }
  }

  Widget appBarTitle = new Text("Create Pick Or Drop Tripsheet");
  Icon actionIcon = new Icon(Icons.search);

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    if (fuelEntryTime.text != privousTime) {
      privousTime = fuelEntryTime.text;
    }
    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.lightGreen,
          centerTitle: true,
          title: appBarTitle,
          leading: new IconButton(
            icon: new Icon(Icons.menu, color: Colors.white),
            onPressed: widget.onMenuPressed,
          ),
          actions: <Widget>[
            new IconButton(
              icon: actionIcon,
              onPressed: () {
                setState(() {
                  if (this.actionIcon.icon == Icons.search) {
                    this.actionIcon = new Icon(Icons.close);
                    this.appBarTitle = new TextField(
                      controller: pickAndDropSearch,
                      style: new TextStyle(
                        color: Colors.white,
                      ),
                      //focusNode: myFocusNode,
                      keyboardType: TextInputType.number,
                      decoration: new InputDecoration(
                        labelText: 'TS - Number',
                        labelStyle: TextStyle(color: Colors.white),
                        icon: new RaisedButton(
                          padding: const EdgeInsets.all(2.0),
                          textColor: Colors.white,
                          color: Colors.blue,
                          onPressed: () {
                            searchPickOrDropByNumber(pickAndDropSearch.text);
                          },
                          child: Padding(
                            padding: const EdgeInsets.symmetric(
                                vertical: 0.5, horizontal: 0.5),
                            child: Text(
                              "Search",
                              style: TextStyle(
                                  fontSize: 15,
                                  color: Colors.white,
                                  fontWeight: FontWeight.w500),
                            ),
                          ),
                        ),
                      ),
                    );
                  } else {
                    this.actionIcon =
                        new Icon(Icons.search, color: Colors.white);
                    this.appBarTitle =
                        new Text("Create Pick Or Drop Tripsheet");
                  }
                });
              },
            ),
          ]),
      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          _settingModalBottomSheet(context);
        },
        child: new Icon(Icons.add),
      ),
      backgroundColor: AppTheme.white,
      body: FutureBuilder(
        future: getData(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return SizedBox();
          } else {
            return Padding(
              padding: EdgeInsets.only(top: MediaQuery.of(context).padding.top),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Expanded(
                    child: FutureBuilder(
                      future: getData(),
                      builder: (context, snapshot) {
                        if (!snapshot.hasData) {
                          return SizedBox();
                        } else {
                          return Scaffold(
                            body: SingleChildScrollView(
                              padding: const EdgeInsets.all(8.0),
                              child: Center(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    Container(
                                      child: Padding(
                                        padding:
                                            const EdgeInsets.only(left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            SizedBox(
                                              height: 10.0,
                                            ),
                                            Align(
                                              alignment: Alignment.centerRight,
                                              child: Container(
                                                child: Icon(Icons.stars,
                                                    color: Colors.red,
                                                    size: 12),
                                              ),
                                            ),
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                                  false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                                  TextFieldConfiguration(
                                                controller: vehicleName,
                                                keyboardType:
                                                    TextInputType.number,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.directions_bus,
                                                      color: Colors.black,
                                                    ),
                                                    hintText: 'Vehicle Number',
                                                    labelText:
                                                        'Vehicle Number'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getVehicleNumberSuggestions(
                                                    pattern);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(
                                                      Icons.local_shipping),
                                                  title: Text(suggestion[
                                                      'vehicleName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                vehicleName.text =
                                                    suggestion['vehicleName'];
                                                vehicleId =
                                                    suggestion['vehicleId'];
                                                getVehicleGroupId(suggestion[
                                                    'vehicleGroupId']);
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 3),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            format: format,
                                            controller: fuelEntryDate,
                                            readOnly: true,
                                            initialValue: DateTime.now(),
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "Select Date",
                                                labelText: "Select Date",
                                                hintStyle: TextStyle(
                                                    color: Color(0xff493240)),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.date_range,
                                                  color: Colors.blue,
                                                )),
                                            //format: format,
                                            onShowPicker:
                                                (context, currentValue) {
                                              return showDatePicker(
                                                  context: context,
                                                  firstDate: DateTime(1950),
                                                  initialDate: currentValue ??
                                                      DateTime.now(),
                                                  lastDate: DateTime.now());
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 3),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            readOnly: true,
                                            format: timeFormat,
                                            controller: fuelEntryTime,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "Select Time",
                                                labelText: "Select Time",
                                                hintStyle: TextStyle(
                                                    color: Color(0xff493240)),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.access_time,
                                                  color: Colors.blue,
                                                )),
                                            onShowPicker:
                                                (context, currentValue) async {
                                              final time = await showTimePicker(
                                                context: context,
                                                initialTime:
                                                    TimeOfDay.fromDateTime(
                                                        currentValue ??
                                                            DateTime.now()),
                                                builder: (BuildContext context,
                                                    Widget child) {
                                                  return MediaQuery(
                                                    data: MediaQuery.of(context)
                                                        .copyWith(
                                                            alwaysUse24HourFormat:
                                                                true),
                                                    child: child,
                                                  );
                                                },
                                              );
                                              setState(() {
                                                fuelEntryTime.text =
                                                    fuelEntryTime.text;
                                              });
                                              return DateTimeField.convert(
                                                  time);
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding:
                                            const EdgeInsets.only(left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                                  false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                                  TextFieldConfiguration(
                                                controller: driverName,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.person,
                                                      color: Colors.cyan,
                                                    ),
                                                    hintText: 'Driver Name',
                                                    labelText: 'Driver Name'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getDriverNameSuggestions(
                                                    pattern, 1);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(Icons.person),
                                                  title: Text(
                                                      suggestion['driverName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                driverName.text =
                                                    suggestion['driverName'];
                                                driverId =
                                                    suggestion['driver_id'];
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Visibility(
                                        visible: showPartyMaster,
                                        child: Container(
                                          child: Padding(
                                            padding:
                                                const EdgeInsets.only(left: 10),
                                            child: Column(
                                              children: <Widget>[
                                                TypeAheadField(
                                                  hideSuggestionsOnKeyboardHide:
                                                      false,
                                                  hideOnEmpty: true,
                                                  textFieldConfiguration:
                                                      TextFieldConfiguration(
                                                    controller: partyName,
                                                    decoration: InputDecoration(
                                                        border: OutlineInputBorder(
                                                            borderRadius:
                                                                BorderRadius
                                                                    .circular(
                                                                        5.0)),
                                                        icon: Icon(
                                                          Icons.person,
                                                          color: Colors.amber,
                                                        ),
                                                        hintText: 'Party Name',
                                                        labelText:
                                                            'Party Name'),
                                                  ),
                                                  suggestionsCallback:
                                                      (pattern) async {
                                                    return await getPartyNameSuggestions(
                                                        pattern, 1);
                                                  },
                                                  itemBuilder:
                                                      (context, suggestion) {
                                                    return ListTile(
                                                      leading:
                                                          Icon(Icons.person),
                                                      title: Text(suggestion[
                                                          'partyName']),
                                                      // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                    );
                                                  },
                                                  onSuggestionSelected:
                                                      (suggestion) {
                                                    print(
                                                        "yes...${suggestion}");
                                                    partyName.text =
                                                        suggestion['partyName'];
                                                    vendorPartyId = suggestion[
                                                        'venParty_id'];
                                                    setState(() {
                                                      vendorPartyId =
                                                          suggestion[
                                                              'venParty_id'];
                                                    });
                                                  },
                                                ),
                                              ],
                                            ),
                                          ),
                                        )),
                                    SizedBox(height: 15),
                                    Visibility(
                                        visible: showNewPartyButton,
                                        child: Container(
                                          child: FlatButton.icon(
                                              onPressed: showNewPartyOption,
                                              icon: Icon(Icons.add),
                                              label: Text("New Party Name")),
                                        )),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showNewParty,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: newParty,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText:
                                                      'Enter New Party Name',
                                                  hintText:
                                                      "Enter New Party Name",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.people,
                                                    color: Colors.pink,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 5),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            onChanged: (val) {
                                              if (totalKm.text != '') {
                                                setState(() {
                                                  double amnt =
                                                      double.parse(val) *
                                                          double.parse(
                                                              totalKm.text);
                                                  amount.text = amnt.toString();
                                                });
                                              }
                                            },
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: rate,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Rate',
                                                hintText: 'Rate',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.rate_review,
                                                  color: Colors.greenAccent,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                          padding:
                                              const EdgeInsets.only(left: 10),
                                          child: Row(
                                              mainAxisAlignment:
                                                  MainAxisAlignment
                                                      .spaceBetween,
                                              children: [
                                                Icon(
                                                  Icons.settings,
                                                  color: Colors.purpleAccent,
                                                ),
                                                Text(
                                                  "Pick/Drop Status ",
                                                  style: new TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                  ),
                                                ),
                                                Visibility(
                                                  visible: alwaysVisible,
                                                  child: ToggleButtons(
                                                    borderColor: Colors.black,
                                                    fillColor:
                                                        Colors.greenAccent,
                                                    borderWidth: 0,
                                                    selectedBorderColor:
                                                        Colors.black,
                                                    selectedColor: Colors.white,
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            10),
                                                    children: <Widget>[
                                                      Padding(
                                                        padding:
                                                            const EdgeInsets
                                                                .all(8.0),
                                                        child: Text(
                                                          'Pick',
                                                          style: TextStyle(
                                                            fontSize: 18,
                                                            color: AppTheme
                                                                .darkText,
                                                            fontWeight:
                                                                FontWeight.w700,
                                                          ),
                                                        ),
                                                      ),
                                                      Padding(
                                                        padding:
                                                            const EdgeInsets
                                                                .all(8.0),
                                                        child: Text(
                                                          'Drop',
                                                          style: TextStyle(
                                                            fontSize: 18,
                                                            color: AppTheme
                                                                .darkText,
                                                            fontWeight:
                                                                FontWeight.w700,
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                    onPressed: (int index) {
                                                      setState(() {
                                                        for (int i = 0;
                                                            i <
                                                                isPaymentSelected
                                                                    .length;
                                                            i++) {
                                                          if (i == index) {
                                                            isPaymentSelected[
                                                                i] = true;
                                                          } else {
                                                            isPaymentSelected[
                                                                i] = false;
                                                          }
                                                        }
                                                      });
                                                    },
                                                    isSelected:
                                                        isPaymentSelected,
                                                  ),
                                                )
                                              ])),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Visibility(
                                        visible: showPickDropLocation,
                                        child: Container(
                                          child: Padding(
                                            padding:
                                                const EdgeInsets.only(left: 10),
                                            child: Column(
                                              children: <Widget>[
                                                TypeAheadField(
                                                  hideSuggestionsOnKeyboardHide:
                                                      false,
                                                  hideOnEmpty: true,
                                                  textFieldConfiguration:
                                                      TextFieldConfiguration(
                                                    controller: locationName,
                                                    decoration: InputDecoration(
                                                        border: OutlineInputBorder(
                                                            borderRadius:
                                                                BorderRadius
                                                                    .circular(
                                                                        5.0)),
                                                        icon: Icon(
                                                          Icons.my_location,
                                                          color: Colors.cyan,
                                                        ),
                                                        hintText:
                                                            'Pick/Drop Location',
                                                        labelText:
                                                            'Pick/Drop Location'),
                                                  ),
                                                  suggestionsCallback:
                                                      (pattern) async {
                                                    return await getLocationNameSuggestions(
                                                        pattern, 1);
                                                  },
                                                  itemBuilder:
                                                      (context, suggestion) {
                                                    return ListTile(
                                                      leading:
                                                          Icon(Icons.person),
                                                      title: Text(suggestion[
                                                          'locationName']),
                                                      // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                    );
                                                  },
                                                  onSuggestionSelected:
                                                      (suggestion) {
                                                    locationName.text =
                                                        suggestion[
                                                            'locationName'];
                                                    locationId = suggestion[
                                                        'location_id'];
                                                  },
                                                ),
                                              ],
                                            ),
                                          ),
                                        )),
                                    SizedBox(height: 15),
                                    Container(
                                      child: FlatButton.icon(
                                          onPressed: showNewRouteOption,
                                          icon: Icon(Icons.add),
                                          label:
                                              Text("New Pick/Drop Location")),
                                    ),
                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: showNewRoot,
                                      child: Container(
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: TextField(
                                              maxLines: 1,
                                              controller: newRoute,
                                              style: TextStyle(
                                                  color: Colors.black),
                                              decoration: InputDecoration(
                                                  labelText:
                                                      'Enter New Pick/Drop Location',
                                                  hintText: "",
                                                  hintStyle: TextStyle(
                                                      color: Colors.black),
                                                  border: OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              5.0)),
                                                  icon: Icon(
                                                    Icons.room,
                                                    color: Colors.pink,
                                                  )),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            onChanged: (val) {
                                              setState(() {
                                                double amnt =
                                                    double.parse(rate.text) *
                                                        double.parse(val);
                                                amount.text = amnt.toString();
                                              });
                                            },
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: totalKm,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Total KM',
                                                hintText: 'Total KM',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.radio_button_checked,
                                                  color: Colors.pink,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            readOnly: true,
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: amount,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Amount',
                                                hintText: 'Amount',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.credit_card,
                                                  color: Colors.brown,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: advance,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Advance',
                                                hintText: 'Advance',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.attach_money,
                                                  color: Colors.greenAccent,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 3,
                                            controller: fuelEntryComments,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Comments',
                                                hintText: 'Comments',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.comment,
                                                  color: Colors.pinkAccent,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 20),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                              borderRadius: BorderRadius.all(
                                                  Radius.circular(5.0)),
                                              color: Colors.lightGreen),
                                          child: MaterialButton(
                                            highlightColor: Colors.transparent,
                                            splashColor: Colors.purpleAccent,
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 10.0,
                                                      horizontal: 42.0),
                                              child: Text(
                                                "Submit",
                                                style: TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.white,
                                                    fontWeight:
                                                        FontWeight.w700),
                                              ),
                                            ),
                                            onPressed: _handleSubmitted,
                                          )),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          );
                        }
                      },
                    ),
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
  }

  void getVehicleGroupId(String vehicleGroupId) {
    print("groupId...$vehicleGroupId");

    if (vehicleGroupId == '107') {
      setState(() {
        showNewPartyButton = false;
      });
    } else {
      setState(() {
        showNewPartyButton = true;
      });
    }
  }

  getVehicleDetails(String str) async {
    vehicleList = new Map();
    fuelEntryTime.text = '';
    setState(() {
      vehicleData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_VEHICLE_DETAILS, data, URI.LIVE_URI);
      if (response != null) {
        if (response["vehicleList"] != null) {
          print(response["vehicleList"].length);
          for (int i = 0; i < response["vehicleList"].length; i++) {
            var obj = {
              "vehicleId": response['vehicleList'][i]['vid'].toString(),
              "vehicleName":
                  response['vehicleList'][i]['vehicle_registration'].toString(),
              "vehicleGroupId":
                  response['vehicleList'][i]['vehicleGroupId'].toString(),
            };
            vehicleList[obj['vehicleId']] = obj;
            setState(() {
              vehicleData = vehicleList.values.toList();
            });
          }
        } else {
          setState(() {
            vehicleData = [];
          });
          FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          vehicleData = [];
        });
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        vehicleData = [];
      });
    }
  }

  Future<List> getDriverNameSuggestions(String query, int checkDriver) async {
    getDriverNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(driverNameData.length, (index) {
      return driverNameData[index];
    });
  }

  getDriverNameDetails(String str, int checkDriver) async {
    driverNameList = new Map();
    driverId = '';
    setState(() {
      driverNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.DRIVER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["driverList"] != null) {
          print(response["driverList"].length);
          for (int i = 0; i < response["driverList"].length; i++) {
            var obj = {
              "driver_id": response['driverList'][i]['driver_id'].toString(),
              "driverName":
                  response['driverList'][i]['driver_empnumber'].toString() +
                      '-' +
                      response['driverList'][i]['driver_firstname'].toString() +
                      ' ' +
                      response['driverList'][i]['driver_Lastname'].toString()
            };
            driverNameList[obj['driver_id']] = obj;
            setState(() {
              driverNameData = driverNameList.values.toList();
            });
          }
        } else {
          setState(() {
            driverNameData = [];
          });
        }
      }
    } else {
      setState(() {
        driverNameData = [];
      });
    }
  }

  Future<List> getPartyNameSuggestions(String query, int checkDriver) async {
    getPartyNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(partyNameData.length, (index) {
      return partyNameData[index];
    });
  }

  getPartyNameDetails(String str, int checkDriver) async {
    partyNameList = new Map();
    vendorPartyId = '';
    setState(() {
      partyNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      print(data);
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_PARTY_NAME_DATA, data, URI.LIVE_URI);
      print(response);
      if (response != null) {
        if (response["partyNameList"] != null) {
          print(response["partyNameList"].length);
          for (int i = 0; i < response["partyNameList"].length; i++) {
            var obj = {
              "venParty_id":
                  response['partyNameList'][i]['corporateAccountId'].toString(),
              "partyName":
                  response['partyNameList'][i]['corporateName'].toString(),
              "rate": response['partyNameList'][i]['perKMRate'].toString(),
            };
            print(obj);
            partyNameList[obj['party_id']] = obj;
            setState(() {
              partyNameData = partyNameList.values.toList();
              rate.text = response['partyNameList'][i]['perKMRate'].toString();
            });
          }
        } else {
          setState(() {
            partyNameData = [];
          });
        }
      }
    } else {
      setState(() {
        partyNameData = [];
      });
    }
  }

  Future<List> getLocationNameSuggestions(String query, int checkDriver) async {
    getLocationNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(locationNameData.length, (index) {
      return locationNameData[index];
    });
  }

  getLocationNameDetails(String str, int checkDriver) async {
    locationNameList = new Map();
    locationId = '';
    setState(() {
      locationNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      print(data);
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_PICK_DROP_NAME_DATA, data, URI.LIVE_URI);
      print(response);
      if (response != null) {
        if (response["locationNameList"] != null) {
          print(response["locationNameList"].length);
          for (int i = 0; i < response["locationNameList"].length; i++) {
            var obj = {
              "location_id": response['locationNameList'][i]
                      ['pickAndDropLocationId']
                  .toString(),
              "locationName":
                  response['locationNameList'][i]['locationName'].toString()
            };
            locationNameList[obj['location_id']] = obj;
            setState(() {
              locationNameData = locationNameList.values.toList();
            });
          }
        } else {
          setState(() {
            locationNameData = [];
          });
        }
      }
    } else {
      setState(() {
        locationNameData = [];
      });
    }
  }

  Widget appBar() {
    return SizedBox(
      height: AppBar().preferredSize.height,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Visibility(
            visible: true,
            child: Padding(
              padding: EdgeInsets.only(top: 8, left: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
              ),
            ),
          ),
          Visibility(
            visible: true,
            child: Expanded(
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.only(top: 4),
                  child: Text(
                    "Fuel Entry",
                    style: new TextStyle(
                      fontSize: 22,
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ),
          ),
          Visibility(
            visible: true,
            child: Padding(
              padding: EdgeInsets.only(top: 2, right: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
                color: Colors.white,
                child: Material(
                  color: Colors.transparent,
                  child: InkWell(
                    borderRadius: new BorderRadius.circular(
                        AppBar().preferredSize.height),
                    child: Icon(
                      Icons.search,
                      color: AppTheme.dark_grey,
                    ),
                    onTap: () {
                      Navigator.of(context).push(new MaterialPageRoute(
                          builder: (context) => new SearchFuelEntry()));
                    },
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  void _settingModalBottomSheet(context) {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
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
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => SearchPickOrDropByVehicle()),
                    )
                  },
                  leading: new Icon(
                    Icons.directions_car,
                    color: Colors.red,
                  ),
                  title: Text(
                    "Search Pick Or Drop Trip By Vehicle",
                    style: new TextStyle(
                      fontSize: 18,
                      color: Colors.black,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ],
          );
        });
  }
}
