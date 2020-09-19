import 'package:charts_flutter/flutter.dart' as charts;
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quiver/iterables.dart';
import 'package:tto_mobile/providers/traffic_details_provider.dart';

class TTOHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    TrafficDetailsProvider trafficDetailsProvider = context.watch<TrafficDetailsProvider>();

    final data = zip([trafficDetailsProvider.summary.timestamps, trafficDetailsProvider.summary.durations]).toList();

    return Scaffold(
      appBar: AppBar(
        title: Text("Travel Time Optimizer"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('Welcome to TTO'),
            Text('${trafficDetailsProvider.summary.timestamps.length}'),
            Container(
              width: 1000,
              height: 500,
              child: charts.TimeSeriesChart(
                [
                  new charts.Series<List<Object>, DateTime>(
                    id: "1",
                    data: data,
                    domainFn: (List<Object> traffic, _) => traffic[0] as DateTime,
                    measureFn: (List<Object> traffic, _) => traffic[1] as int,
                  )
                ],
                // Optionally pass in a [DateTimeFactory] used by the chart. The factory
                // should create the same type of [DateTime] as the data provided. If none
                // specified, the default creates local date time.
                dateTimeFactory: const charts.LocalDateTimeFactory(),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
