import 'dart:convert';
import 'dart:io';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/Issues/showIssue.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:location/location.dart';

import '../fleetopuriconstant.dart';
import 'editIssue.dart';

class ImageIssue extends KFDrawerContent {

  final String issueId;
  bool navigateValue;
  ImageIssue({Key key, this.issueId, this.navigateValue});

  @override
  _ImageIssueState createState() => _ImageIssueState();
}

class _ImageIssueState extends State<ImageIssue> with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String issId;

  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;
  bool showImage = false;

  TextEditingController documentName = new TextEditingController();

  Widget showselectedImage(){
    return Container(
                   child: Stack(
                     children: <Widget>[
                       Container(
                         child: CircleAvatar(
                           radius: 120,
                           child: ClipOval(
                               child: new SizedBox(
                                 width: 230.0,
                                 height: 230.0,
                                 child: (base64Image!= null && base64Image.length> 0)?Image.memory(base64Decode(base64Image),
                                     fit: BoxFit.fill
                                 ):
                                 Image.asset("assets/img/signUp.png"))
                           ),
                         ),
                       ),
                     ],
                   ),
                 );
  }

  openBottomSheet() {
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

  Future getImageGallery() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.gallery, maxHeight: 600, maxWidth: 600);
    if (imageFile == null) {
      return;
    }
    setState(() {
      myImage = imageFile;
      showImage = true;
    });
    imageName = myImage.path.split("/").last;
    imageExt = imageName.split(".").last;

    testCompressAndGetFile(myImage, myImage.path);
    base64Image = base64Encode(myImage.readAsBytesSync());
    setState(() {
      uploadedFile = base64Image;
    });
  }

  Future getImageFromCamera() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.camera, maxHeight: 500, maxWidth: 500);
    if (imageFile == null) {
      return;
    }
    imageName = imageFile.path.split("/").last;
    imageExt = imageName.split(".").last;

    setState(() {
      myImage = imageFile;
      showImage = true;
    });
    testCompressAndGetFile(myImage, myImage.path);
    base64Image = base64Encode(myImage.readAsBytesSync());
    setState(() {
      uploadedFile = base64Image;
    });
  }

  void testCompressAndGetFile(File file, String targetPath) async {
    var result = await FlutterImageCompress.compressAndGetFile(
      file.absolute.path,
      targetPath,
      quality: 100,
      rotate: 360,
    );
    Navigator.pop(context, 'Cancel');
    getBase64Image(result);
  }

  Future<String> getBase64Image(File file) async {
    if (file != null) {
      base64Image = base64Encode(file.readAsBytesSync());
      setState(() {
        imageData = base64Image;
      });
    }
  }


  @override
  void initState() {
    getSessionData(widget.issueId, widget.navigateValue);
    animationController = AnimationController(duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData(String idIssue, bool navigateValue) async {

    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

      setState(() {
      issId = idIssue;
      });
      
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

     var issueDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'issuesId':issId,
        'documentName': documentName.text,
        'base64String': imageData,
        'imageName': imageName,
        'imageExt': imageExt,
      };

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_ISSUE_IMAGE, issueDetails, URI.LIVE_URI, context);

      if (data != null) {
             if (data['save'] == true) {
              redirectToDisplay(issId);
            } else {
              FlutterAlert.onInfoAlert(
                  context, "Image Not Attached, Please contact on Support !", "Info");
            }
        } else {
          FlutterAlert.onErrorAlert(
              context, "Some Problem Occur,Please contact on Support !", "Error");
        }    
  }
  

  Future<bool> redirectToDisplay(issueId) async => Alert(
        context: context,
        type: AlertType.success,
        title: "Success",
        desc: "Image Successfully Attached !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => issueDisplayData(issueId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  issueDisplayData(String issueId, context) async {
    Navigator.pop(context);
    Navigator.push(context, MaterialPageRoute(builder: (context) => ShowIssue(issueId: issueId)),);
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    
    return Scaffold(

      appBar: AppBar(
      title: const Text('Add Image'),
      backgroundColor: Colors.pink,
      ),

     // backgroundColor: AppTheme.white,
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

                                    SizedBox(height: 10),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Add Image Details ",
                                              style: new TextStyle(
                                                color: AppTheme.darkText,
                                                fontWeight: FontWeight.w700,
                                                fontSize: 20
                                              )
                                          ),
                                          
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.pink,
                                      margin: const EdgeInsets.only(left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            maxLines: 3,
                                            controller: documentName,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Document Name',
                                                hintText: 'Document Name',
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
                                                  Icons.file_upload,
                                                  color: Colors.purpleAccent,
                                                ),
                                                Text(
                                                  "Issue Document ",
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
                                                  color: Colors.purple,
                                                  onPressed: openBottomSheet,
                                                )
                                              ])),
                                    ),
                                    
                                    SizedBox(height: 15),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(5.0)),
                                            color: Colors.pink
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
                                                "Add Image Issue",
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
  

}
