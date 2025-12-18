

namespace Models
{
    public class CommandeItem
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public required Commande Commande { get; set; }
        public int? BurgerId { get; set; }
        public Burger? Burger { get; set; }   
        public int? MenuId { get; set; }
        public Menu? Menu { get; set; }       
        public int Quantite { get; set; }
        public double PrixUnitaire { get; set; }
    }

}

