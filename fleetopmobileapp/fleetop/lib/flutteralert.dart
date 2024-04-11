import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:flutter/material.dart';

class FlutterAlert {
  static void onErrorAlert(BuildContext context, String mes, String title) {
    Alert(
      context: context,
      type: AlertType.error,
      title: title,
      desc: mes,
      buttons: [
        DialogButton(
          child: Text(
            "OK",
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

  static void onInfoAlert(BuildContext context, String mes, String title) {
    Alert(
      context: context,
      type: AlertType.warning,
      title: title,
      desc: mes,
      buttons: [
        DialogButton(
          child: Text(
            "OK",
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

  static void onSuccessAlert(BuildContext context, String mes, String title) {
    Alert(
      context: context,
      type: AlertType.success,
      title: title,
      desc: mes,
      buttons: [
        DialogButton(
          child: Text(
            "OK",
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

  static Future dialogueSuccess(
      BuildContext context, String message, String title) {
    return AwesomeDialog(
        dismissOnTouchOutside: false,
        context: context,
        btnOkText: 'OK',
        dialogType: DialogType.SUCCES,
        animType: AnimType.LEFTSLIDE,
        title: title,
        desc: message,
        btnCancelOnPress: () {
          Navigator.pop(context);
        },
        btnOkOnPress: () {
          Navigator.pop(context);
          //Navigator.pop(context);
        }).show();
  }

  static Future dialogueFailure(
      BuildContext context, String message, String title) {
    return AwesomeDialog(
            context: context,
            btnOkText: 'OK',
            dialogType: DialogType.ERROR,
            animType: AnimType.LEFTSLIDE,
            title: title,
            desc: message,
            btnCancelOnPress: () {},
            btnOkOnPress: () {})
        .show();
  }

  static void myMethod(BuildContext context, VoidCallback method) {
    Navigator.pop(context);
    method();
  }

  static confirmationAlertWithMethod(
      BuildContext context, String mes, String title, VoidCallback method) {
    Alert(
      context: context,
      type: AlertType.info,
      title: title,
      desc: mes,
      buttons: [
        DialogButton(
          child: Text(
            "YES",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => myMethod(context, method),
          gradient: LinearGradient(colors: [
            Color.fromRGBO(116, 116, 191, 1.0),
            Color.fromRGBO(52, 138, 199, 1.0)
          ]),
        ),
        DialogButton(
          child: Text(
            "CANCEL",
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
