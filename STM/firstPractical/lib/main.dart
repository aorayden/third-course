import 'package:flutter/material.dart';
import 'package:first_practical/activities/main_screen.dart';
import 'package:first_practical/activities/auth_screen.dart';
import 'package:first_practical/activities/register_screen.dart';

void main() => runApp(MaterialApp(
  debugShowCheckedModeBanner: false,
  initialRoute: '/',
  routes: {
    '/': (context) => const AuthScreen(),
    '/registerScreen': (context) => const RegisterScreen(),
    '/mainScreen': (context) => const MainScreen(),
  },
));