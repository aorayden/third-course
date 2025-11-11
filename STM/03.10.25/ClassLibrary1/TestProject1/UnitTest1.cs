using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TestProject1
{
    [TestClass]
    public class Class1Tests
    {
        private ClassLibrary1.Class1 _c;

        [TestInitialize]
        public void Setup()
        {
            _c = new ClassLibrary1.Class1();
        }

        [TestMethod]
        public void Sum_ReturnsExpected()
        {
            Assert.AreEqual(30, _c.Sum(10, 20));
        }

        [TestMethod]
        public void Subtract_ReturnsExpected()
        {
            Assert.AreEqual(-10, _c.Subtract(10, 20));
            Assert.AreEqual(10, _c.Subtract(20, 10));
        }

        [TestMethod]
        public void Multiply_ReturnsExpected()
        {
            Assert.AreEqual(200, _c.Multiply(10, 20));
            Assert.AreEqual(0, _c.Multiply(0, 5));
        }

        [TestMethod]
        public void Divide_ReturnsExpected()
        {
            Assert.AreEqual(2.0, _c.Divide(10, 5));
            Assert.AreEqual(0.5, _c.Divide(1, 2));
        }

        [TestMethod]
        public void Divide_ByZero_Throws()
        {
            try
            {
                _c.Divide(10, 0);
                Assert.Fail("Expected DivideByZeroException was not thrown.");
            }
            catch (System.DivideByZeroException)
            {
                // ожидаемое исключение
            }
        }

        [TestMethod]
        public void Max_ReturnsExpected()
        {
            Assert.AreEqual(20, _c.Max(10, 20));
            Assert.AreEqual(10, _c.Max(10, 10));
        }

        [TestMethod]
        public void IsEven_ReturnsExpected()
        {
            Assert.IsTrue(_c.IsEven(2));
            Assert.IsFalse(_c.IsEven(3));
            Assert.IsTrue(_c.IsEven(0));
            Assert.IsFalse(_c.IsEven(-1));
            Assert.IsTrue(_c.IsEven(-2));
        }
    }
}