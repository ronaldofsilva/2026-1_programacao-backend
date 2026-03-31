import people from "../models/People.js";

class PeopleRepository{
    async create(peopleData){
        const newPeople = new people(peopleData);
        return await newPeople.save();
    }
}
export default new PeopleRepository();