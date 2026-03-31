import express from "express";
import conectaNaDatabase from "./config/dbConnect.js";
import fs from "fs/promises";
import path from 'path';

import upload from "./config/multer.js";
import people from "./models/People.js";
import photo from "./models/Photo.js";

import peopleRoutes from './routes/peopleRoutes.js'
import photoRoutes from './routes/photoRoutes.js';

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

/** ########################## ESTE É O CÓDIGO SEM REFATORAÇÃO, USE COMO PASE PARA APLICAR OS PATTERS */
/** Veja as responsabilidades e finalidade de cada um dos padrões e refatore o código */

/** GET: Lista todos os usuários cadastrados */
app.get("/people", async (req, res) => {
    res.set('Cache-Control', 'public, max-age=3600');
    const people = await people.find({});
    res.status(200).json(people);
});


/** PUT: Atualiza o nome e a idade de um usuário existente pelo ID (índice) */
app.put("/people/:id", async (req, res) => {
    try {
        const id = req.params.id;
        await people.findByIdAndUpdate(id, req.body);
        res.status(200).json({ message: `${req.body.name} atualizado(a) com sucesso!` });
    } catch (erro) {
        res.status(500).json({ message: `${erro.message} - falha na atualização` });
    }

});

/** DELETE: Remove uma pessoa da coleção com base no ID fornecido */
app.delete("/people/:id", async (req, res) => {

    try {
        const id = req.params.id;
        await people.findByIdAndDelete(id);
        res.status(200).json({ message: `Pessoa excluída com sucesso` });
    } catch (erro) {
        res.status(500).json({ message: `${erro.message} - falha na exclusão` });
    }

});

app.get('/people/search', (req, res) => {
    const name = req.query.name;
    try {
        const people = buscaPessaPorNome(name);
        if (!people) {
            res.status(404).json('Pessoa não encontrada');
        }
        res.status(200).json(people);
    } catch (erro) {
        res.status(500).json(`Erro interno: ${erro.message}`);
    }
});

function buscaPessoa(id) {
    return people.findIndex(people =>
        people.id === Number(id)
    );
}

function buscaPessaPorNome(nome) {
    return people.find(people =>
        people.name === nome
    );
}

/**PUT: Alterar uma foto existente (substituir foto) */
app.put('/photo/:id',
    upload.single('photo'),
    async (req, res) => {
        try {
            const { id } = req.paramss;
            const newFile = req.file;
            let updateData = {};
            const currentPhoto = await photo.findById(id);
            if (!currentPhoto) {
                if (newFile) await fs.unlink(newFile.path);
                return res.status(404).json({
                    message: "Foto não encontrada"
                });
            }
            if (newFile) {
                const oldPath = path.join('assets/uploads', currentPhoto.photo);
               try {
                    await fs.access(oldPath);
                    await fs.unlink(oldPath);
                } catch (err) {
                    console.warn("Arquivo não encontrado, ignorando...");
                }
                updateData.photo = newFile.filename;
            }
            const updated = await photo.findByIdAndUpdate(
                id,
                updateData,
                { returnDocument: 'after', runValidators: true }
            ).populate('people', 'name age');
            
            res.status(200).json(updated);
            

        } catch (error) {
            if (req.file) await fs.unlink(req.file.path);
            res.status(400).json({ message: error.message });
        }
    }
);


// GET /photos/:id - Por ID
app.get('/photos/:id', async (req, res) => {
    const photo = await photo.findById(req.params.id)
        .populate('people', 'name age');
    res.status(200).json(photo);
});

// DELETE /photos/:id - Deletar
app.delete('/photos/:id', async (req, res) => {
    const photo = await photo.findById(req.params.id);

    // Verificações...
    // Deletar arquivo
    await fs.unlink(path.join('uploads', photo.photo));

    // Deletar registro
    await Photo.findByIdAndDelete(req.params.id);

    res.status(204).send();
});


/** ####################### CÓDIGO REFATORADO ###################### */
/**Endpoints para API fotos */
// Todas as rotas estão definidas em photoRoutes
app.use('/',photoRoutes);

/** Endpoints para API pessoas */
// Todas as rotas estão definidas em peopleRoutes
app.use('/',peopleRoutes);

export default app;