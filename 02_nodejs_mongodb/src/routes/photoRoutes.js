import express from 'express';
import upload from '../config/multer.js';
import photoController from '../controllers/photoController.js';
const router = express.Router();
router.post('/photo/create',upload.single('photo'));
router.get('/photos', photoController.index);
router.get('/photos/people',photoController.photosPeople);
export default router;
