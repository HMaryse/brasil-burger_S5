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
        public DbSet<Client> Clients { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<CommandeItem> CommandeItems { get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        public DbSet<Zone> Zone { get; set; }


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
                entity.Property(e => e.Etat)
                      .HasColumnName("etat")
                      .HasColumnType("etat_type");
            });

            // MENU 
            modelBuilder.Entity<Menu>(entity =>
            {
                entity.ToTable("menu");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nom).HasColumnName("nom");
                entity.Property(e => e.Prix).HasColumnName("prix");
                entity.Property(e => e.ImageUrl).HasColumnName("image_url");
                entity.Property(e => e.Etat)
                      .HasColumnName("etat")
                      .HasColumnType("etat_type");
            });

            // COMPLEMENT 
            modelBuilder.Entity<Complement>(entity =>
            {
                entity.ToTable("complement");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nom).HasColumnName("nom");
                entity.Property(e => e.Prix).HasColumnName("prix");
                entity.Property(e => e.ImageUrl).HasColumnName("image_url");
                entity.Property(e => e.Etat)
                      .HasColumnName("etat")
                      .HasColumnType("etat_type");
            });

            // CLIENT 
            modelBuilder.Entity<Client>(entity =>
            {
                entity.ToTable("client");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.NomComplet).HasColumnName("nom_complet");
                entity.Property(e => e.Telephone).HasColumnName("telephone");
                entity.Property(e => e.MotDePasse).HasColumnName("mot_de_passe");
                entity.Property(e => e.Adresse).HasColumnName("adresse");
            });

            // COMMANDE 
            modelBuilder.Entity<Commande>(entity =>
            {
                entity.ToTable("commande");
                entity.HasKey(e => e.Id);

                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.ClientId).HasColumnName("client_id");
                entity.Property(e => e.LivreurId).HasColumnName("livreur_id");
                entity.Property(e => e.DateCommande).HasColumnName("date_commande");

                entity.Property(e => e.Statut)
                      .HasColumnName("statut")
                      .HasColumnType("statut_commande_type");

                entity.Property(e => e.ModeConsommation)
                      .HasColumnName("mode_consommation")
                      .HasColumnType("mode_consommation_type");

                entity.Property(e => e.Adresse).HasColumnName("adresse");
                entity.Property(e => e.EstPaye).HasColumnName("est_paye");
                entity.Property(e => e.ZoneId).HasColumnName("zone_id");
            });

            // COMMANDE ITEM 
            modelBuilder.Entity<CommandeItem>(entity =>
            {
                entity.ToTable("commande_item");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.CommandeId).HasColumnName("commande_id");
                entity.Property(e => e.BurgerId).HasColumnName("burger_id");
                entity.Property(e => e.MenuId).HasColumnName("menu_id");
                entity.Property(e => e.Quantite).HasColumnName("quantite");
                entity.Property(e => e.PrixTotal).HasColumnName("prix_total");
            });

            // PAIEMENT
            modelBuilder.Entity<Paiement>(entity =>
            {
                entity.ToTable("paiement");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.CommandeId).HasColumnName("commande_id");
                entity.Property(e => e.Montant).HasColumnName("montant");
                entity.Property(e => e.DatePaiement).HasColumnName("date_paiement");

                entity.Property(e => e.ModePaiement)
                      .HasColumnName("mode_paiement")
                      .HasColumnType("paiement_mode_type");

                entity.Property(e => e.StatutPaiement)
                      .HasColumnName("statut_paiement")
                      .HasColumnType("statut_paiement_type");
            });
        }
    }
}
