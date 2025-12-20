using Models;
using Data;
using System.Collections.Generic;


namespace Services
{
    public class ComplementService : IComplementService
    {
        private readonly AppDbContext _context;

        public ComplementService(AppDbContext context)
        {
            _context = context;
        }

        public List<Complement> GetAll()
        {
            return _context.Complements
                .Where(c => c.Etat == EtatType.DISPONIBLE)
                .ToList();
        }

        public Complement GetById(int id)
        {
            return _context.Complements.First(c => c.Id == id);
        }
    }
}
