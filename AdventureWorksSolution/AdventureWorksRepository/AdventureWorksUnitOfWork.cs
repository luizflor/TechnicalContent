using AdventureWork.DAL;
using AdventureWorks.DTO;
using AdventureWorksEF.Model;
using System.Collections.Generic;
using System.Configuration;

namespace AdventureWorks.DAL
{
    public class AdventureWorksUnitOfWork
    {
        public static string ConnectionString
        {
            get
            {
                return ConfigurationManager.ConnectionStrings["AdventureWorks"].ConnectionString;
            }
        }

        public static IList<SalesDTO> GetSalesBySalesOrderId(int salesOrderId)
        {
            using (var ctx = new Adventureworks2017Context(ConnectionString))
            using (var repo = new AdventureWorksRepository(ctx))
            {
                return repo.GetSalesBySalesOrderId(43659);
            }
        }
    }
}
