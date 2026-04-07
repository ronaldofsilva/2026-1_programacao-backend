// src/repositories/photoRepository.js
import Photo from "../models/Photo.js";

class PhotoRepository {
  // Buscar todas as fotos
  async findAll() {
    return await Photo.find().populate('people', 'name age');
  }

  async findPhotosPeople(){
       return await Photo.find()
        .populate('people', 'name age')
        .sort({ createdAt: -1 }); 
    }
  
  // Buscar foto por ID
  async findById(id) {
    return await Photo.findById(id).populate('people', 'name age');
  }
  
  // Buscar fotos de uma pessoa
  async findByPeopleId(peopleId) {
    return await Photo.find({ people: peopleId }).populate('people');
  }
  
  // Criar nova foto
  async create(photoData) {
    const photo = new Photo(photoData);
    return await photo.save();
  }
  
  // Atualizar foto
  async update(id, photoData) {
    return await Photo.findByIdAndUpdate(id, photoData, { 
      new: true,
      runValidators: true 
    }).populate('people');
  }
  
  // Deletar foto
  async delete(id) {
    return await Photo.findByIdAndDelete(id);
  }
}

export default new PhotoRepository();

// BENEFÍCIO: Toda lógica de banco em um lugar só!
