namespace ViewModels
{
    public class PanierItemVM
    {
        public int Id { get; set; }
        public string Nom { get; set; } = "";
        public double Prix { get; set; }
        public int Quantite { get; set; } = 1;
        public string ImageUrl { get; set; } = "";
        public string Type { get; set; } = "";
    }

    public class PanierViewModel
    {
        public List<PanierItemVM> Items { get; set; } = new();

        public int TotalArticles => Items.Sum(i => i.Quantite);
        public double Total => Items.Sum(i => i.Prix * i.Quantite);

        public void AddItem(int id, string nom, double prix, string image, string type)
        {
            var exist = Items.FirstOrDefault(i => i.Id == id && i.Type == type);
            if (exist != null)
                exist.Quantite++;
            else
                Items.Add(new PanierItemVM
                {
                    Id = id,
                    Nom = nom,
                    Prix = prix,
                    ImageUrl = image,
                    Type = type
                });
        }
    }
}
