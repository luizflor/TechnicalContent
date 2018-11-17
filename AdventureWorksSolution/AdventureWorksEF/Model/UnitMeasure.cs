using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class UnitMeasure
    {
        public UnitMeasure()
        {
            BillOfMaterials = new HashSet<BillOfMaterial>();
            ProductSizeUnitMeasureCodeNavigations = new HashSet<Product>();
            ProductVendors = new HashSet<ProductVendor>();
            ProductWeightUnitMeasureCodeNavigations = new HashSet<Product>();
        }

        public string UnitMeasureCode { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<BillOfMaterial> BillOfMaterials { get; set; }
        public ICollection<Product> ProductSizeUnitMeasureCodeNavigations { get; set; }
        public ICollection<ProductVendor> ProductVendors { get; set; }
        public ICollection<Product> ProductWeightUnitMeasureCodeNavigations { get; set; }
    }
}
