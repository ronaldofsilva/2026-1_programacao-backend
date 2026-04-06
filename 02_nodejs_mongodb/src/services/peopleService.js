import peopleRepository from "../repositories/peopleRepository.js";

class PeopleService{
    async createPeople(people){
        //Método para criar/salvar a pessoa no banco de dados implementado no Repository
        //Caso tenha validações colocar aqui
        return await peopleRepository.create(people);
    }
    /**Impemente os demais métodos */
    async getAll(){
        /**... */
        
    }
}

export default new PeopleService();