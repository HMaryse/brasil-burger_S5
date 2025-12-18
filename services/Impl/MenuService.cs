using Data;
using Models;


namespace Services
{
    public class MenuService : IMenuService
    {
        private readonly AppDbContext _context;

        public MenuService(AppDbContext context)
        {
            _context = context;
        }

        public List<Menu>  GetAllDisponibles()
        {
            return _context.Menus
                .Where(m => m.Etat == "DISPONIBLE")
                .ToList();
        }

        public Menu? GetById(int id)
        {
            return _context.Menus.FirstOrDefault(m => m.Id == id);
        }
    }
}
