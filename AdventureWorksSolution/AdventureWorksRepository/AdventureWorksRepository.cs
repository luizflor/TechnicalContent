using AdventureWorks.DTO;
using AdventureWorksEF;
using AdventureWorksEF.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AdventureWork.DAL
{
    public interface IAdventureWorksRepository: IDisposable
    {
        IList<SalesDTO> GetSalesBySalesOrderId(int salesOrderId);
        Product GetProductByID(int productID);
    }

    public class RepositoryFactory
    {
        public static AdventureWorksRepository GetRepository()
        {
            var ctx = new Adventureworks2017Context();
            {
                return new AdventureWorksRepository(ctx);
            }
        }
    }
    public class AdventureWorksRepository: IAdventureWorksRepository, IDisposable
    {
        private Adventureworks2017Context context;
        public AdventureWorksRepository(Adventureworks2017Context context)
        {
            this.context = context;
        }

        #region IDisposable Support
        private bool disposedValue = false; // To detect redundant calls
      

        protected virtual void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    // TODO: dispose managed state (managed objects).
                    context.Dispose();
                }

                // TODO: free unmanaged resources (unmanaged objects) and override a finalizer below.
                // TODO: set large fields to null.

                disposedValue = true;
            }
        }

        // TODO: override a finalizer only if Dispose(bool disposing) above has code to free unmanaged resources.
        // ~AdventureWorksRepository() {
        //   // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
        //   Dispose(false);
        // }

        // This code added to correctly implement the disposable pattern.
        public void Dispose()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(true);
            // TODO: uncomment the following line if the finalizer is overridden above.
            GC.SuppressFinalize(this);
        }


        #endregion
        public IList<SalesDTO> GetSalesBySalesOrderId(int salesOrderId)
        {
            return DBRead.GetSalesBySalesOrderId(context, salesOrderId);
        }

        public Product GetProductByID(int productID)
        {
            return context.Products.First(x=> x.ProductId == productID);
        }
    }
}
