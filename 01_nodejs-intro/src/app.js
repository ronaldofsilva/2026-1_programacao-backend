import express from "express";

const people = [
    { "id": 1, "name": "Maria dos Santos", "age": 40 },
    { "id": 2, "name": "José dos Santos", "age": 30 }
];

const app = express();

app.use(express.json());

/** GET: Lista todos os usuários cadastrados */
app.get("/people", (req, res) => {
    res.status(200).json(people);
});


/** POST: Cria um novo usuário e o adiciona à lista */
app.post("/people/create", (req, res) => {
    const data = req.body;
    people.push(data);
    res.status(201).json({
        "error": false,
        "msg": "Registro criado com sucesso!",
        "data": data
    });
});

/** PUT: Atualiza o nome e a idade de um usuário existente pelo ID (índice) */
app.put("/people/:id", (req, res) => {
    const index = req.params.id;
    people[index - 1].name = req.body.name;
    people[index - 1].age = req.body.age;
    res.status(200).json(`${req.body.name} alterado com sucesso.`);
});

/** DELETE: Remove um usuário da lista com base no ID fornecido */
app.delete("/people/:id", (req, res) => {
    const index = buscaPessoa(req.params.id);
    people.splice(index, 1);
    res.status(200).send("Registro excluído com sucesso");
});



export default app;