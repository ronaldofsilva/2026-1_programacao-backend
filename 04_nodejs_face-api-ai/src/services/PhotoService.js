// src/services/photoService.js
import photoRepository from "../repositories/photoRepository.js";
import fs from "fs/promises";
import path from "path";

class PhotoService {
  // Listar todas as fotos
  async getAllPhotos() {
    const photos = await photoRepository.findAll();
    // Adicionar URL completa das fotos
    return photos.map(photo => ({
      ...photo.toObject(),
      photoUrl: `/assets/uploads/${photo.photo}`
    }));
  }

  // Listar as fotos com a incoporação da pessoa a qual a foto pertence
  async getPhotosPeople() {
    const photos = await photoRepository.findPhotosPeople();
    return photos.map(item => ({
      ...item.toObject(),
      photoUrl: `/assets/uploads/${item.photo}`,
      people: item.people
    }));
  }

  //Busca uma foto pelo ID
  async getPhotoById(photoId){
    const photos = await photoRepository.findById(photoId);
    return photos;
  }

  // Buscar fotos de uma pessoa
  async getPhotosByPeople(peopleId) {
    // Validação de negócio
    if (!peopleId) {
      throw new Error("ID da pessoa é obrigatório");
    }
    return await photoRepository.findByPeopleId(peopleId);
  }

  // Criar nova foto com validações
  async createPhoto(personId, file) {
    // Validação: pessoa existe?
    // Atenção: Essa implementação é conceitual, veja que a função checkPersonExists está com a lógica incompleta
    const personExists = await this.checkPersonExists(personId);
    if (!personExists) {
      throw new Error("Pessoa não encontrada");
    }

    // Criar registro no banco
    const photoData = {
      people: personId,
      photo: file.filename
    };

    return await photoRepository.create(photoData);
  }

  async updatePhoto(photoId, file) {

    const newFile = file;
    let updateData = {};
    const currentPhoto = await photoRepository.findById(photoId);
    if (!currentPhoto) {
      if (newFile) await fs.unlink(newFile.path);
      throw new Error("Foto não encontrada");
    }
    if (newFile) {
      const oldPath = path.join('assets/uploads', currentPhoto.photo);
      console.log(oldPath);
      try {
        await fs.access(oldPath);
        await fs.unlink(oldPath);
      } catch (err) {
        console.warn("Arquivo não encontrado, ignorando...");
      }
      updateData.photo = newFile.filename;
      return await photoRepository.update(photoId, updateData);
    }
  }

  // Deletar foto (remove arquivo + registro)
  async deletePhoto(id) {
    const photo = await photoRepository.findById(id);
    if (!photo) {
      throw new Error("Foto não encontrada");
    }
    // Remover arquivo físico
    const filePath = path.join('assets/uploads', photo.photo);
    await fs.unlink(filePath);
    // Remover do banco
    return await photoRepository.delete(id);
  }

  async checkPersonExists(personId) {
    // Implementação verificaria no banco
    return true;
  }
}

export default new PhotoService();
