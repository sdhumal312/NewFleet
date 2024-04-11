import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/searchFuelEntryByVehicle.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/Utility/Utility.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:intl/intl.dart';
import 'package:path_provider/path_provider.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:location/location.dart';
import 'package:fleetop/CustomAutoComplete.dart';
import 'dart:math';
import '../fleetopuriconstant.dart';
import 'package:file_picker/file_picker.dart';

class FuelEntry extends KFDrawerContent {
  FuelEntry({
    Key key,
  });

  @override
  _FuelEntryState createState() => _FuelEntryState();
}

class _FuelEntryState extends State<FuelEntry> with TickerProviderStateMixin {
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
  String driverId = '';
  String privousTime = '';
  String driver2Id;
  String cleanerId;
  String routeServiceId;
  String tallyCompanyId;
  String fuelMeter;
  String vehicleGroupId;
  String expectedOdoMeter;
  bool showVehicleDropdown = false;
  bool allowGPSIntegration = false;
  Map vehicleList = Map();
  Map fuelVendorList = Map();
  Map tripSheetList = Map();
  Map driverNameList = Map();
  List driverNameData = List();
  Map cleanerNameList = Map();
  List cleanerNameData = List();
  Map routeServiceList = Map();
  List routeServiceData = List();
  Map tallyServiceList = Map();
  List tallyServiceData = List();
  bool gpsWorking = false;
  bool grpfuelOdometer = true;
  bool validateTripSheetNumber = false;
  bool doNotValidateBackDateEntries = false;
  bool bindMinMaxOdometerOnTripSheet = false;
  bool allowManualOdometerEntry = false;
  bool firstFuelEntry = false;
  bool creatingBackDateFuelEntry = false;
  bool readOnlyOpenOdometer = false;

  List fuelVendorData = List();
  List vehicleData = List();
  List tripSheetData = List();
  List fuelTypeList = List();
  Map configuration;
  bool showDriver2 = false;
  bool showCleaner = false;
  bool creditVisible = false;
  bool showRoot = false;
  bool validateDriver1 = false;
  bool selectAutoCredit = false;
  bool validateKMPL = false;
  bool showTally = false;
  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;
  String latitude = '0';
  String longitude = '0';
  var validateOdometerInFuel;
  var validateMinOdometerInFuel;
  var selectedTripSheetNumber = '0';
  var selectedTripSheetNumberInt = 0;
  var currentLocation;
  var fuelTypeValue = 0;

  double previousFuelEntryCapacity = 0;
  int nextFuelIdOfBackDate = 0;
  int nextFuelMeterOfBackDate = 0;
  String backdateBetweenPreviousAndNext = '';
  String odometerRange = '';
  int minOdometerVal = 0;
  int maxOdometerVal = 0;
  int actualOdameterReading = 0;
  bool meterNotWorking = false;
  bool showNote = false;
  bool showRange = false;
  bool showActiveTSNote = false;
  String fuelStockDetailsString = "";
  bool allowOwnPetrolPump = false;
  String ownPetrolPump = "0";

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");
  List<bool> isTankSelected = [true, false];
  List<bool> isPaymentSelected = [true, false];
  TextEditingController vehicleName = new TextEditingController();
  TextEditingController vehicleGroup = new TextEditingController();
  TextEditingController fuelEntryDate = new TextEditingController();
  TextEditingController fuelEntryTime = new TextEditingController();
  TextEditingController openOdoMeter = new TextEditingController();
  TextEditingController odoMeter = new TextEditingController();
  TextEditingController fuelType = new TextEditingController();
  TextEditingController fuelVendor = new TextEditingController();
  TextEditingController fuelLiter = new TextEditingController();
  TextEditingController fuelPrice = new TextEditingController();
  TextEditingController driverName = new TextEditingController();
  TextEditingController driver2name = new TextEditingController();
  TextEditingController cleaner = new TextEditingController();
  TextEditingController routeService = new TextEditingController();
  TextEditingController fuelEntryComments = new TextEditingController();
  TextEditingController gpsOdoMeter = new TextEditingController();
  TextEditingController vehicleNumber = new TextEditingController();
  TextEditingController mileageFrom = new TextEditingController();
  TextEditingController mileageTo = new TextEditingController();
  TextEditingController tallyCompany = new TextEditingController();
  int IS_OWNED_PETROL_PUMP = 1;
  var maximumFuelCapacity = 0;
  double maximumFuelPrice = 0;
  bool showSelectedImages = false;
  File selectedImageFile;
  String fileName;
  String path;
  File fileData;
  Map<String, String> paths;
  List<String> extensions;
  bool isLoadingPath = false;
  bool isMultiPick = false;
  FileType fileType;
  double fileSize = 0;
  double expectedFileSize = 512.0;
  bool blockGalleryImageSelection = false;
  bool setFuelEntryDefaultDateAndTime = false;
  bool fileIsValid = true;
  bool onlyReadDateAndTime = false;
  String _setTime, _setDate;
  String _hour, _minute, _time;
  String dateTime;
  DateTime selectedDate = DateTime.now();
  TimeOfDay selectedTime = TimeOfDay(hour: 00, minute: 00);
  bool blockSaveFuelEntry = false;
  bool getStockFromInventory = false;
  String fuelInvoiceId = "";
  TextEditingController fuelAmount = new TextEditingController();
  @override
  void initState() {
    setExtensionData();
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  setExtensionData() {
    extensions = new List<String>();
    extensions.add("pdf");
    extensions.add("xlsx");
  }

  void setDataForLiter() {
    if (fuelLiter != null &&
        fuelLiter.text != null &&
        fuelLiter.text.length > 0) {
      if (fuelLiter.text.contains(".")) {
        double num1 =
            double.parse((double.parse(fuelLiter.text)).toStringAsFixed(2));
        fuelLiter.text = num1.toString();
      }
    }
  }

  calculateFuelPrice() {
    try {
      double totalAMt = 0.0;
      double fuelLiterVal = FileUtility.checkAndSetNumDouble(fuelLiter);
      double fuelPriceVal = FileUtility.checkAndSetNumDouble(fuelPrice);
      totalAMt = fuelLiterVal * fuelPriceVal;
      setState(() {
        fuelAmount.text = Utility.getLimitedData(totalAMt);
      });
    } catch (e) {
      setState(() {
        fuelAmount.text = '0';
      });
    }
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
      getStockFromInventory =
          Utility.hasKeyInConfig(configuration, "getStockFromInventory");
      if (response["initializeObj"] != null &&
          response["initializeObj"]["maxFuelCapacity"] != null) {
        setState(() {
          maximumFuelCapacity = response["initializeObj"]["maxFuelCapacity"];
        });
      }

      if (response["initializeObj"] != null &&
          response["initializeObj"]["defaultFuelPrice"] != null) {
        setState(() {
          maximumFuelPrice = response["initializeObj"]["defaultFuelPrice"];
        });
      }
      if (response["initializeObj"] != null) {
        if (response["initializeObj"]["blockGalleryImageSelection"] != null) {
          setState(() {
            blockGalleryImageSelection =
                response["initializeObj"]["blockGalleryImageSelection"];
          });
        }
        if (response["initializeObj"]["setFuelEntryDefaultDateAndTime"] !=
            null) {
          setState(() {
            setFuelEntryDefaultDateAndTime =
                response["initializeObj"]["setFuelEntryDefaultDateAndTime"];
          });
        }
      }
      if (response["initializeObj"] != null &&
          response["initializeObj"]["allowOwnPetrolPump"] != null) {
        //allowOwnPetrolPump = response["initializeObj"]["allowOwnPetrolPump"];
      }
      setState(() {
        showRoot = configuration['showRoute'];
        showDriver2 = configuration['showDriver2'];
        showCleaner = configuration['showCleaner'];
        validateDriver1 = configuration['validateDriver1'];
        selectAutoCredit = configuration['selectAutoCredit'];
        validateTripSheetNumber = configuration['validateTripSheetNumber'];
        doNotValidateBackDateEntries =
            configuration['doNotValidateBackDateEntries'];
        bindMinMaxOdometerOnTripSheet =
            configuration['bindMinMaxOdometerOnTripSheet'];
        allowManualOdometerEntry = configuration['allowManualOdometerEntry'];
        showTally = configuration['tallyImportRequired'];

        allowGPSIntegration = response['initializeObj']['allowGPSIntegration'];
        validateOdometerInFuel =
            response['initializeObj']['validateOdometerInFuel'];
        validateMinOdometerInFuel =
            response['initializeObj']['validateMinOdometerInFuel'];
      });

      setLocationData();
    }
    if (setFuelEntryDefaultDateAndTime != null &&
        setFuelEntryDefaultDateAndTime) {
      setDefaultDateAndTime();
    }
  }

  setDefaultDateAndTime() {
    DateTime now = new DateTime.now();
    String act = FileUtility.getStandardDate(now.day);
    String acmonth = FileUtility.getStandardMonth(now.month);
    String hourAndMinute = FileUtility.getStandardTime(now.hour, now.minute);
    String dt = act + "-" + acmonth + "-" + now.year.toString();
    setState(() {
      fuelEntryDate.text = dt;
      fuelEntryTime.text = hourAndMinute;
      onlyReadDateAndTime = true;
    });
  }

