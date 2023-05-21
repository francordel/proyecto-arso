
using Repositorio;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Linq;
using MongoDB.Bson;
using opiniones_rest.Modelo;
using System;
namespace opiniones_rest.Repositorio
{
    public class RepositorioOpinionesMongoDB : Repositorio<Opinion, string>
    {
        private readonly IMongoCollection<Opinion> opiniones;

        public RepositorioOpinionesMongoDB()
        {
            //var client = new MongoClient("mongodb://localhost:27017");
            //Console.WriteLine(Environment.GetEnvironmentVariable("URI_MONGODB"));
            //var client = new MongoClient("mongodb://root:example@mongo:27017/");
            var client = new MongoClient(Environment.GetEnvironmentVariable("URI_MONGODB"));
            var database = client.GetDatabase("ArsoProyecto");
            opiniones = database.GetCollection<Opinion>("Opiniones");
        }

        public string Add(Opinion entity)
        {
            opiniones.InsertOne(entity);

            return entity.Id;
        }

        public void Update(Opinion entity)
        {
            opiniones.ReplaceOne(opinion => opinion.Id == entity.Id, entity);
        }

        public void Delete(Opinion entity)
        {
            opiniones.DeleteOne(opinion => opinion.Id == entity.Id);
        }

        public List<Opinion> GetAll()
        {
            return opiniones.Find(_ => true).ToList();
        }

        public Opinion GetById(string id)
        {
            return opiniones
                .Find(opinion => opinion.Id == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  opiniones.Find(_ => true).ToList();

            return todas.Select(p => p.Id).ToList();

        }
    }
}