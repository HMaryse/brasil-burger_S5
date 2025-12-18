using System.ComponentModel.DataAnnotations;

namespace Models
{
    public class Burger
    {
        public int Id { get; set; }
        public required string Nom { get; set; }
        public double Prix { get; set; }
        public required string ImageUrl { get; set; }

        public string Etat { get; set; } = "DISPONIBLE";
    }
}
    

