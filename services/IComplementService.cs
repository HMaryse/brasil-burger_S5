using Models;
using System.Collections.Generic;

namespace Services
{
    public interface IComplementService
    {
        List<Complement> GetAll();
        Complement GetById(int id);
    }
}
