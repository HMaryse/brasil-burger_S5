using Models;
using System.Collections.Generic;

namespace Services
{
    public interface IBurgerService
    {
        List<Burger>  GetAllDisponibles();
        Burger? GetById(int id);
    }
}
