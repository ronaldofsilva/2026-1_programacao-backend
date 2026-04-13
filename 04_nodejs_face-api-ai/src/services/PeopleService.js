// src/services/peopleService.js
import peopleRepository from "../repositories/peopleRepository.js";

class PeopleService {
    // Listar todas as pessoas cadastradas
    async getAll() {
        const peoples = await peopleRepository.findAll();
        return peoples.map(people => ({ ...people.toObject() }));
    }

    //Busca pessoa pelo ID
    async getPeopleById(peopleId) {
        // Validação no service
        if (!peopleId) {
            throw new Error('ID da pessoa é obrigatório...')
        }
        const peoples = await peopleRepository.findById(peopleId);
        return peoples.map(people => ({ ...people.toObject() }));
    }

    // Criar nova pessoa
    async createPeople(peopleData) {
        const { name, age, email } = peopleData;
        if (!name) {
            throw new Error('Obrigatório informar o nome.')
        }
        // Outras validações
        // Criar registro no banco
        return await peopleRepository.create(peopleData);
    }

    // Atualiza uma pessoa com o id informado por parâmetro
    async updatePeople(peopleId, peopleData) {
        const people = await peopleRepository.findById(peopleId);
        if (!people) {
            throw new Error("A pessoa que está tentando alterar não existe.");
        }
        return await peopleRepository.update(peopleId, peopleData);
    }

    // Deletar uma pessoa com o id informado por parâmetro
    async deletePeople(id) {
        const people = await peopleRepository.findById(id);
        if (!people) {
            throw new Error("A pessoa que está tentando excluir não existe.");
        }
        return await peopleRepository.delete(id);
    }

    // Pesquisas personalizada
    async buscaPessoaPorNome(nome) {
        // Esse método deve ser implementado no Repository
        return peopleRepository.findByName(nome);
    }


}

export default new PeopleService();
