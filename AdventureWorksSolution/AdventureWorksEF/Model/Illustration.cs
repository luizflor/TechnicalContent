using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class Illustration
    {
        public Illustration()
        {
            ProductModelIllustrations = new HashSet<ProductModelIllustration>();
        }

        public int IllustrationId { get; set; }
        public string Diagram { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<ProductModelIllustration> ProductModelIllustrations { get; set; }
    }
}
