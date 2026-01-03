using Microsoft.AspNetCore.Mvc;
using Helpers;
using Services;
using ViewModels;
using Models;
using Data;
using Microsoft.EntityFrameworkCore;

public class CommandeController : Controller
{
    private readonly IBurgerService _burgerService;
    private readonly IMenuService _menuService;
    private readonly IComplementService _complementService;
    private readonly AppDbContext _context;

    public CommandeController(
        IBurgerService burgerService,
        IMenuService menuService,
        IComplementService complementService,
        AppDbContext context)
    {
        _burgerService = burgerService;
        _menuService = menuService;
        _complementService = complementService;
        _context = context;
    }

    // PANIER
    public IActionResult Ajouter(int id, string type)
    {
        var panier = PanierHelper.GetPanier(HttpContext);

        if (type == "BURGER")
        {
            var b = _burgerService.GetById(id);
            panier.AddItem(b.Id, b.Nom, b.Prix, b.ImageUrl, "BURGER");
        }
        else if (type == "MENU")
        {
            var m = _menuService.GetById(id);
            panier.AddItem(m.Id, m.Nom, m.Prix, m.ImageUrl, "MENU");
        }

        PanierHelper.SavePanier(HttpContext, panier);
        return RedirectToAction("Panier");
    }

    public IActionResult Panier()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
        {
            var returnUrl = HttpContext.Request.Path + HttpContext.Request.QueryString;
            return RedirectToAction("Login", "Auth", new { returnUrl });
        }

        return View(PanierHelper.GetPanier(HttpContext));
    }

    public IActionResult Augmenter(int id, string type)
    {
        var panier = PanierHelper.GetPanier(HttpContext);
        var item = panier.Items.FirstOrDefault(i => i.Id == id && i.Type == type);
        if (item != null) item.Quantite++;
        PanierHelper.SavePanier(HttpContext, panier);
        return RedirectToAction("Panier");
    }

    public IActionResult Diminuer(int id, string type)
    {
        var panier = PanierHelper.GetPanier(HttpContext);
        var item = panier.Items.FirstOrDefault(i => i.Id == id && i.Type == type);
        if (item != null && item.Quantite > 1) item.Quantite--;
        PanierHelper.SavePanier(HttpContext, panier);
        return RedirectToAction("Panier");
    }

    public IActionResult Supprimer(int id, string type)
    {
        var panier = PanierHelper.GetPanier(HttpContext);
        panier.Items.RemoveAll(i => i.Id == id && i.Type == type);
        PanierHelper.SavePanier(HttpContext, panier);
        return RedirectToAction("Panier");
    }

    // COMPLEMENTS
    public IActionResult Complements(int id, string type)
    {
        return View(new ComplementSelectionViewModel
        {
            ArticleId = id,
            Type = type,
            Complements = _complementService.GetAll()
        });
    }

    [HttpPost]
    public IActionResult AjouterComplements(ComplementSelectionViewModel model)
    {
        var panier = PanierHelper.GetPanier(HttpContext);

        foreach (var compId in model.SelectedComplementIds)
        {
            var c = _complementService.GetById(compId);
            panier.AddItem(c.Id, c.Nom, (double)c.Prix, c.ImageUrl ?? "", "COMPLEMENT");
        }

        PanierHelper.SavePanier(HttpContext, panier);
        return RedirectToAction("Panier");
    }

    // VALIDATION
    public IActionResult Valider()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
        {
            var returnUrl = HttpContext.Request.Path;
            return RedirectToAction("Login", "Auth", new { returnUrl });
        }

        var panier = PanierHelper.GetPanier(HttpContext);
        if (!panier.Items.Any())
            return RedirectToAction("Panier");

        return View(panier);
    }

    [HttpPost]
    [HttpPost]
public IActionResult Confirmer(ModeConsommation ModeConsommation, ModePaiement ModePaiement)
{
    var clientId = HttpContext.Session.GetInt32("ClientId");
    if (clientId == null)
    {
        var returnUrl = HttpContext.Request.Path;
        return RedirectToAction("Login", "Auth", new { returnUrl });
    }

    var panier = PanierHelper.GetPanier(HttpContext);
    if (!panier.Items.Any())
        return RedirectToAction("Panier");

    var commande = new Commande
    {
        ClientId = clientId.Value,
        DateCommande = DateTime.UtcNow,
        Statut = StatutCommande.EN_COURS,
        ModeConsommation = ModeConsommation,
        EstPaye = true
    };

    _context.Commandes.Add(commande);
    _context.SaveChanges();

    foreach (var item in panier.Items)
    {
        _context.CommandeItems.Add(new CommandeItem
        {
            CommandeId = commande.Id,
            Quantite = item.Quantite,
            PrixTotal = (decimal)(item.Prix * item.Quantite),
            BurgerId = item.Type == "BURGER" ? item.Id : null,
            MenuId = item.Type == "MENU" ? item.Id : null
        });
    }

    _context.SaveChanges();

    _context.Paiements.Add(new Paiement
    {
        CommandeId = commande.Id,
        Montant = (decimal)panier.Total,
        ModePaiement = ModePaiement,
        StatutPaiement = StatutPaiement.VALIDE,
        DatePaiement = DateTime.UtcNow
    });

    _context.SaveChanges();

    HttpContext.Session.Remove("PANIER");
    return RedirectToAction("MesCommandes");
}

    public IActionResult MesCommandes(int page = 1)
    {
        var clientId = HttpContext.Session.GetInt32("ClientId");
        if (clientId == null)
        {
            var returnUrl = HttpContext.Request.Path + HttpContext.Request.QueryString;
            return RedirectToAction("Login", "Auth", new { returnUrl });
        }

        const int PAGE_SIZE = 2;

        var query = _context.Commandes
            .Where(c => c.ClientId == clientId.Value)
            .Include(c => c.Items)
            .OrderByDescending(c => c.DateCommande);

        var totalCommandes = query.Count();
        var totalPages = (int)Math.Ceiling(totalCommandes / (double)PAGE_SIZE);

        var commandes = query
            .Skip((page - 1) * PAGE_SIZE)
            .Take(PAGE_SIZE)
            .ToList();

        ViewBag.Page = page;
        ViewBag.TotalPages = totalPages;

        return View(commandes);
    }
}
