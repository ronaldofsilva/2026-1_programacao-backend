import express from "express";
import conectaNaDatabase from "./config/dbConnect.js";
import photoRoutes from "./routes/photoRoutes.js";
import peopleRoutes from "./routes/peopleRoutes.js";

const app = express();

/**Midlewares */
app.use(express.json());
app.use('/uploads', express.static('assets/uploads'));

/**Conexão com o banco de dados */
const conexao = await conectaNaDatabase();
conexao.on("error", (erro) => {
    console.error("Erro ao conectar ao banco de dados: ", erro)
});
conexao.once("open", () => {
    console.log("Conexao estabelecida com êxito.")
});

/**Endpoints para API de fotos */
app.use('/',photoRoutes);

/**Endpoints para API de pessoas */
app.use('/',peopleRoutes);

export default app;