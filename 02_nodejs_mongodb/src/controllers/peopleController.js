import peopleService from "../services/peopleService.js";
class PeopleController {
    async create(req, res) {
        try {
            const peopleData = req.body; //Aqui obtem todos os dados obtidos do "front-end"            
            //Chama o método de criação da pessoa implementado no Service            
            const people = await peopleService.createPeople(peopleData);
            res.status(201).json({
                message: 'Pessoa criada com sucesso.',
                data: people
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }
    /**Impmente os outros métodos a partir da qui 
     * GET
     * UPDATE
     * DELETE
     * Outros necessários
    */

}

export default new PeopleController();