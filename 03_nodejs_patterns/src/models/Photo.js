
import mongoose from "mongoose";
import People from "./People.js";
/**Cria o schema para a coleção no banco de dados MongoDB */
const photoSchema = new mongoose.Schema({
    people: {
        type: mongoose.Schema.Types.ObjectId,
        ref: People,
        required: true
    },
    photo: {
        type: String,
        required: true
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
}, {
    versionKey: false
});
/**Retorna a URL completa do arquivo */
photoSchema.virtual('photoUrl').get(function () {
    return `/assets/uploads/${this.photo}`;
});

const Photo = mongoose.model("photo", photoSchema, "photo");
export default Photo;

