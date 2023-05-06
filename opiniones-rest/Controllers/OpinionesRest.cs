using opiniones_rest.Modelo;
using opiniones_rest.Servicio;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;

namespace Opiniones_restApi.Controllers
{
    [Route("api/opiniones")]
    [ApiController]
    public class OpinionesController : ControllerBase
    {
        private readonly IServicioOpiniones _servicio;

        public OpinionesController(IServicioOpiniones servicio)
        {
            _servicio = servicio;
        }

        [HttpGet]
        public ActionResult<List<OpinionResumen>> Get() =>
            _servicio.GetResumenes();

        [HttpGet("{id}", Name = "GetOpinion")]
        public ActionResult<Opinion> Get(string id)
        {
            var entidad = _servicio.GetOpinion(id);

            if (entidad == null)
            {
                return NotFound();
            }

            return entidad;
        }

        [HttpPost]
        public ActionResult<Opinion> Create(Opinion opinion)
        {
            _servicio.Create(opinion);

            return CreatedAtRoute("GetOpinion", new { id = opinion.Id }, opinion);
        }

        [HttpPut("{id}")]
        public IActionResult Update(string id, Opinion opinion)
        {
            var entidad = _servicio.GetOpinion(id);

            if (entidad == null)
            {
                return NotFound();
            }

            _servicio.Update(opinion);

            return NoContent();
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(string id)
        {
            var opinion = _servicio.GetOpinion(id);

            if (opinion == null)
            {
                return NotFound();
            }

            _servicio.RemoveOpinion(id);

            return NoContent();
        }

        [HttpPost("{id}/valoraciones")]
        public IActionResult AddValoracion(string id, Valoracion valoracion)
        {
            var opinion = _servicio.GetOpinion(id);

            if (opinion == null)
            {
                return NotFound();
            }
            
            _servicio.AddValoracion(id, valoracion);

            return NoContent();
        }
    }
}
