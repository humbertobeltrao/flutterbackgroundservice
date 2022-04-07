import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {

  void startServiceInAndroid() async {
    if(Platform.isAndroid) {
      var method = MethodChannel("com.example.backgroundservice.messages");
      String data = await method.invokeMethod("startService");
      debugPrint(data);
    }
  }

  void stopServiceInAndroid() async {
    if(Platform.isAndroid) {
      var method = MethodChannel("com.example.backgroundservice.messages");
      String data = await method.invokeMethod("stopService");
      debugPrint(data);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(onPressed: () {startServiceInAndroid(); }, child: Text('Start music')),
          SizedBox(height: 10,),
          ElevatedButton(onPressed: () { stopServiceInAndroid(); }, child: Text('Stop music'))
        ],
      ),
    );
  }
}

