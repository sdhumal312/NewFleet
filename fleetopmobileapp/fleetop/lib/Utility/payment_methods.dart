import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class PaymentMethods extends StatelessWidget {
  final String name;
  final String amount;
  final IconData icon;
  final int branchId;
  final Color iconColor;
  double lang, latt;
  Map mydata = new Map();
  PaymentMethods(
      {Key key,
      @required this.name,
      @required this.amount,
      @required this.icon,
      @required this.branchId,
      this.iconColor})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2.0,
      child: InkWell(
        onTap: () {},
        child: Container(
          padding: EdgeInsets.only(top: 5.0, bottom: 15.0, right: 05.0),
          decoration: BoxDecoration(
              color: Colors.white, borderRadius: BorderRadius.circular(25.0)),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Padding(
                padding: EdgeInsets.only(left: 03.0, right: 03.0),
                child: IconButton(
                  icon: Icon(
                    icon,
                    color: iconColor != null ? iconColor : Colors.black,
                  ),
                  onPressed: () {
                    if (icon == Icons.call) {}
                  },
                ),
              ),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Column(
                      children: <Widget>[
                        Align(
                          alignment: Alignment.centerLeft,
                          child: Text(
                            name,
                            textAlign: TextAlign.left,
                            style: TextStyle(
                              fontSize: 15.0,
                              fontWeight: FontWeight.bold,
                              color: Colors.cyan,
                            ),
                          ),
                        ),
                        Align(
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "$amount",
                            style: TextStyle(
                              fontSize: 14.0,
                              fontWeight: FontWeight.w900,
                              color: Colors.grey[800],
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
