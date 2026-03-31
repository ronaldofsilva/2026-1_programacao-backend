import photoService from '../services/photoService.js';
class PhotoController {

    async create(req, res) {
        try {
            const { people } = req.body; //Aqui obtem o id da pessoa a qual a foto pertence (referência)
            const file = req.file;
            //Chama o método de criação da foto implementado no Service
            const photo = await photoService.createPhoto(people, file);
            res.status(201).json(photo);

        } catch (error) {
            res.status(400).json({ message: error.message });
        }
    }

    async index(req, res) {
        try {
            const photos = await photoService.getAllPhotos();
            res.status(200).json(photos);
        } catch (error) {
            res.status(500).json({
                message: "Erro ao listar as fotos.",
                error: error.message
            });
        }
    }
    async photosPeople(req, res) {
        try {
            const photos = await photoService.getPhotosAndPeople();
            res.status(200).json(photos);
        } catch (error) {
            res.status(500).json({
                message: "Erro ao listar as fotos.",
                error: error.message
            });
        }
    }
}

export default new PhotoController();