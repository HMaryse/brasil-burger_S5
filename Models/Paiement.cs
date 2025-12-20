namespace Models
{
    public class Paiement
{
    public int Id { get; set; }

    public int CommandeId { get; set; }

    public decimal Montant { get; set; }

    public DateTime DatePaiement { get; set; }

    public ModePaiement ModePaiement { get; set; }

    public StatutPaiement StatutPaiement { get; set; }
}

}