using AdventureWork.DAL;
using AdventureWorksEF.Model;
using Microsoft.EntityFrameworkCore;
using System;
using Xunit;

namespace XUnitTestProject1
{
    public class UnitTest1
    {
        [Fact]
        public void Test1()
        {
            Assert.True(0 == 0);
        }
        private Adventureworks2017Context GetMockContext()
        {
            var options = new DbContextOptionsBuilder<Adventureworks2017Context>()
              .UseInMemoryDatabase(databaseName: "Add_writes_to_database")
              .Options;
            var context = new Adventureworks2017Context(options);

            context.Products.Add(new Product { ProductId = 1, Name = "Product 1 " });

            context.SaveChanges();
            return context;
        }
        [Fact]
        public void TestEFwithXUnit()
        {
            using (IAdventureWorksRepository repo = new AdventureWorksRepository(GetMockContext()))
            {
                var product = repo.GetProductByID(1);
                Assert.True(product.ProductId == 1);
            }
        }
    }
}