  setLocationData() async {
    var location = new Location();
    try {
      if (await location.hasPermission()) {
        var currentLocation = await location.getLocation();
        setState(() {
          latitude = currentLocation.latitude.toString();
          longitude = currentLocation.longitude.toString();
        });
      } else {
        await location.requestPermission();
      }
    } catch (e) {
      print(e);
      location = null;
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

  void testCompressAndGetFile(File file, String targetPath) async {
    var result = await FlutterImageCompress.compressAndGetFile(
        file.absolute.path, targetPath,
        rotate: 360, quality: 100, minWidth: 800, minHeight: 800);
    Navigator.pop(context, 'Cancel');
    getBase64Image(result);
  }

  Future<String> getBase64Image(File file) async {
    if (file != null) {
      base64Image = base64Encode(file.readAsBytesSync());
      setState(() {
        imageData = base64Image;
      });
      File filef = await _createFileFromString(imageData);
      setState(() {
        uploadedFile = base64Image;
        selectedImageFile = filef;
      });
    }
  }

  Future _openFileExplorer() async {
    Navigator.pop(context, 'Cancel');
    setState(() {
      fileName = "";
      path = "";
      fileData = null;
      paths = null;
    });
    setState(() => isLoadingPath = true);
    try {
      fileData = await FilePicker.getFile(
          type: FileType.custom, allowedExtensions: extensions);
      getFileSize(fileData.path, 1);
      var ext = fileData.path.split(".").last;
      paths = null;

      setState(() {
        imageName = fileData.path.split("/").last;
        imageExt = ext;
        base64Image = base64Encode(fileData.readAsBytesSync());
        imageData = base64Image;
        selectedImageFile = null;
        showSelectedImages = false;
      });
      fileIsValid = FileUtility.validateFileExtension(extensions, imageExt);
    } on PlatformException catch (e) {
      print("Unsupported operation" + e.toString());
    }
    if (!mounted) return;
  }

  Future<String> getFileSize(String filepath, int decimals) async {
    var file = File(filepath);
    int bytes = await file.length();
    if (bytes <= 0) return "0 B";
    const suffixes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    var i = (log(bytes) / log(1024)).floor();
    String ab = ((bytes / pow(1024, i)).toStringAsFixed(decimals));
    fileSize = double.parse(ab);
    return ((bytes / pow(1024, i)).toStringAsFixed(decimals)) +
        ' ' +
        suffixes[i];
  }

  Future getImageGallery() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.gallery, maxHeight: 600, maxWidth: 600);
    if (imageFile == null) {
      return;
    }
    setState(() {
      myImage = imageFile;
      fileIsValid = true;
    });
    imageName = myImage.path.split("/").last;
    imageExt = imageName.split(".").last;

    testCompressAndGetFile(myImage, myImage.path);
  }

  Future<File> _createFileFromString(String decodedString) async {
    Uint8List bytes = base64.decode(decodedString);
    String dir = (await getApplicationDocumentsDirectory()).path;
    File file = File(
        "$dir/" + DateTime.now().millisecondsSinceEpoch.toString() + ".jpeg");
    await file.writeAsBytes(bytes);
    return file;
  }

  getTripSheetNumber(String vehicleId) async {
    tripSheetList = new Map();
    setState(() {
      tripSheetData = [];
    });
    try {
      var data = {'companyId': companyId, 'vehicleId': vehicleId};
      var result = await ApiCall.getDataFromApi(
          URI.TRIP_LIST_BY_VEHICLE_ID, data, URI.LIVE_URI, context);
      if (result != null) {
        if (result['tripFinalList'] != null &&
            result['tripFinalList'].length > 0) {
          var obj1 = {
            "tripSheetID": "0",
            "tripSheetNumber": "Select TripSheet",
          };
          tripSheetList[obj1['tripSheetID']] = obj1;
          for (int i = 0; i < result['tripFinalList'].length; i++) {
            var obj = {
              "tripSheetID":
                  result['tripFinalList'][i]['tripSheetID'].toString(),
              "tripSheetNumber": "TS - " +
                  result['tripFinalList'][i]['tripSheetNumber'].toString() +
                  " : " +
                  result['tripFinalList'][i]['tripOpenDate'].toString() +
                  " - " +
                  result['tripFinalList'][i]['closetripDate'].toString(),
            };
            tripSheetList[obj['tripSheetID']] = obj;
            setState(() {
              tripSheetData = tripSheetList.values.toList();
            });
          }
        } else {
          setState(() {
            tripSheetData = [];
          });
        }
      } else {
        setState(() {
          tripSheetData = [];
        });
      }
    } catch (e) {
      print(e);
    }
  }

  Widget showRadiobtn() {
    return Card(
        semanticContainer: true,
        color: Colors.cyanAccent,
        margin: EdgeInsets.only(left: 5, top: 15, right: 5),
        shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
        borderOnForeground: true,
        elevation: 1.0,
        child: SwitchListTile(
            title: Text("Show Selected Image"),
            activeColor: Colors.amber,
            inactiveThumbColor: Colors.black,
            value: showSelectedImages,
            onChanged: (bool value) {
              setState(() {
                showSelectedImages = value;
              });
            }));
  }

  Widget showSelectedImage() {
    if (showSelectedImages && selectedImageFile != null) {
      return Card(
        margin: EdgeInsets.only(left: 5, top: 5, right: 5),
        shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
        borderOnForeground: true,
        elevation: 1.0,
        child:
            Container(child: Image.file(selectedImageFile, fit: BoxFit.fill)),
      );
    } else {
      return Container();
    }
  }

