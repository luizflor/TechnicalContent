using System;
using AdventureWork.DAL;
using AdventureWorksEF.Model;
using Microsoft.EntityFrameworkCore;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace UnitTestEF
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestMethod1()
        {
            Assert.IsTrue(0 == 0);
        }
        private Adventureworks2017Context GetMockContext()
        {
            var options = new DbContextOptionsBuilder<Adventureworks2017Context>()
              .UseInMemoryDatabase(databaseName: "Add_writes_to_database")
              .Options;
            var context = new Adventureworks2017Context(options);

            context.Products.Add(new Product {ProductId =1,Name="Product 1 "});

            context.SaveChanges();
            return context;
        }
        [TestMethod]
        public void TestEF()
        {
            using (IAdventureWorksRepository repo = new AdventureWorksRepository(GetMockContext()))
            {
                var product = repo.GetProductByID(1);
                Assert.IsTrue(product.ProductId == 1);
            }
        }
    }
}
