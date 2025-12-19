using Models;
using System.Collections.Generic;

namespace ViewModels
{
    public class ChoixCommandeViewModel
    {
        public int ArticleId { get; set; }
        public required string Type { get; set; } 
        public Burger? Burger { get; set; }
        public Menu? Menu { get; set; }

        public List<Complement> Complements { get; set; } = new();

        public List<int> ComplementIds { get; set; } = new();

        public int Quantite { get; set; } = 1;
    }
}