  Future getImageFromCamera() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.camera,
        maxHeight: 800,
        maxWidth: 800,
        imageQuality: 100);
    if (imageFile == null) {
      return;
    }
    imageName = imageFile.path.split("/").last;
    imageExt = imageName.split(".").last;

    setState(() {
      myImage = imageFile;
      fileIsValid = true;
    });
    testCompressAndGetFile(myImage, myImage.path);
    setState(() {
      uploadedFile = base64Image;
    });
  }

  openBottomSheet() {
    if (blockGalleryImageSelection != null && !blockGalleryImageSelection) {
      openBottomSheetForAll();
    } else {
      openBottomSheetForCameraAndDoc();
    }
  }

  openBottomSheetForCameraAndDoc() {
    void containerForSheet<T>({BuildContext context, Widget child}) {
      showCupertinoModalPopup<T>(
        context: context,
        builder: (BuildContext context) => child,
      ).then<void>((T value) {});
    }

    containerForSheet<String>(
      context: context,
      child: CupertinoActionSheet(
          title: const Text(
            'Select Image 😊',
          ),
          actions: <Widget>[
            CupertinoActionSheetAction(
              child: const Text("⟃ ⟄ Select Image From Camera"),
              onPressed: getImageFromCamera,
            ),
            CupertinoActionSheetAction(
              child: const Text("⟃ ⟄ Select Documents"),
              onPressed: _openFileExplorer,
            ),
          ],
          cancelButton: CupertinoActionSheetAction(
            child: const Text('Cancel'),
            isDefaultAction: true,
            onPressed: () {
              Navigator.pop(context, 'Cancel');
            },
          )),
    );
  }

  openBottomSheetForAll() {
    void containerForSheet<T>({BuildContext context, Widget child}) {
      showCupertinoModalPopup<T>(
        context: context,
        builder: (BuildContext context) => child,
      ).then<void>((T value) {});
    }

    containerForSheet<String>(
      context: context,
      child: CupertinoActionSheet(
          title: const Text(
            'Select Image 😊',
          ),
          actions: <Widget>[
            CupertinoActionSheetAction(
                child: const Text(" ▦ Select Image From Gallery"),
                onPressed: getImageGallery),
            CupertinoActionSheetAction(
              child: const Text("⟃ ⟄ Select Image From Camera"),
              onPressed: getImageFromCamera,
            ),
            CupertinoActionSheetAction(
              child: const Text("⟃ ⟄ Select Documents"),
              onPressed: _openFileExplorer,
            ),
          ],
          cancelButton: CupertinoActionSheetAction(
            child: const Text('Cancel'),
            isDefaultAction: true,
            onPressed: () {
              Navigator.pop(context, 'Cancel');
            },
          )),
    );
  }

  setLocation() {
    Navigator.pop(context);
    setLocationData();
  }

  Future<bool> switchONGPSAlert() async => Alert(
        context: context,
        type: AlertType.info,
        title: "INFO",
        desc: "Please switch ON the GPS first to do Fuel Entry !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => setLocation(),
            gradient: LinearGradient(colors: [Colors.grey, Colors.black]),
          ),
        ],
      ).show();

  Future<bool> redirectToDisplay(fuelId) async => Alert(
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
            onPressed: () => getFuelData(fuelId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  getFuelData(var fuelId, context) async {
    Navigator.pop(context);
    var fuelData = {'companyId': companyId, 'fuelId': fuelId.toString()};
    var data = await ApiCall.getDataFromApi(
        URI.GET_FUEL_DETAILS, fuelData, URI.LIVE_URI, context);
    if (data != null) {
      Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) =>
              new ShowFuelDetails(fueldata: data['fuelDetails'])));
    }
  }

  checkFinalValidation() {
    if (allowOwnPetrolPump) {
      getFuelStockDetails(true);
    } else {
      _handleSubmitted();
    }
  }

  Future _handleSubmitted() async {
    if (!fieldValidation()) {
      return;
    } else {
      var fuelData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'FuelSelectVehicle': vehicleId,
        'vehicle_group': vehicleGroup.text,
        'fuelDate': fuelEntryDate.text,
        'fuelTime': fuelEntryTime.text.trim(),
        'fuel_meter_old': openOdoMeter.text,
        'fuel_meter': odoMeter.text,
        'fuel_type': fuelTypeValue.toString(),
        'selectVendor': vendorId.toString(),
        'fuel_liters': fuelLiter.text,
        'fuel_price': fuelPrice.text,
        'VehicleTODriverFuel': driverId,
        'VehicleTODriver2Fuel': driver2Id,
        'VehicleTOCleanerFuel': cleanerId,
        'TripRouteList': routeServiceId,
        'tallyCompanyId': tallyCompanyId,
        'fuel_tank': isTankSelected[0] == true ? 0 : 1,
        "fuel_comments": fuelEntryComments.text,
        'base64String': imageData,
        'imageName': imageName,
        'imageExt': imageExt,
        'tripSheetId': selectedTripSheetNumber,
        'latitude': latitude,
        'longitude': longitude,
        'ownPetrolPump': ownPetrolPump.toString()
      };
      if (getStockFromInventory != null && getStockFromInventory) {
        fuelData["getStockFromInventoryConfig"] =
            getStockFromInventory.toString();
        fuelData["fuelInvoiceId"] = fuelInvoiceId.toString();
        fuelData["currentFuelInvoiceId"] = fuelInvoiceId.toString();
        fuelData["fuelAmount"] = fuelAmount.text;
      }
      if (creditVisible == true) {
        fuelData['paymentTypeId'] = isPaymentSelected[0] == true ? 1 : 2;
      } else {
        fuelData['paymentTypeId'] = 1;
      }
      final jsonEncoder = JsonEncoder();
      var finalData = {'FuelData': jsonEncoder.convert(fuelData)};

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_FUEL_ENTRY_DETAILS, finalData, URI.LIVE_URI, context);

      if (data != null) {
        if (data['alreadyExist'] == true) {
          FlutterAlert.onInfoAlert(context, "Already Exist!", "Info");
        } else if (data['contactAdmin'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else if (data['odoMeter'] != null) {
          FlutterAlert.onInfoAlert(context, "" + data['odoMeter'], "Info");
        } else if (data['vehicleStatus'] != null) {
          FlutterAlert.onInfoAlert(context, "" + data['vehicleStatus'], "Info");
        } else {
          refreshData();
          getSessionData();
          redirectToDisplay(data['SuccessId']);
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
    vehicleGroup.text = '';
    fuelEntryDate.text = '';
    fuelEntryTime.text = '';
    openOdoMeter.text = '';
    odoMeter.text = '';
    fuelTypeValue = 0;
    fuelVendor.text = '';
    fuelLiter.text = '';
    fuelPrice.text = '';
    driverId = '';
    driverName.text = '';
    driver2Id = '';
    driver2name.text = '';
    cleaner.text = '';
    cleanerId = '';
    routeService.text = '';
    routeServiceId = '';
    tallyCompany.text = '';
    tallyCompanyId = '';
    imageData = '';
    fuelEntryComments.text = '';
    gpsOdoMeter.text = '';
    setState(() {
      selectedTripSheetNumber = '0';
      selectedTripSheetNumberInt = 0;
      getStockFromInventory = false;
      fuelInvoiceId = "";
      fuelAmount.text = "";
      blockSaveFuelEntry = false;
      tripSheetData = [];
      meterNotWorking = false;
      showNote = false;
      showRange = false;
      showActiveTSNote = false;
      fuelStockDetailsString = "";
      allowOwnPetrolPump = false;
      ownPetrolPump = "0";
      showSelectedImages = false;
      selectedImageFile = null;
      blockGalleryImageSelection = false;
      fileIsValid = true;
      onlyReadDateAndTime = false;
      blockSaveFuelEntry = false;
    });
    if (setFuelEntryDefaultDateAndTime != null &&
        setFuelEntryDefaultDateAndTime) {
      setDefaultDateAndTime();
    }
    setFuelEntryDefaultDateAndTime = false;
  }

  bool fieldValidation() {
    if (blockSaveFuelEntry != null && blockSaveFuelEntry) {
      FlutterAlert.onErrorAlert(context,
          "Please Select Different Time or Wait for a Minute!", "Error");
      return false;
    }
    if (fileIsValid != null && !fileIsValid) {
      FlutterAlert.onErrorAlert(
          context, "Invalid Selected File PDF,EXCEL allowed only !", "Error");
      return false;
    }
    if (latitude == '0' && longitude == '0') {
      switchONGPSAlert();
      return false;
    }
    if (vehicleId == '' || vehicleId == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Valid Vehicle !", "Error");
      return false;
    }
    if (validateKMPL) {
      addKMPL(context);
      return false;
    }
    if (validateTripSheetNumber) {
      if (tripSheetData.length > 0) {
        if (selectedTripSheetNumber == '' || selectedTripSheetNumber == '0') {
          FlutterAlert.onErrorAlert(
              context, "Please Select Trip Sheet Number !", "Error");
          return false;
        }
      }
    }
    if (fuelEntryDate.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Valid Date !", "Error");
      return false;
    }
    if (fuelEntryTime.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Valid Time !", "Error");
      return false;
    }
    if (openOdoMeter.text == '' || openOdoMeter.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Open Odometer !", "Error");
      return false;
    }
    if (odoMeter.text == '' || odoMeter.text == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter Odometer !", "Error");
      return false;
    }
    if (fuelTypeValue == 0) {
      FlutterAlert.onErrorAlert(context, "Please Select Fuel Type !", "Error");
      return false;
    }

    if (!validateMaxOdameter()) {
      return false;
    }

    if (fuelVendor.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter a Vendor Name !", "Error");
      return false;
    }
    if (fuelLiter.text == '' || fuelLiter.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter a Fuel Liter !", "Error");
      return false;
    }
    if (fuelPrice.text == '' || fuelPrice.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter a Fuel Price !", "Error");
      return false;
    }

    if (validateDriver1 == true) {
      if (driverId == '' || driverId == '0') {
        FlutterAlert.onErrorAlert(context, "Please Enter Driver !", "Error");
        return false;
      }
    }

    if (showTally == true) {
      if (tallyCompanyId == '' || tallyCompanyId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Tally Company !", "Error");
        return false;
      }
    }
    if (fuelLiter.text != '' && fuelLiter.text.length > 0) {
      String fl = fuelLiter.text;
      if (fl.contains('.')) {
        var val = double.parse(fuelPrice.text);
        if (val > maximumFuelCapacity) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Liter should not be grater than  $maximumFuelCapacity",
              "Error");
          return false;
        }
      } else {
        int val = int.parse(fuelLiter.text);
        if (val > maximumFuelCapacity) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Liter should not be grater than  $maximumFuelCapacity",
              "Error");
          return false;
        }
      }
    }
    if (fuelPrice.text != '' && fuelPrice.text.length > 0) {
      String fp = fuelPrice.text;

      if (fp.contains('.')) {
        var val = double.parse(fuelPrice.text);
        if (val >= maximumFuelPrice) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Price should not be grater than  $maximumFuelPrice",
              "Error");
          return false;
        }
      } else {
        var val = int.parse(fuelPrice.text);
        if (val >= maximumFuelPrice) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Price should not be grater than  $maximumFuelPrice",
              "Error");
          return false;
        }
      }
    }
    return true;
  }

  validateMaxOdameter() {
    if (meterNotWorking) {
      return true;
    }

    bool validateFuel = false;

    if (validateOdometerInFuel == true) {
      validateFuel = true;
    }

    if (num.parse(odoMeter.text) <= 0) {
      FlutterAlert.onErrorAlert(context, "Odometer cannot be zero !", "Error");
      return false;
    }

    if (creatingBackDateFuelEntry && doNotValidateBackDateEntries == true) {
      validateFuel = false;
    }

    if (allowManualOdometerEntry == true) {
      validateFuel = false;

      if (num.parse(odoMeter.text) <= num.parse(openOdoMeter.text)) {
        FlutterAlert.onErrorAlert(context,
            "Odometer should be greater than Open Odometer !", "Error");
        return false;
      }

      if ((num.parse(odoMeter.text) - num.parse(openOdoMeter.text)) >
          num.parse(expectedOdoMeter)) {
        FlutterAlert.onErrorAlert(
            context,
            "Difference between Open Odometer and Current Odometer cannot be greater then" +
                expectedOdoMeter,
            "Error");
        return false;
      }
    }

    if (num.parse(odoMeter.text) <= num.parse(openOdoMeter.text)) {
      FlutterAlert.onErrorAlert(
          context, "Odometer should be greater than Open Odometer !", "Error");
      return false;
    }

    if (validateFuel) {
      if (num.parse(odoMeter.text) < minOdometerVal ||
          num.parse(odoMeter.text) > maxOdometerVal) {
        FlutterAlert.onErrorAlert(
            context,
            "Please Enter Odometer in Range Of :" +
                minOdometerVal.toString() +
                " - " +
                maxOdometerVal.toString(),
            "Error");
        return false;
      }
    }

    return true;
  }

  getVehicleGPSDataAtTime() async {
    if (vehicleId == '' || vehicleId == '0') {
      return;
    }
    if (fuelEntryDate.text == '') {
      return;
    }
    var gpsData = {
      'vehicleId': vehicleId,
      'companyId': companyId,
      'dispatchedByTime': fuelEntryDate.text,
      'dispatchTime': fuelEntryTime.text.trim()
    };
    var data = await ApiCall.getDataWithoutLoader(
        URI.VEHICLE_GPS_DATA_AT_TIME, gpsData, URI.LIVE_URI);
    if (data != null) {
      if (data['VEHICLE_TOTAL_KM'] != null) {
        gpsOdoMeter.text = data['VEHICLE_TOTAL_KM'].toString();
        odoMeter.text = data['VEHICLE_TOTAL_KM'].toString();
        setState(() {
          gpsWorking = true;
          grpfuelOdometer = true;
        });
      } else {
        setState(() {
          gpsWorking = false;
          grpfuelOdometer = true;
          gpsOdoMeter.text = '';
        });
      }
    }
  }

  Future saveKMPLDetails() async {
    if (!validateKMPLData()) {
      return;
    } else {
      var kmplData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vehicleKmplId': vehicleId,
        'expectedMileageFrom': mileageFrom.text,
        'expectedMileageTo': mileageTo.text
      };

      var data = await ApiCall.getDataFromApi(
          URI.UPDATE_VEHICLE_KMPL_DETAILS, kmplData, URI.LIVE_URI, context);

      if (data != null) {
        if (data['kmplUpdated'] == true) {
          Navigator.pop(context);
          setState(() {
            mileageFrom.text = '';
            mileageTo.text = '';
            validateKMPL = false;
          });
          FlutterAlert.onSuccessAlert(
              context, "KMPL Data Updated Successfully !", "Success");
        } else {
          FlutterAlert.onErrorAlert(context,
              "Some Problem Occur,Please contact on Support !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool validateKMPLData() {
    if (mileageFrom.text == '' || mileageFrom.text == '0') {
      FlutterAlert.onErrorAlert(context,
          "Please Enter Expected Mileage From Greater Than 0 !", "Error");
      return false;
    }

    if (mileageTo.text == '' || mileageTo.text == '0') {
      FlutterAlert.onErrorAlert(context,
          "Please Enter Expected Mileage To Greater Than 0 !", "Error");
      return false;
    }
    return true;
  }

  Widget vehicleNumberAc() {
    return Container(
        child: Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Container(
          child: Icon(
            FontAwesomeIcons.truck,
            color: Colors.blue,
          ),
        ),
        Container(
          margin: EdgeInsets.only(top: 10, left: 5, bottom: 10),
          width: _width - 60,
          child: CustomAutoComplete(
              keyboardType: TextInputType.number,
              controller: vehicleName,
              suggestionList: vehicleData,
              hintLabel: 'Vehicle Number',
              label: 'Vehicle Number',
              dataKeyName: 'vehicleName',
              iconData: FontAwesomeIcons.truck,
              hintStyle: TextStyle(
                  fontSize: 12.0,
                  fontWeight: FontWeight.bold,
                  fontFamily: "WorkSansBold"),
              onItemSelected: (suggestion) {
                vehicleName.text = suggestion['vehicleName'];
                getOdoMeterDetails(suggestion['vehicleId'], context);
                if (setFuelEntryDefaultDateAndTime != null &&
                    setFuelEntryDefaultDateAndTime) {
                  getActiveTripSheetAndBackDateData(false);
                }
              },
              onChanged: (pattern) {
                if (setFuelEntryDefaultDateAndTime != null &&
                    setFuelEntryDefaultDateAndTime) {
                  resetDateTimeData();
                }

                getVehicleDetails(pattern);
              }),
        )
      ],
    ));
  }

  resetDateTimeData() {
    if (setFuelEntryDefaultDateAndTime != null &&
        !setFuelEntryDefaultDateAndTime) {
      setState(() {
        fuelEntryDate.text = '';
        fuelEntryTime.text = '';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    if (fuelEntryTime.text != privousTime) {
      setState(() {
        meterNotWorking = false;
        showNote = false;
        showRange = false;
        showActiveTSNote = false;
      });
      privousTime = fuelEntryTime.text;
      if (allowGPSIntegration == true) {
        getVehicleGPSDataAtTime();
      }
    }
    return Scaffold(
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
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      ClipRRect(
                        borderRadius: BorderRadius.all(Radius.circular(32.0)),
                        child: Material(
                          shadowColor: Colors.transparent,
                          color: Colors.transparent,
                          child: IconButton(
                            icon: Icon(
                              Icons.menu,
                              color: Colors.black,
                            ),
                            onPressed: widget.onMenuPressed,
                          ),
                        ),
                      ),
                      Center(
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
                      Padding(
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
                                Navigator.of(context).push(
                                    new MaterialPageRoute(
                                        builder: (context) =>
                                            new SearchFuelEntry()));
                              },
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
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
                                    SizedBox(height: showNote == true ? 5 : 0),
                                    Visibility(
                                        visible: showNote,
                                        child: Container(
                                          child: Align(
                                            alignment: Alignment.center,
                                            child: Text(
                                                backdateBetweenPreviousAndNext,
                                                style: new TextStyle(
                                                  color: Colors.blueAccent,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15,
                                                )),
                                          ),
                                        )),

                                    SizedBox(height: 5),
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
                                            vehicleNumberAc(),
                                          ],
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
                                            enabled: false,
                                            maxLines: 1,
                                            controller: vehicleGroup,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Vehicle Group',
                                                hintText: "Vehicle Group",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.directions_bus,
                                                  color: Colors.pink,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height:
                                            tripSheetData.length > 0 ? 15 : 0),
                                    Visibility(
                                      visible: tripSheetData.length > 0,
                                      child: Container(
                                        height: 70,
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: DropdownButtonHideUnderline(
                                              child: Card(
                                                  elevation: 0.5,
                                                  color: Colors.white70,
                                                  child: Container(
                                                      padding:
                                                          EdgeInsets.all(17),
                                                      child: DropdownButton<
                                                          String>(
                                                        hint: Text(
                                                            "Trip Sheet Number"),
                                                        value: selectedTripSheetNumberInt !=
                                                                0
                                                            ? selectedTripSheetNumberInt
                                                                .toString()
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            selectedTripSheetNumberInt =
                                                                int.parse(
                                                                    newValue);
                                                            selectedTripSheetNumber =
                                                                newValue;
                                                          });
                                                        },
                                                        items: tripSheetData
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'tripSheetNumber'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'tripSheetID']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),

                                    SizedBox(
                                        height:
                                            showActiveTSNote == true ? 5 : 0),
                                    Visibility(
                                        visible: showActiveTSNote,
                                        child: Container(
                                          child: Align(
                                            alignment: Alignment.center,
                                            child: Text(
                                                "Active Tripsheet Found",
                                                style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15,
                                                )),
                                          ),
                                        )),

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
                                            enabled: !onlyReadDateAndTime,
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
                                    //  timeWidget(),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 10.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            enabled: !onlyReadDateAndTime,
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
                                                    FileUtility.getTime(
                                                        time.toString());
                                              });
                                              getActiveTripSheetAndBackDateData(
                                                  false);
                                              return DateTimeField.convert(
                                                  time);
                                            },
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
                                            enabled: readOnlyOpenOdometer,
                                            maxLines: 1,
                                            controller: openOdoMeter,
                                            keyboardType: TextInputType.number,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Open Odometer',
                                                hintText: "Open Odometer",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.departure_board,
                                                  color: Colors.brown,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height:
                                            grpfuelOdometer == true ? 5 : 0),
                                    Visibility(
                                        visible: grpfuelOdometer,
                                        child: Align(
                                          alignment: Alignment.centerRight,
                                          child: Container(
                                            child: Icon(Icons.stars,
                                                color: Colors.red, size: 12),
                                          ),
                                        )),
                                    Visibility(
                                        visible: grpfuelOdometer,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                maxLines: 1,
                                                controller: odoMeter,
                                                keyboardType:
                                                    TextInputType.number,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    labelText: 'Odometer',
                                                    hintText: "Odometer",
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.departure_board,
                                                      color: Colors.black,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),

                                    SizedBox(height: showRange == true ? 5 : 0),
                                    Visibility(
                                        visible: showRange,
                                        child: Container(
                                          child: Align(
                                            alignment: Alignment.center,
                                            child: Text(odometerRange,
                                                style: new TextStyle(
                                                  color: Colors.red,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15,
                                                )),
                                          ),
                                        )),

                                    SizedBox(
                                        height: allowGPSIntegration == true
                                            ? 15
                                            : 0),
                                    Visibility(
                                      visible: allowGPSIntegration,
                                      child: Container(
                                          child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            enabled: false,
                                            maxLines: 1,
                                            controller: gpsOdoMeter,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'GPS Odometer',
                                                hintText: "GPS Odometer",
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.departure_board,
                                                  color: Colors.brown,
                                                )),
                                          ),
                                        ),
                                      )),
                                    ),

                                    SizedBox(height: 5),
                                    CheckboxListTile(
                                      title: Text("Meter Not Working"),
                                      value: meterNotWorking,
                                      onChanged: (newValue) {
                                        if (!meterNotWorking) {
                                          setState(() {
                                            readOnlyOpenOdometer = true;
                                            meterNotWorking = true;
                                          });
                                        } else {
                                          setState(() {
                                            readOnlyOpenOdometer = false;
                                            meterNotWorking = false;
                                          });
                                        }
                                      },
                                      controlAffinity: ListTileControlAffinity
                                          .leading, //  <-- leading Checkbox
                                    ),

                                    // SizedBox(height: 5),
                                    // Align(
                                    //   alignment: Alignment.centerRight,
                                    //   child: Container(
                                    //     child: Icon(Icons.stars,
                                    //         color: Colors.red, size: 12),
                                    //   ),
                                    // ),
                                    // Container(
                                    //   child: Padding(
                                    //     padding: const EdgeInsets.only(
                                    //         top: 5.0, left: 10),
                                    //     child: Container(
                                    //       child: TextField(
                                    //         maxLines: 1,
                                    //         controller: fuelType,
                                    //         style:
                                    //             TextStyle(color: Colors.black),
                                    //         decoration: InputDecoration(
                                    //             labelText: 'Fuel Type',
                                    //             hintText: 'Fuel Type',
                                    //             hintStyle: TextStyle(
                                    //                 color: Colors.black),
                                    //             border: OutlineInputBorder(
                                    //                 borderRadius:
                                    //                     BorderRadius.circular(
                                    //                         5.0)),
                                    //             icon: Icon(
                                    //               Icons.local_gas_station,
                                    //               color: Colors.red,
                                    //             )),
                                    //       ),
                                    //     ),
                                    //   ),
                                    // ),

                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      height: 70,
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DropdownButtonHideUnderline(
                                            child: Card(
                                                elevation: 0.5,
                                                color: Colors.white,
                                                child: Container(
                                                    padding: EdgeInsets.all(17),
                                                    child:
                                                        DropdownButton<String>(
                                                      hint: Text("Fuel Type"),
                                                      value: fuelTypeValue != 0
                                                          ? fuelTypeValue
                                                              .toString()
                                                          : null,
                                                      isExpanded: true,
                                                      onChanged:
                                                          (String newValue) {
                                                        setState(() {
                                                          fuelTypeValue =
                                                              int.parse(
                                                                  newValue);
                                                        });
                                                      },
                                                      items: fuelTypeList
                                                          .map((item) {
                                                        return new DropdownMenuItem(
                                                          child: new Text(
                                                              item[
                                                                  'fuelTypeName'],
                                                              style: TextStyle(
                                                                  color: Colors
                                                                      .black,
                                                                  fontSize:
                                                                      20.0,
                                                                  fontFamily:
                                                                      "WorkSansBold")),
                                                          value:
                                                              item['fuelTypeId']
                                                                  .toString(),
                                                        );
                                                      }).toList(),
                                                    ))),
                                          ),
                                        ),
                                      ),
                                    ),

                                    // SizedBox(height: 5),
                                    // Align(
                                    //   alignment: Alignment.centerRight,
                                    //   child: Container(
                                    //     child: Icon(Icons.stars,
                                    //         color: Colors.red, size: 12),
                                    //   ),
                                    // ),
                                    // Container(
                                    //   child: Padding(
                                    //     padding:
                                    //         const EdgeInsets.only(left: 10),
                                    //     child: Column(
                                    //       children: <Widget>[
                                    //         SizedBox(
                                    //           height: 5.0,
                                    //         ),
                                    //         TypeAheadField(
                                    //           hideSuggestionsOnKeyboardHide:
                                    //               false,
                                    //           hideOnEmpty: true,
                                    //           textFieldConfiguration:
                                    //               TextFieldConfiguration(
                                    //             controller: fuelVendor,
                                    //             decoration: InputDecoration(
                                    //                 border: OutlineInputBorder(
                                    //                     borderRadius:
                                    //                         BorderRadius
                                    //                             .circular(5.0)),
                                    //                 icon: Icon(
                                    //                   Icons.directions_bus,
                                    //                   color: Colors.black,
                                    //                 ),
                                    //                 hintText: 'Fuel Vendor',
                                    //                 labelText: 'Fuel Vendor'),
                                    //           ),
                                    //           suggestionsCallback:
                                    //               (pattern) async {
                                    //             return await getFuelVendorSuggestions(
                                    //                 pattern);
                                    //           },
                                    //           itemBuilder:
                                    //               (context, suggestion) {
                                    //             return ListTile(
                                    //               leading: Icon(Icons.person),
                                    //               title: Text(
                                    //                   suggestion['vendorName']),
                                    //               // subtitle: Text('\$${suggestion['vehicleId']}'),
                                    //             );
                                    //           },
                                    //           onSuggestionSelected:
                                    //               (suggestion) {
                                    //             fuelVendor.text =
                                    //                 suggestion['vendorName'];
                                    //             setState(() {
                                    //               vendorId =
                                    //                   suggestion['vendorId'];
                                    //               creditVisible = true;
                                    //               isPaymentSelected[1] = true;
                                    //               isPaymentSelected[0] = false;
                                    //             });
                                    //           },
                                    //         ),
                                    //       ],
                                    //     ),
                                    //   ),
                                    // ),

                                    SizedBox(height: 5),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: CustomAutoComplete(
                                          suggestionList: fuelVendorData,
                                          controller: fuelVendor,
                                          hintLabel: 'Fuel Vendor',
                                          label: 'Fuel Vendor',
                                          dataKeyName: 'vendorName',
                                          iconData: Icons.person,
                                          onItemSelected: (suggestion) {
                                            fuelVendor.text =
                                                suggestion['vendorName'];
                                            setState(() {
                                              vendorId = suggestion['vendorId']
                                                  .toString();
                                              creditVisible = true;
                                              isPaymentSelected[1] = true;
                                              isPaymentSelected[0] = false;

                                              ownPetrolPump =
                                                  suggestion["ownPetrolPump"]
                                                      .toString();
                                            });
                                            if (suggestion["ownPetrolPump"] ==
                                                IS_OWNED_PETROL_PUMP) {
                                              setState(() {
                                                allowOwnPetrolPump = true;
                                              });
                                            } else {
                                              setState(() {
                                                allowOwnPetrolPump = false;
                                              });
                                            }

                                            if (allowOwnPetrolPump) {
                                              setState(() {
                                                ownPetrolPump =
                                                    suggestion["ownPetrolPump"]
                                                        .toString();
                                              });
                                              getFuelStockDetails(false);
                                            }
                                          },
                                          onChanged: (pattern) {
                                            //method for getting data from server
                                            getFuelVendorDetails(pattern);
                                          }),
                                    ),
                                    Visibility(
                                      visible: allowOwnPetrolPump,
                                      child: Text(
                                        fuelStockDetailsString,
                                        style: TextStyle(
                                            color: Colors.blue,
                                            fontSize: 14.0,
                                            fontWeight: FontWeight.bold,
                                            fontFamily: "WorkSansBold"),
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
                                          child: TextFormField(
                                            inputFormatters: [
                                              WhitelistingTextInputFormatter(
                                                  RegExp(r'^(\d+)?\.?\d{0,2}')),
                                            ],
                                            keyboardType:
                                                TextInputType.numberWithOptions(
                                                    decimal: true),
                                            onChanged: (String text) {
                                              calculateFuelPrice();
                                              if (fuelLiter.text != '' &&
                                                  fuelLiter.text.length > 0) {
                                                double val = double.parse(
                                                    fuelLiter.text);
                                                calculateFuelPrice();
                                                if (val > maximumFuelCapacity) {
                                                  FlutterAlert.onErrorAlert(
                                                      context,
                                                      "Fuel Liter should not be grater than  $maximumFuelCapacity",
                                                      "Error");
                                                  setState(() {
                                                    fuelLiter.text = '0';
                                                    fuelAmount.text = '0';
                                                  });
                                                  return false;
                                                }
                                              }
                                            },
                                            maxLines: 1,
                                            controller: fuelLiter,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Liter',
                                                hintText: 'Liter',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.invert_colors,
                                                  color: Colors.greenAccent,
                                                )),
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
                                            inputFormatters: [
                                              WhitelistingTextInputFormatter(
                                                  RegExp(r'^(\d+)?\.?\d{0,2}')),
                                            ],
                                            keyboardType:
                                                TextInputType.numberWithOptions(
                                                    decimal: true),
                                            onChanged: (String text) {
                                              calculateFuelPrice();
                                              if (fuelPrice.text != '' &&
                                                  fuelPrice.text.length > 0) {
                                                int val =
                                                    int.parse(fuelPrice.text);
                                                calculateFuelPrice();
                                                if (val >= maximumFuelPrice) {
                                                  FlutterAlert.onErrorAlert(
                                                      context,
                                                      "Fuel Price should not be grater than  $maximumFuelPrice",
                                                      "Error");
                                                  setState(() {
                                                    fuelPrice.text = '0';
                                                  });
                                                  return false;
                                                }
                                              }
                                            },
                                            enabled: !allowOwnPetrolPump,
                                            maxLines: 1,
                                            controller: fuelPrice,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Price/Unit',
                                                hintText: 'Price/Unit',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.attach_money,
                                                  color: Colors.blueGrey,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    renderFuelPrice(),
                                    SizedBox(height: 10),
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
                                                  Icons.monetization_on,
                                                  color: Colors.purpleAccent,
                                                ),
                                                Text(
                                                  "Pay Method ",
                                                  style: new TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                  ),
                                                ),
                                                Visibility(
                                                    visible: !creditVisible,
                                                    child: DialogButton(
                                                      width: 100,
                                                      child: Text(
                                                        "CASH",
                                                        style: TextStyle(
                                                            color: Colors.white,
                                                            fontSize: 25),
                                                      ),
                                                      gradient: LinearGradient(
                                                          colors: [
                                                            Colors.greenAccent,
                                                            Colors.blueAccent
                                                          ]),
                                                      onPressed: null,
                                                    )),
                                                Visibility(
                                                  visible: creditVisible,
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
                                                          'Cash',
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
                                                          'Credit',
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
                                    // SizedBox(height: 15),
                                    // Visibility(
                                    //   visible: validateDriver1,
                                    //   child: Align(
                                    //     alignment: Alignment.centerRight,
                                    //     child: Container(
                                    //       child: Icon(Icons.stars,
                                    //           color: Colors.red, size: 12),
                                    //     ),
                                    //   ),
                                    // ),
                                    // Container(
                                    //   child: Padding(
                                    //     padding:
                                    //         const EdgeInsets.only(left: 10),
                                    //     child: Column(
                                    //       children: <Widget>[
                                    //         TypeAheadField(
                                    //           hideSuggestionsOnKeyboardHide:
                                    //               false,
                                    //           hideOnEmpty: true,
                                    //           textFieldConfiguration:
                                    //               TextFieldConfiguration(
                                    //             controller: driverName,
                                    //             decoration: InputDecoration(
                                    //                 border: OutlineInputBorder(
                                    //                     borderRadius:
                                    //                         BorderRadius
                                    //                             .circular(5.0)),
                                    //                 icon: Icon(
                                    //                   Icons.person,
                                    //                   color: Colors.cyan,
                                    //                 ),
                                    //                 hintText: 'Driver Name',
                                    //                 labelText: 'Driver Name'),
                                    //           ),
                                    //           suggestionsCallback:
                                    //               (pattern) async {
                                    //             return await getDriverNameSuggestions(
                                    //                 pattern, 1);
                                    //           },
                                    //           itemBuilder:
                                    //               (context, suggestion) {
                                    //             return ListTile(
                                    //               leading: Icon(Icons.person),
                                    //               title: Text(
                                    //                   suggestion['driverName']),
                                    //               // subtitle: Text('\$${suggestion['vehicleId']}'),
                                    //             );
                                    //           },
                                    //           onSuggestionSelected:
                                    //               (suggestion) {
                                    //             driverName.text =
                                    //                 suggestion['driverName'];
                                    //             driverId =
                                    //                 suggestion['driver_id'];
                                    //           },
                                    //         ),
                                    //       ],
                                    //     ),
                                    //   ),
                                    // ),

                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: validateDriver1,
                                      child: Align(
                                        alignment: Alignment.centerRight,
                                        child: Container(
                                          child: Icon(Icons.stars,
                                              color: Colors.red, size: 12),
                                        ),
                                      ),
                                    ),
                                    Container(
                                      child: CustomAutoComplete(
                                          suggestionList: driverNameData,
                                          controller: driverName,
                                          hintLabel: 'Driver Name',
                                          label: 'Driver Name',
                                          dataKeyName: 'driverName',
                                          iconData: Icons.person,
                                          onItemSelected: (suggestion) {
                                            setState(() {
                                              driverName.text =
                                                  suggestion['driverName'];
                                              driverId =
                                                  suggestion['driver_id'];
                                            });
                                          },
                                          onChanged: (pattern) {
                                            //method for getting data from server
                                            getDriverNameDetails(pattern, 1);
                                          }),
                                    ),

                                    // SizedBox(
                                    //     height: showDriver2 == true ? 15 : 0),
                                    // Visibility(
                                    //   visible: showDriver2,
                                    //   child: Container(
                                    //     child: Padding(
                                    //       padding: const EdgeInsets.only(
                                    //           top: 5.0, left: 10),
                                    //       child: Column(
                                    //         children: <Widget>[
                                    //           SizedBox(
                                    //             height: 10.0,
                                    //           ),
                                    //           TypeAheadField(
                                    //             hideSuggestionsOnKeyboardHide:
                                    //                 false,
                                    //             hideOnEmpty: true,
                                    //             textFieldConfiguration:
                                    //                 TextFieldConfiguration(
                                    //               controller: driver2name,
                                    //               decoration: InputDecoration(
                                    //                   border:
                                    //                       OutlineInputBorder(
                                    //                           borderRadius:
                                    //                               BorderRadius
                                    //                                   .circular(
                                    //                                       5.0)),
                                    //                   icon: Icon(
                                    //                     Icons.person,
                                    //                     color:
                                    //                         Colors.greenAccent,
                                    //                   ),
                                    //                   hintText: 'Driver2 Name',
                                    //                   labelText:
                                    //                       'Driver2 Name'),
                                    //             ),
                                    //             suggestionsCallback:
                                    //                 (pattern) async {
                                    //               return await getDriverNameSuggestions(
                                    //                   pattern, 2);
                                    //             },
                                    //             itemBuilder:
                                    //                 (context, suggestion) {
                                    //               return ListTile(
                                    //                 leading: Icon(Icons.person),
                                    //                 title: Text(suggestion[
                                    //                     'driverName']),
                                    //                 // subtitle: Text('\$${suggestion['vehicleId']}'),
                                    //               );
                                    //             },
                                    //             onSuggestionSelected:
                                    //                 (suggestion) {
                                    //               driver2name.text =
                                    //                   suggestion['driverName'];
                                    //               driver2Id =
                                    //                   suggestion['driver_id'];
                                    //             },
                                    //           ),
                                    //         ],
                                    //       ),
                                    //     ),
                                    //   ),
                                    // ),

                                    SizedBox(
                                        height: showDriver2 == true ? 15 : 0),
                                    Visibility(
                                        visible: showDriver2,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: driverNameData,
                                              controller: driver2name,
                                              hintLabel: 'Driver 2 Name',
                                              label: 'Driver 2 Name',
                                              dataKeyName: 'driverName',
                                              iconData: Icons.person,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  driver2name.text =
                                                      suggestion['driverName'];
                                                  driver2Id =
                                                      suggestion['driver_id'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getDriverNameDetails(
                                                    pattern, 2);
                                              }),
                                        )),

                                    // SizedBox(
                                    //     height: showCleaner == true ? 15 : 0),
                                    // Visibility(
                                    //   visible: showCleaner,
                                    //   child: Container(
                                    //     child: Padding(
                                    //       padding: const EdgeInsets.only(
                                    //           top: 5.0, left: 10),
                                    //       child: Column(
                                    //         children: <Widget>[
                                    //           SizedBox(
                                    //             height: 10.0,
                                    //           ),
                                    //           TypeAheadField(
                                    //             hideSuggestionsOnKeyboardHide:
                                    //                 false,
                                    //             hideOnEmpty: true,
                                    //             textFieldConfiguration:
                                    //                 TextFieldConfiguration(
                                    //               controller: cleaner,
                                    //               decoration: InputDecoration(
                                    //                   border:
                                    //                       OutlineInputBorder(
                                    //                           borderRadius:
                                    //                               BorderRadius
                                    //                                   .circular(
                                    //                                       5.0)),
                                    //                   icon: Icon(
                                    //                     Icons.person,
                                    //                     color: Colors.amber,
                                    //                   ),
                                    //                   hintText: 'Cleaner Name',
                                    //                   labelText:
                                    //                       'Cleaner Name'),
                                    //             ),
                                    //             suggestionsCallback:
                                    //                 (pattern) async {
                                    //               return await getCleanerNameSuggestions(
                                    //                   pattern);
                                    //             },
                                    //             itemBuilder:
                                    //                 (context, suggestion) {
                                    //               return ListTile(
                                    //                 leading: Icon(Icons.person),
                                    //                 title: Text(suggestion[
                                    //                     'driverName']),
                                    //                 // subtitle: Text('\$${suggestion['vehicleId']}'),
                                    //               );
                                    //             },
                                    //             onSuggestionSelected:
                                    //                 (suggestion) {
                                    //               cleaner.text =
                                    //                   suggestion['driverName'];
                                    //               cleanerId =
                                    //                   suggestion['driver_id'];
                                    //             },
                                    //           ),
                                    //         ],
                                    //       ),
                                    //     ),
                                    //   ),
                                    // ),

                                    SizedBox(
                                        height: showCleaner == true ? 15 : 0),
                                    Visibility(
                                        visible: showCleaner,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: cleanerNameData,
                                              controller: cleaner,
                                              hintLabel: 'Cleaner Name',
                                              label: 'Cleaner Name',
                                              dataKeyName: 'driverName',
                                              iconData: Icons.person,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  cleaner.text =
                                                      suggestion['driverName'];
                                                  cleanerId =
                                                      suggestion['driver_id'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getCleanerNameDetails(pattern);
                                              }),
                                        )),

                                    // SizedBox(height: showRoot == true ? 15 : 0),
                                    // Visibility(
                                    //   visible: showRoot,
                                    //   child: Container(
                                    //     child: Padding(
                                    //       padding: const EdgeInsets.only(
                                    //           top: 5.0, left: 10),
                                    //       child: Column(
                                    //         children: <Widget>[
                                    //           SizedBox(
                                    //             height: 10.0,
                                    //           ),
                                    //           TypeAheadField(
                                    //             hideSuggestionsOnKeyboardHide:
                                    //                 false,
                                    //             hideOnEmpty: true,
                                    //             textFieldConfiguration:
                                    //                 TextFieldConfiguration(
                                    //               controller: routeService,
                                    //               decoration: InputDecoration(
                                    //                   border:
                                    //                       OutlineInputBorder(
                                    //                           borderRadius:
                                    //                               BorderRadius
                                    //                                   .circular(
                                    //                                       5.0)),
                                    //                   icon: Icon(
                                    //                     Icons.directions,
                                    //                     color: Colors.black,
                                    //                   ),
                                    //                   hintText: 'Route Service',
                                    //                   labelText:
                                    //                       'Route Service'),
                                    //             ),
                                    //             suggestionsCallback:
                                    //                 (pattern) async {
                                    //               return await getRouteServiceSuggestions(
                                    //                   pattern);
                                    //             },
                                    //             itemBuilder:
                                    //                 (context, suggestion) {
                                    //               return ListTile(
                                    //                 leading:
                                    //                     Icon(Icons.directions),
                                    //                 title: Text(suggestion[
                                    //                     'routeName']),
                                    //                 // subtitle: Text('\$${suggestion['vehicleId']}'),
                                    //               );
                                    //             },
                                    //             onSuggestionSelected:
                                    //                 (suggestion) {
                                    //               routeService.text =
                                    //                   suggestion['routeName'];
                                    //               routeServiceId =
                                    //                   suggestion['routeID'];
                                    //             },
                                    //           ),
                                    //         ],
                                    //       ),
                                    //     ),
                                    //   ),
                                    // ),

                                    SizedBox(height: showRoot == true ? 15 : 0),
                                    Visibility(
                                        visible: showRoot,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: routeServiceData,
                                              controller: routeService,
                                              hintLabel: 'Route Service',
                                              label: 'Route Service',
                                              dataKeyName: 'routeName',
                                              iconData: Icons.directions,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  routeService.text =
                                                      suggestion['routeName'];
                                                  routeServiceId =
                                                      suggestion['routeID'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getRouteServiceDetails(pattern);
                                              }),
                                        )),

                                    SizedBox(
                                        height: showTally == true ? 15 : 0),
                                    Visibility(
                                        visible: showTally,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: tallyServiceData,
                                              controller: tallyCompany,
                                              hintLabel: 'Tally Company',
                                              label: 'Tally Company',
                                              dataKeyName: 'tallyName',
                                              iconData: Icons.directions,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  tallyCompany.text =
                                                      suggestion['tallyName'];
                                                  tallyCompanyId =
                                                      suggestion['tallyID'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getTallyCompanyDetails(pattern);
                                              }),
                                        )),

                                    SizedBox(height: 15),
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
                                                  Icons.local_gas_station,
                                                  color: Colors.blueAccent,
                                                ),
                                                Text(
                                                  "Fuel Tank ",
                                                  style: new TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                  ),
                                                ),
                                                ToggleButtons(
                                                  borderColor: Colors.black,
                                                  fillColor: Colors.greenAccent,
                                                  borderWidth: 0,
                                                  selectedBorderColor:
                                                      Colors.black,
                                                  selectedColor: Colors.white,
                                                  borderRadius:
                                                      BorderRadius.circular(10),
                                                  children: <Widget>[
                                                    Padding(
                                                      padding:
                                                          const EdgeInsets.all(
                                                              8.0),
                                                      child: Text(
                                                        'Full',
                                                        style: TextStyle(
                                                          fontSize: 22,
                                                          color:
                                                              AppTheme.darkText,
                                                          fontWeight:
                                                              FontWeight.w700,
                                                        ),
                                                      ),
                                                    ),
                                                    Padding(
                                                      padding:
                                                          const EdgeInsets.all(
                                                              8.0),
                                                      child: Text(
                                                        'Partial',
                                                        style: TextStyle(
                                                          fontSize: 22,
                                                          color:
                                                              AppTheme.darkText,
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
                                                              isTankSelected
                                                                  .length;
                                                          i++) {
                                                        if (i == index) {
                                                          isTankSelected[i] =
                                                              true;
                                                        } else {
                                                          isTankSelected[i] =
                                                              false;
                                                        }
                                                      }
                                                    });
                                                  },
                                                  isSelected: isTankSelected,
                                                ),
                                              ])),
                                    ),
                                    SizedBox(height: 15),
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
                                                  Icons.file_upload,
                                                  color: Colors.purpleAccent,
                                                ),
                                                Text(
                                                  "Fuel Document ",
                                                  style: new TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                  ),
                                                ),
                                                DialogButton(
                                                  width: 130,
                                                  child: Text(
                                                    "Browse",
                                                    style: TextStyle(
                                                        color: Colors.white,
                                                        fontSize: 25),
                                                  ),
                                                  gradient: LinearGradient(
                                                      colors: [
                                                        Colors.blueGrey,
                                                        Colors.blueAccent
                                                      ]),
                                                  onPressed: openBottomSheet,
                                                )
                                              ])),
                                    ),
                                    Visibility(
                                      visible: myImage != null &&
                                          base64Image.length > 0,
                                      child: showRadiobtn(),
                                    ),
                                    showSelectedImage(),
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
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 130,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(5.0)),
                                            gradient: new LinearGradient(
                                                colors: [
                                                  Colors.cyanAccent,
                                                  Colors.blueAccent,
                                                ],
                                                begin: const FractionalOffset(
                                                    0.2, 0.2),
                                                end: const FractionalOffset(
                                                    1.0, 1.0),
                                                stops: [0.0, 1.0],
                                                tileMode: TileMode.clamp),
                                          ),
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
                                                    color: AppTheme.darkText,
                                                    fontWeight:
                                                        FontWeight.w700),
                                              ),
                                            ),
                                            onPressed: checkFinalValidation,
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

  Widget renderFuelPrice() {
    return Container(
        margin: EdgeInsets.only(left: 10),
        child: TextField(
          onChanged: (String text) {},
          enabled: false,
          keyboardType: TextInputType.number,
          maxLines: 1,
          controller: fuelAmount,
          style: TextStyle(color: Colors.black),
          decoration: InputDecoration(
              labelText: 'Total Amount : ',
              hintText: 'Total Amount : ',
              hintStyle: TextStyle(color: Colors.black),
              border:
                  OutlineInputBorder(borderRadius: BorderRadius.circular(5.0)),
              icon: Icon(
                Icons.attach_money,
                color: Colors.blueGrey,
              )),
        ));
  }

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
  }

  Future<List> getFuelVendorSuggestions(String query) async {
    getFuelVendorDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(fuelVendorData.length, (index) {
      return fuelVendorData[index];
    });
  }

  Future<List> getDriverNameSuggestions(String query, int checkDriver) async {
    getDriverNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(driverNameData.length, (index) {
      return driverNameData[index];
    });
  }

  Future<List> getCleanerNameSuggestions(String query) async {
    getCleanerNameDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(cleanerNameData.length, (index) {
      return cleanerNameData[index];
    });
  }

  Future<List> getRouteServiceSuggestions(String query) async {
    getRouteServiceDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(routeServiceData.length, (index) {
      return routeServiceData[index];
    });
  }

  getFuelStockDetails(bool saveData) async {
    var fstock;
    var price;
    String str = "";
    String avgrate = "";
    var data = {
      'companyId': companyId,
      'petrolPumpId': vendorId.toString(),
      'getStockFromInventoryConfig': getStockFromInventory.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.GET_FUEL_STOCK_DETAILS, data, URI.LIVE_URI, context);
    if (response != null && response["stockDetails"] != null) {
      if (getStockFromInventory) {
        fuelInvoiceId = response["stockDetails"]["fuelInvoiceId"].toString();
        fstock = response["stockDetails"]["balanceStock"];
        price = response["stockDetails"]["rate"];
        fstock = Utility.getLimitedData(fstock);
        price = Utility.getLimitedData(price);
        str = "Fuel Stock : $fstock ltr" + "& Price :$price";
        setState(() {
          fuelStockDetailsString = str;
          fuelPrice.text = price;
        });
      } else {
        fstock = response["stockDetails"]["stockQuantity"];
        price = response["stockDetails"]["avgFuelRate"];
        fstock = Utility.getLimitedData(fstock);
        price = Utility.getLimitedData(price);
        str = "Fuel Stock : $fstock ltr" + "& Price :$price";
        avgrate = response["stockDetails"]["avgFuelRate"].toString();
        setState(() {
          fuelStockDetailsString = str;
          fuelPrice.text = avgrate;
        });
      }
      if (saveData) {
        _handleSubmitted();
      }
    } else {
      setState(() {
        fuelStockDetailsString = "No stock available at this petrol pump";
        fuelPrice.text = "0";
      });
      FlutterAlert.onInfoAlert(
          context,
          "No stock available at this petrol pump please add stock before continue !",
          "Info");
      return false;
    }
  }

  getFuelVendorDetails(String str) async {
    fuelVendorList = new Map();
    setState(() {
      creditVisible = false;
      fuelVendorData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.FUEL_VENDOR_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["vendorList"] != null) {
          setState(() {
            fuelVendorData = response["vendorList"];
          });
        } else {
          setState(() {
            fuelVendorData = [];
          });
        }
      }
    } else {
      setState(() {
        fuelVendorData = [];
      });
    }
  }

  getDriverNameDetails(String str, int checkDriver) async {
    driverNameList = new Map();
    if (checkDriver == 1) {
      driverId = '';
    } else {
      driver2Id = '';
    }
    setState(() {
      driverNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.DRIVER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["driverList"] != null) {
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

  getCleanerNameDetails(String str) async {
    cleanerNameList = new Map();
    cleanerId = '';
    setState(() {
      cleanerNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.CLEANER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["cleanerList"] != null) {
          for (int i = 0; i < response["cleanerList"].length; i++) {
            var obj = {
              "driver_id": response['cleanerList'][i]['driver_id'].toString(),
              "driverName": response['cleanerList'][i]['driver_empnumber']
                      .toString() +
                  '-' +
                  response['cleanerList'][i]['driver_firstname'].toString() +
                  ' ' +
                  response['cleanerList'][i]['driver_Lastname'].toString()
            };
            cleanerNameList[obj['driver_id']] = obj;
            setState(() {
              cleanerNameData = cleanerNameList.values.toList();
            });
          }
        } else {
          setState(() {
            cleanerNameData = [];
          });
        }
      }
    } else {
      setState(() {
        cleanerNameData = [];
      });
    }
  }

  getRouteServiceDetails(String str) async {
    routeServiceList = new Map();
    routeServiceId = '';
    setState(() {
      routeServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TRIP_ROUTE_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["TripRouteList"] != null) {
          for (int i = 0; i < response["TripRouteList"].length; i++) {
            var obj = {
              "routeID": response['TripRouteList'][i]['routeID'].toString(),
              "routeName": response['TripRouteList'][i]['routeName'].toString()
            };
            routeServiceList[obj['routeID']] = obj;
            setState(() {
              routeServiceData = routeServiceList.values.toList();
            });
          }
        } else {
          setState(() {
            routeServiceData = [];
          });
        }
      }
    } else {
      setState(() {
        routeServiceData = [];
      });
    }
  }

  getTallyCompanyDetails(String str) async {
    tallyServiceList = new Map();
    tallyCompanyId = '';
    setState(() {
      tallyServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TALLY_COMPANY_LIST, data, URI.LIVE_URI);

      if (response != null) {
        if (response["TallyCmpnyList"] != null) {
          for (int i = 0; i < response["TallyCmpnyList"].length; i++) {
            var obj = {
              "tallyID":
                  response['TallyCmpnyList'][i]['tallyCompanyId'].toString(),
              "tallyName":
                  response['TallyCmpnyList'][i]['companyName'].toString()
            };
            tallyServiceList[obj['tallyID']] = obj;
            setState(() {
              tallyServiceData = tallyServiceList.values.toList();
            });
          }
        } else {
          setState(() {
            tallyServiceData = [];
          });
        }
      }
    } else {
      setState(() {
        tallyServiceData = [];
      });
    }
  }

  getVehicleDetails(String str) async {
    vehicleList = new Map();
    vehicleGroup.text = '';
    openOdoMeter.text = '';
    odoMeter.text = '';
    fuelType.text = '';
    vehicleId = '';
    fuelMeter = '';
    vehicleGroupId = '';
    expectedOdoMeter = '';
    gpsOdoMeter.text = '';
    if (setFuelEntryDefaultDateAndTime != null &&
        !setFuelEntryDefaultDateAndTime) {
      fuelEntryTime.text = '';
    }

    setState(() {
      vehicleData = [];
      tripSheetData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_VEHICLE_DETAILS, data, URI.LIVE_URI);
      if (response != null) {
        if (response["vehicleList"] != null) {
          for (int i = 0; i < response["vehicleList"].length; i++) {
            var obj = {
              "vehicleId": response['vehicleList'][i]['vid'].toString(),
              "vehicleName":
                  response['vehicleList'][i]['vehicle_registration'].toString()
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
          //FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          vehicleData = [];
        });
        //FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        vehicleData = [];
      });
    }
  }

  Future getOdoMeterDetails(String id, BuildContext context) async {
    vehicleId = id;
    setState(() {
      tripSheetData = [];
      meterNotWorking = false;
      showNote = false;
      showRange = false;
      showActiveTSNote = false;
    });
    var data = {'companyId': companyId, 'userId': userId, 'vehicleId': id};
    var response = await ApiCall.getDataFromApi(
        URI.GET_ODO_METER_DETAILS, data, URI.LIVE_URI, context);

    if (response != null) {
      if (response['oddMeterDetails'] != null) {
        var oddMeterDetails = response['oddMeterDetails'];
        var vehicleOdometer = oddMeterDetails['vehicle_Odometer'].toString();
        vehicleGroup.text = oddMeterDetails['vehicle_Group'];
        openOdoMeter.text = vehicleOdometer;
        odoMeter.text = vehicleOdometer;
        actualOdameterReading = oddMeterDetails['vehicle_Odometer'];
        fuelType.text = oddMeterDetails['vehicle_Fuel'].toString();

        vehicleGroupId = oddMeterDetails['vehicleGroupId'].toString();
        expectedOdoMeter =
            oddMeterDetails['vehicle_ExpectedOdameter'].toString();

        if (oddMeterDetails['gpsOdameter'] != null &&
            oddMeterDetails['gpsOdameter'] > 0) {
          gpsOdoMeter.text = oddMeterDetails['gpsOdameter'].toString();
        }

        if (oddMeterDetails['lastFuelOdometer'] != null &&
            oddMeterDetails['lastFuelOdometer'] > 0) {
          openOdoMeter.text = oddMeterDetails['lastFuelOdometer'].toString();
        } else {
          openOdoMeter.text = vehicleOdometer;
        }

        var kmplFrom = oddMeterDetails['vehicle_ExpectedMileage'];
        var kmplTo = oddMeterDetails['vehicle_ExpectedMileage_to'];

        if (kmplFrom == 0 && kmplTo == 0) {
          vehicleNumber.text = oddMeterDetails['vehicle_registration'];
          addKMPL(context);
          setState(() {
            validateKMPL = true;
          });
        }

        List<String> fuelTypeName = oddMeterDetails['vehicle_Fuel'].split(",");
        List<String> fuelTypeId = oddMeterDetails['vehicleFuelId'].split(",");

        setState(() {
          fuelTypeList.clear();
        });

        int initailFtypeId = 0;

        for (int i = 0; i < fuelTypeId.length; i++) {
          int latestFtypeId = int.parse(fuelTypeId[i]);

          if (initailFtypeId != latestFtypeId) {
            var fuelTypeData = {
              "fuelTypeId": fuelTypeId[i].toString(),
              "fuelTypeName": fuelTypeName[i].toString(),
            };

            fuelTypeList.add(fuelTypeData);
          }

          initailFtypeId = latestFtypeId;
        }
        setState(() {
          fuelTypeList;
          fuelTypeValue = 1;
        });
      }

      if (response['vendorName'] != null) {
        setState(() {
          fuelVendor.text = response['vendorName'];
          creditVisible = true;

          if (selectAutoCredit) {
            isPaymentSelected[1] = true;
            isPaymentSelected[0] = false;
          }
        });
      }
    }
    getTripSheetNumber(vehicleId);
  }

  check(response) {
    Map resp = response;
    if (resp != null &&
        resp.containsKey("isAlreadyExist") &&
        !response["isAlreadyExist"]) {
      return true;
    } else {
      return false;
    }
  }

  getActiveTripSheetAndBackDateData(bool isCalledOnLoad) async {
    if (vehicleId != null && vehicleId.isNotEmpty && int.parse(vehicleId) > 0) {
      var activeTSAndBackDateData = {
        'fromMob': 'true',
        'companyId': companyId,
        'userId': userId,
        'vid': vehicleId,
        'fuelDate': fuelEntryDate.text,
        'fuelTime': fuelEntryTime.text.trim(),
        'vehicle_ExpectedOdameter': expectedOdoMeter,
        'bindMinMaxOdometerOnTripSheet':
            bindMinMaxOdometerOnTripSheet.toString()
      };
      // print("activeTSAndBackDateData =$activeTSAndBackDateData");
      var response = await ApiCall.getDataFromApi(
          URI.GET_ACTIVE_TS_AND_BACKDATE,
          activeTSAndBackDateData,
          URI.LIVE_URI,
          context);
      if (check(response)) {
        blockSaveFuelEntry = false;
        if (allowManualOdometerEntry == false) {
          if (response['nextFuelDeatils'] != null) {
            if (response['previousFuelDeatils'] != null) {
              setState(() {
                creatingBackDateFuelEntry = true;
                previousFuelEntryCapacity =
                    response['previousFuelDeatils']['fuel_liters'];
                nextFuelIdOfBackDate = response['nextFuelDeatils']['fuel_id'];

                if (!isCalledOnLoad) {
                  openOdoMeter.text = response['previousFuelDeatils']
                          ['lastFuelOdometer']
                      .toString();
                  odoMeter.text =
                      response['nextFuelDeatils']['fuelMeter'].toString();
                }

                nextFuelMeterOfBackDate =
                    response['nextFuelDeatils']['fuelMeter'];
                if (!isCalledOnLoad) {
                  backdateBetweenPreviousAndNext =
                      'Note : You are Creating Back Date Fuel Entry. ' +
                          'Previous Fuel Entry Is FT - ${response['previousFuelDeatils']['fuel_Number']} ' +
                          'And Next Fuel Entry Is FT - ${response['nextFuelDeatils']['fuel_Number']}. ' +
                          'Odameter Deatils Of Next Fuel Entry - ${response['nextFuelDeatils']['fuel_Number']} ' +
                          'Will Be Updated Automatically With Respect To This Entry.';
                  showNote = true;
                }

                int minVal =
                    response['previousFuelDeatils']['lastFuelOdometer'] + 1;
                int maxVal = response['nextFuelDeatils']['fuelMeter'] - 1;
                odometerRange =
                    'Odometer Range : ${minVal} - ${maxVal} with respect ' +
                        'to fuel entries FT - ${response['previousFuelDeatils']['fuel_Number']} ' +
                        'and FT - ${response['nextFuelDeatils']['fuel_Number']} ';
                showRange = true;

                minOdometerVal = minVal;
                maxOdometerVal = maxVal;
              });
            } else {
              setState(() {
                readOnlyOpenOdometer = true;
                creatingBackDateFuelEntry = true;
                firstFuelEntry = true;
                nextFuelIdOfBackDate = response['nextFuelDeatils']['fuel_id'];

                if (!isCalledOnLoad) {
                  openOdoMeter.text = response['nextFuelDeatils']
                          ['lastFuelOdometer']
                      .toString();
                  odoMeter.text =
                      response['nextFuelDeatils']['fuelMeter'].toString();
                }

                nextFuelMeterOfBackDate =
                    response['nextFuelDeatils']['fuelMeter'];
                if (!isCalledOnLoad) {
                  backdateBetweenPreviousAndNext =
                      'Note : You are Creating Back Date Fuel Entry. ' +
                          'There is no Previous Entry ' +
                          'And Next Fuel Entry Is FT - ${response['nextFuelDeatils']['fuel_Number']}. ' +
                          'Odameter Deatils Of Next Fuel Entry - ${response['nextFuelDeatils']['fuel_Number']} ' +
                          'Will Be Updated Automatically With Respect To This Entry.';
                  showNote = true;
                }

                odometerRange = 'Odometer Range : Please Add Odometer below ' +
                    '${response['nextFuelDeatils']['fuelMeter']} Range with respect ' +
                    'to fuel entry FT - ${response['nextFuelDeatils']['fuel_Number']}. ';
                showRange = true;

                if (response['nextFuelDeatils']['lastFuelOdometer'] -
                        response['nextFuelDeatils']
                            ['vehicle_ExpectedOdameter'] >
                    0) {
                  minOdometerVal = response['nextFuelDeatils']
                          ['lastFuelOdometer'] -
                      response['nextFuelDeatils']['vehicle_ExpectedOdameter'];
                } else {
                  minOdometerVal = 0;
                }

                int maxVal = response['nextFuelDeatils']['fuelMeter'] - 1;
                maxOdometerVal = maxVal;
              });
            }
          } else {
            setState(() {
              if (response['previousFuelDeatils'] != null) {
                if (!isCalledOnLoad) {
                  openOdoMeter.text = response['previousFuelDeatils']
                          ['lastFuelOdometer']
                      .toString();
                }

                odometerRange = 'Odometer Range : Please Enter Odometer Above ' +
                    '${response['previousFuelDeatils']['lastFuelOdometer']} Range with respect ' +
                    'to fuel entry FT - ${response['previousFuelDeatils']['fuel_Number']}. ';
                showRange = true;

                int minVal =
                    response['previousFuelDeatils']['lastFuelOdometer'] + 1;
                int maxVal = response['previousFuelDeatils']
                        ['lastFuelOdometer'] +
                    response['previousFuelDeatils']['vehicle_ExpectedOdameter'];

                minOdometerVal = minVal;
                maxOdometerVal = maxVal;
              }

              if (actualOdameterReading > 0) {
                if (!isCalledOnLoad) {
                  odoMeter.text = actualOdameterReading.toString();
                  // This will reset back to current odometer when user changes time from backdate to non backdate
                }
              }

              nextFuelIdOfBackDate = 0;
              nextFuelMeterOfBackDate = 0;
            });
          }
        }

        if (response['tripActive'] != null) {
          tripSheetList = new Map();
          setState(() {
            tripSheetData = [];
          });
          var obj = {
            "tripSheetID": response['tripActive']['tripSheetID'].toString(),
            "tripSheetNumber": "TS - " +
                response['tripActive']['tripSheetNumber'].toString() +
                " : " +
                response['tripActive']['tripOpenDate'].toString() +
                " - " +
                response['tripActive']['closetripDate'].toString(),
          };
          tripSheetList[obj['tripSheetID']] = obj;
          var DID = checkAndSet(
              response["tripActive"]["tripFristDriverID"].toString());
          var DText =
              checkAndSet(response["tripActive"]["tripFristDriverName"]);
          var DFather =
              checkAndSet(response["tripActive"]["tripFristDriverFatherName"]);
          var DLast =
              checkAndSet(response["tripActive"]["tripFristDriverLastName"]);
          var RID = checkAndSet(response["tripActive"]["routeID"].toString());
          var RText =
              checkAndSet(response["tripActive"]["routeName"].toString());
          String finalDriverName = DText + " " + DFather + " " + DLast;
          if (showRoot) {
            setState(() {
              routeServiceId = RID;
              routeService.text = RText;
            });
          }

          setState(() {
            driverId = DID.toString();
            driverName.text = finalDriverName;
            showActiveTSNote = true;
            tripSheetData = tripSheetList.values.toList();
            selectedTripSheetNumberInt = response['tripActive']['tripSheetID'];
            selectedTripSheetNumber = selectedTripSheetNumberInt.toString();
          });
        }
      } else {
        if (response != null &&
            response["isAlreadyExist"] != null &&
            response["isAlreadyExist"]) {
          if (setFuelEntryDefaultDateAndTime != null &&
              setFuelEntryDefaultDateAndTime) {
            setDefaultDateAndTime();
          }
          blockSaveFuelEntry = true;
          FlutterAlert.onErrorAlert(
              context, "Please Select Different Time !", "Error");
        }
      }
    }
  }

  String checkAndSet(String data) {
    if (data != null && data.length > 0) {
      return data;
    } else {
      return "";
    }
  }

  void addKMPL(BuildContext context) {
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
      title:
          "Vehicle Km/L Details Haven't Been Added. Please Update Km/L Details to Continue With Fuel Entries.",
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  enabled: false,
                  maxLines: 1,
                  controller: vehicleNumber,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Vehicle Number',
                      hintText: "Vehicle Number",
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.directions_bus,
                        color: Colors.pink,
                      )),
                ),
              ),
            ),
          ),
          SizedBox(height: 5),
          Align(
            alignment: Alignment.centerRight,
            child: Container(
              child: Icon(Icons.stars, color: Colors.red, size: 12),
            ),
          ),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  keyboardType: TextInputType.number,
                  maxLines: 1,
                  controller: mileageFrom,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Expected Mileage From',
                      hintText: 'Expected Mileage From',
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.invert_colors,
                        color: Colors.greenAccent,
                      )),
                ),
              ),
            ),
          ),
          SizedBox(height: 5),
          Align(
            alignment: Alignment.centerRight,
            child: Container(
              child: Icon(Icons.stars, color: Colors.red, size: 12),
            ),
          ),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  keyboardType: TextInputType.number,
                  maxLines: 1,
                  controller: mileageTo,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Expected Mileage To',
                      hintText: 'Expected Mileage To',
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.invert_colors,
                        color: Colors.purpleAccent,
                      )),
                ),
              ),
            ),
          ),
          Center(
            child: Container(
                height: 50,
                width: _width - 130,
                margin: EdgeInsets.only(top: 20.0, left: 10),
                decoration: new BoxDecoration(
                  color: Colors.pinkAccent,
                  borderRadius: BorderRadius.all(Radius.circular(5.0)),
                ),
                child: MaterialButton(
                  highlightColor: Colors.transparent,
                  splashColor: Colors.purpleAccent,
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 10.0, horizontal: 42.0),
                    child: Text(
                      "Add Km/L",
                      style: TextStyle(
                          fontSize: 22,
                          color: AppTheme.white,
                          fontWeight: FontWeight.w700),
                    ),
                  ),
                  onPressed: saveKMPLDetails,
                )),
          ),
        ],
      ),
    ).show();
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
                          builder: (context) => SearchFuelEntryByVehicle()),
                    )
                  },
                  leading: new Icon(
                    Icons.invert_colors,
                    color: Colors.lightBlueAccent,
                  ),
                  title: Text(
                    "Search Fuel Entry By Vehicle",
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
}

