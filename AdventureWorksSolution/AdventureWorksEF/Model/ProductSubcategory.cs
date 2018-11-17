﻿using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class ProductSubcategory
    {
        public ProductSubcategory()
        {
            Products = new HashSet<Product>();
        }

        public int ProductSubcategoryId { get; set; }
        public int ProductCategoryId { get; set; }
        public string Name { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ProductCategory ProductCategory { get; set; }
        public ICollection<Product> Products { get; set; }
    }
}
