using Models;

namespace ViewModels
{
    public class CatalogueViewModel
    {
        public List<Burger> Burgers { get; set; } = new();
        public List<Menu> Menus { get; set; } = new();

        public string Type { get; set; } = "ALL";

        public int Page { get; set; }
        public int TotalPages { get; set; }
    }
}
