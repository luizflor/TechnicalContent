using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class Currency
    {
        public Currency()
        {
            CountryRegionCurrencies = new HashSet<CountryRegionCurrency>();
            CurrencyRateFromCurrencyCodeNavigations = new HashSet<CurrencyRate>();
            CurrencyRateToCurrencyCodeNavigations = new HashSet<CurrencyRate>();
        }

        public string CurrencyCode { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<CountryRegionCurrency> CountryRegionCurrencies { get; set; }
        public ICollection<CurrencyRate> CurrencyRateFromCurrencyCodeNavigations { get; set; }
        public ICollection<CurrencyRate> CurrencyRateToCurrencyCodeNavigations { get; set; }
    }
}
