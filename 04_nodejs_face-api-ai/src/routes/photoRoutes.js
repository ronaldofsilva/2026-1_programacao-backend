import express from "express";
import photoController from "../controllers/photoController.js";
import upload from "../config/multer.js";
import { authenticateToken } from "../middlewares/auth.js";

const router = express.Router();
router.get("/photo", photoController.index);
router.get("/photo/:id",photoController.getById);
router.get("/photo/people", photoController.getPhotosAndPeople)
router.get("/photo/people/:id", photoController.getByPeople);
router.post("/photo/create", upload.single('photo'), photoController.store);
router.put("/photo/:id", authenticateToken, upload.single('photo'), photoController.update);
export default router;