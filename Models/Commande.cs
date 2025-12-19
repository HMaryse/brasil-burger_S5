namespace Models
{
    public class Commande
    {
        public int Id { get; set; }

        public DateTime DateCommande { get; set; } = DateTime.Now;

        public double Total { get; set; }

        public string Statut { get; set; } = "EN_ATTENTE";

        public string TypeCommande { get; set; } = "SUR_PLACE";
       

        public int ClientId { get; set; }
        public required Client Client { get; set; }

        public List<CommandeItem> Items { get; set; } = new();
    }
}