// validateMaxOdameter() {
//   var expectedMaxOdameter =
//       num.parse(openOdoMeter.text) + num.parse(expectedOdoMeter);
//   var fuelMeter = num.parse(odoMeter.text);
//   var currentTime =
//       (DateFormat("dd-MM-yyyy").format(DateTime.now())).toString();
//   int checkDate = currentTime.compareTo(fuelEntryDate.toString());

//   if (validateMinOdometerInFuel == true) {
//     if (checkDate == 0) {
//       if (openOdoMeter.text != null && num.parse(openOdoMeter.text) > 0) {
//         if (fuelMeter > 0 && fuelMeter < num.parse(openOdoMeter.text)) {
//           FlutterAlert.onErrorAlert(
//               context,
//               "Trip Odometer Should Not Be Less Than " + openOdoMeter.text,
//               "Error");
//           return false;
//         }
//       }
//     }
//   }

//   if (validateOdometerInFuel == false) {
//     return true;
//   }

//   if (checkDate == 0) {
//     if (openOdoMeter.text != null && num.parse(openOdoMeter.text) > 0) {
//       if (fuelMeter > 0 && fuelMeter < num.parse(openOdoMeter.text)) {
//         if (fuelMeter > expectedMaxOdameter) {
//           FlutterAlert.onErrorAlert(
//               context,
//               "You can not enter Odameter greter than " +
//                   expectedMaxOdameter.toString(),
//               "Error");
//           return false;
//         }
//       }
//     }
//   }
//   return true;
// }
