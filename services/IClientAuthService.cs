using Models;

namespace Services
{
    public interface IClientAuthService
    {
        Client? Login(string email, string password);
        Client Register(Client client);
    }

}