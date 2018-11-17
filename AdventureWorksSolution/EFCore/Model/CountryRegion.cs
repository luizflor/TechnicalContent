using System;
using System.Collections.Generic;

namespace EFCore.Model
{
    public partial class CountryRegion
    {
        public CountryRegion()
        {
            StateProvinces = new HashSet<StateProvince>();
        }

        public string CountryRegionCode { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<StateProvince> StateProvinces { get; set; }
    }
}
