using AdventureWorks.DTO;
using AdventureWorksEF.Model;
using System.Collections.Generic;
using System.Linq;

namespace AdventureWorksEF
{
    public class DBRead
    {
        public static IList<SalesDTO> GetSalesBySalesOrderId(Adventureworks2017Context ctx, int salesOrderId)
        {
            var sales = from sod in ctx.SalesOrderDetails
                        join soh in ctx.SalesOrderHeaders on sod.SalesOrderId equals soh.SalesOrderId
                        join p in ctx.Products on sod.ProductId equals p.ProductId
                        where soh.SalesOrderId == salesOrderId
                        select new SalesDTO
                        {
                            ProductId = p.ProductId,
                            Name = p.Name,
                            SubTotal = soh.SubTotal,
                            TaxAmt = soh.TaxAmt,
                            Freight = soh.Freight,
                            TotalDue = soh.TotalDue,
                            OrderQty = sod.OrderQty,
                            UnitPrice = sod.UnitPrice,
                            LineTotal = sod.LineTotal
                        };
            return sales.ToList();
        }
    }
}
