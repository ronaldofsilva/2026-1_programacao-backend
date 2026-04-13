import photoService from "../services/PhotoService.js";
import emotionService from "../services/EmotionService.js";

class EmotionDetectionControler{
    async loadFace(req, res){
        try{
            const id = req.params.id;
            const face = await photoService.getPhotoById(id);
            if (!face || face.length === 0){
                res.status(404).json('Pessoa não encontrada');
            }
            const photoUrl = `assets/uploads/${face.photo}`;            
            const emotion = await emotionService.detect(photoUrl);
            res.status(200).json(emotionService.formatOutput(emotion));
        }catch (error){
            res.status(500).json({message: error.message});
        }
    }
}
export default new EmotionDetectionControler();