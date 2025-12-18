using System;
using System.Collections.Generic;
namespace Models
{
    
    public class Commande
    {
        public int Id { get; set; }
        public DateTime DateCommande { get; set; } = DateTime.Now;
        public double Total { get; set; }
        public string Statut { get; set; } = "EN_ATTENTE";
        public required string TypeCommande { get; set; }
        public int UserId { get; set; }
        public required User User { get; set; }

        public List<CommandeItem> Items { get; set; } = new List<CommandeItem>();
    }
}

