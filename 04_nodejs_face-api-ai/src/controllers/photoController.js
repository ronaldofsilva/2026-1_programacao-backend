// src/controllers/photoController.js
import photoService from "../services/PhotoService.js";

class PhotoController {
  // GET /photo
  async index(req, res) {
    try {
      const photos = await photoService.getAllPhotos();
      res.status(200).json(photos);
    } catch (error) {
      res.status(500).json({
        message: "Erro ao buscar fotos",
        error: error.message
      });
    }
  }

  async getPhotosAndPeople(req, res) {
    try {
      const photos = await photoService.getPhotosPeople();
      res.status(200).json(photos);
    } catch (error) {
      res.status(500).json({
        message: "Erro ao buscar fotos",
        error: error.message
      });
    }
  }

  // GET /photo/people/:id
  async getByPeople(req, res) {
    try {
      const id = req.params.id;
      const photos = await photoService.getPhotosByPeople(id);
      res.status(200).json(photos);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  }

  // GET /photo/:id
  async getById(req, res){
    try{
      const id = req.params.id;
      const photo = await photoService.getPhotoById(id);
      if (!photo || photo.length === 0){
        return res.status(404).json({message: 'Face não encontrada'});
      }
      res.status(200).json(photo);
    }catch (error){
      res.status(500).json({ message: error.message });
    }
  }

  // POST /photo/create
  async store(req, res) {
    try {
      const { people } = req.body;
      const file = req.file;
      if (!file) {
        return res.status(400).json({ message: "Arquivo é obrigatório" });
      }
      const photo = await photoService.createPhoto(people, file);
      res.status(201).json(photo);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  }

  async update(req, res) {
    try {
      const photoId = req.params.id;
      const file = req.file;
      if (!file) {
        return res.status(400).json({ message: "Arquivo é obrigatório" });
      }
      const photo = await photoService.updatePhoto(photoId, file);
      res.status(201).json(photo);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  }

  // DELETE /photo/:id
  async destroy(req, res) {
    try {
      const id = req.params.id;
      await photoService.deletePhoto(id);
      res.status(204).send();
    } catch (error) {
      console.error(error.message);
      res.status(500).json({
        message: 'Erro interno no servidor.'
      });
    }
  }
}

export default new PhotoController();
