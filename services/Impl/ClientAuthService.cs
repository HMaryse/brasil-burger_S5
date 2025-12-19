using Data;
using Models;

namespace Services
{
    public class ClientAuthService : IClientAuthService
    {
        private readonly AppDbContext _context;

        public ClientAuthService(AppDbContext context)
        {
            _context = context;
        }

        public Client? Login(string telephone, string password)
        {
            return _context.Clients
                .FirstOrDefault(c =>
                    c.Telephone == telephone &&
                    c.MotDePasse == password
                );
        }

        public Client Register(Client client)
        {
            _context.Clients.Add(client);
            _context.SaveChanges();
            return client;
        }
    }

}