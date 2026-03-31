import photo from '../models/Photo.js';
class PhotoRepository {
    //Busca todos as fotos cadastradas no banco e dados
    async findAll() {
        return await photo.find();
    }
    
    //Busca todas as fotos e popula com os dados da pessoa a qual a foto pertence
    async findPhotosAndPeople() {
        return await photo.find()
            .populate('people', 'name age')
            .sort({ createdAt: -1 })
    }

    // Criar nova foto
    async create(photoData) {
        const newPhoto = new photo(photoData);
        return await newPhoto.save();
    }

}
export default new PhotoRepository();