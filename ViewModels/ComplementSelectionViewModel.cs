using Models;

namespace ViewModels
{
    public class ComplementSelectionViewModel
    {
        public int ArticleId { get; set; }       
        public string Type { get; set; } = "";   

        public List<Complement> Complements { get; set; } = new();
        public List<int> SelectedComplementIds { get; set; } = new();
    }
}
