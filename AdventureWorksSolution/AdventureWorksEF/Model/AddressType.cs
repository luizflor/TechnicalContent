﻿using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class AddressType
    {
        public AddressType()
        {
            BusinessEntityAddresses = new HashSet<BusinessEntityAddress>();
        }

        public int AddressTypeId { get; set; }
        public string Name { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<BusinessEntityAddress> BusinessEntityAddresses { get; set; }
    }
}
