import 'package:flutter/material.dart';
class GradientCard extends StatelessWidget {

  final String name;
  final String value;
  final IconData icon;
  final Color colorData;
  final bool  valueColorChanged;
  Map mydata = new Map();
  double _width;
  GradientCard({
    Key key,
    @required this.name,
    @required this.value,
    @required this.icon,
    @required this.colorData,
    @required this.valueColorChanged,

  }) : super(key: key);


  @override
  Widget build(BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    return SingleChildScrollView(
        child:Container(
            child :Container(
              width: _width,
              decoration: new BoxDecoration(
                  borderRadius: new BorderRadius.only(
                    topLeft:  const  Radius.circular(20.0),
                    topRight: const  Radius.circular(20.0),
                    bottomLeft: const  Radius.circular(20.0),
                    bottomRight: const  Radius.circular(20.0),
                  ),
                gradient: new LinearGradient(
                    colors: [
                     Color(0xFF8E2DE2),
                                              Color(0xFF4A00E0),
                    ],
                    begin: const FractionalOffset(0.0, 0.0),
                    end: const FractionalOffset(1.0, 1.0),
                    stops: [0.0, 1.0],
                    tileMode: TileMode.clamp),
              ),
              margin: EdgeInsets.only(left:18,top: 8,right:7),
              //shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
              //borderOnForeground: false,
              //elevation: 1.0,
              child: InkWell(
                onTap: () {
                  if (icon == Icons.call) {
                    print(value);
                  }
                  else if (icon == Icons.map) {
                  }
                },
                child: Container(
                  padding: EdgeInsets.only(top: 5.0, bottom: 15.0,),
                  decoration: BoxDecoration(
                      color: Colors.transparent,
                      borderRadius: BorderRadius.circular(25.0)),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.only(left: 10.0),
                        child: IconButton(
                          icon: Icon(icon),
                          onPressed: () {
                            if (icon == Icons.call) {
                              print(value);
                            }
                          },
                        ),
                      ),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: <Widget>[
                                Text(
                                    name,
                                    textAlign: TextAlign.left,
                                    style: TextStyle(
                                      fontSize: 17.0,
                                      fontWeight: FontWeight.bold,
                                      color: colorData,
                                    ),
                                  ),
                                

                               Container(
                                 margin: EdgeInsets.only(right:30),
                                child:  Text("$value",
                                    style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w900,
                                      color: Colors.white,
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
            )
        )
    );
  }
}
