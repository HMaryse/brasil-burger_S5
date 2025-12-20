using Microsoft.AspNetCore.Mvc;
using Models;
using Services;

public class AuthController : Controller
{
    private readonly IClientAuthService _service;

    public AuthController(IClientAuthService service)
    {
        _service = service;
    }

    public IActionResult Login(string? returnUrl)
    {
        ViewBag.ReturnUrl = returnUrl;
        return View();
    }

    [HttpPost]
    public IActionResult Login(string telephone, string motDePasse, string? returnUrl)
    {
        var client = _service.Login(telephone, motDePasse);

        if (client == null)
        {
            ViewBag.Error = "Identifiants incorrects";
            ViewBag.ReturnUrl = returnUrl;
            return View();
        }

        HttpContext.Session.SetInt32("ClientId", client.Id);
        HttpContext.Session.SetString("ClientNom", client.NomComplet);
        HttpContext.Session.SetString("ClientAdresse", client.Adresse);

        if (!string.IsNullOrEmpty(returnUrl))
            return Redirect(returnUrl);

        return RedirectToAction("Index", "Dashboard");
    }

    public IActionResult Register() => View();

    [HttpPost]
    public IActionResult Register(Client client)
    {
        _service.Register(client);
        return RedirectToAction("Login");
    }

    public IActionResult Logout()
    {
        HttpContext.Session.Clear();
        return RedirectToAction("Index", "Dashboard");
    }
}
