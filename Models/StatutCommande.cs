using NpgsqlTypes;

namespace Models
{
    public enum StatutCommande
    {
        [PgName("EN_COURS")]
        EN_COURS,

        [PgName("VALIDÉE")]
        VALIDEE,

        [PgName("TERMINÉE")]
        TERMINEE,

        [PgName("ANNULÉE")]
        ANNULEE
    }
}
