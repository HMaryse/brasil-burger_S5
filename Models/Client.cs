using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("client")]
    public class Client
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Required]
        [Column("nom_complet")]
        public string NomComplet { get; set; } = string.Empty;

        [Required]
        [Column("telephone")]
        public string Telephone { get; set; } = string.Empty;

        [Column("adresse")]
        public string? Adresse { get; set; }

        
        [Required]
        [Column("password")]
        public string MotDePasse { get; set; } = string.Empty;
    }
}
