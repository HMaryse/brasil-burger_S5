using Microsoft.AspNetCore.Mvc;
using Helpers;
using Services;
using ViewModels;

public class CommandeController : Controller
{
    private readonly IBurgerService _burgerService;
    private readonly IMenuService _menuService;
    private readonly IComplementService _complementService;


    public CommandeController(
        IBurgerService burgerService,
        IMenuService menuService, IComplementService complementService)
    {
        _burgerService = burgerService;
        _menuService = menuService;
        _complementService = complementService;
    }

    //  bouton commander
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

    //  page panier
    public IActionResult Panier()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
            return RedirectToAction("Login", "Auth");

        var panier = PanierHelper.GetPanier(HttpContext);
        return View(panier);
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

    // Choix des complements 
    public IActionResult Complements(int id, string type)
    {
        var model = new ComplementSelectionViewModel
        {
            ArticleId = id,
            Type = type,
            Complements = _complementService.GetAll()
        };

        return View(model);
    }

    [HttpPost]
    public IActionResult AjouterComplements(ComplementSelectionViewModel model)
    {
        var panier = PanierHelper.GetPanier(HttpContext);

        foreach (var compId in model.SelectedComplementIds)
        {
            var c = _complementService.GetById(compId);
            if (c != null)
            {
                panier.AddItem(
                    c.Id,
                    c.Nom,
                    (double)c.Prix,
                    c.ImageUrl ?? "",
                    "COMPLEMENT"
                );
            }
        }

        PanierHelper.SavePanier(HttpContext, panier);
        return RedirectToAction("Panier");
    }


}

