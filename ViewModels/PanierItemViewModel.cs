namespace ViewModels
{
    public class PanierItemViewModel
    {
        public int ArticleId { get; set; }
        public string Type { get; set; } = ""; // BURGER ou MENU
        public string Nom { get; set; } = "";
        public double Prix { get; set; }
        public int Quantite { get; set; } = 1;
    }
}
