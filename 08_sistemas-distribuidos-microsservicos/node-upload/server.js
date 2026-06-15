import express from 'express';
import multer from 'multer';
import path from 'path';
const app = express();
const storage = multer.diskStorage({
    destination: (req, file, cb) => cb(null, '/app/uploads'),
    filename: (req, file, cb) => {
        const unique = Date.now() + '-' + Math.round(Math.random() * 1e9);
        cb(null, unique + path.extname(file.originalname));
    }
});
const upload = multer({ storage });
app.post('/upload', upload.single('file'), (req, res) => {
    if (!req.file) return res.status(400).json({ error: 'Nenhum arquivo enviado' });
    res.json({ filename: req.file.filename, size: req.file.size });
});
app.listen(3000, () => console.log('Upload service running :3000'));
