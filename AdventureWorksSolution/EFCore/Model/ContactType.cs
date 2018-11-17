using System;
using System.Collections.Generic;

namespace EFCore.Model
{
    public partial class ContactType
    {
        public ContactType()
        {
            BusinessEntityContacts = new HashSet<BusinessEntityContact>();
        }

        public int ContactTypeId { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<BusinessEntityContact> BusinessEntityContacts { get; set; }
    }
}
