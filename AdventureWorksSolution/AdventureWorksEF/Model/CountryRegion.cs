using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class CountryRegion
    {
        public CountryRegion()
        {
            CountryRegionCurrencies = new HashSet<CountryRegionCurrency>();
            SalesTerritories = new HashSet<SalesTerritory>();
            StateProvinces = new HashSet<StateProvince>();
        }

        public string CountryRegionCode { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<CountryRegionCurrency> CountryRegionCurrencies { get; set; }
        public ICollection<SalesTerritory> SalesTerritories { get; set; }
        public ICollection<StateProvince> StateProvinces { get; set; }
    }
}
