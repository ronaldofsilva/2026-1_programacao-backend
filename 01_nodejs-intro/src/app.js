import express from "express";

const people = [
    { "id": 1, "name": "Maria dos Santos", "age": 40 },
    { "id": 2, "name": "José dos Santos", "age": 30 }
];

const app = express();

app.use(express.json());

/** GET: Lista todos os usuários cadastrados */
app.get("/people", (req, res) => {
    res.set('Cache-Control','public, max-age=3600');
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
    const index = buscaPessoa(req.params.id);
    if (index == -1){
        res.status(404).json(`Pessoa não econtrada.`);
    }
    people[index].name = req.body.name;
    people[index].age = req.body.age;
    res.status(200).json(`${req.body.name} alterado com sucesso.`);
});

app.get('/people/search',(req, res) => {
  const name = req.query.name;
  try{
    const person = buscaPessaPorNome(name);
    if (!person){
        res.status(404).json('Pessoa não encontrada');
    }
    res.status(200).json(person);
  }catch(erro){
    res.status(500).json(`Erro interno: ${erro.message}`);
  }
});

/** DELETE: Remove um usuário da lista com base no ID fornecido */
app.delete("/people/:id", (req, res) => {
    const index = buscaPessoa(req.params.id);
    try{
        if (index == -1){
            res.status(404).json('Pessoa não encontrada');
        }
        people.splice(index, 1);
        res.status(200).send("Registro excluído com sucesso");
    }catch(erro){
        res.status(500).json(`Erro interno: ${erro.message}`);
    }

});

function buscaPessoa(id){
    return people.findIndex(person => 
        person.id === Number(id)
    );
}

function buscaPessaPorNome(nome){
    return people.find(person =>
        person.name === nome
    );
}

export default app;