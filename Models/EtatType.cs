using NpgsqlTypes;

namespace Models
{
    public enum EtatType
    {
        [PgName("DISPONIBLE")]
        DISPONIBLE,

        [PgName("INDISPONIBLE")]
        INDISPONIBLE
    }
}
