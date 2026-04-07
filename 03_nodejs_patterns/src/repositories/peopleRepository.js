import People from '../models/People.js';
class PeopleRepository {
    // Buscar todas as pessoas
    async findAll() {
        return await People.find();
    }

    // Buscar uma pessoa pelo ID
    async findById(id) {
        return await People.findById(id);
    }

    // Criar nova foto
    async create(peopleData) {
        const people = new People(peopleData);
        return await people.save();
    }

    // Atualizar uma pessoa
    async update(id, peopleData) {
        return await People.findByIdAndUpdate(id, peopleData, {
            new: true,
            runValidators: true
        });
    }

    // Deletar uma pessoa
    async delete(id) {
        return await People.findByIdAndDelete(id);
    }

    // Busca aproximada por nome
    async findByName(nome) {
        return await People.find({
                                name: { $regex: nome, $options: "i" }
                            }).collation({ locale: "pt", strength: 1 });
    }
}
export default new PeopleRepository();