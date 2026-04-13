import peopleService from "../services/PeopleService.js";

class PeopleController {
    // GET /peoples
    async index(req, res) {
        try {
            const peoples = await peopleService.getAll();
            res.status(200).json(peoples);
        } catch (error) {
            res.status(500).json({
                message: "Erro ao buscar pessoas",
                error: error.message
            });
        }
    }

    // GET /people/:id
    async getById(req, res) {
        try {
            const { peopleId } = req.params;
            const peoples = await peopleService.getPeopleById(peopleId);
            res.status(200).json(peoples);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    // POST /people/create
    async store(req, res) {
        try {
            const { peopleData } = req.body;
            const people = await peopleService.createPeople(peopleData);
            res.status(201).json(people);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }
    // PUT //people/:id
    async update(req, res) {
        try {
            const peopleId = req.params.id;
            const peopleData = req.body;
            const people = await peopleService.updatePeople(peopleId, peopleData);
            res.status(201).json(people);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    // DELETE /people/:id
    async delete(req, res) {
        try {
            const { id } = req.params;
            await peopleService.deletePeople(id);
            // Melhoria: retornar uma mensagem
            res.status(204).send();
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    async search(req, res) {
        // Busca da query string
        // A URL via Postman deve ser semelhante a esta http://localhost:8080/people/search?name=josé
        // O nome da variável é importante
        const name = req.query.name;      
        try {
            const people = await peopleService.buscaPessoaPorNome(name);
            if (!people || people.length === 0) {
                return res.status(404).json('Pessoa não encontrada');
            }
            res.status(200).json(people);
        } catch (erro) {
            res.status(500).json(`Erro interno: ${erro.message}`);
        }
    }

}
export default new PeopleController();