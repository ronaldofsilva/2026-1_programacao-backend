import express from 'express';
import emotionDetectionController from '../controllers/emotionDetectionController.js';
const router = express.Router();

router.get('/emotion/:id',emotionDetectionController.loadFace);

export default router;