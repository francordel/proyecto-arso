using opiniones_rest.Modelo;

namespace opiniones_rest.Eventos
{
    public class EventoNuevaValoracion
    {
        public string IdOpinion { get; set; }
        public Valoracion NuevaValoracion { get; set; }
        public int NumeroValoraciones { get; set; }
        public double CalificacionMedia { get; set; }
    }
}