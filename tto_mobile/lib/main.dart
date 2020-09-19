import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:tto_mobile/pages/tto_home_page.dart';
import 'package:tto_mobile/providers/traffic_details_provider.dart';

void main() {
  runApp(TTOApp());
}

class TTOApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => TrafficDetailsProvider(),
      builder: (context, child) {
        return MaterialApp(
          title: 'Travel Time Optimizer',
          theme: ThemeData(
            primarySwatch: Colors.orange,
            visualDensity: VisualDensity.adaptivePlatformDensity,
          ),
          debugShowCheckedModeBanner: false,
          home: TTOHomePage(),
        );
      },
    );
  }
}
