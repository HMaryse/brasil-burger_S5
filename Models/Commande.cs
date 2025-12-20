namespace Models
{
   public class Commande
    {
        public int Id { get; set; }
        public int ClientId { get; set; }

        public DateTime DateCommande { get; set; }
        public StatutCommande Statut { get; set; } = StatutCommande.EN_COURS;
        public ModeConsommation ModeConsommation { get; set; }
        public string? Adresse { get; set; }
        public int? LivreurId { get; set; }
        public int? ZoneId { get; set; }

        public bool EstPaye { get; set; } = false;

        public List<CommandeItem> Items { get; set; } = new();
    }
}