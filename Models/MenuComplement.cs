namespace Models
{
    public class MenuComplement
    {
        public int Id { get; set; }

        public int MenuId { get; set; }
        public required Menu Menu { get; set; }

        public int ComplementId { get; set; }
        public required Complement Complement { get; set; }

        public int Quantite { get; set; } = 1;
    }
}

