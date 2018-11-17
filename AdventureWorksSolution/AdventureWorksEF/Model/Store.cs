using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class Store
    {
        public Store()
        {
            Customers = new HashSet<Customer>();
        }

        public int BusinessEntityId { get; set; }
        public string Name { get; set; }
        public int? SalesPersonId { get; set; }
        public string Demographics { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public BusinessEntity BusinessEntity { get; set; }
        public SalesPerson SalesPerson { get; set; }
        public ICollection<Customer> Customers { get; set; }
    }
}
