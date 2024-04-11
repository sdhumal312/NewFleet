import 'package:fleetop/Dashboard/summarybar.dart';
import 'package:flutter/cupertino.dart';
import 'package:charts_flutter/flutter.dart' as charts;
import 'package:flutter/material.dart';

import '../appTheme.dart';

class SummarySeries extends StatelessWidget {
  final List<SummaryBar> data;

  const SummarySeries({Key key, this.data}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    List<charts.Series<SummaryBar, String>> series = [
      charts.Series(
          id: 'SummaryBar',
          data: data,
          labelAccessorFn: (SummaryBar series, _) => series.summaryName +" : "+series.summaryCount ,
          domainFn: (SummaryBar series, _) => series.summaryName,
          measureFn: (SummaryBar series, _) => num.parse(series.summaryCount),
          colorFn: (SummaryBar series, _) => series.barColor)
    ];
    return (Container(
      height: 400,
      padding: EdgeInsets.all(8),
      child:  Padding(
          padding: const EdgeInsets.all(8),
          child: Column(
            children: <Widget>[
              Text("Summary Status",
                  style: new TextStyle(
                    fontSize: 22,
                    color: AppTheme.darkText,
                    fontWeight: FontWeight.w700,
                  )),
              Expanded(
                child: charts.BarChart(
                  series, 
                  animate: true,
                  vertical: false,
                  barRendererDecorator: new charts.BarLabelDecorator<String>(),
                domainAxis: new charts.OrdinalAxisSpec(renderSpec: new charts.NoneRenderSpec()),
                  ),
              )
            ],
          ),
        ),
      
      decoration: new BoxDecoration(
        boxShadow: [
          new BoxShadow(
            color: Colors.grey,
            blurRadius: 50.0,
          ),
        ],
        gradient: LinearGradient(
          colors: [Colors.lightGreen, Colors.white],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
        borderRadius: BorderRadius.only(
          bottomRight: Radius.circular(8.0),
          bottomLeft: Radius.circular(30.0),
          topLeft: Radius.circular(8.0),
          topRight: Radius.circular(30.0),
        ),
      ),
    ));
  }
}

class PieChartSummary extends StatelessWidget {
  final List<SummaryBar> data;
  final String title;

  const PieChartSummary({Key key, this.data,this.title}) : super(key: key);
  @override
  Widget build(BuildContext context) {

    List<charts.Series<SummaryBar, String>> series = [
      charts.Series(
          id: 'PieChartSummary',
          data: data,
          labelAccessorFn: (SummaryBar series, _) => series.summaryName +" : "+series.summaryCount,
          domainFn: (SummaryBar series, _) => series.summaryName,
          measureFn: (SummaryBar series, _) => num.parse(series.summaryCount),
          colorFn: (SummaryBar series, _) => series.barColor)
    ];
    return (Container(
      height: 400,
      padding: EdgeInsets.all(8),
      child:  Padding(
          padding: const EdgeInsets.all(8),
          child: Column(
            children: <Widget>[
              Text(title,
                  style: new TextStyle(
                    fontSize: 22,
                    color: AppTheme.darkText,
                    fontWeight: FontWeight.w700,
                  )),
                SizedBox(height: 10),
              Expanded(
                child: charts.PieChart(series,
                defaultRenderer: charts.ArcRendererConfig(
                  arcRendererDecorators: [new charts.ArcLabelDecorator()],
                  arcWidth: 80,
                ),
                behaviors: [new charts.DatumLegend()],
                 animate: true),
              )
            ],
          ),
        ),
              decoration: new BoxDecoration(
        boxShadow: [
          new BoxShadow(
            color: Colors.grey,
            blurRadius: 50.0,
          ),
        ],
        gradient: LinearGradient(
          colors: [Colors.grey, Colors.white],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
        borderRadius: BorderRadius.only(
          bottomRight: Radius.circular(8.0),
          bottomLeft: Radius.circular(50.0),
          topLeft: Radius.circular(8.0),
          topRight: Radius.circular(50.0),
        ),
      ),
    ));
  }
}
