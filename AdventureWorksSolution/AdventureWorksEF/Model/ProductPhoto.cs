using System;
using System.Collections.Generic;

namespace AdventureWorksEF.Model
{
    public partial class ProductPhoto
    {
        public ProductPhoto()
        {
            ProductProductPhotoes = new HashSet<ProductProductPhoto>();
        }

        public int ProductPhotoId { get; set; }
        public byte[] ThumbNailPhoto { get; set; }
        public string ThumbnailPhotoFileName { get; set; }
        public byte[] LargePhoto { get; set; }
        public string LargePhotoFileName { get; set; }
        public DateTime ModifiedDate { get; set; }

        public ICollection<ProductProductPhoto> ProductProductPhotoes { get; set; }
    }
}
