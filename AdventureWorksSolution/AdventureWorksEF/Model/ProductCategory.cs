using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class ProductCategory
    {
        public ProductCategory()
        {
            ProductSubcategories = new HashSet<ProductSubcategory>();
        }

        public int ProductCategoryId { get; set; }
        public string Name { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<ProductSubcategory> ProductSubcategories { get; set; }
    }
}
