using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AdventureWorksEF.Model
{
    public partial class Adventureworks2017Context : DbContext
    {
        public string ConnectionString { get; }
        public Adventureworks2017Context(string connectionString)
        {
            ConnectionString = connectionString;
        }        
    }
}
