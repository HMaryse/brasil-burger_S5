using Microsoft.AspNetCore.Mvc;
using Services;
using ViewModels;

public class DashboardController : Controller
{
    private readonly IBurgerService _burgerService;
    private readonly IMenuService _menuService;

    private const int PAGE_SIZE = 3;

    public DashboardController(
        IBurgerService burgerService,
        IMenuService menuService)
    {
        _burgerService = burgerService;
        _menuService = menuService;
    }

    public IActionResult Index(string type = "ALL", int page = 1)
    {
        var vm = new CatalogueViewModel
        {
            Type = type,
            Page = page
        };

        if (type == "BURGER" || type == "ALL")
        {
            var allBurgers = _burgerService
                .GetAllDisponibles()
                .ToList();

            vm.TotalPages = (int)Math.Ceiling(allBurgers.Count / (double)PAGE_SIZE);

            vm.Burgers = allBurgers
                .Skip((page - 1) * PAGE_SIZE)
                .Take(PAGE_SIZE)
                .ToList();
        }

        if (type == "MENU" || type == "ALL")
        {
            var allMenus = _menuService
                .GetAllDisponibles()
                .ToList();

            vm.TotalPages = Math.Max(
                vm.TotalPages,
                (int)Math.Ceiling(allMenus.Count / (double)PAGE_SIZE)
            );

            vm.Menus = allMenus
                .Skip((page - 1) * PAGE_SIZE)
                .Take(PAGE_SIZE)
                .ToList();
        }

        return View(vm);
    }
}
