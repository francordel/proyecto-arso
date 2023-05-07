using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;

namespace opiniones_rest.Modelo
{
    public class Opinion
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }

        public string NombreRecurso { get; set; }

        public List<Valoracion> Valoraciones { get; set; } = new List<Valoracion>();

        public double ValoracionMedia
        {
            get
            {
                // Calcula usando la funcion Average de LINQ el promedio de las calificaciones en la lista de Valoraciones si hay al menos una valoración, de lo contrario devuelve 0.0
                return Valoraciones.Count > 0 ? Valoraciones.Average(v => v.Calificacion) : 0.0;
            }
        }

        public int NumeroValoraciones
        {
            get
            {
                return Valoraciones.Count;
            }
        }
    }

    public class Valoracion{
        public string CorreoElectronico { get; set; }
        public DateTime FechaRegistro { get; set; }
        public string Comentario { get; set; }
        private int _calificacion;
        public int Calificacion
        {
            get { return _calificacion; }
            set
            {
                if (value >= 1 && value <= 5)
                {
                    _calificacion = value;
                }
                else
                {
                    throw new ArgumentException("La calificación debe estar entre 1 y 5");
                }
            }
        }
        
    }
}
