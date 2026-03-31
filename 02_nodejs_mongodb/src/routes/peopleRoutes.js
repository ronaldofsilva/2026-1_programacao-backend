import express from 'express';
import peopleController from '../controllers/peopleController.js';
const router = express.Router();
router.post('/people/create', peopleController.create);
/**Implemente as outras rotas a partir daqui */

export default router;