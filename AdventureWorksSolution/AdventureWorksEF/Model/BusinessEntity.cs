using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class BusinessEntity
    {
        public BusinessEntity()
        {
            BusinessEntityAddresses = new HashSet<BusinessEntityAddress>();
            BusinessEntityContacts = new HashSet<BusinessEntityContact>();
        }

        public int BusinessEntityId { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public Person Person { get; set; }
        public Store Store { get; set; }
        public Vendor Vendor { get; set; }
        public ICollection<BusinessEntityAddress> BusinessEntityAddresses { get; set; }
        public ICollection<BusinessEntityContact> BusinessEntityContacts { get; set; }
    }
}
