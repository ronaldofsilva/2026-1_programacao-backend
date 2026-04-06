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
     */
    async index(req, res){
        try{
            const peoples = peopleService.getAll();
            res.status(200).json(peoples);
        }catch(error){
            res.status(500).json({'Erro': error.message});
        }
    } 
    /* UPDATE */
    async update(req, res){

    }
    /* DELETE */
    async delete(req, res){

    }
    /* Outros necessários
    */

}

export default new PeopleController();