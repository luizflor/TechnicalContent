﻿using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class SalesPerson
    {
        public SalesPerson()
        {
            SalesOrderHeaders = new HashSet<SalesOrderHeader>();
            SalesPersonQuotaHistories = new HashSet<SalesPersonQuotaHistory>();
            SalesTerritoryHistories = new HashSet<SalesTerritoryHistory>();
            Stores = new HashSet<Store>();
        }

        public int BusinessEntityId { get; set; }
        public int? TerritoryId { get; set; }
        public decimal? SalesQuota { get; set; }
        public decimal Bonus { get; set; }
        public decimal CommissionPct { get; set; }
        public decimal SalesYtd { get; set; }
        public decimal SalesLastYear { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public Employee BusinessEntity { get; set; }
        public SalesTerritory Territory { get; set; }
        public ICollection<SalesOrderHeader> SalesOrderHeaders { get; set; }
        public ICollection<SalesPersonQuotaHistory> SalesPersonQuotaHistories { get; set; }
        public ICollection<SalesTerritoryHistory> SalesTerritoryHistories { get; set; }
        public ICollection<Store> Stores { get; set; }
    }
}
