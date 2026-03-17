import mongoose from "mongoose";
async function conectaNaDatabase(){
    mongoose.connect(process.env.DB_STRING_CONNECTION);
    return mongoose.connection;
}
export default conectaNaDatabase;