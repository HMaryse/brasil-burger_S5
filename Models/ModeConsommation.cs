using NpgsqlTypes;

namespace Models
{
    public enum ModeConsommation
    {
        [PgName("SUR_PLACE")]
        SUR_PLACE,

        [PgName("A_EMPORTER")]
        A_EMPORTER,

        [PgName("LIVRAISON")]
        LIVRAISON
    }
}
