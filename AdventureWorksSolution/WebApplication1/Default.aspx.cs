using AdventureWork.DAL;
using AdventureWorks.DAL;
using AdventureWorksEF.Model;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebApplication1
{
    public partial class _Default : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void GetSales_Click(object sender, EventArgs e)
        {
            //var conn = ConfigurationManager.ConnectionStrings["AdventureWorks"].ConnectionString;
            //using(var ctx = new Adventureworks2017Context(conn))
            //using (var repo = new AdventureWorksRepository(ctx))
            //{
            //    CountLabel.Text = repo.GetSalesBySalesOrderId(43659).Count.ToString();

            //}
            CountLabel.Text = AdventureWorksUnitOfWork.GetSalesBySalesOrderId(43659).Count.ToString();
        }
    }
}