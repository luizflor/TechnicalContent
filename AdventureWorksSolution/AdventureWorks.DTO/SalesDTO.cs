using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AdventureWorks.DTO
{
    public class SalesDTO
    {
        public int ProductId { get; set; }
        public string Name { get; set; }
        public decimal SubTotal { get; set; }
        public decimal TaxAmt { get; set; }
        public decimal Freight { get; set; }
        public decimal TotalDue { get; set; }
        public decimal UnitPrice { get; set; }
        public decimal LineTotal { get; set; }
        public short OrderQty { get; set; }
    }
}
