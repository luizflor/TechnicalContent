﻿using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class Location
    {
        public Location()
        {
            ProductInventories = new HashSet<ProductInventory>();
            WorkOrderRoutings = new HashSet<WorkOrderRouting>();
        }

        public short LocationId { get; set; }
        public string Name { get; set; }
        public decimal CostRate { get; set; }
        public decimal Availability { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<ProductInventory> ProductInventories { get; set; }
        public ICollection<WorkOrderRouting> WorkOrderRoutings { get; set; }
    }
}
