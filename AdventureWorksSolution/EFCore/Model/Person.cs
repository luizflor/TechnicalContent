﻿using System;
using System.Collections.Generic;

namespace EFCore.Model
{
    public partial class Person
    {
        public Person()
        {
            BusinessEntityContacts = new HashSet<BusinessEntityContact>();
            EmailAddresses = new HashSet<EmailAddress>();
            PersonPhones = new HashSet<PersonPhone>();
        }

        public int BusinessEntityId { get; set; }
        public string PersonType { get; set; }
        public bool NameStyle { get; set; }
        public string Title { get; set; }
        public string FirstName { get; set; }
        public string MiddleName { get; set; }
        public string LastName { get; set; }
        public string Suffix { get; set; }
        public int EmailPromotion { get; set; }
        public string AdditionalContactInfo { get; set; }
        public string Demographics { get; set; }
        public Guid Rowguid { get; set; }
        public DateTime ModifiedDate { get; set; }

        public BusinessEntity BusinessEntity { get; set; }
        public Employee Employee { get; set; }
        public Password Password { get; set; }
        public ICollection<BusinessEntityContact> BusinessEntityContacts { get; set; }
        public ICollection<EmailAddress> EmailAddresses { get; set; }
        public ICollection<PersonPhone> PersonPhones { get; set; }
    }
}
