
import multer from "multer";
import path from "path";
import crypto from "crypto";
/**Define o local e o nome do arquivo salvo */
const storage = multer.diskStorage({
  destination: (req, file, cb) => cb(null, 'assets/uploads/'),
  filename: (req, file, cb) => {
    const hash = crypto.randomBytes(8).toString('hex');
    const ext = path.extname(file.originalname);
    cb(null, `${Date.now()}-${hash}${ext}`);
  }
});
/**Define os tipos (formatos de imagens aceitos) */
const fileFilter = (req, file, cb) => {
  const allowed = ['image/jpeg', 'image/png', 'image/gif'];
  allowed.includes(file.mimetype) ? cb(null, true) : cb(error);
};

/**Aplica as restrições de tipo e tamanho máximo do arquivo aceito */
const upload = multer({
  storage, fileFilter,
  limits: { fileSize: 5 * 1024 * 1024 }  // 5MB
});

export default upload;