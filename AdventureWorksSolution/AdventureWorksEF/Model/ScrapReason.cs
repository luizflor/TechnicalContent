using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class ScrapReason
    {
        public ScrapReason()
        {
            WorkOrders = new HashSet<WorkOrder>();
        }

        public short ScrapReasonId { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<WorkOrder> WorkOrders { get; set; }
    }
}
