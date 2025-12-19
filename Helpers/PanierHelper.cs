using Microsoft.AspNetCore.Http;
using System.Text.Json;
using ViewModels;

namespace Helpers
{
    public static class PanierHelper
    {
        private const string KEY = "PANIER";

        public static PanierViewModel GetPanier(HttpContext context)
        {
            var json = context.Session.GetString(KEY);
            return json == null
                ? new PanierViewModel()
                : JsonSerializer.Deserialize<PanierViewModel>(json)!;
        }

        public static void SavePanier(HttpContext context, PanierViewModel panier)
        {
            context.Session.SetString(KEY, JsonSerializer.Serialize(panier));
        }
    }
}