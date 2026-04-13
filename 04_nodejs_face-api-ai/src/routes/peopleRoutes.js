import express from "express";
import peopleController from "../controllers/peopleController.js";

const router = express.Router();

router.get("/people", peopleController.index);
router.get("/people/search",peopleController.search);
router.get("/people/:id", peopleController.getById);
router.post("/people/create", peopleController.store);
router.put("/people/:id", peopleController.update);
router.delete("/people/:id",peopleController.delete);

export default router;