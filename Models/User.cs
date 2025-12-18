using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Models
{
    public class User
    {
        public int Id { get; set; }
        [MaxLength(100)]
        public required string NomComplet { get; set; }

        [EmailAddress]
        [MaxLength(150)]
        public required string Email { get; set; }
        public required string MotDePasse { get; set; }

        [Required]
        public string Role { get; set; } = "CLIENT"; 
       
       public List<Commande> Commandes { get; set; } = new List<Commande>();
    }
}
