using Models;
using System.Collections.Generic;

namespace Services
{
    public interface IMenuService
    {
        Menu? GetById(int id);
        List<Menu> GetAllDisponibles();
    }
}
