import mongoose from "mongoose";
const peopleSchema = new mongoose.Schema({
    id: {
        type: mongoose.Schema.Types.ObjectId
    },
    name: {
        type: String,
        required: true,
        minlength: 3,
        maxlength: 100
    },
    age: {
        type: Number,
        required: true,
        min: 0,
        max: 150
    },
    email: {
        type: String,
        unique: true,
        lowercase: true
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
},
    {
        versionKey: false
    });
const People = mongoose.model("people", peopleSchema, "people");
export default People;