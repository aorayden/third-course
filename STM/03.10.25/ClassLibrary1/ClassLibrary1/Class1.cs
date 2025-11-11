namespace ClassLibrary1
{
    public class Class1
    {
        public int Sum(int x, int y)
        {
            return x + y;
        }

        // Вычитание
        public int Subtract(int x, int y)
        {
            return x - y;
        }

        // Умножение
        public int Multiply(int x, int y)
        {
            return x * y;
        }

        // Деление. Бросает DivideByZeroException при делении на 0.
        public double Divide(int x, int y)
        {
            if (y == 0)
                throw new System.DivideByZeroException();
            return (double)x / y;
        }

        // Максимум из двух чисел
        public int Max(int a, int b)
        {
            return a >= b ? a : b;
        }

        // Чётность
        public bool IsEven(int x)
        {
            return x % 2 == 0;
        }

    }
}
