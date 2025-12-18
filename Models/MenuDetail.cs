namespace Models
{
    
    public class MenuDetail
    {
        public int Id { get; set; }
        public int MenuId { get; set; }
        public required Menu Menu { get; set; }
        public int BurgerId { get; set; }
        public required Burger Burger { get; set; }
        public int Quantite { get; set; } = 1;
    }
}

