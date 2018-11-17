using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class PhoneNumberType
    {
        public PhoneNumberType()
        {
            PersonPhones = new HashSet<PersonPhone>();
        }

        public int PhoneNumberTypeId { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<PersonPhone> PersonPhones { get; set; }
    }
}
