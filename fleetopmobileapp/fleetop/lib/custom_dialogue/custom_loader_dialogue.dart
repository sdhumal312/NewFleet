import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

class CustomLoaderDialog extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () {
        //Navigator.pop(context);
      },
      child: Dialog(
        elevation: 0,
        backgroundColor: Colors.transparent,
        child: Container(
          padding: EdgeInsets.only(right: 16.0),
          width: 150,
          height: 220,
          decoration: BoxDecoration(
              color: Colors.transparent,
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(45),
                  bottomLeft: Radius.circular(45),
                  topRight: Radius.circular(45),
                  bottomRight: Radius.circular(45))),
          child: Column(
            children: <Widget>[
              Container(
                height: 100,
                //margin: EdgeInsets.only(top: 10.0,left: 10),
                child: SpinKitChasingDots(
                  color: Colors.lightGreen[100],
                  size: 50.0,
                ),
              ),
              Padding(
                padding: EdgeInsets.only(top: 20.0),
              ),
              Text(
                "Please Wait ...",
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 21.0,
                    fontFamily: "WorkSansSemiBold"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
