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

    public IActionResult Login() => View();

    [HttpPost]
    public IActionResult Login(string telephone, string motDePasse)
    {
        var client = _service.Login(telephone, motDePasse);

        if (client == null)
        {
            ViewBag.Error = "Identifiants incorrects";
            return View();
        }

        HttpContext.Session.SetInt32("ClientId", client.Id);
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
