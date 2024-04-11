import 'dart:convert';
import 'dart:io';

import 'package:fleetop/AutoCompleteUtilty/AutoCompleteUtility.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/SearchScreen/SearchScreen.dart';
import 'package:fleetop/Utility/MyCard.dart';
import 'package:fleetop/Utility/Utility.dart';
import 'package:fleetop/Utility/payment_methods.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:image_picker/image_picker.dart';
import 'package:nice_button/NiceButton.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../CustomAutoComplete.dart';
import '../apicall.dart';
import '../appTheme.dart';
import '../fleetopuriconstant.dart';
import 'package:button3d/button3d.dart';

class WorkOrderStepProcess extends StatefulWidget {
  final Map workProcessData;

  WorkOrderStepProcess({
    Key key,
    this.workProcessData,
  }) : super(key: key);

  @override
  _WorkOrderStepProcessState createState() => _WorkOrderStepProcessState();
}

class _WorkOrderStepProcessState extends State<WorkOrderStepProcess>
    with SingleTickerProviderStateMixin {
  TabController _tabController;
  double _height;
  double _width;
  Size size;
  String companyId;
  String userId;
  String email;
  String workOrderId = "";
  Map woData = new Map();
  Map config = new Map();
  List taskList = new List();
  final _scaffoldKey = new GlobalKey<ScaffoldState>();
  List partNameData = new List();
  List techicianNameData = new List();
  List<TextEditingController> partNameControllerList = new List();
  List<TextEditingController> tecNameControllerList = new List();
  List<TextEditingController> partQuantity = new List();
  List<TextEditingController> workHourControllerList = new List();
  List<TextEditingController> labCostControllerList = new List();
  List<TextEditingController> discountControList = new List();
  List<TextEditingController> gstControllerList = new List();
  List<TextEditingController> totalControllerList = new List();
  List<TextEditingController> jobTypeControllerList =
      new List<TextEditingController>();
  List<TextEditingController> subjobTypeControllerList =
      new List<TextEditingController>();
  List<TextEditingController> remarkControllerList =
      new List<TextEditingController>();
  Map<int, bool> partNameVis = new Map();
  Map<int, bool> tecNameVis = new Map();
  Map<int, bool> isPartReceived = new Map();
  List jobTypeList = new List();
  List jobSubTypeList = new List();
  int jobTypeId = 0;
  int subjobId = 0;
  Map<int, int> jobTypeIdMap = new Map();
  Map<int, int> subjobIdMap = new Map();
  bool invoiceWisePartListConfig = false;

  Map<int, int> invetoryId = new Map();
  Map<int, double> taskWisePartQuantity = new Map();
  Map<int, int> taskPartId = new Map();
  bool showSubLocation = false;
  String subLocationId = "";
  Map<int, int> techId = new Map();
  int vehicleId = 0;
  int workOrderStatusId = 0;
  List partList = new List();
  List labourDataList = new List();
  int currTaskId = 0;
  int currPartId = 0;
  int currLabId = 0;
  String base64String;
  String imageName;
  String imageExt;

  bool showServRemindWhileCreatingWO = false;
  bool autoLabourAdd = false;
  bool showDriverNumberCol = false;
  bool showOutWorkStationCol = false;
  bool showRouteCol = false;
  List<String> permission = new List();
  bool showEditJobTypeRemark = false;
  Map<int, String> jobTypeRemark = new Map<int, String>();
  bool reOpenWO = false;
  @override
  void initState() {
    jobTypeControllerList.add(new TextEditingController());
    subjobTypeControllerList.add(new TextEditingController());
    remarkControllerList.add(new TextEditingController());
    _tabController = new TabController(length: 2, vsync: this);
    super.initState();
    getSessionData();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    permission = prefs.getStringList("permission");
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    if (widget.workProcessData != null && widget.workProcessData.isNotEmpty) {
      setState(() {
        woData = widget.workProcessData["WorkOrder"];
        config = widget.workProcessData["configuration"];
        taskList = widget.workProcessData["WorkOrderTask"];
        invoiceWisePartListConfig = config["invoiceWisePartList"];
        showSubLocation = config["showSubLocation"];
        workOrderId = woData["workorders_id"].toString();
        vehicleId = woData["vehicle_vid"];
        workOrderStatusId = widget.workProcessData["workOrderStatus"];
        subLocationId = woData["subLocationId"].toString();
        showServRemindWhileCreatingWO = config["showServRemindWhileCreatingWO"];
        autoLabourAdd = config["autoLabourAdd"];
        showDriverNumberCol = config["showDriverNumberCol"];
        showOutWorkStationCol = config["showOutWorkStationCol"];
        showRouteCol = config["showRouteCol"];
        showEditJobTypeRemark = widget.workProcessData["showEditJobTypeRemark"];
        reOpenWO = widget.workProcessData["reOpenWO"];
      });
      if (widget.workProcessData.containsKey("WorkOrderTaskPart")) {
        if (widget.workProcessData["WorkOrderTaskPart"] != null &&
            widget.workProcessData["WorkOrderTaskPart"].length > 0) {
          setState(() {
            partList = widget.workProcessData["WorkOrderTaskPart"];
          });
        }
      }
      if (widget.workProcessData.containsKey("WorkOrderTaskLabor")) {
        if (widget.workProcessData["WorkOrderTaskLabor"] != null &&
            widget.workProcessData["WorkOrderTaskLabor"].length > 0) {
          setState(() {
            labourDataList = widget.workProcessData["WorkOrderTaskLabor"];
          });
        }
      }

      if (taskList != null && taskList.isNotEmpty) {
        for (int i = 0; i < taskList.length; i++) {
          var data = taskList[i];
          partNameControllerList.add(new TextEditingController());
          tecNameControllerList.add(new TextEditingController());
          partQuantity.add(new TextEditingController());
          workHourControllerList.add(new TextEditingController());
          labCostControllerList.add(new TextEditingController());
          discountControList.add(new TextEditingController());
          gstControllerList.add(new TextEditingController());
          totalControllerList.add(new TextEditingController());
          if (showEditJobTypeRemark != null && showEditJobTypeRemark) {
            jobTypeRemark[i] = data["jobTypeRemark"];
          }
          partNameVis[data["workordertaskid"]] = false;
          tecNameVis[data["workordertaskid"]] = false;
          isPartReceived[data["workordertaskid"]] = false;
        }
      }

      setState(() {});
    }
  }

  @override
  Widget build(BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    return new Scaffold(
      key: _scaffoldKey,
      appBar: GradientAppBar(
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back_ios,
            size: 30,
            color: Colors.white,
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        centerTitle: true,
        gradient: LinearGradient(
            colors: [const Color(0xFF8E2DE2), const Color(0xFF4A00E0)]),
        iconTheme: new IconThemeData(color: Colors.white),
        title: const Text(
          "Work Order",
          style: TextStyle(
              color: Colors.white,
              fontSize: 20.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
        actions: [
          IconButton(
            icon: Icon(
              Icons.search,
              color: Colors.white,
              size: 25,
            ),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => SearchScreen(
                          isWOSearch: true,
                        )),
              );
            },
          ),
        ],
        bottom: TabBar(
          unselectedLabelColor: Colors.white,
          labelColor: Colors.amber,
          tabs: [
            new Tab(
              icon: new Icon(
                Icons.info,
                size: 30,
              ),
              text: "Work Order Details",
            ),
            new Tab(
              text: "Process WO",
              icon: new Icon(
                Icons.settings,
                size: 30,
              ),
            ),
          ],
          controller: _tabController,
          indicatorColor: Colors.white,
          indicatorSize: TabBarIndicatorSize.tab,
        ),
        bottomOpacity: 1,
      ),
      body: TabBarView(
        children: [
          ListView(
            physics: const AlwaysScrollableScrollPhysics(),
            children: <Widget>[renderTaskDiv2()],
          ),
          processPage()
        ],
        controller: _tabController,
      ),
    );
  }

  Widget processPage() {
    return SingleChildScrollView(
        child: Container(
      child: Column(children: renderAllTasks()),
    ));
  }

  Widget getPartsData(Map woData) {
    return Container(
        margin: EdgeInsets.only(
          top: 8,
        ),
        child: Column(children: renderPartsData(woData)));
  }

  Widget getLabourData(Map woData) {
    return Container(child: Column(children: renderLaboursData(woData)));
  }

  Widget renderReOpenStatusButton() {
    return Button3d(
      style: Button3dStyle(
          topColor: Colors.red,
          backColor: Colors.red,
          borderRadius: BorderRadius.circular(30)),
      onPressed: () {
        if (WorkOrderUtility.hasAuthority(permission, "REOPEN_WORKORDER")) {
          FlutterAlert.confirmationAlertWithMethod(
              context,
              'are you sure you want to change status to Progress?',
              'To Progress Workorder ?',
              changeStatusToWorkOrderInProgress);
        }
      },
      child: Text(
        'RE-OPEN',
        style: TextStyle(
            fontSize: 15,
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  List<Widget> renderAllTasks() {
    int index = 0;
    List<Widget> serviceTaskList = new List<Widget>();
    serviceTaskList.add(renderAllStatus());
    serviceTaskList.add(renderAllCalculationValue());
    if (isWorkorderCompleted()) {
      if (reOpenWO != null && reOpenWO) {
        serviceTaskList.add(renderReOpenStatusButton());
      }
    }
    if (!isWorkorderCompleted()) {
      serviceTaskList.add(renderUploadDocu());
    }
    serviceTaskList.add(renderText());
    if (taskList != null && taskList.isNotEmpty) {
      for (index = 0; index < taskList.length; index++) {
        //taskRemarkControllerList[index].text = taskList[index]["jobTypeRemark"];
        serviceTaskList.add(renderOBJTask(taskList[index], index));
      }
    } else {
      serviceTaskList.add(renderTaskDiv(null, 0, 1));
      serviceTaskList.add(new Container());
    }
    if (!isWorkorderCompleted()) {
      serviceTaskList.add(renderMainMenu(
        "Add Task",
        0,
      ));
    }
    return serviceTaskList;
  }

  Widget renderAllCalculationValue() {
    return Container(
      child: Column(
        children: [
          renderRow("SubTotal", woData["totalsubworktask_cost"].toString(),
              "GST Cost", woData["totalworktax_cost"].toString()),
          WorkOrderUtility.renderMyDataFullWidth(
            "Total",
            FileUtility.checkObjIsEmpty(woData) &&
                    woData["totalworkorder_cost"] != null
                ? woData["totalworkorder_cost"].toString()
                : "--",
          ),
        ],
      ),
    );
  }

  Widget renderOBJTask(Map data, int index) {
    return Container(
      child: Column(
        children: [
          renderTask1(data, index),
        ],
      ),
    );
  }

  Widget renderMyData(String key, String val) {
    return Container(
      margin: EdgeInsets.only(top: 5, left: 5, right: 5),
      child: PaymentMethods(
        name: key,
        amount: val != null ? val : "--",
        icon: Icons.info,
      ),
    );
  }

  Widget renderMyIconData(String key, String val, int id) {
    Color iconCol = null;
    IconData iconData = null;
    if (id == 1) {
      iconData = Icons.done;
      iconCol = Colors.green;
    }
    if (id == 0) {
      iconData = FontAwesomeIcons.facebookMessenger;
      iconCol = Colors.grey;
    } else {
      iconData = Icons.settings;
      iconCol = Colors.red;
    }
    return Container(
      margin: EdgeInsets.only(top: 5, left: 5, right: 5),
      child: PaymentMethods(
        iconColor: iconCol,
        name: key,
        amount: val != null ? val : "--",
        icon: iconData,
      ),
    );
  }

  String getData(String key, Map data) {
    if (data != null && data.isNotEmpty) {
      if (data.containsKey(key)) {
        if (data[key] != null) {
          return data[key].toString();
        } else {
          return "";
        }
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  bool checkData(Map data) {
    try {
      if (data != null &&
          data.isNotEmpty &&
          data["last_occurred_woId"] != null &&
          data["last_occurred_woId"] > 0) {
        return true;
      } else {
        return false;
      }
    } catch (e) {
      return false;
    }
  }

  Widget renderLinkWO(Map data) {
    return Visibility(
      visible: checkData(data),
      child: NiceButton(
          fontSize: 18,
          width: 250,
          radius: 35,
          text: "Open WO",
          icon: Icons.open_in_full,
          gradientColors: [
            Colors.cyan,
            Colors.cyan,
          ],
          onPressed: () {
            getWorkOrderDetailsByWorkOrderId(
                data["last_occurred_woId"].toString());
          }),
    );
  }

  getWorkOrderDetailsByWorkOrderId(var woId) async {
    var jsonObject = {};
    jsonObject["workOrderId"] = woId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    jsonObject["isFromMob"] = 'true';
    var response = await ApiCall.getDataFromApi(
        URI.SHOW_WORK_ORDER_DETAILS_FOR_PROCESS,
        jsonObject,
        URI.LIVE_URI,
        context);
    if (response != null && response["WorkOrder"] != null) {
      Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) =>
              new WorkOrderStepProcess(workProcessData: response)));
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  Widget renderTaskDiv(Map data, int index, int id) {
    String text = getStatus(data);
    String content =
        getData("job_typetask", data) + getData("job_subtypetask", data);
    return Card(
      color: Colors.white,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
      ),
      borderOnForeground: true,
      elevation: 1.0,
      child: Column(
        children: [
          WorkOrderUtility.renderMyDataFullWidth(
            "Service Number",
            FileUtility.checkObjIsEmpty(data) &&
                    showServRemindWhileCreatingWO &&
                    data["service_Number"] != null
                ? data["service_Number"].toString()
                : "--",
          ),
          renderRow(
              "Parts Cost",
              FileUtility.checkObjIsEmpty(data) &&
                      data["totalpart_cost"] != null
                  ? data["totalpart_cost"].toString()
                  : "",
              "Labour Cost",
              FileUtility.checkObjIsEmpty(data) &&
                      data["totallaber_cost"] != null
                  ? data["totallaber_cost"].toString()
                  : ""),
          renderLinkWO(data),
          Visibility(
              visible: id == 1 && FileUtility.checkObjIsEmpty(data),
              child: renderRow(
                  "Info ",
                  text,
                  "Total",
                  FileUtility.checkObjIsEmpty(data) &&
                          data["totaltask_cost"] != null
                      ? data["totaltask_cost"].toString()
                      : "")),
          markComplete(id, data, content),
          renderRemoveTaskButton(data),
          Visibility(
            visible: showEditJobTypeRemark != null &&
                showEditJobTypeRemark &&
                data != null &&
                data.isNotEmpty,
            child: renderJobTypeRemark(data, index),
          ),
        ],
      ),
    );
  }

  Widget markComplete(int id, Map data, String content) {
    if (id == 1 && data != null && data.isNotEmpty) {
      if (data.containsKey("mark_complete")) {
        return renderMyIconData("Task ", content, data["mark_complete"]);
      } else {
        return Container();
      }
    } else {
      return Container();
    }
  }

  updateTaskRemark(Map data, index) async {
    var workordertaskid = data["workordertaskid"];
    String remark = jobTypeRemark[index];
    if (remark != "" && remark.length > 0) {
      var jsonObject = {};
      jsonObject["workordertaskid"] = workordertaskid.toString();
      jsonObject["remark"] = remark;
      jsonObject["companyId"] = companyId.toString();
      jsonObject["userId"] = userId.toString();
      jsonObject["isFromMob"] = 'true';
      var data = await ApiCall.getDataFromApi(
          URI.UPDATE_TASK_REMARK, jsonObject, URI.LIVE_URI, context);
      if (data != null) {
        if (data["taskRemark"] != null) {
          Navigator.pop(context);
        }
      } else {
        showMessage('error', 'Some Error Occured!');
      }
    } else {
      showMessage('info', 'Please Enter Remark !');
    }
  }

  Widget renderJobTypeRemark(Map data, int index) {
    return Container(
        width: _width - 20,
        margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
        child: Column(
          children: [
            TextFormField(
              enabled: workOrderStatusId != WorkOrderUtility.COMPLETED_ID,
              initialValue: jobTypeRemark[index],
              onChanged: (String text) {
                jobTypeRemark[index] = text;
              },
              keyboardType: TextInputType.text,
              textInputAction: TextInputAction.done,
              onFieldSubmitted: (term) {},
              style: TextStyle(
                  fontSize: 18.0,
                  fontWeight: FontWeight.bold,
                  fontFamily: "WorkSansBold"),
              //  controller: taskRemarkControllerList[index],
              decoration: InputDecoration(
                border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10.0)),
                prefixIcon: Icon(
                  FontAwesomeIcons.facebookMessenger,
                  size: 22.0,
                  color: Colors.black,
                ),
                labelText: 'Remark :',
                hintText: 'Remark',
              ),
              autofocus: false,
            ),
            RaisedButton(
              color: Colors.green,
              onPressed: () {
                updateTaskRemark(data, index);
              },
              child: Text("Save",
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: 18.0,
                      fontWeight: FontWeight.bold,
                      fontFamily: "WorkSansBold")),
            ),
          ],
        ));
  }

  Widget renderRemoveTaskButton(Map data) {
    if (FileUtility.checkObjIsEmpty(data) &&
        data["workordertaskid"] != null &&
        !isWorkorderCompleted()) {
      return removeTask(data["workordertaskid"]);
    } else {
      return Container();
    }
  }

  Widget renderTask1(Map data, int index) {
    return Container(
      child: Column(
        children: [
          SizedBox(
            height: 15,
          ),
          SizedBox(
            height: 10,
          ),
          Visibility(
              visible: isWorkorderCompleted(),
              child: renderMyData("Closed on ", woData["completed_date"])),
          Card(
            color: Colors.cyanAccent,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20.0),
            ),
            borderOnForeground: true,
            elevation: 1.0,
            child: Column(
              children: [
                renderTaskDiv(data, index, 1),
                Visibility(
                  visible: !isWorkorderCompleted(),
                  child: Column(
                    children: [
                      SizedBox(
                        height: 10,
                      ),
                      Row(
                        children: [
                          renderAddPartButton(data),
                          renderAddLabourButton(data),
                        ],
                      ),
                      SizedBox(
                        height: 10,
                      ),
                      rendermarkCompButton(data),
                      Card(
                        color: Colors.white,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20.0),
                        ),
                        child: Visibility(
                            visible: partNameVis[data["workordertaskid"]],
                            child: Column(
                              children: [
                                addPartNameAC(data, index),
                                quantyWidget(data, index),
                                partReceivedWidget(data),
                                savePartsWidget(data, index)
                              ],
                            )),
                      ),
                      Card(
                        color: Colors.white,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20.0),
                        ),
                        child: Visibility(
                            visible: tecNameVis[data["workordertaskid"]],
                            child: Column(
                              children: [
                                addLabourNameAC(data, index),
                                renderLabDetails(data, index),
                              ],
                            )),
                      ),
                      SizedBox(
                        height: 10,
                      ),
                      SizedBox(
                        height: 10,
                      ),
                    ],
                  ),
                ),
                Visibility(
                  visible: partList != null && partList.isNotEmpty,
                  child: renderTextData("Part Details"),
                ),
                getPartsData(data),
                Visibility(
                  visible: labourDataList != null && labourDataList.isNotEmpty,
                  child: renderTextData("Labour Details"),
                ),
                getLabourData(data)
              ],
            ),
          )
        ],
      ),
    );
  }

  Widget renderTextData(String text) {
    return Container(
      child: Text(
        text,
        style: TextStyle(
            fontFamily: "WorkSansSemiBold",
            fontSize: 22.0,
            fontWeight: FontWeight.bold,
            color: Colors.red),
      ),
    );
  }

  Widget renderMainMenu(String title, int index) {
    Widget widData = renderTask(index);
    if (widData == null) {
      widData = Container();
    }
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
          children: [widData]),
    );
  }

  Widget jobTypeWidget(int index) {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: CustomAutoComplete(
          suggestionList: jobTypeList,
          controller: jobTypeControllerList[index],
          hintLabel: 'Job Type',
          label: 'Job Type',
          dataKeyName: 'job_Type',
          iconData: Icons.directions,
          onItemSelected: (suggestion) {
            setState(() {
              jobTypeControllerList[index].text = suggestion['job_Type'];
              jobTypeIdMap[index] = suggestion['job_id'];
            });
          },
          onChanged: (pattern) {
            //method for getting data from server
            getJobTypelist(pattern);
          }),
    );
  }

  getJobTypelist(String str) async {
    setState(() {
      jobTypeList = [];
    });
    if (str != '' && str.length > 0) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_JOB_TYPE_WO, data, URI.LIVE_URI);
      if (response != null && response["jobTypeList"] != null) {
        setState(() {
          jobTypeList = response["jobTypeList"];
        });
      } else {
        setState(() {
          jobTypeList = [];
        });
      }
    }
  }

  Widget renderTask(int index) {
    return Card(
      color: Colors.white,
      // margin: EdgeInsets.only(left: 7, right: 7),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(25.0),
      ),
      borderOnForeground: true,
      elevation: 1.0,
      child: Column(
        children: [
          jobTypeWidget(index),
          subjobTypeWidget(index),
          remarkTF(index),
          addTasksWidget(index),
          SizedBox(
            height: 20,
          )
        ],
      ),
    );
  }

  Widget remarkTF(int index) {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.text,
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: remarkControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.facebookMessenger,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Remark :',
          hintText: 'Remark',
        ),
        autofocus: false,
      ),
    );
  }

  Widget subjobTypeWidget(int index) {
    return Container(
      margin: EdgeInsets.only(top: 2, left: 10, right: 10),
      child: CustomAutoComplete(
          suggestionList: jobSubTypeList,
          controller: subjobTypeControllerList[index],
          hintLabel: 'Job Sub Type ',
          label: 'Job Sub Type ',
          dataKeyName: 'job_ROT',
          iconData: Icons.directions,
          resetData: () {
            jobTypeIdMap[index] = 0;
            subjobIdMap[index] = 0;
            subjobTypeControllerList[index].text = "";
            jobTypeControllerList[index].text = "";
          },
          onItemSelected: (suggestion) {
            setState(() {
              subjobIdMap[index] = suggestion['job_Subid'];
              subjobTypeControllerList[index].text = suggestion['job_ROT'];
            });
            getJobTypeDetails(suggestion['job_Subid'].toString(), index);
          },
          onChanged: (pattern) {
            //method for getting data from server
            getsubJobTypelist(pattern);
          }),
    );
  }

  getJobTypeDetails(String subjobid, int index) async {
    var data = {'subJobId': subjobid, 'companyId': companyId, 'userId': userId};
    var response = await ApiCall.getDataWithoutLoader(
        URI.GET_JOB_DET_FROM_SUB_JOB_ID, data, URI.LIVE_URI);
    if (response != null && response["jobTypeDetails"] != null) {
      setState(() {
        jobTypeIdMap[0] = response["jobTypeDetails"]['job_TypeId'];
        jobTypeControllerList[0].text = response["jobTypeDetails"]["job_Type"];
      });
    }
  }

  getsubJobTypelist(String str) async {
    setState(() {
      jobSubTypeList = [];
    });
    if (str != '' && str.length > 0) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_SUB_JOB_TYPE_WO, data, URI.LIVE_URI);
      if (response != null && response["subjobTypeList"] != null) {
        setState(() {
          jobSubTypeList = response["subjobTypeList"];
        });
      } else {
        setState(() {
          jobSubTypeList = [];
        });
      }
    }
  }

  Widget addTasksWidget(int index) {
    return NiceButton(
      width: _width / 2,
      radius: 35,
      text: "Add Tasks",
      icon: Icons.add,
      gradientColors: [
        Colors.green,
        Colors.green,
      ],
      onPressed: () {
        saveTaskDetails(index);
      },
    );
  }

  saveTaskDetails(int index) async {
    var jobTypesId = jobTypeIdMap[index] != null ? jobTypeIdMap[index] : 0;
    var subjobTypeId = subjobIdMap[index] != null ? subjobIdMap[index] : 0;
    var remark = FileUtility.checkAndSet(remarkControllerList[index]);
    if (int.parse(workOrderId) <= 0) {
      showMessage('errors', 'Something Is Missing !');
      return false;
    }
    if (jobTypesId <= 0) {
      showMessage('errors', 'Please Enter Job Type !');
      return false;
    }

    if (subjobTypeId <= 0) {
      showMessage('errors', 'Please Enter Job Sub Type !');
      return false;
    }
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["vid"] = vehicleId.toString();
    jsonObject["jobtypeId"] = jobTypesId.toString();
    jsonObject["jobsubtypeId"] = subjobTypeId.toString();
    jsonObject["taskRemark"] = remark;
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    var response = await ApiCall.getDataFromApi(
        URI.SAVE_NEW_TASK_DETAILS, jsonObject, URI.LIVE_URI, context);
    if (response != null) {
      if (response["taskAdded"] != null && response["taskAdded"] == true) {
        closeScreenAndRefreshData();
        //	window.location.replace("showWorkOrder.in?woId="+$('#workorders_id').val()+"");
      }
    } else {
      showMessage('errors', 'Some Error Occred!');
    }
  }

  Widget renderLabWorkHour(Map data, int index) {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(top: 3, left: 5),
      child: TextFormField(
        onChanged: (String text) {
          calculateTotal(index);
        },
        inputFormatters: [
          WhitelistingTextInputFormatter(RegExp(r'^(\d+)?\.?\d{0,2}')),
        ],
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: workHourControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.sortNumericDown,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Work Hour :',
          hintText: 'Work Hour :',
        ),
        autofocus: false,
      ),
    );
  }

  getPercentage(var totAmt, var dis) {
    double res = (totAmt * dis / 100);
    if (res == null) {
      return 0;
    }
    return res;
  }

  getTaxAmt(var totAmt, var tax) {
    double res = (tax * totAmt) / 100;
    if (res == null) {
      return 0;
    }
    return res;
  }

  void calculateTotal(int index) {
    double res = 0.0;
    var hours = FileUtility.checkAndSetNumDouble(workHourControllerList[index]);
    var cost = FileUtility.checkAndSetNumDouble(labCostControllerList[index]);
    var disct = FileUtility.checkAndSetNumDouble(discountControList[index]);
    var tax = FileUtility.checkAndSetNumDouble(gstControllerList[index]);
    res = (hours * cost);
    if (disct > 0) {
      var ab = getPercentage(res, disct);
      if (ab != null && ab > 0) {
        res = res - ab;
      }
    }
    if (tax > 0) {
      res = res.abs();
      var ab = getPercentage(res, tax);
      if (ab != null && ab > 0) {
        res = ab + res;
      }
    }
    var finalRes = res.toStringAsFixed(2);
    totalControllerList[index].text = finalRes.toString();
    setState(() {});
  }

  Widget renderLabCost(Map data, int index) {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(top: 3, left: 5),
      child: TextFormField(
        onChanged: (String text) {
          calculateTotal(index);
        },
        inputFormatters: [
          WhitelistingTextInputFormatter(RegExp(r'^(\d+)?\.?\d{0,2}')),
        ],
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: labCostControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.sortNumericDown,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Lab Cost:',
          hintText: 'Lab Cost:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget renderLabDiscount(Map data, int index) {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(top: 3, left: 5),
      child: TextFormField(
        onChanged: (String text) {
          calculateTotal(index);
        },
        inputFormatters: [
          WhitelistingTextInputFormatter(RegExp(r'^(\d+)?\.?\d{0,2}')),
        ],
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: discountControList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.sortNumericDown,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Discount:',
          hintText: 'Discount :',
        ),
        autofocus: false,
      ),
    );
  }

  Widget renderLabGST(Map data, int index) {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(top: 3, left: 5),
      child: TextFormField(
        onChanged: (String text) {
          calculateTotal(index);
        },
        inputFormatters: [
          WhitelistingTextInputFormatter(RegExp(r'^(\d+)?\.?\d{0,2}')),
        ],
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: gstControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.sortNumericDown,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'GST :',
          hintText: 'GST:',
        ),
        autofocus: false,
      ),
    );
  }

  Widget renderLabtTotal(Map data, int index) {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(top: 3, left: 5),
      child: TextFormField(
        enabled: false,
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: totalControllerList[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.sortNumericDown,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Total :',
          hintText: 'Total :',
        ),
        autofocus: false,
      ),
    );
  }

  Widget renderLabDetails(Map data, int index) {
    return Container(
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              renderLabWorkHour(data, index),
              renderLabCost(data, index),
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              renderLabDiscount(data, index),
              renderLabGST(data, index),
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              renderLabtTotal(data, index),
              saveLabourWidget(data, index),
            ],
          )
        ],
      ),
    );
  }

  Widget savePartsWidget(Map data, int index) {
    return Container(
      margin: EdgeInsets.only(top: 5, bottom: 5),
      child: NiceButton(
        width: _width / 2 - 60,
        radius: 35,
        fontSize: 15,
        text: "Save Parts ",
        icon: Icons.save,
        gradientColors: [
          Colors.green,
          Colors.green,
        ],
        onPressed: () {
          savePartDetails(data["workordertaskid"], index);
        },
      ),
    );
  }

  bool savePartDetails(var workTaskId, int index) {
    String partRec = isPartReceived[workTaskId] ? "RECEIVED" : "";
    var partQuantityNum = taskWisePartQuantity[workTaskId];
    var currentQuantity = FileUtility.checkAndSetNumDouble(partQuantity[index]);
    bool res = quantityValidation(workTaskId, index);
    if (res != null && res) {
      var jsonObject = {};
      jsonObject["isFromMob"] = 'true';
      jsonObject["companyId"] = companyId.toString();
      jsonObject["userId"] = userId.toString();
      jsonObject["workOrderId"] = woData["workorders_id"].toString();
      jsonObject["woTaskId"] = workTaskId.toString();
      jsonObject["partId"] = taskPartId[workTaskId].toString();
      jsonObject["quantity"] = currentQuantity.toString();
      jsonObject["oldpart"] = partRec;
      //jsonObject["last_occurred"]		 		= $('#last_occurred'+workordertaskid+'').val();
      jsonObject["showSubLocation"] = showSubLocation.toString();
      jsonObject["subLocationId"] = subLocationId.toString();
      jsonObject["validateDoublePost"] = 'true';
      jsonObject["inventoryId"] = invetoryId[workTaskId].toString();
      jsonObject["invoiceWisePartListConfig"] =
          invoiceWisePartListConfig.toString();
      //console.log("jsonObject = for part save = ",jsonObject);
      savePartData(jsonObject);
    }
  }

  void savePartData(var data) async {
    var res = await ApiCall.getDataFromApi(
        URI.SAVE_PARTS, data, URI.LIVE_URI, context);
    if (res != null) {
      if (res["partAdded"] != null && res["partAdded"]) {
        closeScreenAndRefreshData();
      }
      if (res["NoInventory"] != null && data["NoInventory"] == true) {
        showMessage(
            'errors', 'Part Not Added, Please Add Parts To Inventory First !');
      }
      if (res["NoAuthen"] != null && res["NoAuthen"] == true) {
        showMessage(
            'errors', 'Please get Permission to add Parts in Work Order !');
      }
    }
  }

  bool quantityValidation(var taskId, int index) {
    var tpId = taskPartId[taskId] == null ? 0 : taskPartId[taskId];
    var tpquan =
        taskWisePartQuantity[taskId] == null ? 0 : taskWisePartQuantity[taskId];
    if (tpId != null && tpId > 0) {
      if (tpquan != null && tpquan > 0) {
        var partQuantityNum = taskWisePartQuantity[taskId];
        var currentQuantity =
            FileUtility.checkAndSetNumDouble(partQuantity[index]);
        if (currentQuantity != null && currentQuantity > 0) {
          if (invoiceWisePartListConfig) {
            if (currentQuantity > partQuantityNum) {
              showMessage(
                  'info',
                  'Quantity Can Not Be Greater Than Total Part Quantity ' +
                      partQuantityNum.toString() +
                      ' ');
              return false;
            }
          }
          return true;
        } else {
          showMessage('info', 'Please Add Part Quantity ');
          return false;
        }
      } else {
        showMessage('info', 'Please Add Part Details ');
        return false;
      }
    } else {
      showMessage('info', 'Please Add Part Details ');
      return false;
    }
  }

  validateLabourSave(var workordertaskid, int index) {
    var labId =
        techId["workordertaskid"] == null ? 0 : techId["workordertaskid"];
    var labName = tecNameControllerList[index].text;
    var hours = FileUtility.checkAndSetNumDouble(workHourControllerList[index]);
    var cost = FileUtility.checkAndSetNumDouble(labCostControllerList[index]);
    var disct = FileUtility.checkAndSetNumDouble(discountControList[index]);
    var tax = FileUtility.checkAndSetNumDouble(gstControllerList[index]);
    if (labId == 0 || labName == "" || labName.length == 0) {
      showMessage('errors', 'Please Enter Technician Name !');
      return false;
    }
    if (hours < 0) {
      showMessage('errors', 'Please Enter Time !');
      return false;
    }
    if (cost < 0) {
      showMessage('errors', 'Please Enter Cost !');
      return false;
    }
    if (disct < 0) {
      showMessage('errors', 'Please Enter Discount !');
      return false;
    }
    if (tax < 0) {
      showMessage('errors', 'Please Enter Tax !');
      return false;
    }
    return true;
  }

  bool saveLabourDetails(var data, int index) {
    var workordertaskid = data["workordertaskid"];
    var labId = techId[workordertaskid] == null ? 0 : techId[workordertaskid];
    var labName = tecNameControllerList[index].text;
    var hours = FileUtility.checkAndSetNumDouble(workHourControllerList[index]);
    var cost = FileUtility.checkAndSetNumDouble(labCostControllerList[index]);
    var disct = FileUtility.checkAndSetNumDouble(discountControList[index]);
    var tax = FileUtility.checkAndSetNumDouble(gstControllerList[index]);
    var tot = FileUtility.checkAndSetNumDouble(totalControllerList[index]);
    if (labId == 0 || labName == "" || labName.length == 0) {
      showMessage('errors', 'Please Enter Technician Name !');
      return false;
    }
    if (hours < 0) {
      showMessage('errors', 'Please Enter Time !');
      return false;
    }
    if (cost < 0) {
      showMessage('errors', 'Please Enter Cost !');
      return false;
    }
    if (disct < 0) {
      showMessage('errors', 'Please Enter Discount !');
      return false;
    }
    if (tax < 0) {
      showMessage('errors', 'Please Enter Tax !');
      return false;
    }
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    jsonObject["workOrderId"] = woData["workorders_id"].toString();
    jsonObject["woTaskId"] = workordertaskid.toString();
    jsonObject["laberid"] = labId.toString();
    jsonObject["laberhourscost"] = hours.toString();
    jsonObject["eachlabercost"] = cost.toString();
    jsonObject["laberdiscount"] = disct.toString();
    jsonObject["labertax"] = tax.toString();
    jsonObject["totalLaborcost"] = tot.toString();
    jsonObject["validateDoublePost"] = 'true';
    saveLaouborData(jsonObject);
    // return true;
  }

  void saveLaouborData(var data) async {
    var response = await ApiCall.getDataFromApi(
        URI.SAVE_LABOUR, data, URI.LIVE_URI, context);
    if (response != null) {
      if (response["labourAdded"] != null && response["labourAdded"]) {
        closeScreenAndRefreshData();
      }
      if (response["NoAuthen"] != null && response["NoAuthen"] == true) {
        showMessage('info',
            'Please get Permission to add Labour Details in Work Order !');
      }
    }
  }

  markAsComplete(workordertaskid) async {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["woTaskId"] = workordertaskid.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    var response = await ApiCall.getDataFromApi(
        URI.MARK_COMPLETE, jsonObject, URI.LIVE_URI, context);
    if (response != null &&
        response["markAsCompleted"] != null &&
        response["markAsCompleted"]) {
      closeScreenAndRefreshData();
      //showMessage('success', 'Mark As Completed');
    } else {
      showMessage('errors', 'Some Error Occred!');
    }
  }

  changeStatusToWorkOrderInProgress() async {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    var response = await ApiCall.getDataFromApi(
        URI.CHANGE_TO_IN_PROG, jsonObject, URI.LIVE_URI, context);
    if (response != null) {
      if (response["accidentEntryApproved"] != null) {
        showMessage('info', response["accidentEntryApproved"].toString());
      } else if (response["statusChangedToInProgress"] != null &&
          response["statusChangedToInProgress"]) {
        closeScreenAndRefreshData();
        //	window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
      }

      if (response["NoAuthen"] != null && response["NoAuthen"]) {
        showMessage('info',
            'Please get Permission to change Work Order Status to In Progress !');
      }
    }
  }

  changeStatusToWorkOrderOnHold() async {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    var response = await ApiCall.getDataFromApi(
        URI.CHANGE_TO_HOLD, jsonObject, URI.LIVE_URI, context);
    if (response != null && response["statusChangedToHold"] != null) {
      if (response["statusChangedToHold"]) {
        closeScreenAndRefreshData();
        //window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
      }
      if (response["NoAuthen"] != null && response["NoAuthen"]) {
        showMessage('info',
            'Please get Permission to change Work Order Status to Hold !');
      }
    }
  }

  closeScreenAndRefreshData() {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    jsonObject["workOrderId"] = workOrderId.toString();
    WorkOrderUtility.getWorkOrderDetailsByWorkOrderIdData(jsonObject, context);
  }

  changeStatusToWorkOrderCompleted() async {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    var response = await ApiCall.getDataFromApi(
        URI.CHANGE_TO_COMPLETED, jsonObject, URI.LIVE_URI, context);
    if (response != null &&
        response["quotationNotApproved"] != null &&
        response["quotationNotApproved"]) {
      showMessage(
          'errors', 'Cannot Close, Vehicle Accident quotation not approved !');
    } else if (response != null &&
        response["statusChangedToComplete"] != null &&
        response["statusChangedToComplete"] == true) {
      closeScreenAndRefreshData();
      //window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
    }
  }

  void showMessage(String tit, String descn) {
    FlutterAlert.onInfoAlert(context, descn, tit);
  }

  Widget saveLabourWidget(Map data, int index) {
    return NiceButton(
      width: _width / 2 - 50,
      radius: 35,
      fontSize: 15,
      text: "Save Labour",
      icon: Icons.save,
      gradientColors: [
        Colors.green,
        Colors.green,
      ],
      onPressed: () {
        saveLabourDetails(data, index);
      },
    );
  }

  Widget partReceivedWidget(Map data) {
    return Card(
      color: Colors.white,
      child: SwitchListTile(
        title: Text(
          'Old Part Received ',
          style: TextStyle(
              color: Colors.blue, fontWeight: FontWeight.w800, fontSize: 20),
        ),
        value: isPartReceived[data["workordertaskid"]],
        activeColor: Colors.red,
        inactiveTrackColor: Colors.grey,
        onChanged: (bool value) {
          setState(() {
            isPartReceived[data["workordertaskid"]] = value;
          });
        },
      ),
    );
  }

  Widget quantyWidget(Map data, int index) {
    return Container(
      width: _width - 20,
      margin: EdgeInsets.only(top: 3, left: 10, right: 10, bottom: 10),
      child: TextFormField(
        inputFormatters: [
          WhitelistingTextInputFormatter(RegExp(r'^(\d+)?\.?\d{0,2}')),
        ],
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: false),
        textInputAction: TextInputAction.done,
        //  focusNode: odometerRange,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: partQuantity[index],
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          prefixIcon: Icon(
            FontAwesomeIcons.sortNumericDown,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Part Quantity :',
          hintText: 'Part Quantity  :',
        ),
        autofocus: false,
      ),
    );
  }

  Widget addLabourNameAC(Map data, int index) {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 2),
      child: AutoCompleteUtility(
          textLimit: 12,
          suggestionList: techicianNameData,
          controller: tecNameControllerList[index],
          hintLabel: 'Technician Name',
          label: 'Technician Name',
          dataKeyName: [
            "driver_firstname",
            "driver_Lastname",
          ],
          iconData: FontAwesomeIcons.truck,
          onItemSelected: (suggestion) {
            setState(() {
              tecNameControllerList[index].text =
                  suggestion["driver_firstname"].toString() +
                      "-" +
                      suggestion["driver_Lastname"].toString();
              techId[data["workordertaskid"]] = suggestion["driver_id"];
            });
          },
          onChanged: (pattern) {
            techNameAC(pattern, index);
          }),
    );
  }

  Widget addPartNameAC(Map data, int index) {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 2),
      child: AutoCompleteUtility(
          textLimit: 12,
          suggestionList: partNameData,
          controller: partNameControllerList[index],
          hintLabel: 'Part Name',
          resetData: () {
            setState(() {
              taskPartId[data["workordertaskid"]] = 0;
              taskWisePartQuantity[data["workordertaskid"]] = 0;
              invetoryId[data["workordertaskid"]] = 0;
            });
          },
          label: 'Part Name',
          dataKeyName: [
            "partnumber",
            "partname",
            "make",
            "inventory_id",
            "quantity"
          ],
          iconData: FontAwesomeIcons.truck,
          onItemSelected: (suggestion) {
            setState(() {
              partNameControllerList[index].text =
                  suggestion["partnumber"].toString() +
                      "-" +
                      suggestion["partname"].toString() +
                      "-" +
                      suggestion["make"].toString() +
                      "-" +
                      suggestion["inventory_id"].toString() +
                      "-" +
                      suggestion["quantity"].toString();
              taskWisePartQuantity[data["workordertaskid"]] =
                  suggestion["quantity"];
              taskPartId[data["workordertaskid"]] =
                  suggestion["inventory_location_id"];
              invetoryId[data["workordertaskid"]] = suggestion["inventory_id"];
            });
          },
          onChanged: (pattern) {
            partNameAC(pattern, data, index);
          }),
    );
  }

  partNameAC(String str, Map data, int index) async {
    setState(() {
      partNameData = [];
    });
    if (str != '' && str.length > 0) {
      var vdata = {
        'companyId': companyId,
        'term': str,
        'userId': userId,
        'isFromMob': 'true',
        'mainLocationId': woData["workorders_location_ID"].toString(),
        'subLocationId': woData["subLocationId"].toString(),
      };
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_PART_DETAILS, vdata, URI.LIVE_URI);
      if (response != null && response["finalList"] != null) {
        setState(() {
          partNameData = response["finalList"];
        });
      } else {
        setState(() {
          partNameData = [];
        });
      }
    }
  }

  techNameAC(String str, int index) async {
    setState(() {
      techicianNameData = [];
    });
    if (str != '' && str.length > 0) {
      var vdata = {
        'companyId': companyId,
        'term': str,
        'userId': userId,
        'isFromMob': 'true',
      };
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_TEC_DETAILS, vdata, URI.LIVE_URI);
      if (response != null && response["technicianList"] != null) {
        setState(() {
          techicianNameData = response["technicianList"];
        });
      } else {
        setState(() {
          techicianNameData = [];
        });
      }
    }
  }

  Widget renderAddPartButton(Map data) {
    return NiceButton(
      fontSize: 14,
      width: _width / 2 - 40,
      radius: 35,
      text: partNameVis[data["workordertaskid"]] ? "Cancel Parts" : "Add Parts",
      icon: partNameVis[data["workordertaskid"]] ? Icons.cancel : Icons.add,
      gradientColors: [
        Color(0xFF72147e),
        Color(0xFF72147e),
      ],
      onPressed: () {
        setState(() {
          partNameVis[data["workordertaskid"]] =
              !partNameVis[data["workordertaskid"]];
        });
      },
    );
  }

  Widget renderAddLabourButton(Map data) {
    return NiceButton(
      fontSize: 14,
      width: _width / 2 - 50,
      radius: 35,
      text:
          tecNameVis[data["workordertaskid"]] ? "Cancel Labour" : "Add Labour",
      icon: tecNameVis[data["workordertaskid"]] ? Icons.cancel : Icons.add,
      gradientColors: [
        Colors.red,
        Color(0xFFf3bda1),
      ],
      onPressed: () {
        setState(() {
          tecNameVis[data["workordertaskid"]] =
              !tecNameVis[data["workordertaskid"]];
        });
      },
    );
  }

  Widget rendermarkCompButton(Map data) {
    if (WorkOrderUtility.hasAuthority(permission, "COMPLETE_WORKORDER")) {
      return NiceButton(
        fontSize: 14,
        width: _width / 2,
        radius: 35,
        text: "Mark Complete",
        icon: Icons.done,
        gradientColors: [
          Color(0xFF2b2e4a),
          Color(0xFF2b2e4a),
        ],
        onPressed: () {
          if (WorkOrderUtility.hasAuthority(permission, "COMPLETE_WORKORDER")) {
            markAsComplete(data["workordertaskid"]);
          }
        },
      );
    } else {
      return WorkOrderUtility.emptyWidget();
    }
  }

  String getStatus(Map data) {
    String text = "";
    if (data != null && data.isNotEmpty) {
      int id = 0;
      id = data["last_occurred_woId"] == null ? 0 : data["last_occurred_woId"];
      if (id != null && id != 0) {
        String dt = data["last_occurred_date"].toString();
        String lastodo = data["last_occurred_odameter"].toString();
        String text =
            "Last occurred on $dt" + "	this task on Odometer = $lastodo";

        return text;
      } else {
        text = 'Never logged for is task';
        return text;
      }
    } else {
      return text;
    }
  }

  Widget cancelButton() {
    return Container(
      width: _width / 2 - 30,
      child: NiceButton(
        fontSize: 14,
        radius: 35,
        text: "Cancel",
        icon: Icons.cancel,
        gradientColors: [
          Colors.green,
          Colors.green,
        ],
        onPressed: () {
          Navigator.pop(context);
        },
      ),
    );
  }

  Widget renderCard(String title, String val) {
    return ListTile(
      isThreeLine: true,
      onTap: () {},
      subtitle: Text(
        val == null ? "" : val,
        style: TextStyle(
            color: Colors.black,
            fontSize: 17.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
      trailing: Text(""),
      title: Text(
        title,
        style: TextStyle(
            color: Colors.black,
            fontSize: 17.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  Widget renderTaskDiv2() {
    return Container(
      //color: Colors.white,
      decoration: new BoxDecoration(
        // color: Colors.cyanAccent,
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(10.0),
          bottomLeft: const Radius.circular(10.0),
        ),
      ),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              approvalButton(),
              SizedBox(
                height: 10,
              ),
              cancelButton(),
            ],
          ),
          SizedBox(
            height: 10,
          ),
          renderText(),
          SizedBox(
            height: 10,
          ),
          renderRow("Vehicle No:", woData["vehicle_registration"].toString(),
              "Assignee:", woData["assignee"].toString()),
          renderRow("Start date:", woData["start_date"], "Due date:",
              woData["due_date"]),
          renderRow("Indent | PO No:", woData["indentno"], "Driver Name:",
              woData["workorders_drivername"]),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Visibility(
                visible: showDriverNumberCol != null && showDriverNumberCol,
                child: WorkOrderUtility.renderMyData("Driver Number :",
                    woData["workorders_driver_number"].toString(), context),
              ),
              Visibility(
                visible: showOutWorkStationCol != null && showOutWorkStationCol,
                child: WorkOrderUtility.renderMyData("Work Station :",
                    woData["out_work_station"].toString(), context),
              ),
            ],
          ),
          renderRow("Odometer :", woData["vehicle_Odometer"].toString(),
              "GPS Work Location:", woData["gpsWorkLocation"]),
          Visibility(
            visible: woData["gpsOdometer"] != null && woData["gpsOdometer"] > 0,
            child: WorkOrderUtility.renderMyData(
                "GPS Odometer :", woData["gpsOdometer"].toString(), context),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              WorkOrderUtility.renderMyData(
                  "Initial_Note :", woData["initial_note"], context),
              Visibility(
                visible: FileUtility.checkFlagInConfig("showDieselCol", config),
                child: WorkOrderUtility.renderMyData(
                    "Diesel :",
                    woData["workorders_diesel"] != null
                        ? woData["workorders_diesel"].toString()
                        : "",
                    context),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Container(
                  child: WorkOrderUtility.renderMyData(
                "Work Location:",
                woData["workorders_location"],
                context,
              )),
              Visibility(
                  visible: config != null &&
                      config.isNotEmpty &&
                      config["TallyCompanyMasterInWO"],
                  child: WorkOrderUtility.renderMyData("Tally Company Master:",
                      woData["tallyCompanyName"], context)),
            ],
          ),
          Visibility(
            visible: FileUtility.checkFlagInConfig("showMakeApproval", config),
            child: renderApprovedButton(
                woData["approvalStatusForMob"], woData["approvalColorId"]),
          )
        ],
      ),
    );
  }

  Color getColorFromId(int id) {
    try {
      if (id == 1) {
        return Colors.green;
      } else {
        return Colors.red;
      }
    } catch (e) {
      return Colors.white;
    }
  }

  Widget renderApprovedButton(String status, int id) {
    Color colData = getColorFromId(id);
    return Container(
      child: NiceButton(
        fontSize: 15,
        width: _width / 2 - 40,
        radius: 05,
        text: status,
        icon: Icons.info,
        gradientColors: [
          colData,
          colData,
        ],
        onPressed: () {},
      ),
    );
  }

  Widget renderText() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            margin: EdgeInsets.only(left: 40),
            child: Text(
              'Work Order : ' + woData["workorders_Number"].toString(),
              style: GoogleFonts.openSans(
                  textStyle: TextStyle(
                      color: Colors.black,
                      fontSize: 20,
                      fontWeight: FontWeight.w600)),
            ),
          ),
          Card(
            margin: EdgeInsets.only(right: 40),
            color: Colors.yellow,
            child: Text(
              woData["priority"].toString(),
              style: GoogleFonts.openSans(
                  textStyle: TextStyle(
                      color: Colors.black,
                      fontSize: 20,
                      fontWeight: FontWeight.w600)),
            ),
          )
        ],
      ),
    );
  }

  Widget setData(String key, String value) {
    return MyCard(
        // onTap: () => ,
        valueColorChanged: false,
        colorData: Colors.white,
        valueColor: Colors.cyanAccent,
        name: key,
        value: value == null || value.length == 0 ? "--" : value,
        icon: Icons.info_outline,
        cardColorChanged: false);
  }

  Widget renderOpenStatusButton() {
    int thisButtonId = WorkOrderUtility.OPEN_WO_ID;
    return GestureDetector(
      onTap: () {},
      child: CircleAvatar(
        foregroundColor: Colors.black,
        backgroundColor: thisButtonId == workOrderStatusId
            ? Colors.greenAccent[400]
            : Colors.white,
        radius: 35,
        child: Text(
          'Open',
          style: TextStyle(
              fontSize: 15,
              color: Colors.black,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ), //Text
      ),
    ); //
  }

  Widget renderAllStatus() {
    return Container(
        child: Visibility(
            visible: !isWorkorderCompleted(),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    renderOpenStatusButton(),
                    renderProgressStatusButton(),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [renderOnHoldStatusButton(), completeStatus()],
                ),
              ],
            )));
  }

  Widget renderProgressStatusButton() {
    int thisButtonId = WorkOrderUtility.IN_PROCESS_ID;
    Color col = thisButtonId == workOrderStatusId
        ? Colors.greenAccent[400]
        : Colors.white;
    return Button3d(
      style: Button3dStyle(
          topColor: col,
          backColor: col,
          borderRadius: BorderRadius.circular(30)),
      onPressed: () {
        FlutterAlert.confirmationAlertWithMethod(
            context,
            'are you sure you want to change status to Progress?',
            'To Progress Workorder ?',
            changeStatusToWorkOrderInProgress);
      },
      child: Text(
        '\t\t\tIn' + '\t\t\nProgress',
        style: TextStyle(
            fontSize: 15,
            color: Colors.black,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  Widget renderOnHoldStatusButton() {
    int thisButtonId = WorkOrderUtility.ON_HOLD_ID;
    Color col = thisButtonId == workOrderStatusId
        ? Colors.greenAccent[400]
        : Colors.white;
    return Button3d(
      style: Button3dStyle(
          topColor: col,
          backColor: col,
          borderRadius: BorderRadius.circular(30)),
      onPressed: () {
        FlutterAlert.confirmationAlertWithMethod(
            context,
            'are you sure you want to change status to In Hold ?',
            'On Hold Workorder ?',
            changeStatusToWorkOrderOnHold);
      },
      child: Text(
        'On Hold',
        style: TextStyle(
            fontSize: 15,
            color: Colors.black,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  Widget completeStatus() {
    int thisButtonId = WorkOrderUtility.ON_HOLD_ID;
    Color col = thisButtonId == workOrderStatusId
        ? Colors.greenAccent[400]
        : Colors.white;
    return Button3d(
      style: Button3dStyle(
          topColor: Colors.green,
          backColor: Colors.green,
          borderRadius: BorderRadius.circular(30)),
      onPressed: () {
        FlutterAlert.confirmationAlertWithMethod(
            context,
            'are you sure you want to change status To Complete ?',
            'Complete Workorder ?',
            changeStatusToWorkOrderCompleted);
      },
      child: Text(
        'Complete',
        style: TextStyle(
            fontSize: 15,
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  Widget completeStatusa() {
    return NiceButton(
      width: _width / 2 - 10,
      radius: 35,
      text: "Complete",
      icon: Icons.add,
      gradientColors: [
        Colors.green,
        Colors.green,
      ],
      onPressed: () {
        FlutterAlert.confirmationAlertWithMethod(
            context,
            'are you sure you want to change status To Complete ?',
            'Complete Workorder ?',
            changeStatusToWorkOrderCompleted);
      },
    );
  }

  bool isWorkorderCompleted() {
    try {
      if (workOrderStatusId == WorkOrderUtility.COMPLETED_ID) {
        return true;
      } else {
        return false;
      }
    } catch (e) {
      return false;
    }
  }

  List<Widget> renderPartsData(Map data) {
    List<Widget> list = new List();
    if (partList != null && partList.isNotEmpty) {
      for (int i = 0; i < partList.length; i++) {
        var woPartData = partList[i];
        if (woPartData["workordertaskid"] == data["workordertaskid"]) {
          list.add(renderMenu(
              "Part: " +
                  partList[i]["partname"].toString() +
                  "  " +
                  partList[i]["partnumber"].toString(),
              setElementInListDataForParts(partList[i])));
        }
      }
      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  List<Widget> renderLaboursData(var data) {
    List<Widget> list = new List();
    if (labourDataList != null && labourDataList.isNotEmpty) {
      for (int i = 0; i < labourDataList.length; i++) {
        var woLabData = labourDataList[i];
        if (woLabData["workordertaskid"] == data["workordertaskid"]) {
          list.add(renderMenu(
              "Technician: " + labourDataList[i]["labername"].toString(),
              setElementInListDataForLabour(labourDataList[i])));
        }
      }
      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  Widget renderMenu(String title, List<Widget> wdList) {
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

  List<Widget> setElementInListDataForParts(Map data) {
    List<Widget> wlist = new List();
    wlist.add(renderRow(
        "Part", data["partname"], "Qty", data["quantity"].toString()));
    wlist.add(renderRow("Each", data["parteachcost"].toString(), "Total",
        data["totalcost"].toString()));
    if (!isWorkorderCompleted()) {
      wlist.add(removePart(data["workordertaskto_partid"]));
    }
    if (FileUtility.checkFlagInConfig(
      "partsCreatedBy",
      config,
    )) {
      wlist.add(WorkOrderUtility.renderDataCard(
        "Part Cr By",
        data["firstName"].toString(),
      ));
    }

    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
    return wlist;
  }

  Widget renderRow(String key1, String value1, String key2, String value2) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        WorkOrderUtility.renderMyData(key1, value1, context),
        WorkOrderUtility.renderMyData(key2, value2, context),
      ],
    );
  }

  List<Widget> setElementInListDataForLabour(Map data) {
    List<Widget> wlist = new List();
    wlist.add(renderRow("Hours", data["laberhourscost"].toString(), "Rates",
        " ₹." + data["eachlabercost"].toString()));

    wlist.add(renderRow("Dis", data["laberdiscount"].toString() + " % ", "GST",
        data["eachlabercost"].toString() + " % "));

    wlist.add(renderMyData(
      "Total",
      data["totalcost"].toString(),
    ));
    if (!isWorkorderCompleted()) {
      wlist.add(removeLabour(data["workordertaskto_laberid"]));
    }
    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
    return wlist;
  }

  Widget removeTask(int id) {
    if (WorkOrderUtility.hasAuthority(permission, "DELETE_WORKORDER")) {
      return Container(
        margin: EdgeInsets.all(10),
        child: NiceButton(
          fontSize: 18,
          width: _width / 2,
          radius: 35,
          text: "Remove Task",
          icon: Icons.delete,
          gradientColors: [
            Colors.red,
            Colors.red,
          ],
          onPressed: () {
            if (WorkOrderUtility.hasAuthority(permission, "DELETE_WORKORDER")) {
              currTaskId = id;
              FlutterAlert.confirmationAlertWithMethod(
                  context,
                  'are you sure you want to delete Task ?',
                  'Delete Part ?',
                  removeTaskDetails);
            }
          },
        ),
      );
    } else {
      return WorkOrderUtility.emptyWidget();
    }
  }

  removeTaskDetails() async {
    if (currTaskId > 0) {
      var jsonObject = {};
      jsonObject["workordertaskid"] = currTaskId.toString();
      jsonObject["isFromMob"] = 'true';
      jsonObject["companyId"] = companyId.toString();
      jsonObject["userId"] = userId.toString();
      var response = await ApiCall.getDataWithoutLoader(
          URI.DELETE_WO_TASK, jsonObject, URI.LIVE_URI);
      if (response != null) {
        if (response["accidentEntryApproved"] != null &&
            response["accidentEntryApproved"]) {
          showMessage('info',
              'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
        } else if (response["deletePartFirst"] != null &&
            response["deletePartFirst"] == true) {
          showMessage('info',
              'Cannot Delete Task, Please Delete Parts which are added to the Task.');
        } else if (response["deleteLobourFirst"] != null &&
            response["deleteLobourFirst"] == true) {
          showMessage('info',
              'Cannot Delete Task, Please Delete Labour Details which are added to the Task.');
        }

        if (response["taskDetailsDeleted"] != null &&
            response["taskDetailsDeleted"] == true) {
          closeScreenAndRefreshData();
          //window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
        }
      }
    }
    currTaskId = 0;
  }

  Widget removePart(int id) {
    if (WorkOrderUtility.hasAuthority(permission, "DELETE_WORKORDER")) {
      return Container(
          margin: EdgeInsets.all(10),
          child: NiceButton(
            fontSize: 18,
            width: _width / 2,
            radius: 35,
            text: "Remove Part",
            icon: Icons.delete,
            gradientColors: [
              Colors.red,
              Colors.red,
            ],
            onPressed: () {
              if (WorkOrderUtility.hasAuthority(
                  permission, "DELETE_WORKORDER")) {
                currPartId = id;
                FlutterAlert.confirmationAlertWithMethod(
                    context,
                    'are you sure you want to delete Part ?',
                    'Delete Part ?',
                    removePartData);
              }
            },
          ));
    } else {
      return WorkOrderUtility.emptyWidget();
    }
  }

  removePartData() async {
    if (currPartId > 0) {
      var jsonObject = {};
      jsonObject["workordertaskto_partid"] = currPartId.toString();
      jsonObject["isFromMob"] = 'true';
      jsonObject["companyId"] = companyId.toString();
      jsonObject["userId"] = userId.toString();
      jsonObject["subLocationId"] = subLocationId.toString();
      var response = await ApiCall.getDataWithoutLoader(
          URI.DELETE_PART_OF_WO_TASK, jsonObject, URI.LIVE_URI);
      if (response != null) {
        if (response["accidentEntryApproved"] != null &&
            response["accidentEntryApproved"]) {
          showMessage('info',
              'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
        } else if (response["partDeleted"] != null &&
            response["partDeleted"] == true) {
          closeScreenAndRefreshData();
        } else if (response["NoAuthen"] != null &&
            response["NoAuthen"] == true) {
          showMessage(
              'info', 'Please get Permission to delete Part in Work Order !');
        }
      }
    }
    currPartId = 0;
  }

  Widget removeLabour(int id) {
    if (WorkOrderUtility.hasAuthority(permission, "DELETE_WORKORDER")) {
      return Container(
          margin: EdgeInsets.all(10),
          child: NiceButton(
            fontSize: 18,
            width: _width / 2,
            radius: 35,
            text: "Remove Labour",
            icon: Icons.delete,
            gradientColors: [
              Colors.red,
              Colors.red,
            ],
            onPressed: () {
              if (WorkOrderUtility.hasAuthority(
                  permission, "DELETE_WORKORDER")) {
                currLabId = id;
                FlutterAlert.confirmationAlertWithMethod(
                    context,
                    'are you sure you want to delete Labour ?',
                    'Delete Labour ?',
                    deleteLabourDetails);
              }
            },
          ));
    } else {
      return WorkOrderUtility.emptyWidget();
    }
  }

  deleteLabourDetails() async {
    if (currLabId > 0) {
      var jsonObject = {};
      jsonObject["isFromMob"] = 'true';
      jsonObject["workordertaskto_laberid"] = currLabId.toString();
      jsonObject["companyId"] = companyId.toString();
      jsonObject["userId"] = userId.toString();
      var response = await ApiCall.getDataWithoutLoader(
          URI.DELETE_LABOUR_OF_WO_TASK, jsonObject, URI.LIVE_URI);
      if (response != null) {
        if (response["accidentEntryApproved"] != null &&
            response["accidentEntryApproved"]) {
          showMessage('info',
              'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
        } else if (response["LabourDetailsDeleted"] != null &&
            response["LabourDetailsDeleted"] == true) {
          closeScreenAndRefreshData();
        } else if (response["NoAuthen"] != null &&
            response["NoAuthen"] == true) {
          showMessage(
              'info', 'Please get Permission to delete Labour in Work Order !');
        }
      }
    }
    currLabId = 0;
  }

  Widget approvalButton() {
    if (FileUtility.checkObjIsEmpty(config) &&
        FileUtility.checkObjIsEmpty(woData) &&
        config["showMakeApproval"] &&
        woData["approvalStatusId"] <= 0) {
      return Container(
        child: NiceButton(
          fontSize: 14,
          width: _width / 2 - 40,
          radius: 20,
          text: "Make Approval",
          icon: Icons.approval,
          gradientColors: [
            Colors.green,
            Colors.green,
          ],
          onPressed: () {
            FlutterAlert.confirmationAlertWithMethod(
                context,
                'You wont be able to revert this !',
                'Are you sure to make Approval?',
                approveWorkOrder);
          },
        ),
      );
    } else {
      return Container();
    }
  }

  approveWorkOrder() async {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workorderId"] = workOrderId.toString();
    jsonObject["approvalStatusId"] = "1";
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    var response = await ApiCall.getDataWithoutLoader(
        URI.APPROVE_WO, jsonObject, URI.LIVE_URI);
    if (response != null) {
      closeScreenAndRefreshData();
      //showMessage('success', 'WorkOrder Approved Successfully !');
    }
  }

  uploadWorkOrderDocument() async {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    jsonObject["base64String"] = base64String;
    jsonObject["imageName"] = imageName;
    jsonObject["imageExt"] = imageExt;
    var response = await ApiCall.getDataWithoutLoader(
        URI.UPLOAD_WO_DCOUMENT, jsonObject, URI.LIVE_URI);
    if (response != null) {
      if (response["UploadSuccess"] != null) {
        closeScreenAndRefreshData();
        //showMessage('success', 'WorkOrder Approved Successfully !');
      }
    }
  }

  Widget renderUploadDocu() {
    return Card(
      margin: EdgeInsets.all(10),
      child: Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
        Icon(
          Icons.file_upload,
          color: Colors.purpleAccent,
        ),
        Text(
          "WO Document ",
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
            style: TextStyle(color: Colors.white, fontSize: 25),
          ),
          gradient:
              LinearGradient(colors: [Colors.blueGrey, Colors.blueAccent]),
          onPressed: openBottomSheetForAll,
        )
      ]),
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

  Future getImageFromCamera() async {
    var myImage;
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
    });
    testCompressAndGetFile(myImage, myImage.path);
  }

  Future getImageGallery() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.gallery, maxHeight: 600, maxWidth: 600);
    if (imageFile == null) {
      return;
    }
    var myImage;
    setState(() {
      myImage = imageFile;
    });
    imageName = myImage.path.split("/").last;
    imageExt = imageName.split(".").last;

    testCompressAndGetFile(myImage, myImage.path);
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
      var base64Images = base64Encode(file.readAsBytesSync());
      setState(() {
        base64String = base64Images;
      });
      uploadWorkOrderDocument();
    }
  }
}
