using System.ComponentModel.DataAnnotations.Schema;

public class CommandeItem
{
    public int Id { get; set; }
    public int CommandeId { get; set; }
    public int? BurgerId { get; set; }
    public int? MenuId { get; set; }
    public int Quantite { get; set; }
    public decimal PrixTotal { get; set; }

    [NotMapped] 
    public decimal PrixUnitaire { get; set; }
}
