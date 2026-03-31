import photoRepository from '../repositories/photoRepository.js';
class PhotoService {
    async getAllPhotos() {
        const photos = await photoRepository.findAll();
        return photos.map(photo => ({
            ...photo.toObject(),
            photoUrl: `/assets/upload/${photo.photo}`
        }));
    }
    async getPhotosAndPeople() {
        const photos = await photoRepository.findPhotosAndPeople();
        return photos.map(photo => ({
            ...photo.toObject(),
            photoUrl: `/assets/upload/${photo.photo}`
        }));
    }

    async createFoto(peopleId, file) {
        // Validação: pessoa existe?
        const personExists = await this.checkPersonExists(peopleId);
        if (!personExists) {
            throw new Error("Pessoa não encontrada");
        }
        // Criar registro no banco
        const photoData = {
            people: peopleId,
            photo: file.filename
        };
        //Método para criar a foto no banco de dados implementado no Repository
        return await photoRepository.create(photoData);
    }

    async checkPersonExists(personId) {
        // Implementação verificaria no banco
        return true;
    }

}
export default new PhotoService();