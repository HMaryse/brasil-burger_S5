using Microsoft.EntityFrameworkCore;
using Models;

namespace Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options)
            : base(options) { }

        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<Client> Clients { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // BURGER
            modelBuilder.Entity<Burger>(entity =>
            {
                entity.ToTable("burger");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nom).HasColumnName("nom");
                entity.Property(e => e.Prix).HasColumnName("prix");
                entity.Property(e => e.ImageUrl).HasColumnName("image_url");
                entity.Property(e => e.Etat).HasColumnName("etat");
            });

            //MENU
            modelBuilder.Entity<Menu>(entity =>
            {
                entity.ToTable("menu");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nom).HasColumnName("nom");
                entity.Property(e => e.Prix).HasColumnName("prix");
                entity.Property(e => e.ImageUrl).HasColumnName("image_url");
                entity.Property(e => e.Etat).HasColumnName("etat");
            });

            //  COMPLEMENT 
            modelBuilder.Entity<Complement>(entity =>
            {
                entity.ToTable("complement");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nom).HasColumnName("nom");
                entity.Property(e => e.Prix).HasColumnName("prix");
                entity.Property(e => e.ImageUrl).HasColumnName("image_url");
                entity.Property(e => e.Etat).HasColumnName("etat");
            });

            //  CLIENT (USER)
            modelBuilder.Entity<Client>(entity =>
            {
                entity.ToTable("client");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.NomComplet).HasColumnName("nom_complet");
                entity.Property(e => e.MotDePasse).HasColumnName("mot_de_passe");
                entity.Property(e => e.Telephone).HasColumnName("telephone");
            });

            // COMMANDE 
            modelBuilder.Entity<Commande>(entity =>
            {
                entity.ToTable("commande");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.DateCommande).HasColumnName("date_commande");
                entity.Property(e => e.Total).HasColumnName("total");
                entity.Property(e => e.Statut).HasColumnName("statut");
                entity.Property(e => e.TypeCommande).HasColumnName("type_commande");
                entity.Property(e => e.ClientId).HasColumnName("client_id");
            });

            // COMMANDE ITEMS
            modelBuilder.Entity<CommandeItem>(entity =>
            {
                entity.ToTable("commande_items");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.CommandeId).HasColumnName("commande_id");
                entity.Property(e => e.BurgerId).HasColumnName("burger_id");
                entity.Property(e => e.MenuId).HasColumnName("menu_id");
                entity.Property(e => e.Quantite).HasColumnName("quantite");
                entity.Property(e => e.PrixUnitaire).HasColumnName("prix_unitaire");
            });

            // MENU DETAIL 
            modelBuilder.Entity<MenuDetail>(entity =>
            {
                entity.ToTable("menu_detail");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.MenuId).HasColumnName("menu_id");
                entity.Property(e => e.BurgerId).HasColumnName("burger_id");
                entity.Property(e => e.Quantite).HasColumnName("quantite");
            });

            // MENU COMPLEMENT
            modelBuilder.Entity<MenuComplement>(entity =>
            {
                entity.ToTable("menu_complement");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.MenuId).HasColumnName("menu_id");
                entity.Property(e => e.ComplementId).HasColumnName("complement_id");
                entity.Property(e => e.Quantite).HasColumnName("quantite");
            });
            modelBuilder.Entity<Client>()
            .ToTable("client"); 
        }
}
}