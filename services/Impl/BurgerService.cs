using Data;
using Models;

namespace Services
{
    public class BurgerService : IBurgerService
    {
        private readonly AppDbContext _context;

        public BurgerService(AppDbContext context)
        {
            _context = context;
        }

        public List<Burger> GetAllDisponibles()
        {
            return _context.Burgers
                .Where(b => b.Etat == EtatType.DISPONIBLE)
                .ToList();
        }

        public Burger? GetById(int id)
        {
            return _context.Burgers.FirstOrDefault(b => b.Id == id);
        }
    }
}
