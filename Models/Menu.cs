using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Models
{
    
    public class Menu
    {
        public int Id { get; set; }
        public required string Nom { get; set; }
        public double Prix { get; set; }
        public required string ImageUrl { get; set; }
        public string Etat { get; set; } = "DISPONIBLE";
        
        public List<MenuDetail> MenuDetails { get; set; } = new List<MenuDetail>();
        public List<MenuComplement> MenuComplements { get; set; } = new List<MenuComplement>();
    }
}

