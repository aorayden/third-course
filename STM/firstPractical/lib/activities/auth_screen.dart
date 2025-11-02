import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class AuthScreen extends StatelessWidget {
  const AuthScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: EdgeInsets.all(25),
          child: Align(
            alignment: Alignment.center,
            child: Column(
                children: [
                  Text(
                      'Добрый день!',
                      style: TextStyle(

                      )
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Введите логин и пароль, чтобы войти',
                    style: TextStyle(

                    )
                  ),
                  SizedBox(height: 32),
                  Text(
                      'Логин',
                      style: TextStyle(

                      )
                  ),
                  SizedBox(height: 8),
                  SizedBox(
                    width: 256,
                    child: TextField(
                      autofocus: true,
                      enableSuggestions: false,
                      onChanged: (text) {
                        if (kDebugMode) {
                          print("[LOGIN] Введено: $text.");
                        }
                      }
                    ),
                  ),
                  SizedBox(height: 16),
                  Text(
                      'Пароль',
                      style: TextStyle(

                      )
                  ),
                  SizedBox(height: 8),
                  SizedBox(
                    width: 256,
                    child: TextField(
                      enableSuggestions: false,
                      onChanged: (text) {
                        if (kDebugMode) {
                          print("[PASSWORD] Введено: $text.");
                        }
                      }
                    )
                  ),
                  SizedBox(height: 16),
                  Align(
                    alignment: Alignment.topRight,
                    child: TextButton(
                        onPressed: () {},
                        child: Text(
                            'Забыли пароль?',
                            style: TextStyle(

                            )
                        )
                    )
                  ),
                  FilledButton(
                    onPressed: () {},
                    child: Text(
                      'Войти',
                      style: TextStyle(

                      )
                    )
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        'Не зарегистрированы?'
                      ),
                      TextButton(
                        onPressed: () {},
                        child: Text(
                          'Зарегистрируйтесь',
                          style: TextStyle(

                          )
                        )
                      )
                    ],
                  )
                ]
            )
          )
        )
      )
    );
  }
}