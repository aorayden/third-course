# an = a1 + d(n-1),
firstNumber = int(input("Enter first number: "))
secondNumber = int(input("Enter second number: "))
thirdNumber = int(input("Enter third number: "))

firstResult = firstNumber - secondNumber
secondResult = secondNumber - thirdNumber

if firstResult == secondResult:
    print('Да')
else: print('Нет')