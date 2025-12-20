using Data;
using Microsoft.EntityFrameworkCore;
using Services;
using Models;
using Npgsql;

var builder = WebApplication.CreateBuilder(args);


builder.Services.AddControllersWithViews();


builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});


var dataSourceBuilder = new NpgsqlDataSourceBuilder(
    builder.Configuration.GetConnectionString("NeonDb")
);

dataSourceBuilder.MapEnum<ModeConsommation>(
    "mode_consommation_type",
    new Npgsql.NameTranslation.NpgsqlNullNameTranslator()
);

dataSourceBuilder.MapEnum<StatutCommande>(
    "statut_commande_type",
    new Npgsql.NameTranslation.NpgsqlNullNameTranslator()
);

dataSourceBuilder.MapEnum<ModePaiement>(
    "paiement_mode_type",
    new Npgsql.NameTranslation.NpgsqlNullNameTranslator()
);

dataSourceBuilder.MapEnum<StatutPaiement>(
    "statut_paiement_type",
    new Npgsql.NameTranslation.NpgsqlNullNameTranslator()
);

dataSourceBuilder.MapEnum<EtatType>(
    "etat_type",
    new Npgsql.NameTranslation.NpgsqlNullNameTranslator()
);



var dataSource = dataSourceBuilder.Build();


builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(dataSource)
);



builder.Services.AddScoped<IBurgerService, BurgerService>();
builder.Services.AddScoped<IMenuService, MenuService>();
builder.Services.AddScoped<IClientAuthService, ClientAuthService>();
builder.Services.AddScoped<IComplementService, ComplementService>();

var app = builder.Build();


if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseSession();

app.UseRouting();
app.UseAuthorization();


app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Dashboard}/{action=Index}/{id?}"
);

app.Run();
