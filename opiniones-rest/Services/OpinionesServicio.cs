
using System;
using System.Collections.Generic;
using opiniones_rest.Modelo;
using Repositorio;

namespace opiniones_rest.Servicio
{
       public class OpinionResumen
    {

        public String Id { get; set; }
        public String NombreRecurso { get; set; }

    }
    public interface IServicioOpiniones
    {
    /// <summary>
    /// Registrar un recurso (con un nombre) para ser valorado (crea una opinión)
    /// </summary>
    string Create(Opinion opinion);
    /// <summary>
    /// Actualizar una opinión	
    /// </summary>
    void Update(Opinion opinion);
    /// <summary>
    /// Añadir una valoración sobre un recurso
    /// </summary>
    bool AddValoracion(string id, Valoracion valoracion);

    /// <summary>
    /// Recuperar la opinión de un recurso utilizando el identificador.
    /// </summary>
    Opinion GetOpinion(string id);

    /// <summary>
    /// Elimina una opinión y sus valoraciones.
    /// </summary>
    bool RemoveOpinion(string id);

    /// <summary>
    /// Obtener el resumen de las opiniones
    /// </summary>
    List<OpinionResumen> GetResumenes();

    }

    public class ServicioOpiniones : IServicioOpiniones
    {

        private Repositorio<Opinion, String> repositorio;
        public ServicioOpiniones(Repositorio<Opinion, String> repositorio)
        {

            this.repositorio = repositorio;
        }
        public string Create(Opinion opinion)
        {
            return repositorio.Add(opinion);
        }

        public void Update(Opinion opinion)
        {

            repositorio.Update(opinion);
        }
        public bool AddValoracion(string id, Valoracion valoracion)
        {
            Opinion opinion = repositorio.GetById(id);
            List<Valoracion> valoraciones = opinion.Valoraciones;

            // Eliminamos las valoraciones con el mismo correo electrónico
            valoraciones.RemoveAll(v => v.CorreoElectronico == valoracion.CorreoElectronico);

            // Añadimos la nueva valoración a la lista
            valoraciones.Add(valoracion);
            repositorio.Update(opinion);
            return true;
        }

        public Opinion GetOpinion(string id)
        {
            return repositorio.GetById(id);
        }

        public bool RemoveOpinion(string id)
        {
            Opinion opinion = repositorio.GetById(id);
            repositorio.Delete(opinion);
            return true;
        }
        
        public List<OpinionResumen> GetResumenes()
        {

            var resultado = new List<OpinionResumen>();

            foreach (String id in repositorio.GetIds())
            {

                    var opinion = GetOpinion(id);
                    var resumen = new OpinionResumen
                    {
                        Id = opinion.Id,
                        NombreRecurso = opinion.NombreRecurso,
                    };
                    resultado.Add(resumen);
            }

            return resultado;
        }

    }


}